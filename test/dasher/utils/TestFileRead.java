/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dasher.utils;

import dasher.resources.StaticResourceManager;
import dasher.ui.JTestFrame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.junit.Test;

/**
 *
 * @author joshua
 */
public class TestFileRead {

    /**
     * The Logger for this class.
     */
    private static Logger log = Logger.getLogger(TestFileRead.class.getName());

    @Test
    public void testProgessInput() {
        class InputWorker extends TestWorker<Integer,String> {

            @Override
            protected Integer doInBackground() throws Exception {
                System.out.println("Input worker started...");
                InputStream in = StaticResourceManager.getResourceStream("menus.xml");
                int read = 0, aval = in.available(), prog = 0;
                System.out.format("Reading in %s chars...\n", aval);
                try {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(in), 5);
                    String line;
                    while ((line = reader.readLine()) != null) {
                        read += line.length() + 1;
                        System.out.println(line);
                        prog = 100 * read / aval;
//                        System.out.format("Progress: %n%%...\n", prog);
                        setProgress(prog);
                        publish(line);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            log.log(Level.SEVERE, null, ex);
                        }
                    }
                } catch (IOException ex) {
                    log.log(Level.SEVERE, null, ex);
                }
                System.out.println("Input worker done.");
                return Integer.valueOf(read);
            }

        }
        InputWorker inw = new InputWorker();
        final JTestFrame form = new JTestFrame();
        form.addWorker(inw);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                form.setVisible(true);
            }
        });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            log.log(Level.SEVERE, null, ex);
        }

        while (form.isVisible()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                log.log(Level.SEVERE, null, ex);
            }
        }
    }

}
