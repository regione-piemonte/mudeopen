import { id } from '@swimlane/ngx-datatable';
/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { AfterContentInit, Component, Inject, Injector, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable, of } from 'rxjs';
import {
  tap
} from 'rxjs/operators';
import { InserisciPersonaFisicaComponent, InserisciPersonaGiuridicaComponent, RicercaInserisciPersonaComponent } from '../../../../shared/components';

import { CONFIG } from 'mudeopen-common';
import {
  StepConfig
} from 'mudeopen-common';
import { MudeopenFoBeService, UserSelectionType } from 'mudeopen-common';
import * as model from 'mudeopen-common';
import { OnStepInit, StepComponent } from "../step.component";
import { forkJoin } from 'rxjs';
import { transpileModule } from 'typescript';
import { USER_FUNC, USER_PROFILE } from '../../../../shared/enums';


export interface subjectStructure {
  "contact": model.ContattoVO,
  "idSoggetto": number,
  "idTitoloSoggetto": string,
  "abilitazioni": any,
  "opera_fini_fiscali_dipendente": any,
  "ente_societa_appartenenza": any,
  "domiciliazione_corrispondenza_professionista": any,
  "rappresentati": string[],
  "roles": {
    "id": string,
    "checked": boolean,
    "value": string,
    "visibile": boolean,
    "def": boolean,
    "gruppo": string
  }[]
};


@Component({
  selector: 'soggetti-coinvolti',
  templateUrl: './soggetti-coinvolti.component.html',
  styleUrls: ['./soggetti-coinvolti.component.css']
})
export class SoggettiCoinvoltiComponent extends StepComponent implements OnInit, OnStepInit, AfterContentInit {
  isDefinizionePersona: string;
  isDefinizioneSoggetti: boolean = false;
  subjectIdCurrentlySelected: any;
  ruoliObbligatori: model.RuoloObbligatorioVO[] = []; 
  atLeastOneMandatory: boolean = false;

  soggettoForm: FormGroup;
  titoliBranch_IN_CO: model.SelectVO[];
  titoliBranch_RT: model.SelectVO[];

  ruoliPossibili: model.RuoloPossibileVO[];

  headerSoggettiRiepilogo: string[];

  soggettiDataTable: any = [];
  rtRappresentatoHeaders = [
    { name: 'Nome', prop: 'name' },
    { name: 'Cod. Fisc./P.IVA', prop: 'cf' }
  ];

  isStepLoaded: boolean;
  constructor(mudeopenFoBeService: MudeopenFoBeService,
                injector: Injector,
                @Inject(CONFIG) injConfig: StepConfig) {
      super(mudeopenFoBeService, injector, injConfig);
  }


	/* 
	* nome metodo "ngOnInit"; method description: 
	* @param ()
	* @returns void
	*/ 

  ngOnInit(): void {
    this.soggettoForm = new FormGroup({
      opera_fini_fiscali_dipendente: new FormControl(),
      ente_societa_appartenenza: new FormControl(null, [Validators.required]),
      domiciliazione_corrispondenza_professionista: new FormControl(),
  
      titoloSoggettoAbilitato: new FormGroup({
        id: new FormControl(null, [Validators.required]),
        value: new FormControl()
      }),
      titoloSoggettoAbilitatoRT: new FormGroup({
        id: new FormControl(null, [Validators.required]),
        value: new FormControl()
      })

    });
    this.checkOperareComeDipendente(false);

    this._init();
  }

  _init() {
    forkJoin([
      this.mudeopenFoBeService.getListaTipoPresentatore('1'),
      this.mudeopenFoBeService.getListaTipoPresentatore('2')
    ]).subscribe((results) => {
      this.titoliBranch_IN_CO = results[0];
      this.titoliBranch_RT = results[1];
    });

  }

	/* 
	* nome metodo "onStepLoader"; method description: 
	* @param (jsondata: any, isNew: boolean)
	* @returns 
	*/ 

  onStepLoader(jsondata: any, isNew: boolean) {
    this.resetState();


    this.mudeopenFoBeService.getRuoliIstanza(this.formsService.id_istanza, null, true).subscribe(x => {
      this.loadSubjectList(x);

      // save in case it is the creator of the instance 
      if(isNew && this.formsService.hasUserPermission(null, 'CREATORE_IST_.*')) {
        this.storeJsonDataDetailed(null, false).subscribe((res) => {
          return; // nothing to do, just return
        } ); }

      this.isStepLoaded = true;
    });

    this.readOnlyMode = !!this.quadroOptions?.quadro_bloccato_sola_lettura;

    return false;
  }


	/* 
	* nome metodo "canLeaveStep"; method description: 
	* @param ()
	* @returns boolean
	*/ 

  canLeaveStep(): boolean { 
    this.subjectIdCurrentlySelected = null;
    return true; 
  }


	/* 
	* nome metodo "loadSubjectList"; method description: 
	* @param (x: any)
	* @returns 
	*/ 

  loadSubjectList(x: model.RuoliIstanzaResponse) {
    this.ruoliObbligatori = x.ruoliObbligatori;
    this.atLeastOneMandatory = x.almenoUnRuolo;

    const savedRappresentanti = {};
    Object.keys(this.stepQuadroData.subjectList||[]).forEach(key => {
      if(this.stepQuadroData.subjectList[key].rappresentati?.length)
        savedRappresentanti[key] = this.stepQuadroData.subjectList[key].rappresentati;
    });

    this.stepQuadroData.subjectList = this.loadSubjectFromRoles(x);

    Object.keys(savedRappresentanti).forEach(key => {
      if(this.stepQuadroData.subjectList[key])
        this.stepQuadroData.subjectList[key].rappresentati =  savedRappresentanti[key];
    });
  }


	/* 
	* nome metodo "isRiepilogoItemSelected"; method description: 
	* @param ()
	* @returns boolean
	*/ 

  isRiepilogoItemSelected(): boolean {
    if(this.stepQuadroData.subjectList) {
      return Object.keys(this.stepQuadroData.subjectList).length > 0; }

    return false;
  }

  soggettoSelectionMethod: string;

	/* 
	* nome metodo "onNewSoggetto"; method description: 
	* @param ()
	* @returns void
	*/ 

  onNewSoggetto(): void {
    this.soggettoSelectionMethod = 'create';
    const modal = this.modalService.open((this.isDefinizionePersona != 'PF'? InserisciPersonaGiuridicaComponent : 
                  InserisciPersonaFisicaComponent), { size: 'xl', backdrop: 'static', keyboard: false });
    modal.componentInstance.closeEvent.subscribe(() => { 
      this.soggettoSelectionMethod = null;
      modal.dismiss(); 
    });
    modal.componentInstance.subjectCreated.subscribe(contact => {
      this.onSoggettoSelected(contact);
      this.soggettoSelectionMethod = null;
      modal.dismiss();
    });      
  }

  subjectToBeRemoved: any;
  subjectKeyToBeRemoved: string;

	/* 
	* nome metodo "onSoggettoSwitch"; method description: 
	* @param (old_contact: any)
	* @returns 
	*/ 

  onSoggettoSwitch(old_contact: any) {
    this.soggettoSelectionMethod = 'switch';
    const modal = this.modalService.open(RicercaInserisciPersonaComponent, { size: 'xl', backdrop: 'static', keyboard: false });

    this.isDefinizionePersona = old_contact.tipo_contatto;
    modal.componentInstance.userSelectionType = UserSelectionType.CONTACTS_SELF_INCLUDED;
    modal.componentInstance.excludeIDs = this.allSubjectsContactIDs;
    modal.componentInstance.type = this.isDefinizionePersona;
    modal.componentInstance.askForReplace = this.isSelectedSubjectInherited(old_contact);
    modal.componentInstance.askForReplaceChk = true;
    modal.componentInstance.title = this.isSelectedSubjectInherited(old_contact)?"Sostituzione soggetto selezionato dal fascicolo":"Sostituzione soggetto";
    modal.componentInstance.label = "Seleziona il nuovo soggetto che sostituisce: <strong>" + this.getSubjectInfo('name', old_contact) + " </strong>";

    modal.componentInstance.closeEvent.subscribe(() => { this.resetState(); modal.dismiss(); });
    modal.componentInstance.selected.subscribe(new_contact => {

      this.subjectKeyToBeRemoved = this.getSubjectInfo('guid', old_contact);
      this.subjectToBeRemoved = this.stepQuadroData.subjectList[this.subjectKeyToBeRemoved];

      delete this.stepQuadroData.subjectList[this.subjectKeyToBeRemoved];
      this.onSoggettoSelected(new_contact, this.subjectToBeRemoved.roles);

      modal.dismiss();
    });      
  }

  get allSubjectsContactIDs(): string {
    return Object.keys(this.stepQuadroData.subjectList || {}).map(s => {return this.stepQuadroData.subjectList[s].contact.id}).join(',');
  }

	/* 
	* nome metodo "onSoggettoSelected"; method description: 
	* @param (contact: any, overrideroles: any = null)
	* @returns void
	*/ 

  differentContactNotAllowed: boolean;
  onSoggettoSelected(contact: any, overrideroles: any = null): void {
    this.isDefinizionePersona = contact.tipo_contatto;

    this.lastRolesFiltered = {};
    this.subjectIdCurrentlySelected = this.getSubjectInfo('guid', contact);
    if(!this.stepQuadroData.subjectList[this.subjectIdCurrentlySelected]) {
      this.ruoliPossibili = this.getFilteredRolesForContact(contact, (overrideroles && this.mergeAllRoles(overrideroles)) || this.allPossibleSubjectRolesClean);
      this.stepQuadroData.subjectList[this.subjectIdCurrentlySelected] = {
        contact: contact,
        roles: this.ruoliPossibili
      };
    }
    else {
      this.subjectIdCurrentlySelected = this.getSubjectInfo('guid', contact);
      this.differentContactNotAllowed = (contact.id != this.stepQuadroData.subjectList[this.subjectIdCurrentlySelected].contact.id);
      if(this.differentContactNotAllowed) {
        this.subjectIdCurrentlySelected = null;
        return;
      }

      this.ruoliPossibili = this.getFilteredRolesForContact(contact,  this.mergeAllRoles(this.stepQuadroData.subjectList[this.subjectIdCurrentlySelected].roles));
    }

    const subjectData = this.stepQuadroData.subjectList[this.subjectIdCurrentlySelected];
    if(this.stepQuadroData.subjectList[this.subjectIdCurrentlySelected].idTitoloSoggetto) {
      this.soggettoForm.get('titoloSoggettoAbilitato').setValue({ id: subjectData.idTitoloSoggetto, value: null }); }
    this.soggettoForm.get('titoloSoggettoAbilitato')[this.stepQuadroData.subjectList[this.subjectIdCurrentlySelected].idTitoloSoggetto? 'enable' : 'disable']();
    if(this.stepQuadroData.subjectList[this.subjectIdCurrentlySelected].idTitoloSoggettoRT) {
      this.soggettoForm.get('titoloSoggettoAbilitatoRT').setValue({ id: subjectData.idTitoloSoggettoRT, value: null }); }
    this.soggettoForm.get('titoloSoggettoAbilitatoRT')[this.stepQuadroData.subjectList[this.subjectIdCurrentlySelected].idTitoloSoggettoRT? 'enable' : 'disable']();
  
    this.soggettoForm.get('opera_fini_fiscali_dipendente').setValue(subjectData.opera_fini_fiscali_dipendente||false);
    this.soggettoForm.get('ente_societa_appartenenza').setValue(subjectData.ente_societa_appartenenza||'');
    this.checkOperareComeDipendente(subjectData.opera_fini_fiscali_dipendente||false);
    this.soggettoForm.get('domiciliazione_corrispondenza_professionista').setValue(subjectData.domiciliazione_corrispondenza_professionista||false);

    this._loadOwnersForRT();
  }

  getFilteredRolesForContact(contact: model.ContattoVO, roles: model.RuoloPossibileVO[]) {
    roles = JSON.parse(JSON.stringify(roles)); // clone

    return roles.filter(r => {
      (r as any).disabled = !((r.operatori||'PF/PG').includes(contact.tipo_contatto) 
                                && this.checkBranchRoles(roles, r.includi, true)
                                && !this.checkBranchRoles(roles, r.escludi, false));
      
      return true;
    });
  }

  checkBranchRoles(roles: model.RuoloPossibileVO[], check: string, inclusive: boolean) {
    let result = inclusive;
    // ie: BRANCH1/DE,IN|BRANCH4/DE
    (check||'').split('|').forEach(tok => {
      // skip if not the current branch to check
      if(!tok || !tok.startsWith('BRANCH'+this.formsService.istanza.tipologia_presentatore.id)) { return; }

      if(tok.indexOf(':') == -1) {
        result = !inclusive;
        return;
      }

      tok.split(':')[1].split(',').forEach(role2check => {
        const findRole = roles.find(r => r.id == role2check && (r as any).checked);
        if((inclusive && !findRole) || (!inclusive && findRole))
          result = !inclusive;
      });
    });

    return result;
  }

  _loadOwnersForRT() {
    this.soggettiDataTable = [];
    this.datatable.selected = [];

    const subjectList: subjectStructure[] = this.stepQuadroData.subjectList;

    Object.keys(subjectList).forEach((key) => {
      if(!subjectList[key].idSoggetto 
                  || key == this.subjectIdCurrentlySelected
                  || key == this.subjectKeyToBeRemoved) {
        return; }

      subjectList[key].roles.forEach((role) => {
        if(((this.formsService.istanza.branch != 1 && role.id === 'IN' /* Intestatario */) 
                          || role.id === 'CO' /* Cointestatario */) && role.checked) {

          const simpleSubject = {
            id: key,
            name: this.getSubjectInfo('name', subjectList[key].contact.anagrafica),
            cf: this.getSubjectInfo('cf', subjectList[key].contact) // j586 this.getSubjectInfo('guid', subjectList[key].contact)
          };
          this.soggettiDataTable.push(simpleSubject);

          // add "rappresentati" to selected
          if((subjectList[this.subjectIdCurrentlySelected].rappresentati || []).indexOf(key) > -1) {
            this.datatable.selected.push(simpleSubject); }
        }
      })
    })
  }


  hasPresenters(subject: subjectStructure) {
    const presentedKey = this.getSubjectInfo('guid', subject.contact);

    for(const key in this.stepQuadroData.subjectList) {
      if((this.stepQuadroData.subjectList[key].rappresentati || []).indexOf(presentedKey) != -1) {
        return true; } }

    return false;
  }

  getPresenterInfo(presented: subjectStructure, key: string): string {
    const presentedKey = this.getSubjectInfo('guid', presented.contact);

    if((this.stepQuadroData.subjectList[key].rappresentati || []).indexOf(presentedKey) != -1)
      return this.getSubjectInfo('name', this.stepQuadroData.subjectList[key].contact.anagrafica);

    return null;
  }

  get subject(): any {
    return this.stepQuadroData.subjectList[this.subjectIdCurrentlySelected];
  }

  roleCategoryBranch1List = [ 
    { id: 'TEC~IMPR', value: "Ruoli tecnici e esecutivi" }
  ];
  roleCategoryAllBranchesList = [ 
    { id: 'ATD', value: "Ruoli legati agli aventi titolo" },
    { id: 'TEC~IMPR', value: "Ruoli tecnici e esecutivi" }
  ];  
  
  get roleCategories() {
    return this.roleCategoryAllBranchesList;
  }

  hasCatgegoryRoles(categoryFilter: any) {
    return this.getRuoliPossibiliListing(categoryFilter).filter(role => role.visibile && !role.disabled && !(role.def || role.default)).length;
  }

  get contactIN(): any {
    return Object.keys(this.stepQuadroData.subjectList).find((key) =>
          this.stepQuadroData.subjectList[key].roles.filter((y) => y.id === 'IN' /* Intestatario */ && y.checked) );
  }

  lastRolesFiltered: any = {};
  titleRequired: boolean = false;
  titleRequiredRT: boolean = false;
  getRuoliPossibiliListing(categoryFilter: any): any {
    if(this.lastRolesFiltered[categoryFilter.id]) return this.lastRolesFiltered[categoryFilter.id];

    // determines if 'IN' is already present and
    // do not display "intestatario" in case is already assigned
    let ownerAlreadyAssigned = this.isInstanceOwnerPresent() 
          || (this.formsService.istanza.branch != 3 && !this.stepQuadroData.subjectList[this.subjectIdCurrentlySelected]?.roles?.find(y => y.id === 'DE' /* DElegante */ && y.checked));
    (this.ruoliPossibili.find((x) => x.id === 'IN' /* INtestatario */) || {}).visibile = !ownerAlreadyAssigned;
    if(this.formsService.istanza.tipologia_presentatore.id == '1' 
            && this.stepQuadroData.subjectList[this.subjectIdCurrentlySelected]?.roles?.find(y => y.id === 'IN' && y.checked))
      (this.ruoliPossibili.find((x) => x.id === 'CO') || {}).visibile = false;

    setTimeout(x => {
      this.titleRequired = this.titleRequiredRT = false;

      this.ruoliPossibili.filter((x:any) => ((this.formsService.istanza.tipologia_presentatore.id != '1' && x.id === 'IN') || x.id === 'CO') && x.checked)
          .forEach(x2 => this.titleRequired = true );
      this.ruoliPossibili.filter((x:any) => (x.id === 'RT') && x.checked)
          .forEach(x3 => this.titleRequiredRT = true );

      this.soggettoForm.get('titoloSoggettoAbilitato')[this.titleRequired? 'enable' : 'disable']();
      this.soggettoForm.get('titoloSoggettoAbilitatoRT')[this.titleRequiredRT? 'enable' : 'disable']();
    }, 100);

    this.lastRolesFiltered[categoryFilter.id] = this.ruoliPossibili.filter(x => categoryFilter.id.indexOf(x.gruppo) > -1);
    return this.lastRolesFiltered[categoryFilter.id];
  }

  isInstanceOwnerPresent(): boolean {
    let ownerAlreadyAssigned = false;
    Object.keys(this.stepQuadroData.subjectList).forEach((key) => {
      if(!this.stepQuadroData.subjectList[key].idSoggetto 
                  || key == this.subjectIdCurrentlySelected
                  || key == this.subjectKeyToBeRemoved) { return; }
      this.stepQuadroData.subjectList[key].roles.forEach((y) => {
        if(y.id === 'IN' /* Intestatario */ && y.checked) {
          ownerAlreadyAssigned = true; }
      })
    })

    return ownerAlreadyAssigned;
  }

  get isRoleRTChecked(): boolean {
    return !!(this.ruoliPossibili || []).filter((x: any) => x.id == 'RT' && x.checked).length;
  }

  /* 
	* nome metodo "roleCheck"; method description: 
	* @param (role: any)
	* @returns 
	*/ 

  roleCheck(role: any, roleCategory: any) {
    this.lastRolesFiltered[roleCategory.id] = null;

    role.checked = !role.checked;
    if(role.checked
                  && this.formsService.istanza.tipologia_presentatore != '2'
                  && this.ruoliPossibili.filter((y:any) => y.id === 'DE' /* DElegante */ && y.checked)) {
      this.ruoliPossibili.filter((x) => 
                                     (role.id == 'IN' && (/*x.id === 'RT' || */x.id === 'CO')) 
                                  || (role.id == 'CO' && (/*x.id === 'RT' || */x.id === 'IN')) 
      ).forEach((r:any) => r.checked = false);
    }
  }



	/* giu
	* nome metodo "addSoggetto"; method description: 
	* @param (contact: model.ContattoVO | model.ContattoVO)
	* @returns 
	*/ 

  addSoggetto(contact: model.ContattoVO) {
    if(this.soggettoForm.invalid) {
      this.commonUtils.validateForm(this.soggettoForm); }
    // else if(this.subjectKeyToBeRemoved) {
    //   this.removeExistingSubject(this.subjectKeyToBeRemoved, this.subjectToBeRemoved).subscribe((x) => {
    //     this.insertSubject(contact);

    //     delete this.stepQuadroData.subjectList[this.subjectKeyToBeRemoved];
    //   });
    // }
    else {
      this.insertSubject(contact, this.subjectKeyToBeRemoved);
    }
  }


	/* 
	* nome metodo "removeExistingSubject"; method description: 
	* @param (key: string, subject: subjectStructure)
	* @returns Observable<any>
	*/ 

  removeExistingSubject(key: string, subject: subjectStructure): Observable<any> {
    return this.mudeopenFoBeService.eliminaSoggettoCoinvolto(this.formsService.id_istanza, subject.idSoggetto).pipe(tap(x => {
      this.removePresentedUserKey(key);
      delete this.stepQuadroData.subjectList[key];
      this.storeJsonData(true, this.isStepValidationOK);
    }));
  }

  removePresentedUserKey(subjectKey) {
    Object.keys(this.stepQuadroData.subjectList).forEach((key) => {
      // in case of othe roles than IN or CO, removes this key from any presenter
      if((this.stepQuadroData.subjectList[key].rappresentati || []).indexOf(subjectKey) != -1) {
        this.stepQuadroData.subjectList[key].rappresentati = 
                          this.stepQuadroData.subjectList[key].rappresentati.filter(presentedKey => presentedKey != subjectKey);
      }
    });
  }

  titoloLegitimazioneRappresentanza: string;
  havePresentersUsers() {
    if(this.formsService.istanza.tipo_istanza?.soggetti_bloccati) {
      return true; }

    this.requiredTitoloSelection = this.requiredATDSelection = false;
    Object.keys(this.stepQuadroData.subjectList).forEach((key) => {
      const subject = this.stepQuadroData.subjectList[key];

      if((subject.roles || []).find((x: any) => x.id == 'RT' && x.checked)
            && !subject.rappresentati?.length) {
        this.requiredATDSelection = true;
      }

      if(this.isPresenteRolePresent(subject)) {
        this.userWithCheckKO = this.getSubjectInfo('name', subject.contact.anagrafica);
        this.requiredTitoloSelection = true;
      }
    });

    return !this.requiredATDSelection && !this.requiredTitoloSelection;
  }

  isPresenteRolePresent(subject) : boolean {
    return ((subject.roles||[]).find(x => x.id === 'RT' && x.checked)
      && !subject.idTitoloSoggettoRT && !!(this.titoloLegitimazioneRappresentanza = 'rappresentanza'))
      || ((subject.roles||[]).find(x => ((this.formsService.istanza.tipologia_presentatore.id != '1' && x.id === 'IN') || x.id === 'CO') && x.checked)
          && !subject.idTitoloSoggetto && !!(this.titoloLegitimazioneRappresentanza = 'legittimazione'));
  }

	/* 
	* nome metodo "insertSubject"; method description: 
	* @param (contact: model.ContattoVO | model.ContattoVO)
	* @returns 
	*/ 
  requiredATDSelection: boolean;
  requiredTitoloSelection: boolean;
  insertSubject(contact: model.ContattoVO, switchSubjectForKey: any = null) {
    this.doPreliminaryInterstChecks();

    if((this.requiredATDSelection = this.ruoliPossibili.find((x: any) => x.id == 'RT' && x.checked) && !this.datatable.selected.length) 
              || !this.isContactChecksOk(contact, this.ruoliPossibili)) {
      setTimeout(_ => { $$.setFocusOnCancelButton(); }, 100);
      return; 
    }

    let replaceInFolder = (contact as any).replaceInFolder;
    delete (contact as any).replaceInFolder;

    const frmValues = this.soggettoForm.value;
    this.mudeopenFoBeService.aggiungiSoggettoCoinvolto(this.createSubjectData(contact, frmValues), 
              this.isDefinizionePersona,
              this.subjectToBeRemoved?.idSoggetto).subscribe(x => {
      if(switchSubjectForKey && replaceInFolder) {
        // add roles to be dismissed during the subject populating phase in the "REGISTRATA DA PA" event
        (this.stepQuadroData.subjectsRoleDismissed =
            (this.stepQuadroData.subjectsRoleDismissed || {}))[switchSubjectForKey] = this.subjectToBeRemoved.roles
                                                                                              .filter(y => y.checked)
                                                                                              .map(x => x.id);
      }

      this.deleteDismissedRole();
      this.handleSubectResponse(x, contact, frmValues);
    });
  }

  doPreliminaryInterstChecks() {
    if(!this.ruoliPossibili.find((x:any) => x.checked && (x.id=='IN' || x.id=='CO'))) 
      this.removePresentedUserKey(this.subjectIdCurrentlySelected);

    if(this.subjectKeyToBeRemoved)
      this.removePresentedUserKey(this.subjectKeyToBeRemoved);
  }

  // if exists, removes subjectsRoleDismissed in order to reenable the subject
  deleteDismissedRole() {
      if(this.stepQuadroData.subjectsRoleDismissed 
        && this.stepQuadroData.subjectsRoleDismissed[this.subjectIdCurrentlySelected]) {
        delete this.stepQuadroData.subjectsRoleDismissed[this.subjectIdCurrentlySelected];
      }
  }

  handleSubectResponse(x, contact, frmValues) {
    this.loadSubjectList(x);
    (((this.titleRequired || this.titleRequiredRT) && 
                // assign the new titles
                Object.assign(this.stepQuadroData.subjectList[this.subjectIdCurrentlySelected], {
                  idTitoloSoggetto: this.soggettoForm.value.titoloSoggettoAbilitato?.id || this.stepQuadroData.subjectList[this.subjectIdCurrentlySelected].idTitoloSoggetto,
                  idTitoloSoggettoRT: this.soggettoForm.value.titoloSoggettoAbilitatoRT?.id,
                  opera_fini_fiscali_dipendente: frmValues.opera_fini_fiscali_dipendente,
                  ente_societa_appartenenza: frmValues.ente_societa_appartenenza,
                  domiciliazione_corrispondenza_professionista: frmValues.domiciliazione_corrispondenza_professionista
                })
                // and save the titles if required
                && this.mudeopenFoBeService.salvaTitoloSoggettoAbilitato(this.formsService.id_istanza,
                                  this.soggettoForm.value.titoloSoggettoAbilitato,
                                  this.soggettoForm.value.titoloSoggettoAbilitatoRT,
                                  contact,  // all subjects selected must be Presenters
                                  this.isDefinizionePersona))
          // if titles are not required, do the job anyway
          || of({})).subscribe(y => {
        return this.handleTitleRequired();
      });
  }

  createSubjectData(contact, frmValues) {
    return {
      contatto: contact,
      idIstanza: this.formsService.id_istanza,
      ruoloSoggetto: this.ruoliPossibili.filter((x:any) => x.checked).map(x => { return { id: x.id, value: x.value };  }),
      ruoliPossibili: JSON.stringify(this.ruoliPossibili.filter((x:any) => x.checked)),
      opera_fini_fiscali_dipendente: frmValues.opera_fini_fiscali_dipendente,
      ente_societa_appartenenza: frmValues.ente_societa_appartenenza,
      domiciliazione_corrispondenza_professionista: frmValues.domiciliazione_corrispondenza_professionista
    };    
  }

  handleTitleRequired() {
      // add "rappresentati" to selected
      if(this.isRoleRTChecked) {
        this.stepQuadroData.subjectList[this.subjectIdCurrentlySelected].rappresentati = (this.datatable.selected || []).map(subjectSimple => {
          return subjectSimple.id;
        });
      }
      else if(this.stepQuadroData.subjectList[this.subjectIdCurrentlySelected].rappresentati) {
        delete this.stepQuadroData.subjectList[this.subjectIdCurrentlySelected].rappresentati; }

      this.resetState();
      this.storeJsonData(true, this.isStepValidationOK);
  }

  requiredPEC: boolean;
  requiredPIVA: boolean;
  requiredALBO: boolean;
  requiredAddress: boolean;
  userWithCheckKO: string;
  isContactChecksOk(contact: model.ContattoVO, roles: any): boolean {
    const hasPecRole = roles.filter((role:any) => role.checked && ( (role.obbligatori||'').includes('PEC') )).length;
    const hasPIVARole = roles.filter((role:any) => role.checked && ( (role.obbligatori||'').includes('PIVA') )).length;
    const hasAlboRole = roles.filter((role:any) => role.checked && ( (role.obbligatori||'').includes('ALBO') )).length;
    const hasIndirizzoRole = roles.filter((role:any) => role.checked && ( (role.obbligatori||'').includes('INDIRIZZO') )).length;

    this.userWithCheckKO = this.getSubjectInfo('name', contact.anagrafica);

    let isErr = false;
    if(this.requiredPEC = hasPecRole && !contact.anagrafica.pec) isErr = true;
    if(this.requiredPIVA = hasPIVARole && !(contact.anagrafica.partitaIva || contact.anagrafica.partitaIvaComunitaria)) isErr = true;
    if(this.requiredALBO = hasAlboRole && this.hasContactQualificheProf(contact)) isErr = true;
    if(this.requiredAddress = !contact.indirizzi?.length && (hasIndirizzoRole || this.formsService.getEnteProperty('INDIRIZZO_SOGGETTO_NECESSARIO') == 'abilitato')) isErr = true;
                
    return !isErr; 
  }

  hasContactQualificheProf(contact): boolean {
    return !(contact.qualificheProfessionali 
      && ((contact.qualificheProfessionali.find(qual => qual.no_obbligo_iscrizione_ordine))
              || (contact.qualificheProfessionali.find(qual => qual.numero_iscrizione_ordine))));
  }

  canRemoveSubjects(subject: subjectStructure): boolean {
    return !subject.roles.find(r => r.def && r.checked) && (!this.quadroOptions?.intestatario_bloccato || !subject.roles.find((y) => y.id === 'IN' /* Intestatario */ && y.checked) )
            /* reverted "MUDEOPEN-207": && (this.authService.getUser().contatto.id_user == subject.contact.id_user
                  || this.authService.getUser().contatto.anagrafica.codiceFiscale == subject.contact.anagrafica.codiceFiscale)
            */
  }

  requirementsPM_SPEC_Error: boolean = false;
  requirementsOptionalPM_SPEC_Error: boolean = false;
  checkIfPM_SpecRequirementsAreFulfilled() {
    // if PM is not mandatory for the template, assume PM is always found
    let isPM_found = !this.stepManagerService.mandatoryPM;
    let isPM_SpecOk = isPM_found;
    let allTECRolesAreOK = true;

    if(this.stepQuadroData.subjectList) {
      Object.keys(this.stepQuadroData.subjectList).forEach((key) => {
        const subject = this.stepQuadroData.subjectList[key];
        if(subject.contact.tipo_contatto != 'PF') { return; }

        // is subject a PM/Specialist?
        if(((this.mudeopenFoBeService.hasUserPermission(null, "PM_RUP_PM_.*", subject.abilitazioni) && (isPM_found = true))
                      || this.mudeopenFoBeService.hasUserPermission(null, USER_PROFILE.PROF_SPEC, subject.abilitazioni))
                  // set to false, in case the other checks are not satisfied
                  && allTECRolesAreOK && !(allTECRolesAreOK = isPM_SpecOk = false)
                  // PM/Specialist has TEC role-group and also the right "qualifica" ?
                  && !!this.stepQuadroData.subjectList[key].roles.filter((y) => y.checked && y.gruppo === 'TEC').length
                  && ((subject.contact.qualificheProfessionali || []).find(qual => qual.no_obbligo_iscrizione_ordine) || 
                        !!(subject.contact.qualificheProfessionali || []).filter(q => q.numero_iscrizione_ordine).length)) {
            allTECRolesAreOK = isPM_SpecOk = true; }
      })
    }

    // template PM optional
    if(!this.stepManagerService.mandatoryPM) {
      return !(this.requirementsOptionalPM_SPEC_Error = !allTECRolesAreOK); }

    // template PM mandatory
    return !(this.requirementsPM_SPEC_Error = !(isPM_found && isPM_SpecOk));
  }


	/* 
	* nome metodo "removeSubject"; method description: 
	* @param (key: string, subject: any)
	* @returns 
	*/ 

  removeSubject(key: string, subject: subjectStructure) {
    if(subject.idSoggetto) {
      this.confirmDialog("Vuoi eliminare il soggetto?", () => {
        this.removeExistingSubject(key, subject).subscribe((x) => {
          this.resetState();
        });
      });
    }
    else {
      delete this.stepQuadroData.subjectList[key]; 
      this.resetState();
    }
  }


	/* 
	* nome metodo "isMandatoryRoleChecked"; method description: 
	* @param (role)
	* @returns boolean
	*/ 

  isMandatoryRoleChecked(role): boolean {
    let isChecked = false;

    if(this.ruoliObbligatori) {
      this.ruoliObbligatori.forEach(item => {
        if(this.stepQuadroData.subjectList) {
          Object.keys(this.stepQuadroData.subjectList).forEach((key) => {
            const mRole = this.stepQuadroData.subjectList[key].roles.filter((x) => x.id == role.id);
              isChecked = isChecked || (mRole && mRole.length && mRole[0].checked)
          }) }
      }); }

    return isChecked;
  }

	/* 
	* nome metodo "isRoleMandatory"; method description: 
	* @param (role)
	* @returns 
	*/ 

  isRoleMandatory(role) {
    return this.ruoliObbligatori.filter((x) => x.id == role.id).length;
  }


	/* 
	* nome metodo "rolesSelected"; method description: 
	* @param ()
	* @returns any
	*/ 

  rolesSelected(): any {
    return this.ruoliPossibili.filter((x:any) => x.checked);
  }

  // Gestori eventi (soggetti coinvolti).
  

	/* 
	* nome metodo "onChangeTipologiaPersona"; method description: 
	* @param (value: string)
	* @returns 
	*/ 

  onChangeTipologiaPersona(value: string) {
    this.headerSoggettiRiepilogo = value === 'persona'?["Ruoli selezionati", "Nome", "Cognome", "Codice fiscale"] :
                                                ["Ruoli selezionati", "Ragione sociale", "CF/Partita IVA"];

    this.isDefinizionePersona = value;
  }


	/* 
	* nome metodo "resetState"; method description: 
	* @param ()
	* @returns void
	*/ 

  resetState(): void {
    this.subjectForWichRTIsRequired = false;
    this.subjectForWichRTIsRequiredUser = '';
    this.requiredPEC = this.requiredPIVA = this.requiredALBO = this.requiredAddress = this.requiredATDSelection = this.requiredTitoloSelection = false; 
    this.soggettoSelectionMethod = this.subjectKeyToBeRemoved = this.subjectIdCurrentlySelected = this.subjectToBeRemoved = null;

    this.soggettoForm.get('titoloSoggettoAbilitato').setValue({ id: null, value: null });
    this.soggettoForm.get('titoloSoggettoAbilitatoRT').setValue({ id: null, value: null });
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

  subjectForWichRTIsRequired: boolean;
  subjectForWichRTIsRequiredUser: string;
  get isStepValidationOK(): boolean {
    if(!this.isStepLoaded || this.subjectIdCurrentlySelected) { return false; }

    let isEditing: boolean = !this.stepQuadroData.subjectList || !Object.keys(this.stepQuadroData.subjectList).length;
    if(isEditing) { return false; }

    this.subjectForWichRTIsRequired = false;
    this.subjectForWichRTIsRequiredUser = '';

    // reset all flags before re-setting them
    this.ruoliObbligatori.forEach((x: any) => x.found = false );

    // checks mandatory roles
    let areAllSubjectsChecksOk = true;
    Object.keys(this.stepQuadroData.subjectList).forEach((key, subject) => {
      if(!areAllSubjectsChecksOk) { return; }
      const sbj = this.stepQuadroData.subjectList[key];

      if(!sbj.idSoggetto) {
        isEditing = true; }

      this.ruoliObbligatori.forEach((x: any) => {
        if(this._howMany(x, key)) {
          x.found = true;
        }
      });

      // in case IN/CO of type PG, it must be there must be an RT that 
      if(sbj.contact.tipo_contatto == 'PG' 
                && sbj.roles.find((x) => (x.id == 'IN' || x.id == 'CO') && x.checked)
                && !this.isUserPresentedByRT(key)) {
        this.subjectForWichRTIsRequired = true; 
        this.subjectForWichRTIsRequiredUser = this.getSubjectInfo('name', sbj.contact.anagrafica);
      }

      if(!this.isContactChecksOk(sbj.contact, sbj.roles)) {
        areAllSubjectsChecksOk = false; }
    })

    const howManyMandatoryRolesFound = this.ruoliObbligatori.filter((x: any) => x.found).length;
    const isCompleted = !isEditing && ((this.atLeastOneMandatory && howManyMandatoryRolesFound >= 1) 
            || howManyMandatoryRolesFound >= this.ruoliObbligatori.length);

    return !this.subjectIdCurrentlySelected 
              && this.setStepCompleteState(this.havePresentersUsers() 
              && areAllSubjectsChecksOk
              && !this.subjectForWichRTIsRequired 
              && isCompleted 
              && this.checkIfPM_SpecRequirementsAreFulfilled())
              && this.globalStepValidation();
  };

  isUserPresentedByRT(key2find) {
    if(this.formsService.istanza.tipo_istanza?.soggetti_bloccati) {
      return true; }

    let found:boolean = false;
    Object.keys(this.stepQuadroData.subjectList).forEach((key) => {
      const subject = this.stepQuadroData.subjectList[key];

      if((subject.roles || []).find((x: any) => x.id == 'RT' && x.checked)
                && (subject.rappresentati||[]).find(k => k == key2find)) {
        found = true; }
    });

    return found;
  }


  _howMany(x, key) {
    let found = false;
    if(this.stepQuadroData.subjectList[key].roles) {
      this.stepQuadroData.subjectList[key].roles.filter((y) => {
        if(y.id == x.id && y.checked) {
          found = true; }
      })
    }

    return found;
  }


	/* 
	* nome metodo "cancelAddChangeSubject"; method description: 
	* @param ()
	* @returns 
	*/ 

  cancelAddChangeSubject() {
    if(this.soggettoSelectionMethod === 'switch') {
      this.stepQuadroData.subjectList[this.subjectKeyToBeRemoved] = this.subjectToBeRemoved; 
      this.resetState();
    }
    else {
      const subject: any = this.stepQuadroData.subjectList[this.subjectIdCurrentlySelected];

      if(subject && !subject.idSoggetto) {
        this.removeSubject(this.subjectIdCurrentlySelected, subject.contact); }
      else {
        this.resetState(); }
    }
  }

  checkOperareComeDipendente(checked: boolean) {
    this.soggettoForm.get('ente_societa_appartenenza')[checked? 'enable' : 'disable']();
  }

  getAccountOwner(subject: subjectStructure): string {
    const ownerInfo = subject.contact.proprietario_rubrica.split('|');
    return ownerInfo[1] + ' ' + ownerInfo[2];
  }

  isVolturaSubentroAllowed(subject) {
    // handle VOLTURA/SUBENTRO
    return this.canRemoveSubjects(subject) 
              && this.formsService.hasUserPermission(USER_FUNC.COMP_SOGG_COINV) 
              && !((subject.roles || []).find(r => r.id == 'IN' && r.checked) && this.formsService.istanza.branch == 1); // esclude IN for branch 1
  }

  isAnagraficaEditAllowed(subject) {
    return this.formsService.hasUserPermission(USER_FUNC.COMP_SOGG_COINV);
  }

  get userSelectionSearch(): string {
    return UserSelectionType.CONTACTS_SELF_INCLUDED;
  }

  onContactEdit(subject: subjectStructure) {
    const modal = this.modalService.open(subject.contact.tipo_contatto=='PF'?InserisciPersonaFisicaComponent:InserisciPersonaGiuridicaComponent, { size: 'xl', backdrop: 'static', keyboard: false });
    modal.componentInstance.soggettoOnEdit = subject.contact;
    modal.componentInstance.idIstanza = this.formsService.id_istanza;
    
    modal.componentInstance.closeEvent.subscribe(() => { modal.dismiss(); });
    modal.componentInstance.subjectCreated.subscribe(subject => {
      this.reloadStep();
      modal.dismiss();
    });
  }

  isSelectedSubjectInherited(contact: any): boolean {
    return !!(this.stepManagerService.jsondata['QDR_SOGGETTO_ABILIT']?.soggettiEreditati || {})[this.getSubjectInfo('guid', contact)];
  }

}