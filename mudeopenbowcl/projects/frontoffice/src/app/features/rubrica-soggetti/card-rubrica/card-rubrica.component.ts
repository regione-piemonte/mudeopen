/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalDialogComponent } from 'mudeopen-common';
import { MudeopenFoBeService } from 'mudeopen-common';
import { MessageService } from 'mudeopen-common';
import { CodTipoMessEnum } from 'mudeopen-common';
import { InserisciPersonaFisicaComponent, InserisciPersonaGiuridicaComponent } from '../../../shared/components';

@Component({
  selector: 'card-rubrica',
  templateUrl: './card-rubrica.component.html',
  styleUrls: ['./card-rubrica.component.css']
})
export class CardRubricaComponent {
  @Input() isPersonaFisica: boolean;
  @Input() soggetto: any;
  @Output() deleteEvent: EventEmitter<any> = new EventEmitter<any>();
  @Output() editEvent: EventEmitter<any> = new EventEmitter<any>();

  constructor(private mudeopenFoBeService: MudeopenFoBeService, 
    private ms: MessageService,
    private modalService: NgbModal) {}


  onDelete() { // segnalibro
    const modal = this.modalService.open(ModalDialogComponent);
    modal.componentInstance.text = "Sei sicuro di voler eliminare il soggetto?"
    modal.componentInstance.title = "Eliminazione soggetto"

    modal.componentInstance.cancelEvent.subscribe(() => modal.dismiss());
    if(this.isPersonaFisica) {
      modal.componentInstance.confirmEvent.subscribe(() => {
        this.mudeopenFoBeService.deleteSoggettoPersonaFisica(this.soggetto.anagrafica.codiceFiscale).subscribe(x => {
          this.handleDelete(x, modal);
        },
        (err) => { this.handleDelete("generic error", modal); });
      });
    }
    else {
      modal.componentInstance.confirmEvent.subscribe(x2 => {
        this.mudeopenFoBeService.deleteSoggettoPersonaGiuridica(this.soggetto.anagrafica.partitaIva || this.soggetto.anagrafica.partitaIvaComunitaria).subscribe(x => {
          this.handleDelete(x, modal);
        },
        (err) => { this.handleDelete("generic error", modal); });
      });
    }
  }


	/* 
	* nome metodo "handleDelete"; method description: 
	* @param (x, modal)
	* @returns 
	*/ 

  handleDelete(x, modal) {
    if(x == null) {
      this.ms.showMessageBox(CodTipoMessEnum.P, "Soggetto eliminato correttamente.", null, 5000); }
    else {
      this.ms.showMessageBox(CodTipoMessEnum.E, "Errore nell'eliminazione: soggetto già associato ad un'istanza."); }
    
    modal.dismiss();
    this.deleteEvent.emit();
  }


	/* 
	* nome metodo "onEditPersonaFisica"; method description: 
	* @param (soggetto: any)
	* @returns 
	*/ 

  onEditPersonaFisica(soggetto: any) {
    const modal = this.modalService.open(this.isPersonaFisica?InserisciPersonaFisicaComponent:InserisciPersonaGiuridicaComponent, { size: 'xl', backdrop: 'static', keyboard: false });
    modal.componentInstance.soggettoOnEdit = soggetto;
    
    modal.componentInstance.closeEvent.subscribe(() => {
      this.editEvent.emit();
      modal.dismiss();  
    });
    modal.componentInstance.subjectCreated.subscribe((x) => {
        this.editEvent.emit();
        modal.dismiss();
    });
  }

  get nome(): string {
    return this.soggetto.anagrafica && (this.soggetto.anagrafica.ragioneSociale 
          || (this.soggetto.anagrafica.nome + ' ' + this.soggetto.anagrafica.cognome));
  }

  get isMale(): boolean {
    return this.soggetto.anagrafica && (!this.soggetto.anagrafica.sesso 
          || this.soggetto.anagrafica.sesso +'' === '1');
  }

  get fiscalData(): string {
     return (/*j586: this.isPersonaFisica && */this.soggetto.anagrafica.codiceFiscale) 
          || this.soggetto.anagrafica?.partitaIva 
          || this.soggetto.anagrafica.partitaIvaComunitaria;
  }

  get phone(): string {
    return this.soggetto.anagrafica?.telefono 
            || (this.soggetto.indirizzi?.length && this.soggetto.indirizzi[0].telefono);
  }
  
  get mail(): string {
    return this.soggetto.anagrafica?.mail 
        || (this.soggetto.indirizzi?.length && this.soggetto.indirizzi[0].mail);
  }

  get pec(): string {
    return this.soggetto.anagrafica?.pec
        || (this.soggetto.indirizzi?.length && this.soggetto.indirizzi[0].pec);
  }

  
}