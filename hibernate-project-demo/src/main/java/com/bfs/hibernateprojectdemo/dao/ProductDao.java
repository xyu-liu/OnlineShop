package com.bfs.hibernateprojectdemo.dao;

import com.bfs.hibernateprojectdemo.domain.Order;
import com.bfs.hibernateprojectdemo.domain.Product;
import com.bfs.hibernateprojectdemo.domain.User;
import com.bfs.hibernateprojectdemo.dto.question.ProductAddOrUpdate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDao extends AbstractHibernateDao<Product>{

    public ProductDao() {
        setClazz(Product.class);
    }

    public Product getProductById(Long id) {
        return this.findById(id);
    }

    public List<Product> getAllProducts() {
        return this.getAll();
    }

    public void addProduct(Product product) {
        this.add(product);
    }

    public List<Product> getAllProductsNotOutOfStock() {
        Session session = this.getCurrentSession();
        Criteria criteria = session.createCriteria(this.clazz);
        criteria.add(Restrictions.gt("quantity", 0));
        return criteria.list();
    }


    public void updateProduct(Long product_id, ProductAddOrUpdate product) {
        Session session = this.getCurrentSession();
        Product old_product = session.get(Product.class, product_id);
        if (old_product == null) {
            throw new NullPointerException("No such product");
        }
        old_product.setName(product.getName());
        old_product.setDescription(product.getDescription());
        old_product.setWholesale_price(product.getWholesalePrice());
        old_product.setRetail_price(product.getRetailPrice());
        old_product.setQuantity(product.getQuantity());
    }

    public List<Product> getMostSoldProducts(int limit) {
        Session session = this.getCurrentSession();
        String queryString = "SELECT p FROM Order_item oi " +
                "JOIN oi.product p " +
                "JOIN oi.order o " +
                "WHERE o.order_status = :completedStatus " +
                "GROUP BY p.id " +
                "ORDER BY SUM(oi.quantity) DESC, p.id ASC";
        TypedQuery<Product> query = session.createQuery(queryString, Product.class);
        query.setParameter("completedStatus", Order.OrderStatus.Completed);
        query.setMaxResults(limit);
        return query.getResultList();

    }
}
