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

    public void signUp(View view) {
        startActivity(new Intent(StartActivity.this, RegisterActivity.class));
    }

    public void onClick(View view) {
        startActivity(new Intent(StartActivity.this, MainSearchActivity.class));
    }
}