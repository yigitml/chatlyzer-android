package com.ch3x.chatlyzer.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ch3x.chatlyzer.data.local.database.entity.CreditEntity

@Dao
interface CreditDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCredit(userCredit: CreditEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCredits(userCredits: List<CreditEntity>)

    @Update
    suspend fun updateCredit(userCredit: CreditEntity)

    @Delete
    suspend fun deleteCredit(userCredit: CreditEntity)

    @Query("SELECT * FROM credits WHERE id = :creditId")
    suspend fun getCreditById(creditId: String): CreditEntity?

    @Query("SELECT * FROM credits")
    suspend fun getAllCredits(): List<CreditEntity>

    @Query("DELETE FROM credits")
    suspend fun deleteAllCredits()
} 