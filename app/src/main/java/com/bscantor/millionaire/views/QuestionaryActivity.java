package com.bscantor.millionaire.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bscantor.millionaire.R;
import com.bscantor.millionaire.models.Question;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class QuestionaryActivity extends AppCompatActivity implements View.OnClickListener {

    private CircularProgressIndicator loading;
    private LinearLayout content;
    private TextView txtLevel, txtQuestion;
    private Button btnOption1, btnOption2, btnOption3, btnOption4;
    private List<Button> buttons = new ArrayList<>();

    private List<String> levels;
    private int index = 0;
    private Question current;

    //private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionary);

        loading = findViewById(R.id.loading);
        content = findViewById(R.id.content);

        txtLevel = findViewById(R.id.txtLevel);
        txtQuestion = findViewById(R.id.txtQuestion);

        btnOption1 = findViewById(R.id.btnOption1);
        btnOption2 = findViewById(R.id.btnOption2);
        btnOption3 = findViewById(R.id.btnOption3);
        btnOption4 = findViewById(R.id.btnOption4);

        buttons.add(btnOption1);
        buttons.add(btnOption2);
        buttons.add(btnOption3);
        buttons.add(btnOption4);

        btnOption1.setOnClickListener(this);
        btnOption2.setOnClickListener(this);
        btnOption3.setOnClickListener(this);
        btnOption4.setOnClickListener(this);

        loadLevel();
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;

        String correct = current.answers.get(current.correct);

        Drawable check = getResources().getDrawable(R.drawable.ic_baseline_check_circle_outline_24);
        Drawable remove = getResources().getDrawable(R.drawable.ic_baseline_remove_circle_outline_24);

        for (Button current : buttons) {
            if (current.getText().toString().equals(correct)) {
                current.setCompoundDrawablesWithIntrinsicBounds(check, null, null, null);
                current.setBackgroundColor(Color.GREEN);
            } else {
                current.setCompoundDrawablesWithIntrinsicBounds(remove, null, null, null);
                current.setBackgroundColor(Color.RED);
            }
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    index++;
                    if (index < levels.size() && correct.equals(button.getText().toString()))
                        loadQuestion(levels.get(index));
                    else {
                        end();
                    }
                });
            }
        }, 3000);
    }

    private void end() {
        new AlertDialog.Builder(QuestionaryActivity.this)
                .setTitle("Millonario")
                .setMessage("Deseas reiniciar la partida")
                .setPositiveButton("Si", (dialog, which) -> {
                    index = 0;
                    loadQuestion(levels.get(index));
                })
                .setNegativeButton("No", (dialog, i) -> {
                    finish();
                })
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void loadLevel() {
        loading.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //db.clearPersistence();
        db.collection("levels").orderBy("id").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> levels = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        levels.add(document.getId());
                    }
                    Log.i("levels:", "" + levels.size());

                    QuestionaryActivity.this.levels = levels;

                    loadQuestion(levels.get(index));
                } else {
                    Log.w("Error", task.getException());
                }
            }
        });
    }

    public void loadQuestion(String key) {
        loading.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);

        for (Button current : buttons) {
            current.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            current.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
        }

        Log.i("key:", "" + key);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("levels").document(key).collection("questions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Question> questions = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        questions.add(document.toObject(Question.class));
                    }

                    Random rand = new Random();

                    int test = rand.nextInt(questions.size());
                    Log.i("test:", "" + test);

                    current = questions.get(test);

                    txtLevel.setText("Nivel " + (index + 1));
                    txtQuestion.setText(current.name);

                    List<String> answers = new ArrayList<>(current.answers);
                    Collections.shuffle(answers);

                    btnOption1.setText(answers.get(0));
                    btnOption2.setText(answers.get(1));
                    btnOption3.setText(answers.get(2));
                    btnOption4.setText(answers.get(3));

                    loading.setVisibility(View.GONE);
                    content.setVisibility(View.VISIBLE);
                } else {
                    Log.w("Error", task.getException());
                }
            }
        });
    }
}