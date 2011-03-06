
/*
This file is part of JDasher.

JDasher is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

JDasher is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with JDasher; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

Copyright (C) 2006      Christopher Smowton <cs448@cam.ac.uk>

JDasher is a port derived from the Dasher project; for information on
the project see www.dasher.org.uk; for information on JDasher itself
and related projects see www.smowton.net/chris

 */
package dasher.applet;

import dasher.utils.ExtensionFileFilter;
import java.awt.Font;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Timer;

import java.awt.datatransfer.Clipboard;

import javax.swing.*;

import dasher.settings.Ebp_parameters;
import dasher.settings.Elp_parameters;
import dasher.settings.Esp_parameters;
import dasher.settings.StaticSettingsManager;
import dasher.resources.StaticResourceManager;
import dasher.ui.PreferencesForm;
import dasher.utils.MenuReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.SAXException;

/**
 * Applet containing a JDasherScreen panel, a JDasherEdit TextBox,
 * and a set of menus to set relevant parameters.
 *
 */
public class JDasherApplet extends JApplet implements MouseListener, KeyListener, JDasherMenuBarListener, JDasherHost, dasher.utils.FontListener {
    private static final long serialVersionUID = 1L;
    /**
     * The Logger for this class.
     */
    private static Logger log = Logger.getLogger(JDasherApplet.class.getName());

    /**
     * Instance of Dasher which does the work
     */
    public JDasher Dasher;

    public JDasher getDasher() {
        return Dasher;
    }

    /**
     * Screen object to be used for drawing
     */
    private JDasherScreen Screen;
    /**
     * Panel object which will reflect those drawings on the GUI
     */
    private JDasherPanel panel;
    /**
     * Our menu bar
     */
    private JDasherMenuBar MenuBar;
    /**
     * Overlay to display when Dasher is locked
     */
    private ScreenOverlay ProgressMeter;
    /**
     * Edit box in which typed text appears
     */
    public JDasherEdit EditBox;
    /**
     * File chooser to use
     */
    private JFileChooser fileChooser;
    private File file;
    protected boolean newFile = true;

    // TODO: Find some way of making fileNotSaved reliable.
    protected boolean fileNotSaved = false;

    // Create random number generator.
    Random random = new Random();

    protected PreferencesForm prefDialog = null;

    /**
     * Reference to the JFrame this is in, if any.
     */
    private JFrame mainFrame;
    /**
     * Clipboard object
     */
    public Clipboard m_Clipboard;
    /**
     * Scheduling agent used to cue new frames
     */
    private Timer taskScheduler;

    /**
     * Instantiates Dasher, gets a handle to the system clipboard
     * if possible, calls constructGUIPanel to produce our GUI,
     * and informs Dasher of the Screen Panel created by this method
     * using the ChangeScreen method.
     * <p>
     * Finally, we call constructMenus to produce our menu bar.
     */
    @Override
    public void init() {

        Dasher = new JDasher(this);

        /* Instantiate Dasher. The argument is simply so it has a back-pointer
         * to its host applet for the purpose of adding listeners where this is
         * necessary.
         */

        /* Try to create a clipboard */

        try {
            m_Clipboard = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard();
        } catch (Exception e) {
            log.fine("Could not get the system clipboard.");
            m_Clipboard = new java.awt.datatransfer.Clipboard("Private clipboard");
        }

        /* Create the file chooser */
        fileChooser = new JFileChooser(Dasher.getSettingsManager().getSetting(Esp_parameters.SP_USER_LOC));
        fileChooser.addChoosableFileFilter(new ExtensionFileFilter("txt", "Text"));

        ProgressMeter = new ScreenOverlay();

        this.add(constructGUIPanel(this.getSize()));

        menuNew();

        Dasher.Realize();
        Dasher.ChangeScreen(Screen);
        setJMenuBar(MenuBar);
        /* Inform Dasher that we have created a Screen and it can now display.
         * This will cause the View to instantiate too, due to the ChangeView
         * method (which in turn fires in response to a parameter-change event!)
         */

        //TimerTask doFrame = new DoFrameTask(this);
        //taskScheduler = new Timer();
        //taskScheduler.scheduleAtFixedRate(doFrame, 0, 20);

        /* Simple threaded scheduling. The C++ version calls NewFrame directly
         * every 20 ms as part of a message-processing loop. This is not entirely
         * suitable for Java as we need the Applet to be idle on some occasions
         * so that we can process the MouseEvents detailing where the pointer
         * is at present. This is actually much less efficient, but I don't think
         * we can ask "where is the pointer now?" in Java, as in C++;
         * we must use a MouseMotionListener.
         * TODO: Make it track to mouse more efficiently
         */

        // TODO: Make handling mouse events on JDasherPanel it's busuiness
        this.addMouseListener(this);
        this.addKeyListener(this);

        /* The applet itself will handle MouseEvents relating to clicks; these will
         * be fed into the interface via KeyDown, which also accounts for mouse-clicks.
         * Further it handles actual keyboard events. The code for this may be moved
         * out to some dedicated event-handler code in the future, but there is no
         * particular reason to do so other than for tidiness' sake.
         */

        /* Next, make our menus */

        // (Parameter-holding object, some action-listener, some item-listener)

        MenuBar = constructMenus();
        try {
            JMenuBar bar = (JMenuBar) (new MenuReader()).parse(StaticResourceManager.getResourceStream("menus.xml"), (Object) this, false);
            setJMenuBar(bar);
        } catch (IOException ex) {
            setJMenuBar(MenuBar);
            log.log(Level.SEVERE, "Failed to load menu bar.  Loading default.", ex);
        } catch (SAXException ex) {
            setJMenuBar(MenuBar);
            log.log(Level.SEVERE, "Failed to load menu bar.  Loading default.", ex);
        }

    }

    /**
     * Sets up a Panel containing the entire Applet's GUI, and
     * returns the Panel.
     * <p>
     * The Panel contains a JDasherScreen (which is stored in the
     * Screen variable) and a JDasherEdit (stored in EditBox).
     *
     * @param size Size of the panel to be created
     * @return Created Panel
     */
    public JPanel constructGUIPanel(java.awt.Dimension size) {

        JPanel GUIPanel = new JPanel();

        GUIPanel.setSize(size);

        GUIPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GUIPanel.setLayout(new javax.swing.BoxLayout(GUIPanel, javax.swing.BoxLayout.Y_AXIS));

        java.awt.Dimension EditSize = new java.awt.Dimension(GUIPanel.getWidth() - 20, GUIPanel.getHeight() / 10);

        Screen = new JDasherScreen(Dasher, GUIPanel.getWidth() - 20, (int) (GUIPanel.getHeight() * 0.9) - 40);
        panel = new JDasherPanel(Screen, Dasher, GUIPanel.getWidth() - 20, (int) (GUIPanel.getHeight() * 0.9) - 40);

        GUIPanel.add(panel);

        /* The Screen is a specialisation of JPanel. All drawing is done by
         * its paintComponent method. This is different to the original Dasher,
         * which painted in a bottom-up method, with Dasher causing its Screen
         * obejct to me modified; here the Screen object is very much in charge,
         * and Dasher is invoked to determine its appearance.
         */

        EditBox = new JDasherEdit(3, 80, Dasher);

        EditBox.setLineWrap(true);

        JScrollPane EditScroll = new JScrollPane(EditBox);

        EditScroll.setPreferredSize(EditSize);
        EditScroll.setMaximumSize(EditSize);
        EditScroll.setMinimumSize(EditSize);
        EditScroll.setSize(EditSize);

        /* The EditBox is a specialisation of a JTextPane, but a much lighter
         * one than the Screen. */

        GUIPanel.add(EditScroll);

        return GUIPanel;

    }

    /**
     * Produces our menu bar (a JDasherMenuBar) and returns it.
     * <p>
     * After instantiating, its options are set from Dasher's
     * current settings. For example, we cause the current Alphabet
     * to be set as 'selected' in the menu bar.
     *
     * @return newMenuBar to be added to our Applet
     */
    public JDasherMenuBar constructMenus() {

        JDasherMenuBar newMenuBar = new JDasherMenuBar(this);

        ArrayList<String> colours = new ArrayList<String>();
        Dasher.GetColours(colours);
        newMenuBar.setColours(colours, Dasher.getSetting(Esp_parameters.SP_COLOUR_ID));

        ArrayList<String> alphs = new ArrayList<String>();
        Dasher.GetAlphabets(alphs);
        newMenuBar.setAlphabets(alphs, Dasher.getSetting(Esp_parameters.SP_ALPHABET_ID));

        newMenuBar.setSelectedFontSize((int) Dasher.getSetting(Elp_parameters.LP_DASHER_FONTSIZE));
        newMenuBar.setInputFilter(Dasher.getSetting(Esp_parameters.SP_INPUT_FILTER));
        newMenuBar.setMouseLine(Dasher.getSetting(Ebp_parameters.BP_DRAW_MOUSE_LINE));
        newMenuBar.setStartMouse(Dasher.getSetting(Ebp_parameters.BP_START_MOUSE));
        newMenuBar.setStartSpace(Dasher.getSetting(Ebp_parameters.BP_START_SPACE));
        newMenuBar.setSelectedLM((int) Dasher.getSetting(Elp_parameters.LP_LANGUAGE_MODEL_ID));
        newMenuBar.setSpeedAuto(Dasher.getSetting(Ebp_parameters.BP_AUTO_SPEEDCONTROL));
        newMenuBar.setLangModelLearns(Dasher.getSetting(Ebp_parameters.BP_LM_ADAPTIVE));

        m_Clipboard.addFlavorListener(newMenuBar);

        return newMenuBar;

    }



    /**
     * Setter for mainFrame.
     *
     * @param newMainFrame
     * @see #mainFrame
     */
    public void setMainFrame(JFrame newMainFrame) {
        mainFrame = newMainFrame;
    }

    /**
     * The Applet responds to the following parameter events:
     * <p>
     * <i>BP_DASHER_PAUSED</i>: We start/stop requesting frames
     * at a regular interval depending on whether Dasher is
     * currently paused.
     * <p>
     * <i>LP_LANGUAGE_MODEL_ID</i>: Updates our newMenuBar's currently
     * selected language model to reflect that which has been
     * chosen.
     * <p>
     * <i>SP_COLOUR_ID</i>: Updates our newMenuBar's currently selected
     * colour scheme to reflect that which has been chosen. Usually
     * this occurs in response to an alphabet specifying its own
     * colour scheme.
     * <p>
     * The Applet also responds to LockEvents by showing our ScreenOverlay
     * when locked and updating its progress bar, and MessageEvents
     * by showing a message dialog.
     * <p>
     * Finally we pass the event on to the EditBox, in case some
     * handling is required there as well.
     * <p>
     * TODO: Revamp the Dasher event system
     */
    public void handleEvent(dasher.events.CEvent event) {

        if (event.m_iEventType == 1) { // Parameter change notification

            dasher.events.CParameterNotificationEvent evt = (dasher.events.CParameterNotificationEvent) event;

            if (evt.m_iParameter == dasher.settings.Ebp_parameters.BP_DASHER_PAUSED) {
                panel.setPaused(Dasher.getSetting(dasher.settings.Ebp_parameters.BP_DASHER_PAUSED));
//                if (Dasher.getSetting(dasher.core.Ebp_parameters.BP_DASHER_PAUSED)) {
//                    if (taskScheduler != null)
//                        taskScheduler.cancel();
//                    taskScheduler = null;
//                } else {
//                    TimerTask doFrame = new DoFrameTask(this);
//                    taskScheduler = new Timer();
//                    taskScheduler.scheduleAtFixedRate(doFrame, 0, 20);
//                }
            } else if (evt.m_iParameter == dasher.settings.Elp_parameters.LP_LANGUAGE_MODEL_ID) {
                if (MenuBar != null) {
                    MenuBar.setSelectedLM((int) Dasher.getSetting(dasher.settings.Elp_parameters.LP_LANGUAGE_MODEL_ID));
                }
            } else if (evt.m_iParameter == dasher.settings.Esp_parameters.SP_COLOUR_ID) {
                if (MenuBar != null) {
                    MenuBar.setSelectedColour(Dasher.getSetting(dasher.settings.Esp_parameters.SP_COLOUR_ID));
                }
            }

        } else if (event.m_iEventType == 7) { // Lock event (training progress report)
            dasher.events.CLockEvent evt = (dasher.events.CLockEvent) event;

            if (evt.m_bLock) {
                ProgressMeter.setVisible(true);
                ProgressMeter.setProgressBarVisible(true);

                try {
                    java.awt.Point myloc = this.getLocationOnScreen();
                    ProgressMeter.setLocation(((myloc.x + this.getWidth()) / 2) - 100, ((myloc.y + this.getHeight()) / 2) - 50);
                } catch (Exception e) {
                    // ignore; this means we're not visible.
                }

                ProgressMeter.setText(evt.m_strMessage);

                ProgressMeter.setProgress(evt.m_iPercent, 100);
            } else {
                ProgressMeter.setVisible(false);
            }


        } else if (event.m_iEventType == 8) { // Requested message display

            dasher.events.CMessageEvent evt = (dasher.events.CMessageEvent) event;

            JOptionPane.showMessageDialog(this, evt.m_strMessage, "JDasher", getMessageType(evt.m_iType));

        }
        if (EditBox != null) {
            EditBox.handleEvent(event);
        }

    }

    /**
     * Sets the file that we are working with.
     * Also sets the mainFrame's title.  I know,
     * this is a horrible way to do it.
     *
     * @param newFile
     */
    protected void setFile(File newFile) {
        file = newFile;
        fileChooser.setSelectedFile(file);
        StaticSettingsManager.S.Filename.set(newFile.toString());
        Dasher.setWorkingFile(file);
//        if (mainFrame != null) {
//            mainFrame.setTitle(String.format("JDasher (%s)", file));
//        }
    }

    /**
     * Converts Dasher's message dialog types into JOptionPane's
     * constants with the same meaning. For example, type 0 becomes
     * JOptionPane.INFORMATION_MESSAGE.
     * <p>
     * If asked to convert an invalid message type, -1 is returned.
     *
     * @param type Type to convert.
     * @return JOptionPane equivalent constant
     */
    private int getMessageType(int type) {

        // Convert internal message types to those used by JOptionPane.

        if (type == 0) {
            return JOptionPane.INFORMATION_MESSAGE;
        }
        if (type == 1) {
            return JOptionPane.WARNING_MESSAGE;
        }
        if (type == 2) {
            return JOptionPane.ERROR_MESSAGE;
        }

        return -1;
    }

    public void infoMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    /**
     * MouseClicks are responded to by feeding Dasher a KeyDown
     * event. See CDasherInterfaceBase.KeyDown.
     */
    public void mouseClicked(MouseEvent e) {

        if (e.getButton() == MouseEvent.BUTTON1) {
            Dasher.KeyDown(System.currentTimeMillis(), 100);
        }

    }

    /**
     * Ignored
     */
    public void mouseEntered(MouseEvent arg0) {
    }

    /**
     * Ignored
     */
    public void mouseExited(MouseEvent arg0) {
    }

    /**
     * Ignored
     */
    public void mousePressed(MouseEvent arg0) {
    }

    /**
     * Ignored
     */
    public void mouseReleased(MouseEvent arg0) {
    }

    /**
     * We respond to the following key presses:
     * <p>
     * CTRL: Set speed boost factor to 175<br>
     * SHIFT: Set speed boost factor to 25.
     * </p>
     */
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            panel.addTasklet(new DasherTasklet() {

                public void run() {
                    Dasher.SetLongParameter(Elp_parameters.LP_BOOSTFACTOR, 175);
                }
            });

        }

        // Speed boost for pressing CTRL. Should this be in the interface?

        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            panel.addTasklet(new DasherTasklet() {

                public void run() {
                    Dasher.SetLongParameter(Elp_parameters.LP_BOOSTFACTOR, 25);
                }
            });
        }

        // Speed reduced when SHIFT pressed. As above?

    }

    /**
     * Upon releasing the space bar, we signal Dasher a KeyDown
     * event with a key of zero.
     * <p>
     * If either CTRL or SHIFT are released, the speed boost
     * constant is reset to 100, 175 or 25, dependent on which
     * keys are still down.
     */
    public void keyReleased(KeyEvent e) {

        /* Dasher will start when SPACE is pressed. */

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            Dasher.KeyDown(System.currentTimeMillis(), 0);
        }


        /* This completes the boost-key implementation by considering
         * whether the other boost key is currently pressed when one
         * is released.
         */

        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            if (e.isShiftDown()) {
                panel.addTasklet(new DasherTasklet() {

                    public void run() {
                        Dasher.SetLongParameter(Elp_parameters.LP_BOOSTFACTOR, 25);
                    }
                });
            } else {
                panel.addTasklet(new DasherTasklet() {

                    public void run() {
                        Dasher.SetLongParameter(Elp_parameters.LP_BOOSTFACTOR, 100);
                    }
                });
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            if (e.isControlDown()) {
                panel.addTasklet(new DasherTasklet() {

                    public void run() {
                        Dasher.SetLongParameter(Elp_parameters.LP_BOOSTFACTOR, 175);
                    }
                });
            } else {
                panel.addTasklet(new DasherTasklet() {

                    public void run() {
                        Dasher.SetLongParameter(Elp_parameters.LP_BOOSTFACTOR, 100);
                    }
                });
            }
        }
    }

    /**
     * Ignored
     */
    public void keyTyped(KeyEvent arg0) {
    }

    /**
     * Cancels our new frame scheduler, and calls Dasher's
     * DestroyInterface method to give it an opportunity to
     * clean up if necessary.
     * <p>
     * Ultimately any neglected cleaning is likely not to
     * cause a problem, as we are about to stop the application.
     */
    @Override
    public void stop() {
        if (taskScheduler != null) {
            taskScheduler.cancel();
        }
        panel.setPaused(true);
        Dasher.DestroyInterface();
    }

    /**
     * Calls the applet's repaint method to trigger a redraw
     * of Dasher.
     * <p>
     * This method is called by Dasher when it wishes to be redrawn;
     * the process has to be started from the top because it is
     * tied in with Swing's painting architecture.
     */
    public void Redraw() {
        repaint();
    }

    /**
     * Adds a MouseMotionListener; requested by Dasher in order
     * to hook its JMouseInput object up to hear mouse events.
     */
    public void setMouseInput(MouseMotionListener e) {
//        if (panel != null)
//            panel.addMouseMotionListener(e);
        addMouseMotionListener(e);
    }

    /**
     * Sets our edit box font
     *
     * @param f New font
     */
    public void setNewFont(Font f) {

        EditBox.setFont(f);

    }

    /**
     * Copies the current edit box selection to the clipboard if
     * possible.
     */
    public void menuCopy() {
        try {
            m_Clipboard.setContents(new java.awt.datatransfer.StringSelection(EditBox.getSelectedText()), null);
        } catch (Exception ex) {
            log.log(Level.WARNING, "Copy to clipboard failed.", ex);
        }
    }

    /**
     * Cuts the current edit box selection to the clipboard if
     * possible.
     */
    public void menuCut() {
        try {
            m_Clipboard.setContents(new java.awt.datatransfer.StringSelection(EditBox.getSelectedText()), null);
            EditBox.replaceSelection("");
        } catch (Exception ex) {
            log.log(Level.WARNING, "Cut to clipboard failed.", ex);
        }
    }

    /**
     * Quits Dasher
     */
    public void menuExit() {
        if (fileNotSaved) {
            switch (JOptionPane.showConfirmDialog(null,
                    "The file is not saved.  Do you want to save it before exiting?",
                    "File Not Saved",
                    JOptionPane.YES_NO_CANCEL_OPTION)) {
                case JOptionPane.YES_OPTION:
                    menuSave();
                    break;
                case JOptionPane.NO_OPTION:
                    break;
                default:
                    return;
            }
        }
        log.fine("Exiting JDasher.");
        System.exit(0); // Should make this tidier...
    }

    /**
     * Displays an About dialog.
     */
    public void menuHelpAbout() {
        JOptionPane.showMessageDialog(this, "JDasher\nA Java adaptation of Dasher",
                "About JDasher", JOptionPane.INFORMATION_MESSAGE);
    }
    public void menuAbout() {
        menuHelpAbout();
    }

    /**
     * Blanks the EditBox and invalidates our current context.
     */
    public void menuNew() {
        newFile = true;
        setFile(new File(Dasher.getSettingsManager().getSetting(Esp_parameters.SP_USER_LOC),
                "dasher-" + Long.toHexString(random.nextLong()) + ".txt"));
        this.EditBox.setText("");
        this.Dasher.InvalidateContext(true);
    }

    /**
     * Saves the current text to the current file.  This may just default to
     * the file set in menuNew.
     */
    public void menuSave() {
        if (newFile) {
            menuSaveAs();
            return;
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(EditBox.getText());
            writer.close();
        } catch (IOException e) {
            log.log(Level.WARNING, "Save failed.", e);
        }
    }

    /**
     * Sets the current file to a file selected by the user and call menuSave.
     */
    public void menuSaveAs() {
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            setFile(fileChooser.getSelectedFile());
            newFile = false;
            menuSave();
        }
    }

    /**
     * Has the user select a file and open it.
     */
    public void menuOpen() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                openFile(fileChooser.getSelectedFile());
            } catch (IOException e) {
                log.log(Level.WARNING, "Could not open file %s.", file);
            }
        }
    }

    public void openFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuffer sbuf = new StringBuffer();
        char[] cbuf = new char[100];
        int read;
        do {
            read = reader.read(cbuf);
            if (read != -1) {
                sbuf.append(cbuf, 0, read);
            }
        } while (read != -1);
        reader.close();
        EditBox.setText(sbuf.toString());
        Dasher.InvalidateContext(true);
        newFile = fileNotSaved = false;
        setFile(file);
    }

    public void openFile(String file) throws IOException {
        openFile(new File(file));
    }

    /**
     * Has the user select a file to train the language model with.
     * TODO: Why dosn't menuTrain work?
     */
    public void menuTrain() {
        JOptionPane.showMessageDialog(rootPane, "Training from external file is expirimental.");
        if (fileChooser.showDialog(this, "Train") == JFileChooser.APPROVE_OPTION) {
            try {
                File train = fileChooser.getSelectedFile();
                FileInputStream in = new FileInputStream(train);
                BufferedInputStream buffin = new BufferedInputStream(in);
                Dasher.TrainStream(buffin, train.length(), 0, true);
            } catch (IOException e) {
                log.log(Level.WARNING, "Training failed.", e);
            }
        }
    }

    /**
     * Attempts to paste from the clipboard, overwriting our current
     * EditBox selection if there is one.
     */
    public void menuPaste() {
        try {
            java.awt.datatransfer.Transferable clipContents = m_Clipboard.getContents(null);
            String temp = (String) clipContents.getTransferData(java.awt.datatransfer.DataFlavor.stringFlavor);
            EditBox.replaceSelection(temp);
        } catch (Exception ex) {
            log.log(Level.WARNING, "Paste from clipboard failed.", ex);
        }
    }

    public void menuSelectAll() {
        EditBox.setSelectionStart(0);
        EditBox.setSelectionEnd(EditBox.getText().length());
    }

    public void menuCopyAll() {
        try {
            m_Clipboard.setContents(new java.awt.datatransfer.StringSelection(EditBox.getText()), null);
        } catch (Exception ex) {
            log.log(Level.WARNING, "Copy to clipboard failed.", ex);
        }
    }

    public void menuSpellCheck() {
        EditBox.spellCheck();
    }

    public void menuPreferences() {
        if (prefDialog == null) {
            prefDialog = new PreferencesForm(Dasher, EditBox);
        }
        prefDialog.resetPreferencesForm();
        prefDialog.setVisible(true);
        prefDialog.toFront();
    }

    /**
     * Opens the Select Font dialog in order to choose a new
     * EditBox font
     */
    public void menuSelFont() {
        new dasher.utils.JFontDialog(this, EditBox.getFont()).setVisible(true);
    }

    /**
     * Sets Dasher's LP_MAX_BITRATE parameter to a given value
     *
     * @param speed New LP_MAX_BITRATE
     */
    public void menuSetDasherSpeed(int speed) {

        final int f_speed = speed;

        panel.addTasklet(new DasherTasklet() {

            public void run() {
                Dasher.SetLongParameter(Elp_parameters.LP_MAX_BITRATE, f_speed);
            }
        });

    }

    /**
     * Sets the Dasher font size (LP_DASHER_FONTSIZE) to a given value.
     *
     * @param size New size; either 1, 2 or 4.
     */
    public void menuSetFontSize(int size) {

        final int f_size = size;

        panel.addTasklet(new DasherTasklet() {

            public void run() {
                Dasher.SetLongParameter(Elp_parameters.LP_DASHER_FONTSIZE, f_size);
            }
        });

    }

    /**
     * Sets the input filter (SP_INPUT_FILTER) to a given String.
     *
     * @param filter Name of new input filter
     */
    public void menuSetInputFilter(String filter) {

        final String f_filter = filter;

        panel.addTasklet(new DasherTasklet() {

            public void run() {
                Dasher.SetStringParameter(Esp_parameters.SP_INPUT_FILTER, f_filter);
            }
        });

    }

    /**
     * Sets the currently selected language model to a given ID.
     * <p>
     * If an ID of 5 (corresponding to the RemotePPM language model)
     * is selected, we first prompt the user to name a host and set
     * the SP_LM_HOST parameter to their chosen value.
     * <p>
     * In any case, we end up setting LP_LANGUAGE_MODEL_ID.
     *
     * @param LMID New LM ID
     */
    public void menuSetLMID(final int LMID) {
        if (LMID == 5) { // Network PPM
            final String hostName = JOptionPane.showInputDialog("Please enter the language model host address", Dasher.getSetting(Esp_parameters.SP_LM_HOST));

            if (hostName != null) {

                panel.addTasklet(new DasherTasklet() {

                    public void run() {
                        Dasher.SetBoolParameter(Ebp_parameters.BP_CONNECT_LOCK, true);
                        // Set the connect lock so the user can't move whilst establishing connection.

                        Dasher.SetStringParameter(Esp_parameters.SP_LM_HOST, hostName);

                        Dasher.SetLongParameter(Elp_parameters.LP_LANGUAGE_MODEL_ID, 5);
                    }
                });


            }
        } else {

            panel.addTasklet(new DasherTasklet() {

                public void run() {
                    Dasher.SetLongParameter(Elp_parameters.LP_LANGUAGE_MODEL_ID, LMID);
                }
            });

        }

    }

    /**
     * Sets BP_LM_ADAPTIVE
     *
     * @param enabled New value
     */
    public void menuSetLMLearn(final boolean enabled) {

        panel.addTasklet(new DasherTasklet() {

            public void run() {
                Dasher.SetBoolParameter(Ebp_parameters.BP_LM_ADAPTIVE, enabled);
            }
        });

    }

    /**
     * Sets BP_DRAW_MOUSE_LINE
     *
     * @param enabled New value
     */
    public void menuSetMouseLine(final boolean enabled) {

        panel.addTasklet(new DasherTasklet() {

            public void run() {
                Dasher.SetBoolParameter(Ebp_parameters.BP_DRAW_MOUSE_LINE, enabled);
            }
        });

    }

    /**
     * Sets BP_AUTO_SPEEDCONTROL
     *
     * @param enabled New value
     */
    public void menuSetSpeedAuto(final boolean enabled) {

        panel.addTasklet(new DasherTasklet() {

            public void run() {
                Dasher.SetBoolParameter(Ebp_parameters.BP_AUTO_SPEEDCONTROL, enabled);
            }
        });

    }

    /**
     * Sets BP_START_MOUSE
     *
     * @param enabled New value
     */
    public void menuSetStartMouse(final boolean enabled) {
        panel.addTasklet(new DasherTasklet() {

            public void run() {
                Dasher.SetBoolParameter(Ebp_parameters.BP_START_MOUSE, enabled);
            }
        });

    }

    /**
     * Sets BP_START_SPACE
     *
     * @param enabled New value
     */
    public void menuSetStartSpace(final boolean enabled) {
        panel.addTasklet(new DasherTasklet() {

            public void run() {
                Dasher.SetBoolParameter(Ebp_parameters.BP_START_SPACE, enabled);
            }
        });
    }

    /**
     * Sets the currently selected alphabet
     *
     * @param newalph Alphabet to select
     */
    public void menuSetAlph(final String newalph) {

        ArrayList<String> Alphs = new ArrayList<String>();
        Dasher.GetAlphabets(Alphs);

        for (String thisAlph : Alphs) {
            if (thisAlph.equals(newalph)) {
                panel.addTasklet(new DasherTasklet() {

                    public void run() {
                        Dasher.SetStringParameter(Esp_parameters.SP_ALPHABET_ID, newalph);
                    }
                });
                return;
            }
        }

    }

    /**
     * Sets the currently selected colourscheme
     *
     * @param newcolours Colour scheme to select
     */
    public void menuSetColours(String newcolours) {

        ArrayList<String> Colours = new ArrayList<String>();
        Dasher.GetColours(Colours);

        for (String thisColour : Colours) {
            if (thisColour.equals(newcolours)) {
                final String col = thisColour;
                panel.addTasklet(new DasherTasklet() {

                    public void run() {
                        Dasher.SetStringParameter(Esp_parameters.SP_COLOUR_ID, col);
                    }
                });
                return;
            }
        }

    }

    /**
     * Checks with the clipboard whether a given data flavour
     * is enabled.
     * <p>
     * Typically this is used to check whether it is sensible
     * to attempt Paste at the moment.
     *
     * @param flavour Flavour to check availability
     * @return True if available at present, false otherwise.
     */
    public boolean isDataFlavorAvailable(java.awt.datatransfer.DataFlavor flavour) {
        return m_Clipboard.isDataFlavorAvailable(flavour);
    }

    /**
     * Retrieves the current EditBox text. This method exists
     * for the purposes of JavaScript calling in to invoke unsafe
     * functions.
     * <p>
     * Thankfully, this doesn't work on FireFox.
     *
     * @return Current EditBox contents
     */
    public String getCurrentEditBoxText() {

        return EditBox.getText();

    }

}
