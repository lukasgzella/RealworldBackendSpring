package com.gzella.realworld.business.dto.requests;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName("user")
public class UpdateRequest {

    private String email;
    private String username;
    private String password;
    private String image;
    private String bio;

}