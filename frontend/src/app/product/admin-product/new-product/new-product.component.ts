import { Component, OnInit } from '@angular/core';
import { AdminProduct } from '../admin-product.model';
import { AdminProductToModify } from './new-product.model';
import { WebService } from 'src/app/shared/web-request.service';
import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-new-product',
  templateUrl: './new-product.component.html',
  styleUrls: ['./new-product.component.css']
})
export class NewProductComponent implements OnInit {

  model = new AdminProductToModify('', 0, '', 0, 0);

  constructor(private webService: WebService, private router: Router) { }

  ngOnInit(): void {
  }

  onSubmit() {
    this.webService.addNewProduct(this.model).subscribe({
      next: (response) => {
        alert("Added successfully!");
        this.router.navigate(['/products']);

      },
      error: (error) => {
        alert("Fail to add: Unknown Error");
      }
    });
  }

}
