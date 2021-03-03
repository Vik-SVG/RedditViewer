package com.vkpriesniakov.redditviewer.ui

import androidx.lifecycle.ViewModel
import com.vkpriesniakov.redditviewer.data.repository.PostsRepository

class MainViewModel(private val postsRepository: PostsRepository) : ViewModel() {

    private var limit: String = ""
    private var after: String = ""

    val allTopPosts = postsRepository.getTopPostsLiveData(after, limit)

    fun setLimit(limit: String) {
        this.limit = limit
    }

    fun setAfter(after: String?) {
        this.after = after.toString()
    }

}
