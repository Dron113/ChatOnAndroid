package com.example.chatonandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://chatonandroid-7450c-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference myRef = database.getReference("message");

    EditText mEditTextMassage;
    Button mSendButton;
RecyclerView mMassagesRecyler;
ArrayList<String> massages = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSendButton=findViewById(R.id.Button);
mEditTextMassage=findViewById(R.id.masin);
mMassagesRecyler=findViewById(R.id.MassageRecyler);
mMassagesRecyler.setLayoutManager(new LinearLayoutManager(this));
DataAdapter dataAdapter=new DataAdapter(this,massages);
mMassagesRecyler.setAdapter(dataAdapter);
mSendButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String msg=mEditTextMassage.getText().toString();
        if (msg.equals("")){
            Toast.makeText(getApplicationContext(),"Введите сообщение",Toast.LENGTH_SHORT).show();
            return;
        }
        if(msg.length()>150){
            Toast.makeText(getApplicationContext(),"Слишком длиное сообщение",Toast.LENGTH_SHORT).show();
            return;
        }
        myRef.push().setValue(msg);
        mEditTextMassage.setText("");
    }
});
myRef.addChildEventListener(new ChildEventListener() {
    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
String msg=dataSnapshot.getValue(String.class);
massages.add(msg);
dataAdapter.notifyDataSetChanged();
mMassagesRecyler.smoothScrollToPosition(massages.size());
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});
    }
}