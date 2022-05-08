package com.zerodeg.memoapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    var title: String,
    var content: String,
    var password:String?,
    var isLock:Boolean,
) {
    @PrimaryKey(autoGenerate = true) var id:Int = 0
}