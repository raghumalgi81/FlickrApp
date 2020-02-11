package com.assignment.cardinalhealth

import android.content.ComponentName
import android.os.Build
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.assignment.cardinalhealth.model.FeedResponse
import com.assignment.cardinalhealth.ui.main.photodetail.PhotoDetailActivity
import com.assignment.cardinalhealth.ui.main.photosfeed.PhotoListActivity
import com.assignment.cardinalhealth.ui.main.photosfeed.PhotosRecyclerAdapter
import com.assignment.cardinalhealth.ui.main.photosfeed.PhotosViewModel
import com.assignment.cardinalhealth.util.TestData
import junit.framework.TestCase.assertEquals
import kotlinx.android.synthetic.main.photo_list.*
import kotlinx.android.synthetic.main.photo_row.view.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowActivity


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class PhotoListActivityTest {
    private lateinit var activity: PhotoListActivity
    private lateinit var activityController: ActivityController<PhotoListActivity>
    @Mock
    private lateinit var viewModel: PhotosViewModel
    @Mock
    private lateinit var feedsLiveData: LiveData<FeedResponse>
    @Mock
    private lateinit var isLoadingLiveData: LiveData<Boolean>
    @Mock
    private lateinit var isErrorLiveData: LiveData<String>
    @Captor
    private lateinit var feedsObserverCaptor: ArgumentCaptor<Observer<FeedResponse>>
    @Captor
    private lateinit var isLoadingObserverCaptor: ArgumentCaptor<Observer<Boolean>>
    @Captor
    private lateinit var isErrorObserverCaptor: ArgumentCaptor<Observer<String>>
    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()


    @Before
    fun setUp() {
        `when`(viewModel.feedResponse).thenReturn(feedsLiveData)
        `when`(viewModel.isLoading).thenReturn(isLoadingLiveData)
        `when`(viewModel.errorMessage).thenReturn(isErrorLiveData)
        activityController = Robolectric.buildActivity(PhotoListActivity::class.java)
        activity = activityController.get()
        activityController.create()
        activity.setTestViewModel(viewModel)
        activityController.start()
        activity.subscribeToFeed()
        verify(feedsLiveData).observe(
            ArgumentMatchers.any(LifecycleOwner::class.java),
            feedsObserverCaptor.capture()
        )
        verify(isLoadingLiveData).observe(
            ArgumentMatchers.any(LifecycleOwner::class.java),
            isLoadingObserverCaptor.capture()
        )
        verify(isErrorLiveData).observe(
            ArgumentMatchers.any(LifecycleOwner::class.java),
            isErrorObserverCaptor.capture()
        )
    }

    @Test
    fun `has visible loading view on create`() {
        isLoadingObserverCaptor.value.onChanged(true)
        assertEquals(View.VISIBLE, activity.progress.visibility)
    }

    @Test
    fun `has recycler view and noitems view on create`() {
        assertEquals(View.GONE, activity.photosRecyclerView.visibility)
        assertEquals(View.GONE, activity.noItemsText.visibility)
    }

    @Test
    fun `displays feed list when available`() {
        val feedResponse = FeedResponse(feeds = listOf(TestData.expectedData))
        feedsObserverCaptor.value.onChanged(feedResponse)
        isLoadingObserverCaptor.value.onChanged(false)
        assertEquals(View.VISIBLE, activity.photosRecyclerView.visibility)
        assertEquals(View.GONE, activity.progress.visibility)
        assertEquals(View.GONE, activity.noItemsText.visibility)
        assertEquals(
            feedResponse.feeds,
            (activity.photosRecyclerView.adapter as? PhotosRecyclerAdapter)?.feedList
        )
    }

    @Test
    fun `displays error view when error obtaining feeds`() {
        isLoadingObserverCaptor.value.onChanged(false)
        isErrorObserverCaptor.value.onChanged("Error Message from server")
        assertEquals(View.GONE, activity.photosRecyclerView.visibility)
        assertEquals(View.GONE, activity.progress.visibility)
    }

    @Test
    fun shouldStartNextActivityWhenButtonIsClicked() {
        val feedResponse = FeedResponse(feeds = listOf(TestData.expectedData))
        feedsObserverCaptor.value.onChanged(feedResponse)
        activity.photosRecyclerView.apply {
            measure(0, 0)
            layout(0, 0, 100, 1000)
            findViewHolderForAdapterPosition(0)?.itemView?.image?.performClick()
        }
        val shadowActivity: ShadowActivity = Shadows.shadowOf(PhotoDetailActivity())
        val intent = shadowActivity.nextStartedActivity
        assertEquals(intent.component, ComponentName(activity, PhotoDetailActivity::class.java))
    }
}