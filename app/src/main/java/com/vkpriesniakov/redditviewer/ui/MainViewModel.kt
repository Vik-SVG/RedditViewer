package com.vkpriesniakov.redditviewer.ui

import androidx.lifecycle.ViewModel
import com.vkpriesniakov.redditviewer.data.repository.PostsRepository

class MainViewModel (private val postsRepository:PostsRepository): ViewModel(){

    val allPosts = postsRepository //TODO: install repository


}