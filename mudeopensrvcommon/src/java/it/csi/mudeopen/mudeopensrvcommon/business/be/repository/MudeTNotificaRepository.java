/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTNotifica;
@Repository
public interface MudeTNotificaRepository extends BaseRepository<MudeTNotifica, Long> {

    @Query("SELECT o FROM MudeTNotifica o WHERE o.istanza.id = :idIstanza")
    Page<MudeTNotifica> findAllByIstanza(@Param("idIstanza") Long idIstanza, Pageable pageable);

    @Query("SELECT o FROM MudeTNotifica o WHERE o.id = :id")
    Optional<MudeTNotifica> findById(@Param("id") Long id);

    @Query(value = "SELECT "
    		+ "    n.id_notifica,"
    		+ "    n.id_tipo_notifica,"
    		+ "    n.id_user_mittente,"
    		+ "    n.id_istanza,"
    		+ "    n.oggetto_notifica,"
    		+ "    n.testo_notifica,"
    		+ "    CONCAT(mtc.nome, '~', mtc.cognome, '~', mtc.cf, '~', letto) AS json_data,"
    		+ "    n.data_inizio,"
    		+ "    n.ruolo_mittente, "
    		+ "    n.dettaglio, "
    		+ "    n.loguser, "
    		+ "    n.data_inizio, "
    		+ "    n.data_modifica, "
    		+ "    n.data_fine "
    		+ "  FROM mudeopen_t_notifica n "
    		+ "      INNER JOIN mudeopen_r_notifica_utente nu ON n.id_notifica = nu.id_notifica "
    		+ "      INNER JOIN mudeopen_t_istanza mti ON mti.id_istanza = n.id_istanza "
    		+ "      INNER JOIN mudeopen_r_istanza_soggetto mris ON mris.id_istanza = n.id_istanza"
    		+ "      INNER JOIN mudeopen_r_istanza_soggetto_ruoli mrisr ON mris.id_istanza_soggetto = mrisr.id_istanza_soggetto AND mrisr.ruoli = 'IN' "
    		+ "      INNER JOIN mudeopen_t_contatto mtc ON mtc.id_contatto = mris.id_soggetto "
    		+ "      INNER JOIN mudeopen_d_comune mdc ON mdc.id_comune = mti.id_comune  "
    		+ "  WHERE nu.id_utente = :idUser"
    		+ "    AND (:idIstanza = -1 OR n.id_istanza = :idIstanza)"
    		+ "    AND (:idFascicolo = -1 OR mti.id_fascicolo = :idFascicolo)"
    		+ "    AND (:codIstanza = '' OR mti.codice_istanza ilike CONCAT('%', :codIstanza, '%'))"
    		+ "    AND (:comune = '' OR mdc.denom_comune ilike CONCAT('%', :comune, '%'))"
    		+ "    AND (:intestatario = '' OR CONCAT(mtc.nome, ' ', mtc.cognome, ' ', mtc.cf) ilike CONCAT('%', :intestatario, '%'))"
    		+ "  ORDER BY n.data_inizio DESC"
    		+ "        --#pageable\n", nativeQuery = true)
    Page<MudeTNotifica> findAllByIstanzaByUser(@Param("idUser") Long idUser,
											   @Param("idIstanza") Long idIstanza,
											   @Param("idFascicolo") Long idFascicolo,
											   @Param("codIstanza") String codIstanza,
											   @Param("comune") String comune,
											   @Param("intestatario") String intestatario,
    										   Pageable pageable);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "UPDATE mudeopen_r_notifica_utente SET letto = true, data_lettura=COALESCE(data_lettura, now()) WHERE id_utente = :idUser AND id_notifica = :idNotifica", nativeQuery = true)
    void setAsRead(@Param("idUser") Long idUser, @Param("idNotifica") Long idIstanza);

    @Modifying
    void deleteByIstanza_id(@Param("idIstanza") Long idIstanza);

}