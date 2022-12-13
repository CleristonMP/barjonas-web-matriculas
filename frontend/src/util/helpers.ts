export const getEnumKeys = <T extends Object>(
  enumToDeconstruct: T
): Array<keyof typeof enumToDeconstruct> => {
  return Object.keys(enumToDeconstruct) as Array<
    keyof typeof enumToDeconstruct
  >;
};

export const getPeriod_PT_BR = (value: string) => {
  const periods: any = {
    MORNING: "Matutino",
    EVENING: "Vespertino",
    NIGHT: "Noturno",
  };
  return periods[value];
};

export const getGender_PT_BR = (value: string) => {
  const genders: any = {
    MALE: "Masculino",
    FEMALE: "Feminino",
    NON_BINARY: "Não binário",
  };
  return genders[value];
};

export const getRace_PT_BR = (value: string) => {
  const races: any = {
    WHITE: "Branca",
    BLACK: "Negra",
    BROWN: "Parda",
    ASIAN: "Amarela",
    NATIVE: "Índigena",
    NOT_DECLARED: "Não declarada",
  };
  return races[value];
};

export const getNationality_PT_BR = (value: string) => {
  const nationalities: any = {
    BRAZILIAN: "Brasileira",
    URUGUAYAN: "Uruguaia",
    PARAGUAYAN: "Paraguaia",
    BOLIVIAN: "Boliviana",
    PERUVIAN: "Peruana",
    COLOMBIAN: "Colombiana",
    VENEZOLAN: "Venezuelana",
    SURINAMESE: "Surinamesa",
    FRENCH_GUYANASE: "Franco-guianense",
    GUYANESE: "Guianesa",
    ARGENTINE: "Argentina",
    CHILEAN: "Chilena",
    ECUADORIAN: "Equatoriana",
    TRINITARIO_TOBAGENSE: "Trinitário-tobagense",
    PANAMENIAN: "Panamense",
    COSTARRICAN: "Costarriquense",
    NICARAGUAN: "Nicaraguense",
    HONDURAN: "Hondurenha",
    SALVADORIAN: "Salvadorenha",
    GUATEMALAN: "Guatemalense",
    MEXICAN: "Mexicana",
    US: "Norte-americana",
    CUBAN: "Cubana",
    JAMAICAN: "Jamaicana",
    HAITIAN: "Haitiana",
    DOMINICAN: "Dominicana",
    PUERTORICAN: "Porto-riquenha",
  };
  return nationalities[value];
};
