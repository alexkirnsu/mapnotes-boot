package com.github.alexkirnsu.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Comment.class)
public class Comment_ {

    public static volatile SingularAttribute<Comment, Integer> id;
    public static volatile SingularAttribute<Comment, String> text;
    public static volatile SingularAttribute<Comment, String> owner;
    public static volatile SingularAttribute<Comment, Note> note;
}
