package org.meditec.clientapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.meditec.clientapp.network.RequestManager;

public class MainMenuActivity extends AppCompatActivity {

    private ListView menu;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        menu = (ListView)findViewById(R.id.main_menu);

        String[] menu_options = {"Reservar cita", "Ver cita", "Pagar", "Califica nuestro servicio"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menu_options);

        menu.setAdapter(adapter);

        get_option_clicked();

    }

    /**
     * Listener del menú.
     */
    private void get_option_clicked() {
        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    get_appointment_booking_activity();
                }else if(position == 1){
                    get_cancel_appointment_screen();
                }else if (position == 2){
                    get_payment_screen();
                }else{
                    get_rating_screen();
                }
            }
        });
    }

    /**
     * obtiene la ventana para reservar.
     */
    private void get_appointment_booking_activity(){
        Intent book = new Intent(this, BookAppointmentActivity.class);
        startActivity(book);
    }

    /**
     * obtiene la ventana para ver detalles de cita.
     */
    private void get_cancel_appointment_screen(){
        Intent cancel = new Intent(this, CancelAppointmentActivity.class);
        startActivity(cancel);
    }

    /**
     * obtiene la ventana para pagar.
     */
    private void get_payment_screen(){
        Intent payment = new Intent(this, PaymentActivity.class);
        startActivity(payment);
    }

    /**
     * obtiene la ventana para comentar a los médicos.
     */
    private void get_rating_screen(){
        Intent rating = new Intent(this, FeedbackActivity.class);
        startActivity(rating);
    }
}
