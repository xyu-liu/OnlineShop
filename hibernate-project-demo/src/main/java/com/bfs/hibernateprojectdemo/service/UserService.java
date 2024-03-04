package com.bfs.hibernateprojectdemo.service;

import com.bfs.hibernateprojectdemo.aop.RepeatedOperationException;
import com.bfs.hibernateprojectdemo.dao.UserDao;
import com.bfs.hibernateprojectdemo.domain.Product;
import com.bfs.hibernateprojectdemo.domain.User;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Transactional
    public boolean addNewUser(String username, String email, String password){
        if (this.userDao.isUserNameTaken(username) || this.userDao.isEmailTaken(email)) {
            return false;
        } else {
            User user = new User();
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);
            this.userDao.addUser(user);
            return true;
        }
    }

    @Transactional
    public List<Product> getAllWatchList(Long user_id) {
        User userById = this.userDao.getUserById(user_id);
        //Hibernate.initialize(userById.getWatch_list());
        ArrayList<Product> products = new ArrayList<>(userById.getWatch_list());
        //List<Product> watchList = userById.getWatch_list();
        return products;
    }

    @Transactional
    public boolean isProductInWatchList(Long user_id, Long product_id) {
        return this.userDao.isProductInWatchList(user_id, product_id);
    }

    @Transactional
    public void addProductToWatchList(Long userId, Long productId) {
        if (!this.userDao.isProductInWatchList(userId, productId)) {
            this.userDao.addProductToWatchList(userId, productId);
        } else {
            throw new RepeatedOperationException("Product already in the list");
        }
    }

    @Transactional
    public void removeProductFromWatchList(Long userId, Long productId) {
        if (this.userDao.isProductInWatchList(userId, productId)) {
            this.userDao.removeProductFromWatchList(userId, productId);
        } else {
            throw new RepeatedOperationException("Product already in the list");
        }
    }

    @Transactional
    public List<Product> getTopRecentPurchases(int limit, Long userId) {
        return this.userDao.getTopRecentPurchases(limit, userId);
    }

    @Transactional
    public List<Product> getTopFrequentPurchases(int limit, Long userId) {
        return this.userDao.getTopFrequentPurchases(limit, userId);
    }

    @Transactional
    public Long getIdByUserName(String username){
        return this.userDao.getUserByUserName(username).getUser_id();
    }



}
