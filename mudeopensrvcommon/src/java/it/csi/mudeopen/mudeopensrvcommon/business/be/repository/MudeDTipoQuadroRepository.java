/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoQuadro;

@Repository
public interface MudeDTipoQuadroRepository extends BaseRepository<MudeDTipoQuadro, Long> {

    MudeDTipoQuadro findByIdTipoQuadro(Long id);

    MudeDTipoQuadro findByCodTipoQuadro(String codTipoQuadro);

    @Query("SELECT DISTINCT tq "
    		+ "      FROM MudeDQuadro q RIGHT JOIN q.mudeDTipoQuadro tq"
    		+ "      WHERE (?1 = 'ACTIVE' AND q.idQuadro IS NOT NULL) OR (?1 = 'INACTIVE' AND q.idQuadro IS NULL)")
    Page<MudeDTipoQuadro> findAllByActiveState(String state, Pageable pageable);

    @Query(value="SELECT f.codice_funzione"
    		+ "  FROM mudeopen_d_tipo_quadro tq"
    		+ "    INNER JOIN mudeopen_d_funzione f ON (tq.id_categoria_quadro = f.id_categoria_quadro AND f.data_fine IS NULL)  "
    		+ "  WHERE tq.cod_tipo_quadro = ?1", nativeQuery = true)
    String[] getAllFunctionsByCodTipoQuadro(String codTipoQuadro);

}