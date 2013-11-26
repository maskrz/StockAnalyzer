/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockanalyzer;

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
        
        
        Reader r = new Reader();
        Analyzer a = new Analyzer(r);
        a.createArffFiles();
        a.analyze();    
//        System.out.println(a.companies);
    }
}
