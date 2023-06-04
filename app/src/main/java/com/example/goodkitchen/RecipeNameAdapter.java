package com.example.goodkitchen;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeNameAdapter extends RecyclerView.Adapter<RecipeNameAdapter.RecipeViewHolder> {
    private RecipeList recipeList;
    private Context context;

    public RecipeNameAdapter(RecipeList recipeList, Context context) {
        this.recipeList = recipeList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_name, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = getItem(position);
        holder.recipeNameTextView.setText(recipe.getRecipeName());

        // Check if imageUri is null, set the default image resource if it is
        if (recipe.getImageUriString() != null) {
            Uri imageUri = Uri.parse(recipe.getImageUriString());
            holder.recipeImageView.setImageURI(imageUri);
        } else {
            holder.recipeImageView.setImageResource(R.drawable.defaultimageforrecipe); // Set the default image resource here
        }
    }

    private Recipe getItem(int position) {
        return this.recipeList.getRecipes().get(position);
    }

    @Override
    public int getItemCount() {
        return this.recipeList == null ? 0 : recipeList.getRecipes().size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView recipeNameTextView;
        ImageView recipeImageView;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeNameTextView = itemView.findViewById(R.id.recipeName);
            recipeImageView = itemView.findViewById(R.id.recipeImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Recipe clickedRecipe = getItem(position);
                openRecipeActivity(clickedRecipe);
            }
        }

        private void openRecipeActivity(Recipe recipe) {
            Intent intent = new Intent(context, RecipeActivity.class);
            intent.putExtra("recipe", recipe);
            context.startActivity(intent);
        }
    }
}
