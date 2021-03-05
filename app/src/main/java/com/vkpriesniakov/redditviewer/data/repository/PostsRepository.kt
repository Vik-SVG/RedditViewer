package com.vkpriesniakov.redditviewer.data.repository

import androidx.lifecycle.LiveData
import com.vkpriesniakov.redditviewer.data.entities.RedditPostsResponse
import com.vkpriesniakov.redditviewer.data.remote.RedditRemoteDataSource
import com.vkpriesniakov.redditviewer.utils.Resource
import com.vkpriesniakov.redditviewer.utils.performGetLiveDataOperation
import com.vkpriesniakov.redditviewer.utils.performGetOperation

class PostsRepository(private val remoteDataSource: RedditRemoteDataSource) {

    suspend fun getTopPostsResponseWithStatus(
        after: String,
        limit: String
    ): Resource<RedditPostsResponse> =
        performGetOperation(networkCall = { remoteDataSource.getTopPostsResource(after, limit) })



    /**
     * Additional converting retrofit response to livedata
     */
    fun getTopPostsLiveData(after: String, limit: String): LiveData<Resource<RedditPostsResponse>> =
        performGetLiveDataOperation(networkCall = {
            remoteDataSource.getTopPostsResource(
                after,
                limit
            )
        })
}


