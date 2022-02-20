package com.example.sfoide.ext

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessRecyclerViewScrollListener(private val layoutManager: RecyclerView.LayoutManager) : RecyclerView.OnScrollListener() {
    private var visibleThreshold = 5

    private var currentPage = 1

    private var previousTotalItemCount = 0

    private var loading = true

    private val startingPageIndex = 1

    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        var lastVisibleItemPosition = 0
        val totalItemCount = layoutManager.itemCount

        lastVisibleItemPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

        if (totalItemCount < previousTotalItemCount) {
            currentPage = startingPageIndex
            previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                loading = true
            }
        }
        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        if (!loading && (lastVisibleItemPosition + visibleThreshold >= totalItemCount)) {
            currentPage++
            onLoadMore(currentPage, totalItemCount, view)
            loading = true
        }
    }

    fun resetState() {
        currentPage = startingPageIndex
        previousTotalItemCount = 0
        loading = true
    }

    abstract fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?)
}
