/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { InserisciPersonaFisicaComponent } from '../../../shared/components/inserisci-persona-fisica/inserisci-persona-fisica.component';
import { InserisciPersonaGiuridicaComponent } from '../../../shared/components/inserisci-persona-giuridica/inserisci-persona-giuridica.component';
import { MudeopenFoBeService } from 'mudeopen-common';
import * as swagger from 'mudeopen-common';

@Component({
  selector: 'ricerca-inserisci-persona-fisica-giuridica',
  templateUrl: './ricerca-inserisci-persona-fisica-giuridica.component.html',
  styleUrls: ['./ricerca-inserisci-persona-fisica-giuridica.component.css']
})
export class RicercaInserisciPersonaFisicaGiuridicaComponent {
  subjectSelectMethod: string = 'PF';
  isNewInsert: boolean;
  
  @Input() excludeIDs: string = null;


  @Output() closeEvent: EventEmitter<any> = new EventEmitter<any>();
  @Output() selectedPF: EventEmitter<any> = new EventEmitter<any>();
  @Output() selectedPG: EventEmitter<any> = new EventEmitter<any>();
  @Output() complete: EventEmitter<any> = new EventEmitter<any>();
  @Input('notitle') notitle: boolean = false;
  @Input('insertChildTitle') insertChildTitle: any = false;
  @Input('selectionType') selectionType: string = 'single'; // 'single', 'checkbox', 'multi'
  @Input('insertModal') insertModal: boolean = true;
  @Input('mode') mode: "PG" | "PF" | "ALL" = "ALL";
  @Input('title') title: "Ricerca soggetto";
  @Input('titleDescription') titleDescription: "Seleziona il soggetto dalla ricerca, oppure inserirne uno nuovo.";
  @Input('userSelectionType') userSelectionType: any;

  constructor(private modalService: NgbModal,
    private mudeopenFoBeService: MudeopenFoBeService) {}


	/* 
	* nome metodo "onChangeTab"; method description: 
	* @param (tabindex: number)
	* @returns 
	*/ 

  onChangeTab(tabindex: number) {
    this.subjectSelectMethod = tabindex?'PG':'PF';
  }


	/* 
	* nome metodo "onItemSelectedPF"; method description: 
	* @param (soggetto: swagger.ContattoVO)
	* @returns void
	*/ 

  onItemSelectedPF(soggetto: swagger.ContattoVO): void {
    this.selectedPF.emit(soggetto);
  }


	/* 
	* nome metodo "onItemSelectedPG"; method description: 
	* @param (soggetto: swagger.ContattoVO)
	* @returns void
	*/ 

  onItemSelectedPG(soggetto: swagger.ContattoVO): void {
    this.selectedPG.emit(soggetto);
  }


	/* 
	* nome metodo "onClose"; method description: 
	* @param ()
	* @returns void
	*/ 

  onClose(): void {
    this.closeEvent.emit();
  }


	/* 
	* nome metodo "insertNewSubject"; method description: 
	* @param ()
	* @returns 
	*/ 

  insertNewSubject() {
    if(this.isNewInsert = !this.insertModal) { return; }

    const modal = this.modalService.open(this.subjectSelectMethod==='PF'?InserisciPersonaFisicaComponent:InserisciPersonaGiuridicaComponent, { size: 'lg', backdrop: 'static', keyboard: false });
    modal.componentInstance.title = this.insertChildTitle;
    modal.componentInstance.closeEvent.subscribe(() => { modal.dismiss(); });
    modal.componentInstance.subjectCreated.subscribe(subject => {
        modal.dismiss();
        this['onItemSelected'+this.subjectSelectMethod](subject);
    });

  }


	/* 
	* nome metodo "onComplete"; method description: 
	* @param ()
	* @returns 
	*/ 

  onComplete() {
    this.complete.emit();
  }

}