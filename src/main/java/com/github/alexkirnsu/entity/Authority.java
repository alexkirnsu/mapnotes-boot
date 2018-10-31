package com.github.alexkirnsu.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@NamedQueries({
        @NamedQuery(name = Authority.QUERY_FIND_AUTHORITY_BY_USER_LOGIN,
                query = "from Authority where user_login = :" + Authority.PARAM_USER_LOGIN)
})
@Table(name = "authorities")
public class Authority {

    public static final String QUERY_FIND_AUTHORITY_BY_USER_LOGIN = "findAuthorityByUserLogin";
    public static final String PARAM_USER_LOGIN = "login";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String role;

    @ManyToOne
    @JoinColumn(name = "user_login", referencedColumnName = "login")
    private User user;
}