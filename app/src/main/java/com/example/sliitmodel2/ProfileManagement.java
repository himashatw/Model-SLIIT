package com.example.sliitmodel2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.sliitmodel2.Database.DBHelper;

public class ProfileManagement extends AppCompatActivity {

    EditText etUserNamePM, etDOBPM, etPasswordPM;
    String gender;
    RadioButton rbMalePM, rbFemalePM;
    Button btnRegisterPM, btnEditPM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_management);

        etUserNamePM = findViewById(R.id.etUserNamePM);
        etDOBPM = findViewById(R.id.etDOBPM);
        etPasswordPM = findViewById(R.id.etPasswordPM);

        rbMalePM = findViewById(R.id.rbMalePM);
        rbFemalePM = findViewById(R.id.rbFemalePM);

        btnRegisterPM = findViewById(R.id.btnRegisterPM);
        btnEditPM = findViewById(R.id.btnEditPM);

        btnRegisterPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(getApplicationContext());

                if(rbMalePM.isChecked()){
                    gender = "Male";
                }else{
                    gender = "Female";
                }

              long rowId =  dbHelper.addInfo(etUserNamePM.getText().toString(),etDOBPM.getText().toString(),gender,etPasswordPM.getText().toString() );

                if (rowId == -1){
                    Toast.makeText(ProfileManagement.this, "Operation Failed ", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(ProfileManagement.this, "User Added ID : "+rowId, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), EditProfile.class);
                    startActivity(intent);
                }

            }
        });

        btnEditPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditProfile.class);
                startActivity(intent);
            }
        });
    }
}