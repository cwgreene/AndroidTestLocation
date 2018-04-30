package com.cwgreene.testlocation.loggers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

public class WifiLogger extends BroadcastReceiver {
    private final WifiManager mWifiManager;
    String TAG = WifiLogger.class.toString();
    String sensorName;

    public WifiLogger(String sesnsorName, Activity activity) {
        mWifiManager = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
        activity.registerReceiver(this,
                new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        this.sensorName = sesnsorName;
        mWifiManager.startScan();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
            List<ScanResult> scanResults = mWifiManager.getScanResults();
            ScanResult[] scanResultsArray = new ScanResult[scanResults.size()];
            scanResults.toArray(scanResultsArray);
            Log.i(sensorName, Arrays.toString(scanResultsArray));
        }
    }
}
