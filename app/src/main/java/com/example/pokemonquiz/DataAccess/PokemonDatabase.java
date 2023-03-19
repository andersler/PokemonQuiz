package com.example.pokemonquiz.DataAccess;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.pokemonquiz.R;

@Database(entities = {Pokemon.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class PokemonDatabase extends RoomDatabase {

    public abstract PokemonDao pokemonDao();

    private static PokemonDatabase INSTANCE;

    public static synchronized PokemonDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    PokemonDatabase.class, "pokemon_database")
                                    .fallbackToDestructiveMigration()
                                    .build();


        }
        return INSTANCE;
    }

}