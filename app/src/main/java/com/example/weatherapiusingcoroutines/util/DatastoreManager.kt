package com.example.weatherapiusingcoroutines.util

import kotlinx.coroutines.flow.Flow

interface DatastoreManager {
    suspend fun setUserLatestSearch(location: String)
    suspend fun getUserLatestSearch(): Flow<String>

}