package com.example.roomdbtutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roomdbtutorial.database.UserDatabase;

import java.io.Serializable;

public class UpdateActivity extends AppCompatActivity  {

    private EditText edtUserName;
    private EditText edtAddress;
    private Button btnUpdateUser;

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        edtUserName = findViewById(R.id.edt_username);
        edtAddress = findViewById(R.id.edt_address);
        btnUpdateUser = findViewById(R.id.btnUpdateUser);

        mUser = (User) getIntent().getExtras().get("object_user");
        if(mUser != null){
            edtUserName.setText(mUser.getUsername());
            edtAddress.setText(mUser.getAddress());
        }

        btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateuser();
            }
        });

    }
    private void updateuser(){
        String strUserName = edtUserName.getText().toString().trim();
        String strAddress = edtAddress.getText().toString().trim();

        if(TextUtils.isEmpty(strUserName) || TextUtils.isEmpty(strAddress)){
            return;
        }
        // Update user in db
        mUser.setUsername(strUserName);
        mUser.setAddress(strAddress);

        UserDatabase.getInstance(this).userDAO().updateUser(mUser);

        Toast.makeText(this, "Update successfully", Toast.LENGTH_SHORT).show();

        Intent intentResult = new Intent();
        setResult(Activity.RESULT_OK,intentResult);
        finish();
    };


}