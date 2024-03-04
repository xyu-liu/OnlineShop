import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { DropdownDirective } from './shared/dropdown.directive';
import { AppRoutingModule } from './app-routing.module';
import { AuthComponent } from './auth/auth.component';
import { LoadingSpinnerComponent } from './shared/loading-spinner/loading-spinner.component';
import { AuthInterceptorService } from './auth/auth-interceptor.service';
import { ProductComponent } from './product/product.component';
import { WatchlistComponent } from './watchlist/watchlist.component';
import { OrderComponent } from './order/order.component';
import { CartComponent } from './cart/cart.component';
import { UserProductsComponent } from './product/user-products/user-products.component';
import { UserProductItemComponent } from './product/user-products/user-product-item/user-product-item.component';
import { WatchlistItemComponent } from './watchlist/watchlist-item/watchlist-item.component';
import { CartItemComponent } from './cart/cart-item/cart-item.component';
import { OrderItemComponent } from './order/order-item/order-item.component';
import { AdminProductComponent } from './product/admin-product/admin-product.component';
import { AdminProductItemComponent } from './product/admin-product/admin-product-item/admin-product-item.component';
import { NewProductComponent } from './product/admin-product/new-product/new-product.component';
import { UpdateProductComponent } from './product/admin-product/update-product/update-product.component';
import { UserStatComponent } from './user-stat/user-stat.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    DropdownDirective,
    AuthComponent,
    LoadingSpinnerComponent,
    ProductComponent,
    WatchlistComponent,
    OrderComponent,
    CartComponent,
    UserProductsComponent,
    UserProductItemComponent,
    WatchlistItemComponent,
    CartItemComponent,
    OrderItemComponent,
    AdminProductComponent,
    AdminProductItemComponent,
    NewProductComponent,
    UpdateProductComponent,
    UserStatComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
