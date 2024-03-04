import { Component, OnInit, OnDestroy } from '@angular/core';

import { AuthService } from '../auth/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html'
})
export class HeaderComponent implements OnInit{
  isAuthenticated = false;
  isAmin: boolean = false;


  constructor(
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.authService.statusUpdated.subscribe(
      (status: string) => {
        this.isAuthenticated = !!this.authService.user;
        if(this.authService.user) {
          this.isAmin = this.authService.user.permission === 'admin';
        }
      }
    );
    //this.isAuthenticated = !!this.authService.user;

    
  }

  onLogout() {
    this.authService.logout();
  }

}
