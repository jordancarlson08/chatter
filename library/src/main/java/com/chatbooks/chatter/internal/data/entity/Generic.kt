package com.chatbooks.chatter.internal.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * Represent a Generic data point that was recorded by an App.
 */
@Entity(tableName = "generics")
data class Generic(
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long? = 0,
        @ColumnInfo(name = "screen") var screen: String,
        @ColumnInfo(name = "subTitle") var subTitle: String?,
        @ColumnInfo(name = "date") var date: Long?,
        @ColumnInfo(name = "title") var title: String?,
        @ColumnInfo(name = "message") var message: String?,
        @ColumnInfo(name = "content") var content: String?
) {
    @Ignore
    constructor(screen: String, title: String, subTitle: String?, message: String?, content: String?) :
            this(null, "", null, null, null, null, null) {
        this.screen = screen
        this.title = title
        this.subTitle = subTitle
        this.date = System.currentTimeMillis()
        this.message = message
        this.content = content
    }
}
