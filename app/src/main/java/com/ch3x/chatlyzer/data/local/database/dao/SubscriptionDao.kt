package com.ch3x.chatlyzer.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ch3x.chatlyzer.data.local.database.entity.SubscriptionEntity

@Dao
interface SubscriptionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubscription(subscription: SubscriptionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubscriptions(subscriptions: List<SubscriptionEntity>)

    @Update
    suspend fun updateSubscription(subscription: SubscriptionEntity)

    @Delete
    suspend fun deleteSubscription(subscription: SubscriptionEntity)

    @Query("SELECT * FROM subscriptions WHERE id = :subscriptionId")
    suspend fun getSubscriptionById(subscriptionId: String): SubscriptionEntity?

    @Query("SELECT * FROM subscriptions")
    suspend fun getAllSubscriptions(): List<SubscriptionEntity>

    @Query("DELETE FROM subscriptions")
    suspend fun deleteAllSubscriptions()
} 