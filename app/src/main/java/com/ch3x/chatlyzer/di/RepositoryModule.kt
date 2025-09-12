package com.ch3x.chatlyzer.di

import com.ch3x.chatlyzer.data.local.database.dao.AnalysisDao
import com.ch3x.chatlyzer.data.local.database.dao.ChatDao
import com.ch3x.chatlyzer.data.local.database.dao.CreditDao
import com.ch3x.chatlyzer.data.local.database.dao.FileDao
import com.ch3x.chatlyzer.data.local.database.dao.MessageDao
import com.ch3x.chatlyzer.data.local.database.dao.SubscriptionDao
import com.ch3x.chatlyzer.data.local.database.dao.UserDao
import com.ch3x.chatlyzer.data.local.preferences.PreferencesManager
import com.ch3x.chatlyzer.data.remote.api.AnalysisApi
import com.ch3x.chatlyzer.data.remote.api.AuthApi
import com.ch3x.chatlyzer.data.remote.api.ChatApi
import com.ch3x.chatlyzer.data.remote.api.CreditApi
import com.ch3x.chatlyzer.data.remote.api.FileApi
import com.ch3x.chatlyzer.data.remote.api.MessageApi
import com.ch3x.chatlyzer.data.remote.api.SubscriptionApi
import com.ch3x.chatlyzer.data.remote.api.UserApi
import com.ch3x.chatlyzer.data.repository.AnalysisRepositoryImpl
import com.ch3x.chatlyzer.data.repository.AppPreferencesRepositoryImpl
import com.ch3x.chatlyzer.data.repository.AuthRepositoryImpl
import com.ch3x.chatlyzer.data.repository.ChatRepositoryImpl
import com.ch3x.chatlyzer.data.repository.CreditRepositoryImpl
import com.ch3x.chatlyzer.data.repository.FileRepositoryImpl
import com.ch3x.chatlyzer.data.repository.MessageRepositoryImpl
import com.ch3x.chatlyzer.data.repository.SubscriptionRepositoryImpl
import com.ch3x.chatlyzer.data.repository.UserRepositoryImpl
import com.ch3x.chatlyzer.domain.repository.AnalysisRepository
import com.ch3x.chatlyzer.domain.repository.AppPreferencesRepository
import com.ch3x.chatlyzer.domain.repository.AuthRepository
import com.ch3x.chatlyzer.domain.repository.ChatRepository
import com.ch3x.chatlyzer.domain.repository.CreditRepository
import com.ch3x.chatlyzer.domain.repository.FileRepository
import com.ch3x.chatlyzer.domain.repository.MessageRepository
import com.ch3x.chatlyzer.domain.repository.SubscriptionRepository
import com.ch3x.chatlyzer.domain.repository.UserRepository
import dagger.Provides
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesChatRepository(
        chatApi: ChatApi,
        chatDao: ChatDao
    ): ChatRepository = ChatRepositoryImpl(chatApi, chatDao)

    @Provides
    @Singleton
    fun providesAnalysisRepository(
        analysisApi: AnalysisApi,
        analysisDao: AnalysisDao
    ): AnalysisRepository = AnalysisRepositoryImpl(analysisApi, analysisDao)

    @Provides
    @Singleton
    fun providesSubscriptionRepository(
        subscriptionApi: SubscriptionApi,
        subscriptionDao: SubscriptionDao
    ): SubscriptionRepository = SubscriptionRepositoryImpl(subscriptionApi, subscriptionDao)

    @Provides
    @Singleton
    fun providesFileRepository(
        fileApi: FileApi,
        fileDao: FileDao
    ): FileRepository = FileRepositoryImpl(fileApi, fileDao)

    @Provides
    @Singleton
    fun providesCreditRepository(
        creditApi: CreditApi,
        creditDao: CreditDao
    ): CreditRepository = CreditRepositoryImpl(creditApi, creditDao)

    @Provides
    @Singleton
    fun providesMessageRepository(
        messageApi: MessageApi,
        messageDao: MessageDao
    ) : MessageRepository = MessageRepositoryImpl(
        messageApi,
        messageDao
    )

    @Provides
    @Singleton
    fun providesUserRepository(
        userDao: UserDao,
        userApi: UserApi,
        preferencesManager: PreferencesManager
    ) : UserRepository = UserRepositoryImpl(userDao, userApi)

    @Provides
    @Singleton
    fun providesAuthRepository(
        authApi: AuthApi,
        userApi: UserApi,
        preferencesManager: PreferencesManager
    ) : AuthRepository = AuthRepositoryImpl(authApi)

    @Provides
    @Singleton
    fun providesAppPreferencesRepository(
        preferencesManager: PreferencesManager
    ) : AppPreferencesRepository = AppPreferencesRepositoryImpl(preferencesManager)
} 