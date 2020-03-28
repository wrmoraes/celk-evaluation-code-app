import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAddress, Address } from 'app/shared/model/address.model';
import { AddressService } from './address.service';
import { ICounty } from 'app/shared/model/county.model';
import { CountyService } from 'app/entities/county/county.service';

@Component({
  selector: 'jhi-address-update',
  templateUrl: './address-update.component.html'
})
export class AddressUpdateComponent implements OnInit {
  isSaving = false;
  counties: ICounty[] = [];

  editForm = this.fb.group({
    id: [],
    streetType: [null, [Validators.required]],
    streetName: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(50)]],
    number: [null, [Validators.required]],
    postalCode: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(10)]],
    county: []
  });

  constructor(
    protected addressService: AddressService,
    protected countyService: CountyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ address }) => {
      this.updateForm(address);

      this.countyService.query().subscribe((res: HttpResponse<ICounty[]>) => (this.counties = res.body || []));
    });
  }

  updateForm(address: IAddress): void {
    this.editForm.patchValue({
      id: address.id,
      streetType: address.streetType,
      streetName: address.streetName,
      number: address.number,
      postalCode: address.postalCode,
      county: address.county
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const address = this.createFromForm();
    if (address.id !== undefined) {
      this.subscribeToSaveResponse(this.addressService.update(address));
    } else {
      this.subscribeToSaveResponse(this.addressService.create(address));
    }
  }

  private createFromForm(): IAddress {
    return {
      ...new Address(),
      id: this.editForm.get(['id'])!.value,
      streetType: this.editForm.get(['streetType'])!.value,
      streetName: this.editForm.get(['streetName'])!.value,
      number: this.editForm.get(['number'])!.value,
      postalCode: this.editForm.get(['postalCode'])!.value,
      county: this.editForm.get(['county'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAddress>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ICounty): any {
    return item.id;
  }
}
