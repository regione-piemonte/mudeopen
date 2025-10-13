/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRuoloUtente;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDStatoFiltroVeloce;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTaskQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoNotifica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaStato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRNotificaUtente;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRUtenteRuolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTDocumento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTNotifica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTPratica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.DocumentoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.NotificaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDRuoloUtenteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTaskQuadroRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoNotificaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaStatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRNotificaUtenteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRUtenteRuoloRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTDocumentoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTNotificaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTPraticaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.ContattoService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.NotificaService;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeDQuadroSpecification;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeTNotificaSpecification;
import it.csi.mudeopen.mudeopensrvcommon.util.FilterUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.PageUtil;
import it.csi.mudeopen.mudeopensrvcommon.util.PaginationResponseUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.StatoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoRuoloUtente;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoTaskQuadro;
import it.csi.mudeopen.mudeopensrvcommon.vo.documento.DocumentoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.notifica.NotificaVO;

@Service
public class NotificaServiceImpl implements NotificaService {
	
	protected static final Logger logger = Logger.getLogger(NotificaServiceImpl.class.getCanonicalName());

    final static Hashtable<String, String> syncmoniker = new Hashtable<String, String>();

    @Autowired
    private MudeTNotificaRepository mudeTNotificaRepository;

    @Autowired
    private MudeRNotificaUtenteRepository mudeRNotificaUtenteRepository;

    @Autowired
    private NotificaEntityMapper notificaEntityMapper;

    @Autowired
    private MudeDTaskQuadroRepository mudeDTaskQuadroRepository;

    @Autowired
    private MudeDQuadroRepository mudeDQuadroRepository;

    @Autowired
    MudeDTipoNotificaRepository mudeDTipoNotificaRepository;

    @Autowired
    MudeRIstanzaStatoRepository mudeRIstanzaStatoRepository;

    @Autowired
    MudeDRuoloUtenteRepository mudeDRuoloUtenteRepository;

    @Autowired
    private MudeRUtenteRuoloRepository mudeRUtenteRuoloRepository;

    @Autowired
    MudeTPraticaRepository mudeTPraticaRepository;

    @Autowired
    MudeTDocumentoRepository mudeTDocumentoRepository;

    @Autowired
	ContattoService contattoService;

    @Autowired
    protected MudeTIstanzaRepository mudeTIstanzaRepository;

    @Autowired
    protected DocumentoEntityMapper documentoEntityMapper;

    @Override
    public Response cercaNotificheIstanza(Long idIstanza, int page, int size, MudeTUser mudeTUser) {

        Pageable pageble = new PageRequest(page, size, new Sort(Sort.Direction.DESC, "dataInizio"));

        List<NotificaVO> notificaVOList = new ArrayList<NotificaVO>();

        Page<MudeTNotifica> mudeTNotificaList = mudeTNotificaRepository.findAllByIstanza(idIstanza, pageble);

        if (mudeTNotificaList.getTotalElements()>0) {
        	for (MudeTNotifica mudeTNotifica: mudeTNotificaList )  {
            	List<MudeRNotificaUtente>  mudeRNotificaUtenteList = mudeRNotificaUtenteRepository.findByMudeTNotifica(mudeTNotifica);
            	notificaVOList.add(notificaEntityMapper.mapEntityToVOList(mudeTNotifica, mudeRNotificaUtenteList));
            }    
        }

        return PaginationResponseUtil.buildResponse(notificaVOList, page, size, mudeTNotificaList.getTotalPages(), mudeTNotificaList.getTotalElements());
     }

    public List<SelectVO> reuperoTemplateFormIoNotifica(Long idIstanza, MudeTUser mudeTUser, Long idTipoNotifica ) {
    	List<SelectVO> notificaFormIO = new ArrayList<SelectVO>();
    	String jsonTemplateFormIo = "";
    	boolean notificaAPA=false;
    	
    	MudeRIstanzaStato istanzaStato = mudeRIstanzaStatoRepository.findStatoByIstanza(idIstanza);
        if (null != istanzaStato && StatoIstanza.REGISTRATA_DA_PA.getValue().equalsIgnoreCase(istanzaStato.getStatoIstanza().getCodice())){
        	jsonTemplateFormIo = recuperoJsonTemplateFormIo(TipoTaskQuadro.NOTIF_ISTANZA_APA.getValue());
        	notificaAPA = true;
        }else {
        	jsonTemplateFormIo = recuperoJsonTemplateFormIo(TipoTaskQuadro.NOTIF_ISTANZA.getValue());
        }
        	
    	//Replace Json Template

        //Destinatari
        String destinatari = contattoService.recuperaDestinatariNotifiche(idIstanza, mudeTUser.getIdUser());
        String jsonReplace = jsonTemplateFormIo.replace("${DESTINATARIO_NOTIFICA}",destinatari);

        //Oggetto
        MudeTIstanza mudeTIstanza = mudeTIstanzaRepository.findOne(idIstanza);
        String oggetto = "istanza MUDE n." + mudeTIstanza.getCodiceIstanza();
        if(idTipoNotifica != null && idTipoNotifica != 0L) {
        	oggetto = oggetto + " " + (mudeDTipoNotificaRepository.findByIdTipoNotifica(idTipoNotifica)).getOggettoProposto();
        }
        jsonReplace = jsonReplace.replace("${OGGETTO_NOTIFICA}",oggetto);

        /*
        List<MudeDTipoNotifica> tipoNotifica = mudeDTipoNotificaRepository.findByCodTipoNotificaAndValid("GENERICA");
        String tipoNotificaLst="";
		boolean isFirst=true;
		if(!tipoNotifica.isEmpty()) {
			tipoNotificaLst = "{ \"values\": [";
	        for(MudeDTipoNotifica mudeDTipoNotifica : tipoNotifica) {
				if(!isFirst) {
					tipoNotificaLst = tipoNotificaLst +",";
				}else {
					isFirst=false;
				}
				tipoNotificaLst = tipoNotificaLst + "{\"value\":\""+mudeDTipoNotifica.getIdTipoNotifica()+"\",\"label\":\""+mudeDTipoNotifica.getDesTipoNotifica()+"\"}";
			}
	        tipoNotificaLst = tipoNotificaLst + "]}";
		}
		jsonReplace = jsonReplace.replace("\"${LISTA_TIPO_NOTIFICA}\"",tipoNotificaLst);*/
		
		List<MudeRUtenteRuolo> mudeRUtenteRuoloList = mudeRUtenteRuoloRepository.findByIdUser(mudeTUser.getIdUser());
		/*String ruoloMax=null;
		for(MudeRUtenteRuolo mudeRUtenteRuolo : mudeRUtenteRuoloList) {
			if((TipoRuoloUtente.CONSULTATORE.getValue()).equals(ruoloMax) || ruoloMax == null){
				ruoloMax = mudeRUtenteRuolo.getRuoloUtente();
			}
		}
		
		MudeDRuoloUtente mudeDRuolo = mudeDRuoloUtenteRepository.findByCodice(ruoloMax);
        String jsonReplaceResult = jsonReplace.replace("${MITTENTE_NOTIFICA}", mudeDRuolo.getDescrizione());
        */
		
		// Valorizzazione ruolo mittente con lista ruoli
        String ruoloMittenteLst="";
        boolean isFirst=true;
        for(MudeRUtenteRuolo mudeRUtenteRuolo : mudeRUtenteRuoloList) {
			if(!isFirst) {
				ruoloMittenteLst = ruoloMittenteLst +",";
			}else {
				isFirst=false;
			}
			MudeDRuoloUtente mudeDRuolo = mudeDRuoloUtenteRepository.findByCodice(mudeRUtenteRuolo.getRuoloUtente());
			ruoloMittenteLst = ruoloMittenteLst +"\""+mudeDRuolo.getDescrizione()+"\"";
        }
        String jsonReplaceResult = jsonReplace.replace("\"${MITTENTE_NOTIFICA}\"", ruoloMittenteLst);

        // Valorizzazione lista documenti della pratica
        if(notificaAPA) {
        	String documentiLst="";
        	MudeTPratica mudeTPratica = mudeTPraticaRepository.findByIdIstanza(idIstanza);
        	List<MudeTDocumento> documentoList = new ArrayList<>();
        	if(mudeTPratica != null) {
        		documentoList = mudeTDocumentoRepository.findAllByPratica(mudeTPratica.getId());
        	}
    		isFirst=true;
    		if(!documentoList.isEmpty()) {
    			documentiLst = "{ \"values\": [";
	            for(MudeTDocumento mudeTDocumento : documentoList) {
	    			if(!isFirst) {
	    				documentiLst = documentiLst +",";
	    			}else {
	    				isFirst=false;
	    			}
	    			documentiLst = documentiLst + "{\"value\":\""+mudeTDocumento.getId()+"\",\"label\":\""+mudeTDocumento.getNomeFileDocumento()+"\"}";
	    				
	    		}
	            documentiLst = documentiLst + "]}";
	        	jsonReplaceResult = jsonReplaceResult.replace("\"${DOCUMENTI_RIF}\"",documentiLst);
        	}
        }

        notificaFormIO.add(new SelectVO("Inserisci notifica", jsonReplaceResult));
        return notificaFormIO;
    }

    public String recuperoJsonTemplateFormIo(String codTask){
    	MudeDTaskQuadro dTaskQuadro = mudeDTaskQuadroRepository.findByCodiceTask(codTask);
        if(dTaskQuadro == null)
        	throw new BusinessException("Non e' presente un idQuadro per il codice '"+codTask+"'");
    	
    	MudeDQuadro mudeDQuadro = mudeDQuadroRepository.findOne(dTaskQuadro.getQuadro());
    	
    	String mudeDQuadroJson = null;
    	
    	if (mudeDQuadro != null) 
    		mudeDQuadroJson = mudeDQuadro.getJsonConfiguraQuadro();
    	

    	return mudeDQuadroJson;
    }

    // *****************************************************************************************
    // FO
    // *****************************************************************************************
    @Override
    public Response loadNotificheFO(String filter, int page, int size, MudeTUser mudeTUser) {
        /*
        Pageable pageable = PageUtil.getPageable(page, size);

        Specification<MudeTNotificaSpecification> hasTipoQuadroLike = MudeDQuadroSpecification.hasTipoQuadroLike(FilterUtil.getValue(filter, "cod_tipo_istanza"));

        Page<MudeDQuadro> mudeDQuadroPages = mudeDQuadroRepository.findAll(Specifications.where(hasTipoQuadroLike)
        		.and(hasTipoGestioneLike)
        		.and(hasVersione), pageable);
        */

        Long idIstanza = FilterUtil.getLongValue(filter, "idIstanza");
        Long idFascicolo = FilterUtil.getLongValue(filter, "idFascicolo");
        String codIstanza = FilterUtil.getStringValue(filter, "codIstanza");
        String comune = FilterUtil.getStringValue(filter, "comune");
        String intestatario = FilterUtil.getStringValue(filter, "intestatario");

        Pageable pageble = new PageRequest(page, size);
        Page<MudeTNotifica> mudeTNotificaList = mudeTNotificaRepository.findAllByIstanzaByUser(mudeTUser.getIdUser(), 
        																					   idIstanza == null? -1 : idIstanza,
        																					   idFascicolo == null? -1 : idFascicolo,
        																					   codIstanza == null? "" : codIstanza,
        																					   comune == null? "" : comune,
        																					   intestatario == null? "" : intestatario,
        																					   pageble);
        List<NotificaVO> notificaVOList = notificaEntityMapper.mapListEntityToListVO(mudeTNotificaList.getContent(), null);

        return PaginationResponseUtil.buildResponse(notificaVOList, page, size, mudeTNotificaList.getTotalPages(), mudeTNotificaList.getTotalElements());
     }

    @Override
    public List<DocumentoVO> notificaLettaFO(Long idNotifica, MudeTUser mudeTUser) {
        mudeTNotificaRepository.setAsRead(mudeTUser.getIdUser(), idNotifica);

        return documentoEntityMapper.mapListEntityToListSlimVO(mudeTDocumentoRepository.findAllByIdNotifica(idNotifica), mudeTUser);
    }

    public List<SelectVO> recuperaTipiNotifica() {

        List<SelectVO> listStatiVO = new ArrayList<SelectVO>();
        List<MudeDTipoNotifica> tipoNotificaList = mudeDTipoNotificaRepository.findByCodTipoNotificaAndValid("GENERICA");
        for (MudeDTipoNotifica tipoNotifica : tipoNotificaList) {
            listStatiVO.add(new SelectVO(tipoNotifica.getIdTipoNotifica(), tipoNotifica.getDesTipoNotifica()));
        }

        return listStatiVO;
    }
}