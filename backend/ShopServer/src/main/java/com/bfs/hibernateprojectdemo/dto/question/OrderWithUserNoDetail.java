package com.bfs.hibernateprojectdemo.dto.question;


import com.bfs.hibernateprojectdemo.domain.Order;
import com.bfs.hibernateprojectdemo.domain.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@ToString
public class OrderWithUserNoDetail {

    private Long order_id;

    private LocalDateTime date_placed;

    private Order.OrderStatus order_status;

    private User user;


    public OrderWithUserNoDetail(Order order){
        this.order_status = order.getOrder_status();
        this.order_id = order.getOrder_id();
        this.date_placed = order.getDate_placed();
        this.user = order.getUser();
    }
}
