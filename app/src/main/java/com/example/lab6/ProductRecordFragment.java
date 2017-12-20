package com.example.lab6;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Olya on 12.12.2017.
 */

public class ProductRecordFragment extends Fragment implements View.OnClickListener{

    private EditText dataField;
    private EditText timeField;
    private Calendar dateAndTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_medical_record, container, false);
        Button startButton = (Button)layout.findViewById(R.id.suggest_button);
        startButton.setOnClickListener(this);

        dateAndTime=Calendar.getInstance();
        dataField=(EditText)layout.findViewById(R.id.date_of_record);
        dataField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    new DatePickerDialog(getActivity(), d,
                            dateAndTime.get(Calendar.YEAR),
                            dateAndTime.get(Calendar.MONTH),
                            dateAndTime.get(Calendar.DAY_OF_MONTH))
                            .show();
                    v.clearFocus();
                }
            }
        });
        timeField=(EditText)layout.findViewById(R.id.time_of_record);
        timeField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new TimePickerDialog(getActivity(), t,
                            dateAndTime.get(Calendar.HOUR_OF_DAY),
                            dateAndTime.get(Calendar.MINUTE), true)
                            .show();
                    v.clearFocus();
                }
            }
        });



        if(savedInstanceState!=null){
            dataField.setText(savedInstanceState.getString("dataFieldValue"));
            timeField.setText(savedInstanceState.getString("timeFieldValue"));
        }
        else {
            setDateToField();
            setTimeToField();
        }

        return layout;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("dataFieldValue",dataField.getText().toString());
        outState.putString("timeFieldValue",timeField.getText().toString());
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.suggest_button:
                String text="Successful! We look forward to you "+ dataField.getText()+" in  "+timeField.getText();
                Toast.makeText(getActivity(),text,Toast.LENGTH_LONG).show();
                break;
        }
    }

    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setDateToField();
        }
    };

    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setTimeToField();
        }
    };

    public void setDateToField(){
        dataField.setText(DateUtils.formatDateTime(getActivity(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    public void setTimeToField(){
        timeField.setText(DateUtils.formatDateTime(getActivity(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME));
    }
}
