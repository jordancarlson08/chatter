package com.chatbooks.chatter.internal.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chatbooks.chatter.internal.data.entity.Generic
import com.chatbooks.chatter.internal.data.entity.HttpTransaction
import com.chatbooks.chatter.internal.data.entity.RecordedThrowable

@Database(entities = [RecordedThrowable::class, HttpTransaction::class, Generic::class], version = 3, exportSchema = false)
internal abstract class ChatterDatabase : RoomDatabase() {

    abstract fun throwableDao(): RecordedThrowableDao
    abstract fun genericDao(): GenericDao
    abstract fun transactionDao(): HttpTransactionDao

    companion object {
        private val OLD_DB_NAME = "chuck.db"
        private val DB_NAME = "chatter.db"

        fun create(context: Context): ChatterDatabase {
            // We eventually delete the old DB if a previous version of Chuck/Chatter was used.
            context.getDatabasePath(OLD_DB_NAME).delete()

            return Room.databaseBuilder(context, ChatterDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
