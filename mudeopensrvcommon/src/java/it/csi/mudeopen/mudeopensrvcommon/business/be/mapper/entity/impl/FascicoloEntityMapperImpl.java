/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloIndirizzo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloIntestatario;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRFascicoloStato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTFascicolo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIndirizzo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ComuneEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.FascicoloEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.FascicoloIntestatarioSlimEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.FascicoloStatoSlimEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IndirizzoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ProvinciaEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.TipoInterventoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRFascicoloIndirizzoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRFascicoloIntestatarioRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRFascicoloStatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.ManagerAbilitazioni;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.UtilsDate;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.ContattoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.IndirizzoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.AbilitazioniEnum;
import it.csi.mudeopen.mudeopensrvcommon.vo.enumeration.FunzioniAbilitazioniEnum;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloIntestatarioSlimVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloStatoSlimVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.fascicolo.FascicoloVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ComuneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ProvinciaVO;

@Component
public class FascicoloEntityMapperImpl implements FascicoloEntityMapper {

    @Autowired
    private UtilsDate utilsDate;

    @Autowired
    private MudeTIstanzaRepository mudeTIstanzaRepository;

    @Autowired
    private MudeRFascicoloStatoRepository mudeRFascicoloStatoRepository;

    @Autowired
    private MudeRFascicoloIntestatarioRepository mudeRFascicoloIntestatarioRepository;

    @Autowired
    private MudeRFascicoloIndirizzoRepository mudeRFascicoloIndirizzoRepository;

    @Autowired
    private TipoInterventoEntityMapper tipoInterventoEntityMapper;

    @Autowired
    private FascicoloStatoSlimEntityMapper fascicoloStatoSlimEntityMapper;

    @Autowired
    private FascicoloIntestatarioSlimEntityMapper fascicoloIntestatarioSlimEntityMapper;

    @Autowired
    private ComuneEntityMapper comuneEntityMapper;

    @Autowired
    private ProvinciaEntityMapper provinciaEntityMapper;

    @Autowired
    private IndirizzoEntityMapper indirizzoEntityMapper;

    @Autowired
    private ManagerAbilitazioni managerAbilitazioni;

    @Override
    public FascicoloVO mapEntityToVO(MudeTFascicolo dto, MudeTUser mudeTUser) {
        FascicoloVO fascicoloVO = mapEntityToSlimVO(dto, mudeTUser);
        if (fascicoloVO != null) {
            //dto.setMudeTIstanzas(mudeTIstanzaRepository.findByMudeTFascicolo(dto));
        	boolean isCreator = (mudeTUser == null || managerAbilitazioni.hasUtenteAbilitazionePerFascicolo(AbilitazioniEnum.FASCIC_CREATORE.getDescription(), dto.getId(), mudeTUser.getIdUser()));

            // il fascicolo puo' essere sempre visualizzato
            fascicoloVO.setVisualizza(true);

//            List<MudeTIstanza> istances = mudeTIstanzaRepository.findByMudeTFascicolo(dto);
            int istances = mudeTIstanzaRepository.countIstanzeByFascicolo(dto.getId());

            // il fascicolo puo' essere modificato solo se non ci sono istanze agganciate al fascicolo
            boolean modify = istances > 0 ? false : true;
            fascicoloVO.setModifica(modify && isCreator);
            //fascicoloVO.setAssegnaAbilitazioni(modify && isCreator);

            // il fascicolo puo' essere eliminato solo se non ci sono istanze agganciate al fascicolo
            boolean delete = istances > 0 ? false : true;
            fascicoloVO.setElimina(delete && isCreator);
            boolean nuovaIstanza = false;

        	boolean hasAssegnaAbilitazioni = true; // JIRA always visible (mudeTUser == null || managerAbilitazioni.hasUtenteAbilitazioneFunzionePerFascicolo(FunzioniAbilitazioniEnum.ABILITA_CREA_IST.getDescription(), dto.getId(), mudeTUser.getIdUser()));
            fascicoloVO.setAssegnaAbilitazioni(hasAssegnaAbilitazioni);

            MudeRFascicoloStato mudeRFascicoloStato = mudeRFascicoloStatoRepository.findStatoByFascicolo(dto.getId());
            if (null != mudeRFascicoloStato) {
                FascicoloStatoSlimVO fascicoloStato = fascicoloStatoSlimEntityMapper.mapEntityToVO(mudeRFascicoloStato);
                fascicoloVO.setStatoFascicolo(fascicoloStato.getStatoFascicolo());
                fascicoloVO.setDataStato(utilsDate.asLocalDateTime(mudeRFascicoloStato.getDataInizio()));
                nuovaIstanza = "OPN".equalsIgnoreCase(mudeRFascicoloStato.getStatoFascicolo().getCodice());
            }
            fascicoloVO.setNuovaIstanza(nuovaIstanza && 
            		(mudeTUser == null || managerAbilitazioni.hasUtenteAbilitazionePerFascicolo(new String[] { 
            				AbilitazioniEnum.FASCIC_CREATORE.getDescription(),
            				AbilitazioniEnum.FASCIC_CREATORE_IST.getDescription(),
            			}, dto.getId(), mudeTUser.getIdUser())));

            // TODO - per valorizzare l'ubicazione, prendo l'ubicazione dell'istanza piu recente legata al fascicolo

        }

        return fascicoloVO;
    }

    public FascicoloVO mapEntityToSlimVO(MudeTFascicolo dto, MudeTUser mudeTUser, String filters) {
        FascicoloVO fascicoloVO = null;
        if (dto != null) {
            fascicoloVO = new FascicoloVO();
            fascicoloVO.setJsonData(dto.getJsonData());
            fascicoloVO.setDataCreazione(utilsDate.asLocalDateTime(dto.getDataCreazione()));
            fascicoloVO.setTipologiaIstanza(new SelectVO(dto.getTipoIstanza().getCodice(), dto.getTipoIstanza().getDescrizioneEstesa()));
            fascicoloVO.setTipologiaIntervento(tipoInterventoEntityMapper.mapEntityToVO(dto.getTipoIntervento()));
            fascicoloVO.setCodiceFascicolo(dto.getCodiceFascicolo());
            fascicoloVO.setUuidIndex(dto.getUuidIndex());
            fascicoloVO.setIdFascicolo(dto.getId());

            ContattoVO intestatario = null;

            MudeRFascicoloIntestatario fascicoloIntestatario = mudeRFascicoloIntestatarioRepository.findActiveByFascicolo(dto.getId());
            if (null != fascicoloIntestatario) {
                FascicoloIntestatarioSlimVO fascicoloIntestatarioSlim = fascicoloIntestatarioSlimEntityMapper.mapEntityToVO(fascicoloIntestatario);
                intestatario = fascicoloIntestatarioSlim.getIntestatario();
            }
            fascicoloVO.setIntestatario(intestatario);

	        if(!hasFilter(filters, "backoffice")) {
	            if (dto.getComune() != null) {
	                ComuneVO comune = comuneEntityMapper.mapEntityToVO(dto.getComune());
	                fascicoloVO.setComune(comune);
	                ProvinciaVO provincia = provinciaEntityMapper.mapEntityToVO(dto.getComune().getMudeDProvincia());
	                fascicoloVO.setProvincia(provincia);
	            }
	            
	            MudeRFascicoloIndirizzo mudeRFascicoloIndirizzo = mudeRFascicoloIndirizzoRepository.findByMudeTFascicolo_IdAndDataFineIsNull(dto.getId());
	            if(null != mudeRFascicoloIndirizzo){
	                MudeTIndirizzo mudeTIndirizzo = mudeRFascicoloIndirizzo.getIndirizzo();
	                IndirizzoVO indirizzoVO = indirizzoEntityMapper.mapEntityToVO(mudeTIndirizzo);
	                fascicoloVO.setUbicazione(indirizzoVO);
	            }
	        }
        }

        return fascicoloVO;
    }

	@Override
	public MudeTFascicolo mapVOtoEntity(FascicoloVO vo, MudeTUser user) {
		// not used
		return null;
	}
}