package com.example.lab6;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ProductListFragment.ProductListListener, ProductDetailFragment.ProductRemoterListener {

    private String fragmentName;
    //private LocationManager lmanager;
    private JSONArray arr;

    public JSONArray getArr() {
        return arr;
    }

    private LocationListener lListener=new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if(location!=null){
                //setTitle(String.valueOf(location.getLatitude())+"\n"+String.valueOf(location.getLongitude()));
                    Location l=new Location(location);
                    for(Object o:arr) {
                        JSONObject oo=(JSONObject) o;
                        l.setLatitude(Double.valueOf((oo.get("latitude").toString())));
                        l.setLongitude(Double.valueOf(((JSONObject) o).get("longitude").toString()));
                        //Toast.makeText(getActivity(), String.valueOf(location.distanceTo(l)), Toast.LENGTH_LONG).show();
                        oo.put("distance",location.distanceTo(l));
                    }

                Collections.sort( arr, new Comparator<JSONObject>() {

                    @Override
                    public int compare(JSONObject a, JSONObject b) {
                        return Float.compare(Float.valueOf(a.get("distance").toString()),
                                Float.valueOf(b.get("distance").toString()));
                    }
                });

//                JSONObject min=(JSONObject)arr.get(0);
//
//                for(Object o:arr) {
//                    JSONObject oo=(JSONObject) o;
//                    if(Double.valueOf(oo.get("distance").toString())<Double.valueOf(min.get("distance").toString()))
//                        min=oo;
//                }
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                try {
                    ft.replace(R.id.fragment_cont, (Fragment)Class.forName(fragmentName).newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                //ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        }


        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if(savedInstanceState!=null)
            fragmentName=savedInstanceState.getString("fragmentName");
        else if((fragmentName=getIntent().getStringExtra("fragment"))==null){
            fragmentName="com.example.lab6.StartFragment";
        }


//        try{
//            SQLiteOpenHelper patientsDatabaseHelper = new PatientsDatabaseHelper(this);
//            SQLiteDatabase db = patientsDatabaseHelper.getReadableDatabase();
//            PatientsDatabaseHelper.insertPatient(db, "Сергей", "Иванов", "Васильевич", "13.01.1990", 123654L, "не определен", 1, new Date(2017,2,12),new Date(2017,2,31));
//            PatientsDatabaseHelper.insertPatient(db, "Юлия", "Савушкина", "Прокофьевна", "01.10.1982", 5689011L, "ангина",0, new Date(2017,10,6),new Date(2017,10,22));
//            PatientsDatabaseHelper.insertPatient(db, "Артем", "Золотой", "Викторович", "31.02.1991", 333188L, "перелом кисти",1, new Date(2017,9,18),null);
//            PatientsDatabaseHelper.insertPatient(db, "Евгения", "Рай", "Александровна", "12.12.1990", 1536754L, "нефроптоз",0, new Date(2017,9,3),null);
//
//
//        } catch(SQLiteException e) {
//            Toast toast = Toast.makeText(this, "Database unavailable. Try later", Toast.LENGTH_SHORT);
//            toast.show();
//        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        try {
            ft.replace(R.id.fragment_cont, (Fragment)Class.forName(fragmentName).newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        LocationManager lmanager=(LocationManager)getSystemService(Activity.LOCATION_SERVICE);
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == 0) {
            lmanager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            lmanager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, lListener);
            try {
                writeSet(this);
                arr=readSet(this);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onResume() {
        super.onResume();
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            lmanager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//            lmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 10, lListener);
//        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("fragmentName",fragmentName);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_menu, menu);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment=null;
        switch(item.getItemId()){
            case R.id.view:
                fragment=new ViewActivity();
                fragmentName="com.example.lab6.ViewActivity";
                break;
            case R.id.task1:
            fragment=new Task1Activity();
                fragmentName="com.example.lab6.Task1Activity";
                break;
            case R.id.task2:
            fragment=new Task2Activity();
                fragmentName="com.example.lab6.Task2Activity";
                break;
            case R.id.task3:
                fragment=new Task3Fragment();
                fragmentName="com.example.lab6.Task3Fragment";
                break;
            case R.id.task4:
                fragment=new Task4Fragment();
                fragmentName="com.example.lab6.Task4Fragment";
                break;
            case R.id.task5:
                fragment=new Task5Fragment();
                fragmentName="com.example.lab6.Task5Fragment";
                break;
            case R.id.task6:
                fragment=new Task6Fragment();
                fragmentName="com.example.lab6.Task6Fragment";
                break;
            case R.id.info:
                AlertDialog.Builder builder= new AlertDialog.Builder(this);
                builder.setMessage("Application name - "+this.getLocalClassName()+"\nVersion - 1.7");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                return true;
            case R.id.exit:
                finish();
                return true;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_cont, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private static JSONArray prepareSet(){
        JSONArray arr=new JSONArray();
        JSONObject obj;
        obj=new JSONObject();
        obj.put("address","Хатаевича, 9");
        obj.put("latitude", "52.445311");
        obj.put("longitude","31.016812");
        arr.add(obj);

        obj=new JSONObject();
        obj.put("address","Речецкий проспект, 5В");
        obj.put("latitude", "52.416793");
        obj.put("longitude","30.960768");
        arr.add(obj);

        obj=new JSONObject();
        obj.put("address","Богдана Хмельницкого, 79");
        obj.put("latitude","52.412710");
        obj.put("longitude","30.970316");
        arr.add(obj);

        return arr;
    }

    public static void writeSet(Activity activity) throws IOException {
        new File(activity.getFilesDir(), "availablePlaces").createNewFile();
        FileOutputStream stream=activity.openFileOutput("availablePlaces", Context.MODE_PRIVATE);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(stream));
        out.write(prepareSet().toJSONString());
        out.close();
        stream.close();
    }

    public static JSONArray readSet(Activity activity) throws IOException {
        // File file=new File(sourse);
        FileInputStream stream=activity.openFileInput("availablePlaces");
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        JSONArray obj;
        try {
           obj= (JSONArray)JSONValue.parseWithException(in.readLine());
            //Toast.makeText(activity,(((JSONObject)obj.get(0)).get("name").toString()),Toast.LENGTH_LONG).show();
        } catch (ParseException e) {
            obj=null;
            e.printStackTrace();
        }
        stream.close();
        in.close();
        return obj;
    }

    @Override
    public void itemClicked(Product p) {
        FrameLayout container=(FrameLayout)findViewById(R.id.fragment_container);
        if(container!=null){
            ProductDetailFragment details = new ProductDetailFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            details.setProduct(p);
            ft.replace(R.id.fragment_container, details);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
        else {
            Intent intent = new Intent(this, DetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("product", p);
            intent.putExtras(bundle);
            startActivity(intent);
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
        startActivity(getIntent());
        finish();
        Toast.makeText(this,"Success!",Toast.LENGTH_LONG).show();
    }



}
