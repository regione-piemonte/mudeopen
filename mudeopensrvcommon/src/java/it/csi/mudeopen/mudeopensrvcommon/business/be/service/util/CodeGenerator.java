/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDComune;

public class CodeGenerator {

    public static String getNewCodiceFascicolo(Long entityId, MudeDComune comune) {

		// genero codiceFascicolo - 
		// il primo gruppo di due numeri e' il codice istat regionale
		// il secondo gruppo di 4 numeri e' il codice istat comunale
		// il terzo gruppo e' un progressivo di 10 numeri
		// l'ultimo e' l'anno
		
		String istatRegione = comune.getMudeDProvincia().getMudeDRegione().getCodRegione();
		String istatComune = comune.getIstatComune();
		String id = zeroPadding(entityId, 10);
		String anno = new SimpleDateFormat("YYYY").format(new Date());
		
		return istatRegione +"-"+ istatComune +"-"+ id +"-"+ anno;
	}

    public static String getNewCodiceIstanza(Long entityId, MudeDComune comune) {

		// genero codiceIstanza - 
		// il primo gruppo di due numeri e' il codice istat regionale
		// il secondo gruppo di 4 numeri e' il codice istat comunale
		// il terzo gruppo e' un progressivo di 10 numeri
		// l'ultimo e' l'anno
		
		String istatRegione = comune.getMudeDProvincia().getMudeDRegione().getCodRegione();
		String istatComune = comune.getIstatComune();
		String id = zeroPadding(entityId, 10);
		String anno = new SimpleDateFormat("YYYY").format(new Date());
		
		return istatRegione +"-"+ istatComune +"-"+ id +"-"+ anno;
	}

	private static String zeroPadding(Long id, int padding) {
		String zero = "0000000000000000000000000000000000000000000000000000000";
		String pad = zero.substring(0, padding);
		String idString = id.toString();
				
		return pad.substring(idString.length()) + idString;
	}

}