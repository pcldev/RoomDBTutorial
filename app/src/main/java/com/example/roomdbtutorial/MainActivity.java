package com.example.roomdbtutorial;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roomdbtutorial.database.UserDAO;
import com.example.roomdbtutorial.database.UserDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 10 ;
    private EditText edtUserName;
    private EditText edtAddress;
    private Button btnAdduser;
    private RecyclerView rcvUser;


    private UserAdapter userAdapter;
    private List<User> mListUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initUI();
        userAdapter = new UserAdapter(new UserAdapter.IClickItemUser() {
            @Override
            public void updateUser(User user) {
                clickUpdateUser(user);
            }
        });
        mListUser = new ArrayList<>();
        userAdapter.setData(mListUser);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvUser.setLayoutManager(linearLayoutManager);

        rcvUser.setAdapter(userAdapter);

        showListUser();


        btnAdduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });
    }

    private void initUI() {
        edtUserName = findViewById(R.id.edt_username);
        edtAddress = findViewById(R.id.edt_address);
        btnAdduser = findViewById(R.id.btnAddUser);
        rcvUser = findViewById(R.id.rcv_user);
    }

    private void addUser() {
        String strUserName = edtUserName.getText().toString().trim();
        String strAddress = edtAddress.getText().toString().trim();

        if(TextUtils.isEmpty(strUserName) || TextUtils.isEmpty(strAddress)){
            return;
        }
        User user = new User(strUserName,strAddress);

        // check user existed
        if(isUserExist(user)){
            Toast.makeText(this, "user is existed", Toast.LENGTH_SHORT).show();
            return;
        }

        UserDatabase.getInstance(this).userDAO().insertUser(user);
        Toast.makeText(this, "Add User Successfully", Toast.LENGTH_SHORT).show();

        edtUserName.setText("");
        edtAddress.setText("");
        hideSoftKeyboard();

        showListUser();
    }
    // hide keyboard functiom
    private void hideSoftKeyboard(){
        try{
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch (NullPointerException ex){
            ex.printStackTrace();
        }
    }

    private void showListUser(){
        mListUser = UserDatabase.getInstance(this).userDAO().getListUser();
        userAdapter.setData(mListUser);
    }

    private boolean isUserExist(User user){
        List<User> list = UserDatabase.getInstance(this).userDAO().checkUser(user.getUsername());
        return list != null && !list.isEmpty();
    }

    private void clickUpdateUser(User user){
        Intent intent = new Intent(MainActivity.this,UpdateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_user",  user);
        intent.putExtras(bundle);
        startActivityForResult(intent,MY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == MY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            showListUser();
        }
    }
}