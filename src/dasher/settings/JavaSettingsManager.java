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

import java.util.logging.Logger;
import java.util.prefs.Preferences;

/**
 *
 * @author joshua
 */
public class JavaSettingsManager extends AbstractSettingsManager {

    /**
     * The Logger for this class.
     */
    private static final Logger log = Logger.getLogger(JavaSettingsManager.class.getName());
    /**
     *
     */
    private static final String typeNotSupported = "Setting type %s not supported.";
    private static final String prefsPrefix = "JDasher/";
    private static final Preferences prefs = Preferences.userRoot().node(prefsPrefix);

    /**
     *
     * @return
     */
    protected static Preferences getPrefs() {
        return prefs;
    }

    /**
     *
     * @param key
     * @return
     */
    protected static String getQualSettingName(EParameters<?> key) {
        if (key instanceof Ebp_parameters)
            return prefsPrefix + "B_" + key.regName();
        else if (key instanceof Elp_parameters)
            return prefsPrefix + "L_" + key.regName();
        else if (key instanceof Esp_parameters)
            return prefsPrefix + "S_" + key.regName();
        else
            throw new UnsupportedOperationException(String.format(typeNotSupported, key.getClass()));
    }

    public JavaSettingsManager() {
        super();
    }

    /**
     * {@inheritDoc}
     * @param key 
     * @return
     */
    public Boolean getSetting(Ebp_parameters key) {
        return getPrefs().getBoolean(getQualSettingName(key), key.defaultValue());
    }

    /**
     * {@inheritDoc}
     * @param key 
     * @return
     */
    public Long getSetting(Elp_parameters key) {
        return getPrefs().getLong(getQualSettingName(key), key.defaultValue());
    }

    /**
     * {@inheritDoc}
     * @param key
     * @return
     */
    public String getSetting(Esp_parameters key) {
        return getPrefs().get(getQualSettingName(key), key.defaultValue());
    }

    /**
     * {@inheritDoc}
     */
    public <T> T getSetting(EParameters<T> key) {
        throw new UnsupportedOperationException(String.format(typeNotSupported, key.getClass()));
    }

    /**
     * {@inheritDoc}
     * @param key 
     * @param value
     */
    public void setSetting(Ebp_parameters key, Boolean value) {
        getPrefs().putBoolean(getQualSettingName(key), value);
    }

    /**
     * {@inheritDoc}
     * @param key
     * @param value
     */
    public void setSetting(Elp_parameters key, Long value) {
        getPrefs().putLong(getQualSettingName(key), value);
    }

    /**
     * {@inheritDoc}
     * @param key 
     * @param value
     */
    public void setSetting(Esp_parameters key, String value) {
        getPrefs().put(getQualSettingName(key), value);
    }

    /**
     * {@inheritDoc}
     */
    public <T> void setSetting(EParameters<T> key, T value) {
        throw new UnsupportedOperationException(String.format(typeNotSupported, key.getClass()));
    }

    /**
     * 
     * {@inheritDoc}
     */
    public void loadPersistantSettings() {
        for (EParameters<?> setting : EParameters.BY_NAME.values())
            if (!setting.isPersistent())
                setting.reset(this);
    }
}
