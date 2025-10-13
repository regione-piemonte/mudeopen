/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/


declare var $$: any;


import { CdkStepper, StepperSelectionEvent } from '@angular/cdk/stepper';
import { ChangeDetectorRef, Component, Injector, Input, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { takeUntil } from 'rxjs/operators';
import { BaseComponent } from 'mudeopen-common';
import { FormsService } from '../services/forms.service';
import { EmptyRouteComponent } from 'mudeopen-common';
import { CONFIG } from 'mudeopen-common';

import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { StepConfig } from 'mudeopen-common';
import { AuthStoreService, MessageService } from 'mudeopen-common';
import { MudeopenFoBeService } from 'mudeopen-common';
import * as model from 'mudeopen-common';
import { StepManagerService /*, StepEventsService*/ } from '../services';
import * as steps from '../steps';
import { checkBoolJsSafeExpression, checkJsSafeExpression } from '../../../shared/utils';
import { DocxTesterComponent } from '../components/docx-tester/docx-tester.component';
import { ListaStatiComponent } from '../lista-stati/lista-stati.component';

import html2canvas from 'html2canvas';

interface ClipboardItem {
  readonly types: string[];
  readonly presentationStyle: "unspecified" | "inline" | "attachment";
  getType(): Promise<Blob>;
}

interface ClipboardItemData {
  [mimeType: string]: Blob | string | Promise<Blob | string>;
}

declare var ClipboardItem: {
  prototype: ClipboardItem;
  new (itemData: ClipboardItemData): ClipboardItem;
};

@Component({
  selector: 'fascicolo-dettaglio',
  templateUrl: './fascicolo-dettaglio.component.html',
  styleUrls: ['./fascicolo-dettaglio.component.scss']
})
export class IstanzaDettaglioComponent extends BaseComponent implements OnInit {

  @Input() idIstanza;
  @ViewChild('stepper') stepper: CdkStepper;

  title = 'Nuova fascicolo - MUDE';

  stepInjectors: Injector[] = [];
  stepperReady = false;

  stepperIndex: number = 1;

  componentsForStepper = {
    SoggettiCoinvoltiComponent: steps.SoggettiCoinvoltiComponent,
    SoggettoAbilitatoComponent: steps.SoggettoAbilitatoComponent,
    RiferimentoIstanzeComponent: steps.RiferimentoIstanzeComponent,
    AllegatiComponent: steps.AllegatiComponent,
    PresentaIstanzaComponent: steps.PresentaIstanzaComponent,
    
    PagamentiComponent: steps.PagamentiComponent,

    CompilaIstanzaComponent: steps.CompilaIstanzaComponent,
    FormioComponent: steps.FormioComponent,
    EmptyRouteComponent: EmptyRouteComponent,
  };

  entryPoint: string;
  isBOEnvironment: boolean = false;


	/* 
	* nome metodo "constructor"; method description: 
	* @param (
            private cdr: ChangeDetectorRef,
            private messages: MessageService,
            private router: Router,
            private stepManagerService: StepManagerService,
            public formsService: FormsService,
            private injector: Injector, 
            private mudeopenFoBeService: MudeopenFoBeService)
	* @returns 
	*/ 

  constructor(
            private cdr: ChangeDetectorRef,
            private authService: AuthStoreService,
            private modalService: NgbModal,
            private router: Router,
            public stepManagerService: StepManagerService,
            public formsService: FormsService,
            private injector: Injector, 
            private mudeopenFoBeService: MudeopenFoBeService,
            private activatedRoute: ActivatedRoute) {
    super();
    stepManagerService.stepsLocked = false;
    stepManagerService.stepsLockedMessage = false;

    this.mudeopenFoBeService.getSessionStorageInfoProfilo().then(x => this.formsService.profilo = x );
    this.isBOEnvironment = window.location.href.indexOf('/mudeopen/bo') > -1;

    if(this.activatedRoute.snapshot.paramMap.get('id')) {
      this.entryPoint = 'view-instance';
      this.formsService.istanza.id_istanza = parseInt(this.activatedRoute.snapshot.paramMap.get('id'), 10);
      this.formsService.removeAllFormIOControls = this.activatedRoute.snapshot.paramMap.get('noControls') == 'yes';
    }
    else {
      this.doInit(); }
  }

  doInit() {
    this.entryPoint = this.formsService.pullCurrentContextName;
    if($$.debug) {
      if(Math.abs($$.debug) >= 1) {
        this.formsService.istanza.id_istanza = Math.abs($$.debug); }
      if($$.debug <= -1) {
        this.entryPoint = 'view-instance'; }
    }

    if(this.router.url.indexOf('?') > -1) {
      const params = this.router.url.split('?')[1].split('&');

      // viewMode=backoffice&impersonCF=&idIstanza=3253
      if((params||[]).map(x => { return x.startsWith('viewMode') && x.split('=')[1] }).find(x => !!x)  == 'backoffice'
                && params.find(x => x.startsWith('idIstanza'))) {
          this.formsService.istanza.id_istanza = parseInt(params.find(x => x.startsWith('idIstanza')).split('=')[1], 10); }

      if((params||[]).find(p => p == 'removeAllFormIOControls=true')) {
        this.formsService.doNotCheckFormIOModify = this.formsService.removeAllFormIOControls = true; }
    
      if((params||[]).find(p => p == 'showAllFormIOKeys=true')) {
        this.formsService.doNotCheckFormIOModify = this.formsService.showAllFormIOKeys = true;

        window.document.body.addEventListener("click", function (e:any) {
          if(e.target.className.indexOf('keyClass') > -1 
              || e.target.className.indexOf('keyValue') > -1
              || e.target.className.indexOf('keyExpr') > -1) {
            var range = document.createRange();
            range.selectNode(e.target);
            window.getSelection().removeAllRanges();
            window.getSelection().addRange(range);
            document.execCommand("copy");
            //window.getSelection().removeAllRanges();// to deselect
          }
        });
      }

    }
  }


	/* 
	* nome metodo "ngOnInit"; method description: 
	* @param ()
	* @returns 
	*/ 

  ngOnInit() {
    if(!this.formsService?.istanza?.id_istanza) {
      this.router.navigate(['/fascicolo']);
      return;
    }

    this.stepManagerService.avantiClickedSub.pipe(takeUntil(this.destroy$)).subscribe(() => this.stepper.next() );
    this.stepManagerService.indietroClickedSub.pipe(takeUntil(this.destroy$)).subscribe((index: number) => this.stepper.selectedIndex = index );
    this.stepManagerService.stepCompletedSub.pipe(takeUntil(this.destroy$)).subscribe((value) => { this.setCompleted(value.stepComponent, value.completed, value.idQuadro); });
    this.stepManagerService.currentStepLoadedSub.pipe(takeUntil(this.destroy$)).subscribe((data) => { this.updateInfoQuadroBox(data) });

    this.mudeopenFoBeService.recuperaIstanza(this.formsService.id_istanza).subscribe((istanza: model.IstanzaVO) => {
      this.formsService.istanza = istanza;
      (this.formsService.istanza as any)._BOView = this.isBOEnvironment;

      this.mudeopenFoBeService.recuperaFascicolo(istanza.id_fascicolo).subscribe((fascicolo: model.FascicoloVO) => {
        this.formsService.fascicolo = fascicolo;

        if(istanza.fascicolo?.json_data)
          istanza.fascicolo.json_data = JSON.parse(istanza.fascicolo.json_data);

        this.stepManagerService.setJData(this.stepManagerService.jsondata = (istanza.json_data && JSON.parse(istanza.json_data)) || {}, 
                                          this.formsService.fascicolo,
                                          this.formsService.istanza);

        this.buildStepper();
      });
    });

    // periodically updates infoQuadroBox
    setInterval(x => { this.updateInfoQuadroBox() }, 10000);
  }

  get breadcrumb(): string {
    if(this.formsService.fascicolo && this.formsService.fascicolo.tipologia_intervento) {
      const str2find = 'interventi di';
      let descr = this.formsService.fascicolo.tipologia_intervento.value;
      if(descr.toLowerCase().indexOf(str2find) > -1) {
        descr = descr.substring(descr.toLowerCase().indexOf(str2find) + str2find. length).trim(); }

      return descr;
    }
    return '';
  }

  get descrTipologiaIstanza(): String {
    return this.formsService?.istanza?.tipologia_istanza?.value
  }


	/* 
	* nome metodo "buildStepper"; method description: 
	* @param ()
	* @returns 
	*/ 

  templateVersion: number;
  isTemplateActive: boolean = true;
  isNewTemplate: boolean = false;
  codTemplate: string;
  templateType: string;

  buildStepper() {
    this.stepManagerService.getQuadriByProcedimento(this.formsService.istanza.tipologia_istanza?.id
                      , this.formsService.istanza.tipologia_presentatore?.id
                      , this.formsService._boTemplateOverride?.template_ver
                      , this.formsService.id_istanza).subscribe((res) => {
        this.stepManagerService.steps = res.quadri;
        this.stepManagerService.mandatoryPM = res.previsto_pm;

        this.templateVersion = res.ver_template;
        this.isTemplateActive = res.stato_template +'' === '1';
        this.codTemplate = res.cod_template;
        this.templateType = res.tipo_istanza?.value;

        if (this.stepManagerService.steps?.length > 0) {
          this.stepManagerService.steps.sort(
            (stepA, stepB) =>
              stepA.ordinamento_template_quadro -
              stepB.ordinamento_template_quadro
          );

          this.stepManagerService.steps.forEach((step, index) => {
            step.completed = false;
            step.json_configura_quadro = JSON.parse(step.json_configura_quadro);

            if(step.json_configura_quadro.disabled)
              (step as any).disabled = !checkBoolJsSafeExpression(step.json_configura_quadro.disabled, 
                                          { 
                                            ex_ISTANZA: this.formsService.istanza,
                                            ex_FASCICOLO: this.formsService.fascicolo
                                          },
                                          null);

          });

          this.stepManagerService.steps.forEach((step, index) => {
            (step as any).proprieta = JSON.parse(this._resolveJDATAexpr((step as any).proprieta || '{}'));

            const stepConfig: StepConfig = {
              stepIndex: index,
              readOnly: (step as any).proprieta?.readOnlyMode || this.formsService.isReadOnlyMode, // read only steps
              isNewInstance: !this.entryPoint
            };
            const options = {
              providers: [{ provide: CONFIG, useValue: stepConfig }],
              parent: this.injector,
            };
            this.stepInjectors[index] = Injector.create(options);
          });

          this.stepperReady = true;
          this.gotoStep();
        }
      });
  }

	/* 
	* nome metodo "setCompleted"; method description: 
	* @param (stepComponent: string, completed: boolean, idQuadro: number = null)
	* @returns 
	*/ 

  setCompleted(stepComponent: string, completed: boolean, idQuadro: number = null) {
    this.stepManagerService.steps.forEach((step) => {
        if(idQuadro) {
          if(step.id_quadro === idQuadro) { step.completed = completed; }
        }
        else if(step.json_configura_quadro.componentName === stepComponent) {
          step.completed = completed; }

      this.cdr.detectChanges();
    });
  }


	/* 
	* nome metodo "gotoStep"; method description: 
	* @param ()
	* @returns 
	*/ 

  gotoStep(lastValid: number = null) {
    if(!lastValid)
      lastValid = this.stepManagerService.getModifiableStepIndex();

    if($$.debug && $$.step) { // debug only
		lastValid = parseInt($$.step)-1; }

    // this forces the stepper states to be reloaded
    this.setCompleted(this.stepManagerService.steps[lastValid].json_configura_quadro.componentName, true);

    if(lastValid == 0) {
      this.stepManagerService.setActiveStep(lastValid); }

    if(!this.stepper === null || this.stepper === undefined) { return; }
    setTimeout(_ => 
      this.stepper.selectedIndex = lastValid)
  }

	/* 
	* nome metodo "stepSelectionChange"; method description: 
	* @param (sel: StepperSelectionEvent)
	* @returns 
	*/ 

  stepSelectionChange(sel: StepperSelectionEvent) {
    if(sel.selectedIndex != sel.previouslySelectedIndex) {
      this.stepManagerService.raiseStepSave(sel.previouslySelectedIndex);
      this.stepManagerService.setActiveStep(sel.selectedIndex);
    }

    this.updateInfoQuadroBox();
  }

  currentStepClicked(event) {
    this.stepper?.selectedIndex && this.stepManagerService.raiseStepReload(this.stepper.selectedIndex);
  }

  canOpenToolbox() {
    return $$.isLocal || $$.isEnvDev || $$.isEnvTest;
  }

  openDebugInfo() {
    this.mudeopenFoBeService.getAssetResourceAsText('toolbox/steps.html', (html) => {
      $$.openToolboxWindow(html);
    });    
  }

  openDocxTester() {
    const modal = this.modalService.open(DocxTesterComponent, { size: 'xl', backdrop: 'static', keyboard: false, windowClass: 'alwaysScrollModal' });

    modal.componentInstance.quadro = this.currentQuadroInfo.quadro;
    modal.componentInstance.idIstanza = this.formsService.istanza.id_istanza;
  }

  copySnippetToClipboard() {
    html2canvas(document.querySelector("#formIOContainer") || document.querySelector("#stepContainer")).then(canvas => {
      canvas.toBlob(function(blob) { 
        const item = new ClipboardItem({ "image/png": blob });
        (navigator.clipboard as any).write([item]); 
      });
    });
  }

  ////////////////////////////////////////////////////////////////////////
  // infoQuadroBox
  ////////////////////////////////////////////////////////////////////////
  infoQuadroBox: any = { enabled: false };
  currentQuadroInfo: any = { enabled: false };
  updateInfoQuadroBox(data: any = null) {
    if(!this.stepperReady) { return; }

    if(data) {
      this.currentQuadroInfo = data; }

    this.isNewTemplate = this.stepManagerService.jsondata?._NEW_TEMPLATE === '';

    const jData = this.stepManagerService.getStepJData(this.stepper?.selectedIndex);

    if(this.stepper?.selectedIndex >= this.stepManagerService.steps.length 
      || !this.stepManagerService.steps[this.stepper?.selectedIndex]) { return }

    const isInstanceStateLock = !(('BZZ,RPA').includes(this.formsService.istanza?.stato_istanza?.id || 'BZZ')
                                  || (this.stepper?.selectedIndex == this.stepManagerService.steps.length-1 /* last step */
                                        && ('DFR,FRM').includes(this.formsService.istanza?.stato_istanza?.id)));
    const lastStateId = this.stepManagerService.steps[this.stepper?.selectedIndex].currentStepState;
    const statusId: number = isInstanceStateLock? 0 : this.stepManagerService.steps[this.stepper?.selectedIndex].currentStepState = this.getStatoQuadro(jData);

    // locks down the Quadro in case is not modifiable
    this.stepManagerService.steps[this.stepper?.selectedIndex].setReadOnly = statusId != 2 || isInstanceStateLock;

    this.infoQuadroBox.enabled = true;
    this.infoQuadroBox.isAdmin = this.formsService.hasUserPermission('IND_.*') || this.formsService.hasUserPermission('NOMINA_.*');
    this.infoQuadroBox.status = this.getStatoQuadroDescr(statusId);
    this.infoQuadroBox.last_modify_time = this.getLastModTime(jData);
    this.infoQuadroBox.modify_by_user = jData?._USER_MOD_NAME;
    this.infoQuadroBox.remaining_time = statusId +'' === '1' && this.getRemainingTime(jData);

    if(lastStateId +'' === '1' /* bloccato */ && statusId +'' === '2' /* modificabile */) {
      this.stepManagerService.raiseStepReload(this.stepper.selectedIndex); }
  }

  getStatoQuadro(jData: any): number {
    if(this.formsService.isReadOnlyMode || !this.stepManagerService.steps[this.stepper?.selectedIndex].canUserModify) {
      return 0; }
    if(!this.stepManagerService.canUserChangeQuadro(this.stepper?.selectedIndex, this.authService.getUser().contatto.anagrafica.codiceFiscale)) {
      return 1; }
    return 2;
  }

  getStatoQuadroDescr(state: number): string {
    if(state == 0) {
      return 'SOLA LETTURA'; }
    if(state +'' === '1') {
      return 'BLOCCATO&nbsp;<span class="red"><em class="fa fa-circle" aria-hidden="true"></em></span>'; }
    return 'MODIFICABILE&nbsp;<span class="green"><em class="fa fa-circle" aria-hidden="true"></em></span>';
  }

  getLastModTime(jData: any): string {
    if(jData?._USER_MOD_TIME) {
      return (new Date(jData._USER_MOD_TIME)).toLocaleDateString("it-IT")
            + '&nbsp;<em>(' + (new Date(jData._USER_MOD_TIME)).toLocaleTimeString('it-IT').substring(0, 5) + ')</em>'; }
    
    return null;
  }

  getRemainingTime(jData: any): string {
    if(jData?._USER_MOD_TIME 
          && (Date.now() - parseInt(jData?._USER_MOD_TIME, 10)) <= parseInt(jData?._USER_MOD_LEASE, 10)) {
      return new Date((parseInt(jData?._USER_MOD_LEASE, 10) - (Date.now() - parseInt(jData?._USER_MOD_TIME, 10)))+61000).getUTCMinutes() + ' minuti';
	}
    
    return null;
  }

  onOpenListaStati() {
    const modal = this.modalService.open(ListaStatiComponent, { size: 'lg', backdrop: 'static', keyboard: false });
    modal.componentInstance.idIstanza = this.formsService.istanza.id_istanza;
  }

  onOpenUsersProviledgeManagment() {
    this.formsService.displayPermissionsManagerPopup(this.authService.getUser(), null, this.formsService.id_istanza, this.formsService.istanza.id_template).subscribe((res) => {
      if(res.isDataChanged) {
        let gotoStep = -1;
        if(res.pmRemoved && this.stepManagerService.mandatoryPM
              && (gotoStep = this.stepManagerService.steps.findIndex(step => step.tipo_quadro.cod_tipo_quadro == 'QDR_SOGGETTO_COINV' && step.isValidated)) > -1) {

          // invalidate step SOGG.COINV in order to compile PM.
          this.stepManagerService.steps[gotoStep].isValidated = false;

          if(this.stepper.selectedIndex > gotoStep) {
            // goto step SOGG.COINV in case we are over.
            this.gotoStep(gotoStep);
            return;
          }

        }
        
        // reload current step
        this.stepManagerService.raiseStepReload(this.stepper.selectedIndex); }
    });
  }

  get statoIstanza():string {
    return this.formsService?.istanza?.stato_istanza?.value || 'BOZZA';
  }

  _resolveJDATAexpr(defaultJson: string): string {
    const JDATAScopeStart = '{{';
    const JDATAScopeEnd = '}}';
    
    while(defaultJson.indexOf(JDATAScopeStart) > -1) {
      const i1 = defaultJson.indexOf(JDATAScopeStart);
      const i2 = defaultJson.indexOf(JDATAScopeEnd, i1);

      let obj = checkJsSafeExpression(defaultJson.substring(i1 + JDATAScopeStart.length, i2), 
                                      this.stepManagerService.jsondata,
                                      this.stepManagerService.jsondata,
                                      this.stepManagerService.jsondata,
                                      {});
      if(obj != null && typeof obj=='object') {
        obj = JSON.stringify(obj); }

      defaultJson = defaultJson.substring(0, i1)
              + (((typeof obj == 'undefined' || obj == null) && 'null') || obj) 
              + defaultJson.substring(i2 + JDATAScopeEnd.length);
    }

    return defaultJson;
  }


}