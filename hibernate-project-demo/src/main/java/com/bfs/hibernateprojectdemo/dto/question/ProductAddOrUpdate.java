package com.bfs.hibernateprojectdemo.dto.question;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class ProductAddOrUpdate {
    private String name;
    private String description;

    private Double wholesalePrice;

    private Double retailPrice;

    private Integer quantity;
}
