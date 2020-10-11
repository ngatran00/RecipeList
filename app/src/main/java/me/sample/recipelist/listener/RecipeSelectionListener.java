package me.sample.recipelist.listener;

import me.sample.recipelist.db.Recipe;

public interface RecipeSelectionListener {

    void onRecipeSelected(Recipe recipe);
}
