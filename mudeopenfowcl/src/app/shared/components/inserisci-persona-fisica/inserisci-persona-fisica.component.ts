/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { AfterContentInit, Component, Input, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthStoreService, MessageService, RegexUtil } from 'mudeopen-common';
import { MudeopenFoBeService } from 'mudeopen-common';
import { InserisciContattoComponent } from '../inserisci-contatto';

import * as model from 'mudeopen-common';
import { UserSelectionType } from 'mudeopen-common';


@Component({
  selector: 'inserisci-persona-fisica',
  templateUrl: './inserisci-persona-fisica.component.html',
  styleUrls: ['./inserisci-persona-fisica.component.css']
})
export class InserisciPersonaFisicaComponent extends InserisciContattoComponent implements OnInit, AfterContentInit {

  @Input('title') title: string;

  qualificaProfessionaleForm: FormGroup;

  comuniNascita: model.ComuneVO[];
  comuniIndirizzo: {
    formArrayIndex: number;
    comuni: model.ComuneVO[];
  }[] = [];
  ordiniProfessionali: model.SelectVO[];
  //tipologieIndirizzo: model.SelectVO[];
  tipologieQualificaProfessionale: model.SelectVO[];

  maxBirthDate: any;


	/* 
	* nome metodo "constructor"; method description: 
	* @param (mudeopenFoBeService: MudeopenFoBeService)
	* @returns 
	*/ 

  constructor(mudeopenFoBeService: MudeopenFoBeService, messageService: MessageService, authService: AuthStoreService) {
    super(mudeopenFoBeService, messageService, authService);

    const date = new Date();
    this.maxBirthDate = { year: date.getUTCFullYear() /*-18*/, month: date.getUTCMonth(), day: date.getUTCDate() };
  }



	/* 
	* nome metodo "ngOnInit"; method description: 
	* @param ()
	* @returns void
	*/ 

  qualificaCollegi: model.DizionarioVO;
  ngOnInit(): void {
    super.ngOnInit();

    this.mainContactForm = new FormGroup({
      anagrafica: this.initAnagraficamainContactForm(),
      tipiIndirizzo: this.tipiIndirizzoForm,
      indirizzi: this.indirizziFormArray,
      titoloRappresentanza: new FormGroup({ id: new FormControl(null, [Validators.required]), value: new FormControl() }),

      tipiQualificaProfessionale: new FormGroup({
        id: new FormControl(null, []),
        value: new FormControl()
      }),
      qualificheProfessionali: new FormArray([]),

      titolare: new FormControl(),
      impresa_lavori: new FormControl(),
      professionista: new FormControl(),
    });

    this.mudeopenFoBeService.filtraListaDizionario("qualifica_collegio", null).subscribe(collegi => {
      this.qualificaCollegi = collegi;

      setTimeout(_ => {
        this.mainContactForm.valueChanges.subscribe(selectedValue  => {
          if(this.soggettoOnEdit) {
            const data = this.mainContactForm.value;
            this.soggettoOnEdit.indirizzi = data.indirizzi;
            this.soggettoOnEdit.qualificheProfessionali = data.qualificheProfessionali;
          }
        })    
      });
    });

    this.anagraficaForm = this.mainContactForm.get('anagrafica') as FormGroup;

    this.mudeopenFoBeService.getSessionStorageInfoProfilo().then(x => {
      const ordineAltro = x.ordineList.filter((ordine) => ordine.id == 'NDF');
      if(!ordineAltro?.length)
        this.ordiniProfessionali = x.ordineList;
      else
        this.ordiniProfessionali = [ ...(x.ordineList.filter((ordine) => ordine.id != 'NDF')), ...(ordineAltro) ];

      const qualifAltro = x.qualificaList.filter((qualif) => qualif.id == '1');
      if(!qualifAltro?.length)
        this.tipologieQualificaProfessionale = x.qualificaList;
      else
        this.tipologieQualificaProfessionale = [ ...(x.qualificaList.filter((qualif) => qualif.id != '1')), ...(qualifAltro) ];
    });

    if($$.debug >= 1 && !this.soggettoOnEdit) { this.soggettoOnEdit = this['object2debug'+$$.debug]; }
    if(this.soggettoOnEdit) {
      this.patchForm(); }
    else {
      this.onChangeStatoNascita(); }

    this.checkRappresentanteState();
  }

  ngAfterContentInit() {
    super.ngAfterContentInit();

    if(this.isLoggedUser) {
      // disabled "accreditamento" fields 
      this.anagraficaForm.get('nome').disable();
      this.anagraficaForm.get('cognome').disable();
      this.anagraficaForm.get('codiceFiscale').disable();
    }
  }

  

	/* 
	* nome metodo "initAnagraficamainContactForm"; method description: 
	* @param ()
	* @returns FormGroup
	*/ 

  initAnagraficamainContactForm(): FormGroup {
    let anFields = {
      nome: new FormControl(null, [Validators.required, Validators.maxLength(100)]),
      cognome: new FormControl(null, [Validators.required, Validators.maxLength(100)]),
      codiceFiscale: new FormControl(null, [Validators.required, Validators.minLength(2), Validators.pattern(RegexUtil.CF)]),
      telefono: new FormControl(null, [Validators.required, Validators.pattern(RegexUtil.Phone), Validators.maxLength(20)]),
      cellulare: new FormControl(null, [Validators.pattern(RegexUtil.Phone), Validators.maxLength(20)]),
      mail: new FormControl(null, [Validators.pattern(RegexUtil.Email), Validators.maxLength(200)]),
      pec: new FormControl(null, [Validators.pattern(RegexUtil.Email), Validators.maxLength(200)]),
      statoEstero: new FormControl(null, [Validators.required]),
      dataNascita: new FormControl(null, [Validators.required]),
      sesso: new FormControl(null, [Validators.required]),
      comuneNascita: new FormGroup({
        codBelfiore: new FormControl(),
        id: new FormControl(null, [Validators.required]),
        value: new FormControl()
      }),
      provinciaNascita: new FormGroup({
        id: new FormControl(null, [Validators.required]),
        value: new FormControl()
      }),
      statoNascita: new FormGroup({
        id: new FormControl(null, [Validators.required]),
        value: new FormControl()
      }),

      partitaIva: new FormControl(null, [Validators.pattern(RegexUtil.PIVA)]),
      partitaIvaComunitaria: new FormControl(null, [Validators.pattern(RegexUtil.PIVA_COMUNITARIA)]),
      statoMembro: new FormGroup({ id: new FormControl(), value: new FormControl() }),
    };

    if(this.isLoggedUser)
      anFields['mail'] = new FormControl(null, [Validators.pattern(RegexUtil.Email), Validators.maxLength(200), Validators.required]);

    return new FormGroup(anFields);
  }

  fiscalcodeduplicated: boolean = false;
  checkIfCFExists(cf) {
    this.fiscalcodeduplicated = false;
    if(cf && this.isContactKeyStilChangeable && this.soggettoOnEdit?.anagrafica?.codiceFiscale != cf) {
      this.submitStatus = 'validationInProgress';

      setTimeout(_ => {
        this.mudeopenFoBeService.ricercaPersonaFisicaByFilter({ "cf": { "eq": cf }, "searchScope": { "eq": UserSelectionType.CONTACTS_SELF_INCLUDED } }).subscribe(res => {
          this.fiscalcodeduplicated = !!res.body.length;
          if(!this.fiscalcodeduplicated && this.submitStatus == 'submitted') {
            this.onSubmit();    }

          this.submitStatus = null;
        });
      }, 500);
    }
  }

  get statiComnunitariEsteri(): model.NazioneVO[] {
    return this.stati;
  }

	/* 
	* nome metodo "initQualificaProfessionaleForm"; method description: 
	* @param ()
	* @returns FormGroup
	*/ 

  initQualificaProfessionaleForm(jobtype_id): FormGroup {
    this.qualificaProfessionaleForm = new FormGroup({
      id: new FormControl(),
      tipologiaQualificaProfessionale: new FormGroup({
        id: new FormControl(null, [Validators.required]),
        value: new FormControl()
      }),
      provincia: new FormGroup({
        id: new FormControl(null, [Validators.required]),
        value: new FormControl()
      }),
      numero_iscrizione_ordine: new FormControl(null, [Validators.required]),
      ordineProfessionale: new FormGroup({
        id: new FormControl(null, [Validators.required]),
        value: new FormControl()
      }),
      specifica_qualifica: new FormControl(null, [Validators.required]),
      
      no_obbligo_iscrizione_ordine: new FormControl(),
      motivazione: new FormControl(null, [Validators.required, Validators.maxLength(500)])
    });

    this.qualificaProfessionaleForm.get('specifica_qualifica')[jobtype_id == 1 /* ALTRO */?'enable':'disable']();
    this.checkObbligoIscrizione(this.qualificaProfessionaleForm);

    return this.qualificaProfessionaleForm;
  }

  // Modifica della form.
  isContactKeyStilChangeable: boolean = true;
  isPivaChangeable: boolean = true;
  
	/* 
	* nome metodo "patchForm"; method description: 
	* @param ()
	* @returns void
	*/ 

  patchForm(): void {
    this.isContactKeyStilChangeable = this.soggettoOnEdit.updatable === false? false : true;
    
    // Anagrafica.
    if(this.soggettoOnEdit.anagrafica) {
      const idStatoNascita: number = this.soggettoOnEdit.anagrafica.statoNascita?.id || 0;
      if(idStatoNascita +'' === '1' && this.soggettoOnEdit.anagrafica.provinciaNascita) {
          this.onChangeProvinciaNascita(this.soggettoOnEdit.anagrafica.provinciaNascita.id); }
      if(idStatoNascita) {
        this.onChangeStatoNascita(idStatoNascita); }
      
      this.patchFormValues(this.mainContactForm, this.soggettoOnEdit);
    }
    super.patchForm();

    // Qualifiche professionali.
    (this.mainContactForm.get('qualificheProfessionali') as FormArray).clear();
    if(this.soggettoOnEdit.qualificheProfessionali && this.soggettoOnEdit.qualificheProfessionali.length) {
      for(const qualificaProfessionale of this.soggettoOnEdit.qualificheProfessionali) {
        const qualificaProfessionaleForm = this.initQualificaProfessionaleForm(qualificaProfessionale.tipologiaQualificaProfessionale.id);
        this.patchFormValues(qualificaProfessionaleForm, qualificaProfessionale);
        (this.mainContactForm.get('qualificheProfessionali') as FormArray).push(qualificaProfessionaleForm);

        this.checkObbligoIscrizione(qualificaProfessionaleForm);
      }
    }
  }

  // Operazioni su form e FormArray.

  isJobChoice: boolean = false;

	/* 
	* nome metodo "addQualificaProfessionaleForm"; method description: 
	* @param ()
	* @returns void
	*/ 

  addQualificaProfessionaleForm(): void {
    const jobtype_id = this.mainContactForm.value.tipiQualificaProfessionale.id;
    if(!jobtype_id) { return; }

    this.isJobChoice = false;
    this.mainContactForm.get('tipiQualificaProfessionale').setValue( { id: null, value: null } );

    this.qualificaProfessionaleForm = this.initQualificaProfessionaleForm(jobtype_id);
    this.qualificaProfessionaleForm.get('tipologiaQualificaProfessionale').setValue( { id: jobtype_id, value: null } );
    this.qualificheProfessionaliFormArray.push(this.qualificaProfessionaleForm);
  }

	/* 
	* nome metodo "getJobType"; method description: 
	* @param (id: number)
	* @returns 
	*/ 

  getJobType(id: string) {
    if(this.tipologieQualificaProfessionale) {
      return this.tipologieQualificaProfessionale.filter((x) => x.id == id)[0].value }
  }


	/* 
	* nome metodo "removeQualificaProfessionaleForm"; method description: 
	* @param (index: number)
	* @returns void
	*/ 

  removeQualificaProfessionaleForm(index: number): void {
    this.qualificheProfessionaliFormArray.removeAt(index);
  }

  get qualificheProfessionaliFormArray(): FormArray {
    return this.mainContactForm.get('qualificheProfessionali') as FormArray;
  }

  checkObbligoIscrizione(qualificaProfessionaleForm: FormGroup) {
    const isNoAlbo: boolean = qualificaProfessionaleForm.get('no_obbligo_iscrizione_ordine').value;
    qualificaProfessionaleForm.get('motivazione')[isNoAlbo?'enable':'disable']();
    
    qualificaProfessionaleForm.get('numero_iscrizione_ordine')[!isNoAlbo?'enable':'disable']();
    qualificaProfessionaleForm.get('provincia')[!isNoAlbo?'enable':'disable']();
    qualificaProfessionaleForm.get('ordineProfessionale')[!isNoAlbo?'enable':'disable']();
  }

	/* 
	* nome metodo "onChangeProvinciaNascita"; method description: 
	* @param (idProvincia: number)
	* @returns 
	*/ 

  onChangeProvinciaNascita(idProvincia: number) {
      this.mudeopenFoBeService.getComuni(idProvincia.toString(), true).subscribe(x => this.comuniNascita = x);
  }


	/* 
	* nome metodo "onChangeStatoNascita"; method description: 
	* @param (idStato: number = null)
	* @returns 
	*/ 

  onChangeStatoNascita(idStato: number = null) {
    if(!idStato) {
      this.anagraficaForm.get('provinciaNascita').disable();
      this.anagraficaForm.get('comuneNascita').disable();
      this.anagraficaForm.get('statoEstero').disable();

      if(this.mainContactForm.get('provinciaNascita')) {
        this.mainContactForm.get('provinciaNascita').setValue({ id: '', value: '' }, {onlySelf: true}); }
      if(this.mainContactForm.get('comuneNascita')) {
        this.mainContactForm.get('comuneNascita').setValue({ id: '', codBelfiore: '', value: '' }, {onlySelf: true}); }
    }
    else if(idStato +'' === '1') { // Italia.
      this.anagraficaForm.get('provinciaNascita').setValidators(Validators.required);
      this.anagraficaForm.get('comuneNascita').setValidators(Validators.required);

      this.anagraficaForm.get('provinciaNascita').enable();
      this.anagraficaForm.get('comuneNascita').enable();
      this.anagraficaForm.get('statoEstero').disable();
    } else { // Estero.
      this.anagraficaForm.get('statoEstero').enable();
      this.anagraficaForm.get('statoEstero').setValue('');
      this.anagraficaForm.get('provinciaNascita').disable();
      this.anagraficaForm.get('comuneNascita').disable();
      this.anagraficaForm.get('provinciaNascita').setValue({ id: '', value: '' }, {onlySelf: true});
      this.anagraficaForm.get('comuneNascita').setValue({ id: '', codBelfiore: '', value: '' }, {onlySelf: true});
    }

    this.anagraficaForm.get('statoEstero').setValue('');
  }

  public getContact(): model.ContattoVO {
    const data = Object.assign({}, this.soggettoOnEdit, this.mainContactForm.value);
    data.anagrafica = Object.assign({}, this.soggettoOnEdit?.anagrafica || {}, this.anagraficaForm.value);

    if(this.legaleRappresentante)
      delete (this.legaleRappresentante as any)._cfPIVA;
      
    data.legaleRappresentante = this.legaleRappresentante;
    data.id_titolo_rappresentante = this.mainContactForm.value.titoloRappresentanza.id;

    delete data.tipiIndirizzo;
    delete data.titoloRappresentanza;
    delete data.tipiQualificaProfessionale;

    return data;
  }

  pivaSelected() {
    this.anagraficaForm.get('partitaIva').setValue('');
    this.anagraficaForm.get('partitaIvaComunitaria').setValue('');
  }

	/* 
	* nome metodo "goOn"; method description: 
	* @param ()
	* @returns 
	*/ 

  goOn() {
    this.subjectCreated.emit(this.contact);
  }

  public _validate(): boolean {
    if(!this.anagraficaForm.value.partitaIva && !this.anagraficaForm.value.partitaIvaComunitaria) {
      this.anagraficaForm.get('statoMembro').setValue( {id: null, value: null }); }

    return super._validate() && !this.fiscalcodeduplicated && !(this.mainContactForm.invalid && this.commonUtils.validateForm(this.mainContactForm)) 
                && this.isValidAddressRequired()
                && (this.anagraficaForm.value.pec?.length || this.isValidAddress());
  }

	/* 
	* nome metodo "onSubmit"; method description: 
	* @param ()
	* @returns 
	*/ 

  _doSubmit() {
    super._doSubmit();

    this.mudeopenFoBeService.insertSoggettoPersonaFisica(this.getContact(), this.idIstanza).subscribe(res => {
      this.contact = res.body;
      this.isSubjectSaved = res.status.toString().startsWith("2");
    });
  }

  _legaleRappresentanteReal: any = null;
  _legaleRappresentanteEx: any = null;
  get _legaleRappresentante() {
    if(this._legaleRappresentanteReal == this.legaleRappresentante)
      return this._legaleRappresentanteEx;

    this._legaleRappresentanteReal = this.legaleRappresentante;
    const sqc = this._legaleRappresentanteEx = Object.assign({ _cfPIVA: this.legaleRappresentante.anagrafica.codiceFiscale || this.legaleRappresentante.anagrafica.partitaIva}, this.legaleRappresentante);
    return sqc;
  }  
}