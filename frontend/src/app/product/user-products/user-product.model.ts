export class UserProduct {
  public product_id: number;
  public name: string;
  public description: string;
  public price: number;

  constructor(product_id: number, name: string, desc: string, price: number) {
    this.name = name;
    this.description = desc;
    this.price = price;
    this.product_id = product_id;
  }
}
