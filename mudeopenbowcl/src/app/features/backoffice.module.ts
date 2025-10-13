/*
* SPDX-FileCopyrightText: Copyright 2020 - 2021 | CSI Piemonte
* SPDX-License-Identifier: EUPL-1.2
*/

declare var $$: any;



import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { BackofficeRoutingModule } from './backoffice-routing.module';
import { NotAuthorizedComponent } from './not-authorized/not-authorized.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { SharedBoModule } from '../shared/shared-bo.module';
import { UserNotFoundComponent } from './user-not-found/user-not-found.component';
import { NoRoleAssignedComponent } from './no-role-assigned/no-role-assigned.component';

@NgModule({
  imports: [
    BackofficeRoutingModule,
    FormsModule,
    SharedBoModule
//    MudeCommonModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  declarations: [NotAuthorizedComponent, NotFoundComponent, UserNotFoundComponent, NoRoleAssignedComponent],
})
export class BackofficeModule {}
