/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.text.SimpleDateFormat;

public class JsonUtils {

    public static String toJsonString(Object obj) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(df);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static JSONObject toJsonObj(String json) throws JSONException {
        String sJson = toJsonString(json);
        JSONObject obj = new JSONObject(sJson);
        obj.remove("json_data");
        return obj;
    }

}