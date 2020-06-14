package com.app.william.testpaging

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val messageDB by lazy { MessageDataBase.getDatabase(application).dao() }

    private val config = PagedList.Config.Builder()
        .setPageSize(20)
        .setPrefetchDistance(10)
        .setEnablePlaceholders(true)
        .build()

    val selectId = MutableLiveData<Long?>()

    private val pagedListBuilder = LivePagedListBuilder(messageDB.select(), config)

    val messages = Transformations.switchMap(selectId) {

        it?.let {
            if (it != 0L) {
                pagedListBuilder.setInitialLoadKey(it.toInt())
                    .build()
            }
        }

        pagedListBuilder.build()
    }

    init {
        selectId.value = null
    }


    fun post(s: String) {
        messageDB.insert(Message(0, s))
            .subscribeOn(Schedulers.io())
            .subscribe(object : SingleObserver<Long> {
                override fun onSuccess(t: Long) {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                }
            })
    }

    fun removeAll() {
        messageDB.removeAll()
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                }
            })

    }

    fun scrollTo(id: Long) {

        selectId.postValue(id)
    }
}