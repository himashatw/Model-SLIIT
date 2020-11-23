package com.example.sliitmodel2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sliitmodel2.Database.DBHelper;
import com.example.sliitmodel2.Model.User;

import java.util.ArrayList;
import java.util.List;

public class UsersList extends AppCompatActivity {

    ListView idUsersUL;
    EditText etSearchUL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        idUsersUL = findViewById(R.id.idUsersUL);
        etSearchUL = findViewById(R.id.etSearchUL);


        final DBHelper dbHelper = new DBHelper(getApplicationContext());

        List userList = dbHelper.readAllInfoObjectsList();

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,userList);

        // Apply the adapter to the spinner
        idUsersUL.setAdapter(adapter);

        idUsersUL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = idUsersUL.getItemAtPosition(i).toString();
                List itemSelect = dbHelper.readAllInfo(selectedItem);
                String password = itemSelect.get(3).toString();
                Toast.makeText(UsersList.this, "User : "+selectedItem+" \n Password : "+password, Toast.LENGTH_SHORT).show();
            }
        });





        etSearchUL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etSearchUL.getText().clear();
            }
        });

    }
}