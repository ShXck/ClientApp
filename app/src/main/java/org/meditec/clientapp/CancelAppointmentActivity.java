package org.meditec.clientapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.meditec.clientapp.network.JSONHandler;
import org.meditec.clientapp.network.RequestManager;

public class CancelAppointmentActivity extends AppCompatActivity {

    private TextView appointment_code;
    private TextView appointment_date;
    private Button cancel_button;
    private ListView details_menu;
    private ListAdapter adapter;

    private String symptoms;
    private String tests;
    private String cases;
    private String medication;
    private boolean can_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_appointment);

        String[] options = {"Síntomas","Exámenes", "Medicación" , "Casos clínicos"};
        details_menu = (ListView)findViewById(R.id.details_menu);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,options);
        details_menu.setAdapter(adapter);
        appointment_code = (TextView)findViewById(R.id.appointment_code);
        appointment_date = (TextView)findViewById(R.id.appointment_date);
        cancel_button = (Button)findViewById(R.id.cancel_button);

        get_appointment_info();

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!can_cancel){
                    show_dialog();
                }else {
                    invalid_dialog();
                }
            }
        });

        get_details();
    }

    /**
     * listener del menú.
     */
    private void get_details() {
        details_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                show_detail_dialog((String)parent.getItemAtPosition(position));
            }
        });
    }

    /**
     * Muestra una advertencia cuando se oprime el botón de cancelar.
     */
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

    /**
     * Muestra un mensaje cuando se quiere hacer una acción inválida.
     */
    private void invalid_dialog(){

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Acción inválida");
        dialog.setMessage("Tu cita ha terminado, no puedes cancelarla ahora");
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * Muestra los detalles seleccionados.
     * @param option
     */
    private void show_detail_dialog(String option){

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle(option);
        switch (option){
            case "Síntomas":
                dialog.setMessage(symptoms);
                break;
            case "Exámenes":
                dialog.setMessage(tests);
                break;
            case "Medicación":
                dialog.setMessage(medication);
                break;
            case "Casos clínicos":
                dialog.setMessage(cases);
                break;
        }
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * petición para cancelar una cita.
     */
    private void remove_appointment() {
        RequestManager.DELETE(LoginActivity.client_name + "/appointments", JSONHandler.delete_appointment(BookAppointmentActivity.code_picked));

        Intent menu = new Intent(CancelAppointmentActivity.this, MainMenuActivity.class);
        startActivity(menu);
    }

    /**
     * petición para obtener los detalles de la cita.
     */
    private void get_appointment_info() {
        RequestManager.GET(LoginActivity.client_name + "/appointments");
        RequestManager.wait_for_response(1000);
        process_appointment(RequestManager.GET_REQUEST_DATA());
    }

    /**
     * Procesa los detalles de la cita.
     * @param json_appointment la cita en formato json.
     */
    private void process_appointment(String json_appointment){
        try {
            JSONObject appointment = new JSONObject(json_appointment);

            appointment_code.setText("Código de cita: " + appointment.getString("code"));
            appointment_date.setText("Fecha: " + appointment.getString("date"));
            symptoms = "Síntomas: " + appointment.getString("symptoms");
            tests = "Exámenes: " + appointment.getString("tests");
            medication = "Medicación: " + appointment.getString("medication");
            cases = "Casos clínicos: " + appointment.getString("cases");
            can_cancel = appointment.getBoolean("finished");

        }catch (JSONException x){
            x.printStackTrace();
        }
    }
}
