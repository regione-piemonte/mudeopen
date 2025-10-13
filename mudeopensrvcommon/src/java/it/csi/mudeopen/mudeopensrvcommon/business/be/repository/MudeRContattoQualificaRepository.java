/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRContattoQualifica;

@Repository
public interface MudeRContattoQualificaRepository extends BaseRepository<MudeRContattoQualifica,Long> {

    @Query("SELECT o FROM MudeRContattoQualifica o WHERE o.id = :id")
	Optional<MudeRContattoQualifica> findById(@Param("id") Long id);

	//void deleteAllByMudeTContattoId(Long idContatto);

    @Modifying
	@Query("delete from MudeRContattoQualifica t where t.mudeTContatto.id = ?1")
	void removeAllByIdContatto(Long idContatto);
}