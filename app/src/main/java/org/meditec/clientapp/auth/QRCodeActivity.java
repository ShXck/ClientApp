package org.meditec.clientapp.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.meditec.clientapp.LoginActivity;
import org.meditec.clientapp.MainMenuActivity;
import org.meditec.clientapp.R;
import org.meditec.clientapp.network.RequestManager;

public class QRCodeActivity extends AppCompatActivity {

    private Button scan_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        scan_button = (Button)findViewById(R.id.scan_button);
        scan_code();
    }

    /**
     * listener del botón.
     */
    private void scan_code() {
        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan();
            }
        });
    }

    /**
     * nos lleva a la pantalla de escaneo.
     */
    private void scan() {
        Intent scan_code = new Intent("com.google.zxing.client.android.SCAN");
        scan_code.putExtra("SCAN_MODE", "QR_CODE_MODE");
        startActivityForResult(scan_code,1);
    }

    /**
     * maneja la informacion obtenida en el escaneo.
     * @param requestCode el codigo.
     * @param resultCode el código del resultado.
     * @param data la actividad.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1)
            if (resultCode == QRCodeActivity.RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                String format = data.getStringExtra("SCAN_RESULT_FORMAT");
                unblock();
                get_main_screen();
            }
    }

    /**
     * obtiene el menu principal.
     */
    private void get_main_screen(){
        Intent menu = new Intent(this, MainMenuActivity.class);
        menu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(menu);
    }

    /**
     * Petición para desbloquear el app
     */
    private void unblock(){
        RequestManager.PUT(LoginActivity.client_name + "/unblock","{blocked:success}");
    }

}
