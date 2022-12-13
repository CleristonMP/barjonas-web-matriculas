export const formatDate = (date: string) => {
  const newDate = new Date(date);
  const day =
    newDate.getDate() <= 9 ? "0" + newDate.getDate() : newDate.getDate();
  const month =
    newDate.getMonth() <= 9 ? "0" + newDate.getMonth() : newDate.getMonth();
  const year = newDate.getFullYear();

  return day + " / " + month + " / " + year;
};

export const formatSocialId = (value: string) => {
  const cnpjCpf = value.replace(/\D/g, "");

  if (cnpjCpf.length === 11) {
    return cnpjCpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/g, "$1.$2.$3-$4");
  }

  return cnpjCpf.replace(
    /(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})/g,
    "$1.$2.$3/$4-$5"
  );
};
/* Crédito: https://gist.github.com/marceloneppel/dd9c17a01c1a8031c760b034dad0efd9 */

export const formatCep = (cep: string) => {
  return cep
    .replace(/\D/g, "")
    .replace(/(\d{5})(\d)/, "$1-$2")
    .replace(/(-\d{3})\d+?$/, "$1");
};

export const formatPhoneNumber = (phoneNumber: string) => {
  return phoneNumber
    .replace(/\D/g, "")
    .replace(/(\d{2})(\d)/, "($1) $2")
    .replace(/(\d{5})(\d)/, "$1-$2")
    .replace(/(-\d{4})\d+?$/, "$1");
};
/* Crédito: https://joaorodrs.medium.com/formatando-n%C3%BAmero-de-celular-cpf-cnpj-e-cep-com-regex-no-react-a2ee498fd9e9 */

export const formatRole = (role: string) => {
  const roles = {
    ROLE_ADMIN: "Admin",
    ROLE_OPERATOR: "Operador",
  }
  return roles[role as keyof typeof roles];
}
