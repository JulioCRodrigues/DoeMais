package com.julioprojects.doemais.view.Create;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.julioprojects.doemais.R;
import com.julioprojects.doemais.databinding.ActivityCreateBinding;

public class Create extends AppCompatActivity {

    private ActivityCreateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Init spinner
        initSpinner();
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