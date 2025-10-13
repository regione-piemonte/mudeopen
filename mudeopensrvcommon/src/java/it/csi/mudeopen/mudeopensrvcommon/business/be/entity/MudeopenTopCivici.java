/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.entity;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "mudeopen_top_civici")
public class MudeopenTopCivici extends BaseEntity {

    @Id
    @Column(name = "pk_sequ_civico")
    private Integer pkSequCivico;

    @Column(name = "pk_sequ_sto_civico")
    private Integer pkSequStoCivico;

    @Column(name = "uk_civico")
    private String ukCivico;

    @Column(name = "fk_comuni")
    private String fkComuni;

    @Column(name = "fk_strade")
    private String fkStrade;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "esp1")
    private String esp1;

    @Column(name = "esp2")
    private String esp2;

    @Column(name = "esp3")
    private String esp3;

    @Column(name = "esp4")
    private String esp4;

    @Column(name = "descrizione_civico")
    private String descrizioneCivico;

    @Column(name = "tipo_civico")
    private String tipoCivico;

    @Column(name = "tipo_accesso")
    private String tipoAccesso;

    @Column(name = "lato_strada")
    private String latoStrada;

    @Column(name = "accessibilita")
    private String accessibilita;

    @Column(name = "tabellato")
    private String tabellato;

    @Column(name = "cap")
    private String cap;

    @Column(name = "natura")
    private String natura;

    @Column(name = "foglio_ct")
    private String foglioCt;

    @Column(name = "particella_ct")
    private String particellaCt;

    @Column(name = "sezione_ct")
    private String sezioneCt;

    @Column(name = "denominatore_ct")
    private String denominatoreCt;

    @Column(name = "edificialita_ct")
    private String edificialitaCt;

    @Column(name = "uk_cat_particella_ct")
    private String ukCatParticellaCt;

    @Column(name = "uk_particella_ct")
    private String ukParticellaCt;

    @Column(name = "fk_sez_censimento")
    private String fkSezCensimento;

    @Column(name = "geometry")
    private String geometry;

    @Column(name = "geom_accesso")
    private String geomAccesso;

    @Column(name = "geom_percorso_accesso")
    private String geomPercorsoAccesso;

    @Column(name = "atto_deliberativo")
    private String attoDeliberativo;

    @Column(name = "data_attivazione")
    private LocalDate dataAttivazione;

    @Column(name = "data_rilievo")
    private LocalDate dataRilievo;

    @Column(name = "codice_rilevatore")
    private String codiceRilevatore;

    @Column(name = "corrispondenza_residenti")
    private String corrispondenzaResidenti;

    @Column(name = "denominaz_resid_o_attiv")
    private String denominazResidOAttiv;

    @Column(name = "codice_censimento")
    private String codiceCensimento;

    @Column(name = "note_censimento")
    private String noteCensimento;

    @Column(name = "note")
    private String note;

    @Column(name = "data_record")
    private LocalDate dataRecord;

    @Column(name = "fk_sez_elettorali")
    private String fkSezElettorali;

    @Column(name = "fk_zone_commerciali")
    private String fkZoneCommerciali;

    @Column(name = "fk_distretti_scolastici")
    private String fkDistrettiScolastici;

    @Column(name = "comune")
    private String comune;

    @Column(name = "fk_distretti_sanitari")
    private String fkDistrettiSanitari;

    @Column(name = "fk_circoscrizioni")
    private String fkCircoscrizioni;

    @Column(name = "chilometrica")
    private Integer chilometrica;

    @Column(name = "duplicato_terr")
    private Integer duplicatoTerr;

    @Column(name = "tabellato_censimento")
    private String tabellatoCensimento;

    @Column(name = "ex_strada")
    private String exStrada;

    @Column(name = "ex_civico")
    private String exCivico;

    @Column(name = "labelx")
    private Double labelx;

    @Column(name = "labely")
    private Double labely;

    @Column(name = "descr_dest_uso")
    private String descrDestUso;

    @Column(name = "descr_strada")
    private String descrStrada;

    @Column(name = "esc_foglio_ct")
    private String escFoglioCt;

    @Column(name = "esc_numero_ct")
    private String escNumeroCt;

    @Column(name = "descr_sez_censimento")
    private String descrSezCensimento;

    @Column(name = "descr_sez_elettorali")
    private String descrSezElettorali;

    @Column(name = "descr_zone_commerciali")
    private String descrZoneCommerciali;

    @Column(name = "descr_distretti_scolastici")
    private String descrDistrettiScolastici;

    @Column(name = "descr_distretti_sanitari")
    private String descrDistrettiSanitari;

    @Column(name = "descr_circoscrizioni")
    private String descrCircoscrizioni;

    @Column(name = "altra_dest_uso")
    private String altraDestUso;

    @Column(name = "descr_strada_ex")
    private String descrStradaEx;

    @Column(name = "xcentroid")
    private Integer xcentroid;

    @Column(name = "ycentroid")
    private Integer ycentroid;

    @Column(name = "fwidth")
    private Integer fwidth;

    @Column(name = "fheight")
    private Integer fheight;

    @Column(name = "codice_stato_civico")
    private Integer codiceStatoCivico;

    @Column(name = "uk_civico_old")
    private String ukCivicoOld;

    @Column(name = "url")
    private String url;

    public Integer getPkSequCivico() {
        return pkSequCivico;
    }

    public void setPkSequCivico(Integer pkSequCivico) {
        this.pkSequCivico = pkSequCivico;
    }

    public Integer getPkSequStoCivico() {
        return pkSequStoCivico;
    }

    public void setPkSequStoCivico(Integer pkSequStoCivico) {
        this.pkSequStoCivico = pkSequStoCivico;
    }

    public String getUkCivico() {
        return ukCivico;
    }

    public void setUkCivico(String ukCivico) {
        this.ukCivico = ukCivico;
    }

    public String getFkComuni() {
        return fkComuni;
    }

    public void setFkComuni(String fkComuni) {
        this.fkComuni = fkComuni;
    }

    public String getFkStrade() {
        return fkStrade;
    }

    public void setFkStrade(String fkStrade) {
        this.fkStrade = fkStrade;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getEsp1() {
        return esp1;
    }

    public void setEsp1(String esp1) {
        this.esp1 = esp1;
    }

    public String getEsp2() {
        return esp2;
    }

    public void setEsp2(String esp2) {
        this.esp2 = esp2;
    }

    public String getEsp3() {
        return esp3;
    }

    public void setEsp3(String esp3) {
        this.esp3 = esp3;
    }

    public String getEsp4() {
        return esp4;
    }

    public void setEsp4(String esp4) {
        this.esp4 = esp4;
    }

    public String getDescrizioneCivico() {
        return descrizioneCivico;
    }

    public void setDescrizioneCivico(String descrizioneCivico) {
        this.descrizioneCivico = descrizioneCivico;
    }

    public String getTipoCivico() {
        return tipoCivico;
    }

    public void setTipoCivico(String tipoCivico) {
        this.tipoCivico = tipoCivico;
    }

    public String getTipoAccesso() {
        return tipoAccesso;
    }

    public void setTipoAccesso(String tipoAccesso) {
        this.tipoAccesso = tipoAccesso;
    }

    public String getLatoStrada() {
        return latoStrada;
    }

    public void setLatoStrada(String latoStrada) {
        this.latoStrada = latoStrada;
    }

    public String getAccessibilita() {
        return accessibilita;
    }

    public void setAccessibilita(String accessibilita) {
        this.accessibilita = accessibilita;
    }

    public String getTabellato() {
        return tabellato;
    }

    public void setTabellato(String tabellato) {
        this.tabellato = tabellato;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getNatura() {
        return natura;
    }

    public void setNatura(String natura) {
        this.natura = natura;
    }

    public String getFoglioCt() {
        return foglioCt;
    }

    public void setFoglioCt(String foglioCt) {
        this.foglioCt = foglioCt;
    }

    public String getParticellaCt() {
        return particellaCt;
    }

    public void setParticellaCt(String particellaCt) {
        this.particellaCt = particellaCt;
    }

    public String getSezioneCt() {
        return sezioneCt;
    }

    public void setSezioneCt(String sezioneCt) {
        this.sezioneCt = sezioneCt;
    }

    public String getDenominatoreCt() {
        return denominatoreCt;
    }

    public void setDenominatoreCt(String denominatoreCt) {
        this.denominatoreCt = denominatoreCt;
    }

    public String getEdificialitaCt() {
        return edificialitaCt;
    }

    public void setEdificialitaCt(String edificialitaCt) {
        this.edificialitaCt = edificialitaCt;
    }

    public String getUkCatParticellaCt() {
        return ukCatParticellaCt;
    }

    public void setUkCatParticellaCt(String ukCatParticellaCt) {
        this.ukCatParticellaCt = ukCatParticellaCt;
    }

    public String getUkParticellaCt() {
        return ukParticellaCt;
    }

    public void setUkParticellaCt(String ukParticellaCt) {
        this.ukParticellaCt = ukParticellaCt;
    }

    public String getFkSezCensimento() {
        return fkSezCensimento;
    }

    public void setFkSezCensimento(String fkSezCensimento) {
        this.fkSezCensimento = fkSezCensimento;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public String getGeomAccesso() {
        return geomAccesso;
    }

    public void setGeomAccesso(String geomAccesso) {
        this.geomAccesso = geomAccesso;
    }

    public String getGeomPercorsoAccesso() {
        return geomPercorsoAccesso;
    }

    public void setGeomPercorsoAccesso(String geomPercorsoAccesso) {
        this.geomPercorsoAccesso = geomPercorsoAccesso;
    }

    public String getAttoDeliberativo() {
        return attoDeliberativo;
    }

    public void setAttoDeliberativo(String attoDeliberativo) {
        this.attoDeliberativo = attoDeliberativo;
    }

    public LocalDate getDataAttivazione() {
        return dataAttivazione;
    }

    public void setDataAttivazione(LocalDate dataAttivazione) {
        this.dataAttivazione = dataAttivazione;
    }

    public LocalDate getDataRilievo() {
        return dataRilievo;
    }

    public void setDataRilievo(LocalDate dataRilievo) {
        this.dataRilievo = dataRilievo;
    }

    public String getCodiceRilevatore() {
        return codiceRilevatore;
    }

    public void setCodiceRilevatore(String codiceRilevatore) {
        this.codiceRilevatore = codiceRilevatore;
    }

    public String getCorrispondenzaResidenti() {
        return corrispondenzaResidenti;
    }

    public void setCorrispondenzaResidenti(String corrispondenzaResidenti) {
        this.corrispondenzaResidenti = corrispondenzaResidenti;
    }

    public String getDenominazResidOAttiv() {
        return denominazResidOAttiv;
    }

    public void setDenominazResidOAttiv(String denominazResidOAttiv) {
        this.denominazResidOAttiv = denominazResidOAttiv;
    }

    public String getCodiceCensimento() {
        return codiceCensimento;
    }

    public void setCodiceCensimento(String codiceCensimento) {
        this.codiceCensimento = codiceCensimento;
    }

    public String getNoteCensimento() {
        return noteCensimento;
    }

    public void setNoteCensimento(String noteCensimento) {
        this.noteCensimento = noteCensimento;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDate getDataRecord() {
        return dataRecord;
    }

    public void setDataRecord(LocalDate dataRecord) {
        this.dataRecord = dataRecord;
    }

    public String getFkSezElettorali() {
        return fkSezElettorali;
    }

    public void setFkSezElettorali(String fkSezElettorali) {
        this.fkSezElettorali = fkSezElettorali;
    }

    public String getFkZoneCommerciali() {
        return fkZoneCommerciali;
    }

    public void setFkZoneCommerciali(String fkZoneCommerciali) {
        this.fkZoneCommerciali = fkZoneCommerciali;
    }

    public String getFkDistrettiScolastici() {
        return fkDistrettiScolastici;
    }

    public void setFkDistrettiScolastici(String fkDistrettiScolastici) {
        this.fkDistrettiScolastici = fkDistrettiScolastici;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public String getFkDistrettiSanitari() {
        return fkDistrettiSanitari;
    }

    public void setFkDistrettiSanitari(String fkDistrettiSanitari) {
        this.fkDistrettiSanitari = fkDistrettiSanitari;
    }

    public String getFkCircoscrizioni() {
        return fkCircoscrizioni;
    }

    public void setFkCircoscrizioni(String fkCircoscrizioni) {
        this.fkCircoscrizioni = fkCircoscrizioni;
    }

    public Integer getChilometrica() {
        return chilometrica;
    }

    public void setChilometrica(Integer chilometrica) {
        this.chilometrica = chilometrica;
    }

    public Integer getDuplicatoTerr() {
        return duplicatoTerr;
    }

    public void setDuplicatoTerr(Integer duplicatoTerr) {
        this.duplicatoTerr = duplicatoTerr;
    }

    public String getTabellatoCensimento() {
        return tabellatoCensimento;
    }

    public void setTabellatoCensimento(String tabellatoCensimento) {
        this.tabellatoCensimento = tabellatoCensimento;
    }

    public String getExStrada() {
        return exStrada;
    }

    public void setExStrada(String exStrada) {
        this.exStrada = exStrada;
    }

    public String getExCivico() {
        return exCivico;
    }

    public void setExCivico(String exCivico) {
        this.exCivico = exCivico;
    }

    public Double getLabelx() {
        return labelx;
    }

    public void setLabelx(Double labelx) {
        this.labelx = labelx;
    }

    public Double getLabely() {
        return labely;
    }

    public void setLabely(Double labely) {
        this.labely = labely;
    }

    public String getDescrDestUso() {
        return descrDestUso;
    }

    public void setDescrDestUso(String descrDestUso) {
        this.descrDestUso = descrDestUso;
    }

    public String getDescrStrada() {
        return descrStrada;
    }

    public void setDescrStrada(String descrStrada) {
        this.descrStrada = descrStrada;
    }

    public String getEscFoglioCt() {
        return escFoglioCt;
    }

    public void setEscFoglioCt(String escFoglioCt) {
        this.escFoglioCt = escFoglioCt;
    }

    public String getEscNumeroCt() {
        return escNumeroCt;
    }

    public void setEscNumeroCt(String escNumeroCt) {
        this.escNumeroCt = escNumeroCt;
    }

    public String getDescrSezCensimento() {
        return descrSezCensimento;
    }

    public void setDescrSezCensimento(String descrSezCensimento) {
        this.descrSezCensimento = descrSezCensimento;
    }

    public String getDescrSezElettorali() {
        return descrSezElettorali;
    }

    public void setDescrSezElettorali(String descrSezElettorali) {
        this.descrSezElettorali = descrSezElettorali;
    }

    public String getDescrZoneCommerciali() {
        return descrZoneCommerciali;
    }

    public void setDescrZoneCommerciali(String descrZoneCommerciali) {
        this.descrZoneCommerciali = descrZoneCommerciali;
    }

    public String getDescrDistrettiScolastici() {
        return descrDistrettiScolastici;
    }

    public void setDescrDistrettiScolastici(String descrDistrettiScolastici) {
        this.descrDistrettiScolastici = descrDistrettiScolastici;
    }

    public String getDescrDistrettiSanitari() {
        return descrDistrettiSanitari;
    }

    public void setDescrDistrettiSanitari(String descrDistrettiSanitari) {
        this.descrDistrettiSanitari = descrDistrettiSanitari;
    }

    public String getDescrCircoscrizioni() {
        return descrCircoscrizioni;
    }

    public void setDescrCircoscrizioni(String descrCircoscrizioni) {
        this.descrCircoscrizioni = descrCircoscrizioni;
    }

    public String getAltraDestUso() {
        return altraDestUso;
    }

    public void setAltraDestUso(String altraDestUso) {
        this.altraDestUso = altraDestUso;
    }

    public String getDescrStradaEx() {
        return descrStradaEx;
    }

    public void setDescrStradaEx(String descrStradaEx) {
        this.descrStradaEx = descrStradaEx;
    }

    public Integer getXcentroid() {
        return xcentroid;
    }

    public void setXcentroid(Integer xcentroid) {
        this.xcentroid = xcentroid;
    }

    public Integer getYcentroid() {
        return ycentroid;
    }

    public void setYcentroid(Integer ycentroid) {
        this.ycentroid = ycentroid;
    }

    public Integer getFwidth() {
        return fwidth;
    }

    public void setFwidth(Integer fwidth) {
        this.fwidth = fwidth;
    }

    public Integer getFheight() {
        return fheight;
    }

    public void setFheight(Integer fheight) {
        this.fheight = fheight;
    }

    public Integer getCodiceStatoCivico() {
        return codiceStatoCivico;
    }

    public void setCodiceStatoCivico(Integer codiceStatoCivico) {
        this.codiceStatoCivico = codiceStatoCivico;
    }

    public String getUkCivicoOld() {
        return ukCivicoOld;
    }

    public void setUkCivicoOld(String ukCivicoOld) {
        this.ukCivicoOld = ukCivicoOld;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "MudeopenTopCivici{" +
                "pkSequCivico=" + pkSequCivico +
                ", pkSequStoCivico=" + pkSequStoCivico +
                ", ukCivico=" + ukCivico +
                ", fkComuni=" + fkComuni +
                ", fkStrade=" + fkStrade +
                ", numero=" + numero +
                ", esp1=" + esp1 +
                ", esp2=" + esp2 +
                ", esp3=" + esp3 +
                ", esp4=" + esp4 +
                ", descrizioneCivico=" + descrizioneCivico +
                ", tipoCivico=" + tipoCivico +
                ", tipoAccesso=" + tipoAccesso +
                ", latoStrada=" + latoStrada +
                ", accessibilita=" + accessibilita +
                ", tabellato=" + tabellato +
                ", cap=" + cap +
                ", natura=" + natura +
                ", foglioCt=" + foglioCt +
                ", particellaCt=" + particellaCt +
                ", sezioneCt=" + sezioneCt +
                ", denominatoreCt=" + denominatoreCt +
                ", edificialitaCt=" + edificialitaCt +
                ", ukCatParticellaCt=" + ukCatParticellaCt +
                ", ukParticellaCt=" + ukParticellaCt +
                ", fkSezCensimento=" + fkSezCensimento +
                ", geometry='" + geometry + '\'' +
                ", geomAccesso='" + geomAccesso + '\'' +
                ", geomPercorsoAccesso='" + geomPercorsoAccesso + '\'' +
                ", attoDeliberativo=" + attoDeliberativo +
                ", dataAttivazione=" + dataAttivazione +
                ", dataRilievo=" + dataRilievo +
                ", codiceRilevatore=" + codiceRilevatore +
                ", corrispondenzaResidenti=" + corrispondenzaResidenti +
                ", denominazResidOAttiv=" + denominazResidOAttiv +
                ", codiceCensimento=" + codiceCensimento +
                ", noteCensimento=" + noteCensimento +
                ", note=" + note +
                ", dataRecord=" + dataRecord +
                ", fkSezElettorali=" + fkSezElettorali +
                ", fkZoneCommerciali=" + fkZoneCommerciali +
                ", fkDistrettiScolastici=" + fkDistrettiScolastici +
                ", comune=" + comune +
                ", fkDistrettiSanitari=" + fkDistrettiSanitari +
                ", fkCircoscrizioni=" + fkCircoscrizioni +
                ", chilometrica=" + chilometrica +
                ", duplicatoTerr=" + duplicatoTerr +
                ", tabellatoCensimento=" + tabellatoCensimento +
                ", exStrada=" + exStrada +
                ", exCivico=" + exCivico +
                ", labelx=" + labelx +
                ", labely=" + labely +
                ", descrDestUso=" + descrDestUso +
                ", descrStrada=" + descrStrada +
                ", escFoglioCt=" + escFoglioCt +
                ", escNumeroCt=" + escNumeroCt +
                ", descrSezCensimento=" + descrSezCensimento +
                ", descrSezElettorali=" + descrSezElettorali +
                ", descrZoneCommerciali=" + descrZoneCommerciali +
                ", descrDistrettiScolastici=" + descrDistrettiScolastici +
                ", descrDistrettiSanitari=" + descrDistrettiSanitari +
                ", descrCircoscrizioni=" + descrCircoscrizioni +
                ", altraDestUso=" + altraDestUso +
                ", descrStradaEx=" + descrStradaEx +
                ", xcentroid=" + xcentroid +
                ", ycentroid=" + ycentroid +
                ", fwidth=" + fwidth +
                ", fheight=" + fheight +
                ", codiceStatoCivico=" + codiceStatoCivico +
                ", ukCivicoOld=" + ukCivicoOld +
                ", url=" + url +
                '}';
    }
}

