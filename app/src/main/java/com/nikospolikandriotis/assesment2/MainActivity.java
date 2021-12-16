package com.nikospolikandriotis.assesment2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    void uiSetup() {
        Button button = findViewById(R.id.main_login_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText usernameInput = findViewById(R.id.main_edit_username);
                EditText passwordInput = findViewById(R.id.main_edit_password);

                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                if (isInputValid(username, password)) {
                    Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
                    startActivityForResult(intent,2000);
                } else {
                    String errorMessage = "Username or password incorrect";
                    Snackbar bar = Snackbar.make(v,errorMessage, Snackbar.LENGTH_SHORT);
                    View sbView = bar.getView();
                    int red = getResources().getColor(R.color.red);
                    usernameInput.setError(errorMessage);
                    passwordInput.setError(errorMessage);
                    sbView.setBackgroundColor(red);
                    bar.show();
                }
            }
        });
    }

    @Override
    void startOperations() {

    }

    @Override
    void stopOperations() {

    }

    boolean isInputValid(String username, String password) {
        return username.equals("demo") && password.equals("demo");
    }
}