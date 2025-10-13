import { filter } from 'rxjs/operators';
/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { Component, Inject, Injector, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { CONFIG } from 'mudeopen-common';
import {
  StepConfig
} from 'mudeopen-common';
import { MudeopenFoBeService, UserSelectionType } from 'mudeopen-common';
import * as model from 'mudeopen-common';
import { OnStepInit, StepComponent } from "../step.component";
import { RouterLinkWithHref } from '@angular/router';





@Component({
  selector: 'soggetto-abilitato',
  templateUrl: './soggetto-abilitato.component.html',
  styleUrls: ['./soggetto-abilitato.component.css']
})
export class SoggettoAbilitatoComponent extends StepComponent implements OnInit, OnStepInit {

  addMoreDeleganti: boolean;
  loggedContactUser: model.ContattoVO;
  titoli: model.SelectVO[];
  ruoliPossibili: any[];
  rolesSelectedFromFolder: any;
  soggettoAbilitatoForm: FormGroup;
  ruoloDelegante: model.RuoloPossibileVO;
  tipologieIstanzaComplete: model.TipoIstanzaVO[];

  constructor(mudeopenFoBeService: MudeopenFoBeService,
        injector: Injector,
        @Inject(CONFIG) injConfig: StepConfig) {
    super(mudeopenFoBeService, injector, injConfig);

    this.mudeopenFoBeService.getTipologieIstanza().then(x => this.tipologieIstanzaComplete = x);    
  }


	/* 
	* nome metodo "ngOnInit"; method description: 
	* @param ()
	* @returns void
	*/ 

  ngOnInit(): void {

    this.soggettoAbilitatoForm = new FormGroup({
      titoloSoggettoAbilitato: new FormGroup({
        id: new FormControl(null, [Validators.required]),
        value: new FormControl()
      })
    });
  }


	/* 
	* nome metodo "canLeaveStep"; method description: 
	* @param ()
	* @returns boolean
	*/ 

  canLeaveStep(): boolean { 
    this.addMoreDeleganti = false;

    return true; 
  }


	/* 
	* nome metodo "onStepLoader"; method description: 
	* @param (jsondata: any, isNew: boolean)
	* @returns 
	*/ 

  lockAgreed: boolean;
  onStepLoader(jsondata: any, isNew: boolean) {
    if(isNew) {
      this._init(); }
    else {
      if(this.stepQuadroData.titoloSoggettoAbilitato) {
        this.soggettoAbilitatoForm.get('titoloSoggettoAbilitato').setValue(this.stepQuadroData.titoloSoggettoAbilitato) }
    }

    setTimeout(_ => {
      forkJoin([
        this.mudeopenFoBeService.getListaTipoPresentatore(this.formsService.istanza.tipologia_presentatore.id),
        this.mudeopenFoBeService.ricercaPersonaFisica(null, null, this.authService.getUser().contatto.anagrafica.codiceFiscale, null, null, null, 0, 10, '', null, UserSelectionType.CONTACTS_SELF_INCLUDED)
      ]).subscribe((results) => {
        this._handleResult(results, isNew);
      });
    }, 100)

    this.lockAgreed = !!jsondata.agreedCheck;
    return false;
  }

  _init() {
    this.stepManagerService.jsondata[this.quadro.tipo_quadro?.cod_tipo_quadro] = this.stepQuadroData = {
      agreedCheck: false,
      idTipoSoggetto: this.formsService.istanza.tipologia_presentatore.id,
      delegatiList: {}
    };

  }

  _handleResult(results, isNew) {
    this.titoli = results[0];
    if(this.titoli && results[0].length === 1) {
      this.stepQuadroData.titoloSoggettoAbilitato = results[0][0]; }

    this.loggedContactUser = results[1].body[0];

    // compile all subject roles to be imported
    this.mudeopenFoBeService.getRuoliIstanza(this.formsService.id_istanza).subscribe((ruoliIstanza: model.RuoliIstanzaResponse) => {
      this.ruoliPossibili = ruoliIstanza.ruoliPossibili;
      this.ruoliPossibili.filter((x) => x.default)[0].checked = true;
      this.ruoloDelegante = ruoliIstanza.ruoliPossibili.filter((x) => !x.visibile)[0];

      // for branches different than 1, don't show 'intestatario'
      if(this.formsService.istanza.branch != 1) {
        this.ruoliPossibili.find((role) => role.id === 'IN' /* INtestatario */).visibile = false; }

      this.rolesSelectedFromFolder = this.loadSubjectFromRoles(ruoliIstanza) || {};
      if(isNew) {
        this.handleNewCase(); }
    });
  }

  handleNewCase() {
    const inheritOwnerFrom = this.formsService.istanza.tipo_istanza?.soggetti_bloccati? 'add_owner_from_parent' : 'add_owner_from_folder';
        
    // checks if it is required to import old IN subject (soggetti_bloccati implies no folder-subjects selection)
    if(inheritOwnerFrom && this.formsService.istanza.branch != 1) {
      // add old IN subject in case it differs from logged user
      this.mudeopenFoBeService.aggiungiSoggettoCoinvolto({ 
        contatto: null,
        idIstanza: this.formsService.id_istanza,
        ruoloSoggetto: this.ruoliPossibili.filter(role => role.id == 'IN').map(x => { return { id: x.id, value: x.value };  }),
        ruoliPossibili: JSON.stringify(this.ruoliPossibili),
        operation: inheritOwnerFrom
      }).subscribe(storedSubjects => {
        if(storedSubjects) {
          this.rolesSelectedFromFolder = this.loadSubjectFromRoles(storedSubjects); }

        this.setPresenters(this.rolesSelectedFromFolder); 
      },
      (err) => {
        // fallback in case of error
        this.stepQuadroData = {}; 
      });
    }
    else {
      // adds default subject based upon branch type selected
      this.setPresenters(this.rolesSelectedFromFolder); 
    }
  }

  get presenterName(): string {
    return this.getSubjectInfo('name', this.formsService.istanza.presentatore.contatto.anagrafica);
  }

	/* 
	* nome metodo "setPresenter"; method description: 
	* @param ()
	* @returns 
	*/ 

  setPresenters(fascicoloSubjects: any) {
    this.stepQuadroData.soggettiEreditati = fascicoloSubjects;
    Object.keys(this.stepQuadroData.soggettiEreditati).forEach(key => {
      fascicoloSubjects[key].roles.filter(x => x.id == 'DE' /* DElegante */ && x.checked).forEach(delegante => {
        this.stepQuadroData.delegatiList[key] = fascicoloSubjects[key];
      });
    });
    
    const legacyRoles = (fascicoloSubjects[this.loggedContactUser.anagrafica.codiceFiscale]?.roles || [])
                              .filter((x) => x.checked).map(x => { return { id: x.id, value: x.value };  });

    this.ruoliPossibili.filter((x) => x.checked).map(x => { return { id: x.id, value: x.value };  }).forEach(x => {
      if(!legacyRoles.find(y => y.id == x.id))
        legacyRoles.push(x);
    });
    this.mudeopenFoBeService.aggiungiSoggettoCoinvolto({
                  contatto: this.loggedContactUser,
                  idIstanza: this.formsService.id_istanza,
                  ruoloSoggetto: legacyRoles,
                  ruoliPossibili: JSON.stringify(this.ruoliPossibili)
                }).subscribe(y => {
        this.storeJsonData(true, this.isStepValidationOK);
    });
  }

  get titoloSoggettoAbilitato(): any {
    if(this.soggettoAbilitatoForm.value.titoloSoggettoAbilitato.id) {
      this.stepQuadroData.titoloSoggettoAbilitato = this.titoli.filter((x) => {
        return x.id == this.soggettoAbilitatoForm.value.titoloSoggettoAbilitato.id;
      })[0]; }

      return this.stepQuadroData.titoloSoggettoAbilitato;
  }


	/* 
	* nome metodo "getDelegatiLength"; method description: 
	* @param ()
	* @returns number
	*/ 

  getDelegatiLength(): number {
    if(!this.stepQuadroData.delegatiList) { return 0; }
    return Object.keys(this.stepQuadroData.delegatiList).length;
  }


	/* 
	* nome metodo "addSubject"; method description: 
	* @param (contact: any, subjectType: string)
	* @returns 
	*/ 

  addSubject(contact: any, subjectType: string) {
    delete (contact as any).replaceInFolder;
    
    const aggiungiSoggettoRequest = {
      contatto: contact,
      idIstanza: this.formsService.id_istanza,
      ruoloSoggetto: this._mergerolesSelectedFromFolderContact(contact, this.ruoloDelegante),
      ruoliPossibili: JSON.stringify(this.ruoliPossibili)
    };

    this.mudeopenFoBeService.aggiungiSoggettoCoinvolto(aggiungiSoggettoRequest, subjectType)
            .pipe(takeUntil(this.destroy$))
            .subscribe(y => {
      const allsubjects = this.loadSubjectFromRoles(y);
      const delegatedSubjects: any = {};

      Object.keys(allsubjects).forEach((key) => {
        if(allsubjects[key].roles.find((role) => role.id == this.ruoloDelegante.id && role.checked )) {
          delegatedSubjects[key] = allsubjects[key]; }
      })

      this.stepQuadroData.delegatiList = delegatedSubjects;
      this.storeJsonData(true, this.isStepValidationOK);
    });
  }

  _mergerolesSelectedFromFolderContact(contact: model.ContattoVO, role: model.RuoloPossibileVO): any {
    const roles = (this.rolesSelectedFromFolder[this.getSubjectInfo('cf', contact)] || { roles: []}).roles
                                          .filter(x => x.id != role.id && x.checked);
    roles.push({ id: role.id, value: role.value });
    return roles;
  }


	/* 
	* nome metodo "setSoggetto"; method description: 
	* @param (soggetto: any = null, subjectType: string = null, modal: any = null)
	* @returns void
	*/ 

  setSoggetto(soggetto: any = null, subjectType: string = null, modal: any = null): void {
      // disabled until logic changes    if(!(soggetto && this.stepQuadroData.delegatiList && this.stepQuadroData.delegatiList[this.retrieveSoggettoId(null, soggetto)]))
      setTimeout(_ => {
        this.mudeopenFoBeService.salvaTitoloSoggettoAbilitato(this.formsService.id_istanza,
                                                      this.formsService.istanza.tipologia_presentatore.id != '2'? this.titoloSoggettoAbilitato : null,
                                                      this.formsService.istanza.tipologia_presentatore.id == '2'? this.titoloSoggettoAbilitato : null,
                                                      soggetto || this.loggedContactUser,  // all subjects selected must be Presenters
                                                      subjectType || 'PF').subscribe(y => {
            this.addMoreDeleganti = false;
            if(modal) {
              modal.dismiss(); }

            if(soggetto) {
              this.addSubject(soggetto, subjectType); }
            else {
              this.storeJsonData(true, this.isStepValidationOK); }
          });
      }, 200);
  }


	/* 
	* nome metodo "onDeleteSoggetto"; method description: 
	* @param (key: string, delegato: any)
	* @returns 
	*/ 

  onDeleteSoggetto(key: string, delegato: any) {
    this.confirmDialog("Vuoi eliminare il delegante?", () => {
      this.mudeopenFoBeService.eliminaSoggettoCoinvolto(this.formsService.id_istanza, delegato.idSoggetto).subscribe((x) => {
        delete this.stepQuadroData.delegatiList[key];
        this.addMoreDeleganti = this.getDelegatiLength() == 0;
  
        this.storeJsonData(true, this.isStepValidationOK);
      });
    });

  }

  wizardLev1FilterText: string = '';

	/* 
	* nome metodo "checkWizardFilter"; method description: 
	* @param (txt: string)
	* @returns boolean
	*/ 

  checkWizardFilter(txt: string): boolean {
      return !this.wizardLev1FilterText || new RegExp(this.wizardLev1FilterText, 'ig').test(txt);
  }


	/* 
	* nome metodo "roleCheck"; method description: 
	* @param (role: any)
	* @returns 
	*/ 

  roleCheck(role: any) {
    role.checked = !role.checked;
  }


	/* 
	* nome metodo "isThereAtLeastOneRole"; method description: 
	* @param ()
	* @returns boolean
	*/ 

  isThereAtLeastOneRole(): boolean {
    let rolesSelected: any = null;
    if(this.ruoliPossibili) {
      rolesSelected = this.ruoliPossibili.filter((x) => x.checked); }

    return rolesSelected && rolesSelected.length;
  }

  get isStepValidationOK(): boolean {
    return this.setStepCompleteState(this.isThereAtLeastOneRole()
            && (this.stepQuadroData.titoloSoggettoAbilitato || this.stepQuadroData.idTipoSoggetto +'' === '4')
            // not required anymore:            && this.stepQuadroData.agreedCheck 
            && (this.quadroOptions?.nessun_delegante || this.stepQuadroData.idTipoSoggetto +'' === '1' || this.getDelegatiLength() > 0));
  }

  setAgreeCheckbox() {
    this.storeJsonData(true, this.isStepValidationOK);
  }

}