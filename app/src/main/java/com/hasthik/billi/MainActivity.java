package com.hasthik.billi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new logindb(this);
        new productdb(this);
        username= (EditText)findViewById(R.id.editTextTextPersonName);
        password= (EditText)findViewById(R.id.editTextTextPassword);

    }
    public void checkLogin(View view)
    {
        logindb db= new logindb(this);
        boolean isPresent=db.checkpresent(username.getText().toString(),password.getText().toString());
        Log.d(null, password.getText().toString());
        Log.d(null, "checkLogin:working");
        Log.d(null, String.valueOf(isPresent));
        if(isPresent)
        {
            Toast.makeText(this, "Successfully Logged In!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,addItem.class));
        }
        else
        {
            Toast.makeText(this, "No matching username and password", Toast.LENGTH_LONG).show();
            password.setText("");
        }
    }
    public void redirect(View view)
    {
        startActivity(new Intent(this,signup.class));
    }
}