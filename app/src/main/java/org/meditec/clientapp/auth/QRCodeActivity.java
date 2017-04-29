package org.meditec.clientapp.auth;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.meditec.clientapp.LoginActivity;
import org.meditec.clientapp.R;
import org.meditec.clientapp.network.JSONHandler;
import org.meditec.clientapp.network.RequestManager;

public class QRCodeActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    private Button pick_button;
    private Button email_button;
    private QRDecoder decoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        pick_button = (Button)findViewById(R.id.pick_button);
        email_button = (Button)findViewById(R.id.email_button);
        decoder = new QRDecoder();

        pick_image();
    }

    private void pick_image() {
        pick_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_gallery();
            }
        });

        email_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_email();
            }
        });
    }

    private void send_email() {
        Log.d("SENDING: ", JSONHandler.get_json_patient_info(LoginActivity.client_name, LoginActivity.client_email));
        RequestManager.POST("login", JSONHandler.get_json_patient_info(LoginActivity.client_name, LoginActivity.client_email));
    }

    private void open_gallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            //hacer aqui lo del escaneo interno con el imageUri.
        }
    }


}
