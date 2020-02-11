package com.assignment.cardinalhealth

import com.assignment.cardinalhealth.base.BaseTest
import org.junit.Rule
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.assignment.cardinalhealth.ui.main.photosfeed.PhotosViewModel
import com.assignment.cardinalhealth.util.TestData
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.net.HttpURLConnection
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class PhotosViewModelTest : BaseTest() {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule() // Force tests to be executed synchronously

    // FOR DATA
    private lateinit var activity: FragmentActivity
    private lateinit var viewModel: PhotosViewModel


    // OVERRIDING
    override fun isMockServerEnabled(): Boolean = true

    @Before
    override fun setUp() {
        super.setUp()
        activity = Robolectric.buildActivity(FragmentActivity::class.java).get()
        viewModel = ViewModelProvider(activity, viewModelFactory)[PhotosViewModel::class.java]
    }

    @Test
    fun getFeed_whenSuccess() {
        // Prepare data
        this.mockHttpResponse("flick_api_success.json", HttpURLConnection.HTTP_OK)
        // Pre-test
        assertEquals(
            null,
            this.viewModel.feedResponse.value,
            "Feed response should be null because stream not started yet"
        )
        // Execute View Model
        this.viewModel.getFlickrFeed()
        // Checks
        val feedResponse = this.viewModel.feedResponse.value
        val feed = feedResponse?.feeds?.get(0)
        assertEquals(TestData.expectedData, feed, "Feed must be fetched")
        assertEquals(
            false,
            this.viewModel.isLoading.value,
            "Should be reset to 'false' because stream ended"
        )
        assertEquals(null, this.viewModel.errorMessage.value, "No error must be founded")
    }


    @Test
    fun getFeed_whenError() {
        // Prepare data
        this.mockHttpResponse("flick_api_success.json", HttpURLConnection.HTTP_BAD_GATEWAY)
        // Pre-test
        assertEquals(
            null,
            this.viewModel.feedResponse.value,
            "Feed should be null because stream not started yet"
        )
        // Execute View Model
        this.viewModel.getFlickrFeed()
        // Checks
        assertEquals(
            null,
            this.viewModel.feedResponse.value,
            "Feed must be null because of http error"
        )
        assertEquals(
            false,
            this.viewModel.isLoading.value,
            "Should be reset to 'false' because stream ended"
        )
        assertNotEquals(null, this.viewModel.errorMessage.value, "Error value must not be empty")
    }
}