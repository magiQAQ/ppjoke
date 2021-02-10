package com.xch.ppjoke

import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

abstract class AbsViewModel<T> : ViewModel() {

    private var dataSource: DataSource<Any, Any>? = null
    private val factory = object : DataSource.Factory<Any, Any>() {
        override fun create(): DataSource<Any, Any> {
            if (dataSource == null || dataSource!!.isInvalid) {
                dataSource = createDataSource()
            }
            return dataSource!!
        }
    }

    private val callback = object :PagedList.BoundaryCallback<Any>(){
        override fun onZeroItemsLoaded() {
            super.onZeroItemsLoaded()
        }

        override fun onItemAtFrontLoaded(itemAtFront: Any) {
            super.onItemAtFrontLoaded(itemAtFront)
        }

        override fun onItemAtEndLoaded(itemAtEnd: Any) {
            super.onItemAtEndLoaded(itemAtEnd)
        }
    }

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setInitialLoadSizeHint(12)
//            .setMaxSize(100)
//            .setEnablePlaceholders(false)
//            .setPrefetchDistance()
            .build()

        val pageData = LivePagedListBuilder(factory, config)
            .setInitialLoadKey(0)
            .setBoundaryCallback(callback)
            .build()
    }

    


    public abstract fun createDataSource(): DataSource<Any, Any>
}