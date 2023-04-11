package com.example.pokemonquiz.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.pokemonquiz.DataAccess.Pokemon;
import com.example.pokemonquiz.R;
import com.example.pokemonquiz.ViewModel.PokemonAdapter;
import com.example.pokemonquiz.ViewModel.PokemonViewModel;

import java.util.List;

public class AnswerActivity extends AppCompatActivity {

    private PokemonViewModel pokemonViewModel;
    private List<Pokemon> pokemonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        PokemonAdapter adapter = new PokemonAdapter();
        recyclerView.setAdapter(adapter);


        pokemonViewModel = ViewModelProviders.of(this).get(PokemonViewModel.class);

        pokemonViewModel.getAll().observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {

                adapter.setPokemonList(pokemons);
                pokemonList = pokemons;
            }
        });

        Button delete = findViewById(R.id.delete);
        delete.setOnClickListener(view -> deleteEntry());

        exitButton();
    }

    public void exitButton() {
        Button btnExit = findViewById(R.id.exit);
        btnExit.setOnClickListener(view -> {
            Intent intent = new Intent(AnswerActivity.this, new MainActivity().getClass());
            startActivity(intent);
            finish();
        });
    }

    void deleteEntry() {
        if(pokemonList.size() > 0) {
            pokemonViewModel = ViewModelProviders.of(this).get(PokemonViewModel.class);
            pokemonViewModel.deletePokemon(pokemonList.get(pokemonList.size() - 1));
        }
        else {
            Toast.makeText(this, "Ingen pokemons å slette",
                    Toast.LENGTH_LONG).show();
        }
    }
}