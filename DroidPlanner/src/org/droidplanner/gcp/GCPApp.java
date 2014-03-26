package org.droidplanner.gcp;

import org.droidplanner.gcp.helpers.TTS;

import android.app.Application;

public class GCPApp extends Application {
	private TTS tts;

	@Override
	public void onCreate() {
		super.onCreate();

		tts = new TTS(this);
	}

}
