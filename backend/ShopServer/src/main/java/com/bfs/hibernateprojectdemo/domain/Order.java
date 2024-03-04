package com.bfs.hibernateprojectdemo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="`order`")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long order_id;

    @Column(nullable = false)
    private LocalDateTime date_placed;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus order_status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Order_item> items;







    public enum OrderStatus {
        Processing,
        Completed,
        Canceled
    }



}



