package com.gabriel.finances;

import com.gabriel.finances.model.Account;
import com.gabriel.finances.model.Category;
import com.gabriel.finances.model.Transaction;
import com.gabriel.finances.model.TransactionType;
import com.gabriel.finances.util.JPAUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

class PopulateAccount {


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
    void populateDatabaseWithAccounts() {

        em.getTransaction().begin();

        Account account1 = new Account();
        Account account2 = new Account();
        Account account3 = new Account();
        Account account4 = new Account();
        Account account5 = new Account();

        account1.setBank("001 - BANCO DO BRASIL");
        account1.setNumber("16987-8");
        account1.setBranchNumber("6543");
        account1.setOwner("Maria dos Santos");

        account2.setBank("237 - BANCO BRADESCO");
        account2.setNumber("86759-1");
        account2.setBranchNumber("1745");
        account2.setOwner("Paulo Roberto Souza");

        account3.setBank("341 - BANCO ITAU UNIBANCO");
        account3.setNumber("46346-3");
        account3.setBranchNumber("4606");
        account3.setOwner("Antonio Duraes");

        account4.setBank("033 - BANCO SANTANDER");
        account4.setNumber("12345-6");
        account4.setBranchNumber("9876");
        account4.setOwner("Leandra Marques");

        account5.setBank("104 - CAIXA ECONOMICA FEDERAL");
        account5.setNumber("98654-3");
        account5.setBranchNumber("1234");
        account5.setOwner("Alexandre Duarte");

        // persisting as accounts
        em.persist(account1);
        em.persist(account2);
        em.persist(account3);
        em.persist(account4);
        em.persist(account5);

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
}