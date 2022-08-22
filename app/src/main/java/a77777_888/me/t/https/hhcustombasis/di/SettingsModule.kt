package a77777_888.me.t.https.hhcustombasis.di

import a77777_888.me.t.https.hhcustombasis.model.SourceProvider
import a77777_888.me.t.https.hhcustombasis.model.settings.search.SearchSettings
import a77777_888.me.t.https.hhcustombasis.model.settings.search.SharedPreferencesSearchSettings
import a77777_888.me.t.https.hhcustombasis.source.RetrofitSourceProvider
import a77777_888.me.t.https.hhcustombasis.utils.logger.LogCatLogger
import a77777_888.me.t.https.hhcustombasis.utils.logger.Logger
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SettingsModule {

    @Binds
    fun bindSearchSettings(
        settings: SharedPreferencesSearchSettings
    ): SearchSettings

    @Binds
    fun bindSourceProvider(
        sourceProvider: RetrofitSourceProvider
    ): SourceProvider
}

@Module
@InstallIn(SingletonComponent::class)
class StuffsModule {

    @Provides
    fun provideLogger(): Logger = LogCatLogger
}