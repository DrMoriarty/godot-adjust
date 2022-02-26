package ru.mobilap.adjust;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.os.Bundle;
import android.view.View;
import java.util.Map;
import java.util.List;
import java.util.Arrays;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Application.ActivityLifecycleCallbacks;

import androidx.annotation.NonNull;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustEvent;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.LogLevel;


import org.godotengine.godot.Dictionary;
import org.godotengine.godot.Godot;
import org.godotengine.godot.GodotLib;
import org.godotengine.godot.plugin.GodotPlugin;
import org.godotengine.godot.plugin.SignalInfo;

public class GodotAdjust extends GodotPlugin {

    private final String TAG = GodotAdjust.class.getName();

    public GodotAdjust(Godot godot) 
    {
        super(godot);
    }

    @Override
    public String getPluginName() {
        return "GodotAdjust";
    }

    @Override
    public List<String> getPluginMethods() {
        return Arrays.asList(
                "init",
                "track_event",
                "track_revenue"
        );
    }

    /*
    @Override
    public Set<SignalInfo> getPluginSignals() {
        return Collections.singleton(loggedInSignal);
    }
    */

    @Override
    public View onMainCreate(Activity activity) {
        // final Activity act = activity;
        // act.runOnUiThread(new Runnable() {
        //     @Override
        //     public void run() {

        //         String appToken = "opp0c2aasagw";
        //         String environment;
        //         AdjustConfig config;
        //         boolean ProductionMode = false;

        //         if (ProductionMode == true) {
        //             environment = AdjustConfig.ENVIRONMENT_PRODUCTION;
        //         }
        //         else {
        //             environment = AdjustConfig.ENVIRONMENT_SANDBOX;
        //         }
                
        //         config = new AdjustConfig(act.getApplicationContext(), appToken, environment);
        //         if (ProductionMode == true) {
        //             config.setLogLevel(LogLevel.SUPRESS);
        //         }
        //         else {
        //             config.setLogLevel(LogLevel.VERBOSE);
        //         }

        //         Adjust.onCreate(config);
                
        //         act.getApplication().registerActivityLifecycleCallbacks(new AdjustLifecycleCallbacks());
        //         Log.d(TAG,"Adjust plugin inited onCreate");
        //     }
        // });
        return null;
    }


    // Public methods

    public void init(final String token, final boolean ProductionMode)
    {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                String appToken = token;
                String environment;
                AdjustConfig config;

                if (ProductionMode == true) {
                    environment = AdjustConfig.ENVIRONMENT_PRODUCTION;
                }
                else {
                    environment = AdjustConfig.ENVIRONMENT_SANDBOX;
                }
                
                config = new AdjustConfig(getActivity().getApplicationContext(), appToken, environment);
                if (ProductionMode == true) {
                    config.setLogLevel(LogLevel.SUPRESS);
                }
                else {
                    config.setLogLevel(LogLevel.VERBOSE);
                }

                Adjust.onCreate(config);
                Adjust.onResume();
                
                getActivity().getApplication().registerActivityLifecycleCallbacks(new AdjustLifecycleCallbacks());


                Log.d(TAG,"Adjust plugin inited on Java");
            }
        });
    }

    private static final class AdjustLifecycleCallbacks implements ActivityLifecycleCallbacks {
        @Override
        public void onActivityCreated (Activity activity, 
                Bundle savedInstanceState) {
            
        }

        @Override
        public void onActivityStarted( Activity activity) {

        }

        @Override
         public void onActivityResumed(Activity activity) {
             Adjust.onResume();
         }

         @Override
         public void onActivityPaused(Activity activity) {
             Adjust.onPause();
         }

        @Override
        public void onActivityStopped( Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState( Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed( Activity activity) {

        }

        //...
     }
    
    public void track_event(final String event, final Dictionary params)
    {
        AdjustEvent adjustEvent = new AdjustEvent(event);
        Adjust.trackEvent(adjustEvent);
    }


    public void track_revenue(final String revenue, final String currency, final String signature, final String originalJson, final String public_key)
    {
        AdjustEvent adjustEvent = new AdjustEvent(signature);
        adjustEvent.setRevenue(Double.parseDouble(revenue), currency);
        Adjust.trackEvent(adjustEvent);
    }


    // Internal methods

    @Override
    public void onMainActivityResult (int requestCode, int resultCode, Intent data)
    {
    }
}
