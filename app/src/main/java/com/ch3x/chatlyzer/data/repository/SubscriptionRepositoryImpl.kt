package com.ch3x.chatlyzer.data.repository

import com.ch3x.chatlyzer.data.local.database.dao.SubscriptionDao
import com.ch3x.chatlyzer.data.mapper.toDomain
import com.ch3x.chatlyzer.data.mapper.toEntity
import com.ch3x.chatlyzer.data.remote.SubscriptionDeleteRequest
import com.ch3x.chatlyzer.data.remote.api.SubscriptionApi
import com.ch3x.chatlyzer.domain.model.Subscription
import com.ch3x.chatlyzer.domain.repository.SubscriptionRepository
import com.ch3x.chatlyzer.data.remote.requireDataOrThrow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubscriptionRepositoryImpl @Inject constructor(
    private val subscriptionApi: SubscriptionApi,
    private val subscriptionDao: SubscriptionDao
): SubscriptionRepository {
    override suspend fun insertSubscription(subscription: Subscription) =
        subscriptionDao.insertSubscription(subscription.toEntity())

    override suspend fun insertSubscriptions(subscriptions: List<Subscription>) =
        subscriptionDao.insertSubscriptions(subscriptions.map { it.toEntity() })

    override suspend fun updateSubscription(subscription: Subscription) =
        subscriptionDao.updateSubscription(subscription.toEntity())

    override suspend fun deleteSubscription(subscription: Subscription) =
        subscriptionDao.deleteSubscription(subscription.toEntity())

    override suspend fun getSubscriptionById(subscriptionId: String): Subscription? =
        subscriptionDao.getSubscriptionById(subscriptionId)?.toDomain()

    override suspend fun getAllSubscriptions(): List<Subscription> =
        subscriptionDao.getAllSubscriptions().map { it.toDomain() }

    override suspend fun clearAllSubscriptions() =
        subscriptionDao.deleteAllSubscriptions()

    override suspend fun fetchSubscriptions(): List<Subscription> =
        subscriptionApi.getSubscriptions().requireDataOrThrow().map { it.toDomain() }

    override suspend fun fetchSubscriptionById(id: String): Subscription? =
        subscriptionApi.getSubscriptionById(id).requireDataOrThrow().toDomain()

    override suspend fun deleteSubscription(subscriptionDeleteRequest: SubscriptionDeleteRequest) =
        subscriptionApi.deleteSubscription(subscriptionDeleteRequest).requireDataOrThrow().toDomain()
}