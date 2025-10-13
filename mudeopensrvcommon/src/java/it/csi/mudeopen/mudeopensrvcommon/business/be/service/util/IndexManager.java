/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.util;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeCProprieta;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTemplate;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloIndirizzo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTPratica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.exception.GenericException;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.IndexAllegatoMetadataProperty;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.IndexDocumentoMetadataProperty;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.IndexFolderFascicoloMetadataProperty;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.IndexFolderIstanzaMetadataProperty;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.IndexFolderPraticaMetadataProperty;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.IndexIstanzaMetadataProperty;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.MudeIndexServiceHelper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.dto.ErrorMessage;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.dto.FileFormatInfo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.dto.Node;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.dto.Property;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.dto.SearchParams;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.index.dto.VerifyReport;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeCProprietaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTemplateRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRFascicoloIndirizzoRepository;
import it.csi.mudeopen.mudeopensrvcommon.util.Constants;
import it.csi.mudeopen.mudeopensrvcommon.vo.allegato.AllegatoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.documento.DocumentoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Component
public class IndexManager {
    private static Logger logger = Logger.getLogger(IndexManager.class.getCanonicalName());

    @Autowired
    private MudeIndexServiceHelper indexServiceHelper;

    @Autowired
    private MudeCProprietaRepository mudeCProprietaRepository;

    @Autowired
    private MudeDTemplateRepository mudeDTemplateRepository;

    @Autowired
    private MudeRFascicoloIndirizzoRepository mudeRFascicoloIndirizzoRepository;

    private String tempFileUUID = null;

    public String createIndexFolderFascicolo(FascicoloVO fascicolo) {
        if (!this.indexPathExist(fascicolo.getCodiceFascicolo(), null, null)) {
            IndexFolderFascicoloMetadataProperty metadata = new IndexFolderFascicoloMetadataProperty();
            metadata.setNumeroIntervento(fascicolo.getCodiceFascicolo());
            MudeRFascicoloIndirizzo mudeRFascicoloIndirizzo = mudeRFascicoloIndirizzoRepository.findByMudeTFascicolo_IdAndDataFineIsNull(fascicolo.getIdFascicolo());
            if (null != mudeRFascicoloIndirizzo) {
                metadata.setCodiceIstatComune(mudeRFascicoloIndirizzo.getIndirizzo().getMudeDComune().getIstatComune());
            }
            int anno = Calendar.getInstance().get(Calendar.YEAR);
            metadata.setAnnoPresentazione(String.valueOf(anno));

            Optional<MudeCProprieta> mudeCProprietaOPT = mudeCProprietaRepository.findByName(Constants.CONFIG_KEY_ALLEGATI_TENANT_UUID);
            if (mudeCProprietaOPT.isPresent()) {
                String uuidIndexRoot = mudeCProprietaOPT.get().getValore();
                if (StringUtils.isBlank(uuidIndexRoot)) {
                    return null;
                }
                return indexServiceHelper.createFascicoloFolderContent(fascicolo.getCodiceFascicolo(), uuidIndexRoot.trim(), metadata);
            } else {
                return null;
            }
        } else {
            List<Node> nodes = this.searchIndex(fascicolo.getCodiceFascicolo(), null, null, false);
            String uuid = null;
            for (Node node : nodes) {
                String name = node.getPrefixedName().substring(3);
                if (fascicolo.getCodiceFascicolo().equalsIgnoreCase(name)) {
                    uuid = node.getUid();
                }
            }
            return uuid;
        }
    }

    public String updateIndexFolderFascicolo(FascicoloVO fascicolo) {
        String codiceIstatComune = null;
        MudeRFascicoloIndirizzo mudeRFascicoloIndirizzo = mudeRFascicoloIndirizzoRepository.findByMudeTFascicolo_IdAndDataFineIsNull(fascicolo.getIdFascicolo());
        if (null != mudeRFascicoloIndirizzo) {
            codiceIstatComune = mudeRFascicoloIndirizzo.getIndirizzo().getMudeDComune().getIstatComune();
        }
        List<Node> nodes = this.searchIndex(fascicolo.getCodiceFascicolo(), null, null, false);
        String uuid = null;
        List<Property> properties = new ArrayList<>();
        for (Node node : nodes) {
            String name = node.getPrefixedName().substring(3);
            if (fascicolo.getCodiceFascicolo().equalsIgnoreCase(name)) {
                if(StringUtils.isNotBlank(codiceIstatComune)) {
                    Property property = new Property();
                    property.setPrefixedName("mude:codiceIstatComune");
                    property.setMultivalue(false);
                    property.setDataType("d:text");
                    List<String> values = new ArrayList<>();
                    values.add(codiceIstatComune);
                    property.setValues(values);
                    properties.add(property);
                    node.setProperties(properties);
                    indexServiceHelper.updateNodeMetadata(node);
                }
            }
        }
        return uuid;
    }

    public String createIndexFolderIstanza(IstanzaVO istanza) {
        if (!this.indexPathExist(istanza.getFascicoloVO().getCodiceFascicolo(), istanza.getCodiceIstanza(), null)) {
            IndexFolderIstanzaMetadataProperty metadata = new IndexFolderIstanzaMetadataProperty();
            metadata.setNumeroMudeIntervento(istanza.getFascicoloVO().getCodiceFascicolo());
            String uuidIndexFascicolo = istanza.getFascicoloVO().getUuidIndex();

            if (StringUtils.isBlank(uuidIndexFascicolo)) {
                return null;
            }
            return indexServiceHelper.createIstanzaFolderContent(istanza.getCodiceIstanza(), uuidIndexFascicolo, metadata);
        } else {
            List<Node> nodes = this.searchIndex(istanza.getFascicoloVO().getCodiceFascicolo(), istanza.getCodiceIstanza(), null, false);
            String uuid = null;
            for (Node node : nodes) {
                String name = node.getPrefixedName().substring(3);
                if (istanza.getCodiceIstanza().equalsIgnoreCase(name)) {
                    uuid = node.getUid();
                }
            }
            return uuid;
        }
    }

    public String createIndexFolderPratica(MudeTPratica mudeTPratica) {	//TODO SD - da sostituire con PraticaVO appena possibile
    	String convertForIndex = mudeCProprietaRepository.getValueByName("INDEX_CONVERT_FOLDER_CHARS", "[:\\/.]");
    	String numeroPratica = mudeTPratica.getNumeroPratica().replaceAll(convertForIndex, "_");
    	
    	// MF: lucene does not find anything even if it exists!
    	//if(mudeTPratica.getUuidIndex() != null)
    	//	return mudeTPratica.getUuidIndex();
    	
    	MudeTIstanza mudeTIstanza = mudeTPratica.getIstanze().get(0);
    	String codiceFascicolo = mudeTIstanza.getMudeTFascicolo().getCodiceFascicolo();	// SD - sostituire con il codice fascicolo preso direttamente da PraticaVO
        if (!this.indexPathExist(codiceFascicolo, numeroPratica, null)) {
            IndexFolderPraticaMetadataProperty metadata = new IndexFolderPraticaMetadataProperty();
            metadata.setNumeroMudeIntervento(codiceFascicolo);
            String uuidIndexFascicolo = mudeTIstanza.getMudeTFascicolo().getUuidIndex();
            if (StringUtils.isBlank(uuidIndexFascicolo)) {
                return null;
            }
            return indexServiceHelper.createPraticaFolderContent(numeroPratica, uuidIndexFascicolo, metadata);
        } else {
            List<Node> nodes = this.searchIndex(codiceFascicolo, numeroPratica, null, false);
            String uuid = null;
            for (Node node : nodes) {
                String name = node.getPrefixedName().substring(3);
                if (numeroPratica.equalsIgnoreCase(name)) {
                    uuid = node.getUid();
                }
            }
            return uuid;
        }
    }

    public String createIndexAllegatoContent(AllegatoVO allegato, File file, MudeTUser user) {
        IndexAllegatoMetadataProperty metadata = new IndexAllegatoMetadataProperty();
        metadata.setCodiceTipoAllegato(allegato.getTipoAllegato().getCodice());
        metadata.setNumeroInterventoAllegato(allegato.getFascicolo().getCodiceFascicolo());
        metadata.setNumeroMudeAllegato(allegato.getIstanza().getCodiceIstanza());
        metadata.setAuthor(user.getCf());
        String uuidIndexFascicolo = allegato.getFascicolo().getUuidIndex();
        String uuidIndexIstanza = allegato.getIstanza().getUuidIndex();

        if (StringUtils.isBlank(uuidIndexFascicolo)) {
            return null;
        }
        if (StringUtils.isBlank(uuidIndexIstanza)) {
            return null;
        }

        return indexServiceHelper.createAllegatoContent(allegato.getNomeFileAllegato(), uuidIndexIstanza, file, metadata);
    }

    public String createIndexDocumentoContent(DocumentoVO documento, MudeTPratica mudeTPratica, String istatComune, File file, String user) {
        IndexDocumentoMetadataProperty metadata = new IndexDocumentoMetadataProperty();
        SimpleDateFormat getYearFormat = new SimpleDateFormat("yyyy");
        String anno = getYearFormat.format(documento.getDataCaricamento());
        metadata.setAnnoCreazioneDocumentoPA(anno);
        metadata.setCodiceTipologia(documento.getTipoDocumento().getCodice());
        metadata.setNumeroPraticaDocumentoPA(mudeTPratica.getNumeroPratica());
        metadata.setIstatComuneDocumentoPA(istatComune);
    	metadata.setAuthor(user);
        //String uuidIndexFascicolo = allegato.getFascicolo().getUuidIndex();
        String uuidIndexPratica = mudeTPratica.getUuidIndex();
        /*if (StringUtils.isBlank(uuidIndexFascicolo)) {
            return null;
        }*/
        if (StringUtils.isBlank(uuidIndexPratica)) {
            return null;
        }
        return indexServiceHelper.createDocumentoContent(documento.getNomeFileDocumento(), uuidIndexPratica, file, metadata);
    }

    public String createIndexIstanzaContent(IstanzaVO istanza, File file) {
        IndexIstanzaMetadataProperty metadata = new IndexIstanzaMetadataProperty();
        metadata.setNumeroIntervento(istanza.getFascicoloVO().getCodiceFascicolo());
        metadata.setCodiceModello(istanza.getTipologiaIstanza().getId());
        MudeDTemplate template = mudeDTemplateRepository.findOne(istanza.getIdTemplate());
        String codiceVersioneModello = "v_" + template.getNumeroVersione();
        metadata.setCodiceVersioneModello(codiceVersioneModello);
        metadata.setNumeroMudeIstanza(istanza.getCodiceIstanza());
        String uuidIndexFascicolo = istanza.getFascicoloVO().getUuidIndex();
        String uuidIndexIstanza = istanza.getUuidIndex();

        if (StringUtils.isBlank(uuidIndexFascicolo)) {
            return null;
        }
        if (StringUtils.isBlank(uuidIndexIstanza)) {
            return null;
        }

        return indexServiceHelper.createIstanzaContent(istanza.getFilename(), uuidIndexIstanza, file, metadata);
    }

    public File getFileByUuid(String uuid) {
        return indexServiceHelper.retrieveContentData(uuid);
    }

    public Node getMetadataIndexByUuid(String uuid) {
        return indexServiceHelper.getContentMetadata(uuid);
    }

    public ErrorMessage deleteContenutoByUuid(String uuid) {
    	try {
            return indexServiceHelper.deleteContent(uuid);
		} catch (Throwable e) {
            logger.error("[IndexManager::deleteContenutoByUuid] Error: Si è verificato un errore nella cancellazione della firma del documento. ", e);
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setStatus(500);
            errorMessage.setTitle(e.getMessage());

            return errorMessage;
		}
    }

    public VerifyReport verifySignedDocument(String uid, File file) {
        try {
            return indexServiceHelper.verifySignedDocument(uid, file);
        } catch (GenericException e) {
            logger.error("[IndexManager::verifySignedDocument] Error: Si è verificato un errore nella verifica della firma del documento. ", e);
            return null;
        }
    }

    public FileFormatInfo verifyBinaryDocumentBySignedFile(File file) {
        try {

            InputStream targetStream = new FileInputStream(file);
            String lUUID = indexServiceHelper.extractIDFileDocumentFromEnvelope(targetStream, file);
            this.tempFileUUID = lUUID;
            return indexServiceHelper.identifyTempDocument(lUUID);
            //File lFileBinary= indexServiceHelper.retrieveTempContentData(lUUID);
            //InputStream targetStreamBinaryFile = new FileInputStream(file);
            //return indexServiceHelper.verifyBinaryDocument(targetStreamBinaryFile);

        } catch (GenericException e) {
            logger.error("[IndexManager::verifySignatureDocument] Error: (1) Si è verificato un errore nella verifica della firma del documento. ", e);
            return null;
        } catch (FileNotFoundException e) {
            logger.error("[IndexManager::verifySignatureDocument] Error: (2 ) Si è verificato un errore nella verifica della firma del documento. ", e);
            return null;
        }
    }

    public FileFormatInfo verifyBinaryDocument(File file) {
        try {
            InputStream targetStream = new FileInputStream(file);
            return indexServiceHelper.verifyBinaryDocument(targetStream, file);
        } catch (GenericException e) {
            logger.error("[IndexManager::verifySignatureDocument] Error: (1) Si è verificato un errore nella verifica della firma del documento. ", e);
            return null;
        } catch (FileNotFoundException e) {
            logger.error("[IndexManager::verifySignatureDocument] Error: (2 ) Si è verificato un errore nella verifica della firma del documento. ", e);
            return null;
        }
    }

    public File getExtractedDocumentFromEnvelopeFile(File file) {
        try {
            return indexServiceHelper.getExtractedDocumentFromEnvelopeFile(file);
        } catch (GenericException e) {
            logger.error("[IndexManager::verifySignedDocument] Error: Si è verificato un errore nella verifica della firma del documento. ", e);
            return null;
        }
    }

    public Boolean indexPathExist(String codFascicolo, String codIstanza, String filename) {
        List<Node> nodeList = indexServiceHelper.luceneSearch(Boolean.TRUE, getIndexSearchParams(codFascicolo, codIstanza, filename, false));
        return nodeList != null && !nodeList.isEmpty() ? Boolean.TRUE : Boolean.FALSE;
    }

    public List<Node> searchIndex(String codFascicolo, String codIstanza, String filename, boolean searchContent) {
        List<Node> nodeList = indexServiceHelper.luceneSearch(Boolean.TRUE, getIndexSearchParams(codFascicolo, codIstanza, filename, searchContent));
        return nodeList;
    }

    private SearchParams getIndexSearchParams(String codFascicolo, String codIstanza, String filename, boolean searchContent) {

        logger.debug("[IndexManager::getIndexSearchParams] Parametro in input codFascicolo [" + codFascicolo + "] - codIstanza [" + codIstanza + "] - filename [" + filename + "]");
        SearchParams searchParams = new SearchParams();
        String luceneQuery = "PATH:\"/app:company_home/cm:mude";
        if (StringUtils.isNotBlank(codFascicolo)) {
            luceneQuery += "/cm:" + codFascicolo;
            if (StringUtils.isNotBlank(codIstanza)) {
                luceneQuery += "/cm:" + codIstanza;
            }
        }

        if (StringUtils.isNotBlank(filename)) {
            luceneQuery += "/cm:" + filename;
        }
        if (searchContent) {
            luceneQuery += "/*\"";
        } else {
            luceneQuery += "\"";
        }

        searchParams.setLuceneQuery(luceneQuery);
        logger.debug("[IndexManager::getIndexSearchParams] luceneQuery : [" + luceneQuery + "]");

        return searchParams;
    }

    public String getTempFileUUID() {
        return tempFileUUID;
    }

    public void setTempFileUUID(String tempFileUUID) {
        this.tempFileUUID = tempFileUUID;
    }

    //######################################
}