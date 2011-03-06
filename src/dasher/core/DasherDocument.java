/*
 *   Copyright 2009 joshua.
 * 
 *   This is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 * 
 *   This is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 * 
 *   You should have received a copy of the GNU General Public License
 *   along with this; if not, write to the Free Software
 *   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package dasher.core;

import dasher.settings.SettingsManager;
import dasher.events.EventManager;
import dasher.resources.ResourceManager;
import dasher.resources.StaticResourceManager;
import dasher.settings.SettingsManagerFactory;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.Random;

/**
 * This should eventually coordinate a session of Dasher, probably replacing
 * much of CDasherInterface.
 *
 * @author joshua
 */
public interface DasherDocument {

    public static final String PROP_WORKINGFILE = "workingFile";
    public static final String PROP_PAUSED = "paused";
    public static final String PROP_LOCKEDFORTRAINING = "lockedForTraining";
    public static final String PROP_LOCKEDFORCONNECTION = "lockedForConnection";
    public static final String PROP_LOCKED = "locked";

    /**
     * Get the SettingsManager associated with this session.
     *
     * @return SettingsManager
     */
    public SettingsManager getSettingsManager();

    /**
     * Get the ResourceManager associated with this session.
     *
     * @return ResourceManager
     */
    public ResourceManager getResourcesManager();

    /**
     * Get the EventManager associated with this session.
     *
     * This should eventully be gone.
     *
     * @return EventManager
     */
    public EventManager getEventManager();

    /**
     * Get the current file this session is working on.
     *
     * @return the working file
     */
    public File getWorkingFile();

    /**
     * Sets the file that this session is working on.
     *
     * @param file
     */
    public void setWorkingFile(File file);

    /**
     *
     * @return whether or not dasher is paused
     */
    public boolean isPaused();

    /**
     *
     * @param isPaused
     */
    public void setPaused(boolean isPaused);

    /**
     *
     * @return whether or not the session/document is locked
     */
    public boolean isLocked();

    /**
     *
     * @return the reason why it is locked or {@code null}
     */
    public String getLockReason();

    /**
     *
     * @param isLocked
     * @param reason
     */
    public void setLock(boolean isLocked, String reason);

    /**
     *
     * @param isLocked
     */
    public void setLock(boolean isLocked);

    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener);

    /**
     * Add a PropertyChangeListener for a specific property.
     * 
     * @param propertyName
     * @param listener
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener);

    /**
     * Remove a PropertyChangeListener for a specific property.
     * 
     * @param propertyName
     * @param listener
     */
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);

    public static class Basic implements DasherDocument {

        protected SettingsManager settings;
        protected ResourceManager resources;
        protected EventManager events;

        public Basic(SettingsManager settings, ResourceManager resources, EventManager events, File workingFile) {
            this.settings = settings;
            this.resources = resources;
            this.events = events;
            setWorkingFile(workingFile);
        }

        public Basic(SettingsManager settings, ResourceManager resources, EventManager events) {
            this(settings, resources, events, new File("dasher-" + Long.toHexString((new Random()).nextLong()) + ".txt"));
        }

        public Basic() {
            this(SettingsManagerFactory.getSettingsManager(null), StaticResourceManager.getStaticRcManager(), null);
        }

        public SettingsManager getSettingsManager() {
            return settings;
        }

        public ResourceManager getResourcesManager() {
            return resources;
        }

        public EventManager getEventManager() {
            return events;
        }
        
        // <editor-fold defaultstate="collapsed" desc="property change support">
        protected final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

        /**
         * Add PropertyChangeListener.
         *
         * @param listener
         */
        public final void addPropertyChangeListener(PropertyChangeListener listener) {
            propertyChangeSupport.addPropertyChangeListener(listener);
        }

        /**
         * Add a PropertyChangeListener for a specific property.
         * 
         * @param propertyName
         * @param listener
         */
        public final void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
            propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
        }

        /**
         * Remove PropertyChangeListener.
         *
         * @param listener
         */
        public final void removePropertyChangeListener(PropertyChangeListener listener) {
            propertyChangeSupport.removePropertyChangeListener(listener);
        }

        /**
         * Remove a PropertyChangeListener for a specific property.
         * 
         * @param propertyName
         * @param listener
         */
        public final void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
            propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
        }

        protected final void firePropertyChange(PropertyChangeEvent pce) {
            propertyChangeSupport.firePropertyChange(pce);
        }

        protected final void firePropertyChange(String string, boolean bln, boolean bln1) {
            propertyChangeSupport.firePropertyChange(string, bln, bln1);
        }

        protected final void firePropertyChange(String string, int i, int i1) {
            propertyChangeSupport.firePropertyChange(string, i, i1);
        }

        protected final void firePropertyChange(String string, Object o, Object o1) {
            propertyChangeSupport.firePropertyChange(string, o, o1);
        }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="protected CAlphabet alphabet; (rwb)">
        protected CAlphabet alphabet;
        public static final String PROP_ALPHABET = "alphabet";

        /**
         * Get the value of alphabet
         *
         * @return the value of alphabet
         */
        public CAlphabet getAlphabet() {
            return alphabet;
        }

        /**
         * Set the value of alphabet
         *
         * @param alphabet new value of alphabet
         */
        public void setAlphabet(CAlphabet alphabet) {
            CAlphabet oldAlphabet = this.alphabet;
            this.alphabet = alphabet;
            propertyChangeSupport.firePropertyChange(PROP_ALPHABET, oldAlphabet, alphabet);
        }// </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="private File workingFile; (rwb)">
        private File workingFile;

        /**
         * Get the value of workingFile
         *
         * @return the value of workingFile
         */
        public final File getWorkingFile() {
            return workingFile;
        }

        /**
         * Set the value of workingFile
         *
         * @param workingFile new value of workingFile
         */
        public final void setWorkingFile(File workingFile) {
            File oldWorkingFile = this.workingFile;
            this.workingFile = workingFile;
            propertyChangeSupport.firePropertyChange(PROP_WORKINGFILE, oldWorkingFile, workingFile);
        }// </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="protected boolean paused; (rwb)">
        protected boolean paused;

        /**
         * Get the value of paused
         *
         * @return the value of paused
         */
        public boolean isPaused() {
            return paused;
        }

        /**
         * Set the value of paused
         *
         * @param paused new value of paused
         */
        public void setPaused(boolean paused) {
            boolean oldPaused = this.paused;
            this.paused = paused;
            propertyChangeSupport.firePropertyChange(PROP_PAUSED, oldPaused, paused);
        }// </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="protected boolean lockedForTraining; (rwb)">
        protected boolean lockedForTraining;

        /**
         * Get the value of lockedForTraining
         *
         * @return the value of lockedForTraining
         */
        public boolean isLockedForTraining() {
            return lockedForTraining;
        }

        /**
         * Set the value of lockedForTraining
         *
         * @param lockedForTraining new value of lockedForTraining
         */
        public void setLockedForTraining(boolean lockedForTraining) {
            boolean oldLockedForTraining = this.lockedForTraining;
            this.lockedForTraining = lockedForTraining;
            propertyChangeSupport.firePropertyChange(PROP_LOCKEDFORTRAINING, oldLockedForTraining, lockedForTraining);
        }// </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="protected boolean lockedForConnection; (rwb)">
        protected boolean lockedForConnection;

        /**
         * Get the value of lockedForConnection
         *
         * @return the value of lockedForConnection
         */
        public boolean isLockedForConnection() {
            return lockedForConnection;
        }

        /**
         * Set the value of lockedForConnection
         *
         * @param lockedForConnection new value of lockedForConnection
         */
        public void setLockedForConnection(boolean lockedForConnection) {
            boolean oldLockedForConnection = this.lockedForConnection;
            this.lockedForConnection = lockedForConnection;
            propertyChangeSupport.firePropertyChange(PROP_LOCKEDFORCONNECTION, oldLockedForConnection, lockedForConnection);
        }// </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="locking (rwb)">
        private boolean isLocked = false;
        protected String lockReason = null;
        
        public boolean isLocked() {
            return isLocked || isLockedForTraining() || isLockedForConnection();
        }

        public void setLock(boolean isLocked, String reason) {
            setGeneralLock(isLocked, reason);
            if ("training".equals(reason))
                setLockedForTraining(isLocked);
            else if ("connection".equals(reason))
                setLockedForConnection(isLocked);
        }
        
        public String getLockReason() {
            return lockReason;
        }

        public void setGeneralLock(boolean isLocked, String reason) {
            String old = getLockReason();
            lockReason = reason;
            firePropertyChange(old, paused, paused);
        }

        public void setLock(boolean isLocked) {
            setLock(isLocked, isLocked ? "locked" : null);
        }// </editor-fold>
    }
}
