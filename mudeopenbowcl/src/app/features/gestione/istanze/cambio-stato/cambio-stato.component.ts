import { Component, OnInit, Output, EventEmitter, ChangeDetectorRef } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { FormUtils, MessageService, MudeopenFoBeService } from 'mudeopen-common';
import * as model from 'mudeopen-common';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-cambio-stato',
  templateUrl: './cambio-stato.component.html',
  styleUrls: ['./cambio-stato.component.css']
})
export class CambioStatoComponent extends FormUtils implements OnInit {

  public istanza: model.IstanzaVO;

  @Output('onConfirm') onConfirm: EventEmitter<any> = new EventEmitter<any>();

  constructor(public mudeopenFoBeService: MudeopenFoBeService,
                      private modalService: NgbModal,
                      public messageService: MessageService) { 
    super(mudeopenFoBeService, messageService);
  }

  registratadapaForm: FormGroup;
  ngOnInit() {
    this.registratadapaForm = this.initRicercaIstanzaForm();
  }

  initRicercaIstanzaForm(): FormGroup {
    return new FormGroup({
      numeroPratica: new FormControl(null, [ Validators.required ]),
      //dataPratica: new FormControl(null, [ Validators.required ]),
      numeroProtocollo: new FormControl(null, [ Validators.required ]),
      dataProtocollo: new FormControl(null, [ Validators.required ]),
      //note: new FormControl()
    });
  }

  onSubmit(isOk: boolean = false) {
    if(!isOk) {
      this.modalService.dismissAll(); }
    else if(!this.registratadapaForm.invalid || this.commonUtils.validateForm(this.registratadapaForm)) {
      this.confirmDialog("Sei sicuro di voler cambiare lo stato dell'istanza in 'REGISTRATA DA PA'?", () => {
        this.onConfirm.emit(this.registratadapaForm.value);
      }, "CAMBIA STATO");
    }
  }
}
