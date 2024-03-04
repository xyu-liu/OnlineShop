package com.bfs.shopauth.dao;


import com.bfs.shopauth.entity.User;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

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

    public User findByUsername(String username) {
        Session session = this.getCurrentSession();
        String hql = "FROM User WHERE username = :username";
        TypedQuery<User> query = session.createQuery(hql, User.class);
        query.setParameter("username", username);
        User user = query.getSingleResult();
        return user;
    }


}
