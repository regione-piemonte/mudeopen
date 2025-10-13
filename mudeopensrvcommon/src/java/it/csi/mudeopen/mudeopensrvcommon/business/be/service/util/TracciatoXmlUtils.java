/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.util;

import it.csi.mudeopen.mudeopensrvcommon.business.ApplicationContextProvider;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoElenco;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRSpeciePraticaTipoIntervento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.CustomTracciatoXMLRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDElementoElencoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDStatoFiltroVeloceRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoElencoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRSpeciePraticaTipoInterventoRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class TracciatoXmlUtils {

    public String loadSingleData(String tablename, String targetColumn, String whereColumn, String whereValue) {
        CustomTracciatoXMLRepository customTrucciatoXMLRepository = ApplicationContextProvider.getApplicationContext().getBean("customTracciatoXMLRepository", CustomTracciatoXMLRepository.class);
        return customTrucciatoXMLRepository.loadSingleData(tablename,targetColumn,whereColumn,whereValue.replaceAll("'", "''"));
    }

    public String replace(String input, String replaceFrom, String replaceTo){
        return input.replaceAll(replaceFrom, replaceTo);
    }

    public String yearFromDate(String date, String pattern) {
        try {
            if(pattern.indexOf("/")==-1)
                date= date.replaceAll("/", "-");
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date d = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            // fix j820
            String year = String.valueOf(calendar.get(Calendar.YEAR));
            return "0000".substring(0, 4-year.length()) + year;
        } catch (Throwable e) {
            return "";
        }
    }

    public String formatXsDate(String date, String inputFormat) {
        try {
            if(inputFormat.indexOf("/")==-1)
                date= date.replaceAll("/", "-");
            SimpleDateFormat sdf = new SimpleDateFormat(inputFormat);
            Date d = sdf.parse(date);
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(d);
        } catch (Throwable e) {
            return "";
        }
    }

    public String stringLeftPad(String value, String fieldLength ){
        return StringUtils.leftPad(value, Integer.parseInt(fieldLength), ' ');
    }

    public String getFileExtension(String filename){
        String s = filename;
        if(filename.toLowerCase().endsWith(".p7m")){
            s = filename.substring(0, filename.lastIndexOf("."));
        }
        return s.substring(s.lastIndexOf(".")+1, s.length());
    }

    public String getDataForTipoInterventoDaSpeciePratica(String targetColumn, String codSpeciePratica){
        MudeRSpeciePraticaTipoInterventoRepository mudeRSpeciePraticaTipoInterventoRepository = ApplicationContextProvider.getApplicationContext().getBean("mudeRSpeciePraticaTipoInterventoRepository", MudeRSpeciePraticaTipoInterventoRepository.class);
        Optional<MudeRSpeciePraticaTipoIntervento> item =  mudeRSpeciePraticaTipoInterventoRepository.findByMudeDSpeciePratica_CodiceAndAbilitatoIsTrue(codSpeciePratica);
        if(item.isPresent()){
            MudeRSpeciePraticaTipoIntervento entity = item.get();
            switch (targetColumn){
                case "codice" : return entity.getMudeDTipoIntervento().getCodice();
                case "descrizione" : return entity.getMudeDTipoIntervento().getDescrizione();
                case "descrizione_estesa" : return entity.getMudeDTipoIntervento().getDescrizioneEstesa();
                default: return null;
            }
        }
        else return null;
    }

    public String getDataForTipoElementoCaratteristico(String targetColumn, String codiceElementoCaratteristico){
        MudeDElementoElencoRepository mudeDElementoElencoRepository = ApplicationContextProvider.getApplicationContext().getBean("mudeDElementoElencoRepository", MudeDElementoElencoRepository.class);
        Long idTipoElenco = mudeDElementoElencoRepository.getIdTipoElencoByCodiceElementoElenco(codiceElementoCaratteristico);

        MudeDTipoElencoRepository mudeDTipoElencoRepository = ApplicationContextProvider.getApplicationContext().getBean("mudeDTipoElencoRepository", MudeDTipoElencoRepository.class);
        Optional<MudeDTipoElenco> tipoElenco = mudeDTipoElencoRepository.findMudeDTipoElencoById(idTipoElenco);
        if(tipoElenco.isPresent()){
            MudeDTipoElenco entity = tipoElenco.get();
            switch (targetColumn){
                case "codice" : return entity.getCodice();
                case "descrizione" : return entity.getDescrizione();
                case "descrizione_estesa" : return entity.getDescrizioneEstesa();
                default: return null;
            }
        }
        else return null;
    }

    public String getRifProcMudeAggiornamentoNotPrel(String codiceFascicolo){
        CustomTracciatoXMLRepository customTrucciatoXMLRepository = ApplicationContextProvider.getApplicationContext().getBean("customTracciatoXMLRepository", CustomTracciatoXMLRepository.class);
        return customTrucciatoXMLRepository.loadRifProcMudeAggiornamentoNotPrel(codiceFascicolo);
    }

    public String withDecimalFormat(String value) {
        try{
            BigDecimal d = new BigDecimal(value);
            DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance();
            dfs.setDecimalSeparator('.');
            DecimalFormat df = new DecimalFormat("#.00");
            df.setDecimalFormatSymbols(dfs);
            df.setMinimumFractionDigits(2);
            df.setMaximumFractionDigits(2);
            return df.format(d);
        } catch(Throwable t){
            return "0.00";
        }
    }
}