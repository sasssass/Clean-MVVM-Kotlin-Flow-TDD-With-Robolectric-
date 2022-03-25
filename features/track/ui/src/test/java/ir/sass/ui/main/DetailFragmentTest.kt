package ir.sass.ui.main

import android.os.Build
import androidx.core.os.bundleOf
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import ir.sass.track.ui.R
import ir.sass.ui.base.launchFragmentInHiltContainer
import ir.track.ui.fragment.main.DetailFragment
import org.hamcrest.Matchers
import org.hamcrest.Matchers.containsString
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
class DetailFragmentTest {
    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)
    
    @Test
    fun check_if_all_text_has_values(){
        launchFragmentInHiltContainer<DetailFragment>(bundleOf().apply {
            putInt("id",1)
        }){
            (this as DetailFragment).apply {
                onView(withId(R.id.txt_title))
                    .check(matches(withText(containsString("fake title"))))

                onView(withId(R.id.txt_artist))
                    .check(matches(withText(containsString("fake name"))))

                onView(withId(R.id.txt_album))
                    .check(matches(withText(containsString("fake album"))))
            }
        }
    }
}