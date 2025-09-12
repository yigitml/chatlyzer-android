package com.ch3x.chatlyzer.di

import android.content.Context
import com.ch3x.chatlyzer.data.local.preferences.PreferencesManager
import com.ch3x.chatlyzer.data.local.preferences.PreferencesManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providePreferencesManager(
        @ApplicationContext context: Context
    ): PreferencesManager = PreferencesManagerImpl(context)
} 