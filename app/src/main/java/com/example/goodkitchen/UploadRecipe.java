package com.example.goodkitchen;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class UploadRecipe extends AppCompatActivity {

    EditText rec_name, rec_ingredients,rec_instructions,rec_preptime;
    private AppCompatButton uploadButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_recipe);

        rec_name = (EditText) findViewById(R.id.txt_recipy_name);
        rec_ingredients = (EditText) findViewById(R.id.txt_recipy_description);
        rec_instructions = (EditText) findViewById(R.id.txt_recipy_description);
        rec_preptime = (EditText) findViewById(R.id.txt_recipy_preptime);
        String categoryName = getIntent().getStringExtra("categoryName");

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public void uploadRecipe(){
        String name = rec_name.getText().toString();
        String description = rec_ingredients.getText().toString();
        String instructions = rec_instructions.getText().toString();
        String preptimeString = rec_preptime.getText().toString();

        int preptime = Integer.parseInt(preptimeString);

        Recipe recipe = new Recipe();
        recipe.setRecipeName(name);
        recipe.setIngridents(description);
        recipe.setInstructions(instructions);
        recipe.setPreparationTime(preptime);

        String categoryName = getIntent().getStringExtra("categoryName");

        if (categoryName != null) {
            if (categoryName.equals("Desserts")) {
                DataManager.dessertRecipe.addRecipe(recipe);
            } else if (categoryName.equals("Children Meal")) {
                DataManager.childrenRecipe.addRecipe(recipe);
            } else if (categoryName.equals("Main Course")) {
                DataManager.mainCourseRecipe.addRecipe(recipe);
            } else if (categoryName.equals("Starters")) {
                DataManager.startersRecipe.addRecipe(recipe);
            }
            // Add more conditions for other categories if needed
        }





    }

}
