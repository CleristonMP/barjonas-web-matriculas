export const maskSocialIdNumber = (cpf: string) => {
  return cpf
    .replace(/\D/g, "")
    .replace(/(\d{3})(\d)/, "$1.$2")
    .replace(/(\d{3})(\d)/, "$1.$2")
    .replace(/(\d{3})(\d{2})$/, "$1-$2");
};

export const maskPhoneNumber = (phone: string) => {
  if (phone.length > 14) {
    return phone
      .replace(/\D/g, "")
      .replace(/(\d{2})(\d)/, "($1) $2")
      .replace(/(\(\d{2}\)\s\d)(\d)/, "$1 $2")
      .replace(/(\(\d{2}\)\s\d\s\d{4})(\d)/, "$1 $2");
  } else {
    return phone
      .replace(/\D/g, "")
      .replace(/(\d{2})(\d)/, "($1) $2")
      .replace(/(\(\d{2}\)\s\d{4})(\d)/, "$1 $2");
  }
};

export const maskZipCodeNumber = (zipCode: string) => {
  return zipCode
    .replace(/\D/g, "")
    .replace(/(\d{2})(\d)/, "$1.$2")
    .replace(/(\d{2}\.\d{3})(\d)/, "$1-$2");
};
