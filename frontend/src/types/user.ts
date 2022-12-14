export type User = {
  id: number;
  name: string;
  lastName: string;
  email: string;
  emailConfirmation: string;
  password: string;
  passwordConfirmation: string;
  roles: Role[];
};

export type Role = {
  id: number;
  authority: string;
};
