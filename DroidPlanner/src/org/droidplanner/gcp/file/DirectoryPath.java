package org.droidplanner.gcp.file;

import android.os.Environment;

public class DirectoryPath {

	static public String getDroidPlannerPath() {
		String root = Environment.getExternalStorageDirectory().getPath();
		return (root + "/DroidPlanner/");
	}

	static public String getGCPPath() {
		return getDroidPlannerPath() + "/GCP/";
	}

	static public String getMapsPath() {
		return getDroidPlannerPath() + "/Maps/";
	}

}
