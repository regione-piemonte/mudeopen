/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.factory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.AbstractServiceHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.IndexAllegatoMetadataProperty;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.IndexDocumentoMetadataProperty;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.IndexFolderFascicoloMetadataProperty;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.IndexFolderIstanzaMetadataProperty;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.IndexFolderPraticaMetadataProperty;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.IndexIstanzaMetadataProperty;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.dto.Aspect;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.dto.Node;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.dto.Property;

public class MudeIndexFactory {

    public static Node createFascicoloFolderContentNode(String foldername, IndexFolderFascicoloMetadataProperty indexMetadataProperty) {
        Node indexNode = new Node();
        indexNode.setModelPrefixedName("cm:mude");
        indexNode.setParentAssocTypePrefixedName("cm:contains");
        if (StringUtils.isNotBlank(foldername)) {
            indexNode.setPrefixedName("cm:" + foldername);
            indexNode.setTypePrefixedName("cm:folder");
        }

        if (null != indexMetadataProperty) {
            List<Property> properties = createFolderFascicoloMetadataProperties(indexMetadataProperty);
            indexNode.setProperties(properties);
            List<Aspect> aspects = createMetadataAspects();
            indexNode.setAspects(aspects);
        }

        return indexNode;
    }

    public static Node createIstanzaFolderContentNode(String foldername, IndexFolderIstanzaMetadataProperty indexMetadataProperty) {
        Node indexNode = new Node();
        indexNode.setModelPrefixedName("cm:mude");
        indexNode.setParentAssocTypePrefixedName("cm:contains");
        if (StringUtils.isNotBlank(foldername)) {
            indexNode.setPrefixedName("cm:" + foldername);
            indexNode.setTypePrefixedName("cm:folder");
        }

        if (null != indexMetadataProperty) {
            List<Property> properties = createFolderIstanzaMetadataProperties(indexMetadataProperty);
            indexNode.setProperties(properties);
            List<Aspect> aspects = createMetadataAspects();
            indexNode.setAspects(aspects);
        }

        return indexNode;
    }

    public static Node createPraticaFolderContentNode(String foldername, IndexFolderPraticaMetadataProperty indexMetadataProperty) {
        Node indexNode = new Node();
        indexNode.setModelPrefixedName("cm:mude");
        indexNode.setParentAssocTypePrefixedName("cm:contains");
        if (StringUtils.isNotBlank(foldername)) {
            indexNode.setPrefixedName("cm:" + foldername);
            indexNode.setTypePrefixedName("cm:folder");
        }

        if (null != indexMetadataProperty) {
            List<Property> properties = createFolderPraticaMetadataProperties(indexMetadataProperty);
            indexNode.setProperties(properties);
            List<Aspect> aspects = createMetadataAspects();
            indexNode.setAspects(aspects);
        }

        return indexNode;
    }

    public static Node createFileAllegatoContentNode(String filename, IndexAllegatoMetadataProperty indexMetadataProperty) {
        Node indexNode = new Node();
        indexNode.setModelPrefixedName("cm:mude");
        indexNode.setParentAssocTypePrefixedName("cm:contains");
       if (StringUtils.isNotBlank(filename)) {
            indexNode.setPrefixedName("cm:" + filename);
            indexNode.setTypePrefixedName("mude:Allegato");
            indexNode.setContentPropertyPrefixedName("cm:content");
            indexNode.setMimeType("application/octet-stream");
            indexNode.setEncoding("UTF-8");
        }

        if (null != indexMetadataProperty) {
            List<Property> properties = createAllegatoMetadataProperties(indexMetadataProperty);
            indexNode.setProperties(properties);
            List<Aspect> aspects = createAllegatiMetadataAspects(createMetadataAspects(),indexMetadataProperty );
            indexNode.setAspects(aspects);
        }

        return indexNode;
    }

    public static Node createFileDocumentoContentNode(String filename, IndexDocumentoMetadataProperty indexMetadataProperty) {
        Node indexNode = new Node();
        indexNode.setModelPrefixedName("cm:mude");
        indexNode.setParentAssocTypePrefixedName("cm:contains");
       if (StringUtils.isNotBlank(filename)) {
            indexNode.setPrefixedName("cm:" + filename);
            indexNode.setTypePrefixedName("mude:Documento");
            indexNode.setContentPropertyPrefixedName("cm:content");
            indexNode.setMimeType("application/octet-stream");
            indexNode.setEncoding("UTF-8");
        }

        if (null != indexMetadataProperty) {
            List<Property> properties = createDocumentoMetadataProperties(indexMetadataProperty);
            indexNode.setProperties(properties);
            List<Aspect> aspects = createDocumentiMetadataAspects(createMetadataAspects(),indexMetadataProperty);
            indexNode.setAspects(aspects);
        }

        return indexNode;
    }

    public static Node createFileIstanzaContentNode(String filename, IndexIstanzaMetadataProperty indexMetadataProperty) {
        Node indexNode = new Node();
        indexNode.setModelPrefixedName("cm:mude");
        indexNode.setParentAssocTypePrefixedName("cm:contains");
        if (StringUtils.isNotBlank(filename)) {
            indexNode.setPrefixedName("cm:" + filename);
            indexNode.setTypePrefixedName("cm:content");
            indexNode.setContentPropertyPrefixedName("cm:content");
            indexNode.setMimeType("application/octet-stream");
            indexNode.setEncoding("UTF-8");
        }

        if (null != indexMetadataProperty) {
            List<Property> properties = createIstanzaMetadataProperties(indexMetadataProperty);
            indexNode.setProperties(properties);
            List<Aspect> aspects = createMetadataAspects();
            indexNode.setAspects(aspects);
        }

        return indexNode;
    }

    public static List<Aspect> createMetadataAspects() {
        List<Aspect> aspectList = new ArrayList<>();
        Aspect aspect;

        aspect = new Aspect();
        aspect.setPrefixedName("sys:referenceable");
        aspectList.add(aspect);

        aspect = new Aspect();
        aspect.setPrefixedName("cm:auditable");
        aspectList.add(aspect);

        return aspectList;
    }

    public static List<Aspect> createAllegatiMetadataAspects(List<Aspect> aspectList,IndexAllegatoMetadataProperty indexMetadataProperty) {
        Aspect aspect;

        aspect = new Aspect();
        aspect.setPrefixedName("cm:author");
        aspectList.add(aspect);

        return aspectList;
    }

    public static List<Aspect> createDocumentiMetadataAspects(List<Aspect> aspectList, IndexDocumentoMetadataProperty indexMetadataProperty) {
        Aspect aspect;

        aspect = new Aspect();
        aspect.setPrefixedName("cm:author");
        aspectList.add(aspect);

        return aspectList;
    }

    private static List<Property> createFolderFascicoloMetadataProperties(IndexFolderFascicoloMetadataProperty indexMetadataProperty) {
        List<Property> properties = new ArrayList<>();
        Property property;
        List<String> values;

        if (StringUtils.isNotBlank(indexMetadataProperty.getNumeroIntervento())) {
            property = new Property();
            property.setPrefixedName("mude:numeroIntervento");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getNumeroIntervento());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getCodiceIstatComune())) {
            property = new Property();
            property.setPrefixedName("mude:codiceIstatComune");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getCodiceIstatComune());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getAnnoPresentazione())) {
            property = new Property();
            property.setPrefixedName("mude:annoPresentazione");
            property.setMultivalue(false);
            property.setDataType("d:int");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getAnnoPresentazione());
            property.setValues(values);
            properties.add(property);
        }

        return properties;
    }

    private static List<Property> createFolderIstanzaMetadataProperties(IndexFolderIstanzaMetadataProperty indexMetadataProperty) {
        List<Property> properties = new ArrayList<>();
        Property property;
        List<String> values;

        if (StringUtils.isNotBlank(indexMetadataProperty.getNumeroMudeIntervento())) {
            property = new Property();
            property.setPrefixedName("mude:numeroMudeIntervento");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getNumeroMudeIntervento());
            property.setValues(values);
            properties.add(property);
        }

        return properties;
    }

    private static List<Property> createFolderPraticaMetadataProperties(IndexFolderPraticaMetadataProperty indexMetadataProperty) {
        List<Property> properties = new ArrayList<>();
        Property property;
        List<String> values;

        if (StringUtils.isNotBlank(indexMetadataProperty.getNumeroMudeIntervento())) {
            property = new Property();
            property.setPrefixedName("mude:numeroMudeIntervento");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getNumeroMudeIntervento());
            property.setValues(values);
            properties.add(property);
        }

        return properties;
    }

    private static List<Property> createAllegatoMetadataProperties(IndexAllegatoMetadataProperty indexMetadataProperty) {
        List<Property> properties = new ArrayList<>();
        Property property;
        List<String> values;

        if (StringUtils.isNotBlank(indexMetadataProperty.getCodiceTipoAllegato())) {
            property = new Property();
            property.setPrefixedName("mude:codiceTipoAllegato");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getCodiceTipoAllegato());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getNumeroMudeAllegato())) {
            property = new Property();
            property.setPrefixedName("mude:numeroMudeAllegato");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getNumeroMudeAllegato());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getNumeroInterventoAllegato())) {
            property = new Property();
            property.setPrefixedName("mude:numeroInterventoAllegato");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getNumeroInterventoAllegato());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getAuthor())) {
            property = new Property();
            property.setPrefixedName("cm:author");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getAuthor());
            property.setValues(values);
            properties.add(property);
        }
		
        return properties;
    }

    private static List<Property> createDocumentoMetadataProperties(IndexDocumentoMetadataProperty indexMetadataProperty) {
        List<Property> properties = new ArrayList<>();
        Property property;
        List<String> values;

        if (StringUtils.isNotBlank(indexMetadataProperty.getCodiceTipologia())) {
            property = new Property();
            property.setPrefixedName("mude:codiceTipologia");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getCodiceTipologia());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getNumeroPraticaDocumentoPA())) {
            property = new Property();
            property.setPrefixedName("mude:numeroPraticaDocumentoPA");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getNumeroPraticaDocumentoPA());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getIstatComuneDocumentoPA())) {
            property = new Property();
            property.setPrefixedName("mude:codIstatComuneDocumentoPA"); // istatComuneDocumentoPA
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getIstatComuneDocumentoPA());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getAnnoCreazioneDocumentoPA())) {
            property = new Property();
            property.setPrefixedName("mude:annoCreazioneDocumentoPA");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getAnnoCreazioneDocumentoPA());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getAuthor())) {
            property = new Property();
            property.setPrefixedName("cm:author");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getAuthor());
            property.setValues(values);
            properties.add(property);
        }
		
        return properties;
    }

    private static List<Property> createIstanzaMetadataProperties(IndexIstanzaMetadataProperty indexMetadataProperty) {
        List<Property> properties = new ArrayList<>();
        Property property;
        List<String> values;

        if (StringUtils.isNotBlank(indexMetadataProperty.getNumeroIntervento())) {
            property = new Property();
            property.setPrefixedName("mude:numeroMudeIntervento");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getNumeroIntervento());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getCodiceModello())) {
            property = new Property();
            property.setPrefixedName("mude:codiceModello");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getCodiceModello());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getCodiceVersioneModello())) {
            property = new Property();
            property.setPrefixedName("mude:codiceVersioneModello");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getCodiceVersioneModello());
            property.setValues(values);
            properties.add(property);
        }

        if (StringUtils.isNotBlank(indexMetadataProperty.getNumeroMudeIstanza())) {
            property = new Property();
            property.setPrefixedName("mude:numeroMudeModulo");
            property.setMultivalue(false);
            property.setDataType("d:text");
            values = new ArrayList<>();
            values.add(indexMetadataProperty.getNumeroMudeIstanza());
            property.setValues(values);
            properties.add(property);
        }

        return properties;
    }

    private static String escapeString(String str) {
        return URLEncoder.encode(str, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
    }
}