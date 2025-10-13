/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(targetNamespace = "ente", name = "limammEnte")
@XmlSeeAlso({ObjectFactory.class})
public interface LimammEnte {

    @WebMethod
    @RequestWrapper(localName = "cercaEstensioneProvincia", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaEstensioneProvincia")
    @ResponseWrapper(localName = "cercaEstensioneProvinciaResponse", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaEstensioneProvinciaResponse")
    @WebResult(name = "cercaEstensioneProvinciaReturn", targetNamespace = "ente")
    public java.lang.String cercaEstensioneProvincia(

        @WebParam(name = "in0", targetNamespace = "ente")
        long in0
    ) throws SystemException_Exception, ParInputValNonCorrettoException_Exception, OutputException_Exception, CSIException_Exception, UnrecoverableException_Exception, ParInputObblMancantiException_Exception;

    @WebMethod
    @RequestWrapper(localName = "cercaGeometriaProvincia", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaGeometriaProvincia")
    @ResponseWrapper(localName = "cercaGeometriaProvinciaResponse", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaGeometriaProvinciaResponse")
    @WebResult(name = "cercaGeometriaProvinciaReturn", targetNamespace = "ente")
    public java.lang.String cercaGeometriaProvincia(

        @WebParam(name = "in0", targetNamespace = "ente")
        long in0
    ) throws SystemException_Exception, ParInputValNonCorrettoException_Exception, OutputException_Exception, CSIException_Exception, UnrecoverableException_Exception, ParInputObblMancantiException_Exception;

    @WebMethod
    @RequestWrapper(localName = "cercaComuniPerNome", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaComuniPerNome")
    @ResponseWrapper(localName = "cercaComuniPerNomeResponse", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaComuniPerNomeResponse")
    @WebResult(name = "cercaComuniPerNomeReturn", targetNamespace = "ente")
    public java.util.List<it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.Comune> cercaComuniPerNome(

        @WebParam(name = "in0", targetNamespace = "ente")
        java.lang.String in0,
        @WebParam(name = "in1", targetNamespace = "ente")
        int in1
    ) throws SystemException_Exception, ParInputValNonCorrettoException_Exception, OutputException_Exception, CSIException_Exception, UnrecoverableException_Exception, ParInputObblMancantiException_Exception;

    @WebMethod
    @RequestWrapper(localName = "cercaRegionePerIdRegione", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaRegionePerIdRegione")
    @ResponseWrapper(localName = "cercaRegionePerIdRegioneResponse", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaRegionePerIdRegioneResponse")
    @WebResult(name = "cercaRegionePerIdRegioneReturn", targetNamespace = "ente")
    public it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.Regione cercaRegionePerIdRegione(

        @WebParam(name = "in0", targetNamespace = "ente")
        java.lang.Long in0
    ) throws SystemException_Exception, ParInputValNonCorrettoException_Exception, OutputException_Exception, CSIException_Exception, UnrecoverableException_Exception, ParInputObblMancantiException_Exception;

    @WebMethod
    @RequestWrapper(localName = "cercaComuniPerIdProvincia", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaComuniPerIdProvincia")
    @ResponseWrapper(localName = "cercaComuniPerIdProvinciaResponse", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaComuniPerIdProvinciaResponse")
    @WebResult(name = "cercaComuniPerIdProvinciaReturn", targetNamespace = "ente")
    public java.util.List<it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.Comune> cercaComuniPerIdProvincia(

        @WebParam(name = "in0", targetNamespace = "ente")
        java.lang.Long in0
    ) throws SystemException_Exception, ParInputValNonCorrettoException_Exception, OutputException_Exception, CSIException_Exception, UnrecoverableException_Exception, ParInputObblMancantiException_Exception;

    @WebMethod
    @RequestWrapper(localName = "cercaComuniPerNomeEIdProvincia", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaComuniPerNomeEIdProvincia")
    @ResponseWrapper(localName = "cercaComuniPerNomeEIdProvinciaResponse", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaComuniPerNomeEIdProvinciaResponse")
    @WebResult(name = "cercaComuniPerNomeEIdProvinciaReturn", targetNamespace = "ente")
    public java.util.List<it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.Comune> cercaComuniPerNomeEIdProvincia(

        @WebParam(name = "in0", targetNamespace = "ente")
        java.lang.String in0,
        @WebParam(name = "in1", targetNamespace = "ente")
        int in1,
        @WebParam(name = "in2", targetNamespace = "ente")
        long in2
    ) throws SystemException_Exception, ParInputValNonCorrettoException_Exception, OutputException_Exception, CSIException_Exception, UnrecoverableException_Exception, ParInputObblMancantiException_Exception;

    @WebMethod
    @RequestWrapper(localName = "cercaComuniPerCap", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaComuniPerCap")
    @ResponseWrapper(localName = "cercaComuniPerCapResponse", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaComuniPerCapResponse")
    @WebResult(name = "cercaComuniPerCapReturn", targetNamespace = "ente")
    public java.util.List<it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.Comune> cercaComuniPerCap(

        @WebParam(name = "in0", targetNamespace = "ente")
        java.lang.String in0
    ) throws SystemException_Exception, ParInputValNonCorrettoException_Exception, OutputException_Exception, CSIException_Exception, UnrecoverableException_Exception, ParInputObblMancantiException_Exception;

    @WebMethod
    @RequestWrapper(localName = "cercaGeometriaRegione", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaGeometriaRegione")
    @ResponseWrapper(localName = "cercaGeometriaRegioneResponse", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaGeometriaRegioneResponse")
    @WebResult(name = "cercaGeometriaRegioneReturn", targetNamespace = "ente")
    public java.lang.String cercaGeometriaRegione(

        @WebParam(name = "in0", targetNamespace = "ente")
        long in0
    ) throws SystemException_Exception, ParInputValNonCorrettoException_Exception, OutputException_Exception, CSIException_Exception, UnrecoverableException_Exception, ParInputObblMancantiException_Exception;

    @WebMethod
    @RequestWrapper(localName = "cercaTutteLeProvince", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaTutteLeProvince")
    @ResponseWrapper(localName = "cercaTutteLeProvinceResponse", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaTutteLeProvinceResponse")
    @WebResult(name = "cercaTutteLeProvinceReturn", targetNamespace = "ente")
    public java.util.List<it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.Provincia> cercaTutteLeProvince()
 throws SystemException_Exception, ParInputValNonCorrettoException_Exception, OutputException_Exception, CSIException_Exception, UnrecoverableException_Exception, ParInputObblMancantiException_Exception;

    @WebMethod
    @RequestWrapper(localName = "cercaTutteLeRegioni", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaTutteLeRegioni")
    @ResponseWrapper(localName = "cercaTutteLeRegioniResponse", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaTutteLeRegioniResponse")
    @WebResult(name = "cercaTutteLeRegioniReturn", targetNamespace = "ente")
    public java.util.List<it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.Regione> cercaTutteLeRegioni()
 throws SystemException_Exception, ParInputValNonCorrettoException_Exception, OutputException_Exception, CSIException_Exception, UnrecoverableException_Exception, ParInputObblMancantiException_Exception;

    @WebMethod
    @RequestWrapper(localName = "cercaLocalitaPerIdLocalita", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaLocalitaPerIdLocalita")
    @ResponseWrapper(localName = "cercaLocalitaPerIdLocalitaResponse", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaLocalitaPerIdLocalitaResponse")
    @WebResult(name = "cercaLocalitaPerIdLocalitaReturn", targetNamespace = "ente")
    public it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.Localita cercaLocalitaPerIdLocalita(

        @WebParam(name = "in0", targetNamespace = "ente")
        java.lang.Long in0
    ) throws SystemException_Exception, ParInputValNonCorrettoException_Exception, OutputException_Exception, CSIException_Exception, UnrecoverableException_Exception, ParInputObblMancantiException_Exception;

    @WebMethod
    @RequestWrapper(localName = "cercaTuttiIComuni", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaTuttiIComuni")
    @ResponseWrapper(localName = "cercaTuttiIComuniResponse", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaTuttiIComuniResponse")
    @WebResult(name = "cercaTuttiIComuniReturn", targetNamespace = "ente")
    public java.util.List<it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.Comune> cercaTuttiIComuni()
 throws SystemException_Exception, ParInputValNonCorrettoException_Exception, OutputException_Exception, CSIException_Exception, UnrecoverableException_Exception, ParInputObblMancantiException_Exception;

    @WebMethod
    @RequestWrapper(localName = "cercaEstensioneComune", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaEstensioneComune")
    @ResponseWrapper(localName = "cercaEstensioneComuneResponse", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaEstensioneComuneResponse")
    @WebResult(name = "cercaEstensioneComuneReturn", targetNamespace = "ente")
    public java.lang.String cercaEstensioneComune(

        @WebParam(name = "in0", targetNamespace = "ente")
        long in0
    ) throws SystemException_Exception, ParInputValNonCorrettoException_Exception, OutputException_Exception, CSIException_Exception, UnrecoverableException_Exception, ParInputObblMancantiException_Exception;

    @WebMethod
    @RequestWrapper(localName = "cercaComunePerCodiceBelfiore", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaComunePerCodiceBelfiore")
    @ResponseWrapper(localName = "cercaComunePerCodiceBelfioreResponse", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaComunePerCodiceBelfioreResponse")
    @WebResult(name = "cercaComunePerCodiceBelfioreReturn", targetNamespace = "ente")
    public it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.Comune cercaComunePerCodiceBelfiore(

        @WebParam(name = "in0", targetNamespace = "ente")
        java.lang.String in0
    ) throws SystemException_Exception, ParInputValNonCorrettoException_Exception, OutputException_Exception, CSIException_Exception, UnrecoverableException_Exception, ParInputObblMancantiException_Exception;

    @WebMethod
    @RequestWrapper(localName = "cercaRegioniPerNome", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaRegioniPerNome")
    @ResponseWrapper(localName = "cercaRegioniPerNomeResponse", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaRegioniPerNomeResponse")
    @WebResult(name = "cercaRegioniPerNomeReturn", targetNamespace = "ente")
    public java.util.List<it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.Regione> cercaRegioniPerNome(

        @WebParam(name = "in0", targetNamespace = "ente")
        java.lang.String in0,
        @WebParam(name = "in1", targetNamespace = "ente")
        int in1
    ) throws SystemException_Exception, ParInputValNonCorrettoException_Exception, OutputException_Exception, CSIException_Exception, UnrecoverableException_Exception, ParInputObblMancantiException_Exception;

    @WebMethod
    @RequestWrapper(localName = "cercaLocalitaPerNome", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaLocalitaPerNome")
    @ResponseWrapper(localName = "cercaLocalitaPerNomeResponse", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaLocalitaPerNomeResponse")
    @WebResult(name = "cercaLocalitaPerNomeReturn", targetNamespace = "ente")
    public java.util.List<it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.Localita> cercaLocalitaPerNome(

        @WebParam(name = "in0", targetNamespace = "ente")
        java.lang.String in0,
        @WebParam(name = "in1", targetNamespace = "ente")
        int in1
    ) throws SystemException_Exception, ParInputValNonCorrettoException_Exception, OutputException_Exception, CSIException_Exception, UnrecoverableException_Exception, ParInputObblMancantiException_Exception;

    @WebMethod
    @RequestWrapper(localName = "cercaProvincePerIdRegione", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaProvincePerIdRegione")
    @ResponseWrapper(localName = "cercaProvincePerIdRegioneResponse", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaProvincePerIdRegioneResponse")
    @WebResult(name = "cercaProvincePerIdRegioneReturn", targetNamespace = "ente")
    public java.util.List<it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.Provincia> cercaProvincePerIdRegione(

        @WebParam(name = "in0", targetNamespace = "ente")
        long in0
    ) throws SystemException_Exception, ParInputValNonCorrettoException_Exception, OutputException_Exception, CSIException_Exception, UnrecoverableException_Exception, ParInputObblMancantiException_Exception;

    @WebMethod
    @RequestWrapper(localName = "cercaProvinciaPerIdProvincia", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaProvinciaPerIdProvincia")
    @ResponseWrapper(localName = "cercaProvinciaPerIdProvinciaResponse", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaProvinciaPerIdProvinciaResponse")
    @WebResult(name = "cercaProvinciaPerIdProvinciaReturn", targetNamespace = "ente")
    public it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.Provincia cercaProvinciaPerIdProvincia(

        @WebParam(name = "in0", targetNamespace = "ente")
        java.lang.Long in0
    ) throws SystemException_Exception, ParInputValNonCorrettoException_Exception, OutputException_Exception, CSIException_Exception, UnrecoverableException_Exception, ParInputObblMancantiException_Exception;

    @WebMethod
    @RequestWrapper(localName = "cercaProvinciaPerCodiceIstat", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaProvinciaPerCodiceIstat")
    @ResponseWrapper(localName = "cercaProvinciaPerCodiceIstatResponse", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaProvinciaPerCodiceIstatResponse")
    @WebResult(name = "cercaProvinciaPerCodiceIstatReturn", targetNamespace = "ente")
    public it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.Provincia cercaProvinciaPerCodiceIstat(

        @WebParam(name = "in0", targetNamespace = "ente")
        java.lang.String in0
    ) throws SystemException_Exception, ParInputValNonCorrettoException_Exception, OutputException_Exception, CSIException_Exception, UnrecoverableException_Exception, ParInputObblMancantiException_Exception;

    @WebMethod
    @RequestWrapper(localName = "cercaGeometriaComune", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaGeometriaComune")
    @ResponseWrapper(localName = "cercaGeometriaComuneResponse", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaGeometriaComuneResponse")
    @WebResult(name = "cercaGeometriaComuneReturn", targetNamespace = "ente")
    public java.lang.String cercaGeometriaComune(

        @WebParam(name = "in0", targetNamespace = "ente")
        long in0
    ) throws SystemException_Exception, ParInputValNonCorrettoException_Exception, OutputException_Exception, CSIException_Exception, UnrecoverableException_Exception, ParInputObblMancantiException_Exception;

    @WebMethod
    @RequestWrapper(localName = "cercaEstensioneRegione", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaEstensioneRegione")
    @ResponseWrapper(localName = "cercaEstensioneRegioneResponse", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaEstensioneRegioneResponse")
    @WebResult(name = "cercaEstensioneRegioneReturn", targetNamespace = "ente")
    public java.lang.String cercaEstensioneRegione(

        @WebParam(name = "in0", targetNamespace = "ente")
        long in0
    ) throws SystemException_Exception, ParInputValNonCorrettoException_Exception, OutputException_Exception, CSIException_Exception, UnrecoverableException_Exception, ParInputObblMancantiException_Exception;

    @WebMethod
    @RequestWrapper(localName = "cercaProvincePerNome", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaProvincePerNome")
    @ResponseWrapper(localName = "cercaProvincePerNomeResponse", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaProvincePerNomeResponse")
    @WebResult(name = "cercaProvincePerNomeReturn", targetNamespace = "ente")
    public java.util.List<it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.Provincia> cercaProvincePerNome(

        @WebParam(name = "in0", targetNamespace = "ente")
        java.lang.String in0,
        @WebParam(name = "in1", targetNamespace = "ente")
        java.lang.Integer in1
    ) throws SystemException_Exception, ParInputValNonCorrettoException_Exception, OutputException_Exception, CSIException_Exception, UnrecoverableException_Exception, ParInputObblMancantiException_Exception;

    @WebMethod
    @RequestWrapper(localName = "cercaRegionePerCodIstat", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaRegionePerCodIstat")
    @ResponseWrapper(localName = "cercaRegionePerCodIstatResponse", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaRegionePerCodIstatResponse")
    @WebResult(name = "cercaRegionePerCodIstatReturn", targetNamespace = "ente")
    public it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.Regione cercaRegionePerCodIstat(

        @WebParam(name = "in0", targetNamespace = "ente")
        java.lang.String in0
    ) throws SystemException_Exception, ParInputValNonCorrettoException_Exception, OutputException_Exception, CSIException_Exception, UnrecoverableException_Exception, ParInputObblMancantiException_Exception;

    @WebMethod
    @RequestWrapper(localName = "cercaLocalitaPerIdComune", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaLocalitaPerIdComune")
    @ResponseWrapper(localName = "cercaLocalitaPerIdComuneResponse", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaLocalitaPerIdComuneResponse")
    @WebResult(name = "cercaLocalitaPerIdComuneReturn", targetNamespace = "ente")
    public java.util.List<it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.Localita> cercaLocalitaPerIdComune(

        @WebParam(name = "in0", targetNamespace = "ente")
        java.lang.Long in0
    ) throws SystemException_Exception, ParInputValNonCorrettoException_Exception, OutputException_Exception, CSIException_Exception, UnrecoverableException_Exception, ParInputObblMancantiException_Exception;

    @WebMethod
    @RequestWrapper(localName = "cercaComunePerCodiceIstat", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaComunePerCodiceIstat")
    @ResponseWrapper(localName = "cercaComunePerCodiceIstatResponse", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaComunePerCodiceIstatResponse")
    @WebResult(name = "cercaComunePerCodiceIstatReturn", targetNamespace = "ente")
    public it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.Comune cercaComunePerCodiceIstat(

        @WebParam(name = "in0", targetNamespace = "ente")
        java.lang.String in0
    ) throws SystemException_Exception, ParInputValNonCorrettoException_Exception, OutputException_Exception, CSIException_Exception, UnrecoverableException_Exception, ParInputObblMancantiException_Exception;

    @WebMethod
    @RequestWrapper(localName = "cercaComunePerIdComune", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaComunePerIdComune")
    @ResponseWrapper(localName = "cercaComunePerIdComuneResponse", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaComunePerIdComuneResponse")
    @WebResult(name = "cercaComunePerIdComuneReturn", targetNamespace = "ente")
    public it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.Comune cercaComunePerIdComune(

        @WebParam(name = "in0", targetNamespace = "ente")
        java.lang.Long in0
    ) throws SystemException_Exception, ParInputValNonCorrettoException_Exception, OutputException_Exception, CSIException_Exception, UnrecoverableException_Exception, ParInputObblMancantiException_Exception;

    @WebMethod
    @RequestWrapper(localName = "cercaComuniPerNomeECodIstatProvincia", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaComuniPerNomeECodIstatProvincia")
    @ResponseWrapper(localName = "cercaComuniPerNomeECodIstatProvinciaResponse", targetNamespace = "ente", className = "it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.CercaComuniPerNomeECodIstatProvinciaResponse")
    @WebResult(name = "cercaComuniPerNomeECodIstatProvinciaReturn", targetNamespace = "ente")
    public java.util.List<it.csi.mudeopen.mudeopensrvcommon.business.be.helper.geeco.svista.Comune> cercaComuniPerNomeECodIstatProvincia(

        @WebParam(name = "in0", targetNamespace = "ente")
        java.lang.String in0,
        @WebParam(name = "in1", targetNamespace = "ente")
        int in1,
        @WebParam(name = "in2", targetNamespace = "ente")
        java.lang.String in2
    ) throws SystemException_Exception, ParInputValNonCorrettoException_Exception, OutputException_Exception, CSIException_Exception, UnrecoverableException_Exception, ParInputObblMancantiException_Exception;
}