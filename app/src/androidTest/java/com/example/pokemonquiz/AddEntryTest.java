package com.example.pokemonquiz;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Looper;
import android.widget.ImageView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.pokemonquiz.Controller.AddPokemonActivity;
import com.example.pokemonquiz.Controller.MainActivity;
import com.example.pokemonquiz.Controller.QuizActivity;
import com.example.pokemonquiz.DataAccess.PokemonDao;
import com.example.pokemonquiz.Repo.Repository;
import com.example.pokemonquiz.ViewModel.PokemonViewModel;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kotlinx.coroutines.GlobalScope;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddEntryTest {
    @Rule
    public ActivityScenarioRule<AddPokemonActivity> addActivityRule = new ActivityScenarioRule<>(AddPokemonActivity.class);

    @Test
    public void testAddEntry() {
        AddPokemonActivity addPokemonActivity = new AddPokemonActivity();
        PokemonViewModel pokemonViewModel = addPokemonActivity.pokemonViewModel;
        int questions = pokemonViewModel.getAll().getValue().size();


        onView(withId(R.id.answerEntry)).perform(typeText("Pikachu"));
        closeSoftKeyboard();
        onView(withId(R.id.submit)).perform(click());
      //  Assert.assertTrue(questions != pokemonViewModel.getAll().getValue().size());
    }
}
