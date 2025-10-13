import { id } from '@swimlane/ngx-datatable';
/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;

import { Component, OnInit, ViewChild } from '@angular/core';
import { Location } from '@angular/common';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { TableColumn } from '@swimlane/ngx-datatable';
import { NavigationEnd, Router } from '@angular/router';
import { takeUntil } from 'rxjs/operators';
import { RicercaService } from 'src/app/shared/services/ricerca.service';
import { Permissions } from '../../../../core/enum/permissions.enum';
import { PraticheService } from '../../services/pratiche.service';

import { FormUtils, MessageService, MudeopenFoBeService } from 'mudeopen-common';
import * as model from 'mudeopen-common';
import { forkJoin } from 'rxjs';
import { ScrivaniaService } from 'src/app/features/scrivania-ds/service/scrivania.service';
import { UtenteBO } from 'src/app/core/models';

@Component({
  selector: 'app-lista-pratiche',
  templateUrl: './lista-pratiche.component.html',
  styleUrls: ['./lista-pratiche.component.scss'],
})
export class ListaPraticheComponent
                    extends FormUtils
                    implements OnInit
{
  permissions = Permissions;

  @ViewChild('istanzeDSTable') istanzeDSTable: any;
  @ViewChild('praticheTable') praticheTable: any;
  
  tipiSoggetto: any = [];
  tipiPratica: any = [];
  tipiRicercaData: any = [
    { id: 1, value: 'Data protocollazione' },
    { id: 2, value: 'Data di accettazione' },
  ];

  tipiCatasto: any = [
    { id: 'T', value: 'Catasto terreni'},
    { id: 'F', value: 'Catasto fabbricati'}
  ];

  public datatableIstanzeDS: any = {
    offset: 0,
    count: 0,
    limit: 10,
    selected: [],
    pages: 0,
    messages: this.datatable.messages
  }
  filteredRowsIstanzeDS: string[];
  stateButtonIstanzeDS: string = '';
  displayedRowsIstanzeDS: model.IstanzaVO[] = null;
  columnsIstanzeDS: any = [
    {name: 'Numero istanza', prop: 'codice_istanza', width: '108'},
    {name: 'Numero pratica', prop: '_numero_pratica', width: '108'},
    {name: 'Tipo Pratica', prop: '_tipo_pratica', width: '108'},
    {name: 'Comune', prop: 'comune.value', width: '108'},
    {name: 'Numero Protocollo', prop: '_numero_protocollo', width: '108'},
    {name: 'Data Protocollo', prop: 'data_protocollo', width: '108'},
    {name: 'Responsabile Procedimento', prop: 'responsabile_procedimento', width: '108'},
    // "Oggetto intervento"?
    {name: 'Richiedente', prop: '_intestatario', width: '108'},
    {name: 'Ubicazione', prop: '_indirizzo', width: '108'},
    {name: 'Progettista', prop: 'pm', width: '108'}
  ];
  ricercaIstanzeDSForm: FormGroup;

  provincePiemonte: model.ProvinciaVO[];
  searchSelected: number = 0;

  lastActivatedUrl: string;
  constructor(
    private router: Router,
    public messageService: MessageService,
    private ricercaService: RicercaService,
    private location: Location,
    private scrivaniaService: ScrivaniaService,
    private authStoreService: model.AuthStoreService,
    public mudeopenFoBeService: MudeopenFoBeService
  ) {
    super(mudeopenFoBeService, messageService);
    this.disableCountryLoading = false;

    this.ricercaIstanzeDSForm = this.initRicercaIstanzeDSForm();

    router.events.subscribe(params => {
      if(params instanceof NavigationEnd) {
        if(params.url == "/fascicolo/ricerca") {
          if(this.lastActivatedUrl?.startsWith("/fascicolo/gestione/")) {
            this.onSearch(); }
          else {
            this.searchSelected = 0;
  
            this.ricercaIstanzeDSForm.reset();
            this.displayedRowsIstanzeDS = null;
          }
        }

        this.lastActivatedUrl = params.url;
      }
    });

  }

  ngOnInit(): void {
    forkJoin([
      this.mudeopenFoBeService.getProvince('{"idRegione":{"eq":"1"}}'),
      this.mudeopenFoBeService.filtraListaDizionario('specie_pratica', '{"visibilita":{"eq":"ds-regionale"}}')
    ]).subscribe((results) => {
      this.provincePiemonte = (results[0] as model.ProvinciaVO[]); 
      this.tipiPratica = results[1]; 

      this.tipiSoggetto = [ 
        { id: '~ATD', value: 'Richiedente/Intestatario' },
        { id: '~TEC', value: 'Professionista' },
        { id: '~IMPR', value: 'Esecutore lavori' },
        { id: '-1', value: '-!___________________________'},
        { id: 'IN', value: 'Intestatario' },
        { id: 'CO', value: 'Cointestatario' },
        { id: 'PRC', value: 'Procuratore' },
        { id: 'PR', value: 'Progettista architettonico' },
        { id: 'PS', value: 'Progettista strutture' },
        { id: 'DR', value: 'Direttore lavori architettonico' },
        { id: 'DL', value: 'Direttore lavori strutture' },
        { id: 'CC', value: 'Costruttore strutture / Impresario' },
        { id: 'CL', value: 'Collaudatore' },
        { id: 'IM', value: 'Impresa Lavori' },
      ]
    });
  }

	/* 
	* nome metodo "initRicercaIstanzeDSForm"; method description: 
	* @param ()
	* @returns FormGroup
	*/ 

  initRicercaIstanzeDSForm(): FormGroup {
    return new FormGroup({
      id_intestatario_pf: new FormControl(),
      id_intestatario_pg: new FormControl(),
      tipo_soggetto: new FormGroup({
        id: new FormControl(),
        value: new FormControl()
      }),
      // FIRST COL
      tipi_pratica: new FormGroup({
        id: new FormControl(),
        value: new FormControl()
      }),
      tipo_data: new FormGroup({
        id: new FormControl(),
        value: new FormControl()
      }),
      data_from: new FormControl(),
      data_to: new FormControl(),
      numero_protocollo: new FormControl(),

      // INDIRIZZO
      id_provincia: new FormGroup({
        id: new FormControl(),
        value: new FormControl()
      }),
      id_comune: new FormGroup({
        codBelfiore: new FormControl(),
        id: new FormControl(),
        value: new FormControl()
      }),
      id_dug: new FormGroup({
        id: new FormControl(),
        value: new FormControl()
      }),
      duf: new FormControl(),
      numero_civico: new FormControl(),
      interno: new FormControl(),
      scala: new FormControl(),
      piano: new FormControl(),

      // CATASTO
      tipo_catasto: new FormGroup({
        id: new FormControl(),
        value: new FormControl()
      }),
      foglio: new FormControl(),
      numero: new FormControl(),
      subalterno: new FormControl(),

      codice_istanza: new FormControl(),
      num_pratica: new FormControl(),
    });
  }  

	/* 
	* nome metodo "onSearch"; method description: 
	* @param (page: any = 0)
	* @returns 
	*/ 

  rowExpandedIstanzeFascicolo: any;
  wrongDateInterval: string;
  selectProvince: boolean;
  wrongDateSelection: boolean;
  wrongSubjectSelection: boolean;
  onSearch(page: any = 0, statusButton: string = '') {
    if(page && page.pageSize) { page = page.offset; }

    this.stateButtonIstanzeDS = statusButton;
    const fmr2fix = this.ricercaIstanzeDSForm;

    let frmValues = fmr2fix.value;
    if(this.selectProvince = (frmValues.numero || frmValues.subalterno || frmValues.tipo_catasto?.id 
          //|| frmValues.numero_protocollo
          || frmValues.interno || frmValues.numero || frmValues.numero_civico || frmValues.piano || frmValues.scala || frmValues.duf || frmValues.id_dug?.id)
        && !frmValues.id_comune?.id) {
      return;
    }

    if(frmValues.stato?.id === '') {
        fmr2fix.get('stato').setValue({id: null, value: null}); }

    if(this.wrongDateInterval = ['data_'].find(name => {
          return fmr2fix.value[name+'from'] && fmr2fix.value[name+'to'] && 
                    fmr2fix.value[name+'from'].split('/').reverse().join() > fmr2fix.value[name+'to'].split('/').reverse().join(); })) {
      return; }

    if(this.wrongDateSelection = !(!fmr2fix.value['data_from'] && !fmr2fix.value['data_to'] && !fmr2fix.value['tipo_data']?.id)
        && !((fmr2fix.value['data_from'] || fmr2fix.value['data_to']) && fmr2fix.value['tipo_data']?.id)) {
      return; }
  
    if(this.wrongSubjectSelection = 
      !(!fmr2fix.value['id_intestatario_pf'] && !fmr2fix.value['id_intestatario_pg'] && !fmr2fix.value['tipo_soggetto']?.id)
        && !((fmr2fix.value['id_intestatario_pf'] || fmr2fix.value['id_intestatario_pg']) && fmr2fix.value['tipo_soggetto']?.id)) {
      return; }

    this.callRightSearch(page);
  }

  get filters(): any {
    return JSON.stringify(Object.assign({}, 
      this.mudeopenFoBeService.generateRicercaIstanzeFilter(this.ricercaIstanzeDSForm.value), 
      {
        id_tipo_istanza: { eq: 'DENUNCIA-SISMICA' },
        stato: !this.stateButtonIstanzeDS ? null : { eq: this.stateButtonIstanzeDS },
        result_type: { eq: 'backoffice.ds-istanza' },
        _defaultSPs: { in: 'SPE00RP211,SPE00RP184,SPE00RP207,SPE00RP208' }
      }));
  }

  callRightSearch(page) {
    this.mudeopenFoBeService.cercaIstanzeByFilter(this.filters,
                                          page, 
                                          this.datatableIstanzeDS.limit,
                                          '-dataProtocollo').subscribe(x => {
        this.handleIstanzeDSRes(page, x);
    });
  }

  handleIstanzeDSRes(page, x) {
    this.handleDatatablePaging(this.datatableIstanzeDS, x);
    this.displayedRowsIstanzeDS = x.body;
    this.completeInfo(this.displayedRowsIstanzeDS);

    if(page == 0) {
      this.filteredRowsIstanzeDS = this.getCountersArray(this.datatableIstanzeDS, this.displayedRowsIstanzeDS[0]?.stato_counters, [ '', 'OPN', 'CLS' ]); }

    setTimeout(y => { 
      this.toggleExpandRowIstanzeFascicolo(this.displayedRowsIstanzeDS.find(t => t.id_istanza == this.rowExpandedIstanzeFascicolo), false);
    });
  }

  completeInfo(lst) {
    lst.map(item => {
      item._intestatario = this.getSubjectInfo('name', item.intestatario?.anagrafica || item.fascicolo?.intestatario?.anagrafica);
      item._indirizzo = this.getIndirizzo(item.ubicazione || item.indirizzo);
      item._tipo_pratica = this.tipiPratica.find(x => x.id == item.specie_pratica?.id)?.value;
      item._numero_protocollo = (item.numero_protocollo||'').replace('/', '<br>/');
      item._numero_pratica = (item.numero_pratica||'').replace('/', '<br>/');
    });
  }

	/* 
	* nome metodo "toggleExpandRowIstanzeFascicolo"; method description: 
	* @param (row)
	* @returns 
	*/ 

  public toggleExpandRowIstanzeFascicolo(istanza: model.IstanzaVO, expanded) {
    if(!istanza?.id_istanza) { return; }

    this.rowExpandedIstanzeFascicolo = expanded? null : istanza.id_istanza;

    if(!(istanza as any)._istanzeFascicoloList) {
      this.mudeopenFoBeService.istanzeFascicolo(istanza.id_fascicolo, { numeroPratica: istanza.numero_pratica}).subscribe(istanze => { 
        (istanza as any)._istanzeFascicoloList = this._getSubIstance(istanze, null);
        this.toggleTemplateDetails(this.istanzeDSTable, istanza, expanded);
      });
    }
    else {
      this.toggleTemplateDetails(this.istanzeDSTable, istanza, expanded); }
  }

	/* 
	* nome metodo "toggleTemplateDetails"; method description: 
	* @param (fascicolo: any)
	* @returns 
	*/ 

  public toggleTemplateDetails(table: any, row: any, expanded: boolean) {
    if(!expanded) {
      table.rowDetail.collapseAllRows();
      table.rowDetail.toggleExpandRow(row);
    }
    else if(expanded) {
      table.rowDetail.collapseAllRows(); }
  }

  _getSubIstance(istanze: model.IstanzaVO[], id_instance_parent: number, level: number = 0) {
    const inst = (istanze||[]);
    return inst.filter((istanza: model.IstanzaVO) => {
              if(istanza.id_istanza_riferimento && !inst.find(i => i.id_istanza == istanza.id_istanza_riferimento))
                istanza.id_istanza_riferimento = null; // remove reference if not found parent

              return true;
            }).filter((istanza: model.IstanzaVO) => {
      if(istanza.id_istanza_riferimento != id_instance_parent) return false;

      (istanza as any)._level = level;
      (istanza as any)._istanzeFiglie = this._getSubIstance(istanze, istanza.id_istanza, level+1);
      return true;
    });
  }

  getIndentation(ist: any) {
    let padding: number = 0;
    if(ist._level)
      padding = ist._level * 25;

    return  { 'padding-left': padding+'px' };
  }

  navigateToDetailPractice(istanza: any) {
    this.router.navigate(['backoffice', 'pratiche-ds', istanza.idPratica, 'documenti-ds', istanza.id_fascicolo]);
  }

  navigateToDetailInstance(istanza: model.IstanzaVO) {
    this.router.navigate(['backoffice', 'lista-istanze', istanza.id_istanza, 'dettaglio-istanza']);
  }

  navigateToStepperInstance(istanza: model.IstanzaVO) {
    this.router.navigate(['backoffice', 'lista-istanze', istanza.id_istanza, 'dettaglio-istanza-stepper']);
  }

  downloadExcel() {
    this.scrivaniaService.downloadExcel(this.filters);
  }

  hasUserRole(roleId: string): boolean {
    return (this.authStoreService.getUser() as UtenteBO).ruoliUtente.some(x => x.id == roleId);
  }

}
