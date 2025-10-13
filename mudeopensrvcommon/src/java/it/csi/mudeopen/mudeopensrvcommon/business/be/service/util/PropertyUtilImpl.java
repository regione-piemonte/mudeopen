/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.util;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeCProprieta;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;

@Service
public class PropertyUtilImpl implements PropertyUtil{

	@Autowired
	private MudeCProprietaRepository mudeCProprietaRepository;
	
	private HashMap<Integer, MudeCProprieta> propertyList = new HashMap<Integer, MudeCProprieta>();
	
	@Override
	public MudeCProprieta getPropertyValue(Long propertyId) {

		if(propertyList.containsKey(propertyId.intValue())) {
			return propertyList.get(propertyId.intValue());
		}
		
		MudeCProprieta proprieta = mudeCProprietaRepository.findOne(propertyId.longValue());
		if(proprieta!=null) {
			propertyList.put(propertyId.intValue(), proprieta);
			return proprieta;
		}		
		return null;
	}

}