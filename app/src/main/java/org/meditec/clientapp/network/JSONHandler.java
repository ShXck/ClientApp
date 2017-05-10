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

    public static String delete_appointment(String medic_code){
        JSONObject json_cancel = new JSONObject();
        try {
            json_cancel.put("code", medic_code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json_cancel.toString();
    }

    public static String build_json_comments(String comments,  String code){
        JSONObject json_comments = new JSONObject();

        try{
            json_comments.put("comments",comments);
            json_comments.put("code",code);
        }catch (JSONException j){
            j.printStackTrace();
        }
        return json_comments.toString();
    }
}
