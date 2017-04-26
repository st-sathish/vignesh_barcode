package com.vignesh.barcode;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vignesh.barcode.databases.DatabaseManager;
import com.vignesh.barcode.user.User;
import com.vignesh.barcode.user.UserOperationType;


/**
 * Created by sysadmin on 23/3/17.
 */

public class SignUpActivity extends BaseActivity implements View.OnClickListener {

    EditText etname,userName,etpassword,mMobileno;
    Button btregister;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        etname = (EditText) findViewById(R.id.et_name);
        userName = (EditText) findViewById(R.id.et_userName);
        etpassword = (EditText) findViewById(R.id.et_password);
        mMobileno = (EditText) findViewById(R.id.et_phone_no);
        btregister = (Button) findViewById(R.id.button_register);
        btregister.setOnClickListener(this);
        db = DatabaseManager.getInstance().openDatabase();

    }
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
        finish();

    }

    @Override
    public void onClick(View v) {
        insert();
    }

    public void insert(){
        String mName = etname.getText().toString();
        String mUsername = userName.getText().toString();
        String mPassword = etpassword.getText().toString();
        long mPhoneno = Long.parseLong(mMobileno.getText().toString());
        User user = new User();
        user.setUserName(mUsername);
        user.setPassword(mPassword);
        user.setName(mName);
        user.setMobileNumber(mPhoneno);
        UserAsyncTask userAsyncTask = new UserAsyncTask(user, UserOperationType.ADD);
        userAsyncTask.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    public class UserAsyncTask extends AsyncTask<Void, Void, Void> {

        UserOperationType userOperationType;
        User user;

        public UserAsyncTask(User user, UserOperationType userOperationType) {
            this.user = user;
            this.userOperationType = userOperationType;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            switch (userOperationType) {
                case ADD:
                    String sql = "insert into user(name ,username , password ,mobile_number) values ('"+ user.getName() +"','"+ user.getUserName() +"','"+ user.getPassword() +"',"+ user.getMobileNumber() +")";
                    try {
                        db.execSQL(sql);
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_LONG).show();
        }
    }
}
