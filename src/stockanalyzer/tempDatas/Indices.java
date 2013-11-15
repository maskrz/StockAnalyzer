/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockanalyzer.tempDatas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import stockanalyzer.Analyzer;
import stockanalyzer.Node;
import stockanalyzer.NodeVal;
import stockanalyzer.Reader;
import stockanalyzer.StockAnalyzer;
import stockanalyzer.tempDatas.Currency;
import stockanalyzer.tempDatas.Stocks;

/**
 *
 * @author Skrzypek
 */
public class Indices {

    public double[] euro;
    public double[] dollar;
    Reader r;
    ArrayList<Integer> years;
    Analyzer a;
    Stocks stocks;
    Currency currency;
    ArrayList<Node> indices;
    public ArrayList<NodeVal> indicesVal;

    public Indices(ArrayList<Integer> y, Analyzer an, Stocks s, Currency cu, Reader re) {

        r = re;
        years = y;
        a = an;
        stocks = s;
        currency = cu; 
        indices = new ArrayList();
        indicesVal = new ArrayList();
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("src/properties/Indices.properties"));
            String l = prop.getProperty("list");
            String[] list = l.split(";");
            int c = Integer.valueOf(list[0]);
            c++;
            for(int i = 1; i < c; i++) {
                indices.add(new Node(list[i]));
                indicesVal.add(new NodeVal(list[i]));
            }
        } catch (IOException ex) {
            Logger.getLogger(Indices.class.getName()).log(Level.SEVERE, null, ex);
        }
        r.indices = indices;
        r.indicesVal = indicesVal;
    }
    
    public void analyzeIndices() {
        for(NodeVal n : indicesVal) {
//            if(n.getKey().equals("WIG")) {
            System.out.println(n.getKey());
                n.value = analyzeIndex(n.getKey());
//            }
            
        }
    }
    
    public double[] analyzeIndex(String indexName) {
        File f = r.createIndexArff(indexName, years, stocks, currency);
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
    
    @Override
    public String toString() {
        String s = "";
        for(NodeVal n : indicesVal) {
            System.out.println(n.getKey());
            s+= createString(n.value, n.getKey());     
        }
        
        return s;

    }

    public String createString(double[] prices, String index) {
        String s = "------------------ Prices of " + index + " ---------------------- \r\n"
                + "in 30days: " + prices[0] + "\r\n"
                + "in 60days: " + prices[1] + "\r\n"
                + "in 90days: " + prices[2] + "\r\n"
                + "in 180days: " + prices[3] + "\r\n"
                + "in 360days: " + prices[4] + "\r\n";
        return s;
    }
}
