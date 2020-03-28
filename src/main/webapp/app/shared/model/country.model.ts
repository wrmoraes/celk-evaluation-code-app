import { Moment } from 'moment';
import { IState } from 'app/shared/model/state.model';

export interface ICountry {
  id?: number;
  name?: string;
  createdDate?: Moment;
  states?: IState[];
}

export class Country implements ICountry {
  constructor(public id?: number, public name?: string, public createdDate?: Moment, public states?: IState[]) {}
}
