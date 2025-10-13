/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeCProprietaEnte;

@Repository
public interface MudeCProprietaEnteRepository extends BaseRepository<MudeCProprietaEnte, Long> {

	List<MudeCProprietaEnte> findByIdEnte(Long idEnte);

    @Query(value = "SELECT DISTINCT ON (mte.nome) mte.*"
    		+ "  FROM mudeopen_c_proprieta_ente mte"
    		+ "    INNER JOIN mudeopen_r_ente_comune_tipo_istanza mrec ON (mrec.id_ente = mte.id_ente AND (mrec.data_fine IS NULL OR mrec.data_fine > NOW()) AND (mrec.data_inizio <= NOW()) AND mrec.id_comune = :idComune) "
    		+ "    LEFT JOIN mudeopen_r_ente_comune_tipo_istanza mreti ON (mreti.id_ente = mte.id_ente AND (mreti.data_fine IS NULL OR mreti.data_fine > NOW()) AND (mreti.data_inizio <= NOW()) AND mreti.competenza_principale = true AND mreti.codice_tipo_istanza = mte.codice_tipo_istanza) "
    		+ "  WHERE (mte.data_fine IS NULL OR mte.data_fine > NOW()) AND (mte.data_inizio <= NOW())"
    		+ "    AND (''=:scope OR visibilita like concat('%', :scope, '%'))"
    		+ "    AND (''=:PropertyName OR mte.nome=:PropertyName)"
    		+ "    AND (mte.codice_tipo_istanza IS NULL OR mte.codice_tipo_istanza=:codTipoIstanza)"
    		+ "  ORDER BY mte.nome, mte.codice_tipo_istanza DESC NULLS LAST", nativeQuery = true)
	List<MudeCProprietaEnte> findByIdComuneAndScope(@Param("PropertyName") String PropertyName, @Param("codTipoIstanza") String codTipoIstanza, @Param("idComune") Long idComune, @Param("scope") String scope);

    @Query(value = "SELECT COALESCE(MAX(valore), CAST(:defaultValue AS varchar)) FROM ("
		    		+ "    SELECT DISTINCT ON (mte.nome) mte.valore"
		    		+ "  FROM mudeopen_c_proprieta_ente mte"
		    		+ "    INNER JOIN mudeopen_r_ente_comune_tipo_istanza mrec ON (mrec.id_ente = mte.id_ente AND (mrec.data_fine IS NULL OR mrec.data_fine > NOW()) AND (mrec.data_inizio <= NOW()) AND mrec.id_comune = :idComune) "
		    		+ "    LEFT JOIN mudeopen_r_ente_comune_tipo_istanza mreti ON (mreti.id_ente = mte.id_ente AND (mreti.data_fine IS NULL OR mreti.data_fine > NOW()) AND (mreti.data_inizio <= NOW()) AND mreti.competenza_principale = true AND mreti.codice_tipo_istanza = mte.codice_tipo_istanza) "
		    		+ "  WHERE (mte.data_fine IS NULL OR mte.data_fine > NOW()) AND (mte.data_inizio <= NOW())"
		    		+ "    AND (''=:scope OR visibilita like concat('%', :scope, '%'))"
		    		+ "    AND (''=:PropertyName OR mte.nome=:PropertyName)"
		    		+ "    AND (mte.codice_tipo_istanza IS NULL OR mte.codice_tipo_istanza=:codTipoIstanza)"
		    		+ "    AND (mte.specie_pratica IS NULL OR mte.specie_pratica=:speciePratica)"
		    		+ "  ORDER BY mte.nome, mte.specie_pratica DESC NULLS LAST, mte.codice_tipo_istanza DESC NULLS LAST"
		    		+ " LIMIT 1) sq", nativeQuery = true)
	String getParamValue(@Param("PropertyName") String PropertyName, @Param("codTipoIstanza") String codTipoIstanza, @Param("idComune") Long idComune, @Param("scope") String scope, @Param("speciePratica") String speciePratica, @Param("defaultValue") String defaultValue);

}