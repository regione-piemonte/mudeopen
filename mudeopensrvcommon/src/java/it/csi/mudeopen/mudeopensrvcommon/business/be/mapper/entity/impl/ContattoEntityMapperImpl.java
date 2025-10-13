/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.impl;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDComune;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDNazione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDProvincia;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDQualifica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRContattoQualifica;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeRPfPg;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTIndirizzo;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTUser;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.ContattoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.mapper.entity.IndirizzoEntityMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDComuneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDNazioneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDOrdineRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDProvinciaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDQualificaCollegioRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDQualificaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaSoggettoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRPfPgRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTContattoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIndirizzoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTUserRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.UtilsDate;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.AnagraficaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.ContattoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.IndirizzoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.QualificaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.TipoContatto;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ComuneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.NazioneVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.luoghi.ProvinciaVO;

@Component
public class ContattoEntityMapperImpl implements ContattoEntityMapper {

    @Autowired
    private MudeDNazioneRepository mudeDNazioneRepository;

    @Autowired
    private MudeDProvinciaRepository mudeDProvinciaRepository;

    @Autowired
    private MudeDComuneRepository mudeDComuneRepository;

    @Autowired
    private MudeTIndirizzoRepository mudeTIndirizzoRepository;

    @Autowired
    private MudeTContattoRepository mudeTContattoRepository;

    @Autowired
    private MudeRPfPgRepository mudeRPfPgRepository;

    @Autowired
    private MudeRIstanzaSoggettoRepository mudeRIstanzaSoggettoRepository;

    @Autowired
    private MudeDQualificaRepository mudeDQualificaRepository;

    @Autowired
    private MudeDOrdineRepository mudeDOrdineRepository;

    @Autowired
    private IndirizzoEntityMapper indirizzoEntityMapper;

    @Autowired
    private ContattoEntityMapper contattoEntityMapper;

    @Autowired
    private UtilsDate utilsDate;

    @Autowired
    private MudeTUserRepository mudeTUserRepository;

    @Autowired
    private MudeDQualificaCollegioRepository mudeDQualificaCollegioRepository;

    @Override
    public ContattoVO mapEntityToVO(MudeTContatto dto, MudeTUser user, String filters) {
    	ContattoVO vo = mapEntityToSlimVO(dto, user);
    	
        try {
        	AnagraficaVO anagrafica = vo.getAnagrafica();

            List<MudeTIndirizzo> indirizzi = mudeTIndirizzoRepository.findByMudeTContattoAndDataFineIsNull(dto);
            if(!indirizzi.isEmpty()) {
                vo.setIndirizzi(indirizzoEntityMapper.mapListEntityToListVO(indirizzi));
            }

            //se il contatto è un pf
            if (dto.getTipoContatto().name().equalsIgnoreCase(TipoContatto.PF.name())) {
                List<QualificaVO> qualifiche = null;
                if (dto.getQualifiche() != null && !dto.getQualifiche().isEmpty()) {
                    qualifiche = new ArrayList<>();
                    for (MudeRContattoQualifica qualifica : dto.getQualifiche()) {
                    	SelectVO denominazione = new SelectVO(qualifica.getMudeDQualifica().getIdQualifica(), qualifica.getMudeDQualifica().getDenominazione());
                    	ProvinciaVO provincia = null;
                    	SelectVO denominazioneOrdine  = null;
                    	
                    	if(qualifica.getProvinciaOrdine() != null) {
                            provincia = new ProvinciaVO();
                            provincia.setDescrizione(qualifica.getProvinciaOrdine().getDescrizione());
                            provincia.setId(qualifica.getProvinciaOrdine().getCodice());
                            denominazioneOrdine = new SelectVO(qualifica.getMudeDOrdine().getCodice(), qualifica.getMudeDOrdine().getDescrizione());
                    	}
                    	
                        QualificaVO qualificaVO = new QualificaVO(denominazione, qualifica.getNumeroIscrizioneOrdine(), provincia, denominazioneOrdine);
                        qualificaVO.setId(qualifica.getIdContattoQualifica());
                        qualificaVO.setSpecificaQualifica(qualifica.getSpecificaQualifica());

                        qualificaVO.setSenzaObbligoIscrizioneOrdine(qualifica.getSenzaObbligoIscrizioneOrdine());
        	            if (null != qualifica.getSenzaObbligoIscrizioneOrdine() && qualifica.getSenzaObbligoIscrizioneOrdine()) {
        	            	qualificaVO.setMotivazione(qualifica.getMotivazione());
        	            }
        	            

                        qualifiche.add(qualificaVO);
                    }
                }
                vo.setQualificheProfessionali(qualifiche);

	            vo.setIdUserAccreditato(mudeTUserRepository.findIdByCf(dto.getCf()));
            }

            Optional<MudeRPfPg> legaleRappresentanteOpt = mudeRPfPgRepository.retrieveLawPresenter(dto.getId());
            if((user != null || hasFilter(filters, "fill-in-rappresentante")) && legaleRappresentanteOpt.isPresent()) {
                MudeTContatto legaleRappresententeEntity = legaleRappresentanteOpt.get().getSoggettoPf();
                ContattoVO legaleRappresentente = contattoEntityMapper.mapEntityToVO(legaleRappresententeEntity, null);
                vo.setLegaleRappresentante(legaleRappresentente);
                vo.setIdTitoloRappresentante(legaleRappresentanteOpt.get().getIdTitolo());

                anagrafica.setNomeLegaleRappresentante(legaleRappresentente.getAnagrafica().getNome());
                anagrafica.setCognomeLegaleRappresentante(legaleRappresentente.getAnagrafica().getCognome());
                anagrafica.setCodiceFiscaleLegaleRappresentante(legaleRappresentente.getAnagrafica().getCodiceFiscale());
            }

	        Long countIstanzeAssociate = mudeRIstanzaSoggettoRepository.countBySoggetto(dto.getId());
	        if (countIstanzeAssociate > 0) {
	            vo.setCanChangeCF(Boolean.FALSE);
	        }
	        

        } catch (Throwable t) {
            return null;
        }

        return vo;
    }

    @Override
    public ContattoVO mapEntityToSlimVO(MudeTContatto dto, MudeTUser user) {
        ContattoVO vo = new ContattoVO();

        try {
	        vo.setGuid(dto.getGuid());
	        vo.setId(dto.getId());
	        vo.setTitolare(dto.getTitolare());
	        vo.setProfessionista(dto.getProfessionista());
	        vo.setImpresaLavori(dto.getImpresaLavori());
	
	        AnagraficaVO anagrafica = new AnagraficaVO();
	        anagrafica.setCodiceFiscale(dto.getCf());
	        anagrafica.setPartitaIva(dto.getPiva());
	        anagrafica.setPartitaIvaComunitaria(dto.getPivaComunitaria());
	        anagrafica.setTelefono(dto.getTelefono());
	        anagrafica.setCellulare(dto.getCellulare());
	        anagrafica.setMail(dto.getMail());
	        anagrafica.setPec(dto.getPec());
	        anagrafica.setStatoEstero(dto.getCittaEstera());
	        
	        
	        if (dto.getTipoContatto().name().equalsIgnoreCase(TipoContatto.PF.name())) {
	            vo.setTipoContatto(TipoContatto.PF);
	            NazioneVO statoNascita = null;
	            if (dto.getStatoNascita() != null) {
	                statoNascita = new NazioneVO();
	                statoNascita.setId(dto.getStatoNascita().getIdNazione());
	                statoNascita.setDescrizione(dto.getStatoNascita().getDenomNazione());
	            }
	
	            ProvinciaVO provinciaNascita = null;
	            ComuneVO comuneNascita = null;
	            if (dto.getComuneNascita() != null) {
	                comuneNascita = new ComuneVO();
	                comuneNascita.setDescrizione(dto.getComuneNascita().getDenomComune());
	                comuneNascita.setId(dto.getComuneNascita().getIdComune());
	                provinciaNascita = new ProvinciaVO();
	                provinciaNascita.setDescrizione(dto.getComuneNascita().getMudeDProvincia().getDenomProvincia());
	                provinciaNascita.setId(dto.getComuneNascita().getMudeDProvincia().getIdProvincia());
	            }
	            anagrafica.setNome(dto.getNome());
	            anagrafica.setCognome(dto.getCognome());
	            anagrafica.setSesso(dto.getSesso());
	            anagrafica.setDataNascita(utilsDate.asLocalDate(dto.getDataNascita()));
	            anagrafica.setStatoNascita(statoNascita);
	            anagrafica.setProvinciaNascita(provinciaNascita);
	            anagrafica.setComuneNascita(comuneNascita);
	
	        } else {
	            vo.setTipoContatto(TipoContatto.PG);
	            anagrafica.setRagioneSociale(dto.getRagioneSociale());
	
	            vo.setNome(dto.getRagioneSociale());
	        }
	        
	        SelectVO statoMembro = null;
	        if (dto.getStatoMembro() != null) {
	            statoMembro = new SelectVO(dto.getStatoMembro().getIdNazione(), dto.getStatoMembro().getDenomNazione());
	        }
	        anagrafica.setNazionalita(dto.getNazionalita());
	        anagrafica.setStatoMembro(statoMembro);
	        anagrafica.setTipoAttivita(dto.getTipoAttivita());
	
	        vo.setAnagrafica(anagrafica);
	    	vo.setIdUser(Long.valueOf(dto.getMudeTUser().getIdUser()));
	        if(user != null && dto.getMudeTUser().getIdUser() == user.getIdUser())
	            vo.setContattoInRubrica(true);
	        
	        vo.setProprietarioRubrica(dto.getMudeTUser().getCf() + "|" + dto.getMudeTUser().getNome() + "|" + dto.getMudeTUser().getCognome());
        } catch (Throwable t) {
            return null;
        }

        return vo;
    }

    @Override
    public MudeTContatto mapVOtoEntity(ContattoVO vo, MudeTUser user) {
        MudeTContatto entity = new MudeTContatto();
        if (null != vo.getId())
            entity = mudeTContattoRepository.findOne(vo.getId());

        entity.setTipoContatto(vo.getTipoContatto());
        entity.setGuid(vo.getGuid());

        entity.setCf(vo.getAnagrafica().getCodiceFiscale() == null? null : vo.getAnagrafica().getCodiceFiscale().toUpperCase());
        entity.setPiva(vo.getAnagrafica().getPartitaIva() == null? null : vo.getAnagrafica().getPartitaIva().toUpperCase());
        entity.setPivaComunitaria(vo.getAnagrafica().getPartitaIvaComunitaria() == null? null : vo.getAnagrafica().getPartitaIvaComunitaria().toUpperCase());

        entity.setMail(vo.getAnagrafica().getMail());
        entity.setPec(vo.getAnagrafica().getPec());
        entity.setTelefono(vo.getAnagrafica().getTelefono());
        entity.setCittaEstera(vo.getAnagrafica().getStatoEstero());
        entity.setCellulare(vo.getAnagrafica().getCellulare());

        if (vo.getTipoContatto().name().equalsIgnoreCase(TipoContatto.PF.name())) {

            entity.setNome(vo.getAnagrafica().getNome().toUpperCase());
            entity.setCognome(vo.getAnagrafica().getCognome().toUpperCase());
            entity.setSesso(vo.getAnagrafica().getSesso());

            if (null != vo.getAnagrafica().getDataNascita()) {
                entity.setDataNascita(Timestamp.valueOf(vo.getAnagrafica().getDataNascita().atTime(LocalTime.MIDNIGHT)));
            }
            if (null != vo.getAnagrafica().getStatoNascita()) {
                MudeDNazione statoNascita = mudeDNazioneRepository.findByIdNazione(vo.getAnagrafica().getStatoNascita().loadIdAsLong());
                entity.setStatoNascita(statoNascita);
                if("ITALIA".equalsIgnoreCase(statoNascita.getDenomNazione())
                		&& null != vo.getAnagrafica().getProvinciaNascita() 
                		&& StringUtils.isNotBlank(vo.getAnagrafica().getProvinciaNascita().getId())) {
                    MudeDProvincia provinciaNascita = mudeDProvinciaRepository.findOne(vo.getAnagrafica().getProvinciaNascita().loadIdAsLong());
                    entity.setProvinciaNascita(provinciaNascita);

    	            if (vo.getAnagrafica().getComuneNascita() != null) {
    	                if (vo.getAnagrafica().getComuneNascita() != null && StringUtils.isNotBlank(vo.getAnagrafica().getComuneNascita().getId())) {
    	                    MudeDComune comuneNascita = mudeDComuneRepository.findByIdComune(vo.getAnagrafica().getComuneNascita().loadIdAsLong());
    	                    entity.setComuneNascita(comuneNascita);
    	                }
    	            }
    	            
    	            entity.setCittaEstera(null);
                }
                else {
                    entity.setProvinciaNascita(null);
                    entity.setComuneNascita(null);
                    entity.setCittaEstera(vo.getAnagrafica().getStatoEstero());
                }
            }

            if (vo.getQualificheProfessionali() != null && vo.getQualificheProfessionali().size() > 0) {
                List<MudeRContattoQualifica> mudeRContattoQualificas = new ArrayList<MudeRContattoQualifica>();
                for (QualificaVO qualificaVo : vo.getQualificheProfessionali()) {
                    MudeRContattoQualifica mudeRContattoQualifica = new MudeRContattoQualifica();

                    mudeRContattoQualifica.setMudeTContatto(entity);

                    MudeDQualifica mudeDQualifica = mudeDQualificaRepository.findOne(qualificaVo.getTipologiaQualificaProfessionale().loadIdAsLong());
                    mudeRContattoQualifica.setMudeDQualifica(mudeDQualifica);

                    mudeRContattoQualifica.setNumeroIscrizioneOrdine(qualificaVo.getNumeroIscrizioneOrdine());

                    if(mudeDQualifica.getIdQualifica() == 1 /* ALTRO */)
	                    mudeRContattoQualifica.setSpecificaQualifica(qualificaVo.getSpecificaQualifica());
                    if(qualificaVo.getProvincia() != null)
	                    mudeRContattoQualifica.setProvinciaOrdine(mudeDQualificaCollegioRepository.findOne(qualificaVo.getProvincia().getId()));
                    if(qualificaVo.getOrdineProfessionale() != null)
	                    mudeRContattoQualifica.setMudeDOrdine(mudeDOrdineRepository.findOne((qualificaVo.getOrdineProfessionale().getId())));

                    mudeRContattoQualifica.setSenzaObbligoIscrizioneOrdine(qualificaVo.getSenzaObbligoIscrizioneOrdine() == null ? false : qualificaVo.getSenzaObbligoIscrizioneOrdine());
                    if(qualificaVo.getSenzaObbligoIscrizioneOrdine() != null && qualificaVo.getSenzaObbligoIscrizioneOrdine() == true)
                    	mudeRContattoQualifica.setMotivazione(qualificaVo.getMotivazione());

                    mudeRContattoQualificas.add(mudeRContattoQualifica);
                }
                entity.setQualifiche(mudeRContattoQualificas);
            }
        }
        else {
            String ragioneSociale = vo.getAnagrafica().getRagioneSociale().toUpperCase();
            entity.setRagioneSociale(ragioneSociale);
        }

        if (null != vo.getAnagrafica().getNazionalita()) {
            entity.setNazionalita(vo.getAnagrafica().getNazionalita().toUpperCase());
        }

        if (null != vo.getAnagrafica().getStatoMembro() && vo.getAnagrafica().getStatoMembro().getId() != null) {
            MudeDNazione statoMembro = mudeDNazioneRepository.findByIdNazione(vo.getAnagrafica().getStatoMembro().loadIdAsLong());
            entity.setStatoMembro(statoMembro);
        }
        if (null != vo.getAnagrafica().getTipoAttivita()) {
            entity.setTipoAttivita(vo.getAnagrafica().getTipoAttivita().toUpperCase());
        }

        List<MudeTIndirizzo> mudeTIndirizzos = new ArrayList<MudeTIndirizzo>();
        if (vo.getIndirizzi() != null) {
            for (IndirizzoVO indirizzo : vo.getIndirizzi()) {
                MudeTIndirizzo mudeTIndirizzo = new MudeTIndirizzo();

                mudeTIndirizzo.setTipoIndirizzo(MudeTIndirizzo.TipoIndirizzo.fromLabel(indirizzo.getTipologiaIndirizzo().loadIdAsLong(), vo.getTipoContatto()));
                mudeTIndirizzo.setCap(indirizzo.getCap());
                mudeTIndirizzo.setLocalita(indirizzo.getLocalita());
                mudeTIndirizzo.setIndirizzo(indirizzo.getDuf());
                mudeTIndirizzo.setMail(indirizzo.getMail());
                mudeTIndirizzo.setPec(indirizzo.getPec());
                mudeTIndirizzo.setCellulare(indirizzo.getCellulare());
                mudeTIndirizzo.setTelefono(indirizzo.getTelefono());

                if (indirizzo.getDug() != null) {
                    mudeTIndirizzo.setIdDug(indirizzo.getDug().loadIdAsLong());
                }

                mudeTIndirizzo.setNumeroCivico(indirizzo.getNumero());
                mudeTIndirizzo.setMudeTContatto(entity);

                MudeDNazione mudeDNazione = mudeDNazioneRepository.findByIdNazione(indirizzo.getStato().loadIdAsLong());
                mudeTIndirizzo.setMudeDNazione(mudeDNazione);

                if (indirizzo.getStato().loadIdAsLong() == 1) {
                    //Italia
                    MudeDComune mudeDComune = mudeDComuneRepository.findByIdComune(indirizzo.getComune().loadIdAsLong());
                    mudeTIndirizzo.setMudeDComune(mudeDComune);
                    mudeTIndirizzo.setIndirizzoEstero(false);
                } else {
                    mudeTIndirizzo.setIndirizzoEstero(true);
                    mudeTIndirizzo.setDenomComuneEstero(indirizzo.getComuneIndirizzoEstero());
                }
                mudeTIndirizzos.add(mudeTIndirizzo);

            }//fine ciclo for indirizzi
        }
        entity.setIndirizzi(mudeTIndirizzos);
        entity.setTitolare(vo.getTitolare() == null ? false : vo.getTitolare());
        entity.setProfessionista(vo.getProfessionista() == null ? false : vo.getProfessionista());
        entity.setImpresaLavori(vo.getImpresaLavori() == null ? false : vo.getImpresaLavori());

        return entity;
    }

}