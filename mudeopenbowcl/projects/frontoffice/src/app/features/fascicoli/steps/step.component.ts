import { id } from '@swimlane/ngx-datatable';
/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;

import { Component, Injector, OnDestroy, Inject, ViewChild, ElementRef } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { environment } from 'buildfiles/environment.local-rp-01';
import { Observable, Subscription } from 'rxjs';
import { catchError, takeUntil, tap } from 'rxjs/operators';
import { FormsService } from '../services/forms.service';
import { Quadro, StepConfig } from 'mudeopen-common';
import { FormUtils } from 'mudeopen-common';
import {
  AuthStoreService,
  MessageService
} from 'mudeopen-common';
import { MudeopenFoBeService } from 'mudeopen-common';
import * as model from 'mudeopen-common';
import { StepManagerService } from '../services/step-manager.service';
import { $ } from 'protractor';
import { checkBoolJsSafeExpression, checkJsSafeExpression } from '../../../shared/utils';




export declare interface OnStepInit {
  onStepLoader(jdata: any, isNew: boolean): boolean;
  onStepUpdate();
  canLeaveStep(): boolean;
}

@Component({
  selector: 'step-component',
  template: ''

})
export class StepComponent extends FormUtils implements OnStepInit, OnDestroy {

  public formsService: FormsService
  public authService: AuthStoreService
  public readOnlyMode: boolean;
  public quadroOptions: any = {};

  modalService: NgbModal
  stepManagerService: StepManagerService
  mudeopenFoBeService: MudeopenFoBeService
  messageService: MessageService;

  // quadro data
  stepName: string;
  stepIndex: number;
  istanzaReadOnlyMode: boolean; // global 
  loadTrigger: any;
  quadro: Quadro;
  quadroInTemplate: Quadro;
  subQuadro: model.QuadroVO | any = {};
  idTemplateQuadro: number;
  stepQuadroData: any = {};
  showButtonPrevious: boolean = false;
  lastValidatedStateChanged: boolean = false;

  loadObserve: Subscription = null;
  updateObserve: Subscription = null;
  saveObserve: Subscription = null;

  // default implementation
  onStepLoader(jdata: any, isNew: boolean): boolean { return false; }
  onStepUpdate() { return; }
  canLeaveStep(): boolean { return true; }

	/* 
	* nome metodo "constructor"; method description: 
	* @param (mudeopenFoBeService: MudeopenFoBeService, 
              private injector: Injector,
              injConfig: StepConfig)
	* @returns 
	*/ 

  constructor(_mudeopenFoBeService: MudeopenFoBeService, 
              injector: Injector,
              @Inject(model.CONFIG) injConfig: StepConfig) {
    super(_mudeopenFoBeService, null);

    this.mudeopenFoBeService = _mudeopenFoBeService;

    this.formsService = injector.get(FormsService);
    this.authService = injector.get(AuthStoreService);
    this.modalService = injector.get(NgbModal);
    this.stepManagerService = injector.get(StepManagerService);
    this.messageService = injector.get(MessageService);

    this._initializeStep(injConfig);
  }

  _initializeStep(injConfig: StepConfig) {
    this.stepIndex = injConfig.stepIndex;
    this.quadro = this.stepManagerService.steps[this.stepIndex];
    this.showButtonPrevious = !!injConfig.stepIndex || !injConfig.isNewInstance;
    this.idTemplateQuadro = this.quadro.id_template_quadro;
    this.subQuadro = {};
    this.stepName = this.quadro.json_configura_quadro.componentName;
    this.istanzaReadOnlyMode = this.readOnlyMode = injConfig.readOnly;

    // load
    this.loadObserve = this.stepManagerService.currentStepLoaderSub.pipe(takeUntil(this.destroy$)).subscribe((stepIndex) => { 
      if(!this.isThisStepAffected(stepIndex)) { return; }

      this.stepManagerService.getIstanzaTemplateQuadro(this.formsService.id_istanza, this.idTemplateQuadro, this.quadro.tipo_quadro.cod_tipo_quadro).subscribe((res) => {
        this.stepManagerService.currentStepTipoQuadro = this.mudeopenFoBeService.currentStepTipoQuadro = res?.template_quadro?.quadro?.tipo_quadro?.cod_tipo_quadro;

        this.showAllFormIOKeys(res.template_quadro.quadro);
        this.checkRemoveControls(res.template_quadro.quadro);

        this.stepManagerService.jsondata.ex_ISTANZA = this.formsService.istanza; 
        this.stepManagerService.jsondata.ex_FASCICOLO = this.formsService.fascicolo;
        
        // set step jsondata
        let data: any = this.stepManagerService.jsondata[this.mudeopenFoBeService.currentStepTipoQuadro];
        if(!data && res.template_quadro?.quadro?.json_default)
          data = JSON.parse(this._resolveJDATAexpr(res.template_quadro?.quadro?.json_default));
        this.setJDataQuadro(data || {});

        // update step modificable flag
        this.stepManagerService.steps[stepIndex].canUserModify = res.quadro_modificabile;
        this.quadroOptions = JSON.parse(this._resolveJDATAexpr(res.json_data_subquadro || '{}'));
        this.readOnlyMode = this.quadroOptions.readOnlyMode || this.istanzaReadOnlyMode || !this.stepManagerService.steps[stepIndex].canUserModify;
        this.lastValidatedStateChanged = !!this.stepQuadroData._VALIDATION_TIME;

        $$.setToolboxWindowPopupValue('Quadro', this.stepQuadroData, this.quadro.tipo_quadro?.cod_tipo_quadro);

        if((this.onStepLoader(this.stepQuadroData, !data) && !data)) {
          this.storeJsonData();
        }

        this.quadroInTemplate = res.template_quadro.quadro;
        this.stepManagerService.raiseStepLoaded(stepIndex, this.quadroInTemplate);
      });
    });

    // track data realod
    this.updateObserve = this.stepManagerService.currentStepUpdateSub.pipe(takeUntil(this.destroy$)).subscribe((stepIndex) => { 
      if(!this.isThisStepAffected(stepIndex) || !this.quadro?.id_quadro) { return; }

    });

    //save step data before switch to a different one
    this.saveObserve = this.stepManagerService.currentStepSaveSub.pipe(takeUntil(this.destroy$)).subscribe((stepIndex) => { 
      if(!this.isThisStepAffected(stepIndex)) { return; }

      this.canLeaveStep(); // handle false
    });
    
  }

  get isStepInReadOnlyMode(): boolean {
    return this.istanzaReadOnlyMode 
            || this.readOnlyMode 
            || this.stepManagerService?.steps[this.stepIndex]?.setReadOnly;
  }

  setJDataQuadro(val: any) {
    this.stepManagerService.jsondata[this.quadro.tipo_quadro?.cod_tipo_quadro] = this.stepQuadroData = val;      
  }

  set stepSubquadroData(val: any) {
    this.stepManagerService.jsondata[this.subQuadro.tipo_quadro.cod_tipo_quadro] 
            = this.stepManagerService.jsondata_backup[this.subQuadro.tipo_quadro.cod_tipo_quadro] = val;
  }

  get stepSubquadroData(): any {
    return this.stepManagerService.jsondata[this.subQuadro.tipo_quadro.cod_tipo_quadro]
            || this.stepManagerService.jsondata_backup[this.subQuadro.tipo_quadro.cod_tipo_quadro];
  }

  _findChangedKeys(map1, map2, keypath) {
    if(!map1 || !map2) { return; }

    let keyFound;
    Object.keys(map1).forEach(key1 => {
      keyFound = keyFound || this._findChangedSubKeys(map1, key1, map2, keypath);
    });

    return keyFound;
  }

  _findChangedSubKeys(map1, key1, map2, keypath) {
    let keyFound;
    Object.keys(map2).forEach(key2 => {
      if(key1 === 'isValid' || keyFound) { 
        return; }

      if(typeof map1[key1] === 'object' && typeof map2[key2] === 'object') {
        keyFound = this._findChangedKeys(map1[key1], map2[key2], keypath + this._getKey(key1)); 
      } // associative arrays
      else if(key1 == key2 && map1[key1] != map2[key2]) {
        keyFound = keypath + this._getKey(key2) + ' == ' + JSON.stringify(map2[key2], null, 4); 
      }
    });        

    return keyFound;
  }

  _getKey(key1) {
    return (!!key1.match(/^[0-9]{1,}$/)?('['+key1+']'):('.' + key1));
  }


  isThisStepAffected(stepIndex: number) {
    return stepIndex == this.stepIndex;
  }

  get basepath(): string {
    return environment?.basePath || '';
  }


  get tipologiaIstanzaSelected(): string {
    if(!this.formsService.istanza.tipologia_istanza) { return; }
    return this.formsService.profilo.tipoIstanzaList.filter(x => x.id == this.formsService.istanza.tipologia_istanza.id)[0].value;
  }

  get tipologiaPresentatoreSelected(): string {
    if(!this.formsService.istanza.tipologia_presentatore) { return; }
    return this.commonConstants.tipologiePresentatore.filter(x => x.id == this.formsService.istanza.tipologia_presentatore.id)[0].value;
  }

  /* to be dismissed: logic changed... to be removed */

	/* 
	* nome metodo "setStepCompleteState"; method description: 
	* @param (isStepOk: boolean)
	* @returns 
	*/ 

  setStepCompleteState(isStepOk: boolean, setCompiledFlag: boolean = true) {
    if(isStepOk === null || isStepOk === undefined) { return; }

    if(isStepOk != this.lastValidatedStateChanged) {
      this.lastValidatedStateChanged = isStepOk; }

    if(setCompiledFlag && this.stepManagerService.jsondata && this.stepManagerService.jsondata[this.quadro.tipo_quadro?.cod_tipo_quadro]) {
      this.stepQuadroData['_COMPILED_AT'] = Math.floor((new Date()).getTime()); }

    return isStepOk;
  }


	/* 
	* nome metodo "storeJsonData"; method description: 
	* @param ()
	* @returns 
	*/ 

  storeJsonData(isStepModified: boolean = false, isStepValid: boolean = false) {
    this.storeJsonDataDetailed(null, true, isStepValid)?.subscribe((res) => {
      return;
    } );
  }


  /* 
	* nome metodo "storeJsonDataDetailed"; method description: 
	* @param (jsondata: any = null, trackDataChange: boolean = false)
	* @returns Observable<any>
	*/ 

  storeJsonDataDetailed(jsondata: any = null, stepDataModified: boolean = false, isStepValidated: boolean = false, jsondataSubQuadro: any = null, setQuadroAsValidated: any = null, jDataKeys2Delete: any = null): Observable<any> {
      // three save methods:
      //  1. just mainQuadroData not validated yet
      //  2. mainQuadroData + subQuadro not validated yet
      //  3. set completed mainQuadroData + validation information (stepForward==true)
      const dataQuadro: model.IstanzaTemplateQuadroRequest = {
          idIstanza: this.formsService.id_istanza,
          idTemplateQuadro: this.idTemplateQuadro,
          jsonDataQuadro: setQuadroAsValidated? null : JSON.stringify(jsondata || this.stepQuadroData),
          id_sub_quadro: !jsondataSubQuadro?null:this.subQuadro?.id_quadro,
          cod_sub_quadro: !jsondataSubQuadro?null:this.subQuadro?.tipo_quadro?.cod_tipo_quadro,
          jsondata_subquadro: setQuadroAsValidated || !jsondataSubQuadro? null : JSON.stringify(jsondataSubQuadro),
          jsondata_modificato: stepDataModified,
          main_quadro_validated: this.lastValidatedStateChanged,
          jdata_keys_to_delete: jDataKeys2Delete || null
        };
      
      $$.setToolboxWindowPopupValue('Quadro', jsondata || this.stepQuadroData, this.quadro.tipo_quadro?.cod_tipo_quadro);
      if(jsondataSubQuadro) {
        $$.setToolboxWindowPopupValue('SottoQuadro', jsondataSubQuadro, this.subQuadro?.tipo_quadro?.cod_tipo_quadro); }

      return this.stepManagerService.salvaJsonDataQuadro(dataQuadro, this.stepIndex/*, isStepValidated*/)?.pipe(
              tap(x => {
                this._reloadAll(x.istanza.json_data);
              }
          ),
          catchError((err, caught) => {
            this.reloadStep();
            return null;
          })
        );
  }

  updateStepData(): Observable<any> {
    return this.stepManagerService.loadJDataIstanza(this.formsService.id_istanza, this.quadro.id_quadro)?.pipe(
              tap(info => {
                this._reloadAll(info.json_data, info.stato_istanza);
              }));
  }

  _reloadAll(jsondata: any, idIstanzaStatus: any = null) {
      // updates all jdata refences
      this.formsService.istanza.json_data = jsondata;
      this.stepManagerService.setJData(JSON.parse(this.formsService.istanza.json_data), 
                                      this.formsService.fascicolo,
                                      this.formsService.istanza);
      this.stepQuadroData = this.stepManagerService.jsondata[this.quadro.tipo_quadro?.cod_tipo_quadro];

      //update istanza status
      if(idIstanzaStatus) {
        this.formsService.istanza.stato_istanza.id = idIstanzaStatus; }

      this.stepManagerService.setEditableSteps();
      //dismissed: this.stepManagerService.raiseStepLoaded(this.stepIndex, this.quadro);
  }

  reloadStep() {
    this.stepManagerService.raiseStepReload(this.stepIndex);
  }

	/* 
	* nome metodo "onStepForward"; method description: 
	* @param ()
	* @returns 
	*/ 

  onStepForward() {
    let isTemplateUpdate: boolean = false;
    if(!this.istanzaReadOnlyMode && this.stepManagerService.jsondata?._NEW_TEMPLATE === '') {
      delete this.stepManagerService.jsondata._NEW_TEMPLATE; 
      isTemplateUpdate = true;
    }

    if(!isTemplateUpdate && (this.istanzaReadOnlyMode 
              // removed condition in order to let anyone validate the step:|| !this.stepManagerService.canUserChangeQuadro(this.stepIndex, this.authService.getUser().contatto.anagrafica.codiceFiscale) 
              || (this.stepManagerService.steps[this.stepIndex]?.isValidated 
                  && this.stepQuadroData?._VALIDATION_TIME))
                  // in case the quadro is declared "readOnlyMode" from config reset validation time
                  && !this.quadroOptions.readOnlyMode) {
			  this.stepManagerService.goOn(this.stepIndex); }
    else { 
      // update validation time info
      this.setJsonDataValidation().subscribe(res => {
        this.stepManagerService.goOn(this.stepIndex);
	  } ); }
  }

  setJsonDataValidation(): Observable<any> {
    return this.storeJsonDataDetailed(null, true, true, null, true);
  }

	/* 
	* nome metodo "onStepBack"; method description: 
	* @param ()
	* @returns 
	*/ 

  onStepBack() {
    this.stepManagerService.goBack(this.stepIndex);
  }


	/* 
	* nome metodo "checkExpression"; method description: 
	* @param (expression: string, scoped_data: any = null, scopename = '')
	* @returns boolean
	*/ 

  checkJsExpression(expression: string, scoped_data: any = null, scopename = ''): boolean {
    if(!expression) { return true; }

    return checkBoolJsSafeExpression(expression, 
                                     this.stepManagerService.jsondata, 
                                     this.stepQuadroData,
                                     scoped_data);
  }


	/* 
	* nome metodo "evalExpression"; method description: 
	* @param (expression: string, scoped_data: any = null)
	* @returns any
	*/ 

  evalJsExpression(expression: string, scoped_data: any = null, wapi: any = {}): any {
    return checkJsSafeExpression(expression, 
              this.stepManagerService.jsondata, 
              this.stepQuadroData,
              scoped_data, 
              wapi);
  }

	/* 
	* nome metodo "howManyAttachsAllowed"; method description: 
	* @param (attach: any)
	* @returns 
	*/ 

  howManyAttachsAllowed(attach: any) {
    return this.evalJsExpression(attach.espressione_ripetibilita) || (!attach.ripetibile?1:undefined);
  }

  _resolveJDATAexpr(defaultJson: string): string {
    const JDATAScopeStart = '{{';
    const JDATAScopeEnd = '}}';
    
    while(defaultJson.indexOf(JDATAScopeStart) > -1) {
      const i1 = defaultJson.indexOf(JDATAScopeStart);
      const i2 = defaultJson.indexOf(JDATAScopeEnd, i1);

      let obj = this.evalJsExpression(defaultJson.substring(i1 + JDATAScopeStart.length, i2), this.stepManagerService.jsondata);
      if(obj != null && typeof obj=='object') {
        obj = JSON.stringify(obj); }

      defaultJson = defaultJson.substring(0, i1)
              + (((typeof obj == 'undefined' || obj == null) && 'null') || obj) 
              + defaultJson.substring(i2 + JDATAScopeEnd.length);
    }

    return defaultJson;
  }

  // global step validation
  globalStepValidation() {
    let isOK: boolean = true;

    (this.quadro as any).message = null;
    ((typeof(this.quadro.json_configura_quadro.stepValidations) === 'string' && this.quadro.json_configura_quadro.stepValidations && [ { check: this.quadro.json_configura_quadro.stepValidations } ]) 
          || this.quadro.json_configura_quadro.stepValidations || []).forEach((chk) => {
      if(isOK && !(isOK = this.checkJsExpression(chk.check))) {
        (this.quadro as any).message = {
          msg: this._resolveJDATAexpr(chk.message),
          type: chk.type,
          title: chk.title || ''
        }
      }
    });

    return isOK;
  }

	/* 
	* nome metodo "ngOnDestroy"; method description: 
	* @param ()
	* @returns void
	*/ 

  ngOnDestroy(): void {
    if(this.loadObserve) { this.stepManagerService.currentStepLoaderSub.next(-1); this.loadObserve.unsubscribe(); }
    if(this.updateObserve) { this.stepManagerService.currentStepUpdateSub.next(-1); this.updateObserve.unsubscribe(); }
    if(this.saveObserve) { this.stepManagerService.currentStepSaveSub.next(-1);this.saveObserve.unsubscribe(); }
  }

  getStatoIstanza(): string {
    return this.formsService.istanza?.stato_istanza?.id;
  }

  get isIstanzaDepositata(): boolean {
    return !(this.formsService.istanza?.stato_istanza?.id == 'BZZ'
          || this.formsService.istanza?.stato_istanza?.id == 'RPA'
          || this.formsService.istanza?.stato_istanza?.id == 'DFR'
          || this.formsService.istanza?.stato_istanza?.id == 'FRM');

  }

  getFloatFromItalianString(value: string): number {
    return parseFloat((value||'0').replace(',', '.'));
  }

  popupWindowPopup: any;
  openPopupWindow(url, width: number = 0, height: number = 0, left: number = 0, top: number = 0) {
    setTimeout(_ => {
      let sw = screen.availWidth;
      let sh = screen.availHeight;
      if(width) { sw = Math.min(sw, width); }
      if(height) { sh = Math.min(sh, height); }
      if(!left) { left = (sw - screen.availWidth) / 2; }
      if(!top) { top = (sh - screen.availHeight) / 2; }
      
      this.popupWindowPopup = window.open(url,'geecoWindow','left='+(left || 0)+',width='+(width || sw)+',top='+(top || 0)+',height='+(height || sh));

      setTimeout(_ => {
        let isBlocked = !this.popupWindowPopup || this.popupWindowPopup.closed || typeof this.popupWindowPopup.closed=='undefined';
        this.showBlockMessage(isBlocked)
      }, 500);
    }, 100);
  }

  showBlockMessage(isBlocked) {
    if(!isBlocked)  { 
      try {
        this.popupWindowPopup.focus();
      }
      catch (e) {
        isBlocked = true;
      }
    }  

    if(isBlocked) {
      alert('Per utilizzare la funzionalità selezionata è necessario disabilitare il blocco dei pop-up dal browser.'); }
  }

  listenForUpdates() {
    window['myUpadteEvent'] = false;
    this._listenForUpdates();
  }

  _listenForUpdates() {
    setTimeout(_ => {
      if(window['myUpadteEvent']) {
        this.onStepUpdate(); // update event from popup
      }
      else {
        this._listenForUpdates(); // listen again
      }
    }, 1000);
  }

  checkRemoveControls(quadro) {
    if(!this.formsService.removeAllFormIOControls 
          || quadro?.flg_tipo_gestione != 'F'
          || !quadro?.json_configura_quadro) {
      return; }

    for(let i=1; quadro.json_configura_quadro.match(/"name": "([a-zA-Z])/); i++) {
      quadro.json_configura_quadro = quadro.json_configura_quadro.replace(/"name": "([a-zA-Z])/, '"name": "_'+i+'_$1');
    }

    for(let i=1; quadro.json_configura_quadro.match(/"key": "([a-zA-Z])/); i++) {
      quadro.json_configura_quadro = quadro.json_configura_quadro.replace(/"key": "([a-zA-Z])/, '"key": "_'+i+'_$1');
    }

    quadro.json_configura_quadro = quadro.json_configura_quadro.replace(/"validate":/g, '"_validate_":');
    quadro.json_configura_quadro = quadro.json_configura_quadro.replace(/"customConditional":/g, '"_customConditional_":');
    quadro.json_configura_quadro = quadro.json_configura_quadro.replace(/"conditional":/g, '"_conditional_":');
    quadro.json_configura_quadro = quadro.json_configura_quadro.replace(/"defaultValue":/g, '"_defaultValue_":');
  }

  showAllFormIOKeys(quadro) {
    if(!this.formsService.showAllFormIOKeys 
          || quadro?.flg_tipo_gestione != 'F'
          || !quadro?.json_configura_quadro) {
      return; }

    let json = this._showAllFormIOKeys(JSON.parse(quadro.json_configura_quadro));
    quadro.json_configura_quadro = JSON.stringify(json);
  }

  _showAllFormIOKeys(json) {
    if(!json) { return json; }
    Object.keys(json).forEach(jsonKey => {
      if(typeof json[jsonKey] != 'object') { return; }

      json[jsonKey] = this._showAllFormIOKeys(json[jsonKey]);
    });

    if(json.inputType == 'radio' && json.name && json.value) {
      json.label += '<i class="keyClass">'+json.name
                    +' == \'<b class="keyValue">'+json.value+'</b>\'</i>'; 
    }
    else if(json.key && json.hideLabel && json.type != 'panel') {
      json.label = '<i class="keyClass">'+json.key
                    +(!json.value?'':' == \'<b class="keyValue">'+json.value+'</b>\'')+'</i>'; 
      json.hideLabel = false;
    }
    else if(json.key && json.label) {
      json.label += '<i class="keyClass">'+json.key
                    +(!json.value?'':' == \'<b class="keyValue">'+json.value+'</b>\'')+'</i>'; 
    }

    if(json.customConditional) {
      json.hideLabel = !(json[json.type=='panel'?'title':'label'] = (json[json.type=='panel'?'title':'label']||'')+'<i class="keyExpr">'+json.customConditional+'</i>');  }
    if(json.conditional && json.conditional.show && json.conditional.when && json.conditional.eq) {
      json.hideLabel =  !(json[json.type=='panel'?'title':'label'] = (json[json.type=='panel'?'title':'label']||'')+'<i class="keyExpr">'+(json.conditional.when+(json.conditional.show?'==':'!=')+json.conditional.eq)+'</i>');  }

    return json;
  }

}

