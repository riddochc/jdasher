package dasher.ui;

import com.apple.eawt.*;
import com.apple.eawt.AppEvent.AboutEvent;
import com.apple.eawt.AppEvent.OpenFilesEvent;
import com.apple.eawt.AppEvent.PreferencesEvent;
import com.apple.eawt.AppEvent.QuitEvent;
import com.apple.eawt.ApplicationAdapter;
import com.apple.eawt.ApplicationEvent;
import com.apple.eawt.ApplicationListener;
import dasher.resources.StaticResourceManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.UIManager;

/**
 * This will hopefully be a wrapper to nicely integrate into MacOSX.
 *
 * @author joshua
 */
public class JAppleDasherForm extends JDasherForm {

    /**
     * The Logger for this class.
     */
    private static final Logger log = Logger.getLogger(JAppleDasherForm.class.getName());

    private Application app;
    private DasherApplicationListener appListener;

    public JAppleDasherForm() {
        super();

        app = Application.getApplication();
        appListener = new DasherApplicationListener();
        app.setAboutHandler(appListener);
        app.setQuitHandler(appListener);
        app.setPreferencesHandler(appListener);
        try {
            app.setDockIconImage(ImageIO.read(StaticResourceManager.getResourceStream("JDasher.png")));
        } catch (IOException ex) {
            log.fine("Could not load JDasher icon.");
        }
        
    }

    public static void initForApple() {
//        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "JDasher");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            log.fine("Could not set LAF to system LAF.  Oh well.");
        }
    }

    protected class DasherApplicationListener implements AboutHandler, QuitHandler, OpenFilesHandler, PreferencesHandler {

//        public void handleOpenFile(ApplicationEvent event) {
//            try {
//                applet.openFile(event.getFilename());
//                event.setHandled(true);
//            } catch (IOException e) {
//                event.setHandled(false);
//                log.log(Level.WARNING, "Could not open file %s.", event.getFilename());
//            }
//        }

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
