package com.github.alexkirnsu.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@NamedQueries({
        @NamedQuery(name = Comment.QUERY_FIND_BY_NOTE_ID,
                query = "from Comment where note_id = :" + Comment.PARAM_NOTE_ID),
})
@Table(name = "comments")
public class Comment {

    public static final String QUERY_FIND_BY_NOTE_ID = "findByNoteId";
    public static final String PARAM_NOTE_ID = "id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private String owner;

    @ManyToOne
    @JoinColumn(name = "note_id")
    private Note note;
}