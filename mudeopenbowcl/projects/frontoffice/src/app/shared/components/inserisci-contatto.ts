import { filter } from 'rxjs/operators';
/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { AfterContentInit, ChangeDetectorRef, Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthStoreService, MessageService, RegexUtil } from 'mudeopen-common';
import { AnagraficaVO, ComuneVO, ContattoVO, SelectVO } from 'mudeopen-common';
import { FormUtils } from 'mudeopen-common';
import { MudeopenFoBeService } from 'mudeopen-common';
import * as model from 'mudeopen-common';

@Component({
  selector: 'inserisci-persona',
  templateUrl: './inserisci-contatto.html'
})
export class InserisciContattoComponent extends FormUtils implements OnInit, AfterContentInit {
  contact: model.ContattoVO;

  mainContactForm: FormGroup;
  anagraficaForm: FormGroup;

  isSubjectSaved: boolean;

  titoli: SelectVO[];
  legaleRappresentante: ContattoVO;
  legaleRappresentanteType: String;
  rappresentanteRequired: boolean = false;

  indirizzoForm: FormGroup;
  indirizziFormArray: FormArray;
  tipiIndirizzoForm: FormGroup;

  submitStatus: string;

  comuniIndirizzo: {
    formArrayIndex: number;
    comuni: ComuneVO[];
  }[] = [];
  _tipologieIndirizzo: SelectVO[];

  @Output() closeEvent: EventEmitter<any> = new EventEmitter<any>();
  @Output() subjectCreated: EventEmitter<AnagraficaVO> = new EventEmitter<AnagraficaVO>();
  @Input() noclose: boolean = false;
  @Input() soggettoOnEdit: ContattoVO | any = null;
  @Input() idIstanza: number = null;
  @Input() addressRequired: boolean = false;
  @Input() addressAlwaysRequired: boolean = false;
  @Input() isLoggedUser: boolean = false;


	/* 
	* nome metodo "constructor"; method description: 
	* @param (mudeopenFoBeService: MudeopenFoBeService)
	* @returns 
	*/ 

  constructor(mudeopenFoBeService: MudeopenFoBeService,
                messageService: MessageService,
                private authService: AuthStoreService) {
    super(mudeopenFoBeService, messageService);
    this.disableCountryLoading = false;
  }

  pgAddressTypes: boolean = false;

	/* 
	* nome metodo "ngOnInit"; method description: 
	* @param ()
	* @returns void
	*/ 

  ngOnInit(): void {
    this.mudeopenFoBeService.getListaTipoPresentatore('2').subscribe((_titoli) => this.titoli = _titoli);

    this.indirizziFormArray = new FormArray([]);
    this.tipiIndirizzoForm = new FormGroup({
      id: new FormControl(null, []),
      value: new FormControl()
    });

    if(this.pgAddressTypes) {
      this._tipologieIndirizzo = [{ id: '1', value: 'Sede legale' }, { id: '2', value: 'Sede operativa' }];
    }
    else {
      this._tipologieIndirizzo = [{ id: '2', value: 'Residenza' }, { id: '1', value: 'Domicilio' }, { id: '3', value: 'Studio' }];
    }
  }

  get tipologieIndirizzo(): SelectVO[] {
    return this._tipologieIndirizzo.filter(x => {
      for(var i = 0; this.indirizziFormArray?.controls && i < this.indirizziFormArray.controls.length; i++) {
        if(this.indirizziFormArray.controls[i].value.tipologiaIndirizzo.id == x.id
              // && ((this.pgAddressTypes && x.id == '1' /* 'Sede legale' */) || (!this.pgAddressTypes && x.id == '2' /* 'Residenza' */)) 
            ) {
          return false; } }

      return true;
    });
  }

	/* 
	* nome metodo "initIndirizzoForm"; method description: 
	* @param ()
	* @returns FormGroup
	*/ 

  initIndirizzoForm(): FormGroup {
    return new FormGroup({
      id: new FormControl(),
      cap: new FormControl(null, [Validators.required]),
      localita: new FormControl(),
      numero: new FormControl(null, [Validators.required, Validators.maxLength(30)]),
      duf: new FormControl(null, [Validators.required, Validators.maxLength(100)]),
      telefono: new FormControl(null, [Validators.pattern(RegexUtil.Phone)]),
      cellulare: new FormControl(null, [Validators.pattern(RegexUtil.Phone)]),
      mail: new FormControl(null, [Validators.pattern(RegexUtil.Email)]),
      pec: new FormControl(null, [Validators.pattern(RegexUtil.Email)]),
      comune: new FormGroup({
        codBelfiore: new FormControl(),
        id: new FormControl(null, [Validators.required]),
        value: new FormControl()
      }),
      dug: new FormGroup({
        id: new FormControl(null, [Validators.required]),
        value: new FormControl()
      }),
      provincia: new FormGroup({
        id: new FormControl(null, [Validators.required]),
        value: new FormControl()
      }),
      stato: new FormGroup({
        id: new FormControl(null, [Validators.required]),
        value: new FormControl()
      }),
      tipologiaIndirizzo: new FormGroup({
        id: new FormControl(null, [Validators.required]),
        value: new FormControl()
      }),
      comuneIndirizzoEstero: new FormControl(null, [Validators.required])
    });
  }

	/* 
	* nome metodo "patchForm"; method description: 
	* @param ()
	* @returns void
	*/ 

  patchForm(): void {
    if(this.soggettoOnEdit.legaleRappresentante) {
      this.legaleRappresentante = this.soggettoOnEdit.legaleRappresentante;
      this.legaleRappresentanteType = this.soggettoOnEdit.legaleRappresentante.tipo_contatto;
      this.mainContactForm.get("titoloRappresentanza").setValue( {id: this.soggettoOnEdit.id_titolo_rappresentante, value: null })
    }
    this.checkRappresentanteState();

    // Indirizzi (push).
    this.indirizziFormArray.clear();
    if(this.soggettoOnEdit?.indirizzi && this.soggettoOnEdit?.indirizzi.length) {
      for(const indirizzo of this.soggettoOnEdit.indirizzi) {
        const indirizzoForm = this.initIndirizzoForm();
        this.patchFormValues(indirizzoForm, indirizzo);
        this.indirizziFormArray.push(indirizzoForm);
      }
    }

    // Indirizzi (province e comuni).
    for(var i = 0; i < this.indirizziFormArray.controls.length; i++) {
      const control: any = this.indirizziFormArray.controls[i];
      const idStato: number = control.get('stato').get('id').value;
      
      this.onChangeStatoIndirizzo(idStato, i);
      if(idStato +'' === '1') {
        this.onChangeProvinciaIndirizzo(control.get('provincia').get('id').value, i);
      } else {
        control.get('provincia').disable();
        control.get('comune').disable();
        control.get('dug').disable();
      }
    }

  }

  // Operazioni su form e FormArray.
  isAddressChoice: boolean = false;

	/* 
	* nome metodo "addIndirizzoForm"; method description: 
	* @param ()
	* @returns void
	*/ 

  addIndirizzoForm(): void {
    const addrtype_id = this.tipiIndirizzoForm.value.id;
    if(!addrtype_id) { return; }

    this.isAddressChoice = false;
    this.tipiIndirizzoForm.setValue( { id: null, value: null } );

    this.indirizzoForm = this.initIndirizzoForm();

    this.indirizzoForm.get('tipologiaIndirizzo').setValue( { id: addrtype_id, value: null } );
    this.indirizziFormArray.push(this.indirizzoForm);
  }


	/* 
	* nome metodo "getAddressType"; method description: 
	* @param (id: number)
	* @returns 
	*/ 

  getAddressType(id: string) {
    if(this._tipologieIndirizzo) {
      return this._tipologieIndirizzo.filter((x) => x.id == id)[0].value }
  }


	/* 
	* nome metodo "getComuniIndirizzo"; method description: 
	* @param (formArrayIndex: number)
	* @returns ComuneVO[]
	*/ 

  getComuniIndirizzo(formArrayIndex: number): ComuneVO[] {
    const comuniIndirizzo = this.comuniIndirizzo.filter(x => x.formArrayIndex == formArrayIndex);
    if(comuniIndirizzo?.length) {
      return comuniIndirizzo[comuniIndirizzo.length-1].comuni; }
  }


	/* 
	* nome metodo "removeIndirizzoForm"; method description: 
	* @param (index: number)
	* @returns void
	*/ 

  removeIndirizzoForm(index: number): void {
    this.indirizziFormArray.removeAt(index);
  }


  // Per ogni elemento dell'array di indirizzi.

	/* 
	* nome metodo "onChangeProvinciaIndirizzo"; method description: 
	* @param (idProvincia: number, formArrayIndex: number)
	* @returns 
	*/ 

  onChangeProvinciaIndirizzo(idProvincia: number, formArrayIndex: number) {
    this.mudeopenFoBeService.getComuni(idProvincia.toString()).subscribe(x => {
        const comuneIndirizzo = {
            formArrayIndex: formArrayIndex,
            comuni: x
        };

        this.comuniIndirizzo.push(comuneIndirizzo);
    });
  }

  isItalySelected(indirizziFormArrayIndex: number) {
    let id = (this.indirizziFormArray.at(indirizziFormArrayIndex) as FormGroup).value.stato.id;
    return !id || id == 1;
  }


	/* 
	* nome metodo "onChangeStatoIndirizzo"; method description: 
	* @param (idStato: number, indirizziFormArrayIndex: number)
	* @returns 
	*/ 

  onChangeStatoIndirizzo(idStato: number, indirizziFormArrayIndex: number) {
    if(idStato == 1) { // Italia.
      this.indirizziFormArray.at(indirizziFormArrayIndex).get('provincia').setValidators(Validators.required);
      this.indirizziFormArray.at(indirizziFormArrayIndex).get('comune').setValidators(Validators.required);
      this.indirizziFormArray.at(indirizziFormArrayIndex).get('cap').setValidators([Validators.required, Validators.pattern(RegexUtil.CAP) ]);
      this.indirizziFormArray.at(indirizziFormArrayIndex).get('cap').updateValueAndValidity();

      this.indirizziFormArray.at(indirizziFormArrayIndex).get('provincia').enable();
      this.indirizziFormArray.at(indirizziFormArrayIndex).get('comune').enable();
      this.indirizziFormArray.at(indirizziFormArrayIndex).get('dug').enable();

      this.indirizziFormArray.at(indirizziFormArrayIndex).get('comuneIndirizzoEstero').disable();
    } else { // Estero.
      this.indirizziFormArray.at(indirizziFormArrayIndex).get('cap').setValidators(Validators.required);
      this.indirizziFormArray.at(indirizziFormArrayIndex).get('provincia').disable();
      this.indirizziFormArray.at(indirizziFormArrayIndex).get('comune').disable();
      this.indirizziFormArray.at(indirizziFormArrayIndex).get('dug').disable();
      this.indirizziFormArray.at(indirizziFormArrayIndex).get('provincia').setValue({ id: '', value: '' }, {onlySelf: true});
      this.indirizziFormArray.at(indirizziFormArrayIndex).get('comune').setValue({ id: '', codBelfiore: '', value: '' }, {onlySelf: true});
      this.indirizziFormArray.at(indirizziFormArrayIndex).get('dug').setValue({ id: '', value: '' }, {onlySelf: true});

      this.indirizziFormArray.at(indirizziFormArrayIndex).get('comuneIndirizzoEstero').enable();
    }
  }


	/* 
	* nome metodo "onClose"; method description: 
	* @param ()
	* @returns void
	*/ 

  onClose(): void {
    if(!this.isSubjectSaved) {
      this.closeEvent.emit(); }
  }
  
	/* 
	* nome metodo "isValidAddress"; method description: 
	* @param ()
	* @returns 
	*/ 

  isValidAddress() {
    this.addressRequired = !this.indirizziFormArray.length;
    return !this.addressRequired;
  }

  isValidAddressRequired() {
    this.addressAlwaysRequired = this.getProperty('INDIRIZZO_SOGGETTO_NECESSARIO') == 'abilitato' && !this.indirizziFormArray.length;
    return !this.addressAlwaysRequired;
  }

  setRappresentanteLegale(contact: model.ContattoVO, contactType: string) {
    this.legaleRappresentante = contact; 
    this.legaleRappresentanteType = contactType;
    this.checkRappresentanteState();
  }

  deleteRappresentante() {
    this.legaleRappresentante = null;
    this.checkRappresentanteState();
  }

  checkRappresentanteState() {
    this.mainContactForm.get('titoloRappresentanza').get('id')[!!this.legaleRappresentante? 'enable' : 'disable']();
  }

  isItaly() {
    return this.anagraficaForm.value.statoMembro.id 
              && this.anagraficaForm.value.statoMembro.id == 1;
  }

  public onSubmit() {
    if((this.submitStatus || 'submitted') != 'submitted') {
      this.submitStatus = 'submitted'; }
    else if(this._validate()) {
      if(!this.soggettoOnEdit || this.soggettoOnEdit.id_user == this.authService.getUser().id) {
        this._doSubmit(); }
      else {
        this.confirmDialog("I dati fanno parte della rubrica di un'altro utente. Vuoi copiare i dati nella tua rubrica?", () => {
          this._doSubmit();
        }, 'COPIA');
      }
    }
  }

  _doSubmit() {
    if(this.submitStatus == undefined) { this.submitStatus = null; } // SQ CRAP!
  }

  public _validate(): boolean { 
    return true; 
  }

}