package com.chuckerteam.chucker.internal.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.chuckerteam.chucker.internal.data.entity.Generic
import com.chuckerteam.chucker.internal.data.entity.GenericTuple

@Dao
internal interface GenericDao {

    @Query("SELECT id,screen,title,date,subTitle,message FROM generics ORDER BY date DESC")
    fun getTuples(): LiveData<List<GenericTuple>>

    @Insert()
    fun insert(throwable: Generic): Long?

    @Query("DELETE FROM generics")
    fun deleteAll()

    @Query("SELECT * FROM generics WHERE id = :id")
    fun getById(id: Long): LiveData<Generic>

    @Query("DELETE FROM generics WHERE date <= :threshold")
    fun deleteBefore(threshold: Long)

    @Query("SELECT id,screen,title,date,subTitle,message FROM generics WHERE screen = :screen ORDER BY date DESC")
    fun getTuplesForScreen(screen: Int): LiveData<List<GenericTuple>>
}
