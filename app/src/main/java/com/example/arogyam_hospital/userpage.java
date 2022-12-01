package com.example.arogyam_hospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class userpage extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public TextView udocnameEdt,uspecailEdt, uemailEdt,utimingEdt,usoltsEdt;
    private TextView username,email,address;
    public Button tv;
    public login log;
    public String index;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpage);

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("HospitalInfo");

        username = findViewById(R.id.up_username);
        email=findViewById(R.id.up_email);
        address=findViewById(R.id.up_address);

        udocnameEdt=findViewById(R.id.up_docname);
        uspecailEdt=findViewById(R.id.up_docspecial);
        uemailEdt=findViewById(R.id.up_docemail);
        utimingEdt=findViewById(R.id.up_doctiming);
        usoltsEdt=findViewById(R.id.up_docslots);


        getdata();
        tv=findViewById(R.id.up_adddoctor);

        tv.setOnClickListener(v->{
            Intent intent2= new Intent(userpage.this,adddoctor.class);
            startActivity(intent2);
        });

    }

    private void getdata() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                username.setText(snapshot.child("01").child("hospitalName").getValue().toString());
                email.setText(snapshot.child("01").child("hospitalEmail").getValue().toString());
                address.setText(snapshot.child("01").child("hospitalAddress").getValue().toString());
                udocnameEdt.setText(snapshot.child("01").child("01").child("doctorName").getValue().toString());
                uemailEdt.setText("Email:"+snapshot.child("01").child("01").child("doctorEmail").getValue().toString());
                uspecailEdt.setText(snapshot.child("01").child("01").child("doctorSpecial").getValue().toString());
                utimingEdt.setText("Timing:"+snapshot.child("01").child("01").child("doctorTiming").getValue().toString());
                usoltsEdt.setText("Slots:"+snapshot.child("01").child("01").child("doctorSlots").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(userpage.this, "Fail to get data.", Toast.LENGTH_SHORT).show();

            }
        });

    }
}