package com.ch3x.chatlyzer.di

import android.content.Context
import androidx.room.Room
import com.ch3x.chatlyzer.data.local.database.AppDatabase
import com.ch3x.chatlyzer.data.local.database.dao.AnalysisDao
import com.ch3x.chatlyzer.data.local.database.dao.ChatDao
import com.ch3x.chatlyzer.data.local.database.dao.FileDao
import com.ch3x.chatlyzer.data.local.database.dao.MessageDao
import com.ch3x.chatlyzer.data.local.database.dao.SubscriptionDao
import com.ch3x.chatlyzer.data.local.database.dao.CreditDao
import com.ch3x.chatlyzer.data.local.database.dao.UserDao
import com.ch3x.chatlyzer.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase 
        = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            Constants.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideAnalysisDao(appDatabase: AppDatabase): AnalysisDao
            = appDatabase.analysisDao()

    @Provides
    @Singleton
    fun provideChatDao(appDatabase: AppDatabase): ChatDao =
        appDatabase.chatDao()

    @Provides
    @Singleton
    fun provideFileDao(appDatabase: AppDatabase): FileDao =
        appDatabase.fileDao()

    @Provides
    @Singleton
    fun provideMessageDao(appDatabase: AppDatabase): MessageDao =
        appDatabase.messageDao()

    @Provides
    @Singleton
    fun provideSubscriptionDao(appDatabase: AppDatabase): SubscriptionDao =
        appDatabase.subscriptionDao()

    @Provides
    @Singleton
    fun provideCreditDao(appDatabase: AppDatabase): CreditDao =
        appDatabase.userCreditDao()

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao =
        appDatabase.userDao()
} 