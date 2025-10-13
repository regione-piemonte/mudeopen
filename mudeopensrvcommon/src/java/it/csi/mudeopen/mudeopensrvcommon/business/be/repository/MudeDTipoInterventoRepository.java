/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoIntervento;

@Repository
public interface MudeDTipoInterventoRepository extends BaseDictionaryRepository<MudeDTipoIntervento, String> {

    @Query(value = "SELECT * FROM mudeopen_d_tipo_intervento WHERE data_fine IS NULL ORDER BY cardinal_pos, codice", nativeQuery = true)
	List<MudeDTipoIntervento> findAllOrdered();

    MudeDTipoIntervento findByDescrizione(String descrizione);

    @Query(value = "select mdti.* from mudeopen_d_tipo_intervento mdti "
            + "inner join mudeopen_r_tipo_istanza_tipo_intervento mrtiti on mrtiti.codice_tipo_intervento = mdti.codice "
            + "where mdti.data_fine is null and mrtiti.data_fine is null and mrtiti.codice_tipo_istanza = :codiceTipoIstanza", nativeQuery = true)
    List<MudeDTipoIntervento> findByTipoIstanzaForCreaFascicoloIstanza(@Param("codiceTipoIstanza")String codiceTipoIstanza);

    @Query(value = "select a.* "
    	+ " from MUDEOPEN_D_TIPO_INTERVENTO as a "
    	+ "   inner join MUDEOPEN_T_FASCICOLO as b on a.codice = b.id_tipo_intervento "
    	+ "   inner join MUDEOPEN_T_ISTANZA as c on b.id_fascicolo = c.id_fascicolo "
    	+ " where c.id_istanza = :idIstanza ", nativeQuery = true)
    MudeDTipoIntervento findByIdIstanza(@Param("idIstanza")Long idIstanza);

}