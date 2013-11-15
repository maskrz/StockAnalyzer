/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockanalyzer;

import stockanalyzer.tempDatas.Indices;
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
import stockanalyzer.tempDatas.Companies;
import stockanalyzer.tempDatas.Currency;
import stockanalyzer.tempDatas.Stocks;

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
        Integer y = 2013;
        ArrayList<Integer> years = new ArrayList();
//        years.add(2011);
//        years.add(2012);
        years.add(y);
        Analyzer a = new Analyzer();
        Reader r = new Reader();
        Stocks stocks = new Stocks(years, a, r);
        stocks.analyzeStocks();
//        System.out.println(stocks);
        Currency currency = new Currency(years, a, stocks, r);
        currency.analyzeCurrency();
//        System.out.println(currency);
        Indices indices = new Indices(years, a, stocks, currency, r);
        indices.analyzeIndices();
//        System.out.println(indices);
        Companies companies = new Companies(years, a, stocks, currency, r, indices.indicesVal);
//        System.out.println(companies.companies.get(6).indices.size());
        companies.analyzeCompanies();
//        System.out.println(companies);
        companies.save();
        
    }
}
