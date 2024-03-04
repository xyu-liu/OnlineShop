package com.bfs.hibernateprojectdemo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="user")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long user_id;

    @Column(nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    @NotBlank
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    @JsonIgnore
    private Integer role;

    @Column(nullable = false)
    @NotBlank
    private String username;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Order> orders;

    @ManyToMany
    @JoinTable(
            name = "watchlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @JsonIgnore
    private List<Product> watch_list;


}
