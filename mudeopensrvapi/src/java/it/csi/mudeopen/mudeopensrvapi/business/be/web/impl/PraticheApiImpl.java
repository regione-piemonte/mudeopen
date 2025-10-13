/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.web.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.jboss.resteasy.plugins.providers.multipart.OutputPart;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.ConfigurationHelper;
import it.csi.mudeopen.mudeopensrvapi.business.be.exception.Error;
import it.csi.mudeopen.mudeopensrvapi.business.be.repository.MudeopenDTipoDocpaRepository;
import it.csi.mudeopen.mudeopensrvapi.business.be.repository.MudeopenRComuneFruitoreRepository;
import it.csi.mudeopen.mudeopensrvapi.business.be.web.PraticheApi;
import it.csi.mudeopen.mudeopensrvapi.util.Constants;
import it.csi.mudeopen.mudeopensrvapi.vo.AllegaDocumentoApiVO;
import it.csi.mudeopen.mudeopensrvapi.vo.DocumentoPraticaVO;
import it.csi.mudeopen.mudeopensrvapi.vo.pratiche.DettaglioPraticaVO;
import it.csi.mudeopen.mudeopensrvapi.vo.pratiche.RicercaPraticheFiltroCommon;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoDocPA;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRComuneFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRRuoloFruitore;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTPratica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon.AllegatiApiServiceHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRRuoloFruitoreRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTPraticaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.specification.MudeTPraticaSpecification;
import it.csi.mudeopen.mudeopensrvcommon.util.FilterUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.StatoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.TipoRuoloUtente;
import it.csi.mudeopen.mudeopensrvcommon.vo.documento.DocumentoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.documento.TipoDocumentoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.pratica.PraticaVO;

@Component
public class PraticheApiImpl extends AbstractApiServiceImpl implements PraticheApi {

	private static Logger logger = Logger.getLogger(PraticheApiImpl.class.getCanonicalName());

	@Autowired
	private MudeopenRComuneFruitoreRepository comuneFruitoreRepository;

	@Autowired
	private MudeopenDTipoDocpaRepository mudeopenDTipoDocpaRepository;
	
    @Autowired
    private MudeRRuoloFruitoreRepository mudeRRuoloFruitoreRepository;
	
    @Autowired
    private AllegatiApiServiceHelper allegatiApiServiceHelper;

    @Autowired
    MudeTPraticaRepository mudeTPraticaRepository;

    @Autowired
    private EntityManager entityManager;

	@SuppressWarnings("deprecation")
	@Override
	public Response ricercaPratiche(String XRequestId, String XForwardedFor, String fruitoreID,
										String istatComune, String numeroPratica, String annoPratica) throws Exception {
		try {
    		MudeDFruitore fruitore = verifyFruitore(fruitoreID);

			if (StringUtils.isBlank(istatComune))
				return errorToResponse(new Error(org.springframework.http.HttpStatus.FORBIDDEN, Constants.COMUNE_OBBLIGATORIO));				

			MudeRComuneFruitore comuniFruitoreList = comuneFruitoreRepository.findByComuneIstatComuneAndFruitoreCodiceFruitoreAndDataFineNull(istatComune, fruitoreID);
			if (comuniFruitoreList == null)
				return errorToResponse(new Error(org.springframework.http.HttpStatus.FORBIDDEN, Constants.COMUNE_NON_ABILILITATO_FRUITORE));				

			RicercaPraticheFiltroCommon filtro = new RicercaPraticheFiltroCommon();
			filtro.setAnnoPratica(annoPratica);
			filtro.setCodIstat(istatComune);
			filtro.setNumeroPratica(numeroPratica);
			filtro.setStatiIstanza(Arrays.asList(StatoIstanza.REGISTRATA_DA_PA.getValue()));
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.WRITE_NUMBERS_AS_STRINGS, true);
			String filtroJson = mapper.writeValueAsString(filtro);
			
			Response response = getResponseWithFruitore(
						String.format("/pratiche?page=0&size=9999&filter=%s&scope=ws", encodeFilter(filtroJson)),
						fruitoreID);
			if(HttpStatus.SC_OK == response.getStatus()) {
				List<DettaglioPraticaVO> pratiche = new ArrayList<>();
				String json = response.readEntity(String.class);
		        ObjectMapper objectMapper = new ObjectMapper();
		        List<PraticaVO> praticheVO = objectMapper.readValue(json, new TypeReference<List<PraticaVO>>() {});
		        
		        for (PraticaVO praticaVO : praticheVO) {
					DettaglioPraticaVO pratica = new DettaglioPraticaVO();
					pratiche.add(pratica);
					pratica.setAnno(praticaVO.getAnno() != null ? Integer.valueOf(String.valueOf(praticaVO.getAnno())) : null);
					pratica.setNumeroPratica(praticaVO.getNumeroPratica());
					pratica.setComune(praticaVO.getComune() != null ?praticaVO.getComune().getDescrizione(): null);
					
				}
		        return voToResponse(pratiche, HttpStatus.SC_OK);
				
			} else {
				return response;
			}

		} catch (Throwable t) {
			return errorToResponse(handleUnexpectedError(t));			
		}
	}

	private List<DocumentoPraticaVO> getDocumenti(List<DocumentoVO> list) {
		List<DocumentoPraticaVO> documenti = new ArrayList<>();
		if (list != null) {
			
			list.stream().forEach(a -> {
				TimeZone tz = TimeZone.getTimeZone("UTC");
				DateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_ISO_8601);
				df.setTimeZone(tz);
				String dataCaricamento = df.format(a.getDataCaricamento());
				documenti.add(new DocumentoPraticaVO(a.getTipoDocumento().getCodice(), a.getTipoDocumento().getDescrizione(), a.getNomeFileDocumento(), dataCaricamento, a.getNotificato(), a.getId()));
			});
		}
		return documenti;
	}
	
	
	@Override
	public Response ricercaElencoDocumentiPratica(String XRequestId, String XForwardedFor, String fruitoreID,
														String istatComune, 
														String numeroPratica, 
														String annoPratica) throws Exception {
		try {
    		MudeDFruitore fruitore = verifyFruitore(fruitoreID);

			if (StringUtils.isBlank(istatComune))
				return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[CampoObbligatorioException] Il codice ISTAT del comune e' obbligatorio"));				

			if (StringUtils.isBlank(numeroPratica))
				return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[CampoObbligatorioException] Numero pratica e' obbligatorio"));				

			if (StringUtils.isBlank(annoPratica))
				return errorToResponse(new Error(org.springframework.http.HttpStatus.BAD_REQUEST, "[CampoObbligatorioException] Anno pratica e' obbligatorio"));

			MudeRComuneFruitore comuniFruitoreList = comuneFruitoreRepository.findByComuneIstatComuneAndFruitoreCodiceFruitoreAndDataFineNull(istatComune, fruitoreID);
			if (comuniFruitoreList == null)
				return errorToResponse(new Error(org.springframework.http.HttpStatus.FORBIDDEN, Constants.COMUNE_NON_ABILILITATO_FRUITORE));				
			
			Long idPratica = ricercaPraticaSLIM(fruitoreID
					, istatComune
					, numeroPratica
					, annoPratica
					, Arrays.asList(StatoIstanza.REGISTRATA_DA_PA.getValue()));
			if(idPratica == null)
				return errorToResponse(new Error(org.springframework.http.HttpStatus.FORBIDDEN, "[PraticaEsistenteException_Exception] Pratica non trovata"));
			
			List<DocumentoVO> list = ottieniDocumentiPratica(idPratica, fruitoreID, null);
			List<DocumentoPraticaVO> result = getDocumenti(list);
			return voToResponse(result, HttpStatus.SC_OK);

		} catch (Throwable t) {
			return errorToResponse(handleUnexpectedError(t));			
		}
	}

	private Long ricercaPraticaSLIM(String fruitoreID
										, String istatComune
										, String numeroPratica
										, String annoPratica
										, List<String> statiIstanza) throws UnsupportedEncodingException {
		// old version
		// return getResponseWithFruitore(String.format("/pratiche?page=0&size=1&filter=%s&scope=ws", encodeFilter(filtroJson)), fruitoreID);
		
        Specification<MudeTPratica> filterByComune = MudeTPraticaSpecification.findByIstatComune(istatComune);
        Specification<MudeTPratica> filterByNumeroPratica = MudeTPraticaSpecification.findByNumeroPratica(numeroPratica);
        Specification<MudeTPratica> filterByAnnoPratica = MudeTPraticaSpecification.findByAnnoPratica(annoPratica);
        Specification<MudeTPratica> filterByFruitore = MudeTPraticaSpecification.findByFruitore(fruitoreID);
        @SuppressWarnings("unchecked")
        Specification<MudeTPratica> filterByStatiIstanza = MudeTPraticaSpecification.findByIstanzaConStati(statiIstanza);
        Specification<MudeTPratica> filterByEnte = MudeTPraticaSpecification.addFruitoreEnte(fruitoreID);

    	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<MudeTPratica> root = query.from(MudeTPratica.class);

        CriteriaQuery<Long> cq = query.select(root.get("id")).where(Specifications.where(filterByComune)
				.and(filterByEnte)
        		.and(filterByNumeroPratica)
        		.and(filterByAnnoPratica)
        		.and(filterByFruitore)
        		.and(filterByStatiIstanza).toPredicate(root, query, cb));
        List<Long> idIstanzeList = entityManager.createQuery(cq).getResultList(); // resultList has Entity that only contains id

        return idIstanzeList != null && idIstanzeList.size() > 0? idIstanzeList.get(0) : null; 
	}

	@Override
	public Response allegaDocumentoPratica(String XRequestId, String XForwardedFor, String fruitoreID,
											MultipartFormDataInput input) throws Exception {
		try {
    		MudeDFruitore fruitore = verifyFruitore(fruitoreID);

			File file = input.getFormDataPart("fileDocumento", File.class, null);
            if(file == null)
				return errorToResponse(new Error(org.springframework.http.HttpStatus.FORBIDDEN, "[CampoObbligatorioException] File obbligatorio"));

            String filename = allegatiApiServiceHelper.getFileName(input, "fileDocumento");

			String istatComune = input.getFormDataPart("comune", String.class, null);
			String numeroPratica = input.getFormDataPart("numeroPratica", String.class, null);
			String annoPratica = input.getFormDataPart("anno", String.class, null);
			String tipoDocumento = input.getFormDataPart("tipoDocumento", String.class, null);
			String nomeDocumento = file.getName();

			if(StringUtils.isBlank(istatComune))
				return errorToResponse(new Error(org.springframework.http.HttpStatus.FORBIDDEN, "[CampoObbligatorioException] Comune obbligatorio"));
			if(StringUtils.isBlank(numeroPratica))
				return errorToResponse(new Error(org.springframework.http.HttpStatus.FORBIDDEN, "[CampoObbligatorioException] Numero pratica obbligatorio"));
			if(StringUtils.isBlank(annoPratica))
				return errorToResponse(new Error(org.springframework.http.HttpStatus.FORBIDDEN, "[CampoObbligatorioException] Anno pratica obbligatorio"));
				
			MudeRComuneFruitore comuniFruitoreList = comuneFruitoreRepository.findByComuneIstatComuneAndFruitoreCodiceFruitoreAndDataFineNull(istatComune, fruitoreID);
			if(comuniFruitoreList == null)
				return errorToResponse(new Error(org.springframework.http.HttpStatus.FORBIDDEN, "[ComuneNonAbilitatoException] Comune non abilitato al fruitore"));
			
			Long idPratica = ricercaPraticaSLIM(fruitoreID
													, istatComune
													, numeroPratica
													, annoPratica
													, Arrays.asList(StatoIstanza.REGISTRATA_DA_PA.getValue()));
			if(idPratica == null)
				return errorToResponse(new Error(org.springframework.http.HttpStatus.FORBIDDEN, "[PraticaEsistenteException_Exception] Pratica non trovata"));

            if(StringUtils.isBlank(tipoDocumento))
				return errorToResponse(new Error(org.springframework.http.HttpStatus.FORBIDDEN, "[CampoObbligatorioException] Tipo documento obbligatorio"));

            Optional<MudeDTipoDocPA> opt = mudeopenDTipoDocpaRepository.findByCodeTipoDocpa(tipoDocumento);
            MudeDTipoDocPA mudeDTipoDocpa = opt.isPresent() ? opt.get() : null;
            if(mudeDTipoDocpa == null)
				return errorToResponse(new Error(org.springframework.http.HttpStatus.FORBIDDEN, "[CampoObbligatorioException] Tipo documento obbligatorio"));

            if(StringUtils.isBlank(nomeDocumento))
				return errorToResponse(new Error(org.springframework.http.HttpStatus.FORBIDDEN, "[CampoObbligatorioException] Nome documento obbligatorio"));

            // fine controlli
			MultipartFormDataOutput output = new MultipartFormDataOutput();
			
            DocumentoVO documentoPratica = new DocumentoVO(); 
            TipoDocumentoVO tipoDocumento2 = new TipoDocumentoVO();
            tipoDocumento2.setCodice(mudeDTipoDocpa.getCodeTipoDocpa());
            tipoDocumento2.setDescrizione(mudeDTipoDocpa.getDescTipoDocpa());
			documentoPratica.setTipoDocumento(tipoDocumento2);

            // JIRA 329
			ObjectMapper mapper = new ObjectMapper();
            documentoPratica.setNomeFileDocumento(mudeDTipoDocpa.getCodeTipoDocpa() + "_" + filename);
			output.addFormData("documentoPratica", mapper.writeValueAsString(documentoPratica), MediaType.TEXT_PLAIN_TYPE);
			OutputPart objPart = output.addFormData("file", file, MediaType.APPLICATION_OCTET_STREAM_TYPE);
        	objPart.getHeaders().putSingle("Content-Disposition", "form-data; name=file; filename=" + filename);

			GenericEntity<MultipartFormDataOutput> entity = new GenericEntity<MultipartFormDataOutput>(output) {};
			
			Response response = postResponse("/pratiche/documenti/"+idPratica+"?scope=ws", entity, fruitore.getCodiceFruitore());
			response.bufferEntity();
			String json = response.readEntity(String.class);
			try {
				JSONObject jsonObj = new JSONObject(json);
				if(jsonObj.has("index_node")) {
			        String uuidDocumentoIndex = jsonObj.getString("index_node");
			        String nomeFileDocumento = jsonObj.getString("nome_file_documento");
			        AllegaDocumentoApiVO allegaDocumentoApiVO = new AllegaDocumentoApiVO(uuidDocumentoIndex, nomeFileDocumento);
					return voToResponse(allegaDocumentoApiVO, HttpStatus.SC_OK);
				}
			}
			catch (JSONException je) {
				return errorToResponse(handleUnexpectedError(je));				
			}
			
			return errorToResponse(handleUnexpectedMessage(json));
		} catch (Throwable t) {
			return errorToResponse(handleUnexpectedError(t));			
		}
	}

	
	@Override
	public Response eliminaDocumentoPratica(String XRequestId, String XForwardedFor, String fruitoreID,
			String istatComune, 
			String numeroPratica, 
			String annoPratica,
			String nomeDocumento) throws Exception {

		Boolean esito = false;

		try {
    		MudeDFruitore fruitore = verifyFruitore(fruitoreID);

			if(StringUtils.isBlank(istatComune))
				return errorToResponse(new Error(org.springframework.http.HttpStatus.FORBIDDEN, "[CampoObbligatorioException] Comune obbligatorio"));
			if(StringUtils.isBlank(numeroPratica))
				return errorToResponse(new Error(org.springframework.http.HttpStatus.FORBIDDEN, "[CampoObbligatorioException] Numero pratica obbligatorio"));
			if(StringUtils.isBlank(annoPratica))
				return errorToResponse(new Error(org.springframework.http.HttpStatus.FORBIDDEN, "[CampoObbligatorioException] Anno pratica obbligatorio"));
    		
			// it is required to have main OG role
			MudeRRuoloFruitore mudeRRuoloFruitore = mudeRRuoloFruitoreRepository.findByIdFruitoreNoType(fruitore.getIdFruitore());
			if(mudeRRuoloFruitore == null || mudeRRuoloFruitore.getMudeDRuoloUtente().getCodice().equals(TipoRuoloUtente.UTENTE_LETTORE_OPERAZIONI.getValue()))
				return errorToResponse(new Error(org.springframework.http.HttpStatus.FORBIDDEN, "[FruitoreNonAbilitatoOperazioneException] OP_modificaStatoIstanza"));	
			
			MudeRComuneFruitore comuneFruitore = comuneFruitoreRepository.findByComuneIstatComuneAndFruitoreCodiceFruitoreAndDataFineNull(istatComune, fruitoreID);
			if(comuneFruitore == null)
				return errorToResponse(new Error(org.springframework.http.HttpStatus.FORBIDDEN, Constants.COMUNE_NON_ABILILITATO_FRUITORE));
			
			Long idPratica = ricercaPraticaSLIM(fruitoreID
					, istatComune
					, numeroPratica
					, annoPratica
					, Arrays.asList(StatoIstanza.REGISTRATA_DA_PA.getValue()));
			if(idPratica == null)
				return errorToResponse(new Error(org.springframework.http.HttpStatus.FORBIDDEN, "[PraticaEsistenteException_Exception] Pratica non trovata"));

			List<DocumentoVO> list = ottieniDocumentiPratica(idPratica, fruitoreID, "essential");
			DocumentoVO documento = null;
			try {
				documento = list.stream().filter(d -> StringUtils.equalsIgnoreCase(d.getNomeFileDocumento(), nomeDocumento)).findAny().orElseGet(null);
	        } catch (NullPointerException skip) { }			
			
			Long idDocumento = documento != null ? documento.getId() : null;
			if (idDocumento == null) {
				Error error = new Error(org.springframework.http.HttpStatus.NOT_FOUND, "[AllegatoNonTrovatoException] COMUNE: " + istatComune + " NUMERO PRATICA:" + numeroPratica + " ANNO:" + annoPratica + " NOME DOCUMENTO:" + nomeDocumento);
				return voToResponse(error, error.getHttpStatus().value());
			}
			// fine controlli
			
			Client client = ClientBuilder.newClient();
			client.register(new AddDefaultHeadersRequestFilter(fruitoreID));
			WebTarget target = client.target(configurationHelper.getEndpointBase()+"/pratiche/documenti/"+idDocumento+"?scope=ws");
			ResteasyWebTarget rtarget = (ResteasyWebTarget) target;
			Response response = rtarget.request().header(Constants.HEADER_USER_CF, fruitoreID).delete();
			//response.bufferEntity();
			String json = response.readEntity(String.class);
			logger.debug("[PraticheApiImpl::deleteDocumento] response common: " + json);
			if(HttpStatus.SC_OK != response.getStatus())
				return errorToResponse(handleUnexpectedMessage(json));
				
			esito = true;
		} catch (Throwable t) {
			logger.error( "[PraticheApiImpl::deleteDocumento] EXCEPTION : " + t.getMessage(), t);
		}
		return voToResponse(esito, HttpStatus.SC_OK);
	}
	

	@Override
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response estraiDocumentoPratica(String XRequestId, String XForwardedFor, String fruitoreID,
			String istatComune, 
			String numeroPratica, 
			String annoPratica,
			String nomeDocumento) throws Exception {

		try {
    		MudeDFruitore fruitore = verifyFruitore(fruitoreID);

			if(StringUtils.isBlank(istatComune))
				return errorToResponse(new Error(org.springframework.http.HttpStatus.FORBIDDEN, "[CampoObbligatorioException] Comune obbligatorio"));
			if(StringUtils.isBlank(numeroPratica))
				return errorToResponse(new Error(org.springframework.http.HttpStatus.FORBIDDEN, "[CampoObbligatorioException] Numero pratica obbligatorio"));
			if(StringUtils.isBlank(annoPratica))
				return errorToResponse(new Error(org.springframework.http.HttpStatus.FORBIDDEN, "[CampoObbligatorioException] Anno pratica obbligatorio"));
			
			MudeRComuneFruitore comuneFruitore = comuneFruitoreRepository.findByComuneIstatComuneAndFruitoreCodiceFruitoreAndDataFineNull(istatComune, fruitoreID);
			if (comuneFruitore == null)
				return errorToResponse(new Error(org.springframework.http.HttpStatus.FORBIDDEN, Constants.COMUNE_NON_ABILILITATO_FRUITORE));
				
			Long idPratica = ricercaPraticaSLIM(fruitoreID
					, istatComune
					, numeroPratica
					, annoPratica
					, Arrays.asList(StatoIstanza.REGISTRATA_DA_PA.getValue()));
			if(idPratica == null)
				return errorToResponse(new Error(org.springframework.http.HttpStatus.FORBIDDEN, "[PraticaEsistenteException_Exception] Pratica non trovata"));

			List<DocumentoVO> list = ottieniDocumentiPratica(idPratica,fruitoreID, "essential");
			
			DocumentoVO documento = list.stream().filter(d -> StringUtils.equalsIgnoreCase(d.getNomeFileDocumento(), nomeDocumento)).findFirst().orElse(null);
			String uuid = documento != null ? documento.getFileUID() : null;
			if (uuid == null)
				return errorToResponse(new Error(org.springframework.http.HttpStatus.NOT_FOUND, "[AllegatoNonTrovatoException] COMUNE: " + istatComune + " NUMERO PRATICA:" + numeroPratica + " ANNO:" + annoPratica + " NOME DOCUMENTO:" + nomeDocumento));

			// fine controlli
			Response response = getResponse("/pratiche/documenti/download/"+uuid, fruitoreID);
			return response;
		} catch (Throwable t) {
			return errorToResponse(handleUnexpectedError(t));
		}
	}
}
