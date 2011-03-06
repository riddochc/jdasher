/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dasher.utils;

import com.swabunga.spell.engine.SpellDictionary;
import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.swing.JTextComponentSpellChecker;
import dasher.settings.StaticSettingsManager;
import dasher.settings.StaticSettingsManager.SettingChangedEvent;
import dasher.resources.StaticResourceManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;
import org.jdesktop.swingworker.SwingWorker;

/**
 *
 * @author joshua
 */
public class SpellCheck {

    /**
     * The Logger for this class.
     */
    private static Logger log = Logger.getLogger(SpellCheck.class.getName());
    static {
        log.setLevel(Level.CONFIG);
    }
    
    protected static SpellDictionary userDictionary, dictionary;
    protected static JTextComponentSpellChecker spellChecker = null;

    static {
        StaticSettingsManager.S.Dictionary.addSettingListener(
                new StaticSettingsManager.SettingListener<String>() {

                    public void settingChanged(SettingChangedEvent<String> e) {
                        spellChecker = null;
                        reloadDictionary();
                    }
                });
    }

    static class SpellCheckTask extends SwingWorker<Boolean,Void> {

        JTextComponent textComponent;

        public SpellCheckTask(JTextComponent text) {
            textComponent = text;
        }

        @Override
        protected Boolean doInBackground() throws Exception {
//            JOptionPane.showMessageDialog(textComponent, "Spell checking is expiramental.");
            checkJTextComponent(textComponent);
            return true;

//            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

    protected static void reloadDictionary() {
        
        SpellDictionary oldUDict = userDictionary;
        SpellDictionary oldDict = dictionary;

        // Setup the user dictionary
        File userDictFile = new File(StaticSettingsManager.Files.UserLoc.get(), StaticSettingsManager.Files.UserDictionary.get().toString());
        log.log(Level.CONFIG, "User dict file: " + userDictFile);
        try {
            userDictionary = new SpellDictionaryHashMap(userDictFile);
        } catch (FileNotFoundException ex) {
            log.log(Level.CONFIG, "Could not find user dictionary.  Trying to create.");
            try {
                userDictFile.getParentFile().mkdirs();
                if (userDictFile.createNewFile()) {
                    try {
                        userDictionary = new SpellDictionaryHashMap(userDictFile);
                    } catch (IOException iOException) {
                        log.log(Level.WARNING, "Could not load newly created user dictionary.", iOException);
                    }
                }
            } catch (IOException ex1) {
                log.log(Level.WARNING, "Could not create user dictionary.", ex1);
            }
        } catch (IOException ex) {
            log.log(Level.WARNING, "Could not load user dictionary.", ex);
        }
        if (userDictionary == null)
            try {
            userDictionary = new SpellDictionaryHashMap();
        } catch (IOException ex) {
            log.log(Level.WARNING, "Could not create even a dummy user dicionary!", ex);
        }

        // Setup the system dictionary
        try {
            String dictFilename = StaticSettingsManager.Files.Dictionary.get().toString(),
                    phonFilename = StaticSettingsManager.Files.Phonetic.get().toString();
            log.log(Level.CONFIG, "Sys dict file: " + dictFilename);
            log.log(Level.CONFIG, "Phon file: " + phonFilename);
            InputStream dictStream = StaticResourceManager.getSystemResourceStream(dictFilename),
                    phonStream = StaticResourceManager.getResourceStream(phonFilename);
            InputStreamReader dictReader = new InputStreamReader(dictStream),
                    phonReader = new InputStreamReader(phonStream);
            dictionary = new SpellDictionaryHashMap(dictReader, phonReader);
//            dictionary = new SpellDictionaryHashMap(dictReader);
            spellChecker = new JTextComponentSpellChecker(dictionary, userDictionary, "Spell Check");
        } catch (IOException ex) {
            userDictionary = oldUDict;
            dictionary = oldDict;
            log.log(Level.WARNING, "Could not get dictionary", ex);
        }
        log.log(Level.CONFIG, "User dict: " + userDictionary);
        log.log(Level.CONFIG, "Sys dict: " + dictionary);
    }

    public static void checkJTextComponent(JTextComponent comp) {
        JOptionPane.showMessageDialog(null, "Spell checking is expirimental");
        if (spellChecker == null) {
            reloadDictionary();
        }
        if (spellChecker == null) {
            log.log(Level.SEVERE, "Could not get dictionary");
        } else {
            spellChecker.spellCheck(comp);
        }
    }

}
