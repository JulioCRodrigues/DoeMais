package com.julioprojects.doemais.view.Create;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.julioprojects.doemais.Login.Login;
import com.julioprojects.doemais.R;
import com.julioprojects.doemais.databinding.ActivityCreateBinding;
import com.julioprojects.doemais.models.User;
import com.julioprojects.doemais.view.Home;

import java.util.HashMap;
import java.util.Map;

public class Create extends AppCompatActivity {

    private ActivityCreateBinding binding;
    private FirebaseAuth auth;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Init auth
        auth = FirebaseAuth.getInstance();

        // Init spinner
        initSpinner();


        binding.btnCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });


    }

    private void registerUser() {

        String name = binding.edtName.getText().toString();
        String email = binding.edtEmail.getText().toString();
        String password = binding.edtPassword.getText().toString();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos para continuar!", Toast.LENGTH_SHORT).show();

        } else {
            auth.createUserWithEmailAndPassword(email, email).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        User user = new User(name, email, password);
                        FirebaseDatabase.getInstance().getReference("users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        startActivity(new Intent(Create.this, Login.class));

                                    }
                                });
                    }
                }
            });
        }

    }


    private void initSpinner() {
        Spinner spinner = (Spinner) binding.spnBlood;

        // Adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.bloods_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set adapter
        spinner.setAdapter(adapter);
    }
}