/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.enumeration;

import java.util.Arrays;

/**
 * The enum Funzioni abilitazioni enum.
 */
public enum FunzioniAbilitazioniEnum {
    /**
     * Crea ist funzioni abilitazioni enum.
     */
    CREA_IST("CREA_IST"),
    /**
     * Comp tit funzioni abilitazioni enum.
     */
    COMP_TIT("COMP_TIT"),
    /**
     * Comp sogg coinv funzioni abilitazioni enum.
     */
    COMP_SOGG_COINV("COMP_SOGG_COINV"),
    /**
     * Ind pm funzioni abilitazioni enum.
     */
    IND_PM("IND_PM"),
    /**
     * Ind collab pm funzioni abilitazioni enum.
     */
    IND_COLLAB_PM("IND_COLLAB_PM"),
    /**
     * Comp assev funzioni abilitazioni enum.
     */
    COMP_ASSEV("COMP_ASSEV"),
    /**
     * Nomina prof spec funzioni abilitazioni enum.
     */
    NOMINA_PROF_SPEC("NOMINA_PROF_SPEC"),
    /**
     * Nomina consult funzioni abilitazioni enum.
     */
    NOMINA_CONSULT("NOMINA_CONSULT"),
    /**
     * Upload alleg funzioni abilitazioni enum.
     */
    UPLOAD_ALLEG("UPLOAD_ALLEG"),
    /**
     * Consulta alleg funzioni abilitazioni enum.
     */
    CONSULTA_ALLEG("CONSULTA_ALLEG"),
    /**
     * Elimina alleg funzioni abilitazioni enum.
     */
    ELIMINA_ALLEG("ELIMINA_ALLEG"),
    /**
     * Elimina ist funzioni abilitazioni enum.
     */
    ELIMINA_IST("ELIMINA_IST"),
    /**
     * Down pdf ist funzioni abilitazioni enum.
     */
    DOWN_PDF_IST("DOWN_PDF_IST"),
    /**
     * Firma ist funzioni abilitazioni enum.
     */
    FIRMA_IST("FIRMA_IST"),
    /**
     * Upl ist funzioni abilitazioni enum.
     */
    UPL_IST("UPL_IST"),
    /**
     * Invia ist funzioni abilitazioni enum.
     */
    INVIA_IST("INVIA_IST"),
    /**
     * Consulta ist funzioni abilitazioni enum.
     */
    CONSULTA_IST("CONSULTA_IST"),
    /**
     * Elenco ist fascic funzioni abilitazioni enum.
     */
    ELENCO_IST_FASCIC("ELENCO_IST_FASCIC"),
    /**
     * Crea fascic funzioni abilitazioni enum.
     */
    CREA_FASCIC("CREA_FASCIC"),
    /**
     * Cons ist all fascic funzioni abilitazioni enum.
     */
    CONS_IST_ALL_FASCIC("CONS_IST_ALL_FASCIC"),
    /**
     * Abilita crea ist funzioni abilitazioni enum.
     */
    ABILITA_CREA_IST("ABILITA_CREA_IST"),
    /**
     * Trasf propr fascic funzioni abilitazioni enum.
     */
    TRASF_PROPR_FASCIC("TRASF_PROPR_FASCIC"),
    /**
     * Modifica dati fascic funzioni abilitazioni enum.
     */
    MODIFICA_DATI_FASCIC("MODIFICA_DATI_FASCIC"),
    /**
     * Chiudi fascic funzioni abilitazioni enum.
     */
    CHIUDI_FASCIC("CHIUDI_FASCIC"),
    /**
     * Elimina fascic funzioni abilitazioni enum.
     */
    ELIMINA_FASCIC("ELIMINA_FASCIC"),
	
	RIPORTA_IN_BOZZA("RIPORTA_IN_BOZZA"),
	CANCELLA_ISTANZA("CANCELLA_ISTANZA"),
	CONSOLIDA_ISTANZA("CONSOLIDA_ISTANZA"),
	ELIMINA_FASCICOLO("ELIMINA_FASCICOLO"),
	
	BE_NUOVA_NOTIFICA("BE_NUOVA_NOTIFICA"),
	
	WS_RICERCA_ISTANZE	("WS_RICERCA_ISTANZE"),
	WS_DATI_ISTANZA	("WS_DATI_ISTANZA"),
	WS_ELENCO_ALLEGATI	("WS_ELENCO_ALLEGATI"),
	WS_ELENCO_NOTIFICHE	("WS_ELENCO_NOTIFICHE"),
	WS_VIEW_NOTIFICA	("WS_VIEW_NOTIFICA"),
	WS_ALLEGATI_NOTIFICA	("WS_ALLEGATI_NOTIFICA"),
	WS_STATI_IST_AMMESSI	("WS_STATI_IST_AMMESSI"),
	WS_STATI_ISTANZA	("WS_STATI_ISTANZA"),
	WS_EDIT_STATO_IST	("WS_EDIT_STATO_IST"),
	WS_RICERCA_PRATICHE	("WS_RICERCA_PRATICHE"),
	WS_DOCUMENTI_PRATICA	("WS_DOCUMENTI_PRATICA"),
	WS_VIEW_TIPO_DOCPA	("WS_VIEW_TIPO_DOCPA"),
	WS_RICERCA_RUOLI	("WS_RICERCA_RUOLI"),
	WS_VIEW_TIPO_NOTIF	("WS_VIEW_TIPO_NOTIF"),
	WS_SCARICO_XML	("WS_SCARICO_XML"),
	WS_ESTRAI_FILE_IST	("WS_ESTRAI_FILE_IST"),
	WS_ESTRAI_ALL_IST	("WS_ESTRAI_ALL_IST"),
	WS_ESTRAI_DOCUMENTO	("WS_ESTRAI_DOCUMENTO"),
	WS_ALLEGA_DOCUMENTO	("WS_ALLEGA_DOCUMENTO"),
	WS_ELIMINA_DOCUMENTO	("WS_ELIMINA_DOCUMENTO"),
	WS_INSERISCI_NOTIF	("WS_INSERISCI_NOTIF"),
	WS_INVIO_ISTANZA	("WS_INVIO_ISTANZA"),
	WS_VIEW_PROTOCOLLO	("WS_VIEW_PROTOCOLLO"),
	WS_NUMERO_MUDE	("WS_NUMERO_MUDE")
	;

    private final String description;

    FunzioniAbilitazioniEnum(String description) {
        this.description = description;
    }

    /**
     * Gets description.
     *
      the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Find by description funzioni abilitazioni enum.
     *
     * @param descrizione the descrizione
      the funzioni abilitazioni enum
     */
    public static FunzioniAbilitazioniEnum findByDescription(final String descrizione) {
        return Arrays.stream(values()).filter(value -> value.getDescription().equals(descrizione)).findFirst().orElse(null);
    }
}