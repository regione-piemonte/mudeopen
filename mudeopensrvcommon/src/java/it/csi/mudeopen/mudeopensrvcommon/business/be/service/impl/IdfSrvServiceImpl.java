/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDDug;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRContattoQualifica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTAllegato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIndirizzo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTPraticaIdf;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocCatasto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.IDF.MudeIdfManager;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.IDF.swagger.model.CreaIstanzaVincoloBody;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.IDF.swagger.model.FileProcuraSpeciale;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.IDF.swagger.model.IstanzaMude;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.IDF.swagger.model.Soggetto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.IDF.swagger.model.Ubicazione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon.IstanzaApiServiceHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ContattoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IstanzaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaEnteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDComuneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDDugRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDRuoloSoggettoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaStatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTAllegatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTAllegatoSlimRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTContattoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTEnteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaSlimRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzeExtRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTPraticaIdfRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTPraticaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeopenRLocCatastoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IdfService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.IndexManager;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.StatiProcessoIdfEnum;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class IdfSrvServiceImpl implements IdfService {
	private static Logger logger = Logger.getLogger(IdfSrvServiceImpl.class.getCanonicalName());

    @Autowired
    private MudeCProprietaRepository mudeCProprietaRepository;
    
    @Autowired
    private MudeCProprietaEnteRepository mudeCProprietaEnteRepository;
    
    @Autowired
    private MudeIdfManager mudeIdfManager;
    
    @Autowired
    private MudeTAllegatoSlimRepository mudeTAllegatoSlimRepository;
    
    @Autowired
    private MudeTPraticaIdfRepository mudeTPraticaIdfRepository;
    
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
    private MudeDComuneRepository mudeDComuneRepository;
    
    @Autowired
    private MudeopenRLocCatastoRepository mudeopenRLocCatastoRepository;
    
    @Autowired
    private MudeTAllegatoRepository mudeTAllegatoRepository;
    
	@Override
	public void processAllJobs() {
		int maxRetries = Integer.parseInt(mudeCProprietaRepository.getValueByName("IDF_MAX_RETRY", "3"));
		
		logger.info("[IdfServiceImpl::processAllPraticaIdf] IDF JOB STARTED: " + maxRetries);
		
		for(MudeTPraticaIdf mudeTPraticaIdf 
					: mudeTPraticaIdfRepository.findAllByCodiceStatoIdfIsNullOrNotInCodiceStatoIdfAndRetriesLessThan(new String[] { StatiProcessoIdfEnum.IN_PROCESSO.toString() }, maxRetries)) {
			MudeTIstanza mudeTIstanza = mudeTIstanzaRepository.findOne(mudeTPraticaIdf.getIdIstanza());
			MudeTIstanza mudeTIstanzaRef = null;
			
			MudeTPraticaIdf mudeTPraticaIdfParent = null;
			if(mudeTIstanza.getIdIstanzaRiferimento() != null) {
				mudeTPraticaIdfParent = mudeTPraticaIdfRepository.findByIdIstanza(mudeTIstanza.getIdIstanzaRiferimento());
				
				if(mudeTPraticaIdfParent != null)
					mudeTIstanzaRef = mudeTIstanzaRepository.findOne(mudeTIstanza.getIdIstanzaRiferimento());
			}
			
			if(!handlePraticaIdf(mudeTPraticaIdf, 
					mudeTIstanza,
					mudeTIstanzaRef,
					mudeTPraticaIdfParent))
				mudeTPraticaIdfRepository.increaseRetryCounter(mudeTPraticaIdf.getId());
		}
	}

	@Transactional
	private boolean handlePraticaIdf(
									final MudeTPraticaIdf mudeTPraticaIdf, 
									final MudeTIstanza istanza,
									final MudeTIstanza istanzaRiferimento,
									final MudeTPraticaIdf mudeTPraticaIdfParent) {
    	try {
		    Long idIstanza = istanza.getId(); 
		    
		    
		    if((mudeTPraticaIdf.getCodiceStatoIdf() == null
			    			|| StatiProcessoIdfEnum.IN_CODA.toString().equals(mudeTPraticaIdf.getCodiceStatoIdf()))) {
		    	
		    	CreaIstanzaVincoloBody payloadObj = new CreaIstanzaVincoloBody();
		    	
		    	payloadObj.setDatiIstanza(new IstanzaMude() {{
		    		setId(""+istanza.getId());
		    		setCodice(istanza.getCodiceIstanza());
		    		setCompetenza(mudeTPraticaIdf.getCompetenza());
		    		setTipo(istanza.getTipoIstanza().getCodice());
		    		setDescrizioneIntervento(mudeTIstanzaRepository.retrieveInterventoFromIdIstanza(idIstanza).split("~")[1]);
		    	}});
		    	
	    		MudeTContatto intestatario = mudeTContattoRepository.getIntestatarioNameByIdIstanza(idIstanza);
		    	payloadObj.setRichiedente(new Soggetto() {{
					setCognome(intestatario.getCognome());
					setNome(intestatario.getNome());
					setTipoPersona(intestatario.getTipoContatto().getValue().toUpperCase());
					
					if("PG".equalsIgnoreCase(getTipoPersona())) {
						if(StringUtils.isNotBlank(intestatario.getCf()))
							setCodiceFiscale(intestatario.getCf());
						else
							setCodiceFiscale(intestatario.getPiva());
						
						setPiva(intestatario.getPiva());
					}
					else
						setCodiceFiscale(intestatario.getCf());
					
					setRagioneSociale(intestatario.getRagioneSociale());
					
					if(intestatario.getIndirizzi() != null 
							&& intestatario.getIndirizzi().size() > 0) {
						MudeTIndirizzo mudeTIndirizzo = intestatario.getIndirizzi().get(0);
		                MudeDDug dug = mudeDDugRepository.findByIdDug(mudeTIndirizzo.getIdDug());
		                if(null != dug)
		                	setIndirizzo(dug.getDenominazione() + " " + mudeTIndirizzo.getIndirizzo());
		                else
		                	setIndirizzo(mudeTIndirizzo.getIndirizzo());
		                
						setCivico(mudeTIndirizzo.getNumeroCivico());
						setComune(mudeTIndirizzo.getMudeDComune().getDenomComune());
						setIstatIndirizzo(mudeTIndirizzo.getMudeDComune().getIstatComune());
					}
					
					String mail = intestatario.getMail();
					if(StringUtils.isBlank(mail))
						mail = intestatario.getPec();
					setEmail(mail);
					setPec(intestatario.getPec());
					setTelefono(intestatario.getTelefono());
		    	}});
		    	
	    		//MudeTContatto delegato = mudeTContattoRepository.findRT(idIstanza, intestatario.getGuid());
		    	MudeTContatto delegato = mudeTContattoRepository.findByIdUser(istanza.getMudeTUser().getIdUser());
	    		if(delegato != null) {
			    	payloadObj.setDelegato(new Soggetto() {{
						setCognome(delegato.getCognome());
						setNome(delegato.getNome());
						setTipoPersona(delegato.getTipoContatto().getValue().toUpperCase());
						
						if("PG".equalsIgnoreCase(getTipoPersona())) {
							if(StringUtils.isNotBlank(delegato.getCf()))
								setCodiceFiscale(delegato.getCf());
							else
								setCodiceFiscale(delegato.getPiva());
							
							setPiva(delegato.getPiva());
						}
						else
							setCodiceFiscale(delegato.getCf());
						
						setRagioneSociale(delegato.getRagioneSociale());
						
						if(delegato.getIndirizzi().size() > 0) {
							MudeTIndirizzo mudeTIndirizzo = delegato.getIndirizzi().get(0);
			                MudeDDug dug = mudeDDugRepository.findByIdDug(mudeTIndirizzo.getIdDug());
			                if(null != dug)
			                	setIndirizzo(dug.getDenominazione() + " " + mudeTIndirizzo.getIndirizzo());
			                else
			                	setIndirizzo(mudeTIndirizzo.getIndirizzo());
			                
							setCivico(mudeTIndirizzo.getNumeroCivico());
							setComune(mudeTIndirizzo.getMudeDComune().getDenomComune());
							setIstatIndirizzo(mudeTIndirizzo.getMudeDComune().getIstatComune());
						}
						
						String mail = delegato.getMail();
						if(StringUtils.isBlank(mail))
							mail = delegato.getPec();
						setEmail(mail);
						setPec(delegato.getPec());
						setTelefono(delegato.getTelefono());
						
						for(MudeRContattoQualifica MmudeRContattoQualifica : CollectionUtils.emptyIfNull(delegato.getQualifiche())) {
							setQualifica(MmudeRContattoQualifica.getMudeDQualifica().getDenominazione());
							break;
						}
			    	}});
	    		}
	    		
	    		for(MudeopenRLocCatasto mudeopenRLocCatasto : mudeopenRLocCatastoRepository.findAllByIdIstanza(idIstanza)) {
	    			payloadObj.getUbicazioni().add(new Ubicazione() {{
						setComune(mudeopenRLocCatasto.getDescFonteCatasto().split("~")[0]);
						setCodiceIstat(mudeopenRLocCatasto.getDescFonteCatasto().split("~")[1]);
						setSezione(mudeopenRLocCatasto.getSezione());
						setFoglio(mudeopenRLocCatasto.getFoglio());
						setParticella(mudeopenRLocCatasto.getParticella());
	    			}});
	    		}
		    	
	    		for(MudeTAllegato mudeTAllegato : mudeTAllegatoRepository.findAllByIstanza_IdAndTipoAllegato_CodiceAndDataFineIsNull(idIstanza, "ATT001" /* procura speciale */)) {
	    			// only one should be allowed!
			    	payloadObj.setFileProcuraSpeciale(new FileProcuraSpeciale() {{
			    		setNomeFile(mudeTAllegato.getNomeFileAllegato());
			    		setMimeType(mudeTAllegato.getMimetype());
			    		
    			        File file = indexManager.getFileByUuid(mudeTAllegato.getFileUID());
			    		//setContenutoFisico(file);
    			        String encoded = Base64.encodeBytes(FileUtils.readFileToByteArray(file));
    			        setContenutoFisico(encoded);
			    	}});
	    		}
			    	
	    		mudeIdfManager.insertNewPractice(mudeTPraticaIdf, payloadObj);
		    	
			    mudeTPraticaIdf.setRetries(0);
			    mudeTPraticaIdf.setIdIstanza(idIstanza);
			    mudeTPraticaIdf.setCodiceStatoIdf(StatiProcessoIdfEnum.IN_PROCESSO.toString());
			    mudeTPraticaIdfRepository.saveDAO(mudeTPraticaIdf);
		    }

			logger.info(String.format("[%s::%s] IDF OK: " + mudeTPraticaIdf.getId(), getClass().getSimpleName(), Thread.currentThread().getStackTrace()[0].getMethodName()));
			return true;
			
		} catch (Exception e) {
			logger.error(String.format("[%s::%s] IDF ERROR " + mudeTPraticaIdf.getId(), getClass().getSimpleName(), Thread.currentThread().getStackTrace()[0].getMethodName()), e);
			
	    	mudeTPraticaIdf.setJsonResponse(StringUtils.defaultString(mudeTPraticaIdf.getJsonResponse(), "") + 
	    			"["+(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()))+"] " + "EXCEPTION: " + e.getMessage() + "\r\n\r\n");
	    	
		    mudeTPraticaIdfRepository.saveDAO(mudeTPraticaIdf);
		}
    	
		return false;
	}

}

