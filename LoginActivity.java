package org.meditec.clientapp;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import org.json.JSONException;
import org.json.JSONObject;
import org.meditec.clientapp.network.JSONHandler;
import org.meditec.clientapp.network.RequestManager;
import java.util.Arrays;
public class LoginActivity extends AppCompatActivity {

    public static EditText client_name, client_email;
    private Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        client_name = (EditText) findViewById(R.id.nombre);
        client_email = (EditText) findViewById(R.id.email);
        boton = (Button)findViewById(R.id.button);

    }

    public void ingresar(View v){
        get_user_info();
        Intent menu = new Intent(LoginActivity.this, MainMenuActivity.class);
        startActivity(menu);
    }

    private void get_user_info() {
        RequestManager.POST("login", JSONHandler.get_json_patient_info(client_name.getText().toString(), client_email.getText().toString()));
    }
}
