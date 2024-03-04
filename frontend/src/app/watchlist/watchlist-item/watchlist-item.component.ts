import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { UserProduct } from 'src/app/product/user-products/user-product.model';
import { WebService } from 'src/app/shared/web-request.service';

@Component({
  selector: 'app-watchlist-item',
  templateUrl: './watchlist-item.component.html',
  styleUrls: ['./watchlist-item.component.css']
})
export class WatchlistItemComponent implements OnInit {

  @Input() product: UserProduct;
  @Output() watchlistChanged = new EventEmitter<String>();

  public showDetail: boolean = false;
  
  constructor(private webService: WebService) { }

  ngOnInit(): void {
  }

  detailStatus() {
    this.showDetail = !this.showDetail;
  }

  remove() {
    this.webService.removeProductFromWatchList(this.product.product_id).subscribe({
      next: (response) => {
        alert("Deleted successfully!");
        this.watchlistChanged.emit("delete");
      },
      error: (error) => {
        alert("Fail");
      }
    });
  }
}
