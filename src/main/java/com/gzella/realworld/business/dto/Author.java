package com.gzella.realworld.business.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Author {

    private String username;
    private String bio;
    private String image;
    private boolean following;

    public Author(String username, String bio, String image, boolean following) {
        this.username = username;
        this.bio = bio;
        this.image = image;
        this.following = following;
    }

    @Override
    public String toString() {
        return "Author{" +
                "username='" + username + '\'' +
                ", bio='" + bio + '\'' +
                ", image='" + image + '\'' +
                ", following=" + following +
                '}';
    }
}