package com.gabriel.finances;

import com.gabriel.finances.model.*;
import com.gabriel.finances.util.JPAUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TestJPARelationship {

    private EntityManager em;

    @BeforeEach
    void setUp() {
        em = new JPAUtil().getEntityManager();
    }

    @AfterEach
    void tearDown() {
        em.close();
    }

    @Test
    void showRelationBetweenTwoEntities() {
        Account account = new Account();
        account.setOwner("Juliano");
        account.setBank("237 - Bradesco");
        account.setBranchNumber("0803");
        account.setNumber("123-4");

        Transaction transaction = new Transaction();
        transaction.setDate(LocalDateTime.now());
        transaction.setDescription("Churrascaria");
        transaction.setType(TransactionType.OUT);
        transaction.setValue(new BigDecimal("200"));

        transaction.setAccount(account);

        em.getTransaction().begin();

        em.persist(account);
        em.persist(transaction);

        em.getTransaction().commit();

    }

    @Test
    void testOneAccountCannotHaveTwoOnwers() {
        Owner owner = new Owner();
        owner.setFirstName("Maria");
        owner.setLastName("dos Santos");
        owner.setAddress("123, First St");

        Owner owner2 = new Owner();
        owner2.setFirstName("Joao");
        owner2.setLastName("dos Santos");
        owner2.setAddress("321, First St");

        Account account = new Account();
        account.setId(2);

        owner.setAccount(account);
        owner2.setAccount(account);

        em.getTransaction().begin();
        em.persist(owner);
        em.getTransaction().commit();


//        em.getTransaction().begin();
//        em.persist(owner2);
//        em.getTransaction().commit();

        assertThrows(javax.persistence.PersistenceException.class, () -> {
            em.getTransaction().begin();
            em.persist(owner2);
            em.getTransaction().commit();
        });
    }
}
