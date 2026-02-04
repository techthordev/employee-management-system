import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Page } from '../models/page';
import { Employee } from '../models/employee';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class EmployeeApiService {
  
  private readonly baseUrl = 'http://localhost:8080/api/employees';

  constructor(private http: HttpClient) {}

  /**
   * Get paginated list of employees.
   * Supports pagination and sorting.
   */
  getEmployees(page: number, size: number, sort: string): Observable<Page<Employee>> {
    const params = new HttpParams().set('page', page).set('size', size).set('sort', sort);

    return this.http.get<Page<Employee>>(this.baseUrl, { params });
  }

  /**
   * Get a single employee by ID.
   */
  getEmployeeById(id: number): Observable<Employee> {
    return this.http.get<Employee>(`${this.baseUrl}/${id}`);
  }

  /**
   * Create a new employee.
   */
  createEmployee(employee: Employee): Observable<Employee> {
    return this.http.post<Employee>(this.baseUrl, employee);
  }

  /**
   * Update an existing employee.
   */
  updateEmployee(id: number, employee: Employee): Observable<Employee> {
    return this.http.put<Employee>(`${this.baseUrl}/${id}`, employee);
  }

  /**
   * Delete an employee by ID.
   */
  deleteEmployee(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
