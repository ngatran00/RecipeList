package me.sample.recipelist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import me.sample.recipelist.db.Recipe;
import me.sample.recipelist.listener.RecipeSelectionListener;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import me.sample.recipelist.R;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeSelectionListener {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private RecipeAdapter recipeAdapter;
    private RecipeViewModel recipeViewModel;
    LiveData<List<Recipe>> recipesList;
    LifecycleOwner context = this;
    boolean flagDelete = false;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recipe_list_recycler);

        recipeAdapter = new RecipeAdapter(this);
        setAdapter();

        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        recipeViewModel.getAllRecipes().observe(this, recipes -> recipeAdapter.setData(recipes));
        recipesList = recipeViewModel.getAllRecipes();
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String userInput = newText.toLowerCase();
                LiveData<List<Recipe>> newRecipeList = recipeViewModel.searchRecipe(userInput);
                newRecipeList.observe(context, recipes -> recipeAdapter.setData(recipes));
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void addRecipe (View view){
        Intent intent = new Intent(this, AddActivity.class);
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 1) {
                String name = data.getStringExtra("name");
                String ingredient = data.getStringExtra("ingredient");
                String recipe = data.getStringExtra("recipe");
                recipeViewModel.addRecipe(new Recipe(name, ingredient, recipe));
            }
        }
    }

    @Override
    public void onRecipeSelected(Recipe recipe) {
        if (! flagDelete){
            Intent intent = new Intent(this, RecipeActivity.class);
            String val = recipe.getRecipeName() + "\n\n" +
                    "Ingredients:\n" + recipe.getIngredients() + "\n\n" +
                    "Recipe:\n" + recipe.getRecipe();
            intent.putExtra("id", recipe.getId());
            intent.putExtra("recipe", val);
            startActivity(intent);
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder
                    .setMessage("Delete " + recipe.getRecipeName() + "?")
                    .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            recipeViewModel.deleteRecipe(recipe);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.cancel();
                        }
                    })
                    .show();
            flagDelete = false;
            button.setBackgroundColor(0xFFC1C1C1);
        }
    }

    void setAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recipeAdapter);
    }

    public void deleteRecipe(View view) {
        button = (Button) findViewById(R.id.button_delete);
        flagDelete = !flagDelete;
        if (flagDelete) {
            button.setBackgroundColor(0xFFFB5E5E);
        }
        else {
            button.setBackgroundColor(0xFFC1C1C1);
        }
    }
}