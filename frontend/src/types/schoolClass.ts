import { Phase } from "./phase";
import { Student } from "./student";

export type SchoolClass = {
  id: number;
  name: string;
  period: string;
  phase: Phase;
  students: Student[];
};
