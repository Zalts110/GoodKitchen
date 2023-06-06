package com.example.goodkitchen;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {

    private TextView recipeNameTextView;
    private TextView instructionTextView;
    private TextView ingredientsTextView;
    private TextView prepTimeTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe);

        recipeNameTextView = findViewById(R.id.recipe_name);
        instructionTextView = findViewById(R.id.instructions_list);
        ingredientsTextView = findViewById(R.id.ingredients_list);
        prepTimeTextView = findViewById(R.id.preparation_time);


        // Retrieve the selected recipe from the intent
        Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");

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
    }
}
