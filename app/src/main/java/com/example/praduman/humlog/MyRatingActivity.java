package com.example.praduman.humlog;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MyRatingActivity extends ActionBarActivity {

    private TextView ratingDataTextView;
    private TextView jobsDoneTextView;
    private TextView scoreTextView;
    private String username;
    private HumLogController humLogController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rating);
        humLogController = (HumLogController) getIntent().getSerializableExtra("controllerObject");
        humLogController.setModelObject();
        username = getIntent().getStringExtra("username");
        setTextView();
        setData();
    }

    private void setTextView(){
        ratingDataTextView = (TextView) findViewById(R.id.myRatingRatingDataText);
        jobsDoneTextView = (TextView) findViewById(R.id.myRatingJobsDataText);
        scoreTextView = (TextView) findViewById(R.id.myRatingTotalScoreDataText);
    }

    private void setData(){
        humLogController.setProfileRatings(username);
        ratingDataTextView.setText(humLogController.getRatings());
        jobsDoneTextView.setText(humLogController.getJobsDone());
        scoreTextView.setText(humLogController.getScore());
    }

}
