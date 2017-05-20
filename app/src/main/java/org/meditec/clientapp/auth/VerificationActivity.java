package org.meditec.clientapp.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.meditec.clientapp.MainMenuActivity;
import org.meditec.clientapp.R;
import org.meditec.clientapp.network.RequestManager;

public class VerificationActivity extends AppCompatActivity {

    private Button scan_button;
    private TextView message;
    private ProgressDialog progress_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        progress_dialog = new ProgressDialog(this);
        progress_dialog.setMessage("Verificando cuenta");
        progress_dialog.show();
        scan_button = (Button)findViewById(R.id.scan_button);
        message = (TextView)findViewById(R.id.message_text);

        check_status();

    }

    private void check_status(){

        AsyncTask.execute(new Runnable() {
            boolean is_blocked = true;
            @Override
            public void run() {
                while (is_blocked){
                    if (RequestManager.GET_REQUEST_DATA() != null) {
                        if (RequestManager.GET_REQUEST_DATA().equals("{status:unblocked}")) {
                            progress_dialog.dismiss();
                            Intent menu = new Intent(VerificationActivity.this, MainMenuActivity.class);
                            menu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(menu);
                            is_blocked = false;
                        }

                        if (RequestManager.GET_REQUEST_DATA().equals("{status:blocked}")){
                            progress_dialog.dismiss();
                            Intent scan = new Intent(VerificationActivity.this, QRCodeActivity.class);
                            scan.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(scan);
                            is_blocked = false;
                        }
                    }
                }
            }
        });
    }
}
