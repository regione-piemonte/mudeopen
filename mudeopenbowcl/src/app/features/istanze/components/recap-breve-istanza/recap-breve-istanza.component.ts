import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IstanzaBO } from '../../../../shared/model/istanzaBO.model';
import { FormUtils, MessageService, MudeopenFoBeService } from 'mudeopen-common';

@Component({
  selector: 'app-recap-breve-istanza',
  templateUrl: './recap-breve-istanza.component.html',
  styleUrls: ['./recap-breve-istanza.component.css'],
})
export class RecapBreveIstanzaComponent extends FormUtils implements OnInit {

  istanza: IstanzaBO;
  indirizzoIstanza: string;

  constructor(public mudeopenFoBeService: MudeopenFoBeService,
              public messageService: MessageService,
              private activatedRoute: ActivatedRoute,
  ) {
    super(mudeopenFoBeService, messageService);
  }

  ngOnInit(): void {
    this.istanza = this.activatedRoute.snapshot.data['istanza'];
    this.indirizzoIstanza = this.getIndirizzo((this.istanza as any).ubicazione || this.istanza.indirizzo);
  }

}
