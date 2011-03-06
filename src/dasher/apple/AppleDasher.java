package dasher.apple;

import com.apple.eawt.*;
import com.apple.eawt.AppEvent.AboutEvent;
import com.apple.eawt.AppEvent.OpenFilesEvent;
import com.apple.eawt.AppEvent.PreferencesEvent;
import com.apple.eawt.AppEvent.QuitEvent;
import dasher.applet.JDasherApplet;
import dasher.resources.StaticResourceManager;
import dasher.ui.DasherApp;
import dasher.ui.JDasherForm;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.UIManager;

/**
 * This will hopefully be a wrapper to nicely integrate into MacOSX
 *
 * @author joshua
 */
public class AppleDasher extends DasherApp {


    private Application app;
    private DasherAppListener appListener;

    private JDasherForm form;
    private JDasherApplet applet;

    public AppleDasher(String args[]) {
        app = Application.getApplication();

        appListener = new DasherAppListener();
        app.setAboutHandler(appListener);
        app.setQuitHandler(appListener);
        app.setPreferencesHandler(appListener);
        try {
            app.setDockIconImage(ImageIO.read(StaticResourceManager.getResourceStream("JDasher.png")));
        } catch (IOException ex) {
            log.fine("Could not load JDasher icon.");
        }

//        app.addApplicationListener(this);
        app.setEnabledAboutMenu(true);

        form = new JDasherForm();
        applet = form.getApplet();

        java.awt.EventQueue.invokeLater(new Runnable() {
             public void run() {
                form.setVisible(true);
            }
        });
    }



//    public AppleDasherForm() {
//        super();
//
//        app = Application.getApplication();
//        appListener = new DasherApplicationListener();
//
//    }

    public static void initForApple() {
//        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "JDasher");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            log.fine("Could not set LAF to system LAF.  Oh well.");
        }
    }

    private class DasherAppListener implements AboutHandler, QuitHandler, OpenFilesHandler, PreferencesHandler {
        public void handleAbout(AboutEvent ae) {
            applet.menuHelpAbout();
        }

        public void handleQuitRequestWith(QuitEvent qe, QuitResponse qr) {
            applet.menuExit();
        }

        public void openFiles(OpenFilesEvent ofe) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void handlePreferences(PreferencesEvent pe) {
            applet.menuPreferences();
        }
    }

}
