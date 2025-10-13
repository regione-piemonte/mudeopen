/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvsoap.business.be.web;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import it.csi.mudeopen.mudeopensrvsoap.vo.AllegaDocumentoSoapResponse;
import it.csi.mudeopen.mudeopensrvsoap.vo.FileOutput;
import it.csi.mudeopen.mudeopensrvsoap.vo.UploadedFile;
import it.csi.mudeopen.mudeopensrvsoap.vo.UploadedFileBase64;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.documenti.CampoObbligatorioException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.documenti.ComuneNonAbilitatoException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.documenti.FruitoreDisabilitatoException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.documenti.FruitoreNonAbilitatoComuneException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.documenti.IstanzaNonTrovataException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.documenti.NotAuthorizedException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.documenti.NumeroTicketInesistente_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.documenti.PraticaEsistenteException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.documenti.SystemException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.documenti.AllegatoNonTrovatoException_Exception;
import it.csi.mudeopen.mudeopensrvsoap.interfacews.documenti.StatoIstanzaNonValidoException_Exception;
@WebService(targetNamespace = "http://documenti.interfacews.mudesrvextsic.mude.csi.it/", name = "Documenti")
public interface Documenti {

    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @WebMethod
    FileOutput estraiFileIstanza(@WebParam(name = "token") String token, @WebParam(name = "numeroIstanza") String numeroIstanza) throws IstanzaNonTrovataException_Exception,
																																		ComuneNonAbilitatoException_Exception, 
    																																	CampoObbligatorioException_Exception,
    																																	FruitoreDisabilitatoException_Exception,
																																		StatoIstanzaNonValidoException_Exception, 
																																		AllegatoNonTrovatoException_Exception,
																																		FruitoreNonAbilitatoComuneException_Exception,
																																		NumeroTicketInesistente_Exception,
																																		SystemException_Exception;

    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @WebMethod(action = "")
    FileOutput estraiAllegatoIstanza(@WebParam(name = "token") String token, @WebParam(name = "numeroIstanza") String numeroIstanza
    		,@WebParam(name ="nomeFileAllegato") String nomeFileAllegato, @WebParam(name = "sbustato") String sbustato) throws CampoObbligatorioException_Exception,
																															    IstanzaNonTrovataException_Exception, 
																																FruitoreDisabilitatoException_Exception,
																																ComuneNonAbilitatoException_Exception, 
																																StatoIstanzaNonValidoException_Exception, 
																																AllegatoNonTrovatoException_Exception,
																																FruitoreNonAbilitatoComuneException_Exception,
																																NumeroTicketInesistente_Exception,
																																SystemException_Exception;

    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @WebMethod(action = "")
    FileOutput estraiDocumentoPratica(@WebParam(name = "token") String token, @WebParam(name = "istatComune") String istatComune, @WebParam(name = "numeroPratica") String numeroPratica,
    		@WebParam(name = "annoPratica") String annoPratica, @WebParam(name = "nomeDocumento") String nomeDocumento) throws NumeroTicketInesistente_Exception,
																																AllegatoNonTrovatoException_Exception,
																																FruitoreDisabilitatoException_Exception,
																																CampoObbligatorioException_Exception,
																																PraticaEsistenteException_Exception,
																																FruitoreNonAbilitatoComuneException_Exception,
																																NotAuthorizedException_Exception,
																																SystemException_Exception;

    @Produces({ MediaType.APPLICATION_XML })
    @WebMethod(action = "")
    AllegaDocumentoSoapResponse allegaDocumentoPratica(@WebParam(name = "token") String token, @WebParam(name = "istatComune") String istatComune, @WebParam(name = "numeroPratica") String numeroPratica,
    		@WebParam(name = "anno") String anno, @WebParam(name = "tipoDocumento") String tipoDocumento,
    		@WebParam(name="fileDocumento") UploadedFile file) throws NumeroTicketInesistente_Exception,
																		AllegatoNonTrovatoException_Exception,
																		FruitoreDisabilitatoException_Exception,
																		CampoObbligatorioException_Exception,
																		FruitoreNonAbilitatoComuneException_Exception,
																		NotAuthorizedException_Exception,
																		PraticaEsistenteException_Exception,
																		IstanzaNonTrovataException_Exception,
																		SystemException_Exception;
    @Produces({ MediaType.APPLICATION_XML })
    @WebMethod(action = "")
    AllegaDocumentoSoapResponse allegaDocumentoPraticaBase64(@WebParam(name = "token") String token, @WebParam(name = "istatComune") String istatComune, @WebParam(name = "numeroPratica") String numeroPratica,
    		@WebParam(name = "anno") String anno, @WebParam(name = "tipoDocumento") String tipoDocumento,
    		@WebParam(name="fileDocumentoBase64") UploadedFileBase64 file) throws NumeroTicketInesistente_Exception,
																		AllegatoNonTrovatoException_Exception,
																		FruitoreDisabilitatoException_Exception,
																		CampoObbligatorioException_Exception,
																		FruitoreNonAbilitatoComuneException_Exception,
																		NotAuthorizedException_Exception,
																		PraticaEsistenteException_Exception,
																		IstanzaNonTrovataException_Exception,
																		SystemException_Exception;

    @Produces({ MediaType.APPLICATION_XML })
    @WebMethod(action = "")
    void eliminaDocumentoPratica(@WebParam(name = "token") String token, @WebParam(name = "istatComune") String istatComune, @WebParam(name = "numeroPratica") String numeroPratica,
    		@WebParam(name = "annoPratica") String annoPratica, @WebParam(name = "nomeDocumento") String nomeDocumento) throws 
																													    NumeroTicketInesistente_Exception,
																														CampoObbligatorioException_Exception,
																														FruitoreDisabilitatoException_Exception,
																														AllegatoNonTrovatoException_Exception,
																														PraticaEsistenteException_Exception,
																														FruitoreNonAbilitatoComuneException_Exception,
																														NotAuthorizedException_Exception,
																														SystemException_Exception;
}
