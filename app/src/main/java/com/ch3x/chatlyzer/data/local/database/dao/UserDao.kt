package com.ch3x.chatlyzer.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ch3x.chatlyzer.data.local.database.entity.UserEntity

@Dao
interface UserDao {
    
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)
    
    
    @Update
    suspend fun updateUser(user: UserEntity)
    
    
    @Delete
    suspend fun deleteUser(user: UserEntity)
    
    
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: String): UserEntity?
    
    
    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserEntity?
    
    
    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserEntity>
    
    
    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()

} 