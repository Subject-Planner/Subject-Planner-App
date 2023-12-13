package com.demo.subjectplanner.activity;

import android.app.Application;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.core.Amplify;

public class SubjectPlannerAmplifyApplication extends Application {
    public static final String TAG = "subjectPlannerApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            Amplify.addPlugin(new AWSApiPlugin());
//            Amplify.addPlugin(new AWSCognitoAuthPlugin());
//            Amplify.addPlugin(new AWSPinpointAnalyticsPlugin(this));
//            Amplify.addPlugin(new AWSPredictionsPlugin());
//            Amplify.addPlugin(new AWSS3StoragePlugin());
//            Amplify.addPlugin(new AWSLocationGeoPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException ae) {
            Log.e(TAG, "Error initializing Amplify" + ae.getMessage(), ae);
        }
    }
}
