import { Moment } from 'moment';
import { IState } from 'app/shared/model/state.model';

export interface ICounty {
  id?: number;
  name?: string;
  createdDate?: Moment;
  state?: IState;
}

export class County implements ICounty {
  constructor(public id?: number, public name?: string, public createdDate?: Moment, public state?: IState) {}
}
