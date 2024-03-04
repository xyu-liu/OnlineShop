package com.bfs.shopauth.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    private Boolean success;
    private String message;
}
