export class AdminProduct {
    public product_id: number;
    public name: string;
    public quantity: number;
    public description: string;
    public retail_price: number;
    public wholesale_price: number;
  
    constructor(product_id: number, name: string, quantity: number, description: string, retail_price: number, wholesale_price: number) {
        this.product_id = product_id;
        this.name = name;
        this.quantity = quantity;
        this.description = description;
        this.retail_price = retail_price;
        this.wholesale_price = wholesale_price;
    }
}
  