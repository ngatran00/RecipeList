package me.sample.recipelist.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<Recipe> recipes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(Recipe recipe);

    @Update
    void update(Recipe recipe);

    @Delete
    void delete(Recipe recipe);

    @Query("SELECT * FROM recipe_db")
    LiveData<List<Recipe>> findAll();

    @Query("SELECT * FROM recipe_db WHERE recipe_name LIKE '%' || :query || '%' " +
            "OR ingredients LIKE '%' || :query || '%'")
    LiveData<List<Recipe>> findSearchValue(String query);

    @Query("DELETE FROM recipe_db WHERE id = :query")
    void deleteById(int query);

}