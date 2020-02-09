package com.assignment.cardinalhealth.ui.main.detail

import android.content.Context
import android.content.Intent
import com.assignment.cardinalhealth.R
import com.assignment.cardinalhealth.base.BaseActivity
import com.assignment.cardinalhealth.model.Feed
import com.assignment.cardinalhealth.util.ImageLoader
import com.assignment.cardinalhealth.util.Utility
import kotlinx.android.synthetic.main.photo_detail.*

class PhotoDetailActivity : BaseActivity() {

    override fun getLayoutById(): Int = R.layout.photo_detail

    override fun configureDesign() {
        val feed = intent.extras?.get(FEED) as Feed
        with(feed) {
            authorText.text = author
            authorIdText.text = authorId
            publishedText.text = Utility.getFormattedDate(published)
            datetaken.text = Utility.getFormattedDate(dateTaken)
            tagsText.text = if(tags.isEmpty())getString(R.string.no_tag_present)else tags
            toolbar.title = title
            ImageLoader.loadLargeImage(photoView, media.imageUrl)
        }
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    companion object {
        private const val FEED = "feed"

        fun newIntent(
            context: Context,
            feed: Feed
        ): Intent =
            Intent(context, PhotoDetailActivity::class.java).apply {
                putExtra(FEED, feed)
            }
    }
}
