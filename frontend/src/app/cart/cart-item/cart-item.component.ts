import { Component, Input, OnInit } from '@angular/core';
import { CartService } from '../cart.service';
import { Product4Order } from '../product4Order.model';
import { Product4Cart } from '../product4Cart.model';

@Component({
  selector: 'app-cart-item',
  templateUrl: './cart-item.component.html',
  styleUrls: ['./cart-item.component.css']
})
export class CartItemComponent implements OnInit {

  @Input() cartItem: Product4Cart;

  constructor(private cartService: CartService) { }

  ngOnInit(): void {
  }
  remove() {
    this.cartService.removeFromCart(this.cartItem);
  }

}
