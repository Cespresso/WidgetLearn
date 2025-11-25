package com.example.widgetlearn.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import com.example.widgetlearn.data.local.NoteEntity
import dagger.hilt.android.EntryPointAccessors
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddNoteActionCallback : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        val entryPoint = EntryPointAccessors.fromApplication(
            context.applicationContext,
            WidgetEntryPoint::class.java
        )
        val noteDao = entryPoint.noteDao()

        val now = System.currentTimeMillis()
        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val note = NoteEntity(
            content = "Widget tap: ${timeFormat.format(Date(now))}",
            createdAt = now,
            updatedAt = now
        )
        noteDao.insert(note)
    }
}
