/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockanalyzer.tempDatas;

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
class Predictable {
    
    double[] weights;
    public int days;
    public int period;
    
    public void setWeights(double[] w) {
        weights = w;
    }
    
    public void setDays(int d) {
        days = d;
    }
    
    public void setPeriod(int p) {
        period = p;
    }
    
    
    public File trimData(String path, String lastLine, Company company) {
        try {
//            System.out.println(companyName);
            Scanner sc = new Scanner(new File(path));
            int c = 0;
            String line = sc.nextLine();
            int stop = Integer.valueOf(line.substring(1));
            int start = period == 0? 0 : stop - period;
            int instances = period+1;
            String result = "%"+instances +"\r\n";
            String last = "";
            while(sc.hasNext()) {
                line = sc.nextLine();
                if(line.contains("%") || line.contains("@")) {
//                    System.out.println(line);
                    result += line +"\r\n";
                    
                } else {
                    c++;
                    if (c >= start && c <= stop) {
                        result += line +"\r\n";
                        if(line.charAt(line.length()-1)!='?') {
                            last = line;
                        }
                    }
                }
                
            }
            
            int ind = last.lastIndexOf(",");
            company.actuall = Double.valueOf(last.substring(ind + 1));
            int p = stop + days;
            result += p+ lastLine +"\r\n";
            File f = new File("src/datas/temp.arff");
            f.mkdirs();
            f.createNewFile();
            PrintWriter out = null;
            out = new PrintWriter(new BufferedWriter(new FileWriter("src/datas/temp.arff")));
            out.print(result);
            out.close();
            return f;
        } catch (Exception ex) {
            Logger.getLogger(Predictable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public File trimData(String path, String lastLine) {
        try {
            Scanner sc = new Scanner(new File(path));
            int c = 0;
            String line = sc.nextLine();
            int stop = Integer.valueOf(line.substring(1));
            int start = period == 0? 0 : stop - period;
            int instances = period+1;
            String result = "%"+instances +"\r\n";
            while(sc.hasNext()) {
                line = sc.nextLine();
                if(line.contains("%") || line.contains("@")) {
                    result += line +"\r\n";
                    
                } else {
                    c++;
                    if (c >= start && c <= stop) {
                        result += line +"\r\n";
                    }
                }
                
            }
            int p = stop + days;
//            System.out.println(stop + " "+ period + " "+p);
            result += p+ lastLine +"\r\n";
            File f = new File("src/datas/temp.arff");
            f.delete();
            f.createNewFile();
            PrintWriter out = null;
            out = new PrintWriter(new BufferedWriter(new FileWriter("src/datas/temp.arff")));
            out.print(result);
            out.close();
            return f;
        } catch (Exception ex) {
            Logger.getLogger(Predictable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
