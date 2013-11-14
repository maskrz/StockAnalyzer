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

/**
 *
 * @author Skrzypek
 */
public class Reader {

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
}
