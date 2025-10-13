/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.util;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

public class XmlValidator {
    private static Logger logger = Logger.getLogger(XmlValidator.class.getCanonicalName());

    private String xsdSchema;
    private String tracciatoXML;

    public XmlValidator(String xsdSchema, String tracciatoXML) {
        this.xsdSchema = xsdSchema;
        this.tracciatoXML = tracciatoXML;
    }

    public boolean isValid() throws IOException, SAXException {
        Validator validator = initValidator(xsdSchema);
        try {
            validator.validate(new StreamSource(new StringReader(tracciatoXML)));
            return true;
        } catch (SAXException e) {
            return false;
        }
    }

    public List<SAXParseException> listParsingExceptions() throws IOException, SAXException {
        XmlErrorHandler xsdErrorHandler = new XmlErrorHandler();
        Validator validator = initValidator(xsdSchema);
        validator.setErrorHandler(xsdErrorHandler);
        try {
            validator.validate(new StreamSource(new StringReader(tracciatoXML)));
        } catch (SAXParseException e) {}
        xsdErrorHandler.getExceptions().forEach(e -> logger.info(e.getMessage()));
        return xsdErrorHandler.getExceptions();
    }

    private Validator initValidator(String xsdSchema) throws SAXException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source schemaFile = new StreamSource(new StringReader(xsdSchema));
        Schema schema = factory.newSchema(schemaFile);
        return schema.newValidator();
    }
}