package com.pam.surveyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CustomQuestionsActivity extends AppCompatActivity {

    Button confirmButton;
    Button cancelButton;
    EditText customQuestion;
    EditText answer1;
    EditText answer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_questions);

        customQuestion = (EditText) findViewById(R.id.custom_question_text);
        answer1 = (EditText) findViewById(R.id.possible_answer1);
        answer2 = (EditText) findViewById(R.id.possible_answer2);

        final String newQuestion = customQuestion.getText().toString();
        final String newAnswer1 = answer1.getText().toString();
        final String newAnswer2 = answer2.getText().toString();

        confirmButton = (Button) findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                Intent confirmIntent = new Intent(CustomQuestionsActivity.this, SurveyActivity.class);
                confirmIntent.putExtra("question", newQuestion);
                confirmIntent.putExtra("answer1", newAnswer1);
                confirmIntent.putExtra("answer2", newAnswer2);
                setResult(RESULT_OK,confirmIntent);
                //need to pass values into buttons/question text view in SurveyApp
                finish();
            }
        });
        cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resetIntent = new Intent(getApplicationContext(), SurveyActivity.class);
                startActivity(resetIntent);
                customQuestion.getText().clear();
                answer1.getText().clear();
                answer2.getText().clear();
                finish();

            }
        });
    }
}
