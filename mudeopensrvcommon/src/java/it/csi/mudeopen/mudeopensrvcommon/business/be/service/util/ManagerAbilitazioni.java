/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.util;

import javax.ws.rs.core.HttpHeaders;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeCProprieta;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaUtenteQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTemplateTipoAllegato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRFascicoloUtenteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaStatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaUtenteQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaUtenteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRTemplateTipoAllegatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.UserUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.StatoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.AbilitazioniEnum;

@Component
public class ManagerAbilitazioni {
    private static Logger logger = Logger.getLogger(ManagerAbilitazioni.class.getCanonicalName());

    private static final String LEASETIME_PROP_NAME = "QUADRO_LEASE_TIME_MILLIS";
    private static final long LEASETIME_IN_MILLIS = 2 /*min*/ * 60 /*sec*/ * 1000;

    @Autowired
    protected UserUtil userUtil;

    @Autowired
    private MudeTIstanzaRepository mudeTIstanzaRepository;

    @Autowired
    private MudeCProprietaRepository mudeCProprietaRepository;

    @Autowired
    private MudeRIstanzaUtenteRepository mudeRIstanzaUtenteRepository;

    @Autowired
    private MudeRFascicoloUtenteRepository mudeRFascicoloUtenteRepository;

    @Autowired
    private MudeRTemplateTipoAllegatoRepository mudeRTemplateTipoAllegatoRepository;

    @Autowired
    private MudeRIstanzaUtenteQuadroRepository mudeRIstanzaUtenteQuadroRepository;

    @Autowired
    private MudeRIstanzaStatoRepository mudeRIstanzaStatoRepository;

    @Autowired
    private MudeDTipoQuadroRepository mudeDTipoQuadroRepository;

    public Boolean hasUtenteAbilitazioneFunzionePerFascicolo(String codiceFunzione, Long idFascicolo, Long idUtente){
        return mudeRFascicoloUtenteRepository.hasFunzionePerFascicolo(new String[] { codiceFunzione },idFascicolo,idUtente);
    }

    public Boolean hasUtenteAbilitazioneFunzionePerFascicolo(String[] codiceFunzione, Long idFascicolo, Long idUtente){
        return mudeRFascicoloUtenteRepository.hasFunzionePerFascicolo(codiceFunzione,idFascicolo,idUtente);
    }

    public Boolean hasUtenteAbilitazionePerFascicolo(String codiceFunzione, Long idFascicolo, Long idUtente){
        return mudeRFascicoloUtenteRepository.hasAbilitazionePerFascicolo(new String[] { codiceFunzione },idFascicolo,idUtente);
    }

    public Boolean hasUtenteAbilitazionePerFascicolo(String[] codiceFunzione, Long idFascicolo, Long idUtente){
        return mudeRFascicoloUtenteRepository.hasAbilitazionePerFascicolo(codiceFunzione,idFascicolo,idUtente);
    }

    public Boolean hasUtenteAbilitazioneFunzionePerIstanza(boolean jdataModify, String codiceFunzione, Long idIstanza, MudeTUser mudeTUser, HttpHeaders httpHeaders){
        return hasUtenteAbilitazioneFunzionePerIstanza(jdataModify, new String[]{codiceFunzione}, idIstanza, mudeTUser, httpHeaders);
    }

    public Boolean hasUtenteAbilitazioneFunzionePerIstanza(boolean jdataModify, String[] codiciFunzione, Long idIstanza, MudeTUser mudeTUser, HttpHeaders httpHeaders) {
        return isBOUserEnabledToInstance(idIstanza, mudeTUser) 
        		|| ((!jdataModify || isIstanzaLockedByOtherUser(idIstanza, mudeTUser, httpHeaders)) 
        				&& mudeRIstanzaUtenteRepository.hasFunzionePerIstanza(codiciFunzione, idIstanza, mudeTUser.getIdUser()));
    }

    public Boolean hasUtenteAbilitazioneFunzionePerIstanzaQuadro(boolean jdataModify, Long idQuadro, Long idIstanza, MudeTUser mudeTUser, HttpHeaders httpHeaders){
        return isBOUserEnabledToInstance(idIstanza, mudeTUser) 
        		|| ((!jdataModify || isIstanzaLockedByOtherUser(idIstanza, mudeTUser, httpHeaders))
        				&& mudeRIstanzaUtenteRepository.hasFunzionePerIstanzaQuadro(idQuadro, idIstanza, mudeTUser.getIdUser()));
    }

    public Boolean hasUtenteAbilitazionePerIstanza(boolean jdataModify, String codiceAbilitazione, Long idIstanza, MudeTUser mudeTUser, HttpHeaders httpHeaders){
        return hasUtenteAbilitazionePerIstanza(jdataModify, new String[] { codiceAbilitazione }, idIstanza, mudeTUser, httpHeaders);
    }

    public Boolean hasUtenteAbilitazionePerIstanza(boolean jdataModify, String[] codiciAbilitazione, Long idIstanza, MudeTUser mudeTUser, HttpHeaders httpHeaders){
        return isBOUserEnabledToInstance(idIstanza, mudeTUser) 
        		|| ((!jdataModify || isIstanzaLockedByOtherUser(idIstanza, mudeTUser, httpHeaders)) 
        				&& mudeRIstanzaUtenteRepository.findByIstanza_IdAndAbilitazione_CodiceAndUtente_IdUserAndDataFineIsNull(codiciAbilitazione, idIstanza, mudeTUser.getIdUser()));
    }

    private boolean isBOUserEnabledToInstance(Long idIstanza, MudeTUser mudeTUser) {
    	if(idIstanza == null || mudeTUser == null) return false;
    	
    	return userUtil.isBOContext()
    				&& mudeTUser.getUtenteBo() != null && mudeTUser.getUtenteBo() == true
    				&& mudeRIstanzaUtenteRepository.hasBoUserHasInstanceVisibility(idIstanza, mudeTUser.getIdUser());
    }

    public Boolean hasUtenteAbilitazioneAllegatiPerIstanza(String codiceAllegato, long idIstanza, long idTemplate, MudeTUser mudeTUser, HttpHeaders httpHeaders){
    	/*
    	 * JIRA-176: condition removed to allow upload just with UPLOAD_ALLEG permission
        if(isUserCreatorOrPM(idIstanza, mudeTUser, httpHeaders)) 
        	return Boolean.TRUE;

		return isAttachCodeAllowed(codiceAllegato, idIstanza, idTemplate, mudeTUser, httpHeaders);
    	 */

       return true;
    }

    private Boolean isAttachCodeAllowed(String codiceAllegato, long idIstanza, long idTemplate, MudeTUser mudeTUser, HttpHeaders httpHeaders) {
        MudeRTemplateTipoAllegato tipoAllegato = mudeRTemplateTipoAllegatoRepository.findByTemplate_IdTemplateAndTipoAllegato_Codice(idTemplate, codiceAllegato);
        if(!tipoAllegato.getObbligatorio())
        	return true;

        String espressione = tipoAllegato.getEspressioneObbligatorieta();

        if(StringUtils.isNotBlank(espressione))
            for (MudeRIstanzaUtenteQuadro mudeRIstanzaUtenteQuadro : 
            			mudeRIstanzaUtenteQuadroRepository.findAllByMudeRIstanzaUtente_Istanza_IdAndMudeRIstanzaUtente_Utente_IdUserAndDataFineIsNull(idIstanza, mudeTUser.getIdUser()))
                if(espressione.contains(mudeRIstanzaUtenteQuadro.getMudeDQuadro().getTopMostCodiceTipoQuadro()))
                    return Boolean.TRUE; // allowed only if relation with "espressione_obbligatorieta" has been found

        return Boolean.FALSE;
    }

    public Boolean isUserCreatorOrPM(long idIstanza, MudeTUser mudeTUser, HttpHeaders httpHeaders) {
        return hasUtenteAbilitazionePerIstanza(true, new String[] {
	        	AbilitazioniEnum.CREATORE_IST_PM_OPZ.getDescription(),
	        	AbilitazioniEnum.CREATORE_IST_PM_OBB.getDescription(),
	        	AbilitazioniEnum.PM_RUP_PM_OPZ.getDescription(), 	
	        	AbilitazioniEnum.PM_RUP_PM_OBB.getDescription(),
        		/*AbilitazioniEnum.COLLAB_PM_RUP_PM_OBB.getDescription(),
        		AbilitazioniEnum.COLLAB_PM_RUP_PM_OPZ.getDescription()*/ 
	        }, idIstanza, mudeTUser, httpHeaders);
    }

    // checks if the user has the permission to modify "templateQuadro"
    public Boolean canUserChangeQuadro(boolean jdataModify, 
    		Long idIstanza, 
    		MudeTUser mudeTUser, 
    		HttpHeaders httpHeaders, 
    		Boolean isObbligatoriaNominaPM, 
    		Long idQuadro, String codTipoQuadro) {
    	if(mudeTUser == null)
    		mudeTUser = userUtil.getUserCF(httpHeaders, false);
    			
    	if(mudeRIstanzaStatoRepository.isInstanceInState(idIstanza, 
						new String[] { 
								StatoIstanza.DEPOSITATA.getValue(), 
								StatoIstanza.PRESA_IN_CARICO.getValue(), 
								StatoIstanza.REGISTRATA_DA_PA.getValue() }) > 0)
        	return false;

    			
    	// check if there is a specific permission for idQuadro 
        if(hasUtenteAbilitazioneFunzionePerIstanzaQuadro(jdataModify, idQuadro, idIstanza, mudeTUser, httpHeaders))
        	return true;

        String[] permRequiredForQuadro = mudeDTipoQuadroRepository.getAllFunctionsByCodTipoQuadro(codTipoQuadro);

        if(permRequiredForQuadro.length == 0) // ok if PM, or not creatore in case no PM is required
            return mudeRIstanzaUtenteRepository.findByIstanza_IdAndAbilitazione_CodiceAndUtente_IdUserAndDataFineIsNull(new String[] { 
			    			AbilitazioniEnum.CREATORE_IST_PM_OPZ.getDescription(),
			    			AbilitazioniEnum.PM_RUP_PM_OPZ.getDescription(),
			    			AbilitazioniEnum.PM_RUP_PM_OBB.getDescription(),
			    	}, idIstanza, mudeTUser.getIdUser());

        // check if user permission (through the abilitation) for which there is no quadro selection required  
        return mudeRIstanzaUtenteRepository.hasPermissionWithNoQuadroSpecific(new String[] { 
		    			AbilitazioniEnum.CREATORE_IST_PM_OPZ.getDescription(),
		    			AbilitazioniEnum.CREATORE_IST_PM_OBB.getDescription(),
		    			AbilitazioniEnum.PM_RUP_PM_OPZ.getDescription(),
		    			AbilitazioniEnum.PM_RUP_PM_OBB.getDescription(),
		    	},
        		permRequiredForQuadro, 
        		idIstanza, 
        		mudeTUser.getIdUser());
    }

    private boolean isIstanzaLockedByOtherUser(Long idIstanza, 
					    						MudeTUser mudeTUser,
					    						HttpHeaders httpHeaders) {
    	// if there is no valid tipoquadroXRequestID passed from client, then consider the API not for a specific quadro    	
    	String codiceTipoQuadro = httpHeaders.getHeaderString(Constants.HEADER_MUDE_TIPO_QUADRO);
    	if(codiceTipoQuadro == null) 
    		return true;
    	
    	String jdata = mudeTIstanzaRepository.getJDataByIdIstanza(idIstanza);
    	
        JSONObject jsonDataIstanza = new JSONObject();
        if(jdata != null)
        	try { jsonDataIstanza = new JSONObject(jdata); } catch (Exception skipAndDeny) { return false; }
    	
    	return preventChangesFromOtherUsers(jsonDataIstanza, codiceTipoQuadro, mudeTUser);
    }

    // prevents different users to make changes for the Quadro where "strJSONDataQuadro" has been submitted
    public boolean preventChangesFromOtherUsers(JSONObject jsonDataIstanza, String codiceTipoQuadro, MudeTUser mudeTUser) {
    	try {
    		JSONObject mainQuadroJDATA = new JSONObject();
    		if(jsonDataIstanza.has(codiceTipoQuadro))
	            mainQuadroJDATA = (JSONObject)jsonDataIstanza.get(codiceTipoQuadro);
    		
        	// extract info about the last user who changed the main quadro
        	String lastUser = !mainQuadroJDATA.has("_USER_MOD_LOCK")?null:mainQuadroJDATA.getString("_USER_MOD_LOCK");
        	String lastUserName = !mainQuadroJDATA.has("_USER_MOD_NAME")?null:mainQuadroJDATA.getString("_USER_MOD_NAME");
        	Long lastTimestamp = !mainQuadroJDATA.has("_USER_MOD_TIME")?null:(mainQuadroJDATA.getLong("_USER_MOD_TIME"));
        	
        	// extract info about the last user who changed the main quadro
        	if(lastUser != null 
        			&& lastUser.hashCode() != mudeTUser.getCf().hashCode()
        			&& System.currentTimeMillis() - lastTimestamp < getCachedLeaseTime()) {
                throw new BusinessException("Il quadro e' stato modificato di recente dall'utente '"+lastUserName+"'. "
                		//+ "Prima di modificare questo quadro è necessario aspettare almeno: " + (new SimpleDateFormat("mm' minuti'")).format(new Date(System.currentTimeMillis() - lastTimestamp))
                		);
        	}
        	
        	// all checks passed: set _USER_MOD_LOCK (=last user to lock the quadro) 
        	// and _USER_MOD_TIME (= last time data as been modified).
        	// _USER_MOD_LEASE set how long the lock is valid
    		jsonDataIstanza.put(codiceTipoQuadro, setNewLeaseTime(mainQuadroJDATA, mudeTUser));

		} catch (BusinessException be) {
			throw be;
		} catch (JSONException e) {
            LoggerUtil.debug(logger, "preventChangesFromOtherUsers: UNEXPECTED EXCEPTION: " + e);
        	return false;
		}
    	
    	return true;
    }

    public JSONObject setNewLeaseTime(JSONObject mainQuadroJDATA, MudeTUser mudeTUser) {
    	try {
	    	mainQuadroJDATA.put("_USER_MOD_LOCK", mudeTUser.getCf());
	    	mainQuadroJDATA.put("_USER_MOD_NAME", mudeTUser.getNome() + " " + mudeTUser.getCognome());
	    	mainQuadroJDATA.put("_USER_MOD_TIME", System.currentTimeMillis());
	    	mainQuadroJDATA.put("_USER_MOD_LEASE", getCachedLeaseTime());
		} catch (JSONException e) {
	        LoggerUtil.debug(logger, "preventChangesFromOtherUsers: UNEXPECTED EXCEPTION: " + e);
		}
    	
    	return mainQuadroJDATA;
    }

    private long leaseTime = 0;
    private long getCachedLeaseTime() {
    	if(leaseTime != 0) return leaseTime;

    	String sLease = mudeCProprietaRepository.findByName(LEASETIME_PROP_NAME).orElse(new MudeCProprieta()).getValore();
    	if(sLease == null)
    		return LEASETIME_IN_MILLIS;

    	return leaseTime = Long.parseLong(sLease);
    }

}