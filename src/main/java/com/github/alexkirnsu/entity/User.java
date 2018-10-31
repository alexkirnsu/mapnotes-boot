package com.github.alexkirnsu.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Data
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NaturalId(mutable = true)
    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @Embedded
    private Profile profile;

    @CreationTimestamp
    @Column(name="creation_date")
    private Timestamp creationDate;

    @OneToMany(cascade = ALL, mappedBy = "user")
    private List<Authority> authorities;

    @OneToMany(cascade = ALL, mappedBy = "user")
    private List<Note> notes;

}