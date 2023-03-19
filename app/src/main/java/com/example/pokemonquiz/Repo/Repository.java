package com.example.pokemonquiz.Repo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.pokemonquiz.DataAccess.Pokemon;
import com.example.pokemonquiz.DataAccess.PokemonDao;
import com.example.pokemonquiz.DataAccess.PokemonDatabase;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    private PokemonDao pokemonDao;


    private LiveData<List<Pokemon>> pokemons;



    public Repository(Application application){
        PokemonDatabase db = PokemonDatabase.getDatabase(application);
        pokemonDao = db.pokemonDao();
        pokemons = pokemonDao.getAll();
    }
    public LiveData<List<Pokemon>> getAll() {return pokemons;}


    public void insertPokemon(Pokemon newPokemon){
        new InsertAsyncTask(pokemonDao).execute(newPokemon);
    }

    public void deletePokemon(Pokemon pokemon){
        new DeleteAsyncTask(pokemonDao).execute(pokemon);
    }

    public void deleteAllPokemons() {

        new DeleteAllNoteAsyncTask(pokemonDao).execute();
    }


    private static class InsertAsyncTask extends AsyncTask<Pokemon,Void,Void>{

        private PokemonDao asyncTaskDao;

       private InsertAsyncTask(PokemonDao dao) {this.asyncTaskDao = dao;}

        @Override
        protected Void doInBackground(final Pokemon... params){
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

private  static  class DeleteAsyncTask extends AsyncTask<Pokemon,Void,Void>{

        private PokemonDao asyncTaskDao;

        private DeleteAsyncTask(PokemonDao dao) {this.asyncTaskDao = dao;}


    @Override
    protected Void doInBackground(Pokemon... params) {
        asyncTaskDao.delete(params[0]);
        return null;
    }
}

    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void> {
        private PokemonDao asyncTaskDao;

        private DeleteAllNoteAsyncTask(PokemonDao pokemonDao) {
            this.asyncTaskDao = pokemonDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            asyncTaskDao.deleteAll();
            return null;
        }
    }


    }


