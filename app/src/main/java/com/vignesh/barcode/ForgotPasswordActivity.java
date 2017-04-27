package com.vignesh.barcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ForgotPasswordActivity extends BaseActivity {

    EditText username;
    Button submit,cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_forgotpassword);
        Initiate();

    }

    public void Initiate(){
        username=(EditText)findViewById(R.id.editText4);
        submit=(Button)findViewById(R.id.accept);
        cancel=(Button)findViewById(R.id.cancellation);

        submit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

            }
        });


        cancel.setOnClickListener(new View.OnClickListener(){
            public  void onClick(View view){

            }
        });


    }
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(ForgotPasswordActivity.this, MainActivity.class));
        finish();

    }
}
