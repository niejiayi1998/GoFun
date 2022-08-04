package edu.neu.madcourse.numad22su_team11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LocationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);
    }

    public void onClick (View view) {
        startActivity(new Intent(LocationDetailActivity.this, UserProfileActivity.class));
    }
}