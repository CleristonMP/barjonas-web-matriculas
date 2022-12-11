import { City } from "./city";

export type Address = {
  id: number;
  zipCode: number;
  district: string;
  number: string;
  complement: string;
  city: City;
};
