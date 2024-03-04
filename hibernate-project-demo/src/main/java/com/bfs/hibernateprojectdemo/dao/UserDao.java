package com.bfs.hibernateprojectdemo.dao;

import com.bfs.hibernateprojectdemo.domain.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class UserDao extends AbstractHibernateDao<User>{
    public UserDao() {
        setClazz(User.class);
    }

    public User getUserById(Long id) {
        return this.findById(id);
    }

    public List<User> getAllUsers() {
        return this.getAll();
    }

    public void addUser(User user) {
        this.add(user);
    }

    public User getUserByUserName(String username) {
        Session session = this.getCurrentSession();
        String hql = "FROM User WHERE username = :username";
        TypedQuery<User> query = session.createQuery(hql, User.class);
        query.setParameter("username", username);
        User user = query.getSingleResult();
        return user;
    }

    public boolean isUserNameTaken(String username){
        Long count = (Long) getCurrentSession().createQuery("select count(u) from User u where u.username = :username")
                .setParameter("username", username)
                .getSingleResult();
        return count != null && count > 0;
    }

    public boolean isEmailTaken(String email){
        Long count = (Long) getCurrentSession().createQuery("select count(u) from User u where u.email = :email")
                .setParameter("email", email)
                .getSingleResult();
        return count != null && count > 0;
    }

    public void addProductToWatchList(Long userId, Long productId) {
        Session session = this.getCurrentSession();
        User user = session.get(User.class, userId);
        Product product = session.get(Product.class, productId);
        //Hibernate.initialize(user.getWatch_list());
        if (!user.getWatch_list().contains(product)) {
            user.getWatch_list().add(product);
        }
    }

    public void removeProductFromWatchList(Long userId, Long productId) {
        Session session = this.getCurrentSession();
        User user = session.get(User.class, userId);
        Product product = session.get(Product.class, productId);
        if (user.getWatch_list().contains(product)) {
            user.getWatch_list().remove(product);
        }
    }

    public boolean isProductInWatchList(Long userId, Long productId) {
        Session session = this.getCurrentSession();
        User user = session.get(User.class, userId);
        Product product = session.get(Product.class, productId);
        return user.getWatch_list().contains(product);
    }



    public List<Product> getTopRecentPurchases(int limit, Long userId) {
        Session session = this.getCurrentSession();
        String queryString = "SELECT DISTINCT p, o.date_placed FROM Order_item oi " +
                "JOIN oi.order o " +
                "JOIN oi.product p " +
                "WHERE o.user.id = :userId " +
                "AND o.order_status <> :canceledStatus " +
                "ORDER BY o.date_placed DESC, p.product_id ASC";

        TypedQuery<Object[]> query = session.createQuery(queryString, Object[].class);
        query.setParameter("userId", userId);
        query.setParameter("canceledStatus", Order.OrderStatus.Canceled);
        List<Object[]> results = query.getResultList();
        List<Product> orderItems = new ArrayList<>();
        for (Object[] result : results) {
            Product product = (Product) result[0];
            orderItems.add(product);
        }


        List<Product> distinctProducts = orderItems.stream()
                .collect(Collectors.toMap(
                        Product::getProduct_id,
                        Function.identity(),
                        (existing, replacement) -> existing,
                        LinkedHashMap::new))
                .values()
                .stream()
                .collect(Collectors.toList());

        return distinctProducts.subList(0, Math.min(distinctProducts.size(),limit));
    }

    public List<Product> getTopFrequentPurchases(int limit, Long userId) {
        Session session = this.getCurrentSession();

        String queryString = "SELECT p FROM Order_item oi " +
                "JOIN oi.product p " +
                "JOIN oi.order o " +
                "WHERE o.user.id = :userId " +
                "AND o.order_status <> :canceledStatus " +
                "GROUP BY p.id " +
                "ORDER BY SUM(oi.quantity) DESC, p.id ASC";

        TypedQuery<Product> query = session.createQuery(queryString, Product.class);
        query.setParameter("userId", userId);
        query.setParameter("canceledStatus", Order.OrderStatus.Canceled); // Replace with your actual canceled status
        query.setMaxResults(limit);
        return query.getResultList();
    }

}
