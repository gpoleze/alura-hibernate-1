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
    void testTransactionWithCategory() {
        Category category1 = new Category("Trip");
        Category category2 = new Category("Business");

        Account account = new Account();
        account.setId(2);

        Transaction transaction1 = new Transaction();
        transaction1.setType(TransactionType.OUT);
        transaction1.setValue(new BigDecimal(400));
        transaction1.setDescription("Trip to Sao Paulo");
        transaction1.setDate(LocalDateTime.now());
        transaction1.setCategory(Arrays.asList(category1, category2));

        transaction1.setAccount(account);

        Transaction transaction2 = new Transaction();
        transaction2.setType(TransactionType.OUT);
        transaction2.setValue(new BigDecimal(600));
        transaction2.setDescription("Trip to RJ");
        transaction2.setDate(LocalDateTime.now().plusDays(7));
        transaction2.setCategory(Arrays.asList(category1, category2));

        transaction2.setAccount(account);

        em.getTransaction().begin();

        em.persist(category1);
        em.persist(category2);

        em.persist(transaction1);
        em.persist(transaction2);

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
