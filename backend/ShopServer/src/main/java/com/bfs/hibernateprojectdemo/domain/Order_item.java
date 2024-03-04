package com.bfs.hibernateprojectdemo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.aspectj.apache.bcel.generic.LineNumberGen;

import javax.persistence.*;

@Entity
@Table(name="order_item")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order_item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long item_id;

    @Column(nullable = false)
    private Double purchased_price;

    @Column(nullable = false)
    private Integer quantity;

    @Column
    private Double wholesale_price;


    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    //@JsonIgnore
    private Product product;


}
