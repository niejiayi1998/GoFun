package edu.neu.madcourse.numad22su_team11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.numad22su_team11.Model.User;

public class SuveryActivity extends AppCompatActivity {

    private CheckBox CBHiking;
    private CheckBox CBSports;
    private CheckBox CBAmusement;
    private CheckBox CBArts;
    private CheckBox CBScenicView;
    private CheckBox CBEntertainment;

    DatabaseReference reference;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suvery);

        CBHiking = findViewById(R.id.checkBoxHiking);
        CBSports = findViewById(R.id.checkBoxSports);
        CBAmusement = findViewById(R.id.checkBoxAmusement);
        CBArts = findViewById(R.id.checkBoxArts);
        CBScenicView = findViewById(R.id.checkBoxScenicView);
        CBEntertainment = findViewById(R.id.checkBoxEntertainment);

        // connect with firebase
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

    }

    // onclick method for letsGoFun button
    public void letsGoFun(View view) {
        List<Integer> preferences = new ArrayList<>();
        if (CBHiking.isChecked()) preferences.add(1);
        else preferences.add(0);
        if (CBSports.isChecked()) preferences.add(1);
        else preferences.add(0);
        if (CBAmusement.isChecked()) preferences.add(1);
        else preferences.add(0);
        if (CBArts.isChecked()) preferences.add(1);
        else preferences.add(0);
        if (CBScenicView.isChecked()) preferences.add(1);
        else preferences.add(0);
        if (CBEntertainment.isChecked()) preferences.add(1);
        else preferences.add(0);

        // UPDATE USER DATA!!!
        reference.child("preferences").setValue(preferences);
        startActivity(new Intent(SuveryActivity.this, MainSearchActivity.class));
        finish();
    }
}