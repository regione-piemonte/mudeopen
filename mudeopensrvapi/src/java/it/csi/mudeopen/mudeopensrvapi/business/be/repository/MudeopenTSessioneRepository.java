/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.business.be.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvapi.business.be.entity.MudeopenTSessione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.BaseRepository;

/**
 * The interface Mude t sessione repository.
 */
@Repository
public interface MudeopenTSessioneRepository extends BaseRepository<MudeopenTSessione, Long> {
	
	MudeopenTSessione findByIdSessione(UUID token);

	@Query(value = "select cast(gen_random_uuid () as varchar)", nativeQuery = true)
	String getUUID();

	/**
     * Find by idIstanza mude t contatto professionista istanza.
     *
     * @param idIstanza the id istanza
      the mude t contatto
     */
	@Query(value = "SELECT c.cognome, c.nome "
    	+ "  FROM MUDEOPEN_R_ISTANZA_UTENTE mriu "
    	+ "       INNER JOIN MUDEOPEN_D_ABILITAZIONE mda ON (mriu.id_abilitazione = mda.id_abilitazione AND mda.CODICE_ABILITAZIONE in ('PM_RUP_PM_OBB','PM_RUP_PM_OPZ'))"
        + "       INNER JOIN MUDEOPEN_T_USER u ON mriu.id_utente = u.id_user "
        + "       INNER JOIN mudeopen_t_contatto c ON c.id_user=u.id_user AND u.cf = c.cf AND data_cessazione IS NULL "
        + "       INNER JOIN MUDEOPEN_R_ISTANZA_SOGGETTO si ON si.id_soggetto=c.id_contatto AND si.id_istanza=mriu.id_istanza "
        + "  WHERE mriu.data_fine IS NULL AND mriu.id_istanza = :idIstanza", nativeQuery = true)
	Object[] findProfessionistaByIdIstanza1(@Param("idIstanza") Long idIstanza);
	
	@Query(value = " SELECT c.* "
	    	+ "  FROM MUDEOPEN_R_ISTANZA_UTENTE mriu "
	    	+ "       INNER JOIN MUDEOPEN_D_ABILITAZIONE mda ON (mriu.id_abilitazione = mda.id_abilitazione AND mda.CODICE_ABILITAZIONE in ('PM_RUP_PM_OBB','PM_RUP_PM_OPZ'))"
	        + "       INNER JOIN MUDEOPEN_T_USER u ON mriu.id_utente = u.id_user "
	        + "       INNER JOIN mudeopen_t_contatto c ON c.id_user=u.id_user AND u.cf = c.cf AND data_cessazione IS NULL "
	        + "       INNER JOIN MUDEOPEN_R_ISTANZA_SOGGETTO si ON si.id_soggetto=c.id_contatto AND si.id_istanza=mriu.id_istanza "
	        + "  WHERE mriu.data_fine IS NULL AND mriu.id_istanza = :idIstanza", nativeQuery = true)
    it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto findProfessionistaByIdIstanza(@Param("idIstanza") Long idIstanza);
	@Query(value = "select mtp.id_pratica from mudeopen_t_pratica mtp\n" + 
			"inner join mudeopen_r_istanza_pratica mrip on mrip.id_pratica = mtp.id_pratica\n" + 
			"inner join mudeopen_t_istanza mti on mti.id_istanza = mrip.id_istanza\n" + 
			"inner join mudeopen_d_comune mdc on mdc.id_comune = mti.id_comune\n" + 
			"where mtp.numero_pratica = :numeroPraticaComunale \n" + 
			"and mtp.anno_pratica = :annoPraticaComunale \n" + 
			"and mdc.cod_istat_comune = :codIstatComune", nativeQuery = true)
	Long getIdPratica(@Param("numeroPraticaComunale") String numeroPratica, @Param("annoPraticaComunale") String annoPratica, @Param("codIstatComune") String istatComune);

	
}