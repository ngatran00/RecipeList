package me.sample.recipelist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import me.sample.recipelist.R;

public class AddActivity extends AppCompatActivity {

    EditText editName, editIngredient, editRecipe;
    Button buttonSubmit, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Intent intent = new Intent(this, MainActivity.class);
        editName = (EditText) findViewById(R.id.editName);
        editIngredient = (EditText) findViewById(R.id.editIngredient);
        editRecipe = (EditText) findViewById(R.id.editRecipe);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        buttonCancel = (Button) findViewById(R.id.buttonCancel);

        buttonSubmit.setOnClickListener(v -> {
            String name = editName.getText().toString();
            String ingredient = editIngredient.getText().toString();
            String recipe = editRecipe.getText().toString();
            intent.putExtra("name", name);
            intent.putExtra("ingredient", ingredient);
            intent.putExtra("recipe", recipe);
            setResult(1, intent);
            finish();
        });

        buttonCancel.setOnClickListener(v -> {
            setResult(0, intent);
            finish();
        });
    }
}