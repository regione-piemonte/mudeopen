import { Directive, Input, OnDestroy, TemplateRef, ViewContainerRef } from '@angular/core';
import { takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import { PermissionService } from '../../core/services';

@Directive({
  selector: '[appNgHasRole]'
})
export class NgHasRoleDirective implements OnDestroy {

  private destroy$ = new Subject<void>();

  private hasView = false;

  constructor(private templateRef: TemplateRef<any>,
              private viewContainer: ViewContainerRef,
              private permissionService: PermissionService) {
  }

  @Input() set appNgHasPermissions(tag: string) {
    this.permissionService.checkRole(tag)
      .pipe(
        takeUntil(this.destroy$),
      )
      .subscribe((can: boolean) => {
        if (can && !this.hasView) {
          this.viewContainer.createEmbeddedView(this.templateRef);
          this.hasView = true;
        } else if (!can && this.hasView) {
          this.viewContainer.clear();
          this.hasView = false;
        }
      });
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
