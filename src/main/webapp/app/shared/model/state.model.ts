import { Moment } from 'moment';
import { ICounty } from 'app/shared/model/county.model';
import { ICountry } from 'app/shared/model/country.model';

export interface IState {
  id?: number;
  initials?: string;
  name?: string;
  createdDate?: Moment;
  counties?: ICounty[];
  country?: ICountry;
}

export class State implements IState {
  constructor(
    public id?: number,
    public initials?: string,
    public name?: string,
    public createdDate?: Moment,
    public counties?: ICounty[],
    public country?: ICountry
  ) {}
}
