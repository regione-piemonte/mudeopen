/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;


import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { InserisciPersonaFisicaComponent } from '../../../shared/components/inserisci-persona-fisica/inserisci-persona-fisica.component';
import { InserisciPersonaGiuridicaComponent } from '../../../shared/components/inserisci-persona-giuridica/inserisci-persona-giuridica.component';
import { MudeopenFoBeService, UserSelectionType } from 'mudeopen-common';



@Component({
  selector: 'ricerca-inserisci-persona',
  templateUrl: './ricerca-inserisci-persona.component.html',
  styleUrls: ['./ricerca-inserisci-persona.component.css']
})
export class RicercaInserisciPersonaComponent implements OnInit {

  @Output() closeEvent: EventEmitter<any> = new EventEmitter<any>();
  @Output() selected: EventEmitter<any> = new EventEmitter<any>();
  @Input() notitle: boolean = false;
  @Input() noclose: boolean = false;
  @Input() type: string = 'PG';
  @Input() askForReplace: boolean = false;
  @Input() askForReplaceChk: boolean = false;
  @Input() excludeIDs: string = null;
  @Input() userSelectionType: UserSelectionType = UserSelectionType.CONTACTS;
  @Input('selectionType') selectionType: string = 'single'; // 'single', 'checkbox', 'multi'
  @Input('label') label: string = null;
  @Input('title') title: string = null;

  constructor(private modalService: NgbModal,
    private mudeopenFoBeService: MudeopenFoBeService) {}


	/* 
	* nome metodo "ngOnInit"; method description: 
	* @param ()
	* @returns void
	*/ 

  ngOnInit(): void {
    if(!this.label) {
      this.label = this.type === 'PG'?
        "Seleziona il soggetto giuridico dalla ricerca, oppure inserirne uno nuovo." :
        "Seleziona la persona fisica dalla ricerca, oppure inserirne uno nuovo."; }

    if(!this.title) {
      this.title = this.type === 'PG'?
        "Ricerca soggetto giuridico" :
        "Ricerca persona fisica"; }
    }


	/* 
	* nome metodo "onItemSelected"; method description: 
	* @param (soggetto: any)
	* @returns void
	*/ 

  onItemSelected(soggetto: any): void {
    soggetto.replaceInFolder = this.askForReplaceChk;
    this.selected.emit(soggetto);
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
    const modal = this.modalService.open(this.type=='PF'?InserisciPersonaFisicaComponent:InserisciPersonaGiuridicaComponent, { size: 'xl', backdrop: 'static', keyboard: false });
    modal.componentInstance.closeEvent.subscribe(() => { modal.dismiss(); });
    modal.componentInstance.subjectCreated.subscribe(subject => {
        modal.dismiss();
        this.onItemSelected(subject)
    });

  }
  


}