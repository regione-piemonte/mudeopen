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

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIndirizzo;

@Repository
//public interface MudeTIndirizzoRepository extends CrudRepository<MudeTIndirizzo, Long>, JpaSpecificationExecutor<MudeTIndirizzo> {
public interface MudeTIndirizzoRepository extends BaseRepository<MudeTIndirizzo, Long>{

	/*
    @Query("SELECT o.mudeDComune.idComune FROM MudeTIndirizzo o WHERE o.id = :idIndirizzo")
    Long getComuneByIdIndirizzo(Long idIndirizzo);
    */
	
    List<MudeTIndirizzo> findByMudeTContattoAndDataFineIsNull(MudeTContatto mudeTContatto);

    @Query(value = "SELECT c.id_residenza FROM MudeTIndirizzo c WHERE (:mudeDComuneList is null or c.id_comune_residenza in (:mudeDComuneList)) and (:idDug is null or c.idDug = :idDug)"
			+ " and (:duf is null or c.localita = CAST(:duf AS text))", nativeQuery = true)
	List<Long> findIdResidenzaByParam(List<Long> mudeDComuneList, Long idDug, String duf);
    
    @Query("SELECT o FROM MudeTIndirizzo o WHERE o.id = :id")
	Optional<MudeTIndirizzo> findById(@Param("id") Long id);

    void deleteAllByMudeTContattoId(Long idContatto);

    @Query(value = "SELECT"
    	+ "  a.indirizzo, "
    	+ "  COALESCE(a.numero_civico, '') as numero_civico, "
    	+ "  COALESCE(a.cap, '') as cap, "
    	+ "  COALESCE(a.localita, '') as localita, "
    	+ "  c.denominazione "
    	+ "  from MUDEOPEN_T_INDIRIZZO as a "
		+ "    inner join MUDEOPEN_T_ISTANZA  as b on a.id_indirizzo = b.id_indirizzo "
		+ "     inner join MUDEOPEN_D_DUG as c on a.id_dug = c.id_dug "
		+ "  WHERE b.id_istanza = :idIstanza", nativeQuery = true)
    List<Object[]> findUbicazioneByIdIstanza(@Param("idIstanza") Long idIstanza);

    @Query("SELECT i.indirizzo FROM MudeTIstanza i WHERE i.id = :idIstanza")
	MudeTIndirizzo findByIdIstanza(@Param("idIstanza") Long idIstanza);
	@Query(value = "SELECT i.indirizzo FROM MUDEOPEN_T_INDIRIZZO i WHERE i.id_indirizzo = :idIndirizzo LIMIT 1", nativeQuery = true)
	String findIndirizzoStringById(@Param("idIndirizzo") Long idIndirizzo);
}