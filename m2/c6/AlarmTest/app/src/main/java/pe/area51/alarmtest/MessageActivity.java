package pe.area51.alarmtest;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MessageActivity extends AppCompatActivity {

    private Ringtone ringtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        findViewById(R.id.button_dismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRingtone();
                finish();
            }
        });
        initRingtone();
        playRingtone();
    }

    private void initRingtone() {
        try {
            ringtone = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        } catch (Exception e) {
            Toast.makeText(MessageActivity.this, R.string.ringtone_not_available, Toast.LENGTH_SHORT).show();
        }
    }

    private void playRingtone() {
        if (ringtone != null) {
            ringtone.play();
        }
    }

    private void stopRingtone() {
        if (ringtone != null) {
            ringtone.stop();
        }
    }
}
