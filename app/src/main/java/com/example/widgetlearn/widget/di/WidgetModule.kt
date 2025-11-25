package com.example.widgetlearn.widget.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.widgetConfigDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "widget_config"
)

@Module
@InstallIn(SingletonComponent::class)
object WidgetModule {

    @Provides
    @Singleton
    fun provideWidgetConfigDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.widgetConfigDataStore
    }
}
