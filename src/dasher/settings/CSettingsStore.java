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
package dasher.settings;

import dasher.events.CEventHandler;
import dasher.events.SettingEvent;
import java.util.EnumMap;
import java.util.Map;

/**
 * SettingsStore is a base implementation of a settings repository.
 * It has no ability to store its settings persistently, and will
 * load the defaults specified in CParamTables every time it is
 * instantiated.
 * <p>
 * In general the contract of a SettingsStore is to
 * <p><ul><li>Store parameter settings internally
 * <li>Optionally save these out to a persistent storage any time
 * a parameter changes
 * <li>Optionally restore parameters from this persistent store
 * when instaitated. 
 * <li>Raise a CParameterNotificationEvent whenever a parameter
 * is changed.
 * </ul>
 * <p>
 * Implementations wishing for persistent settings should subclass
 * CSettingsStore and, by subclassing CDasherInterfaceBase, ensure
 * that CreateSettingsStore is overridden to create their subclass.
 * <p>
 * Directly instantiating CSettingsStore will, however, be perfectly
 * functional for testing purposes or environments which cannot
 * save settings.
 * 
 */
public class CSettingsStore extends AbstractSettingsManager {

    /**
     * Our reference parameter tables
     */
//    protected CParamTables s_oParamTables;
    /**
     * Event handler which we should notify whenever a parameter
     * changes
     */
    protected CEventHandler m_pEventHandler;
    /**
     *
     */
    protected final int ParamBool = 0;
    /**
     *
     */
    protected final int ParamLong = 1;
    /**
     *
     */
    protected final int ParamString = 2;
    /**
     *
     */
    protected final int ParamInvalid = 3;

    protected final Map<Ebp_parameters,Boolean> booleanSettings = new EnumMap<Ebp_parameters, Boolean>(Ebp_parameters.class);
    protected final Map<Elp_parameters,Long> longSettings = new EnumMap<Elp_parameters, Long>(Elp_parameters.class);
    protected final Map<Esp_parameters,String> stringSettings = new EnumMap<Esp_parameters, String>(Esp_parameters.class);

    /**
     *
     * @param id
     * @return
     * @deprecated
     */
    public long getLong(Elp_parameters id) {
        return getSetting(id);
    }

    /**
     *
     * @param id
     * @return
     * @deprecated
     */
    public boolean getBool(Ebp_parameters id) {
        return getSetting(id);
    }

    /**
     *
     * @param id
     * @return
     * @deprecated
     */
    public String getString(Esp_parameters id) {
        return getSetting(id);
    }

    /**
     *
     * @param id
     * @param value
     * @deprecated
     */
    public void setLong(Elp_parameters id, long value) {
        SetLongParameter(id, value);
    }

    /**
     *
     * @param id
     * @param value
     * @deprecated
     */
    public void setBool(Ebp_parameters id, boolean value) {
        SetBoolParameter(id, value);
    }

    /**
     *
     * @param id
     * @param value
     * @deprecated
     */
    public void setString(Esp_parameters id, String value) {
        SetStringParameter(id, value);
    }

    /**
     *
     */
    public void loadPersistantSettings() {
        LoadPersistent();
    }

    /**
     *
     * @param key
     * @return
     * @deprecated
     */
    public Boolean get(Ebp_parameters key) {
        return getSetting(key);
    }

    /**
     *
     * @param key
     * @return
     * @deprecated
     */
    public Long get(Elp_parameters key) {
        return getSetting(key);
    }

    /**
     *
     * @param key
     * @return
     * @deprecated
     */
    public String get(Esp_parameters key) {
        return getSetting(key);
    }

    /**
     *
     * @param <T>
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getSetting(EParameters<T> key) {
        if (key instanceof Ebp_parameters)
            return (T) getBoolSetting((Ebp_parameters) key);
        else if (key instanceof Elp_parameters)
            return (T) getLongSetting((Elp_parameters) key);
        else if (key instanceof Esp_parameters)
            return (T) getStringSetting((Esp_parameters) key);
        else
            throw new UnsupportedOperationException("Unsupported setting");
    }

    private Boolean getBoolSetting(Ebp_parameters key) {
        Boolean value = booleanSettings.get(key);
        if (value == null)
            return key.defaultValue();
        else
            return value;
    }

    private Long getLongSetting(Elp_parameters key) {
        Long value = longSettings.get(key);
        if (value == null)
            return key.defaultValue();
        else
            return value;
    }

    private String getStringSetting(Esp_parameters key) {
        String value = stringSettings.get(key);
        if (value == null)
            return key.defaultValue();
        else
            return value;
    }

    /**
     *
     * @param key
     * @param value
     * @deprecated
     */
    public void set(Ebp_parameters key, boolean value) {
        SetBoolParameter(key, value);
    }

    /**
     *
     * @param key
     * @param value
     * @deprecated
     */
    public void set(Elp_parameters key, long value) {
        SetLongParameter(key, value);
    }

    /**
     *
     * @param key
     * @param value
     * @deprecated
     */
    public void set(Esp_parameters key, String value) {
        SetStringParameter(key, value);
    }

    /**
     *
     * @param <T>
     * @param key
     * @param value
     */
    public <T> void setSetting(EParameters<T> key, T value) {
        if (key instanceof Ebp_parameters)
            setBoolSetting((Ebp_parameters) key, (Boolean) value);
        else if (key instanceof Elp_parameters)
            setLongSetting((Elp_parameters) key, (Long) value);
        else if (key instanceof Esp_parameters)
            setStringSetting((Esp_parameters) key, (String) value);
        else
            throw new UnsupportedOperationException("Unsupported setting");
    }

    /**
     *
     * @param key
     */
    @Override
    public void resetSetting(EParameters<?> key) {
        key.reset(this);
    }

    private void setBoolSetting(Ebp_parameters key, Boolean value) {
        booleanSettings.put(key, value);
    }

    private void setLongSetting(Elp_parameters key, Long value) {
        longSettings.put(key, value);
    }

    private void setStringSetting(Esp_parameters key, String value) {
        stringSettings.put(key, value);
    }

    private void initCSettingsStore() {
        LoadPersistent();
    }

    /**
     * @deprecated
     */
    public static enum ParamType {

        /**
         *
         */
        Bool(Boolean.class),
        /**
         *
         */
        Long(java.lang.Long.class),
        /**
         *
         */
        String(java.lang.String.class),
        /**
         *
         */
        Invalid(Void.class);

        ParamType(Class<?> clazz) {
            this.clazz = clazz;
        }
        /**
         *
         */
        public final Class<?> clazz;
    }

    /**
     * Same as new CSettingsStore(..., true)
     *
     * @param pEventHandler Event handler which we should notify
     * of parameter changes
     */
    public CSettingsStore(CEventHandler pEventHandler) {

        this(pEventHandler, true);

    }

    public CSettingsStore() {
        this(null, true);
    }

    /**
     * Creates a new SettingsStore and instructs it to retrieve persistent
     * settings if the backing store is ready to respond.
     *
     * @param pEventHandler Event handler to notify of parameter changes
     * @param readyYet Is the backing store ready; can we retrieve
     * persistent settings?
     */
    public CSettingsStore(CEventHandler pEventHandler, boolean readyYet) {
        super();
        m_pEventHandler = pEventHandler;
        if (pEventHandler != null)
            addSettingListener(new SettingEvent.ListenerWrapperForCEventHandler(pEventHandler));
//        s_oParamTables = new CParamTables();

        if (readyYet) { // If the backing store is ready (prepared by a subclass)
            initCSettingsStore();
        }
    }

    /* All C++-style integer based enums have now been replaced by three Enum types
     * which implement EParameters, meaning one can pass both a generic parameter AND
     * a specialised parameter. For references into the tables, the .ordinal() of
     * a specialised parameter is used. All switch() statements should now check
     * the parameter's type, cast it to the appropriate one, and then switch on
     * the relevant enum. Alternatively it may be possible to have cases of a
     * child-type, I've yet to check this.
     *
     * 14/07: The whole codebase is now converted to use the new parameter scheme.
     * It's broadly very solid; everything is passed around as enum types until the actual
     * load/store instructions in CSettingsStore, whereupon ordinals are taken.
     *
     * The only weakness is that one CANNOT in fact switch on an EParameters, since
     * there is no way for the compiler to know that all its children are Enums.
     * There may be some way around this -- some sort of enum-interface -- but
     * I haven't found it yet. This can be solved by splitting any switch
     * into three, type-checking, casting, and then switching in a type-specific
     * manner.
     */
    /**
     * Loads persistent settings by means of the LoadSetting function.
     * <p>
     * If loading fails, the default value is retrieved and SaveSetting
     * is called to save this out to our backing store.
     */
    public void LoadPersistent() {

        // Load each of the persistent parameters.  If we fail loading for the store, then
        // we'll save the settings with the default value that comes from Parameters.h

        /* CSFS: The load/save settings were previously using the return value
         * to communicate success or failure, and a reference to some temporary
         * variable to actually confer the value. I have redesigned this
         * to use an Exception instead.
         */

        for (Ebp_parameters key : Ebp_parameters.values()) {
            if (key.isPersistent())
                try {
                    booleanSettings.put(key, LoadBoolSetting(key.regName()));
                } catch (CParameterNotFoundException e) {
                    SaveSetting(key.regName(), getSetting(key));
                }
        }

        for (Elp_parameters key : Elp_parameters.values()) {
            if (key.isPersistent())
                try {
                    longSettings.put(key, LoadLongSetting(key.regName()));
                } catch (CParameterNotFoundException e) {
                    SaveSetting(key.regName(), getSetting(key));
                }
        }

        for (Esp_parameters key : Esp_parameters.values()) {
            if (key.isPersistent())
                try {
                    stringSettings.put(key, LoadStringSetting(key.regName()));
                } catch (CParameterNotFoundException e) {
                    SaveSetting(key.regName(), getSetting(key));
                }
        }

//        for (int i = 0; i < Ebp_parameters.values().length; ++i) {
//            if (s_oParamTables.BoolParamTable[i].persistent) {
//                try {
//                    s_oParamTables.BoolParamTable[i].value = LoadBoolSetting(s_oParamTables.BoolParamTable[i].regName);
//                } catch (CParameterNotFoundException e) {
//                    SaveSetting(s_oParamTables.BoolParamTable[i].regName, s_oParamTables.BoolParamTable[i].value);
//                }
//            }
//        }
//
//        for (int j = 0; j < Elp_parameters.values().length; ++j) {
//            if (s_oParamTables.LongParamTable[j].persistent) {
//                try {
//                    s_oParamTables.LongParamTable[j].value = LoadLongSetting(s_oParamTables.LongParamTable[j].regName);
//                } catch (CParameterNotFoundException e) {
//                    SaveSetting(s_oParamTables.LongParamTable[j].regName, s_oParamTables.LongParamTable[j].value);
//                }
//            }
//        }
//
//        for (int k = 0; k < Esp_parameters.values().length; ++k) {
//            if (s_oParamTables.StringParamTable[k].persistent) {
//                try {
//                    s_oParamTables.StringParamTable[k].value = LoadStringSetting(s_oParamTables.StringParamTable[k].regName);
//                } catch (CParameterNotFoundException e) {
//                    SaveSetting(s_oParamTables.StringParamTable[k].regName, s_oParamTables.StringParamTable[k].value);
//                }
//            }
//        }
    }

    /**
     * Sets the value of a given boolean parameter.
     * <p>
     * This will raise a ParameterNotificationEvent with our
     * event handler.
     *
     * @param iParameter Parameter to setSetting
     * @param bValue New value for this parameter
     * @deprecated
     */
    public void SetBoolParameter(Ebp_parameters iParameter, boolean bValue) {
        setSetting(iParameter, bValue);
    }

    public void setSetting(Ebp_parameters key, Boolean value) {
        boolean old = getSetting(key);
        booleanSettings.put(key, value);

        fireChangeEvent(key, old, value);

        if (key.isPersistent())
            SaveSetting(key.regName(), value);

//        // Check that the parameter is in fact in the right spot in the table
//        if (value == getSetting(key)) {
//            return;
//        }
 //        s_oParamTables.BoolParamTable[key.ordinal()].value = value;

//        // Initiate events for changed parameter
//        CParameterNotificationEvent oEvent = new CParameterNotificationEvent(key);
//
//        m_pEventHandler.InsertEvent(oEvent);
//        oEvent = null; // Left for the Garbage Collector.

        // Write out to permanent storage
//        if (s_oParamTables.BoolParamTable[key.ordinal()].persistent) {
//            SaveSetting(s_oParamTables.BoolParamTable[key.ordinal()].regName, value);
//        }
    }

    /**
     * Sets the value of a given long parameter.
     * <p>
     * This will raise a ParameterNotificationEvent with our
     * event handler.
     *
     * @param iParameter Parameter to setSetting
     * @param lValue New value for this parameter
     * @deprecated
     */
    public void SetLongParameter(Elp_parameters iParameter, long lValue) {
        setSetting(iParameter, lValue);
    }

    public void setSetting(Elp_parameters key, Long value) {
        Long old = getSetting(key);
        longSettings.put(key, value);

        fireChangeEvent(key, old, value);

        if (key.isPersistent())
            SaveSetting(key.regName(), value);

//        if (value == getSetting(key)) {
//            return;
//        }
//
//        long old = getSetting(key);
//        // Set the value
//        s_oParamTables.LongParamTable[key.ordinal()].value = value;
//
//        fireChangeEvent(key, old, value);
////        // Initiate events for changed parameter
////        CParameterNotificationEvent oEvent = new CParameterNotificationEvent(key);
////        m_pEventHandler.InsertEvent(oEvent);
//
//        // Write out to permanent storage
//        if (s_oParamTables.LongParamTable[key.ordinal()].persistent) {
//            SaveSetting(s_oParamTables.LongParamTable[key.ordinal()].regName, value);
//        }
    }

    /**
     * Sets the value of a given string parameter.
     * <p>
     * This will raise a ParameterNotificationEvent with our
     * event handler.
     *
     * @param iParameter Parameter to setSetting
     * @param sValue New value for this parameter
     * @deprecated
     */
    public void SetStringParameter(Esp_parameters iParameter, String sValue) {
        setSetting(iParameter, sValue);
    }

    public void setSetting(Esp_parameters key, String value) {
        String old = getSetting(key);
        stringSettings.put(key, value);

        fireChangeEvent(key, old, value);

        if (key.isPersistent())
            SaveSetting(key.regName(), value);

//         if (value.equals(getSetting(key))) {
//            return;
//        }
//
//        String old = getSetting(key);
//        // Set the value
//        s_oParamTables.StringParamTable[key.ordinal()].value = value;
//
//        fireChangeEvent(key, old, value);
//        //
////        // Initiate events for changed parameter
////        CParameterNotificationEvent oEvent = new CParameterNotificationEvent(iParameter);
////        m_pEventHandler.InsertEvent(oEvent);
//
//        // Write out to permanent storage
//        if (s_oParamTables.StringParamTable[key.ordinal()].persistent) {
//            SaveSetting(s_oParamTables.StringParamTable[key.ordinal()].regName, value);
//        }
    }

    /**
     * Gets the value of a boolean parameter
     *
     * @param iParameter Parameter to query
     * @return Value of this parameter
     */
    public boolean getSetting(Ebp_parameters iParameter) {
        Boolean value = booleanSettings.get(iParameter);
        if (value == null)
            return iParameter.defaultValue();
        else
            return value;
        // Return the value
//        return s_oParamTables.BoolParamTable[iParameter.ordinal()].value;
    }

    /**
     * Gets the value of an integer parameter
     *
     * @param iParameter Parameter to query
     * @return Value of this parameter
     */
    public long getSetting(Elp_parameters iParameter) {
        Long value = longSettings.get(iParameter);
        if (value == null)
            return iParameter.defaultValue();
        else
            return value;

        // Return the value
//        return s_oParamTables.LongParamTable[iParameter.ordinal()].value;
    }

    /**
     * Gets the value of a String parameter
     *
     * @param iParameter Parameter to query
     * @return Value of this parameter
     */
    public String getSetting(Esp_parameters iParameter) {
        String value = stringSettings.get(iParameter);
        if (value == null)
            return iParameter.defaultValue();
        else
            return value;
        // Return the value
//        return s_oParamTables.StringParamTable[iParameter.ordinal()].value;
    }

    /**
     * Resets a given parameter to its default value, as given
     * by its entry in CParamTables.
     *
     * @param key Parameter to reset
     * @deprecated
     */
    public void ResetParameter(EParameters<?> key) {
        resetSetting(key);
//        switch (GetParameterType(key)) {
//            case ParamBool:
//                setSetting((Ebp_parameters) key, s_oParamTables.BoolParamTable[((Ebp_parameters) key).ordinal()].defaultVal);
//                break;
//            case ParamLong:
//                setSetting((Elp_parameters) key, s_oParamTables.LongParamTable[((Elp_parameters) key).ordinal()].defaultVal);
//                break;
//            case ParamString:
//                setSetting((Esp_parameters) key, s_oParamTables.StringParamTable[((Esp_parameters) key).ordinal()].defaultVal);
//                break;
//        }
    }

    /**
     * Determines the type of a given parameter.
     *
     * @param iParameter Parameter to query
     * @return 0 for boolean, 1 for long, 2 for String, 3 for invalid
     */
    public int GetParameterType(EParameters<?> iParameter) {
        if (iParameter instanceof Ebp_parameters) {
            return ParamBool;
        }
        if (iParameter instanceof Elp_parameters) {
            return ParamLong;
        }
        if (iParameter instanceof Esp_parameters) {
            return ParamString;
        }

        return ParamInvalid;
    }

    /**
     * Gets the internal name of a parameter
     *
     * @param iParameter Parameter to query
     * @return Internal name
     * @deprecated 
     */
    public String GetParameterName(EParameters<?> iParameter) {
        // Pull the registry name out of the correct table depending on the parameter type
//        switch (GetParameterType(iParameter)) {
//            case (ParamBool): {
//                return s_oParamTables.BoolParamTable[((Ebp_parameters) iParameter).ordinal()].regName;
//            }
//            case (ParamLong): {
//                return s_oParamTables.LongParamTable[((Elp_parameters) iParameter).ordinal()].regName;
//            }
//            case (ParamString): {
//                return s_oParamTables.StringParamTable[((Esp_parameters) iParameter).ordinal()].regName;
//            }
//        }
        return iParameter.regName();
    }

    /* CSFS: There were some deprecated functions below here, named GetBoolOption
     * and the obvious brethren. Since nobody seemed to be calling these anymore,
     * I've removed them.
     */
    /* Private functions -- Settings are not saved between sessions unless these
    functions are over-ridden.
    --------------------------------------------------------------------------*/
    /**
     * Loads a given boolean setting from the backing store.
     *
     * @param Key Name of parameter to retrieve
     * @return Value of this setting
     * @throws CParameterNotFoundException if loading failed, eg. because the backing
     * store did not contain information about this parameter.
     */
    protected boolean LoadBoolSetting(String Key) throws CParameterNotFoundException {
        throw new CParameterNotFoundException(Key);
    }

    /**
     * Loads a given integer setting from the backing store.
     *
     * @param Key Name of parameter to retrieve
     * @return Value of this setting
     * @throws CParameterNotFoundException if loading failed, eg. because the backing
     * store did not contain information about this parameter.
     */
    protected long LoadLongSetting(String Key) throws CParameterNotFoundException {
        throw new CParameterNotFoundException(Key);
    }

    /**
     * Loads a given String setting from the backing store.
     *
     * @param Key Name of parameter to retrieve
     * @return Value of this setting
     * @throws CParameterNotFoundException if loading failed, eg. because the backing
     * store did not contain information about this parameter.
     */
    protected String LoadStringSetting(String Key) throws CParameterNotFoundException {
        throw new CParameterNotFoundException(Key);
    }

    /**
     * Saves a given bool parameter to the backing store. In this base
     * class, this method is a stub; it should be overridden by a
     * subclass if persistent settings are desired.
     *
     * @param Key Name of the parameter to save
     * @param Value Value of the parameter
     */
    protected void SaveSetting(String Key, boolean Value) {
    }

    /**
     * Saves a given integer parameter to the backing store. In this base
     * class, this method is a stub; it should be overridden by a
     * subclass if persistent settings are desired.
     *
     * @param Key Name of the parameter to save
     * @param Value Value of the parameter
     */
    protected void SaveSetting(String Key, long Value) {
    }

    /**
     * Saves a given string parameter to the backing store. In this base
     * class, this method is a stub; it should be overridden by a
     * subclass if persistent settings are desired.
     *
     * @param Key Name of the parameter to save
     * @param Value Value of the parameter
     */
    protected void SaveSetting(String Key, String Value) {
    }
}
