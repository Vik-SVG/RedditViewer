package com.vkpriesniakov.redditviewer.data.entities

data class RedditPostsResponse(val allPosts: RedditAllPostsResponse)

data class RedditAllPostsResponse(
    val children: List<RedditSinglePostResponse>,
    val after: String?,
    val before: String?
)

data class RedditSinglePostResponse(

    val author: String,
    val title: String,
    val num_comments: Int,
    val created: Long,
    val thumbnail: String,
    val url: String
)