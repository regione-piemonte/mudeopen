/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

@WebServiceClient(name = "limammEnteService",
                  wsdlLocation = "file:wsdl/limammEnte.wsdl",
                  targetNamespace = "ente")
public class LimammEnteService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("ente", "limammEnteService");
    public final static QName LimammEnte = new QName("ente", "limammEnte");
    static {
        URL url = null;
        try {
            url = new URL("file:wsdl/limammEnte.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(LimammEnteService.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "file:wsdl/limammEnte.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public LimammEnteService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public LimammEnteService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public LimammEnteService() {
        super(WSDL_LOCATION, SERVICE);
    }

    @WebEndpoint(name = "limammEnte")
    public LimammEnte getLimammEnte() {
        return super.getPort(LimammEnte, LimammEnte.class);
    }

    @WebEndpoint(name = "limammEnte")
    public LimammEnte getLimammEnte(WebServiceFeature... features) {
        return super.getPort(LimammEnte, LimammEnte.class, features);
    }

}