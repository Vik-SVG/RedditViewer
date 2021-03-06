package com.vkpriesniakov.redditviewer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vkpriesniakov.redditviewer.adapters.PagingTopPostsAdapter
import com.vkpriesniakov.redditviewer.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {


    private lateinit var bdn: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bdn = ActivityMainBinding.inflate(layoutInflater)
        val view = bdn.root
        setContentView(view)
    }

}