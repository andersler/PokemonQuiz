package com.example.pokemonquiz.Controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Pair;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonquiz.DataAccess.Pokemon;
import com.example.pokemonquiz.R;
import com.example.pokemonquiz.ViewModel.PokemonAdapter;
import com.example.pokemonquiz.ViewModel.PokemonViewModel;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    public boolean hardMode = false;
    public CountDownTimer timer;
    public int score = 0;
    int answered = 0;
    private List<Pokemon> pokemonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_activity);

        // Fetch the questions from the database
        PokemonViewModel pokemonViewModel = ViewModelProviders.of(this).get(PokemonViewModel.class);
        PokemonAdapter adapter = new PokemonAdapter();

        pokemonViewModel.getAll().observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {

                if (pokemons != null) {
                    adapter.setPokemonList(pokemons);
                    pokemonList = pokemons;

                    System.out.println("Fetched " + pokemonList.size() + " questions");
                    // Start the question loop
                    displayNextQuestion();
                } else {
                    System.out.println("ERROR, NO POKEMON FOUND");
                    returnToMainMenu();
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void displayNextQuestion() {
        // Scoreboard and progress bar updating
        TextView scoreBoard = findViewById(R.id.scoreBoard);
        scoreBoard.setText("Score: " + score + "/" + answered);
        handleProgressBar();

        // Create a list of questions that's to be displayed and add the next pokemon.
        List<Pokemon> questions = new ArrayList<>();
        questions.add(pokemonList.get(answered));

        // Add two random pokemons from the pokemon list to the question list.
        addRandomQuestions(2, questions);

        // Set the image to the first index of the questions array
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(questions.get(0).getImage());

        // Setup the correct and wrong answer buttons.
        setupButtons(questions);

        exitButton();
    }

    private void handleProgressBar() {

        ProgressBar simpleProgressBar = findViewById(R.id.simpleProgressBar);

        if(hardMode) {
            simpleProgressBar.setMax(10);
            timer = new CountDownTimer(10000, 1000) {
                public void onTick(long millisUntilFinished) {
                    simpleProgressBar.setProgress((int) (millisUntilFinished / 1000));
                }
                public void onFinish() {
                    returnToMainMenu();
                }
            }.start();
        } else { // Easy mode: Progress displays % of correct answers
            simpleProgressBar.setMax(pokemonList.size());
            simpleProgressBar.setProgress(score);
        }
    }

    /** Adds random questions from the pokemonList to the questions list that is not already present
     * @param n Amounts of questions to be added
     * @param questions The pokemonList the questions should be added to
     */
    private void addRandomQuestions(int n, List<Pokemon> questions) {

        Random random = new Random();

        for (int i = 0; i < n;) {

            boolean valid = true;
            int index = random.nextInt(pokemonList.size());

            for (Pokemon p : questions) {
                if (pokemonList.get(index).getName().equals(p.getName())) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                questions.add(pokemonList.get(index));
                i++;
            }
        }
    }

    /** Sets up three buttons and randomized their view order such that one of the button will
     * be the correct answer and two will be the wrong answer.
     * @param questions The list of which the buttons should be set up for
     */
    @SuppressLint("SetTextI18n")
    private void setupButtons(List<Pokemon> questions) {
        // Setup
        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);
        TextView feedback = findViewById(R.id.feedback);

        // Store buttons in a shuffled array for randomness
        ArrayList<Button> buttons = new ArrayList<>( Arrays.asList(btn1, btn2, btn3));
        Collections.shuffle(buttons);

        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setText(questions.get(i).getName());
            int correctIndex = i;

            buttons.get(i).setOnClickListener(view -> {
                answered++;
                if(hardMode) {
                    timer.cancel();
                }
                if(answered == pokemonList.size()) { // If no more questions, return to main menu.
                    returnToMainMenu();
                }
                else {                      // If more questions, display the next one.
                    if (correctIndex == 0) { // Correct answer - if first button was pressed
                        score++;
                        feedback.setText("Correct!");
                    } else {                    // Wrong answer - if one of the wrong buttons.
                        feedback.setText("Right answer was: " + questions.get(0).getName());
                    }
                    displayNextQuestion();
                }
            });
        }
    }

    public void returnToMainMenu(){
        Intent intent = new Intent(QuizActivity.this, new MainActivity().getClass());
        startActivity(intent);
        finish();
    }
    public void exitButton(){
        Button btnExit = findViewById(R.id.exit);
        btnExit.setOnClickListener(view -> {
            Intent intent = new Intent(QuizActivity.this, new MainActivity().getClass());
            startActivity(intent);
            finish();
        });
    }




}
