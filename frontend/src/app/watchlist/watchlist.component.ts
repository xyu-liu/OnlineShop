import { Component, OnInit } from '@angular/core';
import { UserProduct } from '../product/user-products/user-product.model';
import { WebService } from '../shared/web-request.service';

@Component({
  selector: 'app-watchlist',
  templateUrl: './watchlist.component.html',
  styleUrls: ['./watchlist.component.css']
})
export class WatchlistComponent implements OnInit {

  products: UserProduct[] = [];
  
  constructor(private webService: WebService) { }

  ngOnInit(): void {
    this.webService.getWatchListProducts().subscribe(response => {
      if (response.success) {
        this.products = response.data;
      }
    });
  }

  reload() {
    this.webService.getWatchListProducts().subscribe(response => {
      if (response.success) {
        this.products = response.data;
      }
    });
  }

}
