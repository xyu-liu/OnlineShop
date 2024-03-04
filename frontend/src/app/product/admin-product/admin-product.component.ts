import { Component, OnInit } from '@angular/core';
import { AdminProduct } from './admin-product.model';
import { WebService } from 'src/app/shared/web-request.service';

@Component({
  selector: 'app-admin-product',
  templateUrl: './admin-product.component.html',
  styleUrls: ['./admin-product.component.css']
})
export class AdminProductComponent implements OnInit {

  products: AdminProduct[] = [];

  constructor(private webService: WebService) {
    console.log("In Admin Prodcut");
   }

  ngOnInit(): void {
    this.webService.getAdminProducts().subscribe(response => {
      if (response.success) {
        this.products = response.data;
      }
    });
  }

}