package com.pam.surveyappwithdatabase;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ResultsActivity extends AppCompatActivity {

    private Button continueButton;
    private Button resetButton;
    private TextView no_Counter;
    private TextView yes_Counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Bundle resultsBundle = getIntent().getExtras();
        final String noCountString = resultsBundle.getString("noCountString");
        final String yesCountString = resultsBundle.getString("yesCountString");

        yes_Counter= (TextView) findViewById(R.id.yes_Counter);
        yes_Counter.setText( yesCountString);

        no_Counter= (TextView) findViewById(R.id.no_Counter);
        no_Counter.setText(noCountString);

        continueButton = (Button) findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        resetButton= (Button) findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resetIntent = new Intent(getApplicationContext(), SurveyActivity.class);
                startActivity(resetIntent);
                yes_Counter.setText("Yes Counts:0");
                no_Counter.setText("No Counts:0");
                finish();

            }
        });
    }
}
