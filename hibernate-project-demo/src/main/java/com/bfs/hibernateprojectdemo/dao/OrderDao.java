package com.bfs.hibernateprojectdemo.dao;

import com.bfs.hibernateprojectdemo.domain.Order;
import com.bfs.hibernateprojectdemo.domain.Product;
import com.bfs.hibernateprojectdemo.domain.User;
import com.bfs.hibernateprojectdemo.domain.Order_item;
import com.bfs.hibernateprojectdemo.dto.question.ProductForCreateOrder;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDao extends AbstractHibernateDao<Order> {
    public OrderDao() {
        setClazz(Order.class);
    }

    public Order getOrderById(Long id) {
        Order order = this.findById(id);
        Hibernate.initialize(order.getItems());
        return order;
    }

    public List<Order> getAllProducts() {
        return this.getAll();
    }

    public Order createNewOrder(Long userId, List<ProductForCreateOrder> products) {
        Session session = this.getCurrentSession();
        User user = session.get(User.class, userId);
        Order newOrder = new Order();
        newOrder.setUser(user);
        newOrder.setItems(new ArrayList<>());

        for (ProductForCreateOrder p : products) {
            Product product = session.get(Product.class, p.getProductId());
            if (product == null) {
                throw new RuntimeException("Illegal Product Id: " + p.getProductId());
            }

            if (product.getQuantity() < p.getQuantity()){
                throw new RuntimeException("Not Enough Product of " + product.getName());
            }
            //remove from the stock
            product.setQuantity(product.getQuantity() - p.getQuantity());

            Order_item orderItem = new Order_item();
            orderItem.setProduct(product);
            orderItem.setQuantity(p.getQuantity());
            orderItem.setOrder(newOrder);
            orderItem.setPurchased_price(product.getRetail_price());
            orderItem.setWholesale_price(product.getWholesale_price());

            newOrder.getItems().add(orderItem);
        }
        newOrder.setOrder_status(Order.OrderStatus.Processing);
        newOrder.setDate_placed(LocalDateTime.now());

        session.save(newOrder);
        return newOrder;
    }

    public List<Order> getAllOrdersByUserId(Long userId) {
        Session session = this.getCurrentSession();
        TypedQuery<Order> query = session.createQuery(
                "FROM Order o WHERE o.user.id = :userId", Order.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public Boolean cancelOrder(Long order_id){
        Session session = this.getCurrentSession();
        Order order = session.get(Order.class, order_id);
        if (order.getOrder_status() == Order.OrderStatus.Processing) {
            for (Order_item item : order.getItems()) {
                Product product = item.getProduct();
                session.save(product);
                product.setQuantity(product.getQuantity() + item.getQuantity());
            }
            order.setOrder_status(Order.OrderStatus.Canceled);
            return true;
        } else {
            return false;
        }
    }

    public Boolean completeAnOrder(Long order_id) {
        Session session = this.getCurrentSession();
        Order order = session.get(Order.class, order_id);
        if (order.getOrder_status() == Order.OrderStatus.Processing) {
            order.setOrder_status(Order.OrderStatus.Completed);
            return true;
        } else {
            return false;
        }
    }

}
