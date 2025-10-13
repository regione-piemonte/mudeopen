/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.filter.iride.entity;

import java.io.Serializable;

import it.csi.mudeopen.mudeopensrvapi.filter.iride.exception.MalformedIdTokenException;

/**
 * The type Identita.
 */
public class Identita implements Serializable
{
    private String nome;
    private String cognome;
    private String codFiscale;
    private String idProvider;
    private String timestamp;
    private int livelloAutenticazione;
    private String mac;
    /**
     * The constant AUTENTICAZIONE_USERNAME_PASSWORD_UNVERIFIED.
     */
    public static final int AUTENTICAZIONE_USERNAME_PASSWORD_UNVERIFIED = 1;
    /**
     * The constant AUTENTICAZIONE_USERNAME_PASSWORD.
     */
    public static final int AUTENTICAZIONE_USERNAME_PASSWORD = 2;
    /**
     * The constant AUTENTICAZIONE_USERNAME_PASSWORD_PIN.
     */
    public static final int AUTENTICAZIONE_USERNAME_PASSWORD_PIN = 4;
    /**
     * The constant AUTENTICAZIONE_CERTIFICATO.
     */
    public static final int AUTENTICAZIONE_CERTIFICATO = 8;
    /**
     * The constant AUTENTICAZIONE_CERTIFICATO_FORTE.
     */
    public static final int AUTENTICAZIONE_CERTIFICATO_FORTE = 16;

    /**
     * Instantiates a new Identita.
     *
     * @param codFiscale            the cod fiscale
     * @param nome                  the nome
     * @param cognome               the cognome
     * @param idProvider            the id provider
     * @param timestamp             the timestamp
     * @param livelloAutenticazione the livello autenticazione
     */
    public Identita(final String codFiscale, final String nome, final String cognome, final String idProvider, final String timestamp, final int livelloAutenticazione) {
        this.codFiscale = codFiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.idProvider = idProvider;
        this.timestamp = timestamp;
        this.livelloAutenticazione = livelloAutenticazione;
    }

    /**
     * Instantiates a new Identita.
     */
    public Identita() {
        this.codFiscale = null;
        this.nome = null;
        this.cognome = null;
        this.idProvider = null;
        this.timestamp = null;
        this.livelloAutenticazione = 0;
    }

    /**
     * Instantiates a new Identita.
     *
     * @param token the token
     * @throws MalformedIdTokenException the malformed id token exception
     */
    public Identita(final String token) throws MalformedIdTokenException {
        final int slash1Index = token.indexOf(47);
        if (slash1Index == -1) {
            throw new MalformedIdTokenException(token);
        }
        this.codFiscale = token.substring(0, slash1Index);
        final int slash2Index = token.indexOf(47, slash1Index + 1);
        if (slash2Index == -1) {
            throw new MalformedIdTokenException(token);
        }
        this.nome = token.substring(slash1Index + 1, slash2Index);
        final int slash3Index = token.indexOf(47, slash2Index + 1);
        if (slash3Index == -1) {
            throw new MalformedIdTokenException(token);
        }
        this.cognome = token.substring(slash2Index + 1, slash3Index);
        final int slash4Index = token.indexOf(47, slash3Index + 1);
        if (slash4Index == -1) {
            throw new MalformedIdTokenException(token);
        }
        this.idProvider = token.substring(slash3Index + 1, slash4Index);
        final int slash5Index = token.indexOf(47, slash4Index + 1);
        if (slash5Index == -1) {
            throw new MalformedIdTokenException(token);
        }
        this.timestamp = token.substring(slash4Index + 1, slash5Index);
        final int slash6Index = token.indexOf(47, slash5Index + 1);
        if (slash6Index == -1) {
            throw new MalformedIdTokenException(token);
        }
        this.livelloAutenticazione = Integer.parseInt(token.substring(slash5Index + 1, slash6Index));
        this.mac = token.substring(slash6Index + 1);
    }

    /**
     * Gets nome.
     *
      the nome
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Gets cognome.
     *
      the cognome
     */
    public String getCognome() {
        return this.cognome;
    }

    /**
     * Gets cod fiscale.
     *
      the cod fiscale
     */
    public String getCodFiscale() {
        return this.codFiscale;
    }

    /**
     * Gets id provider.
     *
      the id provider
     */
    public String getIdProvider() {
        return this.idProvider;
    }

    /**
     * Gets timestamp.
     *
      the timestamp
     */
    public String getTimestamp() {
        return this.timestamp;
    }

    /**
     * Gets livello autenticazione.
     *
      the livello autenticazione
     */
    public int getLivelloAutenticazione() {
        return this.livelloAutenticazione;
    }

    /**
     * Gets mac.
     *
      the mac
     */
    public String getMac() {
        return this.mac;
    }

    /**
     * Sets mac.
     *
     * @param mac the mac
     */
    public void setMac(final String mac) {
        this.mac = mac;
    }

    public boolean equals(final Object obj) {
        if (obj != null && this.getClass().equals(obj.getClass())) {
            final Identita that = (Identita)obj;
            return ((this.nome == null && that.nome == null) || (this.nome != null && this.nome.equals(that.nome))) && ((this.cognome == null && that.cognome == null) || (this.cognome != null && this.cognome.equals(that.cognome))) && ((this.codFiscale == null && that.codFiscale == null) || (this.codFiscale != null && this.codFiscale.equals(that.codFiscale)));
        }
        return false;
    }

    public int hashCode() {
        return String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.codFiscale))).append(this.nome).append(this.cognome).append(this.mac))).hashCode();
    }

    public String toString() {
        return String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.codFiscale))).append("/").append(this.nome).append("/").append(this.cognome).append("/").append(this.idProvider).append("/").append(this.timestamp).append("/").append(this.livelloAutenticazione).append("/").append(this.mac)));
    }

    /**
     * Gets rappresentazione interna.
     *
      the rappresentazione interna
     */
    public String getRappresentazioneInterna() {
        return String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(this.codFiscale))).append("/").append(this.nome).append("/").append(this.cognome).append("/").append(this.idProvider).append("/").append(this.timestamp).append("/").append(this.livelloAutenticazione)));
    }

    /**
     * Sets nome.
     *
     * @param nome the nome
     */
    public void setNome(final String nome) {
        this.nome = nome;
    }

    /**
     * Sets timestamp.
     *
     * @param timestamp the timestamp
     */
    public void setTimestamp(final String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Sets livello autenticazione.
     *
     * @param livelloAutenticazione the livello autenticazione
     */
    public void setLivelloAutenticazione(final int livelloAutenticazione) {
        this.livelloAutenticazione = livelloAutenticazione;
    }

    /**
     * Sets id provider.
     *
     * @param idProvider the id provider
     */
    public void setIdProvider(final String idProvider) {
        this.idProvider = idProvider;
    }

    /**
     * Sets cognome.
     *
     * @param cognome the cognome
     */
    public void setCognome(final String cognome) {
        this.cognome = cognome;
    }

    /**
     * Sets cod fiscale.
     *
     * @param codFiscale the cod fiscale
     */
    public void setCodFiscale(final String codFiscale) {
        this.codFiscale = codFiscale;
    }
}