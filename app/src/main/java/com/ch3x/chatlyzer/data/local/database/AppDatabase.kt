package com.ch3x.chatlyzer.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ch3x.chatlyzer.data.local.database.converter.DateConverter
import com.ch3x.chatlyzer.data.local.database.converter.ListConverter
import com.ch3x.chatlyzer.data.local.database.dao.ChatDao
import com.ch3x.chatlyzer.data.local.database.dao.AnalysisDao
import com.ch3x.chatlyzer.data.local.database.dao.FileDao
import com.ch3x.chatlyzer.data.local.database.dao.MessageDao
import com.ch3x.chatlyzer.data.local.database.dao.UserDao
import com.ch3x.chatlyzer.data.local.database.dao.SubscriptionDao
import com.ch3x.chatlyzer.data.local.database.dao.CreditDao
import com.ch3x.chatlyzer.data.local.database.entity.ChatEntity
import com.ch3x.chatlyzer.data.local.database.entity.AnalysisEntity
import com.ch3x.chatlyzer.data.local.database.entity.FileEntity
import com.ch3x.chatlyzer.data.local.database.entity.MessageEntity
import com.ch3x.chatlyzer.data.local.database.entity.SubscriptionEntity
import com.ch3x.chatlyzer.data.local.database.entity.CreditEntity
import com.ch3x.chatlyzer.data.local.database.entity.UserEntity
import com.ch3x.chatlyzer.util.Constants

@Database(
    entities = [
        AnalysisEntity::class,
        ChatEntity::class,
        FileEntity::class,
        MessageEntity::class,
        SubscriptionEntity::class,
        CreditEntity::class,
        UserEntity::class,
    ],
    version = Constants.DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(DateConverter::class, ListConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun analysisDao(): AnalysisDao

    abstract fun chatDao(): ChatDao

    abstract fun fileDao(): FileDao

    abstract fun messageDao(): MessageDao

    abstract fun subscriptionDao(): SubscriptionDao

    abstract fun userCreditDao(): CreditDao

    abstract fun userDao(): UserDao
}