package com.example.lab6;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lab6.R;

public class DetailActivity extends AppCompatActivity implements ProductDetailFragment.ProductRemoterListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ProductDetailFragment productDetailFragment = (ProductDetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.detail_frag);
        Product product= (Product) getIntent().getExtras().get("product");
        productDetailFragment.setProduct(product);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.add_action:
                intent=new Intent(Intent.ACTION_INSERT_OR_EDIT);
                startActivity(intent);
                return true;
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void deleteButtonClicked(Product p) {
        try{

            SQLiteOpenHelper patientsDatabaseHelper = new ProductsDatabaseHelper(this);
            SQLiteDatabase db = patientsDatabaseHelper.getReadableDatabase();
            db.delete("product",
                    "_id = ?",
                    new String[] {Long.toString(p.getId())});
            db.close();

        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable. Try later", Toast.LENGTH_SHORT);
            toast.show();
        }

        Intent intent=new Intent(this, Main2Activity.class);
        intent.putExtra("fragment","com.example.lab6.ViewActivity");
        startActivity(intent);

        Toast.makeText(this,"Success!",Toast.LENGTH_LONG).show();
    }

}
