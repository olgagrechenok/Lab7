package com.example.lab6;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Task1Activity extends Fragment implements ListView.OnItemClickListener, View.OnClickListener {
    private String nameValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.activity_task1, container, false);
        ListView patientsViewer=(ListView)layout.findViewById(R.id.task2List);
        patientsViewer.setOnItemClickListener(this);

        Button okButton=(Button)layout.findViewById(R.id.okButton);
        okButton.setOnClickListener(this);
        if(savedInstanceState!=null){
            nameValue=savedInstanceState.getString("diagnosis");
        }
        return layout;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("izgots", nameValue);
    }

    private void fillList(){
        if(nameValue.equals("")){
            Toast.makeText(getActivity(), "field(s) is empty", Toast.LENGTH_LONG).show();
            return;
        }
        ListView productsViewer=(ListView)getView().findViewById(R.id.task2List);
        try{
            SQLiteOpenHelper productsDatabaseHelper = new ProductsDatabaseHelper(getActivity());
            SQLiteDatabase db = productsDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT _id, name || '  '|| upc || ' ' || kol as info FROM product WHERE +" +
                    "name=?",new String[]{nameValue});
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
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        Bundle bundle = new Bundle();
        SQLiteOpenHelper patientsDatabaseHelper = new ProductsDatabaseHelper(getActivity());
        SQLiteDatabase db = patientsDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.query ("product",
                new String[] {"_id", "name", "upc", "izgot", "cena","hranenye", "kol"},
                "_id = ?",
                new String[] {Long.toString(id)},
                null, null,null);
        cursor.moveToFirst();

        bundle.putSerializable("product", new Product(cursor.getLong(0),cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getLong(4), cursor.getLong(5), cursor.getLong(6)));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        nameValue=((EditText)getView().findViewById(R.id.prodEditText)).getText().toString();
        fillList();
    }
}
