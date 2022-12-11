import { Address } from "./address";
import { City } from "./city";
import { NationalId } from "./nationalId";
import { Parent } from "./parent";
import { Phone } from "./phone";

export type Student = {
  enrollment?: number;
  name: string;
  lastName: string;
  gender: string;
  socialAssistance: boolean;
  race: string;
  disability: string;
  socialId: number;
  email: string;
  birthDate: string;
  nationality: string;
  birthPlace: City;
  nationalId: NationalId;
  address: Address;
  parents: Parent[];
  phones: Phone[];
};
