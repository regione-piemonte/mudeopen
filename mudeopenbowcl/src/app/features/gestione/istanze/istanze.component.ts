import { filter } from 'rxjs/operators';
/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $: any;
declare var $$: any;



import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormUtils } from 'mudeopen-common';
import { MessageService } from 'mudeopen-common';
import { MudeopenFoBeService } from 'mudeopen-common';
import { FormControl, FormGroup } from '@angular/forms';
import * as model from 'mudeopen-common';
import { CambioStatoComponent } from './cambio-stato/cambio-stato.component';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'istanze',
  templateUrl: './istanze.component.html',
  styleUrls: ['./istanze.component.scss']
})
export class IstanzeComponent extends FormUtils implements OnInit, AfterViewInit {
  @ViewChild('table') table: any;

  rowExpanded: any;
  rows: model.IstanzaVO[] = null;

  ricercaIstanzeForm: FormGroup;

  tipologieIstanza: model.SelectVO[];
  tipologiaIntervento: model.SelectVO[];
  statoIstanzaList: model.SelectVO[];
  statoFascicoloList: model.SelectVO[];
  provincePiemonte: model.ProvinciaVO[];

  columnsIstanze: any = [
    {name: 'Cod. istanza', prop: 'codice_istanza', width: '148'},
    {name: 'Tipo istanza', prop: 'tipologia_istanza.value', width: '148'},
    {name: 'Intestatario', prop: '_intestatario', width: '128'},
    {name: 'Comune', prop: 'comune.value', width: '108'},
    {name: 'Indirizzo', prop: '_indirizzo', width: '125'},
    {name: 'Stato', prop: 'stato_istanza.value', width: '88'},
    {name: 'Cambio stato', prop: 'data_creazione', width: '135'},
    {name: 'Data pres.', prop: 'data_registrazione_pratica', width: '128'}
  ];


	/* 
	* nome metodo "constructor"; descrizione: 
	* @param (private mudeopenFoBeService: MudeopenFoBeService, 
            private router: Router,
            private route: ActivatedRoute,
            private messages: MessageService,
            private modalService: NgbModal)
	* @returns 
	*/ 
  lastActivatedUrl: string;
  constructor(public mudeopenFoBeService: MudeopenFoBeService, 
              private router: Router,
              public messageService: MessageService,
              private route: ActivatedRoute,
              private modalService: NgbModal) {
    super(mudeopenFoBeService, messageService);
    this.disableCountryLoading = false;

    this.datatable.limit = 10;
    this.ricercaIstanzeForm = this.initRicercaIstanzaForm();
/*
    router.events.subscribe(params => {
      if(params instanceof NavigationEnd) {
        if(route.routeConfig?.data?.reloadIt)
          route.routeConfig.data.reloadIt = this.lastActivatedUrl = null;

        if(params.url?.endsWith("/lista-istanze") && !this.lastActivatedUrl?.endsWith("/lista-istanze")) {
            this.onSearch(); 
            this.lastActivatedUrl = params.url;
          }
        else if(!params.url?.endsWith("/dettaglio-istanza")) {
          this.lastActivatedUrl = params.url; }
      }
    });
*/
  }

	/* 
	* nome metodo "ngOnInit"; descrizione: 
	* @param ()
	* @returns void
	*/ 

  ngOnInit(): void {
    this.mudeopenFoBeService.getTipologieIntervento().then(x => this.tipologiaIntervento = x);
    this.mudeopenFoBeService.getTipologieIstanza().then(x => this.tipologieIstanza = x);
    this.mudeopenFoBeService.getSessionData('statoIstanzaList').then(x => 
      this.statoIstanzaList = x.filter((y:model.SelectVO) => y.id != 'BZZ' && y.id != 'DFR' && y.id != 'FRM')
    );
    this.mudeopenFoBeService.getSessionData('statoFascicoloList').then(x => this.statoFascicoloList = x);

    this.mudeopenFoBeService.getProvince('{"idRegione":{"eq":"1"}}').subscribe(x => { this.provincePiemonte = x; });
  }
 

  ngAfterViewInit(){
    setTimeout(_ => 
      window.dispatchEvent(new Event('resize')), 300 );
  }  

	/* 
	* nome metodo "initRicercaIstanzaForm"; method description: 
	* @param ()
	* @returns FormGroup
	*/ 

  initRicercaIstanzaForm(): FormGroup {
    return new FormGroup({
      id_tipo_istanza: new FormGroup({
        id: new FormControl(),
        value: new FormControl()
      }),
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
      stato: new FormGroup({
        id: new FormControl(),
        value: new FormControl()
      }),
      id_intestatario_pf: new FormControl(),
      id_intestatario_pg: new FormControl(),
      codice_istanza: new FormControl(),
      data_creazione_from: new FormControl(),
      data_creazione_to: new FormControl()
    });
  }

	/* 
	* nome metodo "onSearch"; descrizione: 
	* @param (page: any = 0)
	* @returns 
	*/ 

  wrongDateInterval: string;
  public onSearch(page: any = 0) {
    if(page && page.pageSize) { page = page.offset; }

    if(this.ricercaIstanzeForm?.value?.stato?.id === '') {
        this.ricercaIstanzeForm.get('stato').setValue({id: null, value: null}); }

    if(this.wrongDateInterval = ['data_creazione_', 'data_protocollo_', 'data_registrazione_pratica_']
            .find(name => {
              return this.ricercaIstanzeForm.value[name+'from'] && this.ricercaIstanzeForm.value[name+'to'] && 
                        this.ricercaIstanzeForm.value[name+'from'].split('/').reverse().join() > this.ricercaIstanzeForm.value[name+'to'].split('/').reverse().join();
            })) {
      setTimeout(() => { 
        this.wrongDateInterval = null }, 5000);
      return;
    }

    this.mudeopenFoBeService.cercaIstanze(this.ricercaIstanzeForm.value, page, this.datatable.limit).subscribe(x => {
      this.completeInfo(x.body);
      this.rows = x.body ;

      setTimeout(x => { 
        this.toggleExpandRow(this.rows.find(t => t.id_istanza == this.rowExpanded), false);
      });
      this._handlePaging(x);
    });
  }

  completeInfo(lst) {
    lst.map(item => item._indirizzo = this.getIndirizzo(item.ubicazione || item.indirizzo) );
    lst.map(item => item._intestatario = this.getSubjectInfo('name', item.fascicolo?.intestatario?.anagrafica) );
  }

  displayChangeAPA(istanza: model.IstanzaVO): boolean {
    return istanza.stato_istanza.id == 'DPS';
  }

  // Open/close panel  
  public toggleExpandRow(row: any, expanded) {
    if(!row?.id_istanza) { return; }

    this.rowExpanded = expanded? null : row.id_istanza;

    if(!row.pratiche) {
      // TODO
    }
    else {
      this.toggleTemplateDetails(row, expanded); }
  }

  public toggleTemplateDetails(row: any, expanded: boolean) {
    if(!expanded) {
      this.table.rowDetail.collapseAllRows();
      this.table.rowDetail.toggleExpandRow(row);
    }
    else if(expanded) {
      this.table.rowDetail.collapseAllRows(); }
  }

  searchSelected: number = 0;
  public selectForm(tabIndex: number) {
    this.searchSelected = tabIndex;
  }

  public onTempStateChange(instance: model.IstanzaVO) {
    const modal = this.modalService.open(CambioStatoComponent, { size: 'xl', backdrop: 'static', keyboard: false, windowClass: 'alwaysScrollModal' });
    modal.componentInstance.istanza = instance;
    modal.componentInstance.onConfirm.subscribe((data) => {
      const inst = {
        'anno': parseInt((''+instance.data_registrazione_pratica).replace(/[0-9]{2,2}\/[0-9]{2,2}\/([0-9]{4,4}).*/, '$1'), 10),
        'numero_pratica': data.numeroProtocollo,
        'numero_protocollo': data.numeroProtocollo,
        'data_protocollo': data.dataProtocollo
      }

      //instance.anno = parseInt((''+instance.data_registrazione_pratica).replace(/[0-9]{2,2}\/[0-9]{2,2}\/([0-9]{4,4}).*/, '$1'), 10);
      /*
      instance.numero_pratica = data.numeroProtocollo;
      instance.numero_protocollo = data.numeroProtocollo;
      instance.data_protocollo = data.dataProtocollo;
      */

      this.mudeopenFoBeService.cambiaStatoIstanza(instance.id_istanza, 'APA', inst).subscribe(x => { 
        modal.dismiss()
        this.onSearch();
      });
    });
    /*
    // alternative version with immediate status change:
    this.confirmDialog("Vuoi mettere l'istanza in stato 'REGISTRATA DA PA'?", () => {
      this.mudeopenFoBeService.cambiaStatoIstanza(instance.id_istanza, 'APA').subscribe(x => { 
        this.onSearch();
      });
    }, "CAMBIA STATO");
    */
 
  }  

}