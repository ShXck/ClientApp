package org.meditec.clientapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.meditec.clientapp.network.JSONHandler;
import org.meditec.clientapp.network.RequestManager;

public class CancelAppointmentActivity extends AppCompatActivity {

    private TextView appointment_code;
    private TextView appointment_date;
    private TextView symptoms_text;
    private Button cancel_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_appointment);

        appointment_code = (TextView)findViewById(R.id.appointment_code);
        appointment_date = (TextView)findViewById(R.id.appointment_date);
        symptoms_text = (TextView)findViewById(R.id.symptoms_text);
        cancel_button = (Button)findViewById(R.id.cancel_button);

        get_appointment_info();

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_dialog();
            }
        });
    }

    private void show_dialog(){

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Eliminar cita");
        dialog.setMessage("Está seguro de que quiere eliminar la cita. La decisión es final.");
        dialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                remove_appointment();
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void remove_appointment() {
        RequestManager.DELETE(LoginActivity.client_name + "/appointments", JSONHandler.delete_appointment(BookAppointmentActivity.code_picked));
    }

    private void get_appointment_info() {
        RequestManager.GET(LoginActivity.client_name + "/appointments");
        RequestManager.wait_for_response(500);
        process_appointment(RequestManager.GET_REQUEST_DATA());
    }

    private void process_appointment(String json_appointment){
        try {
            JSONObject appointment = new JSONObject(json_appointment);

            appointment_code.setText("Código de cita: " + appointment.getString("code"));
            appointment_date.setText("Fecha: " + appointment.getString("date"));
            symptoms_text.setText("Síntomas: " + appointment.getString("symptoms"));
        }catch (JSONException x){
            x.printStackTrace();
        }
    }
}
