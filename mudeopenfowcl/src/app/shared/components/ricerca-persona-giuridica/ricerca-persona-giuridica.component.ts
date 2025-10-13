/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;

import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { InserisciPersonaGiuridicaComponent } from '../../../shared/components/inserisci-persona-giuridica/inserisci-persona-giuridica.component';
import { initRicercaPersonaGiuridicaForm } from '../../../shared/utils/common-forms';
import { AnagraficaVO, ContattoVO, UserSelectionType } from 'mudeopen-common';
import { FormUtils, PagedData } from 'mudeopen-common';
import { MudeopenFoBeService } from 'mudeopen-common';






@Component({
  selector: 'ricerca-persona-giuridica',
  templateUrl: './ricerca-persona-giuridica.component.html',
  styleUrls: ['./ricerca-persona-giuridica.component.scss']
})
export class RicercaPersonaGiuridicaComponent extends FormUtils implements OnInit {
  ricercaPersonaGiuridicaForm: FormGroup;
  soggettiDataTable: AnagraficaVO[];

  @Input() excludeIDs: string = null;

  @Output() selected: EventEmitter<any> = new EventEmitter<any>();
  @Output() foundSoggettiEvent: EventEmitter<PagedData<ContattoVO>> = new EventEmitter<PagedData<ContattoVO>>();
  @Output() notFoundSoggettiEvent: EventEmitter<any> = new EventEmitter<any>();
  @Input('selectionType') selectionType: string = 'single'; // 'single', 'checkbox', 'multi'
  @Output() complete: EventEmitter<any> = new EventEmitter<any>();

  @Output() cancel: EventEmitter<any> = new EventEmitter<any>();
  @Input() showCancel: boolean = false;

  headers: any[] = [
    { name: 'Ragione sociale', prop: 'anagrafica.ragioneSociale' },
    { name: 'C.fiscale legale rappr.', prop: 'anagrafica.codiceFiscaleLegaleRappresentante' },
    { name: 'P.Iva', prop: '_cfPIVA' },
    { name: 'P.Iva comunitaria', prop: 'anagrafica.partitaIvaComunitaria'  }
  ];


	/* 
	* nome metodo "constructor"; method description: 
	* @param (private formsService: FormsService, 
      private modalService: NgbModal, 
      mudeopenFoBeService: MudeopenFoBeService)
	* @returns 
	*/ 

  constructor(private modalService: NgbModal, 
      mudeopenFoBeService: MudeopenFoBeService) {
    super(mudeopenFoBeService, null);
    //this.disableCountryLoading = true;

    this.datatable.limit = 5;
  }


	/* 
	* nome metodo "ngOnInit"; method description: 
	* @param ()
	* @returns void
	*/ 

  ngOnInit(): void {
    this.ricercaPersonaGiuridicaForm = initRicercaPersonaGiuridicaForm();
  }


	/* 
	* nome metodo "onModifySubject"; method description: 
	* @param ()
	* @returns 
	*/ 

  onModifySubject() {
    const modal = this.modalService.open(InserisciPersonaGiuridicaComponent, { size: 'xl', backdrop: 'static', keyboard: false });
    modal.componentInstance.soggettoOnEdit = this.datatable.selected[0];
    
    modal.componentInstance.closeEvent.subscribe(() => { modal.dismiss(); });
    modal.componentInstance.subjectCreated.subscribe((x) => {
        this.onSearch();
        modal.dismiss();
    });
  }


	/* 
	* nome metodo "notifySelectedEvent"; method description: 
	* @param ()
	* @returns void
	*/ 

  notifySelectedEvent(): void {
    this.datatable.selected.forEach(anag => {
      delete anag._cfPIVA;
      this.selected.emit(anag);
    });

    this.complete.emit();
  }


	/* 
	* nome metodo "onSearch"; method description: 
	* @param (page: any = 0)
	* @returns void
	*/ 

  onSearch(page: any = 0): void {
    this.datatable.selected = [];

    if(page && page.pageSize) { page = page.offset; }
    
    this.mudeopenFoBeService.ricercaPersonaGiuridica(this.ricercaPersonaGiuridicaForm.get('ragioneSociale').value, 
                                        this.ricercaPersonaGiuridicaForm.get('partitaIva').value, 
                                        this.ricercaPersonaGiuridicaForm.get('partitaIvaComunitaria').value, 
                                        this.ricercaPersonaGiuridicaForm.get('titolare').value,
                                        this.ricercaPersonaGiuridicaForm.get('impresa_lavori').value,
                                        this.ricercaPersonaGiuridicaForm.get('professionista').value,
                                        page, this.datatable.limit,
                                        this.excludeIDs).subscribe(x => {
      this._handlePaging(x);
      if(!x || x.body.length == 0)  {
        this.notFoundSoggettiEvent.emit();
        this.soggettiDataTable = [];
      }
      else {
        this.soggettiDataTable = x.body; //.map(y => y.anagrafica);
        this.foundSoggettiEvent.emit();
      }
    });

  }


	/* 
	* nome metodo "cancelDiallog"; method description: 
	* @param ()
	* @returns 
	*/ 

  cancelDiallog() {
    this.cancel.emit();
  }

  soggettiDataTableReal: any;
  soggettiDataTableEx: any;
  get _soggettiDataTable() {
    if(this.soggettiDataTableReal == this.soggettiDataTable)
      return this.soggettiDataTableEx;

    this.soggettiDataTableReal = this.soggettiDataTable;
    return this.soggettiDataTableEx = (this.soggettiDataTable || []).map((obj: any) => {
      return Object.assign({ _cfPIVA: obj.anagrafica.codiceFiscale || obj.anagrafica.partitaIva}, obj);
    });
  }  
}