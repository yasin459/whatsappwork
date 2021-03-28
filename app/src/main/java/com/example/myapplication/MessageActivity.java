package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.myapplication.Model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MessageActivity extends AppCompatActivity {
    private TextView username;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private EditText message;
    private ImageButton send_btn;
    private Intent intent;
    private  FirebaseUser fUser;
    private DatabaseReference databaseReference;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        imageView = findViewById(R.id.imageview_profile);
        username = findViewById(R.id.username_profile);
        recyclerView= findViewById(R.id.recyclerView);
        message=findViewById(R.id.text_sender);
        send_btn=findViewById(R.id.btn_send);




//        Toolbar toolbar = findViewById(R.id.toolBar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        intent = getIntent();
        String userid = intent.getStringExtra("userid");
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("MyUsers").child(userid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                username.setText(user.getUsername());
                if(user.getImageURL().equals("defualt")){
                    imageView.setImageResource(R.mipmap.ic_launcher);
                }
                else {
                    Glide.with(MessageActivity.this).load(user.getImageURL()).into(imageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg =message.getText().toString();
                if (!msg.equals("")){
                    sendeMessage(fUser.getUid(),userid,msg);

                }
                else {
                    Toast.makeText(MessageActivity.this,"you entered nothing",Toast.LENGTH_SHORT).show();

                }
                message.setText("");
                
            }
        });


    }

    private void sendeMessage(String sender, String receiver , String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String ,String> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);
        reference.child("Chats").push().setValue(hashMap);


    }

    private void setSupportActionBar(Toolbar toolbar) {
    }
}