/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockanalyzer;

import java.math.BigDecimal;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Skrzypek
 */
public class WriterTest {
    Writer w;
    public WriterTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        w = new Writer();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addAnalysis method, of class Writer.
     */
    @Test
    public void testAddAnalysis() {
        System.out.println("addAnalysis");
        Analysis a = new Analysis(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, null, 1, null, null, 1);
        assertEquals(0, w.analysies.size());
        w.addAnalysis(a);
        assertEquals(1, w.analysies.size());
    }
}