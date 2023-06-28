package com.gzella.realworld.persistence.entity;

import jakarta.persistence.*;

@Entity
public class Follower {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="from_user_fk")
    private User from;

    @ManyToOne
    @JoinColumn(name="to_user_fk")
    private User to;

    public Follower() {};

    public Follower(User from, User to) {
        this.from = from;
        this.to = to;
    }
}
