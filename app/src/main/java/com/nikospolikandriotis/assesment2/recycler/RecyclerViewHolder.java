package com.nikospolikandriotis.assesment2.recycler;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.nikospolikandriotis.assesment2.R;

public class RecyclerViewHolder extends BaseRecyclerView {
    private RecyclerViewAdapter.Listener callback;

    public RecyclerViewHolder(@NonNull View itemView, RecyclerViewAdapter.Listener callback) {
        super(itemView);
        this.callback = callback;
    }

    @Override
    public void bindData(int id) {
        TextView textView = itemView.findViewById(R.id.product_row_text);
        Resources res = itemView.getContext().getResources();
        TypedArray productDetails = res.obtainTypedArray(id);
        //noinspection ResourceType
        String title = productDetails.getString(1);
        //noinspection ResourceType
        int imageId = productDetails.getResourceId(3, R.drawable.product_1);
        Drawable image = res.getDrawable(imageId);
        ImageView imageView = itemView.findViewById(R.id.product_row_img);
        imageView.setImageDrawable(image);
        textView.setText(title);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onItemClick(v, id);
            }
        });
    }
}
