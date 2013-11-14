/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockanalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.SMOreg;
import weka.classifiers.meta.RegressionByDiscretization;
import weka.classifiers.trees.M5P;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author Skrzypek
 */
public class Analyzer {

    File file;

    public void setFile(File f) {
        file = f;
    }

    public void calcStock(int instances) {
        try {
            Instances data = new Instances(new BufferedReader(new FileReader(file)));
            data.setClassIndex(data.numAttributes() - 1);

            double[] pricesLR = getPrices(data, new LinearRegression(), instances);
            double[] pricesRBD = getPrices(data, new RegressionByDiscretization(), instances);
            double[] pricesM5P = getPrices(data, new M5P(), instances);
            double[] pricesSMO = getPrices(data, new SMOreg(), instances);
            double[] prices = avarage(pricesLR, pricesRBD, pricesM5P, pricesSMO);

            System.out.println(
                    "Prices: \r\n"
                    + "in 30days: " + prices[0] + "\r\n"
                    + "in 60days: " + prices[1] + "\r\n"
                    + "in 90days: " + prices[2] + "\r\n"
                    + "in 180days: " + prices[3] + "\r\n"
                    + "in 360days: " + prices[4] + "\r\n"
                    );
        } catch (Exception ex) {
            Logger.getLogger(Analyzer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public double[] getPrices(Instances data, Classifier model, int instances) {
        
        try {
            model.buildClassifier(data);
            double[] prices = new double[5];
            Instance ins30 = data.instance(instances);
            prices[0] = model.classifyInstance(ins30);
            Instance ins60 = data.instance(instances + 1);
            prices[1] = model.classifyInstance(ins60);
            Instance ins90 = data.instance(instances + 2);
            prices[2] = model.classifyInstance(ins90);
            Instance ins180 = data.instance(instances + 3);
            prices[3] = model.classifyInstance(ins180);
            Instance ins360 = data.instance(instances + 4);
            prices[4] = model.classifyInstance(ins360);

            return prices;
        } catch (Exception ex) {
            Logger.getLogger(Analyzer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private double[] avarage(double[] lr, double[] rbd, double[] m5p, double[] smo) {
        double[] prices = new double[5];
        for(int i = 0; i < 5; i++) {
            prices[0] = (lr[0] + rbd[0] + m5p[0] + smo[0])/4;
            prices[1] = (lr[1] + rbd[1] + m5p[1] + smo[1])/4;
            prices[2] = (lr[2] + rbd[2] + m5p[2] + smo[2])/4;
            prices[3] = (lr[3] + rbd[3] + m5p[3] + smo[3])/4;
            prices[4] = (lr[4] + rbd[4] + m5p[4] + smo[4])/4;
        }
        return prices;
    }
}
