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

        String[] menu_options = {"Book Appointment", "Cancel Appointment", "Pay", "Rate our service"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menu_options);

        menu.setAdapter(adapter);

        get_option_clicked();

    }

    private void get_option_clicked() {
        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    get_appointment_booking_activity();
                }
            }
        });
    }

    private void get_appointment_booking_activity(){
        Intent book = new Intent(this, BookAppointmentActivity.class);
        startActivity(book);
    }
}
