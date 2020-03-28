import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICounty } from 'app/shared/model/county.model';

type EntityResponseType = HttpResponse<ICounty>;
type EntityArrayResponseType = HttpResponse<ICounty[]>;

@Injectable({ providedIn: 'root' })
export class CountyService {
  public resourceUrl = SERVER_API_URL + 'api/counties';

  constructor(protected http: HttpClient) {}

  create(county: ICounty): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(county);
    return this.http
      .post<ICounty>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(county: ICounty): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(county);
    return this.http
      .put<ICounty>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICounty>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICounty[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(county: ICounty): ICounty {
    const copy: ICounty = Object.assign({}, county, {
      createdDate: county.createdDate && county.createdDate.isValid() ? county.createdDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((county: ICounty) => {
        county.createdDate = county.createdDate ? moment(county.createdDate) : undefined;
      });
    }
    return res;
  }
}
