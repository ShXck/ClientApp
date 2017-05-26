package org.meditec.clientapp;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.meditec.clientapp.network.RequestManager;

import java.util.ArrayList;

public class BookAppointmentActivity extends AppCompatActivity {

    private ListView menu;
    private ArrayAdapter adapter;
    public static String code_picked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        menu = (ListView)findViewById(R.id.medics_list);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        menu.setAdapter(adapter);
        get_medic_codes();
        get_code_picked();
    }

    /**
     * Listener del menú.
     */
    private void get_code_picked() {
        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                code_picked = (String)parent.getItemAtPosition(position);
                get_record_activity();
            }
        });
    }

    /**
     * obtiene la actividad para registrar síntomas.
     */
    private void get_record_activity(){
        Intent record = new Intent(BookAppointmentActivity.this, SymptomsRegisterActivity.class);
        startActivity(record);
    }

    /**
     * petición para obtener los códigos de los médicos.
     */
    private void get_medic_codes(){
        RequestManager.GET("medics_list");
        RequestManager.wait_for_response(1000);
        process_list(RequestManager.GET_REQUEST_DATA());
    }

    /**
     * procesa el json array con los códigos de médicos.
     * @param list la lista con los códigos.
     */
    private void process_list(String list){
        try {
            JSONObject json = new JSONObject(list);
            JSONArray array = json.getJSONArray("medics");

            for (int i = 0; i < array.length(); i++){
                try {
                    adapter.add(array.get(i));
                }catch (JSONException j){
                    break;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
