/*
 *   Copyright 2011 joshua.
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
package dasher.settings;

import dasher.events.CEventHandler;
import dasher.events.SettingEvent.ListenerWrapperForCEventHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joshua
 */
public final class SettingsManagerFactory {

    /**
     * No instances of SettingsManagerFactory allowed.
     */
    private SettingsManagerFactory() {
        throw new UnsupportedOperationException("No instances of SettingsManagerFactory allowed.");
    }
    public static final String DEF_SETMAN = "default";
    private static Map<String, SettingManagerDescriptor> descriptors = new HashMap<String, SettingManagerDescriptor>();
    private static Map<String, SettingsManager> managers = new HashMap<String, SettingsManager>();

    /**
     * Tries to get/create a {@link SettingsManager} with the name {@code prefered},
     * else tries to get/create the default, and finally tries any other before
     * giving up and shamefully returning {@code null}.
     *
     * @param eventHandler
     * @param prefered
     * @return either the {@code prefered}, the default, or which other {@link SettingsManager} is available, or {@code null}
     */
    public static SettingsManager getSettingsManager(CEventHandler eventHandler, String prefered) {
        SettingManagerDescriptor pd = descriptors.get(prefered);
        if (pd != null && pd.isAvailable()) {
            try {
                return pd.getOrCreateSettingsManager(eventHandler);
            } catch (InstantiationException ex) {
                Logger.getLogger(SettingsManagerFactory.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(SettingsManagerFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        pd = descriptors.get(DEF_SETMAN);
        if (pd != null && pd.isAvailable()) {
            try {
                return pd.getOrCreateSettingsManager(eventHandler);
            } catch (InstantiationException ex) {
                Logger.getLogger(SettingsManagerFactory.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(SettingsManagerFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        for (SettingManagerDescriptor des : descriptors.values()) {
            if (des.isAvailable()) {
                try {
                    SettingsManager sm = des.getOrCreateSettingsManager(eventHandler);
                    descriptors.put(DEF_SETMAN, des);
                    return sm;
                } catch (InstantiationException ex) {
                    Logger.getLogger(SettingsManagerFactory.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(SettingsManagerFactory.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return null;
    }

    /**
     * Try to get/create the default {@link SettingsManager}.
     *
     * @param eventHandler
     * @return the default {@link SettingsManager} or {@code null}
     */
    public static SettingsManager getSettingsManager(CEventHandler eventHandler) {
        return getSettingsManager(eventHandler, DEF_SETMAN);
    }

    public static abstract class SettingManagerDescriptor {

        public SettingManagerDescriptor(Class<SettingsManager> settingsManagerClass, String name, String description) {
            this.settingsManagerClass = settingsManagerClass;
            this.name = name;
            this.description = description;
        }

        protected Class<SettingsManager> settingsManagerClass;

        /**
         * Get the value of settingsManagerClass
         *
         * @return the value of settingsManagerClass
         */
        public Class<SettingsManager> getSettingsManagerClass() {
            return settingsManagerClass;
        }

        protected String name;

        /**
         * Get the value of name
         *
         * @return the value of name
         */
        public String getName() {
            return name;
        }
        protected String description;

        /**
         * Get the value of description
         *
         * @return the value of description
         */
        public String getDescription() {
            return description;
        }

        private SettingsManager settingsManagerInstance;

        /**
         * Get the value of settingsManagerInstance
         *
         * @return the value of settingsManagerInstance
         */
        protected SettingsManager getSettingsManagerInstance() {
            return settingsManagerInstance;
        }

        protected abstract SettingsManager createSettingsManager(CEventHandler eventHandler) throws InstantiationException, IllegalAccessException;
        protected abstract void registerCEventHandlerWithSettingsManager(CEventHandler eventHandler, SettingsManager settingsManager);
        public abstract boolean isAvailable();

        public final SettingsManager getOrCreateSettingsManager(CEventHandler eventHandler) throws InstantiationException, IllegalAccessException {
            if (settingsManagerInstance == null)
                settingsManagerInstance = createSettingsManager(eventHandler);
            else
                registerCEventHandlerWithSettingsManager(eventHandler, settingsManagerInstance);

            return settingsManagerInstance;
        }
    }

    public static class BasicSettingManagerDescriptor extends SettingManagerDescriptor {

        public BasicSettingManagerDescriptor(Class<SettingsManager> settingsManagerClass, String name, String description) {
            super(settingsManagerClass, name, description);
        }

        @Override
        protected SettingsManager createSettingsManager(CEventHandler eventHandler) throws InstantiationException, IllegalAccessException {
            SettingsManager manager = getSettingsManagerClass().newInstance();
            registerCEventHandlerWithSettingsManager(eventHandler, manager);
            return manager;
        }

        @Override
        protected void registerCEventHandlerWithSettingsManager(CEventHandler eventHandler, SettingsManager settingsManager) {
            if (eventHandler != null)
                settingsManager.addSettingListener(new ListenerWrapperForCEventHandler(eventHandler));
        }

        @Override
        public boolean isAvailable() {
            return true;
        }

    }

}
