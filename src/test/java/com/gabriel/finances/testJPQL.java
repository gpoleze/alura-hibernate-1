package com.gabriel.finances;

import com.gabriel.finances.model.Account;
import com.gabriel.finances.model.Category;
import com.gabriel.finances.model.Transaction;
import com.gabriel.finances.model.TransactionType;
import org.junit.jupiter.api.Test;

import javax.persistence.Query;
import java.util.List;

class testJPQL extends BaseTest {

    @Test
    void testingHowToWorkWithJPQLInsteadOfSQL() {
        em.getTransaction().begin();

        Account account = new Account();
        account.setId(2);

        String jpql = "SELECT t FROM Transaction t WHERE t.account = :pAccount " +
                "AND t.type = :pType " +
                "ORDER BY t.value ASC";
        Query query = em.createQuery(jpql);
        query.setParameter("pAccount", account);
        query.setParameter("pType", TransactionType.OUT);

        List<Transaction> transactions = query.getResultList();

        System.out.println("\n\n------------RESULTS------------");
        transactions.forEach(transaction -> {
            System.out.println("Account.id:\t" + transaction.getId());
            System.out.println("Description:\t" + transaction.getDescription());
        });
        System.out.println("---------END OF RESULTS---------\n\n");

        em.getTransaction().commit();

    }

    @Test
    void testManyToManyRelationship() {
        em.getTransaction().begin();

        Category category = new Category();
        category.setId(3);

        String jpql = "SELECT t FROM Transaction t JOIN t.category c WHERE c = :pCategory";
        Query query = em.createQuery(jpql);

        query.setParameter("pCategory", category);

        List<Transaction> transactions = query.getResultList();

        System.out.println("\n\n------------RESULTS------------");

        transactions.forEach(transaction -> {
            System.out.println("Account.id:\t" + transaction.getId());
            System.out.println("Description:\t" + transaction.getDescription());
        });

        System.out.println("---------END OF RESULTS---------\n\n");

        em.getTransaction().commit();
    }

    @Test
    void testBidirectionalRelatioshipAccountAntTransactions() {
        em.getTransaction().begin();

        Transaction transaction = em.find(Transaction.class, 4);
        Account account = transaction.getAccount();
        System.out.println(account.getOwner());

        System.out.println(account.getTransactions().size());
        account.getTransactions().forEach(System.out::println);

    }

    @Test
    void testReportWithAllTransactions() {
        em.getTransaction().begin();

        String jpql = "SELECT a FROM Account a JOIN FETCH a.transactions";
        Query query = em.createQuery(jpql);


        List<Account> accounts = query.getResultList();

        accounts.forEach(account -> {
            System.out.println("Onwer:\t" + account.getOwner());
            System.out.println("Transaction:\t" + account.getTransactions());
        });
    }

    @Test
    void testReportWithAllTransactionsUsingALeftJoin() {
        String jpql = "SELECT DISTINCT a FROM Account a LEFT JOIN FETCH a.transactions";
        Query query = em.createQuery(jpql);


        List<Account> accounts = query.getResultList();

        accounts.forEach(account -> {
            System.out.println("\n----------------");
            System.out.println("Onwer:\t" + account.getOwner());
            System.out.println("Transaction:\t" + account.getTransactions());
            System.out.println("----------------\n");
        });
    }
}
