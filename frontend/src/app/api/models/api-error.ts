export interface ApiError {
  status: number;
  error: string;
  message: string;
  path: string;
  validationErrors: string[] | null;
  timestamp: string;
}