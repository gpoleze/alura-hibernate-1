package com.gabriel.finances;

import com.gabriel.finances.model.Account;
import com.gabriel.finances.util.JPAUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;

class TestAccount {

    private EntityManager em;

    @BeforeEach
    void setUp() {
        em = new JPAUtil().getEntityManager();
    }

    @AfterEach
    void tearDown() {
        try {
            em.close();
        } catch (IllegalStateException ignored) {
        }
    }

    @Test
    void testAccountCreation() {

        Account account = new Account();
        account.setOwner("Leonardo");
        account.setBank("Citi");
        account.setBranchNumber("123");
        account.setNumber("456");

        em.getTransaction().begin();
        em.persist(account);
        em.getTransaction().commit();
    }

    @Test
    void searchAccountOnDatabase() {
        em.getTransaction().begin();
        Account account = em.find(Account.class, 1);

        System.out.println(account.getOwner());

        assertEquals("Leonardo", account.getOwner());

        em.getTransaction().commit();

    }

    @Test
    void transformingADeatachedEntityBackIntoManaged() {
        String name1 = "Leonardo";
        String name2 = "Joao";

        em.getTransaction().begin();
        Account account = em.find(Account.class, 1);
        // From here on the entity is on Managed state

        if (!account.getOwner().equals(name1))
            account.setOwner(name1);

        em.getTransaction().commit();
        em.close(); // Since we close the Entity Manager, now its state is Detached

        EntityManager em2 = new JPAUtil().getEntityManager();

        em2.getTransaction().begin();

        account.setOwner(name2);
        // em2.persist(account);
        // To transform the state from Detached back to managed, instead the method persist we should use merge
        em2.merge(account);

        em2.getTransaction().commit();
        em2.close();

        account.setOwner(name2);


    }
}

