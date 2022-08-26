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
import com.julioprojects.doemais.R;
import com.julioprojects.doemais.databinding.ActivityLoginBinding;
import com.julioprojects.doemais.view.Create.Create;
import com.julioprojects.doemais.view.Home;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Init auth
        auth = FirebaseAuth.getInstance();


       binding.btnLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               auth = FirebaseAuth.getInstance();

               String email = binding.edtEmailLogin.getText().toString();
               String password = binding.edtPassLogin.getText().toString();

               if(email.isEmpty() || password.isEmpty()){
                   Toast.makeText(Login.this, "Preencha os dados para continuar", Toast.LENGTH_SHORT).show();
               } else {
                   auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(Login.this, Home.class));
                            } else {
                                Toast.makeText(Login.this, "Erro!", Toast.LENGTH_SHORT).show();
                            }
                       }
                   });
               }
           }
       });

        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Create.class));
            }
        });
    }

}