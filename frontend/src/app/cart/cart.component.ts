import { Component, OnInit } from '@angular/core';
import { CartService } from './cart.service';
import { Product4Order } from './product4Order.model';
import { Product4Cart } from './product4Cart.model';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})


export class CartComponent implements OnInit {

  products: Product4Cart[] = [];

  constructor(private cartService: CartService) {}


  ngOnInit(): void {
    this.products = this.cartService.products;
  }

  submitOrder(){
    this.cartService.submit().subscribe({
      next: (response) => {
        alert("Submit successfully!");
        this.cartService.clear();
        this.products = this.cartService.products;
      },
      error: (error) => {
        alert("Fail to submit: some products are out of stock!");
      }
    });
  }

}
