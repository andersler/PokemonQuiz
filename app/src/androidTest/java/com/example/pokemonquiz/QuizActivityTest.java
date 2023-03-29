package com.example.pokemonquiz;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSubstring;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.pokemonquiz.Controller.MainActivity;
import com.example.pokemonquiz.Controller.QuizActivity;
import com.example.pokemonquiz.DataAccess.Pokemon;
import com.example.pokemonquiz.DataAccess.PokemonDao;
import com.example.pokemonquiz.DataAccess.PokemonDatabase;
import com.example.pokemonquiz.ViewModel.PokemonAdapter;
import com.example.pokemonquiz.ViewModel.PokemonViewModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RunWith(AndroidJUnit4.class)
public class QuizActivityTest {
        private Context context;
@Rule
public ActivityScenarioRule<QuizActivity> QuizActivityRule = new ActivityScenarioRule<>(QuizActivity.class);

        @Test
        public void testShownPicture(){
            onView(withId(R.id.imageView)).check(matches(isDisplayed()));
        }

        @Test
        public void testButtonOne(){
            onView(withId(R.id.btn1)).perform(click());
        }
        @Test
        public void testButtonTwo(){
            onView(withId(R.id.btn2)).perform(click());
        }
        @Test
        public void testButtonThree(){
            onView(withId(R.id.btn3)).perform(click());
        }

        @Test
        public void rightAnswerPressed(){

            final int[] score = new int[1];
            QuizActivityRule.getScenario().onActivity(a -> { score[0] = a.score;});
            onView(withId(R.id.btn1)).perform(click());
            onView(withId(R.id.scoreBoard)).check((matches(withText("Score: "))));

        }
        @Test
        public void wrongAnswerPressed(){

            onView(withId(R.id.btn2)).perform(click());
            onView(withId(R.id.scoreBoard)).check((matches(withText("Score: 0/1"))));

        }



}
