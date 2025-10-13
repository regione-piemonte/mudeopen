/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.mapper.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTAllegatoSlim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.mudeopen.mudeopensrvapi.vo.Allegato;
import it.csi.mudeopen.mudeopensrvapi.vo.AllegatoIstanzaVO;
import it.csi.mudeopen.mudeopensrvapi.vo.mapper.AllegatiMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoAllegato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoAllegatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.vo.allegato.AllegatoVO;

@Component
public class AllegatiMapperImpl implements AllegatiMapper {

	@Autowired
    private MudeDTipoAllegatoRepository mudeDTipoAllegatoRepository;
	
	@Override
	public List<AllegatoIstanzaVO> mapAllegatiVOToAllegati(List<AllegatoVO> allegatiVO) {
		List<AllegatoIstanzaVO> allegati = new ArrayList<>();
		
		for (AllegatoVO allegatoVO : allegatiVO) {
			AllegatoIstanzaVO allegato = new AllegatoIstanzaVO();
			allegati.add(allegato);
			
			if(allegatoVO.getTipoAllegato() != null) {
				allegato.setTipoAllegato(allegatoVO.getTipoAllegato().getCodice());
			}
			
			if(allegatoVO.getIstanza() != null)
				allegato.setNumeroIstanza(allegatoVO.getIstanza().getCodiceIstanza().replaceAll("-", ""));
			
			allegato.setNomeFile(allegatoVO.getNomeFileAllegato());
			
		}
		return allegati;
	}

	@Override
	public List<AllegatoIstanzaVO> mapAllegatiSlimToAllegati(List<MudeTAllegatoSlim> allegatiVO, String numeroIstanza) {
		List<AllegatoIstanzaVO> allegati = new ArrayList<>();

		for (MudeTAllegatoSlim allegatoVO : allegatiVO) {
			AllegatoIstanzaVO allegato = new AllegatoIstanzaVO();
			allegati.add(allegato);

			if(allegatoVO.getIdTipoAllegato() != null) {
				allegato.setTipoAllegato(allegatoVO.getIdTipoAllegato());
			}

			allegato.setNumeroIstanza(numeroIstanza);
			allegato.setNomeFile(allegatoVO.getNomeFileAllegato());

		}
		return allegati;
	}

	@Override
	public List<Allegato> mapAllegatiVOToAllegato(List<AllegatoVO> allegatiVO) {
		List<Allegato> allegati = new ArrayList<>();
		
		for (AllegatoVO allegatoVO : allegatiVO) {
			Allegato allegato = new Allegato();
			allegati.add(allegato);
			
			// CR j697
			Optional<MudeDTipoAllegato> mudeDTipoAllegato = mudeDTipoAllegatoRepository.findByCodice(allegatoVO.getTipoAllegato().getCodice());
			if(mudeDTipoAllegato.isPresent() && mudeDTipoAllegato.get().getId() != null)
				allegato.setIdTipoallegato(mudeDTipoAllegato.get().getId().intValue());
			
			if(allegatoVO.getId() != null) {
				allegato.setIdAllegatoFile(allegatoVO.getId().intValue());
			}
			
			allegato.setNomeAllegato(allegatoVO.getNomeFileAllegato());
			
		}
		return allegati;
	}

	@Override
	public List<Allegato> mapAllegatiSlimToAllegato(List<MudeTAllegatoSlim> allegatiVO) {
		List<Allegato> allegati = new ArrayList<>();

		for (MudeTAllegatoSlim allegatoVO : allegatiVO) {
			Allegato allegato = new Allegato();
			allegati.add(allegato);

			// CR j697
			Optional<MudeDTipoAllegato> mudeDTipoAllegato = mudeDTipoAllegatoRepository.findByCodice(allegatoVO.getIdTipoAllegato());
			if(mudeDTipoAllegato.isPresent() && mudeDTipoAllegato.get().getId() != null)
				allegato.setIdTipoallegato(mudeDTipoAllegato.get().getId().intValue());

			if(allegatoVO.getId() != null) {
				allegato.setIdAllegatoFile(allegatoVO.getId().intValue());
			}

			allegato.setNomeAllegato(allegatoVO.getNomeFileAllegato());

		}
		return allegati;
	}

}
