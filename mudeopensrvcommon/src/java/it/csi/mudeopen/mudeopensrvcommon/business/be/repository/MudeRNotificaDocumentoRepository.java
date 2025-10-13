/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRNotificaDocumento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRNotificaUtente;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTDocumento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTNotifica;

@Repository
public interface MudeRNotificaDocumentoRepository extends BaseRepository<MudeRNotificaDocumento, Long> {
	
	List<MudeRNotificaDocumento> findByMudeTNotifica(MudeTNotifica mudeTNotifica);

    List<MudeRNotificaDocumento> findByMudeTDocumento(MudeTDocumento mudeTDocumento);

    @Query(value = "SELECT COUNT(id_notifica_documento) > 0 FROM mudeopen_r_notifica_documento mrnd  WHERE id_notifica = :idNotifica", nativeQuery = true)
	boolean areThereDocsForNotifica(@Param("idNotifica") Long idNotifica);

    @Query(value = "SELECT COUNT(id_notifica_documento) > 0 FROM mudeopen_r_notifica_documento where id_documento =:idDocumento", nativeQuery = true)
	boolean hasDocumentNotified(@Param("idDocumento") Long idDocumento);

}