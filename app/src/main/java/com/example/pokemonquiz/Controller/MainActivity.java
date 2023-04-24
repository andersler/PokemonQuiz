package com.example.pokemonquiz.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pokemonquiz.DataAccess.Pokemon;
import com.example.pokemonquiz.R;
import com.example.pokemonquiz.ViewModel.PokemonAdapter;
import com.example.pokemonquiz.ViewModel.PokemonViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PokemonViewModel pokemonViewModel;
    public static boolean initialized = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pokemonViewModel = ViewModelProviders.of(this).get(PokemonViewModel.class);

        pokemonViewModel.getAll().observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {
                if (!initialized && pokemons.size() == 0) {
                    //  Initialize question list, can be accessed regardless of what activity started first
                    Bitmap q3 = BitmapFactory.decodeResource(getResources(), R.drawable.bulbasaur);
                    Bitmap q2 = BitmapFactory.decodeResource(getResources(), R.drawable.charmander);
                    Bitmap q1 = BitmapFactory.decodeResource(getResources(), R.drawable.marill);

                    pokemonViewModel.insertPokemon(new Pokemon("Bulbasaur", Bitmap.createScaledBitmap(q3, 1000, 1000, true)));
                    pokemonViewModel.insertPokemon(new Pokemon("Charmander", Bitmap.createScaledBitmap(q2, 1000, 1000, true)));
                    pokemonViewModel.insertPokemon(new Pokemon("Marill", Bitmap.createScaledBitmap(q1, 1000, 1000, true)));
                    initialized = true;
                }

            }
        });

        // Launches the Answers activity
        Button answersBtn = findViewById(R.id.answersBtn);
        answersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AnswerActivity.class);
                startActivity(intent);
            }
        });

        // Launches the AddPokemonActivity activity

        Button addEntryBtn = findViewById(R.id.addEntryBtn);
        addEntryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddPokemonActivity.class);
                startActivity(intent);
            }
        });

        // Launches the Quiz activity

        Button quizBtn = findViewById(R.id.quizBtn);
        quizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,QuizActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intentAnswers = new Intent(MainActivity.this, AnswerActivity.class);
        Intent intentQuiz = new Intent(MainActivity.this, QuizActivity.class);
        Intent intentMain = new Intent(MainActivity.this, MainActivity.class);
        Intent intentAdd = new Intent(MainActivity.this, AddPokemonActivity.class);
        switch (item.getItemId()){
            case R.id.addQuestions:
                startActivity(intentAdd);
                return true;
            case R.id.showAnswers:
                startActivity(intentAnswers);
                return true;
            case R.id.easyQuiz:
                intentQuiz.putExtra("choice",false);
                startActivity(intentQuiz);
                return true;
            case R.id.hardQuiz:
                intentQuiz.putExtra("choice",true);
                startActivity(intentQuiz);

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    }