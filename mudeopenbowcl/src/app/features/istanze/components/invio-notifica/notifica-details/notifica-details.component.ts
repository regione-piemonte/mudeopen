import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {
  FormUtils,
  MessageService,
  MudeopenFoBeService,
} from 'mudeopen-common';
import { IstanzaService } from '../../../services/istanza.service';

@Component({
  selector: 'app-notifica-details',
  templateUrl: './notifica-details.component.html',
  styleUrls: ['./notifica-details.component.css'],
})
export class NotificaDetailsComponent extends FormUtils implements OnInit {
  constructor(
    private modalService: NgbModal,
    public mudeopenFoBeService: MudeopenFoBeService,
    public messageService: MessageService,
    public istanzaService: IstanzaService
  ) {
    super(mudeopenFoBeService, messageService);
  }
notification: any
  ngOnInit(): void {
    console.log(this.notification)
  }
  goBack(){
    this.modalService.dismissAll()
  }
}
