package com.bfs.hibernateprojectdemo.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="product")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long product_id;

    @Column
    private String description;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double retail_price;

    @Column
    private Double wholesale_price;



}
