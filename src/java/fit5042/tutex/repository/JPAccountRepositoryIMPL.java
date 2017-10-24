/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5042.tutex.repository;

import fit5042.tutex.repository.entities.Account;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author jmid3
 */
@Stateless
public class JPAccountRepositoryIMPL implements AccountRepository {

    @PersistenceContext
    EntityManager em;
    
    @Override
    public void addAccount(Account acc) throws Exception {
        if (this.searchAccountById(acc.getAccountId()) != null) {
            throw new Exception("Account already exists");
        }
        
        em.persist(acc);
    }

    @Override
    public void removeAccount(int id) throws Exception {
        
        Account acc = this.searchAccountById(id);
        if (acc == null){
            throw new Exception("Account does not exist!");
        }
        
        em.remove(id);
    }

    @Override
    public void editAccount(Account acc) throws Exception {
        if (this.searchAccountById(acc.getAccountId()) == null) {
            throw  new Exception("Account does not exist!");
        }
        
        em.merge(acc);
    }

    @Override
    public List<Account> getAllAccounts() throws Exception {
        
        Query query = this.em.createNamedQuery(Account.GET_ALL);
        return query.getResultList();
        
    }

    @Override
    public Account searchAccountById(int id) throws Exception {
        
        return this.em.find(Account.class, id);
        
    }

    @Override
    public int getNextAccountId() throws Exception {
        
        List<Account> all = this.getAllAccounts();
        int newId = 0;
        
        for (Account a : all){
            if (a.getAccountId() > newId){
                newId = a.getAccountId();
            } 
        }
        
        return newId + 1;
    }
    
    @Override
    public Set<Account> serachAccountByType(String type) throws Exception {
        
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = builder.createQuery(Account.class);
        Root<Account> c = criteriaQuery.from(Account.class);
        criteriaQuery.select(c).where(builder.equal(c.get("type"), type));
        Query query = em.createQuery(criteriaQuery);
        List<Account> returnValue = query.getResultList();
        if (returnValue == null || returnValue.isEmpty()){
            throw new Exception("Account does not exist in the database!");
        }else{
            return new HashSet(returnValue);
        }
        
    }

    @Override
    public Set<Account> searchAccountByUserId(int id) throws Exception {
        
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = builder.createQuery(Account.class);
        Root<Account> c = criteriaQuery.from(Account.class);
        criteriaQuery.select(c).where(builder.equal(c.get("accountId"), id));
        Query query = em.createQuery(criteriaQuery);
        List<Account> returnValue = query.getResultList();
        if (returnValue == null || returnValue.isEmpty()){
            throw new Exception("Account does not exist in the database!");
        }else{
            return new HashSet(returnValue);
        }
        
    }

    @Override
    public Set<Account> searchAccountByStatus(boolean status) throws Exception {
        
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = builder.createQuery(Account.class);
        Root<Account> c = criteriaQuery.from(Account.class);
        criteriaQuery.select(c).where(builder.equal(c.get("isActive"), status));
        Query query = em.createQuery(criteriaQuery);
        List<Account> returnValue = query.getResultList();
        if (returnValue == null || returnValue.isEmpty()){
            throw new Exception("Account does not exist in the database!");
        }else{
            return new HashSet(returnValue);
        }
    }

    
}
