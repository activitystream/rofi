package com.activitystream.rofi;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SwitchValue {
    SwitchesSource src1 = mock(SwitchesSource.class);

    @Test
    public void should_return_false_when_ask_if_on(){
        when(src1.switchValue(anyString())).thenReturn("0");

        final Switches switches = new Switches(src1);

        assertThat(switches.isOn("fle"), is(equalTo(false)));

    }

    @Test
    public void should_return_true_when_asked_if_off(){
        when(src1.switchValue(anyString())).thenReturn("0");

        final Switches switches = new Switches(src1);

        assertThat(switches.isOff("fle"), is(equalTo(true)));

    }
}
