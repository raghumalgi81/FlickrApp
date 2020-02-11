package com.assignment.cardinalhealth.ui.main.photos

import android.view.Menu
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assignment.cardinalhealth.R
import com.assignment.cardinalhealth.base.BaseActivity
import com.assignment.cardinalhealth.model.Feed
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.photo_list.*
import com.assignment.cardinalhealth.ui.main.detail.PhotoDetailActivity
import com.assignment.cardinalhealth.util.BottomSheetDialog
import com.assignment.cardinalhealth.util.SortByDate
import com.assignment.cardinalhealth.util.hide
import com.assignment.cardinalhealth.util.show


class PhotoListActivity : BaseActivity(), BottomSheetDialog.BottomSheetListener {
    // FOR DATA
    private lateinit var viewModel: PhotosViewModel
    private lateinit var recyclerAdapter: PhotosRecyclerAdapter
    private var feeds: List<Feed> = emptyList()

    override fun getLayoutById(): Int =
        R.layout.photo_list

    override fun configureDesign() {
        setSupportActionBar(toolbar)
        //Set up recycler view
        recyclerAdapter =
            PhotosRecyclerAdapter(
                this
            ) {
                startActivity(PhotoDetailActivity.newIntent(this, it.feed))
            }
        photosRecyclerView.apply {
            layoutManager = GridLayoutManager(this@PhotoListActivity, 2)
            isNestedScrollingEnabled = false
            isFocusable = false
            adapter = recyclerAdapter
        }
        addDividers(photosRecyclerView)
        configureViewModel()
        observeData()
    }

    private fun initSearch(menu: Menu?) {
        searchView.setMenuItem(menu?.findItem(R.id.search))
        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = query?.let {
                selected_tag.text = it
                viewModel.getFlickrFeed(it)
            }!!

            override fun onQueryTextChange(newText: String?): Boolean = false
        })

        sort_option.setOnClickListener {
            if (feeds.isNotEmpty()) BottomSheetDialog().show(supportFragmentManager, null)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_photos, menu)
        initSearch(menu)
        return true
    }

    private fun configureViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[PhotosViewModel::class.java]
        viewModel.getFlickrFeed()
    }

    private fun observeData() {
        viewModel.feedResponse.observe(this, Observer {
            feeds = it.feeds
            loadList(feeds)
        })

        viewModel.isLoading.observe(this, Observer { showLoader ->
            if (showLoader) {
                noItemsText.hide()
                progress.show()
            } else {
                progress.hide()
            }
        })
        viewModel.errorMessage.observe(this, Observer {
            showNetworkDialog()
        })
    }


    override fun onOptionClick(selectedDate: SortByDate) {
        when (selectedDate) {
            SortByDate.DATE_TAKEN -> loadList(feeds.sortedBy { it.dateTaken })
            SortByDate.PUBLISHED -> loadList(feeds.sortedBy { it.published })
        }
    }

    private fun loadList(feeds: List<Feed>) {
        if (feeds.isNotEmpty()) {
            noItemsText.hide()
            recyclerAdapter.submitData(feeds)
        } else {
            noItemsText.show()
        }
    }

    private fun addDividers(photosView: RecyclerView) {
        val verticalDividerItemDecoration = DividerItemDecoration(
            this, DividerItemDecoration.VERTICAL
        ).apply {
            setDrawable(
                ContextCompat.getDrawable(
                    this@PhotoListActivity,
                    R.drawable.vertical_divider
                )!!
            )
        }
        photosView.addItemDecoration(verticalDividerItemDecoration)

        val horizontalDividerItemDecoration = DividerItemDecoration(
            this, DividerItemDecoration.HORIZONTAL
        ).apply {
            setDrawable(
                ContextCompat.getDrawable(
                    this@PhotoListActivity,
                    R.drawable.horizontal_divider
                )!!
            )
        }
        photosView.addItemDecoration(horizontalDividerItemDecoration)
    }

}
