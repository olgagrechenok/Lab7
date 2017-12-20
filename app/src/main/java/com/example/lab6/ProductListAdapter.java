package com.example.lab6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Olya on 12.12.2017.
 */

public class ProductListAdapter extends ArrayAdapter<Product> {

    private final Context context;
    private final List<Product> values;

    public ProductListAdapter(Context context, List<Product> values) {
        super(context, android.R.layout.simple_list_item_1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        Product p=values.get(position);
        ((TextView)rowView.findViewById(android.R.id.text1)).setText(p.toString());
        rowView.setTag(p);
//        rowView.setOnLongClickListener( new View.OnLongClickListener() {
//
//            @Override
//            public boolean onLongClick(View v) {
//                Product product = (Product)v.getTag();
//                Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
//                Bundle bundle=new Bundle();
//                bundle.putSerializable("product",product);
//                intent.putExtras(bundle);
//                context.startActivity(intent);
//                return true;
//            }
//        });

        return rowView;
    }
}
