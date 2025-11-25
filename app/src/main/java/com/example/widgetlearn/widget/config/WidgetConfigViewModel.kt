package com.example.widgetlearn.widget.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.widgetlearn.data.local.NoteDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = WidgetConfigViewModel.Factory::class)
class WidgetConfigViewModel @AssistedInject constructor(
    @Assisted private val appWidgetId: Int,
    private val noteDao: NoteDao,
    private val widgetConfigRepository: WidgetConfigRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WidgetConfigUiState())
    val uiState: StateFlow<WidgetConfigUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val notes = noteDao.getAllOnce()
            val existingConfig = if (appWidgetId != -1) {
                widgetConfigRepository.getConfig(appWidgetId)
            } else {
                WidgetConfigData()
            }

            _uiState.update {
                it.copy(
                    isLoading = false,
                    notes = notes,
                    selectedNoteId = existingConfig.noteId,
                    selectedColor = existingConfig.backgroundColor
                )
            }
        }
    }

    fun onColorSelected(color: Long) {
        _uiState.update { it.copy(selectedColor = color) }
    }

    fun onNoteSelected(noteId: Long) {
        _uiState.update { it.copy(selectedNoteId = noteId) }
    }

    suspend fun saveConfig(): Boolean {
        if (appWidgetId == -1) return false

        val state = _uiState.value
        if (state.selectedNoteId == null) return false

        val config = WidgetConfigData(
            noteId = state.selectedNoteId,
            backgroundColor = state.selectedColor
        )
        widgetConfigRepository.saveConfig(appWidgetId, config)
        return true
    }

    @AssistedFactory
    interface Factory {
        fun create(appWidgetId: Int): WidgetConfigViewModel
    }
}
