package org.meditec.clientapp.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

public class JSONHandler {

    /**
     * Construye en json la infromación de la cita reservada.
     * @param year el año.
     * @param month el mes.
     * @param day el día.
     * @param med_code el código de médico.
     * @param patient_name el nombre del paciente.
     * @param recorded_symptoms los síntomas regustrados.
     * @return un json con la información.
     */
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

    /**
     * Construye un json con la información necesaria para eliminar una cita en el servidor.
     * @param medic_code el código del médico.
     * @return un json con la información.
     */
    public static String delete_appointment(String medic_code){
        JSONObject json_cancel = new JSONObject();
        try {
            json_cancel.put("code", medic_code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json_cancel.toString();
    }

    /**
     * Constuye un json con los comentarios del médico.
     * @param comments los comentarios.
     * @param code el código del médico.
     * @return un json con los comentarios.
     */
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

    /**
     * Constuye un json array con los síntomas que se regustraron por voz.
     * @param symptoms los síntomas.
     * @return un array con los síntomas.
     */
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
