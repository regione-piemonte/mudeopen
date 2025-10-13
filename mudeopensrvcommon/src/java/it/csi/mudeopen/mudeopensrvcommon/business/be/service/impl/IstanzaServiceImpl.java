/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.CsiLogAudit;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDComune;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDProvincia;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRuoloSoggetto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRuoloUtente;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDStatoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTaskQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTemplate;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoDocPA;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoNotifica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTitolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDWfStato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaSoggetto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaStato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRNotificaContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRNotificaDocumento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRNotificaUtente;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTipoIstanzaRuoloSoggetto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRUtenteRuolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTDatiIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTDatiIstanza.DatiIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTDocumento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTEnte;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTFascicolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanzaSlim;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTNotifica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTPratica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.dto.ErrorMessage;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon.IstanzeExcelBuilder;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IstanzaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IstanzaSlimEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDComuneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDProvinciaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDRuoloSoggettoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDRuoloUtenteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDStatoIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTaskQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTemplateRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoDocpaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTitoloRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDWfStatiRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeREnteTipoIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaEnteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaQuadroUtenteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaSoggettoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaStatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaTracciatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaUtenteQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaUtenteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRNotificaContattoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRNotificaDocumentoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRNotificaUtenteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRTipoIstanzaRuoloSoggettoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRUtenteRuoloRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTAllegatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTContattoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTDatiIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTEnteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTFascicoloRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaSlimRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTLogTracciatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTNotificaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTPraticaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTUserRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.AbilitazioneFunzioneService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.ContattoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.CosmoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.FascicoloIntestatarioService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.FascicoloSoggettoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.FascicoloUtenteService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.IstanzaUtenteService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.UtenteService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.UtilsTraceCsiLogAuditService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.CodeGenerator;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.ManagerAbilitazioni;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeTIstanzaSlimSpecification;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeTIstanzaSpecification;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.FilterUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.PaginationResponseUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.UserUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.ContattoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.TipoContatto;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.StatoInvioEmail;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.StatoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoRuoloUtente;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoTaskQuadro;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.BranchIstanzaEnum;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.FunzioniAbilitazioniEnum;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.QueryOpEnum;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloRuoloVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.RuoloObbligatorioVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.RuoloPossibileVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.SoggettoIstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.request.SalvaIstanzaRequest;
import it.csi.mudeopen.mudeopensrvcommon.vo.request.SalvaTitoloSoggettoAbilitatoRequest;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.RuoliIstanzaResponse;
import it.csi.mudeopen.mudeopensrvcommon.vo.serializer.CustomDateTimeSerializer;
import it.csi.mudeopen.mudeopensrvcommon.vo.utente.IstanzaUtenteVO;

/**
 * The type Istanza service.
 */
@Service
public class IstanzaServiceImpl extends IstanzaServiceUtil implements IstanzaService {
    private static final String FRONTOFFICE = "frontoffice";

	private static final String BACKOFFICE = "backoffice";
	
	final static Hashtable<String, String> syncmoniker = new Hashtable<String, String>();

    private final String CLS_NAME = this.getClass().getSimpleName();

    @Autowired
    private MudeDTipoIstanzaRepository mudeDTipoIstanzaRepository;

    @Autowired
    private MudeDComuneRepository mudeDComuneRepository;

    @Autowired
    private IstanzaEntityMapper istanzaEntityMapper;
    
    @Autowired
    private IstanzaSlimEntityMapper istanzaSlimEntityMapper;

    @Autowired
    private MudeTContattoRepository mudeTContattoRepository;

    @Autowired
    private MudeRIstanzaSoggettoRepository mudeRIstanzaSoggettoRepository;
    
    @Autowired
    private MudeRIstanzaEnteRepository mudeRIstanzaEnteRepository;

    @Autowired
    private MudeTDatiIstanzaRepository mudeTDatiIstanzaRepository;

    @Autowired
    private MudeRTipoIstanzaRuoloSoggettoRepository mudeRTipoIstanzaRuoloSoggettoRepository;

    @Autowired
    private MudeDRuoloSoggettoRepository mudeDRuoloSoggettoRepository;

    @Autowired
    private MudeDTitoloRepository mudeDTitoloRepository;

    @Autowired
    private UtilsTraceCsiLogAuditService utilsTraceCsiLogAuditService;

    @Autowired
    private FascicoloIntestatarioService fascicoloIntestatarioService;

    @Autowired
    private MudeTFascicoloRepository mudeTFascicoloRepository;

    @Autowired
    private MudeDProvinciaRepository mudeDProvinciaRepository;

    @Autowired
    private MudeDTemplateRepository mudeDTemplateRepository;

    @Autowired
    private MudeRIstanzaStatoRepository mudeRIstanzaStatoRepository;

    @Autowired
    private MudeTPraticaRepository mudeTPraticaRepository;

    @Autowired
    private IstanzaUtenteService istanzaUtenteService;

    @Autowired
    private AbilitazioneFunzioneService abilitazioneFunzioneService;

    @Autowired
    private MudeRIstanzaQuadroUtenteRepository mudeRIstanzaQuadroUtenteRepository;

    @Autowired
    private MudeRIstanzaUtenteQuadroRepository mudeRIstanzaUtenteQuadroRepository;

    @Autowired
    private MudeRIstanzaUtenteRepository mudeRIstanzaUtenteRepository;

    @Autowired
    private ManagerAbilitazioni managerAbilitazioni;

    @Autowired
    private MudeTUserRepository mudeTUserRepository;

    @Autowired
    private MudeTAllegatoRepository mudeTAllegatoRepository;

    @Autowired
    private FascicoloSoggettoService fascicoloSoggettoService;

    @Autowired
    private MudeDWfStatiRepository mudeDWfStatiRepository;

    @Autowired
    private MudeDStatoIstanzaRepository mudeDStatoIstanzaRepository;

    
    @Autowired
    MudeDQuadroRepository mudeDQuadroRepository;
    
    @Autowired
    MudeCProprietaRepository mudeCProprietaRepository;
    
    @Autowired
    MudeTNotificaRepository mudeTNotificaRepository;
    
    @Autowired
    MudeRNotificaContattoRepository mudeRNotificaContattoRepository;
    
    @Autowired
    MudeRIstanzaTracciatoRepository mudeRIstanzaTracciatoRepository;
    
    @Autowired
    MudeDTaskQuadroRepository mudeDTaskQuadroRepository;
    
    @Autowired
    MudeRNotificaUtenteRepository mudeRNotificaUtenteRepository;
    
    @Autowired
    MudeDTipoDocpaRepository mudeDTipoDocpaRepository;
    
    @Autowired
    MudeRUtenteRuoloRepository mudeRUtenteRuoloRepository;
    
    @Autowired
    MudeTLogTracciatoRepository mudeTLogTracciatoRepository;
    
    @Autowired
    private CosmoService cosmoService;
    
    @Autowired
    UserUtil userUtil;
    
    @Autowired
    ContattoService contattoService;
    
    @Autowired
    MudeRNotificaDocumentoRepository mudeRNotificaDocumentoRepository;

    /*
    @Autowired
    private MudeRTipoIstanzaTipoTracciatoRepository mudeRTipoIstanzaTipoTracciatoRepository;

    @Autowired
    private MudeRIstanzaTracciatoRepository mudeRIstanzaTracciatoRepository;
    
    @Autowired
    private IstanzaTracciatoEntityMapper istanzaTracciatoEntityMapper;
    
    @Autowired
    private TipoTracciatoEntityMapper tipoTracciatoEntityMapper;
    */
    
    @Autowired
    MudeDRuoloUtenteRepository mudeDRuoloUtenteRepository;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    MudeTEnteRepository mudeTEnteRepository;
    
    @Autowired
    MudeREnteTipoIstanzaRepository mudeREnteTipoIstanzaRepository;

    @Autowired
    MudeTIstanzaSlimRepository mudeTIstanzaSlimRepository;
    
    @Autowired
    private UtenteService utenteService;
    
    @Autowired
    private FascicoloUtenteService fascicoloUtenteService;
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public IstanzaVO salvaIstanza(SalvaIstanzaRequest request, MudeTUser mudeTUser, HttpHeaders httpHeaders) {


        IstanzaVO istanza = new IstanzaVO();

        MudeTIstanza mudeTIstanza = new MudeTIstanza();
        mudeTIstanza.setMudeTUser(mudeTUser);

        // l'istanza viene legata a un fascicolo esistente
        mudeTIstanza.setMudeTFascicolo(mudeTFascicoloRepository.findOne(request.getIdFascicolo()));

        mudeTIstanza.setTipoIstanza(mudeDTipoIstanzaRepository.findOne(request.getTipologiaIstanza().getId()));
        MudeDComune comune = mudeDComuneRepository.findByIdComune(request.getComune().loadIdAsLong());
        // capire se inserire il comune
        mudeTIstanza.setComune(comune);

        mudeTIstanza.setDataCreazione(new Timestamp(System.currentTimeMillis()));

        if (request.getIdIstanzaRiferimento() != null && request.getIdIstanzaRiferimento() > 0L)
            mudeTIstanza.setIdIstanzaRiferimento(request.getIdIstanzaRiferimento());

        // set the proper template for the scope FO/BO
        MudeDTemplate mudeDTemplate = getIdTemplateIstanza(mudeTIstanza.getTipoIstanza().getCodice(), request.getBoTemplateOverride(), mudeTUser);
        mudeTIstanza.setTemplate(mudeDTemplate);

        // THIS IS USED BY BO OPERATORS TO GENERATE INSTANCES WITH OLDER TEMPLATE VERSIONS
        if (userUtil.isBackofficeAdminUser(mudeTUser) && mudeDTemplate != null && !Objects.equals(mudeDTemplate.getNumeroVersione(), mudeDTemplateRepository.findByTipoIstanza_idAndMaxVersion(mudeDTemplate.getMudeTipoIstanza().getCodice()).getNumeroVersione()))
            mudeTIstanza.setJsonData("{\"" + IstanzaVO._NEW_TEMPLATE + "\":\"\"}");

        mudeTIstanza.setKeywords(request.getKeywords());
        mudeTIstanzaRepository.saveDAO(mudeTIstanza);

        // set the state to BZZ
        if (request.getIdIstanza() == null)
            istanzaStatoService.saveIstanzaStato(mudeTIstanza, StatoIstanza.BOZZA.getValue(), null, httpHeaders, true);

        // genero codiceIStanza -
        // il primo gruppo di due numeri è il codice istat regionale
        // il secondo gruppo di 4 numeri è il codice istat comunale
        // il terzo gruppo è un progressivo di 10 numeri
        // l'ultimo è l'anno
        String numeroMude = httpHeaders.getHeaderString(Constants.NUMERO_MUDE);
        if(StringUtils.isBlank(numeroMude)) {
        	 mudeTIstanza.setCodiceIstanza(CodeGenerator.getNewCodiceFascicolo(mudeTIstanza.getId(), comune));
        } else {
        	mudeTIstanza.setCodiceIstanza(numeroMude);        	
        }

        mudeTIstanzaRepository.saveDAO(mudeTIstanza);

        String codiceAbilitazione = "CREATORE_IST_PM_OPZ";
        if (mudeTIstanza.getTemplate() == null)
            throw new BusinessException("Non è stato definito nessun template per il tipo di istanza selezionato", "403", Constants.ERRORE_BUSINESS, null);
            
        if (mudeTIstanza.getTemplate().getObbligatoriaNominaPM()) {
            codiceAbilitazione = "CREATORE_IST_PM_OBB";
        }

        istanzaUtenteService.save(mudeTIstanza.getId(), codiceAbilitazione, mudeTUser);

        istanza = istanzaEntityMapper.mapEntityToSlimVO(mudeTIstanza, null);

        // retrieve "abilitazioni" per subject
        istanza.setAbilitazioni(abilitazioneFunzioneService.loadFunzioniAbilitazioniByIdIstanzaAndIdUser(mudeTIstanza.getId(), mudeTUser.getIdUser()));

        if (request.getIdIstanza() == null) {
            salvaTipoPresentatore(mudeTIstanza.getId(), request, mudeTUser);

            for (SoggettoIstanzaVO soggettoIstanzaVO : fascicoloSoggettoService.retrieveSoggettiDaFascicolo(mudeTIstanza.getId(), getRuoliPossibili(null), request.getRuoli(), mudeTUser))
                aggiungiSoggetto(soggettoIstanzaVO, null, mudeTUser, false, mudeTIstanza);
        }

        utilsTraceCsiLogAuditService.traceCsiLogAudit(CsiLogAudit.TraceOperation.INSERIMENTO_ISTANZA.getOperation(), mudeTIstanza.getTableName(), "id=" + mudeTIstanza.getId() + ", idTipoIstanza=" + request.getTipologiaIstanza().getId(), mudeTUser.getCf(), Thread.currentThread().getStackTrace()[1].getMethodName());

        return istanza;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public RuoliIstanzaResponse aggiungiSoggetto(SoggettoIstanzaVO soggetto, Long replaceIdSoggetto, MudeTUser mudeTUser, boolean autoImportIN, MudeTIstanza massiveIstanzaImport) {
        RuoliIstanzaResponse resp = null;
        
        // cerco istanza
        MudeTIstanza mudeTIstanza = massiveIstanzaImport != null? massiveIstanzaImport : mudeTIstanzaRepository.findOne(soggetto.getIdIstanza());
        if (mudeTIstanza == null)
            throw new RuntimeException("mudeTIstanza with id[" + soggetto.getIdIstanza() + "] null!");

        synchronized (syncmoniker) {
            if (syncmoniker.get("" + mudeTIstanza.getMudeTFascicolo().getId()) == null)
                syncmoniker.put("" + mudeTIstanza.getMudeTFascicolo().getId(), "");
        }

        synchronized (syncmoniker.get("" + mudeTIstanza.getMudeTFascicolo().getId())) {
        	Long idImportDataSubjectIN = null;
            MudeTContatto mudeTContatto = null;
            
            if("add_owner_from_parent".equals(soggetto.getOperation())) {
            	// get owner from referenced instance
            	mudeTContatto = mudeTContattoRepository.findIntestatarioByIdIstanzaRiferimento(soggetto.getIdIstanza());
                if(mudeTContatto == null)
                	throw new BusinessException("Impossibile trovare l'intestatario nell'istanza di riferimento, per importarlo tra i soggetti coinvolti");

                String[] parentData = mudeTContatto.getGuid().split("~");
                mudeTContatto.setGuid(parentData[1]); // reverse proper GUID evicted from original id_contatto~ 
        		soggetto.setContatto( contattoEntityMapper.mapEntityToVO( mudeTContatto, mudeTUser));
        		idImportDataSubjectIN = Long.parseLong(parentData[0]);
            }

            else if("add_owner_from_folder".equals(soggetto.getOperation())) { // this implies that the IN is changeable client side!
            	FascicoloRuoloVO fascicoloRuoloVO = fascicoloSoggettoService.getRuoliFascicolo(mudeTIstanza.getMudeTFascicolo().getId(), mudeTUser).stream().filter(fascRuoloVO -> "IN".equals(fascRuoloVO.getRuolo().getId()) ).findFirst().orElse(null);
                if(fascicoloRuoloVO == null)
                	return null; // not owner from folder, then don't do anything

                // first try to get IN from address book's logged user
                if((mudeTContatto = mudeTContattoRepository.findFirstByGuidAndMudeTUser_IdUserAndDataCessazioneNullOrderByIdDesc(fascicoloRuoloVO.getGuidSoggetto(), idImportDataSubjectIN = mudeTUser.getIdUser())) == null)
                    // then try to get IN from folder
                	if((mudeTContatto = mudeTContattoRepository.findInUserFromFolder(fascicoloRuoloVO.getGuidSoggetto(), mudeTIstanza.getMudeTFascicolo().getId())) != null)
                		idImportDataSubjectIN = mudeTContatto.getId();
                	else
                        // if not found continue, allowing the user to add it manually
                    	return null; // not owner from folder, then don't do anything

        		soggetto.setContatto( contattoEntityMapper.mapEntityToVO( mudeTContatto, mudeTUser));
            }
            
            if(idImportDataSubjectIN != null) {
            	// adds already stored roles from current istance
            	for(MudeDRuoloSoggetto mudeDRuoloSoggetto : mudeDRuoloSoggettoRepository.findByIdIstanzaAndIdSoggetto(soggetto.getIdIstanza(), mudeTContatto.getGuid()))
            		if(!"IN".equals(mudeDRuoloSoggetto.getCodice()))
            			soggetto.getRuoloSoggetto().add(new SelectVO(mudeDRuoloSoggetto.getCodice(), null));
            	
            	// import "TITOLO" from prev contact
            	if(mudeTIstanza.getIdIstanzaRiferimento() != null) {
	            	String idTitoloSoggetto = null, idTitoloSoggettoRT = null;
	                for(MudeTDatiIstanza titolo : CollectionUtils.emptyIfNull(mudeTDatiIstanzaRepository.findAllTitoloSoggetto(mudeTIstanza.getIdIstanzaRiferimento(), idImportDataSubjectIN)))
	                	if(titolo.getDato() == DatiIstanza.TITOLO_SOGGETTO_ABILITATO)
	                		idTitoloSoggetto = titolo.getValore();
	                	else if(titolo.getDato() == DatiIstanza.TITOLO_SOGGETTO_ABILITATO_RT)
	                		idTitoloSoggettoRT = titolo.getValore();
	                
	            	String _idTitoloSoggetto = idTitoloSoggetto, _idTitoloSoggettoRT = idTitoloSoggettoRT;
	                if(idTitoloSoggetto != null || idTitoloSoggettoRT != null) {
			    		SalvaTitoloSoggettoAbilitatoRequest request = new SalvaTitoloSoggettoAbilitatoRequest();
			    		request.setIdIstanza(soggetto.getIdIstanza());
			    		request.setSoggettoRappresentato(soggetto.getContatto());
			    		request.setTitoloSoggettoAbilitato(new SelectVO(_idTitoloSoggetto, null));
			    		request.setTitoloSoggettoAbilitatoRT(new SelectVO(_idTitoloSoggettoRT, null));
						salvaTitoloSoggettoAbilitato(request, mudeTUser);
	                }
            	}
            	
            }

            else	
                mudeTContatto = mudeTContattoRepository.findOne(soggetto.getContatto().getId());
            
            TipoContatto tipoContatto = mudeTContatto.getTipoContatto(); // soggetto.getContatto().getTipoContatto();

            if (mudeTContatto != null) {
                //boolean isFolderOwner = fascicoloIntestatarioVO == null || fascicoloIntestatarioVO.getIntestatario().getGuid().equals(mudeTContatto.getGuid());

                // in case of subject switch, first delete the old idSoggetto
                if (replaceIdSoggetto != null)
                    eliminaSoggettoCoinvolto(soggetto.getIdIstanza(), replaceIdSoggetto, mudeTUser);

                List<MudeRIstanzaSoggetto> mudeRIstanzaSoggetti = mudeRIstanzaSoggettoRepository.findByMudeTIstanza(mudeTIstanza);
                MudeRIstanzaSoggetto mudeRIstanzaSoggetto = null;
                for (MudeRIstanzaSoggetto mudeRIstanzaSoggettoEsistente : mudeRIstanzaSoggetti) {
                    if (((tipoContatto.equals(TipoContatto.PF) && mudeRIstanzaSoggettoEsistente.getMudeTContatto().getTipoContatto().equals(TipoContatto.PF))
                    			&& (mudeRIstanzaSoggettoEsistente.getMudeTContatto().getCf() != null 
                    			&& mudeRIstanzaSoggettoEsistente.getMudeTContatto().getCf().equalsIgnoreCase(mudeTContatto.getCf()))) 
                    		|| ((tipoContatto.equals(TipoContatto.PG) && mudeRIstanzaSoggettoEsistente.getMudeTContatto().getTipoContatto().equals(TipoContatto.PG))
                    				&& ((mudeRIstanzaSoggettoEsistente.getMudeTContatto().getPiva() != null 
                    				&& mudeRIstanzaSoggettoEsistente.getMudeTContatto().getPiva().equalsIgnoreCase(mudeTContatto.getPiva())) 
                    						|| (mudeRIstanzaSoggettoEsistente.getMudeTContatto().getPivaComunitaria() != null 
                    						&& mudeRIstanzaSoggettoEsistente.getMudeTContatto().getPivaComunitaria().equalsIgnoreCase(mudeTContatto.getPivaComunitaria()))))) {
                        mudeRIstanzaSoggetto = mudeRIstanzaSoggettoEsistente;
                        break;
                    }
                }

                if (mudeRIstanzaSoggetto == null) {
                    mudeRIstanzaSoggetto = new MudeRIstanzaSoggetto();
                    mudeRIstanzaSoggetto.setMudeTContatto(mudeTContatto);
                    mudeRIstanzaSoggetto.setMudeTIstanza(mudeTIstanza);

                    // prevents the association of IN to more than one subject  
                    if(!mudeTContatto.getGuid().equals(soggetto.getContatto().getGuid()) 
		                    		&& soggetto.getRuoloSoggetto().stream().anyMatch(x -> "IN".equalsIgnoreCase(x.getId())) 
		                    		&& mudeRIstanzaSoggetti.stream().anyMatch(x -> x.getRuoli().stream().anyMatch(y -> "IN".equalsIgnoreCase(y))) )
                        throw new BusinessException("Impossibile associare il ruolo di intestatario ad un altro soggetto!");
                    
                    if (autoImportIN && BranchIstanzaEnum.BRANCH_2.getvalue() == mudeTIstanza.getBranch()) {
                        // set IN to the first DE
                        if (!mudeRIstanzaSoggetti.stream().anyMatch(x -> x.getRuoli().stream().anyMatch(y -> "IN".equalsIgnoreCase(y))))
                            if (soggetto.getRuoloSoggetto().stream().anyMatch(x -> "DE".equalsIgnoreCase(x.getId())))
                                soggetto.getRuoloSoggetto().add(new SelectVO("IN", null));

                        // set CO to the DE, in case there is no IN or CO
                        if (soggetto.getRuoloSoggetto().stream().anyMatch(x -> "DE".equalsIgnoreCase(x.getId())) && !soggetto.getRuoloSoggetto().stream().anyMatch(x -> "IN".equalsIgnoreCase(x.getId()) || "CO".equalsIgnoreCase(x.getId())))
                            soggetto.getRuoloSoggetto().add(new SelectVO("CO", null));
                    }
                }

                mudeRIstanzaSoggetto.setOperaFiniFiscaliDipendente(soggetto.getOperaFiniFiscaliDipendente());
                if (null != soggetto.getDomiciliazioneCorrispondenzaProfessionista()) {
                    mudeRIstanzaSoggetto.setDomiciliazioneCorrispondenzaProfessionista(soggetto.getDomiciliazioneCorrispondenzaProfessionista());
                } else {
                    mudeRIstanzaSoggetto.setDomiciliazioneCorrispondenzaProfessionista(Boolean.FALSE);
                }

                mudeRIstanzaSoggetto.setEnteSocietaAppartenenza(soggetto.getEnteSocietaAppartenenza());

                String autoSubscriptionConf = mudeCProprietaRepository.getValueByName("RUOLI_AUTO_ACCREDITAMENTO_"+mudeTIstanza.getTipoIstanza().getCodice(), "");
        	
                List<String> ruoli = new ArrayList<>();
                for (SelectVO ruolo : soggetto.getRuoloSoggetto()) {
                    // set the folder owner in case it's the only one ISTANZA in folder
                    if ("IN".equalsIgnoreCase(ruolo.getId()) 
                    		&& mudeTFascicoloRepository.howManyIstanzacesIdFolder(mudeTIstanza.getMudeTFascicolo().getId()) == 1)
                        fascicoloIntestatarioService.saveFascicoloIntestatario(mudeTIstanza, mudeTContatto.getId(), mudeTUser, true);

                    // auto-subscription
                    for(String conf : autoSubscriptionConf.split(";")) {
	                    if(conf.indexOf(ruolo.getId() + ":") > -1) {
	                    	Long user2enable = utenteService.accreditaContatto(mudeTContatto);
	                    	
		                    for(String permition : conf.substring(conf.indexOf(':') + 1).split(",")) {
		                    	// const fn = this.mode=='istanza'? 'saveAbilitazioniFunzioniPerIstanzaUtente':'saveAbilitazioniFunzioniPerFascicoloUtente';
		                    	if(permition.trim().startsWith("FASCIC_"))
		                    		fascicoloUtenteService.setAbilitazionePerFascicoloUtente(mudeTIstanza.getMudeTFascicolo().getId(), permition.trim(), user2enable, mudeTUser);
		                    	else
		                    		istanzaUtenteService.setAbilitazionePerIstanzaUtente(mudeTIstanza.getId(), permition.trim(), user2enable, mudeTUser);
		                    }	                    	
	                    }
                    }
                    
                    ruoli.add(ruolo.getId());
                }

                mudeRIstanzaSoggetto.setRuoli(ruoli);//IdRuolo(soggetto.getRuoloSoggetto().getId());
                mudeRIstanzaSoggettoRepository.saveDAO(mudeRIstanzaSoggetto);

                //todo inserice codifica ruolo delegante
                if (ruoli.contains("DE")) {
                    String jsonData = mudeTIstanza.getJsonData();
                    if (StringUtils.isNotBlank(jsonData)) {
                        try {
                            if (tipoContatto.equals(TipoContatto.PF)) {
                                aggiornaJsonDataPF(soggetto, mudeTContatto, mudeTIstanza, mudeRIstanzaSoggetto, ruoli);
                            } else {
                                aggiornaJsonDataPG(mudeTContatto, mudeTIstanza, mudeRIstanzaSoggetto, ruoli);
                            }
                        } catch (JSONException e) {
                            logger.error("[IstanzaApiServiceImpl::aggiungiSoggetto] ERRORE nell'aggiornamento del jsonData : " + e.getMessage());
                        }
                    }
                }

                mudeTIstanzaRepository.saveDAO(mudeTIstanza);
                
                if(massiveIstanzaImport == null)
                	utilsTraceCsiLogAuditService.traceCsiLogAudit(CsiLogAudit.TraceOperation.ASSOCIA_PERSONA_GIURIDICA_RUOLO_ISTANZA.getOperation(), mudeTIstanza.getTableName(), "idIstanza=" + mudeTIstanza.getId() + ", idContatto=" + mudeTContatto.getId(), mudeTUser.getCf(), Thread.currentThread().getStackTrace()[1].getMethodName());
            }


            if(massiveIstanzaImport == null)
            	resp = getRuoliIstanza(mudeTUser, soggetto.getIdIstanza(), null, true);

            syncmoniker.remove("" + mudeTIstanza.getMudeTFascicolo().getId());
        }

        return resp;
    }


    private List<SelectVO> salvaTipoPresentatore(Long idIstanza, SalvaIstanzaRequest request, MudeTUser mudeTUser) {

        // cerco istanza

        //		IstanzaVO istanza = new IstanzaVO();
        //		istanza.setTipologiaPresentatore(request.getTipologiaPresentatore());
        //		istanza.setIdIstanza(request.getIdIstanza());
        MudeTIstanza mudeTIstanza = mudeTIstanzaRepository.findOne(idIstanza);
        if (mudeTIstanza == null) {
            throw new RuntimeException("mudeTIstanza with id[" + idIstanza + "] null!");
        }

        mudeTIstanza.setBranch(Integer.valueOf(request.getTipologiaPresentatore().getId()));
/*        MudeTDatiIstanza mudeTDatiIstanza = new MudeTDatiIstanza();
        mudeTDatiIstanza.setDato(DatiIstanza.TIPOLOGIA_PRESENTATORE);
        mudeTDatiIstanza.setValore(request.getTipologiaPresentatore().getId().toString());
        mudeTDatiIstanza.setMudeTIstanza(mudeTIstanza);

        mudeTDatiIstanzaRepository.saveDAO(mudeTDatiIstanza);*/

        // recupero la lista di titoli a seconda del tipo presentatore salvato sull'istanza
        List<SelectVO> titoliList = new ArrayList<SelectVO>();
        List<MudeDTitolo> mudeDTitolos = mudeDTitoloRepository.findByIdTipoPresentatoreAndDataFineIsNull(request.getTipologiaPresentatore().loadIdAsLong());
        for (MudeDTitolo mudeDTitolo : mudeDTitolos) {
            titoliList.add(new SelectVO(mudeDTitolo.getCodice(), mudeDTitolo.getDescrizione()));
        }

        //modifico step istanza
        //mudeTIstanza.setStep(StepIstanza.DEFINITO_TIPO_PRESENTATORE);
        mudeTIstanzaRepository.saveDAO(mudeTIstanza);

        utilsTraceCsiLogAuditService.traceCsiLogAudit(CsiLogAudit.TraceOperation.SALVA_TIPOLOGIA_PRESENTATORE_ISTANZA.getOperation(), mudeTIstanza.getTableName(), "idIstanza=" + mudeTIstanza.getId() + ", idTipologiaPresentatore=" + request.getTipologiaPresentatore().getId(), mudeTUser.getCf(), Thread.currentThread().getStackTrace()[1].getMethodName());

        return titoliList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public IstanzaVO salvaTitoloSoggettoAbilitato(SalvaTitoloSoggettoAbilitatoRequest request, MudeTUser mudeTUser) {
        // cerco istanza

        IstanzaVO istanza = new IstanzaVO();
        
        MudeTIstanza mudeTIstanza = mudeTIstanzaRepository.findOne(request.getIdIstanza());
        if (mudeTIstanza == null)
            throw new RuntimeException("mudeTIstanza with id[" + request.getIdIstanza() + "] null!");
        
        if (request.getTitoloSoggettoAbilitato() != null && request.getTitoloSoggettoAbilitato().getId() != null) {
        	MudeTDatiIstanza mudeTDatiIstanza = mudeTDatiIstanzaRepository.findByIstanzaAndSoggetto(request.getIdIstanza(), request.getSoggettoRappresentato().getId());
            if(mudeTDatiIstanza == null)
                mudeTDatiIstanza = new MudeTDatiIstanza();

            // Optional<MudeTContatto> soggettoOpt = mudeTContattoRepository.findById(request.getSoggettoRappresentato().getId(), mudeTUser.getCf());
            // changed to include any subject from any address book
            mudeTDatiIstanza.setMudeTContatto(mudeTContattoRepository.findOne(request.getSoggettoRappresentato().getId()));

            mudeTDatiIstanza.setDato(DatiIstanza.TITOLO_SOGGETTO_ABILITATO);
            mudeTDatiIstanza.setValore(request.getTitoloSoggettoAbilitato().getId().toString());
            mudeTDatiIstanza.setMudeTIstanza(mudeTIstanza);
            mudeTDatiIstanzaRepository.saveDAO(mudeTDatiIstanza);
        }
        
        if (request.getTitoloSoggettoAbilitatoRT() != null && request.getTitoloSoggettoAbilitatoRT().getId() != null) {
        	MudeTDatiIstanza mudeTDatiIstanza = mudeTDatiIstanzaRepository.findByIstanzaAndSoggettoRT(request.getIdIstanza(), request.getSoggettoRappresentato().getId());
            if(mudeTDatiIstanza == null)
                mudeTDatiIstanza = new MudeTDatiIstanza();

            // Optional<MudeTContatto> soggettoOpt = mudeTContattoRepository.findById(request.getSoggettoRappresentato().getId(), mudeTUser.getCf());
            // changed to include any subject from any address book
            mudeTDatiIstanza.setMudeTContatto(mudeTContattoRepository.findOne(request.getSoggettoRappresentato().getId()));

            mudeTDatiIstanza.setDato(DatiIstanza.TITOLO_SOGGETTO_ABILITATO_RT);
            mudeTDatiIstanza.setValore(request.getTitoloSoggettoAbilitatoRT().getId().toString());
            mudeTDatiIstanza.setMudeTIstanza(mudeTIstanza);
            mudeTDatiIstanzaRepository.saveDAO(mudeTDatiIstanza);
        }

        // se esiste nella request il soggetto rappresentato, lo salvo e lo aggancio all'istanza
        if (request.getSoggettoRappresentato() != null) {
            MudeTContatto mudeTContatto = mudeTContattoRepository.findFirstByGuidAndMudeTUser_IdUserAndDataCessazioneNullOrderByIdDesc(request.getSoggettoRappresentato().getGuid(), mudeTUser.getIdUser());
            if(mudeTContatto != null) {
            	mudeTIstanza.setIdSoggettoRappresentato(mudeTContatto.getId());
                mudeTIstanzaRepository.saveDAO(mudeTIstanza);
            }
        }

        istanza = istanzaEntityMapper.mapEntityToSlimVO(mudeTIstanza, null);

        utilsTraceCsiLogAuditService.traceCsiLogAudit(CsiLogAudit.TraceOperation.SALVA_TITOLO_PRESENTATORE_ISTANZA.getOperation(), mudeTIstanza.getTableName(), "idIstanza=" + mudeTIstanza.getId() + ", idTitoloSoggettoAbilitato=" + (request.getTitoloSoggettoAbilitato() != null ? request.getTitoloSoggettoAbilitato().getId() : ""), mudeTUser.getCf(), Thread.currentThread().getStackTrace()[1].getMethodName());

        return istanza;
    }

    @Override
    public Page<IstanzaVO> recuperaIstanzeUtente(MudeTUser userCF, int page, int size) {

        Pageable pageble = new PageRequest(page, size);
        List<IstanzaVO> istanzeList = new ArrayList<IstanzaVO>();

        // cerco le istanze create dall'utente
        //todo, modificare cercando tutte le istanza di un fascicolo
        List<MudeTIstanza> istanzeUtente = null;//mudeTIstanzaRepository.findByMudeTUser(userCF);
        for (MudeTIstanza istanzaUtente : istanzeUtente) {
            istanzeList.add(istanzaEntityMapper.mapEntityToVO(istanzaUtente, userCF));
        }

        List<MudeTContatto> contattiConCFUtente = mudeTContattoRepository.findByCf(userCF.getCf());
        if (contattiConCFUtente != null && contattiConCFUtente.size() > 0) {
            // cerco MudeRIstanzaSoggetto le istanze collegate al contatto
            for(MudeRIstanzaSoggetto istanzaSoggetto : mudeRIstanzaSoggettoRepository.findByMudeTContattoIn(contattiConCFUtente))
        		istanzaEntityMapper.mapEntityToSlimVO(istanzaSoggetto.getMudeTIstanza(), null);
        }

        return new PageImpl<IstanzaVO>(istanzeList, pageble, istanzeList.size());
    }

    private IstanzaVO getIstanzaVO(MudeTIstanza mudeTIstanza, MudeTUser mudeTUser, String filters) {

        mudeTIstanza.setMudeTDatiIstanzas(mudeTDatiIstanzaRepository.findByMudeTIstanza(mudeTIstanza));
        IstanzaVO istanza = istanzaEntityMapper.mapEntityToVO(mudeTIstanza, mudeTUser, filters);
        return istanza;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void eliminaIstanza(MudeTUser mudeTUser, Long id) {

        MudeTIstanza mudeTIstanza = mudeTIstanzaRepository.findOne(id);
        if (mudeTIstanza != null) {
            mudeTAllegatoRepository.deleteByIstanza(mudeTIstanza.getId());
            mudeRIstanzaStatoRepository.deleteStatiByIstanza(mudeTIstanza.getId());
            mudeRIstanzaQuadroUtenteRepository.deleteByIstanza(mudeTIstanza.getId());
            mudeRIstanzaUtenteQuadroRepository.deleteByIstanza(mudeTIstanza.getId());
            mudeRIstanzaSoggettoRepository.deleteByMudeTIstanza_id(mudeTIstanza.getId());
            mudeRIstanzaEnteRepository.deleteByIstanza_id(mudeTIstanza.getId());
            mudeRNotificaUtenteRepository.deleteByIstanza_id(mudeTIstanza.getId());
            mudeRIstanzaTracciatoRepository.deleteByMudeTIstanza_id(mudeTIstanza.getId());
            mudeTLogTracciatoRepository.deleteByMudeTIstanza_id(mudeTIstanza.getId());
            
            mudeRNotificaContattoRepository.deleteByIstanza_id(mudeTIstanza.getId());
            mudeTNotificaRepository.deleteByIstanza_id(mudeTIstanza.getId());
            
            mudeRIstanzaUtenteRepository.deleteByIstanza(mudeTIstanza.getId());
            mudeTIstanzaRepository.delete(mudeTIstanza);
        } else {
            // lancio eccezione per istanza inesistente
            throw new BusinessException("Istanza non trovata, impossibile eliminare!");
        }

        utilsTraceCsiLogAuditService.traceCsiLogAudit(CsiLogAudit.TraceOperation.ELIMINA_ISTANZA.getOperation(), mudeTIstanza.getTableName(), "id=" + mudeTIstanza.getId() + ", condiceIStanza" + mudeTIstanza.getCodiceIstanza(), mudeTUser.getCf(), Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    @Override
    public Response cercaIstanzeUtente(String filter, MudeTUser mudeTUser, String scope, int page, int size, String sortBy) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        if (StringUtils.isBlank(sortBy))
        	sortBy = "-dataCreazione";
        
        Pageable pageable = new PageRequest(page, size, new Sort(sortBy.charAt(0) == '-'? Sort.Direction.DESC :  Sort.Direction.ASC, sortBy.substring(1)));

        Specifications<MudeTIstanza> allSpecs = getIstanzeSpecifications(filter, mudeTUser, scope, methodName);
        if(allSpecs == null)
    		return PaginationResponseUtil.buildResponse(new ArrayList<IstanzaVO>(), page, size, 0, 0);
        
        Page<MudeTIstanza> mudeTIstanzaList;
        if("backoffice.ds-istanza".equals(FilterUtil.getStringValue(filter, "result_type")) 
        		&& mudeCProprietaRepository.getValueByName("BO_DS_FILTRA_VARIANTI", "").length() > 0) {
	        Specification<MudeTIstanza> groupByMinDS = MudeTIstanzaSpecification.filterOnlyRootDS(allSpecs);
        	
        	mudeTIstanzaList = mudeTIstanzaRepository.findAll(groupByMinDS, pageable);
        }
        else
        	mudeTIstanzaList = mudeTIstanzaRepository.findAll(allSpecs, pageable);

        String typeOfResult = FilterUtil.getStringValue(filter, "result_type");
        if(typeOfResult == null && FRONTOFFICE.equals(scope))
        	typeOfResult = "frontoffice-slim1";
        if(typeOfResult == null && BACKOFFICE.equals(scope))
        	typeOfResult = BACKOFFICE;
        
        List<IstanzaVO> istanzeVoList = istanzaEntityMapper.mapListEntityToListSlimVO(
        																	mudeTIstanzaList.getContent(), 
        																	mudeTUser, 
        																	"backoffice.ds-set-controllo-campione".equals(typeOfResult)? "essential" : typeOfResult);
        
        if("backoffice.ds-set-controllo-campione".equals(typeOfResult))
        	istanzeVoList = cosmoService.markIstancesAsToBeChecked(istanzeVoList);
        else if(page == 0 && FRONTOFFICE.equals(scope) && istanzeVoList.size() > 0)
        	istanzeVoList.get(0).setStatoCounters(getAllStateCounters(allSpecs));
        
        return PaginationResponseUtil.buildResponse(istanzeVoList, page, size, mudeTIstanzaList.getTotalPages(), mudeTIstanzaList.getTotalElements());
    }
    
    @Override
    public Specifications<MudeTIstanza> getIstanzeSpecifications(String filter, MudeTUser mudeTUser, String scope, String methodName) {
        boolean isDSRegional = "backoffice.ds-istanza".equals(FilterUtil.getStringValue(filter, "result_type"));
        boolean isDSControlloCampione = "backoffice.ds-scrivania".equals(FilterUtil.getStringValue(filter, "result_type"))
        									|| "backoffice.ds-set-controllo-campione".equals(FilterUtil.getStringValue(filter, "result_type"));
    	
        Long idFascicolo = FilterUtil.getLongValue(filter, "id_fascicolo");
        String codiceTipoIStanza = FilterUtil.getStringValue(filter, "id_tipo_istanza");
        String idIntestatarioPf = FilterUtil.getStringValue(filter, "id_intestatario_pf");
        String idIntestatarioPg = FilterUtil.getStringValue(filter, "id_intestatario_pg");
        //Filtro PM
        String idPm = FilterUtil.getStringValue(filter, "id_pm");

        Long idComune = FilterUtil.getLongValue(filter, "id_comune");
        Long idProvincia = FilterUtil.getLongValue(filter, "id_provincia");
        Long idDug = FilterUtil.getLongValue(filter, "id_dug");
        String duf = FilterUtil.getStringValue(filter, "duf");
        LocalDate creationDateFrom = FilterUtil.getDateFromValue(filter, "data_creazione");
        LocalDate creationDateTo = FilterUtil.getDateToValue(filter, "data_creazione");
        String code = FilterUtil.getStringValue(filter, "codice_istanza");
        
        Long idPratica = null;
        String numPratica = null;
        if(!"pratiche".equals(scope)) { // is it call from pratiche?
            idPratica = FilterUtil.getLongValue(filter, "id_pratica");
            numPratica = FilterUtil.getStringValue(filter, "num_pratica");
        }
    	
        List<Long> comuni = null;
        MudeTFascicolo mudeTFascicolo = null;
        Optional<MudeTPratica> mudeTPraticaOpt = null; 
        MudeDTipoIstanza mudeDTipoIstanza = null;
        //        StatoIstanza statoIstanza = null;

        if (idComune != null && idComune == 0) {
            idComune = null;
        }

        if (idProvincia != null && idProvincia == 0) {
            idProvincia = null;
        }

        if (idDug != null && idDug == 0) {
            idDug = null;
        }

        if (idComune != null) {
            MudeDComune mudeDComune = mudeDComuneRepository.findByIdComune(idComune);
            comuni = new ArrayList<Long>();
            comuni.add(mudeDComune.getIdComune());
        } else if (idProvincia != null) {
            MudeDProvincia mudeDProvincia = mudeDProvinciaRepository.findOne(idProvincia);
            comuni = mudeDComuneRepository.findIdComuneByMudeDProvinciaOrderByDenomComuneAsc(mudeDProvincia);
        }

        if (StringUtils.isNotBlank(codiceTipoIStanza)) {
            mudeDTipoIstanza = mudeDTipoIstanzaRepository.findOne(codiceTipoIStanza);
        }

        if (idFascicolo != null) {
            mudeTFascicolo = mudeTFascicoloRepository.findOne(idFascicolo);
        }
        
        List<Long> praticaIDs = new ArrayList();
        if (StringUtils.isNotBlank(numPratica)) {
        	List<MudeTPratica> optPratiche = mudeTPraticaRepository.getByNumeroPratica(numPratica);
        	if(optPratiche.isEmpty()) return null;
        	
        	for(MudeTPratica optPratica : optPratiche)
        		praticaIDs.add(optPratica.getId());
        }
        
        if (idPratica != null)
            praticaIDs.add(idPratica);

        if((StringUtils.isNotBlank(codiceTipoIStanza) && mudeDTipoIstanza == null) ||
                (idFascicolo != null && mudeTFascicolo == null) ||	
                ((StringUtils.isNotBlank(numPratica) || idPratica != null) && praticaIDs.isEmpty()) )
        	return null;
        
        Specification<MudeTIstanza> includeConnectedIds = null;
        if(FilterUtil.getValue(filter, "id_istanza_connesso") != null) {
        	List<Long> ids = mudeTIstanzaRepository.getAllConnectedInstances(idFascicolo, FilterUtil.getLongValue(filter, "id")).stream().map(x -> { return x.getId(); } ).collect(Collectors.toList());
        	HashMap map = new HashMap();
        	map.put("in", ids);
			includeConnectedIds = MudeTIstanzaSpecification.findById(map );
        }
        
        Specification<MudeTIstanza> filterControlloCampione = null;
        Specification<MudeTIstanza> filterStato = null;
        if(isDSControlloCampione) {
        	int _year = -1, _month = -1;
        	try {
        		_year = Integer.parseInt(FilterUtil.getStringValue(filter, "cc_year"));
        		_month = Integer.parseInt(FilterUtil.getStringValue(filter, "cc_month"));
			} catch (Exception skip) { }
        	
            filterControlloCampione = MudeTIstanzaSpecification.findByControlloCampione(_year, _month);
        }
        else
            filterStato = MudeTIstanzaSpecification.hasStato(FilterUtil.getValue(filter, "stato"));
        
        Specification<MudeTIstanza> filterById = MudeTIstanzaSpecification.findById(FilterUtil.getValue(filter, "id"));
        Specification<MudeTIstanza> filterByNumeroProtocolloIstanza = MudeTIstanzaSpecification.findByNumeroProtocolloIstanza(FilterUtil.getValue(filter, "numero_protocollo"));
        Specification<MudeTIstanza> filterTipoIstanza = MudeTIstanzaSpecification.hasTipoIstanza(FilterUtil.getValue(filter, "cod_tipo_istanza"));
        Specification<MudeTIstanza> filterDataRegistrazioneDa = MudeTIstanzaSpecification.findByRegistrazioneFrom(FilterUtil.getDateFromValue(filter, "data_registrazione"));
        Specification<MudeTIstanza> filterDataRegistrazioneA = MudeTIstanzaSpecification.findByRegistrazioneTo(FilterUtil.getDateToValue(filter, "data_registrazione"));
        Specification<MudeTIstanza> filterDataProtocolloDa = MudeTIstanzaSpecification.findByProtocolloFrom(FilterUtil.getDateFromValue(filter, "data_protocollo"));
        Specification<MudeTIstanza> filterDataProtocolloA = MudeTIstanzaSpecification.findByProtocolloTo(FilterUtil.getDateToValue(filter, "data_protocollo"));
        Specification<MudeTIstanza> filterDataDpsDa = MudeTIstanzaSpecification.findByDataDpsFrom(FilterUtil.getStringValue(filter, "data_dps"));
        Specification<MudeTIstanza> filterDataDpsA = MudeTIstanzaSpecification.findByDataDpsTo(FilterUtil.getStringValue(filter, "data_dps"));
        Specification<MudeTIstanza> filterLivello = MudeTIstanzaSpecification.hasLivello(FilterUtil.getValue(filter, "livello"));
        Specification<MudeTIstanza> filterKeywords = MudeTIstanzaSpecification.findByKeywords(FilterUtil.getValue(filter, "keywords"));

        /* ricerca istanze DS */
        Specification<MudeTIstanza> filterTipiPratica = MudeTIstanzaSpecification.findByTipoPratica(FilterUtil.getValue(filter, "tipi_pratica"));
        
        Specification<MudeTIstanza> filterTipoData = null;
        if(!StringUtils.isBlank(FilterUtil.getStringValue(filter, "tipo_data")))
	        filterTipoData = MudeTIstanzaSpecification.findByDates(FilterUtil.getValue(filter, "tipo_data"), 
    																					FilterUtil.getValue(filter, "data_from"), 
    																					FilterUtil.getValue(filter, "data_to"));
        Specification<MudeTIstanza> filterNumeroCivico = MudeTIstanzaSpecification.findByIndirizzo(idComune, 
																						FilterUtil.getValue(filter, "numero_civico"),
																						FilterUtil.getValue(filter, "interno"),
																						FilterUtil.getValue(filter, "scala"), 
																						FilterUtil.getValue(filter, "piano"));
        Specification<MudeTIstanza> filterTipoCatasto = MudeTIstanzaSpecification.findByTipoCatasto(idComune,
        																				FilterUtil.getValue(filter, "tipo_catasto"),
        																				FilterUtil.getValue(filter, "foglio"),
        																				FilterUtil.getValue(filter, "numero"),
        																				FilterUtil.getValue(filter, "subalterno"));
        
        // filters for FO/MUDE "fonte"
        Specification<MudeTIstanza> filterFonte = null;
        if(scope == null || FRONTOFFICE.equals(scope) || "pratiche".equals(scope)) {
        	HashMap map = new HashMap();
        	map.put("ne", "MOON");
			filterFonte = MudeTIstanzaSpecification.hasFonte(map );
        }
        
		List<String> soggettoRole = getRuoliSoggetti(filter);
        
        // this should be split for each specification
        Specification<MudeTIstanza> filters = MudeTIstanzaSpecification.findBy(mudeTUser, 
        																		comuni, 
        																		idDug, 
        																		duf, 
        																		idIntestatarioPf, 
        																		idIntestatarioPg, 
        																		idPm, 
        																		mudeTFascicolo, 
        																		praticaIDs, 
        																		mudeDTipoIstanza, 
        																		creationDateFrom, 
        																		creationDateTo, 
        																		code, 
        																		scope, 
        																		filterById != null,
        																        isDSRegional,
        																        isDSControlloCampione || filterTipoData != null,
        																        soggettoRole);

        Specification<MudeTIstanza> filterByComuneAndEnte = null;
		if(BACKOFFICE.equals(scope))
			filterByComuneAndEnte = MudeTIstanzaSpecification.filterByComuneAndEnte(mudeTUser.getIdUser());

        return Specifications.where(filters)
        					.and(filterStato)
        					.and(filterById)
        					.and(filterTipoIstanza)
        					.and(includeConnectedIds)
        					.and(filterFonte)
        					.and(filterByNumeroProtocolloIstanza)
        					.and(filterDataRegistrazioneDa)
        					.and(filterDataRegistrazioneA)
        					.and(filterDataProtocolloDa)
        					.and(filterDataProtocolloA)
        					.and(filterDataDpsDa)
        					.and(filterDataDpsA)
        					.and(filterLivello)
        					.and(filterKeywords)
        					.and(filterByComuneAndEnte)
        					.and(filterTipiPratica)
        					.and(filterTipoData)
        					.and(filterNumeroCivico)
        					.and(filterTipoCatasto)
        	        		.and(getDSRoleSPSpecification(mudeTUser, filter))
        					.and(filterControlloCampione);
    }

    @Override
    public Specifications<MudeTIstanzaSlim> getIstanzeSpecificationsSlim(String filter, MudeTUser mudeTUser, String scope, String methodName) {
        boolean isDSRegional = "backoffice.ds-istanza".equals(FilterUtil.getStringValue(filter, "result_type"));
        boolean isDSControlloCampione = "backoffice.ds-scrivania".equals(FilterUtil.getStringValue(filter, "result_type"))
        									|| "backoffice.ds-set-controllo-campione".equals(FilterUtil.getStringValue(filter, "result_type"));
    	
        Long idFascicolo = FilterUtil.getLongValue(filter, "id_fascicolo");
        String codiceTipoIStanza = FilterUtil.getStringValue(filter, "id_tipo_istanza");
        String idIntestatarioPf = FilterUtil.getStringValue(filter, "id_intestatario_pf");
        String idIntestatarioPg = FilterUtil.getStringValue(filter, "id_intestatario_pg");
        //Filtro PM
        String idPm = FilterUtil.getStringValue(filter, "id_pm");

        Long idComune = FilterUtil.getLongValue(filter, "id_comune");
        Long idProvincia = FilterUtil.getLongValue(filter, "id_provincia");
        Long idDug = FilterUtil.getLongValue(filter, "id_dug");
        String duf = FilterUtil.getStringValue(filter, "duf");
        LocalDate creationDateFrom = FilterUtil.getDateFromValue(filter, "data_creazione");
        LocalDate creationDateTo = FilterUtil.getDateToValue(filter, "data_creazione");
        String code = FilterUtil.getStringValue(filter, "codice_istanza");
        
        Long idPratica = null;
        String numPratica = null;
        if(!"pratiche".equals(scope)) { // is it call from pratiche?
            idPratica = FilterUtil.getLongValue(filter, "id_pratica");
            numPratica = FilterUtil.getStringValue(filter, "num_pratica");
        }
    	
        List<Long> comuni = null;
        MudeTFascicolo mudeTFascicolo = null;
        Optional<MudeTPratica> mudeTPraticaOpt = null; 
        //        StatoIstanza statoIstanza = null;

        if (idComune != null && idComune == 0) {
            idComune = null;
        }

        if (idProvincia != null && idProvincia == 0) {
            idProvincia = null;
        }

        if (idDug != null && idDug == 0) {
            idDug = null;
        }

        if (idComune != null) {
            MudeDComune mudeDComune = mudeDComuneRepository.findByIdComune(idComune);
            comuni = new ArrayList<Long>();
            comuni.add(mudeDComune.getIdComune());
        } else if (idProvincia != null) {
            MudeDProvincia mudeDProvincia = mudeDProvinciaRepository.findOne(idProvincia);
            comuni = mudeDComuneRepository.findIdComuneByMudeDProvinciaOrderByDenomComuneAsc(mudeDProvincia);
        }

        if (StringUtils.isNotBlank(codiceTipoIStanza))
            codiceTipoIStanza = mudeDTipoIstanzaRepository.findOne(codiceTipoIStanza).getCodice();

        if (idFascicolo != null) {
            mudeTFascicolo = mudeTFascicoloRepository.findOne(idFascicolo);
        }
        
        List<Long> praticaIDs = new ArrayList();
        if (StringUtils.isNotBlank(numPratica)) {
        	List<MudeTPratica> optPratiche = mudeTPraticaRepository.getByNumeroPratica(numPratica);
        	if(optPratiche.isEmpty()) return null;
        	
        	for(MudeTPratica optPratica : optPratiche)
        		praticaIDs.add(optPratica.getId());
        }
        
        if (idPratica != null)
            praticaIDs.add(idPratica);

        if((StringUtils.isNotBlank(codiceTipoIStanza) && codiceTipoIStanza == null) ||
                (idFascicolo != null && mudeTFascicolo == null) ||	
                ((StringUtils.isNotBlank(numPratica) || idPratica != null) && praticaIDs.isEmpty()) )
        	return null;
        
        Specification<MudeTIstanzaSlim> includeConnectedIds = null;
        if(FilterUtil.getValue(filter, "id_istanza_connesso") != null) {
        	List<Long> ids = mudeTIstanzaRepository.getAllConnectedInstances(idFascicolo, FilterUtil.getLongValue(filter, "id")).stream().map(x -> { return x.getId(); } ).collect(Collectors.toList());
        	HashMap map = new HashMap();
        	map.put("in", ids);
			includeConnectedIds = MudeTIstanzaSlimSpecification.findById(map );
        }
        
        Specification<MudeTIstanzaSlim> filterControlloCampione = null;
        Specification<MudeTIstanzaSlim> filterStato = null;
        if(isDSControlloCampione) {
        	int _year = -1, _month = -1;
        	try {
        		_year = Integer.parseInt(FilterUtil.getStringValue(filter, "cc_year"));
        		_month = Integer.parseInt(FilterUtil.getStringValue(filter, "cc_month"));
			} catch (Exception skip) { }
        	
            filterControlloCampione = MudeTIstanzaSlimSpecification.findByControlloCampione(_year, _month);
        }
        else
            filterStato = MudeTIstanzaSlimSpecification.hasStato(FilterUtil.getValue(filter, "stato"));
        
        Specification<MudeTIstanzaSlim> filterById = MudeTIstanzaSlimSpecification.findById(FilterUtil.getValue(filter, "id"));
        Specification<MudeTIstanzaSlim> filterByNumeroProtocolloIstanza = MudeTIstanzaSlimSpecification.findByNumeroProtocolloIstanza(FilterUtil.getValue(filter, "numero_protocollo"));
        Specification<MudeTIstanzaSlim> filterTipoIstanza = MudeTIstanzaSlimSpecification.hasTipoIstanza(FilterUtil.getValue(filter, "cod_tipo_istanza"));
        Specification<MudeTIstanzaSlim> filterDataRegistrazioneDa = MudeTIstanzaSlimSpecification.findByRegistrazioneFrom(FilterUtil.getDateFromValue(filter, "data_registrazione"));
        Specification<MudeTIstanzaSlim> filterDataRegistrazioneA = MudeTIstanzaSlimSpecification.findByRegistrazioneTo(FilterUtil.getDateToValue(filter, "data_registrazione"));
        Specification<MudeTIstanzaSlim> filterDataProtocolloDa = MudeTIstanzaSlimSpecification.findByProtocolloFrom(FilterUtil.getDateFromValue(filter, "data_protocollo"));
        Specification<MudeTIstanzaSlim> filterDataProtocolloA = MudeTIstanzaSlimSpecification.findByProtocolloTo(FilterUtil.getDateToValue(filter, "data_protocollo"));
        Specification<MudeTIstanzaSlim> filterDataDpsDa = MudeTIstanzaSlimSpecification.findByDataDpsFrom(FilterUtil.getStringValue(filter, "data_dps"));
        Specification<MudeTIstanzaSlim> filterDataDpsA = MudeTIstanzaSlimSpecification.findByDataDpsTo(FilterUtil.getStringValue(filter, "data_dps"));
        Specification<MudeTIstanzaSlim> filterLivello = MudeTIstanzaSlimSpecification.hasLivello(FilterUtil.getValue(filter, "livello"));
        Specification<MudeTIstanzaSlim> filterKeywords = MudeTIstanzaSlimSpecification.findByKeywords(FilterUtil.getValue(filter, "keywords"));

        /* ricerca istanze DS */
        Specification<MudeTIstanzaSlim> filterTipiPratica = MudeTIstanzaSlimSpecification.findByTipoPratica(FilterUtil.getValue(filter, "tipi_pratica"));
        
        Specification<MudeTIstanzaSlim> filterTipoData = null;
        if(!StringUtils.isBlank(FilterUtil.getStringValue(filter, "tipo_data")))
	        filterTipoData = MudeTIstanzaSlimSpecification.findByDates(FilterUtil.getValue(filter, "tipo_data"), 
    																					FilterUtil.getValue(filter, "data_from"), 
    																					FilterUtil.getValue(filter, "data_to"));
        Specification<MudeTIstanzaSlim> filterNumeroCivico = MudeTIstanzaSlimSpecification.findByIndirizzo(idComune, 
																						FilterUtil.getValue(filter, "numero_civico"),
																						FilterUtil.getValue(filter, "interno"),
																						FilterUtil.getValue(filter, "scala"), 
																						FilterUtil.getValue(filter, "piano"));
        Specification<MudeTIstanzaSlim> filterTipoCatasto = MudeTIstanzaSlimSpecification.findByTipoCatasto(idComune,
        																				FilterUtil.getValue(filter, "tipo_catasto"),
        																				FilterUtil.getValue(filter, "foglio"),
        																				FilterUtil.getValue(filter, "numero"),
        																				FilterUtil.getValue(filter, "subalterno"));
        
        // filters for FO/MUDE "fonte"
        Specification<MudeTIstanzaSlim> filterFonte = null;
        if(scope == null || FRONTOFFICE.equals(scope) || "pratiche".equals(scope)) {
        	HashMap map = new HashMap();
        	map.put("ne", "MOON");
			filterFonte = MudeTIstanzaSlimSpecification.hasFonte(map );
        }
        
        // this should be split for each specification
        Specification<MudeTIstanzaSlim> filters = MudeTIstanzaSlimSpecification.findBy(mudeTUser, 
        																		comuni, 
        																		idDug, 
        																		duf, 
        																		idIntestatarioPf, 
        																		idIntestatarioPg, 
        																		idPm, 
        																		mudeTFascicolo, 
        																		praticaIDs, 
        																		codiceTipoIStanza, 
        																		creationDateFrom, 
        																		creationDateTo, 
        																		code, 
        																		scope, 
        																		filterById != null,
        																        isDSRegional,
        																        isDSControlloCampione || filterTipoData != null);

        Specification<MudeTIstanzaSlim> filterByComuneAndEnte = null;
		if(BACKOFFICE.equals(scope))
			filterByComuneAndEnte = MudeTIstanzaSlimSpecification.filterByComuneAndEnte(mudeTUser.getIdUser());

        return Specifications.where(filters)
        					.and(filterStato)
        					.and(filterById)
        					.and(filterTipoIstanza)
        					.and(includeConnectedIds)
        					.and(filterFonte)
        					.and(filterByNumeroProtocolloIstanza)
        					.and(filterDataRegistrazioneDa)
        					.and(filterDataRegistrazioneA)
        					.and(filterDataProtocolloDa)
        					.and(filterDataProtocolloA)
        					.and(filterDataDpsDa)
        					.and(filterDataDpsA)
        					.and(filterLivello)
        					.and(filterKeywords)
        					.and(filterByComuneAndEnte)
        					.and(filterTipiPratica)
        					.and(filterTipoData)
        					.and(filterNumeroCivico)
        					.and(filterTipoCatasto)
        	        		.and(getDSRoleSPSpecificationSlim(mudeTUser, filter))
        					.and(filterControlloCampione);
    }

    @Override
    public MudeTIstanzaSlim recuperaIstanzaSlim(Long idIstanza) {
        MudeTIstanzaSlim mudeTIstanza = mudeTIstanzaSlimRepository.findOne(idIstanza);
        return  mudeTIstanza;

    }
    
    private String getAllStateCounters(Specifications<MudeTIstanza> allSpecs) {

    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<MudeTIstanza> root = query.from(MudeTIstanza.class);
        
        CriteriaQuery<Long> cq = query.select(root.get("id")).where(allSpecs.toPredicate(root, query, cb));
        Query q = entityManager.createQuery(cq);
        List<Long> idIstanzeList = q.getResultList(); // resultList has Entity that only contains id
        // String sql = q.unwrap(org.hibernate.Query.class).getQueryString();
        
    	return mudeTIstanzaRepository.getAllStateCunters(idIstanzeList).toString();
    }

    
    @Override
    public Response cercaPraticheUtente(MudeTUser mudeTUser, Long idFascicolo, Long idPratica, String code, String idIntestatarioPf, String idIntestatarioPg, String idPm, Long idIstanzaRiferimento, String codiceTipoIStanza, String numeroProtocollo, LocalDate dataProtocolloDa, LocalDate dataProtocolloA, Long anno, String numPratica, LocalDate dataRegistrazionePraticaDa, LocalDate dataRegistrazionePraticaA, String scope, int page, int size) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        Pageable pageble = new PageRequest(page, size, new Sort(Sort.Direction.DESC, "dataCreazione"));

        MudeTFascicolo mudeTFascicolo = null;
        MudeTPratica mudeTPratica = null;
        MudeDTipoIstanza mudeDTipoIstanza = null;
        StatoIstanza statoIstanza = StatoIstanza.DEPOSITATA;

        if (StringUtils.isNotBlank(codiceTipoIStanza))
            mudeDTipoIstanza = mudeDTipoIstanzaRepository.findOne(codiceTipoIStanza);

        if (idFascicolo != null)
            mudeTFascicolo = mudeTFascicoloRepository.findOne(idFascicolo);
        
        if (numPratica != null) {
        	List<MudeTPratica> optPratica = mudeTPraticaRepository.getByNumeroPratica(numPratica);
            mudeTPratica = !optPratica.isEmpty() ? optPratica.get(0) : null;
        }
        
        if (idPratica != null) {
			Optional<MudeTPratica> opt = mudeTPraticaRepository.findById(idPratica);
			mudeTPratica = opt.isPresent() ? opt.get() : null;
		}
        
        if((StringUtils.isNotBlank(codiceTipoIStanza) && mudeDTipoIstanza == null) ||
                (idFascicolo != null && mudeTFascicolo == null) ||	
                (numPratica != null && mudeTPratica == null) ) {

        	return PaginationResponseUtil.buildResponse(new ArrayList(), page, size, 0, 0);
         }
        
        Specifications<MudeTIstanza> allSpecs = Specifications.where(MudeTIstanzaSpecification.findPraticheBy(mudeTUser, code, idIntestatarioPf, idIntestatarioPg, idPm, idIstanzaRiferimento, mudeTFascicolo, mudeDTipoIstanza, numeroProtocollo, dataProtocolloDa, dataProtocolloA, anno, mudeTPratica, statoIstanza, dataRegistrazionePraticaDa, dataRegistrazionePraticaA, scope));
        Page<MudeTIstanza> mudeTIstanzaList = mudeTIstanzaRepository.findAll(allSpecs, pageble);
		List<IstanzaVO> istanzeVoList = istanzaEntityMapper.mapListEntityToListVO(mudeTIstanzaList.getContent(), mudeTUser);
		
        if(page == 0 && FRONTOFFICE.equals(scope) && istanzeVoList.size() > 0)
        	istanzeVoList.get(0).setStatoCounters(getAllStateCounters(allSpecs));

        return PaginationResponseUtil.buildResponse(istanzeVoList, page, size, mudeTIstanzaList.getTotalPages(), mudeTIstanzaList.getTotalElements());
    }

    @Override
    public Response cercaIstanzeFascicolo(Long idFascicolo, String filter, String sort, int page, int size, MudeTUser mudeTUser, String scope) {
        boolean isBackoffice = "backoffice".equals(scope);

        Pageable pageble = new PageRequest(page, size);
        List<IstanzaVO> istanzeVoList = new ArrayList<IstanzaVO>();

        MudeTFascicolo mudeTFascicolo = null;

        if (idFascicolo != null)
            mudeTFascicolo = mudeTFascicoloRepository.findOne(idFascicolo);

        Specification<MudeTIstanza> specs = MudeTIstanzaSpecification.findByFascicolo(mudeTFascicolo, isBackoffice? null : mudeTUser.getIdUser());
        Specification<MudeTIstanza> boStatoSpec = null;
        Specification<MudeTIstanza> boTipoIstSpec = null;
        Specification<MudeTIstanza> boInPratica = null;
        if(isBackoffice) {
        	Map<String, List<String>> map1 = new HashMap<>();
        	map1.put(QueryOpEnum.NOT_IN.getValue(), new ArrayList<String>(Arrays.asList("BZZ", "DFR", "FRM", "RPA", "DPS", "PRC", "DFR")));
        	boStatoSpec = MudeTIstanzaSpecification.hasStato(map1);
        	
        	Map<String, List<String>> map2 = new HashMap<>();
        	map2.put(QueryOpEnum.IN.getValue(), new ArrayList<String>(Arrays.asList("DENUNCIA-SISMICA", "IL-DS", "FIL-DS", "INT-DOC", "DRE-DS","RSU-DS","COLLAUDO-DS","VAR-NO-SOST-DS","NOMINA-COLL-DS","REL-ILL-DS","NOMINA-COSTR-DS","VAR-INTEST-DS")));
        	boTipoIstSpec = MudeTIstanzaSpecification.hasTipoIstanza(map2);
        	
        	boInPratica = MudeTIstanzaSpecification.findByNumeroPratica(FilterUtil.getStringValue(filter, "numeroPratica"));
        }
        	
        Page<MudeTIstanza> mudeTIstanzaList = mudeTIstanzaRepository.findAll(
        		Specifications.where(specs)
        			.and(boStatoSpec)
        			.and(boTipoIstSpec)
        			.and(boInPratica),
				pageble);

        istanzeVoList = istanzaEntityMapper.mapListEntityToListSlimVO(mudeTIstanzaList.getContent(), mudeTUser, isBackoffice? null : "frontoffice-slim1");


        //        return new PageImpl<IstanzaVO>(istanzeVoList, pageble, istanzeVoList.size());

        return PaginationResponseUtil.buildResponse(istanzeVoList, page, size, mudeTIstanzaList.getTotalPages(), mudeTIstanzaList.getTotalElements());
    }

    @Override
    public List<SelectVO> getListaTipoPresentatore(MudeTUser userCF, Long idTipoPresentatore) {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();


        List<SelectVO> titoliList = new ArrayList<SelectVO>();
        List<MudeDTitolo> mudeDTitolos = mudeDTitoloRepository.findByIdTipoPresentatoreAndDataFineIsNull(idTipoPresentatore);
        for (MudeDTitolo mudeDTitolo : mudeDTitolos) {
            titoliList.add(new SelectVO(mudeDTitolo.getCodice(), mudeDTitolo.getDescrizione()));
        }


        return titoliList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public RuoliIstanzaResponse eliminaSoggettoCoinvolto(Long idIstanza, Long idSoggetto, MudeTUser mudeTUser) {


        // cerco contatto
        MudeTContatto mudeTContatto = mudeTContattoRepository.findOne(idSoggetto);
        if (mudeTContatto == null) {
            throw new RuntimeException("mudeTContatto with id[" + idSoggetto + "] null!");
        }

        // cerco istanza
        MudeTIstanza mudeTIstanza = mudeTIstanzaRepository.findOne(idIstanza);
        if (mudeTIstanza == null) {
            throw new RuntimeException("mudeTIstanza with id[" + idIstanza + "] null!");
        }

        // controllo se sull'istanza esiste gia' il soggetto
        MudeRIstanzaSoggetto mudeRIstanzaSoggetto = mudeRIstanzaSoggettoRepository.findByMudeTContattoAndMudeTIstanza(mudeTContatto, mudeTIstanza);
        if (mudeRIstanzaSoggetto != null) {
            mudeRIstanzaSoggettoRepository.delete(mudeRIstanzaSoggetto.getIdIstanzaSoggetto());
            mudeTDatiIstanzaRepository.deleteByIstanzaAndSoggetto(mudeRIstanzaSoggetto.getMudeTIstanza().getId(), mudeRIstanzaSoggetto.getMudeTContatto().getId());
        }

        removeSubjectFromJData(mudeTIstanza, mudeTContatto, mudeRIstanzaSoggetto.getRuoli().stream().anyMatch(r -> "DE".equalsIgnoreCase(r)));

        utilsTraceCsiLogAuditService.traceCsiLogAudit(CsiLogAudit.TraceOperation.DISASSOCIA_RUOLO_ISTANZA.getOperation(), mudeTIstanza.getTableName(), "idIstanza=" + mudeTIstanza.getId() + ", idContatto=" + mudeTContatto.getId(), mudeTUser.getCf(), Thread.currentThread().getStackTrace()[1].getMethodName());

        return getRuoliIstanza(mudeTUser, idIstanza, null, true);
    }


    @Override
    public int countIstanzeByFascicolo(Long idFascicolo) {
        return mudeTIstanzaRepository.countIstanzeByFascicolo(idFascicolo);
    }

    @Override
    public void updateUuidIndex(IstanzaVO istanzaVO, MudeTUser mudeTUser) {
        MudeTIstanza istanza = mudeTIstanzaRepository.findOne(istanzaVO.getIdIstanza());
        istanza.setUuidIndex(istanzaVO.getUuidIndex());
        mudeTIstanzaRepository.saveDAO(istanza);
    }

    @Override
    public IstanzaVO updateDatiFileIstanza(IstanzaVO istanzaVO, MudeTUser mudeTUser) {
        MudeTIstanza istanza = mudeTIstanzaRepository.findOne(istanzaVO.getIdIstanza());
        istanza.setFilename(istanzaVO.getFilename());
        istanza.setDataCaricamentoFileIstanza(istanzaVO.getDataCaricamentoFileIstanza());
        istanza.setDimensioneFile(istanzaVO.getDimensioneFile());
        istanza.setUuidFileIndex(istanzaVO.getUuidFileIndex());
        mudeTIstanzaRepository.saveDAO(istanza);
        // BZZ MANUAL HANDLING - istanzaStatoService.saveIstanzaStato(istanza, StatoIstanza.BOZZA.getValue(), null, httpHeaders);
        return istanzaEntityMapper.mapEntityToSlimVO(istanza, null);
    }


    @Override
    public Boolean verifyDuplicateFilename(String codIstanza, String filename) {
        Optional<MudeTIstanza> istanzaOPT = mudeTIstanzaRepository.findByCodIstanzaAndFilename(codIstanza, filename);
        if (istanzaOPT.isPresent()) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    /**
     * Gets id template istanza.
     *
     * @param idTipoIstanza      the id tipo istanza
     * @param boTemplateOverride the bo template override
     * @param mudeTUser          the mude t user
     * @param idIstanza TODO
     * @return the id template istanza
     */
    private MudeDTemplate getIdTemplateIstanza(String idTipoIstanza, String boTemplateOverride, MudeTUser mudeTUser) {
        if(!userUtil.isBackofficeAdminUser(mudeTUser)) {
        	String maxTemaplateVer = mudeCProprietaRepository.getValueByName("MAX-TEMPLATE-VERSION-"+idTipoIstanza, "");
        	if(!"".equals(maxTemaplateVer)) {
        		boTemplateOverride = "99999"; // default max template version 
        		for(String token : maxTemaplateVer.split(",")) {
        			if(token.indexOf("=") == -1) continue;

        			if(token.split("=")[0].trim().indexOf(mudeTUser.getCf()) != -1) {
        				boTemplateOverride = token.split("=")[1].trim();
        				break;
        			}

        			if(token.indexOf("PUBBLICO") != -1)
        				boTemplateOverride = token.split("=")[1].trim();
        		}

                return mudeDTemplateRepository.findByTipoIstanza_idAndVersion(idTipoIstanza, Integer.parseInt(boTemplateOverride));
        	}
        	
            return mudeDTemplateRepository.findActiveByTipoIstanza_id(idTipoIstanza);
        }

        if(boTemplateOverride != null && !"".equals(boTemplateOverride))
            return mudeDTemplateRepository.findByTipoIstanza_idAndVersion(idTipoIstanza, Integer.parseInt(boTemplateOverride));
        
        return mudeDTemplateRepository.findByTipoIstanza_idAndMaxVersion(idTipoIstanza);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public RuoliIstanzaResponse getRuoliIstanza(MudeTUser mudeTUSer, Long idIstanza, String excl_user_ids, Boolean loadAbilitazioni) {



        RuoliIstanzaResponse ruoli = new RuoliIstanzaResponse();
        List<RuoloObbligatorioVO> ruoliObbligatori = new ArrayList<RuoloObbligatorioVO>();
        List<SoggettoIstanzaVO> soggettoPFList = new ArrayList<SoggettoIstanzaVO>();
        List<SoggettoIstanzaVO> soggettoPGList = new ArrayList<SoggettoIstanzaVO>();

        ContattoVO soggettoPFRappresentato = null;
        ContattoVO soggettoPGRappresentato = null;
        boolean almenoUnRuolo = false;

        // cerco istanza
        MudeTIstanza mudeTIstanza = mudeTIstanzaRepository.findOne(idIstanza);

        almenoUnRuolo = mudeTIstanza.getTipoIstanza().getAlmenoUnRuolo();

        // ricavo i ruoli abbligatori per il tipo istanza
        List<MudeRTipoIstanzaRuoloSoggetto> mudeRTipoIstanzaRuoloSoggettoList = mudeRTipoIstanzaRuoloSoggettoRepository.findByMudeDTipoIstanzaOrderByMudeDRuoloSoggetto_descrizione(mudeTIstanza.getTipoIstanza());
        for (MudeRTipoIstanzaRuoloSoggetto mudeRTipoIstanzaRuoloSoggetto : mudeRTipoIstanzaRuoloSoggettoList) {
            // valorizzare le liste
            // valorizzo i ruoli possibile nel for successivo
            // ruoliPossibili.add(new SelectVO(mudeRTipoIstanzaRuoloSoggetto.getMudeDRuoloSoggetto().getIdRuolo(), mudeRTipoIstanzaRuoloSoggetto.getMudeDRuoloSoggetto().getDescrizione()));

            if (mudeRTipoIstanzaRuoloSoggetto.getObbligatorio()) {
                Boolean presente = false;
                // controllo se esiste un soggetto con questo ruolo sull'istanza
                List<MudeRIstanzaSoggetto> mudeRIstanzaSoggettoList = mudeRIstanzaSoggettoRepository.findByMudeTIstanzaAndRuoli(mudeTIstanza, mudeRTipoIstanzaRuoloSoggetto.getMudeDRuoloSoggetto().getCodice());
                if (mudeRIstanzaSoggettoList != null && mudeRIstanzaSoggettoList.size() > 0) {
                    presente = true;
                }

                ruoliObbligatori.add(new RuoloObbligatorioVO(mudeRTipoIstanzaRuoloSoggetto.getMudeDRuoloSoggetto().getCodice(), mudeRTipoIstanzaRuoloSoggetto.getMudeDRuoloSoggetto().getDescrizione(), presente));
            }
        }

        //        List<MudeTDatiIstanza> datiIstanzas = mudeTDatiIstanzaRepository.findByMudeTIstanza(mudeTIstanza);
        String titoloPresentatore = String.valueOf(mudeTIstanza.getBranch());
        //        for (MudeTDatiIstanza data : datiIstanzas) {
        //            if (data.getDato() == MudeTDatiIstanza.DatiIstanza.TIPOLOGIA_PRESENTATORE) {
        //                titoloPresentatore = data.getValore();
        //            }
        //        }
        // aggiungo i restanti ruoli come possibili

        List<RuoloPossibileVO> ruoliPossibili = getRuoliPossibili(titoloPresentatore);

        // cerco tutti i soggetti gia coinvolti sull'istanza

        List<MudeRIstanzaSoggetto> mudeRIstanzaSoggettoList = null;
        if (excl_user_ids != null && excl_user_ids.length() > 0)
            mudeRIstanzaSoggettoList = mudeRIstanzaSoggettoRepository.findByMudeTIstanzaAndEscludeUsers(mudeTIstanza.getId(), excl_user_ids.split(","));
        else
            mudeRIstanzaSoggettoList = mudeRIstanzaSoggettoRepository.findByMudeTIstanza(mudeTIstanza);

        for (MudeRIstanzaSoggetto mudeRIstanzaSoggetto : mudeRIstanzaSoggettoList) {
            // Restituisco SoggettoIstanzaVO
            SoggettoIstanzaVO soggettoIstanzaVO = new SoggettoIstanzaVO();
            soggettoIstanzaVO.setIdSoggetto(mudeRIstanzaSoggetto.getMudeTContatto().getId());
            soggettoIstanzaVO.setOperaFiniFiscaliDipendente(mudeRIstanzaSoggetto.getOperaFiniFiscaliDipendente());
            soggettoIstanzaVO.setEnteSocietaAppartenenza(mudeRIstanzaSoggetto.getEnteSocietaAppartenenza());
            soggettoIstanzaVO.setDomiciliazioneCorrispondenzaProfessionista(mudeRIstanzaSoggetto.getDomiciliazioneCorrispondenzaProfessionista());

            String pfUserFiscalCode = null;
            soggettoIstanzaVO.setContatto(contattoEntityMapper.mapEntityToVO(mudeRIstanzaSoggetto.getMudeTContatto(), mudeTUSer));
            if (mudeRIstanzaSoggetto.getMudeTContatto().getTipoContatto() == TipoContatto.PF) {
                pfUserFiscalCode = mudeRIstanzaSoggetto.getMudeTContatto().getCf();

                Long idUser = mudeTUserRepository.findIdByCf(pfUserFiscalCode);
                // retrieve "abilitazioni" per subject
                if (idUser != null && loadAbilitazioni != null && loadAbilitazioni && pfUserFiscalCode != null)
                    soggettoIstanzaVO.setAbilitazioni(istanzaUtenteService.findByIstanzaAndUtente(idIstanza, idUser, pfUserFiscalCode));
            }

            List<SelectVO> ruoliSoggetto = new ArrayList<SelectVO>();

            for (String idRuolo : mudeRIstanzaSoggettoRepository.findRuoliByIdIstanzaSoggetto(mudeRIstanzaSoggetto.getIdIstanzaSoggetto())) {
                MudeDRuoloSoggetto mudeDRuoloSoggetto = mudeDRuoloSoggettoRepository.findOne(idRuolo);
                ruoliSoggetto.add(new SelectVO(mudeDRuoloSoggetto.getCodice(), mudeDRuoloSoggetto.getDescrizione()));
            }

            soggettoIstanzaVO.setRuoloSoggetto(ruoliSoggetto);

            for(MudeTDatiIstanza titolo : CollectionUtils.emptyIfNull(mudeTDatiIstanzaRepository.findAllTitoloSoggetto(idIstanza, mudeRIstanzaSoggetto.getMudeTContatto().getId())))
            	if(titolo.getDato() == DatiIstanza.TITOLO_SOGGETTO_ABILITATO)
            		soggettoIstanzaVO.setIdTitoloSoggetto(titolo.getValore());
            	else if(titolo.getDato() == DatiIstanza.TITOLO_SOGGETTO_ABILITATO_RT)
            		soggettoIstanzaVO.setIdTitoloSoggettoRT(titolo.getValore());

            if (mudeRIstanzaSoggetto.getMudeTContatto().getTipoContatto() == TipoContatto.PF)
                soggettoPFList.add(soggettoIstanzaVO);
            else
                soggettoPGList.add(soggettoIstanzaVO);
        }

        if (mudeTIstanza.getIdSoggettoRappresentato() != null) {
            MudeTContatto mudeTContatto = mudeTContattoRepository.findOne(mudeTIstanza.getIdSoggettoRappresentato());
            if (mudeTContatto != null)
                soggettoPFRappresentato = contattoEntityMapper.mapEntityToVO(mudeTContatto, mudeTUSer);
        }

        ruoli.setRuoliObbligatori(ruoliObbligatori);
        ruoli.setRuoliPossibili(ruoliPossibili);
        ruoli.setSoggettoPFList(soggettoPFList);
        ruoli.setSoggettoPGList(soggettoPGList);
        ruoli.setSoggettoPFRappresentato(soggettoPFRappresentato);
        ruoli.setSoggettoPGRappresentato(soggettoPGRappresentato);
        ruoli.setAlmenoUnRuolo(almenoUnRuolo);

        return ruoli;
    }

    @Override
    public Response recuperaAbilitazioniIstanza(Long idIstanza, MudeTUser mudeTUser) {


        List<IstanzaUtenteVO> istanzaUtenteVOs = istanzaUtenteService.recuperaAbilitazioniIstanza(idIstanza, false);
        return PaginationResponseUtil.buildResponse(istanzaUtenteVOs, 0, istanzaUtenteVOs.size(), 1, istanzaUtenteVOs.size());
    }

    @Override
    public Response listaPossibiliIstanze(MudeTUser mudeTUser, String idTipoIstanza, Long idFascicolo, Long idIstanzaPadre, int page, int size) {
    	idFascicolo = idFascicolo == null  ? 0 : idFascicolo;
    	idIstanzaPadre = idIstanzaPadre == null ? 0 : idIstanzaPadre;
    	
    	String query = mudeDTipoIstanzaRepository.getTipiIstanzeBaseQuery(idFascicolo, 
    					"SELECT DISTINCT mti.*, NULL as formula_col ",
    					(StringUtils.isBlank(idTipoIstanza) ? "" : "		AND mrti.cod_tipo_istanza_figlia = '"+idTipoIstanza+"'") 
    					+ (idIstanzaPadre == 0L? "" : "		AND mti.id_istanza = " + idIstanzaPadre) 
    					+ (idFascicolo == 0L? "" : "		AND mtf.id_fascicolo = " + idFascicolo));
    	
    	List<MudeTIstanza> mudeTIstanzaList = entityManager.createNativeQuery(query, MudeTIstanza.class).getResultList();
        List<IstanzaVO> istanzeVoList = istanzaEntityMapper.mapListEntityToListSlimVO(mudeTIstanzaList, mudeTUser);

        return PaginationResponseUtil.buildResponse(istanzeVoList, page, size, mudeTIstanzaList.size(), mudeTIstanzaList.size());
    }

    private List<RuoloPossibileVO> getRuoliPossibili(String titoloPresentatore) {

        List<RuoloPossibileVO> ruoliPossibili = new ArrayList<RuoloPossibileVO>();

        Iterable<MudeDRuoloSoggetto> mudeDRuoloSoggettoList = mudeDRuoloSoggettoRepository.findAllActiveRoles();
        for (MudeDRuoloSoggetto mudeDRuoloSoggetto : mudeDRuoloSoggettoList) {
            // verifico se il ruolo e' di default per il branch (TIPOLOGIA_PRESENTATORE) presente sull'istanza
            boolean _default = false;
            if (titoloPresentatore != null && titoloPresentatore.equalsIgnoreCase(mudeDRuoloSoggetto.getBranchObbligatorio())) {
                _default = true;
            }

            ruoliPossibili.add(new RuoloPossibileVO(mudeDRuoloSoggetto.getCodice(), mudeDRuoloSoggetto.getDescrizione(), _default, mudeDRuoloSoggetto.getVisibile(), mudeDRuoloSoggetto.getCategoriaRuolo(), mudeDRuoloSoggetto.getOperatori(), mudeDRuoloSoggetto.getIncludi(), mudeDRuoloSoggetto.getEscludi(), mudeDRuoloSoggetto.getObbligatori()));
        }

        return ruoliPossibili;
    }

    @Override
	public void deleteFileIstanzaFirmata(Long idIstanza, MudeTUser mudeTUser, HttpHeaders httpHeaders, boolean setToDFR, String scope) {
        IstanzaVO istanzaVO = recuperaIstanza(mudeTUser, idIstanza, scope, null);

        if (!StatoIstanza.FIRMATA.getValue().equalsIgnoreCase(istanzaVO.getStatoIstanza().getCodice()) 
        				&& !StatoIstanza.DA_FIRMARE.getValue().equalsIgnoreCase(istanzaVO.getStatoIstanza().getCodice()) 
        				&& !StatoIstanza.BOZZA.getValue().equalsIgnoreCase(istanzaVO.getStatoIstanza().getCodice()) 
        				&& !StatoIstanza.RESTITUITA_PER_VERIFICHE.getValue().equalsIgnoreCase(istanzaVO.getStatoIstanza().getCodice()))
            throw new BusinessException("Impossibile cancellare l'istanza firmata. L'istanza si trova nello stato successivo al depositata.");

        if (null != istanzaVO && istanzaVO.getUuidFileIndex() != null) {
            ErrorMessage errorMessage = indexManager.deleteContenutoByUuid(istanzaVO.getUuidFileIndex());
            if (!Constants._environment.equals("local") && errorMessage != null)
                throw new BusinessException("Si è verificata un'anomalia durante la cancellazione del file. riprovare successivamente.", "500", Constants.ERRORE_INTERNO, null);
                
            istanzaVO.setDimensioneFile(null);
            istanzaVO.setUuidFileIndex(null);
            istanzaVO.setDataCaricamentoFileIstanza(null);
            istanzaVO.setFilename(null);
            IstanzaVO updated = updateDatiFileIstanza(istanzaVO, mudeTUser);
            if (null == updated)
                throw new BusinessException("Errore inaspettato nella gestione della richiesta. Riprova a breve.", "500", Constants.ERRORE_INTERNO, null);
                
            // send status back to DA FIRMARE
            if (setToDFR)
                istanzaStatoService.saveIstanzaStato(idIstanza, StatoIstanza.DA_FIRMARE.getValue(), null, httpHeaders);
        }
    }

    @Override
    public byte[] exportExcelIstanzeUtente(String filter, String sortBy, MudeTUser mudeTUser, Long idFascicolo, Long idPratica, String numPratica, String codiceTipoIStanza, String idIntestatarioPf, String idIntestatarioPg, String idPm, Long idComune, Long idProvincia, Long idDug, String duf, LocalDate creationDateFrom, LocalDate creationDateTo, String code, String scope) {
        List<IstanzaVO> istanzaVOs = new ArrayList();

        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        String exportType = FilterUtil.getStringValue(filter, "result_type");
        if(exportType == null) exportType = "";    
        if(exportType.startsWith("backoffice.")) {
        	List<MudeTIstanzaSlim> mudeTIstanzaList = new ArrayList<>();
        	
	        Specifications<MudeTIstanzaSlim> allSpecs = getIstanzeSpecificationsSlim(filter, mudeTUser, scope, methodName);
	        if(allSpecs != null) {
		        Specification<MudeTIstanzaSlim> groupByMinDS = MudeTIstanzaSlimSpecification.filterOnlyRootDS(allSpecs);
	        	
	        	int size = Integer.parseInt(mudeCProprietaRepository.getValueByName("BO_MAX_EXCEL_RESULTSET", "-1"));
	        	if(size != -1) {
			        if (StringUtils.isBlank(sortBy))
			        	sortBy = "-dataCreazione";
			        
			        sortBy = sortBy.replace("dataCreazione", "dataUltimoStato"); // compatibility with MudeTIstanza
			        
			        Pageable pageable = new PageRequest(0, size, new Sort(sortBy.charAt(0) == '-'? Sort.Direction.DESC :  Sort.Direction.ASC, sortBy.substring(1)));
	        
			        mudeTIstanzaList = mudeTIstanzaSlimRepository.findAll(groupByMinDS, pageable).getContent();
	        	}
	        	else
			        mudeTIstanzaList = mudeTIstanzaSlimRepository.findAll(groupByMinDS);
	        		
		        istanzaVOs = istanzaSlimEntityMapper.mapListEntityToListSlimVO(mudeTIstanzaList, mudeTUser, "backoffice.ds-report" + exportType.substring(exportType.indexOf("-")));
	        }
        }
        else {
        	List<MudeTIstanza> mudeTIstanzaList = new ArrayList<MudeTIstanza>();
        	
	        List<Long> comuni = null;
	        MudeTFascicolo mudeTFascicolo = null;
	        Optional<MudeTPratica> mudeTPraticaOpt = null; 
	        MudeDTipoIstanza mudeDTipoIstanza = null;
	        //        StatoIstanza statoIstanza = null;
	
	        if (idComune != null && idComune == 0)
	            idComune = null;
	
	        if (idProvincia != null && idProvincia == 0)
	            idProvincia = null;
	
	        if (idDug != null && idDug == 0)
	            idDug = null;
	
	        if (idComune != null) {
	            MudeDComune mudeDComune = mudeDComuneRepository.findByIdComune(idComune);
	            comuni = new ArrayList<Long>();
	            comuni.add(mudeDComune.getIdComune());
	        } else if (idProvincia != null) {
	            MudeDProvincia mudeDProvincia = mudeDProvinciaRepository.findOne(idProvincia);
	            comuni = mudeDComuneRepository.findIdComuneByMudeDProvinciaOrderByDenomComuneAsc(mudeDProvincia);
	        }
	
	        if (StringUtils.isNotBlank(codiceTipoIStanza))
	            mudeDTipoIstanza = mudeDTipoIstanzaRepository.findOne(codiceTipoIStanza);
	
	        if (idFascicolo != null)
	            mudeTFascicolo = mudeTFascicoloRepository.findOne(idFascicolo);
	
	        List<Long> praticaIDs = new ArrayList();
	        if (StringUtils.isNotBlank(numPratica)) {
	        	List<MudeTPratica> optPratiche = mudeTPraticaRepository.getByNumeroPratica(numPratica);
	        	if(optPratiche.isEmpty()) return null;
	        	
	        	for(MudeTPratica optPratica : optPratiche)
	        		praticaIDs.add(optPratica.getId());
	        }
	        
	        if (idPratica != null)
	            praticaIDs.add(idPratica);
	        
	        if((StringUtils.isNotBlank(codiceTipoIStanza) && mudeDTipoIstanza == null) ||
	                (idFascicolo != null && mudeTFascicolo == null) ||	
	                ((numPratica != null || idPratica != null) && praticaIDs == null) ) {
	             	
	         }else {
	        	 List<String> soggettoRole = getRuoliSoggetti(filter);
	        	 
	        	 Specification<MudeTIstanza> filterStato = MudeTIstanzaSpecification.hasStato(FilterUtil.getValue(filter, "stato"));
	        	 // this should be split for each specification
	        	 Specification<MudeTIstanza> filters = MudeTIstanzaSpecification.findBy(mudeTUser, 
	        			 																comuni, 
	        			 																idDug, 
	        			 																duf, 
	        			 																idIntestatarioPf, 
	        			 																idIntestatarioPg, 
	        			 																idPm, 
	        			 																mudeTFascicolo, 
	        			 																praticaIDs, 
	        			 																mudeDTipoIstanza, 
	        			 																creationDateFrom, 
	        			 																creationDateTo, 
	        			 																code, 
	        			 																scope, 
	        			 																false, 
	        			 																false, 
	        			 																false,
	        			 																soggettoRole);
	             Specification<MudeTIstanza> filterById = MudeTIstanzaSpecification.findById(FilterUtil.getValue(filter, "id"));
	             Specification<MudeTIstanza> filterTipoIstanza = MudeTIstanzaSpecification.hasTipoIstanza(FilterUtil.getValue(filter, "cod_tipo_istanza"));
	             Specification<MudeTIstanza> filterSpeciePratica = getDSRoleSPSpecification(mudeTUser, filter);
	
	             Specification<MudeTIstanza> filterByComuneAndEnte = null;
	             if(BACKOFFICE.equals(scope) && idFascicolo == null && idPratica == null && numPratica == null)
	     			filterByComuneAndEnte = MudeTIstanzaSpecification.filterByComuneAndEnte(mudeTUser.getIdUser());
	             
	             //Sort sort = new Sort(Sort.Direction.ASC, "dataDps");
	             if(filterByComuneAndEnte != null) {
	         		mudeTIstanzaList = mudeTIstanzaRepository.findAll(Specifications.where(filters)
	         					.and(filterStato)
	         					.and(filterById)
	         					.and(filterTipoIstanza)
	         					.and(filterSpeciePratica)
	         					.and(filterByComuneAndEnte));
	         	 }else{
	         		// CercaIstanzeNelFascicolo o CercaIstanzeNellaPratica
	         		mudeTIstanzaList = mudeTIstanzaRepository.findAll(Specifications.where(filters)
	         					.and(filterStato)
	         					.and(filterById)
	         					.and(filterSpeciePratica)
	         					.and(filterTipoIstanza));
	         	 }
	
	             LoggerUtil.debug(logger, "[" + CLS_NAME + "::" + methodName + "] Saranno esportate " + mudeTIstanzaList.size() + " istanze nel file excel.");
	        	 istanzaVOs = istanzaEntityMapper.mapListEntityToListSlimVO(mudeTIstanzaList, mudeTUser);
	        }
        }
        
        byte[] fileContent = null;
        IstanzeExcelBuilder istanzeExcelBuilder = new IstanzeExcelBuilder();
        HSSFWorkbook workbook = new HSSFWorkbook();
        try {
        	LoggerUtil.debug(logger, "[" + CLS_NAME + "::" + methodName + "] Saranno esportate " + istanzaVOs.size() + " istanzeVO nel file excel.");
            fileContent = istanzeExcelBuilder.buildExcelDocuments(istanzaVOs, workbook, exportType);
        } catch (Exception e) {
			LoggerUtil.error(logger, "[IstanzaServiceImpl::exportExcelIstanzeUtente] EXCEPTION ",  e);
        }

        return fileContent;
    }

    @Override
    public IstanzaVO recuperaIstanza(MudeTUser mudeTUser, Long idIstanza) {
    	return recuperaIstanza(mudeTUser, idIstanza, null, null);
    }
    
    @Override
    public IstanzaVO recuperaIstanza(MudeTUser mudeTUser, Long idIstanza, String scope, String filters) {
        IstanzaVO istanza = new IstanzaVO();

        MudeTIstanza mudeTIstanza = mudeTIstanzaRepository.findOne(idIstanza);
        if(!BACKOFFICE.equals(scope) && !Constants.SCOPE_WS.equals(scope) && !managerAbilitazioni.hasUtenteAbilitazioneFunzionePerIstanza(false, FunzioniAbilitazioniEnum.CONSULTA_IST.getDescription(), idIstanza, mudeTUser, null) && !managerAbilitazioni.hasUtenteAbilitazioneFunzionePerFascicolo(FunzioniAbilitazioniEnum.CONS_IST_ALL_FASCIC.getDescription(), mudeTIstanza.getMudeTFascicolo().getId(), mudeTUser.getIdUser()))
            throw new BusinessException("L'utente non è abilitato ad eseguire questa operazione");

        istanza = getIstanzaVO(mudeTIstanza, mudeTUser, filters);

        // retrieve "abilitazioni" per subject
        if(!BACKOFFICE.equals(scope) && !Constants.SCOPE_WS.equals(scope))
        	istanza.setAbilitazioni(abilitazioneFunzioneService.loadFunzioniAbilitazioniByIdIstanzaAndIdUser(idIstanza, mudeTUser.getIdUser()));
        

        return istanza;
    }

    /**
     * Clean cache pdf istanza.
     *
     * @param idIstanza the id istanza
     */
    @Override
    public void cleanCachePdfIstanza(Long idIstanza) {
        mudeTIstanzaRepository.removePdfCache(idIstanza);
    }

    /**
     * Load cached pdf istanza byte [ ].
     *
     * @param idIstanza the id istanza
     * @return the byte [ ]
     */
    @Override
    public byte[] loadCachedPdfIstanza(Long idIstanza) {
        return mudeTIstanzaRepository.getCachedPdfIstanza(idIstanza);
    }

    /**
     * Pdf istanza is cached.
     *
     * @param idIstanza the id istanza
     */
    @Override
    public boolean pdfIstanzaIsCached(Long idIstanza) {
        return mudeTIstanzaRepository.checkCachedPdfExists(idIstanza);
    }
    
    
    @Override
    public byte[] exportExcelPraticheUtente(MudeTUser mudeTUser, Long idFascicolo, Long idPratica, String code, String idIntestatarioPf, String idIntestatarioPg, String idPm, Long idIstanzaRiferimento, String codiceTipoIStanza, String numeroProtocollo, LocalDate dataProtocolloDa, LocalDate dataProtocolloA, Long anno, String numPratica, LocalDate dataRegistrazionePraticaDa, LocalDate dataRegistrazionePraticaA, String scope) {


        byte[] fileContent = null;
        List<IstanzaVO> istanzeVoList = new ArrayList<IstanzaVO>();

        MudeTFascicolo mudeTFascicolo = null;

        MudeTPratica mudeTPratica = null;

        MudeDTipoIstanza mudeDTipoIstanza = null;
        StatoIstanza statoIstanza = StatoIstanza.DEPOSITATA;

        if (StringUtils.isNotBlank(codiceTipoIStanza)) {
            mudeDTipoIstanza = mudeDTipoIstanzaRepository.findOne(codiceTipoIStanza);
        }

        if (idFascicolo != null) {
            mudeTFascicolo = mudeTFascicoloRepository.findOne(idFascicolo);            
        }
        
        if (numPratica != null) {
        	List<MudeTPratica> optPratica = mudeTPraticaRepository.getByNumeroPratica(numPratica);
            mudeTPratica = !optPratica.isEmpty() ? optPratica.get(0) : null;
        }

        if((StringUtils.isNotBlank(codiceTipoIStanza) && mudeDTipoIstanza == null) ||
                (idFascicolo != null && mudeTFascicolo == null) ||	
                ((numPratica != null || idPratica != null) && mudeTPratica == null) ) {
             	
         }else{
        	List<MudeTIstanza> mudeTIstanzaList = mudeTIstanzaRepository.findAll(MudeTIstanzaSpecification.findPraticheBy(mudeTUser, code, idIntestatarioPf, idIntestatarioPg, idPm, idIstanzaRiferimento, mudeTFascicolo, mudeDTipoIstanza, numeroProtocollo, dataProtocolloDa, dataProtocolloA, anno, mudeTPratica, statoIstanza, dataRegistrazionePraticaDa, dataRegistrazionePraticaA, scope));
        	istanzeVoList = istanzaEntityMapper.mapListEntityToListVO(mudeTIstanzaList, mudeTUser);
        }

        IstanzeExcelBuilder istanzeExcelBuilder = new IstanzeExcelBuilder();
        HSSFWorkbook workbook = new HSSFWorkbook();
        try {
            fileContent = istanzeExcelBuilder.buildExcelPraticheDocuments(istanzeVoList, workbook);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return fileContent;
    }
    
    @Override
	@SuppressWarnings("unchecked")
	public List<SelectVO> recuperoTemplateFormIoCambioStato(MudeTUser mudeTUser, IstanzaVO istanzaVO, String codStatusEnd){


    	String codStatusStart = istanzaVO.getStatoIstanza().getCodice();
    	String tipoIstanza = istanzaVO.getTipologiaIstanza().getId();
        List<SelectVO> statoFormIO = new ArrayList<SelectVO>();
        
        MudeDWfStato wfStato = mudeDWfStatiRepository.findByCodiceStatoStartAndCodiceStatoEndAndTipoIstanza(codStatusStart, codStatusEnd, tipoIstanza, istanzaVO.getSpeciePratica() != null? istanzaVO.getSpeciePratica().getCodice() : "");
        if(wfStato == null)
        	throw new BusinessException("L'istanza è in stato '"+codStatusStart+"'. Non disponi dei diritti necessari a cambiare lo stato in '"+codStatusEnd+"'");
    	
		if(istanzaVO.getFonte() != null && !"FO".equals(istanzaVO.getFonte()) && wfStato.getCodiceStatoEnd().equals(StatoIstanza.RESTITUITA_PER_VERIFICHE))
			throw new BusinessException("Cambio stato '"+ wfStato.getCodiceStatoEnd()  +"' non permesso per l'istanza specificata");
        
        String jsonTemplateFormIo = null;
        if(codStatusEnd.equals(StatoIstanza.REGISTRATA_DA_PA.getValue())) 
        	jsonTemplateFormIo = recuperoJsonTemplateFormIo(TipoTaskQuadro.CAMBIO_STATO_APA.getValue());
        else
        	jsonTemplateFormIo = recuperoJsonTemplateFormIo(TipoTaskQuadro.CAMBIO_STATO.getValue());
    	
    	if(codStatusEnd.equals(StatoIstanza.REGISTRATA_DA_PA.getValue())) {
    		jsonTemplateFormIo = modificaJsonTemplateFormIoCambioStatoApa (mudeTUser, istanzaVO, jsonTemplateFormIo);
    	}
    	
    	String destinatari = contattoService.recuperaDestinatariNotifiche(istanzaVO.getIdIstanza(),mudeTUser.getIdUser());
    	String jsonReplace = jsonTemplateFormIo.replace("${DESTINATARIO_NOTIFICA}",destinatari);
    	
    	MudeDStatoIstanza mudeDStatoIstanzaEnd = mudeDStatoIstanzaRepository.findMudeDStatoIstanzaByCodice(codStatusEnd);

		String numeroPratica = mudeTPraticaRepository.getNumeroPraticaByIdIstanza(istanzaVO.getIdIstanza());
		if(StringUtils.isBlank(numeroPratica))
			numeroPratica = "";
			
    	String oggettoNotifica = wfStato.getOggettoNotifica();
    	if(oggettoNotifica != null)
    		oggettoNotifica = oggettoNotifica.replace("${NUMERO_ISTANZA}", istanzaVO.getCodiceIstanza())
    										 .replace("${DESCRIZIONE_STATO}", mudeDStatoIstanzaEnd.getDescrizione())
    										 .replace("${NUMERO_PRATICA}", numeroPratica);
    	
    	String testoNotifica = wfStato.getTestoNotifica();
    	if(testoNotifica != null)
    		testoNotifica = testoNotifica.replace("${NUMERO_ISTANZA}", istanzaVO.getCodiceIstanza())
    									 .replace("${DESCRIZIONE_STATO}", mudeDStatoIstanzaEnd.getDescrizione())
    									 .replace("${NUMERO_PRATICA}", numeroPratica);
    	
    	jsonReplace = jsonReplace.replace("${STATO_ISTANZA}", mudeDStatoIstanzaEnd.getDescrizione())
						    	 .replace("${OGGETTO_NOTIFICA}",oggettoNotifica == null? "" : oggettoNotifica) 
						    	 .replace("${TESTO_NOTIFICA}",testoNotifica == null? "" : testoNotifica);
    	
    	statoFormIO.add(new SelectVO(wfStato.getCodiceStatoEnd(), jsonReplace));


        return statoFormIO;
    }
    

    public String recuperoJsonTemplateFormIo(String codTask){


    	
    	MudeDTaskQuadro dTaskQuadro = mudeDTaskQuadroRepository.findByCodiceTask(codTask);
        if(dTaskQuadro == null)
        	throw new BusinessException("Non è presente un idQuadro per il codice '"+codTask+"'");
    	
    	MudeDQuadro mudeDQuadro = mudeDQuadroRepository.findOne(dTaskQuadro.getQuadro());
    	
    	String mudeDQuadroJson = null;
    	
    	if (mudeDQuadro != null) 
    		mudeDQuadroJson = mudeDQuadro.getJsonConfiguraQuadro();
    	
    	return mudeDQuadroJson;
    }
 
    
    public String modificaJsonTemplateFormIoCambioStatoApa(MudeTUser mudeTUser, IstanzaVO istanzaVO, String templateFormIo){

    	
    		String praticheLst="";
    		boolean isFirst=true;
    		List<MudeTPratica> pratiche = mudeTPraticaRepository.findByFascicoloIstanza(istanzaVO.getIdIstanza());
    		for(MudeTPratica mudeTPratica : pratiche) {
    			if (mudeTPratica.getNumeroPratica() == null || mudeTPratica.getNumeroPratica().isEmpty())
    				continue;
    			if(!isFirst) {
    				praticheLst = praticheLst +",";
    			}else {
    				isFirst=false;
    			}

    			String numeroAnnoPratica = mudeTPratica.getNumeroPratica() + " - " + mudeTPratica.getAnnoPratica();
    			praticheLst = praticheLst +"\""+numeroAnnoPratica+"\"";
    				
    		}

    		String jsonReplace = templateFormIo.replace("\"${LISTA_PRATICHE}\"",praticheLst);
    		
    		/*MudeTContatto mudeTContattoRespProc = mudeTContattoRepository.findRespProcedimentoByIdIstanza(istanzaVO.getIdIstanza());
    		if(mudeTContattoRespProc != null) {
    			jsonReplace = jsonReplace.replace("${RESPONSABILE_PROCEDIMENTO}",mudeTContattoRespProc.getCognome() + " " + mudeTContattoRespProc.getNome());
    		} else {
    			jsonReplace = jsonReplace.replace("${RESPONSABILE_PROCEDIMENTO}","");
    		}*/
    		String respProcedimentoReplace="";
    		String respProcedimento = mudeREnteTipoIstanzaRepository.findRespProcedimentoForCambioStato(istanzaVO.getIdIstanza());
    		if(respProcedimento == null || StringUtils.isBlank(respProcedimento)) {
    			MudeTIstanza mudeTIstanza = mudeTIstanzaRepository.findOne(istanzaVO.getIdIstanza());
    			if(mudeTIstanza != null && mudeTIstanza.getIdIstanzaRiferimento() != null && mudeTIstanza.getIdIstanzaRiferimento()!= 0L) {
    				respProcedimento = mudeREnteTipoIstanzaRepository.findRespProcedimentoForCambioStato(mudeTIstanza.getIdIstanzaRiferimento());
    			}
    		}

    		if(respProcedimento != null)
    			respProcedimentoReplace = respProcedimento;
    		jsonReplace = jsonReplace.replace("${RESPONSABILE_PROCEDIMENTO}",respProcedimentoReplace);
    		

        return jsonReplace;
    }
    
    @Override
	public MudeTNotifica insertTNotifica(MudeTUser mudeTUser,Long idIstanza,String jsonData, MudeDTipoNotifica mudeDTipoNotifica) {

		
    	MudeTIstanza mudeTIstanza = mudeTIstanzaRepository.findOne(idIstanza);
    	MudeTNotifica mudeTNotifica = new MudeTNotifica();
    	mudeTNotifica.setTipoNotifica(mudeDTipoNotifica);
    	mudeTNotifica.setIstanza(mudeTIstanza);
    	if( jsonData != null)
    		mudeTNotifica.setJsonData(jsonData);
    	mudeTNotifica.setDataInizio(new Date());
    	mudeTNotifica.setMudeTUser(mudeTUser);
    	if( jsonData != null) {
    	   try {
				JSONObject objJson = new JSONObject(jsonData);
				if(objJson.has("oggetto")) 
					mudeTNotifica.setOggettoNotifica(objJson.getString("oggetto"));
				if(objJson.has("testo")) 
					mudeTNotifica.setTestoNotifica(objJson.getString("testo"));
				if(objJson.has("mittente")) {
					mudeTNotifica.setRuoloMittente(objJson.getString("mittente"));
				}

				else {
					List<MudeRUtenteRuolo> mudeRUtenteRuoloList = mudeRUtenteRuoloRepository.findByIdUser(mudeTUser.getIdUser());
					String ruoloMax=null;
					for(MudeRUtenteRuolo mudeRUtenteRuolo : mudeRUtenteRuoloList) {
						if((TipoRuoloUtente.CONSULTATORE.getValue()).equals(ruoloMax) || ruoloMax == null){
							ruoloMax = mudeRUtenteRuolo.getRuoloUtente();
						}
					}

					MudeDRuoloUtente mudeDRuolo = mudeDRuoloUtenteRepository.findByCodice(ruoloMax);
					mudeTNotifica.setRuoloMittente(mudeDRuolo.getDescrizione());
				}			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}else {
    		// Gestione DEPOSITATA
    		 MudeDWfStato wfStato = mudeDWfStatiRepository.findByCodiceStatoStartAndCodiceStatoEndAndTipoIstanza(StatoIstanza.FIRMATA.getValue(), StatoIstanza.DEPOSITATA.getValue(), mudeTIstanza.getTipoIstanza().getCodice(), mudeTIstanza.getSpeciePratica() != null? mudeTIstanza.getSpeciePratica().getCodice() : "");
	        if(wfStato == null)
	        	throw new BusinessException("L'istanza è in stato '"+StatoIstanza.FIRMATA.getValue()+"'. Non disponi dei diritti necessari a cambiare lo stato in '"+StatoIstanza.DEPOSITATA.getValue()+"'");
		
	        String oggettoNotifica = wfStato.getOggettoNotifica();
	    	if(oggettoNotifica != null)
	    		oggettoNotifica = oggettoNotifica.replace("${NUMERO_ISTANZA}", mudeTIstanza.getCodiceIstanza()).replace("${DESCRIZIONE_STATO}", StatoIstanza.DEPOSITATA.name());
	        mudeTNotifica.setOggettoNotifica(oggettoNotifica);
    		mudeTNotifica.setTestoNotifica("");
    		
    		List<MudeRUtenteRuolo> mudeRUtenteRuoloList = mudeRUtenteRuoloRepository.findByIdUser(mudeTUser.getIdUser());
			String ruoloMax=null;
			for(MudeRUtenteRuolo mudeRUtenteRuolo : mudeRUtenteRuoloList) {
				if((TipoRuoloUtente.CONSULTATORE.getValue()).equals(ruoloMax) || ruoloMax == null){
					ruoloMax = mudeRUtenteRuolo.getRuoloUtente();
				}
			}

			MudeDRuoloUtente mudeDRuolo = mudeDRuoloUtenteRepository.findByCodice(ruoloMax);
            if(null != mudeDRuolo)
			    mudeTNotifica.setRuoloMittente(mudeDRuolo.getDescrizione());
    	}

    	MudeTNotifica mudeTNotificaOut = mudeTNotificaRepository.saveDAO(mudeTNotifica);

    	return mudeTNotificaOut;
	}

	
	@Override
	public void updateJsonDataFormIO(Long idIstanza, String codStatus, String jsonData, IstanzaVO istanzaVo) {

        MudeTIstanza istanza = mudeTIstanzaRepository.findByIdIstanza(idIstanza);
        JSONObject jsonDataIstanza;
        if (null != istanza) {
        	try {
		        
		        if(istanza.getJsonData() == null)
		        	jsonDataIstanza = new JSONObject();
		        else
		            jsonDataIstanza = new JSONObject(istanza.getJsonData());
		
		        // set jdata for main quadro
				JSONObject cambiaStatoQuadroJDATA = null;
				cambiaStatoQuadroJDATA = new JSONObject(jsonData);
				
				if("APA".equals(codStatus))
					cambiaStatoQuadroJDATA.put("_data_creazione_protocollo", new SimpleDateFormat("dd/MM/yyyy").format(Date.from(istanzaVo.getDataRegistrazionePratica().atZone(CustomDateTimeSerializer.LOCAL_TIME_ZONE.toZoneId()).toInstant())));
		
		        jsonDataIstanza.put("CAMBIO_STATO_"+codStatus, cambiaStatoQuadroJDATA);
		       	// updates entire JData
		        istanza.setJsonData(jsonDataIstanza.toString());
		        mudeTIstanzaRepository.saveDAO(istanza);
		            
			} catch (JSONException e) {
				LoggerUtil.debug(logger, "[IstanzaServiceImpl::updateJsonDataFormIO] ERROR:" + e);
		    }
       }
    }

	@Override
	public List<MudeTContatto> recuperaContatti(Long idIstanza, MudeTUser mudeTUser) {
		List<MudeTContatto> contattoDestinatari = new ArrayList<MudeTContatto>();
    	
		//Destinatari
    	List<MudeTContatto> contattoCreatore = mudeTContattoRepository.findCreatoreByIdIstanza(idIstanza);
    	if(contattoCreatore != null) {
    		boolean bTrovato=false;
    		String guid = "";
    		for(MudeTContatto mudeTContatto :contattoCreatore) {
    			if(mudeTContatto.getDataCessazione()!=null) {
    				contattoDestinatari.add(mudeTContatto);
    				bTrovato=true;
    				break;
    			}else {
    				guid = mudeTContatto.getGuid();
    			}
    		}

    		if(!bTrovato && guid != null && !guid.isEmpty()) {
    			MudeTContatto contattoCreatoreModificato = mudeTContattoRepository.findCreatoreByIdIstanzaAndDataCessazioneNull(idIstanza,guid);
    			contattoDestinatari.add(contattoCreatoreModificato);
    		}
    		
    	}

    	Long idUser=-1L;
    	if(mudeTUser != null)
    		idUser = mudeTUser.getIdUser();
    	
    	MudeTContatto contattoProfessionista = mudeTContattoRepository.findPMByIdIstanza(idIstanza, idUser);
    	if(contattoProfessionista != null && contattoProfessionista != contattoCreatore)
    		contattoDestinatari.add(contattoProfessionista);
    	
    	MudeTContatto contattoColaboratore= mudeTContattoRepository.findCollaboratorePMByIdIstanza(idIstanza,idUser);
    	if(contattoColaboratore != null && contattoColaboratore != contattoProfessionista)
    		contattoDestinatari.add(contattoColaboratore);
		
		return contattoDestinatari;
	}
	
	@Override
	public void insertRNotificaContatto(Long idIstanza, MudeTNotifica mudeTNotifica,  List<MudeTContatto> contattiDestinatari, IstanzaVO istanzaVO) {
    		
		//Rimossi eventuali duplicati
		List<MudeTContatto> contattiDestinatariNoDupl = new ArrayList<MudeTContatto>();
		for(MudeTContatto mudeTContatto:contattiDestinatari) {
        	boolean bDuplicate=false;
        	for(MudeTContatto mudeTContattoTmp:contattiDestinatariNoDupl) {
        		if(mudeTContattoTmp.getMail() != null && mudeTContattoTmp.getMail().equals(mudeTContatto.getMail())) {
        			bDuplicate=true;
        			break;
        		}
        	}

        	if(!bDuplicate)
        		contattiDestinatariNoDupl.add(mudeTContatto);
        }
		
		String testoSalutiEmail = createSalutiNotificaEmail(idIstanza, mudeTNotifica, istanzaVO);
		String testoDettaglio="";
		
		MudeRIstanzaStato istanzaStato = mudeRIstanzaStatoRepository.findStatoByIstanza(idIstanza);
		MudeDStatoIstanza statoIstanza = istanzaStato.getStatoIstanza();
		if(mudeTNotifica.getTipoNotifica().getCodTipoNotifica().equalsIgnoreCase("CAMBIO_STATO") &&
			StatoIstanza.DEPOSITATA.getValue().equalsIgnoreCase(statoIstanza.getCodice()) ) {
			testoDettaglio = createTestoNotificaDPS(idIstanza, mudeTNotifica);
		}else {
			testoDettaglio = createDettaglioNotificaEmail(idIstanza, mudeTNotifica, istanzaVO);
		}
			
		
		for(MudeTContatto mudeTContatto:contattiDestinatariNoDupl) {
			if(mudeTContatto.getMail() != null && StringUtils.isNotBlank(mudeTContatto.getMail())) {
				String testoIntestazioneEmail = createIntestazioneNotificaEmail(idIstanza, mudeTNotifica, mudeTContatto);
				
				StringBuilder testoEmail = new StringBuilder();
				testoEmail.append(testoIntestazioneEmail).append("\n\n");
				if(mudeTNotifica.getTestoNotifica() != null) {
					testoEmail.append(mudeTNotifica.getTestoNotifica()).append("\n");
				}
				
				if(!mudeTNotifica.getTipoNotifica().getCodTipoNotifica().equalsIgnoreCase("CAMBIO_STATO") ||
						!StatoIstanza.DEPOSITATA.getValue().equalsIgnoreCase(statoIstanza.getCodice()) ) {
					MudeTIstanza istanza = mudeTIstanzaRepository.findByIdIstanza(idIstanza);
					testoEmail.append("Nr. Istanza MUDE di riferimento: ").append(istanza.getCodiceIstanza());
					testoEmail.append("\n");
				}

				testoEmail.append(testoDettaglio).append("\n");
				
				testoEmail.append(testoSalutiEmail).append("\n\n");
				MudeRNotificaContatto mudeRNotificaContatto = new MudeRNotificaContatto();
				mudeRNotificaContatto.setBodyEmail(testoEmail.toString());
				mudeRNotificaContatto.setMudeTNotifica(mudeTNotifica);
				mudeRNotificaContatto.setMudeTContatto(mudeTContatto);
				mudeRNotificaContatto.setEmailStatus(StatoInvioEmail.TO_SEND.getValue());
				mudeRNotificaContattoRepository.saveDAO(mudeRNotificaContatto);
			}
		}
	}

	@Override
	public void insertRNotificaUtenti(Long idIstanza, MudeTNotifica mudeTNotifica, List<MudeTContatto> contattiDestinatari) {
		
		//Rimossi eventuali duplicati
		List<MudeTContatto> contattiDestinatariNoDupl = new ArrayList<MudeTContatto>();
		for(MudeTContatto mudeTContatto:contattiDestinatari) {
        	boolean bDuplicate=false;
        	for(MudeTContatto mudeTContattoTmp:contattiDestinatariNoDupl) {
        		if(mudeTContattoTmp.getMudeTUser() != null && mudeTContattoTmp.getMudeTUser().getIdUser() == mudeTContatto.getMudeTUser().getIdUser()) {
        			bDuplicate=true;
        			break;
        		}
        	}

        	if(!bDuplicate)
        		contattiDestinatariNoDupl.add(mudeTContatto);
        }
		
		for(MudeTContatto mudeTContatto:contattiDestinatariNoDupl) {
			if(mudeTContatto.getMudeTUser() != null) {
				MudeRNotificaUtente mudeRNotificaUtente = new MudeRNotificaUtente();
				mudeRNotificaUtente.setMudeTNotifica(mudeTNotifica);
				mudeRNotificaUtente.setMudeTUser(mudeTContatto.getMudeTUser());
				mudeRNotificaUtente.setLetto(false);
				mudeRNotificaUtenteRepository.saveDAO(mudeRNotificaUtente);
			}
		}
		
	}

	@Override
	public List<SelectVO> recuperoTipoDoc() {

		
		List<SelectVO> tipoDocFormIO = new ArrayList<SelectVO>();       
        
		List<MudeDTipoDocPA> tipoDocPA = mudeDTipoDocpaRepository.findAllOrderByDescrizione();
		for(MudeDTipoDocPA mudeDTipoDocPA : tipoDocPA) {
			tipoDocFormIO.add(new SelectVO(mudeDTipoDocPA.getDescTipoDocpa(), mudeDTipoDocPA.getCodeTipoDocpa()));
				
		}


        return tipoDocFormIO;
	}
	
	/*Non più utilizzata
	  
	 @Override	 
	public List<SelectVO> recuperoTemplateFormIoUplDoc(MudeTUser mudeTUser, Long idPratica, String nomeFile, String tipoFile) {

		
		List<SelectVO> tipoDocFormIO = new ArrayList<SelectVO>();
		
        String jsonTemplateFormIo = recuperoJsonTemplateFormIo(TipoTaskQuadro.UPLOAD_DOC_PR.getValue());
    
        ArrayList<Long> idsIstanza = new ArrayList<Long>();
        Pageable pageble = new PageRequest(0, 99999);
        MudeTPratica mudeTPratica = null;

        if (idPratica != null) {
            mudeTPratica = mudeTPraticaRepository.findOne(idPratica);
        }

        Page<MudeTIstanza> mudeTIstanzaList = mudeTIstanzaRepository.findAll(MudeTIstanzaSpecification.findByPratica(mudeTPratica, mudeTUser.getIdUser()), pageble);
        for(MudeTIstanza mudeTIstanza: mudeTIstanzaList) {
        	idsIstanza.add(mudeTIstanza.getId());
        }
        
        String jsonReplaceFormIoConDestinatari = modificaJsonTemplateFormIoDestinatari (mudeTUser, idsIstanza, jsonTemplateFormIo);
        
        String oggettoNotifica = "Upload Documento su Pratica " + idPratica;
    	String testoNotifica = "E' stato allegato il documento " + nomeFile + " alla pratica N."+idPratica;
    	String jsonReplaceOggTesto = jsonReplaceFormIoConDestinatari
    	.replace("${OGGETTO_NOTIFICA}",oggettoNotifica) 
    	.replace("${TESTO_NOTIFICA}",testoNotifica);
        
        
        Optional<MudeDTipoDocPA> tipoDocPA = mudeDTipoDocpaRepository.findByCodice(tipoFile);
        String tipoDocDesc="";
        String jsonReplaceNomeFile = jsonReplaceOggTesto.replace("${NOME_FILE}",nomeFile);
        if(tipoDocPA != null)
        	tipoDocDesc = tipoDocPA.get().getDescTipoDocpa();
		String jsonReplace = jsonReplaceNomeFile.replace("${TIPO_FILE}",tipoDocDesc);
        
        
    	tipoDocFormIO.add(new SelectVO(TipoTaskQuadro.TIPO_DOC_PR.getValue(), jsonReplace));


        return tipoDocFormIO;
	}*/
	
	 @Override
	    public Response cercaIstanzePratica(Long idPratica, int page, int size, MudeTUser mudeTUser) {


	        Pageable pageble = new PageRequest(page, size);
	        List<IstanzaVO> istanzeVoList = new ArrayList<IstanzaVO>();

	        MudeTPratica mudeTPratica = null;

	        if (idPratica != null) {
	            mudeTPratica = mudeTPraticaRepository.findOne(idPratica);
	        }

	        Page<MudeTIstanza> mudeTIstanzaList = mudeTIstanzaRepository.findAll(MudeTIstanzaSpecification.findByPratica(mudeTPratica, mudeTUser.getIdUser()), pageble);

	        istanzeVoList = istanzaEntityMapper.mapListEntityToListVO(mudeTIstanzaList.getContent(), mudeTUser);



	        return PaginationResponseUtil.buildResponse(istanzeVoList, page, size, mudeTIstanzaList.getTotalPages(), mudeTIstanzaList.getTotalElements());
	    }

	 @Override
	 public void insertRNotificaDocumento(Long idIstanza, MudeTNotifica mudeTNotifica,  List<MudeTDocumento> documenti) {
			
			for(MudeTDocumento mudeTDocumento:documenti) {
				MudeRNotificaDocumento mudeRNotificaDocumento = new MudeRNotificaDocumento();
				mudeRNotificaDocumento.setMudeTNotifica(mudeTNotifica);
				mudeRNotificaDocumento.setMudeTDocumento(mudeTDocumento);
				mudeRNotificaDocumentoRepository.saveDAO(mudeRNotificaDocumento);
			}
		}
	 
	 
	 @Override
	 public Response cercaIstanzeUtenteScrivania(String filter, MudeTUser mudeTUser, String scope, int page, int size) {

        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();


        Pageable pageable = new PageRequest(page, size, new Sort(Sort.Direction.ASC, "dataDps"));

        Map<String, String> inMap = FilterUtil.getValue(filter, "stato");
        Specification<MudeTIstanza> filterStato = MudeTIstanzaSpecification.hasStato(inMap);
        Specification<MudeTIstanza> filterTipoIstanza = MudeTIstanzaSpecification.hasTipoIstanza(FilterUtil.getValue(filter, "id_tipo_istanza"));
        Specification<MudeTIstanza> filterSpeciePratica = getDSRoleSPSpecification(mudeTUser, filter);
        
        // this should be split for each specification
        Specification<MudeTIstanza> filterByRole = null;
        Page<MudeTIstanza> mudeTIstanzaList = null;
        
        // Fix RUOLI Utente BO
    	 List <Long> mudeTIstanzaIdsListRuoli = mudeTIstanzaRepository.getInstancesIdByRuoliScrivania(mudeTUser.getIdUser(), ((String)inMap.get("in")).split(","));
    	 if(mudeTIstanzaIdsListRuoli == null || mudeTIstanzaIdsListRuoli.isEmpty())
     		return null;
    	 else {
    		HashMap map = new HashMap();
    		map.put("in", mudeTIstanzaIdsListRuoli);
			filterByRole = MudeTIstanzaSpecification.findById(map );
    	 }
    	 
    	mudeTIstanzaList = mudeTIstanzaRepository.findAll(Specifications.where(filterStato)
    					.and(filterByRole)
    					.and(filterSpeciePratica)
    					.and(filterTipoIstanza), pageable);

        List<IstanzaVO> istanzeVoList = istanzaEntityMapper.mapListEntityToListSlimVO(mudeTIstanzaList.getContent(), mudeTUser);


        //        return new PageImpl<IstanzaVO>(istanzeVoList, pageble, istanzeVoList.size());

        return PaginationResponseUtil.buildResponse(istanzeVoList, page, size, mudeTIstanzaList.getTotalPages(), mudeTIstanzaList.getTotalElements());
    }
	 
	private String createIntestazioneNotificaEmail(Long idIstanza, MudeTNotifica mudeTNotifica, MudeTContatto mudeTContatto) {

		StringBuilder bodyEmail = new StringBuilder();
		bodyEmail.append("Gentile ");
		if(mudeTContatto.getCognome() == null && mudeTContatto.getNome()==null) {
			bodyEmail.append(mudeTContatto.getRagioneSociale());
		}else {
			bodyEmail.append(mudeTContatto.getCognome()).append(" ").append(mudeTContatto.getNome());
		}

		bodyEmail.append(",");
		return bodyEmail.toString();
		
	}
	
	public String createDettaglioNotificaEmail(Long idIstanza, MudeTNotifica mudeTNotifica, IstanzaVO istanzaVO) {
		StringBuilder bodyEmail = new StringBuilder();
		
		MudeTIstanza istanza = mudeTIstanzaRepository.findByIdIstanza(idIstanza);
		
		// Notifica Generica con lista documenti notificati
		List<MudeRNotificaDocumento> documenti = mudeRNotificaDocumentoRepository.findByMudeTNotifica(mudeTNotifica);
		if (documenti != null && documenti.size()>0) {
			
			MudeTEnte mudeTEnte = null;
			if(mudeTNotifica.getMudeTUser() == null && istanzaVO.getEnteDiRiferimento() != null) {
				Long enteId = istanzaVO.getEnteDiRiferimento().getId();
				mudeTEnte = mudeTEnteRepository.findOne(enteId);
			}else if(mudeTNotifica.getMudeTUser() != null){
				mudeTEnte = mudeTEnteRepository.getEnteRiceventeByUtenteIstanza(mudeTNotifica.getMudeTUser().getIdUser(), idIstanza);
			}
			
			StringBuilder documentiList= new StringBuilder();
			bodyEmail.append("Si prega di accedere al Sistema MUDE Piemonte per prendere visione dei seguenti documenti ");
			
			if(mudeTEnte != null) {
				bodyEmail.append("inviati da :").append("\n");
				bodyEmail.append(mudeTEnte.getDescrizione()).append("\n");
			}
			
			for(MudeRNotificaDocumento mudeRNotificaDocumento : documenti) {
				documentiList.append(mudeRNotificaDocumento.getMudeTDocumento().getTipoDocumento().getDescTipoDocpa()).append(" - ")
						.append(mudeRNotificaDocumento.getMudeTDocumento().getNomeFileDocumento())
						.append("\n");
			}

			bodyEmail.append(documentiList.toString());
		}
		
		// Notifica di cambio stato in Registrata da PA
		MudeRIstanzaStato istanzaStato = mudeRIstanzaStatoRepository.findStatoByIstanza(idIstanza);
		MudeDStatoIstanza statoIstanza = istanzaStato.getStatoIstanza();
		if(mudeTNotifica.getTipoNotifica().getCodTipoNotifica().equalsIgnoreCase("CAMBIO_STATO") &&
			StatoIstanza.REGISTRATA_DA_PA.getValue().equalsIgnoreCase(statoIstanza.getCodice()) ) {
			    String pattern = "dd/MM/yyyy";
			    DateFormat df = new SimpleDateFormat(pattern); 
			    bodyEmail.append("Dettaglio dati registrazione:").append("\n");
				bodyEmail.append("Nr. Protocollo: ").append(istanza.getNumeroProtocollo()).append("\n");
				bodyEmail.append("Data. Protocollo: ").append(df.format(istanza.getDataProtocollo())).append("\n");
				MudeTPratica mudeTPratica = mudeTPraticaRepository.findByIdIstanza(idIstanza);
				bodyEmail.append("Nr. Pratica: ").append(mudeTPratica.getNumeroPratica()).append("\n");
				
				bodyEmail.append("Responsabile del procedimento: ").append(istanza.getResponsabileProcedimento()).append("\n");
				
				if(istanza.getTipoIstanza().getCodice().equalsIgnoreCase("SCIA") ||
				   istanza.getTipoIstanza().getCodice().equalsIgnoreCase("PDC") ||
				   istanza.getTipoIstanza().getCodice().equalsIgnoreCase("AUT-PAES") ||
				   istanza.getTipoIstanza().getCodice().equalsIgnoreCase("SCIA-ALT-PDC") ||
				   istanza.getTipoIstanza().getCodice().equalsIgnoreCase("SCIA-AGI") ||
				   istanza.getTipoIstanza().getCodice().equalsIgnoreCase("SCIA-AGI-ATD")) {
					
					String lart1_default = "Ai sensi della L. 7 agosto 1990 nr 241, del D.P.R. 6 giugno 2001 nr 380 e dell'art. 49 della LR 56/1977 si comunica che la data di avvio del procedimento corrisponde alla data di presentazione dell'istanza e il valore dell'ultima data di invio dell'istanza, considerando quindi quello con data_inizio più recente.";
					String lart1 = getValueFromMudeCProprieta("MUDE_RIF_NORMATIVI_DATA_AVVIO_PROCEDIMENTO",lart1_default);
					bodyEmail.append(lart1).append("\n");
					
					String lart2_default = "Ai sensi dei commi 2 e 3 dell'art. 2 L. 241/1990 si comunica che la domanda sarà esaminata secondo l'ordine cronologico di presentazione e nel rispetto dei termini previsti ";
					String lart2 = getValueFromMudeCProprieta("MUDE_RIF_NORMATIVI_TERMINI_PROCEDIMENTO",lart2_default);
					bodyEmail.append(lart2);
					
					String lart_forAll="";
					switch (istanza.getTipoIstanza().getCodice()) {
			            case "SCIA":
			            	String lart_scia_default = "dall’art. 19 comma 3 della L 241/1990 e smi.";
							lart_forAll = getValueFromMudeCProprieta("MUDE_RIF_NORMATIVI_TERMINI_PROCEDIMENTO_SCIA",lart_scia_default);
						break;
			            case "PDC":
			            	String lart_pdc_default = "dall'art. 20 del DPR 380/2001 e smi.";
			            	lart_forAll = getValueFromMudeCProprieta("MUDE_RIF_NORMATIVI_TERMINI_PROCEDIMENTO_PDC",lart_pdc_default);
						break;
			            case "AUT-PAES":
			            	String lart_aut_paes_default = "";
			            	if(istanza.getSpeciePratica() != null && istanza.getSpeciePratica().getCodice().equals("SPE0000088")) {
			            		lart_aut_paes_default = "dall'art. 22 del D. Lgs. 42/2004 e smi.";
			            		lart_forAll = getValueFromMudeCProprieta("MUDE_RIF_NORMATIVI_TERMINI_PROCEDIMENTO_AUT_PAES_ordinaria",lart_aut_paes_default);
			            	}

			            	else if(istanza.getSpeciePratica() != null && istanza.getSpeciePratica().getCodice().equals("SPE0000089")) {
			            		lart_aut_paes_default = "dal DPR 139/2010 e smi.";
			            		lart_forAll = getValueFromMudeCProprieta("MUDE_RIF_NORMATIVI_TERMINI_PROCEDIMENTO_AUT_PAES_semplificata",lart_aut_paes_default);
			            	}

						break;
			            case "SCIA-ALT-PDC":
			            	String lart_scia_alt_pdc_default = "dall'art. 23 del DPR 380/2001 e smi.";
			            	lart_forAll = getValueFromMudeCProprieta("MUDE_RIF_NORMATIVI_TERMINI_PROCEDIMENTO_SCIA_ALTPDC",lart_scia_alt_pdc_default);
						break;
			            case "SCIA-AGI":
			            case "SCIA-AGI-ATD":
			            	String lart_scia_agi_default = "dall'art. 24 del DPR 380/2001 e smi.";
			            	lart_forAll = getValueFromMudeCProprieta("MUDE_RIF_NORMATIVI_TERMINI_PROCEDIMENTO_SCIA_AGIBIL",lart_scia_agi_default);
						break;
					}

					bodyEmail.append(lart_forAll).append("\n");
					
					String lart3_default = "Decorso inutilmente il termine per la conclusione del procedimento, il privato può rivolgersi al responsabile individuato nell'ambito delle figure apicali dell'amministrazione, cui è attribuito il potere sostitutivo in caso di inerzia, così come previsto all'art. 2 comma 9-ter L. 241/1990.";
					String lart3 = getValueFromMudeCProprieta("MUDE_RIF_NORMATIVI_TERMINI_PROCEDIMENTO_NON_RISPETTO",lart3_default);
					bodyEmail.append(lart3).append("\n");
				}
		}
		
		return bodyEmail.toString();
		
	}
	
	public String createDettaglioNotificaPortale(Long idIstanza, MudeTNotifica mudeTNotifica) {
		StringBuilder bodyEmail = new StringBuilder();
		
		MudeTIstanza istanza = mudeTIstanzaRepository.findByIdIstanza(idIstanza);
		
		// Notifica di cambio stato in Registrata da PA
		MudeRIstanzaStato istanzaStato = mudeRIstanzaStatoRepository.findStatoByIstanza(idIstanza);
		MudeDStatoIstanza statoIstanza = istanzaStato.getStatoIstanza();
		if(mudeTNotifica.getTipoNotifica().getCodTipoNotifica().equalsIgnoreCase("CAMBIO_STATO") &&
			StatoIstanza.REGISTRATA_DA_PA.getValue().equalsIgnoreCase(statoIstanza.getCodice()) ) {
			    String pattern = "dd/MM/yyyy";
			    DateFormat df = new SimpleDateFormat(pattern); 
				bodyEmail.append("Nr. Protocollo: ").append(istanza.getNumeroProtocollo()).append(";  ");
				bodyEmail.append("Data. Protocollo: ").append(df.format(istanza.getDataProtocollo())).append(";  ");
				MudeTPratica mudeTPratica = mudeTPraticaRepository.findByIdIstanza(idIstanza);
				bodyEmail.append("Nr. Pratica: ").append(mudeTPratica.getNumeroPratica()).append(";  ");
				
				bodyEmail.append("Responsabile del procedimento: ").append(istanza.getResponsabileProcedimento()).append(";  ");
				
				if(istanza.getTipoIstanza().getCodice().equalsIgnoreCase("SCIA") ||
				   istanza.getTipoIstanza().getCodice().equalsIgnoreCase("PDC") ||
				   istanza.getTipoIstanza().getCodice().equalsIgnoreCase("AUT-PAES") ||
				   istanza.getTipoIstanza().getCodice().equalsIgnoreCase("SCIA-ALT-PDC") ||
				   istanza.getTipoIstanza().getCodice().equalsIgnoreCase("SCIA-AGI") ||
				   istanza.getTipoIstanza().getCodice().equalsIgnoreCase("SCIA-AGI-ATD")) {
					
					String lart1_default = "Ai sensi della L. 7 agosto 1990 nr 241, del D.P.R. 6 giugno 2001 nr 380 e dell'art. 49 della LR 56/1977 si comunica che la data di avvio del procedimento corrisponde alla data di presentazione dell'istanza e il valore dell'ultima data di invio dell'istanza, considerando quindi quello con data_inizio più recente.";
					String lart1 = getValueFromMudeCProprieta("MUDE_RIF_NORMATIVI_DATA_AVVIO_PROCEDIMENTO",lart1_default);
					bodyEmail.append(lart1).append(";  ");
					
					String lart2_default = "Ai sensi dei commi 2 e 3 dell'art. 2 L. 241/1990 si comunica che la domanda sarà esaminata secondo l'ordine cronologico di presentazione e nel rispetto dei termini previsti ";
					String lart2 = getValueFromMudeCProprieta("MUDE_RIF_NORMATIVI_TERMINI_PROCEDIMENTO",lart2_default);
					bodyEmail.append(lart2);
					
					String lart_forAll="";
					switch (istanza.getTipoIstanza().getCodice()) {
			            case "SCIA":
			            	String lart_scia_default = "dall’art. 19 comma 3 della L 241/1990 e smi.";
							lart_forAll = getValueFromMudeCProprieta("MUDE_RIF_NORMATIVI_TERMINI_PROCEDIMENTO_SCIA",lart_scia_default);
						break;
			            case "PDC":
			            	String lart_pdc_default = "dall'art. 20 del DPR 380/2001 e smi.";
			            	lart_forAll = getValueFromMudeCProprieta("MUDE_RIF_NORMATIVI_TERMINI_PROCEDIMENTO_PDC",lart_pdc_default);
						break;
			            case "AUT-PAES":
			            	String lart_aut_paes_default = "";
			            	if(istanza.getSpeciePratica() != null && istanza.getSpeciePratica().getCodice().equals("SPE0000088")) {
			            		lart_aut_paes_default = "dall'art. 22 del D. Lgs. 42/2004 e smi.";
			            		lart_forAll = getValueFromMudeCProprieta("MUDE_RIF_NORMATIVI_TERMINI_PROCEDIMENTO_AUT_PAES_ordinaria",lart_aut_paes_default);
			            	}

			            	else if(istanza.getSpeciePratica() != null && istanza.getSpeciePratica().getCodice().equals("SPE0000089")) {
			            		lart_aut_paes_default = "dal DPR 139/2010 e smi.";
			            		lart_forAll = getValueFromMudeCProprieta("MUDE_RIF_NORMATIVI_TERMINI_PROCEDIMENTO_AUT_PAES_semplificata",lart_aut_paes_default);
			            	}

						break;
			            case "SCIA-ALT-PDC":
			            	String lart_scia_alt_pdc_default = "dall'art. 23 del DPR 380/2001 e smi.";
			            	lart_forAll = getValueFromMudeCProprieta("MUDE_RIF_NORMATIVI_TERMINI_PROCEDIMENTO_SCIA_ALTPDC",lart_scia_alt_pdc_default);
						break;
			            case "SCIA-AGI":
			            case "SCIA-AGI-ATD":
			            	String lart_scia_agi_default = "dall'art. 24 del DPR 380/2001 e smi.";
			            	lart_forAll = getValueFromMudeCProprieta("MUDE_RIF_NORMATIVI_TERMINI_PROCEDIMENTO_SCIA_AGIBIL",lart_scia_agi_default);
						break;
					}

					bodyEmail.append(lart_forAll).append(";  ");
					
					String lart3_default = "Decorso inutilmente il termine per la conclusione del procedimento, il privato può rivolgersi al responsabile individuato nell'ambito delle figure apicali dell'amministrazione, cui è attribuito il potere sostitutivo in caso di inerzia, così come previsto all'art. 2 comma 9-ter L. 241/1990.";
					String lart3 = getValueFromMudeCProprieta("MUDE_RIF_NORMATIVI_TERMINI_PROCEDIMENTO_NON_RISPETTO",lart3_default);
					bodyEmail.append(lart3).append(";  ");
				}
		}
		
		return bodyEmail.toString();
		
	}
	
	private String createSalutiNotificaEmail(Long idIstanza, MudeTNotifica mudeTNotifica, IstanzaVO istanzaVO) {

		StringBuilder bodyEmail = new StringBuilder();
		
		MudeTEnte mudeTEnte = null;
		if(mudeTNotifica.getMudeTUser() == null) {
			if(istanzaVO.getEnteDiRiferimento() != null) {
				Long enteId = istanzaVO.getEnteDiRiferimento().getId();
				mudeTEnte = mudeTEnteRepository.findOne(enteId);
			}
		}else {
			mudeTEnte = mudeTEnteRepository.getEnteRiceventeByUtenteIstanza(mudeTNotifica.getMudeTUser().getIdUser(), idIstanza);
		}

		bodyEmail.append("Cordiali saluti.").append("\n");
		if(mudeTEnte != null) 
			bodyEmail.append(mudeTEnte.getDescrizione()).append("\n");
		return bodyEmail.toString();
		
	}
	
	private String getValueFromMudeCProprieta(String name, String defaultValue) {

		String value = mudeCProprietaRepository.getValueByName(name, defaultValue);
		if (value!=null) {
			return value;
		} 

		return "";
	}
	
	public String createTestoNotificaDPS(Long idIstanza, MudeTNotifica mudeTNotifica) {
		StringBuilder bodyEmail = new StringBuilder();
		String pattern = "dd/MM/yyyy";
	    DateFormat df = new SimpleDateFormat(pattern); 
		MudeTIstanza istanza = mudeTIstanzaRepository.findByIdIstanza(idIstanza);			
		String lart1_default = "Ai sensi della L. 7 agosto 1990 nr 241, del D.P.R. 6 giugno 2001 nr 380 e dell'art. 49 della LR 56/1977, si comunica che la presentazione dell'istanza MUDE n.";
		String lart1 = getValueFromMudeCProprieta("MUDE_RIF_NORMATIVI_DATA_AVVIO_PROCEDIMENTO_DPS",lart1_default);
		bodyEmail.append(lart1).append(istanza.getCodiceIstanza()).append(" è avvenuta in data ");
		bodyEmail.append(df.format(new Date()));
		return bodyEmail.toString();
		
	}

	String[] getArrayFilterStato(String filter) {
		if(FilterUtil.getValue(filter, "stato") != null && FilterUtil.getValue(filter, "stato").get("in") != null) {
			String filterStati = (String)FilterUtil.getValue(filter, "stato").get("in");
			return filterStati.split(",");
		}

		if(FilterUtil.getValue(filter, "stato") != null && FilterUtil.getValue(filter, "stato").get("eq") != null)
			return new String[] { (String)FilterUtil.getValue(filter, "stato").get("eq") };
				
		return new String[] {""};		
	}

	private Specification<MudeTIstanza> getDSRoleSPSpecification(MudeTUser mudeTUser, String filter) {
		Specification<MudeTIstanza> filterSpeciePratica = null;
		String defaultSPs = FilterUtil.getStringValue(filter, "_defaultSPs", "in");
		if(!StringUtils.isBlank(defaultSPs)) {
			 String filterSPs = mudeCProprietaRepository.retrieveByUserRoles("DS_USER_ROLE_SP_VISIBILITY", mudeTUser.getIdUser(), defaultSPs);
			 Map<String, String> sps = new HashMap<>();
			 sps.put("in", filterSPs);
			 filterSpeciePratica = MudeTIstanzaSpecification.findByTipoPratica(sps);
		}

		return filterSpeciePratica;
	}
 
	private Specification<MudeTIstanzaSlim> getDSRoleSPSpecificationSlim(MudeTUser mudeTUser, String filter) {
		Specification<MudeTIstanzaSlim> filterSpeciePratica = null;
		String defaultSPs = FilterUtil.getStringValue(filter, "_defaultSPs", "in");
		if(!StringUtils.isBlank(defaultSPs)) {
			 String filterSPs = mudeCProprietaRepository.retrieveByUserRoles("DS_USER_ROLE_SP_VISIBILITY", mudeTUser.getIdUser(), defaultSPs);
			 Map<String, String> sps = new HashMap<>();
			 sps.put("in", filterSPs);
			 filterSpeciePratica = MudeTIstanzaSlimSpecification.findByTipoPratica(sps);
		}

		return filterSpeciePratica;
	}
 
	private List<String> getRuoliSoggetti(String filter) {
		List<String> soggettoRole = new ArrayList<>();
		String _soggettoRole = FilterUtil.getStringValue(filter, "tipo_soggetto");

		if(StringUtils.isBlank(_soggettoRole))
            soggettoRole.add("IN");
		else {
            for(String roleCode : _soggettoRole.split(",")) {
        		if(roleCode.startsWith("~"))
        			for(MudeDRuoloSoggetto mudeDRuoloSoggetto : mudeDRuoloSoggettoRepository.findAllActiveRolesByCategory(roleCode.substring(1)))
        				soggettoRole.add(mudeDRuoloSoggetto.getCodice());
            	else
            		soggettoRole.add(roleCode);
            }
		}

		return soggettoRole;
	}

}