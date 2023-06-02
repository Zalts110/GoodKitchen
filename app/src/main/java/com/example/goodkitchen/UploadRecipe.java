package com.example.goodkitchen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class UploadRecipe extends AppCompatActivity {

    EditText rec_name, rec_description,rec_instructions;
    private AppCompatButton uploadButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_recipe);

        rec_name = (EditText) findViewById(R.id.txt_recipy_name);
        rec_description = (EditText) findViewById(R.id.txt_recipy_description);
        rec_instructions = (EditText) findViewById(R.id.txt_recipy_description);

        String categoryName = getIntent().getStringExtra("categoryName");

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
