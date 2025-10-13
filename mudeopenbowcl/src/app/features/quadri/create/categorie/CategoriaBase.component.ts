import { Component, OnInit } from '@angular/core';
import { FormUtils } from 'mudeopen-common';
import { MessageService } from 'mudeopen-common';
import { MudeopenFoBeService } from 'mudeopen-common';
import * as model from 'mudeopen-common';


@Component({
  selector: 'app-CategoriaBase',
  templateUrl: './CategoriaBase.component.html',
  styleUrls: ['./CategoriaBase.component.css']
})
export class CategoriaBaseComponent extends FormUtils {

  constructor(public mudeopenFoBeService: MudeopenFoBeService, 
                    public messageService: MessageService) {
    super(mudeopenFoBeService, messageService);
  }

  docxFileList: any = [];
  selectDocx(data: any, file: any) {
    if(file.name.toLowerCase().substring(file.name.length - 5) != '.docx') {
      return false; }

    this.docxFileList[0] = file;
  }

  downloadDocx(data: any, file: any, modelname: string = null) {
    const modello: model.ModelloVO = data[modelname]; // 'modello_allegato' || 'modello_documentale'

    if(modello?.id) {
      this.mudeopenFoBeService.downloadModello(modello.id, null).subscribe(x => {
        this.download2user(x);
      }); }
  }

  removeDocx(data: any, info: any) {
    this.docxFileList = [];

    const modello: model.ModelloVO = (data.modello_allegato || data.modello_documentale);
    if(modello?.id) {
      modello.dimensione_file = -1;  }// mark to be deleted
  }
  
  toggleTableDetails(table: any, row: any, expanded: boolean) {
    if(!expanded) {
      table.rowDetail.collapseAllRows();
      table.rowDetail.toggleExpandRow(row);
    }
    else if (expanded) {
      table.rowDetail.collapseAllRows(); }
  }

  initialData: string;
  getDataDifferences(... data): boolean {
    const newData = JSON.stringify(Object.assign({ attach: this.docxFileList }, data));
    return !!(this.initialData != newData && (this.initialData = newData));
  }

  getJS(js: any) {
    if(js) {
      try { js = JSON.stringify(js) } catch(e) {}; }
    return js || '';
  }
}
