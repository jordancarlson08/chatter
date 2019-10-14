package com.chatbooks.chatter.internal.data.repository

import androidx.lifecycle.LiveData
import com.chatbooks.chatter.internal.data.entity.RecordedThrowable
import com.chatbooks.chatter.internal.data.entity.RecordedThrowableTuple

/**
 * Repository Interface representing all the operations that are needed to let Chatter work
 * with [RecordedThrowable] and [RecordedThrowableTuple]. Please use [ChatterDatabaseRepository]
 * that uses Room and SqLite to run those operations.
 */
internal interface RecordedThrowableRepository {

    fun saveThrowable(throwable: RecordedThrowable)

    fun deleteOldThrowables(threshold: Long)

    fun deleteAllThrowables()

    fun getSortedThrowablesTuples(): LiveData<List<RecordedThrowableTuple>>

    fun getRecordedThrowable(id: Long): LiveData<RecordedThrowable>
}
