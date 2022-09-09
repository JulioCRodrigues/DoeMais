package com.julioprojects.doemais.Login;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.julioprojects.doemais.R;
import com.julioprojects.doemais.databinding.ActivityLoginBinding;
import com.julioprojects.doemais.view.Create.Create;
import com.julioprojects.doemais.view.Home;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding binding;

    // Instanciando firebase
    private FirebaseAuth mAuth;

    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Recuperando instancia firebase
        mAuth = FirebaseAuth.getInstance();


        // Eventos de clique

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.edtEmailLogin.getText().toString().isEmpty() || binding.edtPassLogin.getText().toString().isEmpty()){
                    Toast.makeText(Login.this, "Preencha os campos para continuar!", Toast.LENGTH_SHORT).show();
                } else {
                    login(binding.edtEmailLogin.getText().toString(), binding.edtPassLogin.getText().toString());
                }
            }
        });

        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreate();

            }
        });

        binding.tvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    private void openCreate() {
        startActivity(new Intent(Login.this, Create.class));
    }

    private void login(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            startActivity(new Intent(Login.this, Home.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}