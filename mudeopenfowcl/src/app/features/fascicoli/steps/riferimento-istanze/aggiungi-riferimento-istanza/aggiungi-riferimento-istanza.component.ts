import { Component, OnInit, Output, EventEmitter, ChangeDetectorRef } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { FormUtils, MessageService, MudeopenFoBeService } from 'mudeopen-common';
import * as model from 'mudeopen-common';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-aggiungi-riferimento-istanza',
  templateUrl: './aggiungi-riferimento-istanza.component.html',
  styleUrls: ['./aggiungi-riferimento-istanza.component.css']
})
export class AggiungiRiferimentoIstanzaComponent extends FormUtils implements OnInit {

  public istanza: model.IstanzaVO;
  public excludeIstances: number[];
  
  @Output('onConfirm') onConfirm: EventEmitter<any> = new EventEmitter<any>();

  rows: model.IstanzaVO[] = null;
  typeSelected: number = 0;

  columnsIstanze: any = [
    {name: 'Cod. istanza', prop: 'codice_istanza', width: '148'},
    {name: 'Tipo istanza', prop: 'tipologia_istanza.value', width: '148'},
    {name: 'Intestatario', prop: '_intestatario', width: '128'},
    {name: 'Comune', prop: 'comune.value', width: '108'},
    {name: 'Indirizzo', prop: '_indirizzo', width: '125'},
    {name: 'Stato', prop: 'stato_istanza.value', width: '88'},
    {name: 'Cambio stato', prop: 'data_creazione', width: '135'},
    {name: 'Data pres.', prop: 'data_registrazione_pratica', width: '128'}
  ];

  constructor(public mudeopenFoBeService: MudeopenFoBeService,
                      private modalService: NgbModal,
                      public messageService: MessageService) { 
    super(mudeopenFoBeService, messageService);
  }

  registratadapaForm: FormGroup;
  ngOnInit() {
    this.registratadapaForm = this.initRicercaIstanzaForm();
    this.onSearch();
  }

  initRicercaIstanzaForm(): FormGroup {
    return new FormGroup({
      descrizione: new FormControl(null, [ Validators.required, Validators.maxLength(500) ]),
      //numero_pratica: new FormControl(null, [ Validators.required, Validators.maxLength(20) ]),
      //data_pratica: new FormControl(null, [ Validators.required ]),
      numero_protocollo_comune: new FormControl(null, [ Validators.required, Validators.maxLength(20) ]),
      data_protocollo_comune: new FormControl(null, [ Validators.required ])
    });
  }

  public onSearch(page: any = 0) {
    if(page && page.pageSize) { page = page.offset; }

    this.mudeopenFoBeService.cercaIstanzeByFilter('{"result_type": {"eq": "frontoffice-slim2"}, "id_fascicolo": {"eq": '+this.istanza.id_fascicolo+'},"stato": {"nin": "BZZ,RPA,DFR,FRM,VRF"}, "id": {"nin":['+(this.excludeIstances||[]).join(',')+']} }', page, this.datatable.limit).subscribe(x => {
      this._handlePaging(x);
      this.rows = x.body;
      this.completeInfo(this.rows);
    });
  }

  completeInfo(lst) {
    lst.map(item => item._indirizzo = this.getIndirizzo(item.ubicazione || item.indirizzo) );
    lst.map(item => item._intestatario = this.getSubjectInfo('name', item.intestatario?.anagrafica) );
  }

  noIstanzaSelected: boolean;
  onSubmit(isOk: boolean = false) {
    if(!isOk) {
      this.modalService.dismissAll(); }
    else {
      if(this.typeSelected === 0 /* selection from datatable */) {
        if(this.rows.length == 1) {
          this.datatable.selected = this.rows; }

        this.noIstanzaSelected = !this.datatable.selected.length;
        if(!this.noIstanzaSelected){
          this.onConfirm.emit(this.datatable.selected[0]); }
      }
      else if(!this.registratadapaForm.invalid || this.commonUtils.validateForm(this.registratadapaForm)) {
          this.onConfirm.emit(this.registratadapaForm.value); }
    }
  }
}
