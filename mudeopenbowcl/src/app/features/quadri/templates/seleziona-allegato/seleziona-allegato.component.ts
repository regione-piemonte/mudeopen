/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;


import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MudeopenFoBeService } from 'mudeopen-common';
import * as model from 'mudeopen-common';
import { CategoriaBaseComponent } from "../../create/categorie/CategoriaBase.component";
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { forkJoin } from 'rxjs';






@Component({
  selector: 'app-seleziona-allegato',
  templateUrl: './seleziona-allegato.component.html',
  styleUrls: ['./seleziona-allegato.component.scss']
})
export class SelezionaAllegatoComponent extends CategoriaBaseComponent implements OnInit {

  @Output() confirmEvent: EventEmitter<any> = new EventEmitter<any>();

  public data: model.TipoAllegatoExtendedVO;
  public allegatiAlreadyAssigned: model.TipoAllegatoExtendedVO[];

  DEROGHE_TAG: string = '/* ENTI IN DEROGA (obbligatori): ';
  DEROGHE_ESCLUD_TAG: string = '/* ENTI IN DEROGA (facoltativi): ';

  conditionType: number = 0;

  mainForm: FormGroup;
  tipoAllegati: model.TipoAllegatoVO[];
  allegatiList: model.SelectVO[];

  entiList: model.SelectVO[];
  entiListRemaining: model.SelectVO[];

  enteSelected: any = '-';
  entiChosen: any[] = [ ];

  constructor(public mudeopenFoBeService: MudeopenFoBeService,
              private modalService: NgbModal) {
    super(mudeopenFoBeService, null);

    this.disableCountryLoading = true;

    this.mainForm = new FormGroup({
      codice_tipo_allegato: new FormControl(null, [ Validators.required ]),
      obbligatorio: new FormControl(),
      ripetibile: new FormControl(),
      flag_ricorrente: new FormControl(),
      espressione_obbligatorieta: new FormControl(),
      espressione_ripetibilita: new FormControl(),
      modello_allegato: new FormControl(),
      ordinamento: new FormControl(null, [ Validators.required, Validators.pattern('^[0-9]*$') ]),
      _entiSelect: new FormGroup({ id: new FormControl(), value: new FormControl() })
    });
  }

  ngOnInit() {
    forkJoin([
      this.mudeopenFoBeService.loadTipiAllegato(),
      this.mudeopenFoBeService.listaEnti()
    ]).subscribe((results) => {
      const x:model.TipoAllegatoVO[] = results[0] as model.TipoAllegatoVO[];
      this.entiList = results[1] as model.SelectVO[];

      this.tipoAllegati = x.filter(y => 
        !this.allegatiAlreadyAssigned.find(a => a.codice_tipo_allegato == y.codice_tipo_allegato && (!this.data || y.codice_tipo_allegato != this.data.codice_tipo_allegato)) 
      );
      this.allegatiList = this.tipoAllegati.map(y => { return { id: y.codice_tipo_allegato, value: y.descrizione_breve_tipo_allegato + ' (' + y.codice_tipo_allegato + ')' } });

      if(this.data) {
        this.conditionType = (this.data.espressione_obbligatorieta||'').startsWith(this.DEROGHE_TAG) ? 1 : 
            (this.data.espressione_obbligatorieta||'').startsWith(this.DEROGHE_ESCLUD_TAG) ? 2 : 0;

        if(this.conditionType > 0) {
          const arrayIdEnte = JSON.parse(this.data.espressione_obbligatorieta.substring(this.data.espressione_obbligatorieta.indexOf('['), this.data.espressione_obbligatorieta.indexOf(']')+1));
          this.entiChosen = this.entiList.filter(x => arrayIdEnte.indexOf(x.id) > -1 );
        }

        this.patchFormValues(this.mainForm, this.data);
        if(this.data?.modello_allegato?.path_modello) {
          this.docxFileList = [ {
            name: this.data.modello_allegato.path_modello, 
            size: this.data.modello_allegato.dimensione_file,
          } ];}
      }

      this._setEntiRemainig();
      this.mainForm.get('_entiSelect').setValue({ id: '', value: null})
    });
  }

  onTabChange(tabindex: number) {
    if(!this.mainForm.value.obbligatorio) { return; }
    this.conditionType = tabindex;
  }

  onEnteSelected(enteCode) {
    if(!enteCode || enteCode == '-') { return; } // .target.value

    this.entiChosen = [... this.entiChosen, this.entiList.find(x => x.id == enteCode)];
    this._setEntiRemainig();
    this.enteSelected = '-';
  }

  deleteEnte(enteCode, rowIndex) {
    this.entiChosen.splice(rowIndex, 1);
    this.entiChosen = [... this.entiChosen ];
    this._setEntiRemainig();
    this.enteSelected = '-';
  }

  getEnteName(row): string {
    return row.id + ' - ' + row.value ;
  }

  _setEntiRemainig() {
    this.entiListRemaining = this.entiList.filter(item => !this.entiChosen.find(y => item.id == y.id));
  }

  onSubmit(isOk: any = false) {
    if(!isOk) {
      this.modalService.dismissAll(); }
    else if(this.mainForm.invalid) {
      this.commonUtils.validateForm(this.mainForm); }
    else {
      const frmData = this.mainForm.value;
      delete frmData._entiSelect;

      if(this.conditionType >= 1) {
        frmData.espressione_obbligatorieta = (this.conditionType == 2? this.DEROGHE_ESCLUD_TAG : this.DEROGHE_TAG) + '\r\n\r\n' + this.entiChosen.map(x => { return x && x.value }).join('\r\n') + '\r\n\r\n */ ' +
            (this.conditionType == 2? '!' : '') + JSON.stringify(this.entiChosen.map(x => { return x && x.id })) + '.find(_id => _id == ex_ISTANZA.ente_di_riferimento?.id)';
      }

      this.confirmEvent.emit({ 
          allegato: Object.assign({}, this.data, frmData), 
          file: (this.docxFileList.length && this.docxFileList[0] instanceof Blob && this.docxFileList[0]) || null 
      }); 

      this.modalService.dismissAll();      
    }
  }

  setObbligatorio(event) {
    if(event.target.checked) {
      this.mainForm.get('flag_ricorrente').setValue(!event.target.checked); }

    this.mainForm.get('espressione_obbligatorieta').setValue('');
  }

  setRipetibile(event) {
    this.mainForm.get('espressione_ripetibilita').setValue('');
  }

}
