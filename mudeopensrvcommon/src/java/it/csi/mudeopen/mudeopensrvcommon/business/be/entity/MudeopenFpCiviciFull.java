/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mudeopen_fp_civici_full")
public class MudeopenFpCiviciFull {

    @Id
    @Column(name="id_civico")
    private Long idCivico;

    @Column(name="codice_via")
    private Long codiceVia;

    @Column(name="numero_radice")
    private Long numeroRadice;

    @Column(name="bis_ter")
    private Long bisTer;

    @Column(name="interno_1")
    private String interno1;

    @Column(name="bis_interno_1")
    private Long bisInterno1;

    @Column(name="interno_2")
    private String interno2;

    @Column(name="bis_interno_2")
    private Long bisInterno2;

    @Column(name="scala")
    private String scala;

    @Column(name="secondario")
    private String secondario;

    @Column(name="codice_stato_civico")
    private Long codiceStatoCivico;

    @Column(name="codice_tipologia_civico")
    private String codiceTipologiaCivico;

    @Column(name="codice_abitativo")
    private Long codiceAbitativo;

    @Column(name="flag_carraio")
    private Long flagCarraio;

    @Column(name="flag_fronte_via")
    private Long flagFronteVia;

    @Column(name="cap")
    private Long cap;

    @Column(name="flag_indirizzo_convenzionale")
    private Long flagIndirizzoConvenzionale;

    @Column(name="codice_sezione_censimento")
    private Long codiceSezioneCensimento;

    @Column(name="codice_quartiere")
    private Long codiceQuartiere;

    @Column(name="codice_sezione_vigili_urbani")
    private Long codiceSezioneVigiliUrbani;

    @Column(name="codice_circoscrizione")
    private Long codiceCircoscrizione;

    @Column(name="lettera_distretto_assistenza")
    private String letteraDistrettoAssistenza;

    @Column(name="desc_zona_statistica")
    private String descZonaStatistica;

    @Column(name="desc_raggruppamento_statistico")
    private String descRaggruppamentoStatistico;

    @Column(name="cod_localita_istat")
    private Long codLocalitaIstat;

    @Column(name="sezione_elettorale")
    private Long sezioneElettorale;

    @Column(name="collegio_camerale")
    private Long collegioCamerale;

    @Column(name="collegio_senatoriale")
    private Long collegioSenatoriale;

    @Column(name="collegio_provinciale")
    private Long collegioProvinciale;

    @Column(name="asl")
    private Long asl;

    @Column(name="codice_parrocchia")
    private Long codiceParrocchia;

    @Column(name="ztl")
    private Long ztl;

    @Column(name="coordinata_x")
    private Double coordinataX;

    @Column(name="coordinata_y")
    private Double coordinataY;

    @Column(name="geometry")
    private String geometry;

    @Column(name="pk_fp_civici_full")
    private Long pkFpCiviciFull;

    public Long getIdCivico() {
        return idCivico;
    }

    public void setIdCivico(Long idCivico) {
        this.idCivico = idCivico;
    }

    public Long getCodiceVia() {
        return codiceVia;
    }

    public void setCodiceVia(Long codiceVia) {
        this.codiceVia = codiceVia;
    }

    public Long getNumeroRadice() {
        return numeroRadice;
    }

    public void setNumeroRadice(Long numeroRadice) {
        this.numeroRadice = numeroRadice;
    }

    public Long getBisTer() {
        return bisTer;
    }

    public String getBisTerDecoded(){
        if(bisTer==null){
            return "";
        }
        switch(Integer.valueOf(""+bisTer)){
            case 1 : return "BIS";
            case 2 : return "TER";
            case 3 : return "QUATER";
            default: return "";
        }
    }

    public void setBisTer(Long bisTer) {
        this.bisTer = bisTer;
    }

    public String getInterno1() {
        return interno1;
    }

    public void setInterno1(String interno1) {
        this.interno1 = interno1;
    }

    public Long getBisInterno1() {
        return bisInterno1;
    }

    public void setBisInterno1(Long bisInterno1) {
        this.bisInterno1 = bisInterno1;
    }

    public String getBisInterno1Decoded(){
        if(bisInterno1==null){
            return "";
        }
        switch(Integer.valueOf(""+bisInterno1)){
            case 1 : return "BIS";
            case 2 : return "TER";
            case 3 : return "QUATER";
            default: return "";
        }
    }

    public String getInterno2() {
        return interno2;
    }

    public void setInterno2(String interno2) {
        this.interno2 = interno2;
    }

    public Long getBisInterno2() {
        return bisInterno2;
    }

    public void setBisInterno2(Long bisInterno2) {
        this.bisInterno2 = bisInterno2;
    }

    public String getScala() {
        return scala;
    }

    public void setScala(String scala) {
        this.scala = scala;
    }

    public String getSecondario() {
        return secondario;
    }

    public void setSecondario(String secondario) {
        this.secondario = secondario;
    }

    public Long getCodiceStatoCivico() {
        return codiceStatoCivico;
    }

    public void setCodiceStatoCivico(Long codiceStatoCivico) {
        this.codiceStatoCivico = codiceStatoCivico;
    }

    public String getCodiceTipologiaCivico() {
        return codiceTipologiaCivico;
    }

    public void setCodiceTipologiaCivico(String codiceTipologiaCivico) {
        this.codiceTipologiaCivico = codiceTipologiaCivico;
    }

    public Long getCodiceAbitativo() {
        return codiceAbitativo;
    }

    public void setCodiceAbitativo(Long codiceAbitativo) {
        this.codiceAbitativo = codiceAbitativo;
    }

    public Long getFlagCarraio() {
        return flagCarraio;
    }

    public void setFlagCarraio(Long flagCarraio) {
        this.flagCarraio = flagCarraio;
    }

    public Long getFlagFronteVia() {
        return flagFronteVia;
    }

    public void setFlagFronteVia(Long flagFronteVia) {
        this.flagFronteVia = flagFronteVia;
    }

    public Long getCap() {
        return cap;
    }

    public void setCap(Long cap) {
        this.cap = cap;
    }

    public Long getFlagIndirizzoConvenzionale() {
        return flagIndirizzoConvenzionale;
    }

    public void setFlagIndirizzoConvenzionale(Long flagIndirizzoConvenzionale) {
        this.flagIndirizzoConvenzionale = flagIndirizzoConvenzionale;
    }

    public Long getCodiceSezioneCensimento() {
        return codiceSezioneCensimento;
    }

    public void setCodiceSezioneCensimento(Long codiceSezioneCensimento) {
        this.codiceSezioneCensimento = codiceSezioneCensimento;
    }

    public Long getCodiceQuartiere() {
        return codiceQuartiere;
    }

    public void setCodiceQuartiere(Long codiceQuartiere) {
        this.codiceQuartiere = codiceQuartiere;
    }

    public Long getCodiceSezioneVigiliUrbani() {
        return codiceSezioneVigiliUrbani;
    }

    public void setCodiceSezioneVigiliUrbani(Long codiceSezioneVigiliUrbani) {
        this.codiceSezioneVigiliUrbani = codiceSezioneVigiliUrbani;
    }

    public Long getCodiceCircoscrizione() {
        return codiceCircoscrizione;
    }

    public void setCodiceCircoscrizione(Long codiceCircoscrizione) {
        this.codiceCircoscrizione = codiceCircoscrizione;
    }

    public String getLetteraDistrettoAssistenza() {
        return letteraDistrettoAssistenza;
    }

    public void setLetteraDistrettoAssistenza(String letteraDistrettoAssistenza) {
        this.letteraDistrettoAssistenza = letteraDistrettoAssistenza;
    }

    public String getDescZonaStatistica() {
        return descZonaStatistica;
    }

    public void setDescZonaStatistica(String descZonaStatistica) {
        this.descZonaStatistica = descZonaStatistica;
    }

    public String getDescRaggruppamentoStatistico() {
        return descRaggruppamentoStatistico;
    }

    public void setDescRaggruppamentoStatistico(String descRaggruppamentoStatistico) {
        this.descRaggruppamentoStatistico = descRaggruppamentoStatistico;
    }

    public Long getCodLocalitaIstat() {
        return codLocalitaIstat;
    }

    public void setCodLocalitaIstat(Long codLocalitaIstat) {
        this.codLocalitaIstat = codLocalitaIstat;
    }

    public Long getSezioneElettorale() {
        return sezioneElettorale;
    }

    public void setSezioneElettorale(Long sezioneElettorale) {
        this.sezioneElettorale = sezioneElettorale;
    }

    public Long getCollegioCamerale() {
        return collegioCamerale;
    }

    public void setCollegioCamerale(Long collegioCamerale) {
        this.collegioCamerale = collegioCamerale;
    }

    public Long getCollegioSenatoriale() {
        return collegioSenatoriale;
    }

    public void setCollegioSenatoriale(Long collegioSenatoriale) {
        this.collegioSenatoriale = collegioSenatoriale;
    }

    public Long getCollegioProvinciale() {
        return collegioProvinciale;
    }

    public void setCollegioProvinciale(Long collegioProvinciale) {
        this.collegioProvinciale = collegioProvinciale;
    }

    public Long getAsl() {
        return asl;
    }

    public void setAsl(Long asl) {
        this.asl = asl;
    }

    public Long getCodiceParrocchia() {
        return codiceParrocchia;
    }

    public void setCodiceParrocchia(Long codiceParrocchia) {
        this.codiceParrocchia = codiceParrocchia;
    }

    public Long getZtl() {
        return ztl;
    }

    public void setZtl(Long ztl) {
        this.ztl = ztl;
    }

    public Double getCoordinataX() {
        return coordinataX;
    }

    public void setCoordinataX(Double coordinataX) {
        this.coordinataX = coordinataX;
    }

    public Double getCoordinataY() {
        return coordinataY;
    }

    public void setCoordinataY(Double coordinataY) {
        this.coordinataY = coordinataY;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public Long getPkFpCiviciFull() {
        return pkFpCiviciFull;
    }

    public void setPkFpCiviciFull(Long pkFpCiviciFull) {
        this.pkFpCiviciFull = pkFpCiviciFull;
    }
}