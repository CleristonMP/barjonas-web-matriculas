import { City } from "./city";

export type NationalId = {
  id: number;
  number: number;
  issuingEntity: string;
  city: City;
};
