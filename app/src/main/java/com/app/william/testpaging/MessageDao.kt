package com.app.william.testpaging

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MessageDao {

    @Query("SELECT * FROM ${Message.TABLE_NAME} ORDER BY id DESC")
    fun select(): DataSource.Factory<Int, Message>


    @Query("SELECT * FROM ${Message.TABLE_NAME} WHERE message LIKE '%' || :s || '%' ORDER BY id DESC")
    fun select(s:String): DataSource.Factory<Int, Message>


    @Insert()
    fun insert(message: Message): Single<Long>

    @Query("DELETE FROM ${Message.TABLE_NAME}")
    fun removeAll(): Completable
}