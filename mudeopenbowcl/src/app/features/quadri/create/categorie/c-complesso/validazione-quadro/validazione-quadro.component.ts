import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { getAlertTypes } from '../../../../services/quadri-utils';

@Component({
  selector: 'app-validazione-quadro',
  templateUrl: './validazione-quadro.component.html',
  styleUrls: ['./validazione-quadro.component.scss']
})
export class ValidazioneQuadroComponent implements OnInit {
  public data: any;
  public isReadonly: boolean = false;

  @Output('confirmEvent') confirmEvent: EventEmitter<any> = new EventEmitter<any>();

  check: string;
  message: string;
  type: string;
  title: string;

  invalidTabStructure: boolean;
  types: any = getAlertTypes(true);

  ngOnInit() {
    if(this.data) {
      this.check = this.data.check;
      this.message = this.data.message;
      this.type = this.data.type;
    }
  }

  onSubmit(isOk: boolean = false) {
    if(!isOk) {
      this.confirmEvent.emit(null);
      return;
    }

    if(this.invalidTabStructure = !this.check?.length) {
      return; }

    this.confirmEvent.emit(!isOk?null : {
      "check": this.check,
      "message": this.message,
      "type": this.type
    });
  }

	/* 
	* nome metodo "onChangeSelection"; descrizione: 
	* @param (value: any)
	* @returns 
	*/ 

  previewMessage: boolean = true;
  onChangeSelection(value: any) {
    this.previewMessage = false;
    setTimeout(_ => { 
      this.previewMessage = true;}, 100);
  }

  get _message(): string {
    return this.message;
  }

  timeout: any;
  set _message(val: string) {
    this.message = val;
    this.previewMessage = false;
    if(!this.timeout) {
      this.timeout = setTimeout(_ => { 
        this.timeout = null; this.previewMessage = true;}, 1000); }
  }
}
