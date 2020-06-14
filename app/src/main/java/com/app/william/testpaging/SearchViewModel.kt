package com.app.william.testpaging

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

class SearchViewModel(application: Application) : AndroidViewModel(application) {


    private val messageDB by lazy { MessageDataBase.getDatabase(application).dao() }

    val selectId = MutableLiveData<Long>()

    private val config = PagedList.Config.Builder()
        .setPageSize(30)
        .setPrefetchDistance(20)
        .setEnablePlaceholders(true)
        .build()

    private val query = MutableLiveData<String>()

    val messages = Transformations.switchMap(query) {
        LivePagedListBuilder(messageDB.select(it), config).build()
    }


    fun setQuery(s: String) {
        query.postValue(s)
    }


    fun click(id: Long) {
        selectId.postValue(id)
    }
}