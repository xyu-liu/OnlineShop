package com.bfs.hibernateprojectdemo.service;

import com.bfs.hibernateprojectdemo.dao.OrderDao;
import com.bfs.hibernateprojectdemo.domain.Order;
import com.bfs.hibernateprojectdemo.dto.question.ProductForCreateOrder;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Transactional
    public Order createNewOrder(Long userId, List<ProductForCreateOrder> products){
        return this.orderDao.createNewOrder(userId, products);
    }
    @Transactional
    public List<Order> getUserOrders(Long userid){
        return this.orderDao.getAllOrdersByUserId(userid);
    }
    @Transactional
    public Order getOrderById(Long order_id) {
        return this.orderDao.getOrderById(order_id);
    }

    @Transactional
    public Boolean cancelOrder(Long order_id){
        return this.orderDao.cancelOrder(order_id);
    }

    @Transactional
    public List<Order> getAllOrders() {
        return this.orderDao.getAll();
    }

    @Transactional
    public Boolean completeAnOrder(Long order_id){
        return this.orderDao.completeAnOrder(order_id);
    }
}
