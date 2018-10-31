package com.github.alexkirnsu.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NamedQueries({
        @NamedQuery(name = Note.QUERY_FIND_NOTE_BY_USER_LOGIN,
                query = "from Note where user_login = :" + Note.PARAM_USER_LOGIN),
        @NamedQuery(name = Note.QUERY_FIND_ALIEN_NOTE_BY_USER_LOGIN,
                query = "from Note where privacy is null and user_login <> :" + Note.PARAM_USER_LOGIN)
})
@Table(name = "notes")
public class Note {

    public static final String QUERY_FIND_NOTE_BY_USER_LOGIN = "findNoteByUserLogin";
    public static final String QUERY_FIND_ALIEN_NOTE_BY_USER_LOGIN = "findAlienNoteByUserLogin";
    public static final String PARAM_USER_LOGIN = "login";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String place;

    @Column
    private Double lat;

    @Column
    private Double lng;

    @Column
    private String privacy;

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "user_login", referencedColumnName = "login")
    private User user;
}