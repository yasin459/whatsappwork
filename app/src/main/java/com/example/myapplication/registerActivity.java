package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class registerActivity extends AppCompatActivity {

    //garbage comment
    private EditText userET, emailET, passET;
    private Button registreBtn;
    private FirebaseAuth auth;
    private DatabaseReference myRefrence;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userET = findViewById(R.id.userEditText);
        emailET=findViewById(R.id.emailEditText);
        passET =findViewById(R.id.passEditText);
        registreBtn=findViewById(R.id.registerButton);

        auth=FirebaseAuth.getInstance();

        registreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tUsername = userET.getText().toString();
                String tPass = passET.getText().toString();
                String tEmail = emailET.getText().toString();

                if(tUsername.isEmpty() || tPass.isEmpty() || tEmail.isEmpty()){
                    Toast.makeText(registerActivity.this,"please complete info boxes",Toast.LENGTH_SHORT).show();
                }
                else {
                    registerNow(tUsername,tPass,tEmail);
                }
            }
        });
    }
     private void registerNow(final  String username, final String pass,final String email){
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    String userid = firebaseUser.getUid();
                    myRefrence = FirebaseDatabase.getInstance().getReference("MyUsers").child(userid);
                    System.out.println(myRefrence.toString());
                    HashMap<String,String > hashMap = new HashMap<>();
                    hashMap.put("id",userid);
                    hashMap.put("username",username);
                    hashMap.put("imageURL","default");
                    System.out.println("entring setvalue");
                    //todo authentication has to be priavate
                    myRefrence.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            System.out.println("after setvalue");
                            if (task.isSuccessful()){
                                Intent intent = new Intent(registerActivity.this,MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(registerActivity.this,"setvlue has failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(registerActivity.this,"invalid username or email",Toast.LENGTH_SHORT).show();
                }
            }
        });
     }
}