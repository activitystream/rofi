package com.activitystream.rofi.sources;

import com.activitystream.rofi.SwitchesSource;

public class EnvironmentSwitches implements SwitchesSource {
    @Override
    public String featureValue(String feature) {
        return System.getenv(feature.toUpperCase());
    }
}
