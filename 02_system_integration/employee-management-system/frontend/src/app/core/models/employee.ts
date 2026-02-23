import { Department } from "./department";

export interface Employee {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  department: Department;
}