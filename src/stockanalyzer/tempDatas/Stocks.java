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
public class Stocks extends Predictable{

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

    public void analyzeStocks(double[] w, int historicalPerdiod, int days) {
        int j = 0;
        System.out.println(days);
        switch (days) {
                case 30: 
                    j = 0;
                    break;
                case 60:
                    j = 1;
                    break;
                case 90:
                    j = 2;
                    break;
                case 180:
                    j = 3;
                    break;
                case 360:
                    j = 4;
                    break;
            }
        setWeights(w);
        setPeriod(historicalPerdiod);
        setDays(days);
//        System.out.println("djia");
        djia = analyzeStock("djia", j);
//        System.out.println("nasdaq");
        nasdaq = analyzeStock("nasdaq", j);
//        System.out.println("eurostoxx");
        eurostoxx = analyzeStock("eurostoxx50", j);
    }
    
    public double[] analyzeStock(String stock, int d) {
//        File f = r.createStockArff(stock, years);
        File f = trimData("src/datas/" + stock + "/" + stock + ".arff", ",?");
//        System.out.println("file trimmed");
        int instances = 0;
        try {
            Scanner sc = new Scanner(f);
            instances = Integer.valueOf(sc.nextLine().substring(1));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StockAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
        }        
        a.setFile(f);
//        System.out.println("file setted");
//        System.out.println(f);
        return a.calcPrices(instances, weights, d);
    }
    
    public void createArffs() {
        r.createStockArff("djia", years);
        r.createStockArff("nasdaq", years);
        r.createStockArff("eurostoxx50", years);
    }
}
