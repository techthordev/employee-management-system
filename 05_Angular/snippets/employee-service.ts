@Injectable({providedIn: 'root'})
export class EmployeeService {
  constructor(private http: HttpClient) {}
  list(): Observable<Employee[]> {
    return this.http.get<Employee[]>('/api/employees');
  }
}
