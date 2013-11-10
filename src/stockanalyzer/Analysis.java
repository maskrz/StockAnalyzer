/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockanalyzer;

/**
 *
 * @author Skrzypek
 */
public class Analysis {
    float lowestRate;
    float mediumRate;
    float highestRate;
    String algRate;
    int period;
    String date;
    String companyName;
    int companyId;
    
    public Analysis(float lr, float mr, float hr, String ar, int p, String d, String cn, int id) {
        lowestRate = lr;
        mediumRate = mr;
        highestRate = hr;
        algRate = ar;
        period = p;
        date = d;
        companyName = cn;
        companyId = id;
    }
    
}
