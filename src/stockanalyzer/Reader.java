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
import stockanalyzer.tempDatas.Companies;
import stockanalyzer.tempDatas.Company;
import stockanalyzer.tempDatas.Currency;
import stockanalyzer.tempDatas.Stocks;

/**
 *
 * @author Skrzypek
 */
public class Reader {

    String[] djiaData;
    String[] nasdaqData;
    String[] eurostoxxData;
    String[] dollarData;
    String[] euroData;
    public ArrayList<Node> indices;
    public ArrayList<NodeVal> indicesVal;
    public Companies companies;

    public Reader() {
        djiaData = new String[10000];
        nasdaqData = new String[10000];
        eurostoxxData = new String[10000];
        dollarData = new String[10000];
        euroData = new String[10000];
        indices = new ArrayList();
        indicesVal = new ArrayList();
        companies = null;
    }

    public void createStockArff(String stockName, ArrayList<Integer> years) {

        String result = "";
        int instances = 0;
        result += "@RELATION " + stockName + "\r\n";
        result += "@ATTRIBUTE day NUMERIC " + "\r\n";
        result += "@ATTRIBUTE price NUMERIC " + "\r\n";
        result += "@DATA " + "\r\n";
        for (Integer i : years) {
            try {
                File f = new File("src/datas/" + stockName + "/" + stockName + "_" + i.toString() + ".csv");
                Scanner sc = new Scanner(f);
                String line = "";
                String ld = "";
                while (sc.hasNext()) {
                    line = sc.nextLine();
//                    System.out.println(f);
//                    System.out.println(line);
                    String date = line.split(";")[0];
                    if (!date.equals(ld)) {
                        instances++;
//                        System.out.println(line);
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
                    ld = date;

                }

            } catch (FileNotFoundException ex) {
                System.out.println("Cannot find specified path: " + stockName + " " + i);
            }
        }
        result = "%" + instances + "\r\n" + result;

        try {
            File f = new File("src/datas/" + stockName + "/" + stockName + ".arff");
            f.delete();
            f.createNewFile();
            PrintWriter out = null;
            out = new PrintWriter(new BufferedWriter(new FileWriter("src/datas/" + stockName + "/" + stockName + ".arff", true)));
            out.print(result);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createCurrencyArff(String cn, ArrayList<Integer> years) {

        String result = "";
        int instances = 0;
        result += "@RELATION " + cn + "\r\n";
        result += "@ATTRIBUTE day NUMERIC " + "\r\n";
        result += "@ATTRIBUTE djia NUMERIC " + "\r\n";
        result += "@ATTRIBUTE nasdaq NUMERIC " + "\r\n";
        result += "@ATTRIBUTE eurostoxx NUMERIC " + "\r\n";
        result += "@ATTRIBUTE price NUMERIC " + "\r\n";
        result += "@DATA " + "\r\n";
        String currencyName = cn;
        for (Integer i : years) {

            try {
                File f = new File("src/datas/" + currencyName + "/" + currencyName + "_" + i.toString() + ".csv");
                Scanner sc = new Scanner(f);
                String line = "";
                String ld = "";
                while (sc.hasNext()) {
                    line = sc.nextLine();
                    String date = line.split(";")[0];
                    if (!date.equals(ld)) {
                        instances++;
                        String attr = line.split(";")[1].equals("N/A") ? "?" : line.split(";")[1];
                        String replace = attr.replace(",", ".");
                        String toSave = instances + "," + djiaData[instances - 1] + "," + nasdaqData[instances - 1] + "," + eurostoxxData[instances - 1] + "," + replace + "\r\n";
                        result += toSave;
                        switch (currencyName) {
                            case "dollar":
                                dollarData[instances - 1] = replace;
                            case "nasdaq":
                                euroData[instances - 1] = replace;
                        }
                    } 
                    ld = date;
                }
            } catch (FileNotFoundException ex) {
                System.out.println("Cannot find specified path: " + currencyName + " " + i);
            }
        }
        result = "%" + instances + "\r\n" + result;

        try {
            File f = new File("src/datas/" + currencyName + "/" + currencyName + ".arff");
            f.delete();
            f.createNewFile();
            PrintWriter out = null;
            out = new PrintWriter(new BufferedWriter(new FileWriter("src/datas/" + currencyName + "/" + currencyName + ".arff", true)));
            out.print(result);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createIndexArff(String in, ArrayList<Integer> years) {

        String result = "";
        int instances = 0;
        result += "@RELATION " + in + "\r\n";
        result += "@ATTRIBUTE day NUMERIC " + "\r\n";
        result += "@ATTRIBUTE djia NUMERIC " + "\r\n";
        result += "@ATTRIBUTE nasdaq NUMERIC " + "\r\n";
        result += "@ATTRIBUTE eurostoxx NUMERIC " + "\r\n";
        result += "@ATTRIBUTE dollar NUMERIC " + "\r\n";
        result += "@ATTRIBUTE euro NUMERIC " + "\r\n";
        result += "@ATTRIBUTE gain NUMERIC " + "\r\n";
        result += "@ATTRIBUTE price NUMERIC " + "\r\n";
        result += "@DATA " + "\r\n";
        String indexName = in.toUpperCase();
        for (Integer i : years) {

            try {
                File f = new File("src/datas/indices/" + indexName + "/" + indexName + "_" + i.toString() + ".csv");
                Scanner sc = new Scanner(f);
                String line = "";
                String ld = "";
                while (sc.hasNext()) {
                    line = sc.nextLine();
                    String date = line.split(";")[0];
                    if (!date.equals(ld)) {
                        instances++;
                        String price = line.split(";")[1].equals("N/A") ? "?" : line.split(";")[1];
                        String gain = line.split(";")[2].equals("N/A") ? "?" : line.split(";")[2];
                        String replacePrice = price.replace(",", ".");
                        String replaceGain = gain.replace(",", ".");
                        String toSave = instances + "," + djiaData[instances - 1] + "," + nasdaqData[instances - 1] + "," + eurostoxxData[instances - 1] + "," + dollarData[instances - 1] + "," + euroData[instances - 1] + "," + replaceGain + "," + replacePrice + "\r\n";
                        result += toSave;
                        for (Node n : indices) {
                            if (n.getKey().equals(in)) {
                                n.value[instances - 1] = replacePrice;
                                break;
                            }
                        }
                    }
                    ld = date;
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        result = "%" + instances + "\r\n" + result;

        try {
            File f = new File("src/datas/indices/" + indexName + "/" + indexName + ".arff");
            f.delete();
            f.createNewFile();
            PrintWriter out = null;
            out = new PrintWriter(new BufferedWriter(new FileWriter("src/datas/indices/" + indexName + "/" + indexName + ".arff", true)));
            out.print(result);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createCompanyArff(String cn, ArrayList<Integer> years) {
        String result = "";
        int instances = 0;
        Company comp = companies.getCompanyByName(cn);
        result += "@RELATION " + cn + "\r\n";
        result += "@ATTRIBUTE day NUMERIC " + "\r\n";
        for (String index : comp.indices) {
            result += "@ATTRIBUTE " + index + " NUMERIC " + "\r\n";
        }
        result += "@ATTRIBUTE gain NUMERIC " + "\r\n";
        result += "@ATTRIBUTE transactions NUMERIC " + "\r\n";
        result += "@ATTRIBUTE gainValue NUMERIC " + "\r\n";
        result += "@ATTRIBUTE before NUMERIC " + "\r\n";
        result += "@ATTRIBUTE price NUMERIC " + "\r\n";
        result += "@DATA " + "\r\n";
        String companyName = cn.toUpperCase();
        for (Integer i : years) {
            String before = "?";
            try {
                File f = new File("src/datas/companies/" + companyName + "/" + companyName + "_" + i.toString() + ".csv");
                Scanner sc = new Scanner(f);
                String line = "";
                String ld = "";
                while (sc.hasNext()) {
                    line = sc.nextLine();
                    String date = line.split(";")[0];
                    if (!date.equals(ld)) {
                        instances++;
                        String toSave = instances + ",";
                        for (String index : comp.indices) {
                            for (Node n : indices) {
                                if (n.getKey().equals(index)) {
//                                System.out.println(n.value[10]);
                                    toSave += n.value[instances - 1] + ",";
                                    break;
                                }
                            }
                        }
                        String[] sp = line.split(";");
                        String price = sp[1].equals("N/A") ? "?" : sp[1];
                        String gain = sp[2].equals("N/A") ? "?" : sp[2];
                        String transactions = sp[3].equals("N/A") ? "?" : sp[3];
                        String gainValue = sp[4].equals("N/A") ? "?" : sp[4];

                        String replacePrice = price.replace(",", ".");
                        String replaceGain = gain.replace(",", ".");
                        String replaceTransactions = transactions.replace(",", ".");
                        String replaceGainValue = gainValue.replace(",", ".");
                        toSave += replaceGain + "," + replaceTransactions + "," + replaceGainValue + "," + before + "," + replacePrice + "\r\n";
                        result += toSave;

                        before = replacePrice;
                    }
                    ld = date;
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        result = "%" + instances + "\r\n" + result;

        try {
            File f = new File("src/datas/companies/" + companyName + "/" + companyName + ".arff");
            f.delete();
            f.createNewFile();
            PrintWriter out = null;
            out = new PrintWriter(new BufferedWriter(new FileWriter("src/datas/companies/" + companyName + "/" + companyName + ".arff", true)));
            out.print(result);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
