package org.meditec.clientapp.network;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONHandler {

    public static String get_json_patient_info(String name, String email){

        JSONObject json_patient = new JSONObject();

        try {
            json_patient.put("name",name);
            json_patient.put("email",email);
        }catch (JSONException j){
            j.printStackTrace();
        }
        return json_patient.toString();
    }

    public static String get_json_date(int year, int month, int day, String med_code, String patient_name){

        JSONObject json_date = new JSONObject();

        try {
            json_date.put("year", year);
            json_date.put("month", month);
            json_date.put("day", day);
            json_date.put("code", med_code);
            json_date.put("patient", patient_name);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return json_date.toString();
    }
}
