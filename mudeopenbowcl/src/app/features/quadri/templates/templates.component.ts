/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $: any;
declare var $$: any;



import { AfterViewInit, Component, Injectable, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbDateAdapter, NgbDateParserFormatter, NgbDatepickerI18n, NgbDateStruct, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { environment } from 'buildfiles/environment.local-rp-01';
import { forkJoin } from 'rxjs';
import { FormUtils } from 'mudeopen-common';
import { MessageService } from 'mudeopen-common';
import { MudeopenFoBeService } from 'mudeopen-common';
import * as model from 'mudeopen-common';
import { SelectVO } from 'mudeopen-common';
import { QuadriService } from '../services/quadri-services';
import { PublicationConfirmComponent } from './publication-confirm/publication-confirm.component';
import { SelezionaAllegatoComponent } from './seleziona-allegato/seleziona-allegato.component';
import { SelezionaTipoIstanzaComponent } from "./seleziona-tipo-istanza/seleziona-tipo-istanza.component";
import { SelezioneIntestazioneComponent } from "./selezione-intestazione/selezione-intestazione.component";
import { SelezioneQuadroComponent } from './selezione-quadro/selezione-quadro.component';













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
	* nome metodo "constructor"; descrizione: 
	* @param (private _i18n: I18n)
	* @returns 
	*/ 

  constructor(private _i18n: I18n) {
    super();
  }


	/* 
	* nome metodo "getWeekdayShortName"; descrizione: 
	* @param (weekday: number)
	* @returns string
	*/ 

  getWeekdayShortName(weekday: number): string {
    return I18N_VALUES[this._i18n.language].weekdays[weekday - 1];
  }

	/* 
	* nome metodo "getMonthShortName"; descrizione: 
	* @param (month: number)
	* @returns string
	*/ 

  getMonthShortName(month: number): string {
    return I18N_VALUES[this._i18n.language].months[month - 1];
  }

	/* 
	* nome metodo "getMonthFullName"; descrizione: 
	* @param (month: number)
	* @returns string
	*/ 

  getMonthFullName(month: number): string {
    return this.getMonthShortName(month);
  }


	/* 
	* nome metodo "getDayAriaLabel"; descrizione: 
	* @param (date: NgbDateStruct)
	* @returns string
	*/ 

  getDayAriaLabel(date: NgbDateStruct): string {
    return `${date.day}-${date.month}-${date.year}`;
  }
}

@Injectable()
export class CustomDateAdapter {

	/* 
	* nome metodo "fromModel"; descrizione: 
	* @param (value: string)
	* @returns NgbDateStruct
	*/ 

  fromModel(value: string): NgbDateStruct {
    if(!value) { return null; }

    const parts = value.split('/');
    
    return {
      day: + parts[0], month: + parts[1], year: + parts[2]
    } as NgbDateStruct;
  }


	/* 
	* nome metodo "toModel"; descrizione: 
	* @param (date: NgbDateStruct)
	* @returns string
	*/ 

  toModel(date: NgbDateStruct): string {
    return date ? ('0' + date.day).slice(-2) + "/" + ('0' + date.month).slice(-2) + "/" + date.year : null;
  }
}

@Injectable()
export class NgbDateCustomParserFormatter extends NgbDateParserFormatter {

	/* 
	* nome metodo "parse"; descrizione: 
	* @param (value: string)
	* @returns NgbDateStruct
	*/ 

  parse(value: string): NgbDateStruct {
    if(!value) { return null; }
    
    const parts = value.split('/');
    return {
      day: + parts[2], month: + parts[1], year: + parts[0]
    } as NgbDateStruct;
  }


	/* 
	* nome metodo "format"; descrizione: 
	* @param (date: NgbDateStruct)
	* @returns string
	*/ 

  format(date: NgbDateStruct): string {
    return date ? ('0' + date.day).slice(-2) + "/" + ('0' + date.month).slice(-2) + "/" + date.year : null;
  }
}


@Component({
  selector: 'app-templates',
  templateUrl: './templates.component.html',
  styleUrls: ['./templates.component.scss'],
  providers: [
    I18n,
    { provide: NgbDateAdapter, useClass: CustomDateAdapter },
    { provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter },
    { provide: NgbDatepickerI18n, useClass: CustomDatepickerI18n }
  ]

})
export class TemplatesComponent extends FormUtils implements OnInit, AfterViewInit {

  @ViewChild('table') table: any;

  _CREATE_NEW: any = [ { '_CREATE_NEW': true } ];

  statiList: SelectVO[] = [ 
    { id: '0', value: 'No' },
    { id: '1', value: 'Si' },
  ]

  editingRow: any = null;
  isFilterOn: boolean = false;
  rowExpandedAllegati: any;
  rowExpandedQuadri: any;

  isEditingIndex: number = null;

  tipoIstanzaList: model.SelectVO[] = [];
  tipoIstanzaListFiltered: model.SelectVO[] = [];

  edtTipologiaIstanza: model.SelectVO;
  edtCodice: string;
  edtDescrizione: string;
  edtVersione: number;
  edtStato: number = 0;
  edtDataInizioValidita: string;
  edtDataCessazione: string;
  edtPM: boolean = false;

  searchTipologiaIstanza: model.SelectVO;
  searchCodice: string;
  searchDescrizione: string;
  searchVersione: string;
  searchStato: string;
  searchDataInizioValidita: string;
  searchDataCessazione: string;

  errCol: string[] = [ null, null, null, null, null, null, null ];

  rows: model.TemplateVO[] = null;

  minDate: any = null;
  maxDate: any = null;
  startDate: any = null;

	/* 
	* nome metodo "constructor"; descrizione: 
	* @param (private mudeopenFoBeService: MudeopenFoBeService, 
            private router: Router,
            private route: ActivatedRoute,
            private messages: MessageService,
            private modalService: NgbModal)
	* @returns 
	*/ 
  constructor(public mudeopenFoBeService: MudeopenFoBeService, 
              private router: Router,
              public quadriService: QuadriService,
              public messageService: MessageService,
              private route: ActivatedRoute,
              private modalService: NgbModal) {
    super(mudeopenFoBeService, messageService);

    this.disableCountryLoading = true;
    this.datatable.limit = 50;
  }


	/* 
	* nome metodo "ngOnInit"; descrizione: 
	* @param ()
	* @returns void
	*/ 

  ngOnInit(): void {
    this.mudeopenFoBeService.getTipologieIstanza().then(x => {
      this.tipoIstanzaList = x;
      this.onSearch(0, true);
    });
  }

  ngAfterViewInit(){
    setTimeout(_ => 
      window.dispatchEvent(new Event('resize')), 300 );
  }  

	/* 
	* nome metodo "onSearch"; descrizione: 
	* @param (page: any = 0)
	* @returns 
	*/ 

  onSearch(page: any = 0, isInit: boolean = false) {
    if(page && page.pageSize) { page = page.offset; }

    this.mudeopenFoBeService.listaTemplate(JSON.stringify({
                        "tipologiaIstanza": {"eq":this.searchTipologiaIstanza?.id || null },
                        "codice": {"eq":this.searchCodice || null},
                        "descrizione": {"eq":this.searchDescrizione || null},
                        "versione": {"eq":this.searchVersione || null},
                        "stato": {"eq":this.searchStato || null},
                        "dataInizioValidita": {"gte":this.searchDataInizioValidita || null},
                        "dataCessazione": {"lte":this.searchDataCessazione || null}
                }), page, this.datatable.limit).subscribe(x => {
        this.rows = x.body ;

        if(isInit) {
          this.tipoIstanzaListFiltered = this.tipoIstanzaList.filter(x => 
            !this.rows.find(y => y?.tipo_istanza?.id == x.id)
          );
        }
      
        setTimeout(x => { 
            this.toggleExpandRow(this.rows.find(t => t.id_template == this.rowExpanded), false, true);
          });
        this._handlePaging(x);
    });
  }

  startEditing(row: model.TemplateVO, rowIndex: number) {
    this.edtTipologiaIstanza = this.tipoIstanzaList.find(x => x.id == row.tipo_istanza?.id );
    this.edtCodice = row.cod_template;
    this.edtDescrizione = row.des_template;
    this.edtVersione = row.num_versione;
    this.edtStato = row.flg_attivo;
    this.edtDataInizioValidita = row.data_inizio_validita;
    this.edtDataCessazione = row.data_cessazione;
    this.edtPM = row.obbligatoria_nomina_pm;
    
    this.isEditingIndex = rowIndex;
    this.editingRow = row;
  }

  isRowChanged(row: model.TemplateVO) {
    return !(row.tipo_istanza?.id == this.edtTipologiaIstanza?.id
              && row.cod_template == this.edtCodice
              && row.des_template == this.edtDescrizione
              && row.num_versione == this.edtVersione
              && row.flg_attivo == this.edtStato
              && row.data_inizio_validita == this.edtDataInizioValidita
              && row.data_cessazione == this.edtDataCessazione
              && row.obbligatoria_nomina_pm != this.edtPM)
  }
  
  cancelEditing(row: model.TemplateVO, rowIndex: number) {
    if(!this.isRowChanged(row)) {
      this._cancelEditing(row, rowIndex); }
    else {
      this.confirmDialog("Si vogliono eliminare le modifiche?", () => {
        this._cancelEditing(row, rowIndex);
      }); }
  }

  _cancelEditing(row: model.TemplateVO, rowIndex: number) {
    if(!row.id_template) {
      this._splice(null, rowIndex, 1); }

    this.isNewRecord = false;
    this.editingRow = this.isEditingIndex = null;
  }

  isNewRecord: boolean = false;
  saveMessage: boolean;
  saveRow(row: model.TemplateVO, rowIndex: number) {
    this.errCol[0] = row.id_template || this.edtTipologiaIstanza?.id? null :  'Selezionare il campo';
    this.errCol[1] = (row?.id_template && row?.num_versione != 1) ? null : 'Campo obbligatorio';
    this.errCol[1] = !this.errCol[1] || (this.edtCodice = (this.edtCodice||'').trim())?.match(/^[a-zA-Z0-9._-]{1,20}$/) ? null : 'Solo caratteri: A-Z, 0-9, _';
    this.errCol[2] = this.edtDescrizione? null :  'Campo obbligatorio';

    if(this.errCol.filter(x => x != null).length > 0) {
      return; }

    this.mudeopenFoBeService.salvaTemplate({
              id_template: this.isNewRecord?null:row.id_template,
              tipo_istanza: this.edtTipologiaIstanza,
              cod_template: this.edtCodice.toUpperCase(),
              des_template: this.edtDescrizione,
              num_versione: this.edtVersione,
              flg_attivo: this.edtStato,
              data_inizio_validita: this.edtDataInizioValidita,
              data_cessazione: this.edtDataCessazione,
              obbligatoria_nomina_pm: this.edtPM
          }).subscribe(x => {
            this.editingRow = this.isEditingIndex = null;
            this.saveMessage = true;

            this.rows[this.rows.indexOf(row)] = x
            this.rows = [...this.rows];
          });

    this.isNewRecord = false;
  }

  /*
  getDate(dateIn: any): Date {
    if(!dateIn) return null;
    dateIn = dateIn.split('/');

    return new Date(parseInt(dateIn[3], 10), parseInt(dateIn[3], 10) - 1, parseInt(dateIn[0], 10);
  }
  */

  deleteRow(template: model.TemplateVO, rowIndex: number) {
    this.confirmDialog("Vuoi eliminare il tipo quadro?", () => {
      this.mudeopenFoBeService.eliminaTemplate(template.id_template).subscribe(x => {
        this._splice(null, rowIndex, 1);
      });
    });
  }

  resetEditFields() {
    this.edtTipologiaIstanza = 
      this.edtCodice = 
      this.edtDescrizione = 
      this.edtVersione = 
      this.edtStato = 
      this.edtDataInizioValidita = 
      this.edtDataCessazione = null;
      this.edtPM = false;
  }

  resetSearchFields() {
    this.searchTipologiaIstanza = 
      this.searchCodice = 
      this.searchDescrizione = 
      this.searchVersione = 
      this.searchStato = 
      this.searchDataInizioValidita = 
      this.searchDataCessazione = null;
  }

  addNew() {
    this.resetEditFields();
    this.isNewRecord = true;

    this._splice(0, 0, 0, this.editingRow = {
        num_versione: (this.edtVersione = 1),
        id_template: null,
        tipo_istanza: this.edtTipologiaIstanza,
        cod_template: this.edtCodice,
        des_template: this.edtDescrizione,
        flg_attivo: this.edtStato,
        data_inizio_validita: this.edtDataInizioValidita,
        data_cessazione: this.edtDataCessazione,
        obbligatoria_nomina_pm: this.edtPM
      });
      this.datatable.count++;

    setTimeout(x => { 
      window.scrollTo(0,0); }, 200);
  }

  _splice(eIndex, startIndex, size, obj?) {
    this.isEditingIndex = eIndex;
    if(obj) {
      this.rows.splice(startIndex, size, obj); }
    else {
      this.rows.splice(startIndex, size); }

    this.datatable.count -= size;

    if(!size) {
      this.rows = [...this.rows]; }
    else {
      this.onSearch(this.datatable.offset); }
  }

  getRowClass = (row)=>{
    return { 'editing-row': row == this.editingRow,
              'expanded-row': row.expanded }
  }

  showFilter() {
    this.isFilterOn = true;
  }

  hideFilter() {
    this.resetSearchFields();

    this.isFilterOn = false;
    this.onSearch(this.datatable.offset);
  }
 
  // Open/close panel  
  rowExpanded: number;
  rowQuadroExpanded: number;
  rowAllegatoExpanded: number;
  toggleExpandRow(row, expanded, realoading: boolean = false) {
    if(!row?.id_template) { return; }

    if(!realoading) {
      this.resetAllegatiFilter(); }

    this.rowExpanded = expanded? null : row.id_template;

    if(!row.templateQuadri) {
      forkJoin([
        this.mudeopenFoBeService.listaQuadriTemplate(row.id_template),
        this.mudeopenFoBeService.listaAllegatiTemplate(row.id_template)
      ]).subscribe((results) => {
        row.templateQuadri = results[0];
        row.allegati = results[1];
        this.toggleTemplateDetails(row, expanded);
      }); }
    else {
      this.toggleTemplateDetails(row, expanded); }
  }

  toggleTemplateDetails(row: any, expanded: boolean) {
    if(!expanded) {
      this.table.rowDetail.collapseAllRows();
      this.table.rowDetail.toggleExpandRow(row);
    }
    else if(expanded) {
      this.table.rowDetail.collapseAllRows(); }
  }

  quadroCreate(quadro: model.QuadroVO) {
    this.quadriService.selectNewQuadroType('crea nuovo quadro', res => {
      this.onSearch(this.datatable.offset);
    }, quadro.tipo_quadro);
  }

  quadroCreateNewVersion(quadro: model.QuadroVO) {
    this.quadriService.openQuadroDialog('crea nuova versione ' + (quadro.num_versione + 1), quadro.flg_tipo_gestione, quadro, quadroRes => {
      this.onSearch(this.datatable.offset);
    }); // open just the same quadro type
  }

  quadroModify(quadro: model.QuadroVO) {
    this.quadriService.openQuadroDialog('modifica versione ' + quadro.num_versione, quadro.flg_tipo_gestione, quadro, quadroRes => {
      this.reload();
    });
  }

  reload() {
    this.rows = JSON.parse(JSON.stringify(this.rows));    
  }

  quadroAdd(template: model.TemplateVO, templateQuadro: model.TemplateQuadroVO = null) {
    const modal = this.modalService.open(SelezioneQuadroComponent, { size: 'xl', backdrop: 'static', keyboard: false });
    modal.componentInstance.id_quadro = templateQuadro?.quadro?.id_quadro;
    modal.componentInstance.ordinamento_template_quadro = templateQuadro?.ordinamento_template_quadro;
    modal.componentInstance.parameter_String = templateQuadro?.proprieta;

    modal.componentInstance.confirmEvent.subscribe((data) => {
      data && this.mudeopenFoBeService.salvaQuadroTemplate(template.id_template, {
          id_template_quadro: templateQuadro?.id_template_quadro,
          ordinamento_template_quadro: data.ordinamento_template_quadro,
          quadro: data.quadro,
          template: template,
          proprieta: data.parameter_string
        }).subscribe(x => {
          this.saveMessage = true;
          this.onSearch(this.datatable.offset);
        });

      modal.dismiss();
    });
  }

  quadroDelete(template: model.TemplateVO, templateQuadro: model.TemplateQuadroVO) {
    this.confirmDialog("Vuoi eliminare il collegamento al quadro nel template?", () => {
      this.mudeopenFoBeService.eliminaQuadroTemplate(templateQuadro.id_template_quadro).subscribe(x => {
        this.saveMessage = true;
        this.onSearch(this.datatable.offset);
      });
    });
  }

  allegatoAdd(template: model.TemplateVO, templateTipoAllegato: model.TipoAllegatoExtendedVO = null) {
    const modal = this.modalService.open(SelezionaAllegatoComponent, { size: 'xl', backdrop: 'static', keyboard: false });
    modal.componentInstance.data = templateTipoAllegato;
    modal.componentInstance.allegatiAlreadyAssigned = (template as any).allegati;

    modal.componentInstance.confirmEvent.subscribe((data: any) => {
      data && this.mudeopenFoBeService.salvaAllegatoTemplate(template.id_template, data.allegato, data.file).subscribe(x => {
          this.saveMessage = true;
          this.onSearch(this.datatable.offset);
        });
    });
  }

  allegatoDelete(template: model.TemplateVO, templateAllegato: model.TipoAllegatoExtendedVO) {
    this.confirmDialog("Vuoi eliminare il collegamento all'allegato nel template?", () => {
      this.mudeopenFoBeService.eliminaAllegatoTemplate(templateAllegato.id).subscribe(x => {
        this.saveMessage = true;
        this.onSearch(this.datatable.offset);
      });
    });
  }

  cloneTemplate(row: model.TemplateVO) {
    const modal = this.modalService.open(SelezionaTipoIstanzaComponent, { size: 'xl', backdrop: 'static', keyboard: false });

    modal.componentInstance.confirmEvent.subscribe((id_tipo_istanza: any) => {
      id_tipo_istanza && this.mudeopenFoBeService.duplicaTemplate(row.id_template, id_tipo_istanza).subscribe(x => {
        this.onSearch(this.datatable.offset);
      });

      modal.dismiss();
    });
    
  }

  newTemplateVersion(row: model.TemplateVO) {
    this.confirmDialog("Vuoi creare una nuova versione del template?", () => {
      this.mudeopenFoBeService.nuovaVersioneTemplate(row.id_template).subscribe(x => {
        this.onSearch(this.datatable.offset);
      });
    }, 'CREA');
  }

  publishTemplate(row: model.TemplateVO) {
    const modal = this.modalService.open(PublicationConfirmComponent, { size: 'xl', backdrop: 'static', keyboard: false });
    modal.componentInstance.data = row;
    modal.componentInstance.confirmEvent.subscribe((data) => {
      data && this.mudeopenFoBeService.pubblicaTemplate(row.id_template, data.simplePublish && 'simple').subscribe(x => {
        this.saveMessage = true;
        this.onSearch(this.datatable.offset);
      });
      modal.dismiss();
    });
  }

  intestazioneTemplate(row: model.TemplateVO) {
    const modal = this.modalService.open(SelezioneIntestazioneComponent, { size: 'xl', backdrop: 'static', keyboard: false });
    modal.componentInstance.data = row;
    modal.componentInstance.readOnly = !row.modificabile;

    modal.componentInstance.confirmEvent.subscribe((data) => {
      if(data) {
        this.onSearch(this.datatable.offset); }
        
      modal.dismiss();
    });

  }

  get frontOfficePath(): string {
    return environment?.frontoffice || '';
  }

  searchAllegato: string;
  searchCategoria: string;
  searchObbligatorio: string;
  searchRipetibile: string;
  searchRicorrente: string;
  searchFirma: string;
  searchOrdinale: string;
  rowExpandedFilterAllegati: boolean = false;

  resetAllegatiFilter() {
    this.searchAllegato =
                          this.searchCategoria =
                          this.searchObbligatorio =
                          this.searchRipetibile =
                          this.searchRicorrente =
                          this.searchFirma =
                          this.searchOrdinale = null;
  }

  isYesNo(str: string): boolean {
    if(str) {
      str = str.toLowerCase();
      if(str === 'si' || str === 'sì') { return true; }
      if(str === 'no') { return false; }
    }
  }

  templateAllegati(row: any): any {
    return (row?.allegati || []).filter((allegato: model.TipoAllegatoExtendedVO) => {
      return (!this.searchAllegato || (
                !!allegato.codice_tipo_allegato.match(new RegExp(this.searchAllegato, 'i')) ||
                !!allegato.descrizione_tipo_allegato.match(new RegExp(this.searchAllegato, 'i')) ||
                !!allegato.descrizione_breve_tipo_allegato.match(new RegExp(this.searchAllegato, 'i'))
              )
            )
            && (!this.searchCategoria || (
                  !!allegato.categoria_allegato.id_categoria_allegato.match(new RegExp(this.searchCategoria, 'i')) ||
                  !!allegato.categoria_allegato.descrizione_categoria_allegato.match(new RegExp(this.searchCategoria, 'i')) ||
                  !!allegato.categoria_allegato.descrizione_estesa_categoria_allegato.match(new RegExp(this.searchCategoria, 'i'))
                )
            )
            && (!this.searchObbligatorio || allegato.obbligatorio == this.isYesNo(this.searchObbligatorio) || (this.isYesNo(this.searchObbligatorio) === undefined && !!allegato.espressione_obbligatorieta?.match(new RegExp(this.searchObbligatorio, 'i'))))
            && (!this.searchRipetibile || allegato.ripetibile == this.isYesNo(this.searchRipetibile) || (this.isYesNo(this.searchRipetibile) === undefined && !!allegato.espressione_ripetibilita?.match(new RegExp(this.searchRipetibile, 'i'))))
            && (!this.searchRicorrente || allegato.flag_ricorrente == this.isYesNo(this.searchRicorrente));
    });
  }

  toggleAllegatiFilter() {
    this.resetAllegatiFilter();
    this.rowExpandedFilterAllegati = !this.rowExpandedFilterAllegati;
  }

}
