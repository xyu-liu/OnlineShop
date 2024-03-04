export class AdminProductToModify {
    public name: string;
    public quantity: number;
    public description: string;
    public retailPrice: number;
    public wholesalePrice: number;
  
    constructor(name: string, quantity: number, description: string, retail_price: number, wholesale_price: number) {
        this.name = name;
        this.quantity = quantity;
        this.description = description;
        this.retailPrice = retail_price;
        this.wholesalePrice = wholesale_price;
    }
}
  