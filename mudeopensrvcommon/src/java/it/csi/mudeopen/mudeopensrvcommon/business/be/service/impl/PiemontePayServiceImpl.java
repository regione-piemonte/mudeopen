/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDPPayImporto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTEnte;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTPpayPagamento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.PpayImportoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaEnteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDPPayImportiRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTContattoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTEnteRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTPpayPagamentoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.PiemontePayService;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.LoggerUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.ppay.PPayImportoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.ppay.PPayResultVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.ppay.api.ComponentePagamentoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.ppay.api.GetIUVChiamanteEsternoInputVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.ppay.api.GetIUVChiamanteEsternoOutputVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.ppay.api.PagamentoIUVInputVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.ppay.api.PagamentoIUVOutputVO;

@Service
public class PiemontePayServiceImpl implements PiemontePayService {
	private final String CLS_NAME = this.getClass().getSimpleName();
    private static Logger logger = Logger.getLogger(PiemontePayServiceImpl.class.getCanonicalName());

    @Autowired
    MudeTIstanzaRepository mudeTIstanzaRepository;

    @Autowired
    PpayImportoEntityMapper ppayImportoEntityMapper;

    @Autowired
    MudeDPPayImportiRepository mudeDPPayImportiRepository;

    @Autowired
    MudeTEnteRepository mudeTEnteRepository;

    @Autowired
    MudeTPpayPagamentoRepository mudeTPpayPagamentoRepository;

    @Autowired
    MudeCProprietaRepository mudeCProprietaRepository;

    @Autowired
    MudeCProprietaEnteRepository mudeCProprietaEnteRepository;

    @Autowired
    MudeTContattoRepository mudeTContattoRepository;

	public List<PPayImportoVO> recuperaDettagliPagamento(Long idIstanza) {
    	MudeTIstanza mudeTIstanza = mudeTIstanzaRepository.findOne(idIstanza);
    	MudeTEnte enteRiferimento = mudeTEnteRepository.retrieveMainActive(mudeTIstanza.getComune().getIdComune(),
																					   mudeTIstanza.getTipoIstanza().getCodice(),
																					   mudeTIstanza.getSpeciePratica() == null? "getnulls" : mudeTIstanza.getSpeciePratica().getCodice());

    	List<MudeDPPayImporto> importi = enteRiferimento == null? 
    							new ArrayList<>() :
								mudeDPPayImportiRepository.getFirstValidAmountSet(enteRiferimento.getId(), 
																				  mudeTIstanza.getTipoIstanza().getCodice(), 
																				  mudeTIstanza.getSpeciePratica().getCodice());
    	
    	return ppayImportoEntityMapper.mapListEntityToListVO(importi, null);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, noRollbackFor=BusinessException.class)
    public PPayResultVO avviaPagamento(Long idIstanza, String extraParams, MudeTUser mudeTUser) {
		PPayResultVO ppayResultVO = new PPayResultVO();
		
		MudeTIstanza mudeTIstanza = mudeTIstanzaRepository.getOne(idIstanza);
		if(mudeTIstanza == null) throw new BusinessException("Istanza non trovata: " + idIstanza);

		MudeTPpayPagamento mudeTPpayPagamento = createNewPayment(mudeTIstanza);
		List<MudeDPPayImporto> importi = setTotalAmountToPay(mudeTIstanza, mudeTPpayPagamento, ppayResultVO, true);

		if(extraParams != null && !"PROD".equals(Constants._environment)) {
			if(extraParams.indexOf("skip-ppay") == -1)
				doPPayTransactionBegin(mudeTIstanza, mudeTPpayPagamento, ppayResultVO, importi, mudeTUser);
			if(extraParams.indexOf("simulate-succeed") > -1)
				ppayResultVO.setUrl("/ppayCallback?idPagamento="+mudeTPpayPagamento.getIdentificativoPagamento()+"&codEsito=000&descEsito=&source=epayapi-pagamentoIUV");
			if(extraParams.indexOf("simulate-failure") > -1)
				ppayResultVO.setUrl("/ppayCallback?idPagamento="+mudeTPpayPagamento.getIdentificativoPagamento()+"&codEsito=100&descEsito=&source=epayapi-pagamentoIUV");
		}
		else
			doPPayTransactionBegin(mudeTIstanza, mudeTPpayPagamento, ppayResultVO, importi, mudeTUser);
		
		mudeTPpayPagamentoRepository.saveAndFlushDAO(mudeTPpayPagamento);
		
		return ppayResultVO;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public PPayResultVO ppayCallback(String idPagamento, String codEsito, String descrizioneEsito, MudeTUser mudeTUser) {
		MudeTPpayPagamento mudeTPpayPagamento = mudeTPpayPagamentoRepository.getByIdentificativoPagamento(idPagamento);
		if(mudeTPpayPagamento == null) throw new BusinessException("Identificativo di pagamento non trovato: " + idPagamento);
		
		mudeTPpayPagamento.setCodiceEsito(codEsito);
		mudeTPpayPagamento.setDescrizioneEsito("ppayCallback-" + descrizioneEsito);
		mudeTPpayPagamentoRepository.saveDAO(mudeTPpayPagamento);

		PPayResultVO ppayResultVO = new PPayResultVO();
		MudeTIstanza mudeTIstanza = mudeTIstanzaRepository.getOne(mudeTPpayPagamento.getIstanza().getId());

		setTotalAmountToPay(mudeTIstanza, mudeTPpayPagamento, ppayResultVO, false);
		mudeTPpayPagamentoRepository.saveDAO(mudeTPpayPagamento);
		
		return ppayResultVO;
	}
	
	private List<MudeDPPayImporto> setTotalAmountToPay(MudeTIstanza mudeTIstanza, MudeTPpayPagamento mudeTPpayPagamento, PPayResultVO ppayResultVO, boolean checkAmount) {
		double total = 0;
    	List<MudeDPPayImporto> mudeDPPayImporti = new ArrayList<>();
		
		try {
	        JSONObject jsonDataIstanza = new JSONObject(mudeTIstanza.getJsonData());
	        JSONObject qdrPagamento = (JSONObject)jsonDataIstanza.get("QDR_PAGAMENTO");
	        
	        JSONArray importiArray = qdrPagamento.getJSONArray("importi");
	        for (int i = 0, size = importiArray.length(); i < size; i++) {
	        	JSONObject importoInArray = importiArray.getJSONObject(i);

	        	// in case of transaction completed, skip record 
	        	if(importoInArray.has("_statoPagamento") && "completato".equals(importoInArray.get("_statoPagamento"))) 
	        			continue;

        		importoInArray.put("_statoPagamento", "100".equals(mudeTPpayPagamento.getCodiceEsito()) ? "errore" : 
        				"000".equals(mudeTPpayPagamento.getCodiceEsito()) ? "completato" : null );
	        	importoInArray.put("_identificativoPagamento", mudeTPpayPagamento.getIdentificativoPagamento());

	        	double payAmount = 0;
	        	if(importoInArray.has("payAmount"))
		        	total = total + (payAmount = ((Number)importoInArray.get("payAmount")).doubleValue());
	        	
	        	if(checkAmount) {
	        		MudeDPPayImporto mudeDPPayImporto = mudeDPPayImportiRepository.findOne(((Number)importoInArray.get("id_importo")).longValue());
	        		mudeDPPayImporto.setImporto(new BigDecimal(payAmount));
	        		mudeDPPayImporti.add(mudeDPPayImporto);
	        	}
	        }

			ppayResultVO.setJsonImporti(importiArray.toString());
	        mudeTIstanza.setJsonData(jsonDataIstanza.toString());
	        mudeTIstanzaRepository.saveDAO(mudeTIstanza);
		} catch (Exception e) {
			LoggerUtil.error(logger, "Errore generco nel calcolo del pagamento", e);
			throw new BusinessException("Errore generico nel calcolo del pagamento, impossibile procedere");
		}
		
		
		if(checkAmount && total == 0)
			throw new BusinessException("Attenzione non è stato selezionato nessun importo da pagare!");
		
		mudeTPpayPagamento.setImporto(String.format(Locale.ITALIAN, "%.2f", total));
		ppayResultVO.setTotale(mudeTPpayPagamento.getImporto());
		
		return mudeDPPayImporti;
	}
	
	private MudeTPpayPagamento createNewPayment(MudeTIstanza mudeTIstanza) {
		MudeTPpayPagamento mudeTPpayPagamento = new MudeTPpayPagamento();
		mudeTPpayPagamento.setIstanza(mudeTIstanza);
		mudeTPpayPagamentoRepository.saveDAO(mudeTPpayPagamento);
		
		// MPAY-yyMMdd-sequenceID
		mudeTPpayPagamento.setIdentificativoPagamento("MPAY-" + mudeTIstanza.getId() + "-" + (new SimpleDateFormat("yyMMdd").format(mudeTPpayPagamento.getDataInizio())) + "-" + mudeTPpayPagamento.getId());
		
		return mudeTPpayPagamento;
	}

	private Response invokeRestEasyPost(final String url, final String uid, final String pwd,
						Object vo) throws Exception {
    	ResteasyClient client = new ResteasyClientBuilder().build();
    	client.register(new ClientRequestFilter() {
			@Override
			public void filter(ClientRequestContext requestContext) throws IOException {
		        String token = uid + ":" + pwd;
		        String base64Token = Base64.encodeBytes(token.getBytes());
		        requestContext.getHeaders().add("Authorization", "Basic " + base64Token);
		        
				LoggerUtil.debug(logger, "PPAY-rest CALL: " + requestContext.getHeaders().toString() + "\n" + requestContext.getEntity().toString());
			}
		});
    	
    	ResteasyWebTarget target = client.target(url);
    	Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON); // .header("some", "header");
    	
    	String objAsJson = new ObjectMapper().writeValueAsString(vo);

    	Invocation invocation = invocationBuilder.buildPost(Entity.entity(objAsJson, MediaType.APPLICATION_JSON));
    	
    	Response res = invocation.invoke();
    	if(res.getStatus() != 200) {
    		String errorJson = res.readEntity(String.class);
			LoggerUtil.error(logger, "PPAY-rest RESULT: " + errorJson);
        	
        	//PPayGenericError error = res.readEntity(PPayGenericError.class);
			throw new Exception("PPAY-rest RESULT: " + errorJson);
    	}
    	
    	return res;
	}
	
	private GetIUVChiamanteEsternoOutputVO getIUVChiamanteEsterno(final String url, final String uid, final String pwd,
													GetIUVChiamanteEsternoInputVO getIUVChiamanteEsternoInputVO) throws Exception {
    	return invokeRestEasyPost(url, uid, pwd, getIUVChiamanteEsternoInputVO)
    				.readEntity(GetIUVChiamanteEsternoOutputVO.class);
	}
	
	private PagamentoIUVOutputVO pagamentoIUV(String url, String uid, String pwd,
											PagamentoIUVInputVO getIUVChiamanteEsternoInputVO) throws Exception {
		
    	return invokeRestEasyPost(url, uid, pwd, getIUVChiamanteEsternoInputVO)
					.readEntity(PagamentoIUVOutputVO.class);
	}
	
	/*
		{
		    "causale": "Oneri Istruttoria TEST (JH, RDI 02, RC)",
		    "codiceFiscaleEnte": "00514490010",
		    "tipoPagamento": "AT10",
		    "importo": 3.50,
		    "nome": "Mario",
		    "cognome": "Rossi",
		    "ragioneSociale": "",
		    "email": "mario.rossi@consulenti.csi.it",
		    "codiceFiscalePartitaIVAPagatore": "AAABBB71S06B354Z",
		    "identificativoPagamento": "015-09122022",
		    "componentiPagamento": [
		        {
		            "progressivo": 1,
		            "importo": 1.20,
		            "causale": "causale componente di pagamento test per comune 1",
		            "datiSpecificiRiscossione": "0115105AP/2099.9999",
		            "annoAccertamento": "2099",
		            "numeroAccertamento": "9999"
		        },
		        {
		            "progressivo": 2,
		            "importo": 2.30,
		            "causale": "causale componente di pagamento test per comune 1",
		            "datiSpecificiRiscossione": "0115105AP/2099.9999CCCC",
		            "annoAccertamento": "2099",
		            "numeroAccertamento": "9999"
		        }
		    ]
		}
	 */

	private void doPPayTransactionBegin(MudeTIstanza mudeTIstanza, 
										MudeTPpayPagamento mudeTPpayPagamento, 
										PPayResultVO ppayResultVO,
										List<MudeDPPayImporto> importi,
										MudeTUser mudeTUser) {
        MudeTEnte mudeTEnte = mudeTEnteRepository.retrieveMainActive(mudeTIstanza.getComune().getIdComune(), 
        																		 mudeTIstanza.getTipoIstanza().getCodice(),
        																		 mudeTIstanza.getSpeciePratica() == null? "getnulls" : mudeTIstanza.getSpeciePratica().getCodice());
        if(mudeTEnte == null)
			throw new BusinessException("Non è stato trovato un ente di riferimento per il comnue associato a questa istanza."); 
		
        MudeTContatto mudeTContatto = mudeTContattoRepository.findByIdUser(mudeTUser.getIdUser());

    	try {
    		String ppayUID = mudeCProprietaRepository.getValueByName("PPAY_UID", "epayapi_008");    		
    		String ppayPWD = mudeCProprietaRepository.getValueByName("PPAY_PWD", "96wVeDM8");    		
    		
			final String specie_pratica = mudeTIstanza.getSpeciePratica() == null? null : mudeTIstanza.getSpeciePratica().getCodice();
    		GetIUVChiamanteEsternoInputVO getIUVChiamanteEsternoInputVO = new GetIUVChiamanteEsternoInputVO();
    		getIUVChiamanteEsternoInputVO.setCausale(mudeCProprietaEnteRepository.getParamValue("PPAY_CAUSALE", mudeTIstanza.getTipoIstanza().getCodice(), mudeTIstanza.getComune().getIdComune(), "FO", specie_pratica, null));
    		getIUVChiamanteEsternoInputVO.setTipoPagamento(mudeCProprietaEnteRepository.getParamValue("PPAY_COD_VERSAMENTO", mudeTIstanza.getTipoIstanza().getCodice(), mudeTIstanza.getComune().getIdComune(), "FO", specie_pratica, null));
    		getIUVChiamanteEsternoInputVO.setCodiceFiscaleEnte(mudeCProprietaEnteRepository.getParamValue("PPAY_CODICE_FISCALE", mudeTIstanza.getTipoIstanza().getCodice(), mudeTIstanza.getComune().getIdComune(), "FO", specie_pratica, null));
			    
		    if(getIUVChiamanteEsternoInputVO.getCausale() == null)
		    	throw new BusinessException("Parametro PPAY_CAUSALE non configurato per tipo istanza `" + mudeTIstanza.getTipoIstanza().getCodice() + "` e comune `" + mudeTIstanza.getComune().getDenomComune() + "`. Contattare l'amministratore di sistema per risolvere il problema.");
		    if(getIUVChiamanteEsternoInputVO.getTipoPagamento() == null)
		    	throw new BusinessException("Parametro PPAY_COD_VERSAMENTO non configurato per tipo istanza `" + mudeTIstanza.getTipoIstanza().getCodice() + "` e comune `" + mudeTIstanza.getComune().getDenomComune() + "`. Contattare l'amministratore di sistema per risolvere il problema.");
		    if(getIUVChiamanteEsternoInputVO.getCodiceFiscaleEnte() == null)
		    	throw new BusinessException("Parametro PPAY_CODICE_FISCALE non configurato per tipo istanza `" + mudeTIstanza.getTipoIstanza().getCodice() + "` e comune `" + mudeTIstanza.getComune().getDenomComune() + "`. Contattare l'amministratore di sistema per risolvere il problema.");
		    
		    getIUVChiamanteEsternoInputVO.setImporto(ppayResultVO.getTotaleAsDobule());
		    
		    getIUVChiamanteEsternoInputVO.setNome(mudeTContatto.getNome());
		    getIUVChiamanteEsternoInputVO.setCognome(mudeTContatto.getCognome());
		    getIUVChiamanteEsternoInputVO.setRagioneSociale(mudeTContatto.getRagioneSociale());
		    getIUVChiamanteEsternoInputVO.setEmail(mudeTContatto.getMail());
		    getIUVChiamanteEsternoInputVO.setCodiceFiscalePartitaIVAPagatore(mudeTContatto.getCf());
		    getIUVChiamanteEsternoInputVO.setIdentificativoPagamento(mudeTPpayPagamento.getIdentificativoPagamento());

		    for(int i = 0; i < importi.size(); i++) {
		    	MudeDPPayImporto importo = importi.get(i);
		    	
		    	ComponentePagamentoVO componentePagamentoVO = new ComponentePagamentoVO();
		    	
		    	componentePagamentoVO.setProgressivo(i+1);
		    	componentePagamentoVO.setImporto(importo.getImporto().doubleValue());
		    	componentePagamentoVO.setCausale(importo.getCausale());
		    	componentePagamentoVO.setDatiSpecificiRiscossione(importo.getDatiSpecificiRiscossione());
		    	componentePagamentoVO.setAnnoAccertamento(importo.getAnnoAccertamento());
		    	componentePagamentoVO.setNumeroAccertamento(importo.getNumeroAccertamento());
		    	
		    	getIUVChiamanteEsternoInputVO.addComponentePagamento(componentePagamentoVO);
		    }
			GetIUVChiamanteEsternoOutputVO getIUVChiamanteEsternoOutputVO = getIUVChiamanteEsterno(mudeCProprietaRepository.getValueByName("PPAY_URL_GETIUV", "https://tu-exp-pay-sistemapiemonte.bilancio.csi.it/epayapi/api/getIUVChiamanteEsterno"), ppayUID, ppayPWD, getIUVChiamanteEsternoInputVO);
    		
    		mudeTPpayPagamento.setIUV(getIUVChiamanteEsternoOutputVO.getIuv());
    		mudeTPpayPagamento.setCodiceAvviso(getIUVChiamanteEsternoOutputVO.getCodiceAvviso());
    		mudeTPpayPagamento.setCodiceEsito(getIUVChiamanteEsternoOutputVO.getCodiceEsito());
    		mudeTPpayPagamento.setDescrizioneEsito("getIUVChiamanteEsterno: " + getIUVChiamanteEsternoOutputVO.getDescrizioneEsito());

    		if("000".equals(getIUVChiamanteEsternoOutputVO.getCodiceEsito())) {
	    		PagamentoIUVInputVO getIUVChiamanteEsternoInputVO2 = new PagamentoIUVInputVO();
	    		getIUVChiamanteEsternoInputVO2.setIuv(getIUVChiamanteEsternoOutputVO.getIuv());
	    		getIUVChiamanteEsternoInputVO2.setCodiceFiscale(mudeTContatto.getCf());
	    		getIUVChiamanteEsternoInputVO2.setIdentificativoPagamento(mudeTPpayPagamento.getIdentificativoPagamento());
				PagamentoIUVOutputVO pagamentoIUVOutputVO = pagamentoIUV(mudeCProprietaRepository.getValueByName("PPAY_URL_PAGAMENTOIUV", "https://tu-exp-pay-sistemapiemonte.bilancio.csi.it/epayapi/api/pagamentoIUV"), ppayUID, ppayPWD,
						getIUVChiamanteEsternoInputVO2);
	    		
	    		mudeTPpayPagamento.setUrlWisp(pagamentoIUVOutputVO.getUrlWisp());
	    		mudeTPpayPagamento.setCodiceEsito(pagamentoIUVOutputVO.getCodiceEsito());
	    		mudeTPpayPagamento.setDescrizioneEsito("pagamentoIUV: " + pagamentoIUVOutputVO.getDescrizioneEsito());
	    		
	    		if("000".equals(pagamentoIUVOutputVO.getCodiceEsito())) {
	    			ppayResultVO.setUrl(pagamentoIUVOutputVO.getUrlWisp());
	    			return; // all ok!
	    		}
	    		
				LoggerUtil.error(logger, "doPPayTransactionBegin: error calling pagamentoIUV ppay: " + pagamentoIUVOutputVO.getCodiceEsito() + " - " + pagamentoIUVOutputVO.getDescrizioneEsito());
    		}
    		else
				LoggerUtil.error(logger, "doPPayTransactionBegin: error calling getIUVChiamanteEsterno ppay: " + getIUVChiamanteEsternoOutputVO.getCodiceEsito() + " - " + getIUVChiamanteEsternoOutputVO.getDescrizioneEsito());
    			
		} catch (Exception e) {
			LoggerUtil.error(logger, "doPPayTransactionBegin: error calling ppay", e);
		}
    	
		mudeTPpayPagamentoRepository.saveAndFlushDAO(mudeTPpayPagamento);
		throw new BusinessException("Al momento non risultano disponibili i servizi di pagamento esposti da PiemontePay. Si consiglia di riprovare successivamente."); 
	}

}