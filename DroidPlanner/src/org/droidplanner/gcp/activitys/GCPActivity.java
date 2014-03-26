package org.droidplanner.gcp.activitys;

import java.util.ArrayList;
import java.util.List;

import org.droidplanner.gcp.R;
import org.droidplanner.gcp.dialogs.openfile.OpenFileDialog;
import org.droidplanner.gcp.dialogs.openfile.OpenGcpFileDialog;
import org.droidplanner.gcp.file.IO.GcpReader;
import org.droidplanner.gcp.fragments.GcpMapFragment;
import org.droidplanner.gcp.fragments.GcpMapFragment.OnGcpClickListner;
import org.droidplanner.gcp.fragments.markers.MarkerManager.MarkerSource;
import org.droidplanner.gcp.gcp.Gcp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class GCPActivity extends Activity implements OnGcpClickListner {

	public List<Gcp> gcpList;
	private GcpMapFragment gcpMapFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

		setContentView(R.layout.activity_gcp);

		gcpList = new ArrayList<Gcp>();

		gcpMapFragment = ((GcpMapFragment) getFragmentManager()
				.findFragmentById(R.id.gcpMapFragment));
		clearWaypointsAndUpdate();

		checkIntent();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		case R.id.menu_clear:
			clearWaypointsAndUpdate();
			return true;
		case R.id.menu_open_kmz:
			openGcpFile();
			return true;
		case R.id.menu_zoom:
			gcpMapFragment.zoomToExtents(getGcpCoordinates());
		default:
			return super.onMenuItemSelected(featureId, item);
		}
	}

	private List<LatLng> getGcpCoordinates() {
		List<LatLng> result = new ArrayList<LatLng>();
		for (Gcp latLng : gcpList) {
			result.add(latLng.coord);
		}
		return result;
	}

	public void openGcpFile() {
		OpenFileDialog dialog = new OpenGcpFileDialog() {
			@Override
			public void onGcpFileLoaded(List<Gcp> list) {
				if (list != null) {
					putListToGcp(list);
				}
			}
		};
		dialog.openDialog(this);
	}

	private void putListToGcp(List<Gcp> list) {
		gcpList.clear();
		gcpList.addAll(list);
		gcpMapFragment.markers.updateMarkers(gcpList, false,
				getApplicationContext());
		gcpMapFragment.zoomToExtents(getGcpCoordinates());
	}

	private void checkIntent() {
		Intent intent = getIntent();
		String action = intent.getAction();
		String type = intent.getType();
		if (Intent.ACTION_VIEW.equals(action) && type != null) {
			Toast.makeText(this, intent.getData().getPath(), Toast.LENGTH_LONG)
					.show();
			GcpReader parser = (new GcpReader());
			boolean fileIsOpen = parser.openGCPFile(intent.getData().getPath());
			if (fileIsOpen) {
				putListToGcp(parser.gcpList);
			}
		}
	}

	public void clearWaypointsAndUpdate() {
		gcpList.clear();
		gcpMapFragment.markers.clear();
	}

	@Override
	public void onGcpClick(MarkerSource gcp) {
		((org.droidplanner.gcp.gcp.Gcp) gcp).toogleState();
		gcpMapFragment.markers
				.updateMarker(gcp, false, getApplicationContext());
	}

}
