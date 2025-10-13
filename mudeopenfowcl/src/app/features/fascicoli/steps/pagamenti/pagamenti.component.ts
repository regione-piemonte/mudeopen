/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { AfterContentInit, Component, Inject, Injector, OnInit } from '@angular/core';
import { OnStepInit, StepComponent } from "../step.component";
import { CodTipoMessEnum, CONFIG, MudeopenFoBeService, StepConfig } from 'mudeopen-common';

import * as model from 'mudeopen-common';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'pagamenti',
  templateUrl: './pagamenti.component.html',
  styleUrls: ['./pagamenti.component.css']
})
export class PagamentiComponent extends StepComponent implements OnInit, OnStepInit, AfterContentInit {

  importiFormArray: FormArray;

  attach: model.TipoAllegatoExtendedVO;
  cod_allegato: string;

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
    this.cod_allegato = this.formsService.getEnteProperty('PIEMONTE_PAY_COD_ALLEGATO') || 'PAG006';

    this.mudeopenFoBeService.loadTipiAllegatoByTemplateQuadro(this.formsService.id_istanza, this.idTemplateQuadro, this.cod_allegato).subscribe(x => { 
      this.attach = x[0];
    });
  }

	/* 
	* nome metodo "onStepLoader"; method description: 
	* @param (jsondata: any, isNew: boolean)
	* @returns 
	*/ 

  onStepLoader(jsondata: any, isNew: boolean) {
    this.mudeopenFoBeService.recuperaDettagliPagamento(this.formsService.id_istanza).subscribe(importi => {
      const oldImporti = this.stepQuadroData.importi || [];

      const isAtLeastOnePaid = !!oldImporti.find(x => x._statoPagamento == 'completato');
      if(!isAtLeastOnePaid) {
        this.stepQuadroData.importi = [];
        // adds all "importi" giving priority to the ones in jdata if present, in roder to respct the user inputs
        importi.forEach(actualImporto => {
          if(actualImporto.tipo_importo == 'disabilitato') {return;}

          this.stepQuadroData.importi.push((actualImporto.tipo_importo != 'bloccato' 
                                                  && oldImporti.find(x => x.descrizione == actualImporto.descrizione))
                                              || actualImporto);
        });

        // adds old paid items not already included
        oldImporti.forEach(oldImporto => {
          if(oldImporto.tipo_importo == 'disabilitato' || !oldImporto._statoPagamento) {return;}

          if(!importi.find(x => x.descrizione == oldImporto.descrizione)) {
            this.stepQuadroData.importi.push(oldImporto); }
        });
      }

      this.initForm();

      if((this.stepQuadroData.sp != this.stepManagerService.jsondata?.TAB_QUALIF_1?.chk_comunica) 
            && !this.isStepInReadOnlyMode) {
        this.stepQuadroData.sp = this.stepManagerService.jsondata?.TAB_QUALIF_1?.chk_comunica;

        // save in case this is the creation
        this.storeJsonDataDetailed(null, false).subscribe((res) => {
          if(res == undefined) { res = null; } // SQ crap
        } ); 
      }
    });

    return false;
  }

  isLeftSomeToPay: boolean;
  mainContactForm: FormGroup;
  initForm() {
    if(!this.stepQuadroData.importi.length) return;

    let totalAmount = 0;

    this.isLeftSomeToPay = false;
    this.importiFormArray = new FormArray([]);
    for(const importo of this.stepQuadroData.importi) {
      const vals = [ Validators.pattern("^(\\d{1,3}(\\.\\d{3})*|(\\d+))(\\,\\d{1,2})$") ];
      if(importo.tipo_importo != 'opzionale') {
        vals.push(Validators.required); }

      const fieldImporto  = new FormControl(null, vals);
      fieldImporto.setValue(this.getImportoLocale(importo));
      if(importo.tipo_importo == 'bloccato' || (importo as any)._statoPagamento == 'completato') {
        fieldImporto.disable();  }

      this.importiFormArray.push(new FormGroup({ importo: fieldImporto }));

      if((importo as any)._statoPagamento != 'completato') {
        this.isLeftSomeToPay = true; }

      totalAmount += importo.importo||0;
    }

    this.formsService.istanza.pagamento_attivo = !!totalAmount;

    this.mainContactForm = new FormGroup({
      importi: this.importiFormArray
    });
  }

  get isStepValidationOK(): boolean {
    return this.setStepCompleteState((!this.formsService.istanza.pagamento_attivo 
          || (this.getStatoIstanza() !== 'BZZ' && this.getStatoIstanza() !== 'RPA') 
                || ((this.isPaySkipChecked || !this.isLeftSomeToPay) 
                      && !!this.paymentReceipts.length)));
  }


  getImportoLocale(importo: any): string {
    // {{ amount | currency: 'EUR':'symbol':undefined:'it-IT' }}
    // return new Intl.NumberFormat('it-IT', { style: 'currency', currency: 'EUR' }).format(importo.payAmount || importo.importo || 0);
    let amount = (importo.payAmount || importo.importo || 0).toLocaleString('it-IT');
    if(amount.indexOf(',') == -1)
      amount = amount + ',00';

    return amount;
  }

  getFloat(str: string): number {
    return parseFloat((str || '0').replace('.','').replace(',','.'));
  }

	/* 
	* nome metodo "peekFilesData"; method description: 
	* @param (attach: any)
	* @returns any
	*/ 

  get attachKey(): string {
    return this.attach && 
        (this.attach.sub_cod_tipo_allegato || this.attach.codice_tipo_allegato);
  }

  get paymentReceipts(): any {
    return this.stepQuadroData[this.attachKey] || [];
  }

	/* 
	* nome metodo "download"; method description: 
	* @param (file: any)
	* @returns 
	*/ 

  download(file: any) {
    this.mudeopenFoBeService.downloadAllegato(file.nodeIndex).subscribe(x => {
      this.download2user(x);
    });
  }
  
  /* 
	* nome metodo "delete"; method description: ringrazia che abbiaringrazia che abbia
	* @param (info: any = null)
	* @returns 
	*/ 

  delete(info: any = null) {
    this.confirmDialog("Vuoi eliminare il file?", () => {
      this.mudeopenFoBeService.deleteAllegato(info.file.id).subscribe(x => {
        info.files.splice(info.index, 1);
        this.storeJsonData(true, this.isStepValidationOK);
      });
    });
  }

	/* 
	* nome metodo "uploadPaymentReceipt"; method description: 
	* @param (file: any)
	* @returns 
	*/ 

  uploadPaymentReceipt(file: any){
    /*
    const jsonInfo = {
        "cod_tipo_allegato": ""+this.attachKey,
        "id_istanza": ""+this.formsService.id_istanza,
        "desc_breve_allegato": "",
        "dont_save_jdata": true,
        "jsondata": JSON.stringify({
          name: null, 
          size: null,
          obbligatorio: null,
          desc_allegato: null,
          desc_breve_allegato: null,
          descrizione_categoria_allegato: null,
          ordinamento: null,
          valida_firma: null
        })
      };
      */
      const jsonInfo = {
        cod_tipo_allegato: ""+this.attachKey,
        id_istanza: ""+this.formsService.id_istanza,
        desc_breve_allegato: "",
        dont_save_jdata: true, // don't save to QDR_ALLEGATI, but to QDR_PAGAMENTO instead
        jsondata: JSON.stringify({
          // filled in by BE after pushed to INDEX
          //id: addedAttach.id,  
          //nodeIndex: addedAttach.index_node, 
          name: file.name, 
          size: file.size,
          obbligatorio: 'obbligatorio',
          desc_allegato: this.attach.descrizione_tipo_allegato,
          desc_breve_allegato: this.attach.descrizione_breve_tipo_allegato,
          descrizione_categoria_allegato: this.attach.categoria_allegato.descrizione_estesa_categoria_allegato,
          ordinamento: this.attach.ordinamento,
          valida_firma: this.attach.valida_firma
        }),
        quadro_validated: this.lastValidatedStateChanged,
        id_template_quadro: this.idTemplateQuadro
      };

      if(this.paymentReceipts.find((f) => file.name == f.name )) {
        this.messageService.showMessageBox(CodTipoMessEnum.I, "Esiste già un file con lo stesso nome");
        return;
      }

      this.mudeopenFoBeService.uploadAllegato(JSON.stringify(jsonInfo), file).subscribe((addedAttach:model.AllegatoVO) => {
        if(!addedAttach.id) { return; }

        this.stepManagerService.jsondata[this.quadro.tipo_quadro?.cod_tipo_quadro] = this.stepQuadroData = JSON.parse(addedAttach.desc_breve_allegato); // "desc_breve_allegato" passed as istanza.json_data;
      });
  }

  howManyAttachsAllowed(attach: any) {
    return attach && (this.evalJsExpression(attach.espressione_ripetibilita) || (!attach.ripetibile?1:undefined));
  }

  get isDebugOn(): boolean {
    return !!$$.debug;
  }

  selectedDebugOption: string = null;
  changeDebugMode(event) {
    this.selectedDebugOption = event.srcElement.options[event.srcElement.selectedIndex].value;
  }

  isPaymentStarted: boolean = false;
  onStartPayment() {
    const amountValues = this.mainContactForm.value.importi;

    if((this.mainContactForm.invalid && this.commonUtils.validateForm(this.mainContactForm))) { return }

    this.stepQuadroData.importi.forEach((amountItem, index) => {
      (amountItem as any).isLower = !(amountItem.tipo_importo != 'maggiore' || this.getFloat(amountValues[index].importo) < (amountItem.importo||0) );
      (amountItem as any).isGreater = !(amountItem.tipo_importo != 'minore' || this.getFloat(amountValues[index].importo) > (amountItem.importo||0) );
    });
  
    if(this.stepQuadroData.importi.find((amountItem: any) =>  amountItem.isLower || amountItem.isGreater)) { return }
  
    $$.log3('mainContactForm: ', amountValues);

    this.stepQuadroData.importi.forEach((amountItem, index) => {
      (amountItem as any).payAmount = this.getFloat(amountValues[index].importo);
    });

    this.storeJsonDataDetailed(null, true, this.isStepValidationOK)?.subscribe((r) => {
      this.mudeopenFoBeService.avviaPagamento(this.formsService.id_istanza, this.selectedDebugOption).subscribe(res => {
        this.stepQuadroData.pagamento_avviato = true;
        this.isPaymentStarted = true;
        this.stepQuadroData.importi = JSON.parse(res.json_importi);

        if(this.selectedDebugOption)
          res.url = window.location.href.replace(/^(.*\/[#]).*$/, '$1'+res.url);

        this.openPopupWindow(res.url);
        this.listenForUpdates();
      });
    });
  }

  onStepUpdate() {
    this.updateStepData().subscribe((info: model.JDataIstanzaVO) => {
      this.onStepLoader(this.stepManagerService.jsondata, false);
    });
  }

  onAbortPayment() {
    this.confirmDialog('Se il pagamento non è andato a buon fine clicca "CONFERMA". In questo modo potrai effettuare successivamente una nuova transazione. Altrimenti clicca "CONTINUA" per completare la transazione iniziata in precedenza.', (x) => {
      this.stepQuadroData.pagamento_avviato = this.isPaymentStarted = false;
      this.onStepUpdate();
    }, 'CONFERMA', 'CONTINUA');
  }

  noPPayNecessary(isChecked, chkVal) {
    this.stepQuadroData.pagamento_gia_effettuato = !isChecked? false : chkVal;
  }

  get isPaySkipPrecari(): boolean {
    if(this.getProperty('PPAY_SKIP_FLAG_PRECARIO') != 'disabilitato')
      return this.stepManagerService.jsondata?.TAB_QUALIF_1?.opere_in_precario_su_suolo_pubblico;

    return true;
  }

  get isPaySkipChecked(): boolean {
    if(this.stepQuadroData._PAYMENT_OUTSIDE_PPAY)
      return true;

    if(this.getProperty('PPAY_SKIP_FLAG_PRECARIO') != 'disabilitato')
      return !!this.stepQuadroData.pagamento_gia_effettuato || this.isPaySkipPrecari;

    return !!this.stepManagerService.jsondata?.TAB_QUALIF_1?.opere_in_precario_su_suolo_pubblico 
            && (!!this.stepQuadroData.pagamento_gia_effettuato || this.isPaySkipPrecari);
  }

  get getPrecariPaymentUrl(): string {
    return this.formsService.getEnteProperty('PPAY_INFO_PRECARI_URL') || ('h'.replace('noway', '')+'ttp'.replace('noway', '') + '://www'.replace('noway', '') + '.comune.torino.it/commercio/'.replace('noway', '')); // SQ crap
  }

  get introMessage(): string {
    return this.stepManagerService.jsondata?.TAB_QUALIF_1?.opere_in_precario_su_suolo_pubblico? 'Per le opere edili in "precario" su suolo pubblico, si rimanda alla consultazione delle <a href="'+this.getPrecariPaymentUrl+'" target="_blank">pagine web dedicate</a> alle modalità e agli importi di pagamenti previsti per la loro presentazione.<br>Una volta effettuato il pagamento, è possibile allegare la ricevuta firmata digitalmente'
          : "Una volta effettuato il pagamento, è possibile allegare la ricevuta firmata digitalmente";
  }

}