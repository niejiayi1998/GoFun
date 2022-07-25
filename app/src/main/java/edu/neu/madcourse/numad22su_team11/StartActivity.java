package edu.neu.madcourse.numad22su_team11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

    }

    public void goToJulia(View view) {
        startActivity(new Intent(StartActivity.this, MainSearchActivity.class));
    }

    public void goToDaisy(View view) {
        startActivity(new Intent(StartActivity.this, LocationDetailActivity.class));
    }
}