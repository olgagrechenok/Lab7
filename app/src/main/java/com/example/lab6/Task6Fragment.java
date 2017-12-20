package com.example.lab6;


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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class Task6Fragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_task6, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        fillList();
    }

    private void fillList(){
        ListView productsViewer=(ListView)getView().findViewById(R.id.task6List);
        try{
            SQLiteOpenHelper productsDatabaseHelper = new ProductsDatabaseHelper(getActivity());
            SQLiteDatabase db = productsDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT _id, name || '  '|| upc || ' ' || kol as info FROM product WHERE " +
                    "kol>0",null);
            if(cursor.getCount()==0)
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
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(getActivity(), "Database unavailable. Try later", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

}