import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  Router,
  UrlTree
} from '@angular/router';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { AuthService } from './auth.service';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {
    
  }

  canActivate(route: ActivatedRouteSnapshot, router: RouterStateSnapshot):
    | boolean
    | UrlTree
    | Promise<boolean | UrlTree>
    | Observable<boolean | UrlTree> {
      
      if (this.authService.user) {
        const userRole = this.authService.user.permission;
        console.log(userRole, route.data.role);
        if (route.data.role && route.data.role.indexOf(userRole) === -1) {
          this.router.navigate(['/auth']);
          return false;
        } else {
          return true;
        }
      } else {
        this.router.navigate(['/auth']);
      }
  }

}
