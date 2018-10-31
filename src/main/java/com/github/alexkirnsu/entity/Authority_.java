package com.github.alexkirnsu.entity;


import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Authority.class)
public class Authority_ {

    public static volatile SingularAttribute<Authority, Integer> id;
    public static volatile SingularAttribute<Authority, String> role;
    public static volatile SingularAttribute<Authority, User> user;
}
