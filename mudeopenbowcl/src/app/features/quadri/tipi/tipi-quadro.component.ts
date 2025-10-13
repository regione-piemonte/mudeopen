/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $: any;
declare var $$: any;



import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormUtils } from 'mudeopen-common';
import { MessageService } from 'mudeopen-common';
import { MudeopenFoBeService } from 'mudeopen-common';
import * as model from 'mudeopen-common';
import { QuadriService } from '../services/quadri-services';









@Component({
  selector: 'tipi-quadro',
  templateUrl: './tipi-quadro.component.html',
  styleUrls: ['./tipi-quadro.component.scss']
})
export class TipiQuadroComponent extends FormUtils implements OnInit, AfterViewInit {

  @ViewChild('table') table: any;

  
  _CREATE_NEW: any = [ { '_CREATE_NEW': true } ];

  editingRow: any = null;
  isFilterOn: boolean = false;

  isEditingIndex: number = null;

  edtCode: string;
  edtDescr: string;
  edtCategoriaTipoQuadri: any;
  searchCode: string;
  searchDescr: string;
  searchCategoriaTipoQuadri: model.CategoriaQuadroVO;

  errCol1: string;
  errCol2: string;
  errCol3: string;
  rows: model.TipoQuadroVO[] = null;
  rowExpanded: any;
  listaCategoriaTipoQuadri: model.CategoriaQuadroVO[] = null;

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
  }

	/* 
	* nome metodo "ngOnInit"; descrizione: 
	* @param ()
	* @returns void
	*/ 

  ngOnInit(): void {
    this.mudeopenFoBeService.listaCategoriaTipoQuadri().subscribe(res => { 
      this.listaCategoriaTipoQuadri = res;
    });

    this.onSearch();
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

  onSearch(page: any = 0) {
    if(page && page.pageSize) { page = page.offset; }

    this.mudeopenFoBeService.listaTipoQuadri(JSON.stringify({
                                  "code": {"eq":this.searchCode || null},
                                  "descr": {"eq":this.searchDescr || null},
                                  "idCategoriaQuadro": {"eq":this.searchCategoriaTipoQuadri || null}
                                }), page, this.datatable.limit).subscribe(x => {
        this.rows = x.body ;

        setTimeout(x => { 
          this.toggleExpandRow(this.rows.find(t => t.id_tipo_quadro == this.rowExpanded), false);
        });
        this._handlePaging(x);
    });
  }

  startEditing(row: model.TipoQuadroVO, rowIndex: number) {
    this.edtCode = row.cod_tipo_quadro;
    this.edtDescr = row.des_tipo_quadro;
    this.edtCategoriaTipoQuadri = row.id_categoria_quadro;
    this.isEditingIndex = rowIndex;
    this.editingRow = row;
    this.rowExpanded = null;
  }

  cancelEditing(row: any, rowIndex: number) {
    if(row.cod_tipo_quadro == this.edtCode 
          && row.des_tipo_quadro == this.edtDescr
          && row.id_categoria_quadro == this.edtCategoriaTipoQuadri) {
		  this._cancelEditing(row, rowIndex); }
    else {
      this.confirmDialog("Si vogliono eliminare le modifiche?", () => {
        this._cancelEditing(row, rowIndex);
	}); }
  }

  _cancelEditing(row: any, rowIndex: number) {
    if(!row.id_tipo_quadro) {
      this._splice(null, rowIndex, 1); }

    this.isNewRecord = false;
    this.editingRow = this.isEditingIndex = null;
  }

  isNewRecord: boolean = false;
  saveMessage: boolean;
  //Abbiamo disabilitato il check sul tipo perchè nella libreria common non è presente
  saveRow(row: model.TipoQuadroVO | any, rowIndex: number) {
    this.errCol1 = !(this.edtCode = (this.edtCode || '').trim()).length? 'Campo obbligatorio' : (!this.edtCode.match(/[a-zA-Z0-9_]*/)?"Formato non valido" : null);
    this.errCol2 = !this.edtDescr?.length?"Campo obbligatorio" : null;

    if(this.errCol1 || this.errCol2) { return; }
  //Abbiamo disabilitato il check sul tipo perchè nella libreria common non è presente

    this.mudeopenFoBeService.salvaTipoQuadro({
          "id_tipo_quadro": this.isNewRecord?null:row.id_tipo_quadro,
          "cod_tipo_quadro": this.edtCode,
          "des_tipo_quadro": this.edtDescr,
          "id_categoria_quadro": this.edtCategoriaTipoQuadri,
          "tipo_quadro_padre": row.tipo_quadro_padre
        }as any).subscribe(x => {
      this.editingRow = this.isEditingIndex = null;
      this.saveMessage = true;

      this.rows[this.rows.indexOf(row)] = x
      this.rows = [...this.rows];
    });

    this.isNewRecord = false;
  }

  deleteRow(tipoQuadro: model.TipoQuadroVO, rowIndex: number) {
    this.confirmDialog("Vuoi eliminare il tipo quadro?", () => {
      this.mudeopenFoBeService.eliminaTipoQuadro(tipoQuadro.id_tipo_quadro).subscribe(x => {
        this._splice(null, rowIndex, 1);
      });
    });
  }

  addNew(tipoQuadroParent: model.TipoQuadroVO = null) {
    this.edtCode = this.edtDescr = this.edtCategoriaTipoQuadri = '';
    this.isNewRecord = true;

    this._splice(0, 0, 0, this.editingRow = {
        "id_tipo_quadro": null,
        "cod_tipo_quadro": this.edtCode,
        "des_tipo_quadro": this.edtDescr,
        "id_categoria_quadro": this.edtCategoriaTipoQuadri,
        "modificabile": true,
        "tipo_quadro_padre": tipoQuadroParent
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
    this.searchCode = this.searchDescr = null;
    this.isFilterOn = false;
    this.onSearch(this.datatable.offset);
  }
 
  // Open/close panel  
  toggleExpandRow(row: any, expanded) {
    if(!row?.id_tipo_quadro) { return; }

    //row.expanded = !expanded;
    this.rowExpanded = expanded? null : row.id_tipo_quadro;

    if(!row.quadri) {
      this.mudeopenFoBeService.listaQuadriPerTipo(row.id_tipo_quadro).subscribe(x => {
        row.quadri = x;
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

  quadroCreate(quadro: model.TipoQuadroVO) {
    this.quadriService.selectNewQuadroType('crea nuovo quadro', res => {
      this.saveMessage = true;
      this.onSearch(this.datatable.offset);
    }, quadro);
  }

  quadroCreateNewVersion(quadro: model.QuadroVO) {
    this.quadriService.openQuadroDialog('crea nuova versione ' + (quadro.num_versione + 1), quadro.flg_tipo_gestione, quadro, quadroRes => {
      this.saveMessage = true;
      this.onSearch(this.datatable.offset);
    }); // open just the same quadro type
  }

  quadroModify(quadro: model.QuadroVO) {
    this.quadriService.openQuadroDialog('modifica versione ' + quadro.num_versione, quadro.flg_tipo_gestione, quadro, quadroRes => {
      this.saveMessage = true;
      this.onSearch(this.datatable.offset);
    });
  }

  quadroView(quadro: model.QuadroVO) {
    this.quadriService.openQuadroDialog('visualizza versione ' + quadro.num_versione, quadro.flg_tipo_gestione, quadro, null, true);
  }

  quadroDelete(lista: any, quadro: model.QuadroVO, index: number) {
    this.confirmDialog("Vuoi eliminare il quadro?", () => {
      this.mudeopenFoBeService.eliminaQuadro(quadro.id_quadro).subscribe(x => {
        lista.splice(index, 1);
        this.saveMessage = true;
        this.onSearch(this.datatable.offset);
      });
    });
  }

  getCategoriaQuadro(idCat) {
    return (this.listaCategoriaTipoQuadri || []).find(x => x.id_categoria_quadro == idCat)?.desc_categoria_quadro;
  }
}