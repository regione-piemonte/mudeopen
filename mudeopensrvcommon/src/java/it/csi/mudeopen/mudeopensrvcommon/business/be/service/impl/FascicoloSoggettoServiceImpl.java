/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeCProprieta;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRuoloSoggetto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloRuolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloSoggetto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaSoggetto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTFascicolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ContattoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.FascicoloSoggettoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IstanzaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDRuoloSoggettoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRFascicoloRuoloRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRFascicoloSoggettoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaSoggettoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTContattoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTFascicoloRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTUserRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.FascicoloIntestatarioService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.FascicoloSoggettoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.UtilsTraceCsiLogAuditService;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloRuoloVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloSoggettoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.RuoloPossibileVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.SoggettoIstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.request.RuoliFascicoloRequest;

@Service
public class FascicoloSoggettoServiceImpl implements FascicoloSoggettoService {
    private static Logger logger = Logger.getLogger(IstanzaUtenteServiceImpl.class.getCanonicalName());

    @Autowired
    private MudeRFascicoloSoggettoRepository mudeRFascicoloSoggettoRepository;

    @Autowired
    private MudeRFascicoloRuoloRepository mudeRFascicoloRuoloRepository;

    @Autowired
    private MudeRIstanzaSoggettoRepository mudeRIstanzaSoggettoRepository;

    @Autowired
    private MudeTIstanzaRepository mudeTIstanzaRepository;

    @Autowired
    private MudeTFascicoloRepository mudeTFascicoloRepository;

    @Autowired
    private MudeTContattoRepository mudeTContattoRepository;

    @Autowired
    private FascicoloSoggettoEntityMapper fascicoloSoggettoEntityMapper;

    @Autowired
    private ContattoEntityMapper contattoEntityMapper;

    @Autowired
    private IstanzaEntityMapper istanzaEntityMapper;

    @Autowired
    private MudeDRuoloSoggettoRepository mudeDRuoloSoggettoRepository;

    @Autowired
    private FascicoloIntestatarioService fascicoloIntestatarioService;

    @Autowired
    private MudeTUserRepository mudeTUserRepository;

	@Autowired
    private MudeCProprietaRepository mudeCProprietaRepository;

    @Override
    public void delete(Long idFascicoloSoggetto) {
        MudeRFascicoloSoggetto mudeRFascicoloSoggetto = mudeRFascicoloSoggettoRepository.findOne(idFascicoloSoggetto);
        mudeRFascicoloSoggetto.setDataFine(Calendar.getInstance().getTime());
        mudeRFascicoloSoggettoRepository.saveDAO(mudeRFascicoloSoggetto);
    }

    /*
     * LISTING: used to show the list or roles/subjects during the creation of a subsequent instance
     */
    @Override
	public List<FascicoloRuoloVO> getRuoliFascicolo(Long idFascicolo, MudeTUser user) {

        List<MudeDRuoloSoggetto> allRoles = mudeDRuoloSoggettoRepository.findAll();
        List<FascicoloRuoloVO> resFascicoloRuoloVO = new ArrayList<FascicoloRuoloVO>();

        // get unique ROLE/GUID_SOGGETTO records
        for(MudeRFascicoloRuolo mudeRFascicoloRuolo : mudeRFascicoloRuoloRepository.findAllByFascicolo_Id(idFascicolo)) {
        	// esclude default roles from list
        	MudeDRuoloSoggetto role = allRoles.stream().filter(x -> x.getCodice().equals(mudeRFascicoloRuolo.getRuolo()) ).findFirst().get();
        	if(!role.getVisibile() && role.getBranchObbligatorio() != null)
        		continue;
        	
        	FascicoloRuoloVO other = new FascicoloRuoloVO(); 
        	other.setId(mudeRFascicoloRuolo.getId());
        	other.setRuolo(new SelectVO(mudeRFascicoloRuolo.getRuolo(), role.getDescrizione()));
        	other.setSoggetti(new ArrayList<FascicoloSoggettoVO>());
        	other.setGuidSoggetto(mudeRFascicoloRuolo.getGuidSoggetto());
			FascicoloRuoloVO fascicoloRuoloVO = resFascicoloRuoloVO.stream().filter(x -> x.getRuolo().getId().equals(mudeRFascicoloRuolo.getRuolo()))
        			.findFirst().orElse(other);
        	
        	// add the latest version of contact from active (DATA_FINE IS NULL) unique GUID/ID_USER_INS from FASCICOLO_SOGGETTO
        	FascicoloSoggettoVO fascicoloSoggettoVO = new FascicoloSoggettoVO();
        	
        	List<MudeRFascicoloSoggetto> sogg = findBestMatch(idFascicolo, 
										        			mudeRFascicoloRuolo.getGuidSoggetto(),
															mudeRFascicoloRuolo.getUser().getIdUser());
        	if(sogg == null || sogg.size() == 0) continue;
        	
        	fascicoloSoggettoVO.setId(sogg.get(0) .getId());
        	fascicoloSoggettoVO.setIstanzeSoggetto(new ArrayList<IstanzaVO>());
        	fascicoloSoggettoVO.setContattoFascicolo(
							contattoEntityMapper.mapEntityToSlimVO(
		        				mudeTContattoRepository.findFirstByGuidAndMudeTUser_IdUserAndDataCessazioneNullOrderByIdDesc(
		        					mudeRFascicoloRuolo.getGuidSoggetto(), 
		    						mudeRFascicoloSoggettoRepository.getIdUserInsByIdFascicoloAndGuid(idFascicolo, mudeRFascicoloRuolo.getGuidSoggetto())), 
	    						user));
					// set valid ROLE dates for this subject
        	fascicoloSoggettoVO.setDataInizio(mudeRFascicoloRuolo.getDataInizio());
        	fascicoloSoggettoVO.setDataFine(mudeRFascicoloRuolo.getDataFine());
        	fascicoloRuoloVO.getSoggetti().add(fascicoloSoggettoVO);
        	
        	// add all "istanze" passing through FASCICOLO_SOGGETTO where a subject is found using the current ROLE
            for(MudeTIstanza mudeTIstanza : CollectionUtils.emptyIfNull(
            									mudeTIstanzaRepository.getInstancesByIdFascicoloAndGuidSoggettoAndRuoli(idFascicolo, mudeRFascicoloRuolo.getGuidSoggetto(), mudeRFascicoloRuolo.getRuolo())))
            	fascicoloSoggettoVO.getIstanzeSoggetto().add(istanzaEntityMapper.mapEntityToSlimVO(mudeTIstanza, user));

            if(resFascicoloRuoloVO.indexOf(fascicoloRuoloVO) == -1)
            	resFascicoloRuoloVO.add(fascicoloRuoloVO);
        }

        return resFascicoloRuoloVO;
    }

	private List<MudeRFascicoloSoggetto> findBestMatch(Long idFascicolo, 
														String guidSoggetto,
														Long idUser) {
		List<MudeRFascicoloSoggetto> sogg = mudeRFascicoloSoggettoRepository.findAllByFascicolo_IdAndGuidSoggettoAndUser_IdUserAndDataFineIsNullOrderByIdDesc(idFascicolo,
																				guidSoggetto,
																				idUser);
		if(sogg == null || sogg.size() == 0)
			if((sogg = mudeRFascicoloSoggettoRepository.findAllByFascicolo_IdAndGuidSoggettoAndDataFineIsNullOrderByIdDesc(idFascicolo, guidSoggetto) ) == null || sogg.size() == 0) {
				/* TODO: find a solution to fix broken MudeRFascicoloSoggetto records 
				sogg = new ArrayList();
					
				MudeRIstanzaSoggetto mudeRIstanzaSoggetto = mudeRIstanzaSoggettoRepository.findByContactGuidAndIstanza(idFascicolo, guidSoggetto, idUser);
				if(mudeRIstanzaSoggetto != null) {
					// try to fix the case in which there is no subject in r_fascicolo_soggetto
					MudeRFascicoloSoggetto mudeRFascicoloSoggetto = new MudeRFascicoloSoggetto();
					mudeRFascicoloSoggetto.setFascicolo(mudeTFascicoloRepository.findOne(idFascicolo));
					mudeRFascicoloSoggetto.setGuidSoggetto(guidSoggetto);
					mudeRFascicoloSoggetto.setUser(mudeTUserRepository.findOne(idUser));
					mudeRFascicoloSoggetto.setIstanzaSoggetto(mudeRIstanzaSoggetto);
					mudeRFascicoloSoggetto.setDataInizio(new Date());			   
					mudeRFascicoloSoggettoRepository.saveDAO(mudeRFascicoloSoggetto);
					
					sogg.add(mudeRFascicoloSoggetto);
				}
				*/
			}
		
		return sogg;
	}

    /*
     * IMPORTING SUBJECTS: used to add roles/subjects to the "ISTANZA" after the user selection
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<SoggettoIstanzaVO> retrieveSoggettiDaFascicolo(Long idIstanza, List<RuoloPossibileVO> ruoliPossibili, List<RuoliFascicoloRequest> ruoli, MudeTUser mudeTUser) {
    	List<SoggettoIstanzaVO> listSoggettoIstanzaVO = new ArrayList<SoggettoIstanzaVO>();

        // collects all MudeRFascicoloSoggetto involved in the role list
        for(RuoliFascicoloRequest ruolo : CollectionUtils.emptyIfNull(ruoli)) {
            for(Long idFascicoloSoggetto : ruolo.getIdSoggettiFascicolo()) {
            	MudeRFascicoloSoggetto mudeRFascicoloSoggetto = mudeRFascicoloSoggettoRepository.getAllByIdAndDataFineIsNull(idFascicoloSoggetto);

            	List<SelectVO> userRoles;
            	SoggettoIstanzaVO soggettoIstanzaVO = listSoggettoIstanzaVO.stream()
            			.filter(element -> element.getContatto().getGuid().equals(mudeRFascicoloSoggetto.getGuidSoggetto())).findFirst().orElse(null);
            	if(soggettoIstanzaVO == null) {
	        		listSoggettoIstanzaVO.add(soggettoIstanzaVO = new SoggettoIstanzaVO());
	        		
	        		soggettoIstanzaVO.setRuoloSoggetto(userRoles = new ArrayList<SelectVO>());
	        		soggettoIstanzaVO.setIdIstanza(idIstanza);
	            	
	            	soggettoIstanzaVO.setContatto(contattoEntityMapper.mapEntityToVO(
    						mudeTContattoRepository.findFirstByGuidAndMudeTUser_IdUserAndDataCessazioneNullOrderByIdDesc(
    								mudeRFascicoloSoggetto.getGuidSoggetto(), mudeRFascicoloSoggetto.getUser().getIdUser()), 
    						mudeTUser));
            	}
            	else
            		userRoles = soggettoIstanzaVO.getRuoloSoggetto();
            	
            	RuoloPossibileVO role = ruoliPossibili.stream().filter(r -> r.getId().equals(ruolo.getIdRuolo())).findFirst().orElse(null);
            	if(role != null)
            		userRoles.add(new SelectVO(role.getId(), role.getDenominazione()));
            }
        }

        // add all soggettoIstanzaVO
        for(SoggettoIstanzaVO soggettoIstanzaVO : listSoggettoIstanzaVO) {
        	JSONArray possibleRoles = new JSONArray();
        	for(RuoloPossibileVO role : ruoliPossibili)
	        	try {
	        		JSONObject jsonObject = new JSONObject();
	        		jsonObject.put("id", role.getId());
	        		jsonObject.put("value", role.getDenominazione());
	        		jsonObject.put("checked", ruoli != null && ruoli.stream().anyMatch(r -> r.getIdRuolo().equals(role.getId())));
					possibleRoles.put(jsonObject);
	            } catch (JSONException skipSetRole) { }

    		soggettoIstanzaVO.setRuoliPossibili(possibleRoles.toString());
        }

        return listSoggettoIstanzaVO;
    }

	public void recoverMigrazioneSoggettiInFascicolo() {
        String idIstanzaSoggetti2Recover = mudeCProprietaRepository.getValueByName("RECOVER_SOGGETTI_FASCICOLO_IDISTANZE", "");
        if(StringUtils.isNotBlank(idIstanzaSoggetti2Recover)) {
        	for(String idIstanza : idIstanzaSoggetti2Recover.split(",")) {
				try {
	        		MudeTIstanza mudeTIstanza = mudeTIstanzaRepository.findOne(Long.parseLong(idIstanza.trim()));
	        		if(mudeTIstanza == null)
	        			return;
			        		
	        		//migrazioneSoggettiInFascicolo(mudeTIstanza, mudeTIstanza.getMudeTUser());
				} catch (Exception skipAll) {
					logger.error("[FascicoloSoggettoServiceImpl::recoverMigrazioneSoggettiInFascicolo] exception for idIstanza: " + idIstanza, skipAll);
				}
        	}

        	MudeCProprieta mudeCProprieta = mudeCProprietaRepository.findByName("RECOVER_SOGGETTI_FASCICOLO_IDISTANZE").get();
        	mudeCProprieta.setValore("");
        	mudeCProprietaRepository.saveDAO(mudeCProprieta);
        }
	}
    
	/*
	 * POPULATING: used to add the records in FASCICOLO_RUOLO/FASCICOLO_SOGGETTO when the "ISTANZA" change state to "REGISTRATA DA PA"
	 */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
	public void migrazioneSoggettiInFascicolo(MudeTIstanza mudeTIstanza, MudeTUser mudeTUser) {
		Long idIstanza = mudeTIstanza.getId();
		Long idFascicolo = mudeTIstanza.getMudeTFascicolo().getId();
		
		// loop for jsonData "QDR_SOGGETTO_COINV.subjectsRoleDismissed" in order to dismiss the roles in folder
		try {
			JSONObject userRolesDismissed = getUserRolesDismissed(mudeTIstanza);
			if(userRolesDismissed != null)
				for(Iterator it = userRolesDismissed.keys(); it.hasNext(); ) { 
					String cf_piva = (String)it.next();

					JSONArray roles = (JSONArray)userRolesDismissed.get(cf_piva);
					for(int j=0; j<roles.length(); j++) {
						MudeRFascicoloRuolo mudeRFascicoloRuolo = mudeRFascicoloRuoloRepository.findAllByFascicolo_IdAndGuidSoggettoAndRuoloAndDataFineIsNull(idFascicolo, cf_piva, roles.getString(j));
						if(mudeRFascicoloRuolo == null)
							continue;

						// dismiss role
						mudeRFascicoloRuolo.setDataFine(new Date());
						mudeRFascicoloRuoloRepository.saveDAO(mudeRFascicoloRuolo);
					}
				}
		}
		catch(JSONException skip) {}
		   
		// copies all mudeopen_t_istanza_soggetto (for istanza) to mudeopen_t_fascicolo_soggetto
		for(MudeRIstanzaSoggetto mudeRIstanzaSoggetto : CollectionUtils.emptyIfNull(mudeRIstanzaSoggettoRepository.findByMudeTIstanza(mudeTIstanza))) {
			MudeTContatto mudeTContatto = mudeRIstanzaSoggetto.getMudeTContatto();

			// set DATA_FINE=NULL in case there prev contacts from other users
			
        	List<MudeRFascicoloSoggetto> sogg = findBestMatch(idFascicolo, 
																mudeTContatto.getGuid(),
																mudeTContatto.getMudeTUser().getIdUser());
    		MudeRFascicoloSoggetto mudeRFascicoloSoggetto = null;
        	if(sogg != null && sogg.size() > 0)
        		mudeRFascicoloSoggetto = sogg.get(0);
        	
			if(mudeRFascicoloSoggetto != null && mudeTContatto.getMudeTUser().getIdUser() != mudeRFascicoloSoggetto.getUser().getIdUser()) {
				mudeRFascicoloSoggettoRepository.dismissPrevByGuid(idFascicolo, mudeTContatto.getGuid());
				mudeRFascicoloSoggetto = null;
			}
			   
			if(mudeRFascicoloSoggetto == null) {
				// create new FASCICOLO_SOGGETTO record
				mudeRFascicoloSoggetto = new MudeRFascicoloSoggetto();
				mudeRFascicoloSoggetto.setFascicolo(mudeTIstanza.getMudeTFascicolo());
				mudeRFascicoloSoggetto.setGuidSoggetto(mudeTContatto.getGuid());
				mudeRFascicoloSoggetto.setUser(mudeTContatto.getMudeTUser());
				mudeRFascicoloSoggetto.setIstanzaSoggetto(mudeRIstanzaSoggetto);
				mudeRFascicoloSoggetto.setDataInizio(new Date());			   
				mudeRFascicoloSoggettoRepository.saveDAO(mudeRFascicoloSoggetto);
			}
			
			// store roles in FASCICOLO_RUOLO
			for(String role : mudeRIstanzaSoggetto.getRuoli()) {
				// store folder owner for role IN
                if("IN".equalsIgnoreCase(role)) {
                    fascicoloIntestatarioService.saveFascicoloIntestatario(mudeTIstanza, mudeTContatto.getId(), mudeTUser, false);

    				MudeRFascicoloRuolo mudeRFascicoloRuoloInt = mudeRFascicoloRuoloRepository.findAllByFascicolo_IdAndRuoloAndDataFineIsNull(idFascicolo, role);
    				// is new instance owner? then dismiss the old owner and assign the ownership to the new contact 
    				if(mudeRFascicoloRuoloInt != null
    									&& !mudeTContatto.getGuid().equalsIgnoreCase(mudeRFascicoloRuoloInt.getGuidSoggetto())) {
    					mudeRFascicoloRuoloInt.setDataFine(new Date());
    					mudeRFascicoloRuoloRepository.saveDAO(mudeRFascicoloRuoloInt);
    				}
                }

				MudeRFascicoloRuolo mudeRFascicoloRuolo = mudeRFascicoloRuoloRepository.findAllByFascicolo_IdAndGuidSoggettoAndRuoloAndDataFineIsNull(idFascicolo, mudeTContatto.getGuid(), role);
				if(mudeRFascicoloRuolo == null) {
					// create new record
					MudeRFascicoloRuolo createMudeRFascicoloRuolo = new MudeRFascicoloRuolo();
					createMudeRFascicoloRuolo.setRuolo(role);
					createMudeRFascicoloRuolo.setFascicolo(mudeTIstanza.getMudeTFascicolo());
					createMudeRFascicoloRuolo.setUser(mudeTContatto.getMudeTUser());
					createMudeRFascicoloRuolo.setGuidSoggetto(mudeTContatto.getGuid());
					createMudeRFascicoloRuolo.setDataInizio(new Date());
					mudeRFascicoloRuoloRepository.saveDAO(createMudeRFascicoloRuolo);
				}
//				else if(mudeRFascicoloRuolo.getDataFine() != null) {
//					// set DATA_FINE to null in order to re-activate the role that was dismissed
//					mudeRFascicoloRuolo.setDataFine(null);
//					mudeRFascicoloRuoloRepository.saveDAO(mudeRFascicoloRuolo);
//				}
			}
		}

	}

	
    protected JSONObject getUserRolesDismissed(MudeTIstanza mudeTIstanza) throws JSONException {
        JSONObject jsonDataIstanza = new JSONObject(mudeTIstanza.getJsonData());

        if(jsonDataIstanza.has("QDR_SOGGETTO_COINV")) {
            JSONObject jsonDataCoinv = (JSONObject)jsonDataIstanza.get("QDR_SOGGETTO_COINV");
            if(jsonDataCoinv.has("subjectsRoleDismissed"))
                return (JSONObject)jsonDataCoinv.get("subjectsRoleDismissed");
        }

        return null;
    }
	

}