import { ICounty } from 'app/shared/model/county.model';
import { StreetType } from 'app/shared/model/enumerations/street-type.model';

export interface IAddress {
  id?: number;
  streetType?: StreetType;
  streetName?: string;
  number?: number;
  postalCode?: string;
  county?: ICounty;
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public streetType?: StreetType,
    public streetName?: string,
    public number?: number,
    public postalCode?: string,
    public county?: ICounty
  ) {}
}
