/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRTipoIstanzaTipoOpera;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MudeRTipoIstanzaTipoOperaRepository extends BaseRepository<MudeRTipoIstanzaTipoOpera, Long> {

    List<MudeRTipoIstanzaTipoOpera> findByMudeDTipoIstanza_CodiceAndAbilitatoIsTrueOrderByMudeDTipoOpera_descrizione(String codiceTipoIstanza);

    List<MudeRTipoIstanzaTipoOpera> findByMudeDTipoOpera_CodiceAndAbilitatoIsTrue(String codiceTipoOpera);

    /*
    select distinct m.id_tipo_istanza, o.id_tipo_opera from mudeopen_r_tipo_istanza_specie_pratica m
inner join mudeopen_r_specie_pratica_tipo_opera o ON m.id_specie_pratica = o.id_specie_pratica
where (m.regione = 'PIEMONTE' and m.abilitato = true ) and o.abilitato = true
     */
    @Query(value = "select distinct o.id_tipo_opera from mudeopen_r_tipo_istanza_specie_pratica m "
            + "inner join mudeopen_r_specie_pratica_tipo_opera o ON m.id_specie_pratica = o.id_specie_pratica "
            + "where (m.regione = 'PIEMONTE' and m.abilitato = true ) and o.abilitato = true and m.id_tipo_istanza = :codiceTipoIstanza", nativeQuery = true)
    List<String>findByTipoIstanzaViaSpeciePratica(String codiceTipoIstanza);

    @Query(value = "select distinct m.id_tipo_istanza from mudeopen_r_tipo_istanza_specie_pratica m "
            + "inner join mudeopen_r_specie_pratica_tipo_opera o ON m.id_specie_pratica = o.id_specie_pratica "
            + "where (m.regione = 'PIEMONTE' and m.abilitato = true ) and o.abilitato = true and o.id_tipo_opera = :codiceTipoOpera", nativeQuery = true)
    List<String> findByTipoOperaViaSpeciePratica(String codiceTipoOpera);
}