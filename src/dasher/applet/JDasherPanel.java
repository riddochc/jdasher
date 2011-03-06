package dasher.applet;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JPanel;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class JDasherPanel extends JPanel {

    protected Timer frameTimer;
    protected TimerTask frameTask;
//    protected JDasherScreen screen;
    // <editor-fold defaultstate="collapsed" desc="screen prop">
    protected JDasherScreen screen;

    /**
     * Get the value of screen
     *
     * @return the value of screen
     */
    public JDasherScreen getScreen() {
        return screen;
    }// </editor-fold>
    protected boolean isActive = true;
    protected Image notActiveImage;
    protected JDasherThread worker;

    protected JDasher dasher;

    public JDasherPanel(JDasherScreen newScreen, final JDasher newDasher, int width, int height) {
        this.screen = newScreen;
        this.dasher = newDasher;
        this.worker = new JDasherThread(newScreen, dasher, width, height);
//        addMouseMotionListener(dasher.getMouseInput());

        /**
         * Forward resize events to the screen and worker.
         */
        addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int height = getHeight();
                screen.setSize(width, height);
                worker.setSize(width, height);
            }
        });

        frameTimer = new Timer();
        frameTask = new FrameTask(this);

        worker.start();
    }

    public JDasherPanel(JDasher dasher, int width, int height) {
        this(new JDasherScreen(dasher, width, height),
                dasher, width, height);
    }

    public JDasherPanel(JDasher nDasher) {
        this(nDasher, 200, 200);
        // TODO: if we are going to have additional inputs, we will have to change this
        addMouseMotionListener(dasher.getMouseInput());
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    dasher.KeyDown(System.currentTimeMillis(), 100);
                }
            }
        });
    }

    /**
     * This should never be called except from the NetBeans GUI Builder.
     */
    public JDasherPanel() {
        isActive = false;
//        try {
//            notActiveImage = ImageIO.read(dasher.getResourceStream("JDasher.png"));
//        } catch (IOException ex) {
//            Logger.getLogger(JDasherPanel.class.getName()).log(Level.SEVERE, "Cannot get dasher icon.", ex);
//        }
    }

    @Override
    protected void paintComponent(Graphics g) {
//        screen.setSize(this.getWidth(), this.getHeight());
//        worker.setSize(this.getWidth(), this.getHeight());
        if (isActive) {
            Image buffer = worker.getCurrentFrontbuffer();
            g.drawImage(buffer, 0, 0, null);
        } else {
//            g.drawImage(notActiveImage, 0, 0, null);
        }

    }

    public void addTasklet(DasherTasklet t) {
        worker.addTasklet(t);
    }

    public void setPaused(boolean paused) {
        if (paused) {
            frameTask.cancel();
        } else {
            frameTask = new FrameTask(this);
            frameTimer.schedule(frameTask, 0, 20);
        }
    }
}

class FrameTask extends TimerTask {

    Component comp;

    public FrameTask(Component newComp) {
        comp = newComp;
    }

    public void run() {
        comp.repaint();
    }
}

