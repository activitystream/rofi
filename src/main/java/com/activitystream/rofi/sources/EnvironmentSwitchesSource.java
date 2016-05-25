package com.activitystream.rofi.sources;

import com.activitystream.rofi.SwitchesSource;

public class EnvironmentSwitchesSource implements SwitchesSource {

    private EnvironmentSwitchParser environmentParser;

    public EnvironmentSwitchesSource() {
        environmentParser = new EnvironmentSwitchParser(System::getenv);
    }

    public EnvironmentSwitchesSource(String envVariableWithEnabled, String envVariableWithDisabled) {
        environmentParser = new EnvironmentSwitchParser(System::getenv, envVariableWithEnabled, envVariableWithDisabled);
    }

    @Override
    public String switchValue(String switch_) {
        return environmentParser.switchValue(switch_);
    }
}
