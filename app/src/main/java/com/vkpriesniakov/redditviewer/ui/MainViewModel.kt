package com.vkpriesniakov.redditviewer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vkpriesniakov.redditviewer.data.entities.RedditChildrenResponse
import com.vkpriesniakov.redditviewer.data.repository.PostsRepository
import com.vkpriesniakov.redditviewer.data.repository.TopPostsPagingSource
import kotlinx.coroutines.flow.Flow

class MainViewModel(private val postsRepository: PostsRepository) : ViewModel() {


    val allTopPostsPaging: Flow<PagingData<RedditChildrenResponse>> =
        Pager(
            PagingConfig(
                10, 10, false, 7
            )
        ) {
            TopPostsPagingSource(
                postsRepository
            )
        }.flow
            .cachedIn(viewModelScope)


    //Alternative loading with live data
    private var limit: String = "20"
    private var after: String = ""

    val allPostsStatus = postsRepository.getTopPostsLiveData(after, limit)

    fun setLimit(limit: String) {
        this.limit = limit
    }

    fun setAfter(after: String?) {
        this.after = after.toString()
    }

// origin get implementation with live data
// val allTopPosts : Flow<Resource<RedditPostsResponse>> = postsRepository.getTopPostsLiveData(after, limit).asFlow()
}
