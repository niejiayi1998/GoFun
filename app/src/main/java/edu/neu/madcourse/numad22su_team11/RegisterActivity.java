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

import java.util.HashMap;
import java.util.Map;

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
        String name = enterUsername.getText().toString().trim();
        String email = enterEmail.getText().toString().trim();
        String password = enterPassword.getText().toString();

        User newUser = new User(name, email, password);
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.setValue(newUser);

//        if (name.equals("") || email.equals("") || password.equals("")) {
//            Toast.makeText(RegisterActivity.this, "Fields can't be empty", Toast.LENGTH_SHORT).show();
//        } else {
//            // create a new user
//            Toast.makeText(RegisterActivity.this, "hey", Toast.LENGTH_LONG).show();
//            firebaseAuth.createUserWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                firebaseUser = firebaseAuth.getCurrentUser();
//                                assert firebaseUser != null;
//                                String firebaseUserUid = firebaseUser.getUid();
//                                // hierarchy is created here
//                                reference = FirebaseDatabase.getInstance().getReference().child("Users");
//                                User newUser = new User(name, email, password);
//                                reference.push().setValue(newUser)
//                                        // if successful, continue
//                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//                                        Intent intent = new Intent(RegisterActivity.this, SuveryActivity.class);
//                                        startActivity(intent);
//                                        finish();
//                                    }
//                                });
//                            }  else {
//                                // Toast.makeText(RegisterActivity.this, "Something is wrong", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//        }



        //startActivity(new Intent(RegisterActivity.this, SuveryActivity.class));
    }
}