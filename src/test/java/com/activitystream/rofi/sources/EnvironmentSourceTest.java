package com.activitystream.rofi.sources;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class EnvironmentSourceTest {

    @Test
    public void should_return_null_when_undefined(){
        Map<String, String> env = new HashMap<String, String>(){{
        }};
        final EnvironmentSwitchParser environmentSwitches = new EnvironmentSwitchParser(getGetenv(env));

        assertThat(environmentSwitches.switchValue("a"), is(equalTo(null)));
    }
    @Test
    public void should_read_uppercase_variable(){
        Map<String, String> env = new HashMap<String, String>(){{
            put("A", "1");
        }};
        final EnvironmentSwitchParser environmentSwitches = new EnvironmentSwitchParser(getGetenv(env));

        assertThat(environmentSwitches.switchValue("a"), is(equalTo("1")));
    }
    @Test
    public void should_prefer_normalcase_variable(){
        Map<String, String> env = new HashMap<String, String>(){{
            put("a", "1");
            put("A", "2");
        }};
        final EnvironmentSwitchParser environmentSwitches = new EnvironmentSwitchParser(getGetenv(env));

        assertThat(environmentSwitches.switchValue("a"), is(equalTo("1")));
    }

    @Test
    public void should_lookup_in_feature_list(){
        Map<String, String> env = new HashMap<String, String>(){{
            put("FEATURES_ON", "a,b, c");
        }};
        final EnvironmentSwitchParser environmentSwitches = new EnvironmentSwitchParser(getGetenv(env), "FEATURES_ON", "FEATURES_OFF");

        assertThat(environmentSwitches.switchValue("c"), is(equalTo("1")));
        assertThat(environmentSwitches.switchValue("a"), is(equalTo("1")));
        assertThat(environmentSwitches.switchValue("b"), is(equalTo("1")));
        assertThat(environmentSwitches.switchValue("d"), is(equalTo(null)));
    }
    @Test
    public void should_allow_all_when_list_is_wildcard(){
        Map<String, String> env = new HashMap<String, String>(){{
            put("FEATURES_ON", "*");
        }};
        final EnvironmentSwitchParser environmentSwitches = new EnvironmentSwitchParser(getGetenv(env), "FEATURES_ON", "FEATURES_OFF");

        assertThat(environmentSwitches.switchValue("c"), is(equalTo("1")));
        assertThat(environmentSwitches.switchValue("a"), is(equalTo("1")));
        assertThat(environmentSwitches.switchValue("b"), is(equalTo("1")));
        assertThat(environmentSwitches.switchValue("whatever"), is(equalTo("1")));
    }

    @Test
    public void should_allow_all_when_list_is_wildcard_except_those_that_are_explicitly_off(){
        Map<String, String> env = new HashMap<String, String>(){{
            put("FEATURES_ON", "*");
            put("FEATURES_OFF", "a");
        }};
        final EnvironmentSwitchParser environmentSwitches = new EnvironmentSwitchParser(getGetenv(env), "FEATURES_ON", "FEATURES_OFF");

        assertThat(environmentSwitches.switchValue("c"), is(equalTo("1")));
        assertThat(environmentSwitches.switchValue("a"), is(equalTo("0")));
        assertThat(environmentSwitches.switchValue("b"), is(equalTo("1")));
        assertThat(environmentSwitches.switchValue("whatever"), is(equalTo("1")));
    }

    @Test
    public void should_disable_all_when_list_is_wildcard_even_those_that_are_explicitly_on(){
        Map<String, String> env = new HashMap<String, String>(){{
            put("FEATURES_ON", "a");
            put("FEATURES_OFF", "*");
        }};
        final EnvironmentSwitchParser environmentSwitches = new EnvironmentSwitchParser(getGetenv(env), "FEATURES_ON", "FEATURES_OFF");

        assertThat(environmentSwitches.switchValue("c"), is(equalTo("0")));
        assertThat(environmentSwitches.switchValue("a"), is(equalTo("0")));
        assertThat(environmentSwitches.switchValue("b"), is(equalTo("0")));
        assertThat(environmentSwitches.switchValue("whatever"), is(equalTo("0")));
    }

    private Function<String, String> getGetenv(Map<String, String> env) {return s -> env.containsKey(s) ? env.get(s) : null;}
}
