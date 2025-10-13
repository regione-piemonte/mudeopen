/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDPPayImporto;

@Repository
public interface MudeDPPayImportiRepository extends BaseRepository<MudeDPPayImporto,Long> {

	/*
	 * returns the set 
	 */
	@Query(value = "SELECT "
			+ "    i.id_importo,"
			+ "    i.id_importo_default,"
			+ "    i.id_ente,"
			+ "    i.id_tipo_istanza,"
			+ "    i.id_specie_pratica,"
			+ "    i.tipo_importo,"
			+ "    i.descrizione,"
			+ "    COALESCE(i.importo, ppi.importo) as importo,"
			+ "    i.dati_spec_riscossione,"
			+ "    i.causale,"
			+ "    i.anno_accertamento,"
			+ "    i.numero_accertamento,"
			+ "    i.data_inizio, "
			+ "    i.data_fine, "
			+ "    i.data_modifica, "
			+ "    i.loguser "
			+ "  FROM mudeopen_d_ppay_importi i"
			+ "    INNER JOIN ("
			+ "      SELECT id_specie_pratica, id_tipo_istanza, id_ente"
			+ "        FROM mudeopen_d_ppay_importi"
			+ "        WHERE id_ente = :idEnte "
			+ "          AND (data_fine IS NULL OR data_fine > NOW()) "
			+ "          AND (data_inizio <= NOW()) "
			+ "          AND NOT(tipo_importo = 'disabilitato') AND ("
			+ "            id_specie_pratica = :idSpeciePratica"
			+ "            OR (id_tipo_istanza = :idTipoIstanza AND id_specie_pratica IS NULL)"
			+ "            OR (id_ente = :idEnte AND id_tipo_istanza IS NULL AND id_specie_pratica IS NULL)"
			+ "          )"
			+ "      ORDER BY id_specie_pratica, id_tipo_istanza, id_ente"
			+ "      LIMIT 1"
			+ "    ) first2use ON (first2use.id_specie_pratica IS NOT NULL AND first2use.id_specie_pratica = i.id_specie_pratica)"
			+ "            OR (first2use.id_specie_pratica IS NULL AND i.id_specie_pratica IS NULL AND first2use.id_tipo_istanza = i.id_tipo_istanza)"
			+ "            OR (first2use.id_specie_pratica IS NULL AND i.id_tipo_istanza IS NULL "
			+ "                AND first2use.id_tipo_istanza IS NULL AND i.id_tipo_istanza IS NULL "
			+ "                AND first2use.id_ente = i.id_ente)"
			+ "    LEFT JOIN mudeopen_d_ppay_importi ppi ON ppi.id_importo = i.id_importo_default "
			+ "  WHERE i.id_ente = :idEnte "
			+ "      AND (i.data_fine IS NULL OR i.data_fine > NOW()) "
			+ "      AND (i.data_inizio <= NOW()) "
			+ "      AND NOT(i.tipo_importo = 'disabilitato')", nativeQuery = true)
	List<MudeDPPayImporto> getFirstValidAmountSet(@Param("idEnte") Long idEnte, 
												  @Param("idTipoIstanza") String idTipoIstanza,
												  @Param("idSpeciePratica") String idSpeciePratica);

}