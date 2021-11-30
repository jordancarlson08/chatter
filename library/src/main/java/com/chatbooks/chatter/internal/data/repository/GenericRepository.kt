package com.chatbooks.chatter.internal.data.repository

import androidx.lifecycle.LiveData
import com.chatbooks.chatter.internal.data.entity.Generic
import com.chatbooks.chatter.internal.data.entity.GenericTuple
import com.chatbooks.chatter.internal.data.entity.RecordedThrowable
import com.chatbooks.chatter.internal.data.entity.RecordedThrowableTuple

/**
 * Repository Interface representing all the operations that are needed to let Chatter work
 * with [Generic] and [GenericTuple]. Please use [ChatterDatabaseRepository]
 * that uses Room and SqLite to run those operations.
 */
internal interface GenericRepository {

    fun saveGeneric(generic: Generic)

    fun deleteOldGenerics(threshold: Long)

    fun deleteAllGenerics()

    fun getSortedGenericsTuples(screen: String): LiveData<List<GenericTuple>>

    fun getRecordedGeneric(id: Long): LiveData<Generic>
}
