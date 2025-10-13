import { id } from '@swimlane/ngx-datatable';
/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormsService } from '../services/forms.service';
import { ModalDialogComponent } from 'mudeopen-common';
import { FormUtils } from 'mudeopen-common';
import { AuthStoreService, MessageService } from 'mudeopen-common';
import { MudeopenFoBeService } from 'mudeopen-common';
import * as model from 'mudeopen-common';
import { NotificheComponent } from '../notifiche/notifiche.component';
import { ListaAllegatiComponent } from '../allegati/lista-allegati/lista-allegati.component';

@Component({
  selector: 'visualizza-ricerca',
  templateUrl: './visualizza-ricerca.component.html',
  styleUrls: ['./visualizza-ricerca.component.scss']
})
export class VisualizzaRicercaComponent extends FormUtils implements OnInit, OnDestroy {

  @ViewChild('fascicoliTable') fascicoliTable: any;
  @ViewChild('praticheTable') praticheTable: any;
  

  comunevalue = 'comune.value';

	/* 
	* nome metodo "isFilterSelectedFascicoli"; method description: 
	* @param (filterIndex: number)
	* @returns boolean
	*/ 

  public datatableFascicoli: any = {
    offset: 0,
    count: 0,
    limit: 10,
    selected: [],
    pages: 0,
    messages: this.datatable.messages
  }

  filteredRowsFascicoli: string[];
  stateButtonFascicoli: string = '';
  displayedRowsFascicoli: model.FascicoloVO[] = null;
  // cod fascicolo, Comune, indirizzo, intestatario, tipo fascicolo, data creazione, stato 
  columnsFascicoli: any = [
    {name: 'Cod. fascicolo', prop: 'codice_fascicolo', width: '108'},
    {name: 'Comune', prop: this.comunevalue, width: '108'},
    {name: 'Indirizzo', prop: '_indirizzo', width: '125'},
    {name: 'Intestatario', prop: '_intestatario', width: '128'},
    {name: 'Istanza apertura fascicolo', prop: 'tipologia_istanza.value', width: '108'},
    {name: 'Data inserimento', prop: 'data_creazione', width: '135'},
    {name: 'Stato', prop: 'stato_fascicolo.value', width: '88'}
    //{name: 'Data presentaz.', prop: 'data_chiusura', width: '128'}
  ];


	/* 
	* nome metodo "isFilterSelectedIstanze"; method description: 
	* @param (filterIndex: number)
	* @returns boolean
	*/ 

  public datatableIstanze: any = {
    offset: 0,
    count: 0,
    limit: 10,
    selected: [],
    pages: 0,
    messages: this.datatable.messages
  }

  filteredRowsIstanze: string[];
  stateButtonIstanze: string = '';
  displayedRowsIstanze: model.IstanzaVO[] = null;
  columnsIstanze: any = [
    {name: 'Cod. istanza', prop: '_cod_and_keywords', width: '148'},
    {name: 'Tipo istanza', prop: '_type_and_idf', width: '148'},
    {name: 'Intestatario', prop: '_intestatario', width: '128'},
    {name: 'Comune', prop: this.comunevalue, width: '108'},
    {name: 'Indirizzo', prop: '_indirizzo', width: '125'},
    {name: 'Stato.', prop: 'stato_istanza.value', width: '88'},
    {name: 'Data cambio stato', prop: 'data_creazione', width: '135'},
    {name: 'Data presentaz.', prop: 'data_stato_dps', width: '128'}
  ];

  public datatablePratiche: any = {
    offset: 0,
    count: 0,
    limit: 10,
    selected: [],
    pages: 0,
    messages: this.datatable.messages
  }

  filteredRowsPratiche: string[];
  stateButtonPratiche: string = '';
  displayedRowsPratiche: any[] = null;
  columnsPratiche = [
    {name: 'Numero pratica',prop: 'numero_pratica', resizeable: true, sortable: false, width: 148},
    {name: 'Anno', prop: 'anno', width: 108 },
    {name: 'Ente', prop: 'ente.descrizione', width: 108 },
    {name: 'Comune', prop: 'comune.value', width: 108 },
    {name: 'Data Creazione',prop: 'data_creazione', width: 125}
  ];

  searchSelected: number = 0;
  ricercaFascicoliForm: FormGroup;
  ricercaIstanzeForm: FormGroup;
  ricercaPraticheForm: FormGroup;

  tipologieIstanza: model.SelectVO[];
  tipologiaIntervento: model.SelectVO[];
  statoIstanzaList: model.SelectVO[];
  statoFascicoloList: model.SelectVO[];

  //tipologiePresentatore: SelectVO[];

  provincePiemonte: model.ProvinciaVO[];

  lastActivatedUrl: string;

	/* 
	* nome metodo "constructor"; method description: 
	* @param (private formsService: FormsService, 
            mudeopenFoBeService: MudeopenFoBeService, 
            private router: Router,
            private route: ActivatedRoute,
            private messages: MessageService,
            private modalService: NgbModal)
	* @returns 
	*/ 

  constructor(public formsService: FormsService, 
            private authService: AuthStoreService,
            public mudeopenFoBeService: MudeopenFoBeService, 
            private router: Router,
            private route: ActivatedRoute,
            private messages: MessageService,
            private modalService: NgbModal) {
    super(mudeopenFoBeService, messages);
    this.disableCountryLoading = false;

    this.ricercaFascicoliForm = this.initRicercaFascicoliForm();
    this.ricercaIstanzeForm = this.initRicercaIstanzaForm();
    this.ricercaPraticheForm = this.initRicercaPraticheForm();

    router.events.subscribe(params => {
      if(params instanceof NavigationEnd) {
        if(params.url == "/fascicolo/ricerca") {
          if(this.lastActivatedUrl?.startsWith("/fascicolo/gestione/")) {
            this.onSearch(); }
          else {
            this.searchSelected = 0;
  
            this.ricercaFascicoliForm.reset();
            this.ricercaIstanzeForm.reset();
            this.ricercaPraticheForm.reset();
    
            this.displayedRowsFascicoli = null;
            this.displayedRowsIstanze = null;
            this.displayedRowsPratiche = null;
          }
        }

        this.lastActivatedUrl = params.url;
      }
    });
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
      data_creazione_to: new FormControl(),
      keywords: new FormControl()
    });
  }

	/* 
	* nome metodo "initRicercaFascicoliForm"; method description: 
	* @param ()
	* @returns FormGroup
	*/ 

  initRicercaFascicoliForm(): FormGroup {
    return new FormGroup({
      id_intestatario_pf: new FormControl(),
      id_intestatario_pg: new FormControl(),
      data_protocollo_from: new FormControl(),
      duf: new FormControl(),
      codice_fascicolo: new FormControl(),
      data_creazione_from: new FormControl(),
      data_creazione_to: new FormControl(),
      id_tipo_intervento: new FormGroup({
        id: new FormControl(),
        value: new FormControl()
      }),
      id_comune: new FormGroup({
        codBelfiore: new FormControl(),
        id: new FormControl(),
        value: new FormControl()
      }),
      id_provincia: new FormGroup({
        id: new FormControl(),
        value: new FormControl()
      }),
      id_dug: new FormGroup({
        id: new FormControl(),
        value: new FormControl()
      }),
      stato: new FormGroup({
        id: new FormControl(),
        value: new FormControl()
      })
    });
  }

	/* 
	* nome metodo "initRicercaPraticheForm"; method description: 
	* @param ()
	* @returns FormGroup
	*/ 

  initRicercaPraticheForm(): FormGroup {
    return new FormGroup({
      id: new FormControl(),
      codice_istanza: new FormControl(),
      id_istanza_riferimento: new FormGroup({
        id: new FormControl(),
        value: new FormControl()
      }),
      id_tipo_istanza: new FormGroup({
        id: new FormControl(),
        value: new FormControl()
      }),
      numero_protocollo: new FormControl(),
      num_pratica: new FormControl(),
      anno: new FormControl(),
      ente: new FormControl(),
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
/*
      id_pratica_comunale: new FormGroup({
        id: new FormControl(),
        value: new FormControl()
      }),
*/
//      id_referente: new FormControl(),
      id_intestatario_pf: new FormControl(),
      id_intestatario_pg: new FormControl(),
      data_protocollo_from: new FormControl(),
      data_protocollo_to: new FormControl(),
      data_registrazione_from: new FormControl(),
      data_registrazione_to: new FormControl(),
      //data_registrazione_pratica_from: new FormControl(),
      //data_registrazione_pratica_to: new FormControl()
    });
  }


	/* 
	* nome metodo "ngOnInit"; method description: 
	* @param ()
	* @returns void
	*/ 

  ngOnInit(): void {
          
    this.mudeopenFoBeService.getTipologieIntervento().then(x => this.tipologiaIntervento = x);
    this.mudeopenFoBeService.getTipologieIstanza().then(x => this.tipologieIstanza = x.map(y => {return { id: y.id, value: y.value + ' (' + y.id + ')' }}) );
    this.mudeopenFoBeService.getSessionData('statoIstanzaList').then(x => {
      this.statoIstanzaList = [];
          
      x.filter((y: model.SelectVO) => {
        this.statoIstanzaList.push(y);

        if('APA'.indexOf(y.id || '-') > -1)
          this.statoIstanzaList.push({ id: '-1', value: '-!- denuncia sismica regionale -'});
      });
    });
    this.mudeopenFoBeService.getSessionData('statoFascicoloList').then(x => this.statoFascicoloList = x);

    this.mudeopenFoBeService.getProvince('{"idRegione":{"eq":"1"}}').subscribe(x => { 
      this.provincePiemonte = x; 
    });

    if($$.isLocal) {
      this.ricercaFascicoliForm.get('codice_fascicolo').setValue('7522'); //7520:add-fil-ds 7083 / 7200 / 7109 /  7048(bugged) - 6927 - 7033 - 6755 - 1903
      this.ricercaPraticheForm.get('codice_istanza').setValue('22');
    }

  }


	/* 
	* nome metodo "getSearchStates"; method description: 
	* @param (type: string)
	* @returns any
	*/ 

  getSearchStates(type: string): any {
    return type === 'fascicoli'?this.statoFascicoloList:this.statoIstanzaList;
  }

  wrongDateInterval: string;

	/* 
	* nome metodo "onSearch"; method description: 
	* @param (page: any = 0)
	* @returns 
	*/ 

  rowExpandedIstanzePratiche: any;
  rowExpandedIstanzeFascicolo: any;
  onSearch(page: any = 0, statusButton: string = '') {
    if(page && page.pageSize) { page = page.offset; }

    const fmr2fix = this.searchSelected==1?this.ricercaIstanzeForm:
        (this.searchSelected==2?this.ricercaPraticheForm:this.ricercaFascicoliForm);

    if(fmr2fix?.value?.stato?.id === '') {
        fmr2fix.get('stato').setValue({id: null, value: null}); }
    this.wrongDateInterval = ['data_creazione_', 'data_protocollo_', 'data_registrazione_pratica_'].find(name => {
          return fmr2fix.value[name+'from'] && fmr2fix.value[name+'to'] && 
                    fmr2fix.value[name+'from'].split('/').reverse().join() > fmr2fix.value[name+'to'].split('/').reverse().join();
        });
    if(this.wrongDateInterval) {
      setTimeout(() => { 
        this.wrongDateInterval = null }, 5000);
      return;
    }

    this.callRightSearch(page, statusButton);
  }

  callRightSearch(page, statusButton) {
    switch(this.searchSelected) {
      case 0: // fascicoli
      this.stateButtonFascicoli = statusButton;
      this.mudeopenFoBeService.recuperaFascicoli(Object.assign({}, this.ricercaFascicoliForm.value, {stato: { id: this.stateButtonFascicoli?statusButton: this.ricercaFascicoliForm.value.stato.id}}), 
                                                   page, 
                                                   this.datatable.limit).subscribe(x => {
          this.handleFascicoliRes(page, x);
        });
        break;
      case 1: // istanze
      this.stateButtonIstanze = statusButton;
      this.mudeopenFoBeService.cercaIstanze(Object.assign({}, this.ricercaIstanzeForm.value, {stato: { id: this.stateButtonIstanze?statusButton: this.ricercaIstanzeForm.value.stato.id}}), 
                                            page, 
                                            this.datatable.limit).subscribe(x => {
            this.handleIstanzeRes(page, x);
          });
        break;
      case 2: // pratiche
      this.stateButtonPratiche = statusButton;
      this.mudeopenFoBeService.recuperaPratiche(Object.assign(this.ricercaPraticheForm.value, this.stateButtonPratiche?{stato: { id: statusButton}}:null), 
                                            page, 
                                            this.datatable.limit).subscribe(x => {
          this.handlePraticheRes(page, x);
        });
        break;
    }
  }

  handleFascicoliRes(page, x) {
    this.handleDatatablePaging(this.datatableFascicoli, x);
    this.displayedRowsFascicoli = x.body;
    this.completeInfo(this.displayedRowsFascicoli);
    if(page == 0) {
      this.filteredRowsFascicoli = this.getCountersArray(this.datatableFascicoli, this.displayedRowsFascicoli[0]?.stato_counters, [ '', 'OPN', 'CLS' ]); }

    setTimeout(y => { 
      this.toggleExpandRowIstanzeFascicolo(this.displayedRowsFascicoli.find(t => t.id_fascicolo == this.rowExpandedIstanzeFascicolo), false);
    });
  }

  handleIstanzeRes(page, x) {
    this.handleFascicoliRes(page, x);
    this.handleDatatablePaging(this.datatableIstanze, x);

    this.displayedRowsIstanze = x.body;
    this.completeInfo(this.displayedRowsIstanze);
    if(page == 0) {
      this.filteredRowsIstanze = this.getCountersArray(this.datatableIstanze, this.displayedRowsIstanze[0]?.stato_counters, [ '', 'BZZ', 'DFR', 'FRM', 'DPS' ]); }
  }
  
  handlePraticheRes(page, x) {
    this.handleDatatablePaging(this.datatablePratiche, x);
    this.displayedRowsPratiche = x.body;
    this.completeInfo(this.displayedRowsPratiche);
    this.filteredRowsPratiche = this.getCountersArray(this.datatablePratiche, this.displayedRowsPratiche[0]?.stato_counters, [ '' ]);
  }

  completeInfo(lst) {
    lst.map(item => {
      item._cod_and_keywords = item.codice_istanza + (!item.keywords ? '' : '<div class="keywordsDiv"><em>Parole chiave: </em><strong>' + item.keywords + '</strong></div>');
      item._type_and_idf = item.tipologia_istanza?.value;
      if(item.idf_autorizzato == 'S') {
        item._type_and_idf +=
          '<div class="idfDiv"><em>Autorizzazione richiesta per vincolo idrogeologico:</em>'
            + '<div><strong>' + (item.idf_autorizzato == 'S'?'concessa':'negata') +'</strong></div>'
            + (!item.idf_numero_determina_esito_aut ? '' : '<div><em>N° determina esito: </em><strong>' + item.idf_numero_determina_esito_aut +'</strong></div>')
            + (!item.idf_data_scadenza_autorizzazione ? '' : '<div><em>Data scadenza aut.: </em><strong>' + item.idf_data_scadenza_autorizzazione +'</strong></div>');
          + '</div>'
      }
      item._intestatario = this.getSubjectInfo('name', item.intestatario?.anagrafica || item.fascicolo?.intestatario?.anagrafica);
      item._indirizzo = this.getIndirizzo(item.ubicazione || item.indirizzo);
      item._isSentState = 'BZZ,RPA,DFR,FRM'.indexOf(item.stato_istanza?.id || '') == -1;
    });
  }

	/* 
	* nome metodo "selectForm"; method description: 
	* @param (tabIndex: number)
	* @returns 
	*/ 

  selectForm(tabIndex: number) {
    this.searchSelected = tabIndex;
  }

	/* 
	* nome metodo "deleteFascicolo"; method description: 
	* @param (fascicolo: any)
	* @returns 
	*/ 

  deleteFascicolo(fascicolo: any) {
    const modal = this.modalService.open(ModalDialogComponent);
    modal.componentInstance.text = "Sei sicuro di voler eliminare il fascicolo selezionato?"
    modal.componentInstance.title = "Eliminazione"

    modal.componentInstance.cancelEvent.subscribe(() => modal.dismiss());
    modal.componentInstance.confirmEvent.subscribe(() => {
      modal.dismiss()

      this.mudeopenFoBeService.eliminaFascicolo(fascicolo.id_fascicolo).subscribe(x => {
        this.messages.showTopMessage("Fascicolo eliminato con succsso");
  
        this.onSearch();
      });
    });
  }


	/* 
	* nome metodo "deleteIstanza"; method description: 
	* @param (istanza: IstanzaVO)
	* @returns 
	*/ 

  deleteIstanza(istanza: model.IstanzaVO) {
    const modal = this.modalService.open(ModalDialogComponent);
    modal.componentInstance.text = "Sei sicuro di voler eliminare l'istanza selezionata?"
    modal.componentInstance.title = "Eliminazione"

    modal.componentInstance.cancelEvent.subscribe(() => modal.dismiss());
    modal.componentInstance.confirmEvent.subscribe(() => {
      modal.dismiss()

      this.mudeopenFoBeService.eliminaIstanza(istanza.id_istanza).subscribe(x => {
        this.messages.showTopMessage("Istanza eliminata con succsso");

        this.onSearch();
      });
    });
  }

	/* 
	* nome metodo "toggleExpandRowIstanzeFascicolo"; method description: 
	* @param (row)
	* @returns 
	*/ 

  public toggleExpandRowIstanzeFascicolo(fascicolo: model.FascicoloVO, expanded) {
    if(!fascicolo?.id_fascicolo) { return; }

    this.rowExpandedIstanzeFascicolo = expanded? null : fascicolo.id_fascicolo;

    if(!(fascicolo as any)._istanzeFascicoloList) {
      this.mudeopenFoBeService.istanzeFascicolo(fascicolo.id_fascicolo).subscribe(istanze => { 
        (fascicolo as any)._istanzeFascicoloList = this._getSubIstance(istanze, null);
        this.toggleTemplateDetails(this.fascicoliTable, fascicolo, expanded);
      });
    }
    else {
      this.toggleTemplateDetails(this.fascicoliTable, fascicolo, expanded); }
  }

  public toggleExpandRowIstanzePratica(pratica: any, expanded) {
    if(!pratica?.id_pratica) { return; }

    this.rowExpandedIstanzePratiche = expanded? null : pratica.id_pratica;

    if(!(pratica as any)._istanzeFascicoloList) {
      this.mudeopenFoBeService.cercaIstanze(JSON.stringify({ id_pratica: { eq: pratica.id_pratica }, stato: {nin: ['BZZ','DFR','FRM','RPA']} }), 0, 1000).subscribe(x => {
        (pratica as any)._istanzePraticaList = this._getSubIstance(x.body, null);
        this.toggleTemplateDetails(this.praticheTable, pratica, expanded);
      });
    }
    else {
      this.toggleTemplateDetails(this.praticheTable, pratica, expanded); }
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

  openNotifiche(idIstanza: number) {
    const modal = this.modalService.open(NotificheComponent, { size: 'xl', backdrop: 'static', keyboard: false });
    modal.componentInstance.lockedOnIdIstanza = idIstanza;
    modal.componentInstance.popupmode = true;
  }
  
  openAttachmentsList(istanza: model.IstanzaVO) {
    const modal = this.modalService.open(ListaAllegatiComponent, { size: 'xl', backdrop: 'static', keyboard: false });
    modal.componentInstance.idIstanza = istanza.id_istanza;
  }

	/* 
	* nome metodo "setRouteTrack"; method description: 
	* @param ()
	* @returns 
  setRouteTrack() {
    this.sub = this.route.queryParams.subscribe(params => { });
  }
	*/ 

	/* 
	* nome metodo "ngOnDestroy"; method description: 
	* @param ()
	* @returns 
	*/ 

  sub: any;
  ngOnDestroy() {
    super.ngOnDestroy();
    if(this.sub) { this.sub.unsubscribe(); }
  }

  displayFolderPermissionsManagerPopup(idFascicolo) {
    this.formsService.displayPermissionsManagerPopup(this.authService.getUser(), idFascicolo).subscribe(res => {
      if(res == undefined) { res = null; } // SQ crap!
    });
  }

  displayInstancePermissionsManagerPopup(idIstanza, idTemplate) {
    this.formsService.displayPermissionsManagerPopup(this.authService.getUser(), null, idIstanza, idTemplate).subscribe(res => {
      if(res == undefined) { res = null; } // SQ crap
    });
  }

  backToBzz(istanza) {
    this.confirmDialog('Vuoi sbloccare l\'istanza e riportarla in stato \'BOZZA\'?', (x) => {
      this.formsService.backToBzz(istanza).subscribe(x => {
        this.onSearch();
      });
    }, 'SBLOCCA');
  }

  downloadPDF(istanza: model.IstanzaVO) {
    this.mudeopenFoBeService.downloadIstanza(istanza.id_istanza).subscribe(x => {
      this.download2user(x);
    });
  }

  downloadIdfFile(istanza: model.IstanzaVO) {
    this.mudeopenFoBeService.downloadAllegato(istanza.idf_index_node, 'idf').subscribe(x => {
      this.download2user(x);
    });
  }

  downloadReceiptPDF(istanza: model.IstanzaVO) {
    this.mudeopenFoBeService.downloadRicevutaPdf(istanza.id_istanza).subscribe(x => {
      this.download2user(x);
    });
  }

}