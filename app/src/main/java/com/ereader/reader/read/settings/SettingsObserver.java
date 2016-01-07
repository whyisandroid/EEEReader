package com.ereader.reader.read.settings;

public interface SettingsObserver {

	public void onChange(String key, Object oldValue, Object newValue);
}
