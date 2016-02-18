package pe.area51.listadapter;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {

    public static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }
}
