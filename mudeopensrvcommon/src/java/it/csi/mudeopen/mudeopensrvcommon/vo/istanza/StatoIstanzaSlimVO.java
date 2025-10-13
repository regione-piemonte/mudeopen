/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.istanza;
import java.util.Date;

public class StatoIstanzaSlimVO {
	
    Date dataInizio;
    String codice;
    String descrizione;
    String descrizioneEstesa;
    int livello;
    
    public StatoIstanzaSlimVO(Date dataInizio, String codice, String descrizione, String descrizioneEstesa, int livello) {
        this.dataInizio = dataInizio;
        this.codice = codice;
        this.descrizione = descrizione;
        this.descrizioneEstesa = descrizioneEstesa;
        this.livello = livello;
    }

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizioneEstesa() {
		return descrizioneEstesa;
	}

	public void setDescrizioneEstesa(String descrizioneEstesa) {
		this.descrizioneEstesa = descrizioneEstesa;
	}

	public int getLivello() {
		return livello;
	}

	public void setLivello(int livello) {
		this.livello = livello;
	}
}