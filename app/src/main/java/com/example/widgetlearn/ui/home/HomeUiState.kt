package com.example.widgetlearn.ui.home

import com.example.widgetlearn.data.local.NoteEntity

data class HomeUiState(
    val isLoading: Boolean = false,
    val notes: List<NoteEntity> = emptyList()
)
