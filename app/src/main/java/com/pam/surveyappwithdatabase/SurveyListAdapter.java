package com.pam.surveyappwithdatabase;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;



public class SurveyListAdapter extends CursorAdapter {

    Context context;

    private static int NAME_COL = 1;
    private static int ANSWER1_COL = 2;
    private static int ANSWER2_COL = 3;

    public SurveyListAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.activity_survey_app, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        //Put data from this cursor (represents one row of the database) into this view (the corresponding row in the list)
        TextView question_text_view = (TextView) view.findViewById(R.id.question_text_view);
        TextView answer1 = (TextView) view.findViewById(R.id.answer1_button);
        TextView answer2 = (TextView) view.findViewById(R.id.answer2_button);
        question_text_view.setText(cursor.getString(NAME_COL));
        answer1.setText(cursor.getString(ANSWER1_COL));
        answer2.setText(cursor.getString(ANSWER2_COL));
    }
}
