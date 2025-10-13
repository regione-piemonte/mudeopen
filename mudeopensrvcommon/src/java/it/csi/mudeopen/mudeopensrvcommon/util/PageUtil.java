/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public class PageUtil {

    public static Pageable getPageable(int page, int size, String sortExp) {
		if(StringUtils.isBlank(sortExp)) {
			return getPageable(page, size);
		}else {
			List <Order> orders = new ArrayList <Order>();
			try {
				String[] sortList = sortExp.split(",");
				for(String sort : sortList) {
					sort = sort.trim();
					if(sort.startsWith("+")) {
						orders.add(new Order(Direction.ASC, sort.substring(sort.indexOf("+")+1)));
					}else if(sort.startsWith("-")){
						orders.add(new Order(Direction.DESC, sort.substring(sort.indexOf("-")+1)));
					}
				}
				if(orders.size()>0) {
					return new PageRequest(page, size, new Sort(orders));
				}else {
					return getPageable(page, size);
				}
			}catch(Throwable t) {
				return getPageable(page, size);
			}
		}
	}
	
	private static Pageable getPageable(int page, int size) {
		return new PageRequest(page, size);
	}
}