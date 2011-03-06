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
import java.security.CodeSource;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import org.junit.*;

/**
 *
 * @author joshua
 */
public class Tests {

    /**
     * The Logger for this class.
     */
    private static Logger log = Logger.getLogger(Tests.class.getName());

    void printf(String f, Object... args) {
        System.out.format(f, args);
    }

    @Test
    public void testCodeSource() {
        CodeSource cs = StaticResourceManager.class.getProtectionDomain().getCodeSource();
        printf("CodeSource for ResourceManager: %s\n", cs);
    }

//    @Test
//    public void testProgessInput() {
//        class InputWorker extends TestWorker<Integer,String> {
//
//            @Override
//            protected Integer doInBackground() throws Exception {
//                InputStream in = StaticResourceManager.getResourceStream("menus.xml");
//                int read = 0;
//                try {
//                    BufferedReader reader = new BufferedReader(
//                            new InputStreamReader(in), 5);
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        try {
//                            read += line.length() + 1;
//                            setProgress(100*read/in.available());
//                            publish(line);
//                            System.out.println(line);
//                            Thread.sleep(100);
//                        } catch (InterruptedException ex) {
//                            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
//                } catch (IOException ex) {
//                    Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                return Integer.valueOf(read);
//            }
//
//        }
//        final JFrame form = new JTestFrame(new InputWorker());
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                form.setVisible(true);
//            }
//        });
//
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        while (form.isVisible()) {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
////        ProgressMonitorInputStream in = new ProgressMonitorInputStream(
////                form,
////                "Testing progess on InputStream...",
////                StaticResourceManager.getResourceStream("menus.xml"));
////        ProgressMonitor prog = in.getProgressMonitor();
////        prog.setMillisToDecideToPopup(10);
////        prog.setMillisToPopup(10);
////
////        System.out.println("Progress monitor started.");
////
////
////
////        java.awt.EventQueue.invokeLater(new Runnable() {
////            public void run() {
////                form.setVisible(false);
////            }
////        });
//
//    }

}
