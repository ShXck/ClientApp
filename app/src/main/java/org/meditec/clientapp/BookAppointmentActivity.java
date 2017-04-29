package org.meditec.clientapp;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;
import org.meditec.clientapp.network.RequestManager;

import java.util.ArrayList;

public class BookAppointmentActivity extends AppCompatActivity {

    private ListView menu;
    private ListAdapter adapter;
    private ArrayList<String> codes = new ArrayList<>();

    public static String code_picked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        get_medic_codes();

        menu = (ListView)findViewById(R.id.medics_list);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, codes);
        menu.setAdapter(adapter);

        get_code_picked();
    }

    private void get_code_picked() {
        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                code_picked = (String)parent.getItemAtPosition(position);
                get_date_picker();
            }
        });
    }

    private void get_date_picker() {
        DialogFragment date_picker = new DatePickerFragment();
        date_picker.show(getFragmentManager(),"Date Picker");
    }

    private void get_medic_codes(){
        RequestManager.GET("medics_list");
        RequestManager.wait_for_response(1000);
        process_list(RequestManager.GET_REQUEST_DATA());
    }

    private void process_list(String list){
        try {
            JSONObject json = new JSONObject(list);

            for (int i = 1; i < json.getInt("count"); i++){
                try {
                    codes.add(json.getString(String.valueOf(i)));
                }catch (JSONException j){
                    break;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
