package com.bscantor.millionaire.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.bscantor.millionaire.R;
import com.bscantor.millionaire.controllers.SingletonFirebase;
import com.bscantor.millionaire.models.Level;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("levels").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<Level> levels = new ArrayList<>();
                    for(QueryDocumentSnapshot document : task.getResult()){
                        levels.add(document.toObject(Level.class));
                    }

                    Log.i("levels: ", ""+ levels.size());

                }else{
                    Log.w("Error",task.getException());
                }
            }
        });


    }
}