package com.melih.instaklon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SnapshotMetadata;

import java.util.ArrayList;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<String> useremailFB;
    ArrayList<String> commentFB;
    ArrayList<String> imageFB;
    FeedReyclerAdapter feedReyclerAdapter;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== R.id.upload){
            Intent intent = new Intent(FeedActivity.this,UploadActivity.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.signout){
            firebaseAuth.signOut();

            Intent intentt = new Intent(FeedActivity.this,MainActivity.class);
            startActivity(intentt);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_feed);

        useremailFB = new ArrayList<>();
        commentFB = new ArrayList<>();
        imageFB =new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        getdatafromfirestore();


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        feedReyclerAdapter = new FeedReyclerAdapter(useremailFB,commentFB,imageFB);
        recyclerView.setAdapter(feedReyclerAdapter);
    }

    public void getdatafromfirestore(){
        CollectionReference collectionReference = firebaseFirestore.collection("Posts");
        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(FeedActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();
                }
                if(queryDocumentSnapshots!=null){
                    for(DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()){
                        Map<String,Object> data = snapshot.getData();

                        String comment = (String) data.get("commenttext");
                        String useremail = (String) data.get("useremail");
                        String downloadurl = (String) data.get("downloadurl");

                        useremailFB.add(useremail);
                        commentFB.add(comment);
                        imageFB.add(downloadurl);
                        feedReyclerAdapter.notifyDataSetChanged();

                        Toast.makeText(FeedActivity.this, comment, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
