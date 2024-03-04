import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthComponent } from './auth/auth.component';
import { AuthGuard } from './auth/auth.guard';
import { ProductComponent } from './product/product.component';
import { CartComponent } from './cart/cart.component';
import { WatchlistComponent } from './watchlist/watchlist.component';
import { OrderComponent } from './order/order.component';
import { UpdateProductComponent } from './product/admin-product/update-product/update-product.component';
import { NewProductComponent } from './product/admin-product/new-product/new-product.component';
import { UserStatComponent } from './user-stat/user-stat.component';

const appRoutes: Routes = [
  { path: '', redirectTo: '/products', pathMatch: 'full' },
  { path: 'auth', component: AuthComponent },
  { path: 'products', component: ProductComponent, canActivate: [AuthGuard]},
  { path: 'orders', component: OrderComponent, canActivate: [AuthGuard]},
  {
    path: 'cart', component: CartComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'user'
    }
  },
  {
    path: 'newProduct', component: NewProductComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'admin'
    }
  },
  {
    path: 'updateProduct', component: UpdateProductComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'admin'
    }
  },
  {
    path: 'userStat', component: UserStatComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'user'
    }
  },
  {
    path: 'watchlist', component: WatchlistComponent,
    canActivate: [AuthGuard],
    data: {
      role: 'user'
    }
  }
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
