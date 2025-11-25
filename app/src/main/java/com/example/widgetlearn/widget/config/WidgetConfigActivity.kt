package com.example.widgetlearn.widget.config

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.updateAll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.example.widgetlearn.ui.theme.WidgetLearnTheme
import com.example.widgetlearn.widget.SimpleColorWidget
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WidgetConfigActivity : ComponentActivity() {

    private var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // デフォルトでキャンセル結果を設定
        setResult(RESULT_CANCELED)

        // appWidgetIdを取得
        appWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }

        enableEdgeToEdge()

        setContent {
            WidgetLearnTheme {
                val viewModel: WidgetConfigViewModel = hiltViewModel(
                    creationCallback = { factory: WidgetConfigViewModel.Factory ->
                        factory.create(appWidgetId)
                    }
                )

                WidgetConfigScreen(
                    viewModel = viewModel,
                    onSaveClick = { handleSave(viewModel) },
                    onCancelClick = { finish() }
                )
            }
        }
    }

    private fun handleSave(viewModel: WidgetConfigViewModel) {
        lifecycleScope.launch {
            val saved = viewModel.saveConfig()
            if (saved) {
                // ウィジェットを更新
                SimpleColorWidget().updateAll(this@WidgetConfigActivity)

                // 成功結果を設定
                val resultValue = Intent().apply {
                    putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                }
                setResult(RESULT_OK, resultValue)
            }
            finish()
        }
    }
}
