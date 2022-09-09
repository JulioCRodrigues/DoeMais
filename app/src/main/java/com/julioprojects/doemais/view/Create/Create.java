package com.julioprojects.doemais.view.Create;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
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
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Init auth
        auth = FirebaseAuth.getInstance();

        // Init database
        database = FirebaseDatabase.getInstance();

        // Init user model
        user = new User();


        // Init spinner
        initSpinner();


        binding.btnCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser(binding.edtEmail.getText().toString(), binding.edtPassword.getText().toString());
            }
        });


    }

    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Populando o objeto user para inserir no banco de dados

                            user.setName(binding.edtName.getText().toString());
                            user.setEmail(binding.edtEmail.getText().toString());
                            user.setPassword(binding.edtPassword.getText().toString());
                            user.setBlood_type(binding.spnBlood.toString());


                            userInsertDatabase(user);
                            Toast.makeText(Create.this, "Usuário criado com sucesso!", Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Create.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void userInsertDatabase(User user){

        reference = database.getReference("users");

        String key = reference.child("users").push().getKey();

        user.setId(key);

        // Salvando os dados no banco
        reference.child(key).setValue(user);

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