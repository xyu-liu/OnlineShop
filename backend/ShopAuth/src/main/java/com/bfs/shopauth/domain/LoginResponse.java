package com.bfs.shopauth.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private Boolean success;
    private String message;
    private String token;
    private String permission;

}
