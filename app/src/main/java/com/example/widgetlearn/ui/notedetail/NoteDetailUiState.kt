package com.example.widgetlearn.ui.notedetail

import com.example.widgetlearn.data.local.NoteEntity

data class NoteDetailUiState(
    val isLoading: Boolean = false,
    val note: NoteEntity? = null
)
