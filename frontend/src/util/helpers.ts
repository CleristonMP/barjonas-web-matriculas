export const getEnumKeys = <T extends Object>(
  enumToDeconstruct: T
): Array<keyof typeof enumToDeconstruct> => {
  return Object.keys(enumToDeconstruct) as Array<
    keyof typeof enumToDeconstruct
  >;
};

export const getPeriodPT_BR = (value: string) => {
  const periods: any = {
    MORNING: "Matutino",
    EVENING: "Vespertino",
    NIGHT: "Noturno",
  };
  return periods[value];
};