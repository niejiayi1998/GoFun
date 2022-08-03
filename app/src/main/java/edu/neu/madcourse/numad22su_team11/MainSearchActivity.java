package edu.neu.madcourse.numad22su_team11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainSearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search);

    }

    public void onClick(View view) {
        Intent intent = new Intent(MainSearchActivity.this, SearchDisplayActivity.class);
        startActivity(intent);
    }
}