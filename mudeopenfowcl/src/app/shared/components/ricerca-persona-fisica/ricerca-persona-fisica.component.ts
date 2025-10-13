/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { InserisciPersonaFisicaComponent } from '../../../shared/components/inserisci-persona-fisica/inserisci-persona-fisica.component';
import { initRicercaPersonaFisicaForm } from '../../../shared/utils/common-forms';
import { AnagraficaVO, ContattoVO, UtenteVO } from 'mudeopen-common';
import { FormUtils, PagedData } from 'mudeopen-common';
import { AuthStoreService } from 'mudeopen-common';
import { MudeopenFoBeService, UserSelectionType } from 'mudeopen-common';



@Component({
  selector: 'ricerca-persona-fisica',
  templateUrl: './ricerca-persona-fisica.component.html',
  styleUrls: ['./ricerca-persona-fisica.component.scss']
})
export class RicercaPersonaFisicaComponent extends FormUtils implements OnInit {
  ricercaPersonaFisicaForm: FormGroup;
  soggettiDataTable: AnagraficaVO[];

  headers: any[] = [
    { name: 'Nome', prop: 'anagrafica.nome' },
    { name: 'Cognome', prop: 'anagrafica.cognome' },
    { name: 'Codice fiscale', prop: 'anagrafica.codiceFiscale' }
  ];

  @Output() selected: EventEmitter<ContattoVO | UtenteVO> = new EventEmitter<ContattoVO | UtenteVO>();
  @Output() foundSoggettiEvent: EventEmitter<PagedData<ContattoVO | UtenteVO>> = new EventEmitter<PagedData<ContattoVO | UtenteVO>>();
  @Output() notFoundSoggettiEvent: EventEmitter<any> = new EventEmitter<any>();
  @Output() complete: EventEmitter<any> = new EventEmitter<any>();
  @Input('selectionType') selectionType: string = 'single'; // 'single', 'checkbox', 'multi', 'multiClick'
  @Input() userSelectionType: UserSelectionType = UserSelectionType.CONTACTS;
  @Input() excludeIDs: string = null;
  @Input() idIstanza: number = null;
  
  @Output() cancel: EventEmitter<any> = new EventEmitter<any>();
  @Input() showCancel: boolean = false;


	/* 
	* nome metodo "constructor"; method description: 
	* @param (private formsService: FormsService, 
      private modalService: NgbModal, 
      mudeopenFoBeService: MudeopenFoBeService)
	* @returns 
	*/ 

  constructor(private modalService: NgbModal, 
      private authService: AuthStoreService,
      mudeopenFoBeService: MudeopenFoBeService) {
    super(mudeopenFoBeService, null);

    //this.disableCountryLoading = true;
    this.datatable.limit = 5;
  }

  get isModifyAllowed(): boolean {
    return this.userSelectionType != UserSelectionType.ACCREDITED 
          && this.datatable.selected.length +'' === '1'
          && this.datatable.selected[0].contatto_in_rubrica;
  }

	/* 
	* nome metodo "ngOnInit"; method description: 
	* @param ()
	* @returns void
	*/ 

  ngOnInit(): void {
    this.ricercaPersonaFisicaForm = initRicercaPersonaFisicaForm();

    if(this.userSelectionType == UserSelectionType.ACCREDITED) {
      this.headers = [
        { name: 'Nome', prop: 'contatto.anagrafica.nome' },
        { name: 'Cognome', prop: 'contatto.anagrafica.cognome' },
        { name: 'Codice fiscale', prop: 'contatto.anagrafica.codiceFiscale' }
      ]; 

      this.ricercaPersonaFisicaForm.get('nome').setValidators(Validators.required);
      this.ricercaPersonaFisicaForm.get('cognome').setValidators(Validators.required);
      this.ricercaPersonaFisicaForm.get('codiceFiscale').setValidators(Validators.required);
    }
  }

	/* 
	* nome metodo "notifySelectedEvent"; method description: 
	* @param ()
	* @returns void
	*/ 

  notifySelectedEvent(): void {
    this.datatable.selected.forEach(anag => {
      this.selected.emit(anag);
    });

    this.complete.emit();
  }


	/* 
	* nome metodo "onModifySubject"; method description: 
	* @param ()
	* @returns 
	*/ 

  onModifySubject() {
    const modal = this.modalService.open(InserisciPersonaFisicaComponent, { size: 'xl', backdrop: 'static', keyboard: false });
    modal.componentInstance.soggettoOnEdit = this.datatable.selected[0];

    modal.componentInstance.closeEvent.subscribe(() => { modal.dismiss(); });
    modal.componentInstance.subjectCreated.subscribe((subject) => {
      modal.dismiss();

      this.selected.emit(subject);
      this.complete.emit();
    });
  }

  get isContactSearch(): boolean {
    return this.userSelectionType != UserSelectionType.ACCREDITED;
  }


	/* 
	* nome metodo "onSearch"; method description: 
	* @param (page: any = 0)
	* @returns void
	*/ 

  onSearch(page: any = 0): void {
    this.datatable.selected = [];

    if(this.userSelectionType == UserSelectionType.ACCREDITED
       && this.ricercaPersonaFisicaForm.invalid) {
        this.commonUtils.validateForm(this.ricercaPersonaFisicaForm); 
        return;
    }

    if(page && page.pageSize) { page = page.offset; }
    this.mudeopenFoBeService.ricercaPersonaFisica(this.ricercaPersonaFisicaForm.get('nome').value, 
                                                  this.ricercaPersonaFisicaForm.get('cognome').value,
                                                  this.ricercaPersonaFisicaForm.get('codiceFiscale').value, 
                                                  this.ricercaPersonaFisicaForm.get('titolare').value,
                                                  this.ricercaPersonaFisicaForm.get('impresa_lavori').value,
                                                  this.ricercaPersonaFisicaForm.get('professionista').value,
                                                  page, 
                                                  this.datatable.limit,
                                                  "",
                                                  this.excludeIDs,
                                                  this.userSelectionType,
                                                  this.idIstanza).subscribe(x => {
      this._handlePaging(x);
      if(!x || x.body.length == 0)  {
        this.notFoundSoggettiEvent.emit();
        this.soggettiDataTable = [];
      }
      else {
        this.soggettiDataTable = x.body; // .map(y => y.anagrafica);
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
}