package org.meditec.clientapp;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AppointmentProgram extends AppCompatActivity {

    public static TextView medic,grabar;
    private ImageButton hablar;
    private ListView view_symptoms;
    private ListAdapter adapter;

    private static final int RECOGNIZE_SPEECH_ACTIVITY = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_program);

        medic = (TextView) findViewById(R.id.medic);
        medic.setText(BookAppointmentActivity.code_picked);

        grabar = (TextView) findViewById(R.id.txtGrabarVoz);
        hablar = (ImageButton) findViewById(R.id.img_btn_hablar);
        hablar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                onClickImgBtnHablar();
            }
        });

        view_symptoms = (ListView)findViewById(R.id.symp);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, BookAppointmentActivity.getSymptoms());
        view_symptoms.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RECOGNIZE_SPEECH_ACTIVITY:

                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> speech = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String strSpeech2Text = speech.get(0);

                    grabar.setText(strSpeech2Text);
                }

                break;
        }
    }

    public void onClickImgBtnHablar() {
        Intent intentActionRecognizeSpeech = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intentActionRecognizeSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-MX");
        try {
            startActivityForResult(intentActionRecognizeSpeech, RECOGNIZE_SPEECH_ACTIVITY);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), "Tú dispositivo no soporta el reconocimiento por voz", Toast.LENGTH_SHORT).show();
        }
    }


    public void añadir(View v) {
        try {
            if (contarPalabras(grabar.getText().toString()) == true) {
                BookAppointmentActivity.getSymptoms().add(grabar.getText().toString());
            } else {
                Toast.makeText(getApplicationContext(), "Solo puede ingresar una palabra", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), (CharSequence) e, Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(AppointmentProgram.this, AppointmentProgram.class);
        startActivity(intent);
    }

    public void enviarDatos(View v){
        get_date_picker();
    }

    private void get_date_picker() {
        DialogFragment date_picker = new DatePickerFragment();
        date_picker.show(getFragmentManager(),"Date Picker");
    }

    public boolean contarPalabras(String str) {
        String[] words = str.split(" ");
        int total = words.length;
        if(total>1){
            return false;
        }else{
            return true;
        }
    }
}

