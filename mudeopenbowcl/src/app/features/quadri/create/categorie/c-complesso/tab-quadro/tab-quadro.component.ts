import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { FormUtils } from 'mudeopen-common';
import { MudeopenFoBeService } from 'mudeopen-common';
import * as model from 'mudeopen-common';

@Component({
  selector: 'app-tab-quadro',
  templateUrl: './tab-quadro.component.html',
  styleUrls: ['./tab-quadro.component.scss']
})
export class TabQuadroComponent extends FormUtils implements OnInit {

  public tab: any;
  public tabType: number = 0;
  public isReadonly: boolean = false;
  public changeTypeAllowed: boolean = true;
  public quadriFomrioList: model.SelectVO[] = [];

  tabForm: FormGroup;

  @Output('confirmEvent') confirmEvent: EventEmitter<any> = new EventEmitter<any>();

  constructor(public mudeopenFoBeService: MudeopenFoBeService) {
    super(mudeopenFoBeService, null);
    this.disableCountryLoading = true;

    this.tabForm = new FormGroup({
      label: new FormControl(null, [Validators.required, Validators.maxLength(50)]),
      tag: new FormControl(),
      title: new FormControl(),
      quadro: new FormControl(null, [Validators.required]),
      formValidation: new FormControl(),
      activationCheck: new FormControl(),
      dependencyCheck: new FormControl(),
      eraseKeysOnSubmit: new FormControl()
    });
  }

  ngOnInit() {
    if(this.tab) {
      this.setTabType(this.tabType);
      const data = JSON.parse(JSON.stringify(this.tab));
      data.formValidation = this.convertArray(data.formValidation);
      data.activationCheck = this.convertArray(data.activationCheck);
      data.dependencyCheck = this.convertArray(data.dependencyCheck);
      if(this.tab.id_quadro && this.tab.key)
        data.quadro = this.tab.id_quadro + ' ' + this.tab.key;

      this.patchFormValues(this.tabForm, data);
    }

    if(this.isReadonly) {
      this.tabForm.disable(); }
  }

  convertArray(obj: any): any {
    if(Array.isArray(obj)) {
      return JSON.stringify(obj); }
    return obj;
  }

  convert2Array(str: string): any {
    if(str && str.trim().startsWith('[')) {
      return JSON.parse(str); }
    return str;
  }

  setTabType(id: number) {
    this.tabForm.get('quadro')[(this.tabType = id) == 1? 'disable' : 'enable']();
  }

  quadroRequired: boolean;
  onSubmit(isOk: boolean = false) {
    this.quadroRequired = null;

    if(!isOk) {
      this.confirmEvent.emit(null); }
    else if(this.tabForm.invalid) {
      this.commonUtils.validateForm(this.tabForm); }
    else {
      const frm = this.tabForm.value;
      if(this.tabType === 1) {
        this.confirmEvent.emit({
//_NOEXIT: true,
          "label": frm.label, 
          "tag": frm.tag, 
          "title": frm.title,
          "subtabs": this.tab.subtabs || []
        }); }
      else {
        if(this.quadroRequired = !frm.quadro) return;

        this.confirmEvent.emit({
//_NOEXIT: true,
          "label": frm.label, 
          "tag": frm.tag, 
          "title": frm.title,
          "key": frm.quadro.substring(frm.quadro.indexOf(' ') + 1),
          "id_quadro": frm.quadro.substring(0, frm.quadro.indexOf(' ')),
          "formValidation": this.convert2Array(frm.formValidation),
          "activationCheck": this.convert2Array(frm.activationCheck),
          "dependencyCheck": this.convert2Array(frm.dependencyCheck),
          "eraseKeysOnSubmit": frm.eraseKeysOnSubmit
        });
      }  }
  }

  isDisabled(pg: any) {
    return !pg || pg.checked;
  }

}
