/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanzaSlim;

@Repository
public interface MudeTIstanzaSlimRepository extends BaseRepository<MudeTIstanzaSlim, Long> {

    @Query(value = "SELECT mti.*"
    		+ "  FROM mudeopen_t_istanza mti"
    		+ "  	INNER JOIN mudeopen_t_istanze_ext mtie ON mti.id_istanza = mtie.id_istanza"
    		+ "  WHERE dps_script_stato ~* :stato", nativeQuery = true)
    List<MudeTIstanzaSlim> filterByDpsScriptStato(@Param("stato") String stato);
    
    @Query(value = "SELECT COALESCE(json_data->'TAB_QUALIF_1'->>'zona_sismica_comune', '') FROM mudeopen_t_istanza mti where id_istanza = :idIstanza", nativeQuery = true)
    String getZonaSismicaIstanza(@Param("idIstanza") Long idIstanza);

    @Query(value = "SELECT mti.* "
    		+ "  FROM mudeopen_t_pratica_cosmo mtpc "
    		+ "    INNER JOIN mudeopen_t_istanza mti ON mti.id_tipo_istanza = 'INT-DOC' AND mtpc.id_istanza =mti.id_istanza "
    		+ "  WHERE mtpc.id_istanza  = :idIstanzaDS", nativeQuery = true)
    MudeTIstanzaSlim getIntDocFromIdIstanzaDS(@Param("idIstanzaDS") Long idIstanzaDS);

    @Query(value = "SELECT mti.* "
	    		+ "  FROM mudeopen_t_istanza mti "
	    		+ "    INNER JOIN mudeopen_r_fascicolo_indirizzo mrfi ON mti.id_istanza = mrfi.id_istanza_rif AND mrfi.data_fine  IS NULL "
	    		+ "  WHERE mti.id_fascicolo = :idFascicolo", nativeQuery = true)
    MudeTIstanzaSlim getFromFascicoloIndirizzo(@Param("idFascicolo") Long idFascicolo);

    @Query(value = "SELECT mti.* "
	    		+ "  FROM mudeopen_t_istanza mti "
	    		+ "  WHERE mti.id_fascicolo = :idFascicolo"
	    		+ "    AND id_tipo_istanza IN (:listaTipiIstanza)", nativeQuery = true)
    List<MudeTIstanzaSlim> getInstanceTypeInFolder(@Param("idFascicolo") Long idFascicolo, @Param("listaTipiIstanza") String[] listaTipiIstanza);
    
}