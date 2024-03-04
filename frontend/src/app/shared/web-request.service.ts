import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, throwError } from "rxjs";
import { UserProduct } from "../product/user-products/user-product.model";
import { catchError } from "rxjs/operators";
import { Product4Order } from "../cart/product4Order.model";
import { Order, OrderNoDetail } from "../order/order.model";
import { AdminProduct } from "../product/admin-product/admin-product.model";
import { AdminProductToModify } from "../product/admin-product/new-product/new-product.model";

@Injectable({
    providedIn: 'root'
  })
export class WebService {

    constructor(private http: HttpClient) {}

    getUserProducts(): Observable<{success: boolean; message: string; data: UserProduct[]}> {
        return this.http.get<{ success: boolean; message: string; data: UserProduct[] }>(
            '/other-api/products/all'
        );
    }

    getUserFrequentProducts(): Observable<{success: boolean; message: string; data: UserProduct[]}> {
        return this.http.get<{ success: boolean; message: string; data: UserProduct[] }>(
            '/other-api/products/frequent/3'
        );
    }
    
    getUserRecentProducts(): Observable<{success: boolean; message: string; data: UserProduct[]}> {
        return this.http.get<{ success: boolean; message: string; data: UserProduct[] }>(
            '/other-api/products/frequent/3'
        );
    }

    getAdminProducts(): Observable<{success: boolean; message: string; data: AdminProduct[]}> {
        return this.http.get<{ success: boolean; message: string; data: AdminProduct[] }>(
            '/other-api/products/all'
        );
    }

    getWatchListProducts(): Observable<{success: boolean; message: string; data: UserProduct[]}> {
        return this.http.get<{ success: boolean; message: string; data: UserProduct[] }>(
            '/other-api/watchlist/products/all'
        );
    }

    addProductToWatchList(productId: number): Observable<{success: boolean; message: string; data: any}> {
        return this.http.post<{ success: boolean; message: string; data: UserProduct[] }>(
            '/other-api/watchlist/product/' + productId, {}
        ).pipe(
            catchError(this.handleError)
        );
    }

    removeProductFromWatchList(productId: number): Observable<{success: boolean; message: string; data: any}> {
        return this.http.delete<{ success: boolean; message: string; data: any }>(
            '/other-api/watchlist/product/' + productId, {}
        ).pipe(
            catchError(this.handleError)
        );
    }

    submitOrder(products: Product4Order[]) {
        return this.http.post<{ success: boolean; message: string; data: any }>(
            '/other-api/orders' , {order: products}
        ).pipe(
            catchError(this.handleError)
        );
    }

    getAllOrdersNoDetail(): Observable<{success: boolean; message: string; data: OrderNoDetail[]}> {
        return this.http.get<{ success: boolean; message: string; data: OrderNoDetail[] }>(
            '/other-api/orders/all'
        );
    }

    getOrderDetail(orderId: number): Observable<{success: boolean; message: string; data: Order}> {
        return this.http.get<{ success: boolean; message: string; data: Order }>(
            '/other-api/orders/' + orderId
        );
    }

    cancelOrder(orderId: number): Observable<{success: boolean; message: string; data: any}> {
        return this.http.patch<{ success: boolean; message: string; data: any }>(
            '/other-api/orders/' + orderId + '/cancel', {}
        );
    }

    completeOrder(orderId: number): Observable<{success: boolean; message: string; data: any}> {
        return this.http.patch<{ success: boolean; message: string; data: any }>(
            '/other-api/orders/' + orderId + '/complete' , {}
        );
    }

    addNewProduct(product: AdminProductToModify): Observable<{success: boolean; message: string; data: any}> {
        return this.http.post<{ success: boolean; message: string; data: UserProduct[] }>(
            '/other-api/products', product
        ).pipe(
            catchError(this.handleError)
        );
    }

    updateProduct(id: number, product: AdminProductToModify): Observable<{success: boolean; message: string; data: any}> {
        return this.http.patch<{ success: boolean; message: string; data: UserProduct[] }>(
            '/other-api/products/' + id, product
        ).pipe(
            catchError(this.handleError)
        );
    }



    private handleError(error: HttpErrorResponse) {
        if (error.status === 404) {
          console.error('Not Found', error.message);
          return throwError('Not Found. The requested resource does not exist.');
        } else {
          console.error('An error occurred:', error.message);
          return throwError('An unexpected error occurred.');
        }
    }


}