/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTemplate;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoIstanza;

@Repository
public interface MudeDTemplateRepository extends BaseRepository<MudeDTemplate, Long> {

    MudeDTemplate findByIdTemplate(Long id);

    List<MudeDTemplate> findAllByFlgAttivo(Long flgAttivo);

    MudeDTemplate findByCodTemplate(String codTemplate);

    MudeDTemplate findByMudeTipoIstanza(MudeDTipoIstanza tipoTistanza);

    List<MudeDTemplate> findAllByMudeTipoIstanza_CodiceOrderByNumeroVersioneDesc(String tipoIstanza);

    @Query(value = "SELECT templ.* FROM mudeopen_d_template templ INNER JOIN " +
    		"      (SELECT MAX(id_template) as id_templ_max FROM mudeopen_d_template GROUP BY id_tipo_istanza) AS maxtempl ON templ.id_template=maxtempl.id_templ_max" +
    		"          INNER JOIN (" +
    		"            SELECT tq.id_template " +
    		"          FROM mudeopen_r_template_quadro tq INNER JOIN ( " +
    		"              SELECT qf.*, qp.id_quadro as id_quadro_padre " +
    		"                  FROM mudeopen_d_quadro qf  " +
    		"										LEFT JOIN mudeopen_d_quadro qp ON (qf.id_quadro=qp.id_quadro OR qp.flg_tipo_gestione='C' AND CONCAT(qp.json_configura_quadro, '') like CONCAT('%\"~', qf.id_quadro,'~\"%')) " +
    		"            ) virtualQuadro ON tq.id_quadro=virtualQuadro.id_quadro_padre" +
    		"          WHERE virtualQuadro.id_quadro = :idQuadro" +
    		"        ) AS rtemp ON rtemp.id_template=maxtempl.id_templ_max", nativeQuery = true)
    List<MudeDTemplate> findAllByIdQuadro(@Param("idQuadro") Long idQuadro);

    @Query("SELECT t " +
			"  FROM MudeRTemplateQuadro rq " +
			"    INNER JOIN rq.mudeDTemplate AS t " +
			"  WHERE t.flgAttivo=1 AND " +
			"    (" +
			"      rq.mudeDQuadro.idQuadro = :idQuadro " +
			"      OR rq.mudeDQuadro.idQuadro IN (" + 
			"        SELECT idQuadro FROM MudeDQuadro q " + 
			"          WHERE " + 
			"            q.flgTipoGestione = 'C' " + 
			"						AND CONCAT(q.jsonConfiguraQuadro, '') like CONCAT('%\"~', CAST(:idQuadro as string), '~\"%')" +
			"    )" + 
			"  )")
	List<MudeDTemplate> findActiveIdQuadroInTemplates(@Param("idQuadro") Long idQuadro);

    @Query(value = "SELECT COUNT(mdt.id_template) > 0"
			    		+ "  FROM mudeopen_r_template_quadro mrtq"
			    		+ "    INNER JOIN mudeopen_d_template mdt ON mrtq.id_template = mdt.id_template"
			    		+ "  WHERE"
			    		+ "    mdt.flg_attivo = 1"
			    		+ "    AND (mrtq.id_quadro = :idQuadro"
			    		+ "      OR mrtq.id_quadro in ("
			    		+ "        SELECT mdp2.id_quadro"
			    		+ "        FROM mudeopen_d_quadro mdp2"
			    		+ "        WHERE"
			    		+ "          mdp2.flg_tipo_gestione = 'C'"
			    		+ "					AND ((mdp2.json_configura_quadro || '') like ('%\"~' || cast( :idQuadro as varchar(255))|| '~\"%'))))", nativeQuery = true)
	boolean findActiveIdQuadroInTemplatesCounter(@Param("idQuadro") Long idQuadro);

    @Query("SELECT t   FROM MudeDTemplate t "
    		+ "      WHERE t.mudeTipoIstanza.codice = :idTipoIstanza"
    		+ "         AND t.flgAttivo = 1")
    MudeDTemplate findActiveByTipoIstanza_id(@Param("idTipoIstanza") String idTipoIstanza);

    @Query("SELECT t   FROM MudeDTemplate t"
    		+ "      WHERE mudeTipoIstanza.codice = :idTipoIstanza"
    		+ "        AND numeroVersione = ("
    		+ "            SELECT MAX(numeroVersione) FROM MudeDTemplate WHERE mudeTipoIstanza.codice = :idTipoIstanza AND numeroVersione <= :version"
    		+ "        )")
    MudeDTemplate findByTipoIstanza_idAndVersion(@Param("idTipoIstanza") String idTipoIstanza, @Param("version") Integer version);

    @Query("SELECT t   FROM MudeDTemplate t"
    		+ "      WHERE mudeTipoIstanza.codice = :idTipoIstanza"
    		+ "        AND numeroVersione = ("
    		+ "            SELECT MAX(numeroVersione) FROM MudeDTemplate WHERE mudeTipoIstanza.codice = :idTipoIstanza"
    		+ "        )")
    MudeDTemplate findByTipoIstanza_idAndMaxVersion(@Param("idTipoIstanza") String idTipoIstanza);

    @Query("SELECT template FROM MudeTIstanza o WHERE o.id = :idIstanza")
    MudeDTemplate findTemplateForIstanza(@Param("idIstanza") Long idIstanza);

	@Query(value = "select mdt.num_versione from mudeopen_d_template mdt \n" +
			"where mdt.id_tipo_istanza = :idTipoIstanza \n" +
			"and mdt.num_versione = \n" +
			"(select max(mdt2.num_versione) from mudeopen_d_template mdt2 where mdt2.id_tipo_istanza = :idTipoIstanza )", nativeQuery = true)
	Integer findMaxVersionByTipoIstanza(@Param("idTipoIstanza") String idTipoIstanza);

}