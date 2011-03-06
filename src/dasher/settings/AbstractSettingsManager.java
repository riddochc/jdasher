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
import dasher.events.CParameterNotificationEvent;
import dasher.events.SettingEvent;
import dasher.events.SettingEvent.Changed;
import javax.swing.event.EventListenerList;

/**
 * This class provides the framework for setting listeners and other trivial
 * things.  Note, if you want a {@link dasher.events.CEventHandler} you
 * have to add it as a setting listener wrapping it in
 * {@link dasher.events.SettingEvent.ListenerWrapperForCEventHandler}.
 *
 * @author joshua
 */
public abstract class AbstractSettingsManager implements SettingsManager {

    public AbstractSettingsManager() {
    }
    
    private EventListenerList listenerList = new EventListenerList();

    protected EventListenerList getListenerList() {
        return listenerList;
    }

    public void resetSetting(EParameters<?> key) {
        key.reset(this);
    }

    public void addSettingListener(SettingEvent.Listener listener) {
        listenerList.add(SettingEvent.Listener.class, listener);
    }

    public <T> void addSettingListener(SettingEvent.Listener listener, EParameters<T> key) {
        // TODO add setting listener for one key?  prob be not worth it
        listenerList.add(SettingEvent.Listener.class, listener);
    }

    /**
     * Fires a {@link dasher.events.SettingEvent.Changed} event.
     * 
     * @param <T>
     * @param key
     * @param oldVal 
     * @param newVal
     */
    @SuppressWarnings("unchecked")
    protected <T> void fireChangeEvent(EParameters<T> key, T oldVal, T newVal) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        SettingEvent.Changed<T> event = null;
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == SettingEvent.Listener.class) {
                // Lazily create the event:
                if (event == null) {
                    event = new SettingEvent.Changed<T>(key, oldVal, newVal);
                }
                ((SettingEvent.Listener) listeners[i + 1]).settingChanged(event);
            }
        }
    }
}
