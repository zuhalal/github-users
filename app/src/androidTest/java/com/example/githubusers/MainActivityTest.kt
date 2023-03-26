package com.example.githubusers

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
internal class MainActivityTest {
    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun isViewVisible() {
        onView(withId(R.id.searchInput)).check(matches(isDisplayed()))
        onView(withId(R.id.btnSend)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_user)).check(matches(isDisplayed()))
    }

    @Test
    fun isNavigateToFavorite() {
        onView(withId(R.id.favorite)).perform(click())
        onView(withId(R.id.rv_user_favorite)).check(matches(isDisplayed()))
    }

    @Test
    fun isNavigateToSetting() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)
        onView(withText(R.string.setting)).check(matches(isDisplayed()))
        onView(withText(R.string.setting)).perform(click())
        onView(withId(R.id.switch_theme)).check(matches(isDisplayed()));
    }
}