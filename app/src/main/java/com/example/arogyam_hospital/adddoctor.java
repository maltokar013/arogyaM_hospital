package com.example.arogyam_hospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

public class adddoctor extends AppCompatActivity {

    public EditText docnameEdt,specailEdt, emailEdt,timingEdt,soltsEdt,indexEdt;
    private Button tv;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddoctor);

        docnameEdt=findViewById(R.id.ap_docname);
        specailEdt=findViewById(R.id.ap_typeof);
        emailEdt=findViewById(R.id.ap_email);
        timingEdt=findViewById(R.id.ap_timing);
        soltsEdt=findViewById(R.id.ap_slot);
        indexEdt=findViewById(R.id.ap_index);


        TextView hp=(TextView)findViewById(R.id.ap_hospage);
        hp.setOnClickListener(v->{
            Intent intent2= new Intent(adddoctor.this,userpage.class);
            startActivity(intent2);

        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("HospitalInfo");

        tv=findViewById(R.id.ap_add);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String docname = docnameEdt.getText().toString();
                String email = emailEdt.getText().toString();
                String special = specailEdt.getText().toString();
                String timing=timingEdt.getText().toString();
                String slots=soltsEdt.getText().toString();
                String index = indexEdt.getText().toString();


                if (TextUtils.isEmpty(docname) && TextUtils.isEmpty(email) && TextUtils.isEmpty(special)&& TextUtils.isEmpty(timing)&& TextUtils.isEmpty(slots)&& TextUtils.isEmpty(index)) {

                    Toast.makeText(adddoctor.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {
                    addDatatoFirebase(docname, email, special,timing,slots,index);

                }
            }
        });
    }

    private void addDatatoFirebase(String docname, String email, String special, String timing,String slots,String index) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.child("01").child(index).child("doctorName").setValue(docname);
                databaseReference.child("01").child(index).child("doctorEmail").setValue(email);
                databaseReference.child("01").child(index).child("doctorSpecial").setValue(special);
                databaseReference.child("01").child(index).child("doctorTiming").setValue(timing);
                databaseReference.child("01").child(index).child("doctorSlots").setValue(slots);


                // after adding this data we are showing toast message.
                Toast.makeText(adddoctor.this, "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(adddoctor.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });

    }
}