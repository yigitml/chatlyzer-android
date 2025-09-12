package com.ch3x.chatlyzer.domain.repository

import com.ch3x.chatlyzer.data.remote.SubscriptionDeleteRequest
import com.ch3x.chatlyzer.domain.model.Subscription

interface SubscriptionRepository {
    suspend fun insertSubscription(subscription: Subscription)

    suspend fun insertSubscriptions(subscriptions: List<Subscription>)

    suspend fun updateSubscription(subscription: Subscription)

    suspend fun deleteSubscription(subscription: Subscription)

    suspend fun getSubscriptionById(subscriptionId: String): Subscription?

    suspend fun getAllSubscriptions(): List<Subscription>

    suspend fun clearAllSubscriptions()

    suspend fun fetchSubscriptions(): List<Subscription>

    suspend fun fetchSubscriptionById(id: String): Subscription?

    suspend fun deleteSubscription(subscriptionDeleteRequest: SubscriptionDeleteRequest): Subscription
}