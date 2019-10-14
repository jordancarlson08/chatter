package com.chuckerteam.chucker.internal.data.repository

import androidx.lifecycle.LiveData
import com.chuckerteam.chucker.internal.data.entity.Generic
import com.chuckerteam.chucker.internal.data.entity.GenericTuple
import com.chuckerteam.chucker.internal.data.entity.RecordedThrowable
import com.chuckerteam.chucker.internal.data.entity.RecordedThrowableTuple

/**
 * Repository Interface representing all the operations that are needed to let Chucker work
 * with [Generic] and [GenericTuple]. Please use [ChuckerDatabaseRepository]
 * that uses Room and SqLite to run those operations.
 */
internal interface GenericRepository {

    fun saveGeneric(generic: Generic)

    fun deleteOldGenerics(threshold: Long)

    fun deleteAllGenerics()

    fun getSortedGenericsTuples(screen: Int): LiveData<List<GenericTuple>>

    fun getRecordedGeneric(id: Long): LiveData<Generic>
}
