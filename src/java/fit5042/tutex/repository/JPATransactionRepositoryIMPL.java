/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5042.tutex.repository;

import fit5042.tutex.repository.entities.BankTransaction;
import fit5042.tutex.repository.entities.BankUser;
import fit5042.tutex.repository.entities.TransactionType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jmid3
 */
@Stateless
public class JPATransactionRepositoryIMPL implements BankTransactionRepository{

    @PersistenceContext //(unitName = "CTStudent-ejbPU")
    private EntityManager em;
    
    @Override
    public void addTransaction(BankTransaction transaction) throws TransactionExistsException {
        if (searchTransactionById(transaction.getBankTransactionId()) != null){
            throw new TransactionExistsException("The transaction already exists!");
        }
        
        em.persist(transaction);
    }

    @Override
    public BankTransaction searchTransactionById(int transaction_id) {
        BankTransaction transaction = this.em.find(BankTransaction.class, transaction_id);
        return transaction;
    }

    @Override
    public List<BankTransaction> getAllTransactions() throws Exception {
        List<BankTransaction> retValue = new ArrayList<>();
        Query query = em.createNamedQuery(BankTransaction.GET_ALL);
        retValue = query.getResultList();
        return retValue; 
    }

    @Override
    public List<BankUser> getAllBankUsers() throws Exception {
        List<BankUser> retValue = new ArrayList<>();
        Query query = em.createNamedQuery(BankUser.GET_ALL);
        retValue = query.getResultList();
        return retValue;
    }

    @Override
    public List<TransactionType> getAllTransactionTypes(){
        List<TransactionType> retValue = new ArrayList<>();
        Query query = em.createNamedQuery(TransactionType.GET_ALL);
        retValue = query.getResultList();
        return retValue;
    }
    
    @Override
    public void removeTransaction(int transaction_id) throws Exception {
        BankTransaction t = this.searchTransactionById(transaction_id);
        
        if (t == null){
            throw new NoTransactionExistsException("The transaction does not exist!");
        }
        
        em.remove(t);
    }

    @Override
    public void editTransaction(BankTransaction transaction) throws Exception {
        if (searchTransactionById(transaction.getBankTransactionId()) != null){
            throw new NoTransactionExistsException("The transaction does not exist!");
        }
        
        em.merge(transaction);
    }

    @Override
    public Set<BankTransaction> searchTransactionByUser(BankUser customer) throws Exception {
        int userId = customer.getBankUserId();
        Query query = em.createQuery("SELECT t FROM BankTransaction t where t.userAccount.userId.bankUserId = :userId");
        query.setParameter("userId",userId);
        return new HashSet<>(query.getResultList());
    }

    @Override
    public Set<BankTransaction> searchTransactionByType(TransactionType type) {
        Integer typeId = type.getTransactionTypeId();
        Query query = em.createQuery("Select t from BankTransaction t where t.type.transactionTypeId = :typeId");
        query.setParameter("typeId",typeId);
        return new HashSet<>(query.getResultList());
    }

    @Override
    public Set<BankTransaction> searchTransactionByName(String title){
        Query query = em.createQuery("SELECT t FROM BankTransaction t where t.bankTransactionName = :title");
        query.setParameter("title",title);
        return new HashSet<>(query.getResultList());
    }

    @Override
    public List<BankTransaction> getFilteredList(Integer searchId, String searchName, TransactionType searchType) {
        
        List<BankTransaction> retValue = new ArrayList<>();
        List<BankTransaction> idList = new ArrayList<>();
        List<BankTransaction> nameList = new ArrayList<>();
        List<BankTransaction> typeList = new ArrayList<>();
        boolean idRun = false, nameRun = false;
        
        if (searchId != null && searchId != 0 && searchId != 2147483647) {
            idRun = true;
            Query query = em.createQuery("SELECT t FROM BankTransaction t WHERE t.bankTransactionId = :searchId");
            query.setParameter("searchId", searchId);
            idList = query.getResultList();
            
            retValue.addAll(idList);
        }
        
        if (searchName != null && searchName.trim().length() != 0 ){
            nameRun = true;
            Query nQuery = em.createQuery("SELECT t FROM BankTransaction t WHERE t.bankTransactionName = :searchName");
            nQuery.setParameter("searchName", searchName);
            nameList = nQuery.getResultList();
            
            if (!idRun){
                retValue.addAll(nameList);
            } else {
                retValue = this.intersection(retValue, nameList);
            }
        }
        
        if (searchType != null && searchType.getTransactionTypeId() != 0){
            Integer typeId = searchType.getTransactionTypeId();
            Query tQuery = em.createQuery("SELECT t FROM BankTransaction t Where t.type.transactionTypeId = :typeId");
            tQuery.setParameter("typeId", typeId);
            typeList = tQuery.getResultList();
            
            if (!idRun && !nameRun) {
                retValue.addAll(typeList);
            } else {
                retValue = this.intersection(retValue, typeList);
            }
            
        }
        
        return retValue;
    }

    @Override
    public int getNextId() {
        try {
            List<BankTransaction> all = this.getAllTransactions();
            int newId = 0;
        
            for (BankTransaction t : all) {
                if (t.getBankTransactionId() > newId) {
                    newId = t.getBankTransactionId();
                }
            }
        
            return newId + 1;
        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
    
    public class TransactionExistsException extends Exception {
        public TransactionExistsException(String message){
            super(message);
        }
    }
    
    public class NoTransactionExistsException extends Exception {
        public NoTransactionExistsException(String message){
            super(message);
        }
    }
    
    private List<BankTransaction> intersection(List<BankTransaction> list1, List<BankTransaction> list2){
        
        ArrayList<BankTransaction> retValue = new ArrayList<>();
        
        for (BankTransaction t : list1){
            for (BankTransaction b : list2){
                if (t.getBankTransactionId() == b.getBankTransactionId()){
                    retValue.add(t);
                }
            }
        }
        
        return retValue;
    }
}
