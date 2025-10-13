/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloSoggetto;

@Repository
public interface MudeRFascicoloSoggettoRepository extends BaseRepository<MudeRFascicoloSoggetto,Long> {

    MudeRFascicoloSoggetto findAllByIdAndDataFineIsNull(Long idFascicoloSoggetto);

    List<MudeRFascicoloSoggetto> findAllByFascicolo_IdOrderByDataInizioDesc(Long idFascicolo);

    /* UNUSED
    List<MudeRFascicoloSoggetto> findAllByGuidSoggettoAndDataFineIsNull(String guidSoggetto);

    @Query("select o from MudeRFascicoloSoggetto o where o.dataFine is null and o.user.idUser = :idUser ")
    List<MudeRFascicoloSoggetto> findAllByUserAndDataFineIsNull(@Param("idUser") Long idUserIns);
    */

    List<MudeRFascicoloSoggetto> findAllByFascicolo_IdAndGuidSoggettoAndUser_IdUserAndDataFineIsNullOrderByIdDesc(Long idFascicolo, 
    																						String guidSoggetto,
    																						Long idUser);

    List<MudeRFascicoloSoggetto> findAllByFascicolo_IdAndGuidSoggettoAndDataFineIsNullOrderByIdDesc(Long idFascicolo, 
    																						String guidSoggetto);

    @Query(value = "SELECT  "
	    		+ "    MAX(id_user_ins) "
	    		+ "  FROM         mudeopen_r_fascicolo_soggetto       mrfs "
	    		+ "  WHERE  "
	    		+ "    mrfs.data_fine IS NULL "
	    		+ "    AND mrfs.id_fascicolo = :idFascicolo "
	    		+ "    AND mrfs.guid_soggetto = :guidSoggetto ", nativeQuery = true)
    Long getIdUserInsByIdFascicoloAndGuid(@Param("idFascicolo") Long idFascicolo, @Param("guidSoggetto") String guidSoggetto);

    @Modifying
    @Query("UPDATE MudeRFascicoloSoggetto SET dataFine=NOW() WHERE fascicolo.id=:idFascicolo AND guidSoggetto=:guid AND dataFine IS NULL ")
    void dismissPrevByGuid(@Param("idFascicolo") Long idFascicolo, @Param("guid") String guid);

    @Query(value = "SELECT "
    		+ "    id_fascicolo_soggetto, "
    		+ "    NULL AS id_istanza_soggetto, "
    		+ "    NULL AS id_fascicolo, "
    		+ "    id_user_ins, "
    		+ "    guid_soggetto, "
    		+ "    data_inizio, "
    		+ "    data_fine, "
    		+ "    loguser, "
    		+ "    data_modifica "
    		+ "  FROM mudeopen_r_fascicolo_soggetto mrfs "
    		+ "  WHERE data_fine IS NULL "
    		+ "    AND id_fascicolo_soggetto = :idFascicoloSoggetto", nativeQuery = true)
    MudeRFascicoloSoggetto getAllByIdAndDataFineIsNull(@Param("idFascicoloSoggetto") Long idFascicolo);
}