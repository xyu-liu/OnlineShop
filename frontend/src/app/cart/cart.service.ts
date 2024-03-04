import { Injectable } from "@angular/core";
import { UserProduct } from "../product/user-products/user-product.model";
import { WebService } from "../shared/web-request.service";
import { Product4Order } from "./product4Order.model";
import { Product4Cart } from "./product4Cart.model";

@Injectable({ providedIn: 'root' })
export class CartService {
    public products: Product4Cart[] = [];

    constructor(private webService: WebService){}

    addToCart(product: UserProduct) {
        let index = this.products.findIndex(p =>  p.product_id === product.product_id);
        if (index !== -1) {
            this.products[index].amount +=1;
        } else {
            this.products.push(new Product4Cart(product.name, product.product_id, 1));
        }
    }

    removeFromCart(product: Product4Cart) {
        let index = this.products.findIndex(p =>  p.product_id === product.product_id);
        if (index !== -1) {
            this.products.splice(index, 1);
        }
    }

    submit() {
        let orderItems: Product4Order[] = this.products.map(up => new Product4Order(up.product_id, up.amount));
        console.log(orderItems);
        return this.webService.submitOrder(orderItems);

    }

    clear() {
        this.products = [];
    }


}