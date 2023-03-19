package com.example.pokemonquiz.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.pokemonquiz.DataAccess.Pokemon;
import com.example.pokemonquiz.Repo.Repository;

import java.util.ArrayList;
import java.util.List;

public class PokemonViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<Pokemon>> getAll;


    public PokemonViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        getAll = repository.getAll();
    }

   public LiveData<List<Pokemon>> getAll() {return getAll;}


    public void insertPokemon(Pokemon pokemon) { repository.insertPokemon(pokemon);}

    public void deletePokemon(Pokemon pokemon) {repository.deletePokemon(pokemon);}

    public void deleteAll(){repository.deleteAllPokemons();}
}
