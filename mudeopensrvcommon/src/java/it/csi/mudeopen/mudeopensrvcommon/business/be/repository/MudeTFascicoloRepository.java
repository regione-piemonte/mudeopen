/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTFascicolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
@Repository
public interface MudeTFascicoloRepository extends BaseRepository<MudeTFascicolo, Long> {
    List<MudeTFascicolo> findByMudeTUser(MudeTUser userCF);
    /*
    @Query(value = "SELECT c FROM mudeopen_t_fascicolo c WHERE (:mudeDComuneList is null or c.id_comune in (:mudeDComuneList))"
			+ " and (:mudeTResidenzaList is null or c.id_indirizzo in (:mudeTResidenzaList))"
			+ " and (:mudeTContattoList is null or c.id_contatto_intestatario in (:mudeTContattoList))"
			+ " and (:mudeDTipoIntervento is null or c.id_tipo_intervento = :mudeDTipoIntervento)"
			+ " and (:dataCreazioneDa is null or c.data_creazione >= :dataCreazioneDa)"
			+ " and (:dataCreazioneA is null or c.data_creazione <= :dataCreazioneA)"
			+ " and (:statoFascicolo is null or c.stato <= :statoFascicolo)"
			+ " and c.id_user = :mudeTUser order by c.dataCreazione", nativeQuery = true)
	List<MudeTFascicolo> findByParam(@Param("mudeTUser")  Long mudeTUser, 
			@Param("mudeDComuneList") List<Long> mudeDComuneList, 
			@Param("mudeTResidenzaList") List<Long> mudeTResidenzaList, 
			@Param("mudeTContattoList") List<Long> mudeTContattoList,
			@Param("mudeDTipoIntervento") Long mudeDTipoIntervento, 
			@Param("dataCreazioneDa") Timestamp dataCreazioneDa, 
			@Param("dataCreazioneA") Timestamp dataCreazioneA,
			@Param("statoFascicolo") String statoFascicolo);
	*/
    @Query(value = "SELECT CONCAT(codice_stato_fascicolo, '~', count(codice_stato_fascicolo)) "
    		+ " FROM mudeopen_r_fascicolo_stato "
    		+ " WHERE DATA_FINE IS NULL "
    		+ "   AND id_fascicolo IN (:idFascicoli) "
    		+ " GROUP BY codice_stato_fascicolo", nativeQuery = true)
    List<String> getAllStateCunters(@Param("idFascicoli") List<Long> idFascicoli);
//	/**
//	 * Find by intestatario list.
//	 *
//	 * @param mudeTContattoDB the mude t contatto db
//	 * @return the list
//	 */
//	List<MudeTFascicolo> findByIntestatario(MudeTContatto mudeTContattoDB);
//	/**
//	 * Find by intestatario id list.
//	 *
//	 * @param idIntestario the id intestario
//	 * @return the list
//	 */
//	@Query("SELECT o FROM MudeTFascicolo o WHERE o.intestatario.id = :idIntestario ")
//	List<MudeTFascicolo> findByIntestatarioId(@Param("idIntestario") Long idIntestario);
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from mudeopen_t_fascicolo where id_fascicolo = :idFascicolo", nativeQuery = true)
    @Transactional(propagation = Propagation.REQUIRED)
    Integer deleteFascicolo(@Param("idFascicolo") Long idFascicolo);
    Optional<MudeTFascicolo> findByCodiceFascicolo(String codiceFascicolo);
    @Query(value = "select f.* from mudeopen_t_fascicolo f " + 
    		"   join mudeopen_t_istanza ti on ti.id_fascicolo = f.id_fascicolo " + 
    		"   where " + 
    		"   ti.id_istanza in (:idsIstanza) " + 
    		"  order by f.codice_fascicolo", nativeQuery = true)
    List<MudeTFascicolo> getFascicoliByRuoli(@Param("idsIstanza") List<Long> idsIstanza);
    @Query(value = " select nextval('mudeopen_t_fascicolo_id_fascicolo_seq')", nativeQuery = true)
    Long getNextIdFascicolo();
    @Query(value = "SELECT codice_fascicolo FROM mudeopen_t_fascicolo WHERE id_fascicolo = :idFascicolo", nativeQuery = true)
    String getCodiceFascicolo(@Param("idFascicolo") Long idFascicolo);
    @Query(value = "select f.id_fascicolo as col_0_0_ from mudeopen_t_fascicolo f \n" + 
    		"   INNER JOIN mudeopen_t_istanza ti on ti.id_fascicolo = f.id_fascicolo \n" +
    		"   where ti.id_istanza in (:idsIstanza) \n" + 
    		"  order by f.codice_fascicolo", nativeQuery = true)
    List<Long> getFascicoliIdByRuoli(@Param("idsIstanza") List<Long> idsIstanza);
    @Query(value = "SELECT count(id_istanza) FROM mudeopen_t_istanza mti WHERE id_fascicolo = :idFascicolo AND data_fine  IS NULL", nativeQuery = true)
    Long howManyIstanzacesIdFolder(@Param("idFascicolo") Long idFascicolo);
    @Query(value = "SELECT id_fascicolo FROM mudeopen_t_istanza WHERE id_istanza = :idIstanza", nativeQuery = true)
    Long getIdFascicoloFromIdIstanza(@Param("idIstanza") Long idIstanza);
    @Query(value = " select nextval('mudeopen_numero_fascicolo_cosmo_seq')", nativeQuery = true)
    Long getNextIdNumeroFascicoloCOSMO();
}