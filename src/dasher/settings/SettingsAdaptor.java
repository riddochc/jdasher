/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dasher.settings;

import dasher.events.CParameterNotificationEvent;
import dasher.events.CEventHandler;
import dasher.events.SettingEvent.Listener;
import java.util.logging.Logger;

/**
 * This is an adaptor class for the SettingsManager interface to fool old code
 * into thinking it's just using an ordinary CSettingsStore.
 *
 * @author joshua
 * @deprecated
 */
public class SettingsAdaptor extends CSettingsStore implements SettingsManager {

    private static final Logger log = Logger.getLogger(SettingsAdaptor.class.getName());
    private SettingsManager settingsManager;
    private static SettingsAdaptor instance;

    /**
     *
     * @return
     */
    public static SettingsAdaptor getInstance() {
        if (instance == null) {
            // TODO public static SettingsAdaptor getInstance()
        }
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> void setSetting(EParameters<T> key, T value) {
        settingsManager.setSetting(key, value);

        // Initiate events for changed parameter
        CParameterNotificationEvent oEvent = new CParameterNotificationEvent(key);
        m_pEventHandler.InsertEvent(oEvent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetSetting(EParameters<?> key) {
        settingsManager.resetSetting(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadPersistantSettings() {
        settingsManager.loadPersistantSettings();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T getSetting(EParameters<T> key) {
        return settingsManager.getSetting(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> void addSettingListener(Listener listener, EParameters<T> key) {
        settingsManager.addSettingListener(listener, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addSettingListener(Listener listener) {
        settingsManager.addSettingListener(listener);
    }

    /**
     *
     * @param pEventHandler Event handler which we should notify
     * of parameter changes
     * @param settingsManager
     */
    public SettingsAdaptor(CEventHandler pEventHandler, SettingsManager settingsManager) {
        super(pEventHandler);
        this.settingsManager = settingsManager;
    }

    /* All C++-style integer based enums have now been replaced by three Enum types
     * which implement EParameters<T>, meaning one can pass both a generic parameter AND
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
     * The only weakness is that one CANNOT in fact switch on an EParameters<T>, since
     * there is no way for the compiler to know that all its children are Enums.
     * There may be some way around this -- some sort of enum-interface -- but
     * I haven't found it yet. This can be solved by splitting any switch
     * into three, type-checking, casting, and then switching in a type-specific
     * manner.
     */


    /**
     * {@inheritDoc}
     */
    @Override
    public void SetBoolParameter(Ebp_parameters iParameter, boolean bValue) {
        setSetting(iParameter, bValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void SetLongParameter(Elp_parameters iParameter, long lValue) {
        setSetting(iParameter, lValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void SetStringParameter(Esp_parameters iParameter, String sValue) {
        setSetting(iParameter, sValue);
    }

    /**
     *
     * @param iParameter
     * @return value of setting
     */
    public boolean GetBoolParameter(Ebp_parameters iParameter) {
        return getSetting(iParameter);
    }

    /**
     *
     * @param iParameter
     * @return value of setting
     */
    public long GetLongParameter(Elp_parameters iParameter) {
        return getSetting(iParameter);
    }

    /**
     *
     * @param iParameter
     * @return value of setting
     */
    public String GetStringParameter(Esp_parameters iParameter) {
        return getSetting(iParameter);
    }
}
