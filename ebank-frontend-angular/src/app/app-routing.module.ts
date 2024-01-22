import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CustomersComponent} from "./ui/customers/customers.component";
import {AuthGuard} from "./guards/auth.guard";

const routes: Routes = [
  {
    path : "customers", component : CustomersComponent, canActivate : [AuthGuard], data : { roles : ['ADMIN']}
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
