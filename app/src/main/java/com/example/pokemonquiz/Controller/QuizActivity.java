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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonquiz.DataAccess.Pokemon;
import com.example.pokemonquiz.R;
import com.example.pokemonquiz.ViewModel.PokemonAdapter;
import com.example.pokemonquiz.ViewModel.PokemonViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    public boolean choice = false;
    public CountDownTimer timer;
    public int score = 0;
    public int answered = 0;

    private PokemonViewModel pokemonViewModel;
    public List<Pokemon> questions;

    private ImageView imageView;

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btnExit;
    public ProgressBar simpleProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_activity);


        pokemonViewModel = ViewModelProviders.of(this).get(PokemonViewModel.class);
        PokemonAdapter adapter = new PokemonAdapter();

        pokemonViewModel.getAll().observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {

                adapter.setPokemonList(pokemons);

                // Fetch questions
                questions = pokemons;


                //DETTE FUNKER IKKE
                //Bundle values = getIntent().getBundleExtra("choice");
                //choice = values.getBoolean("choice",false);



                //    Pokemon p =  pokemonViewModel.getStandardPokemonList().get(0);

                //Shuffle the questions to preserve randomness
                Collections.shuffle(questions);
                System.out.println("SHUFFLED");



                // Setup imageView and buttons
                imageView = findViewById(R.id.imageView);

                btn1 = findViewById(R.id.btn1);
                btn2 = findViewById(R.id.btn2);
                btn3 = findViewById(R.id.btn3);

                displayNextQuestion();
            }
        });

    }


    @SuppressLint("SetTextI18n")

    private void displayNextQuestion() {
        simpleProgressBar=(ProgressBar) findViewById(R.id.simpleProgressBar); // initiate the progress bar
        TextView scoreBoard = findViewById(R.id.scoreBoard);
        TextView feedback = findViewById(R.id.feedback);
        scoreBoard.setText("Score: " + score + "/" + answered);
        if(choice) {
            simpleProgressBar.setMax(10);

            timer = new CountDownTimer(10000, 1000) {

                public void onTick(long millisUntilFinished) {
                    simpleProgressBar.setProgress((int) (millisUntilFinished / 1000));
                }

                public void onFinish() {
                    answered++;
                    if (answered == questions.size()) {
                        returnToMainMenu();
                    } else {
                        displayNextQuestion();
                    }
                }
            }.start();
        }
        else{
            simpleProgressBar.setMax(questions.size());
        }
        simpleProgressBar.setProgress(score);
        // Set the imageView to be the first element in the question array
        imageView.setImageBitmap(questions.get(answered).getImage());

        // The correct answer
        String correctAnswer = questions.get(answered).getName();
        System.out.println("Question order: 1: " + questions.get(0).getName() + ", 2: " + questions.get(1).getName() + ", 3:" + questions.get(2).getName());
        System.out.println("Current question: (" + (answered+1) + ") " + questions.get(answered).getName());
        System.out.println("Current answer: " + correctAnswer);

        // Get two wrong answers at random
        ArrayList<String> wrongAnswers = new ArrayList<>();

        ArrayList<Pokemon> shuffled = new ArrayList<>(questions);
        Collections.shuffle(shuffled);

        for ( Pokemon p : shuffled) {
            if (!p.getName().equals(correctAnswer)) {
                wrongAnswers.add(p.getName());
                if (wrongAnswers.size() == 2) break;
            }
        }

        // Store buttons in arraylist and shuffle
        ArrayList<Button> buttons = new ArrayList<>( Arrays.asList(btn1, btn2, btn3));
        Collections.shuffle(buttons);

        // Set the correct answer button
        buttons.get(0).setText(correctAnswer);
        buttons.get(0).setOnClickListener(view -> {
            score++;
            answered++;
            if(answered==questions.size()){
                if(choice) {
                    timer.cancel();
                }
                returnToMainMenu();
            }
            else {
                feedback.setText("");
                if(choice) {
                    timer.cancel();
                }
                displayNextQuestion();
            }
        });

        // Set the wrong answer buttons
        for (int i = 1; i <= wrongAnswers.size(); i++) {
            buttons.get(i).setText(wrongAnswers.get(i-1));
            buttons.get(i).setOnClickListener(view -> {
                answered++;
                if(answered==questions.size()){
                    if(choice) {
                        timer.cancel();
                    }
                    returnToMainMenu();
                }
                else {
                    feedback.setText("Right answer was: " + correctAnswer);
                    if(choice) {
                        timer.cancel();
                    }
                    displayNextQuestion();
                }
            });
        }
        exitButton();
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
