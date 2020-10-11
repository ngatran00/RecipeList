package me.sample.recipelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.sample.recipelist.R;

import me.sample.recipelist.db.Recipe;
import me.sample.recipelist.listener.RecipeSelectionListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> data;
    private Context context;
    private LayoutInflater layoutInflater;
    private RecipeSelectionListener selectionListener;

    public RecipeAdapter(Context context){
        this.data = new ArrayList<>();
        this.context = context;
        selectionListener = (MainActivity) context;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item, parent, false);
        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeViewHolder holder, int position) {
        Recipe recipe = data.get(position);
        holder.recipeName.setText(recipe.getRecipeName());

        holder.recipeName.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionListener.onRecipeSelected(data.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Recipe> newData) {
        if (data != null) {
            data.clear();
            data.addAll(newData);
            notifyDataSetChanged();
        } else {
            data = newData;
        }
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder{

        private TextView recipeName;

        public RecipeViewHolder(View itemView) {
            super(itemView);

            recipeName = itemView.findViewById(R.id.recipeName);
        }

        void bind(final Recipe recipe) {
            if (recipe != null) {
                recipeName.setText(recipe.getRecipeName());
            }
        }

    }
}
