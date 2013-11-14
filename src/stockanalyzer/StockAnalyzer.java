/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockanalyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Skrzypek
 */
public class StockAnalyzer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Calendar calendar = Calendar.getInstance();
//        java.util.Date currentDate = calendar.getTime();
//        java.sql.Date date = new java.sql.Date(currentDate.getTime());
//        BigDecimal lr = new BigDecimal(2.55);
//        BigDecimal mr = new BigDecimal(9.12);
//        BigDecimal hr = new BigDecimal(17.44);
//        Analysis a = new Analysis(lr, mr, hr, "5.22", 90, date, "TestCompany", 3);
//        Writer w = new Writer();
//        w.addAnalysis(a);
//        w.saveAnalisies();
        Reader r = new Reader();
        Integer y = 2013;
        ArrayList<Integer> years = new ArrayList();
        years.add(y);
        File f = r.createStockArff("djia", years);
        System.out.println(f);
        Analyzer a = new Analyzer();
        int instances = 0;
        try {
            Scanner sc = new Scanner(f);
            instances = Integer.valueOf(sc.nextLine().substring(1));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StockAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        a.setFile(f);
        a.calcStock(instances);
    }
}
