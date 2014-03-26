package org.droidplanner.gcp.file;

import java.io.File;
import java.io.FilenameFilter;

public class FileList {
	static public String[] getKMZFileList() {
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String filename) {
				return filename.contains(".kml") || filename.contains(".kmz");
			}
		};
		String gcpPath = DirectoryPath.getGCPPath();
		return getFileList(gcpPath, filter);
	}

	static public String[] getFileList(String path, FilenameFilter filter) {
		File mPath = new File(path);
		try {
			mPath.mkdirs();
			if (mPath.exists()) {
				return mPath.list(filter);
			}
		} catch (SecurityException e) {
		}
		return new String[0];
	}

}
