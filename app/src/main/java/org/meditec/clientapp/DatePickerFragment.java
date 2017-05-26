package org.meditec.clientapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import org.meditec.clientapp.network.JSONHandler;
import org.meditec.clientapp.network.RequestManager;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    /**
     * Petición para crear una cita en el servidor una vez seleccionada la fecha y el médico.
     * @param view el calendario.
     * @param year el año.
     * @param month el mes.
     * @param dayOfMonth el día.
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        RequestManager.POST("book", JSONHandler.build_json_appointment_info(year,month,dayOfMonth, BookAppointmentActivity.code_picked, LoginActivity.client_name, SymptomsRegisterActivity.recorded));
        Toast.makeText(getContext(), "Reservación creada", Toast.LENGTH_SHORT).show();
        Intent menu = new Intent(getActivity(), MainMenuActivity.class);
        menu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(menu);
    }
}
