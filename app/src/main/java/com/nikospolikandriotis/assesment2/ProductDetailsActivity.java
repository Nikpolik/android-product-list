package com.nikospolikandriotis.assesment2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikospolikandriotis.assesment2.recycler.RecyclerViewAdapter;

import java.util.logging.Logger;

public class ProductDetailsActivity extends AbstractActivity {

    @Override
    int getLayout() {
        return R.layout.product_details;
    }

    @Override
    void uiSetup() {
        bindViewData();
        setupHandlers();
    }

    @Override
    void startOperations() {

    }

    @Override
    void stopOperations() {
    }

    private void bindViewData() {
        Resources res = getResources();
        int id = getIntent().getIntExtra("id", R.array.product1);
        TypedArray productDetails = res.obtainTypedArray(id);
        if (productDetails == null) {
            return;
        }
        //noinspection ResourceType
        String title = productDetails.getString(1);
        //noinspection ResourceType
        String description = productDetails.getString(2);
        //noinspection ResourceType
        int imageId = productDetails.getResourceId(3, R.drawable.product_1);
        Drawable image = res.getDrawable(imageId);
        if (image == null) {
            return;
        }

        TextView titleView = findViewById(R.id.product_details_title);
        TextView descView = findViewById(R.id.product_details_description);
        ImageView imageView = findViewById(R.id.product_details_image);

        titleView.setText(title);
        descView.setText(description);
        imageView.setImageDrawable(image);
    }

    private void setupHandlers() {
        Button button = findViewById(R.id.product_details_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, getShareMessage());
                shareIntent.setType("text/plain");
                startActivity(shareIntent);

            }
        });

    }

    private String getShareMessage() {
        int id = getIntent().getIntExtra("id", R.array.product1);
        return "Check this cool pair of shoes! http://mycool_product_site/products/" + id;
    }
}