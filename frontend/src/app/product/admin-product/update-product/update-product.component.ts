import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { WebService } from 'src/app/shared/web-request.service';
import { AdminProductToModify } from '../new-product/new-product.model';
import { UpdateService } from '../update.service';

@Component({
  selector: 'app-update-product',
  templateUrl: './update-product.component.html',
  styleUrls: ['./update-product.component.css']
})
export class UpdateProductComponent implements OnInit {

  model = new AdminProductToModify('', 0, '', 0, 0);
  public id: number = -1;

  constructor(private webService: WebService, private router: Router, private updateService: UpdateService) { }

  ngOnInit(): void {
    const product = this.updateService.product;
    this.id = product.product_id;
    this.model.description = product.description;
    this.model.name = product.name;
    this.model.quantity = product.quantity;
    this.model.retailPrice = product.retail_price;
    this.model.wholesalePrice = product.wholesale_price;
  }

  onSubmit() {
    this.webService.updateProduct(this.id, this.model).subscribe({
      next: (response) => {
        alert("Update successfully!");
        this.router.navigate(['/products']);

      },
      error: (error) => {
        alert("Fail to Update: Unknown Error");
      }
    });
  }
}
