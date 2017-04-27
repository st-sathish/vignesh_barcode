package com.vignesh.barcode;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vignesh.barcode.databases.DatabaseManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mUsername, mPassword;
    Button btlogin;
    TextView notYetRegister,forGotPassword;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    public void initialize() {
        mUsername = (EditText) findViewById(R.id.et_name);
        mPassword = (EditText) findViewById(R.id.et_password);
        btlogin = (Button) findViewById(R.id.btn_login);
        notYetRegister = (TextView) findViewById(R.id.tv_signUp);
        forGotPassword = (TextView) findViewById(R.id.tv_forgot_password);
        notYetRegister.setOnClickListener(this);
        forGotPassword.setOnClickListener(this);
        btlogin.setOnClickListener(this);


        db = DatabaseManager.getInstance().openDatabase();
    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_signUp:
                Intent create = new Intent(this, SignUpActivity.class);
                startActivity(create);
                break;
            case R.id.btn_login:
                checkLogin();
                break;
            case R.id.tv_forgot_password:
                Intent forgotpassword = new Intent(this, ForgotPasswordActivity.class);
                startActivity(forgotpassword);
                break;
        }

    }

    public void checkLogin() {

        String[] selectionArg = new String[2];
        selectionArg[0] = mUsername.getText().toString();
        selectionArg[1] = mPassword.getText().toString();
        Cursor cursor = db.rawQuery("SELECT * FROM user where username = ? and password = ?", selectionArg);
        if(null != cursor && cursor.getCount() > 0) {
            Intent intent = new Intent(this, LandingPageActivity.class);
            startActivity(intent);
            Toast.makeText(getBaseContext(),"Successfully login", Toast.LENGTH_SHORT).show();
            cursor.close();
        } else {
            Toast.makeText(getBaseContext(),"invalid username", Toast.LENGTH_SHORT).show();
        }

    }
}
