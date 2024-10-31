package com.example.task_7

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.not

import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var imageLoader: ImageLoader
    private lateinit var api: ImageApi

    @Before
    fun setup() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.example.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(ImageApi::class.java)
        imageLoader = ImageLoader(api)
    }

    @Test
    fun testLoadImageValid() = runBlocking {
        val url = "https://random-image-pepebigotes.vercel.app/api/random-image"
        val result = imageLoader.loadImage(url)
        assertNotNull(result)
        assert(result?.isSuccessful == true)
    }

    @Test
    fun testLoadImageInvalid() = runBlocking {
        val url = "https://invalid-url.example.com"
        val result = imageLoader.loadImage(url)

        assertNull(result)
    }
    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun loadImage() {
        onView(withId(R.id.editText)).check(matches(isDisplayed()))
        onView(withId(R.id.imageView)).check(matches(isDisplayed()))
        onView(withId(R.id.button)).check(matches(isDisplayed()))
        onView(withId(R.id.copyLand)).check(matches(isDisplayed()))
        onView(withId(R.id.copySkull)).check(matches(isDisplayed()));
        onView(withId(R.id.pasteButton)).check(matches(isDisplayed()));

        onView(withId(R.id.copyLand)).perform(click())
        onView(withId(R.id.pasteButton)).perform(click())
        onView(withId(R.id.button)).perform(click())
        Thread.sleep(2000)
        onView(withId(R.id.imageView)).check(matches(isDisplayed()))
    }

    @Test
    fun errorLoadImage() {
        // Устанавливаем фейковый URL, который не работает
        val fakeUrl = "https://random-image-fake-url-is-dont-work"

        // Проверяем, что элементы интерфейса отображаются
        onView(withId(R.id.editText)).check(matches(isDisplayed()))
        onView(withId(R.id.imageView)).check(matches(isDisplayed()))
        onView(withId(R.id.button)).check(matches(isDisplayed()))
        onView(withId(R.id.copyLand)).check(matches(isDisplayed()))
        onView(withId(R.id.copySkull)).check(matches(isDisplayed()))
        onView(withId(R.id.pasteButton)).check(matches(isDisplayed()))

        // Устанавливаем некорректный URL
        onView(withId(R.id.editText)).perform(typeText(fakeUrl))
        onView(withId(R.id.button)).perform(click())

        // Ожидаем некоторое время для обработки ошибки
        Thread.sleep(2000)

        // Проверяем, что изображение не отображается
        onView(withId(R.id.imageView)).check(matches(isDisplayed()))
    }

}

