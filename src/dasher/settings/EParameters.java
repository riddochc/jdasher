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

import java.util.HashMap;
import java.util.Map;

/**
 * Interface implemented by all parameter enumerations, allowing
 * an EParameters to be passed as a generic parameter of indeterminate
 * type.
 *
 * Implementations should have constructors like
 * {@code private C(String regName, boolean persistant, T def, String humanReadable)}.
 *
 * If this is to be reserved to global settings, the {@link SettingsManager}
 * should be a singleton, represented by perhaps something like
 * {@code SettingsManagerFactory.getSettingsManager()}.
 *
 * @param <T> The type of the parameter/setting
 * @see Ebp_parameters
 * @see Elp_parameters
 * @see Esp_parameters
 */
public interface EParameters<T> {

    /**
     * Is this really needed?
     */
    public static final Map<String, EParameters<?>> BY_NAME = new HashMap<String, EParameters<?>>();
    /**
     * This is the persistent flag that we set on any parameters that should be.
     * But eventually all will be so this will become deprecated.
     */
    public static final boolean PERS = true;

    /**
     * Gets the <code>ordinal()</code> for this key.
     *
     * @return <code>ordinal()</code>
     * @deprecated this is not used anywhere and shouldn't be
     */
    public int key();

    /**
     * Gets the name by which this is stored.
     *
     * @return the name as specified by the constructor
     */
    public String regName();

    /**
     * Gets the default value of this setting.
     *
     * @return the default value
     */
    public T defaultValue();

    /**
     * Gets the type of this setting.
     *
     * @return <code>T.class</code>
     */
    public Class<T> type();

    /**
     * Resets the setting on the given {@link SettingsManager}.
     * 
     * @param sm
     */
    public void reset(SettingsManager sm);
    
    public boolean isPersistent();

    /* TOTO Maybe add custom set/get, or leave that up to the SM
     * public T get(SettingsManager sm);
     * public void set(T value, SettingsManager sm);
     */


    /* CSFS: This space intentionally left blank.
     *
     * This is the parent class for the enumerations of all three types of parameters.
     * Ebp_parameters houses the boolean parameters,
     * Elp_parameters the longs,
     * and Esp_parameters the strings.
     */
}
