import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'country',
        loadChildren: () => import('./country/country.module').then(m => m.CelkCountryModule)
      },
      {
        path: 'state',
        loadChildren: () => import('./state/state.module').then(m => m.CelkStateModule)
      },
      {
        path: 'county',
        loadChildren: () => import('./county/county.module').then(m => m.CelkCountyModule)
      },
      {
        path: 'address',
        loadChildren: () => import('./address/address.module').then(m => m.CelkAddressModule)
      }

      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class CelkEntityModule {}
