package com.astra.melkovhw131;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init UI widgets
        initViews();

        // init file with login and password
        initData();
    }

    private void initData() {
        Data.addData(MainActivity.this, "", "", true);
    }

    private void initViews() {
        /* Field login */
        final EditText edtLogin = findViewById(R.id.login_name);

        /* Field password */
        final EditText edtPassword = findViewById(R.id.password);

        /* Button login */
        Button btnLogin = findViewById(R.id.login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = edtLogin.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                checkExists(login, password);
            }
        });

        /* Button registration */
        Button btnRegistration = findViewById(R.id.registration);
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void checkExists(String login, String password) {
        if(login.length() == 0 || password.length() == 0) {
            Toast.makeText(MainActivity.this,
                    R.string.enter_data,
                    Toast.LENGTH_SHORT).show();

            return;
        }

        if(Data.checkExists(MainActivity.this, login, password)) {
            Toast.makeText(MainActivity.this,
                    R.string.logon_success,
                    Toast.LENGTH_SHORT).show();

            return;
        }

        Toast.makeText(MainActivity.this,
                R.string.logon_fail,
                Toast.LENGTH_SHORT).show();
    }
}