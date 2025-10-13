/*
SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
SPDX-License-Identifier: EUPL-1.2
 */

import groovy.json.JsonSlurper

def validate(args) {
    def jsonSlurper = new JsonSlurper()
    def jsonParse = jsonSlurper.parseText(args)
    def json = jsonParse.get("data")
    def mappa = [:]

    def soggetti = json.get("soggetti")
    soggetti.eachWithIndex { it , i->
        def nome = it.nome
        if (null == nome || nome == "") {
            def errors = []
            errors.add("campo obbligatorio")
            mappa.put("soggetti["+ i +"].nome", errors )
        } else {
            if (nome.length() > 50 ) {
                def errors = []
                errors.add("specificare un valore stringa con lunghezza massima 50 caratteri")
                mappa.put("soggetti["+ i +"].nome", errors )
            }
        }
        def eta = it.eta
        if (null == eta) {
            def errors = []
            errors.add("campo obbligatorio")
            mappa.put("soggetti["+ i +"].eta", errors )
        } else {
            if(!( eta.isNumber()) || eta.toInteger() < 0 || eta.toInteger() > 150 ) {
                def errors = []
                errors.add("specificare un valore numerico tra 0 e 150")
                mappa.put("soggetti["+ i +"].eta", errors )
            }
        }
        def cognome = it.cognome
        if (null == cognome || cognome == "") {
            def errors = []
            errors.add("campo obbligatorio")
            mappa.put("soggetti["+ i +"].cognome", errors )
        } else {
            if (cognome.length() > 50 ) {
                def errors = []
                errors.add("specificare un valore stringa con lunghezza massima 50 caratteri")
                mappa.put("soggetti["+ i +"].cognome", errors )
            }
        }
    }
    return mappa
}
String jo =
"""
{
\t"data": {
\t\t"soggetti": [
\t\t\t{
\t\t\t\t"nome": "Paolo",
\t\t\t\t"cognome": "Loddo",
\t\t\t\t"eta": "47"
\t\t\t},{
\t\t\t\t"nome": "Andrea",
\t\t\t\t"cognome": null,
\t\t\t\t"eta": "X"
\t\t\t},{
\t\t\t\t"nome": "AndreaAndreaAndreaAndreaAndreaAndreaAndreaAndreaAndreaAndreaAndreaAndreaAndrea",
\t\t\t\t"cognome": "Spano",
\t\t\t\t"eta": "47"
\t\t\t}
\t\t]
\t}
}
"""
println(validate(jo))