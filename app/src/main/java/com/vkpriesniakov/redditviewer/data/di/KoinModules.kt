package com.vkpriesniakov.redditviewer.data.di

import com.vkpriesniakov.redditviewer.data.remote.RedditRemoteDataSource
import com.vkpriesniakov.redditviewer.data.remote.RedditService
import com.vkpriesniakov.redditviewer.data.repository.PostsRepository
import com.vkpriesniakov.redditviewer.image_worker.ImageWriter
import com.vkpriesniakov.redditviewer.ui.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val mainModule = module {

    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.reddit.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    fun provideRedditService(retrofit: Retrofit): RedditService {
        return retrofit.create(RedditService::class.java)
    }

    single { provideRetrofit() }
    single { provideRedditService(get()) }
    single { RedditRemoteDataSource(provideRedditService(get())) }
    single { PostsRepository(get()) }
    single { ImageWriter(androidContext()) }

    viewModel { MainViewModel(get()) }
}

