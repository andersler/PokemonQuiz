package com.example.pokemonquiz;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.pokemonquiz.Controller.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityRule = new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void testLaunchQuiz() {
        onView(withId(R.id.quizBtn)).perform(click());
    }

    @Test
    public void testLaunchAddEntry() {
        onView(withId(R.id.addEntryBtn)).perform(click());
    }

    @Test
    public void testLaunchAnswers() {
        onView(withId(R.id.answersBtn)).perform(click());
    }
}
