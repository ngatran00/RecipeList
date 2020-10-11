package me.sample.recipelist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import me.sample.recipelist.R;

public class RecipeActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        textView = findViewById(R.id.recipe_display);

        textView.setText(getIntent().getStringExtra("recipe"));
    }
}