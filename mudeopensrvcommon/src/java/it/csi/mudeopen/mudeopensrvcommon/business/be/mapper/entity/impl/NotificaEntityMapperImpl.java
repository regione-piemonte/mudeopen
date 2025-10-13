/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRNotificaDocumento;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRNotificaUtente;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTEnte;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTNotifica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.DocumentoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.EnteEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IstanzaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.NotificaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.UtenteEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRNotificaDocumentoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTEnteRepository;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.AnagraficaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.ContattoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.documento.DocumentoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.notifica.NotificaVO;

@Component
public class NotificaEntityMapperImpl implements NotificaEntityMapper {

    @Autowired
    private UtenteEntityMapper utenteEntityMapper;

    @Autowired
    private EnteEntityMapper enteEntityMapper;

    @Autowired
    private MudeTEnteRepository mudeTEnteRepository;

    @Autowired
    private IstanzaEntityMapper istanzaEntityMapper;

    @Autowired
    MudeRNotificaDocumentoRepository mudeRNotificaDocumentoRepository;

    @Autowired
    DocumentoEntityMapper documentoEntityMapper;

	@Override
    public NotificaVO mapEntityToVOList(MudeTNotifica dto, List<MudeRNotificaUtente>  mudeRNotificaUtenteList) {
        if (dto == null)
            return null;

        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern); 

        NotificaVO vo = new NotificaVO();
        vo.setId_notifica(dto.getId());
        vo.setMittente(utenteEntityMapper.mapEntityToVO(dto.getMudeTUser()));
        vo.setDataNotifica(dto.getDataInizio());
        vo.setOggettoNotifica(dto.getOggettoNotifica());
        vo.setTestoNotifica(dto.getTestoNotifica());
        vo.setDettaglioNotifica(dto.getDettaglio());
        vo.setTipoNotifica(dto.getTipoNotifica());
        vo.setRuoloMittente(dto.getRuoloMittente());
        vo.setRuoloMittente(dto.getRuoloMittente());
        if(mudeRNotificaUtenteList != null && mudeRNotificaUtenteList.size()>0) {
        	List<MudeTUser> mudeTUserList = new ArrayList<>();
        	List<SelectVO> destinatari = new ArrayList<>();
        	for (MudeRNotificaUtente mudeRNotificaUtente: mudeRNotificaUtenteList) {        		
        		mudeTUserList.add(mudeRNotificaUtente.getMudeTUser());
        		if(mudeRNotificaUtente.getMudeTUser() != null && !mudeRNotificaUtente.getMudeTUser().getNome().isEmpty())
        			destinatari.add(new SelectVO(
        					(mudeRNotificaUtente.getMudeTUser().getNome() + " " + mudeRNotificaUtente.getMudeTUser().getCognome()),
        					mudeRNotificaUtente.getDataLettura()==null? "" : df.format(mudeRNotificaUtente.getDataLettura())));
        	}
        	vo.setDestinatari(destinatari);
        	vo.setDestinatarioList(utenteEntityMapper.mapListEntityToListVO(mudeTUserList));	
        }

        vo.setRifDocumenti(false);
        List<MudeRNotificaDocumento> documenti = mudeRNotificaDocumentoRepository.findByMudeTNotifica(dto);
		if (documenti != null && documenti.size()>0) {
			List<DocumentoVO> documentiRifList = new ArrayList<>();
			vo.setRifDocumenti(true);
			
			for(MudeRNotificaDocumento mudeRNotificaDocumento : documenti) {
				DocumentoVO documentoVO = null;
				documentoVO = documentoEntityMapper.mapEntityToVO(mudeRNotificaDocumento.getMudeTDocumento(), null);
				documentoVO.setNotificato(true);
				documentiRifList.add(documentoVO);
			}
			vo.setDocumentiRif(documentiRifList);
		}
        return vo;
    }

	@Override
	public MudeTNotifica mapVOtoEntity(NotificaVO vo, MudeTUser user) {
		return null;
	}

	@Override
	public NotificaVO mapEntityToVO(MudeTNotifica dto, MudeTUser user) {
        NotificaVO vo = mapEntityToVOList(dto, null);

        // set ente Mittente
        if(dto.getMudeTUser() != null) {
	        MudeTEnte mudeTEnte = mudeTEnteRepository.retrieveEnteFromOperator(dto.getMudeTUser().getIdUser(), dto.getIstanza().getId());
	        vo.setEnteMittente(enteEntityMapper.mapEntityToVO(mudeTEnte));
        }

        vo.setIstanza(istanzaEntityMapper.mapEntityToSlimVO(dto.getIstanza(), null, "essential"));
        if(dto.getJsonData() != null) {
	        String[] intestatarioData = dto.getJsonData().split("~");
	        if(intestatarioData.length >= 3) {
		        ContattoVO intestatario = new ContattoVO();
		        AnagraficaVO anagrafica = new AnagraficaVO();
		        anagrafica.setNome(intestatarioData[0]);
		        anagrafica.setCognome(intestatarioData[1]);
		        anagrafica.setCodiceFiscale(intestatarioData[2]);
				intestatario.setAnagrafica(anagrafica);
				vo.getIstanza().setIntestatario(intestatario);
	        
		        if(intestatarioData.length == 4)
		        	vo.setLetto(!"f".equals(intestatarioData[3]));
	        }
        }

        vo.setId_notifica(dto.getId());
        vo.setTestoNotifica(dto.getTestoNotifica());

		return vo;
	}
}