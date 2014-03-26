package com.droidplanner;

import android.app.Application;

import com.droidplanner.helpers.TTS;

public class GCPApp extends Application  {
	private TTS tts;
	@Override
	public void onCreate() {
		super.onCreate();

		tts = new TTS(this);
	}

}
