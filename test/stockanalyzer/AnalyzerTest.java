/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockanalyzer;

import java.io.File;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import weka.classifiers.Classifier;
import weka.core.Instances;

/**
 *
 * @author Skrzypek
 */
public class AnalyzerTest {
    Analyzer a;
    public AnalyzerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        a = new Analyzer(null);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setFile method, of class Analyzer.
     */
    @Test
    public void testSetFile() {
        System.out.println("setFile");
        File f = new File("test");
        a.setFile(f);
        // TODO review the generated test code and remove the default call to fail.
        Assert.assertEquals(f, a.file);
    }

}