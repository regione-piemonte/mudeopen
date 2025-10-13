/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaUtenteQuadro;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MudeRIstanzaUtenteQuadroRepository extends BaseRepository<MudeRIstanzaUtenteQuadro, Long> {

    List<MudeRIstanzaUtenteQuadro> findAllByMudeRIstanzaUtente_Istanza_IdAndMudeRIstanzaUtente_Utente_IdUserAndDataFineIsNull(Long idIstanza, Long idUser);

    @Modifying
	@Query("UPDATE MudeRIstanzaUtenteQuadro o SET o.dataFine = current_date WHERE o.mudeRIstanzaUtente.id = :idIstanzaUtente")
	void deleteByIstanzaUtente(@Param("idIstanzaUtente") Long idIstanzaUtente);

    @Query("SELECT o.mudeDQuadro.idQuadro FROM MudeRIstanzaUtenteQuadro o "
			+ "        INNER JOIN o.mudeRIstanzaUtente "
			+ "  WHERE o.dataFine IS NULL "
			+ "    AND o.mudeRIstanzaUtente.istanza.id = :idIstanza"
			+ "    AND o.mudeRIstanzaUtente.utente.idUser = :idUser")
	List<Long> findAllByMudeRIstanzaAndUtente(@Param("idIstanza") Long idIstanza, @Param("idUser") Long idUser);

    @Modifying
    @Query("delete from MudeRIstanzaUtenteQuadro o where o.mudeRIstanzaUtente.id IN (SELECT x.id FROM MudeRIstanzaUtente x WHERE x.istanza.id = :idIstanza)")
    void deleteByIstanza(@Param("idIstanza") Long idIstanza);

}