/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.CsiLogAudit;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDSpeciePratica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDStatoFiltroVeloce;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDStatoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoNotifica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDWfStato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaStato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaTracciato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTAllegato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTAllegatoSlim;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTNotifica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.GeoriferimentoUpdateTableService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.dto.ErrorMessage;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon.IstanzaApiServiceHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IstanzaStatoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IstanzaStatoSlimEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDSpeciePraticaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDStatoFiltroVeloceRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDStatoIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoNotificaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDWfStatiRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaStatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaTracciatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTAllegatoSlimRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIndirizzoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzeExtRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTNotificaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTUserRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.AllegatiService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.CosmoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.EntiService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.FascicoliService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.FascicoloSoggettoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IdfService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaStatoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaUtenteService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.ModelliService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.PraticaService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.ReportService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.UtilsTraceCsiLogAuditService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.IndexManager;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.ManagerAbilitazioni;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.TracciatoXMLManager;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.UtilsDate;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.UserUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.StatoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.AbilitazioniEnum;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.FunzioniAbilitazioniEnum;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaStatoSlimVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaStatoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.modello.ModelloCompilatoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.IstanzaUtenteVO;

@Service
@Transactional
public class IstanzaStatoServiceImpl implements IstanzaStatoService {
    private static Logger logger = Logger.getLogger(IstanzaTracciatoServiceImpl.class.getCanonicalName());

	@Context
	private HttpServletRequest request;

    @Autowired
    protected UserUtil userUtil;

    @Autowired
    private IstanzaService istanzaService;

    @Autowired
    private MudeRIstanzaStatoRepository mudeRIstanzaStatoRepository;

    @Autowired
    private MudeTIstanzaRepository mudeTIstanzaRepository;

    @Autowired
    private MudeDStatoIstanzaRepository mudeDStatoIstanzaRepository;

    @Autowired
    private EntiService entiService;

    @Autowired
    private CosmoService cosmoService;
    
    @Autowired
    private IdfService idfService;

    @Autowired
    private IstanzaStatoEntityMapper istanzaStatoEntityMapper;

    @Autowired
    private IstanzaStatoSlimEntityMapper istanzaStatoSlimEntityMapper;

    @Autowired
    private FascicoloSoggettoService fascicoloSoggettoService;

    @Autowired
    private PraticaService praticaService;

    @Autowired
    private FascicoliService fascicoliService;

    @Autowired
    protected ManagerAbilitazioni managerAbilitazioni;

    @Autowired
    private IstanzaUtenteService istanzaUtenteService;

    @Autowired
    private ModelliService modelliService;

    @Autowired
    private IstanzaApiServiceHelper istanzaApiServiceHelper;

    @Autowired
    private MudeDWfStatiRepository mudeDWfStatiRepository;

    @Autowired
    private MudeDStatoFiltroVeloceRepository mudeDStatoFiltroVeloceRepository;

    @Autowired
    private TracciatoXMLManager tracciatoXMLManager;

    @Autowired
    private MudeRIstanzaTracciatoRepository mudeRIstanzaTracciatoRepository;

    @Autowired
    private UtilsDate utilsDate;

    @Autowired
    private MudeTIndirizzoRepository mudeTIndirizzoRepository;

    @Autowired
    private MudeTNotificaRepository mudeTNotificaRepository;

    @Autowired
    private ReportService reportService;

    @Autowired
    private MudeTUserRepository mudeTUserRepository;

    @Autowired
    private MudeDTipoNotificaRepository mudeDTipoNotificaRepository;

    @Autowired
    private MudeDQuadroRepository mudeDQuadroRepository;

    @Autowired
    private MudeTIstanzeExtRepository mudeTIstanzeExtRepository;
    
    @Autowired
    private MudeDSpeciePraticaRepository mudeDSpeciePraticaRepository;

    @Autowired
    private GeoriferimentoUpdateTableService georiferimentoUpdateTableService;
    
    @Autowired
    private UtilsTraceCsiLogAuditService utilsTraceCsiLogAuditService;

    @Autowired
    private IndexManager indexManager;

    @Override
    public List<IstanzaStatoVO> findAllByStato(String statoIstanza) {
        List<MudeRIstanzaStato> list = mudeRIstanzaStatoRepository.findAllByStato(statoIstanza);
        return istanzaStatoEntityMapper.mapListEntityToListVO(list);
    }

    @Override
    public List<IstanzaStatoVO> getStatiIstanza(Long idIstanza, String scope) {
        List<MudeRIstanzaStato> list = mudeRIstanzaStatoRepository.findAllByIstanza_idOrderByDataInizioDesc(idIstanza);
        if(!"backoffice".equals(scope))
        	return istanzaStatoEntityMapper.mapListEntityToListSlimVO(list);

		List<IstanzaStatoVO> v = new ArrayList<>();
		for (MudeRIstanzaStato t : list)
			if(!"BZZ,FRM,DFR".contains(t.getStatoIstanza().getCodice()))
				v.add(istanzaStatoEntityMapper.mapEntityToSlimVO(t));

		return v;
    }

    @Override
    public IstanzaStatoVO findStatoByIstanza(Long idIstanza) {
        MudeRIstanzaStato entity = mudeRIstanzaStatoRepository.findStatoByIstanza(idIstanza);
        return istanzaStatoEntityMapper.mapEntityToVO(entity);
    }

    @Override
    public IstanzaStatoSlimVO findStatoByIstanzaSlim(Long idIstanza) {
        MudeRIstanzaStato entity = mudeRIstanzaStatoRepository.findStatoByIstanza(idIstanza);
        return istanzaStatoSlimEntityMapper.mapEntityToVO(entity);
    }

    @Override
    public String getStatoIstanza(Long idIstanza) {
        return mudeRIstanzaStatoRepository.getStatoIstanza(idIstanza);
    }

    @Override
    public int isInstanceInState(Long idIstanza, String[] stati_possibili) {
        return mudeRIstanzaStatoRepository.isInstanceInState(idIstanza, stati_possibili);
    }

    @Override
    public int isThereInstanceStateInFolder(Long idFascicolo, String[] stati_possibili, String[] escludiStati) {
        return mudeRIstanzaStatoRepository.isThereInstanceStateInFolder(idFascicolo, 
        															stati_possibili == null? new String[] {""} : stati_possibili, 
        															escludiStati == null? new String[] {""} : escludiStati);
    }

    @Override
	public boolean saveIstanzaStato(MudeTUser mudeTUser, String userCf, Long idIstanza, String codStatus, IstanzaVO istanza, HttpHeaders httpHeaders, String scope, String jsonFormIO) {
        boolean changeStatus = saveIstanzaStato( mudeTIstanzaRepository.findOne(idIstanza), codStatus, istanza, httpHeaders, scope, false);

        if(StatoIstanza.BOZZA.getValue().equals(codStatus) || StatoIstanza.RESTITUITA_PER_VERIFICHE.getValue().equals(codStatus))
            istanzaService.deleteFileIstanzaFirmata(idIstanza, mudeTUser, httpHeaders, false, scope);

        if((changeStatus && ("backoffice".equals(scope) || Constants.SCOPE_WS.equals(scope))) 
        	|| (changeStatus && "frontoffice".equals(scope) && StatoIstanza.DEPOSITATA.getValue().equals(codStatus))) {
        	if(jsonFormIO != null)
        		istanzaService.updateJsonDataFormIO(idIstanza,codStatus,jsonFormIO, istanza);
        	
        	List<MudeDTipoNotifica> mudeDTipoNotificaAll = mudeDTipoNotificaRepository.findByCodTipoNotificaAndValid("CAMBIO_STATO");
        	if(mudeDTipoNotificaAll != null && !mudeDTipoNotificaAll.isEmpty()) {
	        	MudeDTipoNotifica mudeDTipoNotifica = mudeDTipoNotificaAll.get(0);
	        	
	        	MudeTNotifica mudeTNotifica = istanzaService.insertTNotifica(mudeTUser,idIstanza,jsonFormIO,mudeDTipoNotifica);
	        	
	        	List<MudeTContatto> contattiDestinatari = istanzaService.recuperaContatti(idIstanza,mudeTUser);
	        	
	        	if(mudeTNotifica != null && contattiDestinatari != null && !contattiDestinatari.isEmpty()) {
	        		istanzaService.insertRNotificaUtenti(idIstanza,mudeTNotifica,contattiDestinatari);
	        		
	        		if(mudeDTipoNotifica.getInvioEmail())
	        			istanzaService.insertRNotificaContatto(idIstanza,mudeTNotifica,contattiDestinatari, istanza);
	        	}
	        	
	        	//Dettaglio per notifica portale cambio stato APA e testo notifica portale cambio stato DPS
	        	if(mudeTNotifica.getTipoNotifica().getCodTipoNotifica().equalsIgnoreCase("CAMBIO_STATO") &&
	        			StatoIstanza.REGISTRATA_DA_PA.getValue().equals(codStatus) ) {
	        		String testoDettaglio = istanzaService.createDettaglioNotificaPortale(idIstanza, mudeTNotifica);
	        		mudeTNotifica.setDettaglio(testoDettaglio);
	        		mudeTNotificaRepository.saveDAO(mudeTNotifica);
	        	}else if(mudeTNotifica.getTipoNotifica().getCodTipoNotifica().equalsIgnoreCase("CAMBIO_STATO") &&
	        			StatoIstanza.DEPOSITATA.getValue().equals(codStatus) ) {
	        		String testoDPS = istanzaService.createTestoNotificaDPS(idIstanza, mudeTNotifica);
	        		mudeTNotifica.setTestoNotifica(testoDPS);
	        		mudeTNotificaRepository.saveDAO(mudeTNotifica);
	        	}
        	}
        	StatoIstanza newStatus = StatoIstanza.fromString(codStatus);
        	if(StatoIstanza.REGISTRATA_DA_PA.equals(newStatus)) {
        		if(mudeTUser == null && !Constants.SCOPE_WS.equals(scope)) {
        			mudeTUser = mudeTUserRepository.findByCf(userCf);
        		}
        		reportService.generaReportPDFToByte(idIstanza, mudeTUser, new Timestamp(System.currentTimeMillis()));
        	}
        }

        return changeStatus;
    }

    private boolean saveIstanzaStato(MudeTIstanza istanza, String codStatus, IstanzaVO istanzaVO, HttpHeaders httpHeaders, String scope, boolean create) {
    	boolean sendNotification = true;
    	Long idIstanza = istanza.getId();
    	
    	MudeTUser mudeTUser = null;
    	if(!Constants.SCOPE_WS.equals(scope))
    		mudeTUser = userUtil.getUserCF(httpHeaders, false);
    	
        MudeDStatoIstanza mudeDStatoIstanza = mudeDStatoIstanzaRepository.findOne(codStatus);

        String currState = null;
        MudeRIstanzaStato currentIstanzaStato = null;
        if(!create) {
            currentIstanzaStato = mudeRIstanzaStatoRepository.findStatoByIstanza(istanza.getId());
            currState = currentIstanzaStato.getStatoIstanza().getCodice();
        }

        boolean isThereOldState = currentIstanzaStato != null && !codStatus.equalsIgnoreCase(currState);
        StatoIstanza newStatus = StatoIstanza.fromString(codStatus);

		if(!"FO".equals(istanza.getIdFonte()) && StatoIstanza.RESTITUITA_PER_VERIFICHE.equals(newStatus))
			throw new BusinessException("Cambio stato '"+ newStatus +"' non permesso per l'istanza specificata");

    	// check if it's in a different state
        if(isThereOldState) {
    	
        	if("backoffice".equals(scope) || "ws".equals(scope) ) {
        		MudeDWfStato statiList;
        		
        		if("ws".equals(scope))
        			statiList = mudeDWfStatiRepository.findAllByCodiceStatoStartAndCodiceStatoEndAndTipoIstanza(currentIstanzaStato.getStatoIstanza().getCodice(), newStatus.getValue(),istanza.getTipoIstanza().getCodice(), istanza.getSpeciePratica() != null? istanza.getSpeciePratica().getCodice() : "");
        		else
        			statiList = mudeDWfStatiRepository.findByCodiceStatoStartAndCodiceStatoEndAndTipoIstanza(currentIstanzaStato.getStatoIstanza().getCodice(), newStatus.getValue(),istanza.getTipoIstanza().getCodice(), istanza.getSpeciePratica() != null? istanza.getSpeciePratica().getCodice() : "");
        		
        		if(statiList == null)
        			throw new BusinessException("L'istanza è in stato '"+StatoIstanza.toString(currState)+"'. Non è consentito il cambio stato in '"+newStatus.toName()+"'");

        		// TASK-ISSUE-38
        		if(StringUtils.isBlank(statiList.getOggettoNotifica()) && StringUtils.isBlank(statiList.getTestoNotifica()))
        			sendNotification = false;
        			
        		if(!"FO".equals(istanza.getIdFonte()) && statiList.getCodiceStatoEnd().equals(StatoIstanza.RESTITUITA_PER_VERIFICHE))
        			throw new BusinessException("Cambio stato '"+ statiList.getCodiceStatoEnd()  +"' non permesso per l'istanza specificata");
        			
        	} else if(newStatus != null) {
	        	// Handle status WORKFLOW
	    		switch(newStatus) {
	    		case BOZZA:
	    		case RESTITUITA_PER_VERIFICHE:
	                if(!Constants.SCOPE_WS.equals(scope) && !istanzaVO.getTornaBozza())
	                	throw new BusinessException("L'istanza è in stato '"+StatoIstanza.toString(currState)+"'. Non disponi dei diritti necessari a cambiare lo stato in '"+newStatus.toName()+"'");
	                break;
	    		case DA_FIRMARE:
	                if(!((StatoIstanza.BOZZA.equals(currState) || StatoIstanza.RESTITUITA_PER_VERIFICHE.equals(currState) || StatoIstanza.FIRMATA.equals(currState))
	                		&& managerAbilitazioni.hasUtenteAbilitazioneFunzionePerIstanza(false, FunzioniAbilitazioniEnum.CONSOLIDA_ISTANZA.getDescription(), idIstanza, mudeTUser, httpHeaders)))
	                	throw new BusinessException("L'istanza è in stato '"+StatoIstanza.toString(currState)+"'. Non disponi dei diritti necessari a cambiare lo stato in '"+newStatus.toName()+"'");
	                break;
	    		case FIRMATA:
	                if(!(StatoIstanza.DA_FIRMARE.equals(currState)
	                		&& managerAbilitazioni.hasUtenteAbilitazioneFunzionePerIstanza(false, FunzioniAbilitazioniEnum.UPL_IST.getDescription(), idIstanza, mudeTUser, httpHeaders)))
	                	throw new BusinessException("L'istanza è in stato '"+StatoIstanza.toString(currState)+"'. Non disponi dei diritti necessari a cambiare lo stato in '"+newStatus.toName()+"'");
	                break;
	    		case DEPOSITATA:
	                if(!(StatoIstanza.FIRMATA.equals(currState)
	                		&& managerAbilitazioni.hasUtenteAbilitazioneFunzionePerIstanza(false, FunzioniAbilitazioniEnum.INVIA_IST.getDescription(), idIstanza, mudeTUser, httpHeaders)))
	                	throw new BusinessException("L'istanza è in stato '"+StatoIstanza.toString(currState)+"'. Non disponi dei diritti necessari a cambiare lo stato in '"+newStatus.toName()+"'");

	    			// update specie pratica if null
	                if(istanza.getSpeciePratica() == null) {
	                	MudeDSpeciePratica mudeDSpeciePratica = mudeDSpeciePraticaRepository.retrieveSpeciePraticaFromIstanza(istanza.getId());
	                	if(mudeDSpeciePratica != null)
	                		istanza.setSpeciePratica(mudeDSpeciePratica);
	                }
	                
	    			// update folder address, if requested
	    			fascicoliService.updateFolderAddressFromIstanza(istanza, istanzaVO.getAggiornaFascicoloIndirizzo() == null? false : istanzaVO.getAggiornaFascicoloIndirizzo());
	    			
	    			utilsTraceCsiLogAuditService.traceCsiLogAudit(CsiLogAudit.TraceOperation.DEPOSITA_ISTANZA.getOperation(), istanza.getTableName(), "id=" + istanza.getId(), mudeTUser.getCf(), Thread.currentThread().getStackTrace()[1].getMethodName());
	    			
	                break;
				default:
					break;
	    		}
	    	}
    		
            currentIstanzaStato.setDataFine(Calendar.getInstance().getTime());
            mudeRIstanzaStatoRepository.saveDAO(currentIstanzaStato);

            istanza.setDataCreazione(new Timestamp(System.currentTimeMillis()));
            mudeTIstanzaRepository.saveAndFlushDAO(istanza);

            currentIstanzaStato = null;
    	
    	}
    	// create new state if changed
        if(currentIstanzaStato == null) {
            MudeRIstanzaStato istanzaStatoNew = new MudeRIstanzaStato();
            istanzaStatoNew.setIstanza(istanza);
            istanzaStatoNew.setStatoIstanza(mudeDStatoIstanza);

            if(istanzaVO != null && istanzaVO.getDataCreazione() != null)
            	istanzaStatoNew.setDataInizio(Date.from(istanzaVO.getDataCreazione().atZone(ZoneId.systemDefault()).toInstant()));
            else
            	istanzaStatoNew.setDataInizio(Calendar.getInstance().getTime());

            mudeRIstanzaStatoRepository.saveDAO(istanzaStatoNew);

            if(isThereOldState && newStatus != null)
            	handleStatusTransition(istanza, newStatus, istanzaVO, mudeTUser);

            return sendNotification; // status changed
        }

        return false; // status not changed
    }

    private void handleStatusTransition(MudeTIstanza istanza, StatoIstanza newStatus, IstanzaVO partialIstanzaVOFromUser, MudeTUser mudeTUser) {
		switch(newStatus) {
        case BOZZA:
        case RESTITUITA_PER_VERIFICHE:
            //rimozione dalla cache del pdf istanza compilato
            mudeTIstanzaRepository.removePdfCache(istanza.getId());

            if(newStatus.name().equals(StatoIstanza.RESTITUITA_PER_VERIFICHE.name())){
                List<MudeRIstanzaTracciato> istanzaTracciati = mudeRIstanzaTracciatoRepository.findAllByDataFineNullAndMudeTIstanza_Id(istanza.getId());
                for (MudeRIstanzaTracciato mudeRIstanzaTracciato : istanzaTracciati) {
                    mudeRIstanzaTracciato.setDataFine(new Date());
                    mudeRIstanzaTracciatoRepository.saveDAO(mudeRIstanzaTracciato);
                }
                istanza.setRigeneraTracciato(Boolean.FALSE);
            }

            indexManager.deleteContenutoByUuid(istanza.getUuidFileIndex());            

            // removes the signed file 
            istanza.setFilename(null);
            istanza.setUuidFileIndex(null);

            mudeTIstanzaRepository.saveAndFlushDAO(istanza);

            // CHKIFREQUIRED: refresh contacts to latest version for all address books
            //contattoService.refreshInstanceContacts(istanza, mudeTUser);

            break;
        // CONSOLIDA
		case DA_FIRMARE:
			checkInstanceDataIntegrity(istanza, false);

	        //se il pdf dell'istanza non è in cache, salvo in cache il file pdf istanza compilato e ne calcolo e salvo l' hash
	        if(!mudeTIstanzaRepository.checkCachedPdfExists(istanza.getId())) {
	            try {
	                //generazione e caching del pdf istanza compilato
	                ModelloCompilatoVO vo = modelliService.loadTemplatePDF(istanza.getId(), istanza.getTemplate().getModello().getId(), mudeTUser, false);
	                
	                String hash = istanzaApiServiceHelper.calculateFileHash(vo.getFileContent());
 	                mudeTIstanzaRepository.savePdfCache(istanza.getId(), hash, vo.getFileContent(), mudeTUser.getIdUser(), mudeTUser.getCf());
	            } catch (NoSuchAlgorithmException e) {
	                throw new BusinessException("Impossibile calcolare l'impronta del file", "404", null, null);
	            }
	        }

			break;
		case FIRMATA:
            break;
		case DEPOSITATA:
			checkInstanceDataIntegrity(istanza, true);
			
			entiService.assegnaEntiAdIstanza(istanza);
			
			mudeTIstanzeExtRepository.setDPSScriptState(istanza.getId(), "QUEUED");

			cosmoService.addPraticaCosmoToQueue(istanza);

			// set date of "DEPOSITATA" state
            istanza.setDataDps(new Timestamp(System.currentTimeMillis()));
            mudeTIstanzaRepository.saveDAO(istanza);

            // j932
	        georiferimentoUpdateTableService.cloneGeecoFromParentSelection(istanza);
            
            //quando l'istanza viene depositata vengono generati i tracciati xml
			try {
				tracciatoXMLManager.gemerateTracciato(istanza, mudeTUser, false);
			}catch(Throwable t) {
				logger.error("[IstanzeApiServiceImpl::rigeneraTracciati] exception", t);
			}
			break;
		case REGISTRATA_DA_PA:
			praticaService.savePraticaForIstanza(istanza, partialIstanzaVOFromUser, mudeTUser);
			istanza.setDataRegistrazionePratica(Calendar.getInstance().getTime());
			//istanza.setAnno(partialIstanzaVOFromUser.getAnno());
	        //istanza.setNumeroPratica(partialIstanzaVOFromUser.getNumeroPratica());
	        istanza.setDataProtocollo(utilsDate.asDate(partialIstanzaVOFromUser.getDataProtocollo()));
	    	istanza.setNumeroProtocollo(partialIstanzaVOFromUser.getNumeroProtocollo());
			istanza.setResponsabileProcedimento(partialIstanzaVOFromUser.getResponsabile_procedimento());
			mudeTIstanzaRepository.saveDAO(istanza);
			
			if("FO".equals(istanza.getIdFonte()))
				fascicoloSoggettoService.migrazioneSoggettiInFascicolo(istanza, mudeTUser);
			break;
		default:
			break;
		}
    }

    @Override
    public List<SelectVO> recuperaStati(IstanzaVO istanza) {

        List<SelectVO> statiVOList = new ArrayList<SelectVO>();
        List<MudeDWfStato> statiList = mudeDWfStatiRepository.findByCodiceStatoStartAndTipoIstanza(istanza.getStatoIstanza().getCodice(), istanza.getTipologiaIstanza().getId(), istanza.getSpeciePratica() != null? istanza.getSpeciePratica().getCodice() : "");
        for (MudeDWfStato stato : statiList) {
            MudeDStatoIstanza mudeDStatoIstanza = mudeDStatoIstanzaRepository.findMudeDStatoIstanzaByCodice(stato.getCodiceStatoEnd());

    		if(istanza.getFonte() != null && !"FO".equals(istanza.getFonte()) && stato.getCodiceStatoEnd().equals(StatoIstanza.RESTITUITA_PER_VERIFICHE))
    			continue; // skip RESTITUITA PER VERIFICA

            statiVOList.add(new SelectVO(stato.getCodiceStatoEnd(), mudeDStatoIstanza.getDescrizione()));
        }

        return statiVOList;
    }

    @Override
    public List<SelectVO> recuperaStatiFiltroVeloce() {
        List<SelectVO> listStatiVO = new ArrayList<SelectVO>();
        List<MudeDStatoFiltroVeloce> mudeDStatoIstanzaList = mudeDStatoFiltroVeloceRepository.findAll();
        for (MudeDStatoFiltroVeloce stato : mudeDStatoIstanzaList) {
            listStatiVO.add(new SelectVO(stato.getCodice(), stato.getDescrizione()));
        }

        return listStatiVO;
    }

	
	@Override
	public boolean saveIstanzaStato(Long idIstanza, String codiceStatoIstanza, IstanzaVO istanza,
			HttpHeaders httpHeaders) {
		return saveIstanzaStato(mudeTIstanzaRepository.findOne(idIstanza), codiceStatoIstanza, istanza, httpHeaders, null, false);
	}

	@Override
	public boolean saveIstanzaStato(MudeTIstanza istanza, String codiceStatoIstanza, IstanzaVO istanzaVO,
			HttpHeaders httpHeaders, boolean create) {
		return saveIstanzaStato(istanza, codiceStatoIstanza, istanzaVO,
				httpHeaders, null, create);
	}
	
	private void checkInstanceDataIntegrity(MudeTIstanza istanza, boolean isFinal) {
		boolean hasPM = false;

		JSONObject jsonData = null;
        JSONObject subjectList = null;
        try {
    		jsonData = new JSONObject(istanza.getJsonData());
            subjectList = (JSONObject)((JSONObject)(jsonData.get("QDR_SOGGETTO_COINV"))).get("subjectList");
		} catch (Exception skip) { }
		
		// check if all user with permissions must be skiped in case there is CREATORE_IST_PM_OPZ
        hasPM = checkUserPermissions(istanza, hasPM, subjectList);
		
		if(!mudeTIstanzaRepository.areAllInstanceMandatoryRolesSelected(istanza.getId()))
			throw new BusinessException("Non sono stati selezionati tutti ruoli obbligatori nel quadro dei soggetti coinvolti. E' necessario selezionarli tutti prima di continuare.");
		
		if(istanza.getTemplate().getObbligatoriaNominaPM() && !hasPM)
			throw new BusinessException("Per questo tipo di istanza è necessario indicare il PM/RUP tra i soggetti coinvolti.");
		
		// check if all steps are validated from user
		checkAllValidSteps(istanza, isFinal, jsonData);
		 
        if(istanza.getIdIstanzaRiferimento() != null)
        	migrateDataFromParent(istanza, jsonData, mudeTIstanzaRepository.findOne(istanza.getIdIstanzaRiferimento()));
        
        deleteMissingAttaches(istanza);

	}

	private void deleteMissingAttaches(MudeTIstanza istanza) {
		/*
		for(MudeTAllegatoSlim mudeTAllegato : mudeTAllegatoSlimRepository.findAllAllegatiNotPresentInJsonData(istanza.getId())) {
            indexManager.deleteContenutoByUuid(mudeTAllegato.getFileUID());
            allegatiService.deleteAllegato(mudeTAllegato.getId());
        }
        */
	}

	private boolean checkUserPermissions(MudeTIstanza istanza, boolean hasPM, JSONObject subjectList) {
		List<IstanzaUtenteVO> istanzaUtenteVOs  = istanzaUtenteService.recuperaAbilitazioniIstanza(istanza.getId(), true);
		if(!istanzaUtenteVOs.stream().anyMatch(x -> AbilitazioniEnum.CREATORE_IST_PM_OPZ.equals(x.getAbilitazione().getCodice())))
			// check if all user with permissions are in SOGG.COINV, and PM are presents and required
			for(IstanzaUtenteVO istanzaUtenteVO : istanzaUtenteVOs) {
				if(AbilitazioniEnum.CONSULTATORE.getDescription().equals(istanzaUtenteVO.getAbilitazione().getCodice())
						|| !istanzaUtenteVO.getUtente().getContatto().getTipoContatto().name().equals("PF"))
					continue;
				
				if(AbilitazioniEnum.PM_RUP_PM_OBB.equals(istanzaUtenteVO.getAbilitazione().getCodice()))
					hasPM = true;
				
				if(istanzaUtenteVO.getAbilitazione() != null && istanzaUtenteVO.getAbilitazione().getNecessariaPresenzaInIstanza() != null 
						&& !istanzaUtenteVO.getAbilitazione().getNecessariaPresenzaInIstanza())
					continue;
				
				String cf = istanzaUtenteVO.getUtente().getContatto().getGuid();
				if(subjectList != null && !subjectList.has(cf))
					throw new BusinessException("Impossibile trovare il soggetto '"+ cf +"' tra i soggetti coinvolti dell'istanza. E' necessario aggiungerlo prima di cambiare lo stato dell'istanza in 'DA FIRMARE'.");
			}
		return hasPM;
	}

	private void checkAllValidSteps(MudeTIstanza istanza, boolean isFinal, JSONObject jsonData) {
		long prevQuadroTime = -1l;
		for(MudeDQuadro mudeDQuadro : mudeDQuadroRepository.findAllByIdTemplateOrderByOrdinamentoAsc(istanza.getTemplate().getIdTemplate())) {
			String tipoQuadro = mudeDQuadro.getMudeDTipoQuadro().getCodTipoQuadro();
			if(mudeDQuadro.getMudeDTipoQuadro().getTipoQuadroPadre() != null)
				tipoQuadro = mudeDQuadro.getMudeDTipoQuadro().getTipoQuadroPadre().getCodTipoQuadro();
			
			if(!isFinal && "QDR_PRESENTA".equalsIgnoreCase(tipoQuadro))
				return; // skip QDR_PRESENTA because it is not final validation
			
			long quadroTime = -1l;
			try {
				if(!jsonData.has(tipoQuadro) || !((JSONObject)(jsonData.get(tipoQuadro))).has("_VALIDATION_TIME"))
					continue; // skip those that don't have the info at all!
				
				quadroTime = (long)((JSONObject)(jsonData.get(tipoQuadro))).get("_VALIDATION_TIME");
			} catch (Exception skipAndValidateAfter) { }
		
			if(quadroTime <= prevQuadroTime)
				throw new BusinessException("Alcuni dati nel quadro '"+mudeDQuadro.getMudeDTipoQuadro().getDesTipoQuadro()+"' potrebbero essere stati modificati. Devi rivalidarne le informazioni prima di procedere. Nel caso l'istanza sia stata consolidata, sarà necessario sbloccarla.");
			
			prevQuadroTime = quadroTime;
		}
	}

    private void migrateDataFromParent(MudeTIstanza istanza, JSONObject jsonData, MudeTIstanza instanceReference) {
        boolean doSave = false;
        if(instanceReference == null) return;

        // in case there is no address connected to this record, try to get it from parent record, if any
        if(doSave |= istanza.getIndirizzo() == null)
        	istanza.setIndirizzo(mudeTIndirizzoRepository.findByIdIstanza(instanceReference.getId()));

		if(instanceReference.getJsonData() != null)
			try {
				JSONObject jsonDataRef = new JSONObject(instanceReference.getJsonData());
				boolean hasQualif = jsonData.has("TAB_QUALIF_1");
				
				if(doSave |= ((JSONObject)(jsonDataRef.get("TAB_QUALIF_1"))).has("opere_in_precario_su_suolo_pubblico") 
								&& (!hasQualif || !((JSONObject)(jsonData.get("TAB_QUALIF_1"))).has("opere_in_precario_su_suolo_pubblico"))) {
					if(hasQualif)
						((JSONObject)(jsonData.get("TAB_QUALIF_1"))).put("opere_in_precario_su_suolo_pubblico", ((JSONObject)(jsonDataRef.get("TAB_QUALIF_1"))).get("opere_in_precario_su_suolo_pubblico"));
					else {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("opere_in_precario_su_suolo_pubblico", ((JSONObject)(jsonDataRef.get("TAB_QUALIF_1"))).get("opere_in_precario_su_suolo_pubblico"));
						jsonData.put("TAB_QUALIF_1", jsonObject );
					}
					
					istanza.setJsonData(jsonData.toString());
				}
			} catch (Exception skipNotFound) { }
			
    	if(doSave)
            mudeTIstanzaRepository.saveDAO(istanza);
    }
}