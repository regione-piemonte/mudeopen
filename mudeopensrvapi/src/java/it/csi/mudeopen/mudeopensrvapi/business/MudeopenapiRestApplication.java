/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package  it.csi.mudeopen.mudeopensrvapi.business;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
/**
 * The type Mudeopenapi rest application.
 */
@ApplicationPath("/rest")
public class MudeopenapiRestApplication extends Application{
	private Set<Object> singletons = new HashSet<>();
    private Set<Class<?>> empty = new HashSet<>();

    /**
     * SQ constructor removed! 
     * Instantiates a new Mudeopenapi rest application.
     */
    @Override
    public Set<Class<?>> getClasses() {
         return empty;
    }
    @Override
    public Set<Object> getSingletons() {
         return singletons;
    }

}