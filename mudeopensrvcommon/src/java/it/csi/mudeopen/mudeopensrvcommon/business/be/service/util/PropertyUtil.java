/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.util;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeCProprieta;

public interface PropertyUtil {

    public static Long VERSION_ID = 1L;
    public static Long CODICE_PRODOTTO_ID = 2L;
    public static Long CODICE_LINEA_CLIENTE_ID = 3L;
    public static Long CODICE_AMBIENTE_ID = 4L;
    public static Long CODICE_UNITA_INSTALLAZIONE_ID = 5L;
    public static Long TEMPLATE_DIR_ID = 6L;
    public static Long MUDE_GEECO_SVISTA_GG_RETRAY=12L;

    //valori in {0,1}
    public static Long MUDE_PDFA_ENABLED=14L;
	public static Long MUDE_PDFA_FONT_EMBEDDED = 16L;

    public MudeCProprieta getPropertyValue(Long propertyId);
	
}