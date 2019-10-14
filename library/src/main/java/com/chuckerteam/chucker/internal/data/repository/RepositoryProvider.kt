package com.chuckerteam.chucker.internal.data.repository

import android.content.Context
import com.chuckerteam.chucker.internal.data.repository.RepositoryProvider.initialize
import com.chuckerteam.chucker.internal.data.room.ChuckerDatabase

/**
 * A singleton to hold the [ChuckerRepository] instance. Make sure you call [initialize] before
 * accessing the stored instance.
 */
internal object RepositoryProvider {

    private var transactionRepository: HttpTransactionRepository? = null
    private var throwableRepository: RecordedThrowableRepository? = null
    private var genericRepository: GenericRepository? = null

    @JvmStatic
    fun transaction(): HttpTransactionRepository {
        return checkNotNull(transactionRepository) {
            "You can't access the transaction repository if you don't initialize it!"
        }
    }

    @JvmStatic
    fun throwable(): RecordedThrowableRepository {
        return checkNotNull(throwableRepository) {
            "You can't access the throwable repository if you don't initialize it!"
        }
    }

    @JvmStatic
    fun generic(): GenericRepository {
        return checkNotNull(genericRepository) {
            "You can't access the generic repository if you don't initialize it!"
        }
    }

    /**
     * Idempotent method. Must be called before accessing the repositories.
     */
    @JvmStatic
    fun initialize(context: Context) {
        if (transactionRepository == null || throwableRepository == null || genericRepository == null) {
            val db = ChuckerDatabase.create(context)
            transactionRepository = HttpTransactionDatabaseRepository(db)
            throwableRepository = RecordedThrowableDatabaseRepository(db)
            genericRepository = GenericDatabaseRepository(db)
        }
    }
}
