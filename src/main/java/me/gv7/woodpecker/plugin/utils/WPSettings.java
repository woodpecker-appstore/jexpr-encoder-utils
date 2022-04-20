package me.gv7.woodpecker.plugin.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class WPSettings {
    private Map<String, Object> _settings;

    public WPSettings(Map<String, Object> settings) {
        this._settings = settings;
    }

    public String getString(String key) {
        return getString(key, null);
    }

    public String getString(String key, String defaultValue) {
        if (_settings.containsKey(key)) {
            return _settings.get(key).toString();
        } else {
            return defaultValue;
        }
    }

    public int getInteger(String key) {
        return getInteger(key, null);
    }

    public Integer getInteger(String key, Integer defaultValue) {
        if (_settings.containsKey(key)) {
            return Integer.parseInt(_settings.get(key).toString());
        } else {
            return defaultValue;
        }
    }

    public String getFileContent(String key) {
        if (_settings.containsKey(key)) {
            String filePath = _settings.get(key).toString();
            File file = new File(filePath);
            if (file.exists() && file.isFile()) {
                try {
                    return String.join("\r\n", Files.readAllLines(Paths.get(filePath)));
                } catch (Exception ex) {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
