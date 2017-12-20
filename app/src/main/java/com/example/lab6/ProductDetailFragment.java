package com.example.lab6;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Olya on 12.12.2017.
 */

public class ProductDetailFragment extends Fragment {

    private Product product;

    interface ProductRemoterListener{
        void deleteButtonClicked(Product p);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private ProductDetailFragment.ProductRemoterListener listener;

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.listener=(ProductDetailFragment.ProductRemoterListener)activity;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.patient_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_action:
                AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //удаление пациента
                        listener.deleteButtonClicked(product);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                return true;
            case R.id.edit_action:
                Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
                Bundle bundle=new Bundle();
                bundle.putSerializable("product",product);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        View view=getView();
        ((TextView)view.findViewById(R.id.titleView)).setText(product.getName());
        ((TextView)view.findViewById(R.id.detailsView)).setText(product.getDetails());

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ProductRecordFragment fragment=new ProductRecordFragment();
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.inner_fragment_container, fragment);
        ft.commit();

        View layout = inflater.inflate(R.layout.fragment_product_detail, container, false);

        if (savedInstanceState != null) {
            product = (Product)savedInstanceState.getSerializable("product");
        }
        return layout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("product",product);
    }

}
