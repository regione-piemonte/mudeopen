/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import java.util.List;

import javax.persistence.EntityManager;

import it.csi.mudeopen.mudeopensrvcommon.business.be.service.TipoIstanzaService;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRegimeAggiuntivo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRegimeGiuridico;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTipoIstanzaRegimeGiuRegimeAgg;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoIstanzaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRTipoIstanzaRegimeGIuRegimeAggRepository;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoIstanzaVO;

@Service
public class TipoIstanzaServiceImpl implements TipoIstanzaService {

	private static Logger logger = Logger.getLogger(TipoIstanzaServiceImpl.class.getCanonicalName());

    @Autowired
    private EntityManager entityManager;
	
	@Autowired
	private MudeDTipoIstanzaRepository mudeDTipoIstanzaRepository;

	@Autowired
	private MudeRTipoIstanzaRegimeGIuRegimeAggRepository mudeRTipoIstanzaRegimeGiuRegimeAggRepository;

	@Autowired
	private TipoIstanzaEntityMapper tipoIstanzaMapper;
	
	@Autowired
	private TipoIstanzaEntityMapper tipoIstanzaEntityMapper;

	/*
	 * DISMISSED BY NOW: used by wizard
	 */
	@Override
	public TipoIstanzaVO findByRegimeGiuridicoAndRegimeAggiuntivo(Long idRegimeGiuridico, Long idRegimeAggiuntivo) {
		MudeDRegimeGiuridico regGiu = new MudeDRegimeGiuridico();
		regGiu.setIdRegime(idRegimeGiuridico);

		MudeDRegimeAggiuntivo regAgg = new MudeDRegimeAggiuntivo();
		regAgg.setIdRegime(idRegimeAggiuntivo);

		MudeRTipoIstanzaRegimeGiuRegimeAgg item = mudeRTipoIstanzaRegimeGiuRegimeAggRepository
				.findByMudeDRegimeGiuridicoAndMudeDRegimeAggiuntivo(regGiu, regAgg);

		return tipoIstanzaMapper.mapEntityToVO(item.getMudeDTipoIstanza());
	}

	/*
	 * generic function to retrieve in the proper way the "topologia istanze" filterd by any the four params 
	 */
	@Override
	public List<TipoIstanzaVO> recuperaTipologieIstanze(Long idComune,
													String codiceIstanzaPadre, 
													Long idIstanza, 
													Long idFascicolo) {
		idComune = idComune == null? 0L : idComune; 
		idIstanza = idIstanza == null? 0L : idIstanza;
		idFascicolo = idFascicolo == null? 0L : idFascicolo;

		String query = mudeDTipoIstanzaRepository.getTipiIstanzeBaseQuery(idFascicolo,
				"SELECT DISTINCT \n"
			    		+ "    mdti.codice, \n"
			    		+ "    mdti.descrizione, \n"
			    		+ "    mdti.descrizione_estesa, \n"
			    		+ "    CASE WHEN allowed_tipo_istanze.cod_tipo_istanza_figlia IS NULL THEN FALSE ELSE TRUE END AS legata, \n"
			    		+ "    mdti.id_ambito, \n"
			    		+ "    CASE WHEN mdti.legata = TRUE THEN FALSE ELSE TRUE END AS almeno_un_ruolo, \n"
			    		+ "    mdti.data_inizio, \n"
			    		+ "    mdti.data_fine,\n"
			    		+ "    mdti.cambio_intestatario,\n"
			    		+ "    mdti.id_template_ricevuta_istanza,\n"
			    		+ "    mdti.soggetti_bloccati,\n"
			    		+ "    mdti.escludi_branch,\n"
			    		+ "    mdti.override_ente_padre \n"
			    		+ "    , mdti.loguser\n"
			    		+ "    , mdti.data_inizio\n"
			    		+ "    , mdti.data_modifica\n"
			    		+ "    , mdti.data_fine\n"
			    		+ "  FROM mudeopen_d_tipo_istanza mdti \n"
			    		+ "    INNER JOIN (SELECT DISTINCT codice_tipo_istanza, id_ente, id_comune FROM mudeopen_r_ente_comune_tipo_istanza WHERE (data_fine IS NULL OR data_fine > NOW()) AND competenza_principale = true) mreti ON mdti.codice=mreti.codice_tipo_istanza \n"
			    		+ "    LEFT JOIN (\n"
			    		+ "      SELECT cod_tipo_istanza_figlia \n",
			    		"				AND (NOT("+idIstanza+"=0) OR NOT("+idFascicolo+"=0))\n"
			    		+ "				AND ("+idIstanza+"=0 OR mti.id_istanza = "+idIstanza+")\n"
			    		+ "				AND ("+idFascicolo+"=0 OR mtf.id_fascicolo = "+idFascicolo+")\n"
			    		+ "    ) allowed_tipo_istanze ON allowed_tipo_istanze.cod_tipo_istanza_figlia=mdti.codice \n"
			    		+ "  WHERE (mdti.privato IS NULL OR mdti.privato = false) AND mdti.data_fine IS NULL\n"
			    		+ (idComune == 0? "" : "		AND mreti.id_comune = " + idComune)
			    		+ (StringUtils.isBlank(codiceIstanzaPadre)? "" :"		AND mdti.codice IN (" + codiceIstanzaPadre.split(",") + ")\n")
			    		+ "    AND (NOT(mdti.legata) OR allowed_tipo_istanze.cod_tipo_istanza_figlia IS NOT NULL)\n"
			    		+ "  ORDER BY mdti.descrizione_estesa ASC;");
		
		List<MudeDTipoIstanza> entities = entityManager.createNativeQuery(query, MudeDTipoIstanza.class).getResultList();
		
		return tipoIstanzaEntityMapper.mapListEntityToListVO(entities);
	}

}