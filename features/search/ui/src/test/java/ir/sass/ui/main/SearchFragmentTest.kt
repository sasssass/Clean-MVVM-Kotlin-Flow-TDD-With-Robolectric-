package ir.sass.ui.main

import android.os.Build
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import ir.sass.search.ui.R
import ir.sass.ui.base.TestUtils.withRecyclerView
import ir.sass.ui.base.launchFragmentInHiltContainer
import ir.search.ui.fragment.main.SearchFragment
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.O_MR1],
    application = HiltTestApplication::class,
    instrumentedPackages = [
        // required to access final members on androidx.loader.content.ModernAsyncTask
        "androidx.loader.content"
    ])
class SearchFragmentTest {

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Test
    fun the_search_button_should_be_active_only_when_some_input_exists_on_search_EditText() {
        launchFragmentInHiltContainer<SearchFragment>{
            (this as SearchFragment).apply {
                onView(withId(R.id.btn))
                    .check(matches(not(isEnabled())))

                onView(withId(R.id.edit))
                    .perform(replaceText("Rammstein"))

                onView(withId(R.id.btn))
                    .check(matches(isEnabled()))

                onView(withId(R.id.edit))
                    .perform(replaceText(""))

                onView(withId(R.id.btn))
                    .check(matches(not(isEnabled())))
            }
        }
    }

    @Test
    fun after_we_click_on_search_button_some_data_should_be_loaded_in_our_recycler_view(){
        launchFragmentInHiltContainer<SearchFragment>(){
            (this as SearchFragment).apply {
                onView(withId(R.id.edit))
                    .perform(replaceText("Rammstein"))

                onView(withId(R.id.btn))
                    .perform(click())

                onView(withRecyclerView(R.id.recyclerview)
                    .atPositionOnView(0, R.id.txt_artist))
                    .check(matches(withText("fake name")))
            }
        }
    }
}