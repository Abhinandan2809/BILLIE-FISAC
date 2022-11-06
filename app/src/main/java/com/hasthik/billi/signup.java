package com.hasthik.billi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class signup extends AppCompatActivity {

    EditText username, password, confirmpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        username=(EditText)findViewById(R.id.editTextTextPersonName);
        password=(EditText)findViewById(R.id.editTextTextPassword);
        confirmpassword=(EditText)findViewById(R.id.editTextTextPassword2);
    }

    public void redirect(View view)
    {

        startActivity(new Intent(this,MainActivity.class));
    }
    public void addrecord(View view)
    {
        logindb db= new logindb(this);
        boolean already_exists=db.checkexist(username.getText().toString());
        if(already_exists)
        {
            Toast.makeText(this, "User already exists. Please login!", Toast.LENGTH_LONG).show();
            password.setText("");
            confirmpassword.setText("");
        }
        else
        {
            if(password.getText().toString().equals(confirmpassword.getText().toString()))
            {
                db.addUser(username.getText().toString(),password.getText().toString());
                Toast.makeText(this, "User added successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this,MainActivity.class));
            }
            else
            {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
                password.setText("");
                confirmpassword.setText("");
            }
        }


    }
}