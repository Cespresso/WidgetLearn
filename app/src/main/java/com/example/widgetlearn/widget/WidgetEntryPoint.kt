package com.example.widgetlearn.widget

import com.example.widgetlearn.data.local.NoteDao
import com.example.widgetlearn.widget.config.WidgetConfigRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface WidgetEntryPoint {
    fun noteDao(): NoteDao
    fun widgetConfigRepository(): WidgetConfigRepository
}
