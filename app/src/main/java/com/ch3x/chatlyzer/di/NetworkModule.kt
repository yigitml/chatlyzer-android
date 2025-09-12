package com.ch3x.chatlyzer.di

import com.ch3x.chatlyzer.data.remote.api.AnalysisApi
import com.ch3x.chatlyzer.data.remote.api.ChatApi
import com.ch3x.chatlyzer.data.remote.api.AuthApi
import com.ch3x.chatlyzer.data.remote.api.CreditApi
import com.ch3x.chatlyzer.data.remote.api.FileApi
import com.ch3x.chatlyzer.data.remote.api.MessageApi
import com.ch3x.chatlyzer.data.remote.api.SubscriptionApi
import com.ch3x.chatlyzer.data.remote.api.UserApi
import com.ch3x.chatlyzer.data.remote.interceptor.DynamicJwtInterceptor
import com.ch3x.chatlyzer.util.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            .create()
    }

    @Provides
    @Singleton
    @Named("default")
    fun provideOkHttpClient(
        dynamicJwtInterceptor: DynamicJwtInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(dynamicJwtInterceptor)
            .connectTimeout(Constants.API_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.API_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.API_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @Named("analysis")
    fun provideAnalysisOkHttpClient(
        dynamicJwtInterceptor: DynamicJwtInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(dynamicJwtInterceptor)
            .connectTimeout(Constants.ANALYSIS_API_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.ANALYSIS_API_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.ANALYSIS_API_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @Named("default")
    fun provideRetrofit(
        @Named("default") okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    @Named("analysis")
    fun provideAnalysisRetrofit(
        @Named("analysis") okHttpClient: OkHttpClient,
        gson: Gson
        ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun providesAuthApi(@Named("default") retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAnalysisApi(@Named("analysis") retrofit: Retrofit): AnalysisApi {
        return retrofit.create(AnalysisApi::class.java)
    }

    @Provides
    @Singleton
    fun provideChatApi(@Named("default") retrofit: Retrofit): ChatApi {
        return retrofit.create(ChatApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCreditApi(@Named("default") retrofit: Retrofit): CreditApi {
        return retrofit.create(CreditApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFileApi(@Named("default") retrofit: Retrofit): FileApi {
        return retrofit.create(FileApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMessageApi(@Named("default") retrofit: Retrofit): MessageApi {
        return retrofit.create(MessageApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSubscriptionApi(@Named("default") retrofit: Retrofit): SubscriptionApi {
        return retrofit.create(SubscriptionApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(@Named("default") retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }
} 