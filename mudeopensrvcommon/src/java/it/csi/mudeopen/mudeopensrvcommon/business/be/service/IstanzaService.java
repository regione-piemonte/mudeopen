/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import java.time.LocalDate;
import java.util.List;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specifications;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoNotifica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTDocumento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanzaSlim;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTNotifica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.SoggettoIstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.request.SalvaIstanzaRequest;
import it.csi.mudeopen.mudeopensrvcommon.vo.request.SalvaTitoloSoggettoAbilitatoRequest;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.RuoliIstanzaResponse;

public interface IstanzaService {

    //    /**
    //     * Aggiungi documento soggetto.
    //     *
    //     * @param idIstanza  the id istanza
    //     * @param idSoggetto the id soggetto
    //     * @param input      the input
    //     * @param mudeTUser  the mude t user
    //     */
    //    void aggiungiDocumentoSoggetto(Long idIstanza, Long idSoggetto, MultipartFormDataInput input, MudeTUser mudeTUser);

    IstanzaVO salvaIstanza(SalvaIstanzaRequest request, MudeTUser mudeTUser, HttpHeaders httpHeaders);

    //    /**
    //     * Aggiungi soggetto pf ruoli istanza response.
    //     *
    //     * @param soggetto  the soggetto
    //     * @param mudeTUser the mude t user
    //     * @return the ruoli istanza response
    //     */
    //    RuoliIstanzaResponse aggiungiSoggettoPF(AggiungiSoggettoPFIstanzaRequest soggetto, MudeTUser mudeTUser);

    //    /**
    //     * Aggiungi soggetto pg ruoli istanza response.
    //     *
    //     * @param soggetto  the soggetto
    //     * @param mudeTUser the mude t user
    //     * @return the ruoli istanza response
    //     */
    //    RuoliIstanzaResponse aggiungiSoggettoPG(AggiungiSoggettoPGIstanzaRequest soggetto, MudeTUser mudeTUser);
    RuoliIstanzaResponse aggiungiSoggetto(SoggettoIstanzaVO soggetto, Long replaceIdSoggetto, MudeTUser mudeTUser, boolean autoImportIN, MudeTIstanza massiveIstanzaImport);

    IstanzaVO salvaTitoloSoggettoAbilitato(SalvaTitoloSoggettoAbilitatoRequest request, MudeTUser mudeTUser);

    Page<IstanzaVO> recuperaIstanzeUtente(MudeTUser userCF, int page, int size);

    RuoliIstanzaResponse getRuoliIstanza(MudeTUser userCF, Long idIstanza, String excl_user_ids, Boolean loadAbilitazioni);

    IstanzaVO recuperaIstanza(MudeTUser userCF, Long idIstanza);

    IstanzaVO recuperaIstanza(MudeTUser userCF, Long idIstanza, String scope, String filters);

    void eliminaIstanza(MudeTUser mudeTUser, Long id);

    Response cercaIstanzeUtente(String filter, MudeTUser mudeTUser, String scope, int page, int size, String sort);

	//Response cercaIstanzeWS(String filter, String fruitore, String scope, int page, int size);

    Response cercaIstanzeFascicolo(Long idFascicolo, String filter, String sort, int page, int size, MudeTUser mudeTUser, String scope);

    List<SelectVO> getListaTipoPresentatore(MudeTUser userCF, Long idTipoPresentatore);

    RuoliIstanzaResponse eliminaSoggettoCoinvolto(Long idIstanza, Long idSoggetto, MudeTUser userCF);
    int countIstanzeByFascicolo(Long idFascicolo);

    void updateUuidIndex(IstanzaVO istanzaVO, MudeTUser mudeTUser);

    IstanzaVO updateDatiFileIstanza(IstanzaVO istanzaVO, MudeTUser mudeTUser);

    Boolean verifyDuplicateFilename(String codIstanza, String filename);
    Response recuperaAbilitazioniIstanza(Long idIstanza, MudeTUser mudeTUser);

    Response listaPossibiliIstanze(MudeTUser mudeTUser, String idTipoIstanza, Long idFascicolo, Long idIstanza, int page, int size);

    void deleteFileIstanzaFirmata(Long idIstanza, MudeTUser mudeTUser, HttpHeaders httpHeaders, boolean setToDFR, String scope);
    Response cercaPraticheUtente(MudeTUser mudeTUser, Long idFascicolo, Long idPratica, String code, String idIntestatarioPf, String idIntestatarioPg, String idPm, Long idIstanzaRiferimento, String codiceTipoIStanza, String numeroProtocollo, LocalDate dataProtocolloDa, LocalDate dataProtocolloA, Long anno, String numPratica, LocalDate dataRegistrazionePraticaDa, LocalDate dataRegistrazionePraticaA, String scope, int page, int size);

    byte[] exportExcelIstanzeUtente(String filter, String sort, MudeTUser mudeTUser, Long idFascicolo, Long idPratica, String numPratica, String codiceTipoIStanza, String idIntestatarioPf, String idIntestatarioPg, String idPm, Long idComune, Long idProvincia, Long idDug, String duf, LocalDate creationDateFrom, LocalDate creationDateTo, String code, String scope);

    void cleanCachePdfIstanza(Long idIstanza);

    byte[] loadCachedPdfIstanza(Long idIstanza);

    boolean pdfIstanzaIsCached(Long idIstanza);

    List<SelectVO> recuperoTemplateFormIoCambioStato(MudeTUser mudeTUser, IstanzaVO istanzaVO, String codStatusEnd);

    List<SelectVO> recuperoTipoDoc();

    //List<SelectVO> recuperoTemplateFormIoUplDoc(MudeTUser mudeTUser, Long idPratica, String nomeFile, String tipoFile);

    MudeTNotifica insertTNotifica(MudeTUser mudeTUser,Long idIstanza,String jsonData, MudeDTipoNotifica tipoNotifica);
	
	byte[] exportExcelPraticheUtente(MudeTUser mudeTUser, Long idFascicolo, Long idPratica, String code, String idIntestatarioPf, String idIntestatarioPg, String idPm, Long idIstanzaRiferimento, String codiceTipoIStanza, String numeroProtocollo, LocalDate dataProtocolloDa, LocalDate dataProtocolloA, Long anno, String numPratica, LocalDate dataRegistrazionePraticaDa, LocalDate dataRegistrazionePraticaA, String scope);
	 
	 void updateJsonDataFormIO(Long idIstanza, String codStatus, String jsonData, IstanzaVO istanza);
	
	List<MudeTContatto> recuperaContatti(Long idIstanza, MudeTUser mudeTNotifica);
	
	void insertRNotificaContatto(Long idIstanza, MudeTNotifica mudeTNotifica, List<MudeTContatto> contattiList, IstanzaVO istanza);
	
	void insertRNotificaUtenti(Long idIstanza, MudeTNotifica mudeTNotifica, List<MudeTContatto> contattiDestinatari);

	Response cercaIstanzePratica(Long idPratica, int page, int size, MudeTUser mudeTUser);

    void insertRNotificaDocumento(Long idIstanza, MudeTNotifica mudeTNotifica, List<MudeTDocumento> documenti);

    Response cercaIstanzeUtenteScrivania(String filter, MudeTUser mudeTUser, String scope, int page, int size);

    String createDettaglioNotificaPortale(Long idIstanza, MudeTNotifica mudeTNotifica);

    String createTestoNotificaDPS(Long idIstanza, MudeTNotifica mudeTNotifica);

    Specifications<MudeTIstanza> getIstanzeSpecifications(String filter, MudeTUser mudeTUser, String scope, String methodName);

    Specifications<MudeTIstanzaSlim> getIstanzeSpecificationsSlim(String filter, MudeTUser mudeTUser, String scope, String methodName);
    
    MudeTIstanzaSlim recuperaIstanzaSlim(Long idIstanza);
    
}