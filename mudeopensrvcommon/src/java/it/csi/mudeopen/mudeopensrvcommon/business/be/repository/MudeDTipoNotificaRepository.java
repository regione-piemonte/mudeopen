/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoNotifica;

@Repository
public interface MudeDTipoNotificaRepository extends BaseRepository<MudeDTipoNotifica, Long> {

    MudeDTipoNotifica findByIdTipoNotifica(Long id);

    @Query(value = "select tn.* from mudeopen_d_tipo_notifica tn "
			+ " where tn.data_inizio <= NOW() and (tn.data_fine is null or tn.data_fine >= NOW()) "
			+ " and tn.cod_tipo_notifica = :codTipoNotifica", nativeQuery = true)
    List<MudeDTipoNotifica> findByCodTipoNotificaAndValid(@Param("codTipoNotifica")String codTipoNotifica );

    @Query(value = "select tn.* from mudeopen_d_tipo_notifica tn "
			+ " where tn.data_inizio <= NOW() and (tn.data_fine is null or tn.data_fine >= NOW()) "
			+ " and tn.cod_tipo_notifica = :codTipoNotifica "
			+ " LIMIT 1", nativeQuery = true)
    MudeDTipoNotifica findOneByCodTipoNotificaAndValid(@Param("codTipoNotifica")String codTipoNotifica );

    //@Query("SELECT o.* FROM MudeDTipoNotifica o WHERE o.desTipoNotifica = ?1")

    /*@Query(value = "SELECT dtn.* "
    		+ "  FROM mudeopen_d_tipo_notifica dtn"
    		+ "  WHERE dtn.des_tipo_notifica like concat('%', :desTipoNotifica, '%'))", nativeQuery = true)*/
    List<MudeDTipoNotifica> findByDesTipoNotifica(String desTipoNotifica);

	@Query("select tn from MudeDTipoNotifica tn "
			+ "where tn.dataInizio <= NOW() and (tn.dataFine is null or tn.dataFine >= NOW())")
	List<MudeDTipoNotifica> findAllValid();

	@Query("select tn from MudeDTipoNotifica tn "
			+ " where tn.subCodTipoNotifica = :subcodTipoNotifica "
			+ " and tn.dataInizio <= NOW() and (tn.dataFine is null or tn.dataFine >= NOW())")
	List<MudeDTipoNotifica> findBySubCodTipoNotifica(@Param("subcodTipoNotifica") String subcodTipoNotifica);
	
}