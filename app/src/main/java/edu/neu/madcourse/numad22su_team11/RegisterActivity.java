package edu.neu.madcourse.numad22su_team11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.neu.madcourse.numad22su_team11.Model.Event;
import edu.neu.madcourse.numad22su_team11.Model.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText enterUsername;
    private EditText enterEmail;
    private EditText enterPassword;
    private Button buttonNext;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        enterUsername = findViewById(R.id.editTextUsernameSignUp);
        enterEmail = findViewById(R.id.editTextEmailSignUp);
        enterPassword = findViewById(R.id.editTextPasswordSignUp);
        buttonNext = findViewById(R.id.buttonNext);

        // connect with firebase
        firebaseAuth = FirebaseAuth.getInstance();
    }

    // save data in firebase on button click and open the next activity
    public void next(View view) {

        // get name, email and password values
//        String name = enterUsername.getText().toString().trim();
//        String email = enterEmail.getText().toString().trim();
//        String password = enterPassword.getText().toString();
//
//        firebaseAuth.createUserWithEmailAndPassword(email, password);
//
//        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//        assert firebaseUser != null;
//        String firebaseUserUid = firebaseUser.getUid();
//        User newUser = new User(firebaseUserUid,
//                name,
//                email,
//                password,
//                "www.sample.com",
//                new HashMap<>(),
//                new ArrayList<>(),
//                new ArrayList<>());
//        List<Event> eventList = new ArrayList<>();
//        eventList.add(new Event("sample", "sample", "sample", "sample", 0, "sample",3, null));
//        newUser.setJoinedEvents(eventList);
//        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUserUid);
//        reference.setValue(newUser);


        // line starts here

        // get name, email and password values
        String name = enterUsername.getText().toString().trim();
        String email = enterEmail.getText().toString().trim();
        String password = enterPassword.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            assert firebaseUser != null;
                            String firebaseUserUid = firebaseUser.getUid();
                            // create a new user
                            User newUser = new User(firebaseUserUid,
                                    name,
                                    email,
                                    password,
                                    "www.sample.com",
                                    new HashMap<>(),
                                    new ArrayList<>(),
                                    new ArrayList<>());
                            List<Event> eventList = new ArrayList<>();
                            // SAMPLE: set joinedEvent list
                            eventList.add(new Event("sample",
                                    "sample",
                                    "sample",
                                    "sample",
                                    0,
                                    "sample",
                                    3,
                                    null));
                            newUser.setJoinedEvents(eventList);

                            // save to firebase with its unique userID
                            reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUserUid);
                            reference.setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(RegisterActivity.this, SuveryActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(RegisterActivity.this,
                                                "Can't register with the given info.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            if (password.length() < 6) {
                                Toast.makeText(RegisterActivity.this,
                                        "Password should have 6 characters at least!",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(RegisterActivity.this,
                                        "This email has already been registered!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
}
