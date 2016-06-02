package com.activitystream.rofi;

public class Switches {
    private SwitchesSource[] sources;

    public Switches(SwitchesSource... sources) {
        this.sources = sources;
    }

    public boolean isOn(String switchName) {
        for (SwitchesSource source : sources) {
            final String value = source.switchValue(switchName);
            if (value != null) return value.equalsIgnoreCase("1") || value.equalsIgnoreCase("TRUE");
        }
        return false;
    }
}
