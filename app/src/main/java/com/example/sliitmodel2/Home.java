package com.example.sliitmodel2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sliitmodel2.Database.DBHelper;

public class Home extends AppCompatActivity {

    Button btnRegisterHome, btnLoginHome, btnUsersHome;
    DBHelper dbHelper;
    EditText etUserNameHome, etPasswordHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnRegisterHome = findViewById(R.id.btnRegisterHome);

        btnRegisterHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileManagement.class);
                startActivity(intent);
            }
        });

        btnLoginHome = findViewById(R.id.btnLoginHome);
        etUserNameHome = findViewById(R.id.etUserNameHome);
        etPasswordHome = findViewById(R.id.etPasswordHome);
        btnUsersHome = findViewById(R.id.btnUsersHome);

        btnLoginHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper = new DBHelper(getApplicationContext());
                Boolean loginStatus = dbHelper.loginCheck(etUserNameHome.getText().toString(), etPasswordHome.getText().toString());

                if (loginStatus) {
                    Intent intent = new Intent(getApplicationContext(), UsersList.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Home.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnUsersHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UsersList.class);
                startActivity(intent);
            }
        });



    }


}