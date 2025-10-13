/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDQuadro;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTemplate;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTemplateQuadro;
import org.springframework.stereotype.Repository;

@Repository
public interface MudeRTemplateQuadroRepository extends BaseRepository<MudeRTemplateQuadro, Long> {
    List<MudeRTemplateQuadro> findAllByMudeDQuadro_IdQuadro(long idQuadro);

    MudeRTemplateQuadro findByMudeDTemplateAndMudeDQuadro(MudeDTemplate template, MudeDQuadro quadro);

    MudeRTemplateQuadro findByMudeDTemplate_MudeTipoIstanza_Codice(String codiceTipoIstanza);

    MudeRTemplateQuadro findByMudeDTemplate_MudeTipoIstanza_Descrizione(String descTipoIstanza);

    List<MudeRTemplateQuadro> findAllByMudeDTemplate_CodTemplateAndMudeDQuadro_FlgTipoGestione(String codTemplate, String flgTipoGestione);

    List<MudeRTemplateQuadro> findAllByMudeDTemplate_CodTemplateAndMudeDQuadro(String codTemplate, MudeDQuadro mudeDQuadro);

    List<MudeRTemplateQuadro> findAllByMudeDTemplateAndMudeDTemplate_FlgAttivoOrderByOrdinamentoTemplateQuadro(MudeDTemplate mudeDTemplate, long flgAttivo);

    @Query("SELECT o.mudeDTemplate.idTemplate FROM MudeRTemplateQuadro o WHERE o.idTemplateQuadro = :idTemplateQuadro")
    Long findIdtemplateByIdTemplateQuadro(@Param("idTemplateQuadro") Long idTemplateQuadro);

    @Query("SELECT o FROM MudeRTemplateQuadro o WHERE o.idTemplateQuadro = :idTemplateQuadro")
    Optional<MudeRTemplateQuadro> findByIdTemplateQuadro(@Param("idTemplateQuadro") Long idTemplateQuadro);

    @Query("SELECT o FROM MudeRTemplateQuadro o WHERE o.mudeDTemplate.idTemplate = :idTemplate ORDER BY o.ordinamentoTemplateQuadro ASC")
    List<MudeRTemplateQuadro> findByTemplate(@Param("idTemplate") Long idTemplate);

    @Query("SELECT o FROM MudeRTemplateQuadro o WHERE o.mudeDQuadro.mudeDTipoQuadro.idTipoQuadro = :idTipoQuadro")
    List<MudeRTemplateQuadro> findAllByIdTipoQuadro(@Param("idTipoQuadro") Long idTipoQuadro);

    List<MudeRTemplateQuadro> findAllByMudeDQuadro_IdQuadro(Long idQuadro);

    List<MudeRTemplateQuadro> findAllByMudeDTemplate_CodTemplate(String codTemplate);

    @Query("SELECT rq   FROM MudeRTemplateQuadro rq "
    		+ "        INNER JOIN rq.mudeDTemplate AS t "
    		+ "      WHERE t.mudeTipoIstanza.codice = :idTipoIstanza"
    		+ "         AND t.flgAttivo = 1"
    		+ "      ORDER BY rq.ordinamentoTemplateQuadro")
    List<MudeRTemplateQuadro> findAllActiveByTipoIstanza_id(@Param("idTipoIstanza") String idTipoIstanza);

    @Query("SELECT rq   FROM MudeRTemplateQuadro rq "
    		+ "      WHERE rq.mudeDTemplate.idTemplate = ("
    		+ "          SELECT MAX(idTemplate) "
    		+ "              FROM MudeDTemplate "
    		+ "              WHERE mudeTipoIstanza.codice = :idTipoIstanza"
    		+ "                  AND numeroVersione <= :version"
    		+ "        )"
    		+ "      ORDER BY rq.ordinamentoTemplateQuadro")
    List<MudeRTemplateQuadro> findAllByTipoIstanza_idAndVersion(@Param("idTipoIstanza") String idTipoIstanza, @Param("version") Integer version);

    @Query("SELECT rq   FROM MudeRTemplateQuadro rq "
    		+ "      WHERE rq.mudeDTemplate.idTemplate = ("
    		+ "          SELECT idTemplate "
    		+ "              FROM MudeDTemplate "
    		+ "              WHERE mudeTipoIstanza.codice = :idTipoIstanza"
    		+ "                  AND numeroVersione = ("
    		+ "                      SELECT MAX(numeroVersione) FROM MudeDTemplate WHERE mudeTipoIstanza.codice = :idTipoIstanza"
    		+ "                    )"
    		+ "        )"
    		+ "      ORDER BY rq.ordinamentoTemplateQuadro")
    List<MudeRTemplateQuadro> findAllByTipoIstanza_idAndMaxVersion(@Param("idTipoIstanza") String idTipoIstanza);
    @Query(value = "SELECT " +
			"    virtualQuadro.id_quadro * virtualQuadro.id_quadro_padre as id_template_quadro," + // virtual id_template_quadro
			"    tq.id_template," + 
			"    virtualQuadro.id_quadro," + 
			"    tq.ordinamento_template_quadro," + 
			"    null AS flg_quadro_obbigatorio," + // field to be removed from entity
			"    tq.proprieta, " +
    		"    tq.loguser, " +
    		"    tq.data_inizio, " +
    		"    tq.data_modifica, " +
    		"    tq.data_fine "  +
			"  FROM mudeopen_r_template_quadro tq INNER JOIN (" + 
			"      SELECT qf.*, qp.id_quadro as id_quadro_padre" + 
			"          FROM mudeopen_d_quadro qf " + 
			"						LEFT JOIN mudeopen_d_quadro qp ON (qf.id_quadro=qp.id_quadro OR qp.flg_tipo_gestione='C' AND CONCAT(qp.json_configura_quadro, '') like CONCAT('%\"~', qf.id_quadro,'~\"%'))" + 
			"    ) virtualQuadro ON tq.id_quadro=virtualQuadro.id_quadro_padre" +
			"  WHERE (:idTemplate=-1 OR tq.id_template=:idTemplate) "
			+ "        AND (:idTipoQuadro=-1 OR virtualQuadro.id_tipo_quadro = :idTipoQuadro)"
			+ " ORDER BY tq.ordinamento_template_quadro,virtualQuadro.id_quadro", nativeQuery = true)
	List<MudeRTemplateQuadro> findAllVirtualTemplateQuadroByIdTipoQuadro(@Param("idTipoQuadro") Long idTipoQuadro, @Param("idTemplate") Long idTemplate);

    @Query(value = "SELECT COUNT(virtualQuadro.id_quadro * virtualQuadro.id_quadro_padre) > 0"+  
			"  FROM mudeopen_r_template_quadro tq INNER JOIN (" + 
			"      SELECT qf.*, qp.id_quadro as id_quadro_padre" + 
			"          FROM mudeopen_d_quadro qf " + 
			"						LEFT JOIN mudeopen_d_quadro qp ON (qf.id_quadro=qp.id_quadro OR qp.flg_tipo_gestione='C' AND CONCAT(qp.json_configura_quadro, '') like CONCAT('%\"~', qf.id_quadro,'~\"%'))" + 
			"    ) virtualQuadro ON tq.id_quadro=virtualQuadro.id_quadro_padre" +
			"  WHERE virtualQuadro.id_tipo_quadro = :idTipoQuadro", nativeQuery = true)
	boolean findAllVirtualTemplateQuadroByIdTipoQuadroCounter(@Param("idTipoQuadro") Long idTipoQuadro);

    @Query(value = "SELECT " +
			"    tq.id_template_quadro," + 
			"    tq.id_template," + 
			"    tq.id_quadro," + // returns the complex quadro where id_quadro is present 
			"    tq.ordinamento_template_quadro," + 
			"    null AS flg_quadro_obbigatorio," + // field to be removed from entity
			"    tq.proprieta, " +
    		"    tq.loguser, " +
    		"    tq.data_inizio, " +
    		"    tq.data_modifica, " +
    		"    tq.data_fine " +
			"  FROM mudeopen_r_template_quadro tq INNER JOIN (" + 
			"      SELECT qf.*, qp.id_quadro as id_quadro_padre" + 
			"          FROM mudeopen_d_quadro qf " + 
			"						LEFT JOIN mudeopen_d_quadro qp ON (qf.id_quadro=qp.id_quadro OR qp.flg_tipo_gestione='C' AND CONCAT(qp.json_configura_quadro, '') like CONCAT('%\"~', qf.id_quadro,'~\"%'))" + 
			"    ) virtualQuadro ON tq.id_quadro=virtualQuadro.id_quadro_padre" +
			"  WHERE tq.id_template=:idTemplate "
			+ "        AND virtualQuadro.id_quadro = :idQuadro", nativeQuery = true)
	MudeRTemplateQuadro findByIdQuadroInVirtualTemplateQuadro(@Param("idQuadro") Long idQuadro, @Param("idTemplate") Long idTemplate);
    List<MudeRTemplateQuadro> findAllByMudeDTemplate_IdTemplateOrderByOrdinamentoTemplateQuadroAsc(@Param("idTemplate") Long idTemplate);

    MudeRTemplateQuadro findByMudeDTemplate_IdTemplateAndMudeDQuadro_IdQuadro(@Param("idTemplate") Long idTemplate, @Param("idQuadro") Long idQuadro);

    /**
     * delete idtemplate by id template long.and id quadro
     *
     * @param idTamplat the id template
     * @param idQuadro the id quadro
     * @return void
    @Modifying
    void deleteByMudeDTemplate_IdTemplateAndMudeDQuadro_IdQuadro(Long idTamplat, Long idQuadro);
     */

    @Query(value = "SELECT rq.*   FROM mudeopen_r_template_quadro rq \n"
    		+ "        INNER JOIN mudeopen_t_istanza mti on rq.id_template = mti.id_template\n"
    		+ "      WHERE mti.id_istanza = :idIstanza\n"
    		+ "      ORDER BY rq.ordinamento_template_quadro", nativeQuery = true)
    List<MudeRTemplateQuadro> getAllActiveByTipoIstanza_id(@Param("idIstanza") Long idIstanza);

    
}