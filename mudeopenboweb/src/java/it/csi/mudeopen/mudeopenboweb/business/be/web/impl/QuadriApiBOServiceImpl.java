/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package  it.csi.mudeopen.mudeopenboweb.business.be.web.impl;

import java.io.File;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.mudeopen.mudeopenboweb.business.be.service.QuadriServiceBO;
import it.csi.mudeopen.mudeopenboweb.business.be.web.QuadriApiBO;
import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon.AllegatiApiServiceHelper;
import it.csi.mudeopen.mudeopensrvcommon.util.AbstractApi;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.FilterUtil;
import it.csi.mudeopen.mudeopensrvcommon.vo.allegato.TipoAllegatoExtendedVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.QuadroVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.TemplateQuadroVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.TemplateVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.TipoQuadroVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.response.ErrorResponse;
import org.apache.commons.lang.StringUtils;

/**
 * The type Contatti api service.
 */
@Component
public class QuadriApiBOServiceImpl extends AbstractApi implements QuadriApiBO {

	private static final String IMPOSSIBILE_SALVARE_IL_QUADRO = "impossibile salvare il quadro";

	private static final String ERRORE_INTERNO = "errore_interno";

	@Autowired
	QuadriServiceBO quadriServiceBO;

    @Autowired
    private AllegatiApiServiceHelper allegatiApiServiceHelper;
	
    /* ----------------------------------------------------------------------- */
    /* --------------------------  GESTIONE TIPI QUADRI   -------------------- */
    /* ----------------------------------------------------------------------- */
	
	/**
	 * Ricerca tipo quadro response.
	 *
	 * @param userCf          the user cf
	 * @param XRequestId      the x request id
	 * @param XForwardedFor   the x forwarded for
	 * @param securityContext the security context
	 * @param httpHeaders     the http headers
	 * @param httpRequest     the http request
	 * @param filter          the filter
	 * @param sort            the sort
	 * @param page            the page
	 * @param size            the size
	 * @return the response
	 */
	@Override
	public Response listaTipoQuadri(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
			String filter, String sort, int page, int size) {
		String code = StringUtils.trim(FilterUtil.getStringValue(filter, "code"));
		String descr = StringUtils.trim(FilterUtil.getStringValue(filter, "descr"));
		String active = StringUtils.trim(FilterUtil.getStringValue(filter, "attivo"));
		Long idCategoriaQuadro = FilterUtil.getLongValue(filter, "idCategoriaQuadro");
		
		return quadriServiceBO.findTipoQuadriByParam(headerUtil.getUserCF(httpHeaders, false), code, descr, active, idCategoriaQuadro, page, size, sort);
	}

	/**
	 * Save tipo quadro response.
	 *
	 * @param userCf          the user cf
	 * @param XRequestId      the x request id
	 * @param XForwardedFor   the x forwarded for
	 * @param securityContext the security context
	 * @param httpHeaders     the http headers
	 * @param httpRequest     the http request
	 * @param contatto        the contatto
	 * @return the response
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Response salvaTipoQuadro(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
			TipoQuadroVO tipoQuadroVO) {
		return voToResponse(quadriServiceBO.saveTipoQuadro(tipoQuadroVO, headerUtil.getUserCF(httpHeaders, false)), 200);
	}

	/**
	 * Elimina tipo quadri response.
	 *
	 * @param userCf          the user cf
	 * @param XRequestId      the x request id
	 * @param XForwardedFor   the x forwarded for
	 * @param securityContext the security context
	 * @param httpHeaders     the http headers
	 * @param httpRequest     the http request
	 * @param tipoQuadro	  tipo quadro
	 * @return the response
	 */
	@Override
	public Response eliminaTipoQuadro(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
			Long idTipoQuadro) {
		

		quadriServiceBO.eliminaTipoQuadro(idTipoQuadro, headerUtil.getUserCF(httpHeaders, false));
		return Response.ok().build();
	}

	/**
	 * recupera quadri a partire dal tipo quadro response.
	 *
	 * @param userCf          the user cf
	 * @param XRequestId      the x request id
	 * @param XForwardedFor   the x forwarded for
	 * @param securityContext the security context
	 * @param httpHeaders     the http headers
	 * @param httpRequest     the http request
	 * @param idTipoQuadro	  id tipo quadro
	 * @return the response
	 */
	@Override
	public Response listaQuadriPerTipo(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
			Long idTipoQuadro) {
		return voToResponse(quadriServiceBO.listaQuadriPerTipo(headerUtil.getUserCF(httpHeaders, false), idTipoQuadro), 200);
	}
	
	@Override
	public Response listaCategoriaTipoQuadri(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		return voToResponse(quadriServiceBO.listaCategoriaTipoQuadri(headerUtil.getUserCF(httpHeaders, false)), 200);
	}

	

    /* ----------------------------------------------------------------------- */
    /* --------------------------  GESTIONE QUADRI   ------------------------- */
    /* ----------------------------------------------------------------------- */
	

	
	/**
	 * Ricerca tipo quadro response.
	 *
	 * @param userCf          the user cf
	 * @param XRequestId      the x request id
	 * @param XForwardedFor   the x forwarded for
	 * @param securityContext the security context
	 * @param httpHeaders     the http headers
	 * @param httpRequest     the http request
	 * @param filters          the filter
	 * @param sort            the sort
	 * @param page            the page
	 * @param size            the size
	 * @return the response
	 */
	@Override
	public Response listaQuadri(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
									String filters, 
									String sort, 
									int page, 
									int size,
									String func) {
		if(func != null && !"prod".equals(Constants._environment))
			return Response.ok(quadriServiceBO.doFunction(func)).build();
		
		String tipoQuadro = StringUtils.trim(FilterUtil.getStringValue(filters, "tipoQuadro"));
		String tipoGestione = StringUtils.trim(FilterUtil.getStringValue(filters, "tipoGestione"));
		Long versione = FilterUtil.getLongValue(filters, "versione");
		String sStato = StringUtils.trim(FilterUtil.getStringValue(filters, "stato"));
		
		if(tipoGestione != null)
			if("complesso".startsWith(tipoGestione.toLowerCase()))
					tipoGestione = "C";
			else if("formio".startsWith(tipoGestione.toLowerCase()))
					tipoGestione = "F";
			else if("reactive".startsWith(tipoGestione.toLowerCase()))
					tipoGestione = "R";
		
		Long iStato = null;
		if(sStato != null)
			iStato = sStato.equals("1") || sStato.equalsIgnoreCase("si") || sStato.equalsIgnoreCase("sì") ? 1L : 0L;
		
		return quadriServiceBO.listaQuadri(headerUtil.getUserCF(httpHeaders, false), filters, tipoQuadro, tipoGestione, versione, iStato, page, size, sort);
	}
	
	/**
	 * Save quadro response.
	 *
	 * @param userCf          the user cf
	 * @param XRequestId      the x request id
	 * @param XForwardedFor   the x forwarded for
	 * @param securityContext the security context
	 * @param httpHeaders     the http headers
	 * @param httpRequest     the http request
	 * @param contatto        the contatto
	 * @return the response
	 */
	@Override
	public Response salvaQuadro(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
			MultipartFormDataInput input) {
		

		try {
	        String quadroVoJson = getJson(input, "quadroVO");
	        File file = input.getFormDataPart("file", File.class, null);
	        String filename = allegatiApiServiceHelper.getFileName(input, "file");

	        ObjectMapper mapper = new ObjectMapper();
	        QuadroVO vo = quadriServiceBO.saveQuadro(mapper.readValue(quadroVoJson, QuadroVO.class), file, filename, headerUtil.getUserCF(httpHeaders, false));

			return voToResponse(vo, 200);
		} catch(BusinessException be) {
			throw be;
		} catch(Exception e) {

			
            ErrorResponse error = new ErrorResponse("500", ERRORE_INTERNO, IMPOSSIBILE_SALVARE_IL_QUADRO, null, null);
            return Response.serverError().entity(error).status(403).header(HttpHeaders.CONTENT_LENGTH, getContentLen(error)).build();
        }
	}

	/**
	 * Elimina  quadri response.
	 *
	 * @param userCf          the user cf
	 * @param XRequestId      the x request id
	 * @param XForwardedFor   the x forwarded for
	 * @param securityContext the security context
	 * @param httpHeaders     the http headers
	 * @param httpRequest     the http request
	 * @param Quadro	   quadro
	 * @return the response
	 */
	@Override
	public Response eliminaQuadro(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
			Long idQuadro) {
		quadriServiceBO.eliminaQuadro(idQuadro, headerUtil.getUserCF(httpHeaders, false));
		return Response.ok().build();
	}
	
	public Response recuperaTemplateAssociaQuadro(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, 
			Long idTipoQuadro) {
		return quadriServiceBO.recuperaTemplateAssociaQuadro(idTipoQuadro, headerUtil.getUserCF(httpHeaders, false));
	}

	@Override
	public Response pubblicaQuadro(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, 
			Long idTipoQuadro) {
		quadriServiceBO.pubblicaQuadro(idTipoQuadro, headerUtil.getUserCF(httpHeaders, false));
		return Response.ok().build();
	}

	
    /* ----------------------------------------------------------------------- */
    /* --------------------------  GESTIONE TEMPLATE   ------------------------- */
    /* ----------------------------------------------------------------------- */
	
	/**
	 * Ricerca template response.
	 *
	 * @param userCf          the user cf
	 * @param XRequestId      the x request id
	 * @param XForwardedFor   the x forwarded for
	 * @param securityContext the security context
	 * @param httpHeaders     the http headers
	 * @param httpRequest     the http request
	 * @param filter          the filter
	 * @param sort            the sort
	 * @param page            the page
	 * @param size            the size
	 * @return the response
	 */
	@Override
	public Response listaTemplate(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
									String filter, 
									String sort, 
									int page, 
									int size) {
		String sTipologiaistanza = StringUtils.trim(FilterUtil.getStringValue(filter, "tipologiaIstanza"));
		String sCodice = StringUtils.trim(FilterUtil.getStringValue(filter, "codice"));
		String sDescrizione = StringUtils.trim(FilterUtil.getStringValue(filter, "descrizione"));
		Long iVersione = FilterUtil.getLongValue(filter, "versione");
		String sStato = StringUtils.trim(FilterUtil.getStringValue(filter, "stato"));
		LocalDate dDataInizioValidita = FilterUtil.getDateFromValue(filter, "dataInizioValidita");
		LocalDate dDataCessazione = FilterUtil.getDateToValue(filter, "dataCessazione");
		
		Long iStato = null;
		if(sStato != null && !sStato.equals(""))
			iStato = sStato.equals("1") || sStato.equalsIgnoreCase("si") || sStato.equalsIgnoreCase("sì") ? 1L : 0L;
		
		return quadriServiceBO.findTemplateByParam(headerUtil.getUserCF(httpHeaders, false)
				, sTipologiaistanza
				, sCodice
				, sDescrizione
				, iVersione
				, iStato
				, dDataInizioValidita
				, dDataCessazione
				, page, size, sort);
	}

	/**
	 * Save template response.
	 *
	 * @param userCf          the user cf
	 * @param XRequestId      the x request id
	 * @param XForwardedFor   the x forwarded for
	 * @param securityContext the security context
	 * @param httpHeaders     the http headers
	 * @param httpRequest     the http request
	 * @param contatto        the contatto
	 * @return the response
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Response salvaTemplate(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
			TemplateVO templateVO) {
		return voToResponse(quadriServiceBO.saveTemplate(templateVO, headerUtil.getUserCF(httpHeaders, false)), 200);
	}
	/**
	 * Elimina  quadri response.
	 *
	 * @param userCf          the user cf
	 * @param XRequestId      the x request id
	 * @param XForwardedFor   the x forwarded for
	 * @param securityContext the security context
	 * @param httpHeaders     the http headers
	 * @param httpRequest     the http request
	 * @param Quadro	   quadro
	 * @return the response
	 */
	@Override
	public Response eliminaTemplate(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
			Long idTemplate) {
		quadriServiceBO.eliminaTemplate(idTemplate, headerUtil.getUserCF(httpHeaders, false));
		return Response.ok().build();
	}

    @Override
	public Response listaQuadriTemplate(String userCf, String XRequestId, String XForwardedFor,SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
			Long idTemplate) {
		return quadriServiceBO.listaQuadriTemplate(idTemplate, headerUtil.getUserCF(httpHeaders, false));
    }
	
    @Override
	public Response listaAllegatiTemplate(String userCf, String XRequestId, String XForwardedFor,SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
			Long idTemplate) {
		return quadriServiceBO.listaAllegatiTemplate(idTemplate, headerUtil.getUserCF(httpHeaders, false));
    }
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Response salvaQuadroTemplate(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
			Long idTemplate, TemplateQuadroVO templateQuadroVO) {
		return voToResponse(quadriServiceBO.salvaQuadroTemplate(templateQuadroVO, headerUtil.getUserCF(httpHeaders, false)), 200);
	}

	@Override
	public Response salvaAllegatoTemplate(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
			Long idTemplate, MultipartFormDataInput input) {
		try {
	        String tipoAllegatoVoJson = getJson(input, "tipoAllegatoExtendedVO");
	        File file = input.getFormDataPart("file", File.class, null);
	        String filename = allegatiApiServiceHelper.getFileName(input, "file");

	        ObjectMapper mapper = new ObjectMapper();
	        TipoAllegatoExtendedVO vo = quadriServiceBO.salvaAllegatoTemplate(mapper.readValue(tipoAllegatoVoJson, TipoAllegatoExtendedVO.class), file, filename, headerUtil.getUserCF(httpHeaders, false));
			
    		return voToResponse(vo, 200);
		} catch(BusinessException be) {
			throw be;
		} catch (Exception e) {

			
            ErrorResponse error = new ErrorResponse("500", ERRORE_INTERNO, IMPOSSIBILE_SALVARE_IL_QUADRO, null, null);
            return Response.serverError().entity(error).status(403).header(HttpHeaders.CONTENT_LENGTH, getContentLen(error)).build();
        }
	}

	@Override
	public Response salvaIntestazioneTemplate(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
			Long idTemplate, 
			String type, 
			MultipartFormDataInput input) {
		try {
	        String templateVoJson = getJson(input, "templateVO");
	        File file = input.getFormDataPart("file", File.class, null);
	        String filename = allegatiApiServiceHelper.getFileName(input, "file");

	        ObjectMapper mapper = new ObjectMapper();
	        TemplateVO vo = quadriServiceBO.salvaIntestazioneTemplate(mapper.readValue(templateVoJson, TemplateVO.class), file, filename, headerUtil.getUserCF(httpHeaders, false), type);
			
    		return voToResponse(vo, 200);
		} catch(BusinessException be) {
			throw be;
		} catch (Exception e) {
            ErrorResponse error = new ErrorResponse("500", ERRORE_INTERNO, "impossibile salvare l'intestazione", null, null);
            return Response.serverError().entity(error).status(403).header(HttpHeaders.CONTENT_LENGTH, getContentLen(error)).build();
        }
	}

	@Override
	public Response eliminaQuadroTemplate(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest,
												Long idTemplateQuadro) {
		quadriServiceBO.eliminaQuadroTemplate(idTemplateQuadro, headerUtil.getUserCF(httpHeaders, false));
		return Response.ok().build();
	}

	@Override
	public Response eliminaAllegatoTemplate(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, 
												Long id) {
		quadriServiceBO.eliminaAllegatoTemplate(id, headerUtil.getUserCF(httpHeaders, false));
		return Response.ok().build();
	}

	@Override
	public Response nuovaVersioneTemplate(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, 
												Long idTemplate) {
		quadriServiceBO.nuovaVersioneTemplate(idTemplate, headerUtil.getUserCF(httpHeaders, false));
		return Response.ok().build();
	}

	@Override
	public Response duplicaTemplate(String userCf, String XRequestId, String XForwardedFor,SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, 
												Long idTemplate, String idTipoIstanza) {
		quadriServiceBO.duplicaTemplate(idTemplate, idTipoIstanza, headerUtil.getUserCF(httpHeaders, false));
		return Response.ok().build();
	}

	@Override
	public Response pubblicaTemplate(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, 
												Long idTemplate,
												String publishingMode) {
		quadriServiceBO.pubblicaTemplate(idTemplate, publishingMode, headerUtil.getUserCF(httpHeaders, false));
		return Response.ok().build();
	}

	@Override
	public Response downloadModelloDocxTemplate(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest, 
			Long idTemplate) {
		
		TemplateVO vo = quadriServiceBO.downloadModelloDocxTemplate(idTemplate, headerUtil.getUserCF(httpHeaders, false));
		
		byte[] bytes = vo.getModello().getFileContent();
		
        String outputFilename = vo.getModello().getPathModello();
        return Response.ok(bytes, MediaType.APPLICATION_OCTET_STREAM).header("Content-Disposition", "attachment; filename=\"" + outputFilename + "\"").header(HttpHeaders.CONTENT_LENGTH, bytes.length).build();
	}
	
	private String getJson(MultipartFormDataInput input, String name) throws Exception {
        InputPart jsonPart = input.getFormDataMap().get(name).get(0);
        jsonPart.setMediaType(MediaType.APPLICATION_JSON_TYPE);

        return jsonPart.getBody(String.class, null);
	}

	public Response listaEnti(String userCf, String XRequestId, String XForwardedFor, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
		return quadriServiceBO.listaEnti();
	}
	

}