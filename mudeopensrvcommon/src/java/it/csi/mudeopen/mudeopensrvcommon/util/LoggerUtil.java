/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.util;

import org.apache.log4j.MDC;
import org.apache.log4j.Logger;

public class LoggerUtil {

    public static void debug(Logger logger, String message) {
		String header = (String) MDC.get(Constants.LOG_HEADER_NAME);
		logger.debug(header +" - " + message);
	};
    public static void info(Logger logger, String message) {

		String header = (String) MDC.get(Constants.LOG_HEADER_NAME);
		logger.info(header +" - " + message);
	}

    public static void warn(Logger logger, String message) {

		String header = (String) MDC.get(Constants.LOG_HEADER_NAME);
		logger.warn(header +" - " + message);
	}

    public static void warn(Logger logger, String message, Throwable t) {

		String header = (String) MDC.get(Constants.LOG_HEADER_NAME);
		logger.warn(header +" - " + message, t);
	}

    public static void error(Logger logger, String message) {

		String header = (String) MDC.get(Constants.LOG_HEADER_NAME);
		logger.error(header +" - " + message);
	}

    public static void error(Logger logger, String message, Throwable t) {

		String header = (String) MDC.get(Constants.LOG_HEADER_NAME);
		logger.error(header +" - " + message, t);
	}
}