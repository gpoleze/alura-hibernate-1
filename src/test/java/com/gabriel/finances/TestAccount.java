package com.gabriel.finances;

import com.gabriel.finances.model.Account;
import com.gabriel.finances.util.JPAUtil;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TestAccount {

    @Test
    public void testAccountCreation() {

        Account account = new Account();
        account.setOwner("Leonardo");
        account.setBank("Citi");
        account.setBranchNumber("123");
        account.setNumber("456");

        EntityManager em = new JPAUtil().getEntityManager();

        em.getTransaction().begin();
        em.persist(account);
        em.getTransaction().commit();

        em.close();


    }
}
