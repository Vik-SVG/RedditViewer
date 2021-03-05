package com.vkpriesniakov.redditviewer.data.remote

/**
 * Here we checking if Response<RedditPostsResponse> has any errors
 * ----------------------------------------
 * check occurs in getResults() method
 * and then returning the Resource(STATUS, DATA) object
 */

class RedditRemoteDataSource(private val redditService: RedditService) : BaseDataSource() {
    suspend fun getTopPostsResource(after: String, limit: String) =
        getResults { redditService.getTopPostsResponse(after, limit) }
}
