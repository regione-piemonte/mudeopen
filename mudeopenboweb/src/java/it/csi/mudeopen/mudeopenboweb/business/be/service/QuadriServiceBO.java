/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopenboweb.business.be.service;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import javax.ws.rs.core.Response;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.vo.allegato.TipoAllegatoExtendedVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.CategoriaQuadroVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.QuadroVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.TemplateQuadroVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.TemplateVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.quadri.TipoQuadroVO;

/**
 * The interface quadri service bo .
 */
public interface QuadriServiceBO {

    /* ----------------------------------------------------------------------- */
    /* --------------------------  GESTIONE TIPI QUADRI   -------------------- */
    /* ----------------------------------------------------------------------- */
	
    /**
     * Find quadri by param response.
     *
     * @param mudeTUser the mude t user
     * @param page      the page
     * @param size      the size
     * @param sortExp   the sort exp
     * @return the response
     */
	public Response findTipoQuadriByParam(MudeTUser mudeTUser, String code, String descr, String active, Long idCategoriaQuadro, int page, int size, String sortExp);

    public TipoQuadroVO saveTipoQuadro(TipoQuadroVO tipoQuadroVO, MudeTUser mudeTUser);
	
    public void eliminaTipoQuadro(Long idTipoQuadro, MudeTUser mudeTUser);

    public List<QuadroVO> listaQuadriPerTipo(MudeTUser mudeTUser, Long idTipoQuadro);

    public List<CategoriaQuadroVO> listaCategoriaTipoQuadri(MudeTUser mudeTUser);

    /* ----------------------------------------------------------------------- */
    /* --------------------------  GESTIONE QUADRI   ------------------------- */
    /* ----------------------------------------------------------------------- */
	
	public Response listaQuadri(MudeTUser mudeTUser, String filters, String tipoQuadro, String tipoGestione, Long versione, Long stato, int page, int size, String sortExp);

    public QuadroVO saveQuadro(QuadroVO QuadroVO, File docx, String filename, MudeTUser mudeTUser) throws Exception;
	
    public void eliminaQuadro(Long idQuadro, MudeTUser mudeTUser);

    public Response recuperaTemplateAssociaQuadro(Long idQuadro, MudeTUser mudeTUser);

    public void pubblicaQuadro(Long idQuadro, MudeTUser mudeTUser);

    /* ----------------------------------------------------------------------- */
    /* --------------------------  GESTIONE TEMPLATE   ------------------------- */
    /* ----------------------------------------------------------------------- */
	
    public Response findTemplateByParam(MudeTUser mudeTUser,
    		String sTipologiaistanza,
    		String sCodice,
    		String sDescrizione,
    		Long iVersione,
    		Long iStato,
    		LocalDate sDataInizioValidita,
    		LocalDate sDataCessazione,
    		int page, int size, String sortExp);

    public TemplateVO saveTemplate(TemplateVO tipoQuadroVO, MudeTUser mudeTUser);
	
    public void eliminaTemplate(Long idTemplate, MudeTUser mudeTUser);

	public Response listaQuadriTemplate(Long idTemplate, MudeTUser mudeTUser);
	
	public Response listaAllegatiTemplate(Long idTemplate, MudeTUser mudeTUser);

    public TemplateQuadroVO salvaQuadroTemplate(TemplateQuadroVO templateQuadroVO, MudeTUser mudeTUser);

    public TipoAllegatoExtendedVO salvaAllegatoTemplate(TipoAllegatoExtendedVO tipoAllegatoExtendedVO, File docx, String filename, MudeTUser mudeTUser) throws Exception;

    public TemplateVO salvaIntestazioneTemplate(TemplateVO templateVO, File docx, String filename, MudeTUser mudeTUser, String type) throws Exception;

	public void eliminaQuadroTemplate(Long idTemplateQuadro, MudeTUser mudeTUser);
	
	public void eliminaAllegatoTemplate(Long id, MudeTUser mudeTUser);

    public TemplateVO nuovaVersioneTemplate(Long idTemplate, MudeTUser mudeTUser);

    public TemplateVO duplicaTemplate(Long idTemplate, String idTipoIstanza, MudeTUser mudeTUser);

    public TemplateVO pubblicaTemplate(Long idTemplate, String publishingMode, MudeTUser mudeTUser);

    public TemplateVO downloadModelloDocxTemplate(Long idTemplate, MudeTUser mudeTUser);

	public Response listaEnti();

    public String doFunction(String func);
}