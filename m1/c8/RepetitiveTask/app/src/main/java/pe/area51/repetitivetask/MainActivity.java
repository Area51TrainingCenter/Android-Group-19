package pe.area51.repetitivetask;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int FREQUENCY_IN_MILLIS = 100;

    private TextView displayTextView;
    private Button toggleRepetitionButton;

    private RepetitiveTask repetitiveTask;

    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayTextView = (TextView) findViewById(R.id.textview_display);
        toggleRepetitionButton = (Button) findViewById(R.id.button_toggle_repetition);
        toggleRepetitionButton.setOnClickListener(this);
        toggleRepetitionButton.setText(R.string.start_handler_updates);
        repetitiveTask = new RepetitiveTask(FREQUENCY_IN_MILLIS, new Runnable() {
            @Override
            public void run() {
                addCount();
                updateDisplay();
            }
        });
    }

    private void resetCounter() {
        this.counter = 0;
    }

    private void addCount() {
        /*
        Este es nuestro acumulador para las repeticiones. No se debería
        utilizar este método para hacer un cronógrafo, ya que el Handler
        no ejecuta la actualización en el tiempo exacto. Debe verse esto
        como un método para realizar actualizaciones o refrescos, pero
        considerando siempre que el tiempo no va a ser exacto.
         */
        this.counter = counter + 1;
        /*
        En su lugar si se desea hacer un cronógrafo, puede calcularse la
        diferencia de tiempo entre actualizaciones (obteniendo el tiempo
        con el método "SystemClock.elapsedRealtime()" por ejemplo) y
        utilizarse el Handler únicamente para actualizar la vista.
         */
    }

    private void updateDisplay() {
        displayTextView.setText(String.valueOf(counter));
    }

    @Override
    public void onClick(View v) {
        if (!repetitiveTask.isRunning()) {
            toggleRepetitionButton.setText(R.string.stop_handler_updates);
            repetitiveTask.start(false);
        } else {
            toggleRepetitionButton.setText(R.string.start_handler_updates);
            repetitiveTask.stop();
            resetCounter();
            updateDisplay();
        }
    }
}
