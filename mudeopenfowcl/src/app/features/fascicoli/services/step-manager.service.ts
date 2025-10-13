/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { Location } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Injectable, OnDestroy } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { Quadro } from 'mudeopen-common';
import { DefaultService, IstanzaTemplateQuadroRequest, IstanzaTemplateQuadroVO, QuadroVO, TemplateResponse } from 'mudeopen-common';
import { MudeopenFoBeService } from 'mudeopen-common';





@Injectable()
export class StepManagerService implements OnDestroy {
  steps: Quadro[] = [];
  mandatoryPM: boolean;

  // raised when the step needs to be loaded
  currentStepLoaderSub = new Subject<number>();
  // raised when the step needs to be re-loaded
  currentStepUpdateSub = new Subject<number>();
  // raised when the step needs to be saved
  currentStepSaveSub = new Subject<number>();
  // raised when the step load has been completed
  currentStepLoadedSub = new Subject<any>();

  // raised when the "AVANTI" button has been clicked
  avantiClickedSub = new Subject<any>();
  // raised when the "INDIETRO" button has been clicked
  indietroClickedSub = new Subject<number>();
  // raised when the completed state has been set
  stepCompletedSub = new Subject<{
    stepComponent: string;
    completed: boolean;
    idQuadro: number;
  }>();

  jsondata: any = {};
  jsondata_backup: any = {};

  // this flag is used to prevent the stepper to move while a step has data not saved yet
  stepsLocked: boolean = false;
  stepsLockedMessage: boolean = false;

  public currentStepTipoQuadro: string;
  get xRequestID(): string {
    if(!this.currentStepTipoQuadro) { return null; }

    return this.currentStepTipoQuadro + '~' + Date.now();
  }



	/* 
	* nome metodo "constructor"; method description: 
	* @param (private http: HttpClient, 
          private config: ConfigService,
          private _location: Location,
          private mudeopenFoBeService: MudeopenFoBeService,
          private defaultService: DefaultService)
	* @returns 
	*/ 

  constructor(private http: HttpClient, 
          private _location: Location,
          private mudeopenFoBeService: MudeopenFoBeService,
          private defaultService: DefaultService) {
  }

  setJData(jdata: any, fascicolo: any, istanza: any) {
    this.jsondata_backup = this.jsondata = jdata;
    
    if(istanza.istanza_riferimento?.json_data && typeof istanza.istanza_riferimento?.json_data === 'string')
      istanza.istanza_riferimento.json_data = JSON.parse(istanza.istanza_riferimento?.json_data || '{}');
    (istanza.istanze_collegate||[]).forEach(ist => { if(typeof ist.json_data === 'string') ist.json_data = JSON.parse(ist?.json_data || '{}'); });

    $$.setToolboxWindowPopupValue('JData', JSON.parse(JSON.stringify(this.jsondata)));
    this.jsondata.ex_FASCICOLO = fascicolo;
    $$.setToolboxWindowPopupValue('JFascicolo', this.jsondata.ex_FASCICOLO);
    this.jsondata.ex_ISTANZA = istanza;
    $$.setToolboxWindowPopupValue('JIstanza', this.jsondata.ex_ISTANZA);
  }

	/* 
	* nome metodo "getQuadriByProcedimento"; method description: 
	* @param (idTipoIstanza: number, idTipoIntervento: number)
	* @returns Observable<TemplateResponse>
	*/ 

  getQuadriByProcedimento(idTipoIstanza: string, idTipoIntervento: string = null, boTemplateVersionOverride: string = null, id_istanza: number = null): Observable<TemplateResponse> {
    this.mudeopenFoBeService.logApi('getQuadriByProcedimento', [ idTipoIstanza, null, null, null, boTemplateVersionOverride, id_istanza ] );
    return this.defaultService.loadTemplateQuadriByCodeTemplate(idTipoIstanza, null, null, null, boTemplateVersionOverride, id_istanza)
          .pipe(tap(x => this.mudeopenFoBeService.logApiResult('getQuadriByProcedimento', x)), map((res) => res[0]));
  } 

	/* 
	* nome metodo "getIstanzaTemplateQuadro"; method description: 
	* @param (idIstanza: number, idTemplateQuadro: number)
	* @returns Observable<IstanzaTemplateQuadroVO>
	*/ 

  getIstanzaTemplateQuadro(idIstanza: number, idTemplateQuadro: number, tipoQuadro: string): Observable<IstanzaTemplateQuadroVO> {
    this.mudeopenFoBeService.logApi('getIstanzaTemplateQuadro', [ idIstanza, idTemplateQuadro, null, tipoQuadro ] );
    this.currentStepTipoQuadro = tipoQuadro;
    return this.defaultService.getIstanzaTemplateQuadro(idIstanza, idTemplateQuadro, null, this.currentStepTipoQuadro &&  this.xRequestID)
              .pipe(tap((x: any) => { 
      this.mudeopenFoBeService.logApiResult('getIstanzaTemplateQuadro', x);

      this.jsondata = (x?.istanza?.json_data && JSON.parse(x.istanza.json_data)) || {};
      if(this.jsondata?.istanza_riferimento?.json_data && typeof this.jsondata.istanza_riferimento.json_data === 'string')
        this.jsondata.istanza_riferimento.json_data = JSON.parse(this.jsondata?.istanza_riferimento?.json_data || '{}');
      (this.jsondata?.istanze_collegate||[]).forEach(ist => { if(typeof ist.json_data === 'string') ist.json_data = JSON.parse(ist?.json_data || '{}'); });
    }));
  }


  // invoked from "compilaIstanzaComponent" --- SUPERSEDES 'loadTemplateQuadriByIdTemplateQuadro'
  loadQuadroById(idQuadro: string, idIstanza: number, idFascicolo: number): Observable<QuadroVO> {
    this.mudeopenFoBeService.logApi(' loadQuadroById', [ idQuadro, null, this.xRequestID, null, idIstanza, idFascicolo] );
    return this.defaultService.loadQuadroById(parseInt(idQuadro.replace(/[\"~]/g, ''), 10), null, this.xRequestID, null, idIstanza, idFascicolo).pipe(tap(x => this.mudeopenFoBeService.logApiResult('loadQuadroById', x) ));
  }

	/* 
	* nome metodo "salvaJsonDataQuadro"; method description: 
	* @param (data: IstanzaTemplateQuadroRequest, update = false)
	* @returns Observable<IstanzaTemplateQuadroVO>
	*/ 

  salvaJsonDataQuadro(data: IstanzaTemplateQuadroRequest, stepIndex: number, allowEmptyData: boolean = false): Observable<IstanzaTemplateQuadroVO> {
    if(!allowEmptyData && data.jsonDataQuadro === '{}') { return;}

    this.mudeopenFoBeService.logApi('saveIstanzaTempleteQuadro', [ null, this.xRequestID, data ] );
    return this.defaultService.saveIstanzaTempleteQuadro(null, this.xRequestID, null, data)
      .pipe(tap(x => { 
        this.mudeopenFoBeService.logApiResult('saveIstanzaTempleteQuadro', x);
        this.raiseStepUpdate(stepIndex);
      }));
  }

  // this is the essential data needed to update the instance 
  loadJDataIstanza(idIstanza: number, idQuadro?: number, tipoQuadro?: string, isMandatoryPM?: boolean, requestType?: string): Observable<QuadroVO> {
    $$.logApi(' loadJDataIstanza', [ idIstanza, null, this.xRequestID, null, idQuadro, tipoQuadro, isMandatoryPM, requestType ] );
    return this.defaultService.loadJDataIstanza(idIstanza, null, this.xRequestID, null, idQuadro, tipoQuadro, isMandatoryPM, requestType)
                                  .pipe(tap(x => { 
                                    this.mudeopenFoBeService.logApiResult('loadJDataIstanza', x);
                                  }));
  }



	/* 
	* nome metodo "setCompleted"; method description: 
	* @param (
    stepComponent: string,
    completed: boolean,
    idQuadro: number = null
  )
	* @returns 
	*/ 

  setCompleted(
    stepComponent: string,
    completed: boolean,
    idQuadro: number = null
  ) {
    this.stepCompletedSub.next({ stepComponent, completed, idQuadro });
  }


	/* 
	* nome metodo "goOn"; method description: 
	* @param (currentStepIndex: number)
	* @returns 
	*/ 

  goOn(currentStepIndex: number) {
    setTimeout(_ => 
      this.avantiClickedSub.next());
  }


	/* 
	* nome metodo "goBack"; method description: 
	* @param (currentStepIndex: number)
	* @returns 
	*/ 

  goBack(currentStepIndex: number) {
    for(let j = currentStepIndex - 1; j >= -1 ; j--) {
      if(j < 0) {
        this._location.back(); }
      else if(this.steps[j].editable) {
        //DISABLED because duplicated in 'stepSelectionChange': this.setActiveStep(j);
        this.indietroClickedSub.next(j);
        break;
      }
	}
  }


	/* 
	* nome metodo "setActiveStep"; method description: 
	* @param (stepIndex: number)
	* @returns 
	*/ 

  currentStepPoller: any;
  setActiveStep(stepIndex: number) {
    this.currentStepLoaderSub.next(stepIndex);

    // set polling in order to periodically reload the step essential information
    this.currentStepPoller && clearInterval(this.currentStepPoller);
    this.currentStepPoller = setInterval(_ => {
      this.currentStepUpdateSub.next(stepIndex);
    }, 60000);
  }


	/* 
	* nome metodo "raiseStepSave"; method description: 
	* @param (stepIndex: number)
	* @returns 
	*/ 

  raiseStepReload(stepIndex: number) {
    this.currentStepLoaderSub.next(stepIndex);
  }

  raiseStepSave(stepIndex: number) {
    this.currentStepSaveSub.next(stepIndex);
  }

  raiseStepUpdate(stepIndex: number) {
    this.currentStepUpdateSub.next(stepIndex);
  }

  raiseStepLoaded(stepIndex: number, quadro: any) {
    this.currentStepLoadedSub.next({ index: stepIndex, quadro: quadro});
  }

	/* 
	* nome metodo "ngOnDestroy"; method description: 
	* @param ()
	* @returns void
	*/ 

  ngOnDestroy(): void {
    this.currentStepLoaderSub.unsubscribe();
    this.currentStepUpdateSub.unsubscribe();
    this.currentStepSaveSub.unsubscribe();
    this.currentStepLoadedSub.unsubscribe();
    this.avantiClickedSub.unsubscribe();
    this.indietroClickedSub.unsubscribe();
    this.stepCompletedSub.unsubscribe();
  }
  

	/* 
	* nome metodo "setEditableSteps"; method description: 
	* @param ()
	* @returns number
	*/ 

  setEditableSteps(): number {
    let lastEditableTime: any = 1;
    let lastValidIndex: number = 1000;
    let forceFirst: boolean = false;
    let lastEditableIndex: number = 0;

    this.steps.forEach((step, index) => {
      let currentStepTime = index <= lastValidIndex? this.jsondata[step.tipo_quadro?.cod_tipo_quadro]?._VALIDATION_TIME : null;
      step.completed = !!currentStepTime;
      step.isCompiled = step.completed && ''+this.jsondata[step.tipo_quadro?.cod_tipo_quadro]?._COMPILED == 'true' || false;

      if(index == 0 && !currentStepTime) {
        forceFirst = true; }

      if((step as any).proprieta?.readOnlyMode) {
        currentStepTime = lastEditableTime+1; }

      step.editable = !!currentStepTime;
      if(step.editable  
              && (lastEditableTime === 1 || lastEditableTime <= currentStepTime)
              && index <= lastValidIndex) {

        lastEditableIndex = index;
        lastEditableTime = currentStepTime;
      }
      else {
		    lastValidIndex = index; }
    })

    lastEditableIndex = forceFirst ? 0 : lastEditableIndex;
    this.steps[lastEditableIndex].editable = true;

    this.steps.forEach((step, index) => {
      this._setEditable(step, index, forceFirst?0:lastEditableIndex + 1);
    });

    return lastEditableIndex;
  }

  _setEditable(step, index, lastEditableIndex) {
      // force enabled special steps
      if(step.json_configura_quadro.componentName === 'AllegatiComponent') {
        step.editable = true;  }

      // set already validated steps
      step.isValidated = index<lastEditableIndex;
  }

  getStepJData(index: number): any {
    if(!this.jsondata) {
      return null; }

    return this.jsondata[this.steps[index]?.tipo_quadro?.cod_tipo_quadro];
  }

  getModifiableStepIndex(): number {
    if(!this.steps.length) { return 0; }

    if(this.jsondata._NEW_TEMPLATE === '' && this.jsondata[this.steps[0].tipo_quadro?.cod_tipo_quadro]) {
      this.jsondata[this.steps[0].tipo_quadro?.cod_tipo_quadro]._VALIDATION_TIME = Math.floor((new Date()).getTime());
      this.jsondata[this.steps[0].tipo_quadro?.cod_tipo_quadro].isValidated = false;
      this.setEditableSteps();
      return 0;
    }

    const last_compiled = this.setEditableSteps();
    return Math.min(last_compiled + (this.steps[last_compiled].isCompiled? 1 : 0), this.steps.length-1);
  }

  canUserChangeQuadro(stepIndex: number, cf: string): boolean {
    const jData = this.getStepJData(stepIndex); 

    return jData?._USER_MOD_LOCK == cf || !jData?._USER_MOD_LOCK || (this.steps[stepIndex].canUserModify 
          && Date.now() - parseInt(jData?._USER_MOD_TIME, 10) > parseInt(jData?._USER_MOD_LEASE, 10));
  }

}
