package com.xch.ppjoke.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.xch.libcommon.EmptyView
import com.xch.ppjoke.databinding.LayoutRefreshViewBinding
import kotlinx.android.synthetic.main.layout_refresh_view.*

abstract class AbsListFragment<T> : Fragment(), OnRefreshLoadMoreListener {

    private lateinit var binding: LayoutRefreshViewBinding
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mRefreshLayout: SmartRefreshLayout
    private lateinit var mEmptyView: EmptyView
    private lateinit var mAdapter: PagedListAdapter<T, RecyclerView.ViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutRefreshViewBinding.inflate(inflater, container, false)
        mRecyclerView = binding.recyclerView
        mRefreshLayout = binding.refreshLayout
        mEmptyView = binding.emptyView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRefreshLayout.setOnRefreshListener(this)
        mRefreshLayout.setOnLoadMoreListener(this)

        mAdapter = getAdapter()
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mRecyclerView.itemAnimator = null
    }

    abstract fun getAdapter(): PagedListAdapter<T, RecyclerView.ViewHolder>

    fun submitList(pagedList: PagedList<T>) {
        if (pagedList.size > 0) {
            mAdapter.submitList(pagedList)
        }
        finishRefresh(pagedList.isNotEmpty())
    }

    fun finishRefresh(hasData: Boolean) {
        val currentList = mAdapter.currentList
        val reallyHasData = hasData || currentList != null && currentList.isNotEmpty()
        val state = refresh_layout.state
        if (state.isFooter && state.isOpening) {
            mRefreshLayout.finishLoadMore()
        } else if (state.isHeader && state.isOpening) {
            mRefreshLayout.finishRefresh()
        }

        mEmptyView.visibility = if (reallyHasData) View.GONE else View.VISIBLE
    }
}