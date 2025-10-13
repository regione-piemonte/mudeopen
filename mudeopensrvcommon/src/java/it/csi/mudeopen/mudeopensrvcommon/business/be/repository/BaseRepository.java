/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.repository;

import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.util.UserUtil;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, Long> /*, ApplicationContextAware */ {
	/*
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;   
    }

    public static ApplicationContext getContext() {
        return context;
    }

    @Resource
    HttpServletRequest servletRequest = null;

	ApplicationContext context = null;
		
    @Resource
    HttpHeaders httpHeaders = null;

	@Context
	public HttpServletRequest request;	

    */

    List<T> findAll(Specification<T> specs);

    List<T> findAll(Specification<T> specs, Sort sort);

    Page<T> findAll(Specification<T> specs, Pageable pageable);

    default <S extends T> S saveDAO(S entityTarget) {
    	setUserInfoIfExixts(entityTarget);
    	return save(entityTarget);
    }

    default void saveDAO(List<T> entityTarget) {
    	save(entityTarget);
    }

    default <S extends T> S saveAndFlushDAO(S entityTarget) {
    	setUserInfoIfExixts(entityTarget);
    	return saveAndFlush(entityTarget);
    }

    default public <S extends T> S setUserInfoIfExixts(S entityTarget) {
		Method m;

    	try {
    		//WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			// UserUtil headerUtil = context.getBean(UserUtil.class);
			// MudeTUser mudeTUser = headerUtil.getUserCF("", "", false);
        	
        	if((m = entityTarget.getClass().getMethod("setDataModifica", Date.class)) != null)
        		m.invoke(entityTarget, new Date());
        	
        	String cf = getHeader(Constants.HEADER_USER_CF);
        	if((m = entityTarget.getClass().getMethod("setLoguser", String.class)) != null && cf != null)
        		m.invoke(entityTarget, cf);
		} catch (Exception ignore) {}
    	
    	return entityTarget;
    }

    default public String getHeader(String name) {
        RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
        if (attribs instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes)attribs).getRequest();
            return request.getHeader(name);
        }
        return null;
    }

}