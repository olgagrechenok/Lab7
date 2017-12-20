package com.example.lab6;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;

public class EditAddActivity extends AppCompatActivity {

    Product product=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_edit_add);
        EditText hranenyeField=(EditText)findViewById(R.id.hranenyeTextView);
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(8);
        hranenyeField.setFilters(FilterArray);


        Bundle bundle;
       if((bundle=getIntent().getExtras())==null){
            TextView idField=(TextView)findViewById(R.id.idTextView);
            idField.setText("new product");
        }
        else{

            product =(Product)bundle.getSerializable("product");
           TextView idField=(TextView)findViewById(R.id.idTextView);
           EditText nameField=(EditText)findViewById(R.id.nameTextView);
            EditText upcField=(EditText)findViewById(R.id.upcTextView);
            EditText izgotField=(EditText)findViewById(R.id.izgotTextView);
            EditText cenaField=(EditText)findViewById(R.id.cenaTextView);
           EditText kolField=(EditText)findViewById(R.id.kolTextView);

            idField.setText(String.valueOf(product.getId()));
            nameField.setText(String.valueOf(product.getName()));
            upcField.setText(String.valueOf(product.getUpc()));
            izgotField.setText(String.valueOf(product.getIzgot()));
            cenaField.setText(String.valueOf(product.getCena()));
            hranenyeField.setText(String.valueOf(product.getHranenye()));
            kolField.setText(String.valueOf(product.getKol()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.info_action:
                AlertDialog.Builder builder= new AlertDialog.Builder(this);
                builder.setMessage("Current activity - "+this.getLocalClassName());
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                return true;
            case R.id.suggest_action:
                editAdd();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void editAdd(){
        TextView idField=(TextView)findViewById(R.id.idTextView);
        EditText nameField=(EditText)findViewById(R.id.nameTextView);
        EditText upcField=(EditText)findViewById(R.id.upcTextView);
        EditText izgotField=(EditText)findViewById(R.id.izgotTextView);
        EditText cenaField=(EditText)findViewById(R.id.cenaTextView);
        Long hranenyeFieldValue;
        EditText hranenyeField=(EditText)findViewById(R.id.hranenyeTextView);;
        try {
            hranenyeFieldValue = Long.valueOf(hranenyeField.getText().toString());
        }
        catch( NumberFormatException exp){
            //Toast.makeText(this,R.string.card_number_field_error,Toast.LENGTH_LONG).show();
            hranenyeField.setError(getResources().getString(R.string.card_number_field_error));
// Задаем динамически цвет текста подсказки в EditText
            hranenyeField.setHintTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            return;
        }
        EditText kolField=((EditText)findViewById(R.id.kolTextView));

        try {
            SQLiteOpenHelper patientsDatabaseHelper = new ProductsDatabaseHelper(this);
            SQLiteDatabase db = patientsDatabaseHelper.getReadableDatabase();
            if (product == null) {

                ProductsDatabaseHelper.insertPatient(db,nameField.getText().toString() ,
                        upcField.getText().toString(), izgotField.getText().toString(), Long.valueOf(cenaField.getText().toString()),
                        Long.valueOf(hranenyeField.getText().toString()),
                        Long.valueOf(kolField.getText().toString()));

            }
            else{
                ContentValues values = new ContentValues();
                values.put("name", nameField.getText().toString());
                values.put("upc", upcField.getText().toString());
                values.put("izgot", izgotField.getText().toString());
                values.put("cena", cenaField.getText().toString());
                values.put("hranenye",hranenyeField.getText().toString());
                values.put("kol", kolField.getText().toString());
                db.update("product",
                        values,
                        "_id = ?",
                        new String[] {Long.toString(product.getId())});
            }
            db.close();
        } catch(SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable. Try later", Toast.LENGTH_SHORT);
            toast.show();
        }

        Intent intent=new Intent(this, Main2Activity.class);
        intent.putExtra("fragment","com.example.lab6.ViewActivity");
        startActivity(intent);

    }

}
