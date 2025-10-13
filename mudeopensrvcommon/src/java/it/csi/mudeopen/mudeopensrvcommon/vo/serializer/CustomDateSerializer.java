/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.vo.serializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomDateSerializer extends JsonSerializer<LocalDate> {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter FORMATTER_SORIS = DateTimeFormatter.ofPattern("ddMMyy");
    public static final DateTimeFormatter FORMATTER_DATE = DateTimeFormatter.ofPattern("ddMMyyyy");
    public static final TimeZone LOCAL_TIME_ZONE = TimeZone.getTimeZone("Europe/Rome");

	@Override
	public void serialize(LocalDate date, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
		if (date == null) {
			jsonGenerator.writeNull();
		} else {
			jsonGenerator.writeString(date.format(FORMATTER));
		}

	}

}