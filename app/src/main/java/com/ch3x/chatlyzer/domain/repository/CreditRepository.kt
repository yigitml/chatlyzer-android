package com.ch3x.chatlyzer.domain.repository

import com.ch3x.chatlyzer.domain.model.Credit

interface CreditRepository {
    suspend fun cacheCredit(
        credit: Credit
    )

    suspend fun cacheCredits(
        userCredits: List<Credit>
    )

    suspend fun updateCachedCredit(
        credit: Credit
    )

    suspend fun deleteCachedCredit(
        credit: Credit
    )

    suspend fun getCachedCreditById(
        creditId: String
        ): Credit?

    suspend fun getCachedCredits(): List<Credit>

    suspend fun clearCachedCredits()

    suspend fun fetchCredits(): List<Credit>
}