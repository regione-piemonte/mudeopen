/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { InserisciPersonaGiuridicaComponent, InserisciPersonaFisicaComponent } from '../../../shared/components';
import { initRicercaPersonaFisicaForm, initRicercaPersonaGiuridicaForm } from '../../../shared/utils/common-forms';

import { FormsService } from '../../../features/fascicoli/services/forms.service';
import { MudeopenFoBeService } from 'mudeopen-common';
import { ContattoVO } from 'mudeopen-common';

import { FormUtils } from 'mudeopen-common';

import { MessageService } from 'mudeopen-common';


@Component({
  selector: 'visualizza-rubrica',
  templateUrl: './visualizza-rubrica.component.html',
  styleUrls: ['./visualizza-rubrica.component.css']
})
export class VisualizzaRubricaComponent extends FormUtils  implements OnInit {

  subjectTypes: Array<string> = [ 'Persona fisica', 'Persona giuridica' ];
  subjectTypeSelected: number = 0;
  subjectSorts: Array<any> = 
      [ 
        { 
          _0_nome: 'Nome',
          _1_cognome: 'Cognome',
          _2_cf: 'Codice fiscale' 
        },
        { 
          _0_ragioneSociale: 'Ragione sociale',
          _1_piva: 'Partita IVA',
          _2_pivaComunitaria: 'Partita IVA comunit./estera' 
        }
      ];

  ricercaSoggettoForm: FormGroup;

  soggettiPersonaFisica: ContattoVO[];
  soggettiPersonaGiuridica: ContattoVO[];


	/* 
	* nome metodo "constructor"; method description: 
	* @param (private formsService: FormsService, 
              mudeopenFoBeService: MudeopenFoBeService, 
              private messages: MessageService,
              private modalService: NgbModal)
	* @returns 
	*/ 

  constructor(private formsService: FormsService, 
              mudeopenFoBeService: MudeopenFoBeService, 
              private messages: MessageService,
              private modalService: NgbModal) {
    super(mudeopenFoBeService, messages);

    this.datatable.limit = 4;
  }


	/* 
	* nome metodo "ngOnInit"; method description: 
	* @param ()
	* @returns void
	*/ 

  ngOnInit(): void {
    
    this.ricercaSoggettoForm = initRicercaPersonaFisicaForm();
    this.goSearch();

  }


	/* 
	* nome metodo "getSoggetti"; method description: 
	* @param ()
	* @returns Array<any>
	*/ 

  getSoggetti(): Array<any> {
    const arr = this.subjectTypeSelected == 0? 
        this.soggettiPersonaFisica : this.soggettiPersonaGiuridica;
    return arr || [];
  }


	/* 
	* nome metodo "openInserisciSoggetto"; method description: 
	* @param ()
	* @returns void
	*/ 

  openInserisciSoggetto(): void {
    const modal = this.modalService.open(this.subjectTypeSelected==0?InserisciPersonaFisicaComponent:InserisciPersonaGiuridicaComponent, { size: 'xl', backdrop: 'static', keyboard: false });
    modal.componentInstance.closeEvent.subscribe(() => { 
      modal.dismiss(); });
    modal.componentInstance.subjectCreated.subscribe(soggettoForm => {
        modal.dismiss();
        this.goSearch();
    });
  }

  sortingKey: string;
  get sorting(): string {
    return this.sortingKey || Object.keys(this.subjectSorts[this.subjectTypeSelected])[0];
  }
  
  set sorting(sort: string) {
    this.sortingKey = sort;
    this.goSearch();
  } 


	/* 
	* nome metodo "goSearch"; method description: 
	* @param (page: any = 0)
	* @returns void
	*/ 

  goSearch(page: any = 0): void {
    if(page && page.pageSize) { page = page.offset; }

    
    switch(this.subjectTypeSelected) {
      case 0: // Persona fisica.
        this.mudeopenFoBeService.ricercaPersonaFisica(this.ricercaSoggettoForm.value.nome, 
                                                      this.ricercaSoggettoForm.value.cognome, 
                                                      this.ricercaSoggettoForm.value.codiceFiscale, 
                                                      this.ricercaSoggettoForm.value.titolare,
                                                      this.ricercaSoggettoForm.value.impresa_lavori,
                                                      this.ricercaSoggettoForm.value.professionista,
                                                      page, 
                                                      this.datatable.limit,
                                                      '+'+this.sorting.substring(3)).subscribe(x => {
          this._handlePaging(x);
          this.soggettiPersonaFisica = x.body
        });
        break;
      case 1: // Persona giuridica.
        this.mudeopenFoBeService.ricercaPersonaGiuridica(this.ricercaSoggettoForm.value.ragioneSociale, 
                                                          this.ricercaSoggettoForm.value.partitaIva, 
                                                          this.ricercaSoggettoForm.value.partitaIvaComunitaria, 
                                                          this.ricercaSoggettoForm.value.titolare,
                                                          this.ricercaSoggettoForm.value.impresa_lavori,
                                                          this.ricercaSoggettoForm.value.professionista,
                                                          page,
                                                          this.datatable.limit,
                                                          '+'+this.sorting.substring(3)).subscribe(x => {
          this._handlePaging(x);
          this.soggettiPersonaGiuridica = x.body
        });
    }
  }


	/* 
	* nome metodo "onChangeTipologiaSoggettoRicerca"; method description: 
	* @param (tabindex: number)
	* @returns 
	*/ 

  onChangeTipologiaSoggettoRicerca(tabindex: number) {
    this.sortingKey = null;
    this.ricercaSoggettoForm = (this.subjectTypeSelected = tabindex) == 0? 
        initRicercaPersonaFisicaForm() : initRicercaPersonaGiuridicaForm();

    this.goSearch();
  }


	/* 
	* nome metodo "onEdit"; method description: 
	* @param ()
	* @returns void
	*/ 

  onEdit(): void {
    this.goSearch();
  }


	/* 
	* nome metodo "onSubmit"; method description: 
	* @param ()
	* @returns 
	*/ 

  onSubmit() {
    this.goSearch();
  }


	/* 
	* nome metodo "isPersonaFisica"; method description: 
	* @param ()
	* @returns boolean
	*/ 

  isPersonaFisica(): boolean {
    return this.subjectTypeSelected == 0;
  }

  isChecked(): boolean {
    return this.ricercaSoggettoForm.controls['titolare'].value;
  }

  
}