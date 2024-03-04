export class Product4Order {
    public productId: number;
    public quantity: number;

    constructor(product_id: number, amount: number) {
        this.productId = product_id;
        this.quantity = amount;
    }
}