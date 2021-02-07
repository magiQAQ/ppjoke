package com.xch.ppjoke.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.xch.ppjoke.databinding.LayoutFeedTypeImageBinding
import com.xch.ppjoke.databinding.LayoutFeedTypeVideoBinding
import com.xch.ppjoke.model.Feed

class FeedAdapter(private val category: String) :
    PagedListAdapter<Feed, FeedAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Feed>() {
        override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean {
            return oldItem.itemId == newItem.itemId
        }

        override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.context = recyclerView.context
        super.onAttachedToRecyclerView(recyclerView)
    }

    private lateinit var context: Context
    private val inflater by lazy { LayoutInflater.from(context) }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.itemType ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = if (viewType == Feed.TYPE_IMAGE) {
            LayoutFeedTypeImageBinding.inflate(inflater)
        } else {
            LayoutFeedTypeVideoBinding.inflate(inflater)
        }
        return ViewHolder(binding, category)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bindData(it) }
    }

    class ViewHolder(private val binding: ViewDataBinding, private val category: String) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: Feed) {
            if (binding is LayoutFeedTypeImageBinding) {
                binding.feed = item
                binding.feedImage.bindData(item.width, item.height, 16, item.cover)
            } else if (binding is LayoutFeedTypeVideoBinding) {
                binding.feed = item
                binding.listPlayerView.bindData(category, item.width, item.height, item.cover, item.url)
            }
        }
    }
}