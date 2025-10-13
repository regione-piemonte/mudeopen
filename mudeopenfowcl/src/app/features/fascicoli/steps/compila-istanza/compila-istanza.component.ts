import { Subscription } from 'rxjs';
/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { Component, EventEmitter, Inject, Injector, OnInit, ViewChild } from '@angular/core';
import { FormioBaseComponent, FormioComponent, FormioForm } from '@formio/angular';
import { environment } from 'buildfiles/environment.local-rp-01';
import { CONFIG } from 'mudeopen-common';
import { StepConfig } from 'mudeopen-common';
import { MudeopenFoBeService } from 'mudeopen-common';
import { forkJoin } from 'rxjs';
import { OnStepInit, StepComponent } from "../step.component";
import { LoccsiComponent } from '../../../../loccsi/loccsi.component';




import * as model from 'mudeopen-common';




@Component({
  selector: 'compila-istanza',
  templateUrl: './compila-istanza.component.html',
  styleUrls: ['./compila-istanza.component.css']
})
export class CompilaIstanzaComponent extends StepComponent implements OnStepInit {

  @ViewChild('mainFormio') mainFormio: FormioComponent;

  displayModeWithoutTabs: boolean = false;

  tabsMain = [ ];
  tabMainIndexSelected: number;

  tabsSub = [ ];
  tabSubIndexSelected: number;

  // formio
  customForm: FormioForm;
  submission: any = {};
  idTemplateQuadro: number;
  validationMessage: string;
  validationMessageType: string;
  _isStepValidationOK: boolean = false;

  formioOptions: any = {
    disableAlerts: true,
//    alertsPosition: AlertsPosition.none,
    saveDraft: true,
    saveDraftThrottle: 1000
  }
  renderOptions: any = {
    locale: 'it',
    language: 'it',
    i18n: {
      it: {
        error: "verificare i campi del form prima di salvare.",
        invalid_date:"Il campo non ha una data valida.",
        invalid_email: "{{field}} email non valida.",
        invalid_regex: "il campo non corrisponde al formato {{regex}}.",
        mask: "il campo non corrisponde al formato richiesto.",
        max: "il campo non puo' essere piu' grande di {{max}} caratteri.",
        maxLength: "il campo deve essere al massimo {{length}} caratteri.",
        min: "il campo non puo' essere piu' piccolo di {{min}}.",
        minLength: "il campo deve essere almeno di {{length}} caratteri.",
        next: "Avanti",
        pattern: "il campo non corrisponde al formato {{pattern}}",
        previous: "Indietro",
        required: "campo obbligatorio",
        unsavedRowsError: 'inserire i dati da salvare',
        invalidRowsError: 'correggere le righe non valide prima di procedere',
        invalidRowError: 'riga non valida. Correggerla o eliminarla',
        valueIsNotAvailable: 'campo obbligatorio',
        minWords: 'parole minime non sufficienti',
        maxWords: 'troppe parole inseite',
        custom: 'Verificare che il valore del campo sia corretto e non ci siano duplicazioni',
        'Type to search': 'inizia a scrivere per cercare',
        'No results found': 'nessuna voce trovata',
      }
    }
  };

  constructor(mudeopenFoBeService: MudeopenFoBeService,
        injector: Injector,
        @Inject(CONFIG) injConfig: StepConfig) {
    super(mudeopenFoBeService, injector, injConfig);
  }


  stepLoadingCompled: boolean = false;

	/* 
	* nome metodo "onStepLoader"; method description: 
	* @param (jsondata: any, isNew: boolean)
	* @returns 
	*/ 

  onStepLoader(jsondata: any, isNew: boolean) {
    if(!this.tabsMain.length) {
      this.tabsMain = this.quadro.json_configura_quadro.tabs; }
    else {
      this.loadJsonQuadro((this.tabSubIndexSelected >= 0 && this.tabsSub[this.tabSubIndexSelected])
                   || (this.tabMainIndexSelected >= 0 && this.tabsMain[this.tabMainIndexSelected])); }

    this.stepLoadingCompled = true;
    return false;
  }

  onStepUpdate() {
    this.updateStepData().subscribe((info: model.JDataIstanzaVO) => {
      this.onStepLoader(null, null);
    });
  }



	/* 
	* nome metodo "beforeSelectedTab"; method description: 
	* @param (tabIndex: number)
	* @returns 
	*/ 

  beforeSelectedTab(tabIndex: number) {
    this.stepManagerService.stepsLockedMessage = this.stepManagerService.stepsLocked;
  }


	/* 
	* nome metodo "selectedMainTab"; method description: 
	* @param (tabIndex: number)
	* @returns 
	*/ 

  selectedMainTab(tabIndex: number) {
    this.tabMainIndexSelected = tabIndex;
    this.tabSubIndexSelected = -1;
    this.tabsSub = this.tabsMain[tabIndex].subtabs;

    this.saveMessage = false;

    if(!this.tabsSub) {
      this.loadJsonQuadro(this.tabsMain[tabIndex]); }
  }


	/* 
	* nome metodo "selectedSubTab"; method description: 
	* @param (tabIndex: number)
	* @returns 
	*/ 

  selectedSubTab(tabIndex: number) {
    this.saveMessage = false;

    this.tabSubIndexSelected = tabIndex;
    this.loadJsonQuadro(this.tabsSub[tabIndex]);
  }


	/* 
	* nome metodo "canActivateMainTab"; method description: 
	* @param (tab: any)
	* @returns void
	*/ 

  canActivateMainTab(tab: any): void {
    tab.grayed = tab.disabled = !this.checkJsExpression(tab.activationCheck);
  }


	/* 
	* nome metodo "canActivateSubTab"; method description: 
	* @param (tab: any)
	* @returns void
	*/ 

  canActivateSubTab(tab: any): void {
    tab.disabled = !this.formsService.removeAllFormIOControls && !this.checkJsExpression(tab.activationCheck);
  }

	/* 
	* nome metodo "loadJsonQuadro"; method description: 
	* @param (tab: any)
	* @returns 
	*/ 

  loadJsonQuadro(tab: any) {
    this.dependencyMessageSUCCESS = this.dependencyMessageINFO = this.dependencyMessageWARNING = this.dependencyMessageERROR = "";
    this.subQuadro.json_configura_quadro = tab.jsonForm;

    this.stepManagerService.loadQuadroById(tab.id_quadro, this.formsService.id_istanza, this.formsService.id_fascicolo).subscribe((res) => {
      this.subQuadro = res;
      this.showAllFormIOKeys(this.subQuadro);
      this.checkRemoveControls(this.subQuadro);
      
      tab.jsonForm = this.subQuadro.json_configura_quadro = JSON.parse(this.subQuadro.json_configura_quadro);

      if(this.tabsMain?.[this.tabMainIndexSelected]?.quadroOptions?.readOnlyMode ?? false) {
        this.tabsMain[this.tabMainIndexSelected].quadroOptions.readOnlyMode = this.evalJsExpression(''+this.tabsMain[this.tabMainIndexSelected].quadroOptions.readOnlyMode); }
      if(this.tabsSub?.[this.tabSubIndexSelected]?.quadroOptions ?? false) {
        this.tabsSub[this.tabSubIndexSelected].quadroOptions = this.evalJsExpression(''+this.tabsSub[this.tabSubIndexSelected].quadroOptions); }

      this.quadroOptions = Object.assign({}, this.quadroOptions, this.tabsMain?.[this.tabMainIndexSelected]?.quadroOptions || {}, this.tabsSub?.[this.tabSubIndexSelected]?.quadroOptions || {});
      this._readOnlyModeOption = this.quadroOptions.readOnlyMode || this.istanzaReadOnlyMode || !(this.stepManagerService.steps[this.stepIndex].canUserModify = res.flg_modificabile);

      // load FormIO template
      this.customForm = this.subQuadro.json_configura_quadro.formio || this.subQuadro.json_configura_quadro;
      $$.setToolboxWindowPopupValue('FormIO', JSON.parse(JSON.stringify(this.customForm)));

      /* SET PREEMPTIVE SUBMISSION */
      this.submission = { 
        data: Object.assign({_URL_BACKEND: environment.basePath, _OPTIONS: this.quadroOptions, _ACTIVE_TAB: this.getQuadroInfo(), _ACTIVE_SUBTAB: this.getSubQuadroInfo()}, this.stepManagerService.jsondata)
      };
      
      // used to call updateInfoQuadroBox
      this.stepManagerService.raiseStepLoaded(this.stepIndex, this.subQuadro);
    });
  }

  getSubQuadroInfo() {
    return this.currentTab;
  }

  getQuadroInfo(activetab = null) {
    if(!activetab){
      activetab = this.getQuadroInfo(this.tabsMain[this.tabMainIndexSelected]); }

    return {
      label: activetab.label,
      activationCheck: activetab.activationCheck,
      dependencyCheck: activetab.dependencyCheck,
      disabled: activetab.disabled,
      eraseKeysOnSubmit: activetab.eraseKeysOnSubmit,
      formValidation: activetab.formValidation,
      grayed: activetab.grayed
    };
  }


	/* 
	* nome metodo "loadInitialSubmission"; method description: 
	* @param ()
	* @returns 
	*/ 

  loadInitialSubmission() {
    // QuadroVO --> json_default
    // cod_tipo_quadro flat keys 

    let data = this.stepSubquadroData;
    if(this.subQuadro.json_default && !data) {
      const defaultJson = JSON.parse(this._resolveJDATAexpr(this.subQuadro.json_default));

      if(this._resolveAPICalls(defaultJson)) { 
        return false; }

      data = defaultJson;
    }

    this._setSubmission(data || {});
  }

  _resolveAPICalls(defaultJson: any) {
    const APIScope = 'api://';
    const observableAPIs = {};
    
    Object.keys(defaultJson).forEach(key => {
      for(let index = (''+defaultJson[key]).indexOf(APIScope) ; index>-1; index=-1) {
        observableAPIs[key] = this.evalJsExpression('$$.'+defaultJson[key].substring(index + APIScope.length), 
                                                                            this.stepManagerService.jsondata, 
                                                                            this.mudeopenFoBeService);
      }
    });

    if(Object.values(observableAPIs).length) {
      forkJoin(Object.values(observableAPIs)).subscribe((results) => {
        Object.keys(observableAPIs).forEach((key, index) => {
            observableAPIs[key] = results[index];
        });

        this._setSubmission(Object.assign({}, defaultJson, observableAPIs));
      });

      return true;
    }
  }

  _setSubmission(data: any) {
    if(this.stepSubquadroData) { // presaved tab data
      // set saved data avoiding formio presets
      this.submission = { 
        data: Object.assign({_URL_BACKEND: environment.basePath, _OPTIONS: Object.assign({}, this.quadroOptions, this.tabsMain?.[this.tabMainIndexSelected]?.quadroOptions || {}, this.tabsSub?.[this.tabSubIndexSelected]?.quadroOptions || {}), _ACTIVE_TAB: this.getQuadroInfo(), _ACTIVE_SUBTAB: this.getSubQuadroInfo()}, this.stepManagerService.jsondata, JSON.parse(JSON.stringify(data)))
      };
    }
    else { // tab not compiled yet
      // ADDs any presaved or default data to preinitialized submission
      this.submission = { 
        data: Object.assign(this.submission?.data || {}, JSON.parse(JSON.stringify(data)))
      };
    }

    $$.setToolboxWindowPopupValue('SottoQuadro', this.subtractExtraJsonDataKeys(this.submission?.data), this.subQuadro.tipo_quadro.cod_tipo_quadro);
    $$.setToolboxWindowPopupValue('SottoQuadroSubmission', this.submission, this.subQuadro.tipo_quadro.cod_tipo_quadro);

    // chechs tab dependency
    this.checkDependency();
    // set tab green checks
    this.setTabStates();
    // show nect button if it's all ok
    this._isStepValidationOK = this.validateAllForms();
  }


  // /////////////////////////////////////////////////////
  // FORMIO EVENTS
  // /////////////////////////////////////////////////////

	/* 
	* nome metodo "refreshSubmission"; method description: 
	* @param ()
	* @returns 
	*/ 

  refreshFormIO = new EventEmitter()

	/* 
	* nome metodo "ready"; method description: 
	* @param (formioBaseComponent: FormioBaseComponent)
	* @returns 
	*/ 

  onFormIOReady(formioBaseComponent: FormioBaseComponent){
    this.currentForm = formioBaseComponent.formio;

    setTimeout(_ => {
      this.loadInitialSubmission();
    }, 100);
  }

	/* 
	* nome metodo "onFormIOChange"; method description: 
	* @param (data)
	* @returns 
	*/ 

  onFormIOChange(data) {
    if(data?.changed && data?.isModified) {
      data.data.isValid = data.isValid;
      data.data.submit = false;

      const changedKey = 
        this.findChangedKeys(this.stepSubquadroData, 
              this.subtractExtraJsonDataKeys(data.data));

      this.stepSubquadroData = this.subtractExtraJsonDataKeys(data.data);
      this.checkDependency();

      const _data = JSON.parse(JSON.stringify(this.stepManagerService.jsondata));
      delete _data['ex_ISTANZA'];
      delete _data['ex_FASCICOLO'];
      $$.setToolboxWindowPopupValue('JData', _data);
  
      $$.setToolboxWindowPopupValue('SottoQuadro', this.stepSubquadroData, this.subQuadro.tipo_quadro.cod_tipo_quadro);
      $$.setToolboxWindowPopupValue('SottoQuadroSubmission', data, this.subQuadro.tipo_quadro.cod_tipo_quadro);
  
      $$.setToolboxWindowPopupValue('Chiave', changedKey);

      if(!$$.isLocal && !this.formsService.doNotCheckFormIOModify && this.stepSubquadroData?.submitted) {
        this.stepManagerService.stepsLocked = true; }
    }
  }

	/* 
	* nome metodo "onFormIOSubmit"; method description: 
	* @param (submission)
	* @returns 
	*/ 

  onFormIOSubmit(submission) {
    this.stepManagerService.stepsLockedMessage = false;
    
    let jDataKeys2Delete = null;
    if(this.currentTab.eraseKeysOnSubmit) {
      try {
        jDataKeys2Delete = this.evalJsExpression(this.currentTab.eraseKeysOnSubmit, submission.data)
                                                          .filter(x => typeof x == 'string')
                                                          .join(',');
      } catch(skip) { }
    }
    
    const data = this.subtractExtraJsonDataKeys(submission.data);

    data.submit = true;
    data.submitted = true;
    data.isValid = true;
    this.stepSubquadroData = data;
    this.setTabStates();
    this._isStepValidationOK = this.validateAllForms();
    this.setStepCompleteState(this._isStepValidationOK, false);

    this.storeJsonDataDetailed(this.stepQuadroData, true, this._isStepValidationOK, data, null, jDataKeys2Delete)?.subscribe((res) => {
      this.saveMessage = true;

      this.setTabStates();
      this._isStepValidationOK = this.validateAllForms();
      this.setStepCompleteState(this._isStepValidationOK, false);
      this.checkDependency();
  
      this.successEmitter.emit('Dati salvati con successo');
      this.currentForm.emit('submitDone');

      this.stepManagerService.stepsLocked = false;

      setTimeout(x => {
        window.scrollTo(0,0)}, 100);
    });
  }  

  // /////////////////////////////////////////////////////
  // FormIO custom events
  // /////////////////////////////////////////////////////


	/* 
	* nome metodo "onCustomEvent"; method description: 
	* @param (data)
	* @returns 
	*/ 

  onCustomEvent(row) {
    if(row.type == 'geecoButton') {
      row.data.clicked = true
      this.listenForUpdates();

      if(false && $$.isLocal) { 
        this.openPopupWindow('http://localhost:17000/mudeopen/#/geeco-callback/' + this.formsService.id_istanza);
      }
      else {
        this.mudeopenFoBeService.getGeecoConfigurationUrl(this.formsService.id_istanza).subscribe((res) => {
          row.data.geecoSessionId = res.geecoSessionId;
          this.openPopupWindow(res.url);
        });
      }
    }
    else if(row.type == 'loccsiButton') {
      this.onCustomEventElse(); }

  } 


  onCustomEventElse() {
    const modal = this.modalService.open(LoccsiComponent, { size: 'xl', backdrop: 'static', keyboard: false, windowClass: 'alwaysScrollModal' });
    modal.componentInstance.comune = this.formsService.istanza?.comune;
    modal.componentInstance.onConfirm.subscribe((_data: any) => {
      modal.dismiss();

      if(_data && _data.nome_via) {
        const datagrid = this.stepSubquadroData?.dataGrid || [];
        datagrid.push({
          "n": ''+(_data.civico_num || _data.numnero || ''),
          "bis": "",
          "cap": (_data.cap || ''),
          "int": "",
          "piano": "",
          "scala": "",
          "sedime": (_data.tipo_via || ''),
          "interno2": "",
          "bisInterno": "",
          "secondario": (_data.civico_sub || '').replace('/', ''),
          "denominazione": (_data.nome_via || _data.indirizzo || '')
        });

        if(datagrid.length == 1) {
          datagrid[0].selezionare_se_si_tratta_di_indirizzo_principale = true; }

        if(!this.stepSubquadroData)
          this.stepSubquadroData = {};

        this.stepSubquadroData.dataGrid = datagrid;
        this._setSubmission(this.stepSubquadroData);

        this.refreshFormIO.emit({
          form: this.customForm,
          submission: this.submission
        });

      }
    });
  }


  // /////////////////////////////////////////////////////
  // private
  // /////////////////////////////////////////////////////

  _readOnlyModeOption: boolean = false;
  isStepInReadOnlyModeCACHED: boolean = undefined;
  get _isStepInReadOnlyMode(): boolean {
    
    // special case for TAB_LOCAL_1 from BO
    if((this.formsService.istanza as any)._BOView 
              && this.subQuadro?.tipo_quadro?.cod_tipo_quadro == 'TAB_LOCAL_1') {
      return false; }

    const _isStepInReadOnlyMode = this.isStepInReadOnlyMode || this._readOnlyModeOption;

    if(this.isStepInReadOnlyModeCACHED === undefined) {
      this.isStepInReadOnlyModeCACHED = _isStepInReadOnlyMode; }
    else if(this.isStepInReadOnlyModeCACHED != _isStepInReadOnlyMode) {
      this.isStepInReadOnlyModeCACHED = _isStepInReadOnlyMode
      setTimeout(x => { 
        this.onStepLoader(null, null) }, 500);
    }
    
    return this.isStepInReadOnlyModeCACHED;
  }

  get currentTab(): any { 
    return this.tabSubIndexSelected == -1?this.tabsMain[this.tabMainIndexSelected]:this.tabsSub[this.tabSubIndexSelected]; 
  }

  get tabSelection(): string {
    const title = [ this.tabsMain[this.tabMainIndexSelected]?.label, '. ', this.tabsMain[this.tabMainIndexSelected]?.title ].join('');
    const subtitle = [ this.tabsSub?' - ' + (this.tabsSub?.[this.tabSubIndexSelected]?.label || '') : '', ') ', this.tabsSub?.[this.tabSubIndexSelected]?.title || ''].join('');

    return  '<span class="formio-tab-title">' + title + '</span>'
            + '<span class="formio-tab-subtitle">' + (subtitle.replace(/[ )-]*/, '') === ''?'':subtitle) + '</span>';
  }


	/* 
	* nome metodo "findChangedKeys"; method description: 
	* @param (map1, map2)
	* @returns 
	*/ 

  findChangedKeys(map1, map2) {
    return this._findChangedKeys(map1, map2, this.subQuadro.tipo_quadro.cod_tipo_quadro);
  }

  currentForm: any;
  public successEmitter: EventEmitter<any> = new EventEmitter<any>();


	/* 
	* nome metodo "subtractExtraJsonDataKeys"; method description: 
	* @param (data: any)
	* @returns any
	*/ 

  subtractExtraJsonDataKeys(data: any): any {
    const _data = JSON.parse(JSON.stringify(data));
    Object.keys(this.stepManagerService.jsondata).forEach(key => {
      delete _data[key];
    });
    delete _data['ex_ISTANZA'];
    delete _data['ex_FASCICOLO'];
    delete _data['_URL_BACKEND'];
    delete _data['_OPTIONS'];
    delete _data['_ACTIVE_TAB'];
    delete _data['_ACTIVE_SUBTAB'];
    
    return _data;
  }

  // tabsLocked: boolean = false;
  // tabsLockedMessage: boolean = false;
  saveMessage: boolean = false;

  

	/* 
	* nome metodo "checkDependency"; method description: 
	* @param ()
	* @returns 
	*/ 
  dependencyMessageSUCCESS: string;
  dependencyMessageINFO: string;
  dependencyMessageWARNING: string;
  dependencyMessageERROR: string;

  checkDependency() {
    this.dependencyMessageSUCCESS = this.dependencyMessageINFO = this.dependencyMessageWARNING = this.dependencyMessageERROR = "";

    // show message if check is false
    ((typeof(this.currentTab.dependencyCheck) != 'string' 
            && this.currentTab.dependencyCheck) || []).forEach((dep) => {
      if(!this.checkJsExpression(dep.check))
        this['dependencyMessage'+(dep.type||'INFO')] += this._resolveJDATAexpr(dep.message) + "\r\n";
    });

    // set tab green checks
    this.setTabStates();
    // show nect button if it's all ok
    this._isStepValidationOK = this.validateAllForms();

  }

	/* 
	* nome metodo "validateTab"; method description: 
	* @param (tab: any, data: any)
	* @returns boolean
	*/ 

  validateTab(tab: any, data: any, quietMode: boolean = false): boolean {
    if(tab.disabled) { return true; }
    
    let res = true;

    // show error message if check fails
    ((typeof(tab.formValidation) === 'string' && tab.formValidation && [ { check: tab.formValidation } ]) 
            || tab.formValidation || []).forEach((validation) => {
      if(res && !(res = this.checkJsExpression(validation.check, data)) 
            && !quietMode 
            && tab === this.currentTab
            && validation.message) {
        this.validationMessage = this._resolveJDATAexpr(validation.message);
        this.validationMessageType = validation.type || 'ERROR';
      }
    });

    return res;
  }


	/* 
	* nome metodo "validateAllForms"; method description: 
	* @param ()
	* @returns boolean
	*/ 

  validateAllForms(): boolean {
    // check out the single steps
    let isOK: boolean = this._checkForms();

    if(!this.stepManagerService.jsondata[this.quadro.tipo_quadro?.cod_tipo_quadro])
      this.stepManagerService.jsondata[this.quadro.tipo_quadro?.cod_tipo_quadro] = {};

    this.stepManagerService.steps[this.stepIndex].isCompiled = 
      this.stepManagerService.jsondata[this.quadro.tipo_quadro?.cod_tipo_quadro]._COMPILED = isOK || this.quadroOptions?.readOnlyMode || false;

    return isOK && this.globalStepValidation();
  }

  _checkForms() {
    let isOK: boolean = true;

    this.tabsMain.forEach((tab) => {
      if(isOK) {
        if(!tab.subtabs) {
          isOK = this.isOptional(tab)
                  || tab.disabled 
                  || (tab.isValid /*&& this.validateTab(tab, this.stepManagerService.jsondata, true)*/); }
        else { // in deep validation
          tab.subtabs.forEach((subtab) => {
            if(isOK) {
              isOK = this.isOptional(tab)
                      || this.isOptional(subtab) 
                      || tab.disabled 
                      || subtab.disabled 
                      || (subtab.isValid /*&& this.validateTab(subtab, this.stepManagerService.jsondata, true)*/); }
          }); }
      }
    });

    return isOK;
  }

  isOptional(tab): boolean {
    if(!tab || !tab.optional) { return tab.optional = false; }

    if(typeof tab.optional == 'boolean')
      return tab.optional;

    return tab.optional = this.checkJsExpression(tab.optional);
  }


	/* 
	* nome metodo "setTabStates"; method description: 
	* @param ()
	* @returns 
	*/ 

  setTabStates() {
    this.validationMessage = null;

    this.tabsMain.forEach((tab) => {
      if(!tab.subtabs) {
        tab.isValid = this.isTabValid(tab); }
      else {
        tab.isValid = true;

        tab.subtabs.forEach((subtab) => {
          subtab.isValid = this.isTabValid(subtab);
          if(!subtab.isValid) { tab.isValid = false; }
        });
      }
    });
  }


	/* 
	* nome metodo "isTabValid"; method description: 
	* @param (tab: any)
	* @returns boolean
	*/ 

  isTabValid(tab: any): boolean {
    return this.stepManagerService.jsondata[tab.key]?.isValid 
      && this.stepManagerService.jsondata[tab.key]?.submit
      && this.validateTab(tab, this.stepManagerService.jsondata, false);
  }


  get isStepValidationOK(): boolean {
    return this.setStepCompleteState(this._isStepValidationOK || this.quadroOptions?.readOnlyMode, false) && !this.stepManagerService.stepsLocked;
  }

  doupdate() {
    this._setSubmission(this.stepSubquadroData);
  }

}
