package com.activitystream.rofi;

public class Switches {
    private SwitchesSource[] sources;

    public Switches(SwitchesSource... sources) {
        this.sources = sources;
    }

    public boolean isOn(String feature) {
        for (SwitchesSource source : sources) {
            final String value = source.featureValue(feature);
            if (value != null) return value.equalsIgnoreCase("1") || value.equalsIgnoreCase("TRUE");
        }
        return false;
    }
}
