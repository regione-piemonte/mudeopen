/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon.factory;

import it.csi.ecogis.geeco_java_client.dto.internal.Geometry;
import it.csi.ecogis.geeco_java_client.dto.Configuration;

public interface GeecoConfiguration {

	public Configuration getGeecoConfiguration(String env,String urlQuit,Geometry geometry) throws Exception ; 
}
