package org.meditec.clientapp.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

public class JSONHandler {

    public static String build_json_appointment_info(int year, int month, int day, String med_code, String patient_name, String recorded_symptoms){

        JSONObject json_date = new JSONObject();

        try {
            json_date.put("year", year);
            json_date.put("month", month);
            json_date.put("day", day);
            json_date.put("code", med_code);
            json_date.put("patient", patient_name);
            json_date.put("recorded", build_json_recorded_symptoms(recorded_symptoms));
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

    private static JSONArray build_json_recorded_symptoms(String symptoms){

        JSONArray array = new JSONArray();

        String[] result = symptoms.split(",");

        for (String s: result){
            array.put(s);
        }

        Log.i("RESULT", array.toString());
        return array;
    }
}
