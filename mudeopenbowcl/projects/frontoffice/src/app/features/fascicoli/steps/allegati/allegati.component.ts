/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { Component, Inject, Injector, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { CONFIG } from 'mudeopen-common';
import { CodTipoMessEnum, StepConfig } from 'mudeopen-common';
import { MudeopenFoBeService } from 'mudeopen-common';
import * as model from 'mudeopen-common';
import { OnStepInit, StepComponent } from "../step.component";
import { USER_FUNC } from '../../../../shared/enums';





@Component({
  selector: 'allegati',
  templateUrl: './allegati.component.html',
  styleUrls: ['./allegati.component.css']
})
export class AllegatiComponent extends StepComponent implements OnInit, OnStepInit {
  tabs: any = [ {
		"key": "TAB_ALL_OBBL",
		"label": "12.1",
		"title": "ALLEGATI"
	},
	{
		"key": "TAB_ALL_FAC",
		"label": "12.2",
		"title": "ALLEGATI FACOLTATIVI"
	}];

  constructor(mudeopenFoBeService: MudeopenFoBeService,
        private fb: FormBuilder,
        injector: Injector,
        @Inject(CONFIG) injConfig: StepConfig) {
    super(mudeopenFoBeService, injector, injConfig);
  }

  optionalAttachesForm: any;

  allCategories: any = {};
  allAttachements: any;
  mandatoryAttachements: any = [];
  optionalAttachements: any;

	/* 
	* nome metodo "ngOnInit"; method description: 
	* @param ()
	* @returns void
	*/ 

  ngOnInit(): void {
    this._buildForms();
  }

  private _buildForms() {
    this.optionalAttachesForm = this.fb.group({
        attachCategory: this.fb.group({ id: ['', { validators: [Validators.required], }], value: [''] }),
        attachType: this.fb.group({ id: ['', { validators: [Validators.required], }], value: [''] })
      });
  }


	/* 
	* nome metodo "onStepLoader"; method description: 
	* @param (jsondata: any, isNew: boolean)
	* @returns 
	*/ 

  onStepLoader(jsondata: any, isNew: boolean) {

    if(this.quadro.json_configura_quadro?.tabs) {
      this.tabs = this.quadro.json_configura_quadro.tabs; 
      this.checkDependency();
    }

    if(isNew) {
      // build jsondata
      this.mudeopenFoBeService.loadAllegatiIstanza(this.formsService.id_istanza).subscribe((allegati: Array<model.AllegatoVO>) => {
        (allegati || []).forEach((allegato) => {
          jsondata[this.getAttachKey(allegato.tipo_allegato)] = jsondata[this.getAttachKey(allegato.tipo_allegato)] || [];
          jsondata[this.getAttachKey(allegato.tipo_allegato)].push({ 
            id: allegato.id, 
            nodeIndex: allegato.index_node, 
            name: allegato.tipo_allegato.codice_tipo_allegato + '_' + allegato.nome_file_allegato, 
            size: allegato.dimensione_file 
          });
        })
        
        this.createAttachFields();

        // save in case attaches are already present on server side
        if((allegati || []).length) {
          this.storeJsonDataDetailed(null, false).subscribe((res) => {
            if(res == undefined) { res = null; } // SQ crap
          } ); 
        }
      });
    }
    else {
		  this.createAttachFields(); }

    return false;
  }

  dependencyMessage: string;
  dependencyMessageType: any;
  checkDependency() {
    this.dependencyMessage = null;

    // show message if check is false
    ((typeof(this.currentTab.dependencyCheck) != 'string' 
        && this.currentTab.dependencyCheck) || []).forEach((dep) => {
      if(!this.checkJsExpression(dep.check)) {
        this.dependencyMessage = this._resolveJDATAexpr(dep.message);
        this.dependencyMessageType = dep.type;
      }
    });
  }


	/* 
	* nome metodo "getAttachKey"; method description: 
	* @param (attach)
	* @returns 
	*/ 

  getAttachKey(attach) {
    return attach.sub_cod_tipo_allegato || attach.codice_tipo_allegato;
  }


	/* 
	* nome metodo "createAttachFields"; method description: 
	* @param ()
	* @returns 
	*/ 

  createAttachFields() {
    this.mudeopenFoBeService.loadTipiAllegatoByTemplateQuadro(this.formsService.id_istanza, this.idTemplateQuadro).subscribe(x => {
      this.allAttachements = x;

      if(this.allAttachements) {
        this.mandatoryAttachements = [ 
          ... this.allAttachements.filter((att) => {
            return att.obbligatorio && !att.espressione_obbligatorieta;
          }),
          ... this.allAttachements.filter((att) => {
            return att.obbligatorio && att.espressione_obbligatorieta && this.checkJsExpression(att.espressione_obbligatorieta, null, 'ATTACH: ' + this.getAttachKey(att));
          }) 
        ];

        this.optionalAttachements = this.allAttachements.filter((att: model.AllegatoVO) => {
          return this.mandatoryAttachements.indexOf(att) == -1 && (!this.readOnlyMode || this.peekFilesData(att).length > 0);
        });

        // compile categories
        this.allAttachements.forEach((att) => {
          if(this.mandatoryAttachements.indexOf(att) == -1 
                  && att.categoria_allegato.descrizione_categoria_allegato
                  && this.getAttachTypes(att.categoria_allegato.descrizione_categoria_allegato).length) {
              this.allCategories[att.categoria_allegato.descrizione_categoria_allegato] = att.categoria_allegato.descrizione_estesa_categoria_allegato; }
        });
      }

    });

  }

  openNewAttachDialog: boolean;

	/* 
	* nome metodo "addOptionalAttach"; method description: 
	* @param ()
	* @returns 
	*/ 

  addOptionalAttach() {
    if(this.optionalAttachesForm.invalid) {
      this.commonUtils.validateForm(this.optionalAttachesForm); }
    else {
      const attach = this.optionalAttachements.find((x) => x.descrizione_breve_tipo_allegato == this.optionalAttachesForm.value.attachType.id );
      this.stepQuadroData[attach.sub_cod_tipo_allegato || attach.codice_tipo_allegato] = [];
      this.openNewAttachDialog = false;
      this.optionalAttachesForm.reset();

      this.storeJsonData(true, this.isStepValidationOK);
    }
  }

  get isThereAnyOptionalAttach(): boolean {
    return this.optionalAttachements && this.optionalAttachements.filter((x) => this.isOptionalAttachToBeDisplayed(x) ).length;
  }

  get areAllOptionalAttachCompiled(): boolean {
    const optionals = this.optionalAttachements.filter((x) => !x.flag_ricorrente && this.isOptionalAttachToBeDisplayed(x) );
    return !optionals.length;
  }

  get hasUploadPermission(): boolean {
    return this.formsService.hasUserPermission(USER_FUNC.UPLOAD_ALLEG);
  }

  get isStepValidationOK(): boolean {
    return this.setStepCompleteState(this.allAttachements 
                &&
                  ((this.mandatoryAttachements && !this.mandatoryAttachements.length)
                    || !this.mandatoryAttachements.filter((x) => !this.peekFilesData(x).length).length)
                //&& this.areAllOptionalAttachCompiled; // are optional compiled mandatory?
                && this.globalStepValidation())
                && this.stepManagerService.setEditableSteps() >= this.stepIndex-1;
  }

  tabSubIndexSelected: number = 0;
  get currentTab(): any { 
    return this.tabs[this.tabSubIndexSelected]; 
  }

	/* 
	* nome metodo "selectedTab"; method description: 
	* @param (tabIndex: number)
	* @returns 
	*/ 

  selectedTab(tabIndex: number) {
    this.tabSubIndexSelected = tabIndex;
  }


	/* 
	* nome metodo "peekFilesData"; method description: 
	* @param (attach: any)
	* @returns any
	*/ 

  peekFilesData(attach: any): any {
    return this.stepQuadroData?.[this.getAttachKey(attach)] || [];
  }


	/* 
	* nome metodo "isOptionalAttachToBeDisplayed"; method description: 
	* @param (attach: any)
	* @returns boolean
	*/ 

  isOptionalAttachToBeDisplayed(attach: any): boolean {
    return !!this.stepQuadroData?.[this.getAttachKey(attach)] || attach.flag_ricorrente;
  }


	/* 
	* nome metodo "onStepNext"; method description: 
	* @param ()
	* @returns 
	*/ 

  onStepNext() {
    this.onStepForward()
  }


	/* 
	* nome metodo "getAttachTypes"; method description: 
	* @param (category: string)
	* @returns any
	*/ 

  getAttachTypes(category: string): any {
    const arr = this.optionalAttachements.filter((x) => {
      return x.categoria_allegato.descrizione_categoria_allegato == category
            && !this.isOptionalAttachToBeDisplayed(x);
    }).map((x) => {
      return { id: x.descrizione_breve_tipo_allegato, value: x.descrizione_breve_tipo_allegato }
    });

    return arr;
  }

  allAttachesTypes: any = [];

	/* 
	* nome metodo "categoryChanged"; method description: 
	* @param (category: string)
	* @returns 
	*/ 

  categoryChanged(category: string) {
    this.allAttachesTypes = this.getAttachTypes(category);
  }


	/* 
	* nome metodo "upload"; method description: 
	* @param (attach: any, file: any)
	* @returns 
	*/ 

  upload(attach: any, file: any){
      if(this.peekFilesData(attach).find((f) => file.name == f.name )) {
        this.messageService.showMessageBox(CodTipoMessEnum.I, "Esiste già un file con lo stesso nome");
        return;
      }

      const sqm = (attach.espressione_obbligatorieta?'necessario':'obbligatorio');
      const sqm2 = this.optionalAttachements.indexOf(attach) != -1?'facoltativo':sqm;
      const jsonInfo = {
        cod_tipo_allegato: ""+this.getAttachKey(attach),
        id_istanza: ""+this.formsService.id_istanza,
        desc_breve_allegato: "",
        jsondata: JSON.stringify({
          // filled in by BE after pushed to INDEX
          //id: addedAttach.id,  
          //nodeIndex: addedAttach.index_node, 
          name: file.name, 
          size: file.size,
          obbligatorio: sqm2,
          desc_allegato: attach.descrizione_tipo_allegato,
          desc_breve_allegato: attach.descrizione_breve_tipo_allegato,
          descrizione_categoria_allegato: attach.categoria_allegato.descrizione_estesa_categoria_allegato,
          ordinamento: attach.ordinamento,
          valida_firma: attach.valida_firma
        }),
        quadro_validated: this.lastValidatedStateChanged,
        id_template_quadro: this.idTemplateQuadro
      };

      this.mudeopenFoBeService.uploadAllegato(JSON.stringify(jsonInfo), file).subscribe((addedAttach:model.AllegatoVO) => {
        if(!addedAttach.id) { return; }

        this.stepManagerService.jsondata[this.quadro.tipo_quadro?.cod_tipo_quadro] = this.stepQuadroData = JSON.parse(addedAttach.desc_breve_allegato); // "desc_breve_allegato" passed as istanza.json_data;
      });
  }

	/* 
	* nome metodo "download"; method description: 
	* @param (attach: any, file: any)
	* @returns 
	*/ 

  download(attach: any, file: any) {
    this.mudeopenFoBeService.downloadAllegato(file.nodeIndex).subscribe(x => {
      this.download2user(x);
    });
  }

  areModelsDownloadActive(): boolean {
    return this.stepIndex == 0 || this.stepManagerService.steps[this.stepIndex-1]?.isValidated;
  }

	/* 
	* nome metodo "downloadModello"; method description: 
	* @param (id: number)
	* @returns 
	*/ 

  downloadModello(id: number) {
    if(!this.areModelsDownloadActive()) { return; }

    this.mudeopenFoBeService.downloadModello(id, this.formsService.id_istanza).subscribe(x => {
      this.download2user(x);
    });
  }


	/* 
	* nome metodo "delete"; method description: ringrazia che abbiaringrazia che abbia
	* @param (attach: any, info: any = null)
	* @returns 
	*/ 

  delete(attach: any, info: any = null) {
    this.confirmDialog(info?"Vuoi eliminare il file?":"Vuoi eliminare il gruppo di file?", () => {
      if(info) {
        this._deleteFile(attach, info); }
      else {
        this.deleteOptionalAttachment(attach); }
    });
  }

  async _deleteFile(attach: any, info: any, isGroup: boolean = false) {
    this.mudeopenFoBeService.deleteAllegato(info.file.id).subscribe(x => {
      this._deleteFileHandler(attach, info, isGroup);
    }, 
    err => {
      // in case of error tries to store the jdata anyway
      this.storeJsonData(true, this.isStepValidationOK);
    });
  }

  async _deleteFileHandler(attach: any, info: any, isGroup: boolean = false) {
      if(!isGroup) {
        info.files.splice(info.index, 1);
        this.storeJsonData(true, this.isStepValidationOK);  
      }
      else if(info.index == 0) { // remove all group if no more files
        delete this.stepQuadroData[this.getAttachKey(attach)];
        this.storeJsonData(true, this.isStepValidationOK);
      }
  }

	/* 
	* nome metodo "deleteOptionalAttachment"; method description: 
	* @param (attach: any)
	* @returns 
	*/ 

  deleteOptionalAttachment(attach: any) {
    const filesArr: any = this.stepQuadroData[this.getAttachKey(attach)];

    if(!filesArr.length) { // remove all group if no more files
      delete this.stepQuadroData[this.getAttachKey(attach)];
      this.storeJsonData(true, this.isStepValidationOK);
    }
    else {
      for(let i = filesArr.length-1; i>=0; i--) {
        this._deleteFile(attach, { 
                    files:  filesArr, 
                    file: filesArr[i], 
                    index: i
                  },
                  true);
      };
    }
  }

  // to be removed
  debugConditions(attach: any) { 
    $$.log3('ATTACH['+this.getAttachKey(attach)+']='+ attach.espressione_obbligatorieta, attach);
    $$.setToolboxWindowPopupValue('JEspr', attach.espressione_obbligatorieta, attach.espressione_obbligatorieta);
  }

  get isThereAtLeastOneCategory() {
    return Object.keys(this.allCategories).length && !this.formsService.hasUserPermission(null, '.*CONSULTATORE');
  }

}