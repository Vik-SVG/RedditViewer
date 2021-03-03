package com.vkpriesniakov.redditviewer.data.remote

import com.vkpriesniakov.redditviewer.data.entities.RedditPostsResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditService {

    @GET("top.json")
    suspend fun getTopPostsResponse(
        @Query("after") after: String,
        @Query("limit") limit: String
    ): Response<RedditPostsResponse>

}