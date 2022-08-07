package edu.neu.madcourse.numad22su_team11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {

    private EditText ETEmail;
    private EditText ETPassword;

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ETEmail = findViewById(R.id.editTextEmail);
        ETPassword = findViewById(R.id.editTextPassword);

        // Connect with Firebase
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void signIn(View view) {
        String email = ETEmail.getText().toString().trim();
        String password = ETPassword.getText().toString();

        if (email.equals("")) {
            Toast.makeText(StartActivity.this, "Email can't be empty", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(StartActivity.this, MainSearchActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(StartActivity.this, "Wrong password or account doesn't exist!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void signUp(View view) {
        startActivity(new Intent(StartActivity.this, RegisterActivity.class));
    }

    public void onClick(View view) {
        startActivity(new Intent(StartActivity.this, MainSearchActivity.class));
    }
}