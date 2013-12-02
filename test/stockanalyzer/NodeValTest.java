/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stockanalyzer;

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
public class NodeValTest {
    NodeVal n;
    public NodeValTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        n = new NodeVal("testKey");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getKey method, of class NodeVal.
     */
    @Test
    public void testGetKey() {
        System.out.println("getKey");
        String expResult = "testKey";
        String result = n.getKey();
        assertEquals(expResult, result);
    }

    /**
     * Test of getValue method, of class NodeVal.
     */
    @Test
    public void testGetValue() {
        System.out.println("getValue");
        double[] result = n.getValue();
        assertEquals(result.length, 10000);
    }
}