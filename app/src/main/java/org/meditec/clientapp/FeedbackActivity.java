package org.meditec.clientapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.meditec.clientapp.network.JSONHandler;
import org.meditec.clientapp.network.RequestManager;

public class FeedbackActivity extends AppCompatActivity {

    private RatingBar rating_bar;
    private TextView medic_text_view;
    private EditText comment_section_field;
    private Button send_button;

    private String code;
    private boolean can_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        rating_bar = (RatingBar)findViewById(R.id.rating_bar);
        medic_text_view = (TextView)findViewById(R.id.medic_text);
        send_button = (Button)findViewById(R.id.send_button);
        comment_section_field = (EditText)findViewById(R.id.comment_section);

        get_last_appointment_info();

        set_listeners();
    }

    private void get_last_appointment_info() {
        RequestManager.GET(LoginActivity.client_name + "/appointments/last");
        RequestManager.wait_for_response(1000);
        set_ui(RequestManager.GET_REQUEST_DATA());
    }

    private void set_ui(String data){
        try {
            JSONObject json_data = new JSONObject(data);
            medic_text_view.setText("Médico: " + json_data.getString("medic"));
            code = json_data.getString("medic");
            can_comment = json_data.getBoolean("finished");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void set_listeners() {

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (can_comment) {
                    send_comments();
                    Toast.makeText(getApplicationContext(), "Gracias por colaborar", Toast.LENGTH_SHORT).show();
                }else {
                    show_dialog();
                }
            }
        });
    }

    private void send_comments(){
        RequestManager.POST(LoginActivity.client_name + "/rate", JSONHandler.build_json_comments(comment_section_field.getText().toString(), code));
        Intent menu = new Intent(FeedbackActivity.this, MainMenuActivity.class);
        menu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(menu);
    }

    private void show_dialog(){

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Acción inválida");
        dialog.setMessage("Tu cita no ha terminado, puedes pagarla cuando el médico marque tu cita como terminada");
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
