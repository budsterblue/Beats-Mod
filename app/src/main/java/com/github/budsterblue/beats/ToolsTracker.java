package com.github.budsterblue.beats;

import java.util.HashMap;

import android.util.Log;


public class ToolsTracker {
	private static final String appVersion = "";
	
	// Called by MenuHome.onDestroy

	public static void info(String event) {
		Log.i("Beats info:", appVersion + "Info: " + event);
	}
	
	public static void error(String event, Throwable e, String value) {
		// logcat this
		String ev = appVersion + "Error: " + event;
		String atr = e.getMessage();
		Log.e("Beats exception:", ev + " / " + atr + " / " + value);
	}
	
	public static void data(String event, String attribute, String value) {
		Log.v("Beats data (str):", appVersion + "Data: " + event + " / " + attribute + " / " + value);
	}
	
	public static void data(String event, HashMap<String,String> attributes) {
		Log.v("Beats data (hash):", appVersion + "Data: " + event + " / " + attributes);
	}
	
}
