package com.gabriel.finances;

import com.gabriel.finances.model.Account;
import com.gabriel.finances.model.Transaction;
import com.gabriel.finances.model.TransactionType;
import com.gabriel.finances.util.JPAUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;

class TestJPARelation {

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
        account.setBank("Bradesco");
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
}
