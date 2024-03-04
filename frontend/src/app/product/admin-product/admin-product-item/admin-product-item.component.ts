import { Component, Input, OnInit } from '@angular/core';
import { AdminProduct } from '../admin-product.model';
import { WebService } from 'src/app/shared/web-request.service';
import { Router } from '@angular/router';
import { UpdateService } from '../update.service';

@Component({
  selector: 'app-admin-product-item',
  templateUrl: './admin-product-item.component.html',
  styleUrls: ['./admin-product-item.component.css']
})
export class AdminProductItemComponent implements OnInit {

  @Input() product: AdminProduct;
  public showDetail: boolean = false;
  
  constructor(private webService: WebService, private router: Router, private updateService: UpdateService) { }

  ngOnInit(): void {
  }

  detailStatus() {
    this.showDetail = !this.showDetail;
  }

  updateProduct(){
    this.updateService.updateTheProduct(this.product);
    this.router.navigate(['/updateProduct']);
  }
  


}
