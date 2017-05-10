package org.meditec.clientapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.meditec.clientapp.network.RequestManager;

public class PaymentActivity extends AppCompatActivity {

    private TextView appointment_code;
    private TextView appointment_cost;
    private Button pay_button;
    private boolean can_pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        get_apppointment_info();

        appointment_code = (TextView)findViewById(R.id.code);
        appointment_cost = (TextView)findViewById(R.id.cost_text);
        pay_button = (Button)findViewById(R.id.pay_button);

        process_info(RequestManager.GET_REQUEST_DATA());

        pay();

    }

    private void pay() {
        pay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (can_pay) {
                    RequestManager.DELETE(LoginActivity.client_name + "/pay", "");

                    Intent menu = new Intent(PaymentActivity.this, MainMenuActivity.class);
                    startActivity(menu);
                }else {
                    show_dialog();
                }
            }
        });
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

    private void get_apppointment_info() {
        RequestManager.GET(LoginActivity.client_name + "/appointments");
        RequestManager.wait_for_response(500);
    }

    private void process_info(String info){
        try {
            JSONObject details = new JSONObject(info);

            appointment_code.setText("Código de cita: " + details.getString("code"));
            appointment_cost.setText("Monto total: " + details.getString("price"));
            can_pay = details.getBoolean("finished");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
