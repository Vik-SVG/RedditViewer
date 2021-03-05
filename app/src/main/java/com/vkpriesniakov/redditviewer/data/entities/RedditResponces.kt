package com.vkpriesniakov.redditviewer.data.entities


data class RedditPostsResponse(
    val data: RedditAllPostsResponse)

data class RedditAllPostsResponse(
    val children: List<RedditChildrenResponse>,
    val after: String?,
    val before: String?
)

data class RedditChildrenResponse(val data: RedditSinglePostResponse)

data class RedditSinglePostResponse(

    val author: String,
    val title: String,
    val num_comments: Int,
    val created_utc: Long,
    val thumbnail: String,
    val url: String
)