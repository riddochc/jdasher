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

import java.io.File;

/**
 * Enumeration of possible String parameter references.
 */
public enum Esp_parameters implements EParameters<String> {

    SP_ALPHABET_ID("AlphabetID", PERS, "", "AlphabetID"),
    SP_ALPHABET_1("Alphabet1", PERS, "", "Alphabet History 1"),
    SP_ALPHABET_2("Alphabet2", PERS, "", "Alphabet History 2"),
    SP_ALPHABET_3("Alphabet3", PERS, "", "Alphabet History 3"),
    SP_ALPHABET_4("Alphabet4", PERS, "", "Alphabet History 4"),
    SP_COLOUR_ID("ColourID", PERS, "", "ColourID"),
    SP_DEFAULT_COLOUR_ID("DefaultColourID", !PERS, "", "Default Colour ID (Used for auto-colour mode)"),
    SP_DASHER_FONT("DasherFont", PERS, "", "DasherFont"),
    SP_SYSTEM_LOC("SystemLocation", !PERS, "sys_", "System Directory"),

    /**
     * This will hopefully be used to save user training data, whenever we figure that out.
     */
    SP_USER_TRAINING_LOC("UserTrainingLocation", PERS,
                (new File(System.getProperty("user.home"), ".dasher")).toString(),
                "User Training Directory"),
    SP_USER_LOC("UserLocation", /* ! */ PERS, System.getProperty("user.home"), "User Directory"),
    SP_GAME_TEXT_FILE("GameTextFile", !PERS, "gamemode_english_GB.txt", "File with strings to practice writing"),
    SP_TRAIN_FILE("TrainingFile", !PERS, "", "Training text for alphabet"),
    SP_SOCKET_INPUT_X_LABEL("SocketInputXLabel", PERS, "x", "Label preceding X values for network input"),
    SP_SOCKET_INPUT_Y_LABEL("SocketInputYLabel", PERS, "y", "Label preceding Y values for network input"),
    SP_INPUT_FILTER("InputFilter", PERS, "Stylus Control", "Input filter used to provide the current control mode"),
    SP_INPUT_DEVICE("InputDevice", PERS, "Mouse Input", "Driver for the input device"),
    SP_LM_HOST("LMHost", PERS, "", "Language Model Host");

    private Esp_parameters(String rName, boolean pers, String def, String hr) {
        humanReadable = hr;
        persistent = pers;
        defaultVal = def;
        regName = rName;
        BY_NAME.put(regName, this);
    }

    @SuppressWarnings("deprecation")
    public int key() {
        return ordinal();
    }

    public String regName() {
        return regName;
    }

    public String defaultValue() {
        return defaultVal;
    }

//    public void reset(CSettingsStore ss) {
//        ss.SetStringParameter(this, defaultVal);
//    }
    public void reset(SettingsManager sm) {
        sm.setSetting(this, defaultVal);
    }

    private final String regName;
    final boolean persistent;
    final String defaultVal;
    final String humanReadable;

    public Class<String> type() {
        return String.class;
    }

    public boolean isPersistent() {
        return persistent;
    }
}
