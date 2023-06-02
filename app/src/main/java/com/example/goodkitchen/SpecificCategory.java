package com.example.goodkitchen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class SpecificCategory extends AppCompatActivity {

    private TextView childrenMealTitle;
    private TextView dessertsTitle;
    private TextView mainCourseTitle;
    private TextView startersTitle;

    private AppCompatButton addRecipeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.specific_category);

        childrenMealTitle = findViewById(R.id.childrenMealTitle);
        dessertsTitle = findViewById(R.id.dessertsTitle);
        mainCourseTitle = findViewById(R.id.mainCourseTitle);
        startersTitle = findViewById(R.id.startersTitle);
        addRecipeButton = findViewById(R.id.uploadButton);

        // Retrieve the passed string from the intent
        String categoryName = getIntent().getStringExtra("categoryName");

        // Check the category and make the corresponding TextView visible
        if (categoryName != null) {
            switch (categoryName) {
                case "Children Meal":
                    childrenMealTitle.setVisibility(View.VISIBLE);
                    break;
                case "Desserts":
                    dessertsTitle.setVisibility(View.VISIBLE);
                    break;
                case "Main Course":
                    mainCourseTitle.setVisibility(View.VISIBLE);
                    break;
                case "Starters":
                    startersTitle.setVisibility(View.VISIBLE);
                    break;

            }
        }

        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpecificCategory.this, UploadRecipe.class);
                intent.putExtra("categoryName", categoryName);
                startActivity(intent);
            }
        });
    }




}


