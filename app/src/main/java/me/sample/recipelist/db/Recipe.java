package me.sample.recipelist.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe_db")
public class Recipe {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "recipe_name")
    private String recipeName;
    @ColumnInfo(name = "ingredients")
    private String ingredients;
    @ColumnInfo(name = "recipe")
    private String recipe;

    public Recipe() {
    }

    @Ignore
    public Recipe(String recipeName, String ingredients, String recipe) {
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.recipe = recipe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }
}