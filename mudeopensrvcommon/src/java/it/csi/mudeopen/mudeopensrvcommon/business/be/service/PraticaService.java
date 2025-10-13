/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service;

import javax.ws.rs.core.Response;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTPratica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.vo.documento.DocumentoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;

public interface PraticaService {
	
    void savePraticaForIstanza(MudeTIstanza mudeTIstanza, IstanzaVO istanzaVO, MudeTUser mudeTuser);

    Response recuperaDocumenti(String numPratica, int page, int size);

    Response recuperaDocumenti(Long idPratica, int page, int size);

    DocumentoVO findByFileUID(String fileUID);

    DocumentoVO loadDocumento(Long idDocumento);

    void deleteDocumento(Long idDocumento);

    MudeTPratica findById(Long idPratica);

    void updateUuidIndex(MudeTPratica mudeTPratica, MudeTUser mudeTUser);

    DocumentoVO saveDocumento(DocumentoVO vo, MudeTUser mudeTUser);

    Response cercaPratiche(String filter, MudeTUser mudeTUser, String scope, int page, int size);

    byte[] exportExcelPraticheUtente(MudeTUser mudeTUser, Long idPratica, Long anno, String numPratica, Long idComune, String scope);

    Response cercaPraticheWs(String codiceFruitore,String filter, int page, int size);

}