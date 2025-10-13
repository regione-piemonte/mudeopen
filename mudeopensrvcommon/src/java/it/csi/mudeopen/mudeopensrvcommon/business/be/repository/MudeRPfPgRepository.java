/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRPfPg;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
public interface MudeRPfPgRepository extends BaseRepository<MudeRPfPg, Long> {

    @Query("select o from MudeRPfPg o where o.soggettoPg.id = :idPG")
    Optional<MudeRPfPg> retrieveLawPresenter(@Param("idPG") Long idPG);

    @Query("select o from MudeRPfPg o where o.soggettoPf.id = :idPF and o.soggettoPg.id = :idPG")
    Optional<MudeRPfPg> findByPFAndPG(@Param("idPF") Long idPF, @Param("idPG") Long idPG);

    @Modifying(clearAutomatically = true)
    @Query(value = "delete from mudeopen_r_pf_pg where id_soggetto_pg = :idPG", nativeQuery = true)
    @Transactional(propagation = Propagation.REQUIRED)
    Integer deleteByPG(@Param("idPG") Long idPG);

    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO mudeopen_r_pf_pg (id_soggetto_pf, id_soggetto_pg, id_titolo) VALUES (:idPF, :idPG, :idTitolo)", nativeQuery = true)
    @Transactional(propagation = Propagation.REQUIRED)
    void savePfPg(@Param("idPF") Long idPF, @Param("idPG") Long idPG, @Param("idTitolo") String idTitolo);

    @Query(value = "SELECT MAX(id_pf_pg) IS NOT NULL FROM mudeopen_r_pf_pg WHERE id_soggetto_pf = :idContattoPF", nativeQuery = true)
    boolean isContactLinkedToOtherContact(@Param("idContattoPF") Long idContattoPF);

}