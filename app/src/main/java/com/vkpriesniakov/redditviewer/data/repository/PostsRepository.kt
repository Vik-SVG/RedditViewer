package com.vkpriesniakov.redditviewer.data.repository

import androidx.lifecycle.LiveData
import com.vkpriesniakov.redditviewer.data.entities.RedditPostsResponse
import com.vkpriesniakov.redditviewer.data.remote.RedditRemoteDataSource
import com.vkpriesniakov.redditviewer.utils.Resource
import com.vkpriesniakov.redditviewer.utils.performGetOperation

class PostsRepository(private val remoteDataSource: RedditRemoteDataSource) {
    /**
     * Converting retrofit response to livedata
     */
    fun getTopPostsLiveData(after:String, limit:String): LiveData<Resource<RedditPostsResponse>> =
        performGetOperation(networkCall = { remoteDataSource.getTopPostsResource(after, limit) })
}