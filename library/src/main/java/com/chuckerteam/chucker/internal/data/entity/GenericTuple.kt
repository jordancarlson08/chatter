package com.chuckerteam.chucker.internal.data.entity

import androidx.room.ColumnInfo

/**
 * A subset of [RecordedThrowable] to perform faster Read operations on the Repository.
 * This Tuple is good to be used on List or Preview interfaces.
 */
internal data class GenericTuple(
        @ColumnInfo(name = "id") var id: Long? = 0,
        @ColumnInfo(name = "screen") var screen: Long = 0,
        @ColumnInfo(name = "subTitle") var subTitle: String?,
        @ColumnInfo(name = "date") var date: Long?,
        @ColumnInfo(name = "title") var title: String?,
        @ColumnInfo(name = "message") var message: String?
)
