package com.example.widgetlearn.widget.config

import kotlinx.serialization.Serializable

@Serializable
data class WidgetConfigData(
    val noteId: Long? = null,
    val backgroundColor: Long = PresetColors.DEFAULT_COLOR
)
