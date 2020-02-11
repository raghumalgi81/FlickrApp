package com.assignment.cardinalhealth.ui.main.photosfeed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.assignment.cardinalhealth.EMPTY_STRING
import com.assignment.cardinalhealth.di.OBSERVER_ON
import com.assignment.cardinalhealth.di.SUBCRIBER_ON
import com.assignment.cardinalhealth.model.FeedResponse
import com.assignment.cardinalhealth.repository.FlickrRepository
import com.assignment.cardinalhealth.ui.main.BaseViewModel
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named


class PhotosViewModel @Inject constructor(
    private val flickrRepository: FlickrRepository,
    @param:Named(SUBCRIBER_ON) private val subscriberOn: Scheduler,
    @param:Named(OBSERVER_ON) private val observerOn: Scheduler
) : BaseViewModel() {

    // FOR DATA
     private val _feedResponse = MutableLiveData<FeedResponse>()
     private val _errorMessage = MutableLiveData<String>()
     private val _isLoading = MutableLiveData<Boolean>()

    //Expose Non Mutable LiveData
    val feedResponse: LiveData<FeedResponse>
        get() = _feedResponse
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    val errorMessage: LiveData<String>
        get() = _errorMessage

    // INIT
    init {
        _isLoading.value = false
    }

    /**
     * Fetch public feed using flickr API
     */
    fun getFlickrFeed(tag: String = EMPTY_STRING): Boolean {
        this.disposable.addAll(this.flickrRepository.getFeed(tag)
            .subscribeOn(subscriberOn)
            .observeOn(observerOn)
            .doOnSubscribe { _isLoading.value = true }
            .doOnComplete { _isLoading.value = false }
            .doOnError { _isLoading.value = false }
            .subscribe({ _feedResponse.value = it },
                { _errorMessage.value = it.message })
        )
        return false
    }
}