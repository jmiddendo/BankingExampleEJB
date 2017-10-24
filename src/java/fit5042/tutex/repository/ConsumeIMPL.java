/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fit5042.tutex.repository;

import fit5042.tutex.repository.entities.BankTransaction;
import fit5042.tutex.repository.entities.BankUser;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 *
 * @author jmid3
 */
@Singleton
public class ConsumeIMPL implements WebServiceConsumptionRepository {

    private static final String BASE_URI = "http://localhost:8080/HD-Student-rest/webresources";

    public ConsumeIMPL() {
    }
    
    @Override
    public String searchBankUserById(int id) {
        
        String strId = Integer.toString(id);
        final String methodPath = "/fit5042.tutex.rest.entities.bankuser/findByBankUser/" + strId;
        
        System.out.println(methodPath);
        
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        
        try {
            url = new URL(BASE_URI + methodPath);
            
            conn = (HttpURLConnection) url.openConnection();
            
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            
            conn.setRequestMethod("GET");
            
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            Scanner inStream = new Scanner(conn.getInputStream());
            
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            } 
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(ConsumeIMPL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsumeIMPL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            
            if (conn != null){
                conn.disconnect();
            }
            
        }
        
        return textResult;
    }

    @Override
    public String searchBankUserByType(String type) {
        
        final String methodPath = "/fit5042.tutex.rest.entities.bankuser/findByType/" + type;
        
        System.out.println(methodPath);
        
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        
        try {
            url = new URL(BASE_URI + methodPath);
            
            conn = (HttpURLConnection) url.openConnection();
            
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            
            conn.setRequestMethod("GET");
            
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            Scanner inStream = new Scanner(conn.getInputStream());
            
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            } 
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(ConsumeIMPL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsumeIMPL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            
            if (conn != null){
                conn.disconnect();
            }
            
        }
        
        return textResult;
        
    }

    @Override
    public String searchBankUserByEmail(String email) {
        
        final String methodPath = "/fit5042.tutex.rest.entities.bankuser/findByEmail/" + email;
        
        System.out.println(methodPath);
        
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        
        try {
            url = new URL(BASE_URI + methodPath);
            
            conn = (HttpURLConnection) url.openConnection();
            
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            
            conn.setRequestMethod("GET");
            
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            Scanner inStream = new Scanner(conn.getInputStream());
            
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            } 
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(ConsumeIMPL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsumeIMPL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            
            if (conn != null){
                conn.disconnect();
            }
            
        }
        
        return textResult;
    }

    @Override
    public String searchTransactionByTransactionType(String type) {
               
        final String methodPath = "/fit5042.tutex.rest.entities.banktransaction/findBankTransactionByType/" + type;
        
        System.out.println(methodPath);
        
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        
        try {
            url = new URL(BASE_URI + methodPath);
            
            conn = (HttpURLConnection) url.openConnection();
            
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            
            conn.setRequestMethod("GET");
            
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            Scanner inStream = new Scanner(conn.getInputStream());
            
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            } 
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(ConsumeIMPL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConsumeIMPL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            
            if (conn != null){
                conn.disconnect();
            }
            
        }
        
        return textResult;
    }
    
}
