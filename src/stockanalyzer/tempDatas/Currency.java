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
public class Currency extends Predictable{
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

    public void analyzeCurrency(double[] w, int historicalPerdiod, int days) {
        int j = 0;
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
        euro = analyzeCurr("euro", j);
        dollar = analyzeCurr("dollar", j);
    }
    
    public double[] analyzeCurr(String currency, int d) {
//        File f = r.createCurrencyArff(currency, years, stocks);
        File f = trimData("src/datas/" + currency + "/" + currency + ".arff", "," + stocks.djia[d] + "," + stocks.nasdaq[d] + "," + stocks.eurostoxx[d] + "," + "?" + "\r\n");
        int instances = 0;
        try {
            Scanner sc = new Scanner(f);
            instances = Integer.valueOf(sc.nextLine().substring(1));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StockAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
        }        
        a.setFile(f);
//        System.out.println(f);
        return a.calcPrices(instances, weights, d);
    } 
    
    public void createArffs() {
        r.createCurrencyArff("dollar", years);
        r.createCurrencyArff("euro", years);
    }
}
