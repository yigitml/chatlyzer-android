package com.ch3x.chatlyzer.data.mapper

import com.ch3x.chatlyzer.data.local.database.TypeConverter
import com.ch3x.chatlyzer.data.local.database.entity.AnalysisEntity
import com.ch3x.chatlyzer.data.local.database.entity.ChatEntity
import com.ch3x.chatlyzer.data.local.database.entity.CreditEntity
import com.ch3x.chatlyzer.data.local.database.entity.FileEntity
import com.ch3x.chatlyzer.data.local.database.entity.MessageEntity
import com.ch3x.chatlyzer.data.local.database.entity.SubscriptionEntity
import com.ch3x.chatlyzer.data.local.database.entity.UserEntity
import com.ch3x.chatlyzer.data.remote.dto.AnalysisDto
import com.ch3x.chatlyzer.data.remote.dto.ChatDto
import com.ch3x.chatlyzer.data.remote.dto.FileDto
import com.ch3x.chatlyzer.data.remote.dto.MessageDto
import com.ch3x.chatlyzer.data.remote.dto.SubscriptionDto
import com.ch3x.chatlyzer.data.remote.dto.UserCreditDto
import com.ch3x.chatlyzer.data.remote.dto.UserDto
import com.ch3x.chatlyzer.domain.model.Analysis
import com.ch3x.chatlyzer.domain.model.Chat
import com.ch3x.chatlyzer.domain.model.Credit
import com.ch3x.chatlyzer.domain.model.File
import com.ch3x.chatlyzer.domain.model.Message
import com.ch3x.chatlyzer.domain.model.Subscription
import com.ch3x.chatlyzer.domain.model.User
import com.ch3x.chatlyzer.util.JsonMapFactory

fun Chat.toEntity(): ChatEntity {
    val converter = TypeConverter()
    return ChatEntity(
        id = id,
        userId = userId,
        title = title,
        participants = converter.fromStringList(participants),
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt
    )
}

fun ChatEntity.toDomain(): Chat {
    val converter = TypeConverter()
    return Chat(
        id = id,
        title = title,
        participants = converter.toStringList(participants),
        createdAt = createdAt,
        updatedAt = updatedAt,
        userId = userId,
        messages = emptyList(),
        analyzes = emptyList<Analysis>(),
        files = emptyList(),
        deletedAt = deletedAt
    )
}

fun Chat.toDto() = ChatDto(
    id = id,
    userId = userId,
    title = title,
    participants = participants,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)

fun ChatDto.toDomain() = Chat(
    id = id,
    title = title,
    participants = participants,
    createdAt = createdAt,
    updatedAt = updatedAt,
    userId = userId,
    messages = emptyList(),
    analyzes = emptyList<Analysis>(),
    files = emptyList(),
    deletedAt = deletedAt
)

fun Message.toEntity() = MessageEntity(
    id = id,
    chatId = chatId,
    sender = sender,
    content = content,
    timestamp = timestamp,
    metadataJson = metadata.toString(),
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)

fun MessageEntity.toDomain() = Message(
    id = id,
    chatId = chatId,
    sender = sender,
    content = content,
    timestamp = timestamp,
    metadata = JsonMapFactory.fromJsonOrNull(metadataJson.toString()),
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)

fun Message.toDto() = MessageDto(
    id = id,
    chatId = chatId,
    sender = sender,
    content = content,
    timestamp = timestamp,
    metadata = metadata.toString(),
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)

fun MessageDto.toDomain() = Message(
    id = id,
    chatId = chatId,
    sender = sender,
    content = content,
    timestamp = timestamp,
    metadata = JsonMapFactory.fromJsonOrNull(metadata.toString()),
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)

fun User.toEntity() = UserEntity(
    id = id,
    name = name,
    email = email,
    createdAt = createdAt,
    isOnboarded = isOnboarded,
    image = image,
    googleId = googleId,
    tokenVersion = tokenVersion,
    lastLoginAt = lastLoginAt,
    isActive = isActive,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)

fun UserEntity.toDomain() = User(
    id = id,
    name = name,
    email = email,
    createdAt = createdAt,
    isOnboarded = isOnboarded,
    image = image,
    googleId = googleId,
    tokenVersion = tokenVersion,
    lastLoginAt = lastLoginAt,
    isActive = isActive,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)

fun User.toDto() = UserDto(
    id = id,
    name = name,
    email = email,
    createdAt = createdAt,
    isOnboarded = isOnboarded,
    image = image,
    googleId = googleId,
    tokenVersion = tokenVersion,
    lastLoginAt = lastLoginAt,
    isActive = isActive,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)

fun UserDto.toDomain() = User(
    id = id,
    name = name,
    email = email,
    createdAt = createdAt,
    isOnboarded = isOnboarded,
    image = image,
    googleId = googleId,
    tokenVersion = tokenVersion,
    lastLoginAt = lastLoginAt,
    isActive = isActive,
    updatedAt = updatedAt,
    deletedAt = deletedAt
)

fun Analysis.toEntity() = AnalysisEntity(
    id = id,
    resultJson = result.toString(),
    createdAt = createdAt,
    updatedAt = updatedAt,
    chatId = chatId,
    userId = userId,
    deletedAt = deletedAt
)

fun AnalysisEntity.toDomain() = Analysis(
    id = id,
    result = JsonMapFactory.fromJsonOrNull(resultJson.toString()),
    createdAt = createdAt,
    updatedAt = updatedAt,
    chatId = chatId,
    userId = userId,
    deletedAt = deletedAt
)

fun Analysis.toDto() = AnalysisDto(
    id = id,
    result = result,
    createdAt = createdAt,
    updatedAt = updatedAt,
    chatId = chatId,
    userId = userId,
    deletedAt = deletedAt
)

fun AnalysisDto.toDomain() = Analysis(
    id = id,
    result = result,
    createdAt = createdAt,
    updatedAt = updatedAt,
    chatId = chatId,
    userId = userId,
    deletedAt = deletedAt
)

fun Credit.toEntity() = CreditEntity(
    id = id,
    userId = userId,
    subscriptionId = subscriptionId,
    type = type,
    totalAmount = totalAmount,
    amount = amount,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt,
    minimumBalance = minimumBalance
)

fun CreditEntity.toDomain() = Credit(
    id = id,
    userId = userId,
    subscriptionId = subscriptionId,
    type = type,
    totalAmount = totalAmount,
    amount = amount,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt,
    minimumBalance = minimumBalance
)

fun Credit.toDto() = UserCreditDto(
    id = id,
    userId = userId,
    subscriptionId = subscriptionId,
    type = type,
    totalAmount = totalAmount,
    amount = amount,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt,
    minimumBalance = minimumBalance
)

fun UserCreditDto.toDomain() = Credit(
    id = id,
    userId = userId,
    subscriptionId = subscriptionId,
    type = type,
    totalAmount = totalAmount,
    amount = amount,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt,
    minimumBalance = minimumBalance
)

fun File.toEntity() = FileEntity(
    id = id,
    url = url,
    size = size,
    createdAt = createdAt,
    userId = userId,
    chatId = chatId
)

fun FileEntity.toDomain() = File(
    id = id,
    url = url,
    size = size,
    createdAt = createdAt,
    userId = userId,
    chatId = chatId
)

fun File.toDto() = FileDto(
    id = id,
    url = url,
    size = size,
    createdAt = createdAt,
    userId = userId,
    chatId = chatId
)

fun FileDto.toDomain() = File(
    id = id,
    url = url,
    size = size,
    createdAt = createdAt,
    userId = userId,
    chatId = chatId
)

fun Subscription.toEntity() = SubscriptionEntity(
    id = id,
    name = name,
    price = price,
    isActive = isActive,
    durationDays = durationDays,
    createdAt = createdAt,
    userId = userId,
    deletedAt = deletedAt
)

fun SubscriptionEntity.toDomain() = Subscription(
    id = id,
    name = name,
    price = price,
    isActive = isActive,
    durationDays = durationDays,
    createdAt = createdAt,
    userId = userId,
    deletedAt = deletedAt
)

fun Subscription.toDto() = SubscriptionDto(
    id = id,
    name = name,
    price = price,
    isActive = isActive,
    durationDays = durationDays,
    createdAt = createdAt,
    userId = userId,
    deletedAt = deletedAt
)

fun SubscriptionDto.toDomain() = Subscription(
    id = id,
    name = name,
    price = price,
    isActive = isActive,
    durationDays = durationDays,
    createdAt = createdAt,
    userId = userId,
    deletedAt = deletedAt
)