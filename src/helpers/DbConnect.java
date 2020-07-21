/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author hocin
 */
public class DbConnect {
    
    
 
    
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 3306 ;
    private static final String DB_NAME = "store_db" ;
    private static final String USERNAME = "root" ;
    private static final String PASSWORD = "" ;
    private static Connection connection ;
    
    
    
    
    
    
    public static Connection getConnect() {
        
        try {
            //  "localhost:3306/store_db","root",""
            connection =DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s",HOST,PORT,DB_NAME),USERNAME,PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
            
            return connection;
              
      
    }
    
}
