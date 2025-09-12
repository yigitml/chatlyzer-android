package com.ch3x.chatlyzer.data.repository

import com.ch3x.chatlyzer.data.local.database.dao.CreditDao
import com.ch3x.chatlyzer.data.mapper.toDomain
import com.ch3x.chatlyzer.data.mapper.toEntity
import com.ch3x.chatlyzer.data.remote.api.CreditApi
import com.ch3x.chatlyzer.domain.model.Credit
import com.ch3x.chatlyzer.domain.repository.CreditRepository
import com.ch3x.chatlyzer.data.remote.requireDataOrThrow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreditRepositoryImpl @Inject constructor(
    private val creditApi: CreditApi,
    private val creditDao: CreditDao
): CreditRepository {
    override suspend fun cacheCredit(credit: Credit) =
        creditDao.insertCredit(credit.toEntity())

    override suspend fun cacheCredits(userCredits: List<Credit>) =
        creditDao.insertCredits(userCredits.map { it.toEntity() })

    override suspend fun updateCachedCredit(credit: Credit) =
        creditDao.updateCredit(credit.toEntity())

    override suspend fun deleteCachedCredit(credit: Credit) =
        creditDao.deleteCredit(credit.toEntity())

    override suspend fun getCachedCreditById(creditId: String): Credit? =
        creditDao.getCreditById(creditId)?.toDomain()

    override suspend fun getCachedCredits(): List<Credit> =
        creditDao.getAllCredits().map { it.toDomain() }

    override suspend fun clearCachedCredits() =
        creditDao.deleteAllCredits()

    override suspend fun fetchCredits(): List<Credit> =
        creditApi.getCredits().requireDataOrThrow().map { it.toDomain() }
}