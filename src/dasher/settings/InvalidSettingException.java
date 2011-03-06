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

/**
 *
 * @author joshua
 */
public class InvalidSettingException extends RuntimeException {

    // <editor-fold defaultstate="collapsed" desc="protected EParameters<?> id; (r/o)">
    /**
     * 
     */
    protected EParameters<?> id;

    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public EParameters<?> getId() {
        return id;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="protected SettingsManager manager; (r/o)">
    /**
     *
     */
    protected SettingsManager manager;

    /**
     * Get the value of manager
     *
     * @return the value of manager
     */
    public SettingsManager getManager() {
        return manager;
    }// </editor-fold>

    /**
     *
     * @param cause
     * @param id
     * @param manager
     */
    public InvalidSettingException(Throwable cause, EParameters<?> id, SettingsManager manager) {
        super(cause);
        this.id = id;
        this.manager = manager;
    }

    /**
     *
     * @param message
     * @param cause
     * @param id
     * @param manager
     */
    public InvalidSettingException(String message, Throwable cause, EParameters<?> id, SettingsManager manager) {
        super(message, cause);
        this.id = id;
        this.manager = manager;
    }

    /**
     *
     * @param message
     * @param id
     * @param manager
     */
    public InvalidSettingException(String message, EParameters<?> id, SettingsManager manager) {
        super(message);
        this.id = id;
        this.manager = manager;
    }

    /**
     *
     * @param id
     * @param manager
     */
    public InvalidSettingException(EParameters<?> id, SettingsManager manager) {
        this.id = id;
        this.manager = manager;
    }

    /**
     *
     * @param message
     * @param id
     */
    public InvalidSettingException(String message, EParameters<?> id) {
        super(message);
        this.id = id;
    }

}
