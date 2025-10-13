/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import it.csi.mudeopen.mudeopensrvcommon.vo.serializer.CustomDateTimeSerializer;

public interface UtilsDate {

    default Date asDate(LocalDate localDate) {
		if (localDate == null)
			return null;
		return Date.from(localDate.atStartOfDay().atZone(CustomDateTimeSerializer.LOCAL_TIME_ZONE.toZoneId()).toInstant());
	}

    default Date asDate(LocalDateTime localDateTime) {
		if (localDateTime == null)
			return null;
		return Date.from(localDateTime.atZone(CustomDateTimeSerializer.LOCAL_TIME_ZONE.toZoneId()).toInstant());
	}

    default LocalDate asLocalDate(Date date) {
		if (date == null)
			return null;
		return Instant.ofEpochMilli(date.getTime()).atZone(CustomDateTimeSerializer.LOCAL_TIME_ZONE.toZoneId()).toLocalDate();
	}

    default LocalDateTime asLocalDateTime(Date date) {
		if (date == null)
			return null;
		return Instant.ofEpochMilli(date.getTime()).atZone(CustomDateTimeSerializer.LOCAL_TIME_ZONE.toZoneId()).toLocalDateTime();
	}

    default Timestamp asTimeStamp(LocalDateTime localDateTime) {
		if (localDateTime == null)
			return null;
		return new Timestamp(Date.from(localDateTime.atZone(CustomDateTimeSerializer.LOCAL_TIME_ZONE.toZoneId()).toInstant()).getTime());
	}

    default Date add30DaysToDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 30);
		date = calendar.getTime();
		return date;
	}

    default Date getDate(String _data, String format) {
		Date data = null;
		try {
			if (_data == null) {

				return null;
			}

			if (format == null || format.trim().equals("")) {

				return null;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			sdf.setLenient(false);
			data = sdf.parse(_data);

			if (data != null && data.before(new Date(new GregorianCalendar(1880, Calendar.JANUARY, 1).getTimeInMillis()))) {

				return null;
			}
			if (data != null && data.after(new Date(new GregorianCalendar(2100, Calendar.JANUARY, 1).getTimeInMillis()))) {

				return null;
			}
		} catch (Exception e) {

		}

		return data;
	}

}