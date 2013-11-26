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

    BigDecimal currentRate;
    BigDecimal mediumRate;
    BigDecimal percentageRate;
    String algRate;
    int period;
    Date date;
    String companyName;
    int companyId;

    public Analysis(BigDecimal cr, BigDecimal mr, BigDecimal pr, String ar, int p, Date d, String cn, int id) {
        currentRate = cr;
        mediumRate = mr;
        percentageRate = pr;
        algRate = ar;
        period = p;
        date = d;
        companyName = cn;
        companyId = id;
    }

    public void save(Connection connection, int id) {
        try {
            String stm = "INSERT INTO temp_analisies (id, current_rate, medium_rate, percentage_rate, algoritms_rate, period, date, company_id, created_at, updated_at)"
                    + " values (?, ?, ?, ?, ?, ?, ?, ?, default, default)";
            PreparedStatement pst = connection.prepareStatement(stm);
            pst.setInt(1, id);
            pst.setBigDecimal(2, currentRate);
            pst.setBigDecimal(3, mediumRate);
            pst.setBigDecimal(4, percentageRate);
            pst.setString(5, algRate);
            pst.setInt(6, period);
            pst.setDate(7, date);
            pst.setInt(8, companyId);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Analysis.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
}
