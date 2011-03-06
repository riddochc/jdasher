/*
 * JDasherForm.java
 *
 * Created on Aug 22, 2009, 1:59:09 PM
 */
package dasher.ui;

import java.awt.BorderLayout;
import dasher.applet.JDasherApplet;
import dasher.core.DasherDocument;
import dasher.settings.StaticSettingsManager;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

/**
 *
 * @author joshua
 */
public class JDasherForm extends javax.swing.JFrame implements PropertyChangeListener {

    /**
     * The Logger for this class.
     */
    private static final Logger log = Logger.getLogger(JDasherForm.class.getName());
    static {
        log.setLevel(null);
    }
    protected JDasherApplet applet;

    /** Creates new form JDasherForm */
    public JDasherForm() {

        setExtendedState(MAXIMIZED_BOTH);
//        pack();
//        System.out.format("Form bounds: %s\n", getBounds());
//        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//        addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//                // why doesn't this work???
//                Dimension size = getSize();
//                StaticSettingsManager.L.SIZE_X.set(size.width);
//                StaticSettingsManager.L.SIZE_Y.set(size.height);
//            }
//        });
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent event) {
                ((JDasherForm) event.getWindow()).getApplet().menuExit();
            }
        });

//        StaticSettingsManager.S.Filename.addSettingListener(
//            new StaticSettingsManager.SettingListener<String>() {
//                public void settingChanged(StaticSettingsManager.SettingChangedEvent<String> e) {
//                    setTitle("JDasher - " + e.getNewValue());
//                }
//            });

        applet = new JDasherApplet();
        applet.setMainFrame(this);
        getContentPane().add(applet, BorderLayout.CENTER);
        // TODO: figure out how to properly resize
        Insets ins = getInsets();
        int insX = ins.left + ins.right;
        int insY = ins.top + ins.bottom;
        applet.setPreferredSize(new Dimension(StaticSettingsManager.L.SIZE_X.get().intValue() - insX,
                                              StaticSettingsManager.L.SIZE_Y.get().intValue() - insY));

        pack();

//        System.out.format("Form bounds: %s\n", getBounds());

        applet.init();
        applet.getDasher().addPropertyChangeListener("workingFile", this);

    }



    public JDasherApplet getApplet() {
        return applet;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        final JDasherForm form;
        String os = System.getProperty("os.name");

        parseArgs(args);
        
        if (os.equals("Mac OS X")) {
            JAppleDasherForm.initForApple();
            form = new JAppleDasherForm();
        } else {
            form = new JDasherForm();
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                form.setVisible(true);
            }
        });
    }

    protected static void parseArgs(String args[]) {
        try {
            Options opts = new Options();

            opts.addOption("h", "help", false, "Show help");
            OptionBuilder.hasArg();
            OptionBuilder.withArgName("level");
            OptionBuilder.withLongOpt("log");
            OptionBuilder.withDescription("Logging level: "
                    + "SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST");
            OptionBuilder.withType(Level.class);
            opts.addOption(OptionBuilder.create("l"));

            CommandLine cl = (new BasicParser()).parse(opts, args);

            for (Option o : cl.getOptions()) {
                String name = o.getOpt();
                if (name.equals("l")) {
                    String val = o.getValue("INFO").toUpperCase();
                    Level lev = Level.parse(val);
                    log.getParent().setLevel(lev);
                    System.out.format("Setting log level to %s.\n", lev);
                } else if (name.equals("h")) {
                    System.out.println("h");
                    (new HelpFormatter()).printHelp("JDasher", opts);
                    System.exit(0);
                }
            }
        } catch (Exception e) {
            log.throwing(JDasherForm.class.toString(), "parseArgs", e);
        }
    }

    public void propertyChange(PropertyChangeEvent pce) {
        if (pce.getPropertyName().equals(DasherDocument.Basic.PROP_WORKINGFILE))
            setTitle("JDasher - " + pce.getNewValue());
    }
}
