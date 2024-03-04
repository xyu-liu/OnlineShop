package com.bfs.hibernateprojectdemo.controller;

import com.bfs.hibernateprojectdemo.dao.UserDao;
import com.bfs.hibernateprojectdemo.domain.Product;
import com.bfs.hibernateprojectdemo.dto.common.DataResponse;
import com.bfs.hibernateprojectdemo.dto.question.Product4User;
import com.bfs.hibernateprojectdemo.dto.question.ProductAddOrUpdate;
import com.bfs.hibernateprojectdemo.service.ProductService;
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
import java.util.stream.Stream;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public DataResponse getAllProducts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isAdmin = authorities.stream()
                .anyMatch(authority -> "admin".equals(authority.getAuthority()));
        if (!isAdmin) {
            List<Product4User> products = this.productService.getProductsInStock().stream().map(x -> new Product4User(x)).collect(Collectors.toList());

            return DataResponse.builder()
                    .success(true)
                    .message("Success")
                    .data(products)
                    .build();
        } else {
            return DataResponse.builder()
                    .success(true)
                    .message("Success")
                    .data(this.productService.getAllProducts())
                    .build();
        }
    }

    @GetMapping("/{productId}")
    public DataResponse getProductById(@PathVariable Long productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isAdmin = authorities.stream()
                .anyMatch(authority -> "admin".equals(authority.getAuthority()));
        if (!isAdmin) {
            Product4User product4User = new Product4User(this.productService.getProductById(productId));
            return DataResponse.builder()
                    .success(true)
                    .message("Success")
                    .data(product4User)
                    .build();
        } else {
            return DataResponse.builder()
                    .success(true)
                    .message("Success")
                    .data(this.productService.getProductById(productId))
                    .build();
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin')")
    public DataResponse createProduct(@RequestBody ProductAddOrUpdate product) {
        this.productService.addProduct(product);
        return DataResponse.builder()
                .success(true)
                .message("Success")
                .build();
    }

    @PatchMapping("/{productId}")
    @PreAuthorize("hasAuthority('admin')")
    public DataResponse updateProduct(@PathVariable Long productId, @RequestBody ProductAddOrUpdate updatedProduct) {
        if (productId <= 0) {
            throw new NullPointerException();
        }
        this.productService.updateProduct(productId, updatedProduct);

        return DataResponse.builder()
                .success(true)
                .message("Success")
                .build();
    }

    @GetMapping("/recent/{limit}")
    @PreAuthorize("hasAuthority('customer')")
    public DataResponse getMostRecentProduct(@PathVariable int limit) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String)authentication.getPrincipal();
        Long user_id = this.userService.getIdByUserName(principal);
        List<Product4User> collect = this.userService.getTopRecentPurchases(limit, user_id).stream().map(x -> new Product4User(x)).collect(Collectors.toList());
        return DataResponse.builder()
                .success(true)
                .message("Success")
                .data(collect)
                .build();
    }

    @GetMapping("/frequent/{limit}")
    @PreAuthorize("hasAuthority('customer')")
    public DataResponse getMostFrequentProduct(@PathVariable int limit) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String)authentication.getPrincipal();
        Long user_id = this.userService.getIdByUserName(principal);
        List<Product4User> collect = this.userService.getTopFrequentPurchases(limit, user_id).stream().map(x -> new Product4User(x)).collect(Collectors.toList());
        return DataResponse.builder()
                .success(true)
                .message("Success")
                .data(collect)
                .build();
    }


    @GetMapping("/popular/{limit}")
    @PreAuthorize("hasAuthority('admin')")
    public DataResponse getMostPopularProduct(@PathVariable int limit) {
        return DataResponse.builder()
                .success(true)
                .message("Success")
                .data(this.productService.getMostSoldProducts(limit))
                .build();
    }
}
