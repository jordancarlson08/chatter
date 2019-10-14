package com.chuckerteam.chucker.internal.data.repository

import androidx.lifecycle.LiveData
import com.chuckerteam.chucker.internal.data.entity.Generic
import com.chuckerteam.chucker.internal.data.entity.GenericTuple
import com.chuckerteam.chucker.internal.data.room.ChuckerDatabase
import java.util.concurrent.Executor
import java.util.concurrent.Executors

internal class GenericDatabaseRepository(
    private val database: ChuckerDatabase
) : GenericRepository {

    private val executor: Executor = Executors.newSingleThreadExecutor()

    override fun getRecordedGeneric(id: Long): LiveData<Generic> {
        return database.genericDao().getById(id)
    }

    override fun deleteAllGenerics() {
        executor.execute { database.genericDao().deleteAll() }
    }

    override fun getSortedGenericsTuples(screen: Int): LiveData<List<GenericTuple>> {
        return database.genericDao().getTuplesForScreen(screen)
    }

    override fun saveGeneric(generic: Generic) {
        executor.execute { database.genericDao().insert(generic) }
    }

    override fun deleteOldGenerics(threshold: Long) {
        executor.execute { database.genericDao().deleteBefore(threshold) }
    }
}
