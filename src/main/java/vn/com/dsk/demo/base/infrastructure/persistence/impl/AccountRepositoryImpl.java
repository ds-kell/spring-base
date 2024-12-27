package vn.com.dsk.demo.base.infrastructure.persistence.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import vn.com.dsk.demo.base.domain.entities.Account;
import vn.com.dsk.demo.base.domain.repository.AccountRepository;


@Repository
public class AccountRepositoryImpl implements AccountRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Account findByUsernameOrEmail(String username, String email) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> account = query.from(Account.class);
        query.select(account).where(cb.or(cb.equal(account.get("username"), username), cb.equal(account.get("email"), email)));
        return entityManager.createQuery(query).getSingleResult();
    }
    // Criteria API:
    // Specifications:
}

