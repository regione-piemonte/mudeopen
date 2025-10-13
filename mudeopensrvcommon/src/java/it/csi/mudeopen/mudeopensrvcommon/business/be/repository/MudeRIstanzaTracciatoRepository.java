/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRIstanzaTracciato;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MudeRIstanzaTracciatoRepository extends BaseRepository<MudeRIstanzaTracciato,Long> {

    List<MudeRIstanzaTracciato> findAllByDataFineNullAndMudeTIstanza_Id(Long idIstanza);

    @Query(value = "select mrit.xml_tracciato from mudeopen_r_istanza_tracciato mrit \n" +
            "join mudeopen_d_tipo_tracciato mdtt on mrit.id_tipo_tracciato = mdtt.id_tipo_tracciato \n" +
            "where mrit.id_istanza = :idIstanza and mrit.data_fine is null and mdtt.codice = :tipoTracciato", nativeQuery = true)
    String findTracciatoXMLByIdIstanza(@Param("idIstanza") Long idIstanza, @Param("tipoTracciato") String tipoTracciato);

    @Query("select o from MudeRIstanzaTracciato o where o.mudeTIstanza.id = :idIstanza and o.mudeDTipoTracciato.id = :idTipoTracciato and o.dataFine is null")
    MudeRIstanzaTracciato findByIstanzaAndTipoTracciato(@Param("idIstanza") Long idIstanza, @Param("idTipoTracciato")Long idTipoTracciato);

    @Modifying(clearAutomatically = true)
    @Query("update MudeRIstanzaTracciato set datFine = now()")
    void updateIstanzaTracciatoDataFineByIstanza(Long idIstanza);

    @Modifying
    void deleteByMudeTIstanza_id(@Param("idIstanza") Long idIstanza);

}