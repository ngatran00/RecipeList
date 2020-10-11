package me.sample.recipelist.db;

import androidx.annotation.NonNull;
import android.content.Context;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import androidx.room.Room;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

@Database(entities = {Recipe.class}, version = 2 ,exportSchema = false)
public abstract class RecipeDatabase extends RoomDatabase {

    private static RecipeDatabase INSTANCE;

    private final static List<Recipe> RECIPES = Arrays.asList(
            new Recipe("White radish with egg",
                    "White radish\nEgg\nFish sauce\nSalt\nPepper\n",
                    "1. Mix egg with fish sauce\n" +
                            "2. Fry radish\n" +
                            "3. Add egg\n" +
                            "4. Fry until egg is cooked"),
            new Recipe("Spicy rib",
                    "Rib\nLemon extract\nSalt\nSugar\nSiracha\n",
                    "1. Boil rib\n" +
                            "2. Fry rib\n" +
                            "3. Mix sauce\n" +
                            "4. Pour sauce on rib until thicken"),
            new Recipe("Corn rib soup",
                    "Corn\nRib\nPotato\nSalt\nPepper\nBroth mix\n",
                    "1. Boil rib and corn\n" +
                            "2. Add potato"),
            new Recipe("Spicy pork belly",
                    "Pork belly\nOnion\n1tbs Soy sauce\n1/4cup Red pepper paste\n" +
                            "1tbs Red pepper flake\n1tbs Brown sugar\n",
                    "1. Add all ingredients and fry"),
            new Recipe("Chicken soup",
                    "Cauliflower\nLeek\nNoodles\nChicken\nMaggi\nBroth mix\nSalt\nPepper\n",
                    "1. Boil everything"),
            new Recipe("Pork garlic",
                    "Pork\nGarlic\nFish sauce\nSugar\nBread crumb\n",
                    "1. Mix pork with bread crumb\n" +
                            "2. Fry pork\n" +
                            "3. Mix sauce\n" +
                            "4. Add pork to sauce"),
            new Recipe("Tomato egg soup",
                    "Tomato\nEgg\nSalt\nBroth mix\n",
                    "1. Cut tomato\n" +
                            "2. Fry tomato until soft\n" +
                            "3. Add water\n" +
                            "4. Add egg to boiling water")
            );

    public abstract RecipeDao recipeDao();

    private static final Object sLock = new Object();

    public static RecipeDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        RecipeDatabase.class, "recipes.db")
                        .allowMainThreadQueries()
                        .addCallback(new Callback() {
                            @Override
                            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                super.onCreate(db);
                                Executors.newSingleThreadExecutor().execute(
                                        () -> getInstance(context).recipeDao().saveAll(RECIPES));
                            }
                        })
                        .build();
            }
            return INSTANCE;
        }
    }
}
