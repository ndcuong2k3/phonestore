package com.example.phonestoreapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;


import com.example.phonestoreapplication.R;


import java.util.List;

import com.example.phonestoreapplication.Model.Product;

public class ProductIndexAdapter extends ArrayAdapter<Product> {
    private List<Product> products;

    private Context context;

    public ProductIndexAdapter(Context context, List<Product> products){
        super(context, 0, products);

        this.products = products;
        this.context = context;
    }

    @Override
    public View getView(int position, View convert_view, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row_view = inflater.inflate(R.layout.item_sp_index, parent, false);

        Product product_first = products.get(position * 2);
        Product product_sercond = null;
        if (position * 2 + 1 < products.size()) {
            product_sercond = products.get(position * 2 + 1);
        }

        ImageView image_product_first = row_view.findViewById(R.id.image_product_first);
        TextView name_product_first = row_view.findViewById(R.id.name_product_first);
        TextView price_product_first = row_view.findViewById(R.id.price_product_first);
        FrameLayout scope_product_first = row_view.findViewById(R.id.scope_product_first);

        scope_product_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, TrangCTSP.class);
//
//                intent.putExtra("product_id", product_first.getId());
//
//                context.startActivity(intent);
            }
        });

        Glide.with(getContext()).
                load(product_first.getImage())
                .into(image_product_first);

        name_product_first.setText(product_first.getName());
        price_product_first.setText(String.valueOf(product_first.getPrice()));

        if(product_sercond != null){
            ImageView image_product_second = row_view.findViewById(R.id.image_product_second);
            TextView name_product_second = row_view.findViewById(R.id.name_product_second);
            TextView price_product_second = row_view.findViewById(R.id.price_product_second);
            FrameLayout scope_product_second = row_view.findViewById(R.id.scope_product_second);

            Product _second = product_sercond;
            scope_product_second.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(context, TrangCTSP.class);
//
//                    intent.putExtra("product_id", _second.getId());

//                    context.startActivity(intent);
                }
            });

            Glide.with(getContext()).
                    load(product_sercond.getImage())
                    .into(image_product_second);
            name_product_second.setText(product_sercond.getName());
            price_product_second.setText(String.valueOf(product_sercond.getPrice()));
        }

        return row_view;
    }

    public int getCount() {
        return (products.size() + 1) / 2;
    }
}