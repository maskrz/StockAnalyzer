/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockanalyzer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import stockanalyzer.tempDatas.Stocks;

/**
 *
 * @author Skrzypek
 */
public class Reader {

    String[] djiaData;
    String[] nasdaqData;
    String[] eurostoxxData;
    
    public Reader() {
        djiaData = new String[10000];
        nasdaqData = new String[10000];
        eurostoxxData = new String[10000];
    }

    public File createStockArff(String stockName, ArrayList<Integer> years) {
        
        String result = "";
        int instances = 0;
        result += "@RELATION " + stockName + "\r\n";
        result += "@ATTRIBUTE day NUMERIC " + "\r\n";
        result += "@ATTRIBUTE price NUMERIC " + "\r\n";
        result += "@DATA " + "\r\n";
        for (Integer i : years) {
            try {
                File f = new File("src/datas/" + stockName + "_" + i.toString() + ".csv");
                Scanner sc = new Scanner(f);
                String line = "";
                while (sc.hasNext()) {
                    instances++;
                    line = sc.nextLine();
                    String attr = line.split(";")[1].equals("N/A") ? "?" : line.split(";")[1];
                    String toSave = instances + "," + attr + "\r\n";
                    result += toSave;
                    switch (stockName) {
                        case "djia":
                            djiaData[instances - 1] = attr;
                        case "nasdaq":
                            nasdaqData[instances - 1] = attr;
                        case "eurostoxx":
                            eurostoxxData[instances - 1] = attr;
                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        result += instances + 30 + "," + "?" + "\r\n";
        result += instances + 60 + "," + "?" + "\r\n";
        result += instances + 90 + "," + "?" + "\r\n";
        result += instances + 180 + "," + "?" + "\r\n";
        result += instances + 360 + "," + "?" + "\r\n";
        result = "%" + instances + "\r\n" + result;

        try {
            File f = new File("src/datas/" + stockName + ".arff");
            f.delete();
            f.createNewFile();
            PrintWriter out = null;
            out = new PrintWriter(new BufferedWriter(new FileWriter("src/datas/" + stockName + ".arff", true)));
            out.print(result);
            out.close();
            return f;
        } catch (IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public File createCurrencyArff(String currencyName, ArrayList<Integer> years, Stocks stocks) {

        String result = "";
        int instances = 0;
        result += "@RELATION " + currencyName + "\r\n";
        result += "@ATTRIBUTE day NUMERIC " + "\r\n";
        result += "@ATTRIBUTE djia NUMERIC " + "\r\n";
        result += "@ATTRIBUTE nasdaq NUMERIC " + "\r\n";
        result += "@ATTRIBUTE eurostoxx NUMERIC " + "\r\n";
        result += "@ATTRIBUTE price NUMERIC " + "\r\n";
        result += "@DATA " + "\r\n";
        for (Integer i : years) {

            try {
                File f = new File("src/datas/" + currencyName + "_" + i.toString() + ".csv");
                Scanner sc = new Scanner(f);
                String line = "";
                while (sc.hasNext()) {
                    instances++;
                    line = sc.nextLine();
                    String attr = line.split(";")[1].equals("N/A") ? "?" : line.split(";")[1];
                    String toSave = instances + "," + djiaData[instances - 1] + "," + nasdaqData[instances - 1] + "," + eurostoxxData[instances - 1] + "," + attr + "\r\n";
                    result += toSave;
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        result += instances + 30 + "," + stocks.djia[0] + "," + stocks.nasdaq[0] + "," + stocks.eurostoxx[0] + "," + "?" + "\r\n";
        result += instances + 60 + "," + stocks.djia[1] + "," + stocks.nasdaq[1] + "," + stocks.eurostoxx[1] + "," + "?" + "\r\n";
        result += instances + 90 + "," + stocks.djia[2] + "," + stocks.nasdaq[2] + "," + stocks.eurostoxx[2] + "," + "?" + "\r\n";
        result += instances + 180 + "," + stocks.djia[3] + "," + stocks.nasdaq[3] + "," + stocks.eurostoxx[3] + "," + "?" + "\r\n";
        result += instances + 360 + "," + stocks.djia[4] + "," + stocks.nasdaq[4] + "," + stocks.eurostoxx[4] + "," + "?" + "\r\n";
        result = "%" + instances + "\r\n" + result;

        try {
            File f = new File("src/datas/" + currencyName + ".arff");
            f.delete();
            f.createNewFile();
            PrintWriter out = null;
            out = new PrintWriter(new BufferedWriter(new FileWriter("src/datas/" + currencyName + ".arff", true)));
            out.print(result);
            out.close();
            return f;
        } catch (IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
