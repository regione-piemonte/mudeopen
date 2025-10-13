import {
  AfterViewInit,
  Component,
  EventEmitter,
  Input, OnChanges,
  OnInit,
  Output, SimpleChanges, TemplateRef,
  ViewChild,
} from '@angular/core';
import { TableColumn } from '@swimlane/ngx-datatable';

@Component({
  selector: 'app-mude-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css'],
})
export class TableComponent implements OnChanges {
  @ViewChild('table') table: any;
  currentExpandedRow: any | null = null;

  @Input() rowIdentity: (value) => any = () => null;
  @Input() rowDetail: TemplateRef<any>;
  @Input() rows: any[];
  @Input() rowExpanded: any;
  @Input() columns: TableColumn[];
  @Input() datatable: any = {
    offset: 0,
    count: 0,
    limit: 10,
    selected: [],
    pages: 0,
    messages: {
      'emptyMessage': 'Non è stato trovato nessun dato',
      'totalMessage': 'Record totali',
    },
  };

  @Output() pageChanged: EventEmitter<any> = new EventEmitter<any>();
  @Output() rowAction: EventEmitter<any> = new EventEmitter<any>();

  ngOnChanges(changes: SimpleChanges): void {
    if (changes?.rowExpanded) {
      this.confirmExpandRow(this.rowExpanded);
    }
  }

  changePage(event: any) {
    this.pageChanged.emit(event);
  }

  confirmExpandRow(row: any) {
    this.table.rowDetail.collapseAllRows();
    if (this.rowIdentity(row) === this.rowIdentity(this.currentExpandedRow)) {
      this.currentExpandedRow = null;
      return;
    }
    console.log('expandrow')
    this.currentExpandedRow = row;
    this.table.rowDetail.toggleExpandRow(row);
  }

}
