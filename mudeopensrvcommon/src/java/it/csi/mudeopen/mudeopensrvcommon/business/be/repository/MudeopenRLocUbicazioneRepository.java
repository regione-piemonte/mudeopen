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

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocUbicazione;

@Repository
public interface MudeopenRLocUbicazioneRepository extends BaseRepository<MudeopenRLocUbicazione, Integer> {
	
    @Query(value = "SELECT t "
    		+ "  FROM MudeopenRLocUbicazione t "
    		+ "  WHERE  "
    		+ "    t.idIstanza = ?1 and idGeoriferimento is null and (idViaToponom is not null)")
    List<MudeopenRLocUbicazione> findByIdIstanza(Long idIstanza);

    @Query(value = "SELECT t "
    		+ "  FROM MudeopenRLocUbicazione t "
    		+ "  WHERE  "
    		+ "    t.idGeoriferimento = ?1 and idFabbricato is null and idCellula is null")
    List<MudeopenRLocUbicazione> findByIdGeoriferimento(Long idGeoriferimento);
    @Query(value = "select * from mudeopen.mudeopen_r_loc_ubicazione where id_georiferimento  in ( "
    		+ "  select id_georiferimento from mudeopen_r_loc_georiferim  where id_istanza = :idIstanza "
    		+ ") ", nativeQuery = true)
    List<MudeopenRLocUbicazione> findAllByIdIstanza(@Param("idIstanza") Long idIstanza);
    
    /*
    @Query(value = "select lu.* from mudeopen_r_loc_ubicazione lu " +
			"inner join mudeopen_r_loc_georiferim lg on lg.id_georiferimento = lu.id_georiferimento " +
			"where lu.id_istanza = :idIstanza", nativeQuery = true)
			*/
    @Query(value = "select * from mudeopen_r_loc_ubicazione mrlu WHERE id_istanza = :idIstanza", nativeQuery = true)
    List<MudeopenRLocUbicazione> getByIdIstanzaViaGeoriferimento(@Param("idIstanza") Long idIstanza);

	@Query(value = "SELECT o FROM MudeopenRLocUbicazione o WHERE o.idIstanza = :idIstanza")
	List<MudeopenRLocUbicazione> getByIdIstanza(@Param("idIstanza") Long idIstanza);

    @Query(value = "SELECT t "
    		+ "  FROM MudeopenRLocUbicazione t "
    		+ "  WHERE  "
    		+ "    t.idFabbricato = ?1")
    List<MudeopenRLocUbicazione> findByIdFabbricato(Long idFabbricato);

	@Modifying
    @Query(value = "delete from MudeopenRLocUbicazione t where t.idIstanza = :idIstanza ")
    void deleteByIdIstanza( @Param("idIstanza") Long idIstanza);
	
    @Query(value = "SELECT t FROM MudeopenRLocUbicazione t WHERE t.idIstanza = ?1")
    List<MudeopenRLocUbicazione> retrieveAllByIdIstanza(Long idIstanza);

    @Query(value = "SELECT * FROM mudeopen_r_loc_ubicazione WHERE id_istanza = ?1 AND f1_personalizzato_dettaglio = 'principale' LIMIT 1", nativeQuery = true)
    MudeopenRLocUbicazione retrieveMainByIdIstanza(Long idIstanza);

}