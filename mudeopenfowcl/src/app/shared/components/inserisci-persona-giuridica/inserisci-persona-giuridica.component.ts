/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { AfterContentInit, Component, ElementRef, OnInit, ViewChild, Input } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthStoreService, MessageService, RegexUtil } from 'mudeopen-common';
import { MudeopenFoBeService } from 'mudeopen-common';
import { InserisciContattoComponent } from '../inserisci-contatto';

import * as model from 'mudeopen-common';

@Component({
  selector: 'inserisci-persona-giuridica',
  templateUrl: './inserisci-persona-giuridica.component.html',
  styleUrls: ['./inserisci-persona-giuridica.component.css']
})
export class InserisciPersonaGiuridicaComponent extends InserisciContattoComponent implements OnInit, AfterContentInit {

  @Input('title') title: string;

  object2debug1 = null;

	/* 
	* nome metodo "constructor"; method description: 
	* @param (mudeopenFoBeService: MudeopenFoBeService)
	* @returns 
	*/ 

  constructor(mudeopenFoBeService: MudeopenFoBeService, messageService: MessageService, authService: AuthStoreService) {
    super(mudeopenFoBeService, messageService, authService);

    this.pgAddressTypes = true;

    this.mudeopenFoBeService.getStati(true).subscribe(x => {
      this.statiUEWithoutItalia = x && x.filter((stato) => stato.id != 1); 
    });
  }

	/* 
	* nome metodo "ngOnInit"; method description: 
	* @param ()
	* @returns void
	*/ 

  ngOnInit(): void {
    super.ngOnInit();

    this.initForms();

    //if($$.debug >= 1) { this.soggettoOnEdit = this['object2debug'+$$.debug]; }

    if(this.soggettoOnEdit) {
  		this.patchForm();  }

    this.checkRappresentanteState();
  }


	/* 
	* nome metodo "initForms"; method description: 
	* @param ()
	* @returns void
	*/ 

  initForms(): void {
    this.anagraficaForm = new FormGroup({
      ragioneSociale: new FormControl(null, [Validators.required, Validators.maxLength(100)]),
      nazionalita: new FormControl(null, [Validators.required]),
      partitaIva: new FormControl(null, [Validators.required, Validators.pattern(RegexUtil.PIVA)]),
      partitaIvaComunitaria: new FormControl(null, [Validators.required, Validators.pattern(RegexUtil.PIVA_COMUNITARIA)]),
      statoMembro: new FormGroup({ id: new FormControl(null, [Validators.required]), value: new FormControl() }),
      //statoEstero: new FormControl(null, [Validators.required]),

      codiceFiscale: new FormControl(null, [Validators.minLength(2), Validators.pattern(RegexUtil.CF)]),
      telefono: new FormControl(null, [Validators.required, Validators.pattern(RegexUtil.Phone), Validators.maxLength(20)]),
      cellulare: new FormControl(null, [Validators.pattern(RegexUtil.Phone), Validators.maxLength(20)]),
      mail: new FormControl(null, [Validators.pattern(RegexUtil.Email), Validators.maxLength(200)]),
      pec: new FormControl(null, [Validators.required, Validators.pattern(RegexUtil.Email), Validators.maxLength(200)])
   });

    this.mainContactForm = new FormGroup({
      anagrafica: this.anagraficaForm,
      tipiIndirizzo: this.tipiIndirizzoForm,
      indirizzi: this.indirizziFormArray,
      titoloRappresentanza: new FormGroup({ id: new FormControl(null, [Validators.required]), value: new FormControl() }),

      titolare: new FormControl(),
      impresa_lavori: new FormControl(),
      professionista: new FormControl(),

      //no_obbligo_iscrizione_ordine: new FormControl(),
      //motivazione: new FormControl(),
    });
  
    if(this.soggettoOnEdit) {
      this.patchForm(); }
    else {
      // Finché non si selezionano stato e nazionalità, questi controlli vanno disabilitati.
      //this.anagraficaForm.get('statoEstero').disable();
      this.anagraficaForm.get('partitaIva').disable();
      this.anagraficaForm.get('partitaIvaComunitaria').disable();
      this.anagraficaForm.get('statoMembro').disable();
    }
  }
  
  // Modifica della form.
  isContactKeyStilChangeable: boolean = true;

	/* 
	* nome metodo "patchForm"; method description: 
	* @param ()
	* @returns void
	*/ 

  patchForm(): void {
    this.isContactKeyStilChangeable = this.soggettoOnEdit.updatable === false? false : true;

    this.onChangeNazionalita(this.soggettoOnEdit.anagrafica.nazionalita);
    //this.patchFormValues(this.anagraficaForm, this.soggettoOnEdit.anagrafica);
    this.patchFormValues(this.mainContactForm, this.soggettoOnEdit);

    super.patchForm();
  }


	/* 
	* nome metodo "onChangeNazionalita"; method description: 
	* @param (idNazionalita: number)
	* @returns 
	*/ 

  onChangeNazionalita(idNazionalita: number) {
    if(idNazionalita  == 1) { // Italiana.
      this.anagraficaForm.get('partitaIva').enable();
      this.anagraficaForm.get('partitaIvaComunitaria').disable();
      this.anagraficaForm.get('statoMembro').disable();
      //this.anagraficaForm.get('statoEstero').disable();

      this.anagraficaForm.get('partitaIvaComunitaria').setValue('');
      this.anagraficaForm.get('statoMembro').setValue( {id: null, value: null });
      //this.anagraficaForm.get('statoEstero').setValue('');
    }
    else { // Estera.
      //this.anagraficaForm.get('statoEstero').enable();
      this.anagraficaForm.get('partitaIvaComunitaria').enable();
      this.anagraficaForm.get('partitaIva').disable();
      this.anagraficaForm.get('statoMembro').enable();
      this.anagraficaForm.get('partitaIva').setValue('');
      this.anagraficaForm.get('statoMembro').setValue( {id: null, value: null });
    }
  }

  vatcodeduplicated: boolean = false;
  checkIfPivaExists(field, iva) {
    if(iva && this.isContactKeyStilChangeable 
          && (this.soggettoOnEdit?.anagrafica?.partitaIva || this.soggettoOnEdit?.anagrafica?.partitaIvaComunitaria) != iva) {
      this.submitStatus = 'validationInProgress';

      setTimeout(_ => {
        this.mudeopenFoBeService.ricercaPersonaGiuridica('', (field == 'partitaIva' && iva) || null, (field == 'partitaIvaComunitaria' && iva) || null).subscribe(res => {
          if(!(this.vatcodeduplicated = !!res.body.length) && this.submitStatus == 'submitted') {
            this.onSubmit();    }

          this.submitStatus = null;
        });
      }, 500);
    }
  }

	/* 
	* nome metodo "goOn"; method description: 
	* @param ()
	* @returns 
	*/ 

  goOn() {
    this.subjectCreated.emit(this.contact);
  }

  statiUEWithoutItalia: model.NazioneVO[];
  get statiComnunitariEsteri(): model.NazioneVO[] {
    return this.anagraficaForm.value.nazionalita==2?this.statiUEWithoutItalia:this.statiWithoutItalia;
  }

  public _validate(): boolean {
    return super._validate() && !(this.mainContactForm.invalid && this.commonUtils.validateForm(this.mainContactForm)) 
                && this.isValidAddressRequired()
                && (this.anagraficaForm.value.pec?.length || this.isValidAddress())
                && !(this.rappresentanteRequired = !this.legaleRappresentante);
  }

	/* 
	* nome metodo "onSubmit"; method description: 
	* @param ()
	* @returns 
	*/ 

  _doSubmit() {
    super._doSubmit();

    const data = Object.assign({}, this.soggettoOnEdit, this.mainContactForm.value);
    data.anagrafica = Object.assign({}, this.soggettoOnEdit?.anagrafica || {}, this.anagraficaForm.value);
    
    if(this.anagraficaForm.value.nazionalita==1 || this.soggettoOnEdit?.anagrafica?.nazionalita == 1)
      data.anagrafica.partitaIva = this.anagraficaForm.value.partitaIva || this.soggettoOnEdit?.anagrafica?.partitaIva;
    if(this.anagraficaForm.value.nazionalita==2 || this.soggettoOnEdit?.anagrafica?.nazionalita == 2)
      data.anagrafica.partitaIvaComunitaria = this.anagraficaForm.value.partitaIvaComunitaria || this.soggettoOnEdit?.anagrafica?.partitaIvaComunitaria;

    data.legaleRappresentante = this.legaleRappresentante;
    data.id_titolo_rappresentante = this.mainContactForm.value.titoloRappresentanza.id;
    delete data.tipiIndirizzo;
    delete data.titoloRappresentanza;
    delete data._cfPIVA;
    
    this.mudeopenFoBeService.insertSoggettoPersonaGiuridica(data, this.idIstanza).subscribe(res => {
      this.contact = res.body;
      this.isSubjectSaved = res.status.toString().startsWith("2");
    });
  }


  _contactReal: any = null;
  _contactEx: any = null;
  get _contact() {
    if(this._contactReal == this.contact)
      return this._contactEx;

      this._contactReal = this.contact;
      return this._contactEx = Object.assign({ _cfPIVA: this.contact.anagrafica.codiceFiscale || this.contact.anagrafica.partitaIva}, this.contact);
  }

  _legaleRappresentanteReal: any = null;
  _legaleRappresentanteEx: any = null;
  get _legaleRappresentante() {
    if(this._legaleRappresentanteReal == this.legaleRappresentante)
      return this._legaleRappresentanteEx;

    this._legaleRappresentanteReal = this.legaleRappresentante;
    return this._legaleRappresentanteEx = Object.assign({ _cfPIVA: this.legaleRappresentante.anagrafica.codiceFiscale || this.legaleRappresentante.anagrafica.partitaIva}, this.legaleRappresentante);
  }

}