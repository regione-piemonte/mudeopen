/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.mudeopen.mudeopensrvcommon.business.be.common.exception.BusinessException;
import it.csi.mudeopen.mudeopensrvcommon.vo.serializer.CustomDateTimeSerializer;
/**
 * The type Filter util.
 */
public class FilterUtil {
	public static Map getValue(String filter, String param) {
		if(StringUtils.isNotBlank(filter) ) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				Map<String, Map> filterVO = mapper.readValue(filter, HashMap.class);
				if(filterVO.containsKey(param) 
						&& filterVO.get(param).values().stream().anyMatch(x -> x!=null))
					return filterVO.get(param);
			}catch(Throwable t) {
			}
		}
		return null;
	}

	public static String getValueWS(String filter, String param) {
		if (StringUtils.isNotBlank(filter) ) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				Map<String, String> filterVO = mapper.readValue(filter, HashMap.class);
				if (filterVO.containsKey(param))
					return filterVO.get(param);
			}catch(Throwable t) {
			}
		}
		return null;
	}
	
	public static Number getValueNumWS(String filter, String param) {
		if (StringUtils.isNotBlank(filter) ) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				Map<String, Number> filterVO = mapper.readValue(filter, HashMap.class);
				if (filterVO.containsKey(param))
					return filterVO.get(param);
			}catch(Throwable t) {
			}
		}
		return null;
	}
	
	public static List getValues(String filter, String param) {
		if (StringUtils.isNotBlank(filter) ) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				Map<String, Map> filterVO = mapper.readValue(filter, HashMap.class);
				if (filterVO.containsKey(param))
					return (List) filterVO.get(param);
			}catch(Throwable t) {
			}
		}
		return null;
	}
//
//    /**
//     * Gets string value.
//     *
//     * @param filter the filter
//     * @param param  the param
//     * @return the string value
//     */
    public static String getStringValue(String filter, String param) {
    	return getStringValue(filter, param, "eq");
    }
	
    public static String getStringValue(String filter, String param, String op) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			HashMap<String, HashMap<String, String>> filterVO = mapper.readValue(filter, HashMap.class);
			if(filterVO.containsKey(param)) {
				HashMap<String, String> filterVOValue = filterVO.get(param);
				Object obj = filterVOValue.get(op);
				if(obj != null)
					if(obj instanceof String)
						return ((String)obj).trim();
					else
						return ""+obj;
			}
		} catch(Throwable t) { }
		
		return null;
	}

	public static List getListValue(String filter, String param) {
		if (StringUtils.isNotBlank(filter) ) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				HashMap<String, HashMap<String, Object>> filterVO = mapper.readValue(filter, HashMap.class);
				if (filterVO.containsKey(param)) {
					HashMap<String, Object> filterVOValue = filterVO.get(param);
					return (List) filterVOValue.get("in");
				}
			}catch(Throwable t) {
			}
		}
		return null;
	}
	
    /**
     * Gets long value.
     *
     * @param filter the filter
     * @param param  the param
     * @return the long value
     */
    public static Long getLongValue(String filter, String param) {
		Long ret = null;

		ObjectMapper mapper = new ObjectMapper();
		try {
			HashMap<String, HashMap<String, Integer>> filterVO = mapper.readValue(filter, HashMap.class);
			if(filterVO.containsKey(param)) {
				HashMap<String, Integer> filterVOValue = filterVO.get(StringUtils.trim(param));
				ret = filterVOValue.get("eq").longValue();
			}
		}catch(Throwable t) {
			try {
				// controllo se e' arrivato come stringa
				HashMap<String, HashMap<String, String>> filterVO = mapper.readValue(filter, HashMap.class);
				if(filterVO.containsKey(param)) {
					HashMap<String, String> filterVOValue = filterVO.get(param);
					ret = Long.parseLong(filterVOValue.get("eq"));
				}
			}catch(Throwable e) {}
		}
		return ret;
	}

	public static Boolean getBooleanValue(String filter, String param) {
		Boolean ret = null;

		ObjectMapper mapper = new ObjectMapper();
		try {
			HashMap<String, HashMap<String, Boolean>> filterVO = mapper.readValue(filter, HashMap.class);
			if(filterVO.containsKey(param)) {
				HashMap<String, Boolean> filterVOValue = filterVO.get(StringUtils.trim(param));
				for (String key : filterVOValue.keySet()) {

				}

				ret = filterVOValue.get("eq").booleanValue();
			}
		}catch(Throwable t) {
			try {
				// controllo se e' arrivato come stringa
				HashMap<String, HashMap<String, String>> filterVO = mapper.readValue(filter, HashMap.class);
				if(filterVO.containsKey(param)) {
					HashMap<String, String> filterVOValue = filterVO.get(param);
					ret = Boolean.parseBoolean(filterVOValue.get("eq"));
				}
			}catch(Throwable e) {}
		}
		return ret;
	}

    /**
     * Gets date from value.
     *
     * @param filter the filter
     * @param param  the param
     * @return the date from value
     */
    public static LocalDate getDateFromValue(String filter, String param) {
		LocalDate ret = null;

		ObjectMapper mapper = new ObjectMapper();
		try {
			HashMap<String, HashMap<String, String>> filterVO = mapper.readValue(filter, HashMap.class);
			if(filterVO.containsKey(param)) {
				HashMap<String, String> filterVOValue = filterVO.get(StringUtils.trim(param));
				CharSequence dateAsString = filterVOValue.get("gte");
				ret = LocalDate.parse(dateAsString, CustomDateTimeSerializer.DATE_FORMATTER);
			}
		}catch(Throwable t) {

		}
		return ret;
	}

    /**
     * Gets date to value.
     *
     * @param filter the filter
     * @param param  the param
     * @return the date to value
     */
    public static LocalDate getDateToValue(String filter, String param) {
		LocalDate ret = null;

		ObjectMapper mapper = new ObjectMapper();
		try {
			HashMap<String, HashMap<String, String>> filterVO = mapper.readValue(filter, HashMap.class);
			if(filterVO.containsKey(param)) {
				HashMap<String, String> filterVOValue = filterVO.get(StringUtils.trim(param));
				CharSequence dateAsString = filterVOValue.get("lte");
				ret = LocalDate.parse(dateAsString, CustomDateTimeSerializer.DATE_FORMATTER);
			}
		}catch(Throwable t) {

		}
		return ret;
	}

    /**
     * Gets date value.
     *
     * @param filter the filter
     * @param param  the param
     * @return the date value
     */
    public static LocalDateTime getDateValue(String filter, String param) {
		LocalDateTime ret = null;

		ObjectMapper mapper = new ObjectMapper();
		try {
			HashMap<String, HashMap<String, String>> filterVO = mapper.readValue(filter, HashMap.class);
			if(filterVO.containsKey(param)) {
				HashMap<String, String> filterVOValue = filterVO.get(StringUtils.trim(param));
				CharSequence dateAsString = filterVOValue.get("eq");
				ret = LocalDateTime.parse(dateAsString, CustomDateTimeSerializer.DATE_FORMATTER);
			}
		}catch(Throwable t) {

		}
		return ret;
	}
	
}