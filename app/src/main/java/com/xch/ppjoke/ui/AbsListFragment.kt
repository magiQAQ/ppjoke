package com.xch.ppjoke.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.xch.libcommon.EmptyView
import com.xch.ppjoke.databinding.LayoutRefreshViewBinding

open class AbsListFragment: Fragment(), OnRefreshLoadMoreListener {

    private lateinit var binding: LayoutRefreshViewBinding
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mRefreshLayout: SmartRefreshLayout
    private lateinit var mEmptyView: EmptyView

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
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        TODO("Not yet implemented")
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        TODO("Not yet implemented")
    }
}