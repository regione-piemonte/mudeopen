/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.util.oauth2;
public class Util {
    public static String maskForLog(String in) {
		if (in != null && in.length() > 5) {
			int n = in.length() / 3;
			String pre = in.substring(0, n);
			String end = in.substring(in.length() - n);
			StringBuffer sb = new StringBuffer();
			sb.append(pre);
			for (int i = 0; i < in.length() - 2 * n; i++)
				sb.append("*");
			sb.append(end);
			return sb.toString();
		}

		return "*****";
	}
}