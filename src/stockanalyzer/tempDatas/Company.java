/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockanalyzer.tempDatas;

import java.util.ArrayList;

/**
 *
 * @author Skrzypek
 */
public class Company {
    public String name;
    public ArrayList<String> indices;
    public double[] value = new double[5];
    public double[] rate = new double[5];
    public double actuall;
    
    public Company(String n) {
        name = n;
        indices = new ArrayList();
    }
    
    public double getPercentage(int i) {
        double res = value[i] / actuall;
        double r = res - 1;
        return r*100;
    }
    
    public void addIndex(String indexName) {
        indices.add(indexName);
    }
    
    public String toString() {
        String s = "------------------ Prices of " + name + " ---------------------- \r\n"
                + "in 30days: " + value[0] + " " + rate[0] + " " + actuall +"\r\n"
                + "in 60days: " + value[1] + " " + rate[1] + "\r\n"
                + "in 90days: " + value[2] + " " + rate[2] + "\r\n"
                + "in 180days: " + value[3] + " " + rate[3] + "\r\n"
                + "in 360days: " + value[4] + " " + rate[4] + "\r\n";
        return s;
    }
}
