/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDProvincia;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDRegione;

@Repository
public interface MudeDProvinciaRepository extends BaseRepository<MudeDProvincia, Long> {

    @Query("Select u from MudeDProvincia u where u.mudeDRegione=?1 and ((u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW()))  ORDER BY u.denomProvincia ASC")
	List<MudeDProvincia> findByMudeDRegioneAndFineValiditaOrderByDenomProvinciaAsc(MudeDRegione mudeRegione);

    @Query("Select u from MudeDProvincia u where ((u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW()))  ORDER BY u.denomProvincia ASC")
	List<MudeDProvincia> findByFineValiditaOrderByDenomProvinciaAsc();

    @Query("Select u from MudeDProvincia u where u.mudeDRegione=?1 ORDER BY u.denomProvincia ASC")
	List<MudeDProvincia> findByMudeDRegioneOrderByDenomProvinciaAsc(MudeDRegione mudeRegione);

    @Query("Select u from MudeDProvincia u where u.mudeDRegione=?1 and ((u.fineValidita is null or u.fineValidita>?2) and (u.inizioValidita is null or u.inizioValidita<=?2))  ORDER BY u.denomProvincia ASC")
	List<MudeDProvincia> findByMudeDRegioneAndValidaInDataOrderByDenomProvinciaAsc(MudeDRegione mudeRegione, Date dataOraAccertamento);

    List<MudeDProvincia> findAllByOrderByDenomProvinciaAsc();
    @Query(value = "SELECT DISTINCT mdp.* FROM mudeopen_d_provincia mdp "
	            + "    INNER JOIN mudeopen_d_comune mdc ON mdc.id_provincia = mdp.id_provincia "
	            + "  WHERE mdc.id_comune IN (SELECT id_comune FROM mudeopen_r_ente_comune_tipo_istanza WHERE (data_fine IS NULL OR data_fine > NOW()) AND competenza_principale=true)"
	            + " ORDER BY denom_provincia", nativeQuery = true )
    List<MudeDProvincia> findProvinciaForComuniRegistered();

}