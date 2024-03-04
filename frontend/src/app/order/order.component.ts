import { Component, OnInit } from '@angular/core';
import { OrderNoDetail } from './order.model';
import { WebService } from '../shared/web-request.service';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

  public orders: OrderNoDetail[] = []

  constructor(private webService: WebService) { }

  ngOnInit(): void {
    this.webService.getAllOrdersNoDetail().subscribe(response => {
      if (response.success) {
        this.orders = response.data;
      }
    });
  }

  reload(){
    this.webService.getAllOrdersNoDetail().subscribe(response => {
      if (response.success) {
        this.orders = response.data;
      }
    });
  }

}
