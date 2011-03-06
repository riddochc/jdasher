/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MenuReaderTestForm.java
 *
 * Created on Sep 21, 2009, 7:21:36 AM
 */
package dasher.utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.JMenuBar;
import javax.swing.MenuElement;
import org.xml.sax.SAXException;

/**
 *
 * @author joshua
 */
public class MenuReaderTestForm extends javax.swing.JFrame {

    /** Creates new form MenuReaderTestForm */
    public MenuReaderTestForm() {
        try {
            try {
                JMenuBar bar = (JMenuBar) (new MenuReader()).parse(getClass().getResourceAsStream("menutest.xml"),
                        (new MenuReaderTest()).getReceiver(), false);
                printMenu(bar, "");
                setJMenuBar(bar);
            } catch (IOException ex) {
                Logger.getLogger(MenuReaderTestForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SAXException ex) {
            Logger.getLogger(MenuReaderTestForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();
    }

    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 400, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(0, 300, Short.MAX_VALUE));

        pack();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new MenuReaderTestForm().setVisible(true);
            }
        });
    }

    private void printMenu(MenuElement elem, String prefix) {
            System.out.format("%s %s %s\n",
                    prefix,
                    elem.getClass().getName(),
                    AbstractButton.class.isInstance(elem) ? ((AbstractButton) elem).getText() : "root");
            String newPrefix = prefix + "--";
            for (MenuElement newElem : elem.getSubElements())
                printMenu(newElem, newPrefix);
    }
}
