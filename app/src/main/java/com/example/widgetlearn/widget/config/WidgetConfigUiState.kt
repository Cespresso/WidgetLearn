package com.example.widgetlearn.widget.config

import com.example.widgetlearn.data.local.NoteEntity

data class WidgetConfigUiState(
    val isLoading: Boolean = true,
    val notes: List<NoteEntity> = emptyList(),
    val selectedNoteId: Long? = null,
    val selectedColor: Long = PresetColors.DEFAULT_COLOR,
    val presetColors: List<Long> = PresetColors.list
)
