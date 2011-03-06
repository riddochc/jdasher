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

/**
 * Enumeration of possible integer parameter references.
 */
public enum Elp_parameters implements EParameters<Long> {

    LP_ORIENTATION("ScreenOrientation", PERS, -2, "Screen Orientation"),
    LP_REAL_ORIENTATION("RealOrientation", !PERS, 0, "Actual screen orientation (allowing for alphabet default)"),
    LP_MAX_BITRATE("MaxBitRateTimes100", PERS, 80, "Max Bit Rate Times 100"),
    LP_SPEED_DIVISOR("SpeedDivisor", !PERS, 100, "Factor by which to slow down (multiplied by 100)"),
    LP_VIEW_ID("ViewID", PERS, 1, "ViewID"),
    LP_LANGUAGE_MODEL_ID("LanguageModelID", PERS, 0, "LanguageModelID"),
    LP_DASHER_FONTSIZE("DasherFontSize", PERS, 1, "DasherFontSize"),
    LP_UNIFORM("UniformTimes1000", PERS, 50, "UniformTimes1000"),
    LP_YSCALE("YScaling", PERS, 0, "YScaling"),
    LP_MOUSEPOSDIST("MousePositionBoxDistance", PERS, 50, "MousePositionBoxDistance"),
    //LP_STOP_IDLETIME("StopIdleTime", PERS, 1000, "StopIdleTime" ),
    
    /* TODO What are LP_TRUNCATION and LP_TRUNCATIONTYPE for? */
    LP_TRUNCATION("Truncation", PERS, 0, "Truncation"),
    LP_TRUNCATIONTYPE("TruncationType", PERS, 0, "TruncationType"),
    LP_LM_MAX_ORDER("LMMaxOrder", PERS, 5, "LMMaxOrder"),
    LP_LM_EXCLUSION("LMExclusion", PERS, 0, "LMExclusion"),
    LP_LM_UPDATE_EXCLUSION("LMUpdateExclusion", PERS, 1, "LMUpdateExclusion"),
    LP_LM_ALPHA("LMAlpha", PERS, 49, "LMAlpha"),
    LP_LM_BETA("LMBeta", PERS, 77, "LMBeta"),
    //LP_LM_MIXTURE("LMMixture", PERS, 50, "LMMixture"),
    LP_MOUSE_POS_BOX("MousePosBox", !PERS, -1, "Mouse Position Box Indicator"),
    LP_NORMALIZATION("Normalization", !PERS, 1 << 16, "Interval for child nodes"),
    LP_LINE_WIDTH("LineWidth", PERS, 1, "Width to draw crosshair and mouse line"),
    //LP_LM_WORD_ALPHA("WordAlpha", PERS, 50, "Alpha value for word-based model"),
    LP_USER_LOG_LEVEL_MASK("UserLogLevelMask", PERS, 0, "Controls level of user logging, 0 = none, 1 = short, 2 = detailed, 3 = both"),
    LP_ZOOMSTEPS("Zoomsteps", PERS, 32, "Integerised ratio of zoom size for click/button mode, denom 64."),
    LP_B("ButtonMenuBoxes", PERS, 4, "Number of boxes for button menu mode"),
    LP_S("ButtonMenuSafety", PERS, 25, "Safety parameter for button mode, in percent."),
    LP_R("ButtonModeNonuniformity", PERS, 0, "Button mode box non-uniformity"),
    //LP_Z("ButtonMenuBackwardsBox", PERS, 1, "Number of back-up boxes for button menu mode"),
    LP_RIGHTZOOM("ButtonCompassModeRightZoom", PERS, 5120, "Zoomfactor (*1024) for compass mode"),
    LP_BOOSTFACTOR("BoostFactor", !PERS, 100, "Boost/brake factor (multiplied by 100)"),
    LP_AUTOSPEED_SENSITIVITY("AutospeedSensitivity", PERS, 100, "Sensitivity of automatic speed control (percent)"),
    /*LP_SOCKET_PORT("SocketPort", PERS, 20320, "UDP/TCP socket to use for network socket input"),
    LP_SOCKET_INPUT_X_MIN("SocketInputXMinTimes1000", PERS, 0, "Bottom of range of X values expected from network input"),
    LP_SOCKET_INPUT_X_MAX("SocketInputXMaxTimes1000", PERS, 1000, "Top of range of X values expected from network input"),
    LP_SOCKET_INPUT_Y_MIN("SocketInputYMinTimes1000", PERS, 0, "Bottom of range of Y values expected from network input"),
    LP_SOCKET_INPUT_Y_MAX("SocketInputYMaxTimes1000", PERS, 1000, "Top of range of Y values expected from network input"),*/
    LP_OX("OX", PERS, 2048, "X coordinate of crosshair"),
    LP_OY("OY", PERS, 2048, "Y coordinate of crosshair"),
    LP_MAX_Y("MaxY", PERS, 4096, "Maximum Y coordinate"),
    //LP_INPUT_FILTER("InputFilterID", PERS, 3, "Module ID of input filter"),
    LP_CIRCLE_PERCENT("CirclePercent", PERS, 10, "Percentage of nominal vertical range to use for radius of start circle"),
    LP_TWO_BUTTON_OFFSET("TwoButtonOffset", PERS, 1024, "Offset for two button dynamic mode"),
    LP_TAP_TIME("TapTime", PERS, 100, "Max time for tap in Stylus Mode"),
    LP_NON_LINEAR_X("NonLinearX", PERS, 8, "Nonlinear compression of X-axis (0 = none, higher = more extreme)"),
    LP_DASHER_MARGIN("MarginWidth", PERS, 400, "Width of RHS margin (in Dasher co-ords)"),
    LP_NODE_BUDGET("NodeBudget", PERS, 1200, "Target number of node objects"),
    LP_BUTTON_SCAN_TIME("ButtonScanTime", PERS, 0, "Scanning interval in button mode (0 = don't scan)"),
    LP_MIN_NODE_SIZE_TEXT("MinNodeSizeForText", PERS, 40, "Minimum size for node to have text (4096=whole screen)");

    private Elp_parameters(String rName, boolean pers, int def, String hr) {
        this(rName, pers, (long) def, hr);
    }

    private Elp_parameters(String rName, boolean pers, Long def, String hr) {
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

    public Long defaultValue() {
        return defaultVal;
    }

    public void reset(SettingsManager sm) {
        sm.setSetting(this, defaultVal);
    }
    private final String regName;
    final boolean persistent;
    final Long defaultVal;
    final String humanReadable;

    public Class<Long> type() {
        return Long.class;
    }

    public boolean isPersistent() {
        return persistent;
    }
}
