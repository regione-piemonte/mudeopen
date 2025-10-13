/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.fasterxml.jackson.core.type.TypeReference;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.*;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.*;
import it.csi.mudeopen.mudeopensrvcommon.vo.cosmo.*;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.StatoIstanza;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.MudeCosmoManager;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.EsitoCreazioneDocumentiFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.cosmo.swagger.model.FileUploadResult;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon.IstanzaApiServiceHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ContattoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IstanzaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.CosmoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.IndexManager;
import it.csi.mudeopen.mudeopensrvcommon.util.BasicTransformer;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.TipoContatto;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.StatiProcessoCosmoEnum;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;



@Service
public class CosmoServiceImpl implements CosmoService {
	private static Logger logger = Logger.getLogger(CosmoServiceImpl.class.getCanonicalName());

	private static final String LOCAL = "local";
    private static final String _DENUNCIA_SISMICA = "DENUNCIA_SISMICA";

    private static final String _INT_DOC = "INT_DOC";
    private static final String _IL_DS = "IL_DS";
    private static final String _FIL_DS = "FIL_DS";
    
    private static final String _DRE = "DRE_DS";
    private static final String _RSU = "RSU_DS";
    private static final String _COLLAUDO = "COLLAUDO_DS";
	
    @Autowired
    private MudeCProprietaRepository mudeCProprietaRepository;
    
    @Autowired
    private MudeCProprietaEnteRepository mudeCProprietaEnteRepository;
    
    @Autowired
    private MudeCosmoManager mudeCosmoManager;
    
    @Autowired
    private MudeTAllegatoSlimRepository mudeTAllegatoSlimRepository;
    
    @Autowired
    private MudeTPraticaCosmoRepository mudeTPraticaCosmoRepository;
    
    @Autowired
    private MudeTContattoRepository mudeTContattoRepository;
    
    @Autowired
    private MudeDDugRepository mudeDDugRepository;
    
    @Autowired
    private MudeTIstanzaSlimRepository mudeTIstanzaSlimRepository;
    
    @Autowired
    private MudeTIstanzeExtRepository mudeTIstanzeExtRepository;
    
    @Autowired
    private MudeTIstanzaRepository mudeTIstanzaRepository;
    
    @Autowired
    private MudeTEnteRepository mudeTEnteRepository;
    
    @Autowired
    private MudeTFascicoloRepository mudeTFascicoloRepository;
    
    //@Autowired
    //private MudeRPfPgRepository mudeRPfPgRepository;
    
    @Autowired
    private IndexManager indexManager;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private PlatformTransactionManager transactionManager;
    
    @Autowired
    private IstanzaApiServiceHelper istanzaApiServiceHelper;
    
    @Autowired
    private IstanzaEntityMapper istanzaEntityMapper;
    
    @Autowired
    private MudeTPraticaRepository mudeTPraticaRepository;
    
    @Autowired
    private ContattoEntityMapper contattoEntityMapper;
    
    @Autowired
    private MudeDRuoloSoggettoRepository mudeDRuoloSoggettoRepository;
    
    @Autowired
    private MudeRIstanzaStatoRepository mudeRIstanzaStatoRepository;

	@Autowired
	private MudeDStatoIstanzaRepository mudeDStatoIstanzaRepository;
    
    @Autowired
    private MudeDComuneRepository mudeDComuneRepository;
    
    final static private int _CREATION = 1;
    final static private int _INTEGRATION = 2;
    
	@Override
	public void addPraticaCosmoToQueue(final MudeTIstanza istanza) {
		Long idIstanza = istanza.getId();
		logger.info("[CosmoServiceImpl::addPraticaCosmoToQueue] DA AGGIUNGERE: " + idIstanza);

		MudeTIstanza istanzaRiferimento = null;
	    MudeTPraticaCosmo mudeTPraticaCosmoParent = null;
		if(istanza.getIdIstanzaRiferimento() != null) {
			mudeTPraticaCosmoParent = mudeTPraticaCosmoRepository.findByIdIstanza(istanza.getIdIstanzaRiferimento());
			istanzaRiferimento = mudeTIstanzaRepository.findOne(istanza.getIdIstanzaRiferimento());
		}
		
		MudeTEnte mudeTEnteUTR = null;
		if("DENUNCIA-SISMICA".equals(istanza.getTipoIstanza().getCodice()))
			mudeTEnteUTR = mudeTEnteRepository.getUTR(istanza.getComune().getIdComune(), 
	    		istanza.getTipoIstanza().getCodice(),
	    		istanza.getSpeciePratica() == null ? "getnulls" : istanza.getSpeciePratica().getCodice());
	    
		logger.info("[CosmoServiceImpl::addPraticaCosmoToQueue] mudeTEnteUTR[" + mudeTEnteUTR + "] - mudeTPraticaCosmoParent[" + mudeTPraticaCosmoParent + "]");
	    if(/*!LOCAL.equals(Constants._environment) 
	    		&& */mudeTEnteUTR == null
	    		&& mudeTPraticaCosmoParent == null)
	    	return;
		
	    if(mudeTPraticaCosmoRepository.findByIdIstanza(idIstanza) != null)
		    if(LOCAL.equals(Constants._environment) || "test".equals(Constants._environment))
		    	return; // for debug purposes only!
		    else
				throw new BusinessException("Impossibile creare due volte la stessa pratica su cosmo per la stessa istanza");
	    
    	String numeroPraticaCosmo = /*!isDS && */mudeTPraticaCosmoParent != null ?
    										mudeTPraticaCosmoParent.getNumeroPratica() : // for child instances get the "numeroPraticaCosmo" from parent   
    										null;
    	
    	logger.info("[CosmoServiceImpl::addPraticaCosmoToQueue] numeroPraticaCosmo: " + numeroPraticaCosmo);
    	if(numeroPraticaCosmo == null) {
			String zone = mudeTIstanzaSlimRepository.getZonaSismicaIstanza(idIstanza);
			if(!"3S".equalsIgnoreCase(zone))
				zone = istanza.getComune().getMudeDProvincia().getSiglaProvincia();
		
    		String year = "" + Year.now().getValue();
    	    //mudeTPraticaCosmoRepository.createTemporaryExecuteFunction();
    	    
    	    // create sequence: mudeopen_numero_pratica_cosmo_<anno_corrente>_<codice_ente>_<3S oppure sigla prov>_seq
    	    String seqToUse = mudeTPraticaCosmoRepository.createCosmoSeq(year + "_" + mudeTEnteUTR.getCodice() + "_" + zone);
        	Long numeroPracticeSeq = mudeTPraticaCosmoRepository.getNextNumeroPraticaCOSMO(seqToUse);
    	    
        	numeroPraticaCosmo = year + "/" + (LOCAL.equals(Constants._environment)?"L":"") + numeroPracticeSeq + "/" + zone; // creates a new "numeroPraticaCosmo"
    	}

	    MudeTPraticaCosmo mudeTPraticaCosmo = new MudeTPraticaCosmo();
	    mudeTPraticaCosmo.setIdIstanza(idIstanza);
    	mudeTPraticaCosmo.setNumeroPratica(numeroPraticaCosmo);
    	mudeTPraticaCosmo.setIdDocCosmo(numeroPraticaCosmo + "/C");
    	
	    mudeTPraticaCosmo.setCodiceStatoCosmo(isCosmoCreationProcess(istanza, istanzaRiferimento, false)?
													    		StatiProcessoCosmoEnum.IN_CODA.toString() :
													    		StatiProcessoCosmoEnum.CREATA.toString());
	    mudeTPraticaCosmo = mudeTPraticaCosmoRepository.saveDAO(mudeTPraticaCosmo);
	    
		logger.info("[CosmoServiceImpl::addPraticaCosmoToQueue] ADDED: " + idIstanza);
	    
		/*
	    if(DENUNCIA_SISMICA.equals(istanza.getTipoIstanza().getCodice()) &&
	    			(!"prod".equals(Constants._environment) 
	    						&& "sync".equals(mudeCProprietaRepository.getValueByName("COSMO_PROCESS", "async")))) 
	    	handlPraticaCosmo(mudeTPraticaCosmo, istanza);
	    */
	}

	@Override
	public void processAllJobs() {
		int maxRetries = Integer.parseInt(mudeCProprietaRepository.getValueByName("COSMO_MAX_RETRY", "3"));
		
		logger.info("[CosmoServiceImpl::processAllPraticaCosmo] COSMO JOB STARTED: " + maxRetries);
		
		for(MudeTPraticaCosmo mudeTPraticaCosmo 
					: mudeTPraticaCosmoRepository.findAllByCodiceStatoCosmoIsNullOrNotInCodiceStatoCosmoAndRetriesLessThan(
							new String[] {
								StatiProcessoCosmoEnum.IN_PROCESSO.toString(),
								StatiProcessoCosmoEnum.SEGNALATA.toString()
							}, maxRetries)) {
			MudeTIstanza mudeTIstanza = mudeTIstanzaRepository.findOne(mudeTPraticaCosmo.getIdIstanza());
			MudeTIstanza mudeTIstanzaRef = null;
			
			MudeTPraticaCosmo mudeTPraticaCosmoParent = null;
			if(mudeTIstanza.getIdIstanzaRiferimento() != null) {
				mudeTPraticaCosmoParent = mudeTPraticaCosmoRepository.findByIdIstanza(mudeTIstanza.getIdIstanzaRiferimento());
				
				if(mudeTPraticaCosmoParent != null)
					mudeTIstanzaRef = mudeTIstanzaRepository.findOne(mudeTIstanza.getIdIstanzaRiferimento());
			}
			
			if(!handlePraticaCosmo(mudeTPraticaCosmo, 
					mudeTIstanza,
					mudeTIstanzaRef,
					mudeTPraticaCosmoParent))
				mudeTPraticaCosmoRepository.increaseRetryCounter(mudeTPraticaCosmo.getId());
		}
	}
	
	private boolean isCosmoCreationProcess(final MudeTIstanza istanza, final MudeTIstanza istanzaRif, boolean isControlloCampioneSelected) {
		String itype = istanza.getTipoIstanza().getCodice().replace("-", "_");
    	boolean hasFilDSFromAEOS = istanzaRif != null && "AEOS".equalsIgnoreCase(istanzaRif.getIdFonte());  // mudeTIstanzaSlimRepository.getInstanceTypeInFolder(istanza.getMudeTFascicolo().getId(), new String[] { "FIL-DS" }).stream().anyMatch(istanzaSlim -> "AEOS".equalsIgnoreCase(istanzaSlim.getIdFonte()));
    	
    	List<Long> idIstanzaStati = new ArrayList();
    	idIstanzaStati.add(istanza.getId());
    	if(istanzaRif != null) {
    		idIstanzaStati.add(istanzaRif.getId()); // second level
    		if(istanzaRif.getIdIstanzaRiferimento() != null)
    			idIstanzaStati.add(istanzaRif.getIdIstanzaRiferimento()); // third level
    	}
    	
		return _DENUNCIA_SISMICA.equals(itype)
									|| hasFilDSFromAEOS
									/* if the instance is not it one of these states and it is an INT-DOC, then send it to TD. Otherwise attach the doc to the DS/CC */
									|| ((isControlloCampioneSelected?"ATI,AIC":"ATI,SIA,SAI,SIC,AIC").indexOf(mudeRIstanzaStatoRepository.getStatoIstanza(istanza.getIdIstanzaRiferimento())) == -1
											&& _INT_DOC.equals(itype))
	    							|| (!_INT_DOC.equals(itype) && !_DRE.equals(itype) && !_RSU.equals(itype) && !_COLLAUDO.equals(itype))
	    							|| (idIstanzaStati != null && mudeRIstanzaStatoRepository.getStatoIstanze(idIstanzaStati).contains("ARC"));
	}
	
	@Override
	public List<IstanzaVO> markIstancesAsToBeChecked(List<IstanzaVO> instances) {
		for(int i=0; i<instances.size(); i++) {
			IstanzaVO instance = instances.get(i);
			 
		    MudeTPraticaCosmo mudeTPraticaCosmo = mudeTPraticaCosmoRepository.findByIdIstanza(instance.getIdIstanza());
			if(mudeTPraticaCosmo == null) {
				instance.setIdIstanza(null);
				continue;
			}

			mudeTPraticaCosmoRepository.markAsToBeChecked(instance.getIdIstanza());
		}
		
		return instances;
	}
	
	
	private boolean handlePraticaCosmo(
									MudeTPraticaCosmo mudeTPraticaCosmo, 
									final MudeTIstanza istanza,
									final MudeTIstanza istanzaRiferimento,
									final MudeTPraticaCosmo mudeTPraticaCosmoParent) {
		String dsPracticeID = ""+mudeTPraticaCosmo.getId();
		
		boolean isDS = "DENUNCIA-SISMICA".equals(istanza.getTipoIstanza().getCodice());
		boolean isFilDS = "FIL-DS".equals(istanza.getTipoIstanza().getCodice());
		boolean isControlloCampioneSelected = isDS && mudeTPraticaCosmo.getCcSelezionato();
		boolean isDsVariante = isDS && "SPE00RP211,SPE00RP208,SPE00RP210".indexOf(istanza.getSpeciePratica().getCodice()) > -1;
		
	    boolean isCosmoCreationProcess = isCosmoCreationProcess(istanza, istanzaRiferimento, isControlloCampioneSelected);
		boolean isTrasmissioneDocumenti = isCosmoCreationProcess && !(isDS || isFilDS);
		
		boolean sendAllegatiList = mudeCProprietaRepository.getValueByName("COSMO_TD_LISTA_ALLEGATI_PER", "").indexOf(istanza.getTipoIstanza().getCodice()) > -1;

		MudeTIstanza lastDSForPratica = mudeTIstanzaRepository.retrieveMainDenunciaSismica(istanza.getId());

    	try {
		    Long idIstanza = istanza.getId(); 
		    String itype = istanza.getTipoIstanza().getCodice().replace("-", "_");
		    
		    String cfCreatore = istanza.getMudeTUser().getCf();
		    String codiceIstanza = isCosmoCreationProcess? istanza.getCodiceIstanza() : istanzaRiferimento.getCodiceIstanza();
			if(isControlloCampioneSelected || (istanzaRiferimento != null && "AIC".equals(mudeRIstanzaStatoRepository.getStatoIstanza(istanzaRiferimento.getId()))))
				codiceIstanza += "-CC";
		    
		    MudeTContatto intestatario = mudeTContattoRepository.getIntestatarioNameByIdIstanza(idIstanza);
		    
			String ipaEnte = mudeCProprietaRepository.getValueByName("COSMO_CODICE_IPA", "r_piemon");
		    
		    /////////////////////////////////////////////////////////////////////
		    // STEP 1 (----->CREATA): create practice on COSMO

		    if(isCosmoCreationProcess
		    		&& (mudeTPraticaCosmo.getCodiceStatoCosmo() == null
			    			|| StatiProcessoCosmoEnum.IN_CODA.toString().equals(mudeTPraticaCosmo.getCodiceStatoCosmo()))) {
		    	MudeopenInfo mudeopenInfo = new MudeopenInfo();
		    	
		    	mudeopenInfo.urlBO = mudeCProprietaRepository.getValueByName("COSMO_URL_MUDE_BO", "https://dev-mudeopen-bo-rp.nivolapiemonte.it/mudeopen/bo/#/backoffice");
		    	mudeopenInfo.urlIstanzaBO = mudeopenInfo.urlBO+"/lista-istanze/"+idIstanza+"/dettaglio-istanza";
		    	mudeopenInfo.urlIstanzaBODetail = mudeopenInfo.urlBO+"/lista-istanze/"+idIstanza+"/dettaglio-istanza-stepper";
		    	mudeopenInfo.urlIstanzaBOSearch = mudeopenInfo.urlBO+"/pratiche-ds";
		    	mudeopenInfo.urlIstanzaBOPraticaDetail = mudeopenInfo.urlBO+"/pratiche-ds/COSMO-"+mudeTPraticaCosmo.getId()+"/documenti-ds/" + lastDSForPratica.getMudeTFascicolo().getId();
		    	
    			List<MudeTContatto> contactProcuratore = mudeTContattoRepository.findRoleByIdIstanza(istanza.getId(), new String[] { "PRC" });
    			if(contactProcuratore.size() > 0)
    				mudeopenInfo.procuratore += ", " + ((contactProcuratore.get(0).getNome() == null? "" : contactProcuratore.get(0).getNome()) + " " 
    											+ (contactProcuratore.get(0).getCognome() == null? "" : contactProcuratore.get(0).getCognome()) 
    											+ (contactProcuratore.get(0).getRagioneSociale() == null? "" : contactProcuratore.get(0).getRagioneSociale())).trim();
		    	
		    	String intervento = mudeTIstanzaRepository.retrieveDSTitoloIntervento(istanza.getId());
		    	if(intervento != null && !"~".equals(intervento)) {
			    	mudeopenInfo.titoloIntervento = intervento.split("~")[0];
			    	mudeopenInfo.descrizioneIntervento = intervento.split("~")[1];
			    	mudeopenInfo.tipoIstruttoria = "PA".equals(mudeTPraticaCosmo.getTipoControllo()) ? "presa d’atto" : "controllo formale";
		    	}
		    	
		    	String metadati;
		    	if(isControlloCampioneSelected)
		    		metadati = new ObjectMapper().writeValueAsString(getMetadatiControlloCampione(istanza, 
																									mudeTPraticaCosmo, 
																									mudeopenInfo.urlIstanzaBO, 
																									mudeopenInfo.urlIstanzaBODetail, 
																									mudeopenInfo.titoloIntervento,
																									isControlloCampioneSelected));
		    	else if("DENUNCIA-SISMICA".equals(istanza.getTipoIstanza().getCodice()))
		    		metadati = new ObjectMapper().writeValueAsString(getMetadati(istanza, 
																					mudeTPraticaCosmo, 
																					mudeopenInfo.urlIstanzaBO, 
																					mudeopenInfo.urlIstanzaBODetail, 
																					mudeopenInfo.titoloIntervento,
																					isControlloCampioneSelected));
		    	else // isTrasmissioneDocumenti / "FIL-DS" ....
		    		metadati = new ObjectMapper().writeValueAsString(getMetadatiFil(istanza, 
		    																		istanzaRiferimento,
																					mudeTPraticaCosmo, 
																					mudeopenInfo.urlIstanzaBO, 
																					mudeopenInfo.urlIstanzaBODetail,
																					mudeopenInfo.titoloIntervento,
																					isControlloCampioneSelected));
		    	
		    	String comune = istanza.getComune() == null? "" : (istanza.getComune().getDenomComune()+" "); 
		    		
	    		mudeCosmoManager.insertNewPractice(codiceIstanza,
	    											mudeTPraticaCosmo, 
										    		ipaEnte, 
										    		cfCreatore, 
													mudeCProprietaRepository.getValueByName("COSMO_TIPO_PRATICA_" + (isTrasmissioneDocumenti? "TRASMISSIONE_DOCUMENTI" : itype + (isControlloCampioneSelected?"_CC":"") + (isTrasmissioneDocumenti? "_TD": "")), "trasmissione-documenti"), 
													getOggettoDescription(mudeTPraticaCosmo, istanza, isDsVariante,
																			mudeopenInfo, comune),
													metadati,
													getSummaryByTemplate(mudeTPraticaCosmo, istanza, intestatario, mudeopenInfo, isControlloCampioneSelected));
	    		
			    mudeTPraticaCosmo.setRetries(0);
			    mudeTPraticaCosmo.setTrasmissDoc(isTrasmissioneDocumenti && sendAllegatiList);
			    mudeTPraticaCosmo.setIdIstanza(idIstanza);
			    mudeTPraticaCosmo.setCodiceStatoCosmo(StatiProcessoCosmoEnum.CREATA.toString());
			    mudeTPraticaCosmo = mudeTPraticaCosmoRepository.saveDAO(mudeTPraticaCosmo);
		    }
		    
		    /////////////////////////////////////////////////////////////////////
		    // STEP 2 ((----->ALLEGATI): upload all attachments
		    
		    if(!isControlloCampioneSelected 
		    		&& StatiProcessoCosmoEnum.CREATA.toString().equals(mudeTPraticaCosmo.getCodiceStatoCosmo())) {
		    	EsitoCreazioneDocumentiFruitore masterDoc = null;

			    // upload PDF istanza
		    	FileUploadResult fileDocUploadResult = mudeCosmoManager.uploadDocument(mudeTPraticaCosmo,
		    																			indexManager.getFileByUuid(istanza.getUuidFileIndex()),
		    																			istanza.getFilename(),
		    																			"application/pkcs7-mime");

			    ArrayList<String> uidDocuments = new ArrayList<String>();
			    ArrayList<String> fnDocuments = new ArrayList<String>();
			    ArrayList<String> fnInternalID = new ArrayList<String>();
			    
			    fnDocuments.add(istanza.getFilename());
			    uidDocuments.add(fileDocUploadResult.getUploadUUID());
			    fnInternalID.add(istanzaRiferimento != null? istanza.getCodiceIstanza() : mudeTPraticaCosmo.getIdDocCosmo());

				masterDoc = mudeCosmoManager.assignDocumentToPractice(codiceIstanza,
										mudeTPraticaCosmo, 
			    						ipaEnte, 
			    						uidDocuments, 
			    						fnDocuments,
			    						mudeCProprietaRepository.getValueByNameAndInstanceSP("COSMO_CODICE_TIPO_ALLEGATO_IST_" + itype + (isDsVariante?"_VAR":"") + (isTrasmissioneDocumenti? "_TD": ""), idIstanza, ""),
			    						fnInternalID,
			    						null); 
				
			    //mudeTPraticaCosmo.setIdDocCosmo(masterDoc.getEsiti().get(0).getInput().getId());
			    mudeTPraticaCosmo = mudeTPraticaCosmoRepository.saveDAO(mudeTPraticaCosmo);
		    	
			    long delaySeconds = Long.parseLong(mudeCProprietaRepository.getValueByName("COSMO_UPLOAD_DELAY_SECONDS", "0"));
			    	
			    List<String> attachUIDs = new ArrayList<>();
			    List<String> internalIDs = new ArrayList<>();
			    List<String> filenames = new ArrayList<>();
			    for(MudeTAllegatoSlim mudeTAllegatoSlim : mudeTAllegatoSlimRepository.findAllByIstanza(idIstanza)) {
			        File file = indexManager.getFileByUuid(mudeTAllegatoSlim.getFileUID());
			    	if(file == null) {
			    		mudeTPraticaCosmo.setJsonResponse(StringUtils.defaultString(mudeTPraticaCosmo.getJsonResponse(), "") + "["+(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))+"] " + "FileUploadResult JSON: " + 
			    				"NO INDEX FILE... SKIPED:" + mudeTAllegatoSlim.getId() + "\r\n\r\n"); 
			    		continue; 
			    	}
			    	
			    	if(delaySeconds > 0)
			    		Thread.sleep(delaySeconds * 1000);
			    	
			    	FileUploadResult fileUploadResult = mudeCosmoManager.uploadDocument(mudeTPraticaCosmo,
			    																		file, 
			    																		mudeTAllegatoSlim.getNomeFileAllegato(),  
			    																		mudeTAllegatoSlim.getMimeType());
			    	
			    	filenames.add(mudeTAllegatoSlim.getNomeFileAllegato());
			    	internalIDs.add(""+mudeTAllegatoSlim.getId());
			    	attachUIDs.add(fileUploadResult.getUploadUUID());
			    	
			    	try { file.delete(); } catch (Exception e) { }
			    }
			    
			    // STEP 3 (ASSEGNATA): link all sent attachments to the previously created practice
			    if(attachUIDs.size() > 0)
			    	mudeCosmoManager.assignDocumentToPractice(codiceIstanza,
			    							mudeTPraticaCosmo, 
			    							ipaEnte, 
			    							attachUIDs, 
			    							filenames,
			    							mudeCProprietaRepository.getValueByNameAndInstanceSP("COSMO_CODICE_TIPO_ALLEGATO_" + itype + (isDsVariante?"_VAR":"") + (isTrasmissioneDocumenti? "_TD": ""), idIstanza, ""), 
			    							internalIDs,
			    							istanzaRiferimento != null? istanza.getCodiceIstanza() : mudeTPraticaCosmo.getIdDocCosmo()); 

			    mudeTPraticaCosmo.setRetries(0);
			    mudeTPraticaCosmo.setCodiceStatoCosmo(StatiProcessoCosmoEnum.ALLEGATO.toString());
			    mudeTPraticaCosmo = mudeTPraticaCosmoRepository.saveDAO(mudeTPraticaCosmo);
		    }
		    
		    /////////////////////////////////////////////////////////////////////
		    // STEP 3 ((----->IN_PROCESSO): create COSM process
		    if(isCosmoCreationProcess 
		    		&& (StatiProcessoCosmoEnum.ALLEGATO.toString().equals(mudeTPraticaCosmo.getCodiceStatoCosmo())
		    				|| (isControlloCampioneSelected && StatiProcessoCosmoEnum.CREATA.toString().equals(mudeTPraticaCosmo.getCodiceStatoCosmo()))
		    			)) {
			    mudeCosmoManager.processPractice(codiceIstanza, mudeTPraticaCosmo, ipaEnte);

			    if(isTrasmissioneDocumenti 
			    		&& sendAllegatiList
			    		&& !"AEOS".equals(lastDSForPratica.getIdFonte())
			    		&& mudeRIstanzaStatoRepository.findStatoByIstanza(lastDSForPratica.getId()).getStatoIstanza().getLivello() < 201 /* ACC */)
			    	mudeTPraticaCosmo.setCodiceStatoCosmo(StatiProcessoCosmoEnum.INVIO_VARIBILI.toString());
			    else
			    	mudeTPraticaCosmo.setCodiceStatoCosmo(getNextProcessStatus(istanzaRiferimento, 
														    					mudeTPraticaCosmoParent, isControlloCampioneSelected,
														    					isDsVariante, 
														    					isTrasmissioneDocumenti));
			    mudeTPraticaCosmo.setRetries(0);
			    mudeTPraticaCosmo = mudeTPraticaCosmoRepository.saveDAO(mudeTPraticaCosmo);
		    }
		    
		    /////////////////////////////////////////////////////////////////////
		    // STEP 3.1 ((-----TD invio docuenti aggiuntivi): 
		    if(!"AEOS".equals(lastDSForPratica.getIdFonte()) && StatiProcessoCosmoEnum.INVIO_VARIBILI.toString().equals(mudeTPraticaCosmo.getCodiceStatoCosmo())
		    		&& mudeRIstanzaStatoRepository.findStatoByIstanza(lastDSForPratica.getId()).getStatoIstanza().getLivello() < 201 /* ACC */
		    		&& mudeCosmoManager.sendAttachListToPractice(lastDSForPratica.getCodiceIstanza(), 
		    												mudeTPraticaCosmo, 
		    												(List<String>)getAlleagatiList(istanza, true))) {
			    mudeTPraticaCosmo.setRetries(0);
			    mudeTPraticaCosmo.setCodiceStatoCosmo(getNextProcessStatus(istanzaRiferimento, 
														    					mudeTPraticaCosmoParent, isControlloCampioneSelected,
														    					isDsVariante, 
														    					isTrasmissioneDocumenti));
			    mudeTPraticaCosmo = mudeTPraticaCosmoRepository.saveDAO(mudeTPraticaCosmo);
		    }

		    /////////////////////////////////////////////////////////////////////
		    // STEP 4 ((----->SEGNALATA)
		    String signalCode = mudeCProprietaRepository.getValueByNameAndInstanceSP("COSMO_SEGNALE_" + itype + (isControlloCampioneSelected || (mudeTPraticaCosmoParent != null && mudeTPraticaCosmoParent.getCcSelezionato())?"_CC":""), idIstanza, "");
		    if((!isCosmoCreationProcess && !StringUtils.isBlank(signalCode))
		    		&& mudeCosmoManager.sendSignalToPractice(codiceIstanza, 
		    												mudeTPraticaCosmo, 
		    												ipaEnte, 
		    												signalCode, 
		    												(String)getAlleagatiList(istanza, false),
		    												true) != null) {
			    mudeTPraticaCosmo.setRetries(0);
			    mudeTPraticaCosmo.setCodiceStatoCosmo(StatiProcessoCosmoEnum.SEGNALATA.toString());
			    mudeTPraticaCosmo = mudeTPraticaCosmoRepository.saveDAO(mudeTPraticaCosmo);
		    }
		    
		    /////////////////////////////////////////////////////////////////////
		    // STEP 5 ((----->REL_PRATICHE): 
		    if(StatiProcessoCosmoEnum.DA_COLLEGARE.toString().equals(mudeTPraticaCosmo.getCodiceStatoCosmo())) {
			    if((mudeTPraticaCosmoParent != null || isControlloCampioneSelected))
			    	mudeCosmoManager.linkPractiche(codiceIstanza, isControlloCampioneSelected? codiceIstanza : istanzaRiferimento.getCodiceIstanza(), mudeTPraticaCosmo, ipaEnte);
			    
			    mudeTPraticaCosmo.setRetries(0);
			    mudeTPraticaCosmo.setCodiceStatoCosmo(StatiProcessoCosmoEnum.IN_PROCESSO.toString());
			    mudeTPraticaCosmo = mudeTPraticaCosmoRepository.saveDAO(mudeTPraticaCosmo);
		    }

			/////////////////////////////////////////////////////////////////////
			// STEP 6 ((----->PROPAGA_SEGNALE):
			if(StatiProcessoCosmoEnum.PROPAGA_SEGNALE.toString().equals(mudeTPraticaCosmo.getCodiceStatoCosmo())
						&& propagateSignalToRelatedInstances(mudeTPraticaCosmo.getIdIstanza())) {
				mudeTPraticaCosmo.setRetries(0);
				mudeTPraticaCosmo.setCodiceStatoCosmo(StatiProcessoCosmoEnum.IN_PROCESSO.toString());
				mudeTPraticaCosmo = mudeTPraticaCosmoRepository.saveDAO(mudeTPraticaCosmo);
			}

			logger.info(String.format("[%s::%s] COSMO OK: " + dsPracticeID, getClass().getSimpleName(), Thread.currentThread().getStackTrace()[0].getMethodName()));
			return true;
			
		} catch (Exception e) {
			logger.error(String.format("[%s::%s] COSMO ERROR " + dsPracticeID, getClass().getSimpleName(), Thread.currentThread().getStackTrace()[0].getMethodName()), e);
			
	    	mudeTPraticaCosmo.setJsonResponse(StringUtils.defaultString(mudeTPraticaCosmo.getJsonResponse(), "") + 
	    			"["+(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))+"] " + "EXCEPTION: " + e.getMessage() + "\r\n\r\n");
	    	
		    mudeTPraticaCosmo = mudeTPraticaCosmoRepository.saveDAO(mudeTPraticaCosmo);
		}
    	
		return false;
	}

	private String getOggettoDescription(MudeTPraticaCosmo mudeTPraticaCosmo, 
										final MudeTIstanza istanza,
										boolean isDsVariante, 
										MudeopenInfo mudeopenInfo, 
										String comune) {
		// Numero pratica – comune – Denuncia Sismica – titolo intervento (la modifica consiste nel sostituire la descriozione estesa del tipo istanza con solo la dicitura Denuncia Sismica)
		return getMaxLength(mudeTPraticaCosmo.getNumeroPratica() 
							+ " - " 
							+ comune 
							+ ("DENUNCIA-SISMICA".equals(istanza.getTipoIstanza().getCodice())? " – Denuncia Sismica" : istanza.getTipoIstanza().getDescrizioneEstesa()) 
							+ (StringUtils.isBlank(mudeopenInfo.titoloIntervento)? "" : " - " + mudeopenInfo.titoloIntervento) 
							+ (isDsVariante? " - Variante" : "")
						, 200);
	}

	private String getNextProcessStatus(final MudeTIstanza istanzaRiferimento,
			final MudeTPraticaCosmo mudeTPraticaCosmoParent, boolean isControlloCampioneSelected, boolean isDsVariante,
			boolean isTrasmissioneDocumenti) {
		return (!isTrasmissioneDocumenti && !isDsVariante && !isControlloCampioneSelected && mudeTPraticaCosmoParent == null) 
										  	|| (istanzaRiferimento != null && !"FO".equals(istanzaRiferimento.getIdFonte())) 
										  		? StatiProcessoCosmoEnum.IN_PROCESSO.toString() 
										  				: StatiProcessoCosmoEnum.DA_COLLEGARE.toString();
	}
	
	private MudeTPratica getMudeTPratica(MudeTIstanza istanza) {
		MudeTPratica mudeTPratica = mudeTPraticaRepository.findByIdIstanza(istanza.getId());
		if(mudeTPratica != null)
			return mudeTPratica;
		else if(istanza.getIdIstanzaRiferimento() != null)
			return getMudeTPratica(mudeTIstanzaRepository.findOne(istanza.getIdIstanzaRiferimento()));
		
		return null;
	}
	
	private Object getAlleagatiList(MudeTIstanza istanza, boolean isCosmoCreationProcess) throws Exception {
		List<String> list = setAllegati(istanza, isCosmoCreationProcess);
		if(isCosmoCreationProcess)
			return list;

		if(list != null && list.size() > 0)
			return new ObjectMapper().writeValueAsString(list);
		
		return null;
	}

	private String getSummaryByTemplate(MudeTPraticaCosmo mudeTPraticaCosmo, final MudeTIstanza istanza,
										MudeTContatto intestatario, MudeopenInfo mudeopenInfo, 
										boolean isControlloCampioneSelected) {
		HashMap<String, Object> contextMap = new HashMap();
		IstanzaVO istanzaVO = istanzaEntityMapper.mapEntityToVO(istanza, null);

		if(intestatario != null) {
			if(TipoContatto.PG == intestatario.getTipoContatto())
				istanzaVO.getIntestatario().setNome(intestatario.getRagioneSociale());
			else
				istanzaVO.getIntestatario().setNome(intestatario.getNome() + " " + intestatario.getCognome());
			
			istanzaVO.getIntestatario().setProprietarioRubrica("Ragione sociale intestatario");
		}

    	mudeopenInfo.cointestatari = "";
		for(MudeTContatto contact: mudeTContattoRepository.findRoleByIdIstanza(istanza.getId(), new String[] { "CO" })) {
			mudeopenInfo.cointestatari += ", " + ((contact.getNome() == null? "" : contact.getNome()) 
					+ " " + (contact.getCognome() == null? "" : contact.getCognome()) 
					+ (contact.getRagioneSociale() == null? "" : contact.getRagioneSociale())).trim();
		}
		
    	if(mudeopenInfo.cointestatari.length() > 0)
    		mudeopenInfo.cointestatari = mudeopenInfo.cointestatari.substring(2);
		
		contextMap.put("ex_ISTANZA", istanzaVO);
		contextMap.put("ex_MUDE_INFO", mudeopenInfo);
		contextMap.put("ex_PRATICA_COSMO", mudeTPraticaCosmo);
		
		return BasicTransformer.fillInMergeFields(mudeCProprietaRepository.getValueByName("COSMO_RIASSUNTO_HTML_" + istanza.getTipoIstanza().getCodice().replace("-", "_"), "") + (isControlloCampioneSelected?"_CC":""), contextMap);
	}

	private void mapSubject(MudeTContatto contact, IntestatarioVO subject2set, Long idIstanza) {
		if(contact == null) return;
			
		subject2set.setTipo(contact.getTipoContatto().getValue().toUpperCase());
		
		if("PF".equalsIgnoreCase(contact.getTipoContatto().getValue())) {
			subject2set.setCognome(contact.getCognome());
			subject2set.setNome(contact.getNome());
			subject2set.setEmail(contact.getMail());
			subject2set.setCf(contact.getCf());
		}
		else {
			subject2set.setRagioneSociale(contact.getRagioneSociale());
			subject2set.setPec(contact.getPec());
			subject2set.setEmail(contact.getMail());
			subject2set.setPiva(contact.getPivaComunitaria() != null? contact.getPivaComunitaria() : contact.getPiva());
			
			MudeTContatto legaleRappresentente = null;
			for(MudeTContatto rt: mudeTContattoRepository.findRoleByIdIstanza(idIstanza, new String[] { "RT" })) {
				String representedArray = mudeTContattoRepository.findPresentedByRT(idIstanza, rt.getGuid());
				if(representedArray != null && representedArray.indexOf(contact.getGuid()) > -1) {
					legaleRappresentente = rt;
					break;
				}
			}

            if(legaleRappresentente != null) {
    			subject2set.setCognomeRappresentante(legaleRappresentente.getCognome());
    			subject2set.setNomeRappresentante(legaleRappresentente.getNome());
    			subject2set.setEmailRappresentante(legaleRappresentente.getMail());
            }
			
			/*
			 * RT from contact
            Optional<MudeRPfPg> legaleRappresentanteOpt = mudeRPfPgRepository.retrieveLawPresenter(contact.getId());
            if(legaleRappresentanteOpt.isPresent()) {
                MudeTContatto legaleRappresentente = legaleRappresentanteOpt.get().getSoggettoPf();
    			subject2set.setCognomeRappresentante(legaleRappresentente.getCognome());
    			subject2set.setNomeRappresentante(legaleRappresentente.getNome());
    			subject2set.setEmailRappresentante(legaleRappresentente.getMail());
            }
            */
		}
	}
	
	private void mapDeleganti(MudeTContatto contact, DelegatoVO delegatiVO) {
		delegatiVO.setCognomeDelegato(contact.getCognome());
		delegatiVO.setNomeDelegato(contact.getNome());
		delegatiVO.setPec(contact.getPec());
		delegatiVO.setEmailDelegato(contact.getMail());
	}
	
	private MetadatiDenunciaSismicaVO getMetadati(final MudeTIstanza istanza, 
									final MudeTPraticaCosmo mudeTPraticaCosmo,
									final String urlIstanzaBO, 
									final String urlIstanzaBODetail,
									String titoloIntevento,
									boolean isControlloCampioneSelected) throws Exception {
    	MetadatiDenunciaSismicaVO metadatiVO = new MetadatiDenunciaSismicaVO();
    	
		MudeTEnte mudeTEnte = mudeTEnteRepository.getUTR(istanza.getComune().getIdComune(), 
	    		istanza.getTipoIstanza().getCodice(),
	    		istanza.getSpeciePratica() == null ? "getnulls" : istanza.getSpeciePratica().getCodice());
	    if(mudeTEnte != null)
	    	metadatiVO.setIntestazione(mudeTEnte.getIntestazione());
		
		String denomComune = setComune(istanza, metadatiVO);
		
		setIndirizzo(istanza, metadatiVO);
		MudeTContatto intContact = setIntestatario(istanza.getId(), metadatiVO);
		
		setOggettoFascicolo(istanza, denomComune, metadatiVO, intContact, titoloIntevento + " " + mudeTPraticaCosmo.getNumeroPratica());

		setTitolario(istanza, metadatiVO);
		setSerieFascicoli(istanza, metadatiVO);
		
		if("INT-DOC".equals(istanza.getTipoIstanza().getCodice()))
			metadatiVO.setCodiceIstanzaRiferimento(istanza.getIdIstanzaRiferimento() == null?
				null
				: mudeTIstanzaSlimRepository.findOne(istanza.getIdIstanzaRiferimento()).getCodiceIstanza());
		setIstanzaRiferimento(istanza, metadatiVO);

		//removed: setCointestatari(istanza, metadatiVO);
		//removed: setDelegani(istanza, metadatiVO);
		//removed: setPM(istanza, metadatiVO);
		
		/*
			4021  se si tratta di  Denuncia lavori di costruzione in zona sismica
			4022 se si tratta di  Denuncia di Variante
		*/
		metadatiVO.setNomeModello(istanza.getSpeciePratica().getFlagVariante() ? 4022L : 4021L);
		
		metadatiVO.setUrlBo(urlIstanzaBO);
		metadatiVO.setUrlBoDetails(urlIstanzaBODetail);
	    setEnte(istanza, metadatiVO);	    		
		
		//metadatiVO.setNumeroFascicolo((new SimpleDateFormat("yyyy").format(new Date()) + mudeTFascicoloRepository.getNextIdNumeroFascicoloCOSMO()) + "C");
		metadatiVO.setNumeroFascicolo(mudeTPraticaCosmo.getIdDocCosmo());
		
		setOpere(istanza, metadatiVO);
		metadatiVO.setElencoAllegati(setAllegati(istanza, false));
		
		// 1 = Presa d'atto, 2 = Controllo Formale (dipende da parametri inseriti nella REL ILL)
		metadatiVO.setTipoIstruttoria("PA".equals(mudeTPraticaCosmo.getTipoControllo()) ? 2 : 1);

		setDestinatariFields(istanza, metadatiVO, isControlloCampioneSelected);
		
		setICE(istanza, metadatiVO);
		setAssegnatari(mudeTPraticaCosmo, metadatiVO);

		metadatiVO.setGruppoUtr(mudeCProprietaEnteRepository.getParamValue("COSMO_GRUPPO_UTR", istanza.getTipoIstanza().getCodice(), istanza.getComune().getIdComune(), "", istanza.getSpeciePratica() == null? "" : istanza.getSpeciePratica().getCodice(), "ufficio-tecnico"));
		
		return metadatiVO;		
	}

	private void setICE(final MudeTIstanza istanza, BaseMetadatiVO metadatiVO) {
		MudeTPratica mudeTPratica = getMudeTPratica(istanza);
		if(mudeTPratica != null)
			metadatiVO.setIce(mudeTPratica.getIce());
	}

	private void setAssegnatari(final MudeTPraticaCosmo mudeTPraticaCosmo, BaseMetadatiVO metadatiVO) throws Exception {
		String ssonAssegnatari = mudeTPraticaCosmoRepository.getJsonAssegnatari(mudeTPraticaCosmo.getNumeroPratica());

		if(ssonAssegnatari != null) {
			AssegnazioneRequest assegnazioni = new ObjectMapper().readValue(ssonAssegnatari, new TypeReference<AssegnazioneRequest>() {});			
			metadatiVO.setAssegnatari(assegnazioni.getUsers());
		}
	}


	private void setSerieFascicoli(final MudeTIstanza istanza, BaseMetadatiVO metadatiVO) {
		String serie_fascicoli = mudeDComuneRepository.getSerieFascicoliById(istanza.getComune().getIdComune());
		if(serie_fascicoli == null)
			serie_fascicoli = mudeCProprietaEnteRepository.getParamValue("COSMO_SERIE_FASCICOLI", istanza.getTipoIstanza().getCodice(), istanza.getComune().getIdComune(), "", istanza.getSpeciePratica() == null? "" : istanza.getSpeciePratica().getCodice(), "");
		metadatiVO.setSerieFascicoli(serie_fascicoli);
	}

	private MetadatiControlloCampioneVO getMetadatiControlloCampione(final MudeTIstanza istanza, 
									final MudeTPraticaCosmo mudeTPraticaCosmo,
									final String urlIstanzaBO, 
									final String urlIstanzaBODetail,
									String titoloIntevento,
									boolean isControlloCampioneSelected) throws Exception {
		MetadatiControlloCampioneVO metadatiVO = new MetadatiControlloCampioneVO();
    	
		String denomComune = setComune(istanza, metadatiVO);
		
		setIndirizzo(istanza, metadatiVO);
		MudeTContatto intContact = setIntestatario(istanza.getId(), metadatiVO);
		
		setOggettoFascicolo(mudeTIstanzaRepository.retrieveMainDenunciaSismica(istanza.getId()), denomComune, metadatiVO, intContact, titoloIntevento + " " + mudeTPraticaCosmo.getNumeroPratica());
		
		setTitolario(istanza, metadatiVO);
		setSerieFascicoli(istanza, metadatiVO);
		
		if("INT-DOC".equals(istanza.getTipoIstanza().getCodice()))
			metadatiVO.setCodiceIstanzaRiferimento(istanza.getIdIstanzaRiferimento() == null?
				null
				: mudeTIstanzaSlimRepository.findOne(istanza.getIdIstanzaRiferimento()).getCodiceIstanza());
		
		metadatiVO.setUrlBo(urlIstanzaBO);
		metadatiVO.setUrlBoDetails(urlIstanzaBODetail);

		MudeTEnte mudeTEnte = setEnte(istanza, metadatiVO);
		metadatiVO.setIntestazione(mudeTEnte.getIntestazione());
		metadatiVO.setResponsabileProcedimento(mudeTEnte.getResponsabileEnte());
	    
		//setSoggettoFascicolo(istanza, denomComune, metadatiVO, intContact, titoloIntevento);
		metadatiVO.setNumeroFascicolo(mudeTPraticaCosmo.getIdDocCosmo());

		MudeTPratica mudeTPratica = mudeTPraticaRepository.findByIdIstanza(istanza.getId());
		if(mudeTPratica != null) {
			metadatiVO.setNumeroPraticaMUDE(mudeTPratica.getNumeroPratica());
			metadatiVO.setAnnoPraticaMUDE(""+mudeTPratica.getAnnoPratica());
		}

		setICE(istanza, metadatiVO);
		setAssegnatari(mudeTPraticaCosmo, metadatiVO);
		
		//metadatiVO.setTitoloIntervento(mudeTIstanzaRepository.retrieveDSTitoloIntervento(istanza.getId()).split("~")[0]);
		metadatiVO.setTitoloIntervento(titoloIntevento);

		metadatiVO.setProtocolloDenuncia(istanza.getNumeroProtocollo());
		metadatiVO.setDataProtocolloDenuncia(new SimpleDateFormat("dd/MM/yyyy").format(istanza.getDataProtocollo()));
		
		MudeTIstanzaSlim mudeTIstanzaSlim = mudeTIstanzaSlimRepository.getIntDocFromIdIstanzaDS(istanza.getId());
		if(mudeTIstanzaSlim != null) {
			metadatiVO.setProtocolloIntegrazione(mudeTIstanzaSlim.getNumeroProtocollo());
			metadatiVO.setDataProtocolloIntegrazione(new SimpleDateFormat("dd/MM/yyyy").format(istanza.getDataProtocollo()));
		}

		setDestinatariFields(istanza, metadatiVO, isControlloCampioneSelected);
		
		metadatiVO.setGruppoUtr(mudeCProprietaEnteRepository.getParamValue("COSMO_CC_GRUPPO_UTR", istanza.getTipoIstanza().getCodice(), istanza.getComune().getIdComune(), "", istanza.getSpeciePratica() == null? "" : istanza.getSpeciePratica().getCodice(), "ufficio-tecnico"));
		
		return metadatiVO;		
	}

	private MudeTContatto setIntestatario(Long idIstanza, BaseMetadatiVO metadatiVO) {
		MudeTIstanzaSlim mudeTIstanzaSlim;
		
		// set "intestario"
		while(idIstanza != null && (mudeTIstanzaSlim = mudeTIstanzaSlimRepository.findOne(idIstanza)) != null) {
			MudeTContatto intContact = mudeTContattoRepository.findIntestatarioByIdIstanza(mudeTIstanzaSlim.getIdIstanza());
			
			if(intContact != null) {
				mapSubject(intContact, metadatiVO, mudeTIstanzaSlim.getIdIstanza());
				return intContact;
			}
			
			idIstanza = mudeTIstanzaSlim.getIdIstanzaRiferimento();
		}
		
		return null;
	}

	private MetadatiFineLavoriVO getMetadatiFil(final MudeTIstanza istanzaFil, 
									final MudeTIstanza istanzaRif,
									final MudeTPraticaCosmo mudeTPraticaCosmo,
									final String urlIstanzaBO, 
									final String urlIstanzaBODetail,
									final String titoloIntevento,
									boolean isControlloCampioneSelected) throws Exception {
    	MetadatiFineLavoriVO metadatiVO = new MetadatiFineLavoriVO();
    	
		MudeTEnte mudeTEnte = mudeTEnteRepository.getUTR(istanzaRif.getComune().getIdComune(), 
	    		istanzaRif.getTipoIstanza().getCodice(),
	    		istanzaRif.getSpeciePratica() == null ? "getnulls" : istanzaRif.getSpeciePratica().getCodice());
		
		String denomComune = setComune(istanzaRif, metadatiVO);
		MudeTContatto intContact = setIntestatario(istanzaFil.getId(), metadatiVO);

		setOggettoFascicolo(mudeTIstanzaRepository.retrieveMainDenunciaSismica(istanzaRif.getId()), denomComune, metadatiVO, intContact, titoloIntevento + " " + mudeTPraticaCosmo.getNumeroPratica());
		
		setTitolario(istanzaRif, metadatiVO);
		setSerieFascicoli(istanzaRif, metadatiVO);
		
		metadatiVO.setUrlBo(urlIstanzaBO);
		metadatiVO.setUrlBoDetails(urlIstanzaBODetail);
		
		//metadatiVO.setNumeroFascicolo((new SimpleDateFormat("yyyy").format(new Date()) + mudeTFascicoloRepository.getNextIdNumeroFascicoloCOSMO()) + "C");
		metadatiVO.setNumeroFascicolo(mudeTPraticaCosmo.getIdDocCosmo());
		
		setOpere(istanzaRif, metadatiVO);
		setPM(istanzaRif, metadatiVO);

		MudeTPratica mudeTPratica = mudeTPraticaRepository.findByIdIstanza(istanzaRif.getId());
		if(mudeTPratica != null) {
			metadatiVO.setNumeroPraticaMUDE(mudeTPratica.getNumeroPratica());
			metadatiVO.setAnnoPraticaMUDE(""+mudeTPratica.getAnnoPratica());
		}

		setICE(istanzaRif, metadatiVO);
		setAssegnatari(mudeTPraticaCosmo, metadatiVO);
		setDestinatariFields(istanzaRif, metadatiVO, isControlloCampioneSelected);
		
		metadatiVO.setGruppoUtr(mudeCProprietaEnteRepository.getParamValue("COSMO_GRUPPO_UTR", istanzaRif.getTipoIstanza().getCodice(), istanzaRif.getComune().getIdComune(), "", istanzaRif.getSpeciePratica() == null? "" : istanzaRif.getSpeciePratica().getCodice(), "ufficio-tecnico"));
		
		return metadatiVO;		
	}

	private void setPM(final MudeTIstanza istanza, MetadatiFineLavoriVO metadatiVO) {
		MudeTContatto pm = mudeTContattoRepository.findProfessionistaByIdIstanza(istanza.getId());
		if(pm != null) {
			metadatiVO.setCognomePm(pm.getCognome());
			metadatiVO.setNomePm(pm.getNome());
			metadatiVO.setPecPm(pm.getPec());
			metadatiVO.setEmailPm(pm.getMail());
		}
	}
	
	private void setDestinatariFields(final MudeTIstanza istanza, 
												BaseMetadatiVO metadatiVO,
												boolean isControlloCampioneSelected) {
		List<String> htmlSubjects = getSubjectsByTemplate(istanza, metadatiVO, isControlloCampioneSelected);
		metadatiVO.setDestinatari(htmlSubjects);
		metadatiVO.setDestinatario(String.join("<br>", htmlSubjects));
		
		List<String> comuneList = getComuniByTemplate(istanza, metadatiVO, true);
		metadatiVO.setDestinatariPC(comuneList);
		metadatiVO.setDestinatarioPC(istanza.getComune().getDenomComune());
	}

	private String setComune(final MudeTIstanza istanza, BaseMetadatiVO metadatiVO) {
		String denomComune = "";
		
		if(istanza.getComune() != null) {
			denomComune = istanza.getComune().getDenomComune();
			metadatiVO.setDenomComune(denomComune);
			metadatiVO.setEmailComune(istanza.getComune().getEmail());
		}

		return denomComune;
	}
	
	private void setTitolario(final MudeTIstanza istanza, BaseMetadatiVO metadatiVO) {
		String titolario = mudeCProprietaEnteRepository.getParamValue("COSMO_TITOLARIO", istanza.getTipoIstanza().getCodice(), istanza.getComune().getIdComune(), "", istanza.getSpeciePratica() != null? istanza.getSpeciePratica().getCodice() : "", null);
		metadatiVO.setTitolario(titolario != null ? 
					titolario 
					: mudeCProprietaRepository.getValueByName("COSMO_TITOLARIO", "12.100.50"));
	}
	
	private void setIndirizzo(final MudeTIstanza istanza, BaseMetadatiVO metadatiVO) {
		MudeTIndirizzo indirizzo = istanza.getIndirizzo();
		if(indirizzo != null) {
			if(indirizzo.getIdDug() != null)
				metadatiVO.setSedime(mudeDDugRepository.getDenominazione(indirizzo.getIdDug()));
			
			metadatiVO.setIndirizzo(indirizzo.getIndirizzo());
			metadatiVO.setCivico(indirizzo.getNumeroCivico());
		}
	}
	
	private void setIstanzaRiferimento(final MudeTIstanza istanza, MetadatiDenunciaSismicaVO metadatiVO) {
		String istRefInfo = mudeTIstanzaRepository.retrieveDSReferenceInstance(istanza.getId());
		if(istRefInfo != null) {
			metadatiVO.setDescTitoloAbilitativo(istRefInfo.split("~")[0]);
			metadatiVO.setDataRegTitoloAbilitativo(istRefInfo.split("~")[1]);
		}
	}
	
	/*
	private void setCointestatari(final MudeTIstanza istanza, MetadatiDenunciaSismicaVO metadatiVO) {
		List<CointestarioVO> subject2set = new ArrayList<>();
		for(MudeTContatto contact: mudeTContattoRepository.findRoleByIdIstanza(istanza.getId(), new String[] { "CO" })) {
			CointestarioVO cointestarioVO = new CointestarioVO();
			subject2set.add(cointestarioVO);
			mapSubject(contact, cointestarioVO, istanza.getId());
		}

		metadatiVO.setCointestatari(subject2set);
	}
	
	private void setDelegani(final MudeTIstanza istanza, MetadatiDenunciaSismicaVO metadatiVO) {
		List<DelegatoVO> delegati2set = new ArrayList<>();
		for(MudeTContatto contact : mudeTContattoRepository.findRoleByIdIstanza(istanza.getId(), new String[] { "DP" })) {
			DelegatoVO delegatoVO = new DelegatoVO();
			delegati2set.add(delegatoVO); 
			mapDeleganti(contact, delegatoVO);
		}

		metadatiVO.setDelegati(delegati2set);
	}
	*/

	private List<String> setAllegati(final MudeTIstanza istanza, boolean getAllButDS) throws Exception {
		List<String> allegati = new ArrayList<>();
		
		List<MudeTAllegatoSlim> mudeTAllegatoSlimList;
		if(getAllButDS)
			mudeTAllegatoSlimList = mudeTAllegatoSlimRepository.findAllAllegatiForPraticaCosmoButDS(istanza.getId());
		else
			mudeTAllegatoSlimList = mudeTAllegatoSlimRepository.findAllByIstanza(istanza.getId());
		
		Map<Long, Boolean> idIstanze = new Hashtable<>();
		for(MudeTAllegatoSlim mudeTAllegatoSlim : mudeTAllegatoSlimList) {
			if(!idIstanze.containsKey(mudeTAllegatoSlim.getIdIstanza())) {
				MudeTIstanza istanzaAll = mudeTIstanzaRepository.findOne(mudeTAllegatoSlim.getIdIstanza());
				
		        File fileIstanza = istanzaAll.getUuidFileIndex() == null? null : indexManager.getFileByUuid(istanzaAll.getUuidFileIndex());
				if(fileIstanza != null)
			    	addFile(allegati, istanzaAll.getFilename(), fileIstanza);
				
				idIstanze.put(mudeTAllegatoSlim.getIdIstanza(), Boolean.TRUE);
			}
			
	        File file = indexManager.getFileByUuid(mudeTAllegatoSlim.getFileUID());
	    	addFile(allegati, mudeTAllegatoSlim.getNomeFileAllegato(), file);
		}
		
		return allegati;
	}

	private void addFile(List<String> allegati, String allegato, File file)
			throws IOException, NoSuchAlgorithmException, FileNotFoundException {
		if(file == null)
			return;

		String templateFile = mudeCProprietaRepository.getValueByName("COSMO_TEMPLATE_FILE", "<p><h4><strong>{{ex_FILENAME}}</strong></h4></p><p><h5>{{ex_FILEHASH}}</h5></p>");
		
		try(FileInputStream fin = new FileInputStream(file)) {
			byte[] bytes = fin.readAllBytes();
		    String hash = istanzaApiServiceHelper.calculateFileHashHex(bytes).toLowerCase();
		    
			try { file.delete(); } catch (Exception e) { }
			
			HashMap<String, Object> contextMap = new HashMap();
			contextMap.put("ex_FILENAME", allegato);
			contextMap.put("ex_FILEHASH", hash);
			
			String allegatoTml = BasicTransformer.fillInMergeFields(templateFile, contextMap);
			
			allegati.add(allegatoTml);
		}
	}
	
	private void setOpere(final MudeTIstanza istanza, Object metadatiVO) {
		String opere = "";
		for(String opera : mudeTIstanzaRepository.retrieveOpereFromIdIstanza(istanza.getId()))
			opere += (opere.length() == 0? "":", ")  + opera;

		if(metadatiVO instanceof MetadatiDenunciaSismicaVO)
			((MetadatiDenunciaSismicaVO)metadatiVO).setOpere(opere);
		if(metadatiVO instanceof MetadatiFineLavoriVO)
			((MetadatiFineLavoriVO)metadatiVO).setOpere(opere);
	}
	
	private MudeTEnte setEnte(final MudeTIstanza istanza, BaseMetadatiVO metadatiVO) {
		MudeTEnte mudeTEnte = mudeTEnteRepository.getUTR(istanza.getComune().getIdComune(), 
	    		istanza.getTipoIstanza().getCodice(),
	    		istanza.getSpeciePratica() == null ? "getnulls" : istanza.getSpeciePratica().getCodice());
	    if(mudeTEnte != null) {
	    	metadatiVO.setDescrizioneEnte(mudeTEnte.getDescrizioneEstesa());
			setIndirizzoEnte(metadatiVO, mudeTEnte);
			
			//replaced: metadatiVO.setElencoEmailEnte(mudeTEnteRepository.retrieveEnteOperatorsEmails(mudeTEnte.getId()));
			metadatiVO.setElencoEmailEnte(mudeTEnte.getEmailGruppoEnte());
	    }
	    
	    return mudeTEnte;
	}

	private void setIndirizzoEnte(BaseMetadatiVO metadatiVO, MudeTEnte mudeTEnte) {
		String indirizzo = mudeTEnte.getIndirizzoEnte();
		if(indirizzo == null) return;
		
		metadatiVO.setIndirizzoEnte(Arrays.asList(indirizzo.split("~")));
	}
	
	private void setOggettoFascicolo(final MudeTIstanza istanza, String denomComune, BaseMetadatiVO metadatiVO,
											MudeTContatto intContact,
											String titoloIntervento) {
		if("PF".equalsIgnoreCase(intContact.getTipoContatto().getValue())) 
			// <denom_comune> + " " + <cognome_intestatario> + " " + <nome_intestatario> + " " + <oggetto_pratica>
		    metadatiVO.setOggettoFascicolo(denomComune + " " + intContact.getCognome() + " " + intContact.getNome() + " " + titoloIntervento);
		else 
			// <denom_comune> + " " + <ragione_sociale> + " " + <oggetto_pratica>
			metadatiVO.setOggettoFascicolo(denomComune + " " + intContact.getRagioneSociale() + " " + titoloIntervento);
	}
	
	private String getMaxLength(String str, int maxlen) {
		if(str.length() > maxlen)
			return str.substring(0, maxlen);
		return str;
	}
	
	private List<String> getSubjectsByTemplate(final MudeTIstanza istanza, 
												BaseMetadatiVO metadatiVO,
												boolean isControlloCampioneSelected) {
		List<String> res = new ArrayList();
		List<Long> recipientDone = new ArrayList();
		
		String pfTemplate = mudeCProprietaRepository.getValueByName("COSMO_TEMPLATE_PF" + (isControlloCampioneSelected?"_CC":""), "");
		String pgTemplate = mudeCProprietaRepository.getValueByName("COSMO_TEMPLATE_PG" + (isControlloCampioneSelected?"_CC":""), "");
		
		for(String roleToBeIncluded : mudeCProprietaRepository.getValueByName("COSMO_RUOLI_SOGGETTI" + (isControlloCampioneSelected?"_CC":""), "IN, CO, PS, CC, DL, CL").split(","))
			for(MudeTContatto contact: mudeTContattoRepository.findRoleByIdIstanza(istanza.getId(), new String[] { roleToBeIncluded.trim() })) {
				if(recipientDone.contains(contact.getId())) continue;
				
				if(StringUtils.isBlank(contact.getPec()))
					contact.setPec(contact.getMail());
				if(StringUtils.isBlank(contact.getPec()))
					continue; // CR Silvana: per le mail (secondo Silvana la lista per i template andrebbe completa e la lista per doqui valorizzata solo se la mail è presente..... cosa ne  pensi ?
				
				HashMap<String, Object> contextMap = new HashMap();
				contextMap.put("ex_RUOLO", mudeDRuoloSoggettoRepository.findOne(roleToBeIncluded.trim()));
				contextMap.put("ex_CONTATTO", contattoEntityMapper.mapEntityToVO(contact, null, "fill-in-rappresentante"));
				contextMap.put("ex_ISTANZA_MUDE", istanza);
				
				String subject = BasicTransformer.fillInMergeFields(TipoContatto.PF == contact.getTipoContatto() ? pfTemplate : pgTemplate, contextMap);
				res.add(subject);
				
				recipientDone.add(contact.getId());

				// adds recipient to array
				metadatiVO.getTipoDestinatario().add(contact.getTipoContatto().getValue().toUpperCase());
				if(contact.getTipoContatto() == TipoContatto.PG) {
					metadatiVO.getDenominazioneDestPg().add(contact.getRagioneSociale());
					metadatiVO.getIndirizzoTelematicoDestPg().add(!StringUtils.isBlank(contact.getPec())? contact.getPec() : (!StringUtils.isBlank(contact.getMail())? contact.getMail() : "") );
					metadatiVO.getTipoIndirizzoTelematicoDestPg().add("smtp");
					metadatiVO.getRuoloDestinatarioPg().add(1 /* per competenza */);
				}
				else {
					metadatiVO.getCodiceFiscaleDestPf().add(contact.getCf());
					metadatiVO.getCognomeDestPf().add(contact.getCognome());
					metadatiVO.getNomeDestPf().add(contact.getNome());
					
					metadatiVO.getIndirizzoTelematicoDestPf().add(!StringUtils.isBlank(contact.getPec())? contact.getPec() : (!StringUtils.isBlank(contact.getMail())? contact.getMail() : "") );
					metadatiVO.getTipoIndirizzoTelematicoDestPf().add("smtp");
					metadatiVO.getRuoloDestinatarioPf().add(1 /* per competenza */);
				}
			}
		
		return res;
	}
	
	private List<String> getComuniByTemplate(final MudeTIstanza istanza,
												BaseMetadatiVO metadatiVO,
												boolean isControlloCampioneSelected) {
		List<String> res = new ArrayList();
		String pfTemplate = mudeCProprietaRepository.getValueByName("COSMO_TEMPLATE_COMUNE", "");
				
		HashMap<String, Object> contextMap = new HashMap();
		contextMap.put("ex_ISTANZA_MUDE", istanza);
		
		String subject = BasicTransformer.fillInMergeFields(pfTemplate, contextMap);
		res.add(subject);
		
		// adds recipient to array
		metadatiVO.getTipoDestinatario().add("PG");
		metadatiVO.getDenominazioneDestPg().add(istanza.getComune().getDenomComune());
		metadatiVO.getIndirizzoTelematicoDestPg().add(istanza.getComune().getEmail());
		metadatiVO.getTipoIndirizzoTelematicoDestPg().add("smtp");
		metadatiVO.getRuoloDestinatarioPg().add(3 /* per conoscenza */);

		return res;
	}
	
	
	/*
	private List<String> getCarbonCopySubjectsByTemplate(final MudeTIstanza istanza,
												final MudeTEnte ente,
												boolean isControlloCampioneSelected) {
		List<String> res = new ArrayList();
		
		String pfTemplate = mudeCProprietaRepository.getValueByName("COSMO" + (isControlloCampioneSelected?"_CC_":"_") + "TEMPLATE_PF", "");
		
		for(String roleToBeIncluded : mudeCProprietaRepository.getValueByName("COSMO" + (isControlloCampioneSelected?"_CC_":"_") + "TEMPLATE", "IN, CO, PS, CC, DL, CL").split(","))
			for(MudeTContatto contact: mudeTContattoRepository.findRoleByIdIstanza(istanza.getId(), new String[] { roleToBeIncluded })) {
				HashMap<String, Object> contextMap = new HashMap();
				contextMap.put("ex_ENTE", ente);
				contextMap.put("ex_ISTANZA_MUDE", istanza);
				
				String subject = BasicTransformer.fillInMergeFields(TipoContatto.PG.getValue().equals(contact.getTipoContatto()) ? pfTemplate : pgTemplate, contextMap);
				res.add(subject);
			}
		
		return res;
	}
	*/

	private boolean propagateSignalToRelatedInstances(Long idIstanzaFrom) throws Exception {
		Long idIstanza = mudeTIstanzaRepository.retrieveMainDenunciaSismica(idIstanzaFrom).getId();
		
		String currState = mudeRIstanzaStatoRepository.getStatoIstanza(idIstanza);
		if(!"ACC".equalsIgnoreCase(currState) && !"ARC".equalsIgnoreCase(currState))
			return true;

	    String signalCode = mudeCProprietaRepository.getValueByNameAndInstanceSP("COSMO_PROPAGA_SEGNALE_" + currState, idIstanza, ""); // "ACCETTATA" : "ARCHIVIA"
		String ipaEnte = mudeCProprietaRepository.getValueByName("COSMO_CODICE_IPA", "r_piemon");

		String excludeIdTipoIstanza = mudeCProprietaRepository.getValueByName("COSMO_EXCLUDE_"+currState+"_TIPO_ISTANZE", "NONE");
		for(MudeTIstanza mudeTIstanza : mudeTIstanzaRepository.getIstenzeRelatedToCOSMO(idIstanza,
				StatoIstanza.ARCHIVIATA.getLevel()
		)) {
			MudeTPraticaCosmo mudeTPraticaCosmo = mudeTPraticaCosmoRepository.findByIdIstanza(mudeTIstanza.getId());
			
			// cambia stato senza notifica
			MudeRIstanzaStato mudeRIstanzaStato = mudeRIstanzaStatoRepository.findStatoByIstanza(mudeTIstanza.getId());
			
			if(currState.equals(mudeRIstanzaStato.getStatoIstanza().getCodice()))
				continue; // already ARC/ACC
				
			mudeRIstanzaStato.setDataFine(new Date());
			mudeRIstanzaStatoRepository.saveDAO(mudeRIstanzaStato);

			mudeRIstanzaStato = new MudeRIstanzaStato();
			mudeRIstanzaStato.setIstanza(mudeTIstanza);
			mudeRIstanzaStato.setStatoIstanza(mudeDStatoIstanzaRepository.findOne(currState));
			mudeRIstanzaStato.setDataInizio(new Date());
			mudeRIstanzaStato.setDataModifica(new Date());
			mudeRIstanzaStatoRepository.saveDAO(mudeRIstanzaStato);

			// InviaSegnaleFruitoreResponse
			if(!StringUtils.isBlank(signalCode) 
					&& "FO".equalsIgnoreCase(mudeTIstanza.getIdFonte())
					&& StatiProcessoCosmoEnum.IN_PROCESSO.toString().equalsIgnoreCase(mudeTPraticaCosmo.getCodiceStatoCosmo())
					&& excludeIdTipoIstanza.indexOf(mudeTIstanza.getTipoIstanza().getCodice()) == -1
					&& mudeCosmoManager.sendSignalToPractice(mudeTIstanza.getCodiceIstanza(),
															mudeTPraticaCosmo,
															ipaEnte,
															signalCode,
															null,
															false) != null) {
				return false;
			}
		}

		return true;
	}
	
}

class MudeopenInfo {
	public String urlBO = "";
	public String urlIstanzaBO = "";
	public String urlIstanzaBODetail = "";
	public String urlIstanzaBOSearch = "";
	public String urlIstanzaBOPraticaDetail;
	public String titoloIntervento = "";
	public String descrizioneIntervento = "";
	public String tipoIstruttoria = "";
	public String cointestatari = "";
	public String procuratore = "";

	public String getUrlBO() { return urlBO; }
	public String getUrlIstanzaBO() { return urlIstanzaBO; }
	public String getUrlIstanzaBODetail() { return urlIstanzaBODetail; }
	public String getUrlIstanzaBOSearch() { return urlIstanzaBOSearch; }
	public String getUrlIstanzaBOPraticaDetail() { return urlIstanzaBOPraticaDetail; }
	public String getTitoloIntervento() { return titoloIntervento; }
	public String getDescrizioneIntervento() { return descrizioneIntervento; }
	public String getTipoIstruttoria() { return tipoIstruttoria; }
	public String getCointestatari() { return cointestatari; }
	public String getProcuratore() { return procuratore; }
	
} 
