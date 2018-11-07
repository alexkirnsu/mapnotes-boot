package com.github.alexkirnsu.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.*;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Note.class)
public class Note_ {

    public static volatile SingularAttribute<Note, Integer> id;
    public static volatile SingularAttribute<Note, String> place;
    public static volatile SingularAttribute<Note, Double> lat;
    public static volatile SingularAttribute<Note, Double> lng;
    public static volatile SingularAttribute<Note, String> privacy;
    public static volatile ListAttribute<Note, Comment> comments;
    public static volatile SingularAttribute<Note, User> user;
}
