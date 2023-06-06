package com.example.goodkitchen;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SpecificCategory extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView childrenMealTitle;
    private TextView dessertsTitle;
    private TextView mainCourseTitle;
    private TextView startersTitle;

    private RecyclerView recipeListView;

    private AppCompatButton addRecipeButton;

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.specific_category);

        childrenMealTitle = findViewById(R.id.childrenMealTitle);
        dessertsTitle = findViewById(R.id.dessertsTitle);
        mainCourseTitle = findViewById(R.id.mainCourseTitle);
        startersTitle = findViewById(R.id.startersTitle);
        addRecipeButton = findViewById(R.id.uploadButton);
        recipeListView = findViewById(R.id.mRecyclerView);
        spinner = findViewById(R.id.spinner_tags);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.sort,
                R.layout.spinner_text
        );

        arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text);
        spinner.setAdapter(arrayAdapter);

        // Set the OnItemSelectedListener for the spinner
        spinner.setOnItemSelectedListener(this);

        // Retrieve the passed string from the intent
        String categoryName = getIntent().getStringExtra("categoryName");

        // Check the category and make the corresponding TextView visible
        if (categoryName != null) {
            switch (categoryName) {
                case "Children Meal":
                    childrenMealTitle.setVisibility(View.VISIBLE);
                    RecipeNameAdapter childrenRecipeNameAdapter = new RecipeNameAdapter(DataManager.childrenRecipe, SpecificCategory.this);
                    recipeListView.setLayoutManager(new LinearLayoutManager(this));
                    recipeListView.setAdapter(childrenRecipeNameAdapter);
                    loadRecipesFromDb(DataManager.childrenRecipe,childrenRecipeNameAdapter,this);
                    break;
                case "Desserts":
                    dessertsTitle.setVisibility(View.VISIBLE);
                    RecipeNameAdapter dessertRecipeNameAdapter = new RecipeNameAdapter(DataManager.dessertRecipe, SpecificCategory.this);
                    recipeListView.setLayoutManager(new LinearLayoutManager(this));
                    recipeListView.setAdapter(dessertRecipeNameAdapter);
                    loadRecipesFromDb(DataManager.dessertRecipe,dessertRecipeNameAdapter,this);
                    break;
                case "Main Course":
                    mainCourseTitle.setVisibility(View.VISIBLE);
                    RecipeNameAdapter mainCourseRecipeNameAdapter = new RecipeNameAdapter(DataManager.mainCourseRecipe, SpecificCategory.this);
                    recipeListView.setLayoutManager(new LinearLayoutManager(this));
                    recipeListView.setAdapter(mainCourseRecipeNameAdapter);
                    loadRecipesFromDb(DataManager.mainCourseRecipe,mainCourseRecipeNameAdapter,this);
                    break;
                case "Starters":
                    startersTitle.setVisibility(View.VISIBLE);
                    RecipeNameAdapter startersRecipeNameAdapter = new RecipeNameAdapter(DataManager.startersRecipe, SpecificCategory.this);
                    recipeListView.setLayoutManager(new LinearLayoutManager(this));
                    recipeListView.setAdapter(startersRecipeNameAdapter);
                    loadRecipesFromDb(DataManager.startersRecipe,startersRecipeNameAdapter,this);
                    break;

            }

        }

        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpecificCategory.this, UploadRecipe.class);
                intent.putExtra("categoryName", categoryName);
                startActivityForResult(intent, 1); // Start the activity for result
            }
        });

    }

    private String selectedSortTag = ""; // Track the selected sort tag

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Handle the selection of an item in the spinner
        String newSelectedTag = parent.getItemAtPosition(position).toString();

        String categoryName = getIntent().getStringExtra("categoryName");
        RecipeList recipeList = DataManager.getRecipeListByCategory(categoryName);

        if (recipeList != null) {
            if (!selectedSortTag.equals(newSelectedTag)) {
                // Only sort and update if the selected tag has changed

                switch (newSelectedTag) {
                    case "Name":
                        recipeList.sortByName();
                        break;
                    case "Preparation Time":
                        recipeList.sortByPreparationTime();
                        break;
                    // Add more cases for other sorting options if needed
                }

                // Update the RecyclerView adapter with the sorted list
                RecipeNameAdapter adapter = new RecipeNameAdapter(recipeList, SpecificCategory.this);
                recipeListView.setAdapter(adapter);

                // Update the selected sort tag
                selectedSortTag = newSelectedTag;
            }
        }
    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Handle the case where no item is selected in the spinner
        // ...
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Refresh the RecyclerView with the updated recipe list
            String categoryName = getIntent().getStringExtra("categoryName");
            if (categoryName != null) {
                switch (categoryName) {
                    case "Children Meal":
                        RecipeNameAdapter childrenRecipeNameAdapter = new RecipeNameAdapter(DataManager.childrenRecipe, SpecificCategory.this);
                        recipeListView.setAdapter(childrenRecipeNameAdapter);
                        break;
                    case "Desserts":
                        RecipeNameAdapter dessertRecipeNameAdapter = new RecipeNameAdapter(DataManager.dessertRecipe, SpecificCategory.this);
                        recipeListView.setAdapter(dessertRecipeNameAdapter);
                        break;
                    case "Main Course":
                        RecipeNameAdapter mainCourseRecipeNameAdapter = new RecipeNameAdapter(DataManager.mainCourseRecipe, SpecificCategory.this);
                        recipeListView.setAdapter(mainCourseRecipeNameAdapter);
                        break;
                    case "Starters":
                        RecipeNameAdapter startersRecipeNameAdapter = new RecipeNameAdapter(DataManager.startersRecipe, SpecificCategory.this);
                        recipeListView.setAdapter(startersRecipeNameAdapter);
                        break;
                }
            }
        }
    }


    private void loadRecipesFromDb(RecipeList recipeList, RecipeNameAdapter adapter, Context context) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        String categoryName = getIntent().getStringExtra("categoryName");
        DatabaseReference recipesRef = db.getReference(categoryName);

        recipesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recipeList.clear(); // Clear the existing recipe list

                for (DataSnapshot child : snapshot.getChildren()) {
                    Recipe recipe = child.getValue(Recipe.class);

                    // Convert the image URI string to Uri object if it exists
                    String imageUriString = recipe.getImageUriString();
                    if (imageUriString != null && !imageUriString.isEmpty()) {
                        Uri imageUri = Uri.parse(imageUriString);
                        recipe.setImageUriString(imageUri.toString());
                    }

                    recipeList.addRecipe(recipe); // Add the updated recipe to the list
                }

                Log.d("Tag", DataManager.getRecipeListByCategory(categoryName).toString());
                recipeListView.setLayoutManager(new LinearLayoutManager(context));
                recipeListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the database error
            }
        });
    }

}





