package org.meditec.clientapp;

import android.app.DialogFragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class SymptomsRegisterActivity extends AppCompatActivity {

    private ImageButton voice_button;
    private TextView said_text;
    private ListView said_list;
    private Button add_button;
    private Button save_button;
    private ArrayAdapter adapter;

    private static final int RECOGNIZE_SPEECH_ACTIVITY = 100;
    private String recorded_temp;
    public static String recorded;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms_register);

        voice_button = (ImageButton) findViewById(R.id.voice_button);
        said_list = (ListView)findViewById(R.id.recorded_symptoms);
        said_text = (TextView)findViewById(R.id.recorded_text);
        add_button = (Button)findViewById(R.id.add_button);
        save_button = (Button)findViewById(R.id.send_symptoms_button);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        said_list.setAdapter(adapter);
        recorded = "";
        recorded_temp = "";

        set_listeners();

    }

    private void set_listeners() {
        voice_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                record();
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment date_picker = new DatePickerFragment();
                date_picker.show(getFragmentManager(),"Date Picker");
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_symptom(recorded_temp);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RECOGNIZE_SPEECH_ACTIVITY:

                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> speech = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String strSpeech2Text = speech.get(0);

                    said_text.setText("Usted dijo: " + strSpeech2Text);
                    recorded_temp = strSpeech2Text;

                }
                break;
        }
    }

    private void record(){
        Intent intentActionRecognizeSpeech = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intentActionRecognizeSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-MX");
        try {
            startActivityForResult(intentActionRecognizeSpeech, RECOGNIZE_SPEECH_ACTIVITY);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), "Tú dispositivo no soporta el reconocimiento por voz", Toast.LENGTH_SHORT).show();
        }
    }

    private void add_symptom(String recorded_text){
        recorded += recorded_text + ",";
        adapter.add(recorded_text);
        adapter.notifyDataSetChanged();
    }
}
