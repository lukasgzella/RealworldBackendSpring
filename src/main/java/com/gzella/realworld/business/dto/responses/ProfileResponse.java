package com.gzella.realworld.business.dto.responses;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName("profile")
public class ProfileResponse {

    private String username;
    private String bio;
    private String image;
    private String following; //boolean

}