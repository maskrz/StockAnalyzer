/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockanalyzer;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Calendar;

/**
 *
 * @author Skrzypek
 */
public class StockAnalyzer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentDate = calendar.getTime();
        java.sql.Date date = new java.sql.Date(currentDate.getTime());
        BigDecimal lr = new BigDecimal(2.55);
        BigDecimal mr = new BigDecimal(9.12);
        BigDecimal hr = new BigDecimal(17.44);
        Analysis a = new Analysis(lr, mr, hr, "5.22", 90, date, "TestCompany", 3);
        Writer w = new Writer();
        w.addAnalysis(a);
        w.saveAnalisies();
    }
}
