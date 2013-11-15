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
    public double[] value;
    
    public Company(String n) {
        name = n;
        indices = new ArrayList();
    }
    
    public void addIndex(String indexName) {
        indices.add(indexName);
    }
}
