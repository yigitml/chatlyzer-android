package com.ch3x.chatlyzer.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ch3x.chatlyzer.data.local.database.entity.AnalysisEntity

@Dao
interface AnalysisDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnalysis(analysis: AnalysisEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnalyzes(analyses: List<AnalysisEntity>)

    @Update
    suspend fun updateAnalysis(analysis: AnalysisEntity)

    @Delete
    suspend fun deleteAnalysis(analysis: AnalysisEntity)

    @Query("SELECT * FROM analyses WHERE id = :analysisId")
    suspend fun getAnalysisById(analysisId: String): AnalysisEntity?

    @Query("SELECT * FROM analyses")
    suspend fun getAllAnalyzes(): List<AnalysisEntity>

    @Query("DELETE FROM analyses")
    suspend fun deleteAllAnalyzes()
} 