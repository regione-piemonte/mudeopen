/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRSpeciePraticaTipoIntervento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MudeRSpeciePraticaTipoInterventoRepository extends BaseRepository<MudeRSpeciePraticaTipoIntervento, Long> {

    List<MudeRSpeciePraticaTipoIntervento> findByMudeDSpeciePratica_CodiceAndAbilitatoIsTrueOrderByMudeDTipoIntervento_descrizione(String codiceSpeciePratica);

    List<MudeRSpeciePraticaTipoIntervento> findByMudeDTipoIntervento_CodiceAndAbilitatoIsTrue(String codiceTipoIntervento);

    Optional<MudeRSpeciePraticaTipoIntervento> findByMudeDSpeciePratica_CodiceAndAbilitatoIsTrue(String codiceSpeciePratica);
}