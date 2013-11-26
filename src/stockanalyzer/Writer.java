/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockanalyzer;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
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

        try {
            String user = "postgres";
            String password = "password";
            String host = "127.0.0.1:5432/gpwanalizer";
            
            Properties prop = new Properties();
                prop.load(new FileInputStream("src/properties/Login.properties"));
                user = prop.getProperty("user");
                password = prop.getProperty("password");
                host = prop.getProperty("host");
                System.out.println(user+" " +password + " "+host);

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
                String url = "jdbc:postgresql://"+host;
                connection = DriverManager.getConnection(
    //                    "jdbc:postgresql://127.0.0.1:5432/gpwanalizer", "postgres", "nowe1haslo");
                        url, user, password);
                System.out.println("Connected");
                Statement stmt = null;
                stmt = connection.createStatement();
                String sql = "DELETE from temp_analisies;";
                stmt.executeUpdate(sql);
    //            connection.commit();
                int id = 1;
                for (Analysis a : analysies) {
                System.out.println("Saving analysis: "+id +" of "+analysies.size()+" - "+100*id/analysies.size() + "% ");
                    a.save(connection, id);
                    id++;
                }
                connection.close();
            } catch (SQLException ex) {
                System.out.println("Oooops, something went wrong");
                Logger.getLogger(Analysis.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
