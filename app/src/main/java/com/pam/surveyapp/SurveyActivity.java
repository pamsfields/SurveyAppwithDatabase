package com.pam.surveyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SurveyActivity extends AppCompatActivity {

    private Button mYesButton;
    private Button mNoButton;
    private Button resultsButton;
    private Button customButton;
    protected TextView QuestionTextView;
    private int yesCount = 0;
    private int noCount = 0;
    private String noCountString;
    private String yesCountString;
    private static final int RESULTS_CODE = 0;
    private static final int CUSTOM_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_app);

        Intent launchIntent = getIntent();

        launchIntent.putExtra("no answers", noCount);
        launchIntent.putExtra("yes answers", yesCount);

        QuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mYesButton = (Button) findViewById(R.id.answer1_button);
        mYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yesCount++;
                Toast.makeText(SurveyActivity.this, "Thank You!", Toast.LENGTH_SHORT).show();
                yesCountString = "@string/yes_count_text" + yesCount;
            }
        });
        mNoButton = (Button) findViewById(R.id.answer2_button);
        mNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noCount++;
                Toast.makeText(SurveyActivity.this, "Thank You!", Toast.LENGTH_SHORT).show();
                noCountString = "@string/no_count_text" + noCount;
            }
        });
        resultsButton = (Button) findViewById(R.id.results_button);
        resultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start ResultsActivity
                Intent resultsIntent = new Intent(SurveyActivity.this, ResultsActivity.class);
                startActivityForResult(resultsIntent, RESULTS_CODE);
                onActivityResult(CUSTOM_CODE, RESULT_OK, resultsIntent);
            }
        });

        customButton = (Button) findViewById(R.id.custom_button);
        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start CustomActivity
                Intent customIntent = new Intent(SurveyActivity.this, CustomQuestionsActivity.class);
                startActivityForResult(customIntent, CUSTOM_CODE);
                onActivityResultCustom(CUSTOM_CODE, RESULT_OK, customIntent);
            }
        });
    }

    protected void onActivityResultCustom(int CUSTOM_CODE, int resultCode, Intent customIntent) {
        // Check which request it is that we're responding to
        if (CUSTOM_CODE == RESULT_OK) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                //get the requested information from CustomQuestionActivity
                //set up question and answers for the survey
                //restart SurveyActivity so new question comes up
            }
        }
    }
    @Override
    protected void onActivityResult(int RESULTS_CODE, int resultCode, Intent resultsIntent) {
        // Check which request it is that we're responding to
        if (RESULTS_CODE == RESULT_OK) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // reset counts for noCount and yesCount
            }
        }
    }
}
