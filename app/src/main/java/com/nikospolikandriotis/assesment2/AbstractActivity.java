package com.nikospolikandriotis.assesment2;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class AbstractActivity extends AppCompatActivity {
    abstract int getLayout();

    abstract void uiSetup();

    abstract void startOperations();

    abstract void stopOperations();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        uiSetup();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        startOperations();
    }

    @Override
    protected void onPause() {
        stopOperations();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
