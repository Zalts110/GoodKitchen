package com.example.goodkitchen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity
{

    private AppCompatButton starters,childrenMeal,mainCourse,desserts;
    private DataManager dataManager = new DataManager();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_activity);

        starters = findViewById(R.id.startersButton);
        childrenMeal = findViewById(R.id.childrenMealButton);
        mainCourse = findViewById(R.id.mainCourseButton);
        desserts = findViewById(R.id.dessertsButton);


        starters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoriesActivity.this, SpecificCategory.class);
                intent.putExtra("categoryName", "Starters"); // Add the string as an extra with a key
                startActivity(intent);
            }
        });

        childrenMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the new activity here
                Intent intent = new Intent(CategoriesActivity.this, SpecificCategory.class);
                intent.putExtra("categoryName", "Children Meal"); // Add the string as an extra with a key
                startActivity(intent);
            }
        });

        desserts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the new activity here
                Intent intent = new Intent(CategoriesActivity.this, SpecificCategory.class);
                intent.putExtra("categoryName", "Desserts"); // Add the string as an extra with a key
                startActivity(intent);
            }
        });

        mainCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the new activity here
                Intent intent = new Intent(CategoriesActivity.this, SpecificCategory.class);
                intent.putExtra("categoryName", "Main Course"); // Add the string as an extra with a key
                startActivity(intent);
            }
        });

    }

}
