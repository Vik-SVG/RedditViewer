package com.vkpriesniakov.redditviewer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import com.vkpriesniakov.redditviewer.databinding.ActivityMainBinding
import com.vkpriesniakov.redditviewer.utils.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivityTag"

    private val mViewModel: MainViewModel by viewModel()
    private lateinit var bdn: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bdn = ActivityMainBinding.inflate(layoutInflater)
        val view = bdn.root
        setContentView(view)
        Log.i(TAG, "Starting")

        mViewModel.allTopPosts.observe(this, {
            Log.d(TAG, "Observing")

            when (it.status) {
                Resource.Status.SUCCESS -> {
                    //TODO: set progress bar visibility to none
                    if (!it.data?.data?.children.isNullOrEmpty())
                        Log.d(TAG, it.data?.data?.children.toString())
//                        mViewModel.setAfter(it.data?.allPosts?.after)
                    Log.d(TAG, "Success")

                    //TODO: set rv Adapter
                }
                Resource.Status.ERROR -> {
                    Snackbar.make(
                        view,
                        it.message.toString(),
                        Snackbar.LENGTH_LONG
                    ).show()
                    Log.d(TAG, "Error")
                }
                Resource.Status.LOADING -> {
                    //TODO:set progress bar visible
                    Log.d(TAG, "Loading")
                }
            }
        })
    }
}