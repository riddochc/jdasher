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
package dasher.events;

// <editor-fold defaultstate="collapsed" desc="import logging...">
import dasher.settings.EParameters;
import dasher.settings.SettingsManager;
import java.util.EventListener;
import java.util.EventObject;
import java.util.logging.Logger;
// </editor-fold>

/**
 *
 * @param <T> the type of the setting
 * @author joshua
 */
public class SettingEvent<T> extends EventObject {
    private static final long serialVersionUID = 1L;

    // <editor-fold defaultstate="collapsed" desc="log creation">
    /**
     * The Logger for this class.
     */
    private static final Logger log = Logger.getLogger(SettingEvent.class.getName());// </editor-fold>

    public SettingEvent(EParameters<T> setting) {
        this(setting, null);
    }

    public SettingEvent(EParameters<T> setting, SettingsManager manager) {
        super(setting);
        this.setting = setting;
        settingManager = manager;
    }

    // <editor-fold defaultstate="collapsed" desc="protected EParameters<T> setting; (r/o)">
    protected EParameters<T> setting;

    /**
     * Get the value of setting
     *
     * @return the value of setting
     */
    public EParameters<T> getSetting() {
        return setting;
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="protected SettingsManager settingManager; (r/o)">
    protected SettingsManager settingManager;

    /**
     * Get the value of settingManager
     *
     * @return the value of settingManager
     */
    public SettingsManager getSettingManager() {
        return settingManager;
    }// </editor-fold>

    @Override
    public String toString() {
        return String.format("(%s: %s = %s)", this.getClass(), setting,
                (settingManager != null ? settingManager.getSetting(setting) : "?"));
    }

    public static interface Listener extends EventListener {
        public void settingChanged(SettingEvent.Changed<?> e);
    }

    public static class ListenerWrapperForCEventHandler implements Listener {

        protected CEventHandler eventHandler;

        /**
         * Get the value of eventHandler
         *
         * @return the value of eventHandler
         */
        public CEventHandler getEventHandler() {
            return eventHandler;
        }

        public ListenerWrapperForCEventHandler(CEventHandler eventHandler) {
            this.eventHandler = eventHandler;
        }

        public void settingChanged(Changed<?> e) {
            CParameterNotificationEvent oEvent = new CParameterNotificationEvent(e.getSetting());
            getEventHandler().InsertEvent(oEvent);
        }

    }

    public static class Changed<T> extends SettingEvent<T> {
        private static final long serialVersionUID = 1L;

        protected final T newVal, oldVal;

        public Changed(EParameters<T> setting, T oldVal, T newVal) {
            super(setting);
            this.newVal = newVal;
            this.oldVal = oldVal;
        }

        public T getNewValue() {
            return newVal;
        }

        public T getOldValue() {
            return oldVal;
        }

        @Override
        public String toString() {
            return String.format("(%s: %s new: %s old: %s)",
                    this.getClass(), setting, newVal, oldVal);

        }
    }
}
