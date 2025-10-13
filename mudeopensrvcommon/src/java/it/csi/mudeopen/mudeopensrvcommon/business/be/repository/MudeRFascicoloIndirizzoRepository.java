/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloIndirizzo;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface MudeRFascicoloIndirizzoRepository extends BaseRepository<MudeRFascicoloIndirizzo, Long> {

    MudeRFascicoloIndirizzo findByMudeTFascicolo_IdAndDataFineIsNull(Long idFascicolo);

    @Query("delete from MudeRFascicoloIndirizzo o where o.mudeTFascicolo.id = :idFascicolo")
    @Modifying
    void deleteIndirizziByFascicolo(@Param("idFascicolo") Long idFascicolo);

    @Query(value = "SELECT NOT(COALESCE(mti1.indirizzo, '') = COALESCE(mti2.indirizzo, '')) "
    		+ "    AND COALESCE(mti1.numero_civico, '') = COALESCE(mti2.numero_civico, '') "
    		+ "    AND COALESCE(mti1.id_comune, -1) = COALESCE(mti2.id_comune, -1) "
    		+ "    AND COALESCE(mti1.id_dug, -1) = COALESCE(mti2.id_dug, -1) "
    		+ "  FROM mudeopen_r_fascicolo_indirizzo mrfi "
    		+ "    INNER JOIN mudeopen_t_indirizzo mti1 ON mti1.id_indirizzo = mrfi.id_indirizzo "
    		+ "    INNER JOIN mudeopen_t_istanza mti ON mrfi.id_fascicolo = mti.id_fascicolo "
    		+ "    INNER JOIN mudeopen_t_indirizzo mti2 ON mti2.id_indirizzo = mti.id_indirizzo "
    		+ "  WHERE mrfi.data_fine IS NULL AND id_istanza = :idIstanza"
    		+ " UNION SELECT FALSE LIMIT 1", nativeQuery = true)
    Boolean isIstanzaAddressNotUpdated(@Param("idIstanza") Long idIstanza);

}