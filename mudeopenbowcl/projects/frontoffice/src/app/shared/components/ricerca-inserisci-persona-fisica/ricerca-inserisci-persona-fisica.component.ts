/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ContattoVO } from 'mudeopen-common';

@Component({
  selector: 'ricerca-inserisci-persona-fisica',
  templateUrl: './ricerca-inserisci-persona-fisica.component.html',
  styleUrls: ['./ricerca-inserisci-persona-fisica.component.css']
})
export class RicercaInserisciPersonaFisicaComponent {
  
  @Input() excludeIDs: string = null;

  @Output() subjectCreated: EventEmitter<ContattoVO> = new EventEmitter<ContattoVO>();
  @Output() itemSelectedEvent: EventEmitter<any> = new EventEmitter<any>();

  isSubjectNotFound: boolean;
  

	/* 
	* nome metodo "onFilledSoggettoEvent"; method description: 
	* @param (soggetto: ContattoVO)
	* @returns void
	*/ 

  onFilledSoggettoEvent(soggetto: ContattoVO): void {
    this.subjectCreated.emit(soggetto);
  }


	/* 
	* nome metodo "onItemSelected"; method description: 
	* @param (soggetto: ContattoVO)
	* @returns void
	*/ 

  onItemSelected(soggetto: ContattoVO): void {
    this.itemSelectedEvent.emit(soggetto);
  }

}