package com.vkpriesniakov.redditviewer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vkpriesniakov.redditviewer.data.entities.RedditChildrenResponse
import com.vkpriesniakov.redditviewer.databinding.ActivityMainBinding
import com.vkpriesniakov.redditviewer.databinding.RecycleViewItemPostBinding

class PagingTopPostsAdapter(
    val mainBinding: ActivityMainBinding
) :
    PagingDataAdapter<RedditChildrenResponse, PagingTopPostsAdapter.TopPostsViewHolder>(
        PostsComparator
    ) {

    private val inflater: LayoutInflater = LayoutInflater.from(mainBinding.root.context)


    inner class TopPostsViewHolder(private val binding: RecycleViewItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {

            binding.itemPostTitle.text = getItem(position)?.data?.title ?: "Title is missing"

            binding.itemPostCommentsNumber.text = getItem(position)?.data?.num_comments.toString()

            binding.itemPostAuthor.text =
                getItem(position)?.data?.author ?: "Redditor"    //TODO: finally set icon behaviour

            mainBinding.progressBar2.visibility = ProgressBar.GONE

        }
    }

    override fun onBindViewHolder(holder: TopPostsViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopPostsViewHolder {
        val bdn = RecycleViewItemPostBinding.inflate(inflater, parent, false)
        return TopPostsViewHolder(bdn)
    }

    object PostsComparator : DiffUtil.ItemCallback<RedditChildrenResponse>() {

        override fun areItemsTheSame(
            oldItem: RedditChildrenResponse,
            newItem: RedditChildrenResponse
        ): Boolean {
            return oldItem.data.url == newItem.data.url
        }

        override fun areContentsTheSame(
            oldItem: RedditChildrenResponse,
            newItem: RedditChildrenResponse
        ): Boolean {
            return oldItem.data == newItem.data
        }
    }

}