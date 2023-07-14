package com.example.goodkitchen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {

    private TextView recipeNameTextView;
    private TextView instructionTextView;
    private TextView ingredientsTextView;
    private TextView prepTimeTextView;
    private AppCompatButton editButton;
    private String category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);

        recipeNameTextView = findViewById(R.id.recipe_name);
        instructionTextView = findViewById(R.id.instructions_list);
        ingredientsTextView = findViewById(R.id.ingredients_list);
        prepTimeTextView = findViewById(R.id.preparation_time);
        editButton = findViewById(R.id.editButton);



        // Retrieve the selected recipe from the intent
        Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");

        category = getIntent().getStringExtra("category");



        // Display the recipe details
        if (recipe != null) {
            recipeNameTextView.setText(recipe.getRecipeName());

            StringBuilder instructionsBuilder = new StringBuilder();
            String instructionList = recipe.getInstructions();
            String[] instructionsArray = instructionList.split("\n");
            instructionsBuilder.append("Instructions:\n\n");
            for (int i = 0; i < instructionsArray.length; i++) {
                String instruction = instructionsArray[i];
                String numberedInstruction = (i + 1) + ". " + instruction + "\n";
                instructionsBuilder.append(numberedInstruction);
            }
            instructionTextView.setText(instructionsBuilder.toString());

            StringBuilder ingredientsBuilder = new StringBuilder();
            String ingredientsList = recipe.getIngredients();
            String[] ingredientsArray = ingredientsList.split("\n");
            ingredientsBuilder.append("Ingredients:\n\n");
            for (int i = 0; i < ingredientsArray.length; i++) {
                String ingredient = ingredientsArray[i];
                String numberedIngredient = (i + 1) + ". " + ingredient + "\n";
                ingredientsBuilder.append(numberedIngredient);
            }
            ingredientsTextView.setText(ingredientsBuilder.toString());

            int preparationTime = recipe.getPreparationTime();
            String preparationTimeText = preparationTime + " minutes";
            prepTimeTextView.setText(preparationTimeText);
        }

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipeActivity.this, UploadRecipe.class);
                intent.putExtra("uploadOrEdit", "Edit"); // Add the string as an extra with a key
                intent.putExtra("recpieName",recipe.getRecipeName().toString());
                intent.putExtra("recpieInstruction",recipe.getInstructions().toString());
                intent.putExtra("recipeIngridients",recipe.getIngredients().toString());
                intent.putExtra("prepTime",recipe.getPreparationTime());
                intent.putExtra("id",recipe.getId());
                intent.putExtra("category",category);
                intent.putExtra("image",recipe.getImageUriString());
                startActivity(intent);;
                finish();
            }
        });
    }
}