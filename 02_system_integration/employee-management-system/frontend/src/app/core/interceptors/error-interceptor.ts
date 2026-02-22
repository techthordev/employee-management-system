import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const snackBar = inject(MatSnackBar);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      let message = 'Unexpected error occurred';

      switch (error.status) {
        case 400: message = error.error?.message ?? 'Invalid request'; break;
        case 401: message = 'Invalid credentials'; break;
        case 403: message = 'Access denied'; break;
        case 404: message = 'Resource not found'; break;
        case 409: message = 'Resource already exists'; break;
        case 500: message = 'Server error. Please try again later'; break;
      }

      snackBar.open(message, 'Close', { duration: 4000 });
      return throwError(() => error);
    })
  );
};