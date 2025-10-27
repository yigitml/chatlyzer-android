package com.ch3x.chatlyzer.data.remote.dto

data class PrivacyAnalysisDto(
  val chatDto: ChatDto,
  val analyzes: List<AnalysisDto>
)