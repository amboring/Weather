package com.example.weatherapiusingcoroutines.util

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class DatastoreManagerImp(val application: Application) : DatastoreManager {
    companion object {
        private const val DATASTORE_NAME = "preferences"

        private const val LAST_SEARCH = "LAST_SEARCH"
        private val KEY_LAST_SEARCH = stringPreferencesKey(LAST_SEARCH)


    }

    private val Application.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

    override suspend fun setUserLatestSearch(location: String) {
        application.dataStore.edit {
            it[KEY_LAST_SEARCH] = location
        }
    }

    override suspend fun getUserLatestSearch(): Flow<String> = application.dataStore.data.map {
        it[KEY_LAST_SEARCH] ?: ""
    }.distinctUntilChanged()


}