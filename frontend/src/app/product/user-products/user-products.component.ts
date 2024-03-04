import { Component, OnInit } from '@angular/core';
import { UserProduct } from './user-product.model';
import { WebService } from 'src/app/shared/web-request.service';

@Component({
  selector: 'app-user-products',
  templateUrl: './user-products.component.html',
  styleUrls: ['./user-products.component.css']
})
export class UserProductsComponent implements OnInit {

  products: UserProduct[] = [];

  constructor(private webService: WebService) { }

  ngOnInit(): void {
    this.webService.getUserProducts().subscribe(response => {
      if (response.success) {
        this.products = response.data;
      }
    });
    
  }

}
