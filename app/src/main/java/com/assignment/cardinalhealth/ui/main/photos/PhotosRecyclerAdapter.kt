package com.assignment.cardinalhealth.ui.main.photos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.assignment.cardinalhealth.R
import com.assignment.cardinalhealth.model.Feed
import com.assignment.cardinalhealth.util.ImageLoader
import com.assignment.cardinalhealth.util.Utility
import kotlinx.android.synthetic.main.photo_row.view.*


class PhotosRecyclerAdapter(
    private val context: Context,
    private val imageClickCallBack: (Feed) -> Unit
) :
    ListAdapter<Feed, ViewHolder>(
        PhotosDiff
    ) {


    fun submitData(feeds: List<Feed>) {
        submitList(feeds)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(
                context
            ).inflate(R.layout.photo_row, parent, false)
        )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position), context, imageClickCallBack)
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(feed: Feed, context: Context, imageClickBack: (Feed) -> Unit) =
        with(itemView) {
            title.text = context.getString(R.string.title, feed.title)
            tags.text = context.getString(R.string.tags, feed.tags)
            published.text =
                context.getString(R.string.published, Utility.getFormattedDate(feed.published))
            datetaken.text =
                context.getString(R.string.date_taken, Utility.getFormattedDate(feed.dateTaken))
            ImageLoader.loadMediumImage(image, feed.media.imageUrl)
            image.setOnClickListener {
                imageClickBack(feed)
            }


        }
}

object PhotosDiff : DiffUtil.ItemCallback<Feed>() {
    override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean {
        return oldItem.title == newItem.title &&
                oldItem.dateTaken == newItem.dateTaken &&
                oldItem.published == newItem.published &&
                oldItem.link == newItem.link
    }

    override fun areContentsTheSame(oldItem: Feed, newItem: Feed) = oldItem == newItem
}
