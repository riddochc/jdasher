/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DasherDocumentFrame.java
 *
 * Created on Oct 21, 2009, 1:07:46 PM
 */

package dasher.ui;

import dasher.applet.JDasherHost;
import dasher.applet.JDasherPanel;
import dasher.core.CDasherInterfaceBase;
import dasher.events.CEvent;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author joshua
 */
public class DasherDocumentFrame extends javax.swing.JInternalFrame implements JDasherHost {

    /** Creates new form DasherDocumentFrame */
    public DasherDocumentFrame() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane = new javax.swing.JSplitPane();
        jScrollPane = new javax.swing.JScrollPane();
        jDasherEdit = new dasher.applet.JDasherEdit(dasher);

        setBorder(null);
        setPreferredSize(new java.awt.Dimension(800, 600));

        jSplitPane.setBorder(null);
        jSplitPane.setDividerLocation(450);
        jSplitPane.setDividerSize(3);
        jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane.setResizeWeight(1.0);
        jSplitPane.setPreferredSize(new java.awt.Dimension(800, 600));

        jDasherEdit.setColumns(20);
        jDasherEdit.setLineWrap(true);
        jDasherEdit.setRows(5);
        jScrollPane.setViewportView(jDasherEdit);

        jSplitPane.setBottomComponent(jScrollPane);

        getContentPane().add(jSplitPane, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // <editor-fold defaultstate="collapsed" desc="Variables and Properties">

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private dasher.applet.JDasherEdit jDasherEdit;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JSplitPane jSplitPane;
    // End of variables declaration//GEN-END:variables

    protected CDasherInterfaceBase dasher;

    /**
     * Get the value of dasher
     *
     * @return the value of dasher
     */
    public CDasherInterfaceBase getDasher() {
        return dasher;
    }

    protected JDasherPanel dPanel;

    /**
     * Get the value of dPanel
     *
     * @return the value of dPanel
     */
    public JDasherPanel getDPanel() {
        return dPanel;
    }



    // </editor-fold>

    public void Redraw() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setMouseInput(MouseMotionListener e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void handleEvent(CEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
