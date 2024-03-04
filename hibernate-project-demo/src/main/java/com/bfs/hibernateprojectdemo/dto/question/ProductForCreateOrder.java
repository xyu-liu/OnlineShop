package com.bfs.hibernateprojectdemo.dto.question;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class ProductForCreateOrder {
    private Long productId;
    private Integer quantity;

    public ProductForCreateOrder(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public ProductForCreateOrder() {
    }



}
