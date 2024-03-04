package com.bfs.hibernateprojectdemo.dto.question;

import java.util.List;

public class NewOrderRequest {
    private List<ProductForCreateOrder> order;

    public NewOrderRequest(List<ProductForCreateOrder> order) {
        this.order = order;
    }

    public NewOrderRequest() {
    }

    public List<ProductForCreateOrder> getOrder() {
        return order;
    }

    public void setOrder(List<ProductForCreateOrder> order) {
        this.order = order;
    }
}
