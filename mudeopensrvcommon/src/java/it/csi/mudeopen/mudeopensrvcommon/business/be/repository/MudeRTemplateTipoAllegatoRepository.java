/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTemplateQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTemplateTipoAllegato;
import org.springframework.stereotype.Repository;

@Repository
public interface MudeRTemplateTipoAllegatoRepository extends BaseRepository<MudeRTemplateTipoAllegato,Long> {

    @Query("SELECT o from MudeRTemplateTipoAllegato o WHERE o.template.idTemplate = :idTemplate ORDER BY o.ordinamento")
    List<MudeRTemplateTipoAllegato> findAllByTemplate(@Param("idTemplate") Long idTemplate);

    List<MudeRTemplateTipoAllegato> findAllByTemplate_IdTemplateOrderByOrdinamentoAsc(Long idTemplate);

    //@Query("SELECT o from MudeRTemplateTipoAllegato o WHERE o.template.idTemplate = :idTemplate ORDER BY o.ordinamento")
    MudeRTemplateTipoAllegato findByTemplate_IdTemplateAndTipoAllegato_Codice(@Param("idTemplate") Long idTemplate, @Param("codice") String codice);

    @Query(value = "SELECT o.espressione_obbligatorieta "
    		+ "    FROM mudeopen_r_template_tipo_allegato o "
    		+ "    WHERE o.id_template = :idTemplate"
    		+ "      AND o.id_tipo_allegato = :codice", nativeQuery = true )
    String findByTipoAllegato_Codice(@Param("idTemplate") Long idTemplate, @Param("codice") String codice);
}