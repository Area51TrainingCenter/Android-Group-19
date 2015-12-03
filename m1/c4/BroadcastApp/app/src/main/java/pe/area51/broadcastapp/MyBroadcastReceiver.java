package pe.area51.broadcastapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String message;
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case Intent.ACTION_AIRPLANE_MODE_CHANGED:
                    String airplaneModeStateMessage;
                    if (intent.getBooleanExtra("state", false)) {
                        airplaneModeStateMessage = context.getString(R.string.enabled);
                    } else {
                        airplaneModeStateMessage = context.getString(R.string.disabled);
                    }
                    message = context.getString(R.string.broadcast_airplane_mode, airplaneModeStateMessage);
                    break;
                case LocationManager.PROVIDERS_CHANGED_ACTION:
                    message = context.getString(R.string.broadcast_location_provider_changed);
                    break;
                case MainActivity.MY_CUSTOM_BROADCAST:
                    message = context.getString(R.string.broadcast_my_custom_intent);
                    break;
                default:
                    message = context.getString(R.string.default_case);
                    break;
            }
        } else {
            message = context.getString(R.string.broadcast_explicit_intent);
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
