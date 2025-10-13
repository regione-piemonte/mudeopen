/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTemplateRicevutaIstanza;

@Repository
public interface MudeDTemplateRicevutaIstanzaRepository extends BaseRepository<MudeDTemplateRicevutaIstanza, Long> {
    @Query(value="select mdtri.* "
    	+ "from mudeopen_d_template_ricevuta_istanza mdtri "
    	+ " inner join mudeopen_d_tipo_istanza mdti on mdtri.id_template_ricevuta_istanza = mdti.id_template_ricevuta_istanza "
    	+ " inner join mudeopen_t_istanza mti on mdti.codice = mti.id_tipo_istanza "
    	+ "WHERE mti.id_istanza = :idIstanza", nativeQuery = true)
    MudeDTemplateRicevutaIstanza findTemplateForIstanza(@Param("idIstanza") Long idIstanza);

}