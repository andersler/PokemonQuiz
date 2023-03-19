package com.example.pokemonquiz.DataAccess;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pokemon_table")
public class Pokemon {

    @PrimaryKey(autoGenerate = true)
      int id;

    private String name;

    private Bitmap image;

    public Pokemon(String name, Bitmap image){
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getId(){
        return this.id;
    }
}
