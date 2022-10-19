export type StudentToForm = {
  enrollment: number;
  name: string;
  lastName: string;
  cpf: string;
  birthDate: string;
  address: {
    id?: number;
    publicPlace: string;
    number: string;
    complement: string;
    zipCode: number;
    district: string;
    county: {
      id: number;
      name: string;
      state: string;
    };
  };
  schoolClass: {
    id: number;
    name: string;
    period: {
      id?: number;
      name: string;
    };
  };
  parent: {
    name: string;
    lastName: string;
    cpf: string;
    phone: string;
  };
};
