package com.gabriel.finances;

import com.gabriel.finances.dao.TransactionDao;
import com.gabriel.finances.model.Account;
import com.gabriel.finances.model.TransactionType;
import org.junit.jupiter.api.Test;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

class testJPQLFunctions extends BaseTest {

    @Test
    void testingSumOfValues() {
        em.getTransaction().begin();

        Account account = new Account();
        account.setId(2);

        String jpql = "SELECT SUM(t.value) FROM Transaction t WHERE t.account = :pAccount " +
                "AND t.type = :pType " +
                "ORDER BY t.value ASC";
        Query query = em.createQuery(jpql);
        query.setParameter("pAccount", account);
        query.setParameter("pType", TransactionType.OUT);

        BigDecimal sum = (BigDecimal) query.getSingleResult();

        System.out.println("\n\n------------RESULTS------------");
        System.out.println("The sum is " + sum);
        System.out.println("---------END OF RESULTS---------\n\n");

        em.getTransaction().commit();

    }

    @Test
    void testingAverageOfValuesPerDay() {

        em.getTransaction().begin();

        Account account = new Account();
        account.setId(2);


        TransactionDao dao = new TransactionDao(em);

        List<Double> averages = dao.getAvergesByDayAndType(TransactionType.OUT, account);

        System.out.println("\n\n------------RESULTS------------");
        averages.forEach(avg -> System.out.println("The dayly average is:" + avg));
        System.out.println("---------END OF RESULTS---------\n\n");

        em.getTransaction().commit();

    }
}