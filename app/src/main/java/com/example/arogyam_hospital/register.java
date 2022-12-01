package com.example.arogyam_hospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class register extends AppCompatActivity {
    public EditText usernameEdt,emailEdt, addressEdt,passwordEdt,hselectEdt,indexEdt;
    private Button sendDatabtn;
    private FirebaseAuth mAuth;
    public TextView tv;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEdt = findViewById(R.id.id_username);
        emailEdt = findViewById(R.id.id_email);
        addressEdt = findViewById(R.id.id_address);
        passwordEdt=findViewById(R.id.id_password);
        hselectEdt=findViewById(R.id.id_Hselect);
        indexEdt=findViewById(R.id.id_index1);



        mAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("HospitalInfo");
        tv=findViewById(R.id.login_button);

        tv.setOnClickListener(v->{
            Intent intent2= new Intent(register.this,login.class);
            startActivity(intent2);
        });

        sendDatabtn = findViewById(R.id.button);

        sendDatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = usernameEdt.getText().toString();
                String email = emailEdt.getText().toString();
                String address = addressEdt.getText().toString();
                String password=passwordEdt.getText().toString();
                String hselect=hselectEdt.getText().toString();
                String index = indexEdt.getText().toString();


                if (TextUtils.isEmpty(username) && TextUtils.isEmpty(email) && TextUtils.isEmpty(address)&& TextUtils.isEmpty(hselect)&& TextUtils.isEmpty(password)) {

                    Toast.makeText(register.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {

                    registerNewUser(email,password);
                    addDatatoFirebase(username, email, hselect,address,index);

                }
            }
        });
    }

    private void addDatatoFirebase(String username, String email, String hselect, String address,String index) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.child(index).child("hospitalName").setValue(username);
                databaseReference.child(index).child("hospitalEmail").setValue(email);
                databaseReference.child(index).child("hselect").setValue(hselect);
                databaseReference.child(index).child("hospitalAddress").setValue(address);


                // after adding this data we are showing toast message.
                Toast.makeText(register.this, "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(register.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void registerNewUser(String email, String password) {
        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // create new user or register new user
        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                            "Registration successful!",
                                            Toast.LENGTH_LONG)
                                    .show();

                            Intent intent
                                    = new Intent(register.this,
                                    login.class);
                            startActivity(intent);
                        }
                        else {

                            // Registration failed
                            Toast.makeText(
                                            getApplicationContext(),
                                            "Registration failed!!"
                                                    + " Please try again later",
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });

    }
}