import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthGuard, AuthorizationGuard } from './guards';
import { PermissionService } from './services';

@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ],
  providers: [
    AuthGuard,
    AuthorizationGuard,
    PermissionService
  ]
})
export class CoreModule { }
