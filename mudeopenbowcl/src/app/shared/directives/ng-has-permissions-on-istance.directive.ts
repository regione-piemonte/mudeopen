import {
  AfterViewInit,
  Directive,
  Input,
  OnDestroy,
  OnInit,
  TemplateRef,
  ViewContainerRef,
} from '@angular/core';
import { map, takeUntil } from 'rxjs/operators';
import { Observable, of, Subject } from 'rxjs';
import { PermissionService } from '../../core/services';

@Directive({
  selector: '[appNgHasPermissionsOnIstance]',
})
export class NgHasPermissionsOnIstanceDirective implements OnDestroy {
  private destroy$ = new Subject<void>();
  private hasView = false;

  constructor(
    private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef,
    private permissionService: PermissionService
  ) {}

  @Input() set appNgHasPermissionsOnIstance([tag, functions]: any[]) {
    //debugger
    console.log(tag, functions);
    this.checkPermission(tag, functions)
      .pipe(takeUntil(this.destroy$))
      .subscribe((can: boolean) => {
        if (can && !this.hasView) {
          this.viewContainer.createEmbeddedView(this.templateRef);
          this.hasView = true;
        } else if (!can && this.hasView) {
          this.viewContainer.clear();
          debugger;
          this.hasView = false;
        }
      });
  }

  checkPermission(tag: string, functions: any): Observable<boolean> {
    const hasPermissions = functions.find(
      ({ cod_funzione }) => cod_funzione === tag
    );
    console.log(hasPermissions);
    return of(!!hasPermissions);
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
