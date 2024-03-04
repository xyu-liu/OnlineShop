export class Product4Cart {
    public name: string;
    public product_id: number;
    public amount: number;

    constructor(name: string, product_id: number, amount: number) {
        this.name = name;
        this.product_id = product_id;
        this.amount = amount;
    }
}