package org.droidplanner.gcp.fragments;

import org.droidplanner.gcp.fragments.helpers.OfflineMapFragment;
import org.droidplanner.gcp.fragments.markers.MarkerManager;
import org.droidplanner.gcp.fragments.markers.MarkerManager.MarkerSource;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.Marker;

public class GcpMapFragment extends OfflineMapFragment implements
		OnMarkerClickListener {

	private OnGcpClickListner mListener;

	public MarkerManager markers;

	private GoogleMap mMap;

	public interface OnGcpClickListner {
		void onGcpClick(MarkerSource gcp);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
			Bundle bundle) {
		View view = super.onCreateView(inflater, viewGroup, bundle);
		mMap = getMap();
		mMap.setOnMarkerClickListener(this);
		markers = new MarkerManager(mMap);
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mListener = (OnGcpClickListner) activity;
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		mListener.onGcpClick(markers.getSourceFromMarker(marker));
		return true;
	}

}
