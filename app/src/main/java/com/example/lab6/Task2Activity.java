package com.example.lab6;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class Task2Activity extends Fragment implements View.OnClickListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.activity_task2, container, false);
        Button okButton=(Button)layout.findViewById(R.id.okButton);
        okButton.setOnClickListener(this);

        return layout;
    }

    @Override
    public void onClick(View v) {
        String nameValue=((EditText)getView().findViewById(R.id.NameEditText)).getText().toString();
        String cenaValue=((EditText)getView().findViewById(R.id.cenaEditText)).getText().toString();
        if(nameValue.equals("") || cenaValue.equals("")){
            Toast.makeText(getActivity(), "пустое поле", Toast.LENGTH_LONG).show();
            return;
        }
        ListView productsViewer=(ListView)getView().findViewById(R.id.task2List);
        try {
            SQLiteOpenHelper productsDatabaseHelper = new ProductsDatabaseHelper(getActivity());
            SQLiteDatabase db = productsDatabaseHelper.getReadableDatabase();
//            Cursor cursor = db.query("product",
//                    new String[] {"_id, name || '  '|| upc || ' ' || kol as info"},
//                    "name=? AND cena<?",
//                    new String[]{nameValue,cenaValue, cenaValue},
//                    null, null, null);
            Cursor cursor = db.rawQuery("SELECT _id, name || '  '|| upc || ' ' || kol as info FROM product WHERE " +
                    "name=? AND cena<=?" , new String[]{nameValue,cenaValue});
            if (cursor.getCount() == 0)
                Toast.makeText(getActivity(), "no match results", Toast.LENGTH_LONG).show();
            else {
                CursorAdapter listAdapter = new SimpleCursorAdapter(getActivity(),
                        android.R.layout.simple_list_item_1,
                        cursor,
                        new String[]{"info"},
                        new int[]{android.R.id.text1},
                        0);
                productsViewer.setAdapter(listAdapter);
                productsViewer.requestFocus();
            }
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(getActivity(), "Database unavailable. Try later", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}


