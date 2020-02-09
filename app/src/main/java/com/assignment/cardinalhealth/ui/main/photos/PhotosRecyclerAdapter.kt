package com.assignment.cardinalhealth.ui.main.photos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.arch.core.executor.TaskExecutor
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.assignment.cardinalhealth.R
import com.assignment.cardinalhealth.model.Feed
import com.assignment.cardinalhealth.util.ImageLoader
import com.assignment.cardinalhealth.util.Utility


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
        holder.bind(getItem(position),context, imageClickCallBack)
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val titleText = view.findViewById<TextView>(R.id.title)
    val image = view.findViewById<ImageView>(R.id.image)
    val tagsText = view.findViewById<TextView>(R.id.tags)
    val publishedText = view.findViewById<TextView>(R.id.published)
    val dateTakenText = view.findViewById<TextView>(R.id.datetaken)

    fun bind(feed: Feed,context: Context, imageClickBack: (Feed) -> Unit) {
        with(feed){
            titleText.text = context.getString(R.string.title,title)
            tagsText.text = context.getString(R.string.tags,tags)
            publishedText.text =context.getString(R.string.published,Utility.getFormattedDate(published))
            dateTakenText.text = context.getString(R.string.date_taken,Utility.getFormattedDate(dateTaken))
            ImageLoader.loadMediumImage(image, media.imageUrl)
            image.setOnClickListener {
                imageClickBack(feed)
            }
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
