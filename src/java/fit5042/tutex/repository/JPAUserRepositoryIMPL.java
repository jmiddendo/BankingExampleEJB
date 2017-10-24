/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5042.tutex.repository;

import fit5042.tutex.repository.entities.BankUser;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author jmid3
 */
@Stateless
public class JPAUserRepositoryIMPL implements BankUserRepository{

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void addBankUser(BankUser user) throws Exception {
        
        if (searchBankUserById(user.getBankUserId()) != null){
            throw new Exception("User already exists");
        }
        
        em.persist(user);
    }

    @Override
    public void removeBankUser(int id) throws Exception {
        
        BankUser u = searchBankUserById(id);
        if (u == null){
            throw new Exception("User does not exist!");
        }
        
        em.remove(u);
    }

    @Override
    public void editBankUser(BankUser user) throws Exception {
        if (this.searchBankUserById(user.getBankUserId()) == null){
            throw new Exception("User does not exist!");
        }
        
        em.merge(user);
    }

    @Override
    public List<BankUser> getAllBankUsers() throws Exception {
        List<BankUser> retValue = new ArrayList<>();
        Query query = em.createNamedQuery(BankUser.GET_ALL);
        retValue = query.getResultList();
        return retValue;
    }

    @Override
    public int getNextUserId() throws Exception {
        List<BankUser> all = this.getAllBankUsers();
        int newId = 0;
        
        for (BankUser u : all) {
            if (u.getBankUserId() > newId) {
                newId = u.getBankUserId();
            }
        }
        
        return newId + 1;
    }

    @Override
    public BankUser searchBankUserById(int id) {
        BankUser u = this.em.find(BankUser.class, id);
        return u;
    }

    @Override
    public Set<BankUser> searchBankUserByCombination(BankUser user) throws Exception {
        
        Integer id = user.getBankUserId();
        String fName = user.getFirstName();
        String lName = user.getLastName();
        String type = user.getType();
        String email = user.getEmail();
        
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<BankUser> criteriaQuery = builder.createQuery(BankUser.class);
        Root<BankUser> c = criteriaQuery.from(BankUser.class);
        
        List<Predicate> predicates = new ArrayList<Predicate>();
        
        if (fName != null && id > 0) {
            predicates.add(builder.equal(c.get("bankUserId"), id));
        }
        
        if (fName != null && fName.trim().length() != 0) {
            predicates.add(builder.like(c.<String>get("firstName"), "%" + fName + "%"));
        }
        
        if (lName != null && lName.trim().length() != 0) {
            predicates.add(builder.like(c.<String>get("lastName"), "%" + lName + "%"));
        }
        
        if (type != null && type.trim().length() != 0) {
            predicates.add(builder.like(c.<String>get("type"), "%" + type + "%"));
        }
        
        if (email != null && email.trim().length() != 0) {
            predicates.add(builder.like(c.<String>get("email"), "%" + email + "%"));
        }
        
        criteriaQuery.select(c).where(predicates.toArray(new Predicate[]{}));
        
        Query query = em.createQuery(criteriaQuery);
        List<BankUser> returnValue = query.getResultList();
        if (returnValue == null || returnValue.isEmpty()){
            throw new Exception("User does not exist in the database!");
        }else{
            return new HashSet(returnValue);
        }
    }

    
    
    @Override
    public Set<BankUser> searchUserByEmail(String email) throws Exception {
        
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<BankUser> criteriaQuery = builder.createQuery(BankUser.class);
        Root<BankUser> c = criteriaQuery.from(BankUser.class);
        criteriaQuery.select(c).where(builder.equal(c.get("email"), email));
        Query query = em.createQuery(criteriaQuery);
        List<BankUser> returnValue = query.getResultList();
        if (returnValue == null || returnValue.isEmpty()){
            throw new Exception("User does not exist in the database!");
        }else{
            return new HashSet(returnValue);
        }
        
    }

    @Override
    public Set<BankUser> searchUserBySuburb(String suburb) throws Exception {
        
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<BankUser> criteriaQuery = builder.createQuery(BankUser.class);
        Root<BankUser> c = criteriaQuery.from(BankUser.class);
        criteriaQuery.select(c).where(builder.equal(c.get("suburb"), suburb));
        Query query = em.createQuery(criteriaQuery);
        List<BankUser> returnValue = query.getResultList();
        if (returnValue == null || returnValue.isEmpty()){
            throw new Exception("User does not exist in the database!");
        }else{
            return new HashSet(returnValue);
        }
        
    }

    @Override
    public Set<BankUser> searchUserByState(String state) throws Exception {
        
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<BankUser> criteriaQuery = builder.createQuery(BankUser.class);
        Root<BankUser> c = criteriaQuery.from(BankUser.class);
        criteriaQuery.select(c).where(builder.equal(c.get("state"), state));
        Query query = em.createQuery(criteriaQuery);
        List<BankUser> returnValue = query.getResultList();
        if (returnValue == null || returnValue.isEmpty()){
            throw new Exception("User does not exist in the database!");
        }else{
            return new HashSet(returnValue);
        }
        
    }

    @Override
    public Set<BankUser> searchUserByType(String type) throws Exception {
        
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<BankUser> criteriaQuery = builder.createQuery(BankUser.class);
        Root<BankUser> c = criteriaQuery.from(BankUser.class);
        criteriaQuery.select(c).where(builder.equal(c.get("type"), type));
        Query query = em.createQuery(criteriaQuery);
        List<BankUser> returnValue = query.getResultList();
        if (returnValue == null || returnValue.isEmpty()) {
            throw new Exception("User does not exist in the database!");
        } else {
            return new HashSet(returnValue);
        }
        
    }
    
    
    
}
