/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package  it.csi.mudeopen.mudeopensrvapi.util;

/**
 * The type Constants.
 */
public class Constants {
    /**
     * The constant COMPONENT_NAME.
     */
    public final static String COMPONENT_NAME = "mudeopensrvapi";
    /**
     * The constant LOGGER_NAME.
     */
    public final static String LOGGER_NAME = "mudeopensrvapi";

    public final static String HEADER_MUDE_TIPO_QUADRO = "mude-tipo-quadro";

    /**
     * The constant HEADER_USER_CF.
     */
    public final static String HEADER_USER_CF = "user-cf";
    /**
     * The constant X_REQUEST_ID.
     */
    public final static String X_REQUEST_ID = "X-Request-ID";
    /**
     * The constant X_FORWARDER_FOR.
     */
    public final static String X_FORWARDED_FOR = "X-Forwarded-For";
    /**
     * The constant LOG_HEADER_NAME.
     */
    public static final String LOG_HEADER_NAME = "log-header";

    public static final String ESITO_OK = "OK";

    public static final String DATE_FORMAT_ISO_8601 = "yyyy-MM-dd'T'HH:mm'Z'";	// Quoted "Z" to indicate UTC, no timezone offset

    //public static final String TOKEN_NON_VALIDO = "TOKEN NON VALIDO";

    //public static final String ERRORE_TOKEN_NON_VALIDO = "ERRORE - TOKEN NON VALIDO";

    public static final String COMUNE_OBBLIGATORIO = "[CampoObbligatorioException] COMUNE OBBLIGATORIO";

    public static final String COMUNE_NON_ABILILITATO_FRUITORE = "[ComuneNonAbilitatoException] COMUNE NON ABILITATO PER IL FRUITORE";

    public static final String NUMERO_PRATICA_OBBLIGATORIO = "[CampoObbligatorioException] Numero DettaglioPraticaVO Comunale";

    public static final String ANNO_PRATICA_OBBLIGATORIO = "[CampoObbligatorioException] Anno DettaglioPraticaVO Comunale";

    public static final String PRATICA_NON_TROVATA = "[PraticaEsistenteException] PRATICA NON PRESENTE";

    public static final String FILE_OBBLIGATORIO = "[CampoObbligatorioException] FILE DOCUMENTO OBBLIGATORIO";

    public static final String TIPO_DOCUMENTO_OBBLIGATORIO = "[CampoObbligatorioException] TIPO DOCUMENTO OBBLIGATORIO";

    public static final String TIPO_DOCUMENTO_NON_TROVATO = "[DocumentoNonValidoException] TIPO DOCUMENTO NON TROVATO";

    public static final String DOCUMENTO_NON_TROVATO = "[DocumentoNonValidoException] DOCUMENTO NON TROVATO";

    /**
     * Costante contenete la lista dei CF ..abilitati..
     *
     */
    public static final String CONFIG_MUDE_ALLOWED_CF_PM = "MUDE_ALLOWED_CF_PM";

    public static final String CONFIG_SIGMATER_COMUNE_RETEMPTION = "SIGMATER_COMUNE_RETEMPTION";

    public static final String CONFIG_GEECO_UUID_COTO = "GEECO_UUID_COTO";

    public static final String CONFIG_GEECO_UUID_EXTRA_COTO = "GEECO_UUID_EXTRA_COTO";
}