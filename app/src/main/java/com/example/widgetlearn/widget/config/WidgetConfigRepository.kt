package com.example.widgetlearn.widget.config

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WidgetConfigRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val json = Json { ignoreUnknownKeys = true }

    private fun configKey(appWidgetId: Int) = stringPreferencesKey("widget_config_$appWidgetId")

    suspend fun saveConfig(appWidgetId: Int, config: WidgetConfigData) {
        dataStore.edit { preferences ->
            preferences[configKey(appWidgetId)] = json.encodeToString(config)
        }
    }

    suspend fun getConfig(appWidgetId: Int): WidgetConfigData {
        return dataStore.data.map { preferences ->
            preferences[configKey(appWidgetId)]?.let { jsonString ->
                try {
                    json.decodeFromString<WidgetConfigData>(jsonString)
                } catch (e: Exception) {
                    WidgetConfigData()
                }
            } ?: WidgetConfigData()
        }.first()
    }

    suspend fun deleteConfig(appWidgetId: Int) {
        dataStore.edit { preferences ->
            preferences.remove(configKey(appWidgetId))
        }
    }
}
