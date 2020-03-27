package com.melih.instaklon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth ;
    EditText email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null){
            Intent intent = new Intent(MainActivity.this,FeedActivity.class);
            startActivity(intent);
            finish();
        }

    }
    public void signupclick(View view){
        String emailadres= email.getText().toString();
        String pasword = password.getText().toString();
         firebaseAuth.createUserWithEmailAndPassword(emailadres,pasword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(MainActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,FeedActivity.class);
                startActivity(intent);
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });

    }
    public void signinclick(View view){
        String emailadres= email.getText().toString();
        String pasword = password.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(emailadres,pasword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Intent intent = new Intent(MainActivity.this,FeedActivity.class);
                startActivity(intent);
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG);
            }
        });
    }
}
