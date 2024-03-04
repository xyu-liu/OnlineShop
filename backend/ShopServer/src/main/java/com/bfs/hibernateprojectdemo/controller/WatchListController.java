package com.bfs.hibernateprojectdemo.controller;

import com.bfs.hibernateprojectdemo.domain.Product;
import com.bfs.hibernateprojectdemo.dto.common.DataResponse;
import com.bfs.hibernateprojectdemo.dto.question.Product4User;
import com.bfs.hibernateprojectdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/watchlist")
public class WatchListController {

    @Autowired
    private UserService userService;

    @GetMapping("/products/all")
    @PreAuthorize("hasAuthority('customer')")
    public DataResponse getAllWatchlistItems() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String)authentication.getPrincipal();
        Long user_id = this.userService.getIdByUserName(principal);
        List<Product4User> allWatchList = this.userService.getAllWatchList(user_id).stream().map(x -> new Product4User(x)).collect(Collectors.toList());
        return DataResponse.builder()
                .success(true)
                .message("Success")
                .data(allWatchList)
                .build();
    }

    @PostMapping("/product/{productId}")
    @PreAuthorize("hasAuthority('customer')")
    public DataResponse addToWatchlist(@PathVariable Long productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String)authentication.getPrincipal();
        Long user_id = this.userService.getIdByUserName(principal);
        this.userService.addProductToWatchList(user_id,productId);
        return DataResponse.builder()
                .success(true)
                .message("Success")
                .build();
    }

    @DeleteMapping("/product/{productId}")
    @PreAuthorize("hasAuthority('customer')")
    public DataResponse removeFromWatchlist(@PathVariable Long productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principal = (String)authentication.getPrincipal();
        Long user_id = this.userService.getIdByUserName(principal);
        this.userService.removeProductFromWatchList(user_id,productId);
        return DataResponse.builder()
                .success(true)
                .message("Success")
                .build();
    }


}
