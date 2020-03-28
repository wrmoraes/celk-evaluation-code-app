import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CelkTestModule } from '../../../test.module';
import { StateUpdateComponent } from 'app/entities/state/state-update.component';
import { StateService } from 'app/entities/state/state.service';
import { State } from 'app/shared/model/state.model';

describe('Component Tests', () => {
  describe('State Management Update Component', () => {
    let comp: StateUpdateComponent;
    let fixture: ComponentFixture<StateUpdateComponent>;
    let service: StateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CelkTestModule],
        declarations: [StateUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(StateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StateUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StateService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new State(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new State();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
