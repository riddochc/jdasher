/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dasher.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 *
 * @author joshua
 */
public class FileListReaderTest {

    public FileListReaderTest() {
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
     * Test of getFiles method, of class FileListReader.
     */
    @Test
    public void testGetFiles() throws SAXException {
        System.out.println("getFiles");
        ArrayList<String> files = new ArrayList<String>();
        files.add("blah");
        FileListReader reader = new FileListReader(files);
        System.out.println(reader.getFiles());
        assertEquals("the result of getFiles differs", files, reader.getFiles());
    }

    /**
     * Test of parse method, of class FileListReader.
     */
    @Test
    public void testParse_InputSource() throws Exception {
    }

    /**
     * Test of parse method, of class FileListReader.
     */
    @Test
    public void testParse_InputStream() throws Exception {
    }

    /**
     * Test of parse method, of class FileListReader.
     */
    @Test
    public void testParse_String() throws Exception {        
    }

    /**
     * Test of parse method, of class FileListReader.
     */
    @Test
    public void testParse_InputStream_Collection() throws Exception {
        System.out.println("FileListReader.parse static");
        InputStream in = getClass().getResourceAsStream("files.xml");
        ArrayList<String> files = new ArrayList<String>();
        FileListReader.parse(in, files);
        System.out.println(files);
        assertTrue("\"one.txt\" is not in the files", files.contains("one.txt"));
        assertTrue("\"two.txt\" is not in the files", files.contains("two.txt"));
    }

    /**
     * Test of startElement method, of class FileListReader.
     */
    @Test
    public void testStartElement() throws Exception {
        System.out.println("startElement");
        String namespaceURI = "";
        String simpleName = "file";
        String qualName = "file";
        AttributesImpl tagAttributes = new AttributesImpl();
        tagAttributes.addAttribute("", "name", "name", "CDATA", "thefile");
        ArrayList<String> files = new ArrayList<String>();
        FileListReader instance = new FileListReader(files);
        instance.startElement(namespaceURI, simpleName, qualName, tagAttributes);
        System.out.println(files);
        assertTrue("\"thefile\" is not in the files", files.contains("thefile"));
    }

}