/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/


import { Component, HostBinding, ElementRef, OnInit, ViewChild, AfterViewInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-builder',
  templateUrl: './builder.component.html',
  styleUrls: ['./builder.component.scss']
})
export class BuilderComponent implements OnInit {
  @Output() confirmEvent: EventEmitter<any> = new EventEmitter<any>();;

  public formioTemplate: any = {
    components: []
  }
  public submission: any  = {};

  showCode: boolean;
  formio: any;

  ngOnInit() {
    this.formio = this.formioTemplate;
  }

  get showData(): string {
    return JSON.stringify(this.formio, null, 4);
  }

  onChange(event) {
    this.formioTemplate = event.form;
  }

  onSubmit(isOk = false) {
    this.confirmEvent.emit(!isOk? null : { 
      formio: this.formioTemplate,
      submission: this.submission?this.submission : null // not handled by now
    });
  }

}