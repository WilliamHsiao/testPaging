package com.app.william.testpaging

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Message::class], version = 1, exportSchema = false)
abstract class MessageDataBase : RoomDatabase() {

    abstract fun dao(): MessageDao

    companion object {
        @Volatile
        private var INSTANCE: MessageDataBase? = null

        fun getDatabase(context: Context): MessageDataBase {
            val tempInstance = INSTANCE
            tempInstance?.let { return tempInstance }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    MessageDataBase::class.java,
                    Message.TABLE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}
