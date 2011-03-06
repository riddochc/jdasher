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

import dasher.events.EventManager;
import dasher.resources.ResourceManager;
import dasher.settings.SettingsManager;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.InputStream;
import java.util.Collection;

import dasher.core.CDasherInterfaceBase;
import dasher.events.CEvent;
import dasher.settings.Esp_parameters;
import dasher.resources.StaticResourceManager;
import dasher.settings.CSettingsStore;
import dasher.utils.FileListReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simple implementation of CDasherInterfaceBase providing minimal
 * facilities to support an Applet version of Dasher.
 */
public class JDasher extends CDasherInterfaceBase {

    /**
     * The Logger for this class.
     */
    private static Logger log = Logger.getLogger(JDasher.class.getName());

    /**
     * SAX Parser Factory
     */
//    protected SAXParserFactory saxfac;

    /**
     * SAX Parser
     */
//    protected SAXParser parser;

    // <editor-fold defaultstate="collapsed" desc="protected File file; (rwb) [workingFile]">
    protected File file;

    /**
     * Get the value of file
     *
     * @return the value of file
     */
    public File getFile() {
        return file;
    }

    /**
     * Set the value of file
     *
     * @param file new value of file
     */
    public void setFile(File file) {
        File oldFile = this.file;
        this.file = file;
        propertyChangeSupport.firePropertyChange(PROP_WORKINGFILE, oldFile, file);
//        getSettingsManager().setString(Esp_parameters.SP_FileLocation, file.getParent()); -- This does not belong in settings
    }

    public void newFile() {
        setFile(new File("dasher-" + Double.toHexString(Math.random()) + ".txt"));
        InvalidateContext(true);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Property change support">
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
    }// </editor-fold>

    
    // <editor-fold defaultstate="collapsed" desc="private JMouseInput mouseInput; (r/o)">
    /**
     * Our mouse input
     */
    private JMouseInput mouseInput;

    public JMouseInput getMouseInput() {
        return mouseInput;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="private JDasherHost host; (r/o)">
    /**
     * Host to notify of events and request redraws.
     */
    private JDasherHost host;

    public JDasherHost getHost() {
        return host;
    }// </editor-fold>

    /**
     * Creates a new JDasher. We call CreateSettingsStore immediately
     * after our super-constructor, create, wrap and register a new
     * JMouseInput, run CreateInput and register our newly created
     * input device wiht our host to hook it up to mouse events.
     *
     * @param host Host to report events and request mouse events
     * and redraws.
     */
    public JDasher(JDasherHost host) {
        super();
		
//        try {
//                saxfac = SAXParserFactory.newInstance();
//                parser = saxfac.newSAXParser();
//        }
//        catch(Exception e) {
//            log.log(Level.SEVERE, "Error creating SAX parser!", e);
//        }

        CreateSettingsStore();


        mouseInput = new JMouseInput(m_EventHandler, m_SettingsStore);
        RegisterFactory(new dasher.core.CWrapperFactory(m_EventHandler, m_SettingsStore, mouseInput));
        CreateInput();

        this.host = host;
        this.host.setMouseInput(mouseInput);

    }

    /**
     * External event handler; simply passes the event to our
     * host, typically a JDasherApplet.
     *
     * @param Event Event to handle
     */
    @Override
    public void ExternalEventHandler(CEvent Event) {

        host.handleEvent(Event);

    }

    /**
     * Attempts to create a JSettings object; if a StoreUnavailableException
     * is produced in the course of this, we fall back and produce a
     * standard CSettingsStore.
     * <p>
     * If a SettingsStore already exists, we ignore the call.
     */
    public void CreateSettingsStore() {

        if (m_SettingsStore == null) {
            try {
                m_SettingsStore = new JSettings(m_EventHandler);
            } catch (StoreUnavailableException e) {
                // We can't use the registry/config file due to security problems.
                m_SettingsStore = new CSettingsStore(m_EventHandler);
                log.info("Can't access the registry.  Settings will not be saved.");
            }
//                m_SettingsStore = SettingsManagerFactory.getSettingsManager(m_EventHandler);
            StaticResourceManager.setSettingsStore(m_SettingsStore);

        }
    }

    /**
     * Attempts to retrieve a stream pointing to a given file in this Applet's
     * JAR file and recalls TrainStream on this stream.
     * <p>
     * If we can't retrieve said stream for some reason, 0 is
     * returned.
     *
     * @param Filename File to retrieve
     * @param iTotalBytes Bytes to read
     * @param iOffset Offset to start reading
     * @return Number of bytes successfully read
     */
    @Override
    public int TrainFile(String Filename, long iTotalBytes, long iOffset) {

        java.io.InputStream in = getResourceStream(Filename);
        if (in == null) {
//          This isn't really nessesary.  Maybe in debugging, else it produces
//            confusing warnings.
//          System.out.printf("Couldn't retrieve resource stream for %s%n", Filename);
            return 0; // Failed to retrieve, possibly due to security.
        }

        return TrainStream(in, iTotalBytes, iOffset);

    }

    /**
     * Attempts to retrieve a ResourceStream for a given file by
     * retrieving it from our JAR file, and returns its available
     * property which should indicate the file size.
     * <p>
     * In the event of failure of any sort, we return zero.
     *
     * @param strFileName File whose size we wish to retrieve
     * @return File size, or 0 on error.
     */
    public int GetFileSize(String strFileName) {
        try {
            java.io.InputStream in = getResourceStream(strFileName);
            return in.available();
        } catch (Exception e) { // Including if 'in' was null (throwing NullPointerException)
            return 0;
        }
    }

    /**
     * Populates a given Collection with a list of available
     * alphabet files.
     * <p>
     * Due to the difficulty in enumerating the contents of a JAR
     * file, at present this is hard coded to report a certain setSetting.
     * <p>
     * Ideally this should be upgraded to report the true list of
     * available files, potentially by reading some master XML file.
     *
     * @param vFileList Collection to fill with a list of available alphabet files.
     */
    public void ScanAlphabetFiles(Collection<String> vFileList) {
        try {
            FileListReader.parse(StaticResourceManager.getSystemResourceStream("alphabets.xml"), vFileList);
            InputStream in = StaticResourceManager.getUserResourceStream("alphabets.xml");
            if (in != null)
                FileListReader.parse(in, vFileList);
        } catch (Exception ex) {
            log.log(Level.WARNING, "There was a problem reading the alphabet list.  Trying to load defaults.", ex);
            vFileList.add("alphabet.english.xml");
            vFileList.add("alphabet.englishC.xml");
            vFileList.add("alphabet.Thai.xml");
        }
    }

    /**
     * Populates a given Collection with a list of available
     * colour files.
     * <p>
     * Due to the difficulty in enumerating the contents of a JAR
     * file, at present this is hard coded to report a certain setSetting.
     * <p>
     * Ideally this should be upgraded to report the true list of
     * available files, potentially by reading some master XML file.
     *
     * @param vFileList Collection to fill with a list of available colour files.
     */
    public void ScanColourFiles(Collection<String> vFileList) {
        try {
            FileListReader.parse(getResourceStream("colours.xml"), vFileList);
        } catch (Exception ex) {
            log.log(Level.WARNING, "There was a problem reading the color list.  Trying to load defaults.", ex);
            vFileList.add("colour.euroasian.xml");
            vFileList.add("colour.rainbow.xml");
            vFileList.add("colour.euroasian-new.xml");
            vFileList.add("colour.thai.xml");
        }
    }

    /**
     * Reads a list of XML files and returns an instance
     * of CAlphIO which knows about the alphabets found in these
     * files.
     * <p>
     * This class does it by the simple expedient of creating a new
     * CAlphIO and passing these files, but some extensions may
     * redirect the request in the case that the XML files have
     * already been parsed.
     *
     * @param vFiles List of files to be parsed
     * @return a CAlphIO which knows about the alphabets recorded in these files.
     */
//    @Override
//    public CAlphIO doAlphIO(ArrayList<String> vFiles) {
//        return new CAlphIO(vFiles, this);
//    }

    /**
     * Attempts to retrieve a resource stream representing a given
     * file by first trying to open it on the local filesystem,
     * and if that fails using the getResourceAsStream method.
     *
     * @return InputStream pointing to the relevant file if possible,
     * or null if not.
     */
    @Override
    public InputStream getResourceStream(String filename) {
        try {
            FileInputStream in = new FileInputStream(filename);
            if (in != null) {
                return in;
            }
        } catch (IOException e) {

        }
        InputStream in = getClass().getResourceAsStream(filename);
        log.finer(String.format("Resource stream for %s is %s", filename, in));
        if (in != null) {
            return in;
        } else {
            return getClass().getResourceAsStream(getSetting(Esp_parameters.SP_SYSTEM_LOC) + filename);
        }
        // TODO: why won't redirecting JDasher.getResourceStream to StaticResourceManager work?
//        return StaticResourceManager.getResourceStream(filename);
    }

    /**
     * Sets our system path to "system.rc/".
     */
    public void SetupPaths() {

        m_SettingsStore.SetStringParameter(Esp_parameters.SP_SYSTEM_LOC, "system.rc/");

    }

    /**
     * Stub
     */
    public void SetupUI() {
        // Auto-generated method stub
    }

    /**
     * Orders our host to redraw.
     *
     * @param bChanged ignored
     */
    public void Redraw(boolean bChanged) {
        host.Redraw();
    }

    // <editor-fold defaultstate="collapsed" desc="DasherSession impl">
    // TODO: complete DasherSession impl for JDasher
    public SettingsManager getSettingsManager() {
        return m_SettingsStore;
    }

    public ResourceManager getResourcesManager() {
        return this;
    }

    public EventManager getEventManager() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public File getWorkingFile() {
        return getFile();
    }

    public void setWorkingFile(File file) {
        setFile(file);
    }

    public boolean isPaused() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setPaused(boolean isPaused) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isLocked() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setLock(boolean isLocked, String reason) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setLock(boolean isLocked) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getLockReason() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    // </editor-fold>
}
