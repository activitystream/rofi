package com.activitystream.rofi.sources;

import com.activitystream.rofi.SwitchesSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesSwitchesSource implements SwitchesSource {

    private final Properties properties;

    public PropertiesSwitchesSource(String... propertiesFiles) {
        properties = new Properties();
        for (String propertyFile : propertiesFiles) {
            try {
                try (InputStream inputStream = this.getClass().getResourceAsStream(propertyFile)) {
                    properties.load(inputStream);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String switchValue(String feature) {
        return properties.getProperty(feature);
    }
}
