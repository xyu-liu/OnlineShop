package com.bfs.hibernateprojectdemo.service;

import com.bfs.hibernateprojectdemo.dao.ProductDao;
import com.bfs.hibernateprojectdemo.domain.Product;
import com.bfs.hibernateprojectdemo.dto.question.ProductAddOrUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;


    @Transactional
    public List<Product> getProductsInStock(){
        return this.productDao.getAllProductsNotOutOfStock();
    }

    @Transactional
    public Product getProductById(Long product_id){
        return this.productDao.getProductById(product_id);
    }

    @Transactional
    public List<Product> getAllProducts(){
        return this.productDao.getAllProducts();
    }

    @Transactional
    public void addProduct(ProductAddOrUpdate product){
        Product old_product = new Product();
        old_product.setName(product.getName());
        old_product.setDescription(product.getDescription());
        old_product.setWholesale_price(product.getWholesalePrice());
        old_product.setRetail_price(product.getRetailPrice());
        old_product.setQuantity(product.getQuantity());
        this.productDao.addProduct(old_product);
    }

    @Transactional
    public void updateProduct(Long product_id, ProductAddOrUpdate product){
        this.productDao.updateProduct(product_id,product);
    }

    @Transactional
    public List<Product> getMostSoldProducts(int limit) {
        return this.productDao.getMostSoldProducts(limit);
    }

}
