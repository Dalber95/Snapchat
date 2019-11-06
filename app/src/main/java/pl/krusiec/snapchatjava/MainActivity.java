package pl.krusiec.snapchatjava;

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
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText emailEditText;
    EditText passwordEditText;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            logIn();
        }
    }

    public void goClicked(View view) {
        // Check if we can log in the user
        mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            logIn();
                        } else {
                            // Sign up the user
                            mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                FirebaseDatabase.getInstance().getReference().child("users").child(task.getResult().getUser().getUid())
                                                        .child("email").setValue(emailEditText.getText().toString());
                                                logIn();
                                            } else {
                                                Toast.makeText(MainActivity.this, "Login Failed. Try Again.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    public void logIn() {
        // Move to next Activity
        Intent intent = new Intent(MainActivity.this, SnapsActivity.class);
        startActivity(intent);
    }
}
