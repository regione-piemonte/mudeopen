/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package  it.csi.mudeopen.mudeopenfoweb.business;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import it.csi.mudeopen.mudeopensrvcommon.util.ExceptionHandler;
import it.csi.mudeopen.mudeopenfoweb.business.be.web.impl.CustomReadExceptionMapper;
import it.csi.mudeopen.mudeopenfoweb.business.be.web.impl.IstanzeApiServiceImpl;
import it.csi.mudeopen.mudeopenfoweb.business.be.web.impl.LuoghiApiServiceImpl;
import it.csi.mudeopen.mudeopenfoweb.business.be.web.impl.PingApiServiceImpl;
import it.csi.mudeopen.mudeopenfoweb.business.be.web.impl.UtentiApiServiceImpl;
import it.csi.mudeopen.mudeopenfoweb.business.be.web.impl.WizardApiServiceImpl;
/**
 * The type Mudeopenfobe rest application.
 */
@ApplicationPath("/rest")
public class MudeopenfobeRestApplication extends Application{
	private Set<Object> singletons = new HashSet<>();

    private Set<Class<?>> empty = new HashSet<>();
    /**
     * Instantiates a new Mudeopenfobe rest application.
     */
    public MudeopenfobeRestApplication(){
         singletons.add(new PingApiServiceImpl());
         singletons.add(new UtentiApiServiceImpl());
         singletons.add(new IstanzeApiServiceImpl());
         singletons.add(new LuoghiApiServiceImpl());
         singletons.add(new CustomReadExceptionMapper());
         singletons.add(new ExceptionHandler());
         singletons.add(new WizardApiServiceImpl());
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