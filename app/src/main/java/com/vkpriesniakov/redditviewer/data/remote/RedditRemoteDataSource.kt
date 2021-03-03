package com.vkpriesniakov.redditviewer.data.remote

class RedditRemoteDataSource(private val redditService: RedditService) : BaseDataSource() {
    /**
     * checking if Response<RedditPostsResponse> has errors
     * ----------------------------------------
     * check occurs in getResults() method
     * and then returning the Resource(STATUS, DATA) object
     */
    suspend fun getTopPostsResource(after:String, limit:String) =
        getResults { redditService.getTopPostsResponse(after, limit) }
}
