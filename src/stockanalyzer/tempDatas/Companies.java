/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockanalyzer.tempDatas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import stockanalyzer.Analysis;
import stockanalyzer.Analyzer;
import stockanalyzer.Node;
import stockanalyzer.NodeVal;
import stockanalyzer.Reader;
import stockanalyzer.StockAnalyzer;
import stockanalyzer.Writer;

/**
 *
 * @author Skrzypek
 */
public class Companies {

    public double[] euro;
    public double[] dollar;
    Reader r;
    ArrayList<Integer> years;
    Analyzer a;
    Stocks stocks;
    Currency currency;
    ArrayList<NodeVal> indicesVal;
    public ArrayList<Company> companies;
    public ArrayList<Analysis> analysis;

    public Companies(ArrayList<Integer> y, Analyzer an, Stocks s, Currency cu, Reader re, ArrayList<NodeVal> indices) {

        r = re;
        years = y;
        a = an;
        stocks = s;
        currency = cu;
        indicesVal = indices;
        companies = new ArrayList();
        analysis = new ArrayList();
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("src/properties/Companies.properties"));
            String l = prop.getProperty("list");
            String[] list = l.split(";");
            int c = Integer.valueOf(list[0]);
            c++;
            for (int i = 1; i < c; i++) {
                companies.add(new Company(list[i]));
            }
        } catch (IOException ex) {
            Logger.getLogger(Indices.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            prop.load(new FileInputStream("src/properties/Cindices.properties"));
            String l = prop.getProperty("list");
            String[] list = l.split(";");
            int c = Integer.valueOf(list[0]);
            for (int i = 1; i < c; i++) {
                String[] tempList = list[i].split(":");
                int com = Integer.valueOf(tempList[0]);
                com--;
                int ind = Integer.valueOf(tempList[1]);
                ind--;
//                System.out.println(com+" " + ind);
                companies.get(com).addIndex(indicesVal.get(ind).getKey());
//                companies.add(new Company(list[i]));
            }
        } catch (IOException ex) {
            Logger.getLogger(Indices.class.getName()).log(Level.SEVERE, null, ex);
        }
        r.companies = this;
    }

    public void analyzeCompanies() {
        int i = 1;
        for (Company c : companies) {
//            if(n.getKey().equals("WIG")) {
            System.out.println(i + " " + c.name);
            c.value = analyzeCompany(c.name);
//            }
            i++;
        }
    }

    public double[] analyzeCompany(String companyName) {
        File f = r.createCompanyArff(companyName, years, stocks, currency);
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

    public Company getCompanyByName(String name) {
        for (Company c : companies) {
            if (c.name.equals(name)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String s = "";
        for (Company c : companies) {
            s += createString(c.value, c.name);
        }

        return s;

    }

    public String createString(double[] prices, String company) {
        String s = "------------------ Prices of " + company + " ---------------------- \r\n"
                + "in 30days: " + prices[0] + "\r\n"
                + "in 60days: " + prices[1] + "\r\n"
                + "in 90days: " + prices[2] + "\r\n"
                + "in 180days: " + prices[3] + "\r\n"
                + "in 360days: " + prices[4] + "\r\n";
        return s;
    }

    public void prepareToSave(Writer w) {
        try {
            Calendar calendar = Calendar.getInstance();
            java.util.Date currentDate = calendar.getTime();
            java.sql.Date date = new java.sql.Date(currentDate.getTime());
            Properties prop = new Properties();
            prop.load(new FileInputStream("src/properties/Companies.properties"));
            for (Company c : companies) {
                Analysis a = new Analysis(new BigDecimal(c.value[0]), new BigDecimal(c.value[0]), new BigDecimal(c.value[0]), "0.0", 30, date, c.name, Integer.valueOf(prop.getProperty(c.name)));
                w.addAnalysis(a);
                a = new Analysis(new BigDecimal(c.value[1]), new BigDecimal(c.value[1]), new BigDecimal(c.value[1]), "0.0", 60, date, c.name, Integer.valueOf(prop.getProperty(c.name)));
                w.addAnalysis(a);
                a = new Analysis(new BigDecimal(c.value[2]), new BigDecimal(c.value[2]), new BigDecimal(c.value[2]), "0.0", 90, date, c.name, Integer.valueOf(prop.getProperty(c.name)));
                w.addAnalysis(a);
                a = new Analysis(new BigDecimal(c.value[3]), new BigDecimal(c.value[3]), new BigDecimal(c.value[3]), "0.0", 180, date, c.name, Integer.valueOf(prop.getProperty(c.name)));
                w.addAnalysis(a);
                a = new Analysis(new BigDecimal(c.value[4]), new BigDecimal(c.value[4]), new BigDecimal(c.value[4]), "0.0", 360, date, c.name, Integer.valueOf(prop.getProperty(c.name)));
                w.addAnalysis(a);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Companies.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Companies.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void save() {
        Writer w = new Writer();
        prepareToSave(w);
        w.saveAnalisies();
    }
}
