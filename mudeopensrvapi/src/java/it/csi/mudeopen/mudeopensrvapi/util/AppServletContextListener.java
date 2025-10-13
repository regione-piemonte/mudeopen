/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package  it.csi.mudeopen.mudeopensrvapi.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * The type App servlet context listener.
 */
public class AppServletContextListener implements ServletContextListener {

	private static ServletContext sc;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		/* Sets the context in a static variable */
		AppServletContextListener.sc = sce.getServletContext();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

    /**
     * Gets servlet context.
     *
      the servlet context
     */
    public static ServletContext getServletContext() {
		return sc;
	}
}