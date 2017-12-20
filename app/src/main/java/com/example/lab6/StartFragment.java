package com.example.lab6;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_start, container, false);

        return layout;
    }


    @Override
    public void onStart() {
        super.onStart();
        ListView lv=(ListView)getActivity().findViewById(R.id.lv);
        Main2Activity a=(Main2Activity)getActivity();
        if(a.getArr()==null || ((JSONObject)a.getArr().get(0)).get("distance")==null){
            lv.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
                    new String[]{"Wait! Interesting information will be here"}));
            return;
        }
        List<String> lines=new ArrayList<>();
        for(Object o:a.getArr()) {
            JSONObject oo=(JSONObject) o;
            float distanse=Float.valueOf(oo.get("distance").toString());
            lines.add(oo.get("address")+"\n"+
                    (int)(distanse/1000)+"km "+(int)(distanse%1000)+"m");

        }


        lv.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, lines){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if(position==0)
                    v.setBackgroundColor(getResources().getColor(R.color.suggestButtonColor));
                return v;
            }
        });
    }
}
