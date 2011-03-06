/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dasher.utils;

import java.io.InputStream;
import javax.swing.JComponent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;

/**
 *
 * @author joshua
 */
public class MenuReaderTest {

    public MenuReaderTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of parse method, of class MenuReader.
     */
    @Test
    public void testParse_3args_1() throws Exception {
        System.out.println("parse");
        InputSource in = new InputSource(getClass().getResourceAsStream("menutest.xml"));
        Receiver receiver = new Receiver();
        boolean byAction = false;
        MenuReader instance = new MenuReader();
        JComponent expResult = null;
        JComponent result = instance.parse(in, receiver, byAction);
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    public Receiver getReceiver() {
        return new Receiver();
    }
    public class Receiver {
        public void menuAlphaOne() {
            System.out.println("Alpha One");
        }
        public void menuAlphaTwo() {
            System.out.println("Alpha Two");
        }
        public void menuBetaOne() {
            System.out.println("Beta One");
        }
        public void menuBetaTwo() {
            System.out.println("Beta Two");
        }
        public void menuBetaThree() {
            System.out.println("Beta Three");
        }
    }

    /**
     * Test of parse method, of class MenuReader.
     */
    @Test
    public void testParse_3args_2() throws Exception {
//        System.out.println("parse");
//        InputStream in = null;
//        Object receiver = null;
//        boolean byAction = false;
//        MenuReader instance = new MenuReader();
//        JComponent expResult = null;
//        JComponent result = instance.parse(in, receiver, byAction);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of parse method, of class MenuReader.
     */
    @Test
    public void testParse_3args_3() throws Exception {
//        System.out.println("parse");
//        String url = "";
//        Object receiver = null;
//        boolean byAction = false;
//        MenuReader instance = new MenuReader();
//        JComponent expResult = null;
//        JComponent result = instance.parse(url, receiver, byAction);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getResult method, of class MenuReader.
     */
    @Test
    public void testGetResult() {
//        System.out.println("getResult");
//        MenuReader instance = new MenuReader();
//        JComponent expResult = null;
//        JComponent result = instance.getResult();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getDTDEntity method, of class MenuReader.
     */
    @Test
    public void testGetDTDEntity() throws Exception {
//        System.out.println("getDTDEntity");
//        MenuReader instance = new MenuReader();
//        InputStream expResult = null;
//        InputStream result = instance.getDTDEntity();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of startElement method, of class MenuReader.
     */
    @Test
    public void testStartElement() throws Exception {
//        System.out.println("startElement");
//        String namespaceURI = "";
//        String simpleName = "";
//        String qualName = "";
//        Attributes tagAttributes = null;
//        MenuReader instance = new MenuReader();
//        instance.startElement(namespaceURI, simpleName, qualName, tagAttributes);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of characters method, of class MenuReader.
     */
    @Test
    public void testCharacters() throws Exception {
//        System.out.println("characters");
//        char[] ch = null;
//        int start = 0;
//        int length = 0;
//        MenuReader instance = new MenuReader();
//        instance.characters(ch, start, length);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of endElement method, of class MenuReader.
     */
    @Test
    public void testEndElement() throws Exception {
//        System.out.println("endElement");
//        String uri = "";
//        String localName = "";
//        String qName = "";
//        MenuReader instance = new MenuReader();
//        instance.endElement(uri, localName, qName);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

}