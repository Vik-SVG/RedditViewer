package com.vkpriesniakov.redditviewer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.vkpriesniakov.redditviewer.adapters.PagingTopPostsAdapter
import com.vkpriesniakov.redditviewer.databinding.ActivityMainBinding
import com.vkpriesniakov.redditviewer.utils.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivityTag"

    private val mViewModel: MainViewModel by viewModel()
    private lateinit var bdn: ActivityMainBinding
    private lateinit var mPostsAdapter: PagingTopPostsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bdn = ActivityMainBinding.inflate(layoutInflater)
        val view = bdn.root
        setContentView(view)

        setObserverWithFlow(bdn)

        setObserverWithLivaData(bdn.root)

    }

    private fun setObserverWithFlow(binding: ActivityMainBinding) {
        mPostsAdapter = PagingTopPostsAdapter(binding)
        bdn.mainRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mPostsAdapter
        }

        this.lifecycleScope.launch {
            mViewModel.allTopPostsPaging.collectLatest {
                bdn.mainProgressBar.visibility = ProgressBar.VISIBLE
                mPostsAdapter.submitData(it)
            }
        }
    }

    // additional implementation with live data
    private fun setObserverWithLivaData(view: RelativeLayout) {
        mViewModel.allPostsStatus.observe(this, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
//                    bdn.progressBar2.visibility = ProgressBar.GONE
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
//                    bdn.progressBar2.visibility = ProgressBar.VISIBLE
                    Log.d(TAG, "Loading")
                }
            }
        })
    }
}