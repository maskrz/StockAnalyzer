/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockanalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import stockanalyzer.tempDatas.Companies;
import stockanalyzer.tempDatas.Currency;
import stockanalyzer.tempDatas.Indices;
import stockanalyzer.tempDatas.Stocks;
import weka.classifiers.Classifier;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.AdditiveRegression;
import weka.classifiers.meta.Vote;
import weka.classifiers.trees.M5P;
import weka.classifiers.trees.REPTree;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author Skrzypek
 */
public class Analyzer {

    File file;
    String[] weights;
    Reader r;
    Stocks stocks;
    Currency currency;
    Indices indices;
    Companies companies;

    public Analyzer(Reader reader) {
        r = reader;
        weights = new String[361];
        readProperties();
    }

    public void setFile(File f) {
        file = f;
    }

    public double[] calcPrices(int instances, double[] weights, int d) {
        try {
//            System.out.println(file);
//            System.out.println("calculating prices");
            Instances data = new Instances(new BufferedReader(new FileReader(file)));
            data.setClassIndex(data.numAttributes() - 1);
            double[] prices = avarage(data, instances, weights, d);
            return prices;
        } catch (Exception ex) {
            Logger.getLogger(Analyzer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public double[] getPrices(Instances data, Classifier model, int instances, int d) {

        try {
            model.buildClassifier(data);
            double[] prices = new double[5];
            Instance ins = data.instance(instances);
            prices[d] = model.classifyInstance(ins);

            return prices;
        } catch (Exception ex) {
            System.out.println("tu sie jebie");
//            Logger.getLogger(Analyzer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private double[] avarage(Instances data, int instances, double[] weights, int d) {
//        System.out.println("calc avarage");
        double[] gp = getPrices(data, new LinearRegression(), instances, d);
        double[] ibk = getPrices(data, new IBk(), instances, d);
        double[] ar = getPrices(data, new AdditiveRegression(), instances, d);
        double[] vote = getPrices(data, new Vote(), instances, d);
        double[] m5p = getPrices(data, new M5P(), instances, d);
        double[] rept = getPrices(data, new REPTree(), instances, d);

        double[] prices = new double[10];
        double t = (gp[d] * weights[0] + ibk[d] * weights[1] + ar[d] * weights[2] + vote[d] * weights[3] + m5p[d] * weights[4] + rept[d] * weights[5]);
        double sigma = (Math.pow(t - gp[d], 2) + Math.pow(t - ibk[d], 2) +Math.pow(t - ar[d], 2) +Math.pow(t - vote[d], 2)+Math.pow(t - m5p[d], 2)+Math.pow(t - rept[d], 2))/6;
        double dev = Math.sqrt(sigma);
        prices[d+5] = dev;
//        System.out.println(dev);
        prices[d] = t > 0? t : 0;
        return prices;
    }

    private void readProperties() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("src/properties/Weights.properties"));
            weights[30] = prop.getProperty("30");
            weights[60] = prop.getProperty("60");
            weights[90] = prop.getProperty("90");
            weights[180] = prop.getProperty("180");
            weights[360] = prop.getProperty("360");
        } catch (IOException ex) {
            Logger.getLogger(Indices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void analyze() {
        
        for(int i = 0; i < 5; i++) {
            int j = 0;
            switch (i) {
                case 0: 
                    j = 30;
                    break;
                case 1:
                    j = 60;
                    break;
                case 2:
                    j = 90;
                    break;
                case 3:
                    j = 180;
                    break;
                case 4:
                    j = 360;
                    break;
            }
            System.out.println("-------------------- ANALYZE: "+ j+" days --------------------");
            analyze(j);
        }
        

//        System.out.println(stocks);
        companies.save();
    }
    
    void analyze(int days) {
        String[] s = weights[days].split(";");
        int historicalPeriod = Integer.valueOf(s[0]);
        double[] w = new double[6];
        w[0] = Double.valueOf(s[1]);
        w[1] = Double.valueOf(s[2]);
        w[2] = Double.valueOf(s[3]);
        w[3] = Double.valueOf(s[4]);
        w[4] = Double.valueOf(s[5]);
        w[5] = Double.valueOf(s[6]);
//        System.out.println(historicalPeriod + " "+days);
        stocks.analyzeStocks(w, historicalPeriod, days);
        System.out.println("STOCKS DONE");
//        System.out.println(stocks);
        currency.analyzeCurrency(w, historicalPeriod, days);
        System.out.println("CURRENCY DONE");
//        System.out.println(currency);
        indices.analyzeIndices(w, historicalPeriod, days);
        System.out.println("INDICES DONE");
//        System.out.println(indices);
//        System.out.println(companies.companies.get(6).indices.size());
        companies.analyzeCompanies(w, historicalPeriod, days);
        System.out.println("COMPANIES DONE");
//        System.out.println(companies);
    }

    public void createArffFiles() {
        
        ArrayList<Integer> years = new ArrayList();
        int y = Calendar.getInstance().get(Calendar.YEAR);
        System.out.println(y);
        for(int i = 2009; i <= y; i++) {
            years.add(i);
        }
        System.out.println(years.size());
        
        stocks = new Stocks(years, this, r);
        currency = new Currency(years, this, stocks, r);
        indices = new Indices(years, this, stocks, currency, r);
        companies = new Companies(years, this, stocks, currency, r, indices.indicesVal);
        stocks.createArffs();
        System.out.println("STOCKS ARFF DONE");
        currency.createArffs();  
        System.out.println("CURRENCY ARFF DONE");
        indices.createArffs();
        System.out.println("INDICES ARFF DONE");
        companies.createArffs();
        System.out.println("COMPANIES ARFF DONE");
        
        
    }
}
