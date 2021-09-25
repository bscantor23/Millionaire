package com.bscantor.millionaire.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.bscantor.millionaire.R;
import com.bscantor.millionaire.models.Question;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionaryActivity extends AppCompatActivity {

    private TextView txtLevel, txtQuestion;
    private Button btnOption1, btnOption2, btnOption3, btnOption4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionary);

        txtLevel = findViewById(R.id.txtLevel);
        txtQuestion = findViewById(R.id.txtQuestion);

        btnOption1 = findViewById(R.id.btnOption1);
        btnOption2 = findViewById(R.id.btnOption2);
        btnOption3 = findViewById(R.id.btnOption3);
        btnOption4 = findViewById(R.id.btnOption4);




        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("levels").document("7qggSWcxgq7C7scvK5t2").collection("questions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Question> questions = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        questions.add(document.toObject(Question.class));
                    }

                    Random rand = new Random();
                    Question current = questions.get(rand.nextInt(questions.size()));

                    txtLevel.setText("Nivel 1.");
                    txtQuestion.setText(current.name);

                    btnOption1.setText(current.answers.get(0));
                    btnOption2.setText(current.answers.get(1));
                    btnOption3.setText(current.answers.get(2));
                    btnOption4.setText(current.answers.get(3));

                } else {
                    Log.w("Error", task.getException());
                }
            }
        });


    }
}