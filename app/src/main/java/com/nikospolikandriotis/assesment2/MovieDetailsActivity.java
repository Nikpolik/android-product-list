package com.nikospolikandriotis.assesment2;

import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.widget.Button;

public class MovieDetailsActivity extends AbstractActivity {

    @Override
    int getLayout() {
        return R.layout.activity_movie_details;
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
        String id = getIntent().getStringExtra("id");

//        if (image == null) {
//            return;
//        }
//
//        TextView titleView = findViewById(R.id.product_details_title);
//        TextView descView = findViewById(R.id.product_details_description);
//        ImageView imageView = findViewById(R.id.product_details_image);
//
//        titleView.setText(title);
//        descView.setText(description);
//        imageView.setImageDrawable(image);
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
//        int id = getIntent().getIntExtra("id", R.array.product1);
//        return "Check this cool pair of shoes! http://mycool_product_site/products/" + id;
        return "";
    }
}