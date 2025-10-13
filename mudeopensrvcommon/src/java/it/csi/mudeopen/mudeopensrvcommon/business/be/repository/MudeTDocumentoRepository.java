/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTDocumento;
@Repository
public interface MudeTDocumentoRepository extends BaseRepository<MudeTDocumento, Long> {

    @Query("SELECT o FROM MudeTDocumento o WHERE o.pratica.id = :idPratica and o.dataAnnullamento is null")
    List<MudeTDocumento> findAllByPratica(@Param("idPratica") Long idPratica);

    @Query("SELECT o FROM MudeTDocumento o WHERE o.id = :id")
    Optional<MudeTDocumento> findById(@Param("id") Long id);

    //@Query("SELECT o FROM MudeTDocumento o WHERE o.id = :id and o.user.idUser = :idUser")
    //Optional<MudeTDocumento> findByIdAndUser(@Param("id") Long id, @Param("idUser") Long idUser);

    @Query("SELECT o.fileContent FROM MudeTDocumento o WHERE o.id = :id")
    byte[] findFileByIdDocumento(@Param("id") Long id);

    @Query("SELECT o FROM MudeTDocumento o WHERE o.pratica.id = :idPratica AND o.nomeFileDocumento = :filename")
    Optional<MudeTDocumento> findByIdPraticaAndFilename(@Param("idPratica") Long idPratica, @Param("filename") String filename);

    @Query("SELECT o FROM MudeTDocumento o WHERE o.fileUID = :fileUID")
    Optional<MudeTDocumento> findByFileUID(@Param("fileUID") String fileUID);

    @Modifying
    @Query("DELETE FROM MudeTDocumento WHERE pratica.id = :idPratica")
    void deleteByPratica(@Param("idPratica") Long idPratica);

    @Query("SELECT o FROM MudeRNotificaDocumento nd INNER JOIN nd.mudeTDocumento o WHERE nd.mudeTNotifica.id = :idNotifica and o.dataAnnullamento is null")
    List<MudeTDocumento> findAllByIdNotifica(@Param("idNotifica") Long idNotifica);

    @Query(value = "SELECT mtd.* FROM mudeopen_r_istanza_pratica mrip "
    		+ "            INNER JOIN mudeopen_t_documento mtd ON mtd.id_pratica = mrip.id_pratica "
    		+ "            INNER JOIN mudeopen_d_tipo_docpa mdtd ON mtd.id_tipo_documento = mdtd.id_tipo_docpa AND cod_tipo_docpa = 'IDF-ACQ' "
    		+ "    WHERE mrip.id_istanza = :idIstanza", nativeQuery = true)
    MudeTDocumento retrieveIdfDocument(@Param("idIstanza") Long idIstanza);
}