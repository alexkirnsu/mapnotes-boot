package com.github.alexkirnsu.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.sql.Timestamp;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public class User_ {

    public static volatile SingularAttribute<User, String> login;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, Profile> profile;
    public static volatile SingularAttribute<User, Timestamp> creationDate;
    public static volatile ListAttribute<User, Authority> authorities;
    public static volatile ListAttribute<User, Note> notes;
}
