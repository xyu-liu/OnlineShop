package com.bfs.hibernateprojectdemo.dto.question;

import com.bfs.hibernateprojectdemo.domain.Order;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@ToString
public class OrderNoDetail {

    private Long order_id;

    private LocalDateTime date_placed;

    private Order.OrderStatus order_status;


    public OrderNoDetail(Order order){
        this.order_status = order.getOrder_status();
        this.order_id = order.getOrder_id();
        this.date_placed = order.getDate_placed();
    }


}
