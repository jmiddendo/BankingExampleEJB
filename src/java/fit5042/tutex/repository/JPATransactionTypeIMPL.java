/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5042.tutex.repository;

import fit5042.tutex.repository.entities.BankTransaction;
import fit5042.tutex.repository.entities.TransactionType;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author jmid3
 */
@Stateless
public class JPATransactionTypeIMPL  implements TransactionTypeRepository {
    
    private static final String PERSISTENCE_UNIT = "HDStudent-app-ejbPU";

    @PersistenceContext //(unitName = "CTStudent-ejbPU")
    private EntityManager em;
    
    public JPATransactionTypeIMPL(){
    }

    @Override
    public void addTransactionType(TransactionType transactionType) throws Exception {
        
        if (this.searchTransactionTypeById(transactionType.getTransactionTypeId()) != null){
            throw new Exception("Transaction type already exists!");
        }
        
        em.persist(transactionType);
        
    }

    @Override
    public void editTransactionType(TransactionType transactionType) throws Exception {
        if (this.searchTransactionTypeById(transactionType.getTransactionTypeId()) == null){
            throw new Exception("Transaction type doesn't exist!");
        }
        
        em.merge(transactionType);
    }

    @Override
    public void deleteTransactionType(int id) throws Exception{
        TransactionType t = this.searchTransactionTypeById(id);
        
        if (t == null){
            throw new Exception("Transaction type does not exist!");
        }
        
        em.remove(t);
    }

    @Override
    public TransactionType searchTransactionTypeById(int id) {
        TransactionType transactionType = this.em.find(TransactionType.class, id);
        return transactionType;
    }

    @Override
    public List<TransactionType> getAllTransactionTypes() {
        return em.createNamedQuery(TransactionType.GET_ALL).getResultList();
    }

    @Override
    public List<TransactionType> searchTransactionByName(String ttName) {
        Query query = em.createQuery("SELECT t FROM TransactionType t WHERE t.transactionTypeName = :ttName");
        query.setParameter("ttName", ttName);
        return query.getResultList();
    }

    @Override
    public List<TransactionType> searchTransactionLikeDescription(String ttDescription) {
        Query query = em.createQuery("SELECT t FROM TransactionType t WHERE t.transactionTypeDescription LIKE :ttDesciption");
        query.setParameter("ttDescription", "%" + ttDescription + "%");
        return query.getResultList();
    }
    
    public class TransactionTypeExistsException extends Exception {
        public TransactionTypeExistsException(String message){
            super(message);
        }
    }

    @Override
    public int generateNextId() throws Exception {
        
        
        
        List<TransactionType> lstTransactionType = this.getAllTransactionTypes();
        int highId = 0;
        for (TransactionType tt : lstTransactionType) {
            if (tt.getTransactionTypeId() > highId) {
                highId = tt.getTransactionTypeId();
            }
        }
        return highId + 1;
    }
}
