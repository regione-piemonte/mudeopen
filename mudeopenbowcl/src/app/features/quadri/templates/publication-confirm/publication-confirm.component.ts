import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-publication-confirm',
  templateUrl: './publication-confirm.component.html',
  styleUrls: ['./publication-confirm.component.scss']
})
export class PublicationConfirmComponent {

  @Output() confirmEvent: EventEmitter<any> = new EventEmitter<any>();

  public data: any;

  simplePublish: string = null;

  onSubmit(isOk: any = false) {
    this.confirmEvent.emit(isOk? { simplePublish: this.simplePublish } : null);
  }

}
