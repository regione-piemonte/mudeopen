/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.service.impl;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.*;
import it.csi.mudeopen.mudeopensrvcommon.business.be.helper.mudeopensrvcommon.AllegatiExcelBuilder;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.AllegatoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoAllegatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTAllegatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTAllegatoSlimRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.AllegatiService;
import it.csi.mudeopen.mudeopensrvcommon.vo.allegato.AllegatoVO;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class AllegatiServiceImpl implements AllegatiService {
    private static Logger logger = Logger.getLogger(AllegatiServiceImpl.class.getCanonicalName());

    @Autowired
    private MudeTAllegatoRepository mudeTAllegatoRepository;

    @Autowired
    private MudeTAllegatoSlimRepository mudeTAllegatoSlimRepository;

    @Autowired
    private MudeTIstanzaRepository mudeTIstanzaRepository;

    @Autowired
    private MudeDTipoAllegatoRepository mudeDTipoAllegatoRepository;

    @Autowired
    private AllegatoEntityMapper allegatoEntityMapper;

    @Override
    public Boolean verifyDuplicateFilename(Long idIstanza, String filename, String codAllegato) {
        Optional<MudeTAllegato> allegatoOPT = mudeTAllegatoRepository.findByIdIstanzaAndFilename(idIstanza, filename);
        if(allegatoOPT.isPresent()) {return Boolean.TRUE;}
        return Boolean.FALSE;
    }

    @Override
    public AllegatoVO saveAllegato(AllegatoVO allegato, MudeTUser mudeTUser) {
        MudeTAllegato entity = new MudeTAllegato();

        MudeTIstanza istanza = mudeTIstanzaRepository.findOne(allegato.getIstanza().getIdIstanza());
        MudeTFascicolo fascicolo = istanza.getMudeTFascicolo();
        MudeDTipoAllegato tipoAllegato = mudeDTipoAllegatoRepository.findOne(allegato.getTipoAllegato().getCodice());
        entity.setIstanza(istanza);
//        entity.setFascicolo(fascicolo);
        if(null != tipoAllegato){
            entity.setTipoAllegato(tipoAllegato);
        }
        entity.setNomeFileAllegato(allegato.getNomeFileAllegato());
        entity.setDescBreveAllegato(allegato.getDescBreveAllegato());
        entity.setDataCaricamento(Calendar.getInstance().getTime());
        entity.setFileContent(allegato.getFileContent());
        entity.setDimensioneFile(allegato.getDimensioneFile());
//        entity.setFileUID(allegato.getFileUID());
        entity.setFileUID(allegato.getFileUID());

        if(allegato.getNomeFileAllegato().toLowerCase().endsWith(".p7m")) {
        	entity.setFirmato(true);
        	entity.setMimetype("application/pkcs7-mime");
        }
        else
        	entity.setMimetype(allegato.getMimetype());

        entity.setUser(mudeTUser);

        mudeTAllegatoRepository.saveDAO(entity);
        return allegatoEntityMapper.mapEntityToVO(entity, null);
    }

    @Override
    public AllegatoVO loadAllegato(Long idAllegato) {
        Optional<MudeTAllegato> entityOPT = mudeTAllegatoRepository.findById(idAllegato);
        AllegatoVO vo = null;
        if(entityOPT.isPresent()){
            vo = allegatoEntityMapper.mapEntityToVO(entityOPT.get(), null);
        }
        return vo;
    }

    @Override
    public AllegatoVO loadAllegato(Long idAllegato, MudeTUser mudeTUser) {
        Optional<MudeTAllegato> entityOPT = mudeTAllegatoRepository.findByIdAndUser(idAllegato, mudeTUser.getIdUser());
        AllegatoVO vo = null;
        if(entityOPT.isPresent()){
            vo = allegatoEntityMapper.mapEntityToVO(entityOPT.get(), null);
        }
        return vo;
    }

    @Override
    public byte[] downloadFileAllegato(Long idAllegato) {
        return mudeTAllegatoRepository.findFileByIdAllegato(idAllegato);
    }

    @Override
    public void deleteAllegato(Long idAllegato) {
        mudeTAllegatoRepository.delete(idAllegato);
    }

    @Override
    public List<AllegatoVO> loadAllegatiIstanza(Long idIstanza, MudeTUser mudeTUser) {
    	return loadAllegatiIstanza(idIstanza, null, mudeTUser, null);
    }

    @Override
    public List<AllegatoVO> loadAllegatiIstanza(Long idIstanza, String tipo_codice, MudeTUser mudeTUser, String filters) {
        List<MudeTAllegato> entities = tipo_codice == null? 
        			mudeTAllegatoRepository.findAllByIstanza(idIstanza) :
        			mudeTAllegatoRepository.findAllByIstanza_IdAndTipoAllegato_CodiceAndDataFineIsNull(idIstanza, tipo_codice);

        return allegatoEntityMapper.mapListEntityToListVO(entities, mudeTUser, filters);
    }

    @Override
    public List<MudeTAllegatoSlim> loadAllegatiSlimIstanza(Long idIstanza, MudeTUser mudeTUser) {
        return loadAllegatiSlimIstanza(idIstanza, null, mudeTUser);
    }

    @Override
    public List<MudeTAllegatoSlim> loadAllegatiSlimIstanza(Long idIstanza, String tipo_allegato, MudeTUser mudeTUser) {
        List<MudeTAllegatoSlim> entities = tipo_allegato == null?
                mudeTAllegatoSlimRepository.findAllByIstanza(idIstanza) :
                mudeTAllegatoSlimRepository.findAllByIstanzaAndTipo(idIstanza, tipo_allegato);
        return entities;

    }

    @Override
    public AllegatoVO findByFileUID(String fileUID) {
        Optional<MudeTAllegato> entityOPT = mudeTAllegatoRepository.findByFileUID(fileUID);
        AllegatoVO vo = null;
        if(entityOPT.isPresent()){
            vo = allegatoEntityMapper.mapEntityToVO(entityOPT.get(), null);
        }
        return vo;
    }

    @Override
    public MudeTAllegatoSlim findSlimByFileUID(String fileUID) {
        Optional<MudeTAllegatoSlim> entityOPT = mudeTAllegatoSlimRepository.findByFileUID(fileUID);
        MudeTAllegatoSlim res = null;
        if(entityOPT.isPresent()){
            res = entityOPT.get();
        }
        return res;
    }

    @Override
	public byte[] exportExcelAllegatiIstanza(List<AllegatoVO> vos) {

        byte[] fileContent = null;

        AllegatiExcelBuilder allegatiExcelBuilder = new AllegatiExcelBuilder();
        HSSFWorkbook workbook = new HSSFWorkbook();
        try {
            fileContent = allegatiExcelBuilder.buildExcelDocuments(vos, workbook);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return fileContent;
    }

    @Override
    public MudeDTipoAllegato findTipoAllegatoByCodice(String codice) {

        Optional<MudeDTipoAllegato> entityOPT = mudeDTipoAllegatoRepository.findByCodice(codice);
        MudeDTipoAllegato res = null;
        if(entityOPT.isPresent()){
            res = entityOPT.get();
        }
        return res;
    }
}