package com.ch3x.chatlyzer.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PrivacyAnalysisDto(
  val chat: ChatDto,
  val analyses: List<AnalysisDto>
)