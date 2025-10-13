/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl.LogTracciatoServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Objects;

@Repository("customTracciatoXMLRepository")
public class CustomTracciatoXMLRepository {
    private static Logger logger = Logger.getLogger(LogTracciatoServiceImpl.class.getCanonicalName());
    @Autowired
    private EntityManager em;

    public String loadSingleData(String tablename, String targetColumn, String whereColumn, String whereValue) {
        try {
            if (StringUtils.isNotBlank(whereValue) && !whereValue.equalsIgnoreCase("null")) {
                String queryAsString = new StringBuilder("SELECT ").append(targetColumn).append(" FROM ").append(tablename).append(" WHERE ").append(whereColumn).append("='").append(whereValue).append("'").toString();
                Query query = em.createNativeQuery(queryAsString);
                return String.valueOf(query.getSingleResult());
            } else
                return "";
        }
        catch(NoResultException nre){
            return "";
        }
    }

    public String loadRifProcMudeAggiornamentoNotPrel(String codiceFascicolo) {
        try {
            if (StringUtils.isNotBlank(codiceFascicolo) ) {
                String queryAsString = "select mti.codice_istanza from mudeopen_t_istanza mti "
                        + "inner join mudeopen_t_fascicolo mtf on mtf.id_fascicolo = mti.id_fascicolo "
                        + "inner join mudeopen_r_istanza_stato mris on mris.id_istanza = mti.id_istanza "
                        + "inner join mudeopen_d_stato_istanza mdsi on mdsi.codice = mris.codice_stato_istanza "
                        + "where mtf.codice_fascicolo = ? and mti.id_tipo_istanza = 'NOT-PREL' and mdsi.codice = 'APA' "
                        + "order by mti.id_istanza asc limit 1";

                Query query = em.createNativeQuery(queryAsString).setParameter(1, codiceFascicolo);
                return String.valueOf(query.getSingleResult());
            } else
                return "";
        }
        catch(NoResultException nre){
            return "";
        }
    }

    @PostConstruct
    public void postConstruct() {
        Objects.requireNonNull(em);
    }

}