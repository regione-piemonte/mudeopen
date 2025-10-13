/*
 *  SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 *  SPDX-License-Identifier: EUPL-1.2
 */
package it.csi.mudeopen.mudeopensrvapi.vo.mapper.impl;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import it.csi.mudeopen.mudeopensrvapi.business.be.entity.MudeTIstanzaSLIM;
import it.csi.mudeopen.mudeopensrvapi.business.be.repository.MudeopenTIstanzaRepository;
import it.csi.mudeopen.mudeopensrvapi.vo.DatiSintesiIstanzaVO;
import it.csi.mudeopen.mudeopensrvapi.vo.IstanzaEstesaVO;
import it.csi.mudeopen.mudeopensrvapi.vo.TipoTracciatoXML;
import it.csi.mudeopen.mudeopensrvapi.vo.geo.GeoCatasto;
import it.csi.mudeopen.mudeopensrvapi.vo.geo.GeoCellula;
import it.csi.mudeopen.mudeopensrvapi.vo.geo.GeoDatocarota;
import it.csi.mudeopen.mudeopensrvapi.vo.geo.GeoFabbricato;
import it.csi.mudeopen.mudeopensrvapi.vo.geo.GeoRiferimento;
import it.csi.mudeopen.mudeopensrvapi.vo.geo.GeoUbicazione;
import it.csi.mudeopen.mudeopensrvapi.vo.localizzazione.DataGrid;
import it.csi.mudeopen.mudeopensrvapi.vo.mapper.IstanzaMapper;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDStatoIstanza;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeDTipoTracciato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeTContatto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocCatasto;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocCellula;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocDatocarota;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocFabbricato;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocGeoriferim;
import it.csi.mudeopen.mudeopensrvcommon.business.be.entity.MudeopenRLocUbicazione;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDComuneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDSpeciePraticaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDStatoIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoIstanzaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeDTipoTracciatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeRIstanzaStatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTContattoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTFascicoloRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTIndirizzoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeTPraticaRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeopenRLocCatastoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeopenRLocFabbricatoRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.repository.MudeopenRLocUbicazioneRepository;
import it.csi.mudeopen.mudeopensrvcommon.business.be.service.util.UtilsDate;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.AnagraficaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.ContattoVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.anagrafica.TipoContatto;
import it.csi.mudeopen.mudeopensrvcommon.vo.common.SelectVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.dizionario.DizionarioVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.IstanzaVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.istanza.StatoIstanzaSlimVO;
import it.csi.mudeopen.mudeopensrvcommon.vo.tracciato.TipoTracciatoVO;
@Component
public class IstanzaMapperImpl implements IstanzaMapper{
	private static final String GIURIDICA = "G";

	private static final String FISICA = "F";
	@Autowired
	private MudeDTipoIstanzaRepository mudeDTipoIstanzaRepository;

	@Autowired
	private MudeDSpeciePraticaRepository mudeDSpeciePraticaRepository;

	@Autowired
	private MudeTPraticaRepository mudeTPraticaRepository;

	@Autowired
	private MudeRIstanzaStatoRepository mudeRIstanzaStatoRepository;
	
	@Autowired
	private MudeTContattoRepository mudeTContattoRepository;
	
	@Autowired
	private MudeTFascicoloRepository mudeTFascicoloRepository;
	
	@Autowired
	private MudeDTipoTracciatoRepository mudeDTipoTracciatoRepository;
	
    @Autowired
    private MudeopenRLocFabbricatoRepository mudeopenRLocFabbricatoRepository;

    @Autowired
    private MudeopenRLocCatastoRepository mudeopenRLocCatastoRepository;

    @Autowired
    private MudeopenRLocUbicazioneRepository mudeopenRLocUbicazioneRepository;

    @Autowired
	private MudeDComuneRepository mudeDComuneRepository;

	@Autowired
	private MudeTIndirizzoRepository mudeTIndirizzoRepository;

	@Autowired
	private MudeopenTIstanzaRepository mudeopenTIstanzaRepository;

	@Autowired
	private MudeDStatoIstanzaRepository mudeDStatoIstanzaRepository;

    @Autowired
    private UtilsDate utilsDate;

	private static Logger logger = Logger.getLogger(IstanzaMapperImpl.class.getCanonicalName());
	
	@Override
	public List<IstanzaEstesaVO> mapVOToIstanzaEstesa(List<MudeTIstanzaSLIM> istanzeDTO) {
		List<IstanzaEstesaVO> istanzeEstese = new ArrayList<>();
		
		for (MudeTIstanzaSLIM istanzaVO : istanzeDTO) {
			IstanzaEstesaVO istanzaEstesa = new IstanzaEstesaVO();
			istanzeEstese.add(istanzaEstesa);
			
			istanzaEstesa.setCodiceTipoIstanza(istanzaVO.getIdTipoIstanza());
			
			
			if(istanzaVO.getIdTipoIstanza() != null)
				istanzaEstesa.setTipoIstanza(mudeDTipoIstanzaRepository.getDescrizioneByCodice(istanzaVO.getIdTipoIstanza()));
			istanzaEstesa.setIdentificativoPratica(mudeTPraticaRepository.getNumeroPraticaByIdIstanza(istanzaVO.getId()));
			istanzaEstesa.setNumeroMude(istanzaVO.getCodiceIstanza().replaceAll("-", ""));
			MudeTContatto mudeTContatto = mudeTContattoRepository.getIntestatarioNameByIdIstanza(istanzaVO.getId());
			if(mudeTContatto != null) {
				istanzaEstesa.setIntestatario(TipoContatto.PG.equals(mudeTContatto.getTipoContatto()) ? mudeTContatto.getRagioneSociale() : mudeTContatto.getCognome());
				istanzaEstesa.setNome(mudeTContatto.getNome());
				istanzaEstesa.setTipoIntestatario(TipoContatto.PG.equals(mudeTContatto.getTipoContatto()) ? GIURIDICA : FISICA);
			}

			DataGrid dataGrid = getGeoloc(istanzaVO.getId());
			if(dataGrid != null) {
				istanzaEstesa.setSedime(dataGrid.getSedime());
				istanzaEstesa.setVia(dataGrid.getDenominazione());
				istanzaEstesa.setBis(dataGrid.getBis());
				istanzaEstesa.setNumeroCivico(dataGrid.getCivico());
				istanzaEstesa.setInterno(dataGrid.getInterno());
			}

			istanzaEstesa.setCodiceSpeciePratica(istanzaVO.getIdSpeciePratica());
			if(istanzaVO.getIdSpeciePratica() != null)
				istanzaEstesa.setSpeciePratica(mudeDSpeciePraticaRepository.getDescrizioneByCodice(istanzaVO.getIdSpeciePratica()));
			
			istanzaEstesa.setDataRicezione(istanzaVO.getDataDps());
			
			StatoIstanzaSlimVO mudeRIstanzaStato = mudeRIstanzaStatoRepository.findStatoByIdIstanza(istanzaVO.getId());
			istanzaEstesa.setDataUltimaVariazioneStato(mudeRIstanzaStato.getDataInizio());
			istanzaEstesa.setCodiceStatoIstanza(mudeRIstanzaStato.getCodice());
			istanzaEstesa.setStatoIstanza(mudeRIstanzaStato.getDescrizione());
			istanzaEstesa.setOccupazioneSuoloPubblico(istanzaVO.isOpereInPrecario() ? "S" : "N");
			
			istanzaEstesa.setNumeroIntervento(mudeTFascicoloRepository.getCodiceFascicolo(istanzaVO.getIdFascicolo()).replaceAll("-", ""));
	        for (MudeDTipoTracciato mudeDTipoTracciato : mudeDTipoTracciatoRepository.getPossibleByIdIstanza(istanzaVO.getId())) {
	        	TipoTracciatoXML tipiTracciatiXML = new TipoTracciatoXML();
	        	tipiTracciatiXML.setCodTipoTracciato(mudeDTipoTracciato.getCodice());
	        	tipiTracciatiXML.setDescrizione(mudeDTipoTracciato.getDescrizione());
	        	tipiTracciatiXML.setXmlPresente("true".equals(mudeDTipoTracciato.getXsdValidazione()));
				istanzaEstesa.addTipiTracciatiXML(tipiTracciatiXML);
	        }
		}
		
		return istanzeEstese;
	}

	private TipoTracciatoXML ottieniTracciatiXml(IstanzaVO istanzaVO, TipoTracciatoVO tipoTracciatoVO) {

		TipoTracciatoXML tipoTracciatoXML = new TipoTracciatoXML();
		tipoTracciatoXML.setCodTipoTracciato(tipoTracciatoVO.getCodice());
		tipoTracciatoXML.setDescrizione(tipoTracciatoVO.getDescrizione());
		tipoTracciatoXML.setXmlPresente(istanzaVO.getTracciatiIstanza().stream().anyMatch(t-> 
												t.getTipoTracciato().getId().equals(tipoTracciatoVO.getId())
												&& StringUtils.isNotBlank(t.getXmlTracciato())));
		return tipoTracciatoXML;
	}

	private DataGrid getGeoloc(Long idIstanza) {

        MudeopenRLocUbicazione mudeRLocUbicazione = mudeopenRLocUbicazioneRepository.retrieveMainByIdIstanza(idIstanza);
        if(mudeRLocUbicazione == null)
        	return null;
        	
        DataGrid dataGrid = new DataGrid();
        dataGrid.setSedime(mudeRLocUbicazione.getSedime());
        dataGrid.setDenominazione(mudeRLocUbicazione.getDescVia());
        dataGrid.setCivico(mudeRLocUbicazione.getNumCivico());
        dataGrid.setBis(mudeRLocUbicazione.getBis());
        dataGrid.setScala(mudeRLocUbicazione.getScala());
        dataGrid.setPiano(mudeRLocUbicazione.getPiano());
        dataGrid.setBisInterno(mudeRLocUbicazione.getBisinterno());
        dataGrid.setInterno2(mudeRLocUbicazione.getInterno2());
        dataGrid.setSecondario(mudeRLocUbicazione.getSecondario());
        dataGrid.setCap(mudeRLocUbicazione.getCap());
		return dataGrid;
	}

	@Override
	public List<DatiSintesiIstanzaVO> mapVOtoSintesiIstanza(List<IstanzaVO> istanzeVo) {
		List<DatiSintesiIstanzaVO> datiSintesiIstanzaList = new ArrayList<>();
		
		for (IstanzaVO istanzaVO : istanzeVo) {
			DatiSintesiIstanzaVO datiSintesiIstanza = new DatiSintesiIstanzaVO();
			datiSintesiIstanzaList.add(datiSintesiIstanza);
			
			datiSintesiIstanza.setNumeroIstanza(istanzaVO.getCodiceIstanza().replaceAll("-", ""));
			
			SelectVO tipologiaIstanza = istanzaVO.getTipologiaIstanza();
			if(tipologiaIstanza != null) {
				datiSintesiIstanza.setTipoIstanza(tipologiaIstanza.getDescrizione());
			}

			if(istanzaVO.getFascicoloVO() != null && istanzaVO.getFascicoloVO().getIntestatario() != null) {
				ContattoVO intestatario = istanzaVO.getFascicoloVO().getIntestatario();
				AnagraficaVO anagrafica = intestatario.getAnagrafica();
				if(intestatario.getTipoContatto() != null && anagrafica != null ) {
					String tipoIntestatario = intestatario.getTipoContatto().equals(TipoContatto.PF) ? FISICA : GIURIDICA; 
					datiSintesiIstanza.setTipoIntestatario(tipoIntestatario);
					if(FISICA.equals(tipoIntestatario)) {
						datiSintesiIstanza.setCognome(anagrafica.getCognome());
						datiSintesiIstanza.setNome(anagrafica.getNome());
					} else {
						datiSintesiIstanza.setRagioneSociale(anagrafica.getRagioneSociale());
					}
				}
			}

			if(istanzaVO.getComune() != null) {
				datiSintesiIstanza.setComune(istanzaVO.getComune().getDescrizione());
			}
			
			DataGrid dataGrid = getGeoloc(istanzaVO.getIdIstanza());
			if(dataGrid != null) {
				datiSintesiIstanza.setSedime(dataGrid.getSedime());
				datiSintesiIstanza.setBis(dataGrid.getBis());
				datiSintesiIstanza.setNumeroCivico(dataGrid.getCivico());
				datiSintesiIstanza.setInterno(dataGrid.getInterno());
			}
			
			if(istanzaVO.getIndirizzo() != null && istanzaVO.getIndirizzo().getDuf() != null)
				datiSintesiIstanza.setDescrizione(istanzaVO.getIndirizzo().getDuf());
			
			DizionarioVO statoIstanza = istanzaVO.getStatoIstanza();
			if(statoIstanza != null) {
				datiSintesiIstanza.setStatoIstanza(statoIstanza.getDescrizione());
			}
			
			DizionarioVO speciePratica = istanzaVO.getSpeciePratica();
			if(speciePratica != null) {
				datiSintesiIstanza.setSpeciePratica(speciePratica.getDescrizione());
			}
			
			if(istanzaVO.getFascicoloVO() != null) {
				datiSintesiIstanza.setNumeroIntervento(istanzaVO.getFascicoloVO().getCodiceFascicolo().replaceAll("-", ""));
			}
			
			if(istanzaVO.getIstanzaRiferimento() != null) {
				datiSintesiIstanza.setIstanzaDiRiferimento(istanzaVO.getIstanzaRiferimento().getCodiceIstanza().replaceAll("-", ""));
			}

			datiSintesiIstanza.setProfessionista(istanzaVO.getPm());
			/*
			for (TipoTracciatoVO tipoTracciatoVO : istanzaVO.getTipiTracciato()) {
				datiSintesiIstanza.getTipiTracciatiXml().add(ottieniTracciatiXml(istanzaVO, tipoTracciatoVO));
			}
			*/
			
		}
		
		return datiSintesiIstanzaList;
	}

	/**
	 * assume che siano state fatte tutte le validazioni del caso
	@Override
	public SalvaIstanzaRequest maptoCommonIstanzaRequest(
			IstanzaExtVO istanzaRequest, FascicoloVO fascicoloVO) {
		
		SalvaIstanzaRequest request = new SalvaIstanzaRequest();
		
		MudeTIstanzaSLIM istRiferimento = istanzaService.findByNumeroIstanza(istanzaRequest.getNumeroMudeIstanzaPrincipale());
		if(istRiferimento != null)
			request.setIdIstanzaRiferimento(istRiferimento.getId());
		request.setIdFascicolo(fascicoloVO.getIdFascicolo());
		
		request.setTipologiaPresentatore(new SelectVO(1L,null));
		
		request.setComune(fascicoloVO.getComune());
		
		request.setTipologiaIstanza(fascicoloVO.getTipologiaIstanza());
		
		return request;
	}
	 */
	public GeoRiferimento mapEntityGeoRiferimentoToVO(MudeopenRLocGeoriferim mudeopenRLocGeoriferim) {
		GeoRiferimento geoRiferimento = new GeoRiferimento();
		
		if(mudeopenRLocGeoriferim.getIdLivelloPoligono() != null)
			geoRiferimento.setIdLivelloPoligono(mudeopenRLocGeoriferim.getIdLivelloPoligono().intValue());
		geoRiferimento.setDescLivelloPoligono(mudeopenRLocGeoriferim.getDescLivelloPoligono());
		
		if(mudeopenRLocGeoriferim.getServizioFonte() != null)
			geoRiferimento.setServizioFonte(Integer.parseInt(mudeopenRLocGeoriferim.getServizioFonte()));
		
		if(mudeopenRLocGeoriferim.getServizioLivello() != null)
			geoRiferimento.setServizioLivello(Integer.parseInt(mudeopenRLocGeoriferim.getServizioLivello()));
		
		geoRiferimento.setDataGeoriferimento(utilsDate.asLocalDateTime(mudeopenRLocGeoriferim.getDataGeoriferimento()));
		geoRiferimento.setCodIstatComune(mudeopenRLocGeoriferim.getIstatComune());		
		return geoRiferimento;
	}
	
	public GeoCellula mapEntityGeoCellulaToVO(MudeopenRLocCellula mudeopenRLocCellula) {
		GeoCellula geoCellula = new GeoCellula();
		
		geoCellula.setChiaveCarotaggio(mudeopenRLocCellula.getChiaveCarotaggio());
		if(mudeopenRLocCellula.getCodCellula() != null)
			geoCellula.setCodCellula(Integer.parseInt(mudeopenRLocCellula.getCodCellula()));
		//geoCellula.setDataGeoriferimento(geoCellula.getDataGeoriferimento());
		
		List<GeoFabbricato> geoFabbricatoVOs = new ArrayList<GeoFabbricato>();
		
		List<MudeopenRLocFabbricato> geoFabbricatoGeoCells = mudeopenRLocFabbricatoRepository.findByIdCellula(mudeopenRLocCellula.getIdCellula());
		for(MudeopenRLocFabbricato mudeopenRLocFabbricato : geoFabbricatoGeoCells) {
			GeoFabbricato geoFabbricatoVO = new GeoFabbricato();
			geoFabbricatoVO.setChiaveCarotaggio(mudeopenRLocFabbricato.getChiaveCarotaggio());
			if(mudeopenRLocFabbricato.getCodFabbricato() != null)
				geoFabbricatoVO.setCodFabbricato(Integer.parseInt(mudeopenRLocFabbricato.getCodFabbricato()));
			
			 List<MudeopenRLocUbicazione> geoUbicaziones = mudeopenRLocUbicazioneRepository.findByIdFabbricato(mudeopenRLocFabbricato.getIdFabbricato());
	       	 List<GeoUbicazione> geoUbicazioniVOs = mapListEntityGeoUbicazioniToListVO(geoUbicaziones);
	       	 
	       	 List<MudeopenRLocCatasto> geoCatastos = mudeopenRLocCatastoRepository.findByIdFabbricato(mudeopenRLocFabbricato.getIdFabbricato());
	       	 List<GeoCatasto> geoCatastoVOs = mapListEntityGeoCatastoToListVO(geoCatastos);
			
			geoFabbricatoVO.setGeoUbicaziones(geoUbicazioniVOs);
			geoFabbricatoVO.setGeoCatastos(geoCatastoVOs);
			geoFabbricatoVOs.add(geoFabbricatoVO);
		}

		geoCellula.setGeoFabbricatos(geoFabbricatoVOs);
		
		return geoCellula;
	}
	
	public List<GeoCatasto> mapListEntityGeoCatastoToListVO(List<MudeopenRLocCatasto> geoCatastos){
		
		List<GeoCatasto> geoCatastoVOs = new ArrayList<GeoCatasto>();
		for(MudeopenRLocCatasto mudeopenRLocCatasto : geoCatastos) {
			GeoCatasto geoCatastoVO = new GeoCatasto();
			geoCatastoVO.setFoglio(mudeopenRLocCatasto.getFoglio());
			geoCatastoVO.setParticella(mudeopenRLocCatasto.getParticella());
			geoCatastoVO.setSubalterno(mudeopenRLocCatasto.getSubalterno());
			geoCatastoVO.setFlTipoCatasto(mudeopenRLocCatasto.getF1TipoCatasto());
			geoCatastoVO.setFlPersonalizzato(mudeopenRLocCatasto.getF1Personalizzato());
			geoCatastoVO.setChiaveCarotaggio(mudeopenRLocCatasto.getChiaveCarotaggio());
			geoCatastoVO.setDescFonteCatasto(mudeopenRLocCatasto.getDescFonteCatasto());			
			geoCatastoVOs.add(geoCatastoVO);
		}

		return geoCatastoVOs;
		
	}
	
	public List<GeoUbicazione> mapListEntityGeoUbicazioniToListVO(List<MudeopenRLocUbicazione> geoUbicaziones){
		List<GeoUbicazione> geoUbicazioneVOs = new ArrayList<GeoUbicazione>();
		for(MudeopenRLocUbicazione mudeopenRLocUbicazione : geoUbicaziones) {
			GeoUbicazione geoUbicazioneVO = new GeoUbicazione();
			geoUbicazioneVO.setBis(mudeopenRLocUbicazione.getBis());
			geoUbicazioneVO.setBisinterno(mudeopenRLocUbicazione.getBisinterno());
			geoUbicazioneVO.setCap(mudeopenRLocUbicazione.getCap());
			geoUbicazioneVO.setDescFonteUbicazione(mudeopenRLocUbicazione.getDescFonteUbicazione());
			geoUbicazioneVO.setDescVia(mudeopenRLocUbicazione.getDescVia());
			geoUbicazioneVO.setFlPersonalizzato(mudeopenRLocUbicazione.getF1Personalizzato());
			geoUbicazioneVO.setFlPrincipale(mudeopenRLocUbicazione.getF1PersonalizzatoDettaglio());
			if(mudeopenRLocUbicazione.getIdCivicoToponom() != null)
				geoUbicazioneVO.setIdCivicoTopon(Integer.parseInt(mudeopenRLocUbicazione.getIdCivicoToponom()));	
			if(mudeopenRLocUbicazione.getIdViaToponom() != null)
				geoUbicazioneVO.setIdViaTopon(Integer.parseInt(mudeopenRLocUbicazione.getIdViaToponom()));	
			geoUbicazioneVO.setInterno(mudeopenRLocUbicazione.getInterno());	
			geoUbicazioneVO.setInterno2(mudeopenRLocUbicazione.getInterno2());	
			geoUbicazioneVO.setNumCivico(mudeopenRLocUbicazione.getNumCivico());	
			geoUbicazioneVO.setPiano(mudeopenRLocUbicazione.getPiano());	
			geoUbicazioneVO.setScala(mudeopenRLocUbicazione.getScala());	
			geoUbicazioneVO.setSecondario(mudeopenRLocUbicazione.getSecondario());	
			geoUbicazioneVO.setSedime(mudeopenRLocUbicazione.getSedime());	
			geoUbicazioneVOs.add(geoUbicazioneVO);
		}

		return geoUbicazioneVOs;
	}

	public List<GeoDatocarota> mapListEntityGeoDatocarotaToListVO(List<MudeopenRLocDatocarota> geoDatoCarotas){
		
		List<GeoDatocarota> geoDatocarotaVOs = new ArrayList<GeoDatocarota>();
		for(MudeopenRLocDatocarota mudeopenRLocDatocarota : geoDatoCarotas) {
			GeoDatocarota geoDatocarotaVO = new GeoDatocarota();
			geoDatocarotaVO.setTipoCarotaggio(mudeopenRLocDatocarota.getTipoCarotaggio());
			if(mudeopenRLocDatocarota.getIdFonte() != null)
				geoDatocarotaVO.setIdFonte(Integer.parseInt(mudeopenRLocDatocarota.getIdFonte()));
			geoDatocarotaVO.setDescFonte(mudeopenRLocDatocarota.getDescFonte());
			if(mudeopenRLocDatocarota.getIdLivello() != null)
				geoDatocarotaVO.setIdLivello(Integer.parseInt(mudeopenRLocDatocarota.getIdLivello()));
			geoDatocarotaVO.setDescLivello(mudeopenRLocDatocarota.getDescLivello());
			geoDatocarotaVO.setValore(mudeopenRLocDatocarota.getValore());
			geoDatocarotaVOs.add(geoDatocarotaVO);
		}

		return geoDatocarotaVOs;
	}

	@Override
	public List<DatiSintesiIstanzaVO> mapIstanzaSlimtoSintesiIstanza(List<MudeTIstanzaSLIM> istanzeVo) {
		List<DatiSintesiIstanzaVO> datiSintesiIstanzaList = new ArrayList<>();
		for (MudeTIstanzaSLIM istanzaVO : istanzeVo) {
			DatiSintesiIstanzaVO datiSintesiIstanza = new DatiSintesiIstanzaVO();
			datiSintesiIstanzaList.add(datiSintesiIstanza);
			datiSintesiIstanza.setNumeroIstanza(istanzaVO.getCodiceIstanza().replaceAll("-", ""));
			if(istanzaVO.getIdTipoIstanza() != null)
				datiSintesiIstanza.setTipoIstanza(mudeDTipoIstanzaRepository.getDescrizioneByCodice(istanzaVO.getIdTipoIstanza()));
			String intestatarioString = mudeTContattoRepository.findIntestatarioAsStringByIdIstanza(istanzaVO.getId());
			if(intestatarioString!=null) {
				ContattoSlim contattoIntestatario = fromString(intestatarioString);
				if (contattoIntestatario != null) {
					String tipoIntestatario = contattoIntestatario.getTipo().equals(TipoContatto.PF) ? FISICA : GIURIDICA;
					datiSintesiIstanza.setTipoIntestatario(tipoIntestatario);
					if (FISICA.equals(tipoIntestatario)) {
						datiSintesiIstanza.setCognome(contattoIntestatario.getCognome());
						datiSintesiIstanza.setNome(contattoIntestatario.getNome());
					} else {
						datiSintesiIstanza.setRagioneSociale(contattoIntestatario.getRagioneSociale());
					}
				}
			}

			if(istanzaVO.getIdComune() != null) {
				String comuneDenom = mudeDComuneRepository.getComuneDenominazioneById(istanzaVO.getIdComune());
				datiSintesiIstanza.setComune(comuneDenom);
			}

			DataGrid dataGrid = getGeoloc(istanzaVO.getId());
			if(dataGrid != null) {
				
				datiSintesiIstanza.setSedime(dataGrid.getSedime());
				datiSintesiIstanza.setBis(dataGrid.getBis());
				datiSintesiIstanza.setNumeroCivico(dataGrid.getCivico());
				datiSintesiIstanza.setInterno(dataGrid.getInterno());
			}

			if(istanzaVO.getIdIndirizzo() != null) {
				String indirizzoString = mudeTIndirizzoRepository.findIndirizzoStringById(istanzaVO.getIdIndirizzo());
				datiSintesiIstanza.setDescrizione(indirizzoString);
			}

			String mudeRIstanzaStatoCodice = mudeRIstanzaStatoRepository.getStatoIstanza(istanzaVO.getId());
			if(mudeRIstanzaStatoCodice!=null) {
				MudeDStatoIstanza stato = mudeDStatoIstanzaRepository.findMudeDStatoIstanzaByCodice(mudeRIstanzaStatoCodice);
				if(stato!=null) {
					datiSintesiIstanza.setStatoIstanza(stato.getDescrizione());
				}
			}

			if(istanzaVO.getIdSpeciePratica() != null) {
				datiSintesiIstanza.setSpeciePratica(mudeDSpeciePraticaRepository.getDescrizioneBreveByCodice(istanzaVO.getIdSpeciePratica()));
			}

			if(istanzaVO.getIdFascicolo() != null) {
				String codiceFascicolo = mudeTFascicoloRepository.getCodiceFascicolo(istanzaVO.getIdFascicolo());
				if(codiceFascicolo!=null){
					datiSintesiIstanza.setNumeroIntervento(codiceFascicolo.replaceAll("-", ""));
				}
			}

			if(istanzaVO.getIdIstanzaRiferimento() != null) {
				String codiceIstanzaRiferimento = mudeopenTIstanzaRepository.findCodiceIstanzaByIdIstanza(istanzaVO.getIdIstanzaRiferimento());
				datiSintesiIstanza.setIstanzaDiRiferimento(codiceIstanzaRiferimento.replaceAll("-", ""));
			}

			MudeTContatto prof = mudeTContattoRepository.findProfessionistaByIdIstanza(istanzaVO.getId());
			String professionistaContattoString = mudeTContattoRepository.findProfessionistaAsStringByIdIstanza(istanzaVO.getId());
			if(professionistaContattoString!=null) {
				ContattoSlim professionista = fromString(professionistaContattoString);
				datiSintesiIstanza.setProfessionista(professionista.getDisplayName());
			}

			/*
			for (TipoTracciatoVO tipoTracciatoVO : istanzaVO.getTipiTracciato()) {
				datiSintesiIstanza.getTipiTracciatiXml().add(ottieniTracciatiXml(istanzaVO, tipoTracciatoVO));
			}
			*/
		}

		return datiSintesiIstanzaList;
	}

	public ContattoSlim fromString(String contattoString){
		ContattoSlim c = new ContattoSlim();
		String[] tokens = contattoString.split("\\$\\$\\$");
		c.nome = "null_string".equalsIgnoreCase(tokens[0]) ? null : tokens[0];
		c.cognome = "null_string".equalsIgnoreCase(tokens[1]) ? null : tokens[1];
		c.ragioneSociale = "null_string".equalsIgnoreCase(tokens[2]) ? null : tokens[2];
		c.tipo = "null_string".equalsIgnoreCase(tokens[3]) ? null : TipoContatto.findByValue(tokens[3]);
		return c;
	}

	private class ContattoSlim {

		public String nome;
		public String cognome;
		public String ragioneSociale;
		public TipoContatto tipo;
		public String getDisplayName() {
			if(tipo==TipoContatto.PF) {
				return cognome + " " + nome;
			} else {
				return ragioneSociale;
			}
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getCognome() {
			return cognome;
		}

		public void setCognome(String cognome) {
			this.cognome = cognome;
		}

		public String getRagioneSociale() {
			return ragioneSociale;
		}

		public void setRagioneSociale(String ragioneSociale) {
			this.ragioneSociale = ragioneSociale;
		}

		public TipoContatto getTipo() {
			return tipo;
		}

		public void setTipo(TipoContatto tipo) {
			this.tipo = tipo;
		}
	}
}
