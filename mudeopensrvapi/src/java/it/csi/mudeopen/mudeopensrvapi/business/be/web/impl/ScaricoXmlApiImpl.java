/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.web.impl;

import java.util.Optional;

import javax.ws.rs.core.Response;

import it.csi.mudeopen.mudeopensrvapi.business.be.entity.MudeTIstanzaSLIM;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoTracciatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaTracciatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaStatoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.ConfigurationHelper;
import it.csi.mudeopen.mudeopensrvapi.business.be.exception.Error;
import it.csi.mudeopen.mudeopensrvapi.business.be.service.MudeopenTIstanzaService;
import it.csi.mudeopen.mudeopensrvapi.business.be.web.ScaricoXmlApi;
import it.csi.mudeopen.mudeopensrvapi.vo.DatiXmlVO;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDFruitore;
@Component
public class ScaricoXmlApiImpl extends AbstractApiServiceImpl implements ScaricoXmlApi {

	private static Logger logger = Logger.getLogger(ScaricoXmlApiImpl.class.getCanonicalName());

	@Autowired
	public MudeopenTIstanzaService mudeopenTIstanzaService;

	@Autowired
	private IstanzaStatoService istanzaStatoService;

	@Autowired
	private MudeRIstanzaTracciatoRepository mudeRIstanzaTracciatoRepository;

	@Autowired
	private MudeDTipoTracciatoRepository mudeDTipoTracciatoRepository;
	
	@Override
	public Response scaricoXML(String XRequestId, String XForwardedFor, String fruitoreID,
									String numeroIstanza, String codiceTipoTracciato) throws Exception {
		try {
    		MudeDFruitore fruitore = verifyFruitore(fruitoreID);

			if(StringUtils.isBlank(numeroIstanza))
				return errorToResponse(new Error(org.springframework.http.HttpStatus.NOT_FOUND, "[CampoObbligatorioException] Numero istanza"));
			if(StringUtils.isBlank(codiceTipoTracciato))
				return errorToResponse(new Error(org.springframework.http.HttpStatus.NOT_FOUND, "[CampoObbligatorioException] Codice tipo tracciato"));

			Optional<MudeTIstanzaSLIM> opt = mudeopenTIstanzaService.cercaIstanzaDPSOSuccessivoWSSlim(fruitore, numeroIstanza);
			if(opt.isEmpty())
				return errorToResponse(new Error(org.springframework.http.HttpStatus.NOT_FOUND, "[IstanzaNonTrovataException] Istanza / intervento non trovati"));
			MudeTIstanzaSLIM mudeTIstanza = opt.get();
			String statoIstanza = istanzaStatoService.getStatoIstanza(mudeTIstanza.getId());

			if(mudeTIstanza.getId() != null && "BZZ,DFR,FRM".indexOf(StringUtils.stripToEmpty(statoIstanza)) > -1)
				return errorToResponse(new Error(org.springframework.http.HttpStatus.NOT_FOUND, "[IstanzaNonConsultabileException] Operazione non consentita: istanza in stato " + statoIstanza));

			String datiXmlString = mudeRIstanzaTracciatoRepository.findTracciatoXMLByIdIstanza(opt.get().getId(), codiceTipoTracciato);
			if(datiXmlString==null){
				return errorToResponse(new Error(org.springframework.http.HttpStatus.NOT_FOUND, "[SystemException] XML inesistente per istanza"));
			}

			String tipoTracciatoString = mudeDTipoTracciatoRepository.findTipoTracciatoByCodice(codiceTipoTracciato);
			TipoTracciatoSlim tipoTracciato = fromString(tipoTracciatoString);

        	DatiXmlVO datiXml = new DatiXmlVO();
			datiXml.setTracciatoXML(datiXmlString);
			datiXml.setDescrizioneTipoTracciato(tipoTracciato.getDescrizione());
    		datiXml.setVersione(tipoTracciato.getVersione());
        	return voToResponse(datiXml, HttpStatus.SC_OK);
		}catch (Throwable t) {
			return errorToResponse(handleUnexpectedError(t));			
        }
	}

	public TipoTracciatoSlim fromString(String tipoTracciatoString){
		TipoTracciatoSlim c = new TipoTracciatoSlim();
		String[] tokens = tipoTracciatoString.split("\\$\\$\\$");
		c.codice = "null_string".equalsIgnoreCase(tokens[0]) ? null : tokens[0];
		c.descrizione = "null_string".equalsIgnoreCase(tokens[1]) ? null : tokens[1];
		c.versione = "null_string".equalsIgnoreCase(tokens[2]) ? null : tokens[2];
		return c;
	}

	private class TipoTracciatoSlim {
		public String codice;
		public String descrizione;
		public String versione;
		public String getCodice() {
			return codice;
		}

		public void setCodice(String codice) {
			this.codice = codice;
		}

		public String getDescrizione() {
			return descrizione;
		}

		public void setDescrizione(String descrizione) {
			this.descrizione = descrizione;
		}

		public String getVersione() {
			return versione;
		}

		public void setVersione(String versione) {
			this.versione = versione;
		}
	}
}
