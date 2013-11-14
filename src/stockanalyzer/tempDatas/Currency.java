/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockanalyzer.tempDatas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import stockanalyzer.Analyzer;
import stockanalyzer.Reader;
import stockanalyzer.StockAnalyzer;

/**
 *
 * @author Skrzypek
 */
public class Currency {
    public double[] euro;
    public double[] dollar;    
    Reader r;
    ArrayList<Integer> years;
    Analyzer a;
    Stocks stocks;
    
    public Currency(ArrayList<Integer> y, Analyzer an, Stocks s, Reader re) {
        r = re;
        years = y;
        a = an;
        stocks = s;
    }

    public void setDollar(double[] d) {
        dollar = d;
    }

    public void setEuro(double[] d) {
        euro = d;
    }

    @Override
    public String toString() {
        String s = "";
        s+= createString(euro, "euro");
        s+= createString(dollar, "dollar");      
        return s;

    }

    public String createString(double[] prices, String currency) {
        String s = "------------------ Prices of " + currency + " ---------------------- \r\n"
                + "in 30days: " + prices[0] + "\r\n"
                + "in 60days: " + prices[1] + "\r\n"
                + "in 90days: " + prices[2] + "\r\n"
                + "in 180days: " + prices[3] + "\r\n"
                + "in 360days: " + prices[4] + "\r\n";
        return s;
    }

    public void analyzeCurrency() {
        euro = analyzeCurr("euro");
        dollar = analyzeCurr("dollar");
    }
    
    public double[] analyzeCurr(String currency) {
        File f = r.createCurrencyArff(currency, years, stocks);
        int instances = 0;
        try {
            Scanner sc = new Scanner(f);
            instances = Integer.valueOf(sc.nextLine().substring(1));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StockAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
        }        
        a.setFile(f);
        return a.calcPrices(instances);
    }
}
