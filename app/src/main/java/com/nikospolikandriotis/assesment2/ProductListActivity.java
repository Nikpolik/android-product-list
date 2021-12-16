package com.nikospolikandriotis.assesment2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.nikospolikandriotis.assesment2.recycler.RecyclerViewAdapter;

public class ProductListActivity extends AbstractActivity {

    private boolean isClicked = false;

    @Override
    int getLayout() {
        return R.layout.activity_product_list;
    }

    @Override
    void uiSetup() {
        Button button = findViewById(R.id.product_list_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClicked = true;
                Intent intent = new Intent(ProductListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    void startOperations() {

    }

    @Override
    void stopOperations() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
    }

    @Override
    protected void onPostResume() {
        this.isClicked = false;
        super.onPostResume();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        TypedArray productIds = getResources().obtainTypedArray(R.array.products);
        if(productIds == null) {
            return;
        }

        RecyclerView recyclerView = findViewById(R.id.recycler_view_list);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(productIds,  new RecyclerViewAdapter.Listener() {
            @Override
            public void onItemClick(View view, int id) {
                if (!isClicked) {
                    isClicked = true;
                    Intent intent = new Intent(ProductListActivity.this, ProductDetailsActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }

            }
        });

        recyclerView.setAdapter(adapter);
    }
}
