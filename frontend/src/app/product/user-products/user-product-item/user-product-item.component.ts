import { Component, Input, OnInit } from '@angular/core';
import { UserProduct } from '../user-product.model';
import { WebService } from 'src/app/shared/web-request.service';
import { CartService } from 'src/app/cart/cart.service';

@Component({
  selector: 'app-user-product-item',
  templateUrl: './user-product-item.component.html',
  styleUrls: ['./user-product-item.component.css']
})
export class UserProductItemComponent implements OnInit {

  @Input() product: UserProduct;
  public showDetail: boolean = false;
  
  constructor(private webService: WebService, private cartService: CartService) { }

  ngOnInit(): void {
  }

  detailStatus() {
    this.showDetail = !this.showDetail;
  }

  addToCart() {
    this.cartService.addToCart(this.product);
  }

  addToWatchList() {
    this.webService.addProductToWatchList(this.product.product_id).subscribe({
      next: (response) => {
        alert("Added to watch list successfully");
      },
      error: (error) => {
        alert("Fail: Already in the watch list");
      }
    });
  }

}
