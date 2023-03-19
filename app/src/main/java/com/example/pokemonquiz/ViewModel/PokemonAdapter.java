package com.example.pokemonquiz.ViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonquiz.DataAccess.Pokemon;
import com.example.pokemonquiz.R;

import java.util.ArrayList;
import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonHolder> {


    private List<Pokemon> pokemonList = new ArrayList<>();


    @NonNull
    @Override
    public PokemonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.row_layout,parent,false);
        return  new PokemonHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonHolder holder, int position) {
      Pokemon currentPokemon = pokemonList.get(position);

      holder.text.setText(currentPokemon.getName());
      holder.image.setImageBitmap(currentPokemon.getImage());
    }

    @Override
    public int getItemCount() {
        return pokemonList == null ? 0 : pokemonList.size();
    }

    class PokemonHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView image;

        PokemonHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.name_txt);
            image = itemView.findViewById(R.id.imageView);
        }
    }

    public void setPokemonList(List<Pokemon> pokemons) {
        pokemonList = pokemons;
        notifyDataSetChanged();
    }
    public List<Pokemon> getPokemonList(){
        return this.pokemonList;
    }
}
