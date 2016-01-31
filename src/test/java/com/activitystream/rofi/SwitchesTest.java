package com.activitystream.rofi;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.*;

public class SwitchesTest {
    SwitchesSource src1 = mock(SwitchesSource.class);
    SwitchesSource src2 = mock(SwitchesSource.class);
    SwitchesSource src3 = mock(SwitchesSource.class);

    @Test
    public void order_of_sources_dictates_priorities_of_value() {
        when(src1.featureValue(anyString())).thenReturn("0");
        when(src2.featureValue(anyString())).thenReturn("1");

        final Switches switches = new Switches(src1, src2);

        assertThat(switches.isOn("fle"), is(equalTo(false)));
    }

    @Test
    public void first_source_to_have_value_is_used() {
        when(src1.featureValue(anyString())).thenReturn(null);
        when(src2.featureValue(anyString())).thenReturn("0");
        when(src3.featureValue(anyString())).thenReturn("1");

        final Switches switches = new Switches(src1, src2, src3);

        assertThat(switches.isOn("fle"), is(equalTo(false)));
    }

    @Test
    public void when_no_value_is_found_in_any_of_the_sources_then_switch_is_off() {
        when(src1.featureValue(anyString())).thenReturn(null);

        final Switches switches = new Switches(src1);

        assertThat(switches.isOn("fle"), is(equalTo(false)));
    }

    @Test
    public void value_of_1_or_TRUE_turns_switch_on() {
        when(src1.featureValue(anyString())).thenReturn("1");
        when(src1.featureValue(anyString())).thenReturn("TRUE");
        when(src1.featureValue(anyString())).thenReturn("truE");

        final Switches switches = new Switches(src1);

        assertThat(switches.isOn("fle"), is(equalTo(true)));
        assertThat(switches.isOn("fle"), is(equalTo(true)));
        assertThat(switches.isOn("fle"), is(equalTo(true)));
    }

    @Test
    public void value_different_from_1_or_TRUE_turns_switch_off() {
        when(src1.featureValue(anyString())).thenReturn("2");
        when(src1.featureValue(anyString())).thenReturn("FOR REAL");
        when(src1.featureValue(anyString())).thenReturn("YES");

        final Switches switches = new Switches(src1);

        assertThat(switches.isOn("fle"), is(equalTo(false)));
        assertThat(switches.isOn("fle"), is(equalTo(false)));
        assertThat(switches.isOn("fle"), is(equalTo(false)));
    }
}
