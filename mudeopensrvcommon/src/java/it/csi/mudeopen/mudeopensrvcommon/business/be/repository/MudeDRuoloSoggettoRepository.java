/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;
import java.util.Optional;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDOrdine;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRuoloSoggetto;

@Repository
public interface MudeDRuoloSoggettoRepository extends BaseDictionaryRepository<MudeDRuoloSoggetto, String> {

    List<MudeDRuoloSoggetto> findAllByCategoriaRuoloAndDataFineIsNull(String categoriaRuolo);

    @Query(value = "SELECT mdrs.* "
    		+ "  FROM mudeopen_r_istanza_soggetto mris "
    		+ "    INNER JOIN mudeopen_r_istanza_soggetto_ruoli mrisr ON mrisr.id_istanza_soggetto = mris.id_istanza_soggetto "
    		+ "    INNER JOIN mudeopen_t_contatto mtc ON mtc.id_contatto = mris.id_soggetto "
    		+ "    INNER JOIN mudeopen_d_ruolo_soggetto mdrs ON mdrs.codice = mrisr.ruoli  "
    		+ "  WHERE id_istanza = :idIstanza"
    		+ "    AND mtc.guid = :guid", nativeQuery = true)
    List<MudeDRuoloSoggetto> findByIdIstanzaAndIdSoggetto(@Param("idIstanza") Long idIstanza, @Param("guid") String guid);
    
    @Query(value = "SELECT * FROM mudeopen_d_ruolo_soggetto mdrs WHERE data_fine IS NULL OR data_fine >= NOW() ORDER BY descrizione", nativeQuery = true)
    List<MudeDRuoloSoggetto> findAllActiveRoles();
    
    @Query(value = "SELECT * FROM mudeopen_d_ruolo_soggetto mdrs WHERE categoria_ruolo = :categoria", nativeQuery = true)
    List<MudeDRuoloSoggetto> findAllActiveRolesByCategory(@Param("categoria") String categoria);
}