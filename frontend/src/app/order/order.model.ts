export interface User {
    user_id: number;
    email: string;
    username: string;
}

export interface Product {
    product_id: number;
    description: string;
    name: string;
    quantity: number;
    retail_price: number;
    wholesale_price: number;
}

export interface OrderItem {
    item_id: number;
    purchased_price: number;
    quantity: number;
    wholesale_price: number;
    product: Product;
}

export interface Order {
    order_id: number;
    date_placed: string;
    order_status: string;
    user: User;
    items: OrderItem[];
}

export interface ApiResponse {
    data: Order;
}

export interface OrderNoDetail {
    order_id: number;
    date_placed: string;
    order_status: string;
}