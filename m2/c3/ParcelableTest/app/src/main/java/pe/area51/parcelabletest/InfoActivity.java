package pe.area51.parcelabletest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by alumno on 5/01/16.
 */
public class InfoActivity extends AppCompatActivity {

    private TextView infoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        infoTextView = (TextView) findViewById(R.id.textview_info);
        final Person person = getIntent().getParcelableExtra(MainActivity.EXTRA_PERSON);
        showPerson(person);
    }

    private void showPerson(final Person person){
        infoTextView.setText(person.toString());
    }
}
