/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.util;
public class Constants {
	public static String _environment = "";
    public static final String CONFIG_KEY_ENVIRONMENT = "codice_ambiente";
	public static String _pdf_istanza_checks = "";
    public static final String CONFIG_KEY_SKIP_PDF_CHECKS = "PDF_ISTANZA_CHECKS";
    public static String _mude_allowed_cf_pm = "";
    public static final String CONFIG_MUDE_ALLOWED_CF_PM = "MUDE_ALLOWED_CF_PM";
    public final static String COMPONENT_NAME = "mudeopensrvcommon";
    public final static String LOGGER_NAME = "mudeopensrvcommon";
    public final static String HEADER_MUDE_TIPO_QUADRO = "mude-tipo-quadro";
    public final static String HEADER_USER_CF = "user-cf";
    public static final String IRIDE_ID_SESSIONATTR = "iride2_id";
    public static final String AUTH_ID_MARKER = "Shib-Iride-IdentitaDigitale";
    public static final String USERINFO_SESSIONATTR = "appDatacurrentUser";
    public final static String X_REQUEST_ID = "X-Request-ID";
    public final static String X_FORWARDED_FOR = "X-Forwarded-For";
    public final static String MUDEOPEN_API_SCOPE = "Mudeopen-Api-Scope";
    public final static String LOG_HEADER_NAME = "log-header";
    public final static String IDENTITY_CONTENT_ENCODING = "identity";
    public static final String NOT_VALID = "not valid";
    //public static final String CONFIG_KEY_ALLEGATI_TENANT_NAME = "MUDE_INDEX_TENANT_NAME";
    public static final String CONFIG_KEY_ALLEGATI_TENANT_UUID = "MUDE_INDEX_TENANT_UUID";
    public static long  _config_key_allegati_max_size = 50; // default 50mb
    public static final String CONFIG_KEY_ALLEGATI_MAX_SIZE = "MUDE_INDEX_MAX_MB_FILE";
    public static String _config_key_allegati_allowed_file_extensions = "application/pdf,.pdf,.p7m"; // default
    public static final String CONFIG_KEY_ALLEGATI_ALLOWED_FILE_EXTENSIONS = "MUDE_ALLOWED_FILE_EXTENSIONS";
    public static String _config_key_logouturl = "application/pdf,.pdf,.p7m"; // default
    public static final String CONFIG_KEY_MUDE_LOGOUT_URL = "MUDE_LOGOUT_URL";
    //public static final String BASE_TEMPLATE_DIRECTORY_PATH = "directory_template";
    public static final String NUMERO_MUDE = "numeroMude";
    public static final String SCOPE_WS = "ws";
    
	public static final String ERRORE_BUSINESS = "errore_business";
	public static final String ERRORE_INTERNO = "errore_interno";
	public static final String ERRORE_VALIDAZIONE = "errore_validazione";
	
 }