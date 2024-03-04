package com.bfs.shopauth.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="permission")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long permission_id;

    @Column(nullable = false)
    private String value;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
