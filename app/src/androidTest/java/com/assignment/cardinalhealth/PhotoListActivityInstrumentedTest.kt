package com.assignment.cardinalhealth

import androidx.lifecycle.MutableLiveData
import com.assignment.cardinalhealth.model.Feed
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.assignment.cardinalhealth.model.Media
import com.assignment.cardinalhealth.ui.main.photos.PhotoListActivity

@RunWith(AndroidJUnit4ClassRunner::class)
class PhotoListActivityInstrumentedTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(PhotoListActivity::class.java, true, true)
    private val feedListLiveData = MutableLiveData<List<Feed>>()


    @Test
    fun givenPhotosListFragment_whenContentVisibilityVisible_thenContentIsShown() {
        /* Given */
        feedListLiveData.postValue(fakeFeedList)
        /* Then */
        onView(withId(R.id.photosRecyclerView)).check(matches((isDisplayed())))
        onView(withId(R.id.progress)).check(matches((isDisplayed())))
    }


    private val fakeFeedList = listOf(
        Feed(
            title = "Tabby",
            dateTaken = "2020-02-07T17:49:49-08:00",
            link = "https://www.flickr.com/photos/tabbynera/49501530363/",
            tags = "cats",
            media = Media(imageUrl = "https://live.staticflickr.com/65535/49501530363_1c2b83e35f_m.jpg")
        )
    )


}