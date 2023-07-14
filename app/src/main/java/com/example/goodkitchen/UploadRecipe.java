package com.example.goodkitchen;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UploadRecipe extends AppCompatActivity {

    EditText rec_name, rec_ingredients, rec_instructions, rec_preptime;
    private ImageView iv_foodImage;
    private AppCompatButton uploadButton, selectPictureButton,editButton;

    private static final int SELECT_PICTURE_REQUEST_CODE = 1;
    private Uri selectedImageUri;
    private DatabaseReference mDatabase;

    private String category,image;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_recipe);

        rec_name = findViewById(R.id.txt_recipy_name);
        rec_ingredients = findViewById(R.id.txt_recipy_description);
        rec_instructions = findViewById(R.id.txt_recipy_instruction);
        rec_preptime = findViewById(R.id.txt_recipy_preptime);
        iv_foodImage = findViewById(R.id.iv_foodImage);
        uploadButton = findViewById(R.id.uploadButton);
        selectPictureButton = findViewById(R.id.selectPictureButton);
        editButton = findViewById(R.id.editButton);


        String uploadOrEdit = getIntent().getStringExtra("uploadOrEdit");
        Log.d("uploadoredit",uploadOrEdit.toString());

        if (uploadOrEdit != null) {
            switch (uploadOrEdit) {
                case "Edit":
                    uploadButton.setVisibility(View.INVISIBLE);
                    editButton.setVisibility(View.VISIBLE);
                    selectPictureButton.setVisibility(View.INVISIBLE);
                    editRecipe();
                    break;
                case "Upload":
                    uploadButton.setVisibility(View.VISIBLE);
                    editButton.setVisibility(View.INVISIBLE);
                    break;
            }
        }

        selectPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPicture();
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadRecipe();
                Intent intent = new Intent(UploadRecipe.this, CategoriesActivity.class);
                startActivity(intent);
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = rec_name.getText().toString();
                String ingredients = rec_ingredients.getText().toString();
                String instructions = rec_instructions.getText().toString();
                String preptimeString = rec_preptime.getText().toString();

                if (!preptimeString.matches("\\d+")) {
                    // Input is not a valid number
                    Toast.makeText(getApplicationContext(), "You entered an invalid preparation time", Toast.LENGTH_SHORT).show();
                    return;
                }

                int preptime = Integer.parseInt(preptimeString);

                Recipe recipe = new Recipe();
                recipe.setRecipeName(name);
                recipe.setIngredients(ingredients);
                recipe.setInstructions(instructions);
                recipe.setPreparationTime(preptime);
                recipe.setUID(FirebaseAuth.getInstance().getUid());
                recipe.setImageUriString(image);
                updateRecipeToDb(recipe);
                Intent intent = new Intent(UploadRecipe.this, CategoriesActivity.class);
                startActivity(intent);
            }
        });
    }



    private void selectPicture() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_PICTURE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PICTURE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();

            // Add this block to handle the permission grant
            if (selectedImageUri != null) {
                getContentResolver().takePersistableUriPermission(
                        selectedImageUri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                );
            }

            iv_foodImage.setImageURI(selectedImageUri);
        }
    }


    public void uploadRecipe() {
        String name = rec_name.getText().toString();
        String ingredients = rec_ingredients.getText().toString();
        String instructions = rec_instructions.getText().toString();
        String preptimeString = rec_preptime.getText().toString();

        if (!preptimeString.matches("\\d+")) {
            // Input is not a valid number
            Toast.makeText(this, "You entered an invalid preparation time", Toast.LENGTH_SHORT).show();
            return;
        }

        int preptime = Integer.parseInt(preptimeString);

        Recipe recipe = new Recipe();
        recipe.setRecipeName(name);
        recipe.setIngredients(ingredients);
        recipe.setInstructions(instructions);
        recipe.setPreparationTime(preptime);
        recipe.setUID(FirebaseAuth.getInstance().getUid());

        // Convert selectedImageUri to string and set it as the image URI
        if (selectedImageUri != null) {
            String imageUriString = selectedImageUri.toString();
            recipe.setImageUriString(imageUriString);
        }


        String categoryName = getIntent().getStringExtra("categoryName");

        // Add the recipe to the appropriate category in DataManager
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
            saveToDb(recipe,categoryName);
            uploadImage(Uri.parse(recipe.getImageUriString()),recipe);
        }
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
    public void saveToDb(Recipe recipe,String category){
        mDatabase = FirebaseDatabase.getInstance().getReference(category);
        mDatabase.push().setValue(recipe);
        Log.d("Tag1", mDatabase.toString());

    }

    public static void uploadImage(Uri imageURi, Recipe recipe) {
        String fileName = String.valueOf(System.currentTimeMillis());

        StorageReference storageReference = FirebaseStorage.getInstance().getReference("IMAGES/" + fileName);
        storageReference.putFile(imageURi).continueWithTask(task -> {
            return storageReference.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri downloadURi = task.getResult();
                recipe.setImageUriString(downloadURi.toString());
            }
        });
    }

    public void editRecipe(){
        rec_name.setText(getIntent().getStringExtra("recpieName"));
        rec_instructions.setText(getIntent().getStringExtra("recpieInstruction"));
        rec_ingredients.setText(getIntent().getStringExtra("recipeIngridients"));
        rec_preptime.setText("" + getIntent().getIntExtra("prepTime",0));
        category = getIntent().getStringExtra("category");
        id = getIntent().getStringExtra("id");
        image = getIntent().getStringExtra("image");

    }

    private void updateRecipeToDb(Recipe recipe) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference recipesRef = db.getReference(category);

        recipesRef.orderByChild("id").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot child : snapshot.getChildren()){
                        String key = child.getKey();
                        recipesRef.child(key).setValue(recipe);
                        Log.d("shakshuka",key.toString());
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the database error
            }
        });

        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}