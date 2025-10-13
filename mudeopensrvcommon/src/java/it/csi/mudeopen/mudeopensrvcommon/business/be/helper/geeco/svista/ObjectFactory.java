/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
@XmlRegistry
public class ObjectFactory {

    private final static QName _Fault_QNAME = new QName("ente", "fault");
    private final static QName _Fault1_QNAME = new QName("ente", "fault1");
    private final static QName _Fault2_QNAME = new QName("ente", "fault2");
    private final static QName _Fault3_QNAME = new QName("ente", "fault3");
    private final static QName _Fault4_QNAME = new QName("ente", "fault4");
    private final static QName _Fault5_QNAME = new QName("ente", "fault5");

    public ObjectFactory() {
    }

    public CercaRegionePerCodIstat createCercaRegionePerCodIstat() {
        return new CercaRegionePerCodIstat();
    }

    public CercaRegionePerCodIstatResponse createCercaRegionePerCodIstatResponse() {
        return new CercaRegionePerCodIstatResponse();
    }

    public Regione createRegione() {
        return new Regione();
    }

    public CSIException createCSIException() {
        return new CSIException();
    }

    public SystemException createSystemException() {
        return new SystemException();
    }

    public UnrecoverableException createUnrecoverableException() {
        return new UnrecoverableException();
    }

    public OutputException createOutputException() {
        return new OutputException();
    }

    public ParInputObblMancantiException createParInputObblMancantiException() {
        return new ParInputObblMancantiException();
    }

    public ParInputValNonCorrettoException createParInputValNonCorrettoException() {
        return new ParInputValNonCorrettoException();
    }

    public CercaRegioniPerNome createCercaRegioniPerNome() {
        return new CercaRegioniPerNome();
    }

    public CercaRegioniPerNomeResponse createCercaRegioniPerNomeResponse() {
        return new CercaRegioniPerNomeResponse();
    }

    public CercaLocalitaPerIdComune createCercaLocalitaPerIdComune() {
        return new CercaLocalitaPerIdComune();
    }

    public CercaLocalitaPerIdComuneResponse createCercaLocalitaPerIdComuneResponse() {
        return new CercaLocalitaPerIdComuneResponse();
    }

    public Localita createLocalita() {
        return new Localita();
    }

    public CercaLocalitaPerIdLocalita createCercaLocalitaPerIdLocalita() {
        return new CercaLocalitaPerIdLocalita();
    }

    public CercaLocalitaPerIdLocalitaResponse createCercaLocalitaPerIdLocalitaResponse() {
        return new CercaLocalitaPerIdLocalitaResponse();
    }

    public CercaLocalitaPerNome createCercaLocalitaPerNome() {
        return new CercaLocalitaPerNome();
    }

    public CercaLocalitaPerNomeResponse createCercaLocalitaPerNomeResponse() {
        return new CercaLocalitaPerNomeResponse();
    }

    public CercaEstensioneComune createCercaEstensioneComune() {
        return new CercaEstensioneComune();
    }

    public CercaEstensioneComuneResponse createCercaEstensioneComuneResponse() {
        return new CercaEstensioneComuneResponse();
    }

    public CercaGeometriaComune createCercaGeometriaComune() {
        return new CercaGeometriaComune();
    }

    public CercaGeometriaComuneResponse createCercaGeometriaComuneResponse() {
        return new CercaGeometriaComuneResponse();
    }

    public CercaEstensioneRegione createCercaEstensioneRegione() {
        return new CercaEstensioneRegione();
    }

    public CercaEstensioneRegioneResponse createCercaEstensioneRegioneResponse() {
        return new CercaEstensioneRegioneResponse();
    }

    public CercaGeometriaRegione createCercaGeometriaRegione() {
        return new CercaGeometriaRegione();
    }

    public CercaGeometriaRegioneResponse createCercaGeometriaRegioneResponse() {
        return new CercaGeometriaRegioneResponse();
    }

    public CercaEstensioneProvincia createCercaEstensioneProvincia() {
        return new CercaEstensioneProvincia();
    }

    public CercaEstensioneProvinciaResponse createCercaEstensioneProvinciaResponse() {
        return new CercaEstensioneProvinciaResponse();
    }

    public CercaGeometriaProvincia createCercaGeometriaProvincia() {
        return new CercaGeometriaProvincia();
    }

    public CercaGeometriaProvinciaResponse createCercaGeometriaProvinciaResponse() {
        return new CercaGeometriaProvinciaResponse();
    }

    public CercaTuttiIComuni createCercaTuttiIComuni() {
        return new CercaTuttiIComuni();
    }

    public CercaTuttiIComuniResponse createCercaTuttiIComuniResponse() {
        return new CercaTuttiIComuniResponse();
    }

    public Comune createComune() {
        return new Comune();
    }

    public CercaComunePerCodiceIstat createCercaComunePerCodiceIstat() {
        return new CercaComunePerCodiceIstat();
    }

    public CercaComunePerCodiceIstatResponse createCercaComunePerCodiceIstatResponse() {
        return new CercaComunePerCodiceIstatResponse();
    }

    public CercaComuniPerIdProvincia createCercaComuniPerIdProvincia() {
        return new CercaComuniPerIdProvincia();
    }

    public CercaComuniPerIdProvinciaResponse createCercaComuniPerIdProvinciaResponse() {
        return new CercaComuniPerIdProvinciaResponse();
    }

    public CercaComuniPerCap createCercaComuniPerCap() {
        return new CercaComuniPerCap();
    }

    public CercaComuniPerCapResponse createCercaComuniPerCapResponse() {
        return new CercaComuniPerCapResponse();
    }

    public CercaComunePerCodiceBelfiore createCercaComunePerCodiceBelfiore() {
        return new CercaComunePerCodiceBelfiore();
    }

    public CercaComunePerCodiceBelfioreResponse createCercaComunePerCodiceBelfioreResponse() {
        return new CercaComunePerCodiceBelfioreResponse();
    }

    public CercaComunePerIdComune createCercaComunePerIdComune() {
        return new CercaComunePerIdComune();
    }

    public CercaComunePerIdComuneResponse createCercaComunePerIdComuneResponse() {
        return new CercaComunePerIdComuneResponse();
    }

    public CercaComuniPerNome createCercaComuniPerNome() {
        return new CercaComuniPerNome();
    }

    public CercaComuniPerNomeResponse createCercaComuniPerNomeResponse() {
        return new CercaComuniPerNomeResponse();
    }

    public CercaComuniPerNomeECodIstatProvincia createCercaComuniPerNomeECodIstatProvincia() {
        return new CercaComuniPerNomeECodIstatProvincia();
    }

    public CercaComuniPerNomeECodIstatProvinciaResponse createCercaComuniPerNomeECodIstatProvinciaResponse() {
        return new CercaComuniPerNomeECodIstatProvinciaResponse();
    }

    public CercaComuniPerNomeEIdProvincia createCercaComuniPerNomeEIdProvincia() {
        return new CercaComuniPerNomeEIdProvincia();
    }

    public CercaComuniPerNomeEIdProvinciaResponse createCercaComuniPerNomeEIdProvinciaResponse() {
        return new CercaComuniPerNomeEIdProvinciaResponse();
    }

    public CercaTutteLeProvince createCercaTutteLeProvince() {
        return new CercaTutteLeProvince();
    }

    public CercaTutteLeProvinceResponse createCercaTutteLeProvinceResponse() {
        return new CercaTutteLeProvinceResponse();
    }

    public Provincia createProvincia() {
        return new Provincia();
    }

    public CercaProvincePerIdRegione createCercaProvincePerIdRegione() {
        return new CercaProvincePerIdRegione();
    }

    public CercaProvincePerIdRegioneResponse createCercaProvincePerIdRegioneResponse() {
        return new CercaProvincePerIdRegioneResponse();
    }

    public CercaProvinciaPerCodiceIstat createCercaProvinciaPerCodiceIstat() {
        return new CercaProvinciaPerCodiceIstat();
    }

    public CercaProvinciaPerCodiceIstatResponse createCercaProvinciaPerCodiceIstatResponse() {
        return new CercaProvinciaPerCodiceIstatResponse();
    }

    public CercaProvinciaPerIdProvincia createCercaProvinciaPerIdProvincia() {
        return new CercaProvinciaPerIdProvincia();
    }

    public CercaProvinciaPerIdProvinciaResponse createCercaProvinciaPerIdProvinciaResponse() {
        return new CercaProvinciaPerIdProvinciaResponse();
    }

    public CercaProvincePerNome createCercaProvincePerNome() {
        return new CercaProvincePerNome();
    }

    public CercaProvincePerNomeResponse createCercaProvincePerNomeResponse() {
        return new CercaProvincePerNomeResponse();
    }

    public CercaTutteLeRegioni createCercaTutteLeRegioni() {
        return new CercaTutteLeRegioni();
    }

    public CercaTutteLeRegioniResponse createCercaTutteLeRegioniResponse() {
        return new CercaTutteLeRegioniResponse();
    }

    public CercaRegionePerIdRegione createCercaRegionePerIdRegione() {
        return new CercaRegionePerIdRegione();
    }

    public CercaRegionePerIdRegioneResponse createCercaRegionePerIdRegioneResponse() {
        return new CercaRegionePerIdRegioneResponse();
    }

    public UserException createUserException() {
        return new UserException();
    }

    public Asl createAsl() {
        return new Asl();
    }

    public ArrayOfAsl createArrayOfAsl() {
        return new ArrayOfAsl();
    }

    @XmlElementDecl(namespace = "ente", name = "fault")
    public JAXBElement<CSIException> createFault(CSIException value) {
        return new JAXBElement<CSIException>(_Fault_QNAME, CSIException.class, null, value);
    }

    @XmlElementDecl(namespace = "ente", name = "fault1")
    public JAXBElement<SystemException> createFault1(SystemException value) {
        return new JAXBElement<SystemException>(_Fault1_QNAME, SystemException.class, null, value);
    }

    @XmlElementDecl(namespace = "ente", name = "fault2")
    public JAXBElement<UnrecoverableException> createFault2(UnrecoverableException value) {
        return new JAXBElement<UnrecoverableException>(_Fault2_QNAME, UnrecoverableException.class, null, value);
    }

    @XmlElementDecl(namespace = "ente", name = "fault3")
    public JAXBElement<OutputException> createFault3(OutputException value) {
        return new JAXBElement<OutputException>(_Fault3_QNAME, OutputException.class, null, value);
    }

    @XmlElementDecl(namespace = "ente", name = "fault4")
    public JAXBElement<ParInputObblMancantiException> createFault4(ParInputObblMancantiException value) {
        return new JAXBElement<ParInputObblMancantiException>(_Fault4_QNAME, ParInputObblMancantiException.class, null, value);
    }

    @XmlElementDecl(namespace = "ente", name = "fault5")
    public JAXBElement<ParInputValNonCorrettoException> createFault5(ParInputValNonCorrettoException value) {
        return new JAXBElement<ParInputValNonCorrettoException>(_Fault5_QNAME, ParInputValNonCorrettoException.class, null, value);
    }

}