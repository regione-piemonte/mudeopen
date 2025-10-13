/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTGeecoSessionClbk;
@Repository
public interface MudeTGeecoSessionCllbkRepository extends BaseRepository<MudeTGeecoSessionClbk, Long> {

	/*
	List<MudeTGeecoSessionClbk> findByIdIstanzaAndIdLocalizzazione(Long idFascicolo, Long idLocalizzazione);

	List<MudeTGeecoSessionClbk> findByIdIstanzaAndSessioneGeeco(Long idIstanza, String geecoSessioinID);

	List<MudeTGeecoSessionClbk> findByIdIstanza(Long idIstanza);
	
	MudeTGeecoSessionClbk findTop1ByMudeTUserOrderByDataInizioDesc(MudeTUser mudeTUser);
	*/

	@Query(
	value = "select cc.cod_belfiore_comune FROM mudeopen_t_geeco_session ss inner join mudeopen_t_istanza ii on ii.id_istanza=ss.id_istanza inner join mudeopen_d_comune cc on cc.id_comune=ii.id_comune where ss.sessione_geeco=:geecoSessionId"
	, nativeQuery = true)	
	String  findBelfioreByGeecoSessinId(@Param("geecoSessionId")  String geecoSessionId);
	
	
	@Query(
	value = "select cc.cod_belfiore_comune FROM mudeopen_t_istanza ii  inner join mudeopen_d_comune cc on cc.id_comune=ii.id_comune where ii.id_istanza=:idSessione"
	, nativeQuery = true)	
	String  findBelfioreByMudeIstanzaId(@Param("idSessione") String idSessione);

	@Query(value = "SELECT id_istanza FROM mudeopen_t_geeco_session where sessione_geeco = :geecoSessionId", nativeQuery = true)
	Long findIdIstanzaByGeecoSessionId(@Param("geecoSessionId")  String geecoSessionId);

    
}
