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

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDQuadro;

@Repository
public interface MudeDQuadroRepository extends BaseRepository<MudeDQuadro, Long> {

    MudeDQuadro findByIdQuadro(Long idQuadro);

//    /**
//     * Find all by id quadro list.
//     *
//     * @param idQuadro the id quadro
//     * @return the list
//     */
//    List<MudeDQuadro> findAllByIdQuadro(Long idQuadro);
    MudeDQuadro findByMudeDTipoQuadro_IdTipoQuadro(Long idTIpoQuadro);

    List<MudeDQuadro> findAllByMudeDTipoQuadro_IdTipoQuadro(Long idTIpoQuadro);

    MudeDQuadro findByMudeDTipoQuadro_DesTipoQuadro(String descTipoQuadro);

    MudeDQuadro findByMudeDTipoQuadro_CodTipoQuadro(String codTipoQuadro);
    @Query("SELECT o FROM MudeDQuadro o WHERE o.mudeDTipoQuadro.idTipoQuadro = :idTipoQuadro")
    List<MudeDQuadro> findAllByIdTipoQuadro(@Param("idTipoQuadro") Long idTipoQuadro);

    List<MudeDQuadro> findAllByMudeDTipoQuadro_IdTipoQuadroOrderByNumVersioneDesc(Long idTipoQuadro);

    MudeDQuadro findFirstByMudeDTipoQuadro_IdTipoQuadroOrderByNumVersioneDesc(Long idTipoQuadro);

    @Query(value = "SELECT mdq.num_versione"
    		+ "  FROM mudeopen_d_quadro mdq "
    		+ "    INNER JOIN mudeopen_d_tipo_quadro mdtq ON mdq.id_tipo_quadro = mdtq.id_tipo_quadro"
    		+ "  WHERE"
    		+ "    mdtq.id_tipo_quadro = :idTipoQuadro"
    		+ "  ORDER BY mdq.num_versione DESC"
    		+ "  LIMIT 1", nativeQuery = true)
    Long findFirstByMudeDTipoQuadro_IdTipoQuadroOrderByNumVersioneDescSLIM(@Param("idTipoQuadro") Long idTipoQuadro);
    @Query("SELECT q FROM MudeRTemplateQuadro r INNER JOIN r.mudeDQuadro AS q WHERE r.mudeDTemplate.idTemplate = :idTemplate ORDER BY r.ordinamentoTemplateQuadro")
    List<MudeDQuadro> findAllByIdTemplateOrderByOrdinamentoAsc(@Param("idTemplate") Long idTemplate);

    @Modifying
    @Query(value = "UPDATE mudeopen_d_quadro "
    		+ "			SET json_configura_quadro=REPLACE(json_configura_quadro\\:\\:varchar, CONCAT('\"~', :fromIdQuadro, '~\"'), CONCAT('\"~',  :toIdQuadro, '~\"'))\\:\\:json "
    		+ "      WHERE (:idQuadroComplex=-1 OR id_quadro=:idQuadroComplex) AND flg_tipo_gestione='C'", nativeQuery = true)
    void updatePrevQuadroVersionInQuadroComplex(@Param("idQuadroComplex") Long idQuadroComplex, @Param("fromIdQuadro") String fromIdQuadro, @Param("toIdQuadro") String toIdQuadro);

}