package org.meditec.clientapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private LoginButton button;
    private CallbackManager callback_manager;

    public static String client_name;
    public static String client_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        callback_manager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);

        button = (LoginButton)findViewById(R.id.login_button);

        button.setReadPermissions("email");

        check_login_status();
    }

    private void check_login_status() {
        button.registerCallback(callback_manager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                get_user_info(loginResult);
                Toast.makeText(getApplicationContext(), "Welcome to MediTEC - Client Services", Toast.LENGTH_SHORT);
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Login Canceled", Toast.LENGTH_SHORT);
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(getApplicationContext(), "There's been an error. Check your internet connection and try again", Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callback_manager.onActivityResult(requestCode, resultCode, data);
    }

    private void get_user_info(LoginResult result){
        AccessToken token = result.getAccessToken();

        GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.v("response:", response.toString());

                try{
                    client_name = object.getString("name");
                    client_email = object.getString("email");
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
        Bundle parameter = new Bundle();
        parameter.putString("fields", "name,email");
        request.setParameters(parameter);
        request.executeAsync();
    }
}