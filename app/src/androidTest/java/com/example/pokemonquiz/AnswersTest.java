package com.example.pokemonquiz;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.pokemonquiz.Controller.AddPokemonActivity;
import com.example.pokemonquiz.Controller.AnswerActivity;

import org.junit.Rule;
import org.junit.Test;

public class AnswersTest {

    @Rule
    public ActivityScenarioRule<AnswerActivity> answerActivityRule = new ActivityScenarioRule<>(AnswerActivity.class);

    @Test
    public void deleteEntry() {
        onView(withId(R.id.delete)).perform(click());
        onView(withText("Ingen pokemons å slette")).check(matches(isDisplayed()));
    }
}
