/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDComune;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDProvincia;

@Repository
public interface MudeDComuneRepository extends BaseRepository<MudeDComune, Long> {

    @Query("Select u from MudeDComune u where u.mudeDProvincia=?1 and ((u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW())) ORDER BY u.denomComune ASC")
	List<MudeDComune> findByMudeDProvinciaAndFineValiditaOrderByDenomComuneAsc(MudeDProvincia mudeDProvincia);

    @Query("Select u from MudeDComune u where u.mudeDProvincia=?1 ORDER BY u.denomComune ASC")
	List<MudeDComune> findByMudeDProvinciaOrderByDenomComuneAsc(MudeDProvincia mudeDProvincia);

    @Query("Select u.idComune from MudeDComune u where u.mudeDProvincia=?1 ORDER BY u.denomComune ASC")
	List<Long> findIdComuneByMudeDProvinciaOrderByDenomComuneAsc(MudeDProvincia mudeDProvincia);

    @Query("Select u from MudeDComune u where u.istatComune=?1 and ((u.fineValidita is null or u.fineValidita>NOW()) and (u.inizioValidita is null or u.inizioValidita<=NOW()))")
	MudeDComune findByIstatComuneAndFineValidita(String istatComune);

    @Query("Select u from MudeDComune u where u.istatComune=?1 and ((u.fineValidita is null or u.fineValidita>?2) and (u.inizioValidita is null or u.inizioValidita<=?2))")
	MudeDComune findByIstatComuneAndFineValiditaDataNascita(String istatComune, Date dataNascita);

    MudeDComune findByIdComune(Long idComune);

    @Query("Select u from MudeDComune u where u.mudeDProvincia=?1 and ((u.fineValidita is null or u.fineValidita>?2) and (u.inizioValidita is null or u.inizioValidita<=?2)) ORDER BY u.denomComune ASC")
	List<MudeDComune> findByMudeDProvinciaAndValidaOrderByDenomComuneAsc(MudeDProvincia mudeDProvincia,
			Date convertToDateViaInstant);

    //@Query(value = "SELECT * FROM mudeopen_d_comune mdc WHERE denom_comune = :idComune ORDER BY fine_validita DESC LIMIT 1", nativeQuery = true)
    //MudeDComune findMudeDComuneByDenomComune(@Param("idComune") Long idComune);

    @Query(value = "SELECT * FROM mudeopen_d_comune "
	            + "  WHERE ((fine_validita IS NULL OR fine_validita > now()) AND (inizio_validita IS NULL OR inizio_validita <= now())) AND id_provincia = :idProvincia "
	            + "    AND id_comune IN (SELECT id_comune FROM mudeopen_r_ente_comune_tipo_istanza WHERE (data_fine IS NULL OR data_fine > NOW()) AND competenza_principale=true)"
	            + " ORDER BY denom_comune", nativeQuery = true )
    List<MudeDComune> findComuniRegisteredForProvincia(@Param("idProvincia") Long idProvincia);

    @Query(value = "SELECT CONCAT(s.codice, '~', s.descrizione) "
    		+ "  FROM mudeopen_d_comune c "
    		+ "    INNER JOIN mudeopen_d_cat_rischio_sismico s ON c.rischio_sismico = s.codice AND s.data_fine IS NULL "
    		+ "  WHERE ((c.fine_validita IS NULL OR c.fine_validita > now()) AND (c.inizio_validita IS NULL OR c.inizio_validita <= now())) AND cod_belfiore_comune = :codiceBelfiore", nativeQuery = true )
    String getComuneRischioSismico(@Param("codiceBelfiore") String codiceBelfiore);

    @Query(value = "SELECT * FROM mudeopen_d_comune "
            + "  WHERE id_provincia = :idProvincia "
            + "    AND ("
            + "      ((fine_validita IS NULL OR fine_validita > now()) AND (inizio_validita IS NULL OR inizio_validita <= now())) "
            + "      OR validita_fiscale = true"
            + "    )"
            + " ORDER BY denom_comune", nativeQuery = true )
    List<MudeDComune> findComuniFiscalmenteValidi(@Param("idProvincia") Long idProvincia);
	@Query(value = "SELECT c.denom_comune FROM mudeopen_d_comune c where id_comune = :idComune", nativeQuery = true )
	String getComuneDenominazioneById(@Param("idComune") Long idComune);

	@Query(value = "SELECT cosmo_serie_fascicoli FROM mudeopen_d_comune c where id_comune = :idComune", nativeQuery = true )
	String getSerieFascicoliById(@Param("idComune") Long idComune);
}