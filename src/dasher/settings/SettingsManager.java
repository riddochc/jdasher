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

package dasher.settings;

import dasher.events.SettingEvent;

/**
 * Implementations of this should always have a public null-arg constructor.
 *
 * @author joshua
 */
public interface SettingsManager {

    /* Should always have this.
    public SettingsManager();
    */

    /**
     * Get setting with {@code key}.
     *
     * @param <T> type of setting
     * @param key
     * @return value of key
     */
    public <T> T getSetting(EParameters<T> key);

    /**
     * Set {@code key} to {@code value}.
     *
     * @param <T> type of setting
     * @param key
     * @param value
     */
    public <T> void setSetting(EParameters<T> key, T value);

    /**
     * Loads persistent settings.
     */
    public void loadPersistantSettings();

    /**
     * Resets {@code key} to its default.
     *
     * @param key
     * @see EParameters#defaultValue()
     */
    public void resetSetting(EParameters<?> key);

    /**
     * Add a listener to all settings.
     *
     * @param listener
     */
    public void addSettingListener(SettingEvent.Listener listener);

    /**
     * Add a listener to the specific setting.
     *
     * @param <T> 
     * @param listener
     * @param key
     */
    public <T> void addSettingListener(SettingEvent.Listener listener, EParameters<T> key);

}
