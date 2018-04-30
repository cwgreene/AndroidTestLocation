package com.cwgreene.testlocation.loggers;

import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.util.Log;

import java.util.function.Function;

public class TriggerSensorLogger extends TriggerEventListener {
    private final Function<float[], Void> callback;
    String sensorName;
    public TriggerSensorLogger(String name, Function<float[], Void> callback) {
        this.sensorName = name;
        this.callback = callback;
    }

    @Override
    public void onTrigger(TriggerEvent triggerEvent) {
        Log.i("DOOM", triggerEvent.toString());
        callback.apply(triggerEvent.values);
    }
}
