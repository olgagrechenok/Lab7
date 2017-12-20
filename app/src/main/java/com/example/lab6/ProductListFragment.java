package com.example.lab6;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

/**
 * Created by Olya on 12.12.2017.
 */

public class ProductListFragment extends ListFragment {

    interface ProductListListener{
        void itemClicked(Product p);
    };

    private ProductListListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if(listener!=null){
            SQLiteOpenHelper patientsDatabaseHelper = new ProductsDatabaseHelper(getActivity());
            SQLiteDatabase db = patientsDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query ("product",
                    new String[] {"_id", "name", "upc", "izgot", "cena","hranenye", "kol"},
                    "_id = ?",
                    new String[] {Long.toString(id)},
                    null, null,null);
            cursor.moveToFirst();

            listener.itemClicked(new Product(cursor.getLong(0),cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getLong(4), cursor.getLong(5), cursor.getLong(6)));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener=(ProductListListener)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try{
            SQLiteOpenHelper productsDatabaseHelper = new ProductsDatabaseHelper(getActivity());
            SQLiteDatabase db = productsDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT _id, name || '  '|| upc || ' ' || kol as info FROM product",null);
            //Cursor cursor = db.rawQuery("SELECT * FROM patient",null);
            CursorAdapter listAdapter = new SimpleCursorAdapter(getActivity(),
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"info"},
                    new int[]{android.R.id.text1},
                    0);
            setListAdapter(listAdapter);
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(getActivity(), "Database unavailable. Try later", Toast.LENGTH_SHORT);
            toast.show();
        }
//        ArrayAdapter<Product> adapter = new ProductListAdapter(
//                inflater.getContext(), ProductRepository.products);


        return super.onCreateView(inflater, container, savedInstanceState);

    }
}
