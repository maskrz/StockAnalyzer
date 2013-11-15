/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockanalyzer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Skrzypek
 */
public class Writer {
    
    ArrayList<Analysis> analysies;
    Connection connection;
    
    public Writer() {
        analysies = new ArrayList();
        connection = null;
    }
    
    public void addAnalysis(Analysis a) {
        analysies.add(a);
    }
    
    
    public void saveAnalisies() {
        
        System.out.println("JDBC connection test");

        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver found");
        } catch (ClassNotFoundException ex) {
            System.out.println("Cannot find JDBC Driver");
            Logger.getLogger(Analysis.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Connection in 3... 2... 1...");
        
        try {
            connection = DriverManager.getConnection(
//                    "jdbc:postgresql://127.0.0.1:5432/gpwanalizer", "postgres", "nowe1haslo");
                    "jdbc:postgresql://pgsql-591378.vipserv.org/maskrz_prod", "maskrz_dev", "develo");
            System.out.println("Connected");
            for(Analysis a : analysies) {
                a.save(connection);
            }
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Oooops, something went wrong");
            Logger.getLogger(Analysis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
