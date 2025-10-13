/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDComune;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDSpeciePratica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTitolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRRuoloFunzione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRUtenteEnteComuneRuolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTDatiIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTEnte;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIndirizzo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanzaSlim;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTPraticaCosmo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.GeoriferimentoUpdateTableService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ComuneEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ContattoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.EnteEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.FascicoloEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IndirizzoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IstanzaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IstanzaSlimEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IstanzaStatoSlimEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ProvinciaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.UtenteEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.UtenteFunzioneEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaEnteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDComuneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDPPayImportiRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDRuoloSoggettoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDSpeciePraticaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTitoloRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaSoggettoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaStatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRRuoloFunzioneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRUtenteEnteComuneRuoloRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTContattoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTEnteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIndirizzoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaSlimRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTPraticaCosmoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTPraticaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTUserRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.ManagerAbilitazioni;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.UtilsDate;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.IndirizzoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.StatoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoIstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoRuoloUtente;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.AbilitazioniEnum;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.FunzioniAbilitazioniEnum;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.StatoIstanzaSlimVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ComuneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ProvinciaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.pratica.PraticaSlimVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.FunzioneUtenteVO;

@Component
public class IstanzaSlimEntityMapperImpl implements IstanzaSlimEntityMapper {

	static final Map<String, SelectVO> tipoIstanzaCache = new HashMap<>();
	static final Map<String, DizionarioVO> speciePraticaCache = new HashMap<>();
	
    @Autowired
    MudeRIstanzaStatoRepository mudeRIstanzaStatoRepository;
    @Autowired
    private UtilsDate utilsDate;
    @Autowired
    private MudeDTitoloRepository mudeDTitoloRepository;
    @Autowired
    private MudeTIstanzaRepository mudeTIstanzaRepository;
    @Autowired
    private MudeTUserRepository mudeTUserRepository;
    @Autowired
    private ContattoEntityMapper contattoEntityMapper;

    //	@Autowired
    //	private ContattoPGEntityMapper contattoPGEntityMapper;

    @Autowired
    private MudeDRuoloSoggettoRepository mudeDRuoloSoggettoRepository;

    @Autowired
    private MudeRIstanzaSoggettoRepository mudeRIstanzaSoggettoRepository;

    @Autowired
    private FascicoloEntityMapper fascicoloEntityMapper;

    @Autowired
    private IstanzaStatoSlimEntityMapper istanzaStatoSlimEntityMapper;

    @Autowired
    private ComuneEntityMapper comuneEntityMapper;

    @Autowired
    private EnteEntityMapper enteEntityMapper;

    @Autowired
    private ProvinciaEntityMapper provinciaEntityMapper;

    @Autowired
    private IndirizzoEntityMapper indirizzoEntityMapper;

    @Autowired
    private ManagerAbilitazioni managerAbilitazioni;

    @Autowired
    MudeTPraticaRepository mudeTPraticaRepository;

    @Autowired
    private UtenteEntityMapper utenteEntityMapper;

    @Autowired
    MudeTContattoRepository mudeTContattoRepository;

    @Autowired
    MudeTEnteRepository mudeTEnteRepository;

    @Autowired
    MudeCProprietaEnteRepository mudeCProprietaEnteRepository;

    @Autowired
    MudeDPPayImportiRepository mudeDPPayImportiRepository;

    @Autowired
    MudeCProprietaRepository mudeCProprietaRepository;

    @Autowired
    MudeRUtenteEnteComuneRuoloRepository mudeRUtenteEnteComuneRuoloRepository;

    @Autowired
    private UtenteFunzioneEntityMapper utenteFunzioneEntityMapper;

    @Autowired
    private MudeRRuoloFunzioneRepository mudeRUtenteFunzioneRepository;

    @Autowired
    private GeoriferimentoUpdateTableService georiferimentoUpdateTableService;
    
    @Autowired
    private MudeTPraticaCosmoRepository mudeTPraticaCosmoRepository;
    
    @Autowired
    private MudeTIstanzaSlimRepository mudeTIstanzaSlimRepository;
    
    @Autowired
    private IstanzaEntityMapper istanzaEntityMapper;
    
    @Autowired
    private MudeDSpeciePraticaRepository mudeDSpeciePraticaRepository;
    
    @Autowired
    private MudeDComuneRepository mudeDComuneRepository;
    
    @Autowired
    private MudeDTipoIstanzaRepository mudeDTipoIstanzaRepository;
    
    @Autowired
    private MudeTIndirizzoRepository mudeTIndirizzoRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Override
    public IstanzaVO mapEntityToVO(MudeTIstanzaSlim dto, MudeTUser mudeTUser, String filters) {
        IstanzaVO istanzaVO = mapEntityToSlimVO(dto, mudeTUser, filters);
        if(istanzaVO == null) return null;

    	istanzaVO.setJsonData(dto.getJsonData());
		istanzaVO.setRigeneraTracciato(dto.getRigeneraTracciato());

        if(!hasFilter(filters, "essential")) {
	        if(!hasFilter(filters, "frontoffice-slim2")) {
		        istanzaVO.setProprieta(mudeCProprietaEnteRepository.findByIdComuneAndScope("", dto.getIdTipoIstanza(), dto.getIdComune(), "FO")
						.stream().map(mudeCProprietaEnte -> { 
							return new SelectVO(mudeCProprietaEnte.getNome(), mudeCProprietaEnte.getValore());
						}).collect(Collectors.toList()));
		        
		        if(dto.getIdIstanzaRiferimento() != null) {
			        MudeTIstanza mudeTIstanzaRiferimento = mudeTIstanzaRepository.findOne(dto.getIdIstanzaRiferimento());
			        if (mudeTIstanzaRiferimento != null) {
			            istanzaVO.setIdIstanzaRiferimento(dto.getIdIstanzaRiferimento());
			            
			            IstanzaVO istanzaRiferimento = istanzaEntityMapper.mapEntityToVO(mudeTIstanzaRiferimento, null, filters);
			            
			            // FIX native address missing info
			            if(!"FO".equals(mudeTIstanzaRiferimento.getIdFonte()))
		            		istanzaRiferimento.setJsonData(georiferimentoUpdateTableService.reverseJsonAddressFromLocTables(mudeTIstanzaRiferimento.getId(), mudeTIstanzaRiferimento.getJsonData()));
			            
			            istanzaRiferimento.setFascicoloVO(null);
			            istanzaVO.setIstanzaRiferimento(istanzaRiferimento);
			            
					    if(istanzaVO.getJsonData() == null 
					    		&& hasFilter(filters, "frontoffice-istanza")
					    		&& (
					    				// special cases for DENUNCIA-SISMICA(variante) / REL-ILL-DS
					    				("DENUNCIA-SISMICA".equals(dto.getIdTipoIstanza()) && "DENUNCIA-SISMICA".equals(mudeTIstanzaRiferimento.getTipoIstanza().getCodice()))
					    				|| "REL-ILL-DS".equals(dto.getIdTipoIstanza())
					    			)
					    		&& mudeTIstanzaRiferimento.getJsonData() != null) {
					    	try {
						    	JSONObject jsonDataIstanza = new JSONObject(mudeTIstanzaRiferimento.getJsonData());
						    	
	        					jsonDataIstanza.remove("QDR_SOGGETTO_ABILIT");
	        					jsonDataIstanza.remove("QDR_SOGGETTO_COINV");
	        					jsonDataIstanza.remove("QDR_ALLEGATI");
	        					jsonDataIstanza.remove("QDR_PAGAMENTO");
	        					jsonDataIstanza.remove("QDR_PRESENTA");
						    	
						    	dto.setJsonData(jsonDataIstanza.toString());
						    	mudeTIstanzaSlimRepository.save(dto); // save json data from parent
						    	istanzaVO.setJsonData(dto.getJsonData());
							} catch (Exception skipCloning) { }
					    }
			            
			        }
		
			        // get ALL connected instances up to 4 levels
			        if(filters != null && !hasFilter(filters, "frontoffice-istanza"))
						istanzaVO.setIstanzeCollegate(istanzaEntityMapper.mapListEntityToListSlimVO(mudeTIstanzaRepository.getAllConnectedInstances(dto.getIdFascicolo(), dto.getIdIstanzaRiferimento()), null, "essential"));
					else if(!hasFilter(filters, "frontoffice-slim1"))
			        	istanzaVO.setIstanzeCollegate(istanzaEntityMapper.mapListEntityToListVO(mudeTIstanzaRepository.getAllConnectedInstances(dto.getIdFascicolo(), dto.getIdIstanzaRiferimento()), null, "essential"));
		        }
		        else if(hasFilter(filters, "frontoffice-istanza")) {
		        	FascicoloVO fascicoloVO = istanzaVO.getFascicoloVO();
		        	if(fascicoloVO == null)
		        		istanzaVO.setFascicoloVO(new FascicoloVO());
		        	
		        	MudeTIstanzaSlim istanzaIndirizzoRif = mudeTIstanzaSlimRepository.getFromFascicoloIndirizzo(dto.getIdFascicolo());
		        	if(istanzaIndirizzoRif != null)
		        		istanzaVO.getFascicoloVO().setJsonData(georiferimentoUpdateTableService.reverseJsonAddressFromLocTables(istanzaIndirizzoRif.getIdIstanza(), istanzaIndirizzoRif.getJsonData()));
		        }
		        
		        istanzaVO.setGeoriferimentoAttivo(!mudeCProprietaRepository.getValueByName("GEECO_MAPPA", "abilitato").equalsIgnoreCase("disabilitato")
					&& istanzaVO.getProprieta().stream().anyMatch(p -> "GEECO_MAPPA".equals(p.getId()) && "abilitato".equals(p.getDescrizione())));
		        
//		        istanzaVO.setPresentatore(utenteEntityMapper.mapEntityToVO(dto.getMudeTUser()));
		        
		        MudeTEnte mudeTEnte = mudeTEnteRepository.retrieveLastActive(dto.getIdComune(), 
		        																		 dto.getIdTipoIstanza(),
		        																		 dto.getIdSpeciePratica() == null ? "getnulls" : dto.getIdSpeciePratica());
		        if(mudeTEnte != null) {
		        	istanzaVO.setEnteDiRiferimento(enteEntityMapper.mapEntityToVO(mudeTEnte));
		        	
			  		// set flag to indicate if it is possible to use payment for this instance 
			  		istanzaVO.setPagamentoAttivo(!mudeCProprietaRepository.getValueByName("PPAY", "disabilitato").equalsIgnoreCase("disabilitato")
			  				&& istanzaVO.getProprieta().stream().anyMatch(p -> "PPAY_COD_VERSAMENTO".equals(p.getId()) && !"disabilitato".equals(p.getDescrizione()))
			  				&& mudeDPPayImportiRepository.getFirstValidAmountSet(istanzaVO.getEnteDiRiferimento().getId(), 
			  	  				  dto.getIdTipoIstanza(), 
			  	  				  istanzaVO.getSpeciePratica() == null? "-" : istanzaVO.getSpeciePratica().getCodice()).stream().anyMatch(p -> !"disabilitato".equals( p.getTipoImporto())));
		        }
		        
		        istanzaVO.setResponsabile_procedimento(dto.getResponsabileProcedimento());
	        }
        }
        return istanzaVO;
    }

    @Override
    public MudeTIstanzaSlim mapVOtoEntity(IstanzaVO vo, MudeTUser user) {
        // not used
        return null;
    }

    public IstanzaVO mapEntityToSlimVO(MudeTIstanzaSlim dto, MudeTUser mudeTUser, String filters) {
        if(dto == null) return null;

        IstanzaVO istanzaVO = new IstanzaVO();
        istanzaVO.setRigeneraTracciato(dto.getRigeneraTracciato());

//        String intervento = dto.getAnnoIntervento();

        istanzaVO.setFonte(dto.getIdFonte());

        istanzaVO.setIdIstanza(dto.getIdIstanza());
        if (null != dto.getIdTemplate()) {
            istanzaVO.setIdTemplate(dto.getIdTemplate());
        }
        istanzaVO.setUuidIndex(dto.getIndexFolder());
        istanzaVO.setUuidFileIndex(dto.getIndexNode());
        istanzaVO.setFilename(dto.getNomeFileIstanza());
        istanzaVO.setDimensioneFile(dto.getDimensioneFileIstanza());
        istanzaVO.setDataCaricamentoFileIstanza(dto.getDataCaricamentoFileIstanza());
        istanzaVO.setKeywords(dto.getKeywords());
        
	    if (dto.getIdComune() != null) {
	    	MudeDComune mudeDComune = mudeDComuneRepository.findOne(dto.getIdComune());
	    	
	        ComuneVO comune = comuneEntityMapper.mapEntityToVO(mudeDComune);
	        istanzaVO.setComune(comune);
	        ProvinciaVO provincia = provinciaEntityMapper.mapEntityToVO(mudeDComune.getMudeDProvincia());
	        istanzaVO.setProvincia(provincia);
	    }
        
	    istanzaVO.setDataCreazione(utilsDate.asLocalDateTime(dto.getDataUltimoStato()));
        
	    if(tipoIstanzaCache.get(dto.getIdTipoIstanza()) == null) {
	        MudeDTipoIstanza tipoistanza = mudeDTipoIstanzaRepository.findOne(dto.getIdTipoIstanza());
	        SelectVO tipologiaIstanza = new SelectVO(tipoistanza.getCodice(), tipoistanza.getDescrizioneEstesa());
	        istanzaVO.setTipologiaIstanza(tipologiaIstanza);
	        
/*
	        TipoIstanzaVO tipoIstanza2 = new TipoIstanzaVO();
	        tipoIstanza2.setId(tipoistanza.getCodice());
	        tipoIstanza2.setDescrizione(tipoistanza.getDescrizioneEstesa());
	        tipoIstanza2.setLegata(tipoistanza.getLegata());
	        tipoIstanza2.setCambioIntestatario(tipoistanza.getCambioIntestatario());
	        tipoIstanza2.setAlmenoUnRuolo(tipoistanza.getAlmenoUnRuolo());
	        tipoIstanza2.setSoggettiBloccati(tipoistanza.getSoggettiBloccati());
	        tipoIstanza2.setEscludiBranch(tipoistanza.getEscludiBranch());
	        tipoIstanza2.setSoggettiBloccati(tipoistanza.getSoggettiBloccati());
	        tipoIstanza2.setEscludiBranch(tipoistanza.getEscludiBranch());        	
			istanzaVO.setTipoIstanza(tipoIstanza2);
*/
	        tipoIstanzaCache.put(dto.getIdTipoIstanza(), tipologiaIstanza);
	    }
        istanzaVO.setTipologiaIstanza(tipoIstanzaCache.get(dto.getIdTipoIstanza()));

        istanzaVO.setCodiceIstanza(dto.getCodiceIstanza());
        istanzaVO.setIdFascicolo(dto.getIdFascicolo());

/*
        if(dto.getMudeTFascicolo() != null && dto.getMudeTFascicolo().getCodiceFascicolo() != null) {
        	istanzaVO.setFascicoloVO(new FascicoloVO());
        	istanzaVO.getFascicoloVO().setCodiceFascicolo(dto.getMudeTFascicolo().getCodiceFascicolo()); 
        }
*/

    	if(dto.getIdSpeciePratica() != null) {
    		if(speciePraticaCache.get(dto.getIdSpeciePratica()) == null) {
	    		MudeDSpeciePratica mudeDSpeciePratica = mudeDSpeciePraticaRepository.findOne(dto.getIdSpeciePratica());

	    		DizionarioVO specie = new DizionarioVO();
	    		specie.setCodice(mudeDSpeciePratica.getCodice());
	    		specie.setDescrizione(mudeDSpeciePratica.getDescrizione());
	    		specie.setDescrizioneEstesa(mudeDSpeciePratica.getDescrizioneEstesa());
    			
	    		speciePraticaCache.put(dto.getIdSpeciePratica(), specie);
    		}
    		istanzaVO.setSpeciePratica(speciePraticaCache.get(dto.getIdSpeciePratica()));
    	}
    	
        istanzaVO.setDataRegistrazionePratica(utilsDate.asLocalDateTime(dto.getDataRegistrazionePratica()));
        istanzaVO.setDataProtocollo(utilsDate.asLocalDateTime(dto.getDataProtocollo()));
        istanzaVO.setNumeroProtocollo(dto.getNumeroProtocollo());
        istanzaVO.setBranch(dto.getBranch());
        istanzaVO.setTipologiaPresentatore(new SelectVO(String.valueOf(dto.getBranch()), null));
    	istanzaVO.setDataStatoDPS(utilsDate.asLocalDateTime(dto.getDataDps()));

        if (dto.getIdIstanzaRiferimento() != null)
            istanzaVO.setIdIstanzaRiferimento(dto.getIdIstanzaRiferimento());

        if(!hasFilter(filters, "essential")) {
/*
            if (dto.getMudeTDatiIstanzas() != null) 
            	try {
	                for (MudeTDatiIstanza data : dto.getMudeTDatiIstanzas()) 
	                    if (data.getDato() == MudeTDatiIstanza.DatiIstanza.TITOLO_SOGGETTO_ABILITATO)
	                        try {
	                            MudeDTitolo mudeDTitolo = mudeDTitoloRepository.findOne(data.getValore());
	                            istanzaVO.setTitoloSoggettoAbilitato(new SelectVO(mudeDTitolo.getCodice(), mudeDTitolo.getDescrizione()));
	                        } catch (Throwable e) { }
                }  catch (Throwable e) { }

	        istanzaVO.setVisualizza(("FO".equals(dto.getIdFonte()) || dto.getUuidFileIndex() != null)
	        			&& (mudeTUser == null 
		        			|| managerAbilitazioni.hasUtenteAbilitazioneFunzionePerIstanza(false, FunzioniAbilitazioniEnum.CONSULTA_IST.getDescription(), istanzaVO.getIdIstanza(), mudeTUser, null) 
		        			|| managerAbilitazioni.hasUtenteAbilitazioneFunzionePerFascicolo(FunzioniAbilitazioniEnum.CONS_IST_ALL_FASCIC.getDescription(), istanzaVO.getIdFascicolo(), mudeTUser.getIdUser())));
*/

	        // il l'istanza puo' essere modificata/eliminata solo se in stato BOZZA
	        final StatoIstanzaSlimVO statoIstanza = mudeRIstanzaStatoRepository.findStatoByIdIstanza(dto.getIdIstanza());
	        if(statoIstanza != null) {
DizionarioVO statoIstanza2 = new DizionarioVO();
statoIstanza2.setCodice(statoIstanza.getCodice());
statoIstanza2.setDescrizione(statoIstanza.getDescrizione());
statoIstanza2.setDescrizioneEstesa(statoIstanza.getDescrizioneEstesa());
istanzaVO.setStatoIstanza(statoIstanza2);
istanzaVO.setDataStato(utilsDate.asLocalDateTime(statoIstanza.getDataInizio()));

	            if(!hasFilter(filters, "backoffice") 
	            		&& !hasFilter(filters, "backoffice.ds-report")) {
	                istanzaVO.setAssegnaAbilitazioni(mudeTUser == null || managerAbilitazioni.hasUtenteAbilitazioneFunzionePerIstanza(false, new String[]{FunzioniAbilitazioniEnum.NOMINA_CONSULT.getDescription(), FunzioniAbilitazioniEnum.NOMINA_PROF_SPEC.getDescription(), FunzioniAbilitazioniEnum.IND_COLLAB_PM.getDescription(), FunzioniAbilitazioniEnum.IND_PM.getDescription()}, istanzaVO.getIdIstanza(), mudeTUser, null));
		            
		            if ((StatoIstanza.BOZZA.getValue().equalsIgnoreCase(statoIstanza.getCodice()) 
			                		|| StatoIstanza.DA_FIRMARE.getValue().equalsIgnoreCase(statoIstanza.getCodice())
			                		|| StatoIstanza.RESTITUITA_PER_VERIFICHE.getValue().equalsIgnoreCase(statoIstanza.getCodice())
			                		|| StatoIstanza.FIRMATA.getValue().equalsIgnoreCase(statoIstanza.getCodice())) 
			                	&& (mudeTUser == null || managerAbilitazioni.hasUtenteAbilitazionePerIstanza(false, new String[]{AbilitazioniEnum.CREATORE_IST_PM_OBB.getDescription(), AbilitazioniEnum.CREATORE_IST_PM_OPZ.getDescription(), AbilitazioniEnum.PM_RUP_PM_OBB.getDescription(), AbilitazioniEnum.PM_RUP_PM_OPZ.getDescription(), AbilitazioniEnum.COLLAB_PM_RUP_PM_OBB.getDescription(), AbilitazioniEnum.COLLAB_PM_RUP_PM_OPZ.getDescription(), AbilitazioniEnum.PROF_SPEC.getDescription()}, istanzaVO.getIdIstanza(), mudeTUser, null))) {
		                istanzaVO.setModifica(true);
		                istanzaVO.setElimina(mudeTUser != null && (
		                		managerAbilitazioni.hasUtenteAbilitazioneFunzionePerIstanza(false, FunzioniAbilitazioniEnum.CREA_IST.getDescription(), istanzaVO.getIdIstanza(), mudeTUser, null)
		                		/* || managerAbilitazioni.hasUtenteAbilitazioneFunzionePerFascicolo(FunzioniAbilitazioniEnum.CREA_IST.getDescription(), istanzaVO.getIdFascicolo(), mudeTUser.getIdUser())*/
		               		));
		                
		            }
	
		            istanzaVO.setTornaBozza(mudeTUser != null && managerAbilitazioni.hasUtenteAbilitazioneFunzionePerIstanza(false, FunzioniAbilitazioniEnum.RIPORTA_IN_BOZZA.getDescription(), istanzaVO.getIdIstanza(), mudeTUser, null));
		            istanzaVO.setConsolida(mudeTUser != null && managerAbilitazioni.hasUtenteAbilitazioneFunzionePerIstanza(false, FunzioniAbilitazioniEnum.CONSOLIDA_ISTANZA.getDescription(), istanzaVO.getIdIstanza(), mudeTUser, null));
	            }
	
	            if(statoIstanza.getLivello() >= StatoIstanza.REGISTRATA_DA_PA.getLevel()) {
	                istanzaVO.setNuovaIstanza(true);

					// pratica
					if(!hasFilter(filters, "frontoffice-istanza") 
							&& !hasFilter(filters, "frontoffice-slim1")) {
						PraticaSlimVO mudeTPratica = mudeTPraticaRepository.findByIdIstanzaSlim(dto.getIdIstanza());
					    if(mudeTPratica != null) {
					    	istanzaVO.setIdPratica(mudeTPratica.getId());
					        istanzaVO.setAnno(mudeTPratica.getAnnoPratica());
					        istanzaVO.setNumeroPratica(mudeTPratica.getNumeroPratica());
					    }
					}
	            }
	        }

			MudeTIndirizzo mudeTindirizzo = mudeTIndirizzoRepository.findByIdIstanza(dto.getIdIstanza());
			if (null != mudeTindirizzo) {
			    IndirizzoVO indirizzoVO = indirizzoEntityMapper.mapEntityToVO(mudeTindirizzo);
			    istanzaVO.setIndirizzo(indirizzoVO);
			}

			// main "intestatario" of the instance
			MudeTContatto intestatario = mudeTContattoRepository.findIntestatarioByIdIstanza(dto.getIdIstanza());
			if(intestatario != null) {
			    istanzaVO.setIntestatario(contattoEntityMapper.mapEntityToSlimVO(intestatario, null));
			    istanzaVO.setGuidIntestatario(intestatario.getGuid());
			}
	        
	        istanzaVO.setDataInizio(dto.getDataInizio());
	        istanzaVO.setDataFine(dto.getDataFine());
	        
	        if(!hasFilter(filters, "frontoffice-slim2")) {
		        if(hasFilter(filters, "backoffice.ds-")) {
		        	List<MudeTContatto> progettista = mudeTContattoRepository.findRoleByIdIstanza(dto.getIdIstanza(), new String[] { "PR" });
		        	if(progettista != null && progettista.size() > 0) {
						istanzaVO.setPm(progettista.get(0).getCognome()+ " " + progettista.get(0).getNome());
						istanzaVO.setPmCodiceFiscale(progettista.get(0).getCf());
		        	}
		        }
		        else {
					MudeTUser pms = mudeTUserRepository.findPm(dto.getIdIstanza());
					if(pms != null) {
						istanzaVO.setPm(pms.getCognome()+ " " + pms.getNome());
						istanzaVO.setPmCodiceFiscale(pms.getCf());
						istanzaVO.setPmPresente(true);
					}
/*
			        else {
			        	MudeTUser creatorUser = dto.getMudeTUser();
			        	if(creatorUser != null) {
				        	istanzaVO.setPm(creatorUser.getCognome()+ " " + creatorUser.getNome());
							istanzaVO.setPmCodiceFiscale(creatorUser.getCf());
			        	}
			        }
*/
		        	
		        }
/*
		        if(!hasFilter(filters, "frontoffice-slim1") && !hasFilter(filters, "frontoffice-istanza") && null != dto.getMudeTFascicolo())
		            istanzaVO.setFascicoloVO(fascicoloEntityMapper.mapEntityToSlimVO(dto.getMudeTFascicolo(), null, filters));
*/

		        // esclude backoffice stuff
		        if(!hasFilter(filters, "frontoffice")) {
			        istanzaVO.setOccupazioneSuoloPubblico(false);
			        istanzaVO.setResponsabile_procedimento(dto.getResponsabileProcedimento());

					if(!hasFilter(filters, "backoffice.ds-report")) {
				        String tipiIstanzaToCheck = mudeCProprietaRepository.getValueByName("IST_TO_CHECK_OPERE_IN_PRECARIO", "|FIL-CILA|FIL-SCIA|IL-PDC|FIL-PDC|PRO-PDC|SCIA-AGI|INT-DOC|IL-DS|FIL-DS|");
				        
						if(dto.getIdIstanzaRiferimento() != null && tipiIstanzaToCheck != null && tipiIstanzaToCheck.contains("|"+ dto.getIdTipoIstanza()+"|"))
							istanzaVO.setOccupazioneSuoloPubblico(mudeTIstanzaRepository.getOpereInPrecario(dto.getIdIstanzaRiferimento()));
						else if(tipiIstanzaToCheck != null && !tipiIstanzaToCheck.contains("|"+ dto.getIdTipoIstanza()+"|"))
							istanzaVO.setOccupazioneSuoloPubblico(mudeTIstanzaRepository.getOpereInPrecario(dto.getIdIstanza()));
					
				        String extraColumnsQuery = mudeCProprietaRepository.getValueByNameAndInstanceSP("BO_EXTRA_COLUMNS_" + dto.getIdTipoIstanza(), dto.getIdIstanza(), "");
				        if(!StringUtils.isBlank(extraColumnsQuery)) {
				        	String query = "SELECT CAST(ROW_TO_JSON(tblQuery) AS TEXT) FROM(" + extraColumnsQuery + " WHERE tblIstanza.id_istanza = " + dto.getIdIstanza() + ") tblQuery";
				        	try {
				        		List<String> lst = entityManager.createNativeQuery(query.replace(":", "\\:")).getResultList();
				        		if(lst.size() > 0)
				        			istanzaVO.setExtraColumns(lst.get(0));
							} catch (Exception e) {
								e.printStackTrace();
							}
				        }
				        
				        // Fix RUOLI Utente BO sull'istanza
				        if(mudeTUser != null) {
					        if(mudeTUser.getUtenteBo() && mudeRUtenteEnteComuneRuoloRepository.existsRuoloByUtente(mudeTUser.getIdUser()))
					        {
					        	MudeRUtenteEnteComuneRuolo mudeRUtenteEnteComuneRuolo = mudeRUtenteEnteComuneRuoloRepository.getRuoloEnteRiceventeByUtenteIstanza(mudeTUser.getIdUser(),istanzaVO.getIdIstanza());
					        	
					        	if(mudeRUtenteEnteComuneRuolo != null) {
					        		istanzaVO.setRuoloUtenteBoSuIstanza(mudeRUtenteEnteComuneRuolo.getRuoloUtente());
					        	}else {
					        		List<MudeRUtenteEnteComuneRuolo> mudeRUtenteEnteComuneRuoloList = mudeRUtenteEnteComuneRuoloRepository.getRuoloEntiTerziByUtenteIstanza(mudeTUser.getIdUser(),istanzaVO.getIdIstanza());
	
					        		if(mudeRUtenteEnteComuneRuoloList != null && !mudeRUtenteEnteComuneRuoloList.isEmpty())
					        			istanzaVO.setRuoloUtenteBoSuIstanza(TipoRuoloUtente.ENTE_TERZO.getValue());
					        		else
					        			istanzaVO.setRuoloUtenteBoSuIstanza("");
					        	}
					        
						        String ruoloUtenteBO = istanzaVO.getRuoloUtenteBoSuIstanza();
						        
						        // Fix Funzionalita Utente BO sull'istanza
						        List<MudeRRuoloFunzione> mudeRUtenteFunzioneList = mudeRUtenteFunzioneRepository.findByCodiceRuoloUtente(ruoloUtenteBO);
	
						        List<FunzioneUtenteVO> funzioneRuoloVOs = utenteFunzioneEntityMapper.mapListEntityToListVO(mudeRUtenteFunzioneList);
	
						        istanzaVO.setFunzioniUtente(funzioneRuoloVOs);
					        }
				        }
			        } // !backoffice.ds-report
		        }
	        }
	        
	        if(hasFilter(filters, "backoffice.ds-scrivania") || hasFilter(filters, "backoffice.ds-report-scrivania")) {
	        	MudeTPraticaCosmo mudeTPraticaCosmo = mudeTPraticaCosmoRepository.findByIdIstanza(dto.getIdIstanza());
	        	if(mudeTPraticaCosmo != null) {
			    	String titoloIntervento = mudeTPraticaCosmo.getNumeroPratica() + " - " + mudeTIstanzaRepository.retrieveDSTitoloIntervento(dto.getIdIstanza()).split("~")[0];
			    	
					if(titoloIntervento.length() > 100)
						titoloIntervento = titoloIntervento.substring(0, 100);

		        	istanzaVO.setKeywords(titoloIntervento);
	                istanzaVO.setAssegnaAbilitazioni(mudeTPraticaCosmo.getCcSelezionato());
	        	}
	        }
	        
        }

        return istanzaVO;
    }
    
}