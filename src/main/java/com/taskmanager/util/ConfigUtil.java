package com.taskmanager.util;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {

    @NotNull
    public static String getKey() throws IOException {
        @NotNull final String key = getPropertiesFile().getProperty("key");
        return key;
    }

    @NotNull
    public static String getSalt() throws IOException {
        @NotNull final String salt = getPropertiesFile().getProperty("salt");
        return salt;
    }

    public static Integer getCycle() throws IOException {
        @NotNull final Integer cycle = Integer.parseInt(getPropertiesFile().getProperty("cycle"));
        return cycle;
    }

    @NotNull
    private static Properties getPropertiesFile() throws IOException {
        @NotNull final Properties props = new Properties();
        @NotNull final InputStream is = ConfigUtil.class.getResourceAsStream("/META-INF/config.properties");
        props.load(is);
        is.close();
        return props;
    }
}
