package com.example.sliitmodel2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sliitmodel2.Database.DBHelper;
import com.example.sliitmodel2.Model.User;

import java.util.ArrayList;
import java.util.List;

public class EditProfile extends AppCompatActivity {

    EditText etUserNameEP, etDOBEP, etPasswordEP;
    RadioButton rbMaleEP, rbFemaleEP;
    Button btnEditEP, btnDeleteEP, btnSearchEP;
    String gender;
    Spinner spCategoryEP;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etUserNameEP = findViewById(R.id.etUserNameEP);
        etDOBEP = findViewById(R.id.etDOBEP);
        etPasswordEP = findViewById(R.id.etPasswordEP);
        rbMaleEP = findViewById(R.id.rbMaleEP);
        rbFemaleEP = findViewById(R.id.rbFemaleEP);
        btnEditEP = findViewById(R.id.btnEditEP);
        btnDeleteEP = findViewById(R.id.btnDeleteEP);
        btnSearchEP = findViewById(R.id.btnSearchEP);
        spCategoryEP = findViewById(R.id.spCategoryEP);

        btnSearchEP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(getApplicationContext());
                List selectedUser = dbHelper.readAllInfo(etUserNameEP.getText().toString());

                if (selectedUser.isEmpty()) {
                    Toast.makeText(EditProfile.this, "User Not Found : " + etUserNameEP.getText().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditProfile.this, "User Found : " + selectedUser.get(0).toString(), Toast.LENGTH_SHORT).show();
                    etDOBEP.setText(selectedUser.get(1).toString());
                    etPasswordEP.setText(selectedUser.get(3).toString());

                    if (selectedUser.get(2).toString().equals("Male")) {
                        rbMaleEP.setChecked(true);
                    } else {
                        rbFemaleEP.setChecked(true);
                    }
                }
            }
        });

        btnEditEP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(getApplicationContext());
                if (rbMaleEP.isChecked()) {
                    gender = "Male";
                } else {
                    gender = "Female";
                }
                Boolean updateStatus = dbHelper.updateInfo(etUserNameEP.getText().toString(), etDOBEP.getText().toString(), gender, etPasswordEP.getText().toString());
                if (updateStatus) {
                    Toast.makeText(EditProfile.this, "Update Successful : "+etUserNameEP.getText().toString(), Toast.LENGTH_SHORT).show();
                    etPasswordEP.getText().clear();
                    etDOBEP.getText().clear();
                    rbMaleEP.setChecked(false);
                    rbFemaleEP.setChecked(false);
                    gender = null;
                }else{
                    Toast.makeText(EditProfile.this, "Update Unsuccessful :"+etUserNameEP.getText().toString(), Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(EditProfile.this, "Category : "+spCategoryEP.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

            }
        });

        btnDeleteEP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(getApplicationContext());
                boolean deleteStatus = dbHelper.deleteInfo(etUserNameEP.getText().toString());
                
                if (deleteStatus){
                    Toast.makeText(EditProfile.this, "Delete Successfull : "+etUserNameEP.getText().toString(), Toast.LENGTH_SHORT).show();
                    etPasswordEP.getText().clear();
                    etDOBEP.getText().clear();
                    rbMaleEP.setChecked(false);
                    rbFemaleEP.setChecked(false);
                }else{
                    Toast.makeText(EditProfile.this, "Delete not Successfull", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*
        *
        * Spinner
        *
         */

        Spinner spinner = (Spinner) findViewById(R.id.spCategoryEP);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.foodCategory, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        /*
        *
        * Spinner using DB
         */

        DBHelper dbHelper = new DBHelper(getApplicationContext());

        List <User> usersList =  dbHelper.readAllInfoObjects();
        List <String> userNames = new ArrayList<>();

        for (User user : usersList){
            userNames.add(user.getUserName());
        }

        Spinner spinnerUsers = (Spinner) findViewById(R.id.spUsersEP);
        // Create an ArrayAdapter using the string array and a default spinner layout

       /* ArrayAdapter<CharSequence> adapterUsers = ArrayAdapter.createFromResource(this,
                R.array.foodCategory, android.R.layout.simple_spinner_item);*/

        ArrayAdapter<String> adapterUsers = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,userNames);
        // Specify the layout to use when the list of choices appears
        adapterUsers.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerUsers.setAdapter(adapterUsers);


    }
}