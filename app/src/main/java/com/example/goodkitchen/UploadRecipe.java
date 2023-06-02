package com.example.goodkitchen;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class UploadRecipe extends AppCompatActivity {

    EditText rec_name, rec_description,rec_instructions;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_recipe);

        rec_name = (EditText) findViewById(R.id.txt_recipy_name);
        rec_description = (EditText) findViewById(R.id.txt_recipy_description);
        rec_instructions = (EditText) findViewById(R.id.txt_recipy_description);

    }
}
