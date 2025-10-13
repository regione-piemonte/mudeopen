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

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaStato;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.StatoIstanzaSlimVO;

@Repository
public interface MudeRIstanzaStatoRepository extends BaseRepository<MudeRIstanzaStato, Long> {

    @Query("select o from MudeRIstanzaStato o where o.dataFine is null and o.statoIstanza.codice = :stato")
    List<MudeRIstanzaStato> findAllByStato(@Param("stato") String statoIstanza);

    List<MudeRIstanzaStato> findAllByIstanza_idOrderByDataInizioDesc(Long idIstanza);

    @Query("select o from MudeRIstanzaStato o where o.dataFine is null and o.istanza.id = :idIstanza")
    MudeRIstanzaStato findStatoByIstanza(@Param("idIstanza") Long idIstanza);

	@Query("SELECT new it.csi.mudeopen.mudeopensrvcommon.vo.istanza.StatoIstanzaSlimVO(o.dataInizio, o.statoIstanza.codice, o.statoIstanza.descrizione, o.statoIstanza.descrizioneEstesa, si.livello) FROM MudeRIstanzaStato o INNER JOIN o.statoIstanza si WHERE o.dataFine is null and o.istanza.id = :idIstanza")
	StatoIstanzaSlimVO findStatoByIdIstanza(@Param("idIstanza") Long idIstanza);

	@Query(value = "SELECT codice_stato_istanza "
			+ "  FROM mudeopen_r_istanza_stato "
			+ "  WHERE data_fine IS NULL "
			+ "    AND id_istanza = :idIstanza ", nativeQuery = true)
	String getStatoIstanza(@Param("idIstanza") Long idIstanza);

	@Query(value = "SELECT codice_stato_istanza "
			+ "  FROM mudeopen_r_istanza_stato "
			+ "  WHERE data_fine IS NULL "
			+ "    AND id_istanza IN (:idIstanze)", nativeQuery = true)
	List<String> getStatoIstanze(@Param("idIstanze") List<Long> idIstanze);

	@Query(value = "SELECT count(id_istanza_stato) "
			+ "  FROM mudeopen_r_istanza_stato "
			+ "  WHERE data_fine IS NULL "
			+ "    AND codice_stato_istanza in (:statiPossibili) "
			+ "    AND id_istanza = :idIstanza ", nativeQuery = true)
	int isInstanceInState(@Param("idIstanza") Long idIstanza, @Param("statiPossibili") String[] statiPossibili);

	@Query(value = "SELECT count(id_istanza_stato) "
					+ "  FROM mudeopen_r_istanza_stato si "
					+ "    INNER JOIN mudeopen_t_istanza i ON si.id_istanza = i.id_istanza "
					+ "  WHERE si.data_fine IS NULL "
					+ "    AND ('' IN (:statiPossibili) OR si.codice_stato_istanza in (:statiPossibili)) "
					+ "    AND ('' IN (:escludiStati) OR si.codice_stato_istanza NOT IN (:escludiStati)) "
					+ "    AND i.id_fascicolo = :idFascicolo", nativeQuery = true)
	int isThereInstanceStateInFolder(@Param("idFascicolo") Long idFascicolo, @Param("statiPossibili") String[] statiPossibili, @Param("escludiStati") String[] escludiStati);

	
    @Modifying
    @Query("delete from MudeRIstanzaStato o where o.istanza.id = :idIstanza")
    void deleteStatiByIstanza(@Param("idIstanza") Long idIstanza);
    @Query(value = "SELECT * "
		+ "  FROM mudeopen_r_istanza_stato "
		+ "  WHERE  "
		+ "    codice_stato_istanza = :stato "
		+ "    AND id_istanza = :idIstanza " 
		+ "  order by data_inizio desc ", nativeQuery = true)
    List<MudeRIstanzaStato> findByStatoAndIdIstanza(@Param("idIstanza") Long idIstanza, @Param("stato") String stato);

	@Query(value = "SELECT * FROM mudeopen_r_istanza_stato mris WHERE id_istanza = :idIstanza AND data_inizio\\:\\:varchar <= :limitDate ORDER BY data_inizio DESC LIMIT 1", nativeQuery = true)
    MudeRIstanzaStato findLastActiveStateByIstanzaBeforeDate(@Param("idIstanza") Long idIstanza, @Param("limitDate") String limitDate);

}