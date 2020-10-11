package me.sample.recipelist;

import android.app.Application;

import me.sample.recipelist.db.Recipe;
import me.sample.recipelist.db.RecipeDao;
import me.sample.recipelist.db.RecipeDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class RecipeViewModel extends AndroidViewModel {

    private RecipeDao recipeDao;
    private ExecutorService executorService;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        recipeDao = RecipeDatabase.getInstance(application).recipeDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    LiveData<List<Recipe>> getAllRecipes() {
        return recipeDao.findAll();
    }

    void addRecipe(Recipe recipe) {
        executorService.execute(() -> recipeDao.save(recipe));
    }

    void deleteRecipe(Recipe recipe) {
        executorService.execute(() -> recipeDao.delete(recipe));
    }

    LiveData<List<Recipe>> searchRecipe(String query) {
        return recipeDao.findSearchValue(query);
    }

    void deleteById(int query) {
        recipeDao.deleteById(query);
    }
}