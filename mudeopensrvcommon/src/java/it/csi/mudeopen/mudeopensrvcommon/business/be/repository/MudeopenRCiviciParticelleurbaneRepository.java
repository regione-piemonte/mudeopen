/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRCiviciParticelleurbane;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MudeopenRCiviciParticelleurbaneRepository extends BaseRepository<MudeopenRCiviciParticelleurbane, Integer> {

    @Query(value = "select mtfc from MudeopenRCiviciParticelleurbane mtfc where mtfc.fkCellula = ?1")
    List<MudeopenRCiviciParticelleurbane> findByFkCellula(String fkCellula);

}
