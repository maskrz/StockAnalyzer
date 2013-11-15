/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockanalyzer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Skrzypek & Bart
 */
public class NodeVal {

    String key;
    public double[] value;

    public NodeVal(String key) {
        this.key = key;
        value = new double[10000];
    }

    public String getKey() {
        return key;
    }

    public double[] getValue() {
        return value;
    }
//        public void saveNode(String path, String year, String date) {
//            System.out.println(path + key + "/" + key + "_" + year + ".csv");
//            PrintWriter out = null;
//            try {
//                out = new PrintWriter(new BufferedWriter(new FileWriter(path + key + "/" + key + "_" + year + ".csv", true)));
//                out.print(date + ";" + value+"\r\n");
//                out.close();
//            } catch (IOException ex) {
//                Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
//            } finally {
//                out.close();
//            }
//        }
}
