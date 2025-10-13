/*
 * SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 */

import { AfterViewInit, Component, Injectable, OnInit, TemplateRef, ViewChild } from '@angular/core';
import * as model from 'mudeopen-common';
import {
  FormUtils,
  MessageService,
  MudeopenFoBeService,
} from 'mudeopen-common';
import { ScrivaniaService } from '../service/scrivania.service';
import { TableColumn } from '@swimlane/ngx-datatable';
import { IstanzaBO } from 'src/app/shared/model/istanzaBO.model';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { Permissions } from '../../../core/enum/permissions.enum';
import { NgbDateAdapter, NgbDateParserFormatter, NgbDatepickerI18n, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';


const I18N_VALUES = {
  'it': {
    weekdays: ['Lu', 'Ma', 'Me', 'Gi', 'Ve', 'Sa', 'Do'],
    months: ['Gen', 'Feb', 'Mar', 'Apr', 'Mag', 'Jun', 'Jul', 'Ago', 'Set', 'Ott', 'Nov', 'Dic'],
  }
};

@Injectable()
export class I18n {
  language = 'it';
}


@Injectable()
export class CustomDatepickerI18n extends NgbDatepickerI18n {


	/* 
	* nome metodo "constructor"; method description: 
	* @param (private _i18n: I18n)
	* @returns 
	*/ 

  constructor(private _i18n: I18n) {
    super();
  }


	/* 
	* nome metodo "getWeekdayShortName"; method description: 
	* @param (weekday: number)
	* @returns string
	*/ 

  getWeekdayShortName(weekday: number): string {
    return I18N_VALUES[this._i18n.language].weekdays[weekday - 1];
  }

	/* 
	* nome metodo "getMonthShortName"; method description: 
	* @param (month: number)
	* @returns string
	*/ 

  getMonthShortName(month: number): string {
    return I18N_VALUES[this._i18n.language].months[month - 1];
  }

	/* 
	* nome metodo "getMonthFullName"; method description: 
	* @param (month: number)
	* @returns string
	*/ 

  getMonthFullName(month: number): string {
    return this.getMonthShortName(month);
  }


	/* 
	* nome metodo "getDayAriaLabel"; method description: 
	* @param (date: NgbDateStruct)
	* @returns string
	*/ 

  getDayAriaLabel(date: NgbDateStruct): string {
    return `${date.day}-${date.month}-${date.year}`;
  }
}
@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss'],
  providers: [
    I18n,
    { provide: NgbDatepickerI18n, useClass: CustomDatepickerI18n }
  ]
})
export class MainComponent extends FormUtils implements AfterViewInit {
  @ViewChild('table') table: any;
  @ViewChild('actionsTemplate') actionsTemplate: TemplateRef<any>;

  rowExpanded: any;
  rows: model.IstanzaVO[] = null;

  permissions = Permissions;

  columnsIstanze: TableColumn[] = [];

  lastActivatedUrl: string;
  constructor(
    public mudeopenFoBeService: MudeopenFoBeService,
    private scrivaniaService: ScrivaniaService,
    public messageService: MessageService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    super(mudeopenFoBeService, messageService);
    this.datatable.limit = 10;

    router.events.subscribe(params => {
      if(params instanceof NavigationEnd) {
        if(route.routeConfig?.data?.reloadIt)
          route.routeConfig.data.reloadIt = this.lastActivatedUrl = null;

        /*
        if(params.url?.endsWith("/scrivania-ds") && !this.lastActivatedUrl?.endsWith("/scrivania-ds")) {
            this.loadPage(); 
            this.lastActivatedUrl = params.url;
          }
        else if(params.url?.indexOf("/dettaglio-istanza") == -1) {
          this.lastActivatedUrl = params.url; }
        */
      }
    });
  }

  booleanPipe = {
    transform: (value) => value ? 'Si' : 'No',
  };

  ngAfterViewInit() {
    this._initTable();
  }

  downloadExcel() {
    this.scrivaniaService.downloadExcel(JSON.stringify(this.filters));
  }

  get filters():any {
    return {
      id_tipo_istanza: { eq: 'DENUNCIA-SISMICA' },
      result_type: { eq: 'backoffice.ds-scrivania' },
      _defaultSPs: { in: 'SPE00RP211,SPE00RP184,SPE00RP207,SPE00RP208' },
      stato: { ne: 'ARC' },
      cc_month: { eq: this.lastMonthSelected },
      cc_year: { eq: this.lastYearSelected }
    };
  }

  loadPage(page: any = 0) {
    if (page && page.pageSize) {
      page = page.offset; }

    this.mudeopenFoBeService.cercaIstanzeByFilter(JSON.stringify(this.filters),
                                                  page, 
                                                  this.datatable.limit,
                                                  null).subscribe(x => {
        this.completeInfo(x.body);
        this.rows = x.body;
        this._handlePaging(x);
      });
  }

  rowIdentity(row) {
    return row?.id_istanza;
  }

  navigateToDetailInstance(istanza: IstanzaBO) {
    this.router.navigate(['backoffice', 'lista-istanze', istanza.id_istanza, 'dettaglio-istanza']);
  }

  navigateToStepperInstance(istanza: IstanzaBO) {
    this.router.navigate(['backoffice', 'lista-istanze', istanza.id_istanza, 'dettaglio-istanza-stepper']);
  }

  completeInfo(lst) {
    lst.map((item) =>
        (item._intestatario = this.getSubjectInfo(
          'name',
          item.intestatario?.anagrafica
        )),
        lst.map(item => item._indirizzo = this.getIndirizzo(item.ubicazione || item.indirizzo)),

    );
    lst.map((item) => {
      item._numero_pratica = item.numero_pratica + (item.assegna_abilitazioni && !item._cc_checked?'<div class="msgCC">pratica già inviata per il controllo a campione</div>':'');
    });
  }

  
  private _initTable() {
    this.columnsIstanze = [
      {
        name: 'Azioni',
        maxWidth: 98,
        canAutoResize: false,
        cellTemplate: this.actionsTemplate,
      },
      { name: 'Numero pratica', prop: '_numero_pratica', width: 148 },
      { name: 'Numero istanza', prop: 'codice_istanza', width: 148 },
      { name: 'Comune', prop: 'comune.value', width: 108 },
      { name: 'Titolo intervento', prop: 'keywords', width: 125, },
      { name: 'Indirizzo', prop: '_indirizzo', width: 125 },
      //{ name: 'Data Ricezione', prop: 'data_stato_dps', width: 125, },
      //{ name: 'Stato istanza', prop: 'stato_istanza.value', width: 88 },
      //{ name: 'Data stato', prop: 'data_creazione', width: 135 },
      { name: 'Intestatario', prop: '_intestatario', width: 128 }
    ];
  }

  public markToBeChecked(row: any) {
    if(row.assegna_abilitazioni && !row._cc_checked) {return};
    row.assegna_abilitazioni = row._cc_checked = !row._cc_checked;
  }
  
  public sendToBeChecked() {
    this.confirmDialog('Vuoi inviare a controllo a campione le pratiche selezionate?', (x) => {

        const idsToBechecked = this.rows.filter((t:any) => t._cc_checked).map(t => t.id_istanza);
        if(!idsToBechecked.length) { return; }

        const filters = this.filters;
        filters.result_type = { eq: 'backoffice.ds-set-controllo-campione' };
        filters.id = { in: idsToBechecked };
  
        this.mudeopenFoBeService.cercaIstanzeByFilter(JSON.stringify(filters),
                                                      0, 
                                                      idsToBechecked.length,
                                                      null).subscribe(x => {
          x.body.filter(ist => { 
            ((this.rows.find((t:any) => t.id_istanza == ist.id_istanza) as any) || {})._cc_checked = false;
          });
        });
    }, 'CONFERMA');
  }

  lastMonthSelected: number;
  lastYearSelected: number;
  dateNavigate(e) {
    const isDateSet = this.lastMonthSelected && this.lastYearSelected;

    this.lastMonthSelected = e.next.month;
    this.lastYearSelected = e.next.year;

    if(isDateSet) {
      this.loadPage(); }

  }
}
