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

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocCatasto;

@Repository
public interface MudeopenRLocCatastoRepository extends BaseRepository<MudeopenRLocCatasto, Integer> {
	
    @Query(value = "SELECT t "
    		+ "  FROM MudeopenRLocCatasto t"
    		+ "  WHERE  "
    		+ "  t.idGeoriferimento = ?1 and idFabbricato is null and idCellula is null")
    List<MudeopenRLocCatasto> findByIdGeoriferimento(Long idGeoriferimento);

    // gets only _loc_ccatasto that arrives from GEECO seleztion: " AND id_fabbricato is null AND id_cellula is null" 
	@Query(value = "select lc.* from mudeopen_r_loc_catasto lc " +
			"inner join mudeopen_r_loc_georiferim lg on lg.id_georiferimento = lc.id_georiferimento " +
			"where lc.id_istanza = :idIstanza", nativeQuery = true)
	List<MudeopenRLocCatasto> getByIdIstanza(@Param("idIstanza") Long idIstanza);
	
    @Query(value = "SELECT t "
    		+ "  FROM MudeopenRLocCatasto t"
    		+ "  WHERE  "
    		+ "  t.idFabbricato = ?1")
    List<MudeopenRLocCatasto> findByIdFabbricato(Long idFabbricato);

	@Modifying
    @Query(value = "delete from MudeopenRLocCatasto t where t.idIstanza = :idIstanza ")
    void deleteByIdIstanza(@Param("idIstanza") Long idIstanza);

	@Query(value = "SELECT mrlc.id_catasto, "
			+ "		mrlc.id_georiferimento, "
			+ "		mrlc.id_fabbricato, "
			+ "		mrlc.id_cellula, "
			+ "		mrlc.id_istanza, "
			+ "		mrlc.sezione, "
			+ "		mrlc.sezione_urbana, "
			+ "		mrlc.foglio, "
			+ "		mrlc.particella, "
			+ "		mrlc.subalterno, "
			+ "		mrlc.f1_tipo_catasto, "
			+ "		mrlc.f1_personalizzato, "
			+ "		mrlc.f1_personalizzato_dettaglio, "
			+ "		concat(mdc.denom_comune, '~', mdc.cod_istat_comune) as desc_fonte_catasto,"
			+ "		mrlc.chiave_carotaggio, "
			+ "		mrlc.loguser, "
			+ "		mrlc.data_inizio, "
			+ "		mrlc.data_modifica, "
			+ "		mrlc.data_fine "
			+ "    FROM mudeopen_r_loc_catasto mrlc "
			+ "        INNER JOIN mudeopen_t_istanza mti ON mti.id_istanza = mrlc.id_istanza "
			+ "        INNER JOIN mudeopen_d_comune mdc ON mti.id_comune=mdc.id_comune "
			+ "    WHERE mrlc.id_istanza = :idIstanza", nativeQuery = true)
	List<MudeopenRLocCatasto> findAllByIdIstanza(@Param("idIstanza") Long idIstanza);
}
