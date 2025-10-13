/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTPratica;
import it.csi.mudeopen.mudeopensrvcommon.vo.pratica.PraticaSlimVO;

@Repository
public interface MudeTPraticaRepository extends BaseRepository<MudeTPratica, Long> {
	
    @Query(value = "SELECT mtp.* FROM mudeopen_r_istanza_pratica mrip "
    		+ "			inner join mudeopen_t_pratica mtp on mrip.id_pratica =mtp.id_pratica and anno_pratica IS NOT NULL "
    		+ "		where id_istanza=:idIstanza", nativeQuery = true)
    MudeTPratica findByIdIstanza(@Param("idIstanza") Long idIstanza);
    
    @Query(value = "SELECT mtp.* FROM mudeopen_r_istanza_pratica mrip "
    		+ "			inner join mudeopen_t_pratica mtp on mrip.id_pratica =mtp.id_pratica "
    		+ "		where id_istanza=:idIstanza", nativeQuery = true)
    MudeTPratica findAnyByIdIstanza(@Param("idIstanza") Long idIstanza);
    
    @Query("SELECT new it.csi.mudeopen.mudeopensrvcommon.vo.pratica.PraticaSlimVO(o.pratica.id, o.pratica.numeroPratica, o.pratica.annoPratica) FROM MudeRIstanzaPratica o WHERE o.istanza.id = :idIstanza AND o.pratica.annoPratica IS NOT NULL")
    PraticaSlimVO findByIdIstanzaSlim(@Param("idIstanza") Long idIstanza);
	
    @Query(value="select concat_ws('$$$', mtp.numero_pratica, extract(epoch from mtp.data_inizio) * 1000) \n" +
				"from mudeopen_t_pratica mtp \n" +
				"	join mudeopen_r_istanza_pratica mrip on mtp.id_pratica = mrip.id_pratica \n" +
				"	where mtp.anno_pratica IS NOT NULL AND mrip.id_istanza = :idIstanza "
			+ " LIMIT 1", nativeQuery = true)
	String findPraticaStringByIdIstanza(@Param("idIstanza") Long idIstanza);
	
    @Query(value = "SELECT o.* FROM mudeopen_t_pratica o WHERE o.numero_pratica = :numeroPratica AND o.anno_pratica = :annoPratica AND o.id_ente = :idEnte", nativeQuery = true)
    MudeTPratica findByNumeroPraticaAnnoPraticaEnte(@Param("numeroPratica") String numeroPratica,
    		@Param("annoPratica") Long annoPratica,
    		@Param("idEnte") Long idEnte);

    @Query(value = "SELECT o.* FROM mudeopen_t_pratica o WHERE o.numero_pratica LIKE CONCAT('%', :numeroPratica, '%')", nativeQuery = true)
    List<MudeTPratica> getByNumeroPratica(@Param("numeroPratica") String numeroPratica);
    
    Optional<MudeTPratica> findById(Long idPratica);
    @Query(value = "select p.* from mudeopen_t_pratica p " + 
    		"   join mudeopen_r_istanza_pratica mrip on mrip.id_pratica = p.id_pratica " + 
    		"   join mudeopen_t_istanza ti on ti.id_istanza = mrip.id_istanza " + 
    		"   where p.anno_pratica IS NOT NULL AND " + 
    		"   mrip.id_istanza in (:idsIstanza) " + 
    		"  order by p.numero_pratica", nativeQuery = true)
    List<MudeTPratica> getPraticheByRuoli(@Param("idsIstanza") List<Long> idsIstanza);
    
    @Query(value = "select p.* " + 
    		"  from mudeopen_t_istanza mti " + 
    		"   join mudeopen_r_istanza_pratica mrip on mrip.id_istanza = mti.id_istanza " + 
    		"   join mudeopen_t_pratica p on p.id_pratica = mrip.id_pratica AND p.anno_pratica IS NOT NULL " + 
    		"   where mti.id_fascicolo in (select i.id_fascicolo " + 
    		"   from MUDEOPEN_T_ISTANZA i " + 
    		"     where i.id_istanza = :idIstanza " + 
    		"  ) order by p.numero_pratica", nativeQuery = true)
    List<MudeTPratica> findByFascicoloIstanza(@Param("idIstanza") Long idIstanza);
	
    @Query(value = "SELECT MAX(mtp.numero_pratica) FROM mudeopen_t_pratica mtp "
			+ "              INNER JOIN mudeopen_r_istanza_pratica mrip ON mrip.id_pratica = mtp.id_pratica "
			+ "          WHERE mrip.id_istanza = :idIstanza AND mtp.anno_pratica IS NOT NULL "
			+ "          LIMIT 1", nativeQuery = true)
	String getNumeroPraticaByIdIstanza(@Param("idIstanza") Long idIstanza);
	
    @Query(value = "select p.id_pratica as col_0_0_ from mudeopen_t_pratica p \n" + 
    		"   INNER JOIN mudeopen_r_istanza_pratica mrip ON mrip.id_pratica = p.id_pratica \n" +  
    		"   INNER JOIN mudeopen_t_istanza ti ON ti.id_istanza = mrip.id_istanza \n" +
    		"   where p.anno_pratica IS NOT NULL AND  mrip.id_istanza in (:idsIstanza) \n" + 
    		"  order by p.numero_pratica", nativeQuery = true)
    List<Long> getPraticheIdByRuoli(@Param("idsIstanza") List<Long> idsIstanza);
    
    @Query(value = "SELECT anno_pratica FROM mudeopen_t_pratica mtp "
	    		+ "    INNER JOIN mudeopen_r_istanza_pratica mrip ON mtp.id_pratica = mrip.id_pratica "
	    		+ "  WHERE mtp.anno_pratica IS NOT NULL AND  id_istanza = :idIstanza "
				+ "  LIMIT 1", nativeQuery = true)
	String getAnnoPraticaByIdIstanza(@Param("idIstanza") Long idIstanza);
	
	@Query(value = "select mtp.* from mudeopen_t_pratica_cosmo mtpc "
						+ "		inner join mudeopen_t_pratica mtp on mtpc.numero_pratica = mtp.numero_pratica "
						+ "	where mtpc.id_pratica_cosmo = :idPraticaCosmo "
						+ "	order by id_pratica desc "
						+ "	limit 1	", nativeQuery = true)
    MudeTPratica findBynumeroPraticaCosmo(@Param("idPraticaCosmo") Long idPraticaCosmo);
	
}