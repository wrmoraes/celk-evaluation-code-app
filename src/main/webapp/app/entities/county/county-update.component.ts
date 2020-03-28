import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICounty, County } from 'app/shared/model/county.model';
import { CountyService } from './county.service';
import { IState } from 'app/shared/model/state.model';
import { StateService } from 'app/entities/state/state.service';

@Component({
  selector: 'jhi-county-update',
  templateUrl: './county-update.component.html'
})
export class CountyUpdateComponent implements OnInit {
  isSaving = false;
  states: IState[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
    createdDate: [null, [Validators.required]],
    state: []
  });

  constructor(
    protected countyService: CountyService,
    protected stateService: StateService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ county }) => {
      if (!county.id) {
        const today = moment();
        county.createdDate = today;
      }

      this.updateForm(county);

      this.stateService.query().subscribe((res: HttpResponse<IState[]>) => (this.states = res.body || []));
    });
  }

  updateForm(county: ICounty): void {
    this.editForm.patchValue({
      id: county.id,
      name: county.name,
      createdDate: county.createdDate ? county.createdDate.format(DATE_TIME_FORMAT) : null,
      state: county.state
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const county = this.createFromForm();
    if (county.id !== undefined) {
      this.subscribeToSaveResponse(this.countyService.update(county));
    } else {
      this.subscribeToSaveResponse(this.countyService.create(county));
    }
  }

  private createFromForm(): ICounty {
    return {
      ...new County(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      state: this.editForm.get(['state'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICounty>>): void {
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

  trackById(index: number, item: IState): any {
    return item.id;
  }
}
