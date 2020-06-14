package com.app.william.testpaging

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Message(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val message: String = ""){
    companion object{
        const val TABLE_NAME="Message"

    }

}