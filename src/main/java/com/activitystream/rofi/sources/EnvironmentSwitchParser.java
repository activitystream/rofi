package com.activitystream.rofi.sources;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EnvironmentSwitchParser {
    static final String NONE = "24234NONSDFWERWEE";
    static final String ALL_SWITCHES = "*";
    final List<String> enabledSwitches;
    final List<String> disabledSwitches;
    private final Function<String, String> getenv;

    public EnvironmentSwitchParser(Function<String, String> getenv)  {
        this(getenv, NONE, NONE);
    }

    public EnvironmentSwitchParser(Function<String, String> getenv, String envVariableWithEnabled, String envVariableWithDisabled){
        this.getenv = getenv;
        envVariableWithEnabled = getenv.apply(envVariableWithEnabled);
        envVariableWithDisabled = getenv.apply(envVariableWithDisabled);
        if (envVariableWithDisabled == null) envVariableWithDisabled = "";
        if (envVariableWithEnabled == null) envVariableWithEnabled = "";

        enabledSwitches = Arrays.asList(envVariableWithEnabled.split(",")).stream().map(String::trim).collect(Collectors.toList());
        disabledSwitches = Arrays.asList(envVariableWithDisabled.split(",")).stream().map(String::trim).collect(Collectors.toList());
    }

    public String switchValue(String switch_) {
        final String upperCaseValue = getenv.apply(switch_.toUpperCase());
        final String value = getenv.apply(switch_);
        final boolean onDisabledList = disabledSwitches.indexOf(switch_) != -1;
        final boolean onEnabledList = enabledSwitches.indexOf(switch_) != -1;

        if (value != null) return value;
        if (upperCaseValue != null) return upperCaseValue;
        if (onDisabledList || disabledSwitches.indexOf(ALL_SWITCHES) > -1) return "0";
        if (onEnabledList || enabledSwitches.indexOf(ALL_SWITCHES) > -1) return "1";
        return null;
    }
}
