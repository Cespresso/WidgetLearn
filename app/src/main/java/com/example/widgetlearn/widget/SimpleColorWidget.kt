package com.example.widgetlearn.widget

import android.content.Context
import android.content.Intent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.widgetlearn.MainActivity
import dagger.hilt.android.EntryPointAccessors

class SimpleColorWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val appWidgetId = GlanceAppWidgetManager(context).getAppWidgetId(id)

        val entryPoint = EntryPointAccessors.fromApplication(
            context.applicationContext,
            WidgetEntryPoint::class.java
        )
        val configRepository = entryPoint.widgetConfigRepository()
        val noteDao = entryPoint.noteDao()

        val config = configRepository.getConfig(appWidgetId)
        val noteContent = config.noteId?.let { noteId ->
            noteDao.getById(noteId)?.content
        } ?: "ノートを選択してください"

        val backgroundColor = Color(config.backgroundColor)
        val noteId = config.noteId

        provideContent {
            GlanceTheme {
                val clickAction = actionStartActivity(
                    Intent(context, MainActivity::class.java).apply {
                        if (noteId != null) {
                            putExtra("noteId", noteId)
                        }
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    }
                )

                Box(
                    modifier = GlanceModifier
                        .fillMaxSize()
                        .cornerRadius(16.dp)
                        .background(backgroundColor)
                        .clickable(onClick = clickAction)
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = noteContent,
                        style = TextStyle(
                            color = ColorProvider(Color.White),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        maxLines = 6
                    )
                }
            }
        }
    }
}
