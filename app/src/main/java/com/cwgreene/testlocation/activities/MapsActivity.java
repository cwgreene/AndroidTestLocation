package com.cwgreene.testlocation.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.cwgreene.testlocation.R;
import com.cwgreene.testlocation.loggers.SensorLogger;
import com.cwgreene.testlocation.loggers.WifiLogger;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager mLocationManager;
    private LocationProvider mLocationProvider;
    private SensorManager mSensorManager;
    private WifiLogger mWifiLogger;

    public LocationListener mLocationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            Log.e("Hello", "Hi!");
            CircleOptions options = new CircleOptions();
            LatLng latlng = new LatLng(location.getLatitude(),
                    location.getLongitude());
            options.center(latlng);
            options.radius(3);
            Log.e("PositionInfo", location.toString());
            if(mMap != null) {
                mMap.addCircle(options);
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    public boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.e("OHNO", "It failed!");
            return false;
        }
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mLocationManager =
                (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        mLocationProvider = mLocationManager.getProvider(LocationManager.GPS_PROVIDER);

        SensorLogger.LogSensors(this);
        if (!checkPermission()) {
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                500, .1f,
                mLocationListener);

    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        if (!checkPermission()) {
            return;
        }
        Location l = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Log.e("Position", l.toString());
        LatLng current = new LatLng(l.getLatitude(), l.getLongitude());
        mMap.addMarker(new MarkerOptions().position(current).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
    }
}
