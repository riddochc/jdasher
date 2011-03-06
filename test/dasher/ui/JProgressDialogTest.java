/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dasher.ui;

import java.beans.PropertyChangeEvent;
import org.jdesktop.swingworker.SwingWorker;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joshua
 */
public class JProgressDialogTest {

    public JProgressDialogTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of show method, of class JProgressDialog.
     */
    @Test
    public void testShow_String_int() {
        System.out.println("show");
        String msg = "";
        int max = 0;
        JProgressDialog instance = null;
        instance.show(msg, max);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of show method, of class JProgressDialog.
     */
    @Test
    public void testShow_String() {
        System.out.println("show");
        String msg = "";
        JProgressDialog instance = null;
        instance.show(msg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of show method, of class JProgressDialog.
     */
    @Test
    public void testShow_String_boolean() {
        System.out.println("show");
        String msg = "";
        boolean indeterminate = false;
        JProgressDialog instance = null;
        instance.show(msg, indeterminate);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of doTask method, of class JProgressDialog.
     */
    @Test
    public void testDoTask() {
        System.out.println("doTask");
        String msg = "Reading...";
        int max = 0;
        SwingWorker worker = new ReaderTask();
        JProgressDialog instance = null;
        instance.doTask(msg, max, worker);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    class ReaderTask extends SwingWorker<Void,String> {

        @Override
        protected Void doInBackground() throws Exception {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

    /**
     * Test of setProgress method, of class JProgressDialog.
     */
    @Test
    public void testSetProgress() {
        System.out.println("setProgress");
        int done = 0;
        int max = 0;
        JProgressDialog instance = null;
        instance.setProgress(done, max);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class JProgressDialog.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        JProgressDialog.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of propertyChange method, of class JProgressDialog.
     */
    @Test
    public void testPropertyChange() {
        System.out.println("propertyChange");
        PropertyChangeEvent evt = null;
        JProgressDialog instance = null;
        instance.propertyChange(evt);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}