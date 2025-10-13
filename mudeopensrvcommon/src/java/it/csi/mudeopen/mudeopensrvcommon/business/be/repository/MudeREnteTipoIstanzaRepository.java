/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeREnteTipoIstanza;

@Repository
public interface MudeREnteTipoIstanzaRepository extends BaseRepository<MudeREnteTipoIstanza, Long> {

    @Query(value = 
    		"SELECT mreti.* "
    		+ "  FROM mudeopen_t_ente mte"
    		+ "    INNER JOIN mudeopen_r_ente_comune_tipo_istanza mreti ON (mreti.id_ente = mte.id_ente AND (mreti.data_fine IS NULL OR mreti.data_fine > NOW() OR :getAlsoDisabled)) "
    		+ "  WHERE mte.data_fine IS NULL"
    		+ "    AND mreti.id_comune = :idComune"
    		+ "    AND mreti.codice_tipo_istanza = :codTipoIstanza"
    		+ "    AND COALESCE(mreti.codice_specie_pratica, 'getnull') = ("
    		+ "				SELECT COALESCE(codice_specie_pratica, 'getnull') "
    		+ "                            FROM mudeopen_r_ente_comune_tipo_istanza "
    		+ "                            WHERE (data_fine IS NULL OR :getAlsoDisabled)"
    		+ "                               AND id_comune = :idComune"
    		+ "                               AND codice_tipo_istanza = :codTipoIstanza"
    		+ "                               AND competenza_principale = true "
    		+ "                               AND (codice_specie_pratica IS NULL OR codice_specie_pratica = :codSpeciePratica) "
    		+ "                            ORDER BY codice_specie_pratica DESC NULLS LAST"
    		+ "                            LIMIT 1)", nativeQuery = true)
	List<MudeREnteTipoIstanza> retrieveAllActives(@Param("idComune") Long idComune, 
																 @Param("codTipoIstanza") String codTipoIstanza,
																 @Param("codSpeciePratica") String codSpeciePratica,
																 @Param("getAlsoDisabled") boolean getAlsoDisabled);

	

 	@Query(value = 
 			"SELECT mreti.responsabile_procedimento"
 			+ "  FROM mudeopen_t_istanza mti "
 			+ "    INNER JOIN mudeopen_r_istanza_ente mrie ON (mti.id_istanza = mrie.id_istanza AND mrie.ente_ricevente = true) "
 			+ "    INNER JOIN mudeopen_r_ente_comune_tipo_istanza mreti ON (mreti.id_ente = mrie.id_ente AND mti.id_tipo_istanza=mreti.codice_tipo_istanza AND (mreti.data_fine IS NULL OR mreti.data_fine > NOW()) AND mreti.competenza_principale=true) "
 			+ "  WHERE mti.id_istanza = :idIstanza"
 			+ " LIMIT 1", nativeQuery = true)
 	String findRespProcedimentoForCambioStato(@Param("idIstanza") Long idIstanza);
}