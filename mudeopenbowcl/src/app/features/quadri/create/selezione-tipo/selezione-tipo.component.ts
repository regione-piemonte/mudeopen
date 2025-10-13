import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MudeopenFoBeService } from 'mudeopen-common';
import * as model from 'mudeopen-common';
import { getComponentCategory } from '../../services/quadri-utils';



@Component({
  selector: 'app-selezione-tipo',
  templateUrl: './selezione-tipo.component.html',
  styleUrls: ['./selezione-tipo.component.scss']
})
export class SelezioneTipoComponent implements OnInit {

  @Output() confirmEvent: EventEmitter<any> = new EventEmitter<any>();

  public data: model.TipoQuadroVO;
  dataStatic: model.TipoQuadroVO;
  public messageAction: string;

  categoria_quadro_list: any;

  categoria_quadro: any;
  tipo_quadro_list: any = null;

  constructor(public mudeopenFoBeService: MudeopenFoBeService) {
    this.categoria_quadro_list = getComponentCategory();
  }

  ngOnInit() {
    if(!this.data) {
      this.mudeopenFoBeService.listaTipoQuadri(JSON.stringify({
        "attivo": {"eq":'no'}
      }), 0, 1000).subscribe(x => {
        this.tipo_quadro_list = x.body;
      });
    }
    else {
	this.dataStatic = this.data; }
  }

  onSubmit(isOk: boolean = false) {
    this.confirmEvent.emit(!isOk?null : { 
      'tipo_quadro': this.data,
      'categoria_quadro': this.categoria_quadro
    });
  }

}
