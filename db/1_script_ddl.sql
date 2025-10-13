--
-- DDL STRUCTURE
--
--
-- Name: MUDEOPEN; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE "MUDEOPEN" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.UTF-8';


ALTER DATABASE "MUDEOPEN" OWNER TO postgres;

\connect "MUDEOPEN"


--
-- Name: MUDEOPEN; Type: DATABASE PROPERTIES; Schema: -; Owner: postgres
--

ALTER DATABASE "MUDEOPEN" SET search_path TO '$user', 'public', 'topology';


\connect "MUDEOPEN"



--
-- Name: csi_log_audit; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE csi_log_audit (
    id_log integer NOT NULL,
    data_ora timestamp without time zone,
    id_app character varying(255),
    ip_address character varying(255),
    key_oper character varying(255),
    ogg_oper character varying(255),
    operazione character varying(255),
    utente character varying(255)
);


ALTER TABLE csi_log_audit OWNER TO mudeopen;

--
-- Name: csi_log_audit_id_log_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE csi_log_audit_id_log_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE csi_log_audit_id_log_seq OWNER TO mudeopen;

--
-- Name: csi_log_audit_id_log_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE csi_log_audit_id_log_seq OWNED BY csi_log_audit.id_log;


--
-- Name: idfascicolo; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE idfascicolo (
    id_fascicolo bigint
);


ALTER TABLE idfascicolo OWNER TO mudeopen;

--
-- Name: iduser; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE iduser (
    id_intestatario bigint
);


ALTER TABLE iduser OWNER TO mudeopen;

--
-- Name: log_sql; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE log_sql (
    datid oid,
    datname name,
    pid integer,
    usesysid oid,
    usename name,
    application_name text,
    client_addr inet,
    client_hostname text,
    client_port integer,
    backend_start timestamp with time zone,
    xact_start timestamp with time zone,
    query_start timestamp with time zone,
    state_change timestamp with time zone,
    wait_event_type text,
    wait_event text,
    state text,
    backend_xid xid,
    backend_xmin xid,
    query text,
    backend_type text
);


ALTER TABLE log_sql OWNER TO mudeopen;

--
-- Name: mudeopen_c_proprieta; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_c_proprieta (
    id_proprieta bigint NOT NULL,
    nome character varying(255),
    valore text,
    note character varying,
    visibilita character varying,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    id_specie_pratica character varying
);


ALTER TABLE mudeopen_c_proprieta OWNER TO mudeopen;

--
-- Name: COLUMN mudeopen_c_proprieta.visibilita; Type: COMMENT; Schema: mudeopen; Owner: mudeopen
--

COMMENT ON COLUMN mudeopen_c_proprieta.visibilita IS 'FO, BO, WS';


--
-- Name: mudeopen_c_proprieta_ente; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_c_proprieta_ente (
    id_proprieta_ente bigint NOT NULL,
    id_ente bigint NOT NULL,
    codice_tipo_istanza character varying,
    nome character varying,
    valore character varying,
    note character varying,
    visibilita character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    specie_pratica character varying,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_c_proprieta_ente OWNER TO mudeopen;

--
-- Name: COLUMN mudeopen_c_proprieta_ente.codice_tipo_istanza; Type: COMMENT; Schema: mudeopen; Owner: mudeopen
--

COMMENT ON COLUMN mudeopen_c_proprieta_ente.codice_tipo_istanza IS 'NULL | codice tipo istanza';


--
-- Name: COLUMN mudeopen_c_proprieta_ente.visibilita; Type: COMMENT; Schema: mudeopen; Owner: mudeopen
--

COMMENT ON COLUMN mudeopen_c_proprieta_ente.visibilita IS 'NULL (default: vistibile a tutti) | FO | BO | WEBAPI';


--
-- Name: mudeopen_c_proprieta_ente_id_proprieta_ente_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_c_proprieta_ente_id_proprieta_ente_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_c_proprieta_ente_id_proprieta_ente_seq OWNER TO mudeopen;

--
-- Name: mudeopen_c_proprieta_ente_id_proprieta_ente_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_c_proprieta_ente_id_proprieta_ente_seq OWNED BY mudeopen_c_proprieta_ente.id_proprieta_ente;


--
-- Name: mudeopen_c_proprieta_id_proprieta_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_c_proprieta_id_proprieta_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_c_proprieta_id_proprieta_seq OWNER TO mudeopen;

--
-- Name: mudeopen_c_proprieta_id_proprieta_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_c_proprieta_id_proprieta_seq OWNED BY mudeopen_c_proprieta.id_proprieta;


--
-- Name: mudeopen_d_abilitazione; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_abilitazione (
    id_abilitazione bigint NOT NULL,
    codice_abilitazione character varying(20),
    desc_abilitazione character varying(1000),
    tipo_abilitazione character(1) NOT NULL,
    necessaria_iscrizione_albo boolean DEFAULT false,
    necessaria_presenza_in_istanza boolean DEFAULT false,
    data_inizio timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    necessaria_selezione_quadro boolean DEFAULT false,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_abilitazione OWNER TO mudeopen;

--
-- Name: mudeopen_d_abilitazione_id_abilitazione_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_d_abilitazione_id_abilitazione_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_d_abilitazione_id_abilitazione_seq OWNER TO mudeopen;

--
-- Name: mudeopen_d_abilitazione_id_abilitazione_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_d_abilitazione_id_abilitazione_seq OWNED BY mudeopen_d_abilitazione.id_abilitazione;


--
-- Name: mudeopen_d_adempimento; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_adempimento (
    id_adempimento bigint NOT NULL,
    ambito character varying(500),
    denominazione character varying(500),
    tipo_adempimento character varying(50),
    id_regime bigint,
    posizione bigint,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_adempimento OWNER TO mudeopen;

--
-- Name: mudeopen_d_adempimento_id_adempimento_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_d_adempimento_id_adempimento_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_d_adempimento_id_adempimento_seq OWNER TO mudeopen;

--
-- Name: mudeopen_d_adempimento_id_adempimento_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_d_adempimento_id_adempimento_seq OWNED BY mudeopen_d_adempimento.id_adempimento;


--
-- Name: mudeopen_d_ambito; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_ambito (
    id_ambito integer NOT NULL,
    cod_ambito character varying(20) NOT NULL,
    des_ambito character varying(50) NOT NULL,
    des_estesa_ambito character varying(200),
    flg_attivo numeric(1,0) NOT NULL,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_ambito OWNER TO mudeopen;

--
-- Name: mudeopen_d_cat_rischio_sismico; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_cat_rischio_sismico (
    codice character varying(20) NOT NULL,
    descrizione character varying(300) NOT NULL,
    descrizione_estesa character varying(1000),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_cat_rischio_sismico OWNER TO mudeopen;

--
-- Name: mudeopen_d_categoria; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_categoria (
    id_categoria bigint NOT NULL,
    denominazione character varying(1500),
    id_regime bigint,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_categoria OWNER TO mudeopen;

--
-- Name: mudeopen_d_categoria_allegato; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_categoria_allegato (
    codice character varying(20) NOT NULL,
    descrizione character varying(300),
    descrizione_estesa character varying(1000),
    ordinamento character varying(255),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_categoria_allegato OWNER TO mudeopen;

--
-- Name: mudeopen_d_categoria_id_categoria_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_d_categoria_id_categoria_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_d_categoria_id_categoria_seq OWNER TO mudeopen;

--
-- Name: mudeopen_d_categoria_id_categoria_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_d_categoria_id_categoria_seq OWNED BY mudeopen_d_categoria.id_categoria;


--
-- Name: mudeopen_d_categoria_quadro; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_categoria_quadro (
    id_categoria_quadro bigint NOT NULL,
    codice_categoria_quadro character varying(20),
    desc_categoria_quadro character varying(1000),
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_categoria_quadro OWNER TO mudeopen;

--
-- Name: mudeopen_d_categoria_quadro_id_categoria_quadro_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_d_categoria_quadro_id_categoria_quadro_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_d_categoria_quadro_id_categoria_quadro_seq OWNER TO mudeopen;

--
-- Name: mudeopen_d_categoria_quadro_id_categoria_quadro_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_d_categoria_quadro_id_categoria_quadro_seq OWNED BY mudeopen_d_categoria_quadro.id_categoria_quadro;


--
-- Name: mudeopen_d_comune; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_comune (
    id_comune bigint NOT NULL,
    cod_belfiore_comune character varying(255),
    cod_istat_comune character varying(255),
    denom_comune character varying(255),
    dt_id_comune numeric(19,2),
    dt_id_comune_next numeric(19,2),
    dt_id_comune_prev numeric(19,2),
    fine_validita date,
    inizio_validita date,
    id_provincia bigint,
    rischio_sismico character varying,
    validita_fiscale boolean,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    email character varying,
    cosmo_serie_fascicoli character varying
);


ALTER TABLE mudeopen_d_comune OWNER TO mudeopen;

--
-- Name: mudeopen_d_comune_id_comune_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_d_comune_id_comune_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_d_comune_id_comune_seq OWNER TO mudeopen;

--
-- Name: mudeopen_d_comune_id_comune_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_d_comune_id_comune_seq OWNED BY mudeopen_d_comune.id_comune;


--
-- Name: mudeopen_d_controllo_cosmo; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_controllo_cosmo (
    codice character varying(20) NOT NULL,
    descrizione character varying(300) NOT NULL,
    descrizione_estesa character varying(1000),
    data_inizio timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_controllo_cosmo OWNER TO mudeopen;

--
-- Name: mudeopen_d_destinazione_d_uso; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_destinazione_d_uso (
    codice character varying(20) NOT NULL,
    descrizione character varying(300) NOT NULL,
    descrizione_estesa character varying(1000),
    data_inizio timestamp without time zone NOT NULL,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_destinazione_d_uso OWNER TO mudeopen;

--
-- Name: mudeopen_d_dug; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_dug (
    id_dug bigint NOT NULL,
    denominazione character varying(255),
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now(),
    cardinal_pos integer
);


ALTER TABLE mudeopen_d_dug OWNER TO mudeopen;

--
-- Name: mudeopen_d_elemento; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_elemento (
    id_elemento bigint NOT NULL,
    denominazione character varying(500),
    posizione bigint,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_elemento OWNER TO mudeopen;

--
-- Name: mudeopen_d_elemento_elenco; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_elemento_elenco (
    id_elemento_elenco bigint NOT NULL,
    codice character varying(10),
    descrizione character varying(255),
    descrizione_estesa character varying(500),
    ordinamento numeric(22,0),
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    id_tipo_elenco bigint,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_elemento_elenco OWNER TO mudeopen;

--
-- Name: mudeopen_d_elemento_elenco_id_elemento_elenco_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_d_elemento_elenco_id_elemento_elenco_seq
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_d_elemento_elenco_id_elemento_elenco_seq OWNER TO mudeopen;

--
-- Name: mudeopen_d_elemento_elenco_id_elemento_elenco_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_d_elemento_elenco_id_elemento_elenco_seq OWNED BY mudeopen_d_elemento_elenco.id_elemento_elenco;


--
-- Name: mudeopen_d_elemento_id_elemento_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_d_elemento_id_elemento_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_d_elemento_id_elemento_seq OWNER TO mudeopen;

--
-- Name: mudeopen_d_elemento_id_elemento_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_d_elemento_id_elemento_seq OWNED BY mudeopen_d_elemento.id_elemento;


--
-- Name: mudeopen_d_fonte; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_fonte (
    id_fonte character varying(4) NOT NULL,
    descrizione character varying NOT NULL,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_d_fonte OWNER TO mudeopen;

--
-- Name: mudeopen_d_fruitore; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_fruitore (
    id_fruitore bigint NOT NULL,
    codice_fruitore character varying(20) NOT NULL,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    note character varying(250),
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_fruitore OWNER TO mudeopen;

--
-- Name: TABLE mudeopen_d_fruitore; Type: COMMENT; Schema: mudeopen; Owner: mudeopen
--

COMMENT ON TABLE mudeopen_d_fruitore IS 'Tabella dove vengono censiti i fruitori dei web services';


--
-- Name: COLUMN mudeopen_d_fruitore.codice_fruitore; Type: COMMENT; Schema: mudeopen; Owner: mudeopen
--

COMMENT ON COLUMN mudeopen_d_fruitore.codice_fruitore IS 'codice identificativo univoco del fruitore. assegnato al momento di stipula del contratto di servizio';


--
-- Name: COLUMN mudeopen_d_fruitore.data_inizio; Type: COMMENT; Schema: mudeopen; Owner: mudeopen
--

COMMENT ON COLUMN mudeopen_d_fruitore.data_inizio IS 'Data inizio validità del record';


--
-- Name: COLUMN mudeopen_d_fruitore.data_fine; Type: COMMENT; Schema: mudeopen; Owner: mudeopen
--

COMMENT ON COLUMN mudeopen_d_fruitore.data_fine IS 'Data fine validità del record';


--
-- Name: COLUMN mudeopen_d_fruitore.note; Type: COMMENT; Schema: mudeopen; Owner: mudeopen
--

COMMENT ON COLUMN mudeopen_d_fruitore.note IS 'Note fruitore';


--
-- Name: mudeopen_d_fruitore_id_fruitore_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_d_fruitore_id_fruitore_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_d_fruitore_id_fruitore_seq OWNER TO mudeopen;

--
-- Name: mudeopen_d_fruitore_id_fruitore_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_d_fruitore_id_fruitore_seq OWNED BY mudeopen_d_fruitore.id_fruitore;


--
-- Name: mudeopen_d_funzione; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_funzione (
    id_funzione bigint NOT NULL,
    codice_funzione character varying(20) NOT NULL,
    desc_funzione character varying(1000),
    tipo_funzione character(1) NOT NULL,
    id_categoria_quadro bigint,
    previsto_pm boolean DEFAULT false,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    nomina_abiltiazione character varying(255),
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_funzione OWNER TO mudeopen;

--
-- Name: mudeopen_d_funzione_id_funzione_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_d_funzione_id_funzione_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_d_funzione_id_funzione_seq OWNER TO mudeopen;

--
-- Name: mudeopen_d_funzione_id_funzione_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_d_funzione_id_funzione_seq OWNED BY mudeopen_d_funzione.id_funzione;


--
-- Name: mudeopen_d_nazione; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_nazione (
    id_nazione bigint NOT NULL,
    cod_belfiore_nazione character varying(255),
    cod_istat_nazione character varying(255),
    denom_nazione character varying(255),
    dt_id_stato numeric(19,2),
    dt_id_stato_next numeric(19,2),
    dt_id_stato_prev numeric(19,2),
    fine_validita date,
    inizio_validita date,
    id_user bigint,
    stato_membro_ue boolean DEFAULT false NOT NULL,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_d_nazione OWNER TO mudeopen;

--
-- Name: mudeopen_d_nazione_id_nazione_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_d_nazione_id_nazione_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_d_nazione_id_nazione_seq OWNER TO mudeopen;

--
-- Name: mudeopen_d_nazione_id_nazione_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_d_nazione_id_nazione_seq OWNED BY mudeopen_d_nazione.id_nazione;


--
-- Name: mudeopen_d_opera; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_opera (
    id_opera bigint NOT NULL,
    denominazione character varying(500),
    id_categoria bigint,
    id_elemento bigint,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_opera OWNER TO mudeopen;

--
-- Name: mudeopen_d_opera_id_opera_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_d_opera_id_opera_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_d_opera_id_opera_seq OWNER TO mudeopen;

--
-- Name: mudeopen_d_opera_id_opera_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_d_opera_id_opera_seq OWNED BY mudeopen_d_opera.id_opera;


--
-- Name: mudeopen_d_ordine; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_ordine (
    codice character varying(20) NOT NULL,
    descrizione character varying(300),
    descrizione_estesa character varying(1000),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now(),
    cardinal_pos integer
);


ALTER TABLE mudeopen_d_ordine OWNER TO mudeopen;

--
-- Name: mudeopen_d_ppay_importi; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_ppay_importi (
    id_importo bigint NOT NULL,
    id_importo_default bigint,
    id_ente bigint NOT NULL,
    id_tipo_istanza character varying,
    id_specie_pratica character varying,
    tipo_importo character varying,
    descrizione character varying NOT NULL,
    importo numeric(19,2),
    dati_spec_riscossione character varying NOT NULL,
    causale character varying NOT NULL,
    anno_accertamento character varying NOT NULL,
    numero_accertamento character varying NOT NULL,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_ppay_importi OWNER TO mudeopen;

--
-- Name: COLUMN mudeopen_d_ppay_importi.id_importo_default; Type: COMMENT; Schema: mudeopen; Owner: mudeopen
--

COMMENT ON COLUMN mudeopen_d_ppay_importi.id_importo_default IS 'NULL | id della voce di importo di default';


--
-- Name: COLUMN mudeopen_d_ppay_importi.id_ente; Type: COMMENT; Schema: mudeopen; Owner: mudeopen
--

COMMENT ON COLUMN mudeopen_d_ppay_importi.id_ente IS 'id_ente al quale si riferisce questo importo';


--
-- Name: COLUMN mudeopen_d_ppay_importi.id_tipo_istanza; Type: COMMENT; Schema: mudeopen; Owner: mudeopen
--

COMMENT ON COLUMN mudeopen_d_ppay_importi.id_tipo_istanza IS 'id_tipo_istanza al quale si riferisce questo importo';


--
-- Name: COLUMN mudeopen_d_ppay_importi.id_specie_pratica; Type: COMMENT; Schema: mudeopen; Owner: mudeopen
--

COMMENT ON COLUMN mudeopen_d_ppay_importi.id_specie_pratica IS 'NULL | id_specie_pratica';


--
-- Name: COLUMN mudeopen_d_ppay_importi.tipo_importo; Type: COMMENT; Schema: mudeopen; Owner: mudeopen
--

COMMENT ON COLUMN mudeopen_d_ppay_importi.tipo_importo IS 'modificabile (default) | maggiore | minore | bloccato | opzionale | disabilitato';


--
-- Name: COLUMN mudeopen_d_ppay_importi.descrizione; Type: COMMENT; Schema: mudeopen; Owner: mudeopen
--

COMMENT ON COLUMN mudeopen_d_ppay_importi.descrizione IS 'HTML da mostrare all''utente come voce di importo';


--
-- Name: COLUMN mudeopen_d_ppay_importi.importo; Type: COMMENT; Schema: mudeopen; Owner: mudeopen
--

COMMENT ON COLUMN mudeopen_d_ppay_importi.importo IS 'regexp: [0-9]*(,[0-9]{2}){0,1} | NULL in caso di ''disabilitato'' oppure importo preso da id_importo_default->importo';


--
-- Name: COLUMN mudeopen_d_ppay_importi.causale; Type: COMMENT; Schema: mudeopen; Owner: mudeopen
--

COMMENT ON COLUMN mudeopen_d_ppay_importi.causale IS 'componente di pagamento `causale` da passare a ppay';


--
-- Name: COLUMN mudeopen_d_ppay_importi.anno_accertamento; Type: COMMENT; Schema: mudeopen; Owner: mudeopen
--

COMMENT ON COLUMN mudeopen_d_ppay_importi.anno_accertamento IS 'componente di pagamento `annoAccertamento` da passare a ppay';


--
-- Name: COLUMN mudeopen_d_ppay_importi.numero_accertamento; Type: COMMENT; Schema: mudeopen; Owner: mudeopen
--

COMMENT ON COLUMN mudeopen_d_ppay_importi.numero_accertamento IS 'componente di pagamento `numeroAccertamento` da passare a ppay';


--
-- Name: mudeopen_d_ppay_importi_id_importo_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_d_ppay_importi_id_importo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_d_ppay_importi_id_importo_seq OWNER TO mudeopen;

--
-- Name: mudeopen_d_ppay_importi_id_importo_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_d_ppay_importi_id_importo_seq OWNED BY mudeopen_d_ppay_importi.id_importo;


--
-- Name: mudeopen_d_provincia; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_provincia (
    id_provincia bigint NOT NULL,
    cod_provincia character varying(255),
    denom_provincia character varying(255),
    fine_validita date,
    inizio_validita date,
    sigla_provincia character varying(255),
    id_regione bigint,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_d_provincia OWNER TO mudeopen;

--
-- Name: mudeopen_d_provincia_id_provincia_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_d_provincia_id_provincia_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_d_provincia_id_provincia_seq OWNER TO mudeopen;

--
-- Name: mudeopen_d_provincia_id_provincia_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_d_provincia_id_provincia_seq OWNED BY mudeopen_d_provincia.id_provincia;


--
-- Name: mudeopen_d_quadro; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_quadro (
    id_quadro bigint NOT NULL,
    id_tipo_quadro integer NOT NULL,
    num_versione integer NOT NULL,
    flg_tipo_gestione character varying(1) NOT NULL,
    json_configura_quadro json,
    json_default character varying,
    validation_script text,
    id_modello_documentale integer,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_quadro OWNER TO mudeopen;

--
-- Name: mudeopen_d_quadro_id_quadro_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_d_quadro_id_quadro_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_d_quadro_id_quadro_seq OWNER TO mudeopen;

--
-- Name: mudeopen_d_quadro_id_quadro_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_d_quadro_id_quadro_seq OWNED BY mudeopen_d_quadro.id_quadro;


--
-- Name: mudeopen_d_qualifica; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_qualifica (
    id_qualifica bigint NOT NULL,
    denominazione character varying(255),
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    codice character varying(20) NOT NULL,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now(),
    cardinal_pos integer
);


ALTER TABLE mudeopen_d_qualifica OWNER TO mudeopen;

--
-- Name: mudeopen_d_qualifica_collegio; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_qualifica_collegio (
    codice character varying(20) NOT NULL,
    descrizione character varying(300),
    descrizione_estesa character varying(1000),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_qualifica_collegio OWNER TO mudeopen;

--
-- Name: mudeopen_d_qualifica_id_qualifica_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_d_qualifica_id_qualifica_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_d_qualifica_id_qualifica_seq OWNER TO mudeopen;

--
-- Name: mudeopen_d_qualifica_id_qualifica_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_d_qualifica_id_qualifica_seq OWNED BY mudeopen_d_qualifica.id_qualifica;


--
-- Name: mudeopen_d_qualifica_urbanistica; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_qualifica_urbanistica (
    id_qualifica_urbanistica integer NOT NULL,
    codice character varying,
    descrizione character varying NOT NULL,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_qualifica_urbanistica OWNER TO mudeopen;

--
-- Name: mudeopen_d_qualifica_urbanistica_id_qualifica_urbanistica_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_d_qualifica_urbanistica_id_qualifica_urbanistica_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_d_qualifica_urbanistica_id_qualifica_urbanistica_seq OWNER TO mudeopen;

--
-- Name: mudeopen_d_qualifica_urbanistica_id_qualifica_urbanistica_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_d_qualifica_urbanistica_id_qualifica_urbanistica_seq OWNED BY mudeopen_d_qualifica_urbanistica.id_qualifica_urbanistica;


--
-- Name: mudeopen_d_regime_giuridico; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_regime_giuridico (
    id_regime bigint NOT NULL,
    denominazione character varying(255),
    priorita bigint,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_regime_giuridico OWNER TO mudeopen;

--
-- Name: mudeopen_d_regime_giuridico_aggiuntivo; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_regime_giuridico_aggiuntivo (
    id_regime bigint NOT NULL,
    denominazione character varying(500),
    priorita bigint,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_regime_giuridico_aggiuntivo OWNER TO mudeopen;

--
-- Name: mudeopen_d_regime_giuridico_aggiuntivo_id_regime_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_d_regime_giuridico_aggiuntivo_id_regime_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_d_regime_giuridico_aggiuntivo_id_regime_seq OWNER TO mudeopen;

--
-- Name: mudeopen_d_regime_giuridico_aggiuntivo_id_regime_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_d_regime_giuridico_aggiuntivo_id_regime_seq OWNED BY mudeopen_d_regime_giuridico_aggiuntivo.id_regime;


--
-- Name: mudeopen_d_regime_giuridico_id_regime_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_d_regime_giuridico_id_regime_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_d_regime_giuridico_id_regime_seq OWNER TO mudeopen;

--
-- Name: mudeopen_d_regime_giuridico_id_regime_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_d_regime_giuridico_id_regime_seq OWNED BY mudeopen_d_regime_giuridico.id_regime;


--
-- Name: mudeopen_d_regione; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_regione (
    id_regione bigint NOT NULL,
    cod_regione character varying(255),
    denom_regione character varying(255),
    fine_validita date,
    inizio_validita date,
    id_nazione bigint,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_d_regione OWNER TO mudeopen;

--
-- Name: mudeopen_d_regione_id_regione_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_d_regione_id_regione_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_d_regione_id_regione_seq OWNER TO mudeopen;

--
-- Name: mudeopen_d_regione_id_regione_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_d_regione_id_regione_seq OWNED BY mudeopen_d_regione.id_regione;


--
-- Name: mudeopen_d_ruolo_soggetto; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_ruolo_soggetto (
    codice character varying(20) NOT NULL,
    descrizione character varying(300),
    descrizione_estesa character varying(1000),
    visibile boolean DEFAULT true,
    branch_obbligatorio character varying(50),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    categoria_ruolo character varying,
    operatori character varying,
    includi character varying,
    escludi character varying,
    obbligatori character varying,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_ruolo_soggetto OWNER TO mudeopen;

--
-- Name: COLUMN mudeopen_d_ruolo_soggetto.obbligatori; Type: COMMENT; Schema: mudeopen; Owner: mudeopen
--

COMMENT ON COLUMN mudeopen_d_ruolo_soggetto.obbligatori IS 'PIVA,PEC,ALBO';


--
-- Name: mudeopen_d_ruolo_utente; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_ruolo_utente (
    codice character varying(20) NOT NULL,
    descrizione character varying(300) NOT NULL,
    descrizione_estesa character varying(1000),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_ruolo_utente OWNER TO mudeopen;

--
-- Name: mudeopen_d_sism_classe_uso; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_sism_classe_uso (
    codice character varying(20) NOT NULL,
    descrizione character varying(300) NOT NULL,
    descrizione_estesa character varying(1000),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_sism_classe_uso OWNER TO mudeopen;

--
-- Name: mudeopen_d_sism_costr_tipo; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_sism_costr_tipo (
    codice character varying(20) NOT NULL,
    descrizione character varying(300) NOT NULL,
    descrizione_estesa character varying(1000),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_sism_costr_tipo OWNER TO mudeopen;

--
-- Name: mudeopen_d_sism_int_par; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_sism_int_par (
    codice character varying(20) NOT NULL,
    descrizione character varying(300) NOT NULL,
    descrizione_estesa character varying(1000),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_sism_int_par OWNER TO mudeopen;

--
-- Name: mudeopen_d_sism_int_tipo; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_sism_int_tipo (
    codice character varying(20) NOT NULL,
    descrizione character varying(300) NOT NULL,
    descrizione_estesa character varying(1000),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_sism_int_tipo OWNER TO mudeopen;

--
-- Name: mudeopen_d_sism_norma; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_sism_norma (
    id_sism_norma character varying(20) NOT NULL,
    descrizione character varying(300) NOT NULL,
    descrizione_estesa character varying(1000),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_sism_norma OWNER TO mudeopen;

--
-- Name: mudeopen_d_specie_pratica; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_specie_pratica (
    codice character varying(20) NOT NULL,
    descrizione character varying(300) NOT NULL,
    descrizione_estesa character varying(1000),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    flag_sanatoria boolean DEFAULT false,
    flag_variante boolean DEFAULT false,
    flag_sanatoria_in_corso boolean DEFAULT false,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now(),
    visibilita character varying
);


ALTER TABLE mudeopen_d_specie_pratica OWNER TO mudeopen;

--
-- Name: mudeopen_d_stato_cosmo; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_stato_cosmo (
    codice character varying(20) NOT NULL,
    descrizione character varying(300) NOT NULL,
    descrizione_estesa character varying(1000),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_stato_cosmo OWNER TO mudeopen;

--
-- Name: mudeopen_d_stato_fascicolo; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_stato_fascicolo (
    codice character varying(20) NOT NULL,
    descrizione character varying(300) NOT NULL,
    descrizione_estesa character varying(1000),
    data_inizio timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now(),
    cardinal_pos integer
);


ALTER TABLE mudeopen_d_stato_fascicolo OWNER TO mudeopen;

--
-- Name: mudeopen_d_stato_filtro_veloce; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_stato_filtro_veloce (
    codice character varying(20) NOT NULL,
    descrizione character varying(300) NOT NULL,
    descrizione_estesa character varying(1000),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_stato_filtro_veloce OWNER TO mudeopen;

--
-- Name: mudeopen_d_stato_idf; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_stato_idf (
    codice character varying(20) NOT NULL,
    descrizione character varying(300) NOT NULL,
    descrizione_estesa character varying(1000),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_stato_idf OWNER TO mudeopen;

--
-- Name: mudeopen_d_stato_istanza; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_stato_istanza (
    codice character varying(20) NOT NULL,
    descrizione character varying(300) NOT NULL,
    descrizione_estesa character varying(1000),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now(),
    livello integer,
    cardinal_pos integer
);


ALTER TABLE mudeopen_d_stato_istanza OWNER TO mudeopen;

--
-- Name: mudeopen_d_stato_istanza_ext_moon; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_stato_istanza_ext_moon (
    cod_stato_istanza_ext_moon character varying(3) NOT NULL,
    desc_stato_istanza_ext_moon character varying(40) NOT NULL,
    azione_proposta character varying(120),
    messaggio_proposto character varying(600),
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_d_stato_istanza_ext_moon OWNER TO mudeopen;

--
-- Name: mudeopen_d_task_quadro; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_task_quadro (
    codice_task character varying(255) NOT NULL,
    desc_task character varying(255),
    id_quadro bigint,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_d_task_quadro OWNER TO mudeopen;

--
-- Name: mudeopen_d_template; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_template (
    id_template bigint NOT NULL,
    id_tipo_istanza character varying(20) NOT NULL,
    cod_template character varying(20),
    des_template character varying(50),
    data_inizio_validita date,
    data_cessazione date,
    flg_attivo numeric(1,0) NOT NULL,
    json_configura_template jsonb,
    id_modello_documentale integer,
    num_versione integer DEFAULT 1 NOT NULL,
    obbligatoria_nomina_pm boolean DEFAULT false NOT NULL,
    id_modello_intestazione integer,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    CONSTRAINT chk_mudeopen_d_template_01 CHECK ((flg_attivo = ANY (ARRAY[(0)::numeric, (1)::numeric])))
);
ALTER TABLE ONLY mudeopen_d_template ALTER COLUMN id_template SET STATISTICS 3000;
ALTER TABLE ONLY mudeopen_d_template ALTER COLUMN id_modello_documentale SET STATISTICS 3000;


ALTER TABLE mudeopen_d_template OWNER TO mudeopen;

--
-- Name: mudeopen_d_template_id_template_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_d_template_id_template_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_d_template_id_template_seq OWNER TO mudeopen;

--
-- Name: mudeopen_d_template_id_template_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_d_template_id_template_seq OWNED BY mudeopen_d_template.id_template;


--
-- Name: mudeopen_d_template_ricevuta_istanza; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_template_ricevuta_istanza (
    id_template_ricevuta_istanza bigint NOT NULL,
    des_template character varying(100),
    content_template bytea,
    testo_oggetto character varying(1000),
    testo_body_1 character varying(1000),
    testo_body_2 character varying(1000),
    testo_body_3 character varying(1000),
    testo_footer character varying(1000),
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_d_template_ricevuta_istanza OWNER TO mudeopen;

--
-- Name: mudeopen_d_template_ricevuta_istanza_id_template_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_d_template_ricevuta_istanza_id_template_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_d_template_ricevuta_istanza_id_template_seq OWNER TO mudeopen;

--
-- Name: mudeopen_d_template_ricevuta_istanza_id_template_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_d_template_ricevuta_istanza_id_template_seq OWNED BY mudeopen_d_template_ricevuta_istanza.id_template_ricevuta_istanza;


--
-- Name: mudeopen_d_template_tracciato; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_template_tracciato (
    id_template_tracciato bigint DEFAULT nextval('mudeopen.mudeopen_d_template_tracciato_id_template_tracciato_seq'::regclass) NOT NULL,
    codice character varying(40) NOT NULL,
    descrizione character varying NOT NULL,
    xslt_template text,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_template_tracciato OWNER TO mudeopen;

--
-- Name: mudeopen_d_tipo_allegato; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_tipo_allegato (
    codice character varying(20) NOT NULL,
    descrizione character varying(300),
    descrizione_estesa character varying(1000),
    id_gruppo_allegato character varying(20),
    sub_cod_tipo_allegato character varying(255),
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    valida_firma boolean DEFAULT false NOT NULL,
    firma_obbligatoria boolean DEFAULT false NOT NULL,
    id bigint,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_tipo_allegato OWNER TO mudeopen;

--
-- Name: mudeopen_d_tipo_deroga; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_tipo_deroga (
    codice character varying(20) NOT NULL,
    descrizione character varying(300) NOT NULL,
    descrizione_estesa character varying(1000),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_tipo_deroga OWNER TO mudeopen;

--
-- Name: mudeopen_d_tipo_ditta; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_tipo_ditta (
    id_tipo_ditta bigint NOT NULL,
    denominazione character varying(255),
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now(),
    cardinal_pos integer
);


ALTER TABLE mudeopen_d_tipo_ditta OWNER TO mudeopen;

--
-- Name: mudeopen_d_tipo_ditta_id_tipo_ditta_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_d_tipo_ditta_id_tipo_ditta_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_d_tipo_ditta_id_tipo_ditta_seq OWNER TO mudeopen;

--
-- Name: mudeopen_d_tipo_ditta_id_tipo_ditta_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_d_tipo_ditta_id_tipo_ditta_seq OWNED BY mudeopen_d_tipo_ditta.id_tipo_ditta;


--
-- Name: mudeopen_d_tipo_docpa; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_tipo_docpa (
    id_tipo_docpa bigint NOT NULL,
    cod_tipo_docpa character varying(20),
    desc_tipo_docpa character varying(1000),
    data_inizio timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_tipo_docpa OWNER TO mudeopen;

--
-- Name: mudeopen_d_tipo_docpa_id_tipo_docpa_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_d_tipo_docpa_id_tipo_docpa_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_d_tipo_docpa_id_tipo_docpa_seq OWNER TO mudeopen;

--
-- Name: mudeopen_d_tipo_docpa_id_tipo_docpa_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_d_tipo_docpa_id_tipo_docpa_seq OWNED BY mudeopen_d_tipo_docpa.id_tipo_docpa;


--
-- Name: mudeopen_d_tipo_elenco; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_tipo_elenco (
    id_tipo_elenco bigint NOT NULL,
    codice character varying(10),
    descrizione character varying(100),
    descrizione_estesa character varying(500),
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_tipo_elenco OWNER TO mudeopen;

--
-- Name: mudeopen_d_tipo_elenco_id_tipo_elenco_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_d_tipo_elenco_id_tipo_elenco_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_d_tipo_elenco_id_tipo_elenco_seq OWNER TO mudeopen;

--
-- Name: mudeopen_d_tipo_elenco_id_tipo_elenco_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_d_tipo_elenco_id_tipo_elenco_seq OWNED BY mudeopen_d_tipo_elenco.id_tipo_elenco;


--
-- Name: mudeopen_d_tipo_ente; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_tipo_ente (
    codice character varying(20) NOT NULL,
    descrizione character varying(300) NOT NULL,
    descrizione_estesa character varying(1000),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_tipo_ente OWNER TO mudeopen;

--
-- Name: mudeopen_d_tipo_intervento; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_tipo_intervento (
    codice character varying(20) NOT NULL,
    descrizione character varying(300) NOT NULL,
    descrizione_estesa character varying(1000),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now(),
    cardinal_pos integer
);


ALTER TABLE mudeopen_d_tipo_intervento OWNER TO mudeopen;

--
-- Name: mudeopen_d_tipo_intervento_paesaggistica; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_tipo_intervento_paesaggistica (
    codice character varying(20) NOT NULL,
    descrizione character varying(300) NOT NULL,
    descrizione_estesa character varying(1000),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    id_tipologia_tipo_intervento_paesaggistica character varying,
    riferimento_normativa character varying(300),
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_tipo_intervento_paesaggistica OWNER TO mudeopen;

--
-- Name: mudeopen_d_tipo_istanza; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_tipo_istanza (
    codice character varying(20) NOT NULL,
    descrizione character varying(300),
    descrizione_estesa character varying(1000),
    id_ambito integer,
    almeno_un_ruolo boolean,
    legata boolean,
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    cambio_intestatario boolean DEFAULT false,
    id_template_ricevuta_istanza bigint,
    soggetti_bloccati boolean,
    escludi_branch character varying,
    override_ente_padre boolean,
    privato boolean DEFAULT false NOT NULL,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_tipo_istanza OWNER TO mudeopen;

--
-- Name: COLUMN mudeopen_d_tipo_istanza.cambio_intestatario; Type: COMMENT; Schema: mudeopen; Owner: mudeopen
--

COMMENT ON COLUMN mudeopen_d_tipo_istanza.cambio_intestatario IS 'se impostato a true, mostra l`ozione del branch 1 sebbene l`intestario a livello di fascicolo abbia CF differente dall`utente in sessione';


--
-- Name: COLUMN mudeopen_d_tipo_istanza.soggetti_bloccati; Type: COMMENT; Schema: mudeopen; Owner: mudeopen
--

COMMENT ON COLUMN mudeopen_d_tipo_istanza.soggetti_bloccati IS 'inibisce la selezione dei soggetti a livello di fascicolo durante la creazione di una istanza';


--
-- Name: COLUMN mudeopen_d_tipo_istanza.escludi_branch; Type: COMMENT; Schema: mudeopen; Owner: mudeopen
--

COMMENT ON COLUMN mudeopen_d_tipo_istanza.escludi_branch IS 'esempio: BRANCH2,BRANCH3,BRANCH4';


--
-- Name: COLUMN mudeopen_d_tipo_istanza.privato; Type: COMMENT; Schema: mudeopen; Owner: mudeopen
--

COMMENT ON COLUMN mudeopen_d_tipo_istanza.privato IS 'se true, non ne viene permessa la selezione durante la creazione di una nuova istanza da FO';


--
-- Name: mudeopen_d_tipo_notifica; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_tipo_notifica (
    id_tipo_notifica bigint NOT NULL,
    cod_tipo_notifica character varying,
    des_tipo_notifica character varying,
    invio_email boolean DEFAULT false,
    data_inizio timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    sub_cod_tipo_notifica character varying,
    oggetto_proposto character varying,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_tipo_notifica OWNER TO mudeopen;

--
-- Name: mudeopen_d_tipo_notifica_id_tipo_notifica_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_d_tipo_notifica_id_tipo_notifica_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_d_tipo_notifica_id_tipo_notifica_seq OWNER TO mudeopen;

--
-- Name: mudeopen_d_tipo_notifica_id_tipo_notifica_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_d_tipo_notifica_id_tipo_notifica_seq OWNED BY mudeopen_d_tipo_notifica.id_tipo_notifica;


--
-- Name: mudeopen_d_tipo_opera; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_tipo_opera (
    codice character varying(20) NOT NULL,
    descrizione character varying(300) NOT NULL,
    descrizione_estesa character varying(1000),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_tipo_opera OWNER TO mudeopen;

--
-- Name: mudeopen_d_tipo_presentatore; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_tipo_presentatore (
    id_tipo bigint NOT NULL,
    denominazione character varying(255),
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_tipo_presentatore OWNER TO mudeopen;

--
-- Name: mudeopen_d_tipo_presentatore_id_tipo_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_d_tipo_presentatore_id_tipo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_d_tipo_presentatore_id_tipo_seq OWNER TO mudeopen;

--
-- Name: mudeopen_d_tipo_presentatore_id_tipo_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_d_tipo_presentatore_id_tipo_seq OWNED BY mudeopen_d_tipo_presentatore.id_tipo;


--
-- Name: mudeopen_d_tipo_quadro; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_tipo_quadro (
    id_tipo_quadro bigint NOT NULL,
    cod_tipo_quadro character varying(50),
    des_tipo_quadro character varying(255),
    id_categoria_quadro bigint,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    id_tipo_quadro_padre bigint,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_tipo_quadro OWNER TO mudeopen;

--
-- Name: mudeopen_d_tipo_quadro_id_tipo_quadro_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_d_tipo_quadro_id_tipo_quadro_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_d_tipo_quadro_id_tipo_quadro_seq OWNER TO mudeopen;

--
-- Name: mudeopen_d_tipo_quadro_id_tipo_quadro_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_d_tipo_quadro_id_tipo_quadro_seq OWNED BY mudeopen_d_tipo_quadro.id_tipo_quadro;


--
-- Name: mudeopen_d_tipo_tracciato; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_tipo_tracciato (
    id_tipo_tracciato bigint DEFAULT nextval('mudeopen.mudeopen_d_tipo_tracciato_id_tipo_tracciato_seq'::regclass) NOT NULL,
    codice character varying(20) NOT NULL,
    descrizione character varying NOT NULL,
    versione character varying NOT NULL,
    xsd_validazione text,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_tipo_tracciato OWNER TO mudeopen;

--
-- Name: mudeopen_d_tipologia_committente; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_tipologia_committente (
    id_tipologia_committente integer DEFAULT nextval('mudeopen.mudeopen_d_tipologia_committente_id_tipologia_committente_seq'::regclass) NOT NULL,
    denominazione character varying NOT NULL,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_tipologia_committente OWNER TO mudeopen;

--
-- Name: mudeopen_d_tipologia_opere_precarie; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_tipologia_opere_precarie (
    codice character varying(20) NOT NULL,
    descrizione character varying(300) NOT NULL,
    descrizione_estesa character varying(1000),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_tipologia_opere_precarie OWNER TO mudeopen;

--
-- Name: mudeopen_d_tipologia_tipo_intervento_paesaggistica; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_tipologia_tipo_intervento_paesaggistica (
    codice character varying(20) NOT NULL,
    descrizione character varying(300) NOT NULL,
    descrizione_estesa character varying(1000),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_d_tipologia_tipo_intervento_paesaggistica OWNER TO mudeopen;

--
-- Name: mudeopen_d_titolo; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_titolo (
    codice character varying(20) NOT NULL,
    descrizione character varying(300),
    descrizione_estesa character varying(1000),
    id_tipo_presentatore bigint,
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now(),
    cardinal_pos integer
);


ALTER TABLE mudeopen_d_titolo OWNER TO mudeopen;

--
-- Name: mudeopen_d_wf_stato; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_d_wf_stato (
    codice_stato_start character varying(255) NOT NULL,
    codice_stato_end character varying(255) NOT NULL,
    id_tipo_istanza character varying(255) NOT NULL,
    oggetto_notifica character varying(120),
    testo_notifica character varying(600),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    id_wf_stato bigint NOT NULL,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now(),
    specie_pratica character varying
);


ALTER TABLE mudeopen_d_wf_stato OWNER TO mudeopen;

--
-- Name: mudeopen_d_wf_stato_id_wf_stato_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_d_wf_stato_id_wf_stato_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_d_wf_stato_id_wf_stato_seq OWNER TO mudeopen;

--
-- Name: mudeopen_d_wf_stato_id_wf_stato_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_d_wf_stato_id_wf_stato_seq OWNED BY mudeopen_d_wf_stato.id_wf_stato;


--
-- Name: mudeopen_fab_fabbricati_coto_aci; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_fab_fabbricati_coto_aci (
    pk_sequ_fabbricato bigint NOT NULL,
    fk_comuni character varying NOT NULL,
    foglio_ct character varying,
    particella_ct character varying,
    fk_par_catastali_ct character varying,
    foglio_cu character varying,
    particella_cu character varying,
    fk_par_catastali_cu character varying,
    stato_accatastamento character varying,
    superficie double precision,
    altezza double precision,
    volume double precision,
    n_piani_ft double precision,
    n_piani_et double precision,
    destinazione_uso character varying,
    stato_costruito character varying,
    stato_conservazione character varying,
    anno_costruzione bigint,
    origine character varying,
    conc_edil_n character varying,
    conc_edil_data date,
    conc_edil_scadenza date,
    file_foto character varying,
    foto bytea,
    geometry character varying,
    xcentroid double precision,
    ycentroid double precision,
    fwidth double precision,
    fheight double precision,
    note character varying,
    inserimento_data date,
    inserimento_utente character varying,
    modifica_data date,
    modifica_utente character varying,
    data_rilievo date,
    codice_rilevatore character varying,
    geometria_corrisp character varying,
    codice_censimento character varying,
    causa_non_rilevazione character varying,
    n_conc_edilizia_ril character varying,
    note_rilievo character varying,
    url character varying,
    conc_edil_tipo character varying,
    content_type character varying,
    sezione_ct character varying,
    denominatore_ct character varying,
    edificialita_ct character varying,
    uk_cat_particella_ct character varying,
    sezione_cu character varying,
    denominatore_cu character varying,
    edificialita_cu character varying,
    uk_cat_particella_cu character varying,
    uk_particella_ct character varying,
    uk_particella_cu character varying,
    fk_particelle_urbane double precision,
    nrscale double precision,
    tipocopertura character varying,
    tipotamponamentiesterni character varying,
    impiantotermico character varying,
    strutturaportante character varying,
    acquacaldacentralizzata character varying,
    ascensore character varying,
    altriimpianti character varying,
    cortile_giardinoesclusivo character varying,
    cantine_soffitte character varying,
    alloggiocustode character varying,
    particomuni character varying,
    piscinaprivata character varying,
    recinzione character varying,
    finitureesterne_facciata character varying,
    finitureesterne_serramenti character varying,
    finitureparticomuniinterne character varying,
    autorimesse_spazisosta character varying,
    impiantofotovoltaico character varying,
    collettoresolare character varying,
    classeenergeticaedificio character varying,
    rectrattamentorisorseidriche character varying,
    num_ui_perdestinazione double precision,
    tipo_modif character varying
);


ALTER TABLE mudeopen_fab_fabbricati_coto_aci OWNER TO mudeopen;

--
-- Name: mudeopen_fab_fabbricati_coto_dbi; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_fab_fabbricati_coto_dbi (
    pk_sequ_fabbricato bigint NOT NULL,
    pk_sequ_sto_fabbricato bigint NOT NULL,
    uk_codi_fabbricato character varying,
    codice_ecografico character varying,
    fk_comuni character varying NOT NULL,
    sezione_ct character varying,
    foglio_ct character varying,
    particella_ct character varying,
    fk_par_catastali_ct character varying,
    sezione_cu character varying,
    foglio_cu character varying,
    particella_cu character varying,
    fk_par_catastali_cu character varying,
    denominatore_ct character varying,
    edificialita_ct character varying,
    uk_cat_particella_ct character varying,
    denominatore_cu character varying,
    edificialita_cu character varying,
    uk_cat_particella_cu character varying,
    stato_accatastamento character varying,
    superficie double precision,
    altezza double precision,
    volume double precision,
    n_piani_ft double precision,
    n_piani_et double precision,
    destinazione_uso character varying,
    stato_costruito character varying,
    stato_conservazione character varying,
    anno_costruzione bigint,
    origine character varying,
    n_conc_edilizia character varying,
    file_foto character varying,
    foto bytea,
    geometry character varying,
    note character varying,
    data_rilievo date,
    codice_rilevatore character varying,
    geometria_corrisp character varying,
    codice_censimento character varying,
    causa_non_rilevazione character varying,
    n_conc_edilizia_ril character varying,
    note_rilievo character varying,
    fk_particelle_urbane double precision,
    esc_foglio_ct character varying,
    esc_numero_ct character varying,
    esc_foglio_cu character varying,
    esc_numero_cu character varying,
    uk_particella_ct character varying,
    uk_particella_cu character varying,
    comune character varying,
    xcentroid double precision,
    ycentroid double precision,
    fwidth double precision,
    fheight double precision,
    url character varying,
    content_type character varying,
    nrscale double precision,
    tipocopertura character varying,
    tipotamponamentiesterni character varying,
    impiantotermico character varying,
    strutturaportante character varying,
    acquacaldacentralizzata character varying,
    ascensore character varying,
    altriimpianti character varying,
    cortile_giardinoesclusivo character varying,
    cantine_soffitte character varying,
    alloggiocustode character varying,
    particomuni character varying,
    piscinaprivata character varying,
    recinzione character varying,
    finitureesterne_facciata character varying,
    finitureesterne_serramenti character varying,
    finitureparticomuniinterne character varying,
    autorimesse_spazisosta character varying,
    impiantofotovoltaico character varying,
    collettoresolare character varying,
    classeenergeticaedificio character varying,
    rectrattamentorisorseidriche character varying,
    num_ui_perdestinazione double precision
);


ALTER TABLE mudeopen_fab_fabbricati_coto_dbi OWNER TO mudeopen;

--
-- Name: mudeopen_fabbricatiparticelle; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_fabbricatiparticelle (
    fk_edificio character varying,
    fk_cellula character varying,
    parent_rec_key character varying,
    pk_fabbricati_particelle bigint,
    id bigint NOT NULL
);


ALTER TABLE mudeopen_fabbricatiparticelle OWNER TO mudeopen;

--
-- Name: mudeopen_fp_civici_full; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_fp_civici_full (
    id_civico bigint,
    codice_via bigint,
    numero_radice bigint,
    bis_ter bigint,
    interno_1 character varying,
    bis_interno_1 bigint,
    interno_2 character varying,
    bis_interno_2 bigint,
    scala character varying,
    secondario character varying,
    codice_stato_civico bigint,
    codice_tipologia_civico character varying,
    codice_abitativo bigint,
    flag_carraio bigint,
    flag_fronte_via bigint,
    cap bigint,
    flag_indirizzo_convenzionale bigint,
    codice_sezione_censimento bigint,
    codice_quartiere bigint,
    codice_sezione_vigili_urbani bigint,
    codice_circoscrizione bigint,
    lettera_distretto_assistenza character varying,
    desc_zona_statistica character varying,
    desc_raggruppamento_statistico character varying,
    cod_localita_istat bigint,
    sezione_elettorale bigint,
    collegio_camerale bigint,
    collegio_senatoriale bigint,
    collegio_provinciale bigint,
    asl bigint,
    codice_parrocchia bigint,
    ztl bigint,
    coordinata_x double precision,
    coordinata_y double precision,
    geometry character varying,
    pk_fp_civici_full bigint
);


ALTER TABLE mudeopen_fp_civici_full OWNER TO mudeopen;

--
-- Name: mudeopen_mw_pre_t_indirizzo; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_mw_pre_t_indirizzo (
    id_indirizzo bigint NOT NULL,
    id_comune bigint,
    id_civico_topon bigint,
    id_via_topon bigint,
    sedime character varying,
    desc_via character varying,
    num_civico character varying,
    bis character varying,
    bisinterno character varying,
    interno character varying,
    indirizzo_completo character varying,
    interno2 character varying,
    cap character varying,
    scala character varying,
    fl_provvisorio character varying,
    secondario character varying
);


ALTER TABLE mudeopen_mw_pre_t_indirizzo OWNER TO mudeopen;

--
-- Name: mudeopen_r_abilitazione_funzione; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_abilitazione_funzione (
    id_abilitazione_funzione bigint NOT NULL,
    id_abilitazione bigint NOT NULL,
    id_funzione bigint NOT NULL,
    data_inizio timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_r_abilitazione_funzione OWNER TO mudeopen;

--
-- Name: mudeopen_r_abilitazione_funzione_id_abilitazione_funzione_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_abilitazione_funzione_id_abilitazione_funzione_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_abilitazione_funzione_id_abilitazione_funzione_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_abilitazione_funzione_id_abilitazione_funzione_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_abilitazione_funzione_id_abilitazione_funzione_seq OWNED BY mudeopen_r_abilitazione_funzione.id_abilitazione_funzione;


--
-- Name: mudeopen_r_allegato_moon_ricevuto; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_allegato_moon_ricevuto (
    id_allegato_moon_ricevuto bigint NOT NULL,
    id_istanza_ext_moon bigint NOT NULL,
    formio_name_file character varying(255),
    codice_file character varying(50),
    nome_file character varying(255),
    lunghezza numeric,
    content_type character varying(50),
    media_type character varying(50),
    submedia_type character varying(100),
    estensione character varying(10),
    uid_index character varying(50),
    gest_attore_ins character varying(30) NOT NULL,
    gest_data_ins timestamp without time zone NOT NULL,
    gest_attore_upd character varying(30) NOT NULL,
    gest_data_upd timestamp without time zone NOT NULL,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_r_allegato_moon_ricevuto OWNER TO mudeopen;

--
-- Name: mudeopen_r_allegato_moon_ricevuto_id_allegato_moon_ricevuto_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_allegato_moon_ricevuto_id_allegato_moon_ricevuto_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_allegato_moon_ricevuto_id_allegato_moon_ricevuto_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_allegato_moon_ricevuto_id_allegato_moon_ricevuto_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_allegato_moon_ricevuto_id_allegato_moon_ricevuto_seq OWNED BY mudeopen_r_allegato_moon_ricevuto.id_allegato_moon_ricevuto;


--
-- Name: mudeopen_r_civici_particelleurbane; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_civici_particelleurbane (
    fk_civico character varying,
    fk_cellula character varying,
    id_civico bigint,
    id bigint NOT NULL,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_r_civici_particelleurbane OWNER TO mudeopen;

--
-- Name: mudeopen_r_comune_fruitore; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_comune_fruitore (
    id_comune_fruitore integer NOT NULL,
    id_comune integer NOT NULL,
    id_fruitore integer NOT NULL,
    data_inizio timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_r_comune_fruitore OWNER TO mudeopen;

--
-- Name: mudeopen_r_comune_fruitore_id_comune_fruitore_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_comune_fruitore_id_comune_fruitore_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_comune_fruitore_id_comune_fruitore_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_comune_fruitore_id_comune_fruitore_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_comune_fruitore_id_comune_fruitore_seq OWNED BY mudeopen_r_comune_fruitore.id_comune_fruitore;


--
-- Name: mudeopen_r_contatto_qualifica; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_contatto_qualifica (
    id_contatto_qualifica bigint NOT NULL,
    numero_iscrizione_ordine character varying(255),
    id_ordine character varying(20),
    id_qualifica bigint,
    id_contatto bigint,
    id_provincia character varying,
    specifica_qualifica character varying,
    motivazione character varying,
    no_obbligo_iscrizione_ordine boolean DEFAULT false,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_r_contatto_qualifica OWNER TO mudeopen;

--
-- Name: mudeopen_r_contatto_qualifica_id_contatto_qualifica_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_contatto_qualifica_id_contatto_qualifica_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_contatto_qualifica_id_contatto_qualifica_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_contatto_qualifica_id_contatto_qualifica_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_contatto_qualifica_id_contatto_qualifica_seq OWNED BY mudeopen_r_contatto_qualifica.id_contatto_qualifica;


--
-- Name: mudeopen_r_ente_comune_tipo_istanza; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_ente_comune_tipo_istanza (
    id_ente_comune_tipo_istanza bigint NOT NULL,
    id_ente bigint NOT NULL,
    id_comune bigint NOT NULL,
    codice_tipo_istanza character varying(20) NOT NULL,
    codice_specie_pratica character varying(20),
    data_inizio timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    competenza_principale boolean NOT NULL,
    responsabile_procedimento character varying,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_r_ente_comune_tipo_istanza OWNER TO mudeopen;

--
-- Name: mudeopen_r_ente_comune_tipo_ist_id_ente_comune_tipo_istanza_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_ente_comune_tipo_ist_id_ente_comune_tipo_istanza_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_ente_comune_tipo_ist_id_ente_comune_tipo_istanza_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_ente_comune_tipo_ist_id_ente_comune_tipo_istanza_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_ente_comune_tipo_ist_id_ente_comune_tipo_istanza_seq OWNED BY mudeopen_r_ente_comune_tipo_istanza.id_ente_comune_tipo_istanza;


--
-- Name: mudeopen_r_ente_fruitore; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_ente_fruitore (
    id_ente_fruitore bigint NOT NULL,
    id_ente integer NOT NULL,
    id_fruitore integer NOT NULL,
    data_inizio timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_r_ente_fruitore OWNER TO mudeopen;

--
-- Name: mudeopen_r_ente_fruitore_id_ente_fruitore_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_ente_fruitore_id_ente_fruitore_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_ente_fruitore_id_ente_fruitore_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_ente_fruitore_id_ente_fruitore_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_ente_fruitore_id_ente_fruitore_seq OWNED BY mudeopen_r_ente_fruitore.id_ente_fruitore;


--
-- Name: mudeopen_r_fabbr_civici; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_fabbr_civici (
    fk_civici integer NOT NULL,
    fk_fabbricati integer,
    fk_civici_city character varying,
    fk_fabbricati_city character varying,
    pk_r_fabbr_civici integer,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_r_fabbr_civici OWNER TO mudeopen;

--
-- Name: mudeopen_r_fascicolo_indirizzo; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_fascicolo_indirizzo (
    id_fascicolo_indirizzo bigint NOT NULL,
    id_fascicolo bigint NOT NULL,
    id_indirizzo bigint NOT NULL,
    data_inizio timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now(),
    id_istanza_rif bigint
);


ALTER TABLE mudeopen_r_fascicolo_indirizzo OWNER TO mudeopen;

--
-- Name: mudeopen_r_fascicolo_indirizzo_id_fascicolo_indirizzo_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_fascicolo_indirizzo_id_fascicolo_indirizzo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_fascicolo_indirizzo_id_fascicolo_indirizzo_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_fascicolo_indirizzo_id_fascicolo_indirizzo_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_fascicolo_indirizzo_id_fascicolo_indirizzo_seq OWNED BY mudeopen_r_fascicolo_indirizzo.id_fascicolo_indirizzo;


--
-- Name: mudeopen_r_fascicolo_intestatario; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_fascicolo_intestatario (
    id_fascicolo_intestatario bigint NOT NULL,
    id_fascicolo bigint NOT NULL,
    id_intestatario bigint NOT NULL,
    data_inizio timestamp without time zone NOT NULL,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_r_fascicolo_intestatario OWNER TO mudeopen;

--
-- Name: mudeopen_r_fascicolo_intestatario_id_fascicolo_intestatario_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_fascicolo_intestatario_id_fascicolo_intestatario_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_fascicolo_intestatario_id_fascicolo_intestatario_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_fascicolo_intestatario_id_fascicolo_intestatario_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_fascicolo_intestatario_id_fascicolo_intestatario_seq OWNED BY mudeopen_r_fascicolo_intestatario.id_fascicolo_intestatario;


--
-- Name: mudeopen_r_fascicolo_ruolo; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_fascicolo_ruolo (
    id_fascicolo_ruolo bigint NOT NULL,
    id_fascicolo bigint NOT NULL,
    ruolo character varying(20) NOT NULL,
    id_user_ins bigint NOT NULL,
    guid_soggetto character varying NOT NULL,
    data_inizio timestamp without time zone NOT NULL,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_r_fascicolo_ruolo OWNER TO mudeopen;

--
-- Name: mudeopen_r_fascicolo_ruolo_id_fascicolo_ruolo_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_fascicolo_ruolo_id_fascicolo_ruolo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_fascicolo_ruolo_id_fascicolo_ruolo_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_fascicolo_ruolo_id_fascicolo_ruolo_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_fascicolo_ruolo_id_fascicolo_ruolo_seq OWNED BY mudeopen_r_fascicolo_ruolo.id_fascicolo_ruolo;


--
-- Name: mudeopen_r_fascicolo_soggetto; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_fascicolo_soggetto (
    id_fascicolo_soggetto bigint NOT NULL,
    id_istanza_soggetto bigint NOT NULL,
    id_fascicolo bigint NOT NULL,
    id_user_ins bigint NOT NULL,
    guid_soggetto character varying NOT NULL,
    data_inizio timestamp without time zone NOT NULL,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_r_fascicolo_soggetto OWNER TO mudeopen;

--
-- Name: mudeopen_r_fascicolo_soggetto_id_fascicolo_soggetto_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_fascicolo_soggetto_id_fascicolo_soggetto_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_fascicolo_soggetto_id_fascicolo_soggetto_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_fascicolo_soggetto_id_fascicolo_soggetto_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_fascicolo_soggetto_id_fascicolo_soggetto_seq OWNED BY mudeopen_r_fascicolo_soggetto.id_fascicolo_soggetto;


--
-- Name: mudeopen_r_fascicolo_stato; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_fascicolo_stato (
    id_fascicolo_stato bigint NOT NULL,
    id_fascicolo bigint NOT NULL,
    codice_stato_fascicolo character varying(20) NOT NULL,
    data_inizio timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_r_fascicolo_stato OWNER TO mudeopen;

--
-- Name: mudeopen_r_fascicolo_stato_id_fascicolo_stato_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_fascicolo_stato_id_fascicolo_stato_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_fascicolo_stato_id_fascicolo_stato_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_fascicolo_stato_id_fascicolo_stato_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_fascicolo_stato_id_fascicolo_stato_seq OWNED BY mudeopen_r_fascicolo_stato.id_fascicolo_stato;


--
-- Name: mudeopen_r_fascicolo_utente; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_fascicolo_utente (
    id_fascicolo_utente bigint NOT NULL,
    id_fascicolo bigint NOT NULL,
    id_utente bigint NOT NULL,
    id_abilitazione bigint NOT NULL,
    data_inizio timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    id_abilitatore bigint NOT NULL,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_r_fascicolo_utente OWNER TO mudeopen;

--
-- Name: mudeopen_r_fascicolo_utente_id_fascicolo_utente_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_fascicolo_utente_id_fascicolo_utente_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_fascicolo_utente_id_fascicolo_utente_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_fascicolo_utente_id_fascicolo_utente_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_fascicolo_utente_id_fascicolo_utente_seq OWNED BY mudeopen_r_fascicolo_utente.id_fascicolo_utente;


--
-- Name: mudeopen_r_istanza_ente; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_istanza_ente (
    id_istanza_ente bigint DEFAULT nextval('mudeopen.mudeopen_r_istanza_ente_id_istanza_ente_seq'::regclass) NOT NULL,
    id_istanza bigint NOT NULL,
    id_ente bigint NOT NULL,
    ente_ricevente boolean NOT NULL,
    data_inizio date DEFAULT now() NOT NULL,
    data_fine date,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_r_istanza_ente OWNER TO mudeopen;

--
-- Name: mudeopen_r_istanza_ext_moon_stato; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_istanza_ext_moon_stato (
    id_istanza_ext_moon bigint NOT NULL,
    cod_stato_istanza_ext_moon character varying(3) NOT NULL,
    data_inizio timestamp without time zone NOT NULL,
    data_fine timestamp without time zone,
    oggetto_notifica character varying(120),
    messaggio_notifica character varying(2000),
    gest_attore_ins character varying(30) NOT NULL,
    gest_data_ins timestamp without time zone NOT NULL,
    gest_attore_upd character varying(30) NOT NULL,
    gest_data_upd timestamp without time zone NOT NULL,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_r_istanza_ext_moon_stato OWNER TO mudeopen;

--
-- Name: mudeopen_r_istanza_pratica; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_istanza_pratica (
    id_istanza_pratica bigint NOT NULL,
    id_istanza bigint NOT NULL,
    id_pratica bigint NOT NULL,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_r_istanza_pratica OWNER TO mudeopen;

--
-- Name: mudeopen_r_istanza_pratica_id_istanza_pratica_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_istanza_pratica_id_istanza_pratica_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_istanza_pratica_id_istanza_pratica_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_istanza_pratica_id_istanza_pratica_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_istanza_pratica_id_istanza_pratica_seq OWNED BY mudeopen_r_istanza_pratica.id_istanza_pratica;


--
-- Name: mudeopen_r_istanza_quadro_utente; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_istanza_quadro_utente (
    id_istanza_quadro_utente bigint NOT NULL,
    id_istanza bigint NOT NULL,
    id_quadro bigint NOT NULL,
    id_utente bigint NOT NULL,
    data_modifica timestamp without time zone DEFAULT now() NOT NULL,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_r_istanza_quadro_utente OWNER TO mudeopen;

--
-- Name: mudeopen_r_istanza_quadro_utente_id_istanza_quadro_utente_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_istanza_quadro_utente_id_istanza_quadro_utente_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_istanza_quadro_utente_id_istanza_quadro_utente_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_istanza_quadro_utente_id_istanza_quadro_utente_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_istanza_quadro_utente_id_istanza_quadro_utente_seq OWNED BY mudeopen_r_istanza_quadro_utente.id_istanza_quadro_utente;


--
-- Name: mudeopen_r_istanza_soggetto; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_istanza_soggetto (
    id_istanza_soggetto bigint NOT NULL,
    id_soggetto bigint,
    id_istanza bigint,
    opera_fini_fiscali_dipendente boolean DEFAULT false,
    ente_societa_appartenenza character varying,
    domiciliazione_corrispondenza_professionista boolean DEFAULT false,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_r_istanza_soggetto OWNER TO mudeopen;

--
-- Name: mudeopen_r_istanza_soggetto_id_istanza_soggetto_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_istanza_soggetto_id_istanza_soggetto_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_istanza_soggetto_id_istanza_soggetto_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_istanza_soggetto_id_istanza_soggetto_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_istanza_soggetto_id_istanza_soggetto_seq OWNED BY mudeopen_r_istanza_soggetto.id_istanza_soggetto;


--
-- Name: mudeopen_r_istanza_soggetto_ruoli; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_istanza_soggetto_ruoli (
    id_istanza_soggetto bigint NOT NULL,
    ruoli character varying(20) NOT NULL,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_r_istanza_soggetto_ruoli OWNER TO mudeopen;

--
-- Name: mudeopen_r_istanza_stato; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_istanza_stato (
    id_istanza_stato bigint NOT NULL,
    id_istanza bigint NOT NULL,
    codice_stato_istanza character varying(20) NOT NULL,
    data_inizio timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_r_istanza_stato OWNER TO mudeopen;

--
-- Name: mudeopen_r_istanza_stato_id_istanza_stato_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_istanza_stato_id_istanza_stato_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_istanza_stato_id_istanza_stato_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_istanza_stato_id_istanza_stato_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_istanza_stato_id_istanza_stato_seq OWNED BY mudeopen_r_istanza_stato.id_istanza_stato;


--
-- Name: mudeopen_r_istanza_tipo_opera; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_istanza_tipo_opera (
    id_istanza_tipo_opera bigint NOT NULL,
    id_tipo_opera character varying(20),
    id_istanza bigint,
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_r_istanza_tipo_opera OWNER TO mudeopen;

--
-- Name: mudeopen_r_istanza_tipo_opera_id_istanza_tipo_opera_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_istanza_tipo_opera_id_istanza_tipo_opera_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_istanza_tipo_opera_id_istanza_tipo_opera_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_istanza_tipo_opera_id_istanza_tipo_opera_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_istanza_tipo_opera_id_istanza_tipo_opera_seq OWNED BY mudeopen_r_istanza_tipo_opera.id_istanza_tipo_opera;


--
-- Name: mudeopen_r_istanza_tracciato; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_istanza_tracciato (
    id_istanza_tracciato bigint DEFAULT nextval('mudeopen.mudeopen_r_istanza_tracciato_id_istanza_tracciato_seq'::regclass) NOT NULL,
    id_istanza bigint NOT NULL,
    id_tipo_tracciato bigint NOT NULL,
    xml_tracciato text,
    data_inizio timestamp without time zone NOT NULL,
    user_id bigint,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_r_istanza_tracciato OWNER TO mudeopen;

--
-- Name: mudeopen_r_istanza_utente; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_istanza_utente (
    id_istanza_utente bigint NOT NULL,
    id_istanza bigint NOT NULL,
    id_utente bigint NOT NULL,
    id_abilitazione bigint NOT NULL,
    data_inizio timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    id_abilitatore bigint NOT NULL,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_r_istanza_utente OWNER TO mudeopen;

--
-- Name: mudeopen_r_istanza_utente_id_istanza_utente_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_istanza_utente_id_istanza_utente_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_istanza_utente_id_istanza_utente_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_istanza_utente_id_istanza_utente_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_istanza_utente_id_istanza_utente_seq OWNED BY mudeopen_r_istanza_utente.id_istanza_utente;


--
-- Name: mudeopen_r_istanza_utente_quadro; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_istanza_utente_quadro (
    id_istanza_utente_quadro bigint NOT NULL,
    id_istanza_utente bigint NOT NULL,
    id_quadro bigint NOT NULL,
    id_funzione bigint NOT NULL,
    id_abilitazione bigint NOT NULL,
    data_inizio timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_r_istanza_utente_quadro OWNER TO mudeopen;

--
-- Name: mudeopen_r_istanza_utente_quadro_id_istanza_utente_quadro_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_istanza_utente_quadro_id_istanza_utente_quadro_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_istanza_utente_quadro_id_istanza_utente_quadro_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_istanza_utente_quadro_id_istanza_utente_quadro_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_istanza_utente_quadro_id_istanza_utente_quadro_seq OWNED BY mudeopen_r_istanza_utente_quadro.id_istanza_utente_quadro;


--
-- Name: mudeopen_r_loc_catasto; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_loc_catasto (
    id_catasto bigint NOT NULL,
    id_georiferimento bigint,
    id_fabbricato bigint,
    id_cellula bigint,
    id_istanza bigint,
    sezione character varying,
    sezione_urbana character varying,
    foglio character varying,
    particella character varying,
    subalterno character varying,
    f1_tipo_catasto character varying,
    f1_personalizzato character varying,
    f1_personalizzato_dettaglio character varying,
    desc_fonte_catasto character varying,
    chiave_carotaggio character varying,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_r_loc_catasto OWNER TO mudeopen;

--
-- Name: mudeopen_r_loc_catasto_id_catasto_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_loc_catasto_id_catasto_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_loc_catasto_id_catasto_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_loc_catasto_id_catasto_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_loc_catasto_id_catasto_seq OWNED BY mudeopen_r_loc_catasto.id_catasto;


--
-- Name: mudeopen_r_loc_cellula; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_loc_cellula (
    id_cellula bigint NOT NULL,
    id_georiferimento bigint,
    chiave_carotaggio character varying,
    cod_cellula character varying,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_r_loc_cellula OWNER TO mudeopen;

--
-- Name: mudeopen_r_loc_cellula_id_cellula_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_loc_cellula_id_cellula_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_loc_cellula_id_cellula_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_loc_cellula_id_cellula_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_loc_cellula_id_cellula_seq OWNED BY mudeopen_r_loc_cellula.id_cellula;


--
-- Name: mudeopen_r_loc_datocarota; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_loc_datocarota (
    id_geo_datocarota bigint NOT NULL,
    id_georiferimento bigint,
    tipo_carotaggio character varying,
    id_fonte character varying,
    desc_fonte character varying,
    id_livello character varying,
    desc_livello character varying,
    valore character varying,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_r_loc_datocarota OWNER TO mudeopen;

--
-- Name: mudeopen_r_loc_datocarota_id_geo_datocarota_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_loc_datocarota_id_geo_datocarota_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_loc_datocarota_id_geo_datocarota_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_loc_datocarota_id_geo_datocarota_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_loc_datocarota_id_geo_datocarota_seq OWNED BY mudeopen_r_loc_datocarota.id_geo_datocarota;


--
-- Name: mudeopen_r_loc_fabbricato; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_loc_fabbricato (
    id_fabbricato bigint NOT NULL,
    id_cellula bigint,
    chiave_carotaggio character varying,
    cod_fabbricato character varying,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_r_loc_fabbricato OWNER TO mudeopen;

--
-- Name: mudeopen_r_loc_fabbricato_id_fabbricato_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_loc_fabbricato_id_fabbricato_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_loc_fabbricato_id_fabbricato_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_loc_fabbricato_id_fabbricato_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_loc_fabbricato_id_fabbricato_seq OWNED BY mudeopen_r_loc_fabbricato.id_fabbricato;


--
-- Name: mudeopen_r_loc_georiferim; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_loc_georiferim (
    id_georiferimento bigint NOT NULL,
    id_istanza bigint NOT NULL,
    id_livello_poligono bigint,
    desc_livello_poligono character varying,
    servizio_fonte character varying,
    servizio_livello character varying,
    geometria_sdo character varying,
    data_georiferimento date,
    cod_istat_comune character varying,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_r_loc_georiferim OWNER TO mudeopen;

--
-- Name: mudeopen_r_loc_georiferim_id_georiferimento_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_loc_georiferim_id_georiferimento_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_loc_georiferim_id_georiferimento_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_loc_georiferim_id_georiferimento_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_loc_georiferim_id_georiferimento_seq OWNED BY mudeopen_r_loc_georiferim.id_georiferimento;


--
-- Name: mudeopen_r_loc_titolare; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_loc_titolare (
    id_titolare bigint NOT NULL,
    id_catasto bigint,
    cf_titolare character varying,
    cognome character varying,
    nome character varying,
    partita_iva character varying,
    denominazione character varying,
    desc_fonte_titolare character varying,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_r_loc_titolare OWNER TO mudeopen;

--
-- Name: mudeopen_r_loc_titolare_id_titolare_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_loc_titolare_id_titolare_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_loc_titolare_id_titolare_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_loc_titolare_id_titolare_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_loc_titolare_id_titolare_seq OWNED BY mudeopen_r_loc_titolare.id_titolare;


--
-- Name: mudeopen_r_loc_ubicazione; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_loc_ubicazione (
    id_ubicazione bigint NOT NULL,
    id_georiferimento bigint,
    id_fabbricato bigint,
    id_cellula bigint,
    id_istanza bigint,
    sedime character varying,
    desc_via character varying,
    num_civico character varying,
    bis character varying,
    bisinterno character varying,
    interno character varying,
    interno2 character varying,
    scala character varying,
    secondario character varying,
    cap character varying,
    f1_personalizzato character varying,
    f1_personalizzato_dettaglio character varying,
    piano character varying,
    id_civico_toponom character varying,
    id_via_toponom character varying,
    desc_fonte_ubicazione character varying,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_r_loc_ubicazione OWNER TO mudeopen;

--
-- Name: mudeopen_r_loc_ubicazione_id_ubicazione_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_loc_ubicazione_id_ubicazione_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_loc_ubicazione_id_ubicazione_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_loc_ubicazione_id_ubicazione_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_loc_ubicazione_id_ubicazione_seq OWNED BY mudeopen_r_loc_ubicazione.id_ubicazione;


--
-- Name: mudeopen_r_notifica_contatto; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_notifica_contatto (
    id_notifica_contatto bigint DEFAULT nextval('mudeopen.mudeopen_r_notifica_contatto_id_notifica_contatto_seq'::regclass) NOT NULL,
    id_notifica bigint NOT NULL,
    id_contatto bigint NOT NULL,
    email_num_retry bigint,
    email_status character varying(20) NOT NULL,
    data_invio timestamp without time zone,
    data_last_retry timestamp without time zone,
    body_email character varying,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_r_notifica_contatto OWNER TO mudeopen;

--
-- Name: mudeopen_r_notifica_documento; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_notifica_documento (
    id_notifica_documento bigint DEFAULT nextval('mudeopen.mudeopen_r_notifica_documento_id_notifica_documento_seq'::regclass) NOT NULL,
    id_notifica bigint NOT NULL,
    id_documento bigint NOT NULL,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_r_notifica_documento OWNER TO mudeopen;

--
-- Name: mudeopen_r_notifica_utente; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_notifica_utente (
    id_notifica_utente bigint DEFAULT nextval('mudeopen.mudeopen_r_notifica_utente_id_notifica_utente_seq'::regclass) NOT NULL,
    id_notifica bigint NOT NULL,
    id_utente bigint NOT NULL,
    letto boolean DEFAULT false,
    data_lettura timestamp without time zone,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_r_notifica_utente OWNER TO mudeopen;

--
-- Name: mudeopen_r_pf_pg; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_pf_pg (
    id_pf_pg bigint NOT NULL,
    id_soggetto_pf bigint NOT NULL,
    id_soggetto_pg bigint NOT NULL,
    id_titolo character varying(20) NOT NULL,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_r_pf_pg OWNER TO mudeopen;

--
-- Name: mudeopen_r_pf_pg_id_pf_pg_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_pf_pg_id_pf_pg_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_pf_pg_id_pf_pg_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_pf_pg_id_pf_pg_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_pf_pg_id_pf_pg_seq OWNED BY mudeopen_r_pf_pg.id_pf_pg;


--
-- Name: mudeopen_r_ruolo_fruitore; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_ruolo_fruitore (
    id_ruolo_fruitore bigint NOT NULL,
    codice_ruolo character varying NOT NULL,
    id_fruitore integer NOT NULL,
    data_inizio timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now(),
    id_tipo_istanza character varying
);


ALTER TABLE mudeopen_r_ruolo_fruitore OWNER TO mudeopen;

--
-- Name: mudeopen_r_ruolo_fruitore_id_ruolo_fruitore_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_ruolo_fruitore_id_ruolo_fruitore_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_ruolo_fruitore_id_ruolo_fruitore_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_ruolo_fruitore_id_ruolo_fruitore_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_ruolo_fruitore_id_ruolo_fruitore_seq OWNED BY mudeopen_r_ruolo_fruitore.id_ruolo_fruitore;


--
-- Name: mudeopen_r_ruolo_funzione; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_ruolo_funzione (
    id_ruolo_funzione bigint NOT NULL,
    codice_ruolo_utente character varying(20) NOT NULL,
    id_funzione bigint NOT NULL,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_r_ruolo_funzione OWNER TO mudeopen;

--
-- Name: mudeopen_r_ruolo_funzione_id_ruolo_funzione_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_ruolo_funzione_id_ruolo_funzione_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_ruolo_funzione_id_ruolo_funzione_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_ruolo_funzione_id_ruolo_funzione_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_ruolo_funzione_id_ruolo_funzione_seq OWNED BY mudeopen_r_ruolo_funzione.id_ruolo_funzione;


--
-- Name: mudeopen_r_sism_class_opere; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_sism_class_opere (
    id_class_opere bigint NOT NULL,
    id_denuncia_sism bigint,
    id_class_opera bigint,
    descr_class_opera character varying,
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_r_sism_class_opere OWNER TO mudeopen;

--
-- Name: mudeopen_r_sism_class_opere_id_class_opere_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_sism_class_opere_id_class_opere_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_sism_class_opere_id_class_opere_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_sism_class_opere_id_class_opere_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_sism_class_opere_id_class_opere_seq OWNED BY mudeopen_r_sism_class_opere.id_class_opere;


--
-- Name: mudeopen_r_sism_istanze_rif; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_sism_istanze_rif (
    id_sism_istanza_rif bigint NOT NULL,
    id_denuncia_sism bigint,
    istanza_rif_id bigint,
    istanza_rif_desc character varying(200),
    istanza_rif_prot character varying(50),
    istanza_rif_data date,
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_r_sism_istanze_rif OWNER TO mudeopen;

--
-- Name: mudeopen_r_sism_istanze_rif_id_sism_istanza_rif_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_sism_istanze_rif_id_sism_istanza_rif_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_sism_istanze_rif_id_sism_istanza_rif_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_sism_istanze_rif_id_sism_istanza_rif_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_sism_istanze_rif_id_sism_istanza_rif_seq OWNED BY mudeopen_r_sism_istanze_rif.id_sism_istanza_rif;


--
-- Name: mudeopen_r_sism_rel_ill_norma; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_sism_rel_ill_norma (
    id_sism_rel_ill_norma bigint NOT NULL,
    id_sism_rel_ill bigint,
    id_sism_norma character varying,
    descr_norma character varying(50),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_r_sism_rel_ill_norma OWNER TO mudeopen;

--
-- Name: mudeopen_r_sism_rel_ill_norma_id_sism_rel_ill_norma_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_sism_rel_ill_norma_id_sism_rel_ill_norma_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_sism_rel_ill_norma_id_sism_rel_ill_norma_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_sism_rel_ill_norma_id_sism_rel_ill_norma_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_sism_rel_ill_norma_id_sism_rel_ill_norma_seq OWNED BY mudeopen_r_sism_rel_ill_norma.id_sism_rel_ill_norma;


--
-- Name: mudeopen_r_specie_pratica_tipo_intervento; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_specie_pratica_tipo_intervento (
    id_specie_pratica_tipo_intervento bigint DEFAULT nextval('mudeopen.mudeopen_r_specie_pratica_tipo_intervento_id_seq'::regclass) NOT NULL,
    id_tipo_intervento character varying NOT NULL,
    id_specie_pratica character varying NOT NULL,
    abilitato boolean DEFAULT false,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_r_specie_pratica_tipo_intervento OWNER TO mudeopen;

--
-- Name: mudeopen_r_specie_pratica_tipo_intervento_paesaggistica; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_specie_pratica_tipo_intervento_paesaggistica (
    id_specie_pratica_tipo_intervento_paesaggistica bigint DEFAULT nextval('mudeopen.mudeopen_r_specie_pratica_tipo_intervento_paesaggistica_id_seq'::regclass) NOT NULL,
    id_specie_pratica character varying NOT NULL,
    id_tipo_intervento_paesaggistica character varying NOT NULL,
    abilitato boolean DEFAULT false,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_r_specie_pratica_tipo_intervento_paesaggistica OWNER TO mudeopen;

--
-- Name: mudeopen_r_specie_pratica_tipo_opera; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_specie_pratica_tipo_opera (
    id_specie_pratica_tipo_opera bigint DEFAULT nextval('mudeopen.mudeopen_r_specie_pratica_tipo_opera_id_seq'::regclass) NOT NULL,
    id_specie_pratica character varying NOT NULL,
    id_tipo_opera character varying NOT NULL,
    abilitato boolean DEFAULT false,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_r_specie_pratica_tipo_opera OWNER TO mudeopen;

--
-- Name: mudeopen_r_template_quadro; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_template_quadro (
    id_template_quadro bigint NOT NULL,
    id_template integer NOT NULL,
    id_quadro integer NOT NULL,
    ordinamento_template_quadro integer,
    flg_quadro_obbigatorio numeric(1,0) NOT NULL,
    proprieta character varying,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    CONSTRAINT chk_mudeopen_r_template_quad_01 CHECK ((flg_quadro_obbigatorio = ANY (ARRAY[(0)::numeric, (1)::numeric])))
);


ALTER TABLE mudeopen_r_template_quadro OWNER TO mudeopen;

--
-- Name: mudeopen_r_template_quadro_id_template_quadro_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_template_quadro_id_template_quadro_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_template_quadro_id_template_quadro_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_template_quadro_id_template_quadro_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_template_quadro_id_template_quadro_seq OWNED BY mudeopen_r_template_quadro.id_template_quadro;


--
-- Name: mudeopen_r_template_tipo_allegato; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_template_tipo_allegato (
    id_template_tipo_allegato bigint NOT NULL,
    id_template bigint NOT NULL,
    id_tipo_allegato character varying(20) NOT NULL,
    ordinamento integer NOT NULL,
    obbligatorio boolean DEFAULT false NOT NULL,
    ripetibile boolean DEFAULT false NOT NULL,
    espressione_obbligatorieta text,
    espressione_ripetibilita text,
    id_modello_documentale integer,
    flag_ricorrente boolean DEFAULT false NOT NULL,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_r_template_tipo_allegato OWNER TO mudeopen;

--
-- Name: mudeopen_r_template_tipo_allegato_id_template_tipo_allegato_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_template_tipo_allegato_id_template_tipo_allegato_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_template_tipo_allegato_id_template_tipo_allegato_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_template_tipo_allegato_id_template_tipo_allegato_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_template_tipo_allegato_id_template_tipo_allegato_seq OWNED BY mudeopen_r_template_tipo_allegato.id_template_tipo_allegato;


--
-- Name: mudeopen_r_tipo_istanza; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_tipo_istanza (
    id_tipo_istanza bigint NOT NULL,
    cod_tipo_istanza_figlia character varying(20) NOT NULL,
    cod_tipo_istanza_padre character varying(20),
    campo_json_padre character varying,
    cod_tipo_istanza_padre2 character varying(20),
    ripetibile integer,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_r_tipo_istanza OWNER TO mudeopen;

--
-- Name: mudeopen_r_tipo_istanza_fruitore; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_tipo_istanza_fruitore (
    id_tipo_istanza_fruitore integer NOT NULL,
    id_tipo_istanza character varying NOT NULL,
    id_fruitore integer NOT NULL,
    data_inizio timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_r_tipo_istanza_fruitore OWNER TO mudeopen;

--
-- Name: mudeopen_r_tipo_istanza_fruitore_id_tipo_istanza_fruitore_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_tipo_istanza_fruitore_id_tipo_istanza_fruitore_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_tipo_istanza_fruitore_id_tipo_istanza_fruitore_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_tipo_istanza_fruitore_id_tipo_istanza_fruitore_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_tipo_istanza_fruitore_id_tipo_istanza_fruitore_seq OWNED BY mudeopen_r_tipo_istanza_fruitore.id_tipo_istanza_fruitore;


--
-- Name: mudeopen_r_tipo_istanza_id_tipo_istanza_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_tipo_istanza_id_tipo_istanza_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_tipo_istanza_id_tipo_istanza_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_tipo_istanza_id_tipo_istanza_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_tipo_istanza_id_tipo_istanza_seq OWNED BY mudeopen_r_tipo_istanza.id_tipo_istanza;


--
-- Name: mudeopen_r_tipo_istanza_regime_g_regime_a; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_tipo_istanza_regime_g_regime_a (
    id_tipo_istanza_regime_g_regime_a bigint DEFAULT nextval('mudeopen.mudeopen_r_tipo_istanza_regime_g_regime_a_id_seq'::regclass) NOT NULL,
    id_regime_aggiuntivo bigint,
    id_regime_giuridico bigint,
    id_tipo_istanza character varying(20),
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_r_tipo_istanza_regime_g_regime_a OWNER TO mudeopen;

--
-- Name: mudeopen_r_tipo_istanza_ruolo_sog; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_tipo_istanza_ruolo_sog (
    id_tipo_istanza_ruolo_sog bigint DEFAULT nextval('mudeopen.mudeopen_r_tipo_istanza_ruolo_sog_id_tipo_istanza_ruolo_sog_seq'::regclass) NOT NULL,
    obbligatorio boolean,
    id_ruolo_soggetto character varying(20),
    id_tipo_istanza character varying(20),
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_r_tipo_istanza_ruolo_sog OWNER TO mudeopen;

--
-- Name: mudeopen_r_tipo_istanza_specie_pratica; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_tipo_istanza_specie_pratica (
    id_tipo_istanza_specie_pratica bigint DEFAULT nextval('mudeopen.mudeopen_r_tipo_istanza_specie_pratica_id_seq'::regclass) NOT NULL,
    id_tipo_istanza character varying NOT NULL,
    id_specie_pratica character varying NOT NULL,
    abilitato boolean DEFAULT false,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_r_tipo_istanza_specie_pratica OWNER TO mudeopen;

--
-- Name: mudeopen_r_tipo_istanza_stato; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_tipo_istanza_stato (
    id_tipo_istanza_stato bigint NOT NULL,
    id_tipo_istanza character varying(20) NOT NULL,
    codice_stato_istanza character varying(20) NOT NULL,
    data_inizio timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_r_tipo_istanza_stato OWNER TO mudeopen;

--
-- Name: mudeopen_r_tipo_istanza_stato_id_tipo_istanza_stato_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_tipo_istanza_stato_id_tipo_istanza_stato_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_tipo_istanza_stato_id_tipo_istanza_stato_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_tipo_istanza_stato_id_tipo_istanza_stato_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_tipo_istanza_stato_id_tipo_istanza_stato_seq OWNED BY mudeopen_r_tipo_istanza_stato.id_tipo_istanza_stato;


--
-- Name: mudeopen_r_tipo_istanza_tipo_intervento; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_tipo_istanza_tipo_intervento (
    id_tipo_istanza_tipo_intervento bigint NOT NULL,
    codice_tipo_intervento character varying(20) NOT NULL,
    codice_tipo_istanza character varying(20) NOT NULL,
    data_inizio timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_r_tipo_istanza_tipo_intervento OWNER TO mudeopen;

--
-- Name: mudeopen_r_tipo_istanza_tipo_intervento_id_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_tipo_istanza_tipo_intervento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_tipo_istanza_tipo_intervento_id_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_tipo_istanza_tipo_intervento_id_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_tipo_istanza_tipo_intervento_id_seq OWNED BY mudeopen_r_tipo_istanza_tipo_intervento.id_tipo_istanza_tipo_intervento;


--
-- Name: mudeopen_r_tipo_istanza_tipo_opera_diretta; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_tipo_istanza_tipo_opera_diretta (
    id_tipo_istanza_tipo_opera_diretta bigint DEFAULT nextval('mudeopen.mudeopen_r_tipo_istanza_tipo_opera_diretta_id_seq'::regclass) NOT NULL,
    id_tipo_istanza character varying NOT NULL,
    id_tipo_opera character varying NOT NULL,
    abilitato boolean DEFAULT false,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_r_tipo_istanza_tipo_opera_diretta OWNER TO mudeopen;

--
-- Name: mudeopen_r_tipo_istanza_tipo_tracciato; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_tipo_istanza_tipo_tracciato (
    id_tipo_istanza_tipo_tracciato bigint DEFAULT nextval('mudeopen.mudeopen_r_tipo_istanza_tipo_tracciato_id_seq'::regclass) NOT NULL,
    id_tipo_istanza character varying NOT NULL,
    id_tipo_tracciato bigint NOT NULL,
    xslt_template_principale text NOT NULL,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    attiva boolean DEFAULT false,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_r_tipo_istanza_tipo_tracciato OWNER TO mudeopen;

--
-- Name: mudeopen_r_utente_ente; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_utente_ente (
    id_utente_ente bigint NOT NULL,
    id_utente bigint NOT NULL,
    id_ente bigint NOT NULL,
    data_inizio timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_r_utente_ente OWNER TO mudeopen;

--
-- Name: mudeopen_r_utente_ente_comune_ruolo; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_utente_ente_comune_ruolo (
    id_utente_ente_comune_ruolo bigint DEFAULT nextval('mudeopen.mudeopen_r_utente_ente_comune_ruolo_seq'::regclass) NOT NULL,
    id_ente bigint NOT NULL,
    id_comune bigint NOT NULL,
    id_utente bigint NOT NULL,
    cod_ruolo character varying NOT NULL,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_r_utente_ente_comune_ruolo OWNER TO mudeopen;

--
-- Name: mudeopen_r_utente_ente_id_utente_ente_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_utente_ente_id_utente_ente_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_utente_ente_id_utente_ente_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_utente_ente_id_utente_ente_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_utente_ente_id_utente_ente_seq OWNED BY mudeopen_r_utente_ente.id_utente_ente;


--
-- Name: mudeopen_r_utente_ruolo; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_r_utente_ruolo (
    id_utente_ruolo bigint NOT NULL,
    id_utente bigint NOT NULL,
    codice_ruolo_utente character varying(20) NOT NULL,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_r_utente_ruolo OWNER TO mudeopen;

--
-- Name: mudeopen_r_utente_ruolo_id_utente_ruolo_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_r_utente_ruolo_id_utente_ruolo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_r_utente_ruolo_id_utente_ruolo_seq OWNER TO mudeopen;

--
-- Name: mudeopen_r_utente_ruolo_id_utente_ruolo_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_r_utente_ruolo_id_utente_ruolo_seq OWNED BY mudeopen_r_utente_ruolo.id_utente_ruolo;


--
-- Name: mudeopen_t_aeos; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_aeos (
    id_istanza bigint NOT NULL,
    data_effettiva_inizio_lavori character varying,
    data_prot_inizio_lavori character varying,
    num_prot_inizio_lavori character varying,
    data_effettiva_fine_lavori character varying,
    data_prot_fine_lavori character varying,
    num_prot_fine_lavori character varying,
    data_prot_nomina_coll character varying,
    protocollo_nomina_coll character varying,
    dataprotcertificato character varying,
    numprotcertificato character varying,
    data_prot_collaudo character varying,
    protocollo_collaudo character varying,
    data_prot_dre character varying,
    protocollo_dre character varying,
    data_prot_rsu character varying,
    protocollo_rsu character varying,
    datacompilazione character varying,
    notesism character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_t_aeos OWNER TO mudeopen;

--
-- Name: mudeopen_t_allegato; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_allegato (
    id_allegato bigint NOT NULL,
    id_tipo_allegato character varying(20) NOT NULL,
    id_istanza bigint NOT NULL,
    nome_file_allegato character varying(255) NOT NULL,
    desc_breve_allegato character varying(255),
    firmato boolean DEFAULT false,
    index_node character varying(255) NOT NULL,
    dimensione_file integer NOT NULL,
    data_caricamento timestamp without time zone NOT NULL,
    file_content bytea,
    id_user bigint,
    mimetype character varying,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    flg_annullato boolean,
    protocollo character varying,
    data_protocollo character varying
);


ALTER TABLE mudeopen_t_allegato OWNER TO mudeopen;

--
-- Name: mudeopen_t_allegato_id_allegato_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_allegato_id_allegato_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_allegato_id_allegato_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_allegato_id_allegato_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_allegato_id_allegato_seq OWNED BY mudeopen_t_allegato.id_allegato;


--
-- Name: mudeopen_t_arcaeos_log; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_arcaeos_log (
    id_log bigint NOT NULL,
    id_fascicolo bigint,
    id_istanza bigint,
    id_allegato bigint,
    data_elaborazione timestamp without time zone,
    stato character varying(20),
    testo text,
    note text,
    azione character varying(20)
);


ALTER TABLE mudeopen_t_arcaeos_log OWNER TO mudeopen;

--
-- Name: mudeopen_t_arcaeos_log_id_log_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_arcaeos_log_id_log_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_arcaeos_log_id_log_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_arcaeos_log_id_log_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_arcaeos_log_id_log_seq OWNED BY mudeopen_t_arcaeos_log.id_log;


--
-- Name: mudeopen_t_contatto; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_contatto (
    id_contatto bigint NOT NULL,
    cellulare character varying(255),
    cf character varying(255),
    cognome character varying(255),
    data_nascita timestamp without time zone,
    mail character varying(255),
    nome character varying(255),
    pec character varying(255),
    piva character varying(255),
    ragione_sociale character varying(255),
    sesso character varying(255),
    telefono character varying(255),
    tipo_contatto character varying(255),
    id_comune_nascita bigint,
    id_user bigint,
    id_provincia_nascita bigint,
    id_stato_nascita bigint,
    nazionalita character varying(255),
    piva_comunitaria character varying(255),
    tipo_attivita character varying(255),
    id_stato_membro bigint,
    guid character varying,
    data_creazione timestamp without time zone,
    data_cessazione timestamp without time zone,
    citta_estera character varying(255),
    titolare boolean DEFAULT false,
    impresa_lavori boolean DEFAULT false,
    professionista boolean DEFAULT false,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE mudeopen_t_contatto OWNER TO mudeopen;

--
-- Name: mudeopen_t_contatto_id_contatto_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_contatto_id_contatto_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_contatto_id_contatto_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_contatto_id_contatto_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_contatto_id_contatto_seq OWNED BY mudeopen_t_contatto.id_contatto;


--
-- Name: mudeopen_t_dati_istanza; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_dati_istanza (
    id_dati_istanza bigint NOT NULL,
    dato character varying(255),
    valore character varying(255),
    id_istanza bigint,
    id_soggetto bigint,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    tit_arcaeos text
);


ALTER TABLE mudeopen_t_dati_istanza OWNER TO mudeopen;

--
-- Name: mudeopen_t_dati_istanza_id_dati_istanza_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_dati_istanza_id_dati_istanza_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_dati_istanza_id_dati_istanza_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_dati_istanza_id_dati_istanza_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_dati_istanza_id_dati_istanza_seq OWNED BY mudeopen_t_dati_istanza.id_dati_istanza;


--
-- Name: mudeopen_t_documento; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_documento (
    id_documento bigint NOT NULL,
    id_pratica bigint NOT NULL,
    id_tipo_documento bigint NOT NULL,
    nome_file_documento character varying(255) NOT NULL,
    index_node character varying(255) NOT NULL,
    dimensione_file integer NOT NULL,
    data_caricamento timestamp without time zone NOT NULL,
    file_content bytea,
    id_user bigint,
    data_annullamento timestamp without time zone,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    numero_protocollo character varying,
    data_protocollo character varying
);


ALTER TABLE mudeopen_t_documento OWNER TO mudeopen;

--
-- Name: COLUMN mudeopen_t_documento.data_annullamento; Type: COMMENT; Schema: mudeopen; Owner: mudeopen
--

COMMENT ON COLUMN mudeopen_t_documento.data_annullamento IS 'Timestamp eliminazione documento';


--
-- Name: mudeopen_t_documento_id_documento_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_documento_id_documento_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_documento_id_documento_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_documento_id_documento_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_documento_id_documento_seq OWNED BY mudeopen_t_documento.id_documento;


--
-- Name: mudeopen_t_ente; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_ente (
    codice character varying(20) NOT NULL,
    descrizione character varying(300) NOT NULL,
    descrizione_estesa character varying(1000),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    indirizzo_ente character varying,
    cap_ente character varying(5),
    responsabile_ente character varying(100),
    pec_ente character varying(200),
    id_comune bigint,
    id_tipo_ente character varying(20),
    id_ente bigint NOT NULL,
    logo_content bytea,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now(),
    email_gruppo text,
    intestazione text
);


ALTER TABLE mudeopen_t_ente OWNER TO mudeopen;

--
-- Name: mudeopen_t_ente_id_ente_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_ente_id_ente_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_ente_id_ente_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_ente_id_ente_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_ente_id_ente_seq OWNED BY mudeopen_t_ente.id_ente;


--
-- Name: mudeopen_t_fascicolo; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_fascicolo (
    id_fascicolo bigint NOT NULL,
    codice_fascicolo character varying(100),
    id_tipo_istanza character varying(20),
    data_creazione timestamp without time zone,
    id_user bigint,
    id_comune bigint,
    id_indirizzo integer,
    id_tipo_intervento character varying(20),
    json_data jsonb,
    index_folder character varying,
    data_modifica timestamp without time zone DEFAULT now(),
    loguser character varying,
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_t_fascicolo OWNER TO mudeopen;

--
-- Name: mudeopen_t_fascicolo_id_fascicolo_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_fascicolo_id_fascicolo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_fascicolo_id_fascicolo_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_fascicolo_id_fascicolo_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_fascicolo_id_fascicolo_seq OWNED BY mudeopen_t_fascicolo.id_fascicolo;


--
-- Name: mudeopen_t_geeco_comune; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_geeco_comune (
    id_comune bigint NOT NULL,
    fk_comune_geeco bigint,
    cod_belfiore_comune character varying(300) NOT NULL,
    denom_comune character varying(300),
    coordinate_gml character varying(300),
    selected_position jsonb,
    data_inizio timestamp without time zone DEFAULT now(),
    data_modifica timestamp without time zone,
    loguser character varying,
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_t_geeco_comune OWNER TO mudeopen;

--
-- Name: mudeopen_t_geeco_comune_id_comune_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_geeco_comune_id_comune_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_geeco_comune_id_comune_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_geeco_comune_id_comune_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_geeco_comune_id_comune_seq OWNED BY mudeopen_t_geeco_comune.id_comune;


--
-- Name: mudeopen_t_geeco_selected; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_geeco_selected (
    id_selected bigint NOT NULL,
    sessione_geeco character varying(300),
    selected_data jsonb,
    selected_position jsonb,
    data_inizio timestamp without time zone DEFAULT now(),
    data_modifica timestamp without time zone,
    loguser character varying,
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_t_geeco_selected OWNER TO mudeopen;

--
-- Name: mudeopen_t_geeco_selected_cllbk; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_geeco_selected_cllbk (
    id_selected bigint NOT NULL,
    sessione_geeco character varying(300),
    selected_data jsonb,
    selected_position jsonb,
    data_inizio timestamp without time zone,
    data_modifica timestamp without time zone,
    callback_result jsonb,
    loguser character varying,
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_t_geeco_selected_cllbk OWNER TO mudeopen;

--
-- Name: mudeopen_t_geeco_selected_cllbk_id_selected_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_geeco_selected_cllbk_id_selected_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_geeco_selected_cllbk_id_selected_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_geeco_selected_cllbk_id_selected_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_geeco_selected_cllbk_id_selected_seq OWNED BY mudeopen_t_geeco_selected_cllbk.id_selected;


--
-- Name: mudeopen_t_geeco_selected_id_selected_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_geeco_selected_id_selected_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_geeco_selected_id_selected_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_geeco_selected_id_selected_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_geeco_selected_id_selected_seq OWNED BY mudeopen_t_geeco_selected.id_selected;


--
-- Name: mudeopen_t_geeco_session; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_geeco_session (
    id_session bigint NOT NULL,
    id_user bigint,
    id_istanza bigint,
    id_localizzazione bigint,
    url_geeco character varying(300),
    sessione_geeco character varying(300),
    data_inizio timestamp without time zone DEFAULT now(),
    data_modifica timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying
);


ALTER TABLE mudeopen_t_geeco_session OWNER TO mudeopen;

--
-- Name: mudeopen_t_geeco_session_id_session_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_geeco_session_id_session_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_geeco_session_id_session_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_geeco_session_id_session_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_geeco_session_id_session_seq OWNED BY mudeopen_t_geeco_session.id_session;


--
-- Name: mudeopen_t_impersonate; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_impersonate (
    id_impersonate bigint NOT NULL,
    id_user_sostituito integer NOT NULL,
    codice_fiscale_sostituito character varying(16) NOT NULL,
    codice_fiscale_sostituente character varying(16) NOT NULL,
    data_creazione timestamp without time zone NOT NULL,
    data_riabilitazione timestamp without time zone,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_t_impersonate OWNER TO mudeopen;

--
-- Name: mudeopen_t_impersonate_id_impersonate_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_impersonate_id_impersonate_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_impersonate_id_impersonate_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_impersonate_id_impersonate_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_impersonate_id_impersonate_seq OWNED BY mudeopen_t_impersonate.id_impersonate;


--
-- Name: mudeopen_t_indirizzo; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_indirizzo (
    id_indirizzo integer NOT NULL,
    cap character varying(255),
    data_inizio timestamp without time zone,
    data_modifica timestamp without time zone,
    denom_comune_estero character varying(255),
    indirizzo character varying(255),
    numero_civico character varying(255),
    indirizzo_estero boolean,
    tipo_indirizzo character varying(255),
    id_comune bigint,
    id_nazione bigint,
    id_contatto bigint,
    id_dug bigint,
    localita character varying(255),
    telefono character varying,
    cellulare character varying,
    mail character varying,
    pec character varying,
    loguser character varying,
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_t_indirizzo OWNER TO mudeopen;

--
-- Name: mudeopen_t_indirizzo_id_indirizzo_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_indirizzo_id_indirizzo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_indirizzo_id_indirizzo_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_indirizzo_id_indirizzo_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_indirizzo_id_indirizzo_seq OWNED BY mudeopen_t_indirizzo.id_indirizzo;


--
-- Name: mudeopen_t_istanza; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_istanza (
    id_istanza bigint NOT NULL,
    id_tipo_istanza character varying(20),
    data_ultimo_stato timestamp without time zone,
    id_comune bigint,
    id_soggetto_rappresentato bigint,
    creatore character varying(255) DEFAULT 'CREATA'::character varying,
    json_data jsonb,
    codice_istanza character varying,
    id_fascicolo bigint,
    numero_pratica character varying(250),
    numero_protocollo character varying(250),
    anno integer,
    id_istanza_riferimento integer,
    data_registrazione_pratica date,
    flg_principale boolean DEFAULT false NOT NULL,
    id_template integer,
    index_folder character varying,
    index_node character varying,
    nome_file_istanza character varying,
    dimensione_file_istanza integer,
    data_caricamento_file_istanza timestamp without time zone,
    hash_file_istanza_generato character varying,
    id_indirizzo bigint,
    id_specie_pratica character varying(20),
    branch integer,
    id_user bigint,
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    rigenera_tracciato boolean DEFAULT false NOT NULL,
    data_protocollo timestamp without time zone,
    responsabile_procedimento character varying(150),
    id_fonte character varying(4) DEFAULT 'FO'::character varying,
    data_dps timestamp without time zone,
    id_fruitore bigint,
    keywords character varying,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_t_istanza OWNER TO mudeopen;

--
-- Name: COLUMN mudeopen_t_istanza.index_folder; Type: COMMENT; Schema: mudeopen; Owner: mudeopen
--

COMMENT ON COLUMN mudeopen_t_istanza.index_folder IS 'uuid della folder istanza su index';


--
-- Name: mudeopen_t_istanza_ext_moon; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_istanza_ext_moon (
    id_istanza_ext_moon bigint NOT NULL,
    codice_istanza character varying(50) NOT NULL,
    cod_stato_istanza_ext_moon character varying(3) NOT NULL,
    id_istanza bigint NOT NULL,
    dichiarante_cognome character varying(100) NOT NULL,
    dichiarante_nome character varying(100) NOT NULL,
    dichiarante_cod_fiscale character varying(16),
    codice_modulo character varying(30),
    versione_modulo character varying(30),
    codice_ente character varying(10),
    nome_ente character varying(50),
    data_creazione timestamp without time zone,
    data_protocollo timestamp without time zone,
    json_string text,
    attoreins character varying(16),
    gest_attore_ins character varying(30) NOT NULL,
    gest_data_ins timestamp without time zone NOT NULL,
    gest_attore_upd character varying(30) NOT NULL,
    gest_data_upd timestamp without time zone NOT NULL,
    fl_processata character varying(1),
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_t_istanza_ext_moon OWNER TO mudeopen;

--
-- Name: mudeopen_t_istanza_ext_moon_id_istanza_ext_moon_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_istanza_ext_moon_id_istanza_ext_moon_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_istanza_ext_moon_id_istanza_ext_moon_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_istanza_ext_moon_id_istanza_ext_moon_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_istanza_ext_moon_id_istanza_ext_moon_seq OWNED BY mudeopen_t_istanza_ext_moon.id_istanza_ext_moon;


--
-- Name: mudeopen_t_istanza_id_istanza_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_istanza_id_istanza_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_istanza_id_istanza_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_istanza_id_istanza_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_istanza_id_istanza_seq OWNED BY mudeopen_t_istanza.id_istanza;


--
-- Name: mudeopen_t_istanze_ext; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_istanze_ext (
    id_istanza bigint NOT NULL,
    ricevuta_pdf_content bytea,
    data_generazione timestamp without time zone,
    id_user_ins bigint,
    hash_file_istanza_generato character varying,
    pdf_cache bytea,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    dps_script_stato character varying,
    precario boolean,
    descrizione_intervento text,
    note text,
    titolo_intervento text
);


ALTER TABLE mudeopen_t_istanze_ext OWNER TO mudeopen;

--
-- Name: mudeopen_t_log_numeri_mude; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_log_numeri_mude (
    id integer NOT NULL,
    numero_mude character varying NOT NULL,
    tipo character varying NOT NULL,
    id_fruitore integer NOT NULL,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_t_log_numeri_mude OWNER TO mudeopen;

--
-- Name: mudeopen_t_log_numeri_mude_id_fruitore_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_log_numeri_mude_id_fruitore_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_log_numeri_mude_id_fruitore_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_log_numeri_mude_id_fruitore_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_log_numeri_mude_id_fruitore_seq OWNED BY mudeopen_t_log_numeri_mude.id_fruitore;


--
-- Name: mudeopen_t_log_numeri_mude_id_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_log_numeri_mude_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_log_numeri_mude_id_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_log_numeri_mude_id_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_log_numeri_mude_id_seq OWNED BY mudeopen_t_log_numeri_mude.id;


--
-- Name: mudeopen_t_log_tracciato; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_log_tracciato (
    id_log_tracciato bigint NOT NULL,
    id_istanza bigint NOT NULL,
    id_tipo_tracciato bigint,
    errore text NOT NULL,
    tipo_errore character varying,
    error_time timestamp without time zone DEFAULT now() NOT NULL,
    user_id bigint NOT NULL,
    mail_inviata boolean NOT NULL,
    used_xslt text,
    jsondata_as_xml text,
    generated_xml text,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_t_log_tracciato OWNER TO mudeopen;

--
-- Name: mudeopen_t_log_tracciato_id_log_tracciato_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_log_tracciato_id_log_tracciato_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_log_tracciato_id_log_tracciato_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_log_tracciato_id_log_tracciato_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_log_tracciato_id_log_tracciato_seq OWNED BY mudeopen_t_log_tracciato.id_log_tracciato;


--
-- Name: mudeopen_t_modello; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_modello (
    id_modello bigint NOT NULL,
    denominazione character varying(255) NOT NULL,
    path_modello character varying(255) NOT NULL,
    dimensione_file integer,
    file_content bytea,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_t_modello OWNER TO mudeopen;

--
-- Name: mudeopen_t_modello_id_modello_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_modello_id_modello_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_modello_id_modello_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_modello_id_modello_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_modello_id_modello_seq OWNED BY mudeopen_t_modello.id_modello;


--
-- Name: mudeopen_t_notifica; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_notifica (
    id_notifica bigint DEFAULT nextval('mudeopen.mudeopen_t_notifica_id_notifica_seq'::regclass) NOT NULL,
    id_tipo_notifica bigint,
    id_user_mittente bigint,
    id_istanza bigint,
    oggetto_notifica character varying(150),
    testo_notifica character varying(3000),
    json_data jsonb,
    dt_ins timestamp without time zone DEFAULT now(),
    ruolo_mittente character varying,
    dettaglio character varying,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_t_notifica OWNER TO mudeopen;

--
-- Name: mudeopen_t_ppay_pagamento; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_ppay_pagamento (
    id_pagamento bigint NOT NULL,
    id_istanza bigint NOT NULL,
    identificativo_pagamento character varying,
    url_wisp character varying,
    iuv character varying,
    importo character varying,
    codice_avviso character varying,
    codice_esito character varying(3),
    descrizione_esito character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_t_ppay_pagamento OWNER TO mudeopen;

--
-- Name: mudeopen_t_ppay_pagamento_id_pagamento_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_ppay_pagamento_id_pagamento_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_ppay_pagamento_id_pagamento_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_ppay_pagamento_id_pagamento_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_ppay_pagamento_id_pagamento_seq OWNED BY mudeopen_t_ppay_pagamento.id_pagamento;


--
-- Name: mudeopen_t_pratica; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_pratica (
    id_pratica bigint NOT NULL,
    numero_pratica character varying,
    anno_pratica bigint,
    id_ente bigint,
    index_folder character varying,
    data_inizio timestamp without time zone DEFAULT now(),
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now() NOT NULL,
    data_fine timestamp without time zone,
    ice text
);


ALTER TABLE mudeopen_t_pratica OWNER TO mudeopen;

--
-- Name: COLUMN mudeopen_t_pratica.index_folder; Type: COMMENT; Schema: mudeopen; Owner: mudeopen
--

COMMENT ON COLUMN mudeopen_t_pratica.index_folder IS 'Identitifica la cartella della pratica su Index che contiene i documenti associati';


--
-- Name: mudeopen_t_pratica_cosmo; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_pratica_cosmo (
    id_pratica_cosmo bigint NOT NULL,
    id_istanza bigint NOT NULL,
    codice_stato_cosmo character varying NOT NULL,
    numero_pratica character varying,
    tipo_controllo character varying,
    retries integer DEFAULT 0,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    json_metadata character varying,
    json_response character varying,
    id_doc_cosmo character varying,
    controllo_campione boolean DEFAULT false,
    cc_selezionato boolean DEFAULT false,
    trasmiss_doc boolean DEFAULT false,
    json_assegnatari jsonb
);


ALTER TABLE mudeopen_t_pratica_cosmo OWNER TO mudeopen;

--
-- Name: COLUMN mudeopen_t_pratica_cosmo.codice_stato_cosmo; Type: COMMENT; Schema: mudeopen; Owner: mudeopen
--

COMMENT ON COLUMN mudeopen_t_pratica_cosmo.codice_stato_cosmo IS 'CREATA | ALLEGATO | IN_PROCESSO | AVVIATA';


--
-- Name: mudeopen_t_pratica_cosmo_id_pratica_cosmo_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_pratica_cosmo_id_pratica_cosmo_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_pratica_cosmo_id_pratica_cosmo_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_pratica_cosmo_id_pratica_cosmo_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_pratica_cosmo_id_pratica_cosmo_seq OWNED BY mudeopen_t_pratica_cosmo.id_pratica_cosmo;


--
-- Name: mudeopen_t_pratica_id_pratica_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_pratica_id_pratica_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_pratica_id_pratica_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_pratica_id_pratica_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_pratica_id_pratica_seq OWNED BY mudeopen_t_pratica.id_pratica;


--
-- Name: mudeopen_t_pratica_idf; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_pratica_idf (
    id_pratica_idf bigint NOT NULL,
    id_istanza bigint NOT NULL,
    codice_stato_idf character varying NOT NULL,
    competenza character varying,
    autorizzato character varying,
    numero_determina_esito_aut character varying,
    data_scadenza_autorizzazione character varying,
    retries integer DEFAULT 0,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    json_metadata character varying,
    json_response character varying,
    tipo_documento character varying
);


ALTER TABLE mudeopen_t_pratica_idf OWNER TO mudeopen;

--
-- Name: mudeopen_t_pratica_idf_id_pratica_idf_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_pratica_idf_id_pratica_idf_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_pratica_idf_id_pratica_idf_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_pratica_idf_id_pratica_idf_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_pratica_idf_id_pratica_idf_seq OWNED BY mudeopen_t_pratica_idf.id_pratica_idf;


--
-- Name: mudeopen_t_sessione; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_sessione (
    fruitore character varying(2000) NOT NULL,
    data_creazione timestamp without time zone DEFAULT now() NOT NULL,
    id_sessione uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    parametro_input character varying
);


ALTER TABLE mudeopen_t_sessione OWNER TO mudeopen;

--
-- Name: mudeopen_t_sism_costr_es; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_sism_costr_es (
    id_sism_costr_es bigint NOT NULL,
    id_sism_rel_ill integer,
    int_id_tipo character varying,
    int_id_par character varying,
    costr_id_tipo character varying,
    costr_id_classe_uso character varying,
    elemento_nome character varying(20),
    geom_num_piani_ft character varying(50),
    geom_num_piani_int character varying(50),
    geom_pianta_m1 character varying(50),
    geom_pianta_m2 character varying(50),
    geom_copert_m character varying(50),
    geom_sup_max character varying(50),
    int_lc character varying(3),
    int_fc character varying(4),
    int_giust character varying(500),
    costr_vita_nom character varying(50),
    str_port_es_dest character varying(50),
    str_port_es_cao boolean,
    str_port_es_cao_in_op boolean,
    str_port_es_cao_pref boolean,
    str_port_es_cap boolean,
    str_port_es_acciaio boolean,
    str_port_es_mur boolean,
    str_port_es_mur_old boolean,
    str_port_es_mur_arm boolean,
    str_port_es_mur_conf boolean,
    str_port_es_legno boolean,
    str_port_es_mat_sc boolean,
    str_port_es_mista boolean,
    str_port_es_mista_desc character varying(50),
    str_port_es_altro boolean,
    str_port_es_altro_desc character varying(50),
    str_port_es_fond boolean,
    str_port_es_fond_desc character varying(50),
    str_port_es_vert boolean,
    str_port_es_vert_desc character varying(50),
    str_port_es_oriz boolean,
    str_port_es_oriz_desc character varying(50),
    str_port_es_cop boolean,
    str_port_es_cop_desc character varying(50),
    met_anal_stat_lin boolean,
    met_anal_din_lin boolean,
    met_anal_stat_n_lin boolean,
    met_anal_din_n_lin boolean,
    met_anal_altro boolean,
    met_anal_altro_desc character varying(50),
    met_anal_tipo_vinc character varying(500),
    met_anal_classe_dut character varying(1),
    met_anal_reg_pianta boolean,
    met_anal_reg_elev boolean,
    tip_str_desc character varying(500),
    tip_str_el_sec boolean,
    tip_str_el_sec_desc character varying(500),
    tip_str_ger_res boolean,
    tip_str_ger_res_desc character varying(500),
    tip_str_rig_pia boolean,
    tip_str_rig_cop boolean,
    tip_str_fat_q character varying(20),
    tip_str_rif_norm character varying(200),
    tip_str_f_a character varying(50),
    tip_str_el_fls boolean,
    tip_str_az_vert boolean,
    tip_str_zero_sism character varying(500),
    str_port_pr_desc character varying(500),
    str_port_pr_cao boolean,
    str_port_pr_cao_in_op boolean,
    str_port_pr_cao_pref boolean,
    str_port_pr_cap boolean,
    str_port_pr_acciaio boolean,
    str_port_pr_mur boolean,
    str_port_pr_mur_ord boolean,
    str_port_pr_mur_arm boolean,
    str_port_pr_mur_conf boolean,
    str_port_pr_legno boolean,
    str_port_pr_mista boolean,
    str_port_pr_mista_desc character varying(50),
    str_port_pr_altro boolean,
    str_port_pr_altro_desc character varying(50),
    str_port_pr_fond boolean,
    str_port_pr_fond_desc character varying(50),
    str_port_pr_vert boolean,
    str_port_pr_vert_desc character varying(50),
    str_port_pr_oriz boolean,
    str_port_pr_oriz_desc character varying(50),
    str_port_pr_cop boolean,
    str_port_pr_cop_desc character varying(50),
    mat_es_fond boolean,
    mat_es_fond_desc character varying(50),
    mat_es_str_vert boolean,
    mat_es_str_vert_desc character varying(50),
    mat_es_oriz boolean,
    mat_es_oriz_desc character varying(50),
    mat_es_cop boolean,
    mat_es_cop_desc character varying(50),
    sovr_car_perm boolean,
    sovr_car_perm_desc character varying(50),
    sovr_car_var boolean,
    sovr_car_var_desc character varying(50),
    an_sism_per_t character varying(50),
    an_sism_b_mas_x character varying(50),
    an_sism_b_mas_y character varying(50),
    an_sism_b_rig_x character varying(50),
    an_sism_b_rig_y character varying(50),
    an_sism_tagl_base_v character varying(50),
    an_sism_mas_x character varying(50),
    an_sism_mas_y character varying(50),
    an_sism_num_vibr character varying(50),
    an_sism_t1x character varying(50),
    an_sism_t1x_perc character varying(50),
    an_sism_t2x character varying(50),
    an_sism_t2x_perc character varying(50),
    an_sism_t1y character varying(50),
    an_sism_t1y_perc character varying(50),
    an_sism_t2y character varying(50),
    an_sism_t2y_perc character varying(50),
    an_sism_tagl_ult_v character varying(50),
    an_sism_punto_cont_x character varying(50),
    an_sism_punto_cont_y character varying(50),
    an_sism_punto_cont_z character varying(50),
    an_sism_spost_ult character varying(50),
    an_sism_altre_an character varying(500),
    an_sism_sintesi_ver character varying(500),
    spost_ed_tamp_rig boolean,
    spost_ed_tamp_prog boolean,
    spost_costr_m_ord boolean,
    spost_costr_m_arm boolean,
    spost_ver_st character varying(2),
    spost_ver_st_nc_desc character varying(50),
    spost_ver_ds character varying(2),
    spost_ver_fond character varying(2),
    spost_ver_fond_nc_desc character varying(50),
    mat_pr_fond boolean,
    mat_pr_fond_desc character varying(50),
    mat_pr_str_vert boolean,
    mat_pr_str_vert_desc character varying(50),
    mat_pr_oriz boolean,
    mat_pr_oriz_desc character varying(50),
    mat_pr_cop boolean,
    mat_pr_cop_desc character varying(50),
    confr_liv_sic_prima character varying(50),
    confr_liv_sic_dopo character varying(50),
    confr_liv_sic_vert character varying(50),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_t_sism_costr_es OWNER TO mudeopen;

--
-- Name: mudeopen_t_sism_costr_es_id_sism_costr_es_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_sism_costr_es_id_sism_costr_es_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_sism_costr_es_id_sism_costr_es_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_sism_costr_es_id_sism_costr_es_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_sism_costr_es_id_sism_costr_es_seq OWNED BY mudeopen_t_sism_costr_es.id_sism_costr_es;


--
-- Name: mudeopen_t_sism_denuncia; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_sism_denuncia (
    id_denuncia_sism bigint NOT NULL,
    id_istanza bigint NOT NULL,
    id_titolo_riferimento bigint,
    zona_sismica_comune character varying(20),
    id_destinazione_d_uso character varying(20),
    id_denuncia_riferimento bigint,
    prot_estremi_titolo_rif character varying,
    data_prot_titolo_rif date,
    prot_estremi_denuncia_rif character varying,
    data_prot_denuncia_rif date,
    note character varying,
    dich_soggetta_sue boolean,
    dich_soggetta_sue_num character varying,
    dich_soggetta_sue_prot character varying,
    dich_soggetta_sue_data date,
    dich_vinc_idrog boolean,
    dich_vinc_idrog_ril_da character varying,
    dich_vinc_idrog_prot character varying,
    dich_vinc_idrog_data date,
    dich_lav_eseguiti boolean,
    dich_lav_eseguiti_den_a character varying,
    dich_lav_eseguit_prot character varying,
    dich_lav_eseguiti_data date,
    dich_nec_collaud boolean,
    dich_collaud_nomina boolean,
    dich_collaud_terna boolean,
    dich_collaud_op_pubblica boolean,
    dich_esente_bollo boolean,
    dich_esente_diritti boolean,
    istanze_rif boolean,
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_t_sism_denuncia OWNER TO mudeopen;

--
-- Name: mudeopen_t_sism_denuncia_id_denuncia_sism_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_sism_denuncia_id_denuncia_sism_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_sism_denuncia_id_denuncia_sism_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_sism_denuncia_id_denuncia_sism_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_sism_denuncia_id_denuncia_sism_seq OWNED BY mudeopen_t_sism_denuncia.id_denuncia_sism;


--
-- Name: mudeopen_t_sism_muri_sost; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_sism_muri_sost (
    id_sism_muri_sost bigint NOT NULL,
    id_sism_rel_ill integer,
    int_id_tipo character varying,
    int_id_par character varying,
    costr_id_tipo character varying,
    costr_id_classe_uso character varying,
    elemento_nome character varying(20),
    tipo_muro_nuovo boolean,
    tipo_muro_esist boolean,
    geom_svil_max character varying(50),
    geom_altezza character varying(50),
    int_lc character varying(3),
    int_fc character varying(4),
    int_giust character varying(500),
    costr_vita_nom character varying(50),
    str_port_es_cao boolean,
    str_port_es_cao_in_op boolean,
    str_port_es_cao_pref boolean,
    str_port_es_cap boolean,
    str_port_es_acciaio boolean,
    str_port_es_mur boolean,
    str_port_es_altro boolean,
    str_port_es_altro_desc character varying(50),
    str_port_es_fond boolean,
    str_port_es_fond_desc character varying(50),
    str_port_es_vert boolean,
    str_port_es_vert_desc character varying(50),
    met_anal_pseud boolean,
    met_anal_spost boolean,
    met_anal_altro boolean,
    met_anal_altro_desc character varying(50),
    coef_sld_kh character varying(50),
    coef_sld_kv character varying(50),
    coef_slv_kh character varying(50),
    coef_slv_kv character varying(50),
    coef_sld_bm character varying(50),
    coef_slv_bm character varying(50),
    coef_sld_bs character varying(50),
    coef_slv_bs character varying(50),
    coef_sld_b_par character varying(50),
    coef_slv_b_par character varying(50),
    str_port_pr_tipo character varying(500),
    str_port_pr_cao boolean,
    str_port_pr_cao_in_op boolean,
    str_port_pr_cao_pref boolean,
    str_port_pr_cap boolean,
    str_port_pr_acciaio boolean,
    str_port_pr_mur boolean,
    str_port_pr_altro boolean,
    str_port_pr_altro_desc character varying(50),
    str_port_pr_fond boolean,
    str_port_pr_fond_desc character varying(50),
    str_port_pr_vert boolean,
    str_port_pr_vert_desc character varying(50),
    confr_liv_sic_prima character varying(50),
    confr_liv_sic_dopo character varying(50),
    confr_liv_sic_vert character varying(50),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_t_sism_muri_sost OWNER TO mudeopen;

--
-- Name: mudeopen_t_sism_muri_sost_id_sism_muri_sost_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_sism_muri_sost_id_sism_muri_sost_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_sism_muri_sost_id_sism_muri_sost_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_sism_muri_sost_id_sism_muri_sost_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_sism_muri_sost_id_sism_muri_sost_seq OWNED BY mudeopen_t_sism_muri_sost.id_sism_muri_sost;


--
-- Name: mudeopen_t_sism_nuova_costr; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_sism_nuova_costr (
    id_sism_nuova_costr bigint NOT NULL,
    id_sism_rel_ill integer,
    costr_id_tipo character varying,
    costr_id_classe_uso character varying,
    elemento_nome character varying(20),
    geom_num_piani_ft character varying(20),
    geom_num_piani_int character varying(20),
    geom_pianta_m1 character varying(20),
    geom_pianta_m2 character varying(20),
    geom_copert_m character varying(20),
    geom_sup_max character varying(20),
    costr_vita_nom character varying(50),
    str_port_dest character varying(50),
    str_port_cao boolean,
    str_port_cao_in_op boolean,
    str_port_cao_pref boolean,
    str_port_cap boolean,
    str_port_acciaio boolean,
    str_port_mur boolean,
    str_port_mur_ord boolean,
    str_port_mur_arm boolean,
    str_port_mur_conf boolean,
    str_port_legno boolean,
    str_port_mat_sc boolean,
    str_port_mista boolean,
    str_port_mista_desc character varying(50),
    str_port_altro boolean,
    str_port_altro_desc character varying(50),
    str_port_fond boolean,
    str_port_fond_desc character varying(50),
    str_port_vert boolean,
    str_port_vert_desc character varying(50),
    str_port_oriz boolean,
    str_port_oriz_desc character varying(50),
    str_port_cop boolean,
    str_port_cop_desc character varying(50),
    met_anal_stat_lin boolean,
    met_anal_din_lin boolean,
    met_anal_stat_n_lin boolean,
    met_anal_din_n_lin boolean,
    met_anal_altro boolean,
    met_anal_altro_desc character varying(50),
    met_anal_tipo_vinc character varying(500),
    met_anal_classe_dut character varying(1),
    met_anal_reg_pianta boolean,
    met_anal_reg_elev boolean,
    tip_str_desc character varying(500),
    tip_str_el_sec boolean,
    tip_str_el_sec_desc character varying(500),
    tip_str_ger_res boolean,
    tip_str_ger_res_desc character varying(500),
    tip_str_rig_pia boolean,
    tip_str_rig_cop boolean,
    tip_str_fat_q character varying(20),
    tip_str_rif_norm character varying(100),
    tip_str_f_teta character varying(20),
    tip_str_f_q0 character varying(20),
    tip_str_f_a character varying(20),
    tip_str_f_kw character varying(20),
    tip_str_f_kr character varying(20),
    tip_str_el_fls boolean,
    tip_str_az_vert boolean,
    tip_str_zero_sism character varying(50),
    mat_fond boolean,
    mat_fond_desc character varying(50),
    mat_str_vert boolean,
    mat_str_vert_desc character varying(50),
    mat_oriz boolean,
    mat_oriz_desc character varying(50),
    mat_cop boolean,
    mat_cop_desc character varying(50),
    sovr_car_perm boolean,
    sovr_car_perm_desc character varying(50),
    sovr_car_var boolean,
    sovr_car_var_desc character varying(50),
    an_sism_per_t character varying(50),
    an_sism_b_mas_x character varying(50),
    an_sism_b_mas_y character varying(50),
    an_sism_b_rig_x character varying(50),
    an_sism_b_rig_y character varying(50),
    an_sism_tagl_base_v character varying(50),
    an_sism_mas_x character varying(50),
    an_sism_mas_y character varying(50),
    an_sism_num_vibr character varying(50),
    an_sism_t1x character varying(50),
    an_sism_t1x_perc character varying(50),
    an_sism_t2x character varying(50),
    an_sism_t2x_perc character varying(50),
    an_sism_t1y character varying(50),
    an_sism_t1y_perc character varying(50),
    an_sism_t2y character varying(50),
    an_sism_t2y_perc character varying(50),
    an_sism_tagl_ult_v character varying(50),
    an_sism_punto_cont_x character varying(50),
    an_sism_punto_cont_y character varying(50),
    an_sism_punto_cont_z character varying(50),
    an_sism_spost_ult character varying(50),
    an_sism_altre_an character varying(500),
    an_sism_sintesi_ver character varying(500),
    spost_ed_tamp_rig boolean,
    spost_ed_tamp_prog boolean,
    spost_costr_m_ord boolean,
    spost_costr_m_arm boolean,
    spost_ver_st character varying(2),
    spost_ver_st_nc_desc character varying(50),
    spost_ver_ds character varying(2),
    spost_ver_col character varying(2),
    tip_str_tab_giust character varying(200),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_t_sism_nuova_costr OWNER TO mudeopen;

--
-- Name: mudeopen_t_sism_nuova_costr_id_sism_nuova_costr_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_sism_nuova_costr_id_sism_nuova_costr_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_sism_nuova_costr_id_sism_nuova_costr_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_sism_nuova_costr_id_sism_nuova_costr_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_sism_nuova_costr_id_sism_nuova_costr_seq OWNED BY mudeopen_t_sism_nuova_costr.id_sism_nuova_costr;


--
-- Name: mudeopen_t_sism_ponti_viad; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_sism_ponti_viad (
    id_sism_ponti_viad bigint NOT NULL,
    id_sism_rel_ill integer,
    int_id_tipo character varying,
    int_id_par character varying,
    costr_id_tipo character varying,
    costr_id_classe_uso character varying,
    elemento_nome character varying(20),
    tipo_pv_nuovo boolean,
    tipo_pv_esist boolean,
    geom_n_campate character varying(50),
    geom_camp_luce_m character varying(50),
    geom_pv_strad boolean,
    geom_pv_strad_cat character varying(50),
    geom_pv_fer boolean,
    geom_pianta_m1 character varying(50),
    geom_pianta_m2 character varying(50),
    geom_alt_max character varying(50),
    int_lc character varying(3),
    int_fc character varying(4),
    int_giust character varying(500),
    costr_vita_nom character varying(50),
    str_port_es_sist character varying(50),
    str_port_es_cao boolean,
    str_port_es_cao_in_op boolean,
    str_port_es_cao_pref boolean,
    str_port_es_cap boolean,
    str_port_es_acciaio boolean,
    str_port_es_mur boolean,
    str_port_es_legno boolean,
    str_port_es_mista boolean,
    str_port_es_mista_desc character varying(50),
    str_port_es_altro boolean,
    str_port_es_altro_desc character varying(50),
    str_port_es_fond boolean,
    str_port_es_fond_desc character varying(50),
    str_port_es_vert boolean,
    str_port_es_vert_desc character varying(50),
    str_port_es_imp boolean,
    str_port_es_imp_desc character varying(50),
    met_anal_stat_lin boolean,
    met_anal_din_lin boolean,
    met_anal_stat_n_lin boolean,
    met_anal_din_n_lin boolean,
    met_anal_altro boolean,
    met_anal_altro_desc character varying(50),
    met_anal_tipo_vinc character varying(500),
    met_anal_classe_dut character varying(1),
    tip_str_desc character varying(500),
    tip_str_fat_q character varying(50),
    tip_str_rif_norm character varying(50),
    tip_str_az_vert boolean,
    tip_str_zero_sism character varying(500),
    str_port_pr_tipo character varying(50),
    str_port_pr_cao boolean,
    str_port_pr_cao_in_op boolean,
    str_port_pr_cao_pref boolean,
    str_port_pr_cap boolean,
    str_port_pr_acciaio boolean,
    str_port_pr_mur boolean,
    str_port_pr_legno boolean,
    str_port_pr_mista boolean,
    str_port_pr_mista_desc character varying(50),
    str_port_pr_altro boolean,
    str_port_pr_altro_desc character varying(50),
    str_port_pr_fond boolean,
    str_port_pr_fond_desc character varying(50),
    str_port_pr_vert boolean,
    str_port_pr_vert_desc character varying(50),
    str_port_pr_imp boolean,
    str_port_pr_imp_desc character varying(50),
    confr_liv_sic_prima character varying(50),
    confr_liv_sic_dopo character varying(50),
    confr_liv_sic_vert character varying(50),
    an_sism_per_t character varying(50),
    an_sism_b_mas_x character varying(50),
    an_sism_b_mas_y character varying(50),
    an_sism_b_rig_x character varying(50),
    an_sism_b_rig_y character varying(50),
    an_sism_tagl_base_v character varying(50),
    an_sism_mas_x character varying(50),
    an_sism_mas_y character varying(50),
    an_sism_num_vibr character varying(50),
    an_sism_t1x character varying(50),
    an_sism_t1x_perc character varying(50),
    an_sism_t2x character varying(50),
    an_sism_t2x_perc character varying(50),
    an_sism_t1y character varying(50),
    an_sism_t1y_perc character varying(50),
    an_sism_t2y character varying(50),
    an_sism_t2y_perc character varying(50),
    an_sism_tagl_ult_v character varying(50),
    an_sism_punto_cont_x character varying(50),
    an_sism_punto_cont_y character varying(50),
    an_sism_punto_cont_z character varying(50),
    an_sism_spost_ult character varying(50),
    an_sism_altre_an character varying(500),
    an_sism_sintesi_ver character varying(500),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now()
);


ALTER TABLE mudeopen_t_sism_ponti_viad OWNER TO mudeopen;

--
-- Name: mudeopen_t_sism_ponti_viad_id_sism_ponti_viad_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_sism_ponti_viad_id_sism_ponti_viad_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_sism_ponti_viad_id_sism_ponti_viad_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_sism_ponti_viad_id_sism_ponti_viad_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_sism_ponti_viad_id_sism_ponti_viad_seq OWNED BY mudeopen_t_sism_ponti_viad.id_sism_ponti_viad;


--
-- Name: mudeopen_t_sism_rel_ill; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_sism_rel_ill (
    id_sism_rel_ill bigint NOT NULL,
    id_istanza bigint,
    id_denuncia_sism bigint,
    id_tipo_edifici_strat_reg bigint,
    id_tipo_opere_strat_reg bigint,
    id_tipo_edifici_ril_reg bigint,
    id_tipo_opere_ril_reg bigint,
    edificio_opera_strategico boolean,
    edificio_opera_strat_naz boolean,
    edifici_strat_reg boolean,
    opere_strat_reg boolean,
    edificio_opera_rilevante boolean,
    edificio_opera_ril_naz boolean,
    edifici_ril_reg boolean,
    opere_ril_reg boolean,
    norma_tecnica character varying(500),
    data_norma_tecnica date,
    nuova_costruz boolean,
    int_costruz_esist boolean,
    terreno_prove boolean,
    terreno_ang_attrito numeric(10,0),
    terreno_cat_sott character varying(1),
    terreno_cat_top character varying(2),
    terreno_coeff_ss numeric(10,0),
    giudizio character varying(1000),
    par_slo_ag numeric(6,0),
    par_slo_f0 numeric(6,0),
    par_slo_tc numeric(6,0),
    par_sld_ag numeric(6,0),
    par_sld_f0 numeric(6,0),
    par_sld_tc numeric(6,0),
    par_slv_ag numeric(6,0),
    par_slv_f0 numeric(6,0),
    par_slv_tc numeric(6,0),
    par_slc_ag numeric(6,0),
    par_slc_f0 numeric(6,0),
    par_slc_tc numeric(6,0),
    column1 character varying(50),
    terreno_coesione numeric(10,0),
    terreno_peso_volume numeric(10,0),
    terreno_mod_ed numeric(10,0),
    terreno_mod_tgl numeric(10,0),
    terreno_coeff_st numeric(10,0),
    terreno_cat_sott_n_dov character varying(50),
    terreno_cat_top_n_dov character varying(50),
    data_inizio timestamp without time zone,
    data_fine timestamp without time zone,
    loguser character varying,
    data_modifica timestamp without time zone DEFAULT now(),
    terreno_param_geo character varying(3000)
);


ALTER TABLE mudeopen_t_sism_rel_ill OWNER TO mudeopen;

--
-- Name: mudeopen_t_sism_rel_ill_id_sism_rel_ill_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_sism_rel_ill_id_sism_rel_ill_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_sism_rel_ill_id_sism_rel_ill_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_sism_rel_ill_id_sism_rel_ill_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_sism_rel_ill_id_sism_rel_ill_seq OWNED BY mudeopen_t_sism_rel_ill.id_sism_rel_ill;


--
-- Name: mudeopen_t_user; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_t_user (
    id_user bigint NOT NULL,
    cognome character varying(255),
    fine_validita date,
    inizio_validita date,
    nome character varying(255),
    cf character varying(255),
    mail character varying(255),
    telefono character varying(255),
    cellulare character varying(255),
    data_nascita timestamp without time zone,
    pec character varying(255),
    sesso character varying(255),
    id_comune_nascita bigint,
    id_provincia_nascita bigint,
    id_stato_nascita bigint,
    stato_estero character varying(255),
    fine_validita_bo date,
    utente_fo boolean DEFAULT false NOT NULL,
    utente_bo boolean DEFAULT false NOT NULL,
    utente_api boolean DEFAULT false NOT NULL,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone,
    validato_da_utente boolean
);


ALTER TABLE mudeopen_t_user OWNER TO mudeopen;

--
-- Name: mudeopen_t_user_id_user_seq; Type: SEQUENCE; Schema: mudeopen; Owner: mudeopen
--

CREATE SEQUENCE mudeopen_t_user_id_user_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE mudeopen_t_user_id_user_seq OWNER TO mudeopen;

--
-- Name: mudeopen_t_user_id_user_seq; Type: SEQUENCE OWNED BY; Schema: mudeopen; Owner: mudeopen
--

ALTER SEQUENCE mudeopen_t_user_id_user_seq OWNED BY mudeopen_t_user.id_user;


--
-- Name: mudeopen_top_civici; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE mudeopen_top_civici (
    pk_sequ_civico integer,
    pk_sequ_sto_civico integer,
    uk_civico character varying,
    fk_comuni character varying,
    fk_strade character varying,
    numero integer,
    esp1 character varying,
    esp2 character varying,
    esp3 character varying,
    esp4 character varying,
    descrizione_civico character varying,
    tipo_civico character varying,
    tipo_accesso character varying,
    lato_strada character varying,
    accessibilita character varying,
    tabellato character varying,
    cap character varying,
    natura character varying,
    foglio_ct character varying,
    particella_ct character varying,
    sezione_ct character varying,
    denominatore_ct character varying,
    edificialita_ct character varying,
    uk_cat_particella_ct character varying,
    uk_particella_ct character varying,
    fk_sez_censimento character varying,
    geometry character varying,
    geom_accesso character varying,
    geom_percorso_accesso character varying,
    atto_deliberativo character varying,
    data_attivazione date,
    data_rilievo date,
    codice_rilevatore character varying,
    corrispondenza_residenti character varying,
    denominaz_resid_o_attiv character varying,
    codice_censimento character varying,
    note_censimento character varying,
    note character varying,
    data_record date,
    fk_sez_elettorali character varying,
    fk_zone_commerciali character varying,
    fk_distretti_scolastici character varying,
    comune character varying,
    fk_distretti_sanitari character varying,
    fk_circoscrizioni character varying,
    chilometrica integer,
    duplicato_terr integer,
    tabellato_censimento character varying,
    ex_strada character varying,
    ex_civico character varying,
    labelx double precision,
    labely double precision,
    descr_dest_uso character varying,
    descr_strada character varying,
    esc_foglio_ct character varying,
    esc_numero_ct character varying,
    descr_sez_censimento character varying,
    descr_sez_elettorali character varying,
    descr_zone_commerciali character varying,
    descr_distretti_scolastici character varying,
    descr_distretti_sanitari character varying,
    descr_circoscrizioni character varying,
    altra_dest_uso character varying,
    descr_strada_ex character varying,
    xcentroid integer,
    ycentroid integer,
    fwidth integer,
    fheight integer,
    codice_stato_civico integer,
    uk_civico_old character varying,
    url character varying,
    loguser character varying,
    data_inizio timestamp without time zone DEFAULT now() NOT NULL,
    data_modifica timestamp without time zone DEFAULT now(),
    data_fine timestamp without time zone
);


ALTER TABLE mudeopen_top_civici OWNER TO mudeopen;

--
-- Name: mudeopen_v_ext_moon_non_processate; Type: VIEW; Schema: mudeopen; Owner: mudeopen
--

CREATE VIEW mudeopen_v_ext_moon_non_processate AS
 SELECT mtiem.id_istanza_ext_moon,
    mtiem.codice_istanza,
    mtiem.id_istanza,
    mdc.cod_istat_comune AS cod_istat
   FROM ((((mudeopen.mudeopen_t_istanza_ext_moon mtiem
     JOIN mudeopen_t_istanza mti ON ((mti.id_istanza = mtiem.id_istanza)))
     JOIN mudeopen_r_istanza_stato mris ON (((mris.id_istanza = mti.id_istanza) AND (mris.data_fine IS NULL))))
     JOIN mudeopen_t_fascicolo mtf ON ((mtf.id_fascicolo = mti.id_fascicolo)))
     JOIN mudeopen_d_comune mdc ON ((mdc.id_comune = mtf.id_comune)))
  WHERE ((mtiem.fl_processata IS NULL) AND ((mris.codice_stato_istanza)::text = 'APA'::text));


ALTER TABLE mudeopen_v_ext_moon_non_processate OWNER TO mudeopen;

--
-- Name: predpratica; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE predpratica (
    idcomune character varying(6),
    numprotocollo character varying(15),
    dataprotocollo timestamp without time zone,
    idrespprocedimento integer,
    idrespservizio integer,
    idrespistruttoria integer,
    idtipopratica integer,
    datadomanda timestamp without time zone,
    numprotinterno character varying(15),
    dataprotinterno timestamp without time zone,
    idtipointervento integer,
    numpraticaanno character varying(4),
    numpraticanum integer,
    numpratica character varying(15),
    oggettointervento text,
    iddestinazioneuso integer,
    variante integer,
    numprotprec character varying(15),
    numprotsucc character varying(15),
    notedatgen text,
    idcomuneubicazione character varying(6),
    idindirizzoubicazione integer,
    idnumcivubicazione integer,
    numcivubicazione character varying(4),
    numespubicazione character varying(6),
    numscaubicazione character varying(3),
    numpiaubicazione character varying(3),
    numintubicazione character varying(3),
    noteubicazione text,
    idambitourbanistico character varying(30),
    idmodalitaattuazione integer,
    indicefondiario bigint,
    supcopesi bigint,
    supcoppro bigint,
    supcopdem bigint,
    volesi bigint,
    volpro bigint,
    voldem bigint,
    suputiesi bigint,
    suputipro bigint,
    suputidem bigint,
    disstrade bigint,
    disconfine bigint,
    disfabbricati bigint,
    altedificio bigint,
    numabitazioni bigint,
    numpianifuoter bigint,
    numvaniprincipali bigint,
    numvaniaccessori bigint,
    supterritoriale bigint,
    supfondiaria bigint,
    densterritoriale bigint,
    numprovvedimentoanno character varying(4),
    numprovvedimentonum integer,
    numprovvedimento character varying(15),
    dataprovvedimento timestamp without time zone,
    datanotifica timestamp without time zone,
    noterilascio text,
    doccompleta smallint,
    datadocintegrativi timestamp without time zone,
    parericompleti smallint,
    dataparericompleti timestamp without time zone,
    docprofincompleta smallint,
    datadocprofin timestamp without time zone,
    istruttoriatecnica text,
    idpareretec smallint,
    datapareretec timestamp without time zone,
    prontapercomedi integer,
    datainvioincomedi timestamp without time zone,
    coscosto bigint,
    cosimpscomputo bigint,
    cosimptotversato bigint,
    cosimptotresiduo bigint,
    cosimpgaranzia bigint,
    cosnumpolizza character varying(30),
    cosenteassicurativo character varying(200),
    cosdatascadenzapolizza timestamp without time zone,
    cospercriduzione bigint,
    cosnote text,
    oneprimari bigint,
    oneprimariscomputo bigint,
    onesecondari bigint,
    onesecondariscomputo bigint,
    oneimptotversato bigint,
    oneimptotresiduo bigint,
    oneimpgaranzia bigint,
    onenumpolizza character varying(30),
    oneenteassicurativo character varying(200),
    onedatascadenzapolizza timestamp without time zone,
    oneimpscomputo bigint,
    onepercriduzione bigint,
    onenote text,
    notealtripagamenti text,
    datainiziolavorieffettiva timestamp without time zone,
    datafinelavorieffettiva timestamp without time zone,
    docinilavcompleta smallint,
    datadocinilav timestamp without time zone,
    numprotdomandaabi character varying(15),
    datadomandaabi timestamp without time zone,
    abitotale boolean,
    oggettoabi text,
    abiparziale text,
    doccompletaabi boolean,
    datadocintegrativiabi timestamp without time zone,
    numprotabi character varying(15),
    dataabi timestamp without time zone,
    idstrumentourbanistico character varying(50),
    oneindotta bigint,
    onerifiuti bigint,
    datadiniego timestamp without time zone,
    cosdatastipulapolizza timestamp without time zone,
    onedatastipulapolizza timestamp without time zone,
    numarchivio character varying(50),
    diniegoufficio integer,
    datadiniegoufficio timestamp without time zone,
    dataultimaproroga timestamp without time zone,
    numsportellounicoanno character varying(4),
    numsportellouniconum integer,
    numsportellounico character varying(15),
    numprotsportellounico character varying(15),
    dataprotsportellounico timestamp without time zone,
    numrilasciosportellounicoanno character varying(4),
    numrilasciosportellouniconum integer,
    numrilasciosportellounico character varying(15),
    datarilasciosportellounico timestamp without time zone,
    oneindottascomputo bigint,
    onerifiutiscomputo bigint,
    dataconferenzaservizi timestamp without time zone,
    dataritiro timestamp without time zone,
    dataannullamento timestamp without time zone,
    datapubblicazione timestamp without time zone,
    numgiornipubblicazione smallint,
    cosoneimpgaranzia bigint,
    cosonenumpolizza character varying(30),
    cosoneenteassicurativo character varying(200),
    cosonedatascadenzapolizza timestamp without time zone,
    cosonedatastipulapolizza timestamp without time zone,
    datacominiziolavori timestamp without time zone,
    numprotcominiziolavori character varying(15),
    datacomfinelavori timestamp without time zone,
    numprotcomfinelavori character varying(15),
    datasospensionelavori timestamp without time zone,
    dataripresalavori timestamp without time zone,
    datacertcollaudo timestamp without time zone,
    numprotcertcollaudo character varying(15),
    nonricevibile smallint,
    datarifiuto timestamp without time zone,
    datarustico timestamp without time zone,
    prontapercs smallint,
    statoext integer,
    datacomrespproc timestamp without time zone,
    numprotcomrespproc character varying(15),
    datacomdiniego timestamp without time zone,
    datadocdiniego timestamp without time zone,
    numprotdocdiniego character varying(15),
    elaboratigraficiapprovati character varying(255),
    idambitosismico integer,
    supparpubbliciprgvig bigint,
    supparpubbliciprgado bigint,
    suppermeabile bigint,
    supparprivati bigint,
    paesaggioraga smallint,
    idvincoloraga integer,
    noteraga character varying(255),
    paesaggioragb smallint,
    idvincoloragb integer,
    noteragb character varying(255),
    paesaggioragc smallint,
    idvincoloragc integer,
    noteragc character varying(255),
    idtipointerventosuba integer,
    notesuba character varying(255),
    paesaggiosubb smallint,
    notesubb character varying(255),
    prontaperclp smallint,
    datainvioinclp timestamp without time zone,
    supampllr20 integer,
    volampllr20 integer,
    idposizionearchivio integer,
    archiviomancante smallint,
    numfaldone character varying(15),
    dapubblicarealbo smallint,
    idpubblicazionealboext character varying(15),
    numgiorniadeguamentoscia integer,
    numpubblicazionealbo character varying(15),
    datapropostaprovv timestamp without time zone,
    silenzioassenso smallint,
    dataprocedibilita timestamp without time zone,
    datanotificacomdiniego timestamp without time zone,
    datanotificadocintegrativi timestamp without time zone,
    datanotificaaltrecom timestamp without time zone,
    numistanzamude character varying(30),
    codicepratica character varying(30),
    oneidenteassicurativo integer,
    cosidenteassicurativo integer,
    cosoneidenteassicurativo integer,
    onepercriduzionefinale integer,
    cospercriduzionefinale integer,
    codicepraticasuap character varying(30),
    idcasospecificoscrdgt integer,
    idpraticaentescrdgt integer,
    dataricezionesoprintendenza timestamp without time zone,
    datapropostamodificaprogetto timestamp without time zone,
    termineadesionemodificaprog timestamp without time zone,
    dataadesionemodificaprogetto timestamp without time zone,
    datamodificaprogetto timestamp without time zone,
    numprotpropostamodificaprog character varying(255),
    numprotadesionemodificaprog character varying(255),
    numprotmodificaprogetto character varying(255),
    cosdatarichiestarata timestamp without time zone,
    onedatarichiestarata timestamp without time zone,
    cosonedatarichiestarata timestamp without time zone,
    altroimpdatarichiestarata timestamp without time zone,
    idpraticamapel integer,
    dataorainserimento timestamp without time zone,
    domicilioelettronico character varying(50),
    numprotdocintegrativi character varying(15),
    oggettopratica text,
    numpianiinterrati integer,
    guid character varying(36),
    idstatolombardia smallint,
    hashfilexmllombardia character varying(64),
    idsubmod character varying(10),
    noteprogetto text,
    idfascicoloext character varying(15),
    idarchivio character varying(255),
    controllocampione integer
);


ALTER TABLE predpratica OWNER TO mudeopen;

--
-- Name: predsism; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE predsism (
    idcomune character varying(6),
    numprotocollo character varying(30),
    idzonasismica integer,
    fronticomuni character varying(255),
    denart1 integer,
    denart2 integer,
    idprovamateriali integer,
    idcategoria integer,
    fascicolo character varying(255),
    estrazione integer,
    numprotfogliocons character varying(30),
    dataprotfogliocons timestamp without time zone,
    abusivismo integer,
    numprotabusivismo character varying(30),
    dataprotabusivismo timestamp without time zone,
    pagamentoeffettuato integer,
    notesism text,
    amburbanistico character varying(255),
    zonaprgc character varying(100),
    numdeliberaprgc character varying(30),
    datadeliberaprgc timestamp without time zone,
    classesintesiprgc character varying(255),
    vinidro character varying(255),
    numdeliberavinidro character varying(30),
    datadeliberavinidro timestamp without time zone,
    numbur character varying(30),
    databur timestamp without time zone,
    longitudine character varying(30),
    latitudine character varying(30),
    coordx character varying(30),
    coordy character varying(30),
    idtipocostruzione integer,
    idcatsottosuolo integer,
    idclasseuso integer,
    idcattopografica integer,
    provegeotecniche integer,
    idsistcostrprec integer,
    idsistcostrfut integer,
    numprotfabb character varying(30),
    dataprotfabb timestamp without time zone,
    idnormativa integer,
    istatregione character varying(10),
    istatprovincia character varying(10),
    istatcomune character varying(10),
    aggregato character varying(50),
    subaggregato character varying(50),
    edificio character varying(50),
    datainiziolavorieffettiva timestamp without time zone,
    dataprotcominilav timestamp without time zone,
    numprotcominilav character varying(30),
    datafinelavorieffettiva timestamp without time zone,
    dataprotcomfinlav timestamp without time zone,
    numprotcomfinlav character varying(30),
    dataprotnominacoll timestamp without time zone,
    numprotnominacoll character varying(30),
    dataprotcertificato timestamp without time zone,
    numprotcertificato character varying(30),
    dataprotcomc94 timestamp without time zone,
    numprotcomc94 character varying(30),
    dataprotcomdre timestamp without time zone,
    numprotcomdre character varying(30),
    estratto integer,
    dataestrazione timestamp without time zone,
    idesitoestrazione integer,
    idstatopraticaestratta integer,
    dataprotcomesito timestamp without time zone,
    numprotcomesito character varying(30),
    usernameupdate character varying(100),
    idtipovinidro integer,
    idcompilatore integer,
    datacompilazione timestamp without time zone,
    titabitipo character varying(50),
    titabinumero character varying(15),
    titabidata timestamp without time zone,
    classificazione character varying(255)
);


ALTER TABLE predsism OWNER TO mudeopen;

--
-- Name: predtabrespprocedimento; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE predtabrespprocedimento (
    idrespprocedimento integer,
    respprocedimento character varying(50),
    respservizio integer,
    datain timestamp without time zone,
    datafi timestamp without time zone,
    username character varying(30),
    respistruttoria integer,
    riguardante character varying(100),
    idsubmod character varying(10)
);


ALTER TABLE predtabrespprocedimento OWNER TO mudeopen;

--
-- Name: td_t_allegato; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE td_t_allegato (
    id_allegato bigint,
    id_tipo_allegato character varying(20),
    id_istanza bigint,
    nome_file_allegato character varying(255),
    desc_breve_allegato character varying(255),
    firmato boolean,
    index_node character varying(255),
    dimensione_file integer,
    data_caricamento timestamp without time zone,
    file_content bytea,
    id_user bigint,
    mimetype character varying,
    loguser character varying,
    data_inizio timestamp without time zone,
    data_modifica timestamp without time zone,
    data_fine timestamp without time zone,
    flg_annullato boolean
);


ALTER TABLE td_t_allegato OWNER TO mudeopen;

--
-- Name: tempi_esecuzione; Type: TABLE; Schema: mudeopen; Owner: mudeopen
--

CREATE TABLE tempi_esecuzione (
    data timestamp with time zone,
    calls bigint,
    total_time_minuti double precision,
    total_time double precision,
    min_time double precision,
    max_time double precision,
    mean_time double precision,
    query text
);


ALTER TABLE tempi_esecuzione OWNER TO mudeopen;

--
-- Name: csi_log_audit id_log; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY csi_log_audit ALTER COLUMN id_log SET DEFAULT nextval('mudeopen.csi_log_audit_id_log_seq'::regclass);


--
-- Name: mudeopen_c_proprieta id_proprieta; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_c_proprieta ALTER COLUMN id_proprieta SET DEFAULT nextval('mudeopen.mudeopen_c_proprieta_id_proprieta_seq'::regclass);


--
-- Name: mudeopen_c_proprieta_ente id_proprieta_ente; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_c_proprieta_ente ALTER COLUMN id_proprieta_ente SET DEFAULT nextval('mudeopen.mudeopen_c_proprieta_ente_id_proprieta_ente_seq'::regclass);


--
-- Name: mudeopen_d_abilitazione id_abilitazione; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_abilitazione ALTER COLUMN id_abilitazione SET DEFAULT nextval('mudeopen.mudeopen_d_abilitazione_id_abilitazione_seq'::regclass);


--
-- Name: mudeopen_d_adempimento id_adempimento; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_adempimento ALTER COLUMN id_adempimento SET DEFAULT nextval('mudeopen.mudeopen_d_adempimento_id_adempimento_seq'::regclass);


--
-- Name: mudeopen_d_categoria id_categoria; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_categoria ALTER COLUMN id_categoria SET DEFAULT nextval('mudeopen.mudeopen_d_categoria_id_categoria_seq'::regclass);


--
-- Name: mudeopen_d_categoria_quadro id_categoria_quadro; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_categoria_quadro ALTER COLUMN id_categoria_quadro SET DEFAULT nextval('mudeopen.mudeopen_d_categoria_quadro_id_categoria_quadro_seq'::regclass);


--
-- Name: mudeopen_d_comune id_comune; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_comune ALTER COLUMN id_comune SET DEFAULT nextval('mudeopen.mudeopen_d_comune_id_comune_seq'::regclass);


--
-- Name: mudeopen_d_elemento id_elemento; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_elemento ALTER COLUMN id_elemento SET DEFAULT nextval('mudeopen.mudeopen_d_elemento_id_elemento_seq'::regclass);


--
-- Name: mudeopen_d_elemento_elenco id_elemento_elenco; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_elemento_elenco ALTER COLUMN id_elemento_elenco SET DEFAULT nextval('mudeopen.mudeopen_d_elemento_elenco_id_elemento_elenco_seq'::regclass);


--
-- Name: mudeopen_d_fruitore id_fruitore; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_fruitore ALTER COLUMN id_fruitore SET DEFAULT nextval('mudeopen.mudeopen_d_fruitore_id_fruitore_seq'::regclass);


--
-- Name: mudeopen_d_funzione id_funzione; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_funzione ALTER COLUMN id_funzione SET DEFAULT nextval('mudeopen.mudeopen_d_funzione_id_funzione_seq'::regclass);


--
-- Name: mudeopen_d_nazione id_nazione; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_nazione ALTER COLUMN id_nazione SET DEFAULT nextval('mudeopen.mudeopen_d_nazione_id_nazione_seq'::regclass);


--
-- Name: mudeopen_d_opera id_opera; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_opera ALTER COLUMN id_opera SET DEFAULT nextval('mudeopen.mudeopen_d_opera_id_opera_seq'::regclass);


--
-- Name: mudeopen_d_ppay_importi id_importo; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_ppay_importi ALTER COLUMN id_importo SET DEFAULT nextval('mudeopen.mudeopen_d_ppay_importi_id_importo_seq'::regclass);


--
-- Name: mudeopen_d_provincia id_provincia; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_provincia ALTER COLUMN id_provincia SET DEFAULT nextval('mudeopen.mudeopen_d_provincia_id_provincia_seq'::regclass);


--
-- Name: mudeopen_d_quadro id_quadro; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_quadro ALTER COLUMN id_quadro SET DEFAULT nextval('mudeopen.mudeopen_d_quadro_id_quadro_seq'::regclass);


--
-- Name: mudeopen_d_qualifica id_qualifica; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_qualifica ALTER COLUMN id_qualifica SET DEFAULT nextval('mudeopen.mudeopen_d_qualifica_id_qualifica_seq'::regclass);


--
-- Name: mudeopen_d_qualifica_urbanistica id_qualifica_urbanistica; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_qualifica_urbanistica ALTER COLUMN id_qualifica_urbanistica SET DEFAULT nextval('mudeopen.mudeopen_d_qualifica_urbanistica_id_qualifica_urbanistica_seq'::regclass);


--
-- Name: mudeopen_d_regime_giuridico id_regime; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_regime_giuridico ALTER COLUMN id_regime SET DEFAULT nextval('mudeopen.mudeopen_d_regime_giuridico_id_regime_seq'::regclass);


--
-- Name: mudeopen_d_regime_giuridico_aggiuntivo id_regime; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_regime_giuridico_aggiuntivo ALTER COLUMN id_regime SET DEFAULT nextval('mudeopen.mudeopen_d_regime_giuridico_aggiuntivo_id_regime_seq'::regclass);


--
-- Name: mudeopen_d_regione id_regione; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_regione ALTER COLUMN id_regione SET DEFAULT nextval('mudeopen.mudeopen_d_regione_id_regione_seq'::regclass);


--
-- Name: mudeopen_d_template id_template; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_template ALTER COLUMN id_template SET DEFAULT nextval('mudeopen.mudeopen_d_template_id_template_seq'::regclass);


--
-- Name: mudeopen_d_template_ricevuta_istanza id_template_ricevuta_istanza; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_template_ricevuta_istanza ALTER COLUMN id_template_ricevuta_istanza SET DEFAULT nextval('mudeopen.mudeopen_d_template_ricevuta_istanza_id_template_seq'::regclass);


--
-- Name: mudeopen_d_tipo_ditta id_tipo_ditta; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_tipo_ditta ALTER COLUMN id_tipo_ditta SET DEFAULT nextval('mudeopen.mudeopen_d_tipo_ditta_id_tipo_ditta_seq'::regclass);


--
-- Name: mudeopen_d_tipo_docpa id_tipo_docpa; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_tipo_docpa ALTER COLUMN id_tipo_docpa SET DEFAULT nextval('mudeopen.mudeopen_d_tipo_docpa_id_tipo_docpa_seq'::regclass);


--
-- Name: mudeopen_d_tipo_elenco id_tipo_elenco; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_tipo_elenco ALTER COLUMN id_tipo_elenco SET DEFAULT nextval('mudeopen.mudeopen_d_tipo_elenco_id_tipo_elenco_seq'::regclass);


--
-- Name: mudeopen_d_tipo_notifica id_tipo_notifica; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_tipo_notifica ALTER COLUMN id_tipo_notifica SET DEFAULT nextval('mudeopen.mudeopen_d_tipo_notifica_id_tipo_notifica_seq'::regclass);


--
-- Name: mudeopen_d_tipo_presentatore id_tipo; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_tipo_presentatore ALTER COLUMN id_tipo SET DEFAULT nextval('mudeopen.mudeopen_d_tipo_presentatore_id_tipo_seq'::regclass);


--
-- Name: mudeopen_d_tipo_quadro id_tipo_quadro; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_tipo_quadro ALTER COLUMN id_tipo_quadro SET DEFAULT nextval('mudeopen.mudeopen_d_tipo_quadro_id_tipo_quadro_seq'::regclass);


--
-- Name: mudeopen_d_wf_stato id_wf_stato; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_wf_stato ALTER COLUMN id_wf_stato SET DEFAULT nextval('mudeopen.mudeopen_d_wf_stato_id_wf_stato_seq'::regclass);


--
-- Name: mudeopen_r_abilitazione_funzione id_abilitazione_funzione; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_abilitazione_funzione ALTER COLUMN id_abilitazione_funzione SET DEFAULT nextval('mudeopen.mudeopen_r_abilitazione_funzione_id_abilitazione_funzione_seq'::regclass);


--
-- Name: mudeopen_r_allegato_moon_ricevuto id_allegato_moon_ricevuto; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_allegato_moon_ricevuto ALTER COLUMN id_allegato_moon_ricevuto SET DEFAULT nextval('mudeopen.mudeopen_r_allegato_moon_ricevuto_id_allegato_moon_ricevuto_seq'::regclass);


--
-- Name: mudeopen_r_comune_fruitore id_comune_fruitore; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_comune_fruitore ALTER COLUMN id_comune_fruitore SET DEFAULT nextval('mudeopen.mudeopen_r_comune_fruitore_id_comune_fruitore_seq'::regclass);


--
-- Name: mudeopen_r_contatto_qualifica id_contatto_qualifica; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_contatto_qualifica ALTER COLUMN id_contatto_qualifica SET DEFAULT nextval('mudeopen.mudeopen_r_contatto_qualifica_id_contatto_qualifica_seq'::regclass);


--
-- Name: mudeopen_r_ente_comune_tipo_istanza id_ente_comune_tipo_istanza; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_ente_comune_tipo_istanza ALTER COLUMN id_ente_comune_tipo_istanza SET DEFAULT nextval('mudeopen.mudeopen_r_ente_comune_tipo_ist_id_ente_comune_tipo_istanza_seq'::regclass);


--
-- Name: mudeopen_r_ente_fruitore id_ente_fruitore; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_ente_fruitore ALTER COLUMN id_ente_fruitore SET DEFAULT nextval('mudeopen.mudeopen_r_ente_fruitore_id_ente_fruitore_seq'::regclass);


--
-- Name: mudeopen_r_fascicolo_indirizzo id_fascicolo_indirizzo; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_fascicolo_indirizzo ALTER COLUMN id_fascicolo_indirizzo SET DEFAULT nextval('mudeopen.mudeopen_r_fascicolo_indirizzo_id_fascicolo_indirizzo_seq'::regclass);


--
-- Name: mudeopen_r_fascicolo_intestatario id_fascicolo_intestatario; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_fascicolo_intestatario ALTER COLUMN id_fascicolo_intestatario SET DEFAULT nextval('mudeopen.mudeopen_r_fascicolo_intestatario_id_fascicolo_intestatario_seq'::regclass);


--
-- Name: mudeopen_r_fascicolo_ruolo id_fascicolo_ruolo; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_fascicolo_ruolo ALTER COLUMN id_fascicolo_ruolo SET DEFAULT nextval('mudeopen.mudeopen_r_fascicolo_ruolo_id_fascicolo_ruolo_seq'::regclass);


--
-- Name: mudeopen_r_fascicolo_soggetto id_fascicolo_soggetto; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_fascicolo_soggetto ALTER COLUMN id_fascicolo_soggetto SET DEFAULT nextval('mudeopen.mudeopen_r_fascicolo_soggetto_id_fascicolo_soggetto_seq'::regclass);


--
-- Name: mudeopen_r_fascicolo_stato id_fascicolo_stato; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_fascicolo_stato ALTER COLUMN id_fascicolo_stato SET DEFAULT nextval('mudeopen.mudeopen_r_fascicolo_stato_id_fascicolo_stato_seq'::regclass);


--
-- Name: mudeopen_r_fascicolo_utente id_fascicolo_utente; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_fascicolo_utente ALTER COLUMN id_fascicolo_utente SET DEFAULT nextval('mudeopen.mudeopen_r_fascicolo_utente_id_fascicolo_utente_seq'::regclass);


--
-- Name: mudeopen_r_istanza_pratica id_istanza_pratica; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_pratica ALTER COLUMN id_istanza_pratica SET DEFAULT nextval('mudeopen.mudeopen_r_istanza_pratica_id_istanza_pratica_seq'::regclass);


--
-- Name: mudeopen_r_istanza_quadro_utente id_istanza_quadro_utente; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_quadro_utente ALTER COLUMN id_istanza_quadro_utente SET DEFAULT nextval('mudeopen.mudeopen_r_istanza_quadro_utente_id_istanza_quadro_utente_seq'::regclass);


--
-- Name: mudeopen_r_istanza_soggetto id_istanza_soggetto; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_soggetto ALTER COLUMN id_istanza_soggetto SET DEFAULT nextval('mudeopen.mudeopen_r_istanza_soggetto_id_istanza_soggetto_seq'::regclass);


--
-- Name: mudeopen_r_istanza_stato id_istanza_stato; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_stato ALTER COLUMN id_istanza_stato SET DEFAULT nextval('mudeopen.mudeopen_r_istanza_stato_id_istanza_stato_seq'::regclass);


--
-- Name: mudeopen_r_istanza_tipo_opera id_istanza_tipo_opera; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_tipo_opera ALTER COLUMN id_istanza_tipo_opera SET DEFAULT nextval('mudeopen.mudeopen_r_istanza_tipo_opera_id_istanza_tipo_opera_seq'::regclass);


--
-- Name: mudeopen_r_istanza_utente id_istanza_utente; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_utente ALTER COLUMN id_istanza_utente SET DEFAULT nextval('mudeopen.mudeopen_r_istanza_utente_id_istanza_utente_seq'::regclass);


--
-- Name: mudeopen_r_istanza_utente_quadro id_istanza_utente_quadro; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_utente_quadro ALTER COLUMN id_istanza_utente_quadro SET DEFAULT nextval('mudeopen.mudeopen_r_istanza_utente_quadro_id_istanza_utente_quadro_seq'::regclass);


--
-- Name: mudeopen_r_loc_catasto id_catasto; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_loc_catasto ALTER COLUMN id_catasto SET DEFAULT nextval('mudeopen.mudeopen_r_loc_catasto_id_catasto_seq'::regclass);


--
-- Name: mudeopen_r_loc_cellula id_cellula; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_loc_cellula ALTER COLUMN id_cellula SET DEFAULT nextval('mudeopen.mudeopen_r_loc_cellula_id_cellula_seq'::regclass);


--
-- Name: mudeopen_r_loc_datocarota id_geo_datocarota; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_loc_datocarota ALTER COLUMN id_geo_datocarota SET DEFAULT nextval('mudeopen.mudeopen_r_loc_datocarota_id_geo_datocarota_seq'::regclass);


--
-- Name: mudeopen_r_loc_fabbricato id_fabbricato; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_loc_fabbricato ALTER COLUMN id_fabbricato SET DEFAULT nextval('mudeopen.mudeopen_r_loc_fabbricato_id_fabbricato_seq'::regclass);


--
-- Name: mudeopen_r_loc_georiferim id_georiferimento; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_loc_georiferim ALTER COLUMN id_georiferimento SET DEFAULT nextval('mudeopen.mudeopen_r_loc_georiferim_id_georiferimento_seq'::regclass);


--
-- Name: mudeopen_r_loc_titolare id_titolare; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_loc_titolare ALTER COLUMN id_titolare SET DEFAULT nextval('mudeopen.mudeopen_r_loc_titolare_id_titolare_seq'::regclass);


--
-- Name: mudeopen_r_loc_ubicazione id_ubicazione; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_loc_ubicazione ALTER COLUMN id_ubicazione SET DEFAULT nextval('mudeopen.mudeopen_r_loc_ubicazione_id_ubicazione_seq'::regclass);


--
-- Name: mudeopen_r_pf_pg id_pf_pg; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_pf_pg ALTER COLUMN id_pf_pg SET DEFAULT nextval('mudeopen.mudeopen_r_pf_pg_id_pf_pg_seq'::regclass);


--
-- Name: mudeopen_r_ruolo_fruitore id_ruolo_fruitore; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_ruolo_fruitore ALTER COLUMN id_ruolo_fruitore SET DEFAULT nextval('mudeopen.mudeopen_r_ruolo_fruitore_id_ruolo_fruitore_seq'::regclass);


--
-- Name: mudeopen_r_ruolo_funzione id_ruolo_funzione; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_ruolo_funzione ALTER COLUMN id_ruolo_funzione SET DEFAULT nextval('mudeopen.mudeopen_r_ruolo_funzione_id_ruolo_funzione_seq'::regclass);


--
-- Name: mudeopen_r_sism_class_opere id_class_opere; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_sism_class_opere ALTER COLUMN id_class_opere SET DEFAULT nextval('mudeopen.mudeopen_r_sism_class_opere_id_class_opere_seq'::regclass);


--
-- Name: mudeopen_r_sism_istanze_rif id_sism_istanza_rif; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_sism_istanze_rif ALTER COLUMN id_sism_istanza_rif SET DEFAULT nextval('mudeopen.mudeopen_r_sism_istanze_rif_id_sism_istanza_rif_seq'::regclass);


--
-- Name: mudeopen_r_sism_rel_ill_norma id_sism_rel_ill_norma; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_sism_rel_ill_norma ALTER COLUMN id_sism_rel_ill_norma SET DEFAULT nextval('mudeopen.mudeopen_r_sism_rel_ill_norma_id_sism_rel_ill_norma_seq'::regclass);


--
-- Name: mudeopen_r_template_quadro id_template_quadro; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_template_quadro ALTER COLUMN id_template_quadro SET DEFAULT nextval('mudeopen.mudeopen_r_template_quadro_id_template_quadro_seq'::regclass);


--
-- Name: mudeopen_r_template_tipo_allegato id_template_tipo_allegato; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_template_tipo_allegato ALTER COLUMN id_template_tipo_allegato SET DEFAULT nextval('mudeopen.mudeopen_r_template_tipo_allegato_id_template_tipo_allegato_seq'::regclass);


--
-- Name: mudeopen_r_tipo_istanza id_tipo_istanza; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza ALTER COLUMN id_tipo_istanza SET DEFAULT nextval('mudeopen.mudeopen_r_tipo_istanza_id_tipo_istanza_seq'::regclass);


--
-- Name: mudeopen_r_tipo_istanza_fruitore id_tipo_istanza_fruitore; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza_fruitore ALTER COLUMN id_tipo_istanza_fruitore SET DEFAULT nextval('mudeopen.mudeopen_r_tipo_istanza_fruitore_id_tipo_istanza_fruitore_seq'::regclass);


--
-- Name: mudeopen_r_tipo_istanza_stato id_tipo_istanza_stato; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza_stato ALTER COLUMN id_tipo_istanza_stato SET DEFAULT nextval('mudeopen.mudeopen_r_tipo_istanza_stato_id_tipo_istanza_stato_seq'::regclass);


--
-- Name: mudeopen_r_tipo_istanza_tipo_intervento id_tipo_istanza_tipo_intervento; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza_tipo_intervento ALTER COLUMN id_tipo_istanza_tipo_intervento SET DEFAULT nextval('mudeopen.mudeopen_r_tipo_istanza_tipo_intervento_id_seq'::regclass);


--
-- Name: mudeopen_r_utente_ente id_utente_ente; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_utente_ente ALTER COLUMN id_utente_ente SET DEFAULT nextval('mudeopen.mudeopen_r_utente_ente_id_utente_ente_seq'::regclass);


--
-- Name: mudeopen_r_utente_ruolo id_utente_ruolo; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_utente_ruolo ALTER COLUMN id_utente_ruolo SET DEFAULT nextval('mudeopen.mudeopen_r_utente_ruolo_id_utente_ruolo_seq'::regclass);


--
-- Name: mudeopen_t_allegato id_allegato; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_allegato ALTER COLUMN id_allegato SET DEFAULT nextval('mudeopen.mudeopen_t_allegato_id_allegato_seq'::regclass);


--
-- Name: mudeopen_t_arcaeos_log id_log; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_arcaeos_log ALTER COLUMN id_log SET DEFAULT nextval('mudeopen.mudeopen_t_arcaeos_log_id_log_seq'::regclass);


--
-- Name: mudeopen_t_contatto id_contatto; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_contatto ALTER COLUMN id_contatto SET DEFAULT nextval('mudeopen.mudeopen_t_contatto_id_contatto_seq'::regclass);


--
-- Name: mudeopen_t_dati_istanza id_dati_istanza; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_dati_istanza ALTER COLUMN id_dati_istanza SET DEFAULT nextval('mudeopen.mudeopen_t_dati_istanza_id_dati_istanza_seq'::regclass);


--
-- Name: mudeopen_t_documento id_documento; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_documento ALTER COLUMN id_documento SET DEFAULT nextval('mudeopen.mudeopen_t_documento_id_documento_seq'::regclass);


--
-- Name: mudeopen_t_ente id_ente; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_ente ALTER COLUMN id_ente SET DEFAULT nextval('mudeopen.mudeopen_t_ente_id_ente_seq'::regclass);


--
-- Name: mudeopen_t_fascicolo id_fascicolo; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_fascicolo ALTER COLUMN id_fascicolo SET DEFAULT nextval('mudeopen.mudeopen_t_fascicolo_id_fascicolo_seq'::regclass);


--
-- Name: mudeopen_t_geeco_comune id_comune; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_geeco_comune ALTER COLUMN id_comune SET DEFAULT nextval('mudeopen.mudeopen_t_geeco_comune_id_comune_seq'::regclass);


--
-- Name: mudeopen_t_geeco_selected id_selected; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_geeco_selected ALTER COLUMN id_selected SET DEFAULT nextval('mudeopen.mudeopen_t_geeco_selected_id_selected_seq'::regclass);


--
-- Name: mudeopen_t_geeco_selected_cllbk id_selected; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_geeco_selected_cllbk ALTER COLUMN id_selected SET DEFAULT nextval('mudeopen.mudeopen_t_geeco_selected_cllbk_id_selected_seq'::regclass);


--
-- Name: mudeopen_t_geeco_session id_session; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_geeco_session ALTER COLUMN id_session SET DEFAULT nextval('mudeopen.mudeopen_t_geeco_session_id_session_seq'::regclass);


--
-- Name: mudeopen_t_impersonate id_impersonate; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_impersonate ALTER COLUMN id_impersonate SET DEFAULT nextval('mudeopen.mudeopen_t_impersonate_id_impersonate_seq'::regclass);


--
-- Name: mudeopen_t_indirizzo id_indirizzo; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_indirizzo ALTER COLUMN id_indirizzo SET DEFAULT nextval('mudeopen.mudeopen_t_indirizzo_id_indirizzo_seq'::regclass);


--
-- Name: mudeopen_t_istanza id_istanza; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_istanza ALTER COLUMN id_istanza SET DEFAULT nextval('mudeopen.mudeopen_t_istanza_id_istanza_seq'::regclass);


--
-- Name: mudeopen_t_istanza_ext_moon id_istanza_ext_moon; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_istanza_ext_moon ALTER COLUMN id_istanza_ext_moon SET DEFAULT nextval('mudeopen.mudeopen_t_istanza_ext_moon_id_istanza_ext_moon_seq'::regclass);


--
-- Name: mudeopen_t_log_numeri_mude id; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_log_numeri_mude ALTER COLUMN id SET DEFAULT nextval('mudeopen.mudeopen_t_log_numeri_mude_id_seq'::regclass);


--
-- Name: mudeopen_t_log_numeri_mude id_fruitore; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_log_numeri_mude ALTER COLUMN id_fruitore SET DEFAULT nextval('mudeopen.mudeopen_t_log_numeri_mude_id_fruitore_seq'::regclass);


--
-- Name: mudeopen_t_log_tracciato id_log_tracciato; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_log_tracciato ALTER COLUMN id_log_tracciato SET DEFAULT nextval('mudeopen.mudeopen_t_log_tracciato_id_log_tracciato_seq'::regclass);


--
-- Name: mudeopen_t_modello id_modello; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_modello ALTER COLUMN id_modello SET DEFAULT nextval('mudeopen.mudeopen_t_modello_id_modello_seq'::regclass);


--
-- Name: mudeopen_t_ppay_pagamento id_pagamento; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_ppay_pagamento ALTER COLUMN id_pagamento SET DEFAULT nextval('mudeopen.mudeopen_t_ppay_pagamento_id_pagamento_seq'::regclass);


--
-- Name: mudeopen_t_pratica id_pratica; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_pratica ALTER COLUMN id_pratica SET DEFAULT nextval('mudeopen.mudeopen_t_pratica_id_pratica_seq'::regclass);


--
-- Name: mudeopen_t_pratica_cosmo id_pratica_cosmo; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_pratica_cosmo ALTER COLUMN id_pratica_cosmo SET DEFAULT nextval('mudeopen.mudeopen_t_pratica_cosmo_id_pratica_cosmo_seq'::regclass);


--
-- Name: mudeopen_t_pratica_idf id_pratica_idf; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_pratica_idf ALTER COLUMN id_pratica_idf SET DEFAULT nextval('mudeopen.mudeopen_t_pratica_idf_id_pratica_idf_seq'::regclass);


--
-- Name: mudeopen_t_sism_costr_es id_sism_costr_es; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_costr_es ALTER COLUMN id_sism_costr_es SET DEFAULT nextval('mudeopen.mudeopen_t_sism_costr_es_id_sism_costr_es_seq'::regclass);


--
-- Name: mudeopen_t_sism_denuncia id_denuncia_sism; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_denuncia ALTER COLUMN id_denuncia_sism SET DEFAULT nextval('mudeopen.mudeopen_t_sism_denuncia_id_denuncia_sism_seq'::regclass);


--
-- Name: mudeopen_t_sism_muri_sost id_sism_muri_sost; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_muri_sost ALTER COLUMN id_sism_muri_sost SET DEFAULT nextval('mudeopen.mudeopen_t_sism_muri_sost_id_sism_muri_sost_seq'::regclass);


--
-- Name: mudeopen_t_sism_nuova_costr id_sism_nuova_costr; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_nuova_costr ALTER COLUMN id_sism_nuova_costr SET DEFAULT nextval('mudeopen.mudeopen_t_sism_nuova_costr_id_sism_nuova_costr_seq'::regclass);


--
-- Name: mudeopen_t_sism_ponti_viad id_sism_ponti_viad; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_ponti_viad ALTER COLUMN id_sism_ponti_viad SET DEFAULT nextval('mudeopen.mudeopen_t_sism_ponti_viad_id_sism_ponti_viad_seq'::regclass);


--
-- Name: mudeopen_t_sism_rel_ill id_sism_rel_ill; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_rel_ill ALTER COLUMN id_sism_rel_ill SET DEFAULT nextval('mudeopen.mudeopen_t_sism_rel_ill_id_sism_rel_ill_seq'::regclass);


--
-- Name: mudeopen_t_user id_user; Type: DEFAULT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_user ALTER COLUMN id_user SET DEFAULT nextval('mudeopen.mudeopen_t_user_id_user_seq'::regclass);


--
-- Name: mudeopen_d_quadro ak_mudeopen_d_quadro; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_quadro
    ADD CONSTRAINT ak_mudeopen_d_quadro UNIQUE (id_tipo_quadro, num_versione);


--
-- Name: mudeopen_r_template_quadro ak_mudeopen_r_template_quadro_01; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_template_quadro
    ADD CONSTRAINT ak_mudeopen_r_template_quadro_01 UNIQUE (id_template, id_quadro);


--
-- Name: mudeopen_d_categoria_quadro fk_mudeopen_d_categoria_quadro; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_categoria_quadro
    ADD CONSTRAINT fk_mudeopen_d_categoria_quadro PRIMARY KEY (id_categoria_quadro);


--
-- Name: mudeopen_fabbricatiparticelle mudeopen_fabbricatiparticelle_pk; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_fabbricatiparticelle
    ADD CONSTRAINT mudeopen_fabbricatiparticelle_pk PRIMARY KEY (id);


--
-- Name: mudeopen_r_civici_particelleurbane mudeopen_r_civici_particelleurbane_pk; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_civici_particelleurbane
    ADD CONSTRAINT mudeopen_r_civici_particelleurbane_pk PRIMARY KEY (id);


--
-- Name: mudeopen_t_log_numeri_mude mudeopen_t_log_numeri_mude_numero_mude_tipo_id_fruitore_key; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_log_numeri_mude
    ADD CONSTRAINT mudeopen_t_log_numeri_mude_numero_mude_tipo_id_fruitore_key UNIQUE (numero_mude, tipo, id_fruitore);


--
-- Name: mudeopen_t_sessione mudeopen_t_sessione_pk; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sessione
    ADD CONSTRAINT mudeopen_t_sessione_pk PRIMARY KEY (id_sessione);


--
-- Name: mudeopen_c_proprieta pk_mudeopen_c_proprieta; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_c_proprieta
    ADD CONSTRAINT pk_mudeopen_c_proprieta PRIMARY KEY (id_proprieta);


--
-- Name: mudeopen_c_proprieta_ente pk_mudeopen_c_proprieta_ente; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_c_proprieta_ente
    ADD CONSTRAINT pk_mudeopen_c_proprieta_ente PRIMARY KEY (id_proprieta_ente);


--
-- Name: mudeopen_d_abilitazione pk_mudeopen_d_abilitazioni; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_abilitazione
    ADD CONSTRAINT pk_mudeopen_d_abilitazioni PRIMARY KEY (id_abilitazione);


--
-- Name: mudeopen_d_adempimento pk_mudeopen_d_adempimento; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_adempimento
    ADD CONSTRAINT pk_mudeopen_d_adempimento PRIMARY KEY (id_adempimento);


--
-- Name: mudeopen_d_ambito pk_mudeopen_d_ambito; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_ambito
    ADD CONSTRAINT pk_mudeopen_d_ambito PRIMARY KEY (id_ambito);


--
-- Name: mudeopen_d_cat_rischio_sismico pk_mudeopen_d_cat_rischio_sismico; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_cat_rischio_sismico
    ADD CONSTRAINT pk_mudeopen_d_cat_rischio_sismico PRIMARY KEY (codice);


--
-- Name: mudeopen_d_categoria pk_mudeopen_d_categoria; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_categoria
    ADD CONSTRAINT pk_mudeopen_d_categoria PRIMARY KEY (id_categoria);


--
-- Name: mudeopen_d_categoria_allegato pk_mudeopen_d_categoria_allegato; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_categoria_allegato
    ADD CONSTRAINT pk_mudeopen_d_categoria_allegato PRIMARY KEY (codice);


--
-- Name: mudeopen_d_comune pk_mudeopen_d_comune; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_comune
    ADD CONSTRAINT pk_mudeopen_d_comune PRIMARY KEY (id_comune);


--
-- Name: mudeopen_d_controllo_cosmo pk_mudeopen_d_controllo_cosmo; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_controllo_cosmo
    ADD CONSTRAINT pk_mudeopen_d_controllo_cosmo PRIMARY KEY (codice);


--
-- Name: mudeopen_d_destinazione_d_uso pk_mudeopen_d_destinazione_d_uso; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_destinazione_d_uso
    ADD CONSTRAINT pk_mudeopen_d_destinazione_d_uso PRIMARY KEY (codice);


--
-- Name: mudeopen_d_dug pk_mudeopen_d_dug; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_dug
    ADD CONSTRAINT pk_mudeopen_d_dug PRIMARY KEY (id_dug);


--
-- Name: mudeopen_d_elemento pk_mudeopen_d_elemento; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_elemento
    ADD CONSTRAINT pk_mudeopen_d_elemento PRIMARY KEY (id_elemento);


--
-- Name: mudeopen_d_elemento_elenco pk_mudeopen_d_elemento_elenco; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_elemento_elenco
    ADD CONSTRAINT pk_mudeopen_d_elemento_elenco PRIMARY KEY (id_elemento_elenco);


--
-- Name: mudeopen_d_fonte pk_mudeopen_d_fonte_id_fonte; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_fonte
    ADD CONSTRAINT pk_mudeopen_d_fonte_id_fonte PRIMARY KEY (id_fonte);


--
-- Name: mudeopen_d_fruitore pk_mudeopen_d_fruitore; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_fruitore
    ADD CONSTRAINT pk_mudeopen_d_fruitore PRIMARY KEY (id_fruitore);


--
-- Name: mudeopen_d_funzione pk_mudeopen_d_funzione; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_funzione
    ADD CONSTRAINT pk_mudeopen_d_funzione PRIMARY KEY (id_funzione);


--
-- Name: mudeopen_d_nazione pk_mudeopen_d_nazione; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_nazione
    ADD CONSTRAINT pk_mudeopen_d_nazione PRIMARY KEY (id_nazione);


--
-- Name: mudeopen_d_opera pk_mudeopen_d_opera; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_opera
    ADD CONSTRAINT pk_mudeopen_d_opera PRIMARY KEY (id_opera);


--
-- Name: mudeopen_d_ordine pk_mudeopen_d_ordine; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_ordine
    ADD CONSTRAINT pk_mudeopen_d_ordine PRIMARY KEY (codice);


--
-- Name: mudeopen_d_ppay_importi pk_mudeopen_d_ppay_importi_id_importo; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_ppay_importi
    ADD CONSTRAINT pk_mudeopen_d_ppay_importi_id_importo PRIMARY KEY (id_importo);


--
-- Name: mudeopen_d_provincia pk_mudeopen_d_provincia; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_provincia
    ADD CONSTRAINT pk_mudeopen_d_provincia PRIMARY KEY (id_provincia);


--
-- Name: mudeopen_d_quadro pk_mudeopen_d_quadro; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_quadro
    ADD CONSTRAINT pk_mudeopen_d_quadro PRIMARY KEY (id_quadro);


--
-- Name: mudeopen_d_qualifica pk_mudeopen_d_qualifica; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_qualifica
    ADD CONSTRAINT pk_mudeopen_d_qualifica PRIMARY KEY (id_qualifica);


--
-- Name: mudeopen_d_qualifica_collegio pk_mudeopen_d_qualifica_collegio; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_qualifica_collegio
    ADD CONSTRAINT pk_mudeopen_d_qualifica_collegio PRIMARY KEY (codice);


--
-- Name: mudeopen_d_qualifica_urbanistica pk_mudeopen_d_qualifica_urbanistica; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_qualifica_urbanistica
    ADD CONSTRAINT pk_mudeopen_d_qualifica_urbanistica PRIMARY KEY (id_qualifica_urbanistica);


--
-- Name: mudeopen_d_regime_giuridico pk_mudeopen_d_regime_giuridico; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_regime_giuridico
    ADD CONSTRAINT pk_mudeopen_d_regime_giuridico PRIMARY KEY (id_regime);


--
-- Name: mudeopen_d_regime_giuridico_aggiuntivo pk_mudeopen_d_regime_giuridico_aggiuntivo; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_regime_giuridico_aggiuntivo
    ADD CONSTRAINT pk_mudeopen_d_regime_giuridico_aggiuntivo PRIMARY KEY (id_regime);


--
-- Name: mudeopen_d_regione pk_mudeopen_d_regione; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_regione
    ADD CONSTRAINT pk_mudeopen_d_regione PRIMARY KEY (id_regione);


--
-- Name: mudeopen_d_ruolo_soggetto pk_mudeopen_d_ruolo_soggetto; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_ruolo_soggetto
    ADD CONSTRAINT pk_mudeopen_d_ruolo_soggetto PRIMARY KEY (codice);


--
-- Name: mudeopen_d_ruolo_utente pk_mudeopen_d_ruolo_utente; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_ruolo_utente
    ADD CONSTRAINT pk_mudeopen_d_ruolo_utente PRIMARY KEY (codice);


--
-- Name: mudeopen_d_sism_classe_uso pk_mudeopen_d_sism_classe_uso; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_sism_classe_uso
    ADD CONSTRAINT pk_mudeopen_d_sism_classe_uso PRIMARY KEY (codice);


--
-- Name: mudeopen_d_sism_costr_tipo pk_mudeopen_d_sism_costr_tipo; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_sism_costr_tipo
    ADD CONSTRAINT pk_mudeopen_d_sism_costr_tipo PRIMARY KEY (codice);


--
-- Name: mudeopen_d_sism_int_par pk_mudeopen_d_sism_int_par; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_sism_int_par
    ADD CONSTRAINT pk_mudeopen_d_sism_int_par PRIMARY KEY (codice);


--
-- Name: mudeopen_d_sism_int_tipo pk_mudeopen_d_sism_int_tipo; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_sism_int_tipo
    ADD CONSTRAINT pk_mudeopen_d_sism_int_tipo PRIMARY KEY (codice);


--
-- Name: mudeopen_d_sism_norma pk_mudeopen_d_sism_norma; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_sism_norma
    ADD CONSTRAINT pk_mudeopen_d_sism_norma PRIMARY KEY (id_sism_norma);


--
-- Name: mudeopen_d_specie_pratica pk_mudeopen_d_specie_pratica; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_specie_pratica
    ADD CONSTRAINT pk_mudeopen_d_specie_pratica PRIMARY KEY (codice);


--
-- Name: mudeopen_d_stato_cosmo pk_mudeopen_d_stato_cosmo; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_stato_cosmo
    ADD CONSTRAINT pk_mudeopen_d_stato_cosmo PRIMARY KEY (codice);


--
-- Name: mudeopen_d_stato_fascicolo pk_mudeopen_d_stato_fascicolo; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_stato_fascicolo
    ADD CONSTRAINT pk_mudeopen_d_stato_fascicolo PRIMARY KEY (codice);


--
-- Name: mudeopen_d_stato_filtro_veloce pk_mudeopen_d_stato_filtro_veloce; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_stato_filtro_veloce
    ADD CONSTRAINT pk_mudeopen_d_stato_filtro_veloce PRIMARY KEY (codice);


--
-- Name: mudeopen_d_stato_idf pk_mudeopen_d_stato_idf; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_stato_idf
    ADD CONSTRAINT pk_mudeopen_d_stato_idf PRIMARY KEY (codice);


--
-- Name: mudeopen_d_stato_istanza pk_mudeopen_d_stato_istanza; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_stato_istanza
    ADD CONSTRAINT pk_mudeopen_d_stato_istanza PRIMARY KEY (codice);


--
-- Name: mudeopen_d_stato_istanza_ext_moon pk_mudeopen_d_stato_istanza_ext_moon; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_stato_istanza_ext_moon
    ADD CONSTRAINT pk_mudeopen_d_stato_istanza_ext_moon PRIMARY KEY (cod_stato_istanza_ext_moon);


--
-- Name: mudeopen_d_task_quadro pk_mudeopen_d_task_quadro; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_task_quadro
    ADD CONSTRAINT pk_mudeopen_d_task_quadro PRIMARY KEY (codice_task);


--
-- Name: mudeopen_d_template pk_mudeopen_d_template; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_template
    ADD CONSTRAINT pk_mudeopen_d_template PRIMARY KEY (id_template);


--
-- Name: mudeopen_d_template_ricevuta_istanza pk_mudeopen_d_template_ricevuta_istanza_id_template; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_template_ricevuta_istanza
    ADD CONSTRAINT pk_mudeopen_d_template_ricevuta_istanza_id_template PRIMARY KEY (id_template_ricevuta_istanza);


--
-- Name: mudeopen_d_template_tracciato pk_mudeopen_d_template_tracciato; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_template_tracciato
    ADD CONSTRAINT pk_mudeopen_d_template_tracciato PRIMARY KEY (id_template_tracciato);


--
-- Name: mudeopen_d_tipo_allegato pk_mudeopen_d_tipo_allegato; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_tipo_allegato
    ADD CONSTRAINT pk_mudeopen_d_tipo_allegato PRIMARY KEY (codice);


--
-- Name: mudeopen_d_tipo_deroga pk_mudeopen_d_tipo_deroga; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_tipo_deroga
    ADD CONSTRAINT pk_mudeopen_d_tipo_deroga PRIMARY KEY (codice);


--
-- Name: mudeopen_d_tipo_ditta pk_mudeopen_d_tipo_ditta; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_tipo_ditta
    ADD CONSTRAINT pk_mudeopen_d_tipo_ditta PRIMARY KEY (id_tipo_ditta);


--
-- Name: mudeopen_d_tipo_docpa pk_mudeopen_d_tipo_docpa; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_tipo_docpa
    ADD CONSTRAINT pk_mudeopen_d_tipo_docpa PRIMARY KEY (id_tipo_docpa);


--
-- Name: mudeopen_d_tipo_elenco pk_mudeopen_d_tipo_elenco; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_tipo_elenco
    ADD CONSTRAINT pk_mudeopen_d_tipo_elenco PRIMARY KEY (id_tipo_elenco);


--
-- Name: mudeopen_d_tipo_ente pk_mudeopen_d_tipo_ente; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_tipo_ente
    ADD CONSTRAINT pk_mudeopen_d_tipo_ente PRIMARY KEY (codice);


--
-- Name: mudeopen_d_tipo_intervento pk_mudeopen_d_tipo_intervento; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_tipo_intervento
    ADD CONSTRAINT pk_mudeopen_d_tipo_intervento PRIMARY KEY (codice);


--
-- Name: mudeopen_d_tipo_intervento_paesaggistica pk_mudeopen_d_tipo_intervento_paesaggistica; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_tipo_intervento_paesaggistica
    ADD CONSTRAINT pk_mudeopen_d_tipo_intervento_paesaggistica PRIMARY KEY (codice);


--
-- Name: mudeopen_d_tipo_istanza pk_mudeopen_d_tipo_istanza; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_tipo_istanza
    ADD CONSTRAINT pk_mudeopen_d_tipo_istanza PRIMARY KEY (codice);


--
-- Name: mudeopen_d_tipo_notifica pk_mudeopen_d_tipo_notifica; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_tipo_notifica
    ADD CONSTRAINT pk_mudeopen_d_tipo_notifica PRIMARY KEY (id_tipo_notifica);


--
-- Name: mudeopen_d_tipo_opera pk_mudeopen_d_tipo_opera; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_tipo_opera
    ADD CONSTRAINT pk_mudeopen_d_tipo_opera PRIMARY KEY (codice);


--
-- Name: mudeopen_d_tipo_presentatore pk_mudeopen_d_tipo_presentatore; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_tipo_presentatore
    ADD CONSTRAINT pk_mudeopen_d_tipo_presentatore PRIMARY KEY (id_tipo);


--
-- Name: mudeopen_d_tipo_quadro pk_mudeopen_d_tipo_quadro; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_tipo_quadro
    ADD CONSTRAINT pk_mudeopen_d_tipo_quadro PRIMARY KEY (id_tipo_quadro);


--
-- Name: mudeopen_d_tipo_tracciato pk_mudeopen_d_tipo_tracciato; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_tipo_tracciato
    ADD CONSTRAINT pk_mudeopen_d_tipo_tracciato PRIMARY KEY (id_tipo_tracciato);


--
-- Name: mudeopen_d_tipologia_committente pk_mudeopen_d_tipologia_committente; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_tipologia_committente
    ADD CONSTRAINT pk_mudeopen_d_tipologia_committente PRIMARY KEY (id_tipologia_committente);


--
-- Name: mudeopen_d_tipologia_opere_precarie pk_mudeopen_d_tipologia_opere_precarie; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_tipologia_opere_precarie
    ADD CONSTRAINT pk_mudeopen_d_tipologia_opere_precarie PRIMARY KEY (codice);


--
-- Name: mudeopen_d_tipologia_tipo_intervento_paesaggistica pk_mudeopen_d_tipologia_tipo_intervento_paesaggistica; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_tipologia_tipo_intervento_paesaggistica
    ADD CONSTRAINT pk_mudeopen_d_tipologia_tipo_intervento_paesaggistica PRIMARY KEY (codice);


--
-- Name: mudeopen_d_titolo pk_mudeopen_d_titolo; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_titolo
    ADD CONSTRAINT pk_mudeopen_d_titolo PRIMARY KEY (codice);


--
-- Name: mudeopen_d_wf_stato pk_mudeopen_d_wf_stato; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_wf_stato
    ADD CONSTRAINT pk_mudeopen_d_wf_stato PRIMARY KEY (id_wf_stato);


--
-- Name: mudeopen_mw_pre_t_indirizzo pk_mudeopen_mw_pre_t_indirizzo_id; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_mw_pre_t_indirizzo
    ADD CONSTRAINT pk_mudeopen_mw_pre_t_indirizzo_id PRIMARY KEY (id_indirizzo);


--
-- Name: mudeopen_r_abilitazione_funzione pk_mudeopen_r_abilitazione_funzione; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_abilitazione_funzione
    ADD CONSTRAINT pk_mudeopen_r_abilitazione_funzione PRIMARY KEY (id_abilitazione_funzione);


--
-- Name: mudeopen_r_allegato_moon_ricevuto pk_mudeopen_r_allegato_moon_ricevuto; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_allegato_moon_ricevuto
    ADD CONSTRAINT pk_mudeopen_r_allegato_moon_ricevuto PRIMARY KEY (id_allegato_moon_ricevuto);


--
-- Name: mudeopen_r_comune_fruitore pk_mudeopen_r_comune_fruitore; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_comune_fruitore
    ADD CONSTRAINT pk_mudeopen_r_comune_fruitore PRIMARY KEY (id_comune_fruitore);


--
-- Name: mudeopen_r_contatto_qualifica pk_mudeopen_r_contatto_qualifica; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_contatto_qualifica
    ADD CONSTRAINT pk_mudeopen_r_contatto_qualifica PRIMARY KEY (id_contatto_qualifica);


--
-- Name: mudeopen_r_ente_comune_tipo_istanza pk_mudeopen_r_ente_comune_tipo_istanza; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_ente_comune_tipo_istanza
    ADD CONSTRAINT pk_mudeopen_r_ente_comune_tipo_istanza PRIMARY KEY (id_ente_comune_tipo_istanza);


--
-- Name: mudeopen_r_ente_fruitore pk_mudeopen_r_ente_fruitore; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_ente_fruitore
    ADD CONSTRAINT pk_mudeopen_r_ente_fruitore PRIMARY KEY (id_ente_fruitore);


--
-- Name: mudeopen_r_fascicolo_indirizzo pk_mudeopen_r_fascicolo_indirizzo; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_fascicolo_indirizzo
    ADD CONSTRAINT pk_mudeopen_r_fascicolo_indirizzo PRIMARY KEY (id_fascicolo_indirizzo);


--
-- Name: mudeopen_r_fascicolo_intestatario pk_mudeopen_r_fascicolo_intestatario; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_fascicolo_intestatario
    ADD CONSTRAINT pk_mudeopen_r_fascicolo_intestatario PRIMARY KEY (id_fascicolo_intestatario);


--
-- Name: mudeopen_r_fascicolo_ruolo pk_mudeopen_r_fascicolo_ruolo_id_fascicolo_ruolo; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_fascicolo_ruolo
    ADD CONSTRAINT pk_mudeopen_r_fascicolo_ruolo_id_fascicolo_ruolo PRIMARY KEY (id_fascicolo_ruolo);


--
-- Name: mudeopen_r_fascicolo_soggetto pk_mudeopen_r_fascicolo_soggetto_id_fascicolo_soggetto; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_fascicolo_soggetto
    ADD CONSTRAINT pk_mudeopen_r_fascicolo_soggetto_id_fascicolo_soggetto PRIMARY KEY (id_fascicolo_soggetto);


--
-- Name: mudeopen_r_fascicolo_stato pk_mudeopen_r_fascicolo_stato; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_fascicolo_stato
    ADD CONSTRAINT pk_mudeopen_r_fascicolo_stato PRIMARY KEY (id_fascicolo_stato);


--
-- Name: mudeopen_r_fascicolo_utente pk_mudeopen_r_fascicolo_utente; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_fascicolo_utente
    ADD CONSTRAINT pk_mudeopen_r_fascicolo_utente PRIMARY KEY (id_fascicolo_utente);


--
-- Name: mudeopen_r_istanza_ente pk_mudeopen_r_istanza_ente; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_ente
    ADD CONSTRAINT pk_mudeopen_r_istanza_ente PRIMARY KEY (id_istanza_ente);


--
-- Name: mudeopen_r_istanza_ext_moon_stato pk_mudeopen_r_istanza_ext_moon_stato; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_ext_moon_stato
    ADD CONSTRAINT pk_mudeopen_r_istanza_ext_moon_stato PRIMARY KEY (id_istanza_ext_moon, cod_stato_istanza_ext_moon, data_inizio);


--
-- Name: mudeopen_r_istanza_pratica pk_mudeopen_r_istanza_pratica_id_istanza_pratica; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_pratica
    ADD CONSTRAINT pk_mudeopen_r_istanza_pratica_id_istanza_pratica PRIMARY KEY (id_istanza_pratica);


--
-- Name: mudeopen_r_istanza_quadro_utente pk_mudeopen_r_istanza_quadro_utente; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_quadro_utente
    ADD CONSTRAINT pk_mudeopen_r_istanza_quadro_utente PRIMARY KEY (id_istanza_quadro_utente);


--
-- Name: mudeopen_r_istanza_soggetto pk_mudeopen_r_istanza_soggetto; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_soggetto
    ADD CONSTRAINT pk_mudeopen_r_istanza_soggetto PRIMARY KEY (id_istanza_soggetto);


--
-- Name: mudeopen_r_istanza_soggetto_ruoli pk_mudeopen_r_istanza_soggetto_ruoli; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_soggetto_ruoli
    ADD CONSTRAINT pk_mudeopen_r_istanza_soggetto_ruoli PRIMARY KEY (id_istanza_soggetto, ruoli);


--
-- Name: mudeopen_r_istanza_stato pk_mudeopen_r_istanza_stato; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_stato
    ADD CONSTRAINT pk_mudeopen_r_istanza_stato PRIMARY KEY (id_istanza_stato);


--
-- Name: mudeopen_r_istanza_tipo_opera pk_mudeopen_r_istanza_tipo_opera; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_tipo_opera
    ADD CONSTRAINT pk_mudeopen_r_istanza_tipo_opera PRIMARY KEY (id_istanza_tipo_opera);


--
-- Name: mudeopen_r_istanza_tracciato pk_mudeopen_r_istanza_tracciato; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_tracciato
    ADD CONSTRAINT pk_mudeopen_r_istanza_tracciato PRIMARY KEY (id_istanza_tracciato);


--
-- Name: mudeopen_r_istanza_utente pk_mudeopen_r_istanza_utente; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_utente
    ADD CONSTRAINT pk_mudeopen_r_istanza_utente PRIMARY KEY (id_istanza_utente);


--
-- Name: mudeopen_r_istanza_utente_quadro pk_mudeopen_r_istanza_utente_quadro; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_utente_quadro
    ADD CONSTRAINT pk_mudeopen_r_istanza_utente_quadro PRIMARY KEY (id_istanza_utente_quadro);


--
-- Name: mudeopen_r_loc_catasto pk_mudeopen_r_loc_catasto_id_catasto; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_loc_catasto
    ADD CONSTRAINT pk_mudeopen_r_loc_catasto_id_catasto PRIMARY KEY (id_catasto);


--
-- Name: mudeopen_r_loc_cellula pk_mudeopen_r_loc_cellula_id_cellula; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_loc_cellula
    ADD CONSTRAINT pk_mudeopen_r_loc_cellula_id_cellula PRIMARY KEY (id_cellula);


--
-- Name: mudeopen_r_loc_datocarota pk_mudeopen_r_loc_datocarota_id_geo_datocarota; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_loc_datocarota
    ADD CONSTRAINT pk_mudeopen_r_loc_datocarota_id_geo_datocarota PRIMARY KEY (id_geo_datocarota);


--
-- Name: mudeopen_r_loc_fabbricato pk_mudeopen_r_loc_fabbricato_id_fabbricato; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_loc_fabbricato
    ADD CONSTRAINT pk_mudeopen_r_loc_fabbricato_id_fabbricato PRIMARY KEY (id_fabbricato);


--
-- Name: mudeopen_r_loc_georiferim pk_mudeopen_r_loc_georiferim_id_georiferimento; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_loc_georiferim
    ADD CONSTRAINT pk_mudeopen_r_loc_georiferim_id_georiferimento PRIMARY KEY (id_georiferimento);


--
-- Name: mudeopen_r_loc_titolare pk_mudeopen_r_loc_titolare_id_titolare; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_loc_titolare
    ADD CONSTRAINT pk_mudeopen_r_loc_titolare_id_titolare PRIMARY KEY (id_titolare);


--
-- Name: mudeopen_r_loc_ubicazione pk_mudeopen_r_loc_ubicazione_id_ubicazione; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_loc_ubicazione
    ADD CONSTRAINT pk_mudeopen_r_loc_ubicazione_id_ubicazione PRIMARY KEY (id_ubicazione);


--
-- Name: mudeopen_r_notifica_contatto pk_mudeopen_r_notifica_contatto; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_notifica_contatto
    ADD CONSTRAINT pk_mudeopen_r_notifica_contatto PRIMARY KEY (id_notifica_contatto);


--
-- Name: mudeopen_r_notifica_documento pk_mudeopen_r_notifica_documento; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_notifica_documento
    ADD CONSTRAINT pk_mudeopen_r_notifica_documento PRIMARY KEY (id_notifica_documento);


--
-- Name: mudeopen_r_notifica_utente pk_mudeopen_r_notifica_utente; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_notifica_utente
    ADD CONSTRAINT pk_mudeopen_r_notifica_utente PRIMARY KEY (id_notifica_utente);


--
-- Name: mudeopen_r_pf_pg pk_mudeopen_r_pf_pg; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_pf_pg
    ADD CONSTRAINT pk_mudeopen_r_pf_pg PRIMARY KEY (id_pf_pg);


--
-- Name: mudeopen_r_ruolo_fruitore pk_mudeopen_r_ruolo_fruitore; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_ruolo_fruitore
    ADD CONSTRAINT pk_mudeopen_r_ruolo_fruitore PRIMARY KEY (id_ruolo_fruitore);


--
-- Name: mudeopen_r_ruolo_funzione pk_mudeopen_r_ruolo_funzione; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_ruolo_funzione
    ADD CONSTRAINT pk_mudeopen_r_ruolo_funzione PRIMARY KEY (id_ruolo_funzione);


--
-- Name: mudeopen_r_sism_class_opere pk_mudeopen_r_sism_class_opere; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_sism_class_opere
    ADD CONSTRAINT pk_mudeopen_r_sism_class_opere PRIMARY KEY (id_class_opere);


--
-- Name: mudeopen_r_sism_istanze_rif pk_mudeopen_r_sism_istanze_rif; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_sism_istanze_rif
    ADD CONSTRAINT pk_mudeopen_r_sism_istanze_rif PRIMARY KEY (id_sism_istanza_rif);


--
-- Name: mudeopen_r_sism_rel_ill_norma pk_mudeopen_r_sism_rel_ill_norma; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_sism_rel_ill_norma
    ADD CONSTRAINT pk_mudeopen_r_sism_rel_ill_norma PRIMARY KEY (id_sism_rel_ill_norma);


--
-- Name: mudeopen_r_specie_pratica_tipo_intervento_paesaggistica pk_mudeopen_r_specie_pratica_tipo_interv_paesaggistica; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_specie_pratica_tipo_intervento_paesaggistica
    ADD CONSTRAINT pk_mudeopen_r_specie_pratica_tipo_interv_paesaggistica PRIMARY KEY (id_specie_pratica_tipo_intervento_paesaggistica);


--
-- Name: mudeopen_r_specie_pratica_tipo_intervento pk_mudeopen_r_specie_pratica_tipo_intervento; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_specie_pratica_tipo_intervento
    ADD CONSTRAINT pk_mudeopen_r_specie_pratica_tipo_intervento PRIMARY KEY (id_specie_pratica_tipo_intervento);


--
-- Name: mudeopen_r_specie_pratica_tipo_opera pk_mudeopen_r_specie_pratica_tipo_opera; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_specie_pratica_tipo_opera
    ADD CONSTRAINT pk_mudeopen_r_specie_pratica_tipo_opera PRIMARY KEY (id_specie_pratica_tipo_opera);


--
-- Name: mudeopen_r_template_quadro pk_mudeopen_r_template_quadro; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_template_quadro
    ADD CONSTRAINT pk_mudeopen_r_template_quadro PRIMARY KEY (id_template_quadro);


--
-- Name: mudeopen_r_template_tipo_allegato pk_mudeopen_r_template_tipo_allegato; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_template_tipo_allegato
    ADD CONSTRAINT pk_mudeopen_r_template_tipo_allegato PRIMARY KEY (id_template_tipo_allegato);


--
-- Name: mudeopen_r_tipo_istanza pk_mudeopen_r_tipo_istanza; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza
    ADD CONSTRAINT pk_mudeopen_r_tipo_istanza PRIMARY KEY (id_tipo_istanza);


--
-- Name: mudeopen_r_tipo_istanza_fruitore pk_mudeopen_r_tipo_istanza_fruitore; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza_fruitore
    ADD CONSTRAINT pk_mudeopen_r_tipo_istanza_fruitore PRIMARY KEY (id_tipo_istanza_fruitore);


--
-- Name: mudeopen_r_tipo_istanza_regime_g_regime_a pk_mudeopen_r_tipo_istanza_regime_g_regime_a; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza_regime_g_regime_a
    ADD CONSTRAINT pk_mudeopen_r_tipo_istanza_regime_g_regime_a PRIMARY KEY (id_tipo_istanza_regime_g_regime_a);


--
-- Name: mudeopen_r_tipo_istanza_ruolo_sog pk_mudeopen_r_tipo_istanza_ruolo_sog; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza_ruolo_sog
    ADD CONSTRAINT pk_mudeopen_r_tipo_istanza_ruolo_sog PRIMARY KEY (id_tipo_istanza_ruolo_sog);


--
-- Name: mudeopen_r_tipo_istanza_specie_pratica pk_mudeopen_r_tipo_istanza_specie_pratica; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza_specie_pratica
    ADD CONSTRAINT pk_mudeopen_r_tipo_istanza_specie_pratica PRIMARY KEY (id_tipo_istanza_specie_pratica);


--
-- Name: mudeopen_r_tipo_istanza_stato pk_mudeopen_r_tipo_istanza_stato; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza_stato
    ADD CONSTRAINT pk_mudeopen_r_tipo_istanza_stato PRIMARY KEY (id_tipo_istanza_stato);


--
-- Name: mudeopen_r_tipo_istanza_tipo_intervento pk_mudeopen_r_tipo_istanza_tipo_intervento; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza_tipo_intervento
    ADD CONSTRAINT pk_mudeopen_r_tipo_istanza_tipo_intervento PRIMARY KEY (id_tipo_istanza_tipo_intervento);


--
-- Name: mudeopen_r_tipo_istanza_tipo_opera_diretta pk_mudeopen_r_tipo_istanza_tipo_opera_diretta; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza_tipo_opera_diretta
    ADD CONSTRAINT pk_mudeopen_r_tipo_istanza_tipo_opera_diretta PRIMARY KEY (id_tipo_istanza_tipo_opera_diretta);


--
-- Name: mudeopen_r_tipo_istanza_tipo_tracciato pk_mudeopen_r_tipo_istanza_tipo_tracciato; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza_tipo_tracciato
    ADD CONSTRAINT pk_mudeopen_r_tipo_istanza_tipo_tracciato PRIMARY KEY (id_tipo_istanza_tipo_tracciato);


--
-- Name: mudeopen_r_utente_ente pk_mudeopen_r_utente_ente; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_utente_ente
    ADD CONSTRAINT pk_mudeopen_r_utente_ente PRIMARY KEY (id_utente_ente);


--
-- Name: mudeopen_r_utente_ente_comune_ruolo pk_mudeopen_r_utente_ente_comune_ruolo; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_utente_ente_comune_ruolo
    ADD CONSTRAINT pk_mudeopen_r_utente_ente_comune_ruolo PRIMARY KEY (id_ente, id_comune, id_utente, cod_ruolo);


--
-- Name: mudeopen_r_utente_ruolo pk_mudeopen_r_utente_ruolo; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_utente_ruolo
    ADD CONSTRAINT pk_mudeopen_r_utente_ruolo PRIMARY KEY (id_utente_ruolo);


--
-- Name: mudeopen_t_aeos pk_mudeopen_t_aeos; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_aeos
    ADD CONSTRAINT pk_mudeopen_t_aeos PRIMARY KEY (id_istanza);


--
-- Name: mudeopen_t_allegato pk_mudeopen_t_allegato; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_allegato
    ADD CONSTRAINT pk_mudeopen_t_allegato PRIMARY KEY (id_allegato);


--
-- Name: mudeopen_t_arcaeos_log pk_mudeopen_t_arcaeos_log; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_arcaeos_log
    ADD CONSTRAINT pk_mudeopen_t_arcaeos_log PRIMARY KEY (id_log);


--
-- Name: mudeopen_t_contatto pk_mudeopen_t_contatto; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_contatto
    ADD CONSTRAINT pk_mudeopen_t_contatto PRIMARY KEY (id_contatto);


--
-- Name: mudeopen_t_dati_istanza pk_mudeopen_t_dati_istanza; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_dati_istanza
    ADD CONSTRAINT pk_mudeopen_t_dati_istanza PRIMARY KEY (id_dati_istanza);


--
-- Name: mudeopen_t_documento pk_mudeopen_t_documento; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_documento
    ADD CONSTRAINT pk_mudeopen_t_documento PRIMARY KEY (id_documento);


--
-- Name: mudeopen_t_ente pk_mudeopen_t_ente; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_ente
    ADD CONSTRAINT pk_mudeopen_t_ente PRIMARY KEY (id_ente);


--
-- Name: mudeopen_t_fascicolo pk_mudeopen_t_fascicolo; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_fascicolo
    ADD CONSTRAINT pk_mudeopen_t_fascicolo PRIMARY KEY (id_fascicolo);


--
-- Name: mudeopen_t_geeco_comune pk_mudeopen_t_geeco_comune; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_geeco_comune
    ADD CONSTRAINT pk_mudeopen_t_geeco_comune PRIMARY KEY (id_comune);


--
-- Name: mudeopen_t_geeco_selected pk_mudeopen_t_geeco_selected; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_geeco_selected
    ADD CONSTRAINT pk_mudeopen_t_geeco_selected PRIMARY KEY (id_selected);


--
-- Name: mudeopen_t_geeco_selected_cllbk pk_mudeopen_t_geeco_selected_cllbk; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_geeco_selected_cllbk
    ADD CONSTRAINT pk_mudeopen_t_geeco_selected_cllbk PRIMARY KEY (id_selected);


--
-- Name: mudeopen_t_geeco_session pk_mudeopen_t_geeco_session; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_geeco_session
    ADD CONSTRAINT pk_mudeopen_t_geeco_session PRIMARY KEY (id_session);


--
-- Name: mudeopen_t_impersonate pk_mudeopen_t_impersonate; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_impersonate
    ADD CONSTRAINT pk_mudeopen_t_impersonate PRIMARY KEY (id_impersonate);


--
-- Name: mudeopen_t_indirizzo pk_mudeopen_t_indirizzo; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_indirizzo
    ADD CONSTRAINT pk_mudeopen_t_indirizzo PRIMARY KEY (id_indirizzo);


--
-- Name: mudeopen_t_istanza pk_mudeopen_t_istanza; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_istanza
    ADD CONSTRAINT pk_mudeopen_t_istanza PRIMARY KEY (id_istanza);


--
-- Name: mudeopen_t_istanze_ext pk_mudeopen_t_istanza_ext_id_istanza; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_istanze_ext
    ADD CONSTRAINT pk_mudeopen_t_istanza_ext_id_istanza PRIMARY KEY (id_istanza);


--
-- Name: mudeopen_t_istanza_ext_moon pk_mudeopen_t_istanza_ext_moon; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_istanza_ext_moon
    ADD CONSTRAINT pk_mudeopen_t_istanza_ext_moon PRIMARY KEY (id_istanza_ext_moon);


--
-- Name: mudeopen_t_log_numeri_mude pk_mudeopen_t_log_numeri_mude; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_log_numeri_mude
    ADD CONSTRAINT pk_mudeopen_t_log_numeri_mude PRIMARY KEY (id);


--
-- Name: mudeopen_t_log_tracciato pk_mudeopen_t_log_tracciato; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_log_tracciato
    ADD CONSTRAINT pk_mudeopen_t_log_tracciato PRIMARY KEY (id_log_tracciato);


--
-- Name: mudeopen_t_modello pk_mudeopen_t_modello; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_modello
    ADD CONSTRAINT pk_mudeopen_t_modello PRIMARY KEY (id_modello);


--
-- Name: mudeopen_t_notifica pk_mudeopen_t_notifica; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_notifica
    ADD CONSTRAINT pk_mudeopen_t_notifica PRIMARY KEY (id_notifica);


--
-- Name: mudeopen_t_ppay_pagamento pk_mudeopen_t_ppay_pagamento_id_pagamento; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_ppay_pagamento
    ADD CONSTRAINT pk_mudeopen_t_ppay_pagamento_id_pagamento PRIMARY KEY (id_pagamento);


--
-- Name: mudeopen_t_pratica_cosmo pk_mudeopen_t_pratica_cosmo; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_pratica_cosmo
    ADD CONSTRAINT pk_mudeopen_t_pratica_cosmo PRIMARY KEY (id_pratica_cosmo);


--
-- Name: mudeopen_t_pratica pk_mudeopen_t_pratica_id_pratica; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_pratica
    ADD CONSTRAINT pk_mudeopen_t_pratica_id_pratica PRIMARY KEY (id_pratica);


--
-- Name: mudeopen_t_pratica_idf pk_mudeopen_t_pratica_idf; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_pratica_idf
    ADD CONSTRAINT pk_mudeopen_t_pratica_idf PRIMARY KEY (id_pratica_idf);


--
-- Name: mudeopen_t_sism_costr_es pk_mudeopen_t_sism_costr_es; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_costr_es
    ADD CONSTRAINT pk_mudeopen_t_sism_costr_es PRIMARY KEY (id_sism_costr_es);


--
-- Name: mudeopen_t_sism_denuncia pk_mudeopen_t_sism_denuncia; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_denuncia
    ADD CONSTRAINT pk_mudeopen_t_sism_denuncia PRIMARY KEY (id_denuncia_sism);


--
-- Name: mudeopen_t_sism_muri_sost pk_mudeopen_t_sism_muri_sost; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_muri_sost
    ADD CONSTRAINT pk_mudeopen_t_sism_muri_sost PRIMARY KEY (id_sism_muri_sost);


--
-- Name: mudeopen_t_sism_nuova_costr pk_mudeopen_t_sism_nuova_costr; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_nuova_costr
    ADD CONSTRAINT pk_mudeopen_t_sism_nuova_costr PRIMARY KEY (id_sism_nuova_costr);


--
-- Name: mudeopen_t_sism_ponti_viad pk_mudeopen_t_sism_ponti_viad; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_ponti_viad
    ADD CONSTRAINT pk_mudeopen_t_sism_ponti_viad PRIMARY KEY (id_sism_ponti_viad);


--
-- Name: mudeopen_t_sism_rel_ill pk_mudeopen_t_sism_rel_ill; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_rel_ill
    ADD CONSTRAINT pk_mudeopen_t_sism_rel_ill PRIMARY KEY (id_sism_rel_ill);


--
-- Name: mudeopen_t_user pk_mudeopen_t_user; Type: CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_user
    ADD CONSTRAINT pk_mudeopen_t_user PRIMARY KEY (id_user);


--
-- Name: ix_mudeopen_c_proprieta_ente_codice_tipo_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_c_proprieta_ente_codice_tipo_istanza ON mudeopen_c_proprieta_ente USING btree (codice_tipo_istanza);


--
-- Name: ix_mudeopen_c_proprieta_ente_id_ente; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_c_proprieta_ente_id_ente ON mudeopen_c_proprieta_ente USING btree (id_ente);


--
-- Name: ix_mudeopen_d_adempimento_id_regime; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_adempimento_id_regime ON mudeopen_d_adempimento USING btree (id_regime);


--
-- Name: ix_mudeopen_d_ambito_id_ambito; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_ambito_id_ambito ON mudeopen_d_ambito USING btree (id_ambito);


--
-- Name: ix_mudeopen_d_categoria_id_regime; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_categoria_id_regime ON mudeopen_d_categoria USING btree (id_regime);


--
-- Name: ix_mudeopen_d_comune_id_provincia; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_comune_id_provincia ON mudeopen_d_comune USING btree (id_provincia);


--
-- Name: ix_mudeopen_d_comune_rischio_sismico; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_comune_rischio_sismico ON mudeopen_d_comune USING btree (rischio_sismico);


--
-- Name: ix_mudeopen_d_elemento_elenco_id_tipo_elenco; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_elemento_elenco_id_tipo_elenco ON mudeopen_d_elemento_elenco USING btree (id_tipo_elenco);


--
-- Name: ix_mudeopen_d_fruitore_codice_fruitore; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_fruitore_codice_fruitore ON mudeopen_d_fruitore USING btree (codice_fruitore);


--
-- Name: ix_mudeopen_d_funzione_id_categoria_quadro; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_funzione_id_categoria_quadro ON mudeopen_d_funzione USING btree (id_categoria_quadro);


--
-- Name: ix_mudeopen_d_nazione_id_user; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_nazione_id_user ON mudeopen_d_nazione USING btree (id_user);


--
-- Name: ix_mudeopen_d_opera_id_categoria; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_opera_id_categoria ON mudeopen_d_opera USING btree (id_categoria);


--
-- Name: ix_mudeopen_d_opera_id_elemento; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_opera_id_elemento ON mudeopen_d_opera USING btree (id_elemento);


--
-- Name: ix_mudeopen_d_ppay_importi_id_ente; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_ppay_importi_id_ente ON mudeopen_d_ppay_importi USING btree (id_ente);


--
-- Name: ix_mudeopen_d_ppay_importi_id_importo_default; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_ppay_importi_id_importo_default ON mudeopen_d_ppay_importi USING btree (id_importo_default);


--
-- Name: ix_mudeopen_d_ppay_importi_id_specie_pratica; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_ppay_importi_id_specie_pratica ON mudeopen_d_ppay_importi USING btree (id_specie_pratica);


--
-- Name: ix_mudeopen_d_ppay_importi_id_tipo_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_ppay_importi_id_tipo_istanza ON mudeopen_d_ppay_importi USING btree (id_tipo_istanza);


--
-- Name: ix_mudeopen_d_provincia_id_regione; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_provincia_id_regione ON mudeopen_d_provincia USING btree (id_regione);


--
-- Name: ix_mudeopen_d_quadro_id_modello_documentale; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_quadro_id_modello_documentale ON mudeopen_d_quadro USING btree (id_modello_documentale);


--
-- Name: ix_mudeopen_d_regione_id_nazione; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_regione_id_nazione ON mudeopen_d_regione USING btree (id_nazione);


--
-- Name: ix_mudeopen_d_task_quadro_id_quadro; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_task_quadro_id_quadro ON mudeopen_d_task_quadro USING btree (id_quadro);


--
-- Name: ix_mudeopen_d_template_id_modello_documentale; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_template_id_modello_documentale ON mudeopen_d_template USING btree (id_modello_documentale);


--
-- Name: ix_mudeopen_d_template_id_modello_intestazione; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_template_id_modello_intestazione ON mudeopen_d_template USING btree (id_modello_intestazione);


--
-- Name: ix_mudeopen_d_template_id_template; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_template_id_template ON mudeopen_d_template USING btree (id_template);


--
-- Name: ix_mudeopen_d_template_id_tipo_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_template_id_tipo_istanza ON mudeopen_d_template USING btree (id_tipo_istanza);


--
-- Name: ix_mudeopen_d_tipo_intervento_paesaggistica_id_tipologia_tipo_i; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_tipo_intervento_paesaggistica_id_tipologia_tipo_i ON mudeopen_d_tipo_intervento_paesaggistica USING btree (id_tipologia_tipo_intervento_paesaggistica);


--
-- Name: ix_mudeopen_d_tipo_istanza_id_ambito; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_tipo_istanza_id_ambito ON mudeopen_d_tipo_istanza USING btree (id_ambito);


--
-- Name: ix_mudeopen_d_tipo_istanza_id_template_ricevuta_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_tipo_istanza_id_template_ricevuta_istanza ON mudeopen_d_tipo_istanza USING btree (id_template_ricevuta_istanza);


--
-- Name: ix_mudeopen_d_tipo_quadro_id_categoria_quadro; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_tipo_quadro_id_categoria_quadro ON mudeopen_d_tipo_quadro USING btree (id_categoria_quadro);


--
-- Name: ix_mudeopen_d_tipo_quadro_id_tipo_quadro_padre; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_tipo_quadro_id_tipo_quadro_padre ON mudeopen_d_tipo_quadro USING btree (id_tipo_quadro_padre);


--
-- Name: ix_mudeopen_d_wf_stato_codice_stato_end; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_wf_stato_codice_stato_end ON mudeopen_d_wf_stato USING btree (codice_stato_end);


--
-- Name: ix_mudeopen_d_wf_stato_codice_stato_start; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_wf_stato_codice_stato_start ON mudeopen_d_wf_stato USING btree (codice_stato_start);


--
-- Name: ix_mudeopen_d_wf_stato_id_tipo_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_d_wf_stato_id_tipo_istanza ON mudeopen_d_wf_stato USING btree (id_tipo_istanza);


--
-- Name: ix_mudeopen_r_abilitazione_funzione_id_abilitazione; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_abilitazione_funzione_id_abilitazione ON mudeopen_r_abilitazione_funzione USING btree (id_abilitazione);


--
-- Name: ix_mudeopen_r_abilitazione_funzione_id_funzione; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_abilitazione_funzione_id_funzione ON mudeopen_r_abilitazione_funzione USING btree (id_funzione);


--
-- Name: ix_mudeopen_r_allegato_moon_ricevuto_id_istanza_ext_moon; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_allegato_moon_ricevuto_id_istanza_ext_moon ON mudeopen_r_allegato_moon_ricevuto USING btree (id_istanza_ext_moon);


--
-- Name: ix_mudeopen_r_comune_fruitore_id_comune; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_comune_fruitore_id_comune ON mudeopen_r_comune_fruitore USING btree (id_comune);


--
-- Name: ix_mudeopen_r_comune_fruitore_id_fruitore; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_comune_fruitore_id_fruitore ON mudeopen_r_comune_fruitore USING btree (id_fruitore);


--
-- Name: ix_mudeopen_r_contatto_qualifica_id_contatto; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_contatto_qualifica_id_contatto ON mudeopen_r_contatto_qualifica USING btree (id_contatto);


--
-- Name: ix_mudeopen_r_contatto_qualifica_id_ordine; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_contatto_qualifica_id_ordine ON mudeopen_r_contatto_qualifica USING btree (id_ordine);


--
-- Name: ix_mudeopen_r_contatto_qualifica_id_provincia; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_contatto_qualifica_id_provincia ON mudeopen_r_contatto_qualifica USING btree (id_provincia);


--
-- Name: ix_mudeopen_r_contatto_qualifica_id_qualifica; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_contatto_qualifica_id_qualifica ON mudeopen_r_contatto_qualifica USING btree (id_qualifica);


--
-- Name: ix_mudeopen_r_ente_comune_tipo_istanza_codice_specie_pratica; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_ente_comune_tipo_istanza_codice_specie_pratica ON mudeopen_r_ente_comune_tipo_istanza USING btree (codice_specie_pratica);


--
-- Name: ix_mudeopen_r_ente_comune_tipo_istanza_codice_tipo_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_ente_comune_tipo_istanza_codice_tipo_istanza ON mudeopen_r_ente_comune_tipo_istanza USING btree (codice_tipo_istanza);


--
-- Name: ix_mudeopen_r_ente_comune_tipo_istanza_id_comune; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_ente_comune_tipo_istanza_id_comune ON mudeopen_r_ente_comune_tipo_istanza USING btree (id_comune);


--
-- Name: ix_mudeopen_r_ente_fruitore_data_fine; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_ente_fruitore_data_fine ON mudeopen_r_ente_fruitore USING btree (data_fine);


--
-- Name: ix_mudeopen_r_ente_fruitore_id_ente; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_ente_fruitore_id_ente ON mudeopen_r_ente_fruitore USING btree (id_ente);


--
-- Name: ix_mudeopen_r_ente_fruitore_id_fruitore; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_ente_fruitore_id_fruitore ON mudeopen_r_ente_fruitore USING btree (id_fruitore);


--
-- Name: ix_mudeopen_r_fascicolo_indirizzo_id_fascicolo; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_fascicolo_indirizzo_id_fascicolo ON mudeopen_r_fascicolo_indirizzo USING btree (id_fascicolo);


--
-- Name: ix_mudeopen_r_fascicolo_indirizzo_id_indirizzo; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_fascicolo_indirizzo_id_indirizzo ON mudeopen_r_fascicolo_indirizzo USING btree (id_indirizzo);


--
-- Name: ix_mudeopen_r_fascicolo_intestatario_id_fascicolo; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_fascicolo_intestatario_id_fascicolo ON mudeopen_r_fascicolo_intestatario USING btree (id_fascicolo);


--
-- Name: ix_mudeopen_r_fascicolo_intestatario_id_intestatario; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_fascicolo_intestatario_id_intestatario ON mudeopen_r_fascicolo_intestatario USING btree (id_intestatario);


--
-- Name: ix_mudeopen_r_fascicolo_ruolo_id_fascicolo; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_fascicolo_ruolo_id_fascicolo ON mudeopen_r_fascicolo_ruolo USING btree (id_fascicolo);


--
-- Name: ix_mudeopen_r_fascicolo_ruolo_id_user_ins; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_fascicolo_ruolo_id_user_ins ON mudeopen_r_fascicolo_ruolo USING btree (id_user_ins);


--
-- Name: ix_mudeopen_r_fascicolo_soggetto_id_fascicolo; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_fascicolo_soggetto_id_fascicolo ON mudeopen_r_fascicolo_soggetto USING btree (id_fascicolo);


--
-- Name: ix_mudeopen_r_fascicolo_soggetto_id_istanza_soggetto; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_fascicolo_soggetto_id_istanza_soggetto ON mudeopen_r_fascicolo_soggetto USING btree (id_istanza_soggetto);


--
-- Name: ix_mudeopen_r_fascicolo_soggetto_id_user_ins; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_fascicolo_soggetto_id_user_ins ON mudeopen_r_fascicolo_soggetto USING btree (id_user_ins);


--
-- Name: ix_mudeopen_r_fascicolo_stato_codice_stato_fascicolo; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_fascicolo_stato_codice_stato_fascicolo ON mudeopen_r_fascicolo_stato USING btree (codice_stato_fascicolo);


--
-- Name: ix_mudeopen_r_fascicolo_stato_id_fascicolo; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_fascicolo_stato_id_fascicolo ON mudeopen_r_fascicolo_stato USING btree (id_fascicolo);


--
-- Name: ix_mudeopen_r_fascicolo_utente_id_abilitatore; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_fascicolo_utente_id_abilitatore ON mudeopen_r_fascicolo_utente USING btree (id_abilitatore);


--
-- Name: ix_mudeopen_r_fascicolo_utente_id_abilitazione; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_fascicolo_utente_id_abilitazione ON mudeopen_r_fascicolo_utente USING btree (id_abilitazione);


--
-- Name: ix_mudeopen_r_fascicolo_utente_id_fascicolo; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_fascicolo_utente_id_fascicolo ON mudeopen_r_fascicolo_utente USING btree (id_fascicolo);


--
-- Name: ix_mudeopen_r_fascicolo_utente_id_utente; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_fascicolo_utente_id_utente ON mudeopen_r_fascicolo_utente USING btree (id_utente);


--
-- Name: ix_mudeopen_r_istanza_ente_id_ente; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_istanza_ente_id_ente ON mudeopen_r_istanza_ente USING btree (id_ente);


--
-- Name: ix_mudeopen_r_istanza_ente_id_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_istanza_ente_id_istanza ON mudeopen_r_istanza_ente USING btree (id_istanza);


--
-- Name: ix_mudeopen_r_istanza_ext_moon_stato_cod_stato_istanza_ext_moon; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_istanza_ext_moon_stato_cod_stato_istanza_ext_moon ON mudeopen_r_istanza_ext_moon_stato USING btree (cod_stato_istanza_ext_moon);


--
-- Name: ix_mudeopen_r_istanza_pratica_id_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_istanza_pratica_id_istanza ON mudeopen_r_istanza_pratica USING btree (id_istanza);


--
-- Name: ix_mudeopen_r_istanza_pratica_id_pratica; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_istanza_pratica_id_pratica ON mudeopen_r_istanza_pratica USING btree (id_pratica);


--
-- Name: ix_mudeopen_r_istanza_quadro_utente_id_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_istanza_quadro_utente_id_istanza ON mudeopen_r_istanza_quadro_utente USING btree (id_istanza);


--
-- Name: ix_mudeopen_r_istanza_quadro_utente_id_quadro; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_istanza_quadro_utente_id_quadro ON mudeopen_r_istanza_quadro_utente USING btree (id_quadro);


--
-- Name: ix_mudeopen_r_istanza_quadro_utente_id_utente; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_istanza_quadro_utente_id_utente ON mudeopen_r_istanza_quadro_utente USING btree (id_utente);


--
-- Name: ix_mudeopen_r_istanza_soggetto_id_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_istanza_soggetto_id_istanza ON mudeopen_r_istanza_soggetto USING btree (id_istanza);


--
-- Name: ix_mudeopen_r_istanza_soggetto_id_soggetto; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_istanza_soggetto_id_soggetto ON mudeopen_r_istanza_soggetto USING btree (id_soggetto);


--
-- Name: ix_mudeopen_r_istanza_soggetto_ruoli_ruoli; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_istanza_soggetto_ruoli_ruoli ON mudeopen_r_istanza_soggetto_ruoli USING btree (ruoli);


--
-- Name: ix_mudeopen_r_istanza_stato_codice_stato_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_istanza_stato_codice_stato_istanza ON mudeopen_r_istanza_stato USING btree (codice_stato_istanza);


--
-- Name: ix_mudeopen_r_istanza_stato_data_fine; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_istanza_stato_data_fine ON mudeopen_r_istanza_stato USING btree (data_fine);


--
-- Name: ix_mudeopen_r_istanza_stato_id_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_istanza_stato_id_istanza ON mudeopen_r_istanza_stato USING btree (id_istanza);


--
-- Name: ix_mudeopen_r_istanza_tracciato_id_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_istanza_tracciato_id_istanza ON mudeopen_r_istanza_tracciato USING btree (id_istanza);


--
-- Name: ix_mudeopen_r_istanza_tracciato_id_tipo_tracciato; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_istanza_tracciato_id_tipo_tracciato ON mudeopen_r_istanza_tracciato USING btree (id_tipo_tracciato);


--
-- Name: ix_mudeopen_r_istanza_tracciato_user_id; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_istanza_tracciato_user_id ON mudeopen_r_istanza_tracciato USING btree (user_id);


--
-- Name: ix_mudeopen_r_istanza_utente_id_abilitatore; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_istanza_utente_id_abilitatore ON mudeopen_r_istanza_utente USING btree (id_abilitatore);


--
-- Name: ix_mudeopen_r_istanza_utente_id_abilitazione; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_istanza_utente_id_abilitazione ON mudeopen_r_istanza_utente USING btree (id_abilitazione);


--
-- Name: ix_mudeopen_r_istanza_utente_id_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_istanza_utente_id_istanza ON mudeopen_r_istanza_utente USING btree (id_istanza);


--
-- Name: ix_mudeopen_r_istanza_utente_id_utente; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_istanza_utente_id_utente ON mudeopen_r_istanza_utente USING btree (id_utente);


--
-- Name: ix_mudeopen_r_istanza_utente_quadro_id_abilitazione; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_istanza_utente_quadro_id_abilitazione ON mudeopen_r_istanza_utente_quadro USING btree (id_abilitazione);


--
-- Name: ix_mudeopen_r_istanza_utente_quadro_id_funzione; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_istanza_utente_quadro_id_funzione ON mudeopen_r_istanza_utente_quadro USING btree (id_funzione);


--
-- Name: ix_mudeopen_r_istanza_utente_quadro_id_istanza_utente; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_istanza_utente_quadro_id_istanza_utente ON mudeopen_r_istanza_utente_quadro USING btree (id_istanza_utente);


--
-- Name: ix_mudeopen_r_istanza_utente_quadro_id_quadro; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_istanza_utente_quadro_id_quadro ON mudeopen_r_istanza_utente_quadro USING btree (id_quadro);


--
-- Name: ix_mudeopen_r_notifica_contatto_email_status; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_notifica_contatto_email_status ON mudeopen_r_notifica_contatto USING btree (email_status);


--
-- Name: ix_mudeopen_r_notifica_contatto_id_contatto; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_notifica_contatto_id_contatto ON mudeopen_r_notifica_contatto USING btree (id_contatto);


--
-- Name: ix_mudeopen_r_notifica_contatto_id_notifica; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_notifica_contatto_id_notifica ON mudeopen_r_notifica_contatto USING btree (id_notifica);


--
-- Name: ix_mudeopen_r_notifica_documento_id_documento; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_notifica_documento_id_documento ON mudeopen_r_notifica_documento USING btree (id_documento);


--
-- Name: ix_mudeopen_r_notifica_documento_id_notifica; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_notifica_documento_id_notifica ON mudeopen_r_notifica_documento USING btree (id_notifica);


--
-- Name: ix_mudeopen_r_notifica_utente_id_notifica; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_notifica_utente_id_notifica ON mudeopen_r_notifica_utente USING btree (id_notifica);


--
-- Name: ix_mudeopen_r_notifica_utente_id_utente; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_notifica_utente_id_utente ON mudeopen_r_notifica_utente USING btree (id_utente);


--
-- Name: ix_mudeopen_r_pf_pg_id_soggetto_pf; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_pf_pg_id_soggetto_pf ON mudeopen_r_pf_pg USING btree (id_soggetto_pf);


--
-- Name: ix_mudeopen_r_pf_pg_id_soggetto_pg; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_pf_pg_id_soggetto_pg ON mudeopen_r_pf_pg USING btree (id_soggetto_pg);


--
-- Name: ix_mudeopen_r_pf_pg_id_titolo; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_pf_pg_id_titolo ON mudeopen_r_pf_pg USING btree (id_titolo);


--
-- Name: ix_mudeopen_r_ruolo_fruitore_codice_ruolo; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_ruolo_fruitore_codice_ruolo ON mudeopen_r_ruolo_fruitore USING btree (codice_ruolo);


--
-- Name: ix_mudeopen_r_ruolo_fruitore_data_fine; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_ruolo_fruitore_data_fine ON mudeopen_r_ruolo_fruitore USING btree (data_fine);


--
-- Name: ix_mudeopen_r_ruolo_fruitore_id_fruitore; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_ruolo_fruitore_id_fruitore ON mudeopen_r_ruolo_fruitore USING btree (id_fruitore);


--
-- Name: ix_mudeopen_r_ruolo_funzione_codice_ruolo_utente; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_ruolo_funzione_codice_ruolo_utente ON mudeopen_r_ruolo_funzione USING btree (codice_ruolo_utente);


--
-- Name: ix_mudeopen_r_ruolo_funzione_id_funzione; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_ruolo_funzione_id_funzione ON mudeopen_r_ruolo_funzione USING btree (id_funzione);


--
-- Name: ix_mudeopen_r_specie_pratica_tipo_intervento_id_specie_pratica; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_specie_pratica_tipo_intervento_id_specie_pratica ON mudeopen_r_specie_pratica_tipo_intervento USING btree (id_specie_pratica);


--
-- Name: ix_mudeopen_r_specie_pratica_tipo_intervento_id_tipo_intervento; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_specie_pratica_tipo_intervento_id_tipo_intervento ON mudeopen_r_specie_pratica_tipo_intervento USING btree (id_tipo_intervento);


--
-- Name: ix_mudeopen_r_specie_pratica_tipo_intervento_paesaggistica_id_s; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_specie_pratica_tipo_intervento_paesaggistica_id_s ON mudeopen_r_specie_pratica_tipo_intervento_paesaggistica USING btree (id_specie_pratica);


--
-- Name: ix_mudeopen_r_specie_pratica_tipo_intervento_paesaggistica_id_t; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_specie_pratica_tipo_intervento_paesaggistica_id_t ON mudeopen_r_specie_pratica_tipo_intervento_paesaggistica USING btree (id_tipo_intervento_paesaggistica);


--
-- Name: ix_mudeopen_r_specie_pratica_tipo_opera_id_specie_pratica; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_specie_pratica_tipo_opera_id_specie_pratica ON mudeopen_r_specie_pratica_tipo_opera USING btree (id_specie_pratica);


--
-- Name: ix_mudeopen_r_specie_pratica_tipo_opera_id_tipo_opera; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_specie_pratica_tipo_opera_id_tipo_opera ON mudeopen_r_specie_pratica_tipo_opera USING btree (id_tipo_opera);


--
-- Name: ix_mudeopen_r_template_quadro_id_quadro; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_template_quadro_id_quadro ON mudeopen_r_template_quadro USING btree (id_quadro);


--
-- Name: ix_mudeopen_r_template_tipo_allegato_id_modello_documentale; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_template_tipo_allegato_id_modello_documentale ON mudeopen_r_template_tipo_allegato USING btree (id_modello_documentale);


--
-- Name: ix_mudeopen_r_template_tipo_allegato_id_tipo_allegato; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_template_tipo_allegato_id_tipo_allegato ON mudeopen_r_template_tipo_allegato USING btree (id_tipo_allegato);


--
-- Name: ix_mudeopen_r_tipo_istanza_cod_tipo_istanza_figlia; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_tipo_istanza_cod_tipo_istanza_figlia ON mudeopen_r_tipo_istanza USING btree (cod_tipo_istanza_figlia);


--
-- Name: ix_mudeopen_r_tipo_istanza_cod_tipo_istanza_padre; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_tipo_istanza_cod_tipo_istanza_padre ON mudeopen_r_tipo_istanza USING btree (cod_tipo_istanza_padre);


--
-- Name: ix_mudeopen_r_tipo_istanza_fruitore_id_fruitore; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_tipo_istanza_fruitore_id_fruitore ON mudeopen_r_tipo_istanza_fruitore USING btree (id_fruitore);


--
-- Name: ix_mudeopen_r_tipo_istanza_fruitore_id_tipo_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_tipo_istanza_fruitore_id_tipo_istanza ON mudeopen_r_tipo_istanza_fruitore USING btree (id_tipo_istanza);


--
-- Name: ix_mudeopen_r_tipo_istanza_regime_g_regime_a_id_regime_aggiunti; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_tipo_istanza_regime_g_regime_a_id_regime_aggiunti ON mudeopen_r_tipo_istanza_regime_g_regime_a USING btree (id_regime_aggiuntivo);


--
-- Name: ix_mudeopen_r_tipo_istanza_regime_g_regime_a_id_regime_giuridic; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_tipo_istanza_regime_g_regime_a_id_regime_giuridic ON mudeopen_r_tipo_istanza_regime_g_regime_a USING btree (id_regime_giuridico);


--
-- Name: ix_mudeopen_r_tipo_istanza_regime_g_regime_a_id_tipo_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_tipo_istanza_regime_g_regime_a_id_tipo_istanza ON mudeopen_r_tipo_istanza_regime_g_regime_a USING btree (id_tipo_istanza);


--
-- Name: ix_mudeopen_r_tipo_istanza_ruolo_sog_id_ruolo_soggetto; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_tipo_istanza_ruolo_sog_id_ruolo_soggetto ON mudeopen_r_tipo_istanza_ruolo_sog USING btree (id_ruolo_soggetto);


--
-- Name: ix_mudeopen_r_tipo_istanza_ruolo_sog_id_tipo_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_tipo_istanza_ruolo_sog_id_tipo_istanza ON mudeopen_r_tipo_istanza_ruolo_sog USING btree (id_tipo_istanza);


--
-- Name: ix_mudeopen_r_tipo_istanza_specie_pratica_id_specie_pratica; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_tipo_istanza_specie_pratica_id_specie_pratica ON mudeopen_r_tipo_istanza_specie_pratica USING btree (id_specie_pratica);


--
-- Name: ix_mudeopen_r_tipo_istanza_specie_pratica_id_tipo_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_tipo_istanza_specie_pratica_id_tipo_istanza ON mudeopen_r_tipo_istanza_specie_pratica USING btree (id_tipo_istanza);


--
-- Name: ix_mudeopen_r_tipo_istanza_stato_codice_stato_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_tipo_istanza_stato_codice_stato_istanza ON mudeopen_r_tipo_istanza_stato USING btree (codice_stato_istanza);


--
-- Name: ix_mudeopen_r_tipo_istanza_stato_id_tipo_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_tipo_istanza_stato_id_tipo_istanza ON mudeopen_r_tipo_istanza_stato USING btree (id_tipo_istanza);


--
-- Name: ix_mudeopen_r_tipo_istanza_tipo_intervento_codice_tipo_interven; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_tipo_istanza_tipo_intervento_codice_tipo_interven ON mudeopen_r_tipo_istanza_tipo_intervento USING btree (codice_tipo_intervento);


--
-- Name: ix_mudeopen_r_tipo_istanza_tipo_intervento_codice_tipo_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_tipo_istanza_tipo_intervento_codice_tipo_istanza ON mudeopen_r_tipo_istanza_tipo_intervento USING btree (codice_tipo_istanza);


--
-- Name: ix_mudeopen_r_tipo_istanza_tipo_opera_diretta_id_tipo_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_tipo_istanza_tipo_opera_diretta_id_tipo_istanza ON mudeopen_r_tipo_istanza_tipo_opera_diretta USING btree (id_tipo_istanza);


--
-- Name: ix_mudeopen_r_tipo_istanza_tipo_opera_diretta_id_tipo_opera; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_tipo_istanza_tipo_opera_diretta_id_tipo_opera ON mudeopen_r_tipo_istanza_tipo_opera_diretta USING btree (id_tipo_opera);


--
-- Name: ix_mudeopen_r_tipo_istanza_tipo_tracciato_id_tipo_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_tipo_istanza_tipo_tracciato_id_tipo_istanza ON mudeopen_r_tipo_istanza_tipo_tracciato USING btree (id_tipo_istanza);


--
-- Name: ix_mudeopen_r_tipo_istanza_tipo_tracciato_id_tipo_tracciato; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_tipo_istanza_tipo_tracciato_id_tipo_tracciato ON mudeopen_r_tipo_istanza_tipo_tracciato USING btree (id_tipo_tracciato);


--
-- Name: ix_mudeopen_r_utente_ente_comune_ruolo_cod_ruolo; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_utente_ente_comune_ruolo_cod_ruolo ON mudeopen_r_utente_ente_comune_ruolo USING btree (cod_ruolo);


--
-- Name: ix_mudeopen_r_utente_ente_comune_ruolo_id_comune; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_utente_ente_comune_ruolo_id_comune ON mudeopen_r_utente_ente_comune_ruolo USING btree (id_comune);


--
-- Name: ix_mudeopen_r_utente_ente_comune_ruolo_id_utente; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_utente_ente_comune_ruolo_id_utente ON mudeopen_r_utente_ente_comune_ruolo USING btree (id_utente);


--
-- Name: ix_mudeopen_r_utente_ente_id_ente; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_utente_ente_id_ente ON mudeopen_r_utente_ente USING btree (id_ente);


--
-- Name: ix_mudeopen_r_utente_ente_id_utente; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_utente_ente_id_utente ON mudeopen_r_utente_ente USING btree (id_utente);


--
-- Name: ix_mudeopen_r_utente_ruolo_codice_ruolo_utente; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_utente_ruolo_codice_ruolo_utente ON mudeopen_r_utente_ruolo USING btree (codice_ruolo_utente);


--
-- Name: ix_mudeopen_r_utente_ruolo_id_utente; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_r_utente_ruolo_id_utente ON mudeopen_r_utente_ruolo USING btree (id_utente);


--
-- Name: ix_mudeopen_t_allegato_id_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_allegato_id_istanza ON mudeopen_t_allegato USING btree (id_istanza);


--
-- Name: ix_mudeopen_t_allegato_id_tipo_allegato; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_allegato_id_tipo_allegato ON mudeopen_t_allegato USING btree (id_tipo_allegato);


--
-- Name: ix_mudeopen_t_allegato_id_user; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_allegato_id_user ON mudeopen_t_allegato USING btree (id_user);


--
-- Name: ix_mudeopen_t_contatto_id_comune_nascita; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_contatto_id_comune_nascita ON mudeopen_t_contatto USING btree (id_comune_nascita);


--
-- Name: ix_mudeopen_t_contatto_id_provincia_nascita; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_contatto_id_provincia_nascita ON mudeopen_t_contatto USING btree (id_provincia_nascita);


--
-- Name: ix_mudeopen_t_contatto_id_stato_membro; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_contatto_id_stato_membro ON mudeopen_t_contatto USING btree (id_stato_membro);


--
-- Name: ix_mudeopen_t_contatto_id_stato_nascita; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_contatto_id_stato_nascita ON mudeopen_t_contatto USING btree (id_stato_nascita);


--
-- Name: ix_mudeopen_t_contatto_id_user; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_contatto_id_user ON mudeopen_t_contatto USING btree (id_user);


--
-- Name: ix_mudeopen_t_contatto_id_user_guid; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_contatto_id_user_guid ON mudeopen_t_contatto USING btree (id_user, guid);


--
-- Name: ix_mudeopen_t_dati_istanza_id_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_dati_istanza_id_istanza ON mudeopen_t_dati_istanza USING btree (id_istanza);


--
-- Name: ix_mudeopen_t_dati_istanza_id_soggetto; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_dati_istanza_id_soggetto ON mudeopen_t_dati_istanza USING btree (id_soggetto);


--
-- Name: ix_mudeopen_t_documento_id_pratica; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_documento_id_pratica ON mudeopen_t_documento USING btree (id_pratica);


--
-- Name: ix_mudeopen_t_documento_id_tipo_documento; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_documento_id_tipo_documento ON mudeopen_t_documento USING btree (id_tipo_documento);


--
-- Name: ix_mudeopen_t_ente_id_comune; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_ente_id_comune ON mudeopen_t_ente USING btree (id_comune);


--
-- Name: ix_mudeopen_t_ente_id_tipo_ente; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_ente_id_tipo_ente ON mudeopen_t_ente USING btree (id_tipo_ente);


--
-- Name: ix_mudeopen_t_fascicolo_id_comune; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_fascicolo_id_comune ON mudeopen_t_fascicolo USING btree (id_comune);


--
-- Name: ix_mudeopen_t_fascicolo_id_indirizzo; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_fascicolo_id_indirizzo ON mudeopen_t_fascicolo USING btree (id_indirizzo);


--
-- Name: ix_mudeopen_t_fascicolo_id_tipo_intervento; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_fascicolo_id_tipo_intervento ON mudeopen_t_fascicolo USING btree (id_tipo_intervento);


--
-- Name: ix_mudeopen_t_fascicolo_id_tipo_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_fascicolo_id_tipo_istanza ON mudeopen_t_fascicolo USING btree (id_tipo_istanza);


--
-- Name: ix_mudeopen_t_fascicolo_id_user; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_fascicolo_id_user ON mudeopen_t_fascicolo USING btree (id_user);


--
-- Name: ix_mudeopen_t_indirizzo_id_comune; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_indirizzo_id_comune ON mudeopen_t_indirizzo USING btree (id_comune);


--
-- Name: ix_mudeopen_t_indirizzo_id_contatto; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_indirizzo_id_contatto ON mudeopen_t_indirizzo USING btree (id_contatto);


--
-- Name: ix_mudeopen_t_indirizzo_id_nazione; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_indirizzo_id_nazione ON mudeopen_t_indirizzo USING btree (id_nazione);


--
-- Name: ix_mudeopen_t_istanza_ext_moon_cod_stato_istanza_ext_moon; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_istanza_ext_moon_cod_stato_istanza_ext_moon ON mudeopen_t_istanza_ext_moon USING btree (cod_stato_istanza_ext_moon);


--
-- Name: ix_mudeopen_t_istanza_ext_moon_id_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_istanza_ext_moon_id_istanza ON mudeopen_t_istanza_ext_moon USING btree (id_istanza);


--
-- Name: ix_mudeopen_t_istanza_id_comune; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_istanza_id_comune ON mudeopen_t_istanza USING btree (id_comune);


--
-- Name: ix_mudeopen_t_istanza_id_fascicolo; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_istanza_id_fascicolo ON mudeopen_t_istanza USING btree (id_fascicolo);


--
-- Name: ix_mudeopen_t_istanza_id_fonte; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_istanza_id_fonte ON mudeopen_t_istanza USING btree (id_fonte);


--
-- Name: ix_mudeopen_t_istanza_id_fruitore; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_istanza_id_fruitore ON mudeopen_t_istanza USING btree (id_fruitore);


--
-- Name: ix_mudeopen_t_istanza_id_indirizzo; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_istanza_id_indirizzo ON mudeopen_t_istanza USING btree (id_indirizzo);


--
-- Name: ix_mudeopen_t_istanza_id_istanza_riferimento; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_istanza_id_istanza_riferimento ON mudeopen_t_istanza USING btree (id_istanza_riferimento);


--
-- Name: ix_mudeopen_t_istanza_id_specie_pratica; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_istanza_id_specie_pratica ON mudeopen_t_istanza USING btree (id_specie_pratica);


--
-- Name: ix_mudeopen_t_istanza_id_template; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_istanza_id_template ON mudeopen_t_istanza USING btree (id_template);


--
-- Name: ix_mudeopen_t_istanza_id_tipo_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_istanza_id_tipo_istanza ON mudeopen_t_istanza USING btree (id_tipo_istanza);


--
-- Name: ix_mudeopen_t_istanza_id_user; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_istanza_id_user ON mudeopen_t_istanza USING btree (id_user);


--
-- Name: ix_mudeopen_t_istanze_ext_id_user_ins; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_istanze_ext_id_user_ins ON mudeopen_t_istanze_ext USING btree (id_user_ins);


--
-- Name: ix_mudeopen_t_log_numeri_mude_id_fruitore; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_log_numeri_mude_id_fruitore ON mudeopen_t_log_numeri_mude USING btree (id_fruitore);


--
-- Name: ix_mudeopen_t_log_tracciato_id_tipo_tracciato; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_log_tracciato_id_tipo_tracciato ON mudeopen_t_log_tracciato USING btree (id_tipo_tracciato);


--
-- Name: ix_mudeopen_t_log_tracciato_user_id; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_log_tracciato_user_id ON mudeopen_t_log_tracciato USING btree (user_id);


--
-- Name: ix_mudeopen_t_notifica_id_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_notifica_id_istanza ON mudeopen_t_notifica USING btree (id_istanza);


--
-- Name: ix_mudeopen_t_notifica_id_tipo_notifica; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_notifica_id_tipo_notifica ON mudeopen_t_notifica USING btree (id_tipo_notifica);


--
-- Name: ix_mudeopen_t_notifica_id_user_mittente; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_notifica_id_user_mittente ON mudeopen_t_notifica USING btree (id_user_mittente);


--
-- Name: ix_mudeopen_t_ppay_pagamento_id_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_ppay_pagamento_id_istanza ON mudeopen_t_ppay_pagamento USING btree (id_istanza);


--
-- Name: ix_mudeopen_t_pratica_cosmo_cc_selezionato; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_pratica_cosmo_cc_selezionato ON mudeopen_t_pratica_cosmo USING btree (cc_selezionato);


--
-- Name: ix_mudeopen_t_pratica_cosmo_controllo_campione; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_pratica_cosmo_controllo_campione ON mudeopen_t_pratica_cosmo USING btree (controllo_campione);


--
-- Name: ix_mudeopen_t_pratica_cosmo_id_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_pratica_cosmo_id_istanza ON mudeopen_t_pratica_cosmo USING btree (id_istanza);


--
-- Name: ix_mudeopen_t_pratica_id_ente; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_pratica_id_ente ON mudeopen_t_pratica USING btree (id_ente);


--
-- Name: ix_mudeopen_t_pratica_idf_id_istanza; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_pratica_idf_id_istanza ON mudeopen_t_pratica_idf USING btree (id_istanza);


--
-- Name: ix_mudeopen_t_user_id_comune_nascita; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_user_id_comune_nascita ON mudeopen_t_user USING btree (id_comune_nascita);


--
-- Name: ix_mudeopen_t_user_id_provincia_nascita; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_user_id_provincia_nascita ON mudeopen_t_user USING btree (id_provincia_nascita);


--
-- Name: ix_mudeopen_t_user_id_stato_nascita; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX ix_mudeopen_t_user_id_stato_nascita ON mudeopen_t_user USING btree (id_stato_nascita);


--
-- Name: mudeopen_d_stato_istanza_livello_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_d_stato_istanza_livello_idx ON mudeopen_d_stato_istanza USING btree (livello);


--
-- Name: mudeopen_d_template_tracciato_codice_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE UNIQUE INDEX mudeopen_d_template_tracciato_codice_idx ON mudeopen_d_template_tracciato USING btree (codice);


--
-- Name: mudeopen_r_comune_fruitore_ric_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_r_comune_fruitore_ric_idx ON mudeopen_r_comune_fruitore USING btree (id_fruitore, data_fine, id_comune);


--
-- Name: mudeopen_r_ente_comune_tipo_istanza_id_ente_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE UNIQUE INDEX mudeopen_r_ente_comune_tipo_istanza_id_ente_idx ON mudeopen_r_ente_comune_tipo_istanza USING btree (id_ente, id_comune, codice_tipo_istanza, codice_specie_pratica);


--
-- Name: mudeopen_r_ente_fruitore_ric_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_r_ente_fruitore_ric_idx ON mudeopen_r_ente_fruitore USING btree (id_fruitore, data_fine);


--
-- Name: mudeopen_r_istanza_ente_fruitore_ric_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_r_istanza_ente_fruitore_ric_idx ON mudeopen_r_istanza_ente USING btree (id_istanza, id_ente, data_fine, ente_ricevente);


--
-- Name: mudeopen_r_istanza_stato_fruitore_ric_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_r_istanza_stato_fruitore_ric_idx ON mudeopen_r_istanza_stato USING btree (id_istanza, data_fine, codice_stato_istanza);


--
-- Name: mudeopen_r_istanza_tipo_opera_id_class_operaidx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_r_istanza_tipo_opera_id_class_operaidx ON mudeopen_r_istanza_tipo_opera USING btree (id_istanza);


--
-- Name: mudeopen_r_istanza_tipo_opera_id_denuncia_sism_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_r_istanza_tipo_opera_id_denuncia_sism_idx ON mudeopen_r_istanza_tipo_opera USING btree (id_tipo_opera);


--
-- Name: mudeopen_r_loc_catasto_id_georiferimento_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_r_loc_catasto_id_georiferimento_idx ON mudeopen_r_loc_catasto USING btree (id_georiferimento, id_fabbricato, id_cellula);


--
-- Name: mudeopen_r_loc_cellula_id_georiferimento_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_r_loc_cellula_id_georiferimento_idx ON mudeopen_r_loc_cellula USING btree (id_georiferimento);


--
-- Name: mudeopen_r_loc_datocarota_id_georiferimento_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_r_loc_datocarota_id_georiferimento_idx ON mudeopen_r_loc_datocarota USING btree (id_georiferimento);


--
-- Name: mudeopen_r_loc_ubicazione_id_georiferimento_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_r_loc_ubicazione_id_georiferimento_idx ON mudeopen_r_loc_ubicazione USING btree (id_georiferimento, id_fabbricato, id_cellula);


--
-- Name: mudeopen_r_loc_ubicazione_id_istanza_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_r_loc_ubicazione_id_istanza_idx ON mudeopen_r_loc_ubicazione USING btree (id_istanza, f1_personalizzato);


--
-- Name: mudeopen_r_sism_class_opere_id_class_operaidx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_r_sism_class_opere_id_class_operaidx ON mudeopen_r_sism_class_opere USING btree (id_class_opera);


--
-- Name: mudeopen_r_sism_class_opere_id_denuncia_sism_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_r_sism_class_opere_id_denuncia_sism_idx ON mudeopen_r_sism_class_opere USING btree (id_denuncia_sism);


--
-- Name: mudeopen_r_sism_istanze_rif_id_denuncia_sism_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_r_sism_istanze_rif_id_denuncia_sism_idx ON mudeopen_r_sism_istanze_rif USING btree (id_denuncia_sism);


--
-- Name: mudeopen_r_sism_istanze_rif_istanza_rif_id_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_r_sism_istanze_rif_istanza_rif_id_idx ON mudeopen_r_sism_istanze_rif USING btree (istanza_rif_id);


--
-- Name: mudeopen_r_template_tipo_allegato_id_template_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE UNIQUE INDEX mudeopen_r_template_tipo_allegato_id_template_idx ON mudeopen_r_template_tipo_allegato USING btree (id_template, id_tipo_allegato);


--
-- Name: mudeopen_r_tipo_istanza_fruitore_ric_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_r_tipo_istanza_fruitore_ric_idx ON mudeopen_r_tipo_istanza_fruitore USING btree (id_fruitore, data_fine, id_tipo_istanza);


--
-- Name: mudeopen_t_fascicolo_data_creazione_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_fascicolo_data_creazione_idx ON mudeopen_t_fascicolo USING btree (data_creazione);


--
-- Name: mudeopen_t_fascicolo_data_fine_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_fascicolo_data_fine_idx ON mudeopen_t_fascicolo USING btree (data_fine);


--
-- Name: mudeopen_t_istanza_codice_istanza_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_istanza_codice_istanza_idx ON mudeopen_t_istanza USING btree (codice_istanza);


--
-- Name: mudeopen_t_istanza_data_fine_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_istanza_data_fine_idx ON mudeopen_t_istanza USING btree (data_fine);


--
-- Name: mudeopen_t_istanza_data_inizio_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_istanza_data_inizio_idx ON mudeopen_t_istanza USING btree (data_inizio);


--
-- Name: mudeopen_t_istanza_data_protocollo_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_istanza_data_protocollo_idx ON mudeopen_t_istanza USING btree (data_protocollo);


--
-- Name: mudeopen_t_istanza_fruitore_ric_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_istanza_fruitore_ric_idx ON mudeopen_t_istanza USING btree (id_istanza, id_fruitore, id_fonte, id_comune, data_dps, id_tipo_istanza);


--
-- Name: mudeopen_t_istanza_id_fonte_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_istanza_id_fonte_idx ON mudeopen_t_istanza USING btree (id_fonte);


--
-- Name: mudeopen_t_istanza_id_fruitore_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_istanza_id_fruitore_idx ON mudeopen_t_istanza USING btree (id_fruitore, id_fonte);


--
-- Name: mudeopen_t_istanza_keywords_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_istanza_keywords_idx ON mudeopen_t_istanza USING btree (keywords);


--
-- Name: mudeopen_t_istanza_numero_protocollo_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_istanza_numero_protocollo_idx ON mudeopen_t_istanza USING btree (numero_protocollo);


--
-- Name: mudeopen_t_pratica_cosmo_retries_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_pratica_cosmo_retries_idx ON mudeopen_t_pratica_cosmo USING btree (retries, codice_stato_cosmo);


--
-- Name: mudeopen_t_pratica_idf_retries_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_pratica_idf_retries_idx ON mudeopen_t_pratica_idf USING btree (retries, codice_stato_idf);


--
-- Name: mudeopen_t_sism_costr_es_costr_id_classe_uso_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_sism_costr_es_costr_id_classe_uso_idx ON mudeopen_t_sism_costr_es USING btree (costr_id_classe_uso);


--
-- Name: mudeopen_t_sism_costr_es_costr_id_tipo_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_sism_costr_es_costr_id_tipo_idx ON mudeopen_t_sism_costr_es USING btree (costr_id_tipo);


--
-- Name: mudeopen_t_sism_costr_es_id_sism_rel_ill_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_sism_costr_es_id_sism_rel_ill_idx ON mudeopen_t_sism_costr_es USING btree (id_sism_rel_ill);


--
-- Name: mudeopen_t_sism_costr_es_int_id_par_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_sism_costr_es_int_id_par_idx ON mudeopen_t_sism_costr_es USING btree (int_id_par);


--
-- Name: mudeopen_t_sism_costr_es_int_id_tipo_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_sism_costr_es_int_id_tipo_idx ON mudeopen_t_sism_costr_es USING btree (int_id_tipo);


--
-- Name: mudeopen_t_sism_denuncia_id_destinazione_d_uso_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_sism_denuncia_id_destinazione_d_uso_idx ON mudeopen_t_sism_denuncia USING btree (id_destinazione_d_uso);


--
-- Name: mudeopen_t_sism_denuncia_id_istanza_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_sism_denuncia_id_istanza_idx ON mudeopen_t_sism_denuncia USING btree (id_istanza);


--
-- Name: mudeopen_t_sism_denuncia_id_titolo_riferimento_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_sism_denuncia_id_titolo_riferimento_idx ON mudeopen_t_sism_denuncia USING btree (id_titolo_riferimento);


--
-- Name: mudeopen_t_sism_denuncia_zona_sismica_comune_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_sism_denuncia_zona_sismica_comune_idx ON mudeopen_t_sism_denuncia USING btree (zona_sismica_comune);


--
-- Name: mudeopen_t_sism_muri_sost_costr_id_classe_uso_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_sism_muri_sost_costr_id_classe_uso_idx ON mudeopen_t_sism_muri_sost USING btree (costr_id_classe_uso);


--
-- Name: mudeopen_t_sism_muri_sost_costr_id_tipo_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_sism_muri_sost_costr_id_tipo_idx ON mudeopen_t_sism_muri_sost USING btree (costr_id_tipo);


--
-- Name: mudeopen_t_sism_muri_sost_id_sism_rel_ill_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_sism_muri_sost_id_sism_rel_ill_idx ON mudeopen_t_sism_muri_sost USING btree (id_sism_rel_ill);


--
-- Name: mudeopen_t_sism_muri_sost_int_id_par_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_sism_muri_sost_int_id_par_idx ON mudeopen_t_sism_muri_sost USING btree (int_id_par);


--
-- Name: mudeopen_t_sism_muri_sost_int_id_tipo_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_sism_muri_sost_int_id_tipo_idx ON mudeopen_t_sism_muri_sost USING btree (int_id_tipo);


--
-- Name: mudeopen_t_sism_nuova_costr_costr_id_classe_uso_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_sism_nuova_costr_costr_id_classe_uso_idx ON mudeopen_t_sism_nuova_costr USING btree (costr_id_classe_uso);


--
-- Name: mudeopen_t_sism_nuova_costr_costr_id_tipo_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_sism_nuova_costr_costr_id_tipo_idx ON mudeopen_t_sism_nuova_costr USING btree (costr_id_tipo);


--
-- Name: mudeopen_t_sism_nuova_costr_id_sism_rel_ill_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_sism_nuova_costr_id_sism_rel_ill_idx ON mudeopen_t_sism_nuova_costr USING btree (id_sism_rel_ill);


--
-- Name: mudeopen_t_sism_ponti_viad_costr_id_classe_uso_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_sism_ponti_viad_costr_id_classe_uso_idx ON mudeopen_t_sism_ponti_viad USING btree (costr_id_classe_uso);


--
-- Name: mudeopen_t_sism_ponti_viad_costr_id_tipo_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_sism_ponti_viad_costr_id_tipo_idx ON mudeopen_t_sism_ponti_viad USING btree (costr_id_tipo);


--
-- Name: mudeopen_t_sism_ponti_viad_id_sism_rel_ill_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_sism_ponti_viad_id_sism_rel_ill_idx ON mudeopen_t_sism_ponti_viad USING btree (id_sism_rel_ill);


--
-- Name: mudeopen_t_sism_ponti_viad_int_id_par_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_sism_ponti_viad_int_id_par_idx ON mudeopen_t_sism_ponti_viad USING btree (int_id_par);


--
-- Name: mudeopen_t_sism_ponti_viad_int_id_tipo_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_sism_ponti_viad_int_id_tipo_idx ON mudeopen_t_sism_ponti_viad USING btree (int_id_tipo);


--
-- Name: mudeopen_t_sism_rel_ill_id_class_opera_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_sism_rel_ill_id_class_opera_idx ON mudeopen_t_sism_rel_ill USING btree (id_tipo_opere_ril_reg);


--
-- Name: mudeopen_t_sism_rel_ill_id_denuncia_sism_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_sism_rel_ill_id_denuncia_sism_idx ON mudeopen_t_sism_rel_ill USING btree (id_denuncia_sism);


--
-- Name: mudeopen_t_sism_rel_ill_id_istanza_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_sism_rel_ill_id_istanza_idx ON mudeopen_t_sism_rel_ill USING btree (id_istanza);


--
-- Name: mudeopen_t_sism_rel_ill_id_tipo_edifici_ril_reg_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_sism_rel_ill_id_tipo_edifici_ril_reg_idx ON mudeopen_t_sism_rel_ill USING btree (id_tipo_edifici_ril_reg);


--
-- Name: mudeopen_t_sism_rel_ill_id_tipo_edifici_strat_reg_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_sism_rel_ill_id_tipo_edifici_strat_reg_idx ON mudeopen_t_sism_rel_ill USING btree (id_tipo_edifici_strat_reg);


--
-- Name: mudeopen_t_sism_rel_ill_id_tipo_opere_strat_reg_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_sism_rel_ill_id_tipo_opere_strat_reg_idx ON mudeopen_t_sism_rel_ill USING btree (id_tipo_opere_strat_reg);


--
-- Name: mudeopen_t_user_utente_bo_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_user_utente_bo_idx ON mudeopen_t_user USING btree (utente_bo);


--
-- Name: mudeopen_t_user_utente_fo_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_user_utente_fo_idx ON mudeopen_t_user USING btree (utente_fo);


--
-- Name: mudeopen_t_user_validato_da_utente_idx; Type: INDEX; Schema: mudeopen; Owner: mudeopen
--

CREATE INDEX mudeopen_t_user_validato_da_utente_idx ON mudeopen_t_user USING btree (validato_da_utente);


--
-- Name: mudeopen_d_adempimento fk_mudeopen_d_adempimento_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_adempimento
    ADD CONSTRAINT fk_mudeopen_d_adempimento_01 FOREIGN KEY (id_regime) REFERENCES mudeopen_d_regime_giuridico_aggiuntivo(id_regime);


--
-- Name: mudeopen_d_tipo_istanza fk_mudeopen_d_ambito_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_tipo_istanza
    ADD CONSTRAINT fk_mudeopen_d_ambito_01 FOREIGN KEY (id_ambito) REFERENCES mudeopen_d_ambito(id_ambito);


--
-- Name: mudeopen_d_categoria fk_mudeopen_d_categoria_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_categoria
    ADD CONSTRAINT fk_mudeopen_d_categoria_01 FOREIGN KEY (id_regime) REFERENCES mudeopen_d_regime_giuridico(id_regime);


--
-- Name: mudeopen_d_comune fk_mudeopen_d_comune_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_comune
    ADD CONSTRAINT fk_mudeopen_d_comune_01 FOREIGN KEY (id_provincia) REFERENCES mudeopen_d_provincia(id_provincia);


--
-- Name: mudeopen_d_elemento_elenco fk_mudeopen_d_elemento_elenco_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_elemento_elenco
    ADD CONSTRAINT fk_mudeopen_d_elemento_elenco_01 FOREIGN KEY (id_tipo_elenco) REFERENCES mudeopen_d_tipo_elenco(id_tipo_elenco);


--
-- Name: mudeopen_d_funzione fk_mudeopen_d_funzione_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_funzione
    ADD CONSTRAINT fk_mudeopen_d_funzione_01 FOREIGN KEY (id_categoria_quadro) REFERENCES mudeopen_d_categoria_quadro(id_categoria_quadro);


--
-- Name: mudeopen_d_nazione fk_mudeopen_d_nazione_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_nazione
    ADD CONSTRAINT fk_mudeopen_d_nazione_01 FOREIGN KEY (id_user) REFERENCES mudeopen_t_user(id_user);


--
-- Name: mudeopen_d_opera fk_mudeopen_d_opera_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_opera
    ADD CONSTRAINT fk_mudeopen_d_opera_01 FOREIGN KEY (id_elemento) REFERENCES mudeopen_d_elemento(id_elemento);


--
-- Name: mudeopen_d_opera fk_mudeopen_d_opera_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_opera
    ADD CONSTRAINT fk_mudeopen_d_opera_02 FOREIGN KEY (id_categoria) REFERENCES mudeopen_d_categoria(id_categoria);


--
-- Name: mudeopen_d_ppay_importi fk_mudeopen_d_ppay_importi_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_ppay_importi
    ADD CONSTRAINT fk_mudeopen_d_ppay_importi_01 FOREIGN KEY (id_importo_default) REFERENCES mudeopen_d_ppay_importi(id_importo);


--
-- Name: mudeopen_d_ppay_importi fk_mudeopen_d_ppay_importi_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_ppay_importi
    ADD CONSTRAINT fk_mudeopen_d_ppay_importi_02 FOREIGN KEY (id_ente) REFERENCES mudeopen_t_ente(id_ente);


--
-- Name: mudeopen_d_ppay_importi fk_mudeopen_d_ppay_importi_04; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_ppay_importi
    ADD CONSTRAINT fk_mudeopen_d_ppay_importi_04 FOREIGN KEY (id_tipo_istanza) REFERENCES mudeopen_d_tipo_istanza(codice);


--
-- Name: mudeopen_d_ppay_importi fk_mudeopen_d_ppay_importi_05; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_ppay_importi
    ADD CONSTRAINT fk_mudeopen_d_ppay_importi_05 FOREIGN KEY (id_specie_pratica) REFERENCES mudeopen_d_specie_pratica(codice);


--
-- Name: mudeopen_d_provincia fk_mudeopen_d_provincia_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_provincia
    ADD CONSTRAINT fk_mudeopen_d_provincia_01 FOREIGN KEY (id_regione) REFERENCES mudeopen_d_regione(id_regione);


--
-- Name: mudeopen_d_quadro fk_mudeopen_d_quadro_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_quadro
    ADD CONSTRAINT fk_mudeopen_d_quadro_01 FOREIGN KEY (id_modello_documentale) REFERENCES mudeopen_t_modello(id_modello);


--
-- Name: mudeopen_d_quadro fk_mudeopen_d_quadro_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_quadro
    ADD CONSTRAINT fk_mudeopen_d_quadro_02 FOREIGN KEY (id_tipo_quadro) REFERENCES mudeopen_d_tipo_quadro(id_tipo_quadro);


--
-- Name: mudeopen_d_regione fk_mudeopen_d_regione_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_regione
    ADD CONSTRAINT fk_mudeopen_d_regione_01 FOREIGN KEY (id_nazione) REFERENCES mudeopen_d_nazione(id_nazione);


--
-- Name: mudeopen_d_task_quadro fk_mudeopen_d_task_quadro; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_task_quadro
    ADD CONSTRAINT fk_mudeopen_d_task_quadro FOREIGN KEY (id_quadro) REFERENCES mudeopen_d_quadro(id_quadro);


--
-- Name: mudeopen_d_template fk_mudeopen_d_template_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_template
    ADD CONSTRAINT fk_mudeopen_d_template_01 FOREIGN KEY (id_tipo_istanza) REFERENCES mudeopen_d_tipo_istanza(codice);


--
-- Name: mudeopen_d_template fk_mudeopen_d_template_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_template
    ADD CONSTRAINT fk_mudeopen_d_template_02 FOREIGN KEY (id_modello_documentale) REFERENCES mudeopen_t_modello(id_modello);


--
-- Name: mudeopen_d_template fk_mudeopen_d_template_03; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_template
    ADD CONSTRAINT fk_mudeopen_d_template_03 FOREIGN KEY (id_modello_intestazione) REFERENCES mudeopen_t_modello(id_modello);


--
-- Name: mudeopen_d_tipo_intervento_paesaggistica fk_mudeopen_d_tipo_intervento_paesaggistica_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_tipo_intervento_paesaggistica
    ADD CONSTRAINT fk_mudeopen_d_tipo_intervento_paesaggistica_01 FOREIGN KEY (id_tipologia_tipo_intervento_paesaggistica) REFERENCES mudeopen_d_tipologia_tipo_intervento_paesaggistica(codice);


--
-- Name: mudeopen_d_tipo_quadro fk_mudeopen_d_tipo_quadro_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_tipo_quadro
    ADD CONSTRAINT fk_mudeopen_d_tipo_quadro_01 FOREIGN KEY (id_categoria_quadro) REFERENCES mudeopen_d_categoria_quadro(id_categoria_quadro);


--
-- Name: mudeopen_d_comune fk_mudeopen_d_tipo_quadro_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_comune
    ADD CONSTRAINT fk_mudeopen_d_tipo_quadro_02 FOREIGN KEY (rischio_sismico) REFERENCES mudeopen_d_cat_rischio_sismico(codice);


--
-- Name: mudeopen_d_tipo_quadro fk_mudeopen_d_tipo_quadro_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_tipo_quadro
    ADD CONSTRAINT fk_mudeopen_d_tipo_quadro_02 FOREIGN KEY (id_tipo_quadro) REFERENCES mudeopen_d_tipo_quadro(id_tipo_quadro);


--
-- Name: mudeopen_d_tipo_quadro fk_mudeopen_d_tipo_quadro_03; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_tipo_quadro
    ADD CONSTRAINT fk_mudeopen_d_tipo_quadro_03 FOREIGN KEY (id_tipo_quadro_padre) REFERENCES mudeopen_d_tipo_quadro(id_tipo_quadro);


--
-- Name: mudeopen_d_wf_stato fk_mudeopen_d_wf_stati_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_wf_stato
    ADD CONSTRAINT fk_mudeopen_d_wf_stati_01 FOREIGN KEY (id_tipo_istanza) REFERENCES mudeopen_d_tipo_istanza(codice);


--
-- Name: mudeopen_d_wf_stato fk_mudeopen_d_wf_stati_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_wf_stato
    ADD CONSTRAINT fk_mudeopen_d_wf_stati_02 FOREIGN KEY (codice_stato_start) REFERENCES mudeopen_d_stato_istanza(codice);


--
-- Name: mudeopen_d_wf_stato fk_mudeopen_d_wf_stati_03; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_d_wf_stato
    ADD CONSTRAINT fk_mudeopen_d_wf_stati_03 FOREIGN KEY (codice_stato_end) REFERENCES mudeopen_d_stato_istanza(codice);


--
-- Name: mudeopen_r_abilitazione_funzione fk_mudeopen_r_abilitazione_funzione_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_abilitazione_funzione
    ADD CONSTRAINT fk_mudeopen_r_abilitazione_funzione_01 FOREIGN KEY (id_abilitazione) REFERENCES mudeopen_d_abilitazione(id_abilitazione);


--
-- Name: mudeopen_r_abilitazione_funzione fk_mudeopen_r_abilitazione_funzione_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_abilitazione_funzione
    ADD CONSTRAINT fk_mudeopen_r_abilitazione_funzione_02 FOREIGN KEY (id_funzione) REFERENCES mudeopen_d_funzione(id_funzione);


--
-- Name: mudeopen_r_allegato_moon_ricevuto fk_mudeopen_r_allegato_moon_ricevuto_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_allegato_moon_ricevuto
    ADD CONSTRAINT fk_mudeopen_r_allegato_moon_ricevuto_01 FOREIGN KEY (id_istanza_ext_moon) REFERENCES mudeopen_t_istanza_ext_moon(id_istanza_ext_moon);


--
-- Name: mudeopen_r_comune_fruitore fk_mudeopen_r_comune_fruitore_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_comune_fruitore
    ADD CONSTRAINT fk_mudeopen_r_comune_fruitore_01 FOREIGN KEY (id_comune) REFERENCES mudeopen_d_comune(id_comune);


--
-- Name: mudeopen_r_comune_fruitore fk_mudeopen_r_comune_fruitore_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_comune_fruitore
    ADD CONSTRAINT fk_mudeopen_r_comune_fruitore_02 FOREIGN KEY (id_fruitore) REFERENCES mudeopen_d_fruitore(id_fruitore);


--
-- Name: mudeopen_r_contatto_qualifica fk_mudeopen_r_contatto_qualifica_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_contatto_qualifica
    ADD CONSTRAINT fk_mudeopen_r_contatto_qualifica_01 FOREIGN KEY (id_ordine) REFERENCES mudeopen_d_ordine(codice);


--
-- Name: mudeopen_r_contatto_qualifica fk_mudeopen_r_contatto_qualifica_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_contatto_qualifica
    ADD CONSTRAINT fk_mudeopen_r_contatto_qualifica_02 FOREIGN KEY (id_qualifica) REFERENCES mudeopen_d_qualifica(id_qualifica);


--
-- Name: mudeopen_r_contatto_qualifica fk_mudeopen_r_contatto_qualifica_03; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_contatto_qualifica
    ADD CONSTRAINT fk_mudeopen_r_contatto_qualifica_03 FOREIGN KEY (id_provincia) REFERENCES mudeopen_d_qualifica_collegio(codice);


--
-- Name: mudeopen_r_contatto_qualifica fk_mudeopen_r_contatto_qualifica_04; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_contatto_qualifica
    ADD CONSTRAINT fk_mudeopen_r_contatto_qualifica_04 FOREIGN KEY (id_contatto) REFERENCES mudeopen_t_contatto(id_contatto);


--
-- Name: mudeopen_r_ente_comune_tipo_istanza fk_mudeopen_r_ente_comune_tipo_istanza_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_ente_comune_tipo_istanza
    ADD CONSTRAINT fk_mudeopen_r_ente_comune_tipo_istanza_01 FOREIGN KEY (id_ente) REFERENCES mudeopen_t_ente(id_ente);


--
-- Name: mudeopen_r_ente_comune_tipo_istanza fk_mudeopen_r_ente_comune_tipo_istanza_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_ente_comune_tipo_istanza
    ADD CONSTRAINT fk_mudeopen_r_ente_comune_tipo_istanza_02 FOREIGN KEY (codice_tipo_istanza) REFERENCES mudeopen_d_tipo_istanza(codice);


--
-- Name: mudeopen_r_ente_comune_tipo_istanza fk_mudeopen_r_ente_comune_tipo_istanza_03; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_ente_comune_tipo_istanza
    ADD CONSTRAINT fk_mudeopen_r_ente_comune_tipo_istanza_03 FOREIGN KEY (codice_specie_pratica) REFERENCES mudeopen_d_specie_pratica(codice);


--
-- Name: mudeopen_r_ente_comune_tipo_istanza fk_mudeopen_r_ente_comune_tipo_istanza_04; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_ente_comune_tipo_istanza
    ADD CONSTRAINT fk_mudeopen_r_ente_comune_tipo_istanza_04 FOREIGN KEY (id_comune) REFERENCES mudeopen_d_comune(id_comune);


--
-- Name: mudeopen_r_ente_fruitore fk_mudeopen_r_ente_fruitore_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_ente_fruitore
    ADD CONSTRAINT fk_mudeopen_r_ente_fruitore_01 FOREIGN KEY (id_ente) REFERENCES mudeopen_t_ente(id_ente);


--
-- Name: mudeopen_r_ente_fruitore fk_mudeopen_r_ente_fruitore_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_ente_fruitore
    ADD CONSTRAINT fk_mudeopen_r_ente_fruitore_02 FOREIGN KEY (id_fruitore) REFERENCES mudeopen_d_fruitore(id_fruitore);


--
-- Name: mudeopen_r_fascicolo_indirizzo fk_mudeopen_r_fascicolo_indirizzo_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_fascicolo_indirizzo
    ADD CONSTRAINT fk_mudeopen_r_fascicolo_indirizzo_01 FOREIGN KEY (id_fascicolo) REFERENCES mudeopen_t_fascicolo(id_fascicolo);


--
-- Name: mudeopen_r_fascicolo_indirizzo fk_mudeopen_r_fascicolo_indirizzo_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_fascicolo_indirizzo
    ADD CONSTRAINT fk_mudeopen_r_fascicolo_indirizzo_02 FOREIGN KEY (id_indirizzo) REFERENCES mudeopen_t_indirizzo(id_indirizzo);


--
-- Name: mudeopen_r_fascicolo_intestatario fk_mudeopen_r_fascicolo_intestatario_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_fascicolo_intestatario
    ADD CONSTRAINT fk_mudeopen_r_fascicolo_intestatario_01 FOREIGN KEY (id_fascicolo) REFERENCES mudeopen_t_fascicolo(id_fascicolo);


--
-- Name: mudeopen_r_fascicolo_intestatario fk_mudeopen_r_fascicolo_intestatario_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_fascicolo_intestatario
    ADD CONSTRAINT fk_mudeopen_r_fascicolo_intestatario_02 FOREIGN KEY (id_intestatario) REFERENCES mudeopen_t_contatto(id_contatto);


--
-- Name: mudeopen_r_fascicolo_ruolo fk_mudeopen_r_fascicolo_ruolo__01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_fascicolo_ruolo
    ADD CONSTRAINT fk_mudeopen_r_fascicolo_ruolo__01 FOREIGN KEY (id_fascicolo) REFERENCES mudeopen_t_fascicolo(id_fascicolo);


--
-- Name: mudeopen_r_fascicolo_ruolo fk_mudeopen_r_fascicolo_ruolo__02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_fascicolo_ruolo
    ADD CONSTRAINT fk_mudeopen_r_fascicolo_ruolo__02 FOREIGN KEY (id_user_ins) REFERENCES mudeopen_t_user(id_user);


--
-- Name: mudeopen_r_fascicolo_soggetto fk_mudeopen_r_fascicolo_soggetto_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_fascicolo_soggetto
    ADD CONSTRAINT fk_mudeopen_r_fascicolo_soggetto_01 FOREIGN KEY (id_fascicolo) REFERENCES mudeopen_t_fascicolo(id_fascicolo);


--
-- Name: mudeopen_r_fascicolo_soggetto fk_mudeopen_r_fascicolo_soggetto_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_fascicolo_soggetto
    ADD CONSTRAINT fk_mudeopen_r_fascicolo_soggetto_02 FOREIGN KEY (id_istanza_soggetto) REFERENCES mudeopen_r_istanza_soggetto(id_istanza_soggetto);


--
-- Name: mudeopen_r_fascicolo_soggetto fk_mudeopen_r_fascicolo_soggetto_03; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_fascicolo_soggetto
    ADD CONSTRAINT fk_mudeopen_r_fascicolo_soggetto_03 FOREIGN KEY (id_user_ins) REFERENCES mudeopen_t_user(id_user);


--
-- Name: mudeopen_r_fascicolo_stato fk_mudeopen_r_fascicolo_stato_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_fascicolo_stato
    ADD CONSTRAINT fk_mudeopen_r_fascicolo_stato_01 FOREIGN KEY (id_fascicolo) REFERENCES mudeopen_t_fascicolo(id_fascicolo);


--
-- Name: mudeopen_r_fascicolo_stato fk_mudeopen_r_fascicolo_stato_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_fascicolo_stato
    ADD CONSTRAINT fk_mudeopen_r_fascicolo_stato_02 FOREIGN KEY (codice_stato_fascicolo) REFERENCES mudeopen_d_stato_fascicolo(codice);


--
-- Name: mudeopen_r_fascicolo_utente fk_mudeopen_r_fascicolo_utente_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_fascicolo_utente
    ADD CONSTRAINT fk_mudeopen_r_fascicolo_utente_01 FOREIGN KEY (id_fascicolo) REFERENCES mudeopen_t_fascicolo(id_fascicolo);


--
-- Name: mudeopen_r_fascicolo_utente fk_mudeopen_r_fascicolo_utente_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_fascicolo_utente
    ADD CONSTRAINT fk_mudeopen_r_fascicolo_utente_02 FOREIGN KEY (id_utente) REFERENCES mudeopen_t_user(id_user);


--
-- Name: mudeopen_r_fascicolo_utente fk_mudeopen_r_fascicolo_utente_03; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_fascicolo_utente
    ADD CONSTRAINT fk_mudeopen_r_fascicolo_utente_03 FOREIGN KEY (id_abilitazione) REFERENCES mudeopen_d_abilitazione(id_abilitazione);


--
-- Name: mudeopen_r_fascicolo_utente fk_mudeopen_r_fascicolo_utente_04; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_fascicolo_utente
    ADD CONSTRAINT fk_mudeopen_r_fascicolo_utente_04 FOREIGN KEY (id_abilitatore) REFERENCES mudeopen_t_user(id_user);


--
-- Name: mudeopen_r_istanza_ente fk_mudeopen_r_istanza_ente_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_ente
    ADD CONSTRAINT fk_mudeopen_r_istanza_ente_01 FOREIGN KEY (id_ente) REFERENCES mudeopen_t_ente(id_ente);


--
-- Name: mudeopen_r_istanza_ext_moon_stato fk_mudeopen_r_istanza_ext_moon_stato_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_ext_moon_stato
    ADD CONSTRAINT fk_mudeopen_r_istanza_ext_moon_stato_01 FOREIGN KEY (cod_stato_istanza_ext_moon) REFERENCES mudeopen_d_stato_istanza_ext_moon(cod_stato_istanza_ext_moon);


--
-- Name: mudeopen_r_istanza_ext_moon_stato fk_mudeopen_r_istanza_ext_moon_stato_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_ext_moon_stato
    ADD CONSTRAINT fk_mudeopen_r_istanza_ext_moon_stato_02 FOREIGN KEY (id_istanza_ext_moon) REFERENCES mudeopen_t_istanza_ext_moon(id_istanza_ext_moon);


--
-- Name: mudeopen_r_istanza_pratica fk_mudeopen_r_istanza_pratica__02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_pratica
    ADD CONSTRAINT fk_mudeopen_r_istanza_pratica__02 FOREIGN KEY (id_pratica) REFERENCES mudeopen_t_pratica(id_pratica);


--
-- Name: mudeopen_r_istanza_quadro_utente fk_mudeopen_r_istanza_quadro_utente_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_quadro_utente
    ADD CONSTRAINT fk_mudeopen_r_istanza_quadro_utente_02 FOREIGN KEY (id_quadro) REFERENCES mudeopen_d_quadro(id_quadro);


--
-- Name: mudeopen_r_istanza_quadro_utente fk_mudeopen_r_istanza_quadro_utente_03; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_quadro_utente
    ADD CONSTRAINT fk_mudeopen_r_istanza_quadro_utente_03 FOREIGN KEY (id_utente) REFERENCES mudeopen_t_user(id_user);


--
-- Name: mudeopen_r_istanza_soggetto fk_mudeopen_r_istanza_soggetto_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_soggetto
    ADD CONSTRAINT fk_mudeopen_r_istanza_soggetto_02 FOREIGN KEY (id_soggetto) REFERENCES mudeopen_t_contatto(id_contatto);


--
-- Name: mudeopen_r_istanza_soggetto_ruoli fk_mudeopen_r_istanza_soggetto_ruoli_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_soggetto_ruoli
    ADD CONSTRAINT fk_mudeopen_r_istanza_soggetto_ruoli_01 FOREIGN KEY (id_istanza_soggetto) REFERENCES mudeopen_r_istanza_soggetto(id_istanza_soggetto);


--
-- Name: mudeopen_r_istanza_soggetto_ruoli fk_mudeopen_r_istanza_soggetto_ruoli_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_soggetto_ruoli
    ADD CONSTRAINT fk_mudeopen_r_istanza_soggetto_ruoli_02 FOREIGN KEY (ruoli) REFERENCES mudeopen_d_ruolo_soggetto(codice);


--
-- Name: mudeopen_r_istanza_stato fk_mudeopen_r_istanza_stato_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_stato
    ADD CONSTRAINT fk_mudeopen_r_istanza_stato_02 FOREIGN KEY (codice_stato_istanza) REFERENCES mudeopen_d_stato_istanza(codice);


--
-- Name: mudeopen_r_istanza_tipo_opera fk_mudeopen_r_istanza_tipo_opera_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_tipo_opera
    ADD CONSTRAINT fk_mudeopen_r_istanza_tipo_opera_01 FOREIGN KEY (id_tipo_opera) REFERENCES mudeopen_d_tipo_opera(codice);


--
-- Name: mudeopen_r_istanza_tipo_opera fk_mudeopen_r_istanza_tipo_opera_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_tipo_opera
    ADD CONSTRAINT fk_mudeopen_r_istanza_tipo_opera_02 FOREIGN KEY (id_istanza) REFERENCES mudeopen_t_istanza(id_istanza);


--
-- Name: mudeopen_r_istanza_tracciato fk_mudeopen_r_istanza_tracciato_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_tracciato
    ADD CONSTRAINT fk_mudeopen_r_istanza_tracciato_01 FOREIGN KEY (id_istanza) REFERENCES mudeopen_t_istanza(id_istanza);


--
-- Name: mudeopen_r_istanza_tracciato fk_mudeopen_r_istanza_tracciato_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_tracciato
    ADD CONSTRAINT fk_mudeopen_r_istanza_tracciato_02 FOREIGN KEY (id_tipo_tracciato) REFERENCES mudeopen_d_tipo_tracciato(id_tipo_tracciato);


--
-- Name: mudeopen_r_istanza_tracciato fk_mudeopen_r_istanza_tracciato_03; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_tracciato
    ADD CONSTRAINT fk_mudeopen_r_istanza_tracciato_03 FOREIGN KEY (user_id) REFERENCES mudeopen_t_user(id_user);


--
-- Name: mudeopen_r_istanza_utente fk_mudeopen_r_istanza_utente_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_utente
    ADD CONSTRAINT fk_mudeopen_r_istanza_utente_02 FOREIGN KEY (id_utente) REFERENCES mudeopen_t_user(id_user);


--
-- Name: mudeopen_r_istanza_utente fk_mudeopen_r_istanza_utente_03; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_utente
    ADD CONSTRAINT fk_mudeopen_r_istanza_utente_03 FOREIGN KEY (id_abilitazione) REFERENCES mudeopen_d_abilitazione(id_abilitazione);


--
-- Name: mudeopen_r_istanza_utente fk_mudeopen_r_istanza_utente_04; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_utente
    ADD CONSTRAINT fk_mudeopen_r_istanza_utente_04 FOREIGN KEY (id_abilitatore) REFERENCES mudeopen_t_user(id_user);


--
-- Name: mudeopen_r_istanza_utente_quadro fk_mudeopen_r_istanza_utente_quadro_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_utente_quadro
    ADD CONSTRAINT fk_mudeopen_r_istanza_utente_quadro_01 FOREIGN KEY (id_istanza_utente) REFERENCES mudeopen_r_istanza_utente(id_istanza_utente);


--
-- Name: mudeopen_r_istanza_utente_quadro fk_mudeopen_r_istanza_utente_quadro_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_utente_quadro
    ADD CONSTRAINT fk_mudeopen_r_istanza_utente_quadro_02 FOREIGN KEY (id_quadro) REFERENCES mudeopen_d_quadro(id_quadro);


--
-- Name: mudeopen_r_istanza_utente_quadro fk_mudeopen_r_istanza_utente_quadro_03; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_utente_quadro
    ADD CONSTRAINT fk_mudeopen_r_istanza_utente_quadro_03 FOREIGN KEY (id_funzione) REFERENCES mudeopen_d_funzione(id_funzione);


--
-- Name: mudeopen_r_istanza_utente_quadro fk_mudeopen_r_istanza_utente_quadro_04; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_istanza_utente_quadro
    ADD CONSTRAINT fk_mudeopen_r_istanza_utente_quadro_04 FOREIGN KEY (id_abilitazione) REFERENCES mudeopen_d_abilitazione(id_abilitazione);


--
-- Name: mudeopen_r_notifica_contatto fk_mudeopen_r_notifica_contatto_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_notifica_contatto
    ADD CONSTRAINT fk_mudeopen_r_notifica_contatto_01 FOREIGN KEY (id_notifica) REFERENCES mudeopen_t_notifica(id_notifica);


--
-- Name: mudeopen_r_notifica_contatto fk_mudeopen_r_notifica_contatto_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_notifica_contatto
    ADD CONSTRAINT fk_mudeopen_r_notifica_contatto_02 FOREIGN KEY (id_contatto) REFERENCES mudeopen_t_contatto(id_contatto);


--
-- Name: mudeopen_r_notifica_documento fk_mudeopen_r_notifica_documento_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_notifica_documento
    ADD CONSTRAINT fk_mudeopen_r_notifica_documento_01 FOREIGN KEY (id_notifica) REFERENCES mudeopen_t_notifica(id_notifica);


--
-- Name: mudeopen_r_notifica_documento fk_mudeopen_r_notifica_documento_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_notifica_documento
    ADD CONSTRAINT fk_mudeopen_r_notifica_documento_02 FOREIGN KEY (id_documento) REFERENCES mudeopen_t_documento(id_documento);


--
-- Name: mudeopen_r_notifica_utente fk_mudeopen_r_notifica_utente_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_notifica_utente
    ADD CONSTRAINT fk_mudeopen_r_notifica_utente_01 FOREIGN KEY (id_notifica) REFERENCES mudeopen_t_notifica(id_notifica);


--
-- Name: mudeopen_r_notifica_utente fk_mudeopen_r_notifica_utente_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_notifica_utente
    ADD CONSTRAINT fk_mudeopen_r_notifica_utente_02 FOREIGN KEY (id_utente) REFERENCES mudeopen_t_user(id_user);


--
-- Name: mudeopen_r_pf_pg fk_mudeopen_r_pf_pg_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_pf_pg
    ADD CONSTRAINT fk_mudeopen_r_pf_pg_01 FOREIGN KEY (id_soggetto_pf) REFERENCES mudeopen_t_contatto(id_contatto);


--
-- Name: mudeopen_r_pf_pg fk_mudeopen_r_pf_pg_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_pf_pg
    ADD CONSTRAINT fk_mudeopen_r_pf_pg_02 FOREIGN KEY (id_soggetto_pg) REFERENCES mudeopen_t_contatto(id_contatto);


--
-- Name: mudeopen_r_pf_pg fk_mudeopen_r_pf_pg_03; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_pf_pg
    ADD CONSTRAINT fk_mudeopen_r_pf_pg_03 FOREIGN KEY (id_titolo) REFERENCES mudeopen_d_titolo(codice);


--
-- Name: mudeopen_r_ruolo_fruitore fk_mudeopen_r_ruolo_fruitore_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_ruolo_fruitore
    ADD CONSTRAINT fk_mudeopen_r_ruolo_fruitore_01 FOREIGN KEY (codice_ruolo) REFERENCES mudeopen_d_ruolo_utente(codice);


--
-- Name: mudeopen_r_ruolo_fruitore fk_mudeopen_r_ruolo_fruitore_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_ruolo_fruitore
    ADD CONSTRAINT fk_mudeopen_r_ruolo_fruitore_02 FOREIGN KEY (id_fruitore) REFERENCES mudeopen_d_fruitore(id_fruitore);


--
-- Name: mudeopen_r_ruolo_funzione fk_mudeopen_r_ruolo_funz_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_ruolo_funzione
    ADD CONSTRAINT fk_mudeopen_r_ruolo_funz_01 FOREIGN KEY (codice_ruolo_utente) REFERENCES mudeopen_d_ruolo_utente(codice);


--
-- Name: mudeopen_r_ruolo_funzione fk_mudeopen_r_ruolo_funz_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_ruolo_funzione
    ADD CONSTRAINT fk_mudeopen_r_ruolo_funz_02 FOREIGN KEY (id_funzione) REFERENCES mudeopen_d_funzione(id_funzione);


--
-- Name: mudeopen_r_sism_class_opere fk_mudeopen_r_sism_class_opere_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_sism_class_opere
    ADD CONSTRAINT fk_mudeopen_r_sism_class_opere_01 FOREIGN KEY (id_denuncia_sism) REFERENCES mudeopen_t_sism_denuncia(id_denuncia_sism);


--
-- Name: mudeopen_r_sism_class_opere fk_mudeopen_r_sism_class_opere_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_sism_class_opere
    ADD CONSTRAINT fk_mudeopen_r_sism_class_opere_02 FOREIGN KEY (id_class_opera) REFERENCES mudeopen_d_elemento_elenco(id_elemento_elenco);


--
-- Name: mudeopen_r_sism_istanze_rif fk_mudeopen_r_sism_istanze_rif_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_sism_istanze_rif
    ADD CONSTRAINT fk_mudeopen_r_sism_istanze_rif_01 FOREIGN KEY (id_denuncia_sism) REFERENCES mudeopen_t_sism_denuncia(id_denuncia_sism);


--
-- Name: mudeopen_r_sism_istanze_rif fk_mudeopen_r_sism_istanze_rif_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_sism_istanze_rif
    ADD CONSTRAINT fk_mudeopen_r_sism_istanze_rif_02 FOREIGN KEY (istanza_rif_id) REFERENCES mudeopen_t_istanza(id_istanza);


--
-- Name: mudeopen_r_sism_rel_ill_norma fk_mudeopen_r_sism_rel_ill_norma_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_sism_rel_ill_norma
    ADD CONSTRAINT fk_mudeopen_r_sism_rel_ill_norma_01 FOREIGN KEY (id_sism_rel_ill) REFERENCES mudeopen_t_sism_rel_ill(id_sism_rel_ill);


--
-- Name: mudeopen_r_sism_rel_ill_norma fk_mudeopen_r_sism_rel_ill_norma_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_sism_rel_ill_norma
    ADD CONSTRAINT fk_mudeopen_r_sism_rel_ill_norma_02 FOREIGN KEY (id_sism_norma) REFERENCES mudeopen_d_sism_norma(id_sism_norma);


--
-- Name: mudeopen_r_specie_pratica_tipo_intervento_paesaggistica fk_mudeopen_r_specie_pratica_tipo_interv_paesaggistica_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_specie_pratica_tipo_intervento_paesaggistica
    ADD CONSTRAINT fk_mudeopen_r_specie_pratica_tipo_interv_paesaggistica_01 FOREIGN KEY (id_specie_pratica) REFERENCES mudeopen_d_specie_pratica(codice);


--
-- Name: mudeopen_r_specie_pratica_tipo_intervento_paesaggistica fk_mudeopen_r_specie_pratica_tipo_interv_paesaggistica_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_specie_pratica_tipo_intervento_paesaggistica
    ADD CONSTRAINT fk_mudeopen_r_specie_pratica_tipo_interv_paesaggistica_02 FOREIGN KEY (id_tipo_intervento_paesaggistica) REFERENCES mudeopen_d_tipo_intervento_paesaggistica(codice);


--
-- Name: mudeopen_r_specie_pratica_tipo_intervento fk_mudeopen_r_specie_pratica_tipo_intervento_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_specie_pratica_tipo_intervento
    ADD CONSTRAINT fk_mudeopen_r_specie_pratica_tipo_intervento_01 FOREIGN KEY (id_specie_pratica) REFERENCES mudeopen_d_specie_pratica(codice);


--
-- Name: mudeopen_r_specie_pratica_tipo_intervento fk_mudeopen_r_specie_pratica_tipo_intervento_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_specie_pratica_tipo_intervento
    ADD CONSTRAINT fk_mudeopen_r_specie_pratica_tipo_intervento_02 FOREIGN KEY (id_tipo_intervento) REFERENCES mudeopen_d_tipo_intervento(codice);


--
-- Name: mudeopen_r_specie_pratica_tipo_opera fk_mudeopen_r_specie_pratica_tipo_opera_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_specie_pratica_tipo_opera
    ADD CONSTRAINT fk_mudeopen_r_specie_pratica_tipo_opera_01 FOREIGN KEY (id_tipo_opera) REFERENCES mudeopen_d_tipo_opera(codice);


--
-- Name: mudeopen_r_specie_pratica_tipo_opera fk_mudeopen_r_specie_pratica_tipo_opera_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_specie_pratica_tipo_opera
    ADD CONSTRAINT fk_mudeopen_r_specie_pratica_tipo_opera_02 FOREIGN KEY (id_specie_pratica) REFERENCES mudeopen_d_specie_pratica(codice);


--
-- Name: mudeopen_r_template_quadro fk_mudeopen_r_template_quadro_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_template_quadro
    ADD CONSTRAINT fk_mudeopen_r_template_quadro_01 FOREIGN KEY (id_quadro) REFERENCES mudeopen_d_quadro(id_quadro);


--
-- Name: mudeopen_r_template_quadro fk_mudeopen_r_template_quadro_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_template_quadro
    ADD CONSTRAINT fk_mudeopen_r_template_quadro_02 FOREIGN KEY (id_template) REFERENCES mudeopen_d_template(id_template);


--
-- Name: mudeopen_r_template_tipo_allegato fk_mudeopen_r_template_tipo_allegato_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_template_tipo_allegato
    ADD CONSTRAINT fk_mudeopen_r_template_tipo_allegato_01 FOREIGN KEY (id_modello_documentale) REFERENCES mudeopen_t_modello(id_modello);


--
-- Name: mudeopen_r_template_tipo_allegato fk_mudeopen_r_template_tipo_allegato_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_template_tipo_allegato
    ADD CONSTRAINT fk_mudeopen_r_template_tipo_allegato_02 FOREIGN KEY (id_tipo_allegato) REFERENCES mudeopen_d_tipo_allegato(codice);


--
-- Name: mudeopen_r_template_tipo_allegato fk_mudeopen_r_template_tipo_allegato_03; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_template_tipo_allegato
    ADD CONSTRAINT fk_mudeopen_r_template_tipo_allegato_03 FOREIGN KEY (id_template) REFERENCES mudeopen_d_template(id_template);


--
-- Name: mudeopen_r_tipo_istanza fk_mudeopen_r_tipo_istanza_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza
    ADD CONSTRAINT fk_mudeopen_r_tipo_istanza_01 FOREIGN KEY (cod_tipo_istanza_padre) REFERENCES mudeopen_d_tipo_istanza(codice);


--
-- Name: mudeopen_r_tipo_istanza fk_mudeopen_r_tipo_istanza_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza
    ADD CONSTRAINT fk_mudeopen_r_tipo_istanza_02 FOREIGN KEY (cod_tipo_istanza_figlia) REFERENCES mudeopen_d_tipo_istanza(codice);


--
-- Name: mudeopen_r_tipo_istanza_fruitore fk_mudeopen_r_tipo_istanza_fruitore_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza_fruitore
    ADD CONSTRAINT fk_mudeopen_r_tipo_istanza_fruitore_01 FOREIGN KEY (id_tipo_istanza) REFERENCES mudeopen_d_tipo_istanza(codice);


--
-- Name: mudeopen_r_tipo_istanza_fruitore fk_mudeopen_r_tipo_istanza_fruitore_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza_fruitore
    ADD CONSTRAINT fk_mudeopen_r_tipo_istanza_fruitore_02 FOREIGN KEY (id_fruitore) REFERENCES mudeopen_d_fruitore(id_fruitore);


--
-- Name: mudeopen_r_tipo_istanza_regime_g_regime_a fk_mudeopen_r_tipo_istanza_regime_g_regime_a_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza_regime_g_regime_a
    ADD CONSTRAINT fk_mudeopen_r_tipo_istanza_regime_g_regime_a_01 FOREIGN KEY (id_tipo_istanza) REFERENCES mudeopen_d_tipo_istanza(codice);


--
-- Name: mudeopen_r_tipo_istanza_regime_g_regime_a fk_mudeopen_r_tipo_istanza_regime_g_regime_a_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza_regime_g_regime_a
    ADD CONSTRAINT fk_mudeopen_r_tipo_istanza_regime_g_regime_a_02 FOREIGN KEY (id_regime_aggiuntivo) REFERENCES mudeopen_d_regime_giuridico_aggiuntivo(id_regime);


--
-- Name: mudeopen_r_tipo_istanza_regime_g_regime_a fk_mudeopen_r_tipo_istanza_regime_g_regime_a_03; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza_regime_g_regime_a
    ADD CONSTRAINT fk_mudeopen_r_tipo_istanza_regime_g_regime_a_03 FOREIGN KEY (id_regime_giuridico) REFERENCES mudeopen_d_regime_giuridico(id_regime);


--
-- Name: mudeopen_r_tipo_istanza_ruolo_sog fk_mudeopen_r_tipo_istanza_ruolo_sog_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza_ruolo_sog
    ADD CONSTRAINT fk_mudeopen_r_tipo_istanza_ruolo_sog_01 FOREIGN KEY (id_tipo_istanza) REFERENCES mudeopen_d_tipo_istanza(codice);


--
-- Name: mudeopen_r_tipo_istanza_ruolo_sog fk_mudeopen_r_tipo_istanza_ruolo_sog_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza_ruolo_sog
    ADD CONSTRAINT fk_mudeopen_r_tipo_istanza_ruolo_sog_02 FOREIGN KEY (id_ruolo_soggetto) REFERENCES mudeopen_d_ruolo_soggetto(codice);


--
-- Name: mudeopen_r_tipo_istanza_specie_pratica fk_mudeopen_r_tipo_istanza_specie_pratica_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza_specie_pratica
    ADD CONSTRAINT fk_mudeopen_r_tipo_istanza_specie_pratica_01 FOREIGN KEY (id_tipo_istanza) REFERENCES mudeopen_d_tipo_istanza(codice);


--
-- Name: mudeopen_r_tipo_istanza_specie_pratica fk_mudeopen_r_tipo_istanza_specie_pratica_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza_specie_pratica
    ADD CONSTRAINT fk_mudeopen_r_tipo_istanza_specie_pratica_02 FOREIGN KEY (id_specie_pratica) REFERENCES mudeopen_d_specie_pratica(codice);


--
-- Name: mudeopen_r_tipo_istanza_stato fk_mudeopen_r_tipo_istanza_stato_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza_stato
    ADD CONSTRAINT fk_mudeopen_r_tipo_istanza_stato_01 FOREIGN KEY (id_tipo_istanza) REFERENCES mudeopen_d_tipo_istanza(codice);


--
-- Name: mudeopen_r_tipo_istanza_stato fk_mudeopen_r_tipo_istanza_stato_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza_stato
    ADD CONSTRAINT fk_mudeopen_r_tipo_istanza_stato_02 FOREIGN KEY (codice_stato_istanza) REFERENCES mudeopen_d_stato_istanza(codice);


--
-- Name: mudeopen_r_tipo_istanza_tipo_intervento fk_mudeopen_r_tipo_istanza_tipo_intervento_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza_tipo_intervento
    ADD CONSTRAINT fk_mudeopen_r_tipo_istanza_tipo_intervento_01 FOREIGN KEY (codice_tipo_intervento) REFERENCES mudeopen_d_tipo_intervento(codice);


--
-- Name: mudeopen_r_tipo_istanza_tipo_intervento fk_mudeopen_r_tipo_istanza_tipo_intervento_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza_tipo_intervento
    ADD CONSTRAINT fk_mudeopen_r_tipo_istanza_tipo_intervento_02 FOREIGN KEY (codice_tipo_istanza) REFERENCES mudeopen_d_tipo_istanza(codice);


--
-- Name: mudeopen_r_tipo_istanza_tipo_opera_diretta fk_mudeopen_r_tipo_istanza_tipo_opera_diretta_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza_tipo_opera_diretta
    ADD CONSTRAINT fk_mudeopen_r_tipo_istanza_tipo_opera_diretta_01 FOREIGN KEY (id_tipo_istanza) REFERENCES mudeopen_d_tipo_istanza(codice);


--
-- Name: mudeopen_r_tipo_istanza_tipo_opera_diretta fk_mudeopen_r_tipo_istanza_tipo_opera_diretta_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza_tipo_opera_diretta
    ADD CONSTRAINT fk_mudeopen_r_tipo_istanza_tipo_opera_diretta_02 FOREIGN KEY (id_tipo_opera) REFERENCES mudeopen_d_tipo_opera(codice);


--
-- Name: mudeopen_r_tipo_istanza_tipo_tracciato fk_mudeopen_r_tipo_istanza_tipo_tracciato_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_tipo_istanza_tipo_tracciato
    ADD CONSTRAINT fk_mudeopen_r_tipo_istanza_tipo_tracciato_01 FOREIGN KEY (id_tipo_istanza) REFERENCES mudeopen_d_tipo_istanza(codice);


--
-- Name: mudeopen_r_utente_ente fk_mudeopen_r_utente_ente_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_utente_ente
    ADD CONSTRAINT fk_mudeopen_r_utente_ente_01 FOREIGN KEY (id_ente) REFERENCES mudeopen_t_ente(id_ente);


--
-- Name: mudeopen_r_utente_ente fk_mudeopen_r_utente_ente_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_utente_ente
    ADD CONSTRAINT fk_mudeopen_r_utente_ente_02 FOREIGN KEY (id_utente) REFERENCES mudeopen_t_user(id_user);


--
-- Name: mudeopen_r_utente_ente_comune_ruolo fk_mudeopen_r_utente_ente_comune_ruolo_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_utente_ente_comune_ruolo
    ADD CONSTRAINT fk_mudeopen_r_utente_ente_comune_ruolo_01 FOREIGN KEY (id_ente) REFERENCES mudeopen_t_ente(id_ente);


--
-- Name: mudeopen_r_utente_ente_comune_ruolo fk_mudeopen_r_utente_ente_comune_ruolo_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_utente_ente_comune_ruolo
    ADD CONSTRAINT fk_mudeopen_r_utente_ente_comune_ruolo_02 FOREIGN KEY (id_comune) REFERENCES mudeopen_d_comune(id_comune);


--
-- Name: mudeopen_r_utente_ente_comune_ruolo fk_mudeopen_r_utente_ente_comune_ruolo_03; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_utente_ente_comune_ruolo
    ADD CONSTRAINT fk_mudeopen_r_utente_ente_comune_ruolo_03 FOREIGN KEY (id_utente) REFERENCES mudeopen_t_user(id_user);


--
-- Name: mudeopen_r_utente_ente_comune_ruolo fk_mudeopen_r_utente_ente_comune_ruolo_04; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_utente_ente_comune_ruolo
    ADD CONSTRAINT fk_mudeopen_r_utente_ente_comune_ruolo_04 FOREIGN KEY (cod_ruolo) REFERENCES mudeopen_d_ruolo_utente(codice);


--
-- Name: mudeopen_r_utente_ruolo fk_mudeopen_r_utente_ruolo_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_utente_ruolo
    ADD CONSTRAINT fk_mudeopen_r_utente_ruolo_01 FOREIGN KEY (id_utente) REFERENCES mudeopen_t_user(id_user);


--
-- Name: mudeopen_r_utente_ruolo fk_mudeopen_r_utente_ruolo_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_r_utente_ruolo
    ADD CONSTRAINT fk_mudeopen_r_utente_ruolo_02 FOREIGN KEY (codice_ruolo_utente) REFERENCES mudeopen_d_ruolo_utente(codice);


--
-- Name: mudeopen_t_allegato fk_mudeopen_t_allegato_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_allegato
    ADD CONSTRAINT fk_mudeopen_t_allegato_01 FOREIGN KEY (id_tipo_allegato) REFERENCES mudeopen_d_tipo_allegato(codice);


--
-- Name: mudeopen_t_allegato fk_mudeopen_t_allegato_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_allegato
    ADD CONSTRAINT fk_mudeopen_t_allegato_02 FOREIGN KEY (id_user) REFERENCES mudeopen_t_user(id_user);


--
-- Name: mudeopen_t_allegato fk_mudeopen_t_allegato_03; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_allegato
    ADD CONSTRAINT fk_mudeopen_t_allegato_03 FOREIGN KEY (id_istanza) REFERENCES mudeopen_t_istanza(id_istanza);


--
-- Name: mudeopen_t_contatto fk_mudeopen_t_contatto_04; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_contatto
    ADD CONSTRAINT fk_mudeopen_t_contatto_04 FOREIGN KEY (id_provincia_nascita) REFERENCES mudeopen_d_provincia(id_provincia);


--
-- Name: mudeopen_t_contatto fk_mudeopen_t_contatto_05; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_contatto
    ADD CONSTRAINT fk_mudeopen_t_contatto_05 FOREIGN KEY (id_stato_nascita) REFERENCES mudeopen_d_nazione(id_nazione);


--
-- Name: mudeopen_t_contatto fk_mudeopen_t_contatto_06; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_contatto
    ADD CONSTRAINT fk_mudeopen_t_contatto_06 FOREIGN KEY (id_user) REFERENCES mudeopen_t_user(id_user);


--
-- Name: mudeopen_t_contatto fk_mudeopen_t_contatto_07; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_contatto
    ADD CONSTRAINT fk_mudeopen_t_contatto_07 FOREIGN KEY (id_stato_membro) REFERENCES mudeopen_d_nazione(id_nazione);


--
-- Name: mudeopen_t_contatto fk_mudeopen_t_contatto_08; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_contatto
    ADD CONSTRAINT fk_mudeopen_t_contatto_08 FOREIGN KEY (id_comune_nascita) REFERENCES mudeopen_d_comune(id_comune);


--
-- Name: mudeopen_t_dati_istanza fk_mudeopen_t_dati_istanza_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_dati_istanza
    ADD CONSTRAINT fk_mudeopen_t_dati_istanza_01 FOREIGN KEY (id_soggetto) REFERENCES mudeopen_t_contatto(id_contatto);


--
-- Name: mudeopen_t_documento fk_mudeopen_t_documento_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_documento
    ADD CONSTRAINT fk_mudeopen_t_documento_01 FOREIGN KEY (id_tipo_documento) REFERENCES mudeopen_d_tipo_docpa(id_tipo_docpa);


--
-- Name: mudeopen_t_documento fk_mudeopen_t_documento_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_documento
    ADD CONSTRAINT fk_mudeopen_t_documento_02 FOREIGN KEY (id_pratica) REFERENCES mudeopen_t_pratica(id_pratica);


--
-- Name: mudeopen_t_ente fk_mudeopen_t_ente_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_ente
    ADD CONSTRAINT fk_mudeopen_t_ente_01 FOREIGN KEY (id_comune) REFERENCES mudeopen_d_comune(id_comune);


--
-- Name: mudeopen_t_ente fk_mudeopen_t_ente_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_ente
    ADD CONSTRAINT fk_mudeopen_t_ente_02 FOREIGN KEY (id_tipo_ente) REFERENCES mudeopen_d_tipo_ente(codice);


--
-- Name: mudeopen_t_fascicolo fk_mudeopen_t_fascicolo_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_fascicolo
    ADD CONSTRAINT fk_mudeopen_t_fascicolo_01 FOREIGN KEY (id_tipo_istanza) REFERENCES mudeopen_d_tipo_istanza(codice);


--
-- Name: mudeopen_t_fascicolo fk_mudeopen_t_fascicolo_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_fascicolo
    ADD CONSTRAINT fk_mudeopen_t_fascicolo_02 FOREIGN KEY (id_indirizzo) REFERENCES mudeopen_t_indirizzo(id_indirizzo);


--
-- Name: mudeopen_t_fascicolo fk_mudeopen_t_fascicolo_03; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_fascicolo
    ADD CONSTRAINT fk_mudeopen_t_fascicolo_03 FOREIGN KEY (id_comune) REFERENCES mudeopen_d_comune(id_comune);


--
-- Name: mudeopen_t_fascicolo fk_mudeopen_t_fascicolo_04; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_fascicolo
    ADD CONSTRAINT fk_mudeopen_t_fascicolo_04 FOREIGN KEY (id_tipo_intervento) REFERENCES mudeopen_d_tipo_intervento(codice);


--
-- Name: mudeopen_t_fascicolo fk_mudeopen_t_fascicolo_05; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_fascicolo
    ADD CONSTRAINT fk_mudeopen_t_fascicolo_05 FOREIGN KEY (id_user) REFERENCES mudeopen_t_user(id_user);


--
-- Name: mudeopen_t_impersonate fk_mudeopen_t_impersonate_mudeopen_t_user; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_impersonate
    ADD CONSTRAINT fk_mudeopen_t_impersonate_mudeopen_t_user FOREIGN KEY (id_user_sostituito) REFERENCES mudeopen_t_user(id_user);


--
-- Name: mudeopen_t_indirizzo fk_mudeopen_t_indirizzo_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_indirizzo
    ADD CONSTRAINT fk_mudeopen_t_indirizzo_01 FOREIGN KEY (id_nazione) REFERENCES mudeopen_d_nazione(id_nazione);


--
-- Name: mudeopen_t_indirizzo fk_mudeopen_t_indirizzo_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_indirizzo
    ADD CONSTRAINT fk_mudeopen_t_indirizzo_02 FOREIGN KEY (id_comune) REFERENCES mudeopen_d_comune(id_comune);


--
-- Name: mudeopen_t_indirizzo fk_mudeopen_t_indirizzo_03; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_indirizzo
    ADD CONSTRAINT fk_mudeopen_t_indirizzo_03 FOREIGN KEY (id_contatto) REFERENCES mudeopen_t_contatto(id_contatto);


--
-- Name: mudeopen_t_istanza fk_mudeopen_t_istanza_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_istanza
    ADD CONSTRAINT fk_mudeopen_t_istanza_01 FOREIGN KEY (id_tipo_istanza) REFERENCES mudeopen_d_tipo_istanza(codice);


--
-- Name: mudeopen_t_istanza fk_mudeopen_t_istanza_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_istanza
    ADD CONSTRAINT fk_mudeopen_t_istanza_02 FOREIGN KEY (id_indirizzo) REFERENCES mudeopen_t_indirizzo(id_indirizzo);


--
-- Name: mudeopen_t_istanza fk_mudeopen_t_istanza_03; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_istanza
    ADD CONSTRAINT fk_mudeopen_t_istanza_03 FOREIGN KEY (id_specie_pratica) REFERENCES mudeopen_d_specie_pratica(codice);


--
-- Name: mudeopen_t_istanza fk_mudeopen_t_istanza_04; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_istanza
    ADD CONSTRAINT fk_mudeopen_t_istanza_04 FOREIGN KEY (id_comune) REFERENCES mudeopen_d_comune(id_comune);


--
-- Name: mudeopen_t_istanza fk_mudeopen_t_istanza_05; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_istanza
    ADD CONSTRAINT fk_mudeopen_t_istanza_05 FOREIGN KEY (id_istanza_riferimento) REFERENCES mudeopen_t_istanza(id_istanza);


--
-- Name: mudeopen_t_istanza fk_mudeopen_t_istanza_07; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_istanza
    ADD CONSTRAINT fk_mudeopen_t_istanza_07 FOREIGN KEY (id_fascicolo) REFERENCES mudeopen_t_fascicolo(id_fascicolo);


--
-- Name: mudeopen_t_istanza fk_mudeopen_t_istanza_09; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_istanza
    ADD CONSTRAINT fk_mudeopen_t_istanza_09 FOREIGN KEY (id_user) REFERENCES mudeopen_t_user(id_user);


--
-- Name: mudeopen_t_istanza fk_mudeopen_t_istanza_10; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_istanza
    ADD CONSTRAINT fk_mudeopen_t_istanza_10 FOREIGN KEY (id_fonte) REFERENCES mudeopen_d_fonte(id_fonte);


--
-- Name: mudeopen_t_istanza fk_mudeopen_t_istanza_11; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_istanza
    ADD CONSTRAINT fk_mudeopen_t_istanza_11 FOREIGN KEY (id_fruitore) REFERENCES mudeopen_d_fruitore(id_fruitore);


--
-- Name: mudeopen_t_istanze_ext fk_mudeopen_t_istanza_ext_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_istanze_ext
    ADD CONSTRAINT fk_mudeopen_t_istanza_ext_01 FOREIGN KEY (id_user_ins) REFERENCES mudeopen_t_user(id_user);


--
-- Name: mudeopen_t_istanza_ext_moon fk_mudeopen_t_istanza_ext_moon_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_istanza_ext_moon
    ADD CONSTRAINT fk_mudeopen_t_istanza_ext_moon_01 FOREIGN KEY (cod_stato_istanza_ext_moon) REFERENCES mudeopen_d_stato_istanza_ext_moon(cod_stato_istanza_ext_moon);


--
-- Name: mudeopen_t_log_numeri_mude fk_mudeopen_t_log_numeri_mude; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_log_numeri_mude
    ADD CONSTRAINT fk_mudeopen_t_log_numeri_mude FOREIGN KEY (id_fruitore) REFERENCES mudeopen_d_fruitore(id_fruitore);


--
-- Name: mudeopen_t_log_tracciato fk_mudeopen_t_log_tracciato_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_log_tracciato
    ADD CONSTRAINT fk_mudeopen_t_log_tracciato_01 FOREIGN KEY (id_tipo_tracciato) REFERENCES mudeopen_d_tipo_tracciato(id_tipo_tracciato);


--
-- Name: mudeopen_t_log_tracciato fk_mudeopen_t_log_tracciato_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_log_tracciato
    ADD CONSTRAINT fk_mudeopen_t_log_tracciato_02 FOREIGN KEY (user_id) REFERENCES mudeopen_t_user(id_user);


--
-- Name: mudeopen_t_notifica fk_mudeopen_t_notifica_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_notifica
    ADD CONSTRAINT fk_mudeopen_t_notifica_01 FOREIGN KEY (id_tipo_notifica) REFERENCES mudeopen_d_tipo_notifica(id_tipo_notifica);


--
-- Name: mudeopen_t_notifica fk_mudeopen_t_notifica_03; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_notifica
    ADD CONSTRAINT fk_mudeopen_t_notifica_03 FOREIGN KEY (id_user_mittente) REFERENCES mudeopen_t_user(id_user);


--
-- Name: mudeopen_t_pratica fk_mudeopen_t_pratica__01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_pratica
    ADD CONSTRAINT fk_mudeopen_t_pratica__01 FOREIGN KEY (id_ente) REFERENCES mudeopen_t_ente(id_ente);


--
-- Name: mudeopen_t_pratica_cosmo fk_mudeopen_t_pratica_cosmo_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_pratica_cosmo
    ADD CONSTRAINT fk_mudeopen_t_pratica_cosmo_02 FOREIGN KEY (id_istanza) REFERENCES mudeopen_t_istanza(id_istanza);


--
-- Name: mudeopen_t_pratica_cosmo fk_mudeopen_t_pratica_cosmo_03; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_pratica_cosmo
    ADD CONSTRAINT fk_mudeopen_t_pratica_cosmo_03 FOREIGN KEY (tipo_controllo) REFERENCES mudeopen_d_controllo_cosmo(codice);


--
-- Name: mudeopen_t_pratica_cosmo fk_mudeopen_t_pratica_cosmo_04; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_pratica_cosmo
    ADD CONSTRAINT fk_mudeopen_t_pratica_cosmo_04 FOREIGN KEY (codice_stato_cosmo) REFERENCES mudeopen_d_stato_cosmo(codice);


--
-- Name: mudeopen_t_pratica_idf fk_mudeopen_t_pratica_idf_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_pratica_idf
    ADD CONSTRAINT fk_mudeopen_t_pratica_idf_01 FOREIGN KEY (id_istanza) REFERENCES mudeopen_t_istanza(id_istanza);


--
-- Name: mudeopen_t_pratica_idf fk_mudeopen_t_pratica_idf_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_pratica_idf
    ADD CONSTRAINT fk_mudeopen_t_pratica_idf_02 FOREIGN KEY (codice_stato_idf) REFERENCES mudeopen_d_stato_idf(codice);


--
-- Name: mudeopen_t_sism_costr_es fk_mudeopen_t_sism_costr_es_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_costr_es
    ADD CONSTRAINT fk_mudeopen_t_sism_costr_es_01 FOREIGN KEY (id_sism_rel_ill) REFERENCES mudeopen_t_sism_rel_ill(id_sism_rel_ill);


--
-- Name: mudeopen_t_sism_costr_es fk_mudeopen_t_sism_costr_es_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_costr_es
    ADD CONSTRAINT fk_mudeopen_t_sism_costr_es_02 FOREIGN KEY (int_id_tipo) REFERENCES mudeopen_d_sism_int_tipo(codice);


--
-- Name: mudeopen_t_sism_costr_es fk_mudeopen_t_sism_costr_es_03; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_costr_es
    ADD CONSTRAINT fk_mudeopen_t_sism_costr_es_03 FOREIGN KEY (int_id_par) REFERENCES mudeopen_d_sism_int_par(codice);


--
-- Name: mudeopen_t_sism_costr_es fk_mudeopen_t_sism_costr_es_04; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_costr_es
    ADD CONSTRAINT fk_mudeopen_t_sism_costr_es_04 FOREIGN KEY (costr_id_tipo) REFERENCES mudeopen_d_sism_costr_tipo(codice);


--
-- Name: mudeopen_t_sism_costr_es fk_mudeopen_t_sism_costr_es_05; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_costr_es
    ADD CONSTRAINT fk_mudeopen_t_sism_costr_es_05 FOREIGN KEY (costr_id_classe_uso) REFERENCES mudeopen_d_sism_classe_uso(codice);


--
-- Name: mudeopen_t_sism_denuncia fk_mudeopen_t_sism_denuncia_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_denuncia
    ADD CONSTRAINT fk_mudeopen_t_sism_denuncia_01 FOREIGN KEY (id_titolo_riferimento) REFERENCES mudeopen_t_istanza(id_istanza);


--
-- Name: mudeopen_t_sism_denuncia fk_mudeopen_t_sism_denuncia_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_denuncia
    ADD CONSTRAINT fk_mudeopen_t_sism_denuncia_02 FOREIGN KEY (id_denuncia_riferimento) REFERENCES mudeopen_t_istanza(id_istanza);


--
-- Name: mudeopen_t_sism_denuncia fk_mudeopen_t_sism_denuncia_03; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_denuncia
    ADD CONSTRAINT fk_mudeopen_t_sism_denuncia_03 FOREIGN KEY (id_istanza) REFERENCES mudeopen_t_istanza(id_istanza);


--
-- Name: mudeopen_t_sism_denuncia fk_mudeopen_t_sism_denuncia_04; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_denuncia
    ADD CONSTRAINT fk_mudeopen_t_sism_denuncia_04 FOREIGN KEY (zona_sismica_comune) REFERENCES mudeopen_d_cat_rischio_sismico(codice);


--
-- Name: mudeopen_t_sism_denuncia fk_mudeopen_t_sism_denuncia_05; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_denuncia
    ADD CONSTRAINT fk_mudeopen_t_sism_denuncia_05 FOREIGN KEY (id_destinazione_d_uso) REFERENCES mudeopen_d_destinazione_d_uso(codice);


--
-- Name: mudeopen_t_sism_muri_sost fk_mudeopen_t_sism_muri_sost_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_muri_sost
    ADD CONSTRAINT fk_mudeopen_t_sism_muri_sost_01 FOREIGN KEY (id_sism_rel_ill) REFERENCES mudeopen_t_sism_rel_ill(id_sism_rel_ill);


--
-- Name: mudeopen_t_sism_muri_sost fk_mudeopen_t_sism_muri_sost_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_muri_sost
    ADD CONSTRAINT fk_mudeopen_t_sism_muri_sost_02 FOREIGN KEY (int_id_tipo) REFERENCES mudeopen_d_sism_int_tipo(codice);


--
-- Name: mudeopen_t_sism_muri_sost fk_mudeopen_t_sism_muri_sost_03; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_muri_sost
    ADD CONSTRAINT fk_mudeopen_t_sism_muri_sost_03 FOREIGN KEY (int_id_par) REFERENCES mudeopen_d_sism_int_par(codice);


--
-- Name: mudeopen_t_sism_muri_sost fk_mudeopen_t_sism_muri_sost_04; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_muri_sost
    ADD CONSTRAINT fk_mudeopen_t_sism_muri_sost_04 FOREIGN KEY (costr_id_tipo) REFERENCES mudeopen_d_sism_costr_tipo(codice);


--
-- Name: mudeopen_t_sism_muri_sost fk_mudeopen_t_sism_muri_sost_05; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_muri_sost
    ADD CONSTRAINT fk_mudeopen_t_sism_muri_sost_05 FOREIGN KEY (costr_id_classe_uso) REFERENCES mudeopen_d_sism_classe_uso(codice);


--
-- Name: mudeopen_t_sism_nuova_costr fk_mudeopen_t_sism_nuova_costr_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_nuova_costr
    ADD CONSTRAINT fk_mudeopen_t_sism_nuova_costr_01 FOREIGN KEY (id_sism_rel_ill) REFERENCES mudeopen_t_sism_rel_ill(id_sism_rel_ill);


--
-- Name: mudeopen_t_sism_nuova_costr fk_mudeopen_t_sism_nuova_costr_04; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_nuova_costr
    ADD CONSTRAINT fk_mudeopen_t_sism_nuova_costr_04 FOREIGN KEY (costr_id_tipo) REFERENCES mudeopen_d_sism_costr_tipo(codice);


--
-- Name: mudeopen_t_sism_nuova_costr fk_mudeopen_t_sism_nuova_costr_05; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_nuova_costr
    ADD CONSTRAINT fk_mudeopen_t_sism_nuova_costr_05 FOREIGN KEY (costr_id_classe_uso) REFERENCES mudeopen_d_sism_classe_uso(codice);


--
-- Name: mudeopen_t_sism_ponti_viad fk_mudeopen_t_sism_ponti_viad_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_ponti_viad
    ADD CONSTRAINT fk_mudeopen_t_sism_ponti_viad_01 FOREIGN KEY (id_sism_rel_ill) REFERENCES mudeopen_t_sism_rel_ill(id_sism_rel_ill);


--
-- Name: mudeopen_t_sism_ponti_viad fk_mudeopen_t_sism_ponti_viad_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_ponti_viad
    ADD CONSTRAINT fk_mudeopen_t_sism_ponti_viad_02 FOREIGN KEY (int_id_tipo) REFERENCES mudeopen_d_sism_int_tipo(codice);


--
-- Name: mudeopen_t_sism_ponti_viad fk_mudeopen_t_sism_ponti_viad_03; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_ponti_viad
    ADD CONSTRAINT fk_mudeopen_t_sism_ponti_viad_03 FOREIGN KEY (int_id_par) REFERENCES mudeopen_d_sism_int_par(codice);


--
-- Name: mudeopen_t_sism_ponti_viad fk_mudeopen_t_sism_ponti_viad_04; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_ponti_viad
    ADD CONSTRAINT fk_mudeopen_t_sism_ponti_viad_04 FOREIGN KEY (costr_id_tipo) REFERENCES mudeopen_d_sism_costr_tipo(codice);


--
-- Name: mudeopen_t_sism_ponti_viad fk_mudeopen_t_sism_ponti_viad_05; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_ponti_viad
    ADD CONSTRAINT fk_mudeopen_t_sism_ponti_viad_05 FOREIGN KEY (costr_id_classe_uso) REFERENCES mudeopen_d_sism_classe_uso(codice);


--
-- Name: mudeopen_t_sism_rel_ill fk_mudeopen_t_sism_rel_ill_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_rel_ill
    ADD CONSTRAINT fk_mudeopen_t_sism_rel_ill_01 FOREIGN KEY (id_denuncia_sism) REFERENCES mudeopen_t_sism_denuncia(id_denuncia_sism);


--
-- Name: mudeopen_t_sism_rel_ill fk_mudeopen_t_sism_rel_ill_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_rel_ill
    ADD CONSTRAINT fk_mudeopen_t_sism_rel_ill_02 FOREIGN KEY (id_istanza) REFERENCES mudeopen_t_istanza(id_istanza);


--
-- Name: mudeopen_t_sism_rel_ill fk_mudeopen_t_sism_rel_ill_03; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_rel_ill
    ADD CONSTRAINT fk_mudeopen_t_sism_rel_ill_03 FOREIGN KEY (id_tipo_edifici_strat_reg) REFERENCES mudeopen_d_elemento_elenco(id_elemento_elenco);


--
-- Name: mudeopen_t_sism_rel_ill fk_mudeopen_t_sism_rel_ill_04; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_rel_ill
    ADD CONSTRAINT fk_mudeopen_t_sism_rel_ill_04 FOREIGN KEY (id_tipo_opere_strat_reg) REFERENCES mudeopen_d_elemento_elenco(id_elemento_elenco);


--
-- Name: mudeopen_t_sism_rel_ill fk_mudeopen_t_sism_rel_ill_05; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_rel_ill
    ADD CONSTRAINT fk_mudeopen_t_sism_rel_ill_05 FOREIGN KEY (id_tipo_opere_ril_reg) REFERENCES mudeopen_d_elemento_elenco(id_elemento_elenco);


--
-- Name: mudeopen_t_sism_rel_ill fk_mudeopen_t_sism_rel_ill_06; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_sism_rel_ill
    ADD CONSTRAINT fk_mudeopen_t_sism_rel_ill_06 FOREIGN KEY (id_tipo_edifici_ril_reg) REFERENCES mudeopen_d_elemento_elenco(id_elemento_elenco);


--
-- Name: mudeopen_t_user fk_mudeopen_t_user_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_user
    ADD CONSTRAINT fk_mudeopen_t_user_01 FOREIGN KEY (id_provincia_nascita) REFERENCES mudeopen_d_provincia(id_provincia);


--
-- Name: mudeopen_t_user fk_mudeopen_t_user_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_user
    ADD CONSTRAINT fk_mudeopen_t_user_02 FOREIGN KEY (id_stato_nascita) REFERENCES mudeopen_d_nazione(id_nazione);


--
-- Name: mudeopen_t_user fk_mudeopen_t_user_03; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_t_user
    ADD CONSTRAINT fk_mudeopen_t_user_03 FOREIGN KEY (id_comune_nascita) REFERENCES mudeopen_d_comune(id_comune);


--
-- Name: mudeopen_c_proprieta_ente mudeopen_c_proprieta_ente_01; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_c_proprieta_ente
    ADD CONSTRAINT mudeopen_c_proprieta_ente_01 FOREIGN KEY (id_ente) REFERENCES mudeopen_t_ente(id_ente);


--
-- Name: mudeopen_c_proprieta_ente mudeopen_c_proprieta_ente_02; Type: FK CONSTRAINT; Schema: mudeopen; Owner: mudeopen
--

ALTER TABLE ONLY mudeopen_c_proprieta_ente
    ADD CONSTRAINT mudeopen_c_proprieta_ente_02 FOREIGN KEY (codice_tipo_istanza) REFERENCES mudeopen_d_tipo_istanza(codice);



--
--  DDL FUNCTION
--
CREATE OR REPLACE FUNCTION fnc_assign_cosmo_tipo_controllo(idistanza bigint, justcheck boolean DEFAULT false)
 RETURNS boolean
 LANGUAGE plpgsql
AS $function$
DECLARE
    esito VARCHAR := 'ko';
    v_error_stack text;
    idDenunciaSism int8;
    idSismRel int8;
    tipoControllo varchar;
    tipoControlloCostrEx bool := false;
    opereRilevanzaNaz bool := false;
    controlloCampione bool := false;
    justCheckResult bool := false;
BEGIN

    RAISE NOTICE E'\nidIstanza=%', idIstanza;

    -- opere rilevanza nazionale
    SELECT COUNT(id_istanza) > 0 
        INTO opereRilevanzaNaz
        FROM mudeopen_t_istanza mti 
        WHERE id_istanza = idIstanza
            AND (
                    (json_data->'TAB_DEN_SIS_QUALIF_5'->'strategico'->>'edificio_opera_strategico' = 'si' 
                        AND json_data->'TAB_DEN_SIS_QUALIF_5'->'strategico'->>'tipo_strategico' = 'edificio_opera_strat_naz')
                    OR
                    (json_data->'TAB_DEN_SIS_QUALIF_5'->'rilevante'->>'edificio_opera_rilevante' = 'si' 
                        AND json_data->'TAB_DEN_SIS_QUALIF_5'->'rilevante'->>'tipo_interesse_rilevante' = 'edificio_opera_ril_naz')
                );

    -- tipo di controllo: PA/CF
    SELECT CASE WHEN COUNT(id_istanza) = 0 OR opereRilevanzaNaz
                THEN 'PA' 
                ELSE 'CF' 
            END 
        INTO tipoControllo 
        FROM mudeopen_t_istanza mti
        WHERE mti.id_istanza=idIstanza
            AND (
                    json_data->'TAB_DEN_SIS_QUALIF_5'->'strategico'->>'tipo_strategico' = 'edifici_strat_reg'
                    OR (
                        json_data->'TAB_DEN_SIS_QUALIF_5'->'strategico'->>'tipo_strategico' = 'opere_strat_reg'
                            AND json_data->'TAB_DEN_SIS_QUALIF_5'->'strategico'->'tipo_opere_strat_reg'->>'id' IN ('EL00000086', 'EL00000087', 'EL00000088')
                    )
                    OR json_data->'TAB_DEN_SIS_QUALIF_5'->'strategico'->>'tipo_interesse_rilevante' = 'opere_ril_reg'
                    OR (
                        json_data->'TAB_DEN_SIS_QUALIF_5'->'rilevante'->>'tipo_interesse_rilevante' = 'edifici_ril_reg'
                            AND json_data->'TAB_DEN_SIS_QUALIF_5'->'rilevante'->'tipo_edifici_ril_reg'->>'id' IN ('EL00000093', 'EL00000094', 'EL00000095', 'EL00000096', 'EL00000097', 'EL00000098')
                    )
                );

    -- "tipo intervento" costruzioni esistenti
    for i in 0..4 loop
        if not tipoControlloCostrEx then
            SELECT COUNT(id_istanza)>0 
                INTO tipoControlloCostrEx 
                FROM mudeopen_t_istanza mti 
                WHERE id_istanza=idIstanza 
                    AND REPLACE((json_data #>> ARRAY['TAB_DS_DTS_INTERVENTO_X3', 'container0x'||i, 'int_id_tipo', 'id']), ',', '.')::varchar IN ('2', '3');
    
            RAISE NOTICE 'tipo intervento[%]: %', i, tipoControlloCostrEx;
        end if;
    end loop;

    -- "geom_copert_m": Altezza maggiore o ugule a 12 metri fuori terra misurati all’imposta della copertura
    for i in 0..4 loop
        if not controlloCampione then
            SELECT COUNT(id_istanza)>0 
                INTO controlloCampione 
                FROM mudeopen_t_istanza mti 
                WHERE id_istanza=idIstanza 
                    AND REPLACE((json_data #>> ARRAY['TAB_DS_DTS_GEOMETRIA_X2', 'container0x'||i, 'geom_copert_m']), ',', '.')::float8 >= (SELECT COALESCE(MAX(valore)::float8, 12) FROM mudeopen_c_proprieta mcp WHERE nome = 'DS_CC_ALTEZZA_MINIMA');

            RAISE NOTICE 'geom_copert_m[%]: %', i, controlloCampione;
        end if;
    end loop;
    
    -- "geom_sup_max": Superficie di piano maggiore o uguale al 1500 metri quadri.
    for i in 0..4 loop
        if not controlloCampione then
            SELECT COUNT(id_istanza)>0 
                INTO controlloCampione 
                FROM mudeopen_t_istanza mti 
                WHERE id_istanza=idIstanza 
                    AND REPLACE((json_data #>> ARRAY['TAB_DS_DTS_GEOMETRIA_X2', 'container0x'||i, 'geom_sup_max']), ',', '.')::float8 >= (SELECT COALESCE(MAX(valore)::float8, 1500) FROM mudeopen_c_proprieta mcp WHERE nome = 'DS_CC_DIMENSIONE_MINIMA');

            RAISE NOTICE 'geom_sup_max[%]: %', i, controlloCampione;
        end if;
    end loop;
    
    -- "geom_camp_luce_m": luce tra appoggi 40 m
    for i in 0..4 loop
        if not controlloCampione then
            SELECT COUNT(id_istanza)>0 
                INTO controlloCampione 
                FROM mudeopen_t_istanza mti 
                WHERE id_istanza=idIstanza 
                    AND json_data #>> ARRAY['TAB_DS_DTS_TIPO_PONTE', 'container0x'||i, 'tipologiaPonte'] = 'tipo_pv_nuovo'
                    AND REPLACE((json_data #>> ARRAY['TAB_DS_DTS_GEOMETRIA_PV', 'container0x'||i, 'geom_camp_luce_m']), ',', '.')::float8 >= (SELECT COALESCE(MAX(valore)::float8, 40) FROM mudeopen_c_proprieta mcp WHERE nome = 'DS_CC_LUCE_MINIMA');

            RAISE NOTICE 'geom_camp_luce_m[%]: %', i, controlloCampione;
        end if;
    end loop;

    -- "geom_num_piani_int": Altezza maggiore o uguale a 5 metri dall’estradosso delle strutture di fondazione
    for i in 0..4 loop
        if not controlloCampione then
            SELECT COUNT(id_istanza)>0 
                INTO controlloCampione 
                FROM mudeopen_t_istanza mti 
                WHERE id_istanza=idIstanza 
                    AND json_data #>> ARRAY['TAB_DS_DTS_TIPO_MURO', 'container0x'||i, 'tipologiaMuro'] = 'tipo_muro_nuovo'
                    AND REPLACE((json_data #>> ARRAY['TAB_DS_DTS_GEOMETRIA_MS', 'container0x'||i, 'geom_num_piani_int']), ',', '.')::float8 >= (SELECT COALESCE(MAX(valore)::float8, 5) FROM mudeopen_c_proprieta mcp WHERE nome = 'DS_CC_INTERRATO_MINIMO');

            RAISE NOTICE 'geom_num_piani_int[%]: %', i, controlloCampione;
        end if;
    end loop;
    
    RAISE NOTICE E'UPDATE mudeopen_t_pratica_cosmo[%] \n\ttipo_controllo=%\n\tcontrollo_campione=% (parametriDimensionali=%, adeguamento-miglioramento=%)', idIstanza, tipoControllo, ((controlloCampione OR tipoControlloCostrEx) AND not(tipoControllo = 'PA')), controlloCampione, tipoControlloCostrEx;

    if justcheck then
        SELECT  count(id_istanza) > 0
            INTO justCheckResult
            FROM mudeopen_t_pratica_cosmo
            WHERE id_istanza=idIstanza
                --AND tipo_controllo = 'PA' AND controllo_campione = false;
                AND tipo_controllo = tipoControllo AND controllo_campione = ((controlloCampione OR tipoControlloCostrEx) AND not(tipoControllo = 'PA'));

        RAISE NOTICE E'justCheckResult[%]=%', idIstanza, justCheckResult;

        RETURN justCheckResult;
    else
        UPDATE mudeopen_t_pratica_cosmo
            SET tipo_controllo=tipoControllo, controllo_campione = (controlloCampione OR tipoControlloCostrEx) AND not(tipoControllo = 'PA') 
            WHERE id_istanza=idIstanza;
    end if;
    
    RETURN TRUE;


EXCEPTION 
  WHEN OTHERS THEN
    GET STACKED DIAGNOSTICS v_error_stack = PG_EXCEPTION_CONTEXT;
    RAISE NOTICE '% EXCEPTION: %', esito, v_error_stack;
    RETURN FALSE;
END;
$function$
;

CREATE OR REPLACE FUNCTION fnc_checkexpression(istanza record, specie record, speciechk record, istanza_rif record, istanzarifchk record, exprstring character varying)
 RETURNS SETOF boolean
 LANGUAGE plpgsql
 IMMUTABLE
AS $function$declare
  recSpecie mudeopen_d_specie_pratica%ROWTYPE;
  istanzaRif mudeopen_t_istanza%ROWTYPE;
  res bool;
  v_error_stack text;
BEGIN
  if specieChk is null then
    SELECT * INTO recSpecie FROM mudeopen_d_specie_pratica WHERE codice = '';
  else
    recSpecie := specie;
  end if;
  if istanzaRifChk is null then
    SELECT * INTO istanzaRif FROM mudeopen_t_istanza WHERE id_istanza = -1;
  else
    istanzaRif := istanza_rif;
  end if;
 EXECUTE  'SELECT ''' || COALESCE(REPLACE(exprString, '''', ''), '') || ''' = '''' OR ' || COALESCE(REPLACE(REPLACE(REPLACE(exprString, 'istanza_rif.', '$3.'), 'specie.', '$2.'), 'istanza.', '$1.'), 'false') INTO res USING istanza, recSpecie, istanzaRif, exprString;
  IF res IS NULL THEN
      res := false;
  END IF;
  RETURN QUERY EXECUTE  'SELECT ' || res;
EXCEPTION 
  WHEN OTHERS THEN
    RETURN QUERY EXECUTE  'SELECT FALSE';
END$function$
;

CREATE OR REPLACE FUNCTION fnc_copy_ds_data(idistanza integer)
 RETURNS boolean
 LANGUAGE plpgsql
AS $function$
DECLARE
    v_log_stack text := '';
    v_error1_stack text;
    v_error2_stack text;
    v_error3_stack text;
    v_error4_stack text;
    idSismRel float;
    idDenunciaSism float;
    idRelIll float;

    tableRow RECORD;
BEGIN

    -----------------------------
    -- clean up prev records
    -----------------------------
    
    -- search prev idDenunciaSism if exists
    SELECT id_denuncia_sism INTO idDenunciaSism FROM  mudeopen_t_sism_denuncia WHERE id_istanza = idIstanza;
    IF idDenunciaSism IS NOT NULL THEN
        v_log_stack = v_log_stack || FORMAT('CLEAN UP... \n');

        DELETE FROM mudeopen_r_sism_rel_ill_norma WHERE id_sism_rel_ill IN (SELECT id_sism_rel_ill FROM mudeopen_t_sism_denuncia WHERE id_denuncia_sism = idDenunciaSism);

        DELETE FROM mudeopen_t_sism_nuova_costr WHERE id_sism_rel_ill IN (SELECT id_sism_rel_ill FROM mudeopen_t_sism_denuncia WHERE id_denuncia_sism = idDenunciaSism);
        DELETE FROM mudeopen_t_sism_costr_es WHERE id_sism_rel_ill IN (SELECT id_sism_rel_ill FROM mudeopen_t_sism_denuncia WHERE id_denuncia_sism = idDenunciaSism);
        DELETE FROM mudeopen_t_sism_ponti_viad WHERE id_sism_rel_ill IN (SELECT id_sism_rel_ill FROM mudeopen_t_sism_denuncia WHERE id_denuncia_sism = idDenunciaSism);
        DELETE FROM mudeopen_t_sism_muri_sost WHERE id_sism_rel_ill IN (SELECT id_sism_rel_ill FROM mudeopen_t_sism_denuncia WHERE id_denuncia_sism = idDenunciaSism);
        DELETE FROM mudeopen_t_sism_rel_ill WHERE id_denuncia_sism = idDenunciaSism;
    
        --DELETE FROM mudeopen_t_geeco_coordinate WHERE id_denuncia_sism = idDenunciaSism;
        DELETE FROM mudeopen_r_sism_istanze_rif WHERE id_denuncia_sism = idDenunciaSism;
        DELETE FROM mudeopen_r_sism_class_opere WHERE id_denuncia_sism = idDenunciaSism;
    
        DELETE FROM mudeopen_t_sism_denuncia WHERE id_denuncia_sism = idDenunciaSism;
    END IF;
    
    -----------------------------
    -- RAISE NOTICE 'UPDATE mudeopen_t_istanze_ext', idIstanza;
    -----------------------------
    v_log_stack = v_log_stack || FORMAT('UPDATE mudeopen_t_istanze_ext \n');

    UPDATE mudeopen_t_istanze_ext mtix SET data_modifica = now()
        , precario = json_data->'TAB_QUALIF_1'->>'opere_in_precario_su_suolo_pubblico' = 'true'
        , descrizione_intervento = json_data->'TAB_QUALIF_2'->>'descrizione_intervento'
        --, id_tipo_intervento = json_data->'TAB_QUALIF_2'->'tipologia_intervento'->>'id'
        , titolo_intervento = json_data->'TAB_QUALIF_2'->'tipologia_intervento'->>'value'
    FROM mudeopen_t_istanza mti 
    WHERE mtix.id_istanza=mti.id_istanza 
        AND mti.id_istanza = idIstanza;
    
    -----------------------------
    -- RAISE NOTICE 'INSERT[%] mudeopen_r_istanza_tipo_opera', idIstanza;
    -----------------------------
    v_log_stack = v_log_stack || FORMAT(E'INSERT mudeopen_r_istanza_tipo_opera \n');

    DELETE FROM mudeopen_r_istanza_tipo_opera WHERE id_istanza = idIstanza;
    INSERT INTO mudeopen_r_istanza_tipo_opera (
        data_modifica,
        id_istanza,
        id_tipo_opera
    ) SELECT now() AS data_modifica
        , idIstanza as id_istanza 
        , opere->'opera'->>'id' AS id_tipo_opera
    FROM mudeopen_t_istanza mti, json_array_elements((mti.json_data->'TAB_QUALIF_3'->>'opere')::json) opere
    WHERE mti.id_istanza = idIstanza;


    -----------------------------
    -- RAISE NOTICE 'INSERT[%] mudeopen_t_sism_denuncia', idIstanza;
    -----------------------------
    v_log_stack = v_log_stack || FORMAT(E'INSERT mudeopen_t_sism_denuncia \n');

    INSERT INTO mudeopen_t_sism_denuncia (
        id_istanza,
        id_destinazione_d_uso,
        note,
        id_titolo_riferimento,
        id_denuncia_riferimento,
        prot_estremi_titolo_rif,
        data_prot_titolo_rif,
        prot_estremi_denuncia_rif,
        data_prot_denuncia_rif,
        zona_sismica_comune,
        dich_soggetta_sue,
        dich_soggetta_sue_num,
        dich_soggetta_sue_prot,
        dich_soggetta_sue_data,
        dich_vinc_idrog,
        dich_vinc_idrog_ril_da,
        dich_vinc_idrog_prot,
        dich_vinc_idrog_data,
        dich_lav_eseguiti,
        dich_lav_eseguiti_den_a,
        dich_lav_eseguit_prot,
        dich_lav_eseguiti_data,
        dich_nec_collaud,
        dich_collaud_nomina,
        dich_collaud_terna,
        dich_collaud_op_pubblica,
        dich_esente_bollo,
        dich_esente_diritti,
        istanze_rif
    ) SELECT idIstanza AS id_istanza
            , du.codice AS  id_destinazione_d_uso
            , mti.json_data->'TAB_QUALIF_4'->>'descrizione_intervento' AS  note
            , mti2.id_istanza AS id_titolo_riferimento
            , mti3.id_istanza AS id_denuncia_riferimento
            , CASE WHEN mti.json_data->'TAB_QUALIF_1'->'container'->>'titolo_autorizzativo_di_riferimento' = 'titolo_digitale_cartaceo' THEN 
                    mti.json_data->'TAB_QUALIF_1'->'container'->>'prot_estremi_titolo_autorizzativo_cartaceo'
                ELSE 
                    NULL
                END AS prot_estremi_titolo_rif
            , CASE WHEN mti.json_data->'TAB_QUALIF_1'->'container'->>'titolo_autorizzativo_di_riferimento' = 'titolo_digitale_cartaceo' THEN 
                    TO_DATE(mti.json_data->'TAB_QUALIF_1'->'container'->>'data_titolo_autorizzativo_cartaceo', 'DD-MM-YYYY')
                ELSE 
                    NULL
                END AS data_prot_titolo_rif
            , CASE WHEN mti.json_data->'TAB_QUALIF_1'->'container1'->>'rif_pratica_caso_variante' = 'den_sis_cartacea' THEN 
                    mti.json_data->'TAB_QUALIF_1'->'container1'->>'prot_estremi_cartaceo'
                ELSE 
                    NULL
                END AS prot_estremi_denuncia_rif
            , CASE WHEN mti.json_data->'TAB_QUALIF_1'->'container1'->>'rif_pratica_caso_variante' = 'den_sis_cartacea' THEN 
                    TO_DATE(mti.json_data->'TAB_QUALIF_1'->'container1'->>'data_cartaceo', 'DD-MM-YYYY')
                ELSE 
                    NULL
                END AS data_prot_denuncia_rif
            , CONCAT('RSISMA', mti.json_data->'TAB_QUALIF_1'->>'zona_sismica_comune') AS  zona_sismica_comune
            , mti.json_data->'TAB_DEN_SIS_ASS'->>'xx_applica' = 'true' AS  dich_soggetta_sue
            , mti.json_data->'TAB_DEN_SIS_ASS'->>'xx_strum_urb_esecutivo' AS  dich_soggetta_sue_num
            , mti.json_data->'TAB_DEN_SIS_ASS'->>'xx_prot_estremi' AS  dich_soggetta_sue_prot
            , TO_DATE(mti.json_data->'TAB_DEN_SIS_ASS'->>'xx_data', 'DD-MM-YYYY') AS  dich_soggetta_sue_data
            , mti.json_data->'TAB_DEN_SIS_ASS'->>'yy_applica' = 'true' AS  dich_vinc_idrog
            , mti.json_data->'TAB_DEN_SIS_ASS'->>'yy_aut_rilasciata' AS  dich_vinc_idrog_ril_da
            , mti.json_data->'TAB_DEN_SIS_ASS'->>'yy_prot_estremi' AS  dich_vinc_idrog_prot
            , TO_DATE(mti.json_data->'TAB_DEN_SIS_ASS'->>'yy_data', 'DD-MM-YYYY') AS  dich_vinc_idrog_data
            , mti.json_data->'TAB_DEN_SIS_ASS'->>'ar_applica' = 'true' AS  dich_lav_eseguiti
            
            , mti.json_data->'TAB_DEN_SIS_ASS'->>'ar_denunciati_a' AS  dich_lav_eseguiti_den_a
            , mti.json_data->'TAB_DEN_SIS_ASS'->>'ar_prot_estremi' AS  dich_lav_eseguit_prot
            , TO_DATE(mti.json_data->'TAB_DEN_SIS_ASS'->>'ar_data', 'DD-MM-YYYY') AS  dich_lav_eseguiti_data
            
            , mti.json_data->'TAB_ASS_AU'->>'rb_collaudatore' = 'necessita_collaudatore' AS  dich_nec_collaud
            , mti.json_data->'TAB_ASS_AU'->>'rb_necess_collaudatore' = 'allega_dich_nomina' AS  dich_collaud_nomina
            , mti.json_data->'TAB_ASS_AU'->>'rb_necess_collaudatore' = 'terna_professionisti' AS  dich_collaud_terna
            , mti.json_data->'TAB_ASS_AU'->>'rb_necess_collaudatore' = 'riconducibile_opera_pubblica' AS  dich_collaud_op_pubblica
            , mti.json_data->'TAB_ASS_AU'->>'esente_imposta_bollo' = 'true' AS  dich_esente_bollo
            , mti.json_data->'TAB_ASS_AU'->>'esente_bollo_istruttoria' = 'true' AS  dich_esente_diritti
            , mti.json_data->'QDR_IST_RIFERIMENTO'->>'applica' = 'true' AS  istanze_rif
    FROM mudeopen_t_istanza mti 
        LEFT JOIN mudeopen_t_istanza mti2 ON mti2.codice_istanza = CASE WHEN mti.json_data->'TAB_QUALIF_1'->'container'->>'titolo_autorizzativo_di_riferimento' = 'titolo_digitale_mude' THEN 
                    mti.json_data->'TAB_QUALIF_1'->'container'->>'elenco_istanze_titolo_edilizio'
                ELSE 
                    NULL
                END
        LEFT JOIN mudeopen_t_istanza mti3 ON mti3.codice_istanza = CASE WHEN mti.json_data->'TAB_QUALIF_1'->'container1'->>'rif_pratica_caso_variante' = 'den_sis_digitale' THEN 
                    mti.json_data->'TAB_QUALIF_1'->'container1'->>'cod_istanza_riferimento_mudeopen'
                ELSE 
                    NULL
                END
        LEFT JOIN mudeopen_d_destinazione_d_uso du ON du.descrizione_estesa = mti.json_data->'TAB_QUALIF_4'->>'destinazione'
    WHERE mti.id_istanza = idIstanza
    --RETURNING idDenunciaSism
    ;

    idDenunciaSism := LASTVAL();


    -----------------------------
    -- RAISE NOTICE 'INSERT[%] mudeopen_r_sism_class_opere', idDenunciaSism;
    -----------------------------
    v_log_stack = v_log_stack || FORMAT(E'INSERT[%s] mudeopen_r_sism_class_opere \n', idDenunciaSism);

    INSERT INTO mudeopen_r_sism_class_opere (
        data_modifica,
        id_denuncia_sism,
        id_class_opera,
        descr_class_opera
    ) SELECT now() AS data_modifica
        , idDenunciaSism as id_denuncia_sism 
        , id_elemento_elenco AS id_class_opera
        , opere->>'altre_specificare' AS descr_class_opera
    FROM mudeopen_t_istanza mti, json_array_elements((mti.json_data->'TAB_DEN_SIS_QUALIF_7'->>'dataGrid')::json) opere, mudeopen_d_elemento_elenco mdee
    WHERE codice = opere->'classificazione_opera'->>'id'
        AND mti.id_istanza = idIstanza;
    
    
    -----------------------------
    -- RAISE NOTICE 'INSERT[%] mudeopen_r_sism_istanze_rif', idIstanza;
    -----------------------------
    v_log_stack = v_log_stack || FORMAT(E'INSERT mudeopen_r_sism_istanze_rif \n');

        FOR tableRow IN SELECT mti.id_istanza, mti.json_data, rifs.position as i
                                 FROM mudeopen_t_istanza mti,
                                     jsonb_array_elements(json_data->'QDR_IST_RIFERIMENTO'->'rifeimento_istanze') with ordinality rifs(item_object, position)
                                WHERE id_istanza = idIstanza
                            LOOP
            if tableRow.json_data->'QDR_IST_RIFERIMENTO'->>'applica' = 'true' AND istanza.json_data->'QDR_IST_RIFERIMENTO'->'rifeimento_istanze' IS NOT NULL then
                INSERT INTO mudeopen_r_sism_istanze_rif (id_denuncia_sism
                    , istanza_rif_id
            
                    , istanza_rif_desc
                    , istanza_rif_prot
                    , istanza_rif_data
        
                ) SELECT id_denuncia_sism AS idDenunciaSism
                        , idIstanza AS istanza_rif_id
        
                        , tableRow.descrizione_istanza AS istanza_rif_desc
                        , tableRow.numero_protocollo_comune AS istanza_rif_prot
                        , TO_DATE(tableRow.data_protocollo_comune, 'DD-MM-YYYY') AS istanza_rif_data
        
                    FROM mudeopen_t_istanza mti 
                    WHERE mti.id_istanza = idIstanza;
            end if;
    END LOOP;

    /*
    --------------------------------
    -- RAISE NOTICE 'INSERT[%] mudeopen_t_geeco_coordinate', idDenunciaSism;
    --------------------------------
    v_log_stack = v_log_stack || FORMAT(E'INSERT[%s] mudeopen_r_sism_class_opere %s\n', idDenunciaSism);

    INSERT INTO mudeopen_t_geeco_coordinate (
        data_modifica,
        long,
        lat,
        geometria
    ) SELECT now() AS data_modifica
        , idDenunciaSism as id_denuncia_sism 
        , json_geo->>'altre_specificare' AS long
        , json_geo->>'altre_specificare' AS lat
        , json_geo->>'altre_specificare' AS descr_class_opera
     FROM mudeopen_t_geeco_session mtgs 
         INNER JOIN mudeopen_t_geeco_selected_cllbk mtgsc ON mtgsc.sessione_geeco = mtgs.sessione_geeco AND callback_result IS NOT NULL 
     WHERE id_istanza  = idIstanza 
     ORDER BY id_session DESC 
     LIMIT 1;
    */
    
    -----------------------------
    -- RAISE NOTICE 'INSERT mudeopen_t_sism_denuncia';
    -----------------------------
    v_log_stack = v_log_stack || FORMAT(E'INSERT mudeopen_t_sism_rel_ill \n');

    INSERT INTO mudeopen_t_sism_rel_ill (id_istanza, id_denuncia_sism
        
        -- TAB_DEN_SIS_QUALIF_5
        , edificio_opera_strategico
            , edificio_opera_strat_naz    
            , edifici_strat_reg
                /* FK */ , id_tipo_edifici_strat_reg
            , opere_strat_reg
                /* FK */ , id_tipo_opere_strat_reg     
        
        , edificio_opera_rilevante
            , edificio_opera_ril_naz
            , edifici_ril_reg
                /* FK */ , id_tipo_edifici_ril_reg
            , opere_ril_reg
                /* FK */ , id_tipo_opere_ril_reg
        
        -- TAB_DS_DTS_PARAMETRI
        , par_slo_ag
        , par_slo_f0
        , par_slo_tc
        , par_sld_ag
        , par_sld_f0
        , par_sld_tc
        , par_slv_ag
        , par_slv_f0
        , par_slv_tc
        , par_slc_ag
        , par_slc_f0
        , par_slc_tc

        -- TAB_DS_DTS_PARAMETRI
        , terreno_prove
            , terreno_param_geo
            , terreno_ang_attrito
            , terreno_coesione
            , terreno_peso_volume
            , terreno_mod_ed
            , terreno_mod_tgl
        , terreno_cat_sott
            , terreno_coeff_ss
            , terreno_cat_sott_n_dov
        , terreno_cat_top
            , terreno_coeff_st
            , terreno_cat_top_n_dov
        
        -- UNUSED!
        , giudizio
        , nuova_costruz
        , data_norma_tecnica
        , norma_tecnica
        , int_costruz_esist
        , column1
        
    ) SELECT idIstanza AS id_istanza, idDenunciaSism AS id_denuncia_sism
                -- TAB_DEN_SIS_QUALIF_5
                , mti.json_data->'TAB_DEN_SIS_QUALIF_5'->'strategico'->>'edificio_opera_strategico' = 'si' AS  edificio_opera_strategico
                    , CASE WHEN mti.json_data->'TAB_DEN_SIS_QUALIF_5'->'strategico'->>'edificio_opera_strategico' = 'si' THEN 
                                                    mti.json_data->'TAB_DEN_SIS_QUALIF_5'->'strategico'->>'tipo_strategico' = 'edificio_opera_strat_naz'
                                                ELSE NULL END AS edificio_opera_strat_naz
                    , CASE WHEN mti.json_data->'TAB_DEN_SIS_QUALIF_5'->'strategico'->>'edificio_opera_strategico' = 'si' THEN 
                                                    mti.json_data->'TAB_DEN_SIS_QUALIF_5'->'strategico'->>'tipo_strategico' = 'edifici_strat_reg'
                                                ELSE NULL END AS edifici_strat_reg
                        , CASE WHEN mti.json_data->'TAB_DEN_SIS_QUALIF_5'->'strategico'->>'edificio_opera_strategico' = 'si' AND mti.json_data->'TAB_DEN_SIS_QUALIF_5'->'strategico'->>'tipo_strategico' = 'edifici_strat_reg' THEN 
                                                        CAST(REPLACE(NULLIf(mti.json_data->'TAB_DEN_SIS_QUALIF_5'->'strategico'->>'id_tipo_edifici_strat_reg', ''), ',', '.') AS float)
                                                    ELSE NULL END AS id_tipo_edifici_strat_reg
                    , CASE WHEN mti.json_data->'TAB_DEN_SIS_QUALIF_5'->'strategico'->>'edificio_opera_strategico' = 'si' THEN 
                                                mti.json_data->'TAB_DEN_SIS_QUALIF_5'->'strategico'->>'tipo_strategico' = 'opere_strat_reg'
                                            ELSE NULL END AS opere_strat_reg
                        , CASE WHEN mti.json_data->'TAB_DEN_SIS_QUALIF_5'->'strategico'->>'edificio_opera_strategico' = 'si' AND mti.json_data->'TAB_DEN_SIS_QUALIF_5'->'strategico'->>'tipo_strategico' = 'opere_strat_reg' THEN 
                                                    CAST(REPLACE(NULLIf(mti.json_data->'TAB_DEN_SIS_QUALIF_5'->'strategico'->>'id_tipo_opere_strat_reg', ''), ',', '.') AS float)
                                                ELSE NULL END AS id_tipo_opere_strat_reg
    
                , mti.json_data->'TAB_DEN_SIS_QUALIF_5'->'rilevante'->>'edificio_opera_rilevante' = 'si' AS  edificio_opera_rilevante
                    , CASE WHEN mti.json_data->'TAB_DEN_SIS_QUALIF_5'->'rilevante'->>'edificio_opera_rilevante' = 'si' THEN 
                                                    mti.json_data->'TAB_DEN_SIS_QUALIF_5'->'rilevante'->>'tipo_edificio_opera_rilevante' = 'edificio_opera_ril_naz'
                                                ELSE NULL END AS edificio_opera_ril_naz
                    , CASE WHEN mti.json_data->'TAB_DEN_SIS_QUALIF_5'->'rilevante'->>'edificio_opera_rilevante' = 'si' THEN 
                                                    mti.json_data->'TAB_DEN_SIS_QUALIF_5'->'rilevante'->>'tipo_edificio_opera_rilevante' = 'edifici_ril_reg'
                                                ELSE NULL END AS edifici_ril_reg
                        , CASE WHEN mti.json_data->'TAB_DEN_SIS_QUALIF_5'->'rilevante'->>'edificio_opera_rilevante' = 'si' AND mti.json_data->'TAB_DEN_SIS_QUALIF_5'->'rilevante'->>'tipo_edificio_opera_rilevante' = 'edifici_ril_reg' THEN 
                                                        CAST(REPLACE(NULLIf(mti.json_data->'TAB_DEN_SIS_QUALIF_5'->'rilevante'->>'id_tipo_edifici_ril_reg', ''), ',', '.') AS float)
                                                    ELSE NULL END AS id_tipo_edifici_ril_reg
                    , CASE WHEN mti.json_data->'TAB_DEN_SIS_QUALIF_5'->'rilevante'->>'edificio_opera_rilevante' = 'si' THEN 
                                                    mti.json_data->'TAB_DEN_SIS_QUALIF_5'->'rilevante'->>'tipo_edificio_opera_rilevante' = 'opere_ril_reg'
                                                ELSE NULL END AS opere_ril_reg
                        , CASE WHEN mti.json_data->'TAB_DEN_SIS_QUALIF_5'->'rilevante'->>'edificio_opera_rilevante' = 'si' AND mti.json_data->'TAB_DEN_SIS_QUALIF_5'->'rilevante'->>'tipo_edificio_opera_rilevante' = 'opere_ril_reg' THEN 
                                                        CAST(REPLACE(NULLIf(mti.json_data->'TAB_DEN_SIS_QUALIF_5'->'rilevante'->>'id_tipo_opere_ril_reg', ''), ',', '.') AS float)
                                                    ELSE NULL END AS id_tipo_opere_ril_reg
                
                -- TAB_DS_DTS_PARAMETRI
                , CAST(REPLACE(NULLIf(mti.json_data->'TAB_DS_DTS_PARAMETRI'->>'par_slo_ag', ''), ',', '.') AS float) AS par_slo_ag
                , CAST(REPLACE(NULLIf(mti.json_data->'TAB_DS_DTS_PARAMETRI'->>'par_slo_f', ''), ',', '.') AS float) AS par_slo_f
                , CAST(REPLACE(NULLIf(mti.json_data->'TAB_DS_DTS_PARAMETRI'->>'par_slo_tc', ''), ',', '.') AS float) AS par_slo_tc
                    
                , CAST(REPLACE(NULLIf(mti.json_data->'TAB_DS_DTS_PARAMETRI'->>'par_sld_ag', ''), ',', '.') AS float) AS par_sld_ag
                , CAST(REPLACE(NULLIf(mti.json_data->'TAB_DS_DTS_PARAMETRI'->>'par_sld_f', ''), ',', '.') AS float) AS par_sld_f
                , CAST(REPLACE(NULLIf(mti.json_data->'TAB_DS_DTS_PARAMETRI'->>'par_sld_tc', ''), ',', '.') AS float) AS par_sld_tc
                    
                , CAST(REPLACE(NULLIf(mti.json_data->'TAB_DS_DTS_PARAMETRI'->>'par_slv_ag', ''), ',', '.') AS float) AS par_slv_ag
                , CAST(REPLACE(NULLIf(mti.json_data->'TAB_DS_DTS_PARAMETRI'->>'par_slv_f', ''), ',', '.') AS float) AS par_slv_f
                , CAST(REPLACE(NULLIf(mti.json_data->'TAB_DS_DTS_PARAMETRI'->>'par_slv_tc', ''), ',', '.') AS float) AS par_slv_tc
                    
                , CAST(REPLACE(NULLIf(mti.json_data->'TAB_DS_DTS_PARAMETRI'->>'par_slc_ag', ''), ',', '.') AS float) AS par_slc_ag
                , CAST(REPLACE(NULLIf(mti.json_data->'TAB_DS_DTS_PARAMETRI'->>'par_slc_f', ''), ',', '.') AS float) AS par_slc_f
                , CAST(REPLACE(NULLIf(mti.json_data->'TAB_DS_DTS_PARAMETRI'->>'par_slc_tc', ''), ',', '.') AS float) AS par_slc_tc
                
                -- TAB_DS_DT_TERRENO
                , mti.json_data->'TAB_DS_DT_TERRENO'->>'terreno_prove' = 'si' AS terreno_prove
                    , SUBSTRING(mti.json_data->'TAB_DS_DT_TERRENO'->>'terreno_param_geo', 1, 3000) AS terreno_param_geo       --varchar(3000)
                    , CAST(REPLACE(NULLIf(mti.json_data->'TAB_DS_DT_TERRENO'->>'terreno_ang_attrito', ''), ',', '.') AS float) AS terreno_ang_attrito  
                    , CAST(REPLACE(NULLIf(mti.json_data->'TAB_DS_DT_TERRENO'->>'terreno_coesione', ''), ',', '.') AS float) AS terreno_coesione
                    , CAST(REPLACE(NULLIf(mti.json_data->'TAB_DS_DT_TERRENO'->>'terreno_peso_volume', ''), ',', '.') AS float) AS terreno_peso_volume
                    , CAST(REPLACE(NULLIf(mti.json_data->'TAB_DS_DT_TERRENO'->>'terreno_mod_ed', ''), ',', '.') AS float) AS terreno_mod_ed
                    , CAST(REPLACE(NULLIf(mti.json_data->'TAB_DS_DT_TERRENO'->>'terreno_mod_tgl', ''), ',', '.') AS float) AS terreno_mod_tgl
                , mti.json_data->'TAB_DS_DT_TERRENO'->>'terreno_cat_sott' AS terreno_cat_sott
                    , CAST(REPLACE(NULLIf(mti.json_data->'TAB_DS_DT_TERRENO'->>'terreno_coeff_ss', ''), ',', '.') AS float) AS terreno_coeff_ss
                    , mti.json_data->'TAB_DS_DT_TERRENO'->>'terreno_cat_sott_n_dov' AS terreno_cat_sott_n_dov
                , mti.json_data->'TAB_DS_DT_TERRENO'->>'terreno_cat_top' AS terreno_cat_top
                    , CAST(REPLACE(NULLIf(mti.json_data->'TAB_DS_DT_TERRENO'->>'terreno_coeff_st', ''), ',', '.') AS float) AS terreno_coeff_st
                    , mti.json_data->'TAB_DS_DT_TERRENO'->>'terreno_cat_top_n_dov' AS terreno_cat_top_n_dov
    
    
            -- UNUSED!
            , null AS giudizio
            , null AS nuova_costruz
            , null AS data_norma_tecnica
            , null AS norma_tecnica
            , null AS int_costruz_esist
            , null AS column1

        FROM mudeopen_t_istanza mti 
        WHERE mti.id_istanza = idIstanza;

    idRelIll := LASTVAL();

    -----------------------------
    -- RAISE NOTICE 'INSERT[%] mudeopen_r_sism_rel_ill_norma', idIstanza;
    -----------------------------
    v_log_stack = v_log_stack || FORMAT(E'INSERT mudeopen_r_sism_rel_ill_norma \n');

    FOR tableRow IN SELECT mti.id_istanza, normativa.position as i, mdsn.id_sism_norma, mdsn.descrizione_estesa
                             FROM mudeopen_t_istanza mti,
                                 jsonb_array_elements(json_data->'TAB_DS_DT_NORM_TECNICA'->'dataGrid') with ordinality normativa(item_object, position),
                                 mudeopen_d_sism_norma mdsn
                            WHERE normativa.item_object->'normativa'->>'id' = mdsn.id_sism_norma AND 
                                id_istanza = idIstanza
                        LOOP
        INSERT INTO mudeopen_r_sism_rel_ill_norma (id_sism_rel_ill
            , id_sism_norma
            , descr_norma
        ) SELECT idRelIll AS id_sism_rel_ill
                , tableRow.id_sism_norma AS id_sism_norma
                , tableRow.descrizione_estesa AS descr_norma
            FROM mudeopen_t_istanza mti 
            WHERE mti.id_istanza = idIstanza;
    END LOOP;

    -----------------------------
    -- RAISE NOTICE 'INSERT[%] mudeopen_t_sism_nuova_costr', idIstanza;
    -----------------------------
    v_log_stack = v_log_stack || FORMAT(E'INSERT mudeopen_t_sism_nuova_costr \n');

    FOR tableRow IN SELECT mti.id_istanza, struttura.position as i, item_object
                        FROM mudeopen_t_istanza mti,
                             jsonb_array_elements(json_data->'TAB_DS_DT_TIPO_INTEVENTO'->'nc_labels') with ordinality struttura(item_object, position)
                        WHERE 
                            id_istanza = idIstanza    
                        LOOP
        INSERT INTO mudeopen_t_sism_nuova_costr (id_sism_rel_ill
            , elemento_nome
            
            --19051
            , geom_num_piani_ft
            , geom_num_piani_int
            , geom_pianta_m1
            , geom_pianta_m2
            , geom_copert_m
            , geom_sup_max
            --19020
            , costr_id_tipo
            , costr_vita_nom
            , costr_id_classe_uso
            --19021
            , str_port_dest
            , str_port_cao
            , str_port_cao_in_op
            , str_port_cao_pref
            , str_port_cap
            , str_port_acciaio
            , str_port_mur
            , str_port_mur_ord
            , str_port_mur_arm
            , str_port_mur_conf
            , str_port_legno
            , str_port_mat_sc
            , str_port_mista
            , str_port_mista_desc
            , str_port_altro
            , str_port_altro_desc
            , str_port_fond
            , str_port_fond_desc
            , str_port_vert
            , str_port_vert_desc
            , str_port_oriz
            , str_port_oriz_desc
            , str_port_cop
            , str_port_cop_desc
            --19025
            , met_anal_stat_lin
            , met_anal_din_lin
            , met_anal_stat_n_lin
            , met_anal_din_n_lin
            , met_anal_altro
            , met_anal_altro_desc
            , met_anal_tipo_vinc
            , met_anal_classe_dut
            , met_anal_reg_pianta
            , met_anal_reg_elev
            --19028
            , tip_str_desc
            , tip_str_el_sec
            , tip_str_el_sec_desc
            , tip_str_ger_res
            , tip_str_ger_res_desc
            , tip_str_rig_pia
            , tip_str_rig_cop
            , tip_str_fat_q
            , tip_str_rif_norm
            , tip_str_f_teta
            , tip_str_f_q0
            , tip_str_f_a
            , tip_str_f_kw
            , tip_str_f_kr
            , tip_str_tab_giust
            , tip_str_el_fls
            , tip_str_az_vert
            , tip_str_zero_sism
            --19031
            , mat_fond
            , mat_fond_desc
            , mat_str_vert
            , mat_str_vert_desc
            , mat_oriz
            , mat_oriz_desc
            , mat_cop
            , mat_cop_desc
            --19033
            , sovr_car_perm
            , sovr_car_perm_desc
            , sovr_car_var
            , sovr_car_var_desc
            --19034
            , an_sism_per_t
            , an_sism_b_mas_x
            , an_sism_b_mas_y
            , an_sism_b_rig_x
            , an_sism_b_rig_y
            , an_sism_tagl_base_v
            , an_sism_mas_x
            , an_sism_mas_y
            , an_sism_num_vibr
            , an_sism_t1x
            , an_sism_t1x_perc
            , an_sism_t2x
            , an_sism_t2x_perc
            , an_sism_t1y
            , an_sism_t1y_perc
            , an_sism_t2y
            , an_sism_t2y_perc
            , an_sism_tagl_ult_v
            , an_sism_punto_cont_x
            , an_sism_punto_cont_y
            , an_sism_punto_cont_z
            , an_sism_spost_ult
            , an_sism_altre_an
            , an_sism_sintesi_ver
            --19035
            , spost_ed_tamp_rig
            , spost_ed_tamp_prog
            , spost_costr_m_ord
            , spost_costr_m_arm
            , spost_ver_st
            , spost_ver_st_nc_desc
            , spost_ver_ds
            , spost_ver_col
            
        ) SELECT idRelIll AS id_sism_rel_ill
                , SUBSTRING(tableRow.item_object->>'label', 1, 20) AS elemento_nome -- varchar(20)
                
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_GEOMETRIA_X2'->'container0x0'->>'geom_num_piani_ft' /* ='1' */, 1, 20) AS geom_num_piani_ft      --varchar(20)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_GEOMETRIA_X2'->'container0x0'->>'geom_num_piani_int' /* ='2' */, 1, 20) AS geom_num_piani_int        --varchar(20)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_GEOMETRIA_X2'->'container0x0'->>'geom_pianta_m1' /* ='2' */, 1, 20) AS geom_pianta_m1        --varchar(20)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_GEOMETRIA_X2'->'container0x0'->>'geom_pianta_m2' /* ='4' */, 1, 20) AS geom_pianta_m2        --varchar(20)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_GEOMETRIA_X2'->'container0x0'->>'geom_copert_m' /* ='5' */, 1, 20) AS geom_copert_m      --varchar(20)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_GEOMETRIA_X2'->'container0x0'->>'geom_sup_max' /* ='6' */, 1, 20) AS geom_sup_max        --varchar(20)
                --19020
                , mti.json_data->'TAB_DS_DTS_COSTRUZIONE_X4'->'container0x0'->>'costr_id_tipo' /* ='1' */ AS costr_id_tipo      --varchar NULL /* FK mudeopen_d_sism_costr_tipo*/
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_COSTRUZIONE_X4'->'container0x0'->>'costr_vita_nom' /* ='1' */, 1, 50) AS costr_vita_nom      --varchar(50)
                , mti.json_data->'TAB_DS_DTS_COSTRUZIONE_X4'->'container0x0'->'costr_id_classe_uso'->>'id' /* ='I' */ AS costr_id_classe_uso        --varchar NULL /* FK mudeopen_d_sism_classe_uso*/
                --19021
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUTTURE_NC'->'container0x0'->>'str_port_dest' /* ='111' */, 1, 50) AS str_port_dest        --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_NC'->'container0x0'->>'str_port_cao' = 'true' AS str_port_cao        --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_NC'->'container0x0'->>'str_port_cao_in_op' = 'true' AS str_port_cao_in_op        --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_NC'->'container0x0'->>'str_port_cao_pref' = 'true' AS str_port_cao_pref      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_NC'->'container0x0'->>'str_port_cap' = 'true' AS str_port_cap        --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_NC'->'container0x0'->>'str_port_acciaio' = 'true' AS str_port_acciaio        --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_NC'->'container0x0'->>'str_port_mur' = 'true' AS str_port_mur        --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_NC'->'container0x0'->>'str_port_mur_ord' = 'true' AS str_port_mur_ord        --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_NC'->'container0x0'->>'str_port_mur_arm' = 'true' AS str_port_mur_arm        --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_NC'->'container0x0'->>'str_port_mur_conf' = 'true' AS str_port_mur_conf      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_NC'->'container0x0'->>'str_port_legno' = 'true' AS str_port_legno        --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_NC'->'container0x0'->>'str_port_mat_sc' = 'true' AS str_port_mat_sc      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_NC'->'container0x0'->>'str_port_mista' = 'true' AS str_port_mista        --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUTTURE_NC'->'container0x0'->>'str_port_mista_desc' /* ='mista' */, 1, 50) AS str_port_mista_desc      --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_NC'->'container0x0'->>'str_port_altro' = 'true' AS str_port_altro        --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUTTURE_NC'->'container0x0'->>'str_port_altro_desc' /* ='met_anal_altro' */, 1, 50) AS str_port_altro_desc     --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_NC'->'container0x0'->>'str_port_fond' = 'true' AS str_port_fond      --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUTTURE_NC'->'container0x0'->>'str_port_fond_desc' /* ='fondazioni' */, 1, 50) AS str_port_fond_desc       --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_NC'->'container0x0'->>'str_port_vert' = 'true' AS str_port_vert      --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUTTURE_NC'->'container0x0'->>'str_port_vert_desc' /* ='strutture' */, 1, 50) AS str_port_vert_desc        --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_NC'->'container0x0'->>'str_port_oriz' = 'true' AS str_port_oriz      --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUTTURE_NC'->'container0x0'->>'str_port_oriz_desc' /* ='orizzonti' */, 1, 50) AS str_port_oriz_desc        --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_NC'->'container0x0'->>'str_port_cop' = 'true' AS str_port_cop        --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUTTURE_NC'->'container0x0'->>'str_port_cop_desc' /* ='copertura' */, 1, 50) AS str_port_cop_desc      --varchar(50)
                --19025
                , mti.json_data->'TAB_DS_DTS_ANALISI_NC'->'container0x0'->>'met_anal_stat_lin' = 'true' AS met_anal_stat_lin        --boolean NULL
                , mti.json_data->'TAB_DS_DTS_ANALISI_NC'->'container0x0'->>'met_anal_din_lin' = 'true' AS met_anal_din_lin      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_ANALISI_NC'->'container0x0'->>'met_anal_stat_n_lin' = 'true' AS met_anal_stat_n_lin        --boolean NULL
                , mti.json_data->'TAB_DS_DTS_ANALISI_NC'->'container0x0'->>'met_anal_din_n_lin' = 'true' AS met_anal_din_n_lin      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_ANALISI_NC'->'container0x0'->>'met_anal_altro' = 'true' AS met_anal_altro      --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_NC'->'container0x0'->>'met_anal_altro_desc' /* ='met_anal_altro' */, 1, 50) AS met_anal_altro_desc       --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_NC'->'container0x0'->>'met_anal_tipo_vinc' /* ='tipo vinc' */, 1, 500) AS met_anal_tipo_vinc     --varchar(500)
                , SUBSTRING(UPPER(SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_NC'->'container0x0'->>'met_anal_classe_dut', 1, 1)) /* ='alta' / 'media' / 'nessuno' */, 1, 1) AS met_anal_classe_dut        --varchar(1)
                , mti.json_data->'TAB_DS_DTS_ANALISI_NC'->'container0x0'->>'met_anal_reg_pianta' ='si' AS met_anal_reg_pianta       --boolean NULL
                , mti.json_data->'TAB_DS_DTS_ANALISI_NC'->'container0x0'->>'met_anal_reg_elev' ='si' AS met_anal_reg_elev       --boolean NULL
                --19028
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_TIP_STRUT_NC'->'container0x0'->>'tip_str_desc' /* ='tipo strutt' */, 1, 500) AS tip_str_desc     --varchar(500)
                , mti.json_data->'TAB_DS_DTS_TIP_STRUT_NC'->'container0x0'->>'tip_str_el_sec' ='si' AS tip_str_el_sec       --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_TIP_STRUT_NC'->'container0x0'->>'tip_str_el_sec_desc' /* ='descr' */, 1, 500) AS tip_str_el_sec_desc     --varchar(500)
                , mti.json_data->'TAB_DS_DTS_TIP_STRUT_NC'->'container0x0'->>'tip_str_ger_res' ='no' AS tip_str_ger_res     --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_TIP_STRUT_NC'->'container0x0'->>'tip_str_ger_res_desc' /* ='giusti' */, 1, 500) AS tip_str_ger_res_desc      --varchar(500)
                , mti.json_data->'TAB_DS_DTS_TIP_STRUT_NC'->'container0x0'->>'tip_str_rig_pia' ='si' AS tip_str_rig_pia     --boolean NULL
                , mti.json_data->'TAB_DS_DTS_TIP_STRUT_NC'->'container0x0'->>'tip_str_rig_cop' ='si' AS tip_str_rig_cop     --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_TIP_STRUT_NC'->'container0x0'->>'tip_str_fat_q' /* ='1' */, 1, 20) AS tip_str_fat_q      --varchar(20)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_TIP_STRUT_NC'->'container0x0'->>'tip_str_rif_norm' /* ='2' */, 1, 100) AS tip_str_rif_norm       --varchar(100)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_TIP_STRUT_NC'->'container0x0'->>'tip_str_f_teta' /* ='11' */, 1, 20) AS tip_str_f_teta       --varchar(20)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_TIP_STRUT_NC'->'container0x0'->>'tip_str_f_q0' /* ='1' */, 1, 20) AS tip_str_f_q0        --varchar(20)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_TIP_STRUT_NC'->'container0x0'->>'tip_str_f_a' /* ='2' */, 1, 20) AS tip_str_f_a      --varchar(20)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_TIP_STRUT_NC'->'container0x0'->>'tip_str_f_kw' /* ='3' */, 1, 20) AS tip_str_f_kw        --varchar(20)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_TIP_STRUT_NC'->'container0x0'->>'tip_str_f_kr' /* ='4' */, 1, 20) AS tip_str_f_kr        --varchar(20)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_TIP_STRUT_NC'->'container0x0'->>'tip_str_tab_giust' /* ='giustif' */, 1, 200) AS tip_str_tab_giust       --varchar(200)
                , mti.json_data->'TAB_DS_DTS_TIP_STRUT_NC'->'container0x0'->>'tip_str_el_fls' ='si' AS tip_str_el_fls       --boolean NULL
                , mti.json_data->'TAB_DS_DTS_TIP_STRUT_NC'->'container0x0'->>'tip_str_az_vert' ='si' AS tip_str_az_vert     --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_TIP_STRUT_NC'->'container0x0'->>'tip_str_zero_sism' /* ='12' */, 1, 50) AS tip_str_zero_sism     --varchar(50)
                --19031
                , mti.json_data->'TAB_DS_DTS_MATERIALI_NC'->'container0x0'->>'mat_fond' = 'true' AS mat_fond        --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_MATERIALI_NC'->'container0x0'->>'mat_fond_desc' /* ='fondaz' */, 1, 50) AS mat_fond_desc     --varchar(50)
                , mti.json_data->'TAB_DS_DTS_MATERIALI_NC'->'container0x0'->>'mat_str_vert' = 'true' AS mat_str_vert        --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_MATERIALI_NC'->'container0x0'->>'mat_str_vert_desc' /* ='strut' */, 1, 50) AS mat_str_vert_desc      --varchar(50)
                , mti.json_data->'TAB_DS_DTS_MATERIALI_NC'->'container0x0'->>'mat_oriz' = 'true' AS mat_oriz        --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_MATERIALI_NC'->'container0x0'->>'mat_oriz_desc' /* ='orizz' */, 1, 50) AS mat_oriz_desc      --varchar(50)
                , mti.json_data->'TAB_DS_DTS_MATERIALI_NC'->'container0x0'->>'mat_cop' = 'true' AS mat_cop      --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_MATERIALI_NC'->'container0x0'->>'mat_cop_desc' /* ='cop' */, 1, 50) AS mat_cop_desc      --varchar(50)
                --19033
                , mti.json_data->'TAB_DS_DTS_SOVRACC_X2'->'container0x0'->>'sovr_car_perm' = 'true' AS sovr_car_perm        --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_SOVRACC_X2'->'container0x0'->>'sovr_car_perm_desc' /* ='cari' */, 1, 50) AS sovr_car_perm_desc       --varchar(50)
                , mti.json_data->'TAB_DS_DTS_SOVRACC_X2'->'container0x0'->>'sovr_car_var' = 'true' AS sovr_car_var      --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_SOVRACC_X2'->'container0x0'->>'sovr_car_var_desc' /* ='proge' */, 1, 50) AS sovr_car_var_desc        --varchar(50)
                --19034
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3'->'container0x0'->>'an_sism_per_t' /* ='1' */, 1, 50) AS an_sism_per_t        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3'->'container0x0'->>'an_sism_b_mas_x' /* ='2' */, 1, 50) AS an_sism_b_mas_x        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3'->'container0x0'->>'an_sism_b_mas_y' /* ='3' */, 1, 50) AS an_sism_b_mas_y        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3'->'container0x0'->>'an_sism_b_rig_x' /* ='4' */, 1, 50) AS an_sism_b_rig_x        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3'->'container0x0'->>'an_sism_b_rig_y' /* ='5' */, 1, 50) AS an_sism_b_rig_y        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3'->'container0x0'->>'an_sism_tagl_base_v' /* ='6' */, 1, 50) AS an_sism_tagl_base_v        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3'->'container0x0'->>'an_sism_mas_x' /* ='1' */, 1, 50) AS an_sism_mas_x        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3'->'container0x0'->>'an_sism_mas_y' /* ='2' */, 1, 50) AS an_sism_mas_y        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3'->'container0x0'->>'an_sism_num_vibr' /* ='3' */, 1, 50) AS an_sism_num_vibr      --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3'->'container0x0'->>'an_sism_t1x' /* ='1' */, 1, 50) AS an_sism_t1x        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3'->'container0x0'->>'an_sism_t1x_perc' /* ='2' */, 1, 50) AS an_sism_t1x_perc      --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3'->'container0x0'->>'an_sism_t2x' /* ='3' */, 1, 50) AS an_sism_t2x        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3'->'container0x0'->>'an_sism_t2x_perc' /* ='4' */, 1, 50) AS an_sism_t2x_perc      --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3'->'container0x0'->>'an_sism_t1y' /* ='5' */, 1, 50) AS an_sism_t1y        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3'->'container0x0'->>'an_sism_t1y_perc' /* ='6' */, 1, 50) AS an_sism_t1y_perc      --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3'->'container0x0'->>'an_sism_t2y' /* ='7' */, 1, 50) AS an_sism_t2y        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3'->'container0x0'->>'an_sism_t2y_perc' /* ='8' */, 1, 50) AS an_sism_t2y_perc      --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3'->'container0x0'->>'an_sism_tagl_ult_v' /* ='1' */, 1, 50) AS an_sism_tagl_ult_v      --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3'->'container0x0'->>'an_sism_punto_cont_x' /* ='2' */, 1, 50) AS an_sism_punto_cont_x      --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3'->'container0x0'->>'an_sism_punto_cont_y' /* ='3' */, 1, 50) AS an_sism_punto_cont_y      --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3'->'container0x0'->>'an_sism_punto_cont_z' /* ='4' */, 1, 50) AS an_sism_punto_cont_z      --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3'->'container0x0'->>'an_sism_spost_ult' /* ='1' */, 1, 50) AS an_sism_spost_ult        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3'->'container0x0'->>'an_sism_altre_an' /* ='aaa' */, 1, 500) AS an_sism_altre_an       --varchar(500)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3'->'container0x0'->>'an_sism_sintesi_ver' /* ='bbb' */, 1, 500) AS an_sism_sintesi_ver     --varchar(500)
                --19035
                , mti.json_data->'TAB_DS_DTS_CONTR_SPOST_X2'->'container0x0'->>'spost_ed_tamp_rig' = 'true' AS spost_ed_tamp_rig        --boolean NULL
                , mti.json_data->'TAB_DS_DTS_CONTR_SPOST_X2'->'container0x0'->>'spost_ed_tamp_prog' = 'true' AS spost_ed_tamp_prog      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_CONTR_SPOST_X2'->'container0x0'->>'spost_costr_m_ord' = 'true' AS spost_costr_m_ord        --boolean NULL
                , mti.json_data->'TAB_DS_DTS_CONTR_SPOST_X2'->'container0x0'->>'spost_costr_m_arm' = 'true' AS spost_costr_m_arm        --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_CONTR_SPOST_X2'->'container0x0'->>'spost_ver_st' /* ='nonNecessario' */, 1, 2) AS spost_ver_st       --varchar(2)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_CONTR_SPOST_X2'->'container0x0'->>'spost_ver_st_nc_desc' /* ='giusti' */, 1, 50) AS spost_ver_st_nc_desc     --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_CONTR_SPOST_X2'->'container0x0'->>'spost_ver_ds' /* ='nonNecessario' */, 1, 2) AS spost_ver_ds       --varchar(2)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_CONTR_SPOST_X2'->'container0x0'->>'spost_ver_col' /* ='nonNecessario' */, 1, 2) AS spost_ver_col     --varchar(2)

                FROM mudeopen_t_istanza mti 
            WHERE mti.id_istanza = idIstanza;
    END LOOP;


    -----------------------------
    -- RAISE NOTICE 'INSERT[%] mudeopen_t_sism_costr_es', idIstanza;
    -----------------------------
    v_log_stack = v_log_stack || FORMAT(E'INSERT mudeopen_t_sism_costr_es\n');

    FOR tableRow IN SELECT mti.id_istanza, struttura.position as i, item_object
                        FROM mudeopen_t_istanza mti,
                             jsonb_array_elements(json_data->'TAB_DS_DT_TIPO_INTEVENTO'->'ce_labels') with ordinality struttura(item_object, position)
                        WHERE 
                            id_istanza = 8895    
                        LOOP
        INSERT INTO mudeopen_t_sism_costr_es (id_sism_rel_ill
            , elemento_nome
            
            --19051
            , geom_num_piani_ft
            , geom_num_piani_int
            , geom_pianta_m1
            , geom_pianta_m2
            , geom_copert_m
            , geom_sup_max
            --19017
            , int_id_tipo
            , int_id_par
            , int_lc
            , int_fc
            , int_giust
            --19040
            , costr_id_tipo
            , costr_vita_nom
            , costr_id_classe_uso
            --19022
            , str_port_es_dest
            , str_port_es_cao
            , str_port_es_cao_in_op
            , str_port_es_cao_pref
            , str_port_es_cap
            , str_port_es_acciaio
            , str_port_es_mur
            , str_port_es_mur_old
            , str_port_es_mur_arm
            , str_port_es_mur_conf
            , str_port_es_legno
            , str_port_es_mat_sc
            , str_port_es_mista
            , str_port_es_mista_desc
            , str_port_es_altro
            , str_port_es_altro_desc
            , str_port_es_fond
            , str_port_es_fond_desc
            , str_port_es_vert
            , str_port_es_vert_desc
            , str_port_es_oriz
            , str_port_es_oriz_desc
            , str_port_es_cop
            , str_port_es_cop_desc
            --19032
            , mat_es_fond
            , mat_es_fond_desc
            , mat_es_str_vert
            , mat_es_str_vert_desc
            , mat_es_oriz
            , mat_es_oriz_desc
            , mat_es_cop
            , mat_es_cop_desc
            --19029
            , str_port_pr_desc
            , str_port_pr_cao
            , str_port_pr_cao_in_op
            , str_port_pr_cao_pref
            , str_port_pr_cap
            , str_port_pr_acciaio
            , str_port_pr_mur
            , str_port_pr_mur_ord
            , str_port_pr_mur_arm
            , str_port_pr_mur_conf
            , str_port_pr_legno
            , str_port_pr_mista
            , str_port_pr_mista_desc
            , str_port_pr_altro
            , str_port_pr_altro_desc
            , str_port_pr_fond
            , str_port_pr_fond_desc
            , str_port_pr_vert
            , str_port_pr_vert_desc
            , str_port_pr_oriz
            , str_port_pr_oriz_desc
            , str_port_pr_cop
            , str_port_pr_cop_desc
            --19056
            , mat_pr_fond
            , mat_pr_fond_desc
            , mat_pr_str_vert
            , mat_pr_str_vert_desc
            , mat_pr_oriz
            , mat_pr_oriz_desc
            , mat_pr_cop
            , mat_pr_cop_desc
            --19026
            , met_anal_stat_lin
            , met_anal_din_lin
            , met_anal_stat_n_lin
            , met_anal_din_n_lin
            , met_anal_altro
            , met_anal_altro_desc
            , met_anal_tipo_vinc
            , met_anal_classe_dut
            , met_anal_reg_pianta
            , met_anal_reg_elev
            --19043
            , tip_str_desc
            , tip_str_el_sec
            , tip_str_el_sec_desc
            , tip_str_ger_res
            , tip_str_ger_res_desc
            , tip_str_rig_pia
            , tip_str_rig_cop
            , tip_str_fat_q
            , tip_str_rif_norm
            , tip_str_f_a
            , tip_str_el_fls
            , tip_str_az_vert
            , tip_str_zero_sism
            --19044
            , sovr_car_perm
            , sovr_car_perm_desc
            , sovr_car_var
            , sovr_car_var_desc
            --19045
            , an_sism_per_t
            , an_sism_b_mas_x
            , an_sism_b_mas_y
            , an_sism_b_rig_x
            , an_sism_b_rig_y
            , an_sism_tagl_base_v
            , an_sism_mas_x
            , an_sism_mas_y
            , an_sism_num_vibr
            , an_sism_t1x
            , an_sism_t1x_perc
            , an_sism_t2x
            , an_sism_t2x_perc
            , an_sism_t1y
            , an_sism_t1y_perc
            , an_sism_t2y
            , an_sism_t2y_perc
            , an_sism_tagl_ult_v
            , an_sism_punto_cont_x
            , an_sism_punto_cont_y
            , an_sism_punto_cont_z
            , an_sism_spost_ult
            , an_sism_altre_an
            , an_sism_sintesi_ver
            --19047
            , spost_ed_tamp_rig
            , spost_ed_tamp_prog
            , spost_costr_m_ord
            , spost_costr_m_arm
            , spost_ver_st
            , spost_ver_st_nc_desc
            , spost_ver_ds
            , spost_ver_fond
            , spost_ver_fond_nc_desc
            --19037
            , confr_liv_sic_prima
            , confr_liv_sic_dopo
            , confr_liv_sic_vert
            
        ) SELECT idRelIll AS id_sism_rel_ill
                , SUBSTRING(tableRow.item_object->>'label', 1, 20) AS elemento_nome -- varchar(20)
                
                --19051
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_GEOMETRIA_X2_CE'->'container0x0'->>'geom_num_piani_ft' /* ='1' */, 1, 50) AS geom_num_piani_ft       --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_GEOMETRIA_X2_CE'->'container0x0'->>'geom_num_piani_int' /* ='2' */, 1, 50) AS geom_num_piani_int     --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_GEOMETRIA_X2_CE'->'container0x0'->>'geom_pianta_m1' /* ='2' */, 1, 50) AS geom_pianta_m1     --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_GEOMETRIA_X2_CE'->'container0x0'->>'geom_pianta_m2' /* ='4' */, 1, 50) AS geom_pianta_m2     --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_GEOMETRIA_X2_CE'->'container0x0'->>'geom_copert_m' /* ='5' */, 1, 50) AS geom_copert_m       --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_GEOMETRIA_X2_CE'->'container0x0'->>'geom_sup_max' /* ='6' */, 1, 50) AS geom_sup_max     --varchar(50)
                --19017
                , mti.json_data->'TAB_DS_DTS_INTERVENTO_X3'->'container0x0'->>'int_id_tipo' /* ='2' */ AS int_id_tipo       --varchar NULL /* FK mudeopen_d_sism_int_tipo*/
                , mti.json_data->'TAB_DS_DTS_INTERVENTO_X3'->'container0x0'->>'int_id_par' /* ='8' */ AS int_id_par     --varchar NULL /* FK mudeopen_d_sism_int_par*/
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_INTERVENTO_X3'->>'int_lc' /* ='1' */, 1, 3) AS int_lc        --varchar(3)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_INTERVENTO_X3'->>'int_fc' /* ='1' */, 1, 4) AS int_fc        --varchar(4)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_INTERVENTO_X3'->'container0x0'->>'int_giust' /* ='1' */, 1, 500) AS int_giust        --varchar(500)
                --19040
                , mti.json_data->'TAB_DS_DTS_COSTRUZIONE_X4'->'container0x0'->>'int_id_tipo' /* ='1' */ AS costr_id_tipo        --varchar NULL /* FK mudeopen_d_sism_costr_tipo*/
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_COSTRUZIONE_X4_CE'->'container0x0'->>'costr_vita_nom' /* ='1' */, 1, 50) AS costr_vita_nom       --varchar(50)
                , mti.json_data->'TAB_DS_DTS_COSTRUZIONE_X4_CE'->'container0x0'->'costr_id_classe_uso'->>'id' /* ='I' */ AS costr_id_classe_uso     --varchar NULL /* FK mudeopen_d_sism_classe_uso*/
                --19022
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUTTURE_CE'->'container0x0'->>'str_port_es_dest' /* ='aaa' */, 1, 50) AS str_port_es_dest      --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_CE'->'container0x0'->>'str_port_es_cao' = 'true' AS str_port_es_cao      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_CE'->'container0x0'->>'str_port_es_cao_in_op' = 'true' AS str_port_es_cao_in_op      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_CE'->'container0x0'->>'str_port_es_cao_pref' = 'true' AS str_port_es_cao_pref        --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_CE'->'container0x0'->>'str_port_es_cap' = 'true' AS str_port_es_cap      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_CE'->'container0x0'->>'str_port_es_acciaio' = 'true' AS str_port_es_acciaio      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_CE'->'container0x0'->>'str_port_es_mur' = 'true' AS str_port_es_mur      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_CE'->'container0x0'->>'str_port_es_mur_old' = 'true' AS str_port_es_mur_old      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_CE'->'container0x0'->>'str_port_es_mur_arm' = 'true' AS str_port_es_mur_arm      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_CE'->'container0x0'->>'str_port_es_mur_conf' = 'true' AS str_port_es_mur_conf        --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_CE'->'container0x0'->>'str_port_es_legno' = 'true' AS str_port_es_legno      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_CE'->'container0x0'->>'str_port_es_mat_sc' = 'true' AS str_port_es_mat_sc        --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_CE'->'container0x0'->>'str_port_es_mista' = 'true' AS str_port_es_mista      --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUTTURE_CE'->'container0x0'->>'str_port_es_mista_desc' /* ='mista' */, 1, 50) AS str_port_es_mista_desc        --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_CE'->'container0x0'->>'str_port_es_altro' = 'true' AS str_port_es_altro      --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUTTURE_CE'->'container0x0'->>'str_port_es_altro_desc' /* ='met_anal_altro' */, 1, 50) AS str_port_es_altro_desc       --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_CE'->'container0x0'->>'str_port_es_fond' = 'true' AS str_port_es_fond        --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUTTURE_CE'->'container0x0'->>'str_port_es_fond_desc' /* ='fonda' */, 1, 50) AS str_port_es_fond_desc      --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_CE'->'container0x0'->>'str_port_es_vert' = 'true' AS str_port_es_vert        --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUTTURE_CE'->'container0x0'->>'str_port_es_vert_desc' /* ='strutt' */, 1, 50) AS str_port_es_vert_desc     --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_CE'->'container0x0'->>'str_port_es_oriz' = 'true' AS str_port_es_oriz        --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUTTURE_CE'->'container0x0'->>'str_port_es_oriz_desc' /* ='orizzontamenti' */, 1, 50) AS str_port_es_oriz_desc     --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_CE'->'container0x0'->>'str_port_es_cop' = 'true' AS str_port_es_cop      --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUTTURE_CE'->'container0x0'->>'str_port_es_cop_desc' /* ='coper' */, 1, 50) AS str_port_es_cop_desc        --varchar(50)
                --19032
                , mti.json_data->'TAB_DS_DTS_MATERIALI_CS'->'container0x0'->>'mat_es_fond' = 'true' AS mat_es_fond      --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_MATERIALI_CS'->'container0x0'->>'mat_es_fond_desc' /* ='fondaz' */, 1, 50) AS mat_es_fond_desc       --varchar(50)
                , mti.json_data->'TAB_DS_DTS_MATERIALI_CS'->'container0x0'->>'mat_es_str_vert' = 'true' AS mat_es_str_vert      --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_MATERIALI_CS'->'container0x0'->>'mat_es_str_vert_desc' /* ='strut' */, 1, 50) AS mat_es_str_vert_desc        --varchar(50)
                , mti.json_data->'TAB_DS_DTS_MATERIALI_CS'->'container0x0'->>'mat_es_oriz' = 'true' AS mat_es_oriz      --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_MATERIALI_CS'->'container0x0'->>'mat_es_oriz_desc' /* ='oriz' */, 1, 50) AS mat_es_oriz_desc     --varchar(50)
                , mti.json_data->'TAB_DS_DTS_MATERIALI_CS'->'container0x0'->>'mat_es_cop' = 'true' AS mat_es_cop        --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_MATERIALI_CS'->'container0x0'->>'mat_es_cop_desc' /* ='cop' */, 1, 50) AS mat_es_cop_desc        --varchar(50)
                --19029
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUT_CE'->'container0x0'->>'str_port_es_dest' /* ='111' */, 1, 500) AS str_port_pr_desc     --varchar(500)
                , mti.json_data->'TAB_DS_DTS_STRUT_CE'->'container0x0'->>'str_port_es_cao' = 'true' AS str_port_pr_cao      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUT_CE'->'container0x0'->>'str_port_es_cao_in_op' = 'true' AS str_port_pr_cao_in_op      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUT_CE'->'container0x0'->>'str_port_es_cao_pref' = 'true' AS str_port_pr_cao_pref        --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUT_CE'->'container0x0'->>'str_port_es_cap' = 'true' AS str_port_pr_cap      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUT_CE'->'container0x0'->>'str_port_es_acciaio' = 'true' AS str_port_pr_acciaio      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUT_CE'->'container0x0'->>'str_port_es_mur' = 'true' AS str_port_pr_mur      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUT_CE'->'container0x0'->>'str_port_es_mur_old' = 'true' AS str_port_es_mur_old      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUT_CE'->'container0x0'->>'str_port_es_mur_arm' = 'true' AS str_port_pr_mur_arm      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUT_CE'->'container0x0'->>'str_port_es_mur_conf' = 'true' AS str_port_pr_mur_conf        --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUT_CE'->'container0x0'->>'str_port_es_legno' = 'true' AS str_port_pr_legno      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUT_CE'->'container0x0'->>'str_port_es_mista' = 'true' AS str_port_pr_mista      --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUT_CE'->'container0x0'->>'str_port_es_mista_desc' /* ='mista' */, 1, 50) AS str_port_pr_mista_desc        --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUT_CE'->'container0x0'->>'str_port_es_altro' = 'true' AS str_port_pr_altro      --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUT_CE'->'container0x0'->>'str_port_es_altro_desc' /* ='met_anal_altro' */, 1, 50) AS str_port_pr_altro_desc       --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUT_CE'->'container0x0'->>'str_port_es_fond' = 'true' AS str_port_pr_fond        --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUT_CE'->'container0x0'->>'str_port_es_fond_desc' /* ='fondazioni' */, 1, 50) AS str_port_pr_fond_desc     --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUT_CE'->'container0x0'->>'str_port_es_vert' = 'true' AS str_port_pr_vert        --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUT_CE'->'container0x0'->>'str_port_es_vert_desc' /* ='strutture' */, 1, 50) AS str_port_pr_vert_desc      --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUT_CE'->'container0x0'->>'str_port_es_oriz' = 'true' AS str_port_pr_oriz        --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUT_CE'->'container0x0'->>'str_port_es_oriz_desc' /* ='orizzonti' */, 1, 50) AS str_port_pr_oriz_desc      --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUT_CE'->'container0x0'->>'str_port_es_cop' = 'true' AS str_port_pr_cop      --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUT_CE'->'container0x0'->>'str_port_es_cop_desc' /* ='copertura' */, 1, 50) AS str_port_pr_cop_desc        --varchar(50)
                --19056
                , mti.json_data->'TAB_DS_DTS_MAT_PROG_CE'->'container0x0'->>'mat_pr_fond' = 'true' AS mat_pr_fond       --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_MAT_PROG_CE'->'container0x0'->>'mat_pr_fond_desc' /* ='fondaz' */, 1, 50) AS mat_pr_fond_desc        --varchar(50)
                , mti.json_data->'TAB_DS_DTS_MAT_PROG_CE'->'container0x0'->>'mat_pr_str_vert' = 'true' AS mat_pr_str_vert       --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_MAT_PROG_CE'->'container0x0'->>'mat_pr_str_vert_desc' /* ='strut' */, 1, 50) AS mat_pr_str_vert_desc     --varchar(50)
                , mti.json_data->'TAB_DS_DTS_MAT_PROG_CE'->'container0x0'->>'mat_pr_oriz' = 'true' AS mat_pr_oriz       --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_MAT_PROG_CE'->'container0x0'->>'mat_pr_oriz_desc' /* ='orizz' */, 1, 50) AS mat_pr_oriz_desc     --varchar(50)
                , mti.json_data->'TAB_DS_DTS_MAT_PROG_CE'->'container0x0'->>'mat_pr_cop' = 'true' AS mat_pr_cop     --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_MAT_PROG_CE'->'container0x0'->>'mat_pr_cop_desc' /* ='cop' */, 1, 50) AS mat_pr_cop_desc     --varchar(50)
                --19026
                , mti.json_data->'TAB_DS_DTS_ANALISI_CE'->'container0x0'->>'met_anal_stat_lin' = 'true' AS met_anal_stat_lin        --boolean NULL
                , mti.json_data->'TAB_DS_DTS_ANALISI_CE'->'container0x0'->>'met_anal_din_lin' = 'true' AS met_anal_din_lin      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_ANALISI_CE'->'container0x0'->>'met_anal_stat_n_lin' = 'true' AS met_anal_stat_n_lin        --boolean NULL
                , mti.json_data->'TAB_DS_DTS_ANALISI_CE'->'container0x0'->>'met_anal_din_n_lin' = 'true' AS met_anal_din_n_lin      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_ANALISI_CE'->'container0x0'->>'met_anal_altro' = 'true' AS met_anal_altro      --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_CE'->'container0x0'->>'met_anal_altro_desc' /* ='altri' */, 1, 50) AS met_anal_altro_desc        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_CE'->'container0x0'->>'met_anal_tipo_vinc' /* ='aaaaa' */, 1, 500) AS met_anal_tipo_vinc     --varchar(500)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_CE'->'container0x0'->>'met_anal_classe_dut' /* ='nessuno' */, 1, 1) AS met_anal_classe_dut       --varchar(1)
                , mti.json_data->'TAB_DS_DTS_ANALISI_CE'->'container0x0'->>'met_anal_reg_pianta' ='si' AS met_anal_reg_pianta       --boolean NULL
                , mti.json_data->'TAB_DS_DTS_ANALISI_CE'->'container0x0'->>'met_anal_reg_elev' ='si' AS met_anal_reg_elev       --boolean NULL
                --19043
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_TIP_STRUT_CE'->'container0x0'->>'tip_str_desc' /* ='aaa' */, 1, 500) AS tip_str_desc     --varchar(500)
                , mti.json_data->'TAB_DS_DTS_TIP_STRUT_CE'->'container0x0'->>'tip_str_el_sec' ='si' AS tip_str_el_sec       --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_TIP_STRUT_CE'->'container0x0'->>'tip_str_el_sec_desc' /* ='bbb' */, 1, 500) AS tip_str_el_sec_desc       --varchar(500)
                , mti.json_data->'TAB_DS_DTS_TIP_STRUT_CE'->'container0x0'->>'tip_str_ger_res' ='no' AS tip_str_ger_res     --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_TIP_STRUT_CE'->'container0x0'->>'tip_str_ger_res_desc' /* ='cccc' */, 1, 500) AS tip_str_ger_res_desc        --varchar(500)
                , mti.json_data->'TAB_DS_DTS_TIP_STRUT_CE'->'container0x0'->>'tip_str_rig_pia' ='si' AS tip_str_rig_pia     --boolean NULL
                , mti.json_data->'TAB_DS_DTS_TIP_STRUT_CE'->'container0x0'->>'tip_str_rig_cop' ='si' AS tip_str_rig_cop     --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_TIP_STRUT_CE'->'container0x0'->>'tip_str_fat_q' /* ='1' */, 1, 20) AS tip_str_fat_q      --varchar(20)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_TIP_STRUT_CE'->'container0x0'->>'tip_str_rif_norm' /* ='33' */, 1, 200) AS tip_str_rif_norm      --varchar(200)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_TIP_STRUT_CE'->'container0x0'->>'tip_str_f_a' /* ='11' */, 1, 50) AS tip_str_f_a     --varchar(50)
                , mti.json_data->'TAB_DS_DTS_TIP_STRUT_CE'->'container0x0'->>'tip_str_el_fls' ='si' AS tip_str_el_fls       --boolean NULL
                , mti.json_data->'TAB_DS_DTS_TIP_STRUT_CE'->'container0x0'->>'tip_str_az_vert' ='si' AS tip_str_az_vert     --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_TIP_STRUT_CE'->'container0x0'->>'tip_str_zero_sism' /* ='222' */, 1, 500) AS tip_str_zero_sism       --varchar(500)
                --19044
                , mti.json_data->'TAB_DS_DTS_SOVRACC_X2_CE'->'container0x0'->>'sovr_car_perm' = 'true' AS sovr_car_perm     --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_SOVRACC_X2_CE'->'container0x0'->>'sovr_car_perm_desc' /* ='cari' */, 1, 50) AS sovr_car_perm_desc        --varchar(50)
                , mti.json_data->'TAB_DS_DTS_SOVRACC_X2_CE'->'container0x0'->>'sovr_car_var' = 'true' AS sovr_car_var       --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_SOVRACC_X2_CE'->'container0x0'->>'sovr_car_var_desc' /* ='proge' */, 1, 50) AS sovr_car_var_desc     --varchar(50)
                --19045
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_CE'->'container0x0'->>'an_sism_per_t' /* ='2' */, 1, 50) AS an_sism_per_t     --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_CE'->'container0x0'->>'an_sism_b_mas_x' /* ='2' */, 1, 50) AS an_sism_b_mas_x     --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_CE'->'container0x0'->>'an_sism_b_mas_y' /* ='3' */, 1, 50) AS an_sism_b_mas_y     --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_CE'->'container0x0'->>'an_sism_b_rig_x' /* ='4' */, 1, 50) AS an_sism_b_rig_x     --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_CE'->'container0x0'->>'an_sism_b_rig_y' /* ='5' */, 1, 50) AS an_sism_b_rig_y     --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_CE'->'container0x0'->>'an_sism_tagl_base_v' /* ='6' */, 1, 50) AS an_sism_tagl_base_v     --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_CE'->'container0x0'->>'an_sism_mas_x' /* ='1' */, 1, 50) AS an_sism_mas_x     --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_CE'->'container0x0'->>'an_sism_mas_y' /* ='2' */, 1, 50) AS an_sism_mas_y     --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_CE'->'container0x0'->>'an_sism_num_vibr' /* ='3' */, 1, 50) AS an_sism_num_vibr       --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_CE'->'container0x0'->>'an_sism_t1x' /* ='1' */, 1, 50) AS an_sism_t1x     --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_CE'->'container0x0'->>'an_sism_t1x_perc' /* ='2' */, 1, 50) AS an_sism_t1x_perc       --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_CE'->'container0x0'->>'an_sism_t2x' /* ='3' */, 1, 50) AS an_sism_t2x     --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_CE'->'container0x0'->>'an_sism_t2x_perc' /* ='4' */, 1, 50) AS an_sism_t2x_perc       --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_CE'->'container0x0'->>'an_sism_t1y' /* ='5' */, 1, 50) AS an_sism_t1y     --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_CE'->'container0x0'->>'an_sism_t1y_perc' /* ='6' */, 1, 50) AS an_sism_t1y_perc       --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_CE'->'container0x0'->>'an_sism_t2y' /* ='7' */, 1, 50) AS an_sism_t2y     --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_CE'->'container0x0'->>'an_sism_t2y_perc' /* ='8' */, 1, 50) AS an_sism_t2y_perc       --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_CE'->'container0x0'->>'an_sism_tagl_ult_v' /* ='1' */, 1, 50) AS an_sism_tagl_ult_v       --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_CE'->'container0x0'->>'an_sism_punto_cont_x' /* ='2' */, 1, 50) AS an_sism_punto_cont_x       --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_CE'->'container0x0'->>'an_sism_punto_cont_y' /* ='3' */, 1, 50) AS an_sism_punto_cont_y       --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_CE'->'container0x0'->>'an_sism_punto_cont_z' /* ='4' */, 1, 50) AS an_sism_punto_cont_z       --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_CE'->'container0x0'->>'an_sism_spost_ult' /* ='1' */, 1, 50) AS an_sism_spost_ult     --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_CE'->'container0x0'->>'an_sism_altre_an' /* ='aaa' */, 1, 500) AS an_sism_altre_an        --varchar(500)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_CE'->'container0x0'->>'an_sism_sintesi_ver' /* ='bbb' */, 1, 500) AS an_sism_sintesi_ver      --varchar(500)
                --19047
                , mti.json_data->'TAB_DS_DTS_CONTR_SPOST_X2_CE'->'container0x0'->>'spost_ed_tamp_rig' = 'true' AS spost_ed_tamp_rig     --boolean NULL
                , mti.json_data->'TAB_DS_DTS_CONTR_SPOST_X2_CE'->'container0x0'->>'spost_ed_tamp_prog' = 'true' AS spost_ed_tamp_prog       --boolean NULL
                , mti.json_data->'TAB_DS_DTS_CONTR_SPOST_X2_CE'->'container0x0'->>'spost_costr_m_ord' = 'true' AS spost_costr_m_ord     --boolean NULL
                , mti.json_data->'TAB_DS_DTS_CONTR_SPOST_X2_CE'->'container0x0'->>'spost_costr_m_arm' = 'true' AS spost_costr_m_arm     --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_CONTR_SPOST_X2_CE'->'container0x0'->>'spost_ver_st' /* ='nonNecessario' */, 1, 2) AS spost_ver_st        --varchar(2)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_CONTR_SPOST_X2_CE'->'container0x0'->>'spost_ver_st_nc_desc' /* ='aaaaa' */, 1, 50) AS spost_ver_st_nc_desc       --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_CONTR_SPOST_X2_CE'->'container0x0'->>'spost_ver_ds' /* ='nonNecessario' */, 1, 2) AS spost_ver_ds        --varchar(2)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_CONTR_SPOST_X2_CE'->'container0x0'->>'spost_ver_fond' /* ='nonNecessario' */, 1, 2) AS spost_ver_fond        --varchar(2)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_CONTR_SPOST_X2_CE'->'container0x0'->>'spost_ver_fond_nc_desc' /* ='bbb' */, 1, 50) AS spost_ver_fond_nc_desc     --varchar(50)
                --19037
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_LIV_SIC_CE'->'container0x0'->>'confr_liv_sic_prima' /* ='1' */, 1, 50) AS confr_liv_sic_prima        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_LIV_SIC_CE'->'container0x0'->>'confr_liv_sic_dopo' /* ='2' */, 1, 50) AS confr_liv_sic_dopo      --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_LIV_SIC_CE'->'container0x0'->>'confr_liv_sic_vert' /* ='3' */, 1, 50) AS confr_liv_sic_vert      --varchar(50)
            FROM mudeopen_t_istanza mti 
            WHERE mti.id_istanza = idIstanza;
    END LOOP;


    -----------------------------
    -- RAISE NOTICE 'INSERT[%] mudeopen_t_sism_ponti_viad', idIstanza;
    -----------------------------
    v_log_stack = v_log_stack || FORMAT(E'INSERT mudeopen_t_sism_ponti_viad \n');

    FOR tableRow IN SELECT mti.id_istanza, struttura.position as i, item_object
                        FROM mudeopen_t_istanza mti,
                             jsonb_array_elements(json_data->'TAB_DS_DT_TIPO_INTEVENTO'->'nc_labels') with ordinality struttura(item_object, position)
                        WHERE 
                            id_istanza = 8895    
                        LOOP
        INSERT INTO mudeopen_t_sism_ponti_viad (id_sism_rel_ill
            , elemento_nome
            
            --19015
            , tipo_pv_nuovo
            , tipo_pv_esist
            --19019
            , geom_n_campate
            , geom_camp_luce_m
            , geom_pv_strad
            , geom_pv_strad_cat
            , geom_pv_fer
            , geom_pianta_m1
            , geom_pianta_m2
            , geom_alt_max
            --19048
            , int_id_tipo
            , int_id_par
            , int_lc
            , int_fc
            , int_giust
            --19041
            , costr_id_tipo
            , costr_vita_nom
            , costr_id_classe_uso
            --19057
            , str_port_es_sist
            , str_port_es_cao
            , str_port_es_cao_in_op
            , str_port_es_cao_pref
            , str_port_es_cap
            , str_port_es_acciaio
            , str_port_es_mur
            , str_port_es_legno
            , str_port_es_mista
            , str_port_es_mista_desc
            , str_port_es_altro
            , str_port_es_altro_desc
            , str_port_es_fond
            , str_port_es_fond_desc
            , str_port_es_vert
            , str_port_es_vert_desc
            , str_port_es_imp
            , str_port_es_imp_desc
            --19027
            , met_anal_stat_lin
            , met_anal_din_lin
            , met_anal_stat_n_lin
            , met_anal_din_n_lin
            , met_anal_altro
            , met_anal_altro_desc
            , met_anal_tipo_vinc
            , met_anal_classe_dut
            --19030
            , tip_str_desc
            , tip_str_fat_q
            , tip_str_rif_norm
            , tip_str_az_vert
            , tip_str_zero_sism
            --19023
            , str_port_pr_tipo
            , str_port_pr_cao
            , str_port_pr_cao_in_op
            , str_port_pr_cao_pref
            , str_port_pr_cap
            , str_port_pr_acciaio
            , str_port_pr_mur
            , str_port_pr_legno
            , str_port_pr_mista
            , str_port_pr_mista_desc
            , str_port_pr_altro
            , str_port_pr_altro_desc
            , str_port_pr_fond
            , str_port_pr_fond_desc
            , str_port_pr_vert
            , str_port_pr_vert_desc
            , str_port_pr_imp
            , str_port_pr_imp_desc
            --19038
            , confr_liv_sic_prima
            , confr_liv_sic_dopo
            , confr_liv_sic_vert
            --19046
            , an_sism_per_t
            , an_sism_b_mas_x
            , an_sism_b_mas_y
            , an_sism_b_rig_x
            , an_sism_b_rig_y
            , an_sism_tagl_base_v
            , an_sism_mas_x
            , an_sism_mas_y
            , an_sism_num_vibr
            , an_sism_t1x
            , an_sism_t1x_perc
            , an_sism_t2x
            , an_sism_t2x_perc
            , an_sism_t1y
            , an_sism_t1y_perc
            , an_sism_t2y
            , an_sism_t2y_perc
            , an_sism_tagl_ult_v
            , an_sism_punto_cont_x
            , an_sism_punto_cont_y
            , an_sism_punto_cont_z
            , an_sism_spost_ult
            , an_sism_altre_an
            , an_sism_sintesi_ver
            
        ) SELECT idRelIll AS id_sism_rel_ill
                , SUBSTRING(tableRow.item_object->>'label', 1, 20) AS elemento_nome -- varchar(20)
                
                , mti.json_data->'TAB_DS_DTS_TIPO_PONTE'->'an_sism_per_t'->>'tipologiaPonte' = 'tipo_pv_nuovo' AS tipo_pv_nuovo     --boolean NULL
                , mti.json_data->'TAB_DS_DTS_TIPO_PONTE'->'an_sism_per_t'->>'tipologiaPonte' = 'tipo_pv_esist' AS tipo_pv_esist     --boolean NULL
                --19019
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_GEOMETRIA_PV'->'an_sism_per_t'->>'geom_n_campate' /* ='1' */, 1, 50) AS geom_n_campate       --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_GEOMETRIA_PV'->'an_sism_per_t'->>'geom_camp_luce_m' /* ='2' */, 1, 50) AS geom_camp_luce_m       --varchar(50)
                , mti.json_data->'TAB_DS_DTS_GEOMETRIA_PV'->'an_sism_per_t'->>'geom_pv_strad' = 'true' AS geom_pv_strad     --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_GEOMETRIA_PV'->'an_sism_per_t'->>'geom_pv_strad_cat' /* ='3' */, 1, 50) AS geom_pv_strad_cat     --varchar(50)
                , mti.json_data->'TAB_DS_DTS_GEOMETRIA_PV'->'an_sism_per_t'->>'geom_pv_fer' = 'true' AS geom_pv_fer     --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_GEOMETRIA_PV'->'an_sism_per_t'->>'geom_pianta_m1' /* ='4' */, 1, 50) AS geom_pianta_m1       --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_GEOMETRIA_PV'->'an_sism_per_t'->>'geom_pianta_m2' /* ='5' */, 1, 50) AS geom_pianta_m2       --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_GEOMETRIA_PV'->'an_sism_per_t'->>'geom_alt_max' /* ='6' */, 1, 50) AS geom_alt_max       --varchar(50)
                --19048
                , mti.json_data->'TAB_DS_DTS_INTERVENTO_X3_PV'->'an_sism_per_t'->>'int_id_tipo' /* ='1' */ AS int_id_tipo       --varchar NULL,/* FK mudeopen_d_sism_int_tipo*/
                , mti.json_data->'TAB_DS_DTS_INTERVENTO_X3_PV'->'an_sism_per_t'->>'int_id_par' /* ='1' */ AS int_id_par     --varchar NULL,/* FK mudeopen_d_sism_int_par*/
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_INTERVENTO_X3_PV'->'an_sism_per_t'->>'int_lc' /* ='lc1' */, 1, 3) AS int_lc      --varchar(3)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_INTERVENTO_X3_PV'->'an_sism_per_t'->>'int_fc' /* ='fc135' */, 1, 4) AS int_fc        --varchar(4)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_INTERVENTO_X3_PV'->'an_sism_per_t'->>'int_giust' /* ='aaaa' */, 1, 500) AS int_giust     --varchar(500)
                --19041
                , mti.json_data->'TAB_DS_DTS_COSTRUZIONE_X4_PV'->'an_sism_per_t'->>'int_id_tipo' /* ='1' */ AS costr_id_tipo        --varchar NULL, /* FK mudeopen_d_sism_costr_tipo*/
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_COSTRUZIONE_X4_PV'->'an_sism_per_t'->>'costr_vita_nom' /* ='1' */, 1, 50) AS costr_vita_nom      --varchar(50)
                , mti.json_data->'TAB_DS_DTS_COSTRUZIONE_X4_PV'->'an_sism_per_t'->'costr_id_classe_uso'->>'id' /* ='I' */ AS costr_id_classe_uso        --varchar NULL,/* FK mudeopen_d_sism_classe_uso*/
                --19057
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUTT_PORT_PV'->'an_sism_per_t'->>'str_port_es_sist' /* ='aaa' */, 1, 50) AS str_port_es_sist       --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUTT_PORT_PV'->'an_sism_per_t'->>'str_port_es_cao' = 'true' AS str_port_es_cao       --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTT_PORT_PV'->'an_sism_per_t'->>'str_port_es_cao_in_op' = 'true' AS str_port_es_cao_in_op       --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTT_PORT_PV'->'an_sism_per_t'->>'str_port_es_cao_pref' = 'true' AS str_port_es_cao_pref     --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTT_PORT_PV'->'an_sism_per_t'->>'str_port_es_cap' = 'true' AS str_port_es_cap       --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTT_PORT_PV'->'an_sism_per_t'->>'str_port_es_acciaio' = 'true' AS str_port_es_acciaio       --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTT_PORT_PV'->'an_sism_per_t'->>'str_port_es_mur' = 'true' AS str_port_es_mur       --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTT_PORT_PV'->'an_sism_per_t'->>'str_port_es_legno' = 'true' AS str_port_es_legno       --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTT_PORT_PV'->'an_sism_per_t'->>'str_port_es_mista' = 'true' AS str_port_es_mista       --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUTT_PORT_PV'->'an_sism_per_t'->>'str_port_es_mista_desc' /* ='mista' */, 1, 50) AS str_port_es_mista_desc     --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUTT_PORT_PV'->'an_sism_per_t'->>'str_port_es_altro' = 'true' AS str_port_es_altro       --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUTT_PORT_PV'->'an_sism_per_t'->>'str_port_es_altro_desc' /* ='met_anal_altro' */, 1, 50) AS str_port_es_altro_desc        --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUTT_PORT_PV'->'an_sism_per_t'->>'str_port_es_fond' = 'true' AS str_port_es_fond     --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUTT_PORT_PV'->'an_sism_per_t'->>'str_port_es_fond_desc' /* ='fonda' */, 1, 50) AS str_port_es_fond_desc       --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUTT_PORT_PV'->'an_sism_per_t'->>'str_port_es_vert' = 'true' AS str_port_es_vert     --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUTT_PORT_PV'->'an_sism_per_t'->>'str_port_es_vert_desc' /* ='strut' */, 1, 50) AS str_port_es_vert_desc       --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUTT_PORT_PV'->'an_sism_per_t'->>'str_port_es_imp' = 'true' AS str_port_es_imp       --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUTT_PORT_PV'->'an_sism_per_t'->>'str_port_es_imp_desc' /* ='impal' */, 1, 50) AS str_port_es_imp_desc     --varchar(50)
                --19027
                , mti.json_data->'TAB_DS_DTS_ANALISI_PV'->'an_sism_per_t'->>'met_anal_stat_lin' = 'true' AS met_anal_stat_lin       --boolean NULL
                , mti.json_data->'TAB_DS_DTS_ANALISI_PV'->'an_sism_per_t'->>'met_anal_din_lin' = 'true' AS met_anal_din_lin     --boolean NULL
                , mti.json_data->'TAB_DS_DTS_ANALISI_PV'->'an_sism_per_t'->>'met_anal_stat_n_lin' = 'true' AS met_anal_stat_n_lin       --boolean NULL
                , mti.json_data->'TAB_DS_DTS_ANALISI_PV'->'an_sism_per_t'->>'met_anal_din_n_lin' = 'true' AS met_anal_din_n_lin     --boolean NULL
                , mti.json_data->'TAB_DS_DTS_ANALISI_PV'->'an_sism_per_t'->>'met_anal_altro' = 'true' AS met_anal_altro     --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_PV'->'an_sism_per_t'->>'met_anal_altro_desc' /* ='met_anal_altro' */, 1, 50) AS met_anal_altro_desc      --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_PV'->'an_sism_per_t'->>'met_anal_tipo_vinc' /* ='aaa' */, 1, 500) AS met_anal_tipo_vinc      --varchar(500)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_PV'->'an_sism_per_t'->>'met_anal_classe_dut' /* ='nessuno' */, 1, 1) AS met_anal_classe_dut      --varchar(1)
                --19030
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_TIP_STRUT_PV'->'an_sism_per_t'->>'tip_str_desc' /* ='aaa' */, 1, 500) AS tip_str_desc        --varchar(500)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_TIP_STRUT_PV'->'an_sism_per_t'->>'tip_str_fat_q' /* ='1' */, 1, 50) AS tip_str_fat_q     --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_TIP_STRUT_PV'->'an_sism_per_t'->>'tip_str_rif_norm' /* ='2' */, 1, 50) AS tip_str_rif_norm       --varchar(50)
                , mti.json_data->'TAB_DS_DTS_TIP_STRUT_PV'->'an_sism_per_t'->>'tip_str_az_vert' ='si' AS tip_str_az_vert        --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_TIP_STRUT_PV'->'an_sism_per_t'->>'tip_str_zero_sism' /* ='11' */, 1, 500) AS tip_str_zero_sism       --varchar(500)
                --19023
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUTTURE_PV'->'an_sism_per_t'->>'str_port_es_sist' /* ='aaa' */, 1, 50) AS str_port_pr_tipo     --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_PV'->'an_sism_per_t'->>'str_port_es_cao' = 'true' AS str_port_pr_cao     --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_PV'->'an_sism_per_t'->>'str_port_es_cao_in_op' = 'true' AS str_port_pr_cao_in_op     --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_PV'->'an_sism_per_t'->>'str_port_es_cao_pref' = 'true' AS str_port_pr_cao_pref       --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_PV'->'an_sism_per_t'->>'str_port_es_cap' = 'true' AS str_port_pr_cap     --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_PV'->'an_sism_per_t'->>'str_port_es_acciaio' = 'true' AS str_port_pr_acciaio     --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_PV'->'an_sism_per_t'->>'str_port_es_mur' = 'true' AS str_port_pr_mur     --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_PV'->'an_sism_per_t'->>'str_port_es_legno' = 'true' AS str_port_pr_legno     --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_PV'->'an_sism_per_t'->>'str_port_es_mista' = 'true' AS str_port_pr_mista     --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUTTURE_PV'->'an_sism_per_t'->>'str_port_es_mista_desc' /* ='mista' */, 1, 50) AS str_port_pr_mista_desc       --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_PV'->'an_sism_per_t'->>'str_port_es_altro' = 'true' AS str_port_pr_altro     --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUTTURE_PV'->'an_sism_per_t'->>'str_port_es_altro_desc' /* ='met_anal_altro' */, 1, 50) AS str_port_pr_altro_desc      --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_PV'->'an_sism_per_t'->>'str_port_es_fond' = 'true' AS str_port_pr_fond       --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUTTURE_PV'->'an_sism_per_t'->>'str_port_es_fond_desc' /* ='fond' */, 1, 50) AS str_port_pr_fond_desc      --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_PV'->'an_sism_per_t'->>'str_port_es_vert' = 'true' AS str_port_pr_vert       --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUTTURE_PV'->'an_sism_per_t'->>'str_port_es_vert_desc' /* ='strut' */, 1, 50) AS str_port_pr_vert_desc     --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_PV'->'an_sism_per_t'->>'str_port_es_imp' = 'true' AS str_port_pr_imp     --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUTTURE_PV'->'an_sism_per_t'->>'str_port_es_imp_desc' /* ='imal' */, 1, 50) AS str_port_pr_imp_desc        --varchar(50)
                --19038
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_LIV_SIC_PV'->'an_sism_per_t'->>'confr_liv_sic_prima' /* ='1' */, 1, 50) AS confr_liv_sic_prima       --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_LIV_SIC_PV'->'an_sism_per_t'->>'confr_liv_sic_dopo' /* ='2' */, 1, 50) AS confr_liv_sic_dopo     --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_LIV_SIC_PV'->'an_sism_per_t'->>'confr_liv_sic_vert' /* ='3' */, 1, 50) AS confr_liv_sic_vert     --varchar(50)
                --19046
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_PV'->>'an_sism_per_t' /* ='1' */, 1, 50) AS an_sism_per_t     --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_PV'->'an_sism_per_t'->>'an_sism_b_mas_x' /* ='2' */, 1, 50) AS an_sism_b_mas_x        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_PV'->'an_sism_per_t'->>'an_sism_b_mas_y' /* ='3' */, 1, 50) AS an_sism_b_mas_y        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_PV'->'an_sism_per_t'->>'an_sism_b_rig_x' /* ='4' */, 1, 50) AS an_sism_b_rig_x        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_PV'->'an_sism_per_t'->>'an_sism_b_rig_y' /* ='5' */, 1, 50) AS an_sism_b_rig_y        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_PV'->'an_sism_per_t'->>'an_sism_tagl_base_v' /* ='6' */, 1, 50) AS an_sism_tagl_base_v        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_PV'->'an_sism_per_t'->>'an_sism_mas_x' /* ='1' */, 1, 50) AS an_sism_mas_x        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_PV'->'an_sism_per_t'->>'an_sism_mas_y' /* ='2' */, 1, 50) AS an_sism_mas_y        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_PV'->'an_sism_per_t'->>'an_sism_num_vibr' /* ='3' */, 1, 50) AS an_sism_num_vibr      --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_PV'->'an_sism_per_t'->>'an_sism_t1x' /* ='1' */, 1, 50) AS an_sism_t1x        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_PV'->'an_sism_per_t'->>'an_sism_t1x_perc' /* ='2' */, 1, 50) AS an_sism_t1x_perc      --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_PV'->'an_sism_per_t'->>'an_sism_t2x' /* ='3' */, 1, 50) AS an_sism_t2x        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_PV'->'an_sism_per_t'->>'an_sism_t2x_perc' /* ='4' */, 1, 50) AS an_sism_t2x_perc      --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_PV'->'an_sism_per_t'->>'an_sism_t1y' /* ='5' */, 1, 50) AS an_sism_t1y        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_PV'->'an_sism_per_t'->>'an_sism_t1y_perc' /* ='6' */, 1, 50) AS an_sism_t1y_perc      --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_PV'->'an_sism_per_t'->>'an_sism_t2y' /* ='7' */, 1, 50) AS an_sism_t2y        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_PV'->'an_sism_per_t'->>'an_sism_t2y_perc' /* ='8' */, 1, 50) AS an_sism_t2y_perc      --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_PV'->'an_sism_per_t'->>'an_sism_tagl_ult_v' /* ='1' */, 1, 50) AS an_sism_tagl_ult_v      --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_PV'->'an_sism_per_t'->>'an_sism_punto_cont_x' /* ='2' */, 1, 50) AS an_sism_punto_cont_x      --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_PV'->'an_sism_per_t'->>'an_sism_punto_cont_y' /* ='3' */, 1, 50) AS an_sism_punto_cont_y      --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_PV'->'an_sism_per_t'->>'an_sism_punto_cont_z' /* ='4' */, 1, 50) AS an_sism_punto_cont_z      --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_PV'->'an_sism_per_t'->>'an_sism_spost_ult' /* ='1' */, 1, 50) AS an_sism_spost_ult        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_PV'->'an_sism_per_t'->>'an_sism_altre_an' /* ='aaa' */, 1, 500) AS an_sism_altre_an       --varchar(500)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_SISMICA_X3_PV'->'an_sism_per_t'->>'an_sism_sintesi_ver' /* ='bbb' */, 1, 500) AS an_sism_sintesi_ver     --varchar(500)
                
            FROM mudeopen_t_istanza mti 
            WHERE mti.id_istanza = idIstanza;
    END LOOP;


    -----------------------------
    -- RAISE NOTICE 'INSERT[%] mudeopen_t_sism_muri_sost', idIstanza;
    -----------------------------
    v_log_stack = v_log_stack || FORMAT(E'INSERT mudeopen_t_sism_muri_sost \n');

    FOR tableRow IN SELECT mti.id_istanza, struttura.position as i, item_object
                        FROM mudeopen_t_istanza mti,
                             jsonb_array_elements(json_data->'TAB_DS_DT_TIPO_INTEVENTO'->'nc_labels') with ordinality struttura(item_object, position)
                        WHERE 
                            id_istanza = 8895    
                        LOOP
        INSERT INTO mudeopen_t_sism_muri_sost (id_sism_rel_ill
            , elemento_nome
            
            --19016
            , tipo_muro_nuovo
            , tipo_muro_esist
            --19018
            , geom_svil_max
            , geom_altezza
            --19049
            , int_id_tipo
            , int_id_par
            , int_lc
            , int_fc
            , int_giust
            --19042
            , costr_id_tipo
            , costr_vita_nom
            , costr_id_classe_uso
            --19024
            , str_port_es_cao
            , str_port_es_cao_in_op
            , str_port_es_cao_pref
            , str_port_es_cap
            , str_port_es_acciaio
            , str_port_es_mur
            , str_port_es_altro
            , str_port_es_altro_desc
            , str_port_es_fond
            , str_port_es_fond_desc
            , str_port_es_vert
            , str_port_es_vert_desc
            --19050
            , met_anal_pseud
            , met_anal_spost
            , met_anal_altro
            , met_anal_altro_desc
            --19036
            , coef_sld_kh
            , coef_sld_kv
            , coef_slv_kh
            , coef_slv_kv
            , coef_sld_bm
            , coef_slv_bm
            , coef_sld_bs
            , coef_slv_bs
            , coef_sld_b_par
            , coef_slv_b_par
            --19055
            , str_port_pr_tipo
            , str_port_pr_cao
            , str_port_pr_cao_in_op
            , str_port_pr_cao_pref
            , str_port_pr_cap
            , str_port_pr_acciaio
            , str_port_pr_mur
            , str_port_pr_altro
            , str_port_pr_altro_desc
            , str_port_pr_fond
            , str_port_pr_fond_desc
            , str_port_pr_vert
            , str_port_pr_vert_desc
            --19059
            , confr_liv_sic_prima
            , confr_liv_sic_dopo
            , confr_liv_sic_vert
        ) SELECT idRelIll AS id_sism_rel_ill
                , SUBSTRING(tableRow.item_object->>'label', 1, 20) AS elemento_nome -- varchar(20)
                
                --19016
                , mti.json_data->'TAB_DS_DTS_TIPO_MURO'->'container0x0'->>'tipologiaMuro' = 'tipo_muro_nuovo' AS tipo_muro_nuovo        --boolean NULL
                , mti.json_data->'TAB_DS_DTS_TIPO_MURO'->'container0x0'->>'tipologiaMuro' = 'tipo_muro_esist' AS tipo_muro_esist        --boolean NULL
                --19018
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_GEOMETRIA_MS'->'container0x0'->>'geom_svil_max' /* ='1' */, 1, 50) AS geom_svil_max      --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_GEOMETRIA_MS'->'container0x0'->>'geom_altezza' /* ='2' */, 1, 50) AS geom_altezza        --varchar(50)
                --19049
                , mti.json_data->'TAB_DS_DTS_INTERVENTO_X3_MS'->'container0x0'->>'int_id_tipo' /* ='1' */ AS int_id_tipo        --varchar NULL /* FK mudeopen_d_sism_int_tipo*/
                , mti.json_data->'TAB_DS_DTS_INTERVENTO_X3_MS'->'container0x0'->>'int_id_par' /* ='1' */ AS int_id_par      --varchar NULL /* FK mudeopen_d_sism_int_par*/
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_INTERVENTO_X3_MS'->'container0x0'->>'int_lc' /* ='lc1' */, 1, 3) AS int_lc       --varchar(3)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_INTERVENTO_X3_MS'->'container0x0'->>'int_fc' /* ='fc135' */, 1, 4) AS int_fc     --varchar(4)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_INTERVENTO_X3_MS'->'container0x0'->>'int_giust' /* ='aaaa' */, 1, 500) AS int_giust      --varchar(500)
                --19042
                , mti.json_data->'TAB_DS_DTS_COSTRUZIONE_X4_MS'->'container0x0'->>'int_id_tipo' /* ='1' */ AS costr_id_tipo     --varchar NULL /* FK mudeopen_d_sism_costr_tipo*/
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_COSTRUZIONE_X4_MS'->'container0x0'->>'costr_vita_nom' /* ='1' */, 1, 50) AS costr_vita_nom       --varchar(50)
                , mti.json_data->'TAB_DS_DTS_COSTRUZIONE_X4_MS'->'container0x0'->'costr_id_classe_uso'->>'id' /* ='I' */ AS costr_id_classe_uso     --varchar NULL /* FK mudeopen_d_sism_classe_uso*/
                --19024
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_MS'->'container0x0'->>'str_port_es_cao' = 'true' AS str_port_es_cao      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_MS'->'container0x0'->>'str_port_es_cao_in_op' = 'true' AS str_port_es_cao_in_op      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_MS'->'container0x0'->>'str_port_es_cao_pref' = 'true' AS str_port_es_cao_pref        --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_MS'->'container0x0'->>'str_port_es_cap' = 'true' AS str_port_es_cap      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_MS'->'container0x0'->>'str_port_es_acciaio' = 'true' AS str_port_es_acciaio      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_MS'->'container0x0'->>'str_port_es_mur' = 'true' AS str_port_es_mur      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_MS'->'container0x0'->>'str_port_es_altro' = 'true' AS str_port_es_altro      --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUTTURE_MS'->'container0x0'->>'str_port_es_altro_desc' /* ='met_anal_altro' */, 1, 50) AS str_port_es_altro_desc       --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_MS'->'container0x0'->>'str_port_es_fond' = 'true' AS str_port_es_fond        --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUTTURE_MS'->'container0x0'->>'str_port_es_fond_desc' /* ='fond' */, 1, 50) AS str_port_es_fond_desc       --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUTTURE_MS'->'container0x0'->>'str_port_es_vert' = 'true' AS str_port_es_vert        --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUTTURE_MS'->'container0x0'->>'str_port_es_vert_desc' /* ='strutt vert' */, 1, 50) AS str_port_es_vert_desc        --varchar(50)
                --19050
                , mti.json_data->'TAB_DS_DTS_ANALISI_MS'->'container0x0'->>'met_anal_pseud' = 'true' AS met_anal_pseud      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_ANALISI_MS'->'container0x0'->>'met_anal_spost' = 'true' AS met_anal_spost      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_ANALISI_MS'->'container0x0'->>'met_anal_altro' = 'true' AS met_anal_altro      --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_ANALISI_MS'->'container0x0'->>'met_anal_altro_desc' /* ='met_anal_altro' */, 1, 50) AS met_anal_altro_desc       --varchar(50)
                --19036
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_COEFF_SISM'->'container0x0'->>'coef_sld_kh' /* ='1' */, 1, 50) AS coef_sld_kh        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_COEFF_SISM'->'container0x0'->>'coef_sld_kv' /* ='2' */, 1, 50) AS coef_sld_kv        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_COEFF_SISM'->'container0x0'->>'coef_slv_kh' /* ='3' */, 1, 50) AS coef_slv_kh        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_COEFF_SISM'->'container0x0'->>'coef_slv_kv' /* ='4' */, 1, 50) AS coef_slv_kv        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_COEFF_SISM'->'container0x0'->>'coef_sld_bm' /* ='5' */, 1, 50) AS coef_sld_bm        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_COEFF_SISM'->'container0x0'->>'coef_slv_bm' /* ='6' */, 1, 50) AS coef_slv_bm        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_COEFF_SISM'->'container0x0'->>'coef_sld_bs' /* ='7' */, 1, 50) AS coef_sld_bs        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_COEFF_SISM'->'container0x0'->>'coef_slv_bs' /* ='8' */, 1, 50) AS coef_slv_bs        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_COEFF_SISM'->'container0x0'->>'coef_sld_b_par' /* ='9' */, 1, 50) AS coef_sld_b_par      --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_COEFF_SISM'->'container0x0'->>'coef_slv_b_par' /* ='0' */, 1, 50) AS coef_slv_b_par      --varchar(50)
                --19055
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUT_MS'->'container0x0'->>'str_port_pr_tipo' /* ='111' */, 1, 500) AS str_port_pr_tipo     --varchar(500)
                , mti.json_data->'TAB_DS_DTS_STRUT_MS'->'container0x0'->>'str_port_es_cao' = 'true' AS str_port_pr_cao      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUT_MS'->'container0x0'->>'str_port_es_cao_in_op' = 'true' AS str_port_pr_cao_in_op      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUT_MS'->'container0x0'->>'str_port_es_cao_pref' = 'true' AS str_port_pr_cao_pref        --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUT_MS'->'container0x0'->>'str_port_es_cap' = 'true' AS str_port_pr_cap      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUT_MS'->'container0x0'->>'str_port_es_acciaio' = 'true' AS str_port_pr_acciaio      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUT_MS'->'container0x0'->>'str_port_es_mur' = 'true' AS str_port_pr_mur      --boolean NULL
                , mti.json_data->'TAB_DS_DTS_STRUT_MS'->'container0x0'->>'str_port_es_altro' = 'true' AS str_port_pr_altro      --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUT_MS'->'container0x0'->>'str_port_es_altro_desc' /* ='met_anal_altro' */, 1, 50) AS str_port_pr_altro_desc       --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUT_MS'->'container0x0'->>'str_port_es_fond' = 'true' AS str_port_pr_fond        --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUT_MS'->'container0x0'->>'str_port_es_fond_desc' /* ='fond' */, 1, 50) AS str_port_pr_fond_desc       --varchar(50)
                , mti.json_data->'TAB_DS_DTS_STRUT_MS'->'container0x0'->>'str_port_es_vert' = 'true' AS str_port_pr_vert        --boolean NULL
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_STRUT_MS'->'container0x0'->>'str_port_es_vert_desc' /* ='strutt vert' */, 1, 50) AS str_port_pr_vert_desc        --varchar(50)
                --19059
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_LIV_SIC_MS'->'container0x0'->>'confr_liv_sic_prima' /* ='1' */, 1, 50) AS confr_liv_sic_prima        --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_LIV_SIC_MS'->'container0x0'->>'confr_liv_sic_dopo' /* ='2' */, 1, 50) AS confr_liv_sic_dopo      --varchar(50)
                , SUBSTRING(mti.json_data->'TAB_DS_DTS_LIV_SIC_MS'->'container0x0'->>'confr_liv_sic_vert' /* ='3' */, 1, 50) AS confr_liv_sic_vert      --varchar(50)
            FROM mudeopen_t_istanza mti 
            WHERE mti.id_istanza = idIstanza;
    END LOOP;
    
    RETURN TRUE;
EXCEPTION 
  WHEN OTHERS THEN
    GET STACKED DIAGNOSTICS v_error1_stack = MESSAGE_TEXT,
                            v_error2_stack = PG_EXCEPTION_CONTEXT,
                            v_error3_stack = PG_EXCEPTION_HINT,
                            v_error4_stack = PG_EXCEPTION_DETAIL;
    RAISE NOTICE E'%\nEXCEPTION: %\n%\n%\n%', v_log_stack, v_error1_stack, v_error2_stack, v_error3_stack, v_error4_stack;

    --UPDATE mudeopen_t_istanze_ext SET dps_script_stato = FORMAT(E'DS-COPY:\n%s\nEXCEPTION: %s\n%s\n%s\n\n%s', v_log_stack, v_error1_stack, v_error2_stack, v_error3_stack, v_error4_stack) WHERE id_istanza = idIstanza;
    UPDATE mudeopen_t_pratica_cosmo SET json_response = json_response || FORMAT(E'DS-COPY-EXCEPTION:\n%s\n%s\n%s\n%s\n\n%s', v_log_stack, v_error1_stack, v_error2_stack, v_error3_stack, v_error4_stack) WHERE id_istanza = idIstanza;

    RETURN TRUE; -- do not sto cosmo process
END;
$function$
;

CREATE OR REPLACE FUNCTION fnc_create_idf_pratica(idistanza bigint, justcheck boolean DEFAULT false)
 RETURNS boolean
 LANGUAGE plpgsql
AS $function$
DECLARE
    esito VARCHAR := 'ko';
    v_error_stack text;
    idPraticaIDF int8;
    idTipologia int;
    tipoControllo varchar;
    justCheckResult bool := false;
BEGIN



    SELECT CASE 
                WHEN mti.id_tipo_istanza = 'PDC' THEN
                    CASE 
                        --      TAB_VINC_2.vincolo_idrogeologico == "categoria_lettera_a"
                        WHEN json_data->'TAB_VINC_2'->>'vincolo_idrogeologico' = 'categoria_lettera_a' 
                        	THEN 1 -- comunale
                        --      TAB_VINC_2.vincolo_idrogeologico == "categoria_lettera_b_c" && (TAB_VINC_2.radio_categoria_b_c == "autorizzazione_provincia_superiore_5000_inferiore_30000" || TAB_VINC_2.radio_categoria_b_c == "autorizzazione_provincia_superiore_30000")
                        WHEN json_data->'TAB_VINC_2'->>'vincolo_idrogeologico' = 'categoria_lettera_b_c' 
                                AND (json_data->'TAB_VINC_2'->>'radio_categoria_b_c' = 'autorizzazione_provincia_superiore_5000_inferiore_30000'
                                        OR json_data->'TAB_VINC_2'->>'radio_categoria_b_c' = 'autorizzazione_provincia_superiore_30000')
	                        THEN 2 -- regionale
						ELSE null
                    END
                WHEN mti.id_tipo_istanza IN ('SCIA', 'SCIA-ALT-PDC', 'CILA') THEN
                    CASE 
                        --      TAB_VINC_2.vincolo_idrogeologico == "dichiarapere_rientrano_fra_specifica_autorizzazione" && TAB_VINC_2.scelta_vincolo_idrogeologico == "nellaPresenteIstanzaEContenutaLaDocumentazioneNecessariaAiFiniDelRilascioDellautorizzazione"
                        WHEN json_data->'TAB_VINC_2'->>'vincolo_idrogeologico' = 'dichiarapere_rientrano_fra_specifica_autorizzazione' 
                        		AND json_data->'TAB_VINC_2'->>'scelta_vincolo_idrogeologico' = 'nellaPresenteIstanzaEContenutaLaDocumentazioneNecessariaAiFiniDelRilascioDellautorizzazione'
    	                    THEN 1 -- comunale
						ELSE null
                    END
            END
        INTO idTipologia
        FROM mudeopen_t_istanza mti WHERE id_istanza =  idistanza;

    SELECT id_pratica_idf INTO idPraticaIdf FROM mudeopen_t_pratica_idf WHERE id_istanza = idistanza;
    IF idPraticaIdf IS NOT NULL THEN
        RETURN TRUE; -- already processed
    END IF;

    IF idTipologia IS NOT NULL THEN
        INSERT INTO mudeopen_t_pratica_idf (id_istanza, codice_stato_idf, competenza) VALUES (idistanza, 'IN_CODA', idTipologia);
        idPraticaIDF := LASTVAL();
        IF idPraticaIDF IS NULL THEN
            RAISE EXCEPTION 'ERROR CREATING mudeopen_t_pratica_idf RECORD!';
        ELSE
            RETURN TRUE; -- set db_script to 'OK' in order to allow the scheduler to process it
        END IF;
    END IF;
     
    RETURN FALSE; -- not an IDF instance, so set mudeopen_t_istanze_ex->db_scriptto 'NA'
END;
$function$
;

CREATE OR REPLACE FUNCTION fnc_set_istanza_pagata(idistanza integer)
 RETURNS boolean
 LANGUAGE plpgsql
AS $function$
    BEGIN
UPDATE mudeopen_t_istanza 
    SET json_data = jsonb_set(jsonb_set(jsonb_set(jsonb_set(jsonb_set(jsonb_set(jsonb_set(jsonb_set(jsonb_set(jsonb_set(json_data, 
        ARRAY['QDR_PAGAMENTO', 'importi', '9', '_statoPagamento'], '"completato"'::jsonb, true),
        ARRAY['QDR_PAGAMENTO', 'importi', '8', '_statoPagamento'], '"completato"'::jsonb, true),
        ARRAY['QDR_PAGAMENTO', 'importi', '7', '_statoPagamento'], '"completato"'::jsonb, true),
        ARRAY['QDR_PAGAMENTO', 'importi', '6', '_statoPagamento'], '"completato"'::jsonb, true),
        ARRAY['QDR_PAGAMENTO', 'importi', '5', '_statoPagamento'], '"completato"'::jsonb, true),
        ARRAY['QDR_PAGAMENTO', 'importi', '4', '_statoPagamento'], '"completato"'::jsonb, true),
        ARRAY['QDR_PAGAMENTO', 'importi', '3', '_statoPagamento'], '"completato"'::jsonb, true),
        ARRAY['QDR_PAGAMENTO', 'importi', '2', '_statoPagamento'], '"completato"'::jsonb, true),
        ARRAY['QDR_PAGAMENTO', 'importi', '1', '_statoPagamento'], '"completato"'::jsonb, true),
        ARRAY['QDR_PAGAMENTO', 'importi', '0', '_statoPagamento'], '"completato"'::jsonb, true)
    WHERE id_istanza = idIstanza;

        RETURN TRUE;
    END;
$function$
;

CREATE OR REPLACE FUNCTION fnc_simplify_jsondata(idistanza bigint)
 RETURNS boolean
 LANGUAGE plpgsql
AS $function$
DECLARE
    tableRow RECORD;
BEGIN
    
    FOR tableRow IN SELECT keyid, ('[' || array_to_string(array_agg(obj::varchar), ', ') || ']') as ruoli
							    FROM 
							        (SELECT mti2.id_istanza, tbl.keyid, (json_data #>> ARRAY['QDR_SOGGETTO_COINV', 'subjectList', tbl.keyid, 'roles'])::jsonb as jsonallegato 
							            FROM (select id_istanza, jsonb_object_keys(json_data->'QDR_SOGGETTO_COINV'->'subjectList') as keyid from mudeopen_t_istanza mti ) tbl
							                INNER JOIN mudeopen_t_istanza mti2 ON mti2.id_istanza =tbl.id_istanza
							                ) mti
							        , jsonb_array_elements(mti.jsonallegato) with ordinality ruoli(obj, position)
							    WHERE mti.id_istanza=idIstanza and obj->>'checked' = 'true'
							    group by keyid
	LOOP
		UPDATE mudeopen_t_istanza mti 
		    SET json_data = jsonb_set(json_data, 
		        ARRAY['QDR_SOGGETTO_COINV', 'subjectList', tableRow.keyid, 'roles'],
				tableRow.ruoli::jsonb, true)
		    WHERE mti.id_istanza = idIstanza;

    END LOOP;

    RETURN TRUE;
END;
$function$
;

CREATE OR REPLACE FUNCTION fnc_store_extra_fields(idistanza integer)
 RETURNS boolean
 LANGUAGE plpgsql
AS $function$
DECLARE
    v_error1_stack text;
    v_error2_stack text;
    v_error3_stack text;
    v_error4_stack text;

    istanzaRow RECORD;
BEGIN
    
    FOR istanzaRow IN SELECT * FROM mudeopen_t_istanza mti WHERE id_istanza = idIstanza
	LOOP
        if istanzaRow.json_data->'BO_EXTRA_FIELDS' IS NOT NULL then
		    RAISE NOTICE E' data_effettiva_inizio_lavori: %\n data_prot_inizio_lavori : %\n num_prot_inizio_lavori: %\n data_effettiva_fine_lavori: %\n data_prot_fine_lavori: %\n num_prot_fine_lavori: %\n data_prot_nomina_coll: %\n protocollo_nomina_coll : %\n dataprotcertificato: %\n numprotcertificato: %\n data_prot_collaudo: %\n protocollo_collaudo : %\n data_prot_DRE: %\n protocollo_DRE: %\n data_prot_RSU: %\n protocollo_RSU: %\n datacompilazione: %\n notesism: %',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_01_data_inizio_lavori_effettiva',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_04_Data_comunicazione_inizio_lavori',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_05_Num_protocollo_comunicazione_inizio_lavori',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_07_Data_fine_lavori_effettiva',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_10_Data_comunicazione_fine_lavori',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_11_Num_protocollo_comunicazione_fine_lavori',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_13_Data_comunicazione_nomina_collaudatore',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_14_Num_protocollo_nomina_collaudatore',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_16_Data_presentazione_certificato',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_17_Num_protocollo_certificato',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_19_Data_comunicazione_collaudo',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_20_Num_protocollo_collaudo',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_22_Data_comunicazione_DRE',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_23_Num_protocollo_comunicazione_DRE',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_25_Data_comunicazione_RSU',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_26_Num_protocollo_comunicazione_RSU',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_28_Data_compilazione_pratica',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_31_Note';

			INSERT INTO mudeopen_t_aeos (id_istanza, 
								data_effettiva_inizio_lavori,
								data_prot_inizio_lavori ,
								num_prot_inizio_lavori,
								data_effettiva_fine_lavori,
								data_prot_fine_lavori,
								num_prot_fine_lavori,
								data_prot_nomina_coll,
								protocollo_nomina_coll ,
								dataprotcertificato,
								numprotcertificato,
								data_prot_collaudo,
								protocollo_collaudo ,
								data_prot_DRE,
								protocollo_DRE,
								data_prot_RSU,
								protocollo_RSU,
								datacompilazione,
								notesism,
								data_inizio,
								data_fine,
								loguser,
								data_modifica)
			VALUES (idIstanza,
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_01_data_inizio_lavori_effettiva',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_04_Data_comunicazione_inizio_lavori',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_05_Num_protocollo_comunicazione_inizio_lavori',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_07_Data_fine_lavori_effettiva',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_10_Data_comunicazione_fine_lavori',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_11_Num_protocollo_comunicazione_fine_lavori',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_13_Data_comunicazione_nomina_collaudatore',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_14_Num_protocollo_nomina_collaudatore',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_16_Data_presentazione_certificato',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_17_Num_protocollo_certificato',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_19_Data_comunicazione_collaudo',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_20_Num_protocollo_collaudo',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_22_Data_comunicazione_DRE',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_23_Num_protocollo_comunicazione_DRE',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_25_Data_comunicazione_RSU',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_26_Num_protocollo_comunicazione_RSU',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_28_Data_compilazione_pratica',
						istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_31_Note',
						now(),
						null,
						CONCAT('ID:',istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'_LASTUSERID'),
						now())
			ON CONFLICT (id_istanza) DO UPDATE
			SET 
				data_effettiva_inizio_lavori=            istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_01_data_inizio_lavori_effettiva',
				data_prot_inizio_lavori =                istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_04_Data_comunicazione_inizio_lavori',
				num_prot_inizio_lavori=                  istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_05_Num_protocollo_comunicazione_inizio_lavori',
				data_effettiva_fine_lavori=              istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_07_Data_fine_lavori_effettiva',
				data_prot_fine_lavori=                   istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_10_Data_comunicazione_fine_lavori',
				num_prot_fine_lavori=                    istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_11_Num_protocollo_comunicazione_fine_lavori',
				data_prot_nomina_coll=                   istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_13_Data_comunicazione_nomina_collaudatore',
				protocollo_nomina_coll =                 istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_14_Num_protocollo_nomina_collaudatore',
				dataprotcertificato=                     istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_16_Data_presentazione_certificato',
				numprotcertificato=                      istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_17_Num_protocollo_certificato',
				data_prot_collaudo=                      istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_19_Data_comunicazione_collaudo',
				protocollo_collaudo =                    istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_20_Num_protocollo_collaudo',
				data_prot_DRE=                           istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_22_Data_comunicazione_DRE',
				protocollo_DRE=                          istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_23_Num_protocollo_comunicazione_DRE',
				data_prot_RSU=                           istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_25_Data_comunicazione_RSU',
				protocollo_RSU=                          istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_26_Num_protocollo_comunicazione_RSU',
				datacompilazione=			 			 istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_28_Data_compilazione_pratica',
				notesism=					    		 istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'TB1_31_Note',
				loguser=								 CONCAT('ID:',istanzaRow.json_data->'BO_EXTRA_FIELDS'->>'_LASTUSERID'),
				data_modifica=now();
	    end if;
    END LOOP;

    RETURN TRUE;
EXCEPTION 
  WHEN OTHERS THEN
    GET STACKED DIAGNOSTICS v_error1_stack = MESSAGE_TEXT,
                            v_error2_stack = PG_EXCEPTION_CONTEXT,
                            v_error3_stack = PG_EXCEPTION_HINT,
                            v_error4_stack = PG_EXCEPTION_DETAIL;
    RAISE NOTICE E'EXCEPTION: %\n%\n%\n%', v_error1_stack, v_error2_stack, v_error3_stack, v_error4_stack;

    RETURN FALSE;
END;
$function$
;

CREATE OR REPLACE FUNCTION func_create_numero_pratica_cosmo_seq(utr_siglaprov character varying)
 RETURNS character varying
 LANGUAGE plpgsql
AS $function$declare
  v_str varchar;
  v_year varchar;
BEGIN
    SELECT to_char(now(), 'yyyy') INTO v_year;
    v_str := 'mudeopen_numero_pratica_cosmo_' || utr_siglaprov || '_seq';
    EXECUTE  'CREATE SEQUENCE ' || v_str || ';';
    RETURN v_str;
EXCEPTION 
  WHEN OTHERS THEN
    RETURN v_str;
END$function$
;



--
-- PostgreSQL database dump complete
--

