package com.gzella.realworld.persistence.entity.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private Long id;
    private String email;
    private String password;
    private String username;
    private String bio = "";
    private String image;

}
