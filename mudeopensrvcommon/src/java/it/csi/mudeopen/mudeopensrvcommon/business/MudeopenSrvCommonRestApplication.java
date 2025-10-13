/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business;
import it.csi.mudeopen.mudeopensrvcommon.business.be.web.CustomReadExceptionMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.web.impl.*;
import it.csi.mudeopen.mudeopensrvcommon.util.ExceptionHandler;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;
//@Component
@ApplicationPath("rest")
public class MudeopenSrvCommonRestApplication extends Application{
	private Set<Object> singletons = new HashSet<>();

    private Set<Class<?>> empty = new HashSet<>();
    public MudeopenSrvCommonRestApplication(){
    }

    @Override
    public Set<Class<?>> getClasses() {
         return empty;
    }

    @Override
    public Set<Object> getSingletons() {
         return singletons;
    }
}