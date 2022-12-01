package com.example.arogyam_hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         Button login_button = (Button) findViewById(R.id.login);

        login_button.setOnClickListener(v->{
            Intent intent= new Intent(MainActivity.this,login.class);
            startActivity(intent);
        });
        Button register_button = (Button) findViewById(R.id.register);

        register_button.setOnClickListener(v->{
            Intent intent1= new Intent(MainActivity.this,register.class);
            startActivity(intent1);
        });
    }
}