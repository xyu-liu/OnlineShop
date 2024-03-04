import { Component, OnInit } from '@angular/core';
import { UserProduct } from '../product/user-products/user-product.model';
import { WebService } from '../shared/web-request.service';

@Component({
  selector: 'app-user-stat',
  templateUrl: './user-stat.component.html',
  styleUrls: ['./user-stat.component.css']
})
export class UserStatComponent implements OnInit {

  recentProducts: UserProduct[] = [];
  frequentProducts: UserProduct[] = [];

  constructor(private webService: WebService) { }

  ngOnInit(): void {
    this.webService.getUserFrequentProducts().subscribe(response => {
      if (response.success) {
        this.frequentProducts = response.data;
      }
    });

    this.webService.getUserRecentProducts().subscribe(response => {
      if (response.success) {
        this.recentProducts = response.data;
      }
    });

    
  }

}


