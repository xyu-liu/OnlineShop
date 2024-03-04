import { Injectable } from "@angular/core";
import { AdminProduct } from "./admin-product.model";

@Injectable({ providedIn: 'root' })
export class UpdateService {

    public product: AdminProduct;

    constructor(){
    }

    updateTheProduct(pro: AdminProduct) {
        this.product = pro;
    }

}