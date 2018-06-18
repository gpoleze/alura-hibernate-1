package com.gabriel.finances.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("finances");

    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
}
