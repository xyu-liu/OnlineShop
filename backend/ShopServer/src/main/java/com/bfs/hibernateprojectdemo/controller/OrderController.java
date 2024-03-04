package com.bfs.hibernateprojectdemo.controller;

import com.bfs.hibernateprojectdemo.domain.Order;
import com.bfs.hibernateprojectdemo.dto.common.DataResponse;
import com.bfs.hibernateprojectdemo.dto.question.NewOrderRequest;
import com.bfs.hibernateprojectdemo.dto.question.OrderNoDetail;
import com.bfs.hibernateprojectdemo.dto.question.OrderWithUserNoDetail;
import com.bfs.hibernateprojectdemo.dto.question.ProductForCreateOrder;
import com.bfs.hibernateprojectdemo.service.OrderService;
import com.bfs.hibernateprojectdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("hasAuthority('customer')")
    public DataResponse placeNewOrder(@RequestBody NewOrderRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String)authentication.getPrincipal();
        Long user_id = this.userService.getIdByUserName(principal);
        Order newOrder = this.orderService.createNewOrder(user_id, request.getOrder());
        return DataResponse.builder()
                .success(true)
                .message("Success")
                .build();
    }

    @GetMapping("/all")
    public DataResponse getAllOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isAdmin = authorities.stream()
                .anyMatch(authority -> "admin".equals(authority.getAuthority()));
        if (!isAdmin) {
            String principal = (String)authentication.getPrincipal();
            Long user_id = this.userService.getIdByUserName(principal);
            List<OrderNoDetail> collect = this.orderService.getUserOrders(user_id).stream().map(x -> new OrderNoDetail(x)).collect(Collectors.toList());
            return DataResponse.builder()
                    .success(true)
                    .message("Success")
                    .data(collect)
                    .build();
        } else {
            List<OrderNoDetail> collect = this.orderService.getAllOrders().stream().map(x -> new OrderNoDetail(x)).collect(Collectors.toList());
            return DataResponse.builder()
                    .success(true)
                    .message("Success")
                    .data(collect)
                    .build();
        }


    }

    @GetMapping("/{orderId}")
    public DataResponse getOrderDetail(@PathVariable Long orderId) {
        boolean isAdmin = false;
        if (!isAdmin) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String principal = (String)authentication.getPrincipal();
            Long user_id = this.userService.getIdByUserName(principal);
            Order orderById = this.orderService.getOrderById(orderId);
            return DataResponse.builder()
                    .success(true)
                    .message("Success")
                    .data(orderById)
                    .build();
        } else {
            Order orderById = this.orderService.getOrderById(orderId);
            return DataResponse.builder()
                    .success(true)
                    .message("Success")
                    .data(orderById)
                    .build();
        }
    }

    @PatchMapping("/{orderId}/cancel")
    public DataResponse cancelOrder(@PathVariable Long orderId) {
        Boolean b = this.orderService.cancelOrder(orderId);
        return DataResponse.builder()
                .success(b)
                .message(b?"Success":"Cannot cancel completed/canceled order")
                .build();
    }

    @PatchMapping("/{orderId}/complete")
    @PreAuthorize("hasAuthority('admin')")
    public DataResponse completeOrder(@PathVariable Long orderId) {
        Boolean b = this.orderService.completeAnOrder(orderId);
        return DataResponse.builder()
                .success(b)
                .message(b?"Success":"Cannot complete completed/canceled order")
                .build();
    }
}
