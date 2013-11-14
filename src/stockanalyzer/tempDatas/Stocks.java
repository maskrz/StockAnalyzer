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
public class Stocks {

    public double[] djia;
    public double[] nasdaq;
    public double[] eurostoxx;    
    Reader r;
    ArrayList<Integer> years;
    Analyzer a;
    
    public Stocks(ArrayList<Integer> y, Analyzer an, Reader re) {
        r = re;
        years = y;
        a = an;
    }

    public void setDjia(double[] d) {
        djia = d;
    }

    public void setNasdaq(double[] d) {
        nasdaq = d;
    }

    public void setEurostoxx(double[] d) {
        eurostoxx = d;
    }

    @Override
    public String toString() {
        String s = "";
        s+= createString(djia, "djia");
        s+= createString(nasdaq, "nasdaq");
        s+= createString(eurostoxx, "eurostoxx");       
        return s;

    }

    public String createString(double[] prices, String stock) {
        String s = "------------------ Prices of " + stock + " ---------------------- \r\n"
                + "in 30days: " + prices[0] + "\r\n"
                + "in 60days: " + prices[1] + "\r\n"
                + "in 90days: " + prices[2] + "\r\n"
                + "in 180days: " + prices[3] + "\r\n"
                + "in 360days: " + prices[4] + "\r\n";
        return s;
    }

    public void analyzeStocks() {
        djia = analyzeStock("djia");
        nasdaq = analyzeStock("nasdaq");
        eurostoxx = analyzeStock("eurostoxx50");
    }
    
    public double[] analyzeStock(String stock) {
        File f = r.createStockArff(stock, years);
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
