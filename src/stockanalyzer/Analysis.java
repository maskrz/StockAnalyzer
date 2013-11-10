/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockanalyzer;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Skrzypek
 */
public class Analysis {

    BigDecimal lowestRate;
    BigDecimal mediumRate;
    BigDecimal highestRate;
    String algRate;
    int period;
    Date date;
    String companyName;
    int companyId;

    public Analysis(BigDecimal lr, BigDecimal mr, BigDecimal hr, String ar, int p, Date d, String cn, int id) {
        lowestRate = lr;
        mediumRate = mr;
        highestRate = hr;
        algRate = ar;
        period = p;
        date = d;
        companyName = cn;
        companyId = id;
    }

    public void save(Connection connection) {
        try {
            System.out.println("Saving analysis");
            String stm = "INSERT INTO analisies (id, lowest_rate, medium_rate, highest_rate, algoritms_rate, period, date, company_id, created_at, updated_at)"
                    + " values (default, ?, ?, ?, ?, ?, ?, ?, default, default)";
            PreparedStatement pst = connection.prepareStatement(stm);
            pst.setBigDecimal(1, lowestRate);
            pst.setBigDecimal(2, highestRate);
            pst.setBigDecimal(3, mediumRate);
            pst.setString(4, algRate);
            pst.setInt(5, period);
            pst.setDate(6, date);
            pst.setInt(7, companyId);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Analysis.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
}
