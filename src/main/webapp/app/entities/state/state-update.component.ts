import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IState, State } from 'app/shared/model/state.model';
import { StateService } from './state.service';
import { ICountry } from 'app/shared/model/country.model';
import { CountryService } from 'app/entities/country/country.service';

@Component({
  selector: 'jhi-state-update',
  templateUrl: './state-update.component.html'
})
export class StateUpdateComponent implements OnInit {
  isSaving = false;
  countries: ICountry[] = [];

  editForm = this.fb.group({
    id: [],
    initials: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(2)]],
    name: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
    createdDate: [null, [Validators.required]],
    country: []
  });

  constructor(
    protected stateService: StateService,
    protected countryService: CountryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ state }) => {
      if (!state.id) {
        const today = moment();
        state.createdDate = today;
      }

      this.updateForm(state);

      this.countryService.query().subscribe((res: HttpResponse<ICountry[]>) => (this.countries = res.body || []));
    });
  }

  updateForm(state: IState): void {
    this.editForm.patchValue({
      id: state.id,
      initials: state.initials,
      name: state.name,
      createdDate: state.createdDate ? state.createdDate.format(DATE_TIME_FORMAT) : null,
      country: state.country
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const state = this.createFromForm();
    if (state.id !== undefined) {
      this.subscribeToSaveResponse(this.stateService.update(state));
    } else {
      this.subscribeToSaveResponse(this.stateService.create(state));
    }
  }

  private createFromForm(): IState {
    return {
      ...new State(),
      id: this.editForm.get(['id'])!.value,
      initials: this.editForm.get(['initials'])!.value,
      name: this.editForm.get(['name'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      country: this.editForm.get(['country'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IState>>): void {
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

  trackById(index: number, item: ICountry): any {
    return item.id;
  }
}
