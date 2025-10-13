/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { Component, Inject, Injector, OnInit } from '@angular/core';
import { CONFIG } from 'mudeopen-common';
import { StepConfig } from 'mudeopen-common';
import { MudeopenFoBeService } from 'mudeopen-common';
import * as model from 'mudeopen-common';
import { OnStepInit, StepComponent } from "../step.component";





@Component({
  selector: 'presenta-istanza',
  templateUrl: './presenta-istanza.component.html',
  styleUrls: ['./presenta-istanza.component.css']
})
export class PresentaIstanzaComponent extends StepComponent implements OnStepInit {
  constructor(mudeopenFoBeService: MudeopenFoBeService,
        injector: Injector,
        @Inject(CONFIG) injConfig: StepConfig) {
    super(mudeopenFoBeService, injector, injConfig);
  }


	/* 
	* nome metodo "onStepLoader"; method description: 
	* @param (jsondata: any, isNew: boolean)
	* @returns 
	*/ 

  onStepLoader(jsondata: any, isNew: boolean) {
    this.stepManagerService.loadJDataIstanza(this.formsService.id_istanza, null, null, null, 'update-address').subscribe((info: model.JDataIstanzaVO) => {
      this.formsService.istanza.nuovo_indirizzo = info.nuovo_indirizzo;
    });

    return false;
  }

  lastPdf: any;
  lastPdfId: any = null;
  get istanzaAttach(): any {
    if(this.lastPdfId != ('' + this.formsService.istanza?.nome_file_istanza + this.formsService.istanza?.dimensione_file_istanza)) {
      this.lastPdf = [];

      if(this.formsService.istanza.nome_file_istanza) {
        this.lastPdf.push({
          nodeIndex: this.formsService.istanza.index_node, 
          name: this.formsService.istanza.nome_file_istanza, 
          size: this.formsService.istanza.dimensione_file_istanza
        });
      }

      this.lastPdfId = ('' + this.formsService.istanza?.nome_file_istanza + this.formsService.istanza?.dimensione_file_istanza);
    }

    return this.lastPdf;
  }

  get isStepValidationOK(): boolean {
    return this.getStatoIstanza() === 'FRM';
  }

  get showNewAddress(): boolean {
    return this.isStepValidationOK 
            && !!this.formsService.istanza.nuovo_indirizzo;
  }


	/* 
	* nome metodo "downloadPdf"; method description: 
	* @param ()
	* @returns 
	*/ 

  downloadPdf() {
    this.mudeopenFoBeService.downloadTemplatePDFIstanza(this.formsService.id_istanza).subscribe(x => {
      this.updateStatus();
      this.download2user(x);
    });
  }

	/* 
	* nome metodo "download"; method description: 
	* @param (attach: any, file: any)
	* @returns 
	*/ 

  download(file: any) {
    this.mudeopenFoBeService.downloadIstanza(this.formsService.id_istanza).subscribe(x => {
      this.download2user(x);
    });
  }

  consolidateInstance() {
    this.confirmDialog('Vuoi consolidare l\'istanza e bloccarla in modo che non possano essere modificati i dati inseriti?', (x) => {
      this.mudeopenFoBeService.cambiaStatoIstanza(this.formsService.id_istanza, 'DFR').subscribe(x2 => {
        this.updateStatus();
      });
  
      this.formsService.consolidateInstance()
    }, 'CONSOLIDA');
  }


	/* 
	* nome metodo "uploadPPdfIstanza"; method description: 
	* @param (file: any)
	* @returns 
	*/ 

  uploadPPdfIstanza(file: any) {
    const jsonInfo = '{"id_istanza":"'+this.formsService.id_istanza+'"}';

    this.mudeopenFoBeService.uploadIstanzaFirmata(jsonInfo, file).subscribe((addedAttach:model.IstanzaVO) => {
      this.formsService.istanza.dimensione_file_istanza = file.size;
      this.formsService.istanza.nome_file_istanza = file.name;

      this.updateStatus();
    });
  }


	/* 
	* nome metodo "delete"; method description: 
	* @param (info: any)
	* @returns 
	*/ 

  delete(info: any) {
    this.confirmDialog("Vuoi eliminare il file?", () => {
      this.mudeopenFoBeService.deleteFileIstanzaFirmata(this.formsService.id_istanza).subscribe(x => {
        this.lastPdfId = null;
        info.files = this.lastPdf = [];
        this.formsService.istanza.nome_file_istanza = null;

        this.updateStatus();
      });
    });
  }

  updateStatus() {
    setTimeout(_ => {
      this.stepManagerService.loadJDataIstanza(this.formsService.id_istanza).subscribe((info: model.JDataIstanzaVO) => {
        this.formsService.istanza.stato_istanza.id = info.stato_istanza;
        this.stepManagerService.raiseStepLoaded(this.stepIndex, this.quadroInTemplate);
      });
    }, 100);
  }
	/* 
	* nome metodo "onStepNext"; method description: 
	* @param ()
	* @returns 
	*/ 

  updateAddress: boolean = false;
  onSendInstance() {
    this.setStepCompleteState(true);
    this.stepManagerService.steps[this.stepIndex].isCompiled = true;
    (this.stepManagerService.steps[this.stepIndex] as any).updateAddress = this.updateAddress;

    this.storeJsonDataDetailed(this.stepManagerService.steps[this.stepIndex], true, true)?.subscribe((res) => {
      this.mudeopenFoBeService.cambiaStatoIstanza(this.formsService.id_istanza, 'DPS',  { aggiorna_fascicolo_indirizzo: this.updateAddress? true : null }).subscribe(x => {
        this.updateStatus();
      });
    });
  }

  get _isStepInReadOnlyMode(): boolean {
    return "DPS,RPA,APA,SAI,ASR,ITS,ARC,ANN,ACO,CON,ACC,ICI,AIC,SIC,VIG,IAI,INC,ATI,INT,AFL,ICO,COC,RES,RSU,COL,DRE,SIA".indexOf(this.formsService.istanza?.stato_istanza?.id || '-') > -1 
            || this.istanzaReadOnlyMode 
            || this.readOnlyMode 
            // does this make disappear sometimes the uoload? || this.stepManagerService?.steps[this.stepIndex]?.setReadOnly;
  }



}