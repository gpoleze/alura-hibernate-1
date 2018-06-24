package com.gabriel.finances.dao;

import com.gabriel.finances.model.Account;
import com.gabriel.finances.model.TransactionType;
import com.gabriel.finances.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class TransactionDao {

    private EntityManager em;

    public TransactionDao(EntityManager em) {
        this.em = em;
    }

    public List<Double> getAvergesByDayAndType(TransactionType type, Account account) {
        String jpql = "SELECT DISTINCT AVG(t.value) FROM Transaction t WHERE t.account = :pAccount " +
                "AND t.type = :pType " +
                "GROUP BY t.date";

        TypedQuery<Double> query = em.createQuery(jpql, Double.class);
        query.setParameter("pAccount", account);
        query.setParameter("pType", TransactionType.OUT);

        return query.getResultList();

    }
}
