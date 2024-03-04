import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Order, OrderNoDetail } from '../order.model';
import { WebService } from 'src/app/shared/web-request.service';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-order-item',
  templateUrl: './order-item.component.html',
  styleUrls: ['./order-item.component.css']
})
export class OrderItemComponent implements OnInit {

  @Input() order: OrderNoDetail;
  public isAdmin: boolean;
  public isDetailed: boolean = false;
  public detailOrder: Order;
  public DetailLoaded: boolean = false;
  public detail: string;

  @Output() ordersChange = new EventEmitter<String>();


  constructor(private webService: WebService, private authService: AuthService) { }

  ngOnInit(): void {
    this.isAdmin = this.authService.user.permission === 'admin';
  }

  showDetail(){
    this.isDetailed = !this.isDetailed;
    if(this.isDetailed && !this.DetailLoaded) {
      this.webService.getOrderDetail(this.order.order_id).subscribe(response => {
        if (response.success) {
          this.detailOrder = response.data;
          this.detail = JSON.stringify(this.detailOrder, null, "\t");
          this.DetailLoaded = true;
        }
      });
    }
  }

  cancelOrder(){
    this.webService.cancelOrder(this.order.order_id).subscribe(response => {
      if (response.success) {
        alert('Canceled Successfully!');
        this.ordersChange.emit("Changed");
      }
    });

  }

  completeOrder(){
    this.webService.completeOrder(this.order.order_id).subscribe(response => {
      if (response.success) {
        alert('Complete Successfully!');
        this.ordersChange.emit("Changed");
      }
    });
  }

}
