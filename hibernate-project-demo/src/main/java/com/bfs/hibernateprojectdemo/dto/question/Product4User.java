package com.bfs.hibernateprojectdemo.dto.question;

import com.bfs.hibernateprojectdemo.domain.Product;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Getter
@Setter
@ToString
public class Product4User {

    private Long product_id;

    private String description;

    private String name;

    private Double price;

    public Product4User(Product product) {
        this.product_id = product.getProduct_id();
        this.description = product.getDescription();
        this.name = product.getName();
        this.price = product.getRetail_price();
    }

}
