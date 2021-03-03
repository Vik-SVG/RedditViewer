package com.vkpriesniakov.redditviewer.data.di

import com.vkpriesniakov.redditviewer.data.remote.RedditService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val retrofitModule = module {

    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.reddit.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

//            .create(RedditService::class.java)
    }

    fun provideRedditService(retrofit: Retrofit): RedditService {
        return retrofit.create(RedditService::class.java)
    }

    single {
        provideRetrofit()
        provideRedditService(provideRetrofit())
    }

//    single {}
}