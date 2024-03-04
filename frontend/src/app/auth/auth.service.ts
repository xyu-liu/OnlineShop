import { EventEmitter, Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { catchError, tap } from 'rxjs/operators';
import { throwError, BehaviorSubject } from 'rxjs';

import { User } from './user.model';

export interface AuthResponseData {
  success: boolean;
  message: string;
  token: string; 
  permission: string
}

@Injectable({ providedIn: 'root' })
export class AuthService {

  user: User;

  private tokenExpirationTimer: any;

  statusUpdated = new EventEmitter<string>();


  constructor(private http: HttpClient, private router: Router) {}

  signup(username:string, email: string, password: string) {
    return this.http
      .post<AuthResponseData>(
        'http://localhost:8090/signup',
        {
          username: username,
          email: email,
          password: password,
        }
      )
      .pipe(
        catchError(this.uphandleError),
        tap(resData => {
          this.handleAuthentication(
            resData.token,
            resData.permission
          );
        })
      );
  }

  login(username:string, password: string) {
    return this.http
      .post<AuthResponseData>(
        'http://localhost:8090/login',
        {
          username: username,
          password: password,
        }
      )
      .pipe(
        catchError(this.inhandleError),
        tap(resData => {
          this.handleAuthentication(
            resData.token,
            resData.permission
          );
        })
      );
  }

  private inhandleError(errorRes: HttpErrorResponse) {
    let errorMessage = 'Login Fail';
    return throwError(errorMessage);
  }

  private uphandleError(errorRes: HttpErrorResponse) {
    let errorMessage = 'Sign up FAIL: Repead Email/Username';
    return throwError(errorMessage);
  }


  private handleAuthentication(
    token: string,
    permission: string
  ) {
    const user = new User(permission, token);
    this.user = user;
    localStorage.setItem('userData', JSON.stringify(user));
    this.statusUpdated.emit("login");
  }


  logout() {
    this.user = null;
    this.router.navigate(['/auth']);
    localStorage.removeItem('userData');
    if (this.tokenExpirationTimer) {
      clearTimeout(this.tokenExpirationTimer);
    }
    this.tokenExpirationTimer = null;
    this.statusUpdated.emit("logout");
  }



  
}
