/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.business.be.web;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.CognomeIntestatarioPersoneFisicheException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.DataNonValidaException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.DocumentoPraticaInesistenteException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.FruitoreNoModelliAssociatiException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.FruitoreNonAbilitatoModelloException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.FruitoreNonAbilitatoOperazioneException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.FruitoreNonAbilitatoTipologiaIstanzaException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.FunzioneNonAbilitataException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.GeneraNumeroMUDEType;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.IdentificativoIstanzaExt;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.IntestatarioNonPresenteException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.IstanzaExt;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.IstanzaInviataException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.IstanzaNonRegistrataPAException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.NomeIntestatarioPersoneFisicheException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.NumeroFascicoloComuneNonCoerentiException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.NumeroFascicoloNonCorrettoException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.NumeroIstanzaComuneNonCoerentiException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.NumeroIstanzaNonCorrettoException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.NumeroIstanzaObbligatorioException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.NumeroIstanzaRiferimentoInesistenteException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.ObjectFactory;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.PraticaNonConsultabileException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.ProtocollazioneIstanzaExt;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.RagioneSocialeIntestatarioPersoneGiuridicheException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.RuoloUtenteNonValidoException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.SpeciePraticaNonAbilitataException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.SpeciePraticaNonRiferitaModelloException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.TipoTracciatoNonAbilitatoException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.TipoTracciatoNonPrevistoTipoIstanzaException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.TipologiaIstanzaInesistenteException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.TipologiaIstanzaNonAbilitataFruitoreException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.TokenInesistenteException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.TracciatoVersioneNonCoerentiException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.VersioneTracciatoInesistenteException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.VersioneTracciatoNonAttivaException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.autenticazione.FruitoreNoComuniAssociatiException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.CampoObbligatorioException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.ComuneInsesistenteException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.ComuneNonAbilitatoException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.FruitoreNonAbilitatoComuneException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.IstanzaNonConsultabileException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.IstanzaNonTrovataException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.NumeroTicketInesistente_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.PraticaAssociataAdAltraIstanzaException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.SystemException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.UnrecoverableException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.gestionale.ValoreNonAmmessoException_Exception;
/**
 * This class was generated by Apache CXF 3.2.2
 * 2023-01-25T18:38:23.968+01:00
 * Generated source version: 3.2.2
 *
 */
@WebService(targetNamespace = "http://acquisizione.interfacews.mudesrvextsic.mude.csi.it/", name = "Acquisizione")
@XmlSeeAlso({ObjectFactory.class})
public interface Acquisizione {
    @WebMethod
    @RequestWrapper(localName = "generaNumeroMUDE", targetNamespace = "http://acquisizione.interfacews.mudesrvextsic.mude.csi.it/", className = "it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.GeneraNumeroMUDE")
    @ResponseWrapper(localName = "generaNumeroMUDEResponse", targetNamespace = "http://acquisizione.interfacews.mudesrvextsic.mude.csi.it/", className = "it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.GeneraNumeroMUDEResponse")
    @WebResult(name = "result", targetNamespace = "")
    public String generaNumeroMUDE(
        @WebParam(name = "token", targetNamespace = "")
        String token,
        @WebParam(name = "generaNumeroMUDE", targetNamespace = "")
        GeneraNumeroMUDEType generaNumeroMUDE
    ) throws FruitoreNonAbilitatoTipologiaIstanzaException_Exception, FruitoreNonAbilitatoComuneException_Exception, ComuneInsesistenteException_Exception, FruitoreNonAbilitatoOperazioneException_Exception, FruitoreNoModelliAssociatiException_Exception, ComuneNonAbilitatoException_Exception, NumeroTicketInesistente_Exception, FruitoreNoComuniAssociatiException_Exception, UnrecoverableException_Exception, FunzioneNonAbilitataException_Exception, ValoreNonAmmessoException_Exception, FruitoreNonAbilitatoModelloException_Exception, CampoObbligatorioException_Exception, TokenInesistenteException_Exception;
    @WebMethod
    @RequestWrapper(localName = "invioIstanza", targetNamespace = "http://acquisizione.interfacews.mudesrvextsic.mude.csi.it/", className = "it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.InvioIstanza")
    @ResponseWrapper(localName = "invioIstanzaResponse", targetNamespace = "http://acquisizione.interfacews.mudesrvextsic.mude.csi.it/", className = "it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.InvioIstanzaResponse")
    @WebResult(name = "result", targetNamespace = "")
    public String invioIstanza(
        @WebParam(name = "token", targetNamespace = "")
        String token,
        @WebParam(name = "istanzaExt", targetNamespace = "")
        IstanzaExt istanzaExt
    ) throws NomeIntestatarioPersoneFisicheException_Exception, RuoloUtenteNonValidoException_Exception, DataNonValidaException_Exception, IstanzaNonTrovataException_Exception, TipologiaIstanzaInesistenteException_Exception, NumeroIstanzaComuneNonCoerentiException_Exception, FruitoreNonAbilitatoTipologiaIstanzaException_Exception, TipoTracciatoNonPrevistoTipoIstanzaException_Exception, PraticaAssociataAdAltraIstanzaException_Exception, FruitoreNoModelliAssociatiException_Exception, NumeroIstanzaRiferimentoInesistenteException_Exception, ComuneNonAbilitatoException_Exception, IstanzaNonConsultabileException_Exception, VersioneTracciatoInesistenteException_Exception, NumeroTicketInesistente_Exception, NumeroFascicoloNonCorrettoException_Exception, FunzioneNonAbilitataException_Exception, ValoreNonAmmessoException_Exception, FruitoreNonAbilitatoModelloException_Exception, TokenInesistenteException_Exception, TipoTracciatoNonAbilitatoException_Exception, SystemException_Exception, VersioneTracciatoNonAttivaException_Exception, TipologiaIstanzaNonAbilitataFruitoreException_Exception, FruitoreNonAbilitatoComuneException_Exception, SpeciePraticaNonRiferitaModelloException_Exception, FruitoreNonAbilitatoOperazioneException_Exception, ComuneInsesistenteException_Exception, IntestatarioNonPresenteException_Exception, NumeroIstanzaNonCorrettoException_Exception, NumeroFascicoloComuneNonCoerentiException_Exception, IstanzaInviataException_Exception, PraticaNonConsultabileException_Exception, FruitoreNoComuniAssociatiException_Exception, CognomeIntestatarioPersoneFisicheException_Exception, UnrecoverableException_Exception, NumeroIstanzaObbligatorioException_Exception, TracciatoVersioneNonCoerentiException_Exception, CampoObbligatorioException_Exception, DocumentoPraticaInesistenteException_Exception, SpeciePraticaNonAbilitataException_Exception, RagioneSocialeIntestatarioPersoneGiuridicheException_Exception;
    @WebMethod
    @RequestWrapper(localName = "visualizzaDatiProtocollazioneIstanza", targetNamespace = "http://acquisizione.interfacews.mudesrvextsic.mude.csi.it/", className = "it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.VisualizzaDatiProtocollazioneIstanza")
    @ResponseWrapper(localName = "visualizzaDatiProtocollazioneIstanzaResponse", targetNamespace = "http://acquisizione.interfacews.mudesrvextsic.mude.csi.it/", className = "it.csi.mudeopen.mudeopensrvsoap.interfacews.acquisizione.VisualizzaDatiProtocollazioneIstanzaResponse")
    @WebResult(name = "result", targetNamespace = "")
    public ProtocollazioneIstanzaExt visualizzaDatiProtocollazioneIstanza(
        @WebParam(name = "token", targetNamespace = "")
        String token,
        @WebParam(name = "identificativoIstanzaExt", targetNamespace = "")
        IdentificativoIstanzaExt identificativoIstanzaExt
    ) throws SystemException_Exception, IstanzaNonRegistrataPAException_Exception, FruitoreNonAbilitatoTipologiaIstanzaException_Exception, FruitoreNonAbilitatoComuneException_Exception, FruitoreNonAbilitatoOperazioneException_Exception, FruitoreNoModelliAssociatiException_Exception, IstanzaNonConsultabileException_Exception, ComuneNonAbilitatoException_Exception, NumeroTicketInesistente_Exception, FruitoreNoComuniAssociatiException_Exception, UnrecoverableException_Exception, NumeroIstanzaObbligatorioException_Exception, ValoreNonAmmessoException_Exception, FruitoreNonAbilitatoModelloException_Exception, CampoObbligatorioException_Exception, TokenInesistenteException_Exception;
}
